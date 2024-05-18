package com.jmorla.getterx;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

public class InmutableGetGenerator {

    private final StringBuilder sb = new StringBuilder();
    private final Filer filer;
    private final String generateClassName;
    private final String packageName;
    private final String className;

    public InmutableGetGenerator(Filer filer, String qualifiedName) {
        this.filer = filer;
        this.className = getSimpleName(qualifiedName);
        this.generateClassName = "Inmutable" + className;
        this.packageName = getPackageName(qualifiedName);
    }

    public void generate(Set<ExecutableElement> getters) throws IOException {

        sb.append("package ").append(packageName).append(";\n\n");
        sb.append("public class ").append("Inmutable").append(className)
                .append(" implements ").append(className).append(" {\n\n");

        for (var getter : getters) {
            generateField(getter);
        }

        generateConstructor(getters);

        for (ExecutableElement getter : getters) {
            generateImplementation(getter);
        }

        sb.append("}\n");

        write();

    }

    private void write() throws IOException {
        JavaFileObject fileObject = filer.createSourceFile(packageName + '.' + generateClassName);
        try (Writer writer = fileObject.openWriter()) {
            writer.write(sb.toString());
        }
    }

    private void generateField(ExecutableElement getter) {
        String fieldName = getFieldName(getter);
        TypeMirror returnType = getter.getReturnType();

        sb.append("    private final ").append(returnType).append(" ").append(fieldName).append(";\n");
    }

    public void generateConstructor(Set<ExecutableElement> getters) {
        sb.append("\n    public ").append(generateClassName).append("(");

        int count = 0;
        for (ExecutableElement getter : getters) {
            if (count > 0) {
                sb.append(", ");
            }
            TypeMirror returnType = getter.getReturnType();
            String fieldName = getFieldName(getter);
            sb.append(returnType).append(" ").append(fieldName);
            count++;
        }
        sb.append(") {\n");

        for (ExecutableElement getter : getters) {
            String fieldName = getFieldName(getter);
            sb.append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
        }

        sb.append("    }\n\n");
    }

    public void generateImplementation(ExecutableElement getter) {
        String methodName = getter.getSimpleName().toString();
        String fieldName = getFieldName(getter);
        TypeMirror returnType = getter.getReturnType();

        sb.append("    @Override\n");
        sb.append("    public ").append(returnType).append(" ").append(methodName).append("() {\n");
        sb.append("        return this.").append(fieldName).append(";\n");
        sb.append("    }\n\n");
    }

    private String getFieldName(ExecutableElement getter) {
        String methodName = getter.getSimpleName().toString();
        String fieldName = methodName.startsWith("get")
                ? Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4)
                : methodName;
        return fieldName;
    }

    private String getSimpleName(String fullyQualifiedName) {
        int lastDot = fullyQualifiedName.lastIndexOf('.');
        return lastDot == -1 ? fullyQualifiedName : fullyQualifiedName.substring(lastDot + 1);
    }

    private String getPackageName(String fullyQualifiedName) {
        int lastDot = fullyQualifiedName.lastIndexOf('.');
        return lastDot == -1 ? "" : fullyQualifiedName.substring(0, lastDot);
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}
