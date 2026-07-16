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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.common.util.Pair;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PairTests {
    Pair<String,String> pair;

    @BeforeEach
    void setup() {
        pair = Pair.of("one","two");
    }

    @Disabled
    @Test
    void whenSerializingAndDeserializing_ThenObjectIsTheSame() throws IOException, ClassNotFoundException {
        FileOutputStream fileOutputStream = new FileOutputStream("build/yourfile.txt");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(pair);
        objectOutputStream.flush();
        objectOutputStream.close();

        FileInputStream fileInputStream = new FileInputStream("build/yourfile.txt");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Pair<String, String> p2 = safeCast(objectInputStream.readObject());
        objectInputStream.close();

        assertEquals(p2.getL(), pair.getL());
        assertEquals(p2.getR(), pair.getR());
    }

    @SuppressWarnings("unchecked")
    private <L extends Serializable, R extends Serializable> Pair<L, R> safeCast(Object obj) {
        return (Pair<L, R>) obj;
    }

    @Test
    void setAndGet() {
        pair.setL("left");
        pair.setR("right");
        assertEquals("left", pair.getL());
        assertEquals("right", pair.getR());
    }

    @Test
    void test_toString() {
        assertEquals("Pair[one,two]", pair.toString());
    }

    @Test
    void test_hashCode() {
        pair.setL(null);
        pair.setR(null);
        assertEquals(0, pair.hashCode());

        String right = "right";
        pair.setR(right);
        assertEquals(right.hashCode() + 1, pair.hashCode());

        String left = "left";
        pair.setL(left);
        pair.setR(null);
        assertEquals(left.hashCode() + 2, pair.hashCode());

        pair.setR(right);
        int expected = left.hashCode() * 17 + right.hashCode();
        assertEquals(expected, pair.hashCode());
    }

    @Test
    void test_equals() {
        Pair<String,String> p = Pair.of(pair.getL(), pair.getR());
        assertEquals(pair, p);
    }

    @Test
    void test_equals_notEqual_0() {
        Pair<Integer,Integer> p = Pair.of(0, 1);
        assertNotEquals(pair, p);
    }

    @Test
    void test_equals_notEqual_1() {
        Pair<String,String> p = Pair.of("different", pair.getR());
        assertNotEquals(pair, p);
    }

    @Test
    void test_equals_notEqual_2() {
        Pair<String,String> p = Pair.of(pair.getL(), "different");
        assertNotEquals(pair, p);
    }

    @Test
    void test_equals_notEqual_3() {
        Pair<String,String> p = Pair.of("notsame", "different");
        assertNotEquals(pair, p);
    }

    @Test
    void test_equals_notEqual_4() {
        String s = "notapair";
        assertNotEquals(pair, s);
    }
}
