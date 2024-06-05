package com.jmorla;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("com.jmorla.Generate")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class ReportProcessor extends AbstractProcessor {


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        var messager = processingEnv.getMessager();
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                switch (element.getKind()) {
                    case CLASS:
                        messager.printNote("%s is a class".formatted(element.getSimpleName()));
                        handleEnclosedElements(element, messager);
                        break;
                    case INTERFACE:
                        messager.printNote("%s is an interface".formatted(element.getSimpleName()));
                        handleEnclosedElements(element, messager);
                        break;
                    default:
                        messager.printNote("%s is an %s".formatted(element.getSimpleName(), element.getKind()));
                        handleEnclosedElements(element, messager);
                }
            }
            messager.printWarning("advertencia de prueba");
            messager.printError("Error de prueba");
        }
        return false;
    }
    
    private static void handleEnclosedElements(Element element, Messager messager) {
        List<? extends Element> members = element.getEnclosedElements();
        for (var member : members) {
            messager.printNote("kind: %s | name: %s".formatted(member.getKind(), member.getSimpleName()));
        }
    }
    
}
