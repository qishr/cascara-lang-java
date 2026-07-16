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

import io.github.qishr.cascara.common.lang.token.TokenCategory;
import io.github.qishr.cascara.common.lang.token.TokenType;

public enum JavaTokenType implements TokenType {
        // Reserved Keywords (51 words)
    KEYWORD_ABSTRACT, KEYWORD_ASSERT, KEYWORD_BOOLEAN, KEYWORD_BREAK,
    KEYWORD_BYTE, KEYWORD_CASE, KEYWORD_CATCH, KEYWORD_CHAR,
    KEYWORD_CLASS, KEYWORD_CONST, KEYWORD_CONTINUE, KEYWORD_DEFAULT,
    KEYWORD_DO, KEYWORD_DOUBLE, KEYWORD_ELSE, KEYWORD_ENUM,
    KEYWORD_EXTENDS, KEYWORD_FINAL, KEYWORD_FINALLY, KEYWORD_FLOAT,
    KEYWORD_FOR, KEYWORD_GOTO, KEYWORD_IF, KEYWORD_IMPLEMENTS,
    KEYWORD_IMPORT, KEYWORD_INSTANCEOF, KEYWORD_INT, KEYWORD_INTERFACE,
    KEYWORD_LONG, KEYWORD_NATIVE, KEYWORD_NEW, KEYWORD_PACKAGE,
    KEYWORD_PRIVATE, KEYWORD_PROTECTED, KEYWORD_PUBLIC, KEYWORD_RETURN,
    KEYWORD_SHORT, KEYWORD_STATIC, KEYWORD_STRICTFP, KEYWORD_SUPER,
    KEYWORD_SWITCH, KEYWORD_SYNCHRONIZED, KEYWORD_THIS, KEYWORD_THROW,
    KEYWORD_THROWS, KEYWORD_TRANSIENT, KEYWORD_TRY, KEYWORD_VOID,
    KEYWORD_VOLATILE, KEYWORD_WHILE, KEYWORD_OPEN,

    // Contextual Keywords
    CONTEXTUAL_EXPORTS, CONTEXTUAL_MODULE, CONTEXTUAL_NON_SEALED,
    CONTEXTUAL_OPENS, CONTEXTUAL_PERMITS, CONTEXTUAL_PROVIDES,
    CONTEXTUAL_RECORD, CONTEXTUAL_REQUIRES, CONTEXTUAL_SEALED,
    CONTEXTUAL_TO, CONTEXTUAL_TRANSITIVE, CONTEXTUAL_USES,
    CONTEXTUAL_VAR, CONTEXTUAL_WITH, CONTEXTUAL_YIELD,
    CONTEXTUAL_WHEN, // <-- ADDED CONTEXTUAL_WHEN

    // Literals
    IDENTIFIER, INTEGER_LITERAL, FLOAT_LITERAL, CHAR_LITERAL,
    STRING_LITERAL, TEXT_BLOCK_LITERAL, BOOLEAN_LITERAL,
    KEYWORD_NULL,

    // Punctuation
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET,
    SEMICOLON, COMMA, DOT, AT_SIGN, COLON, QUESTION_MARK,

    // Single operators (used as fallback)
    EQUALS, PLUS, MINUS, STAR, SLASH, PERCENT,
    LESS_THAN, GREATER_THAN, AMPERSAND, PIPE, CARET, TILDE, BANG,

    // Multi-character operators
    ARROW, COLON_COLON, DOT_DOT_DOT,
    EQUALS_EQUALS, BANG_EQUALS, LESS_EQUALS, GREATER_EQUALS,
    AMPERSAND_AMPERSAND, PIPE_PIPE,
    LEFT_SHIFT, RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT,
    PLUS_EQUALS, MINUS_EQUALS, STAR_EQUALS, SLASH_EQUALS, PERCENT_EQUALS,
    AMPERSAND_EQUALS, PIPE_EQUALS, CARET_EQUALS,
    LEFT_SHIFT_EQUALS, RIGHT_SHIFT_EQUALS, UNSIGNED_RIGHT_SHIFT_EQUALS,
    PLUS_PLUS, MINUS_MINUS,

    // Special (Non-Semantic)
    WHITESPACE, SINGLE_LINE_COMMENT, BLOCK_COMMENT_CONTENT,
    RESERVED_UNDERSCORE,
    UNRECOGNIZED, EOF;

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public TokenCategory getCategory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCategory'");
    }
}