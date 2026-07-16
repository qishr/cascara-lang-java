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


/// Enum representing Java language modifiers with their string
/// representations.
///
public enum Modifier {
    /// The public modifier.
    PUBLIC("public"),

    /// The protected modifier.
    PROTECTED("protected"),

    /// The private modifier.
    PRIVATE("private"),

    /// The static modifier.
    STATIC("static"),

    /// The final modifier.
    FINAL("final"),

    /// The abstract modifier.
    ABSTRACT("abstract"),

    /// The synchronized modifier.
    SYNCHRONIZED("synchronized"),

    /// The transient modifier.
    TRANSIENT("transient"),

    /// The volatile modifier.
    VOLATILE("volatile"),

    /// The native modifier.
    NATIVE("native"),

    /// The strictfp modifier.
    STRICTFP("strictfp"),

    /// The default modifier.
    DEFAULT("default");


    /// The string representation of the modifier.
    private final String name;

    /// Constructs a Modifier enum constant with the specified string value.
    /// @param s The string representation of the modifier.
    Modifier(String s) {
        name = s;
    }

    /// Returns the string representation of this modifier.
    /// @return The string name of the modifier.
    @Override
    public String toString() {
        return this.name;
    }
}
