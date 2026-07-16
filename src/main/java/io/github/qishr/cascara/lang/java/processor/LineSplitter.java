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


package io.github.qishr.cascara.lang.java.processor;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Utility class for splitting strings based on JLS 3.4 Line Terminators.
 * see https://docs.oracle.com/javase/specs/jls/se17/html/jls-3.html#jls-3.4
 */
public class LineSplitter {

    /**
     * Divides the input string into a list of strings, split by any JLS-compliant
     * Line Terminator (LF, CR, or CRLF). The resulting list elements do not
     * contain the line terminator characters.
     * * @param input The string to be split.
     * @return A List of strings, where each element represents a line.
     */
    public static List<String> splitByJlsLineTerminators(String input) {
        if (input == null) {
            return null;
        }

        // --- Option 1: Modern (Java 11+) and Recommended ---
        // The String.lines() method is specifically designed to handle all
        // JLS-compliant line terminators and is the cleanest approach.
        return input.lines().collect(Collectors.toList());

        /*
        // --- Option 2: Regex-based (For older Java versions or explicit regex requirement) ---
        // The regex "\\r\\n|\\r|\\n" correctly matches CRLF as a single delimiter,
        // or CR/LF individually. The String.split() method automatically handles empty lines
        // and returns lines without the delimiter.
        return Arrays.asList(input.split("\\r\\n|\\r|\\n"));
        */
    }

    // --- Example Usage ---
    public static void main(String[] args) {
        // Test string containing all three JLS line terminators
        String testString = "Line 1\nLine 2\r\nLine 3\rLine 4";

        System.out.println("Input String:\n" + testString.replace("\n", "\\n").replace("\r", "\\r"));

        List<String> lines = splitByJlsLineTerminators(testString);

        System.out.println("\nSplit Lines:");
        for (int i = 0; i < lines.size(); i++) {
            System.out.println("Line " + (i + 1) + ": \"" + lines.get(i) + "\"");
        }

        // Output should be:
        // Line 1: "Line 1"
        // Line 2: "Line 2"
        // Line 3: "Line 3"
        // Line 4: "Line 4"
    }
}