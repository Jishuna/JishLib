package me.jishuna.jishlib;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ClassScanner<T, A extends Annotation> {
    private final ClassLoader classloader;
    private final Class<T> typeClass;
    private final Class<A> annotationClass;
    private final String packageName;

    public ClassScanner(ClassLoader classloader, Class<T> typeClass, Class<A> annotationClass) {
        this(classloader, typeClass, annotationClass, typeClass != null ? typeClass.getPackageName() : annotationClass.getPackageName());
    }

    public ClassScanner(ClassLoader classloader, Class<T> typeClass, Class<A> annotationClass, String packageName) {
        this.classloader = classloader;
        this.typeClass = typeClass;
        this.annotationClass = annotationClass;
        this.packageName = packageName;
    }

    public Set<Class<?>> scan() {
        try {
            return ClassPath
                    .from(this.classloader)
                    .getAllClasses()
                    .stream()
                    .filter(this::isClassValid)
                    .map(ClassInfo::load)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptySet();
    }

    public void forEach(Consumer<T> consumer) {
        if (this.typeClass == null) {
            return;
        }

        for (Class<?> clazz : scan()) {
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                if (!this.typeClass.isInstance(instance)) {
                    continue;
                }

                consumer.accept(this.typeClass.cast(instance));
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }

    public void forEach(BiConsumer<T, A> consumer) {
        if (this.typeClass == null || this.annotationClass == null) {
            return;
        }

        for (Class<?> clazz : scan()) {
            try {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                if (!this.typeClass.isInstance(instance)) {
                    continue;
                }

                consumer.accept(this.typeClass.cast(instance), this.typeClass.getAnnotation(this.annotationClass));
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isClassValid(ClassInfo classInfo) {
        if (!classInfo.getPackageName().startsWith(this.packageName)) {
            return false;
        }

        Class<?> clazz = classInfo.load();
        if ((this.typeClass != null && !this.typeClass.isAssignableFrom(clazz)) ||
                (this.annotationClass != null && !clazz.isAnnotationPresent(this.annotationClass))) {
            return false;
        }

        return true;
    }
}
