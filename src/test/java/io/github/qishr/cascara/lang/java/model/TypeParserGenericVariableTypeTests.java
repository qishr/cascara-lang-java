// # License & Terms
//
// This file is part of **Cascara**.
//
// **Cascara** is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.
//
// ---
//
// ## Special Runtime Exception
//
// As a special exception, the copyright holders of this library give you
// permission to link this library with independent modules to produce an
// executable, regardless of the license terms of these independent modules,
// and to copy and distribute the resulting executable under terms of your
// choice, provided that you also meet, for each linked independent module,
// the terms and conditions of the license of that module.
//
// An independent module is a module which is not derived from or based on
// this library. If you modify this library, you may extend this exception
// to your version of the library, but you are not obligated to do so. If
// you do not wish to do so, delete this exception statement from your
// version.


package io.github.qishr.cascara.lang.java.model;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.lang.java.model.VariableTypeNode.BoundingKind;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.Generic;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.Sequence;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.TypeParameter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TypeParserGenericVariableTypeTests {

    @Test
    void generics_list_of_string() {
        String code = "java.util.List<java.lang.String>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("java.util.List", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        VariableTypeNode.Generic generic = typeRef.asGeneric();
        VariableTypeNode params = generic.getParams();

        assertFalse(params.isArray());
        assertFalse(params instanceof VariableTypeNode.Generic);
        assertFalse(params instanceof VariableTypeNode.Sequence);
        assertEquals("java.lang.String", params.getRawTypeName());
    }

    @Test
    void generics_hashmap_of_string_string() {
        String code = "HashMap<String,String>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("HashMap", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        VariableTypeNode.Generic generic = typeRef.asGeneric();
        VariableTypeNode params = generic.getParams();

        assertTrue(params instanceof VariableTypeNode.Sequence);
        VariableTypeNode.Sequence sequence = params.asSequence();

        VariableTypeNode param0 = sequence.getFirst();
        assertEquals("String", param0.getRawTypeName());

        VariableTypeNode param1 = sequence.getFirst();
        assertEquals("String", param1.getRawTypeName());

    }

    @Test
    void generics_list_of_string_array() {
        String code = "java.util.List<java.lang.String[]>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("java.util.List", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        VariableTypeNode.Generic generic = typeRef.asGeneric();
        VariableTypeNode params = generic.getParams();

        assertTrue(params instanceof TypeParameter);
        assertTrue(params.isArray());
        assertEquals("java.lang.String", params.getRawTypeName());
        assertEquals(1, params.arrayDimensions());
    }

    @Test
    void generics_list_of_2D_string_array() {
        String code = "java.util.List<java.lang.String[][]>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("java.util.List", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        VariableTypeNode.Generic generic = typeRef.asGeneric();
        VariableTypeNode params = generic.getParams();

        assertTrue(params.isArray());
        assertEquals("java.lang.String", params.getRawTypeName());
        assertEquals(2, params.arrayDimensions());
    }

    @Test
    void generics_2_params() {
        String code = "HashMap<VariableType0,VariableType1>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("HashMap", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        VariableTypeNode.Generic generic1 = typeRef.asGeneric();
        VariableTypeNode params = generic1.getParams();

        assertInstanceOf(VariableTypeNode.Sequence.class, params);
        VariableTypeNode.Sequence sequence = params.asSequence();

        VariableTypeNode param0 = sequence.getFirst();
        assertEquals("VariableType0", param0.getRawTypeName());

        VariableTypeNode param1 = sequence.get(1);
        assertEquals("VariableType1", param1.getRawTypeName());
    }

    @Test
    void generics_set_of_extends() {
        String code = "java.util.Set<? extends io.github.sandydunlop.markista.doclet.MarkdownDoclet.Option>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("java.util.Set", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        assertEquals(code, typeRef.toString());

        VariableTypeNode.Generic generic = typeRef.asGeneric();
        TypeParameter param = generic.getParams();
        assertEquals(BoundingKind.UPPER, generic.getBoundingKind());
        assertEquals("?", generic.getBoundingParameter());

        assertFalse(param.isArray());
        assertFalse(param instanceof VariableTypeNode.Generic);
        assertFalse(param instanceof VariableTypeNode.Sequence);
        assertEquals(null, param.asGeneric());
        assertEquals(null, param.asSequence());
    }

    @Test
    void generics_array_param() {
        String code = "Map<String,String[]>";
        VariableTypeNode map = VariableTypeParser.parseVariableType(code);
        assertEquals(code, map.toString());
        TypeParameter param2 = map.asGeneric().getParams().asSequence().getLast();
        assertTrue(param2.isArray());
    }

    @Test
    void generics_throws_exception() {
        String code = "Map<String,String[]>";
        VariableTypeNode map = VariableTypeParser.parseVariableType(code);
        assertEquals(code, map.toString());
        Sequence params = map.asGeneric().getParams().asSequence();
        assertThrows(NoSuchElementException.class, () -> params.get(2));
    }

    @Test
    void generics_throws_exception2() {
        String code = "Map<String,String[]>";
        VariableTypeNode map = VariableTypeParser.parseVariableType(code);
        assertEquals(code, map.toString());
        Sequence params = map.asGeneric().getParams().asSequence();

        Iterator<TypeParameter> iterator = params.iterator();
        TypeParameter param1 = iterator.next();
        assertEquals("String", param1.getRawTypeName());
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void generics_list_of_wildcard_extends_array_type() {
        String code = "List<? extends TypeParameter[]>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        assertEquals(code, type.toString());
    }

    @Test
    void genericCollection_T_extends_B() {
        String code = "Collection<T extends B>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void genericCollection_wildcard_super_B() {
        String code = "Collection<? super B>";
        VariableTypeNode string = VariableTypeParser.parseVariableType(code);
        String s = string.toString();
        assertEquals(code, s);
    }

    @Test
    void test_e() {
        String code = "java.util.Map<? extends javax.lang.model.element.ExecutableElement,? extends javax.lang.model.element.AnnotationValue>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void test_wildcard_super_T() {
        String code = "java.lang.Class<? super T>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void test_rl() {
        String code = "List<Pair<List<SwitchBlockStatementGroup>,List<SwitchLabel>>>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        //String s = type.toString();
        // assertEquals(code, s);
    }

    @Test
    void test_iterableProperty() {
        String code = "Iterable<Property<?>>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void generics_list_of_list_of_string() {
        String code = "java.util.List<java.util.List<java.lang.String>>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("java.util.List", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        VariableTypeNode.Generic generic1 = typeRef.asGeneric();
        VariableTypeNode params1 = generic1.getParams();
        assertEquals("java.util.List", params1.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, params1);

        VariableTypeNode.Generic generic2 = params1.asGeneric();
        VariableTypeNode params2 = generic2.getParams();

        assertFalse(params2.isArray());
        assertFalse(params2 instanceof VariableTypeNode.Generic);
        assertFalse(params2 instanceof VariableTypeNode.Sequence);
        assertEquals("java.lang.String", params2.getRawTypeName());
    }

    //

    @Test
    void generics_long() {
        String code = "HashMap<VariableType,List<Link>>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals("HashMap", typeRef.getRawTypeName());
        assertInstanceOf(VariableTypeNode.Generic.class, typeRef);

        VariableTypeNode.Generic generic1 = typeRef.asGeneric();
        VariableTypeNode params = generic1.getParams();

        assertInstanceOf(VariableTypeNode.Sequence.class, params);
        VariableTypeNode.Sequence sequence = params.asSequence();

        VariableTypeNode param0 = sequence.getFirst();
        assertEquals("VariableType", param0.getRawTypeName());

        VariableTypeNode param1 = sequence.get(1);
        assertInstanceOf(VariableTypeNode.Generic.class, param1);

        VariableTypeNode.Generic generic2 = param1.asGeneric();
        assertEquals("List", generic2.getRawTypeName());

        VariableTypeNode param2 = generic2.getParams();
        assertEquals("Link", param2.getRawTypeName());

        // String s = MarkdownUtils.formatVariableType(typeRef);
        // assertEquals("HashMap<VariableType, List<Link>>", s);
    }

    @Test
    void generics_three_params_and_nested() {
        String code = "Map<String,Text,List<?>>";
        VariableTypeNode typeRef = VariableTypeParser.parseVariableType(code);
        assertEquals(code, typeRef.toString());
    }

    @Test
    void generics_array_of_lists() {
        String code = "List<TypeParameter>[]";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void generics_array_of_lists_of_wildcard() {
        String code = "List<?>[]";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }

    @Test
    void test_f() {
        String code = "p.Modeller<jl.Module,jl.Package,jl.Class<?>,jl.reflect.Field,jl.reflect.Method,jl.reflect.Parameter>";
        VariableTypeNode type = VariableTypeParser.parseVariableType(code);
        String s = type.toString();
        assertEquals(code, s);
    }
}
