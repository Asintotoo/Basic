package com.asintoto.basic.commands;

import com.asintoto.basic.enums.BasicChar;
import com.asintoto.basic.interfaces.Parameter;
import com.asintoto.basic.interfaces.RequiredPermission;
import com.asintoto.basic.structures.Tuple;
import com.asintoto.colorlib.ColorLib;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnotatedCommand extends BasicCommand{

    private Map<String, Tuple<Method, Parameter>> methods = new HashMap<>();
    private String notAPlayer;
    private String executedArg = "";

    public AnnotatedCommand(String label) {
        super(label);
        this.notAPlayer = ColorLib.setColors("&cYou are not a player");

        findMethods();

        this.setUsage("&cUsage: /" + getLabel() + " <" + String.join("|", methods.keySet()
                .stream().filter(methodName -> !methodName.isEmpty()).collect(Collectors.toList())) + ">");
    }

    private void findMethods() {
        for(Method method : this.getClass().getDeclaredMethods()) {
            if(method.isAnnotationPresent(Parameter.class)) {
                Parameter parameter = method.getDeclaredAnnotation(Parameter.class);

                methods.put(parameter.value(), new Tuple<>(method, parameter));
            }
        }
    }

    public String getNotAPlayerMessage() {
        return notAPlayer;
    }

    public void setNotAPlayerMessage(String notAPlayer) {
        this.notAPlayer = notAPlayer;
    }

    @Override
    public final void onCommand() {
        String param = args.length > 0 ? args[0] : "";

        Tuple<Method, Parameter> methodTuple = methods.get(param.toLowerCase());

        if(methodTuple != null) {
            Method method = methodTuple.getKey();
            Parameter parameter = methodTuple.getValue();

            if(parameter.requiresPlayer() && !isPlayer()) {
                sendMessage(notAPlayer);
                return;
            }

            if(method.isAnnotationPresent(RequiredPermission.class)) {
                RequiredPermission requiredPermission = method.getDeclaredAnnotation(RequiredPermission.class);
                String permission = requiredPermission.permission();

                if(!sender.hasPermission(permission)) {
                    printNoPermission(permission);
                    return;
                }
            }

            try {
                this.executedArg = param;
                method.invoke(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            printUsage();
        }
    }

    @Override
    public final List<String> onTabComplete() {
        if(args.length == 1) {
            return methods.keySet().stream().filter(p -> !p.isEmpty()).toList();
        }

        return NO_COMPLETE;
    }


    public void printNoPermission(String permission) {
        String msg = "&7[&4" + BasicChar.CROSS + "&7] &cYou do not have permission &7({permission})".replace("{permission}", permission);
        sendMessage(ColorLib.setColors(msg));
    }

    public void sendSuccessMessage() {
        String msg = ColorLib.setColors("&7[&a&l" + BasicChar.CHECK + "&r&7] <SOLID:42f554>Command &r&7/" + label + " " + executedArg + "&r<SOLID:42f554> executed successfully!");
        sendMessage(msg);
    }
}
