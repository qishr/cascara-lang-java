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

import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.lang.java.model.JlsName;
import io.github.qishr.cascara.lang.java.model.NameUtil;
import io.github.qishr.cascara.lang.java.model.Reference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;

class ReferenceTests {
    @Test
    void test_toString() {
        Reference ref = NameUtil.createReference("java.base/java.lang.String#toString()");
        assertEquals("java.base", ref.getModuleName());
        JlsName name = ref.getName();
        assertTrue(name.isMember());
        assertEquals("java.base/java.lang.String.toString()", ref.toString());
    }

    @Test
    void test_toString_noModule_1() {
        Reference ref = NameUtil.createReference("java.lang.String#toString()");
        assertEquals("", ref.getModuleName());
        JlsName name = ref.getName();
        assertTrue(name.isMember());
        assertEquals("java.lang.String.toString()", ref.toString());
    }

    @Test
    void test_toString_noModule_2() {
        Reference ref = NameUtil.createReference("java.lang.String#toString()");
        ref.setModule(null);
        assertNull(ref.getModuleName());
        JlsName name = ref.getName();
        assertTrue(name.isMember());
        assertEquals("java.lang.String.toString()", ref.toString());
    }

    @Test
    void test_toString_module_nameEmpty() {
        Reference ref = NameUtil.createReference("java.base/");
        ref.setName(new JlsName());
        assertEquals("java.base", ref.getModuleName());
        assertEquals("java.base/", ref.toString());
    }

    @Test
    void test_toString_module_nameUnset() {
        Reference ref = NameUtil.createReference("java.base/");
        assertEquals("java.base", ref.getModuleName());
        assertEquals("java.base/", ref.toString());
    }

    @Disabled
    @Test
    void test_method_only() {
        Reference ref = NameUtil.createReference("#methodname");
        assertEquals("methodname", ref.getName().simpleName());
    }

    @Test
    void test_null() {
        assertThrows(IllegalArgumentException.class, () -> {
            NameUtil.createReference(null);
        });
    }

    @Test
    void test_blank() {
        assertThrows(IllegalArgumentException.class, () -> {
            NameUtil.createReference("");
        });
    }
}
