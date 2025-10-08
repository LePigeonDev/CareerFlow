package com.dev.Annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
    String header();   // Nom de la colonne dans l'entête Excel
    int order() default Integer.MAX_VALUE; // Ordre d'écriture
}