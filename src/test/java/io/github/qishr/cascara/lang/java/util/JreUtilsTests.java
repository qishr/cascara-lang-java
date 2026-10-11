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

import io.github.qishr.cascara.lang.java.exception.ClassLoadException;
import io.github.qishr.cascara.lang.java.model.ClassNode;
import io.github.qishr.cascara.lang.java.model.JlsName;
import io.github.qishr.cascara.lang.java.model.MethodNode;
import io.github.qishr.cascara.lang.java.model.VariableTypeParser;
import io.github.qishr.cascara.lang.java.model.NameUtil;
import io.github.qishr.cascara.lang.java.model.ParamNode;
import io.github.qishr.cascara.lang.java.model.Reference;
import io.github.qishr.cascara.lang.java.model.TypeNode;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode;
import io.github.qishr.cascara.lang.java.ModelTestEnvironment;
import io.github.qishr.cascara.lang.java.modeler.StandardModeler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

class JreUtilsTests extends ModelTestEnvironment {

    @BeforeEach
    void init() {
        setupModel();
        // Relativizer.setFlattenedDirectories(null);
		// ctx.setModuleName("markista");
        // ctx.setPackageName("io.github.sandydunlop.markista.doclet");
    }

    @Test
    void getMethod_compatibleParams1() {
        ClassNode subClass = newClass("SubClass", model);
        subClass.getSupertypes().add(VariableTypeParser.parseVariableType("java.lang.Object"));
        subClass.getSupertypes().add(VariableTypeParser.parseVariableType("javax.lang.model.util.ElementScanner9"));

        JlsName methodName = NameUtil.createMemberName("scan");
        MethodNode scanMethod = new MethodNode("void", methodName);
        scanMethod.setOwnerName(node.getName());
        subClass.addMethod(scanMethod);

        VariableTypeNode vt1 = VariableTypeParser.parseVariableType("javax.lang.model.element.Element");
        ParamNode param1 = new ParamNode(vt1, NameUtil.createMemberName("param1"));
        VariableTypeNode vt2 = VariableTypeParser.parseVariableType("java.lang.Integer");
        ParamNode param2 = new ParamNode(vt2, NameUtil.createMemberName("param2"));
        scanMethod.addParam(param1);
        scanMethod.addParam(param2);

        // TextAssembler.assembleTextAndLinks(api, ctx);

        String typeName = "javax.lang.model.util.ElementScanner9";
        Class<?> standardClass = JreUtil.loadClass(typeName);

        assertNotNull(standardClass);
    }

    @Test
    void getMethod_compatibleParams2() {
        ClassNode subClass = newClass("SubClass", model);
        subClass.getSupertypes().add(VariableTypeParser.parseVariableType("java.lang.Object"));
        subClass.getSupertypes().add(VariableTypeParser.parseVariableType("java.util.ArrayList"));

        JlsName methodName = NameUtil.createMemberName("addAll");
        MethodNode addAllMethod = new MethodNode("void", methodName);
        addAllMethod.setOwnerName(node.getName());
        subClass.addMethod(addAllMethod);

        VariableTypeNode vt1 = VariableTypeParser.parseVariableType("java.util.Collection<? extends String>");
        JlsName paramName = NameUtil.createMemberName("c");
        ParamNode param1 = new ParamNode(vt1, paramName);
        addAllMethod.addParam(param1);

        // TextAssembler.assembleTextAndLinks(api, ctx);

        String typeName = "java.util.ArrayList";
        Class<?> standardClass = JreUtil.loadClass(typeName);

        assertNotNull(standardClass);
    }

    @Test
    void methods1() {
        String typeName = "javax.lang.model.util.ElementScanner9";

        Class<?> jreClass = JreUtil.loadClass(typeName);
        StandardModeler modeller = new StandardModeler();
        TypeNode typeNode = modeller.modelClass(jreClass);
        assertNotNull(typeNode);
    }

    @Test
    void t1() {
        Reference ref = NameUtil.createReference("java.util.List");
        JlsName name = ref.getName();
        Class<?> jreType = JreUtil.loadClass(name.fullyQualifiedName());
        assertNotNull(jreType);
    }

    @Test
    void test_methods() {
        Class<?> jreType = JreUtil.loadClass("java.lang.Integer");
        MethodNode[] methods = JreUtil.getMethods(jreType);
        assertNotNull(methods);
    }

    @Test
    void test_asas() throws ClassLoadException {
        String packageName = "javax.xml.stream";
        List<Class<?>> jreClasses = JreUtil.loadClassesFromPackage(packageName);
        assertFalse(jreClasses.isEmpty());
    }

    @Test
    void test_modules() {
        try {
            List<String> modules = JreUtil.modules();
            assertFalse(modules.isEmpty());
        } catch (ClassLoadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
