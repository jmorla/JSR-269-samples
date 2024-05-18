package com.jmorla;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
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
        if (!roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                switch (element.getKind()) {
                    case CLASS:
                        System.out.println("[%s] is a class".formatted(element.getSimpleName()));
                        handleClass(element);
                        break;
                    case INTERFACE:
                        System.out.println("[%s] is an interface".formatted(element.getSimpleName()));
                        break;
                    default:
                        System.out.println("[%s] is an [%s]".formatted(element.getSimpleName(), element.getKind()));
                }
            }
        }
        return false;
    }
    
    private static void handleClass(Element element) {
        List<? extends Element> members = element.getEnclosedElements();
        for (var member : members) {
            System.out.println("\nkind: [%s] name: [%s]".formatted(member.getKind(), member.getSimpleName()));
        }
    }
    
}
