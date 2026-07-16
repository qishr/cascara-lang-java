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

import java.util.ArrayList;
import java.util.List;

/// Represents an enum type node with its constants.
public class EnumNode extends TypeNode {
    private final List<FieldNode> constants = new ArrayList<>();

    /// Constructs an EnumNode with the specified simple name and package.
    /// Sets the kind to ENUM.
    /// @param name The name of the enum.
    public EnumNode(JlsName name) {
        super(name);
        kind = JavaSemanticNode.Kind.ENUM;
    }

    /// Adds a constant field to this enum.
    /// @param constant The FieldNode representing the enum constant.
    public void addConstant(FieldNode constant) {
        constants.add(constant);
    }

    /// Returns the list of enum constants.
    /// @return List of FieldNode constants.
    public List<FieldNode> getConstants() {
        return constants;
    }

    @Override
    public FieldNode getField(String constantName) {
        for (FieldNode fieldNode : constants) {
            if (fieldNode.getName().simpleName().equals(constantName)) {
                return fieldNode;
            }
        }
        return null;
    }
}
