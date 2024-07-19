package com.asintoto.basic.reflection;

import com.asintoto.basic.Basic;
import com.asintoto.basic.BasicPlugin;
import com.asintoto.basic.commands.BasicCommand;
import com.asintoto.basic.interfaces.AutoRegister;
import com.asintoto.basic.utils.Common;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

public class ReflectionUtils {
    public static String getPackageName(Class<?> clazz) {
        return clazz.getPackage().getName();
    }

    public static Set<Class<?>> getValidPluginClasses() {

        String packageName = getPackageName(Basic.getPlugin().getClass());

        final Set<Class<?>> classes = new HashSet<>();

        final Pattern anonymousClassPattern = Pattern.compile("\\w+\\$[0-9]$");

        Method getFile = null;
        try {

            if (Basic.getPlugin() instanceof BasicPlugin) {
                getFile = Basic.getPlugin().getClass().getSuperclass().getSuperclass().getDeclaredMethod("getFile");
            } else {
                getFile = Basic.getPlugin().getClass().getSuperclass().getDeclaredMethod("getFile");
            }

        } catch (NoSuchMethodException e) {
            Common.error("Method not found: Make sure that you main plugin class extends either BasicPlugin or JavaPlugin");
        }
        getFile.setAccessible(true);
        File filepl = null;
        try {
            filepl = (File) getFile.invoke(Basic.getPlugin());
        } catch (IllegalAccessException e) {
            Common.error("Can't access to the given method");
        } catch (InvocationTargetException e) {
            Common.error("Invocation Target Exception");
        } finally {
            getFile.setAccessible(false);
        }

        try (final JarFile file = new JarFile(filepl)) {
            for (final Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements(); ) {
                final JarEntry jar = entry.nextElement();
                final String name = jar.getName().replace("/", ".");

                if (!name.endsWith(".class"))
                    continue;

                final String className = name.substring(0, name.length() - 6);
                Class<?> clazz = null;

                try {
                    clazz = Basic.class.getClassLoader().loadClass(className);

                } catch (final ClassFormatError | VerifyError | NoClassDefFoundError | ClassNotFoundException |
                               IncompatibleClassChangeError error) {
                    continue;
                }

                if (!Modifier.isAbstract(clazz.getModifiers()) && !anonymousClassPattern.matcher(className).find())
                    if (clazz.getPackage().getName().startsWith(packageName)) {
                        classes.add(clazz);
                    }
            }

        } catch (final Throwable t) {
            Common.warning(t.getMessage());
        }

        return classes;
    }

    public static void registerAll() {
        for (Class<?> clazz : getValidPluginClasses()) {

            if(clazz.isAnnotationPresent(AutoRegister.class)) {

                // Register Listeners
                if (Listener.class.isAssignableFrom(clazz)) {
                    try {
                        Constructor constructor = clazz.getConstructor();
                        Object object = constructor.newInstance();
                        Basic.registerListener((Listener) object);
                    } catch (NoSuchMethodException e) {
                        Common.error("Method not found");
                    } catch (InvocationTargetException e) {
                        Common.error("Invocation target Exception");
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        Common.error("Can't access this method");
                    }

                }

                //Register Basic Commands
                if (clazz.getSuperclass() == BasicCommand.class) {
                    try {
                        Constructor constructor = clazz.getConstructor();
                        Object object = constructor.newInstance();
                        Basic.registerCommand((BasicCommand) object);
                    } catch (NoSuchMethodException e) {
                        Common.error("Method not found");
                    } catch (InvocationTargetException e) {
                        Common.error("Invocation target Exception");
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        Common.error("Can't access this method");
                    }
                }

            }
        }
    }
}
