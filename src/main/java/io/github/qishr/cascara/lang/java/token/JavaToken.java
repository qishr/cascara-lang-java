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

import io.github.qishr.cascara.common.lang.token.Token;

public class JavaToken implements Token {

    private JavaTokenType type;
    private String lexeme;
    private int offset;
    private int line;
    private int column;
    private SemanticType semtype;

    public JavaToken(JavaTokenType type, String lexeme, int start, int startLine, int startCol, SemanticType unknown) {
        this.type = type;
        this.lexeme = lexeme;
        this.offset = start;
        this.line = startLine;
        this.column = startCol;
        this.semtype = unknown;
    }

    @Override
    public String toString() {
        return String.format("[%-18s | '%-15s' | L:%d C:%d]",
            type,
            // Simple escaping for display
            lexeme.replace("\n", "\\n").replace("\r", "\\r").replace("\"", "\\\""),
            line,
            column);
    }

    public JavaTokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getOffset() {
        return offset;
    }

    public int getStartLine() {
        return line;
    }

    public int getStartColumn() {
        return column;
    }

    public SemanticType getSemtype() {
        return semtype;
    }



    @Override
    public String getContent() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getValue'");
    }
}
