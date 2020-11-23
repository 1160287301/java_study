//: annotations/InterfaceExtractorProcessorFactory.java
// APT-based annotation processing.
package java编程思想.注解.使用apt处理注解;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * apt工具需要一个工厂类来为其指明正确的处理器，然后它才能调用处理器上的process()方法：
 */
public class InterfaceExtractorProcessorFactory
        implements AnnotationProcessorFactory {
    public AnnotationProcessor getProcessorFor(
            Set<AnnotationTypeDeclaration> atds,
            AnnotationProcessorEnvironment env) {
        return new InterfaceExtractorProcessor(env);
    }

    public Collection<String> supportedAnnotationTypes() {
        return
                Collections.singleton("annotations.ExtractInterface");
    }

    public Collection<String> supportedOptions() {
        return Collections.emptySet();
    }
} ///:~
