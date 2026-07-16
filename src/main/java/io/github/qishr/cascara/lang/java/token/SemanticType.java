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


package io.github.qishr.cascara.lang.java.token;

public enum SemanticType {
    /** Default for all non-identifier tokens and identifiers that couldn't be resolved. */
    NONE,

    GLOBAL,

    /** Identifies the name of a class, record, or enum. */
    CLASS_NAME,

    /** Identifies the name of an interface or annotation type. */
    INTERFACE_NAME,

    /** Identifies the name of an annotation (e.g., @Override, @SuppressWarnings). */
    ANNOTATION_NAME,

    /** Identifies the name of a method, including constructors. */
    METHOD_NAME,

    /** Identifies a field (instance variable) or static variable within a class. */
    FIELD_NAME,

    /** Identifies a local variable, including formal parameters in method declarations. */
    LOCAL_VARIABLE_NAME,

    /** Identifies the name of a package or part of a package name (e.g., 'java' in java.util). */
    PACKAGE_NAME,

    /** Reserved for primitive types that are often highlighted differently (e.g., int, boolean).
     * These are typically handled by JavaTokenType.KEYWORD_INT, etc., but included here for completeness
     * if the Analyzer needs to differentiate them from Class names in Type contexts. */
    PRIMITIVE_TYPE,

    PARAMETER_NAME,
    ENUM_CONSTANT,
    TYPE_REFERENCE,
    OPERATOR,
    MODIFIER,
    CONSTRUCTOR_NAME

}