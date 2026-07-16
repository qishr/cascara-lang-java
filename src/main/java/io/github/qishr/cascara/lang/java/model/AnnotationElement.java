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

/// An element of an annotation
public class AnnotationElement extends JavaSemanticNode {
    private final String value;

    /// The simple form of the name
    protected String simpleName = "";

    protected VariableTypeNode type;

    /// Constructs an annotation element
    /// @param type The type of the annotation element
    /// @param name The name of the annotation element
    /// @param value The value of the annotation element
    public AnnotationElement(VariableTypeNode type, String name, String value) {
        this.type = type;
        this.simpleName = name;
        this.value = value;
    }

    /// Sets the simple name of this type.
    /// @param name the simple name to set.
    public void setSimpleName(String name) {
        simpleName = name;
    }

    /// Returns the simple name of this type.
    /// @return the simple name.
    public String getSimpleName() {
        return simpleName;
    }

    /// Gets the value of this annotation element
    /// @return The value of the element
    public String getValue() {
        return this.value;
    }

    /// Returns the type of this element.
    /// @return The name of the element's type.
    public VariableTypeNode getTypeName() {
        return type;
    }

    /// Sets the type of this element.
    /// @param type The name of this element's type.
    public void setTypeName(VariableTypeNode type) {
        this.type = type;
    }
}
