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


/// An annotation applied to a type
public class AppliedAnnotationNode extends JavaSemanticNode {
    private boolean custom = false;
    private boolean documented = false;
    private JlsName typeName;
    private final List<AnnotationElement> elements = new ArrayList<>();

    /// Constructs an AppliedAnnotationNode
    public AppliedAnnotationNode() {
        // Only here for the comments
    }

    /// Constructs an AppliedAnnotationNode with the given type
    /// @param typeName The type of this annotation
    public AppliedAnnotationNode(JlsName typeName) {
        this.typeName = typeName;
    }


    /// Specifies if this annotation will be treated as custom or built-in.
    /// @param b If true, set that this annotation is a custom one
    public void setCustom(boolean b) {
        custom = b;
    }

    /// Indicates if this is a custom annotation
    /// @return True if the annotation is a custom one, otherwise false
    public boolean isCustom() {
        return custom;
    }

    /// Sets the documented flag, indicating that this annotation should
    /// be displayed in the documentation of types or fields that it is
    /// applied to.
    /// @param b If true, this annotation will appear in the documentation
    /// of types and fields it is applied to.
    public void setDocumented(boolean b) {
        documented = b;
    }

    /// Gets the flag the indicates this annotation should appear in
    /// the documentation of the type or field it is applied to.
    /// @return True if this annotation should appear in such documentation.
    public boolean isDocumented() {
        return documented;
    }

    /// Returns the type of this annotation.
    /// @return The name of the annotation's type.
    public JlsName getTypeName() {
        return typeName;
    }

    /// Sets the type of this annotation.
    /// @param type The name to set as this annotation's type.
    public void setTypeName(JlsName type) {
        this.typeName = type;
    }

    /// Adds an element to this annotation
    /// @param element the element to add to this annotation
    public void addElement(AnnotationElement element) {
        elements.add(element);
    }

    /// Returns the list of elements of this annotation.
    /// @return list of elements belonging to this annotation.
    public List<AnnotationElement> getElements() {
        return elements;
    }
}
