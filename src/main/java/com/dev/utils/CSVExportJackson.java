package com.dev.utils;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class CSVExportJackson {

    public static <T> void write(Path csvFile,T item, Class<T> type, boolean append) throws Exception {
        JavaTimeModule time = new JavaTimeModule();

        CsvMapper mapper = new CsvMapper();
        mapper.registerModule(time);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        CsvSchema schema = mapper.schemaFor(type).withHeader();

        if (append && Files.exists(csvFile)) {
            schema = schema.withoutHeader();
        }

        var openOptions = append
                ? new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND}
                : new StandardOpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING};

        try (var writer = Files.newBufferedWriter(csvFile, openOptions)) {
            mapper.writer(schema).writeValues(writer).write(item);
        }
    }
    
}
