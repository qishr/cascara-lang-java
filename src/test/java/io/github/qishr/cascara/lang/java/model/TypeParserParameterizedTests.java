package io.github.qishr.cascara.lang.java.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.lang.java.model.VariableTypeNode.Generic;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.Sequence;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.TypeParameter;

public class TypeParserParameterizedTests {


    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(strings = {
        "Set<List<HashMap<String,HashMap<String,List<?>>>>>",
        "Test<String,String,String,String,String>",
        "List<?>",
        "Map<String,List<?>>"
    })
    void generics_various_cases(String code) {
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals(code, typeRef.toString());

        // Additional checks for "Map<String,List<?>>"
        if ("Map<String,List<?>>".equals(code)) {
            assertInstanceOf(Generic.class, typeRef);
            Sequence mapParams = typeRef.asGeneric().getParams().asSequence();
            assertEquals(2, mapParams.size());
            Generic list = mapParams.getLast().asGeneric();
            assertEquals("List", list.getRawTypeName());
            assertNotNull(list.asGeneric().getParams());
            TypeParameter wildcard = list.asGeneric().getParams();
            assertNotNull(wildcard);
        }
    }

}
