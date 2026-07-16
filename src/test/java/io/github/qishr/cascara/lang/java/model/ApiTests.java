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

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.github.qishr.cascara.lang.java.model.PackageNode;
import io.github.qishr.cascara.lang.java.model.SemanticModel;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiTests {
    public static Stream<Arguments> commonBaseCases() {
        return Stream.of(
            // two different top-level packages -> no common base
            Arguments.of(List.of("one", "zero"), ""),
            Arguments.of(List.of("zero", "one"), ""),

            // one and one.two -> no common base (first package is top-level)
            Arguments.of(List.of("one", "one.two"), ""),

            // siblings under 'one' -> common base is 'one'
            Arguments.of(List.of("one.ten", "one.two"), "one"),

            // deeper/nested combinations
            Arguments.of(List.of("one.two.three", "one.two"), "one"),
            Arguments.of(List.of("one.two", "one.two.three"), "one"),

            // single package -> trim last segment
            Arguments.of(List.of("one.two.three"), "one.two"),

            // sibling deep packages -> common base 'one.two'
            Arguments.of(List.of("one.two.three", "one.two.zero"), "one.two"),
            Arguments.of(List.of("one.two.zero", "one.two.three"), "one.two"),

            // parent/child deep relationship -> common base 'one.two'
            Arguments.of(List.of("one.two.three", "one.two.three.four"), "one.two"),
            Arguments.of(List.of("one.two.three.four", "one.two.three"), "one.two"),

            // two vs four-depth -> common base 'one'
            Arguments.of(List.of("one.two", "one.two.three.four"), "one"),
            Arguments.of(List.of("one.two.three.four", "one.two"), "one")
        );
    }

    // @ParameterizedTest(name = "commonBase for packages {0} => ''{1}''")
    // @MethodSource("commonBaseCases")
    // void commonBase_various(List<String> packageNames, String expectedBase) {
    //     SemanticModel api = new SemanticModel("Test API");
    //     for (String pkg : packageNames) {
    //         api.addPackage(new PackageNode(pkg));
    //     }
    //     String base = api.commonBase();
    //     assertEquals(expectedBase, base);
    // }
}
