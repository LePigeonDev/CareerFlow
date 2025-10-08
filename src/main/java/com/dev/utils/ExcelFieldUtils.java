package com.dev.utils;

import com.dev.Annotation.ExcelColumn;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Utilitaire pour analyser les champs annotés @ExcelColumn
 * et les trier selon leur ordre.
 */
public class ExcelFieldUtils {

    public static class FieldMeta {
        public Field field;
        public String header;
        public int order;

        public FieldMeta(Field f, String h, int o) {
            this.field = f;
            this.header = h;
            this.order = o;
            f.setAccessible(true);
        }
    }

    /**
     * Récupère tous les champs annotés @ExcelColumn d'une classe,
     * les trie selon leur "order", et renvoie la liste.
     */
    public static List<FieldMeta> getOrderedAnnotatedFields(Class<?> clazz) {
        List<FieldMeta> list = new ArrayList<>();

        for (Field f : clazz.getDeclaredFields()) {
            ExcelColumn ann = f.getAnnotation(ExcelColumn.class);
            if (ann != null) {
                list.add(new FieldMeta(f, ann.header(), ann.order()));
            }
        }

        list.sort(Comparator.comparingInt(m -> m.order));

        if (list.isEmpty()) {
            throw new IllegalArgumentException("Aucun champ annoté @ExcelColumn trouvé sur " + clazz.getName());
        }

        return list;
    }
}
