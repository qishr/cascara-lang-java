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

public class FileLink extends Link {
    String label = "";
    String fileName = "";

    private FileLink() {
        // Nothing to see here
    }

    /// Sets the target of the link to the provided filename.
    /// @param file The filename to use
    /// @return the link with its target filename set
    public static FileLink to(String file) {
        FileLink link = new FileLink();
        link.fileName = file;
        link.scope = Scope.UNKNOWN;
        link.kind = Kind.FILE;
        return link;
    }

    public FileLink from(Reference ref) {
        this.origin = ref;
        return this;
    }

    public FileLink withLabel(String s) {
        this.label = s;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public String getFileName() {
        return fileName;
    }
}
