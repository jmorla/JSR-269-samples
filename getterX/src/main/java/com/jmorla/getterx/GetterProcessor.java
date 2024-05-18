package com.jmorla.getterx;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.google.auto.service.AutoService;

@AutoService(Processor.class)
@SupportedAnnotationTypes("com.jmorla.getterx.Getter")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class GetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            for (TypeElement annotation : annotations) {
                Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
                for (Element element : annotatedElements) {
                    if (element.getKind() == ElementKind.INTERFACE) {
                        TypeElement typeElement = (TypeElement) element;
                        String className = typeElement.getQualifiedName().toString();

                        var getters = ElementFilter.methodsIn(typeElement.getEnclosedElements())
                                .stream().filter(e -> e.getSimpleName().toString().startsWith("get"))
                                .collect(Collectors.toSet());

                        writeImplementation(className, getters);

                    }
                }

            }
        }
        return false;
    }

    private void writeImplementation(String fullyQualifierName,
        Set<ExecutableElement> getters) {
        try {
            var generator = new InmutableGetGenerator(
                    processingEnv.getFiler(),
                    fullyQualifierName);
            generator.generate(getters);
        } catch (IOException ex) {
            System.out.println(ex);
            processingEnv.getMessager()
                    .printError("Error ocurred while writing implementation of %s interface".formatted(fullyQualifierName));
        }
    }

}
