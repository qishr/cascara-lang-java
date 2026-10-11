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

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/// See Java Language Specification (JLS) [JlsChapter 4. Types, Values, and Variables](https://docs.oracle.com/javase/specs/jls/se24/html/jls-4.html).
public class VariableTypeNode implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    String fullTypeName = "";
    Link link = null;

    String rawTypeName = "";

    int arrayDimensions = 0;
    VariableTypeNode typeParameterDeclaration = null;
    String variableName = "";
    boolean isVar = false;

    public boolean isVar() {
        return isVar;
    }

    public void setVar(boolean isVar) {
        this.isVar = isVar;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setIdentifier(String variableName) {
        this.variableName = variableName;
    }

    public VariableTypeNode() {
        // Nothing to see here
    }

    public boolean isArray() {
        return arrayDimensions > 0;
    }

    public int arrayDimensions() {
        return arrayDimensions;
    }


    public String getRawTypeName() {
        return rawTypeName;
    }

    public Link getLink() {
        return link;
    }

    public void setRawTypeName(String name) {
        if (name.contains("<")) {
            System.out.println("Debug: ParseResultHandler");
        }
        rawTypeName = name;
    }

    public void setFullTypeName(String name) {
        fullTypeName = name;
    }

    public String getFullTypeName() {
        return fullTypeName;
    }

    public VariableTypeNode getTypeParameterDeclaration() {
        return typeParameterDeclaration;
    }

    public Generic asGeneric() {
        if (this instanceof Generic parameterized) {
            return parameterized;
        }
        return null;
    }

    public Sequence asSequence() {
        if (this instanceof Sequence sequence) {
            return sequence;
        }
        return null;
    }

    public String toString() {
        return VariableTypeParser.stringify(this);
    }


    public enum BoundingKind {
        NONE,
        UPPER,
        LOWER
    }

    public static class TypeParameter extends VariableTypeNode {
        String boundingParameter = null;
        BoundingKind boundingKind = BoundingKind.NONE;

        TypeParameter() {
            // Nothing to see here
        }

        public String getBoundingParameter() {
            return boundingParameter;
        }

        public BoundingKind getBoundingKind() {
            return boundingKind;
        }
    }

    public static class Generic extends TypeParameter {
        TypeParameter params;

        Generic() {
            // Nothing to see here
        }

        public TypeParameter getParams() {
            return params;
        }
    }

    public static class Sequence extends TypeParameter implements Iterable<TypeParameter> {
        TypeParameter[] elements;
        int size;
        static final int INITIAL_CAPACITY = 4;

        Sequence() {
            elements = new TypeParameter[INITIAL_CAPACITY];
            size = 0;
        }

        private void resize() {
            int newCapacity = elements.length * 2;
            TypeParameter[] newArray = new TypeParameter[newCapacity];
            System.arraycopy(elements, 0, newArray, 0, elements.length);
            elements = newArray;
        }

        public int size() {
            return size;
        }

        public void append(TypeParameter element) {
            if (element instanceof Sequence sequence) {
                for (TypeParameter typeRef : sequence) {
                    append(typeRef);
                }
            } else {
                if (size == elements.length) {
                    resize();
                }
                elements[size++] = element;
            }
        }

        public TypeParameter get(int index) {
            if (index >= size) {
                throw new NoSuchElementException();
            }
            return elements[index];
        }

        public TypeParameter getFirst() {
            return get(0);
        }

        public TypeParameter getLast() {
            return get(size - 1);
        }

        @Override
        public Iterator<TypeParameter> iterator() {
            return new SequenceIterator();
        }

        class SequenceIterator implements Iterator<TypeParameter> {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size;
            }

            @Override
            public TypeParameter next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return elements[currentIndex++];
            }
        }
    }
}
