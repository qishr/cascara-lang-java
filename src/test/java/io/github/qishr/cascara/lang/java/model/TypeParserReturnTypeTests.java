package io.github.qishr.cascara.lang.java.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TypeParserReturnTypeTests {
    @Test
    void test_A_extends_type_A() {
        String code = "<A extends java.lang.annotation.Annotation> A";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void genericMethodReturnType() {
        String code = "<E> java.util.List<E>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void test_T_extends_type_T_T() {
        String code = "<T extends java.lang.Enum<T>> T";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }
}
