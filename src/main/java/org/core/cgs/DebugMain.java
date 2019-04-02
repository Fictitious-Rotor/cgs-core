package org.core.cgs;

import org.core.cgs.generic.classes.SubPluginCommandFileConfig;

import java.util.Map;

public class DebugMain {
    public static void main(String... args) {
//        mainReflection2();
        final SubPluginCommandFileConfig config = new SubPluginCommandFileConfig("org/core/cgs/subplugins/commands/infinitechests.yml");
        System.out.println(config);
    }

    private static void exporeMap(final Map<Object, Object> givenMap, final String offset) {
        for (Map.Entry<Object, Object> entry : givenMap.entrySet()) {
            if (entry.getValue() instanceof Map) {
                System.out.println(offset + entry.getKey().toString() + ":");
                exporeMap((Map<Object, Object>)entry.getValue(), offset + "    ");
            } else {
                System.out.println(offset + entry.getKey().toString() + ": " + entry.getValue().toString());
            }
        }
    }
}
