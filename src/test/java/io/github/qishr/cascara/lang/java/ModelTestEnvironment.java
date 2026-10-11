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


package io.github.qishr.cascara.lang.java;

import java.io.StringWriter;
import java.net.URI;

import jdk.javadoc.doclet.Reporter;

import com.sun.source.util.DocTreePath;

import io.github.qishr.cascara.lang.java.model.AnnotationNode;
import io.github.qishr.cascara.lang.java.model.ClassNode;
import io.github.qishr.cascara.lang.java.model.EnumNode;
import io.github.qishr.cascara.lang.java.model.FieldNode;
import io.github.qishr.cascara.lang.java.model.InterfaceNode;
import io.github.qishr.cascara.lang.java.model.JlsName;
import io.github.qishr.cascara.lang.java.model.Link;
import io.github.qishr.cascara.lang.java.model.VariableTypeParser;
import io.github.qishr.cascara.lang.java.model.ModuleNode;
import io.github.qishr.cascara.lang.java.model.NameUtil;
import io.github.qishr.cascara.lang.java.model.PackageNode;
import io.github.qishr.cascara.lang.java.model.PackageReference;
import io.github.qishr.cascara.lang.java.model.RecordNode;
import io.github.qishr.cascara.lang.java.model.Reference;
import io.github.qishr.cascara.lang.java.model.SemanticModel;
import io.github.qishr.cascara.lang.java.model.TypeNode;
import io.github.qishr.cascara.lang.java.model.VariableTypeNode;

import javax.lang.model.element.Element;

public class ModelTestEnvironment {
    // protected Context ctx;
    public TestReporter reporter;
	protected SemanticModel api;
    // protected LinkResolver resolver;

    protected ModuleNode module;
    protected PackageNode model;
    protected PackageNode doclet;
	protected PackageNode markista;
    protected ClassNode node;

    protected class TestReporter implements Reporter {
        public StringWriter output = new StringWriter();

        @Override
        public void print(javax.tools.Diagnostic.Kind kind, String message) {
            output.write(message);
        }

        @Override
        public void print(javax.tools.Diagnostic.Kind kind, DocTreePath path, String message) {
            output.write(message);
        }

        @Override
        public void print(javax.tools.Diagnostic.Kind kind, Element element, String message) {
            output.write(message);
        }
    }

    protected void configureType(TypeNode typeNode, PackageNode pkg) {
        typeNode.setOwnerName(pkg.getName().fullyQualifiedName());
        pkg.addType(typeNode);
        api.addType(typeNode);
    }

    protected ClassNode newClass(String typeName, PackageNode pkg) {
        ClassNode classNode = new ClassNode(NameUtil.createTypeName(pkg.getName(), typeName));
        configureType(classNode, pkg);
        return classNode;
    }

    protected EnumNode newEnum(String typeName, PackageNode pkg) {
        EnumNode classNode = new EnumNode(NameUtil.createTypeName(pkg.getName(), typeName));
        configureType(classNode, pkg);
        return classNode;
    }

    protected InterfaceNode newInterface(String typeName, PackageNode pkg) {
        InterfaceNode classNode = new InterfaceNode(NameUtil.createTypeName(pkg.getName(), typeName));
        configureType(classNode, pkg);
        return classNode;
    }

    protected AnnotationNode newAnnotation(String typeName, PackageNode pkg) {
        AnnotationNode classNode = new AnnotationNode(NameUtil.createTypeName(pkg.getName(), typeName));
        configureType(classNode, pkg);
        return classNode;
    }

    protected RecordNode newRecord(String typeName, PackageNode pkg) {
        RecordNode classNode = new RecordNode(NameUtil.createTypeName(pkg.getName(), typeName));
        configureType(classNode, pkg);
        return classNode;
    }

    protected FieldNode newField(String typeName, String name, TypeNode type) {
        VariableTypeNode vt = VariableTypeParser.parseVariableType(typeName);
        JlsName fieldName = NameUtil.createMemberName(name);
        FieldNode fieldNode = new FieldNode(vt, fieldName);
        type.addField(fieldNode);
        return fieldNode;
    }

    protected VariableTypeNode newVariableType(String typeName) {
        return VariableTypeParser.parseVariableType(typeName);
    }

    protected Link newMethodReference(String typeName, String methodName) {
        Link methodLink = Link.to(NameUtil.createReference(typeName + "#" + methodName))
                .withKind(Link.Kind.METHOD)
                .withMethodName(methodName);
        methodLink.setAnchor(methodName.toLowerCase());
        methodLink.setUri(URI.create(typeName));
        return methodLink;
    }

    protected void setupModel() {
        // Context.reset();
		// ctx = Context.getInstance();
        reporter = new TestReporter();
		// ctx.setReporter(reporter);

		api = new SemanticModel("Test API");
        api.addPackage(new PackageNode("io.github.sandydunlop"));
        markista = new PackageNode("io.github.sandydunlop.markista");
		doclet = new PackageNode("io.github.sandydunlop.markista.doclet");
		model = new PackageNode("io.github.sandydunlop.markista.model");
		api.addPackage(markista);
		api.addPackage(doclet);
		api.addPackage(model);

        module = new ModuleNode("markista");
		api.addModule(module);
        module.addPackage(markista);
        module.addPackage(doclet);
        module.addPackage(model);
		// module.addPackage(new PackageReference(markista.getName().fullyQualifiedName()));
		// module.addPackage(new PackageReference(doclet.getName().fullyQualifiedName()));
		// module.addPackage(new PackageReference(model.getName().fullyQualifiedName()));
		markista.setModuleName(module.getName().fullyQualifiedName());
		doclet.setModuleName(module.getName().fullyQualifiedName());
		model.setModuleName(module.getName().fullyQualifiedName());

        node = newClass("Node", model);
        node.setOwnerName(model.getName().fullyQualifiedName());
        model.addType(node);
        api.addType(node);

        // ctx.setApi(api);
    }
}
