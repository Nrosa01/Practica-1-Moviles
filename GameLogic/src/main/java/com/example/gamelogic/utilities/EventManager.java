package com.example.gamelogic.utilities;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static List<Listener> registered = new ArrayList<Listener>();

    public static void register(Listener listener) {
        if (!registered.contains(listener)) {
            registered.add(listener);
        }
    }

    public static void unregister(Listener listener) {
        if (registered.contains(listener)) {
            registered.remove(listener);
        }
    }

    public static List<Listener> getRegistered() {
        return registered;
    }

    public static void callEvent(final Event event) {
        new Thread() {
            @Override
            public void run() {
                call(event);
            }
        }.start();
    }

    private static void call(final Event event) {
        for (Listener registered : getRegistered()) {
            Method[] methods = registered.getClass().getMethods();

            for (int i = 0; i < methods.length; i++) {
                EventHandler eventHandler = methods[i].getAnnotation(EventHandler.class);
                if (eventHandler != null) {
                    Class<?>[] methodParams = methods[i].getParameterTypes();

                    if (methodParams.length < 1) {
                        continue;
                    }

                    if (!event.getClass().getSimpleName().equals(methodParams[0].getSimpleName())) {
                        continue;
                    }

                    try {
                        methods[i].invoke(registered, event);
                    } catch (Exception exception) {
                        System.err.println(exception);
                    }
                }
            }

        }
    }
}
