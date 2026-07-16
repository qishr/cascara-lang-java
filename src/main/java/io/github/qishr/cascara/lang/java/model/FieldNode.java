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

import java.io.Serializable;

/// A class to hold information about fields within a class, interface, or enum.
public class FieldNode extends VariableNode {

    /// The constant value assigned to this field, if any.
    private Serializable constantValue = null;

    private VariableTypeNode constantValueReference = null;

    /// Constructs a FieldNode with the given type and name.
    /// @param type The type of the field.
    /// @param name The simple name of the field.
    public FieldNode(VariableTypeNode type, JlsName name) {
        super(type, name);
        kind = JavaSemanticNode.Kind.FIELD;
    }

    /// Returns the constant value of this field.
    /// @return The constant value of the field or null if none.
    public Serializable getConstantValue() {
        return constantValue;
    }

    /// Sets the constant value of this field.
    /// @param constantValue The constant value to set.
    public void setConstantValue(Serializable constantValue) {
        this.constantValue = constantValue;
    }

    public void setConstantValueReference(VariableTypeNode ref) {
        constantValueReference = ref;
    }

    public VariableTypeNode getConstantValueReference() {
        return constantValueReference;
    }

    /// Returns the full signature of the field including modifiers and name.
    /// @return The string representing the full signature.
    public String fullSignature() {
        String sig = getModifiersString();
        if (!sig.isEmpty()) {
            sig += " ";
        }
        sig += name.simpleName();
        return sig;
    }
}
