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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.lang.java.model.JlsName;
import io.github.qishr.cascara.lang.java.model.NameUtil;

class NameTests {
    @Test
    void test_create_package_name() {
        JlsName name = NameUtil.createPackageName("com.example");

        assertTrue(name.isPackage());
        assertFalse(name.isType());
        assertFalse(name.isMember());

        assertEquals("com.example", name.packageName().toString());
        assertEquals("com.example", name.fullyQualifiedName());
    }

    @Test
    void test_create_type_name() {
        JlsName name = NameUtil.createTypeName("Type");

        assertFalse(name.isPackage());
        assertTrue(name.isType());
        assertFalse(name.isMember());

        assertEquals("Type", name.typeName().simpleName());
        assertEquals("Type", name.simpleName());
    }

    @Test
    void test_create_type_name_from_package_and_string() {
        JlsName packageName = NameUtil.createPackageName("com.example");

        JlsName name = NameUtil.createTypeName(packageName, "Type");

        assertFalse(name.isPackage());
        assertTrue(name.isType());
        assertFalse(name.isMember());

        assertEquals("com.example", name.packageName().toString());
        assertEquals("Type", name.typeName().simpleName());

        assertEquals("com.example.Type", name.fullyQualifiedName());
        assertEquals("Type", name.simpleName());
    }

    @Test
    void test_create_type_name_from_null_and_string() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NameUtil.createTypeName(null, "Type");
        });

        String expectedMessage = "must not be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void test_create_type_name_from_package_and_type() {
        JlsName packageName = NameUtil.createPackageName("com.example");
        JlsName typeName = NameUtil.createTypeName("Type");

        JlsName name = NameUtil.createTypeName(packageName, typeName);

        assertFalse(name.isPackage());
        assertTrue(name.isType());
        assertFalse(name.isMember());

        assertEquals("com.example", name.packageName().toString());
        assertEquals("Type", name.typeName().simpleName());

        assertEquals("com.example.Type", name.fullyQualifiedName());
        assertEquals("Type", name.simpleName());
    }

    @Test
    void test_create_type_name_from_null_and_type() {
        JlsName typeName = NameUtil.createTypeName("Type");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NameUtil.createTypeName(null, typeName);
        });

        String expectedMessage = "must not be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void test_create_type_name_from_type_and_type() {
        JlsName packageName = NameUtil.createPackageName("com.example");
        JlsName outerTypeName = NameUtil.createTypeName(packageName, "Type");
        JlsName nestedTypeName = NameUtil.createTypeName("Nested");

        JlsName name = NameUtil.createTypeName(outerTypeName, nestedTypeName);

        assertFalse(name.isPackage());
        assertTrue(name.isType());
        assertTrue(name.isMember());

        assertEquals("com.example", name.packageName().toString());

        assertEquals("com.example.Type.Nested", name.fullyQualifiedName());
        assertEquals("Type.Nested", name.binaryName());
        assertEquals("Nested", name.simpleName());
    }

    @Test
    void test_create_member_name_from_type_and_string() {
        JlsName packageName = NameUtil.createPackageName("com.example");
        JlsName typeName = NameUtil.createTypeName(packageName, "Type");

        JlsName name = NameUtil.createMemberName(typeName, "member");

        assertFalse(name.isPackage());
        assertFalse(name.isType());
        assertTrue(name.isMember());

        assertEquals("com.example", name.packageName().toString());
        assertEquals("Type", name.typeName().simpleName());

        assertEquals("com.example.Type.member", name.fullyQualifiedName());
        assertEquals("member", name.simpleName());
    }

    @Test
    void test_create_member_name_from_type_and_type() {
        JlsName packageName = NameUtil.createPackageName("com.example");
        JlsName outerTypeName = NameUtil.createTypeName(packageName, "Type");
        JlsName nestedTypeName = NameUtil.createTypeName("Nested");

        JlsName name = NameUtil.createMemberName(outerTypeName, nestedTypeName);

        assertFalse(name.isPackage());
        assertTrue(name.isType());
        assertTrue(name.isMember());

        assertEquals("com.example", name.packageName().toString());

        assertEquals("com.example.Type.Nested", name.fullyQualifiedName());
        assertEquals("Type.Nested", name.binaryName());
        assertEquals("Nested", name.simpleName());
    }

    @Test
    void test_create_member_name_from_nested_type_and_type() {
        JlsName packageName = NameUtil.createPackageName("com.example");
        JlsName outerTypeName = NameUtil.createTypeName(packageName, "Type");
        JlsName middleTypeName = NameUtil.createTypeName(outerTypeName, "Middle");
        JlsName nestedTypeName = NameUtil.createTypeName("Inner");

        JlsName name = NameUtil.createMemberName(middleTypeName, nestedTypeName);

        assertFalse(name.isPackage());
        assertTrue(name.isType());
        assertTrue(name.isMember());

        assertEquals("com.example", name.packageName().toString());

        assertEquals("com.example.Type.Middle.Inner", name.fullyQualifiedName());
        assertEquals("Type.Middle.Inner", name.binaryName());
        assertEquals("Inner", name.simpleName());
    }

    @Test
    void test_create_nested_type_equals_create_member() {
        JlsName packageName = NameUtil.createPackageName("com.example");
        JlsName outerTypeName = NameUtil.createTypeName(packageName, "Type");

        JlsName nestedTypeName = NameUtil.createTypeName(outerTypeName, "Nested");
        JlsName classMemberName = NameUtil.createMemberName(outerTypeName, "Nested");

        assertEquals(nestedTypeName, classMemberName);
    }

    @Test
    void test_invalid_characters() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            NameUtil.createPackageName("com/example");
        });

        String expectedMessage = "is not a valid name";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
