package com.joham.excel.annotaion;

import java.lang.annotation.*;

/**
 * Excel子单元格
 *
 * @author joham
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ChildExcel {

    int startColumn();

    int endColumn();

    Class clazz();
}
