package com.dev.utils;

import com.dev.Entity.Company;
import com.dev.utils.ExcelFieldUtils;
import com.dev.utils.ExcelFieldUtils.FieldMeta;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class XlsxWrite {

    // Chemin conservé tel que demandé
    private static String _FILEPATH_ = "src/main/data/xlsx/excelCandidature.xlsx";
    private static String _SHEETNAME_ = "Candidatures";

    public static void writeToExcel(Company company) {
        Workbook workbook = null;

        try {
            File file = resolveAndPrepare();

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                }
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet(_SHEETNAME_);
            if (sheet == null) sheet = workbook.createSheet(_SHEETNAME_);

            List<FieldMeta> cols = ExcelFieldUtils.getOrderedAnnotatedFields(Company.class);

            boolean sheetIsEmpty = sheet.getPhysicalNumberOfRows() == 0;
            if (sheetIsEmpty) {
                Row header = sheet.createRow(0);
                for (int i = 0; i < cols.size(); i++) {
                    header.createCell(i).setCellValue(cols.get(i).header);
                }
            }

            // Colonnes à centrer (texte/numérique) et à centrer (dates)
            Set<String> centeredTextHeaders = Set.of("ID", "Canal_Envoi", "Statut");
            Set<String> centeredDateHeaders = Set.of("Date_Candidature", "Date_Relance");

            // Index de la colonne ID
            int idColIndex = 0;
            for (int i = 0; i < cols.size(); i++) {
                if ("ID".equalsIgnoreCase(cols.get(i).header)) { idColIndex = i; break; }
            }

            // Dernière ligne réellement remplie (ignore lignes vides préformatées)
            int lastDataRow = findLastDataRowFromTop(sheet, idColIndex, cols.size());
            int targetRowIndex = (lastDataRow <= 0) ? 1 : lastDataRow + 1;

            // Styles
            CreationHelper ch = workbook.getCreationHelper();
            DataFormat df = ch.createDataFormat();

            CellStyle leftStyle = workbook.createCellStyle();
            leftStyle.setAlignment(HorizontalAlignment.LEFT);
            leftStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle centerStyle = workbook.createCellStyle();
            centerStyle.setAlignment(HorizontalAlignment.CENTER);
            centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle dateLeftStyle = workbook.createCellStyle();
            dateLeftStyle.setDataFormat(df.getFormat("dd/mm/yyyy"));
            dateLeftStyle.setAlignment(HorizontalAlignment.LEFT);
            dateLeftStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CellStyle dateCenteredStyle = workbook.createCellStyle();
            dateCenteredStyle.setDataFormat(df.getFormat("dd/mm/yyyy"));
            dateCenteredStyle.setAlignment(HorizontalAlignment.CENTER);
            dateCenteredStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            // Prochain ID si nécessaire
            Integer lastId = readIdAt(sheet, idColIndex, lastDataRow);
            int idToWrite = (lastId == null ? 0 : lastId) + 1;

            // Écriture
            Row row = sheet.getRow(targetRowIndex);
            if (row == null) row = sheet.createRow(targetRowIndex);

            for (int c = 0; c < cols.size(); c++) {
                FieldMeta m = cols.get(c);
                String header = m.header;
                Object value = m.field.get(company);

                // Auto-incrément de l'ID si manquant/≤0
                if ("ID".equalsIgnoreCase(header)) {
                    Integer currentId = (value instanceof Number) ? ((Number) value).intValue() : null;
                    if (currentId == null || currentId <= 0) value = idToWrite;
                }

                Cell cell = row.createCell(c);
                writeCellValueOnly(cell, value); // on écrit la valeur SANS style

                // Appliquer style selon la colonne
                if (centeredDateHeaders.contains(header)) {
                    cell.setCellStyle(dateCenteredStyle);
                } else if (centeredTextHeaders.contains(header)) {
                    cell.setCellStyle(centerStyle);
                } else if (value instanceof LocalDate) {
                    cell.setCellStyle(dateLeftStyle);
                } else {
                    cell.setCellStyle(leftStyle);
                }
            }

            if (sheetIsEmpty) {
                for (int i = 0; i < cols.size(); i++) sheet.autoSizeColumn(i);
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
            }
            System.out.println("✅ Company ajoutée sur la ligne " + (targetRowIndex + 1));

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout dans l'Excel: " + e.getMessage(), e);
        } finally {
            if (workbook != null) {
                try { workbook.close(); } catch (IOException ignored) {}
            }
        }
    }

    // ========= Helpers =========

    private static File resolveAndPrepare() throws IOException {
        Path path = Paths.get(_FILEPATH_).toAbsolutePath();
        Files.createDirectories(path.getParent());
        System.out.println("📁 Écriture dans : " + path);
        return path.toFile();
    }

    /**
     * Parcourt depuis la ligne 1 et renvoie l'index (0-based) de la dernière ligne
     * contenant des données. S'arrête à la première ligne réellement vide.
     */
    private static int findLastDataRowFromTop(Sheet sheet, int idColIndex, int colsCount) {
        DataFormatter fmt = new DataFormatter();
        int last = 0;
        int maxRow = sheet.getLastRowNum();
        for (int r = 1; r <= maxRow; r++) {
            Row row = sheet.getRow(r);
            if (row == null) break;
            if (isRowCompletelyEmpty(row, colsCount, fmt)) break;

            String idVal = "";
            Cell idCell = row.getCell(idColIndex);
            if (idCell != null) idVal = fmt.formatCellValue(idCell).trim();
            if (!idVal.isEmpty() || !isRowCompletelyEmpty(row, colsCount, fmt)) {
                last = r;
            }
        }
        return last;
    }

    private static boolean isRowCompletelyEmpty(Row row, int colsCount, DataFormatter fmt) {
        for (int c = 0; c < colsCount; c++) {
            Cell cell = row.getCell(c);
            if (cell == null) continue;
            String v = fmt.formatCellValue(cell).trim();
            if (!v.isEmpty()) return false;
        }
        return true;
    }

    private static Integer readIdAt(Sheet sheet, int idColIndex, int rowIndex) {
        if (rowIndex <= 0) return null;
        Row row = sheet.getRow(rowIndex);
        if (row == null) return null;
        Cell cell = row.getCell(idColIndex);
        if (cell == null) return null;

        DataFormatter fmt = new DataFormatter();
        String s = fmt.formatCellValue(cell).trim();
        if (s.isEmpty()) return null;
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
    }

    /** Écrit UNIQUEMENT la valeur dans la cellule, sans appliquer de style. */
    private static void writeCellValueOnly(Cell cell, Object v) {
        if (v == null) { cell.setBlank(); return; }
        if (v instanceof Number n) { cell.setCellValue(n.doubleValue()); return; }
        if (v instanceof Boolean b) { cell.setCellValue(b); return; }
        if (v instanceof LocalDate d) {
            Date date = Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
            cell.setCellValue(date);
            return;
        }
        cell.setCellValue(v.toString());
    }

    // Setters optionnels
    public static void setFilePath(String path) { _FILEPATH_ = path; }
    public static void setSheetName(String name) { _SHEETNAME_ = name; }
}
