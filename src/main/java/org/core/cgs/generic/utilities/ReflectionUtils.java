package org.core.cgs.generic.utilities;

import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionUtils {
    public static <T> Set<Class<? extends T>> retrieveClasses(final String subPluginRootPackage,
                                                              final String classPackageName,
                                                              final Class<T> desiredClass) {
        final String classPackage = String.format("%s.%s", subPluginRootPackage, classPackageName);

        return new Reflections(classPackage).getSubTypesOf(desiredClass);
    }

    public static <T> Set<? extends T> instantiateClasses(final String subPluginRootPackage,
                                                          final String classPackageName,
                                                          final Class<T> desiredClass) {
        return retrieveClasses(subPluginRootPackage, classPackageName, desiredClass).stream().map(cClass -> {
            try {
                return cClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());
    }

    public static <T> Set<? extends T> instantiateClasses(final String subPluginRootPackage,
                                                          final String classPackageName,
                                                          final Class<T> desiredClass,
                                                          final Class<?>[] paramClasses,
                                                          final Object[] paramObjects) {
        return retrieveClasses(subPluginRootPackage, classPackageName, desiredClass).stream().map(cClass -> {
            try {
                return cClass.getDeclaredConstructor(paramClasses).newInstance(paramObjects);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toSet());
    }
//
//    public static Set<? extends CGSListener> instantiateListeners(final String subPluginRootPackage,
//                                                                  final MetadataBundle metadataBundle) {
//        return retrieveClasses(subPluginRootPackage, "listeners", CGSListener.class).stream().map(cClass -> {
//            try {
//                return cClass.getDeclaredConstructor(MetadataBundle.class).newInstance(metadataBundle);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).collect(Collectors.toSet());
//    }
}
