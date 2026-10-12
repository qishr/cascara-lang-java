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

import io.github.qishr.cascara.common.annotation.Nullable;
import io.github.qishr.cascara.common.diagnostic.report.NoOpReporter;
import io.github.qishr.cascara.common.diagnostic.report.Reporter;
import io.github.qishr.cascara.common.util.Pair;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.BoundingKind;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.Generic;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.Sequence;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode.TypeParameter;

public class VariableTypeParser {
    private static final Reporter REPORTER = new NoOpReporter();
    // private static final Reporter REPORTER = new LocalReporter().setLevel(Level.DEBUG);

    @Nullable
    public static VariableTypeNode parseVariableType(String typeName) {
        if (typeName == null || typeName.isEmpty()) {
            return new VariableTypeNode();
        }
        String fullTypeName = typeName.strip();
        VariableTypeNode type = parseTypes(fullTypeName);

        // int b = fullTypeName.indexOf("<");
        // if (b > -1) {
        //     type.rawTypeName = fullTypeName.substring(0, b);
        // } else {
        //     type.rawTypeName = fullTypeName;
        // }

        return type;
    }

    @Nullable
    private static TypeParameter parseTypes(String types) {
        List<TypeParameter> genericComponents = splitTypes(types);
        if (genericComponents.size() == 1) {
            return genericComponents.getFirst();
        } else if (genericComponents.size() > 1) {
            Sequence seq = new Sequence();
            for (TypeParameter type : genericComponents) {
                seq.append(type);
            }
            return seq;
        } else {
            return null;
        }
    }

    private static List<TypeParameter> splitTypes(String types) {
        List<TypeParameter> genericComponents = new ArrayList<>();
        int scanDepth = 0;
        int componentStart = 0;
        for (int pos = 0; pos < types.length(); pos++) {
            char c = types.charAt(pos);
            if (c == '<') {
                scanDepth++;
            } else if (c == '>') {
                scanDepth--;
            }
            if (scanDepth == 0) {
                if (c == ',') {
                    String component = types.substring(componentStart, pos);
                    REPORTER.debug("CPT: " + component);
                    genericComponents.add(parseSingleType(component));
                    componentStart = pos + 1;
                }
            }
        }
        String component = types.substring(componentStart);
        genericComponents.add(parseSingleType(component));
        return genericComponents;
    }

    private static TypeParameter parseSingleType(String type) {
        int openingChevron = type.indexOf("<");
        if (openingChevron == -1) {
            TypeParameter param = new TypeParameter();
            param.fullTypeName = type;

            int openingBracket = type.indexOf("[");
            if (openingBracket == -1) {
                param.rawTypeName = type;
            } else {
                param.rawTypeName = type.substring(0, openingBracket);
                parseArray(type.substring(openingBracket), param);
            }


            if (!param.rawTypeName.equals("?")) {
                try {
                    Reference ref = NameUtil.createReference(param.rawTypeName);
                    param.link = Link.to(ref);
                } catch (IllegalArgumentException e) {
                    // This should never happen
                }
            }


            return param;
        }
        int closingChevron = matchingClosingChevron(type, openingChevron);

        String before = type.substring(0, openingChevron).strip();
        String mid = type.substring(openingChevron + 1, closingChevron).strip();

        REPORTER.debug("MID: " + mid);

        String after = type.substring(closingChevron + 1).strip();
        TypeParameter inner = null;

        // `T extends` is called a type bound. It is a constraint rather than a statement or expression.
        // `<T extends Type>` is a type parameter declaration
        Pair<String,String> upperBound = parseBoundingConstraint("extends", mid);
        Pair<String,String> lowerBound = parseBoundingConstraint("super", mid);
        BoundingKind boundingType = BoundingKind.NONE;
        String typeParameter = "";
        if (upperBound != null) {
            boundingType = BoundingKind.UPPER;
            typeParameter = upperBound.getL();
            mid = upperBound.getR();
        } else if (lowerBound != null) {
            boundingType = BoundingKind.LOWER;
            typeParameter = lowerBound.getL();
            mid = lowerBound.getR();
        }

        inner = parseTypes(mid);

        TypeParameter parsedType = null;

        Generic generic = new Generic();

        generic.params = inner;
        generic.boundingKind = boundingType;
        generic.boundingParameter = typeParameter;
        generic.rawTypeName = before;



        if (!generic.rawTypeName.equals("?")) {
            try {
                Reference ref = NameUtil.createReference(generic.rawTypeName);
                generic.link = Link.to(ref);
            } catch (IllegalArgumentException e) {
                // This should never happen
            }
        }



        after = parseArray(after, generic);

        TypeParameter afterType = null;
        if (!after.isBlank()) {
           afterType = parseSingleType(after);
        }

        if (afterType == null) {
            parsedType = generic;
        } else {
            afterType.typeParameterDeclaration = generic;
            parsedType = afterType;
        }

        parsedType.fullTypeName = type;
        return parsedType;
    }

    private static String parseArray(String str, TypeParameter type) {
        REPORTER.debug("parseArray: " + str);

        int pos = 0;
        while (pos < str.length()) {
            char c = str.charAt(pos);
            if (c == ']' || Character.isWhitespace(c)) {
                pos++;
            } else if (c == '[') {
                type.arrayDimensions++;
                pos++;
            } else {
                return str.substring(pos);
            }
        }
        return str.substring(pos);
    }

    static void parseTypeParameter(String str, TypeParameter typeParameter) {
        if (str.isEmpty()) return;

        REPORTER.debug("parseTypeParameter: " + str);

        Pair<String,String> upperBound = parseBoundingConstraint("extends", str);
        Pair<String,String> lowerBound = parseBoundingConstraint("super", str);
        BoundingKind boundingType = BoundingKind.NONE;
        String typeParameterStr = "";
        if (upperBound != null) {
            boundingType = BoundingKind.UPPER;
            typeParameterStr = upperBound.getL();
            str = upperBound.getR();
        } else if (lowerBound != null) {
            boundingType = BoundingKind.LOWER;
            typeParameterStr = lowerBound.getL();
            str = lowerBound.getR();
        }
        typeParameter.boundingKind = boundingType;
        typeParameter.boundingParameter = typeParameterStr;

        int bracket = str.indexOf("[");
        if (bracket > -1) {
            typeParameter.rawTypeName = str.substring(0, bracket);
            parseArray(str.substring(bracket), typeParameter);
        } else {
            typeParameter.rawTypeName = str;
        }
        if (!typeParameter.rawTypeName.equals("?")) {
            try {
                Reference ref = NameUtil.createReference(typeParameter.rawTypeName);
                typeParameter.link = Link.to(ref);
            } catch (IllegalArgumentException e) {
                // This should never happen
            }
        }
    }

    private static Pair<String,String> parseBoundingConstraint(String keyword, String type) {
        String nameOrWildcard = "";
        String boundingType = "";
        int nameStart = -1;
        int boundingTypeStart = -1;
        int pos = 0;
        while (pos < type.length()) {
            char c = type.charAt(pos);
            if (nameStart == -1 && !Character.isWhitespace(c)) {
                nameStart = pos;
            } else if (nameStart > -1 && boundingTypeStart == -1 && Character.isWhitespace(c)) {
                nameOrWildcard = type.substring(nameStart, pos);
            } else if (boundingTypeStart == -1 && !Character.isWhitespace(c)) {
                boundingTypeStart = pos;
            } else if (boundingTypeStart > -1 && Character.isWhitespace(c)) {
                boundingType = type.substring(boundingTypeStart, pos);
                if (boundingType.equals(keyword)) {
                    String remainder = type.substring(pos + 1);
                    return Pair.of(nameOrWildcard,remainder);
                }
            }
            pos++;
        }
        return null;
    }

    //
    // Helpers
    //

    private static int matchingClosingChevron(String string, int pos) {
        int depth = 0;
        for (; pos < string.length(); pos++) {
            char c = string.charAt(pos);
            if (c=='<') {
                depth++;
            } else if (c== '>') {
                depth--;
            }
            if (depth == 0) break;
        }
        return pos;
    }

    public static String stringify(VariableTypeNode typeReference) {
        StringBuilder sb = new StringBuilder();
        if (typeReference.typeParameterDeclaration != null) {
            sb.append(typeReference.typeParameterDeclaration);
            sb.append(" ");
        }
        switch (typeReference) {
            case Generic generic -> {
                sb.append(generic.rawTypeName);
                sb.append("<");
                sb.append(boundingConstraint(generic));
                sb.append(stringify(generic.params));
                sb.append(">");
            }
            case Sequence sequence -> {
                for (VariableTypeNode element : sequence) {
                    if (!sb.isEmpty()) {
                        sb.append(",");
                    }
                    sb.append(stringify(element));
                }
            }
            case TypeParameter typeParameter -> {
                sb.append(boundingConstraint(typeParameter));
                sb.append(typeParameter.rawTypeName);
            }
            default -> sb.append(typeReference.rawTypeName);
        }
        for (int d = 0; d < typeReference.arrayDimensions; d++) {
            sb.append("[]");
        }
        return sb.toString();
    }

    private static String boundingConstraint(TypeParameter type) {
        if (type.getBoundingKind() == BoundingKind.UPPER) {
            return type.getBoundingParameter() + " extends ";
        } else if (type.getBoundingKind() == BoundingKind.LOWER) {
            return type.getBoundingParameter() + " super ";
        }
        return "";
    }
}
