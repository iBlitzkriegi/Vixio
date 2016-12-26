package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import me.iblitzkriegi.vixio.Vixio;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Blitz on 10/30/2016.
 */
public class VixioAnnotationParser {
    public static int classes = 0;
    public static ArrayList<String> vExpressions = new ArrayList<>();
    public static ArrayList<String> vConditions = new ArrayList<>();
    public static ArrayList<String> vEffects = new ArrayList<>();
    public static ArrayList<String> vEvents = new ArrayList<>();
    public static void parse() throws Exception {
        File file = null;
        try {
            file = new File(URLDecoder.decode(Vixio.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Could not find main jar file!");
            e.printStackTrace();
        }
        for(Class clazz : getClasses(file, "me.iblitzkriegi.vixio")) {
            if (clazz.isAnnotationPresent(ExprAnnotation.Expression.class)) {
                ExprAnnotation.Expression ExprAnon = (ExprAnnotation.Expression) clazz.getAnnotation(ExprAnnotation.Expression.class);
                String syntax = ExprAnon.syntax();
                Class returntype = ExprAnon.returntype();
                ExpressionType exprType = ExprAnon.type();
                Skript.registerExpression(clazz, returntype, exprType, syntax);
                vExpressions.add("expression: " + syntax);
            }else if (clazz.isAnnotationPresent(EffectAnnotation.Effect.class)) {
                EffectAnnotation.Effect EffAnon = (EffectAnnotation.Effect) clazz.getAnnotation(EffectAnnotation.Effect.class);
                String syntax = EffAnon.syntax();
                Skript.registerEffect(clazz, syntax);
                vEffects.add("effect: " + syntax);
            } else if (clazz.isAnnotationPresent(EvntAnnotation.Event.class)) {
                EvntAnnotation.Event EvntAnon = (EvntAnnotation.Event) clazz.getAnnotation(EvntAnnotation.Event.class);
                String syntax = EvntAnon.syntax();
                String name = EvntAnon.name();
                Class type = EvntAnon.type();
                Skript.registerEvent(name, type, clazz, syntax);
                vEvents.add("event: " + syntax);
            }else if(clazz.isAnnotationPresent(CondAnnotation.Condition.class)){
                CondAnnotation.Condition CondAnon = (CondAnnotation.Condition) clazz.getAnnotation(CondAnnotation.Condition.class);
                String syntax = CondAnon.syntax();
                Skript.registerCondition(clazz, syntax);
                vConditions.add("condition: " + syntax);
            }
            classes++;
        }




    }
    public static Set<Class<?>> getClasses(File jarFile, String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        try {
            JarFile file = new JarFile(jarFile);
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements();) {
                JarEntry jarEntry = entry.nextElement();
                String name = jarEntry.getName().replace("/", ".");
                if(name.startsWith(packageName) && name.endsWith(".class")){
                    classes.add(Class.forName(name.substring(0, name.length() - 6)));}
            }
            file.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return classes;
    }
}
