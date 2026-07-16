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


package io.github.qishr.cascara.lang.java.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;

import com.sun.source.util.DocTreePath;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.StandardReporter;
import io.github.qishr.cascara.common.util.ModulePath;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
//import jdk.javadoc.doclet.Reporter;

class ModulePathTests {
    @Mock static Reporter reporter = new StandardReporter();
//    {
//        @Override
//        public void print(Kind kind, String message) {
//            System.out.println(kind + ": " + message);
//        }
//
//        @Override
//        public void print(Kind kind, DocTreePath path, String message) {
//            // Do nothing
//        }
//
//        @Override
//        public void print(Kind kind, Element element, String message) {
//            // Do nothing
//        }
//    };

    @Test
    void processDirectoryUrl_callsProcessClassFileForClassFiles() throws Exception {
        Path tempDir = Files.createTempDirectory("lr-processdir");
        File dir = tempDir.toFile();
        try {
            // create a dummy class file in the directory
            File classFile = new File(dir, "MyClass.class");
            assertTrue(classFile.createNewFile(), "create dummy class file");

            URL[] urls = new URL[] { dir.toURI().toURL() };

            ModulePath modulePath = spy(ModulePath.class);
            // modulePath.ctx = Context.getInstance();
            // modulePath.ctx.setReporter(reporter);

            when(modulePath.processClassFile(any(File.class), any(File.class), any(URLClassLoader.class), any())).thenAnswer(invocation -> {
                File directoryArg = invocation.getArgument(0);
                File fileArg = invocation.getArgument(1);
                // assert the arguments are as expected inside the stub
                assertEquals(dir.getAbsoluteFile(), directoryArg.getAbsoluteFile());
                assertEquals(classFile.getName(), fileArg.getName());
                return null;
            });

            modulePath.processDirectoryUrl(dir, urls, null);
            verify(modulePath).processClassFile(any(), any(), any(), any());
        } finally {
            // cleanup
            Files.deleteIfExists(tempDir.resolve("MyClass.class"));
            Files.deleteIfExists(tempDir);
        }
    }

    @Test
    void processClassFile_readsModuleInfoAndSetsSiblingModuleName() throws Exception {
        Path tempDir = Files.createTempDirectory("lr-moduleinfo");
        File dir = tempDir.toFile();
        File moduleInfo = new File(dir, "module-info.class");
        ModulePath modulePath = new ModulePath("");
        try {
            assertTrue(moduleInfo.createNewFile(), "create module-info.class placeholder");

            // Mock ModuleDescriptor.read to avoid parsing actual class bytes
            ModuleDescriptor fakeDescriptor = mock(ModuleDescriptor.class);
            when(fakeDescriptor.name()).thenReturn("fake.module");

            try (MockedStatic<ModuleDescriptor> mdStatic = Mockito.mockStatic(ModuleDescriptor.class)) {
                mdStatic.when(() -> ModuleDescriptor.read(any(InputStream.class))).thenReturn(fakeDescriptor);

                // classLoader not needed for module-info branch; pass null
                String r = modulePath.processClassFile(dir, moduleInfo, null, null);

                assertEquals("fake.module", r);
            }
        } finally {
            Files.deleteIfExists(moduleInfo.toPath());
            Files.deleteIfExists(tempDir);
        }
    }

	// Helper method to create a mock enumeration of JarEntries
    private Enumeration<JarEntry> createMockEnumeration(JarEntry... entries) {
        return Collections.enumeration(java.util.Arrays.asList(entries));
    }

    // Helper method to create a mock JarEntry
    private JarEntry createMockJarEntry(String name) {
        JarEntry entry = Mockito.mock(JarEntry.class);
        Mockito.when(entry.getName()).thenReturn(name);
        return entry;
    }

    // @Test
    // void testProcessJarFileWithModuleInfoAndClasses() throws IOException {
    //     JarFile mockJarFile = Mockito.mock(JarFile.class);

    //     // Arrange
    //     String moduleName = "com.example.module";

    //     // Create a mock ModuleDescriptor object
    //     ModuleDescriptor mockDescriptor = Mockito.mock(ModuleDescriptor.class);
    //     Mockito.when(mockDescriptor.name()).thenReturn(moduleName);

    //     // Mock the JarFile entries
    //     JarEntry moduleInfoEntry = createMockJarEntry("module-info.class");
    //     JarEntry class1Entry = createMockJarEntry("com/example/module/MyClass.class");
    //     JarEntry class2Entry = createMockJarEntry("com/example/module/AnotherClass.class");
    //     Mockito.when(mockJarFile.entries()).thenReturn(createMockEnumeration(moduleInfoEntry, class1Entry, class2Entry));

    //     // Use a dummy InputStream for the module-info entry
    //     Mockito.when(mockJarFile.getInputStream(moduleInfoEntry)).thenReturn(new ByteArrayInputStream(new byte[0]));

    //     // Make the module "external"
    //     // Configuration.setAddModules(moduleName);

    //     // Act & Assert
    //     // Use Mockito.mockStatic to mock the static ModuleDescriptor.read() method
    //     try (MockedStatic<ModuleDescriptor> mockedStatic = Mockito.mockStatic(ModuleDescriptor.class)) {
    //         // Corrected line: specify the InputStream.class to resolve ambiguity
    //         mockedStatic.when(() -> ModuleDescriptor.read(Mockito.any(InputStream.class))).thenReturn(mockDescriptor);

    //         // Call the method under test
    //         ModulePath mp = new ModulePath("");

    //         String modName = mp.processJarFile(mockJarFile, null);
    //         assertEquals("com.example.module", modName);
    //     }
    // }
}
