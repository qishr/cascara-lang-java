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


module cascara.lang.java {
    requires jdk.compiler;
    requires transitive jdk.javadoc;
    requires transitive java.compiler;

    requires transitive cascara.common;
    // requires cascara.common.io;

    exports io.github.qishr.cascara.lang.java;
    exports io.github.qishr.cascara.lang.java.exception;
    exports io.github.qishr.cascara.lang.java.model;
    exports io.github.qishr.cascara.lang.java.modeler;
    exports io.github.qishr.cascara.lang.java.processor;
    exports io.github.qishr.cascara.lang.java.token;
    exports io.github.qishr.cascara.lang.java.util;

    opens io.github.qishr.cascara.lang.java.model;
    opens io.github.qishr.cascara.lang.java.modeler;
    opens io.github.qishr.cascara.lang.java.processor;
    opens io.github.qishr.cascara.lang.java.util;
}
