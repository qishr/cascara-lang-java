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


package io.github.qishr.cascara.lang.java.modeler;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

// import jdk.javadoc.doclet.Reporter;

import com.sun.source.util.DocTreePath;

import io.github.qishr.cascara.common.diagnostic.report.Reporter;
import io.github.qishr.cascara.common.diagnostic.report.LocalReporter;
import io.github.qishr.cascara.lang.java.modeler.MarkdownParser;

// import io.github.sandydunlop.cascara.core.Context;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarkdownParserTests {
    // private static Context ctx;
    @Mock static Reporter reporter = new LocalReporter();
    //  {
    //     @Override
    //     public void print(Kind kind, String message) {
    //         System.out.println(kind + ": " + message);
    //     }

    //     @Override
    //     public void print(Kind kind, DocTreePath path, String message) {
    //         // Do nothing
    //     }

    //     @Override
    //     public void print(Kind kind, Element element, String message) {
    //         // Do nothing
    //     }
    // };

	@BeforeAll
    static void initAll() {
		// ctx =  Context.getInstance();
		// ctx.setReporter(reporter);
    }

    @BeforeEach
    void init() {
        // Nothing to see here
    }

	@Test
	void resolveMarkdownLinks() {
        MarkdownParser parser = new MarkdownParser("one [link resolving][LinkResolver] two");
        MarkdownParser.MarkdownToken segment = parser.firstToken();

        assertEquals(MarkdownParser.TokenKind.TEXT, segment.getKind());
        assertEquals("one ", segment.getText());
        segment = segment.getNext();

        assertEquals(MarkdownParser.TokenKind.BRACKETS_TAG, segment.getKind());
        assertEquals("link resolving", segment.getText());
        segment = segment.getNext();

        assertEquals(MarkdownParser.TokenKind.BRACKETS_TAG, segment.getKind());
        assertEquals("LinkResolver", segment.getText());
        segment = segment.getNext();

        assertEquals(MarkdownParser.TokenKind.TEXT, segment.getKind());
        assertEquals(" two", segment.getText());
        segment = segment.getNext();

        assertEquals(MarkdownParser.TokenKind.END, segment.getKind());
    }

	@Test
	void resolveMarkdownLinks_inlineCode_withBracket() {
        MarkdownParser parser = new MarkdownParser("the position of `]` within the markdown text");
        MarkdownParser.MarkdownToken segment = parser.firstToken();
        assertEquals("the position of `]` within the markdown text", segment.getText());
        segment = segment.getNext();
        assertEquals(MarkdownParser.TokenKind.END, segment.getKind());
    }

    @Test
    void testEmptyString() {
        MarkdownParser parser = new MarkdownParser("");
        MarkdownParser.MarkdownToken seg = parser.firstToken();
        assertEquals(MarkdownParser.TokenKind.END, seg.getKind());
    }

    @Test
    void testPlainText() {
        String markdown = "Just some plain text.";
        MarkdownParser parser = new MarkdownParser(markdown);
        MarkdownParser.MarkdownToken seg = parser.firstToken();
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals(markdown, seg.getText());

        MarkdownParser.MarkdownToken next = seg.getNext();
        assertEquals(MarkdownParser.TokenKind.END, next.getKind());
    }

    @Test
    void testBracketsTag() {
        String markdown = "Here is a [link] in text.";
        MarkdownParser parser = new MarkdownParser(markdown);

        MarkdownParser.MarkdownToken seg = parser.firstToken();
        // First segment: TEXT before [
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals("Here is a ", seg.getText());

        seg = seg.getNext();
        // Second segment: BRACKETS_TAG
        assertEquals(MarkdownParser.TokenKind.BRACKETS_TAG, seg.getKind());
        assertEquals("link", seg.getText());

        seg = seg.getNext();
        // Third segment: TEXT after ]
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals(" in text.", seg.getText());

        assertEquals(MarkdownParser.TokenKind.END, seg.getNext().getKind());
    }

    @Test
    void testParensTagFollowingBrackets() {
        String markdown = "Here is [link](http://example.com)";
        MarkdownParser parser = new MarkdownParser(markdown);

        MarkdownParser.MarkdownToken seg = parser.firstToken();
        // TEXT before [
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals("Here is ", seg.getText());

        seg = seg.getNext();
        // BRACKETS_TAG: link
        assertEquals(MarkdownParser.TokenKind.BRACKETS_TAG, seg.getKind());
        assertEquals("link", seg.getText());

        seg = seg.getNext();
        // PARENS_TAG: http://example.com
        assertEquals(MarkdownParser.TokenKind.PARENS_TAG, seg.getKind());
        assertEquals("http://example.com", seg.getText());

        assertEquals(MarkdownParser.TokenKind.END, seg.getNext().getKind());
    }

    @Test
    void testParensNotFollowingBrackets() {
        String markdown = "This (text) is not a link.";
        MarkdownParser parser = new MarkdownParser(markdown);

        MarkdownParser.MarkdownToken seg = parser.firstToken();
        // The entire text including (text) should be one TEXT segment
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals(markdown, seg.getText());
        assertEquals(MarkdownParser.TokenKind.END, seg.getNext().getKind());
    }

    @Test
    void testCodeBlockToggle() {
        String markdown = "Text with `code [notalink]` outside.";
        MarkdownParser parser = new MarkdownParser(markdown);

        MarkdownParser.MarkdownToken seg = parser.firstToken();
        // TEXT segment: "Text with `code [notalink]` outside."
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals("Text with `code [notalink]` outside.", seg.getText());

        assertEquals(MarkdownParser.TokenKind.END, seg.getNext().getKind());
    }

    @Test
    void testMultipleLinksAndText() {
        String markdown = "Hello [one](url1) and [two](url2)!";
        MarkdownParser parser = new MarkdownParser(markdown);

        MarkdownParser.MarkdownToken seg = parser.firstToken();
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals("Hello ", seg.getText());

        seg = seg.getNext();
        assertEquals(MarkdownParser.TokenKind.BRACKETS_TAG, seg.getKind());
        assertEquals("one", seg.getText());

        seg = seg.getNext();
        assertEquals(MarkdownParser.TokenKind.PARENS_TAG, seg.getKind());
        assertEquals("url1", seg.getText());

        seg = seg.getNext();
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals(" and ", seg.getText());

        seg = seg.getNext();
        assertEquals(MarkdownParser.TokenKind.BRACKETS_TAG, seg.getKind());
        assertEquals("two", seg.getText());

        seg = seg.getNext();
        assertEquals(MarkdownParser.TokenKind.PARENS_TAG, seg.getKind());
        assertEquals("url2", seg.getText());

        seg = seg.getNext();
        assertEquals(MarkdownParser.TokenKind.TEXT, seg.getKind());
        assertEquals("!", seg.getText());

        assertEquals(MarkdownParser.TokenKind.END, seg.getNext().getKind());
    }
}

