package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.ExpressionType;
import com.avaje.ebean.Expr;
import me.iblitzkriegi.vixio.Vixio;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Blitz on 10/30/2016.
 */
public class VixioAnnotationParser {
    public static int classes = 0;
    public static HashMap<String, String> vEventExample = new HashMap<>();
    public static HashMap<String, String> vEventTitle = new HashMap<>();
    public static HashMap<String, String> vEvntShowroom = new HashMap<>();
    public static HashMap<String, String> vEventSyntax = new HashMap<>();
    public static HashMap<String, String> vEventDesc = new HashMap<>();
    public static HashMap<String, String> vCondExample = new HashMap<>();
    public static HashMap<String, String> vCondTitle = new HashMap<>();
    public static HashMap<String, String> vCondShowroom = new HashMap<>();
    public static HashMap<String, String> vCondSyntax = new HashMap<>();
    public static HashMap<String, String> vCondDesc = new HashMap<>();

    public static HashMap<String, String> vEffExample = new HashMap<>();
    public static HashMap<String, String> vEffTitle = new HashMap<>();
    public static HashMap<String, String> vEffShowroom = new HashMap<>();
    public static HashMap<String, String> vEffSyntax = new HashMap<>();
    public static HashMap<String, String> vEffDesc = new HashMap<>();

    public static HashMap<String, String> vExprExample = new HashMap<>();
    public static HashMap<String, String> vExprTitle = new HashMap<>();
    public static HashMap<String, String> vExprShowroom = new HashMap<>();
    public static HashMap<String, String> vExprSyntax = new HashMap<>();
    public static HashMap<String, String> vExprDesc = new HashMap<>();
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
                vExprTitle.put(ExprAnon.name(), ExprAnon.name());
                vExprSyntax.put(ExprAnon.name(), ExprAnon.syntax());
                vExprExample.put(ExprAnon.name(), ExprAnon.example());
                vExprShowroom.put(ExprAnon.name(), ExprAnon.title());
                vExprDesc.put(ExprAnon.name(), ExprAnon.desc());
            }else if (clazz.isAnnotationPresent(EffectAnnotation.Effect.class)) {
                EffectAnnotation.Effect EffAnon = (EffectAnnotation.Effect) clazz.getAnnotation(EffectAnnotation.Effect.class);
                String syntax = EffAnon.syntax();
                Skript.registerEffect(clazz, syntax);
                vEffTitle.put(EffAnon.name(), EffAnon.name());
                vEffSyntax.put(EffAnon.name(), EffAnon.syntax());
                vEffExample.put(EffAnon.name(), EffAnon.example());
                vEffShowroom.put(EffAnon.name(), EffAnon.title());
                vEffDesc.put(EffAnon.name(), EffAnon.desc());
            } else if (clazz.isAnnotationPresent(EvntAnnotation.Event.class)) {
                EvntAnnotation.Event EvntAnon = (EvntAnnotation.Event) clazz.getAnnotation(EvntAnnotation.Event.class);
                String syntax = EvntAnon.syntax();
                String name = EvntAnon.name();
                Class type = EvntAnon.type();
                Skript.registerEvent(name, type, clazz, syntax);
                vEventTitle.put(EvntAnon.name(), EvntAnon.name());
                vEventSyntax.put(EvntAnon.name(), EvntAnon.syntax());
                vEventExample.put(EvntAnon.name(), EvntAnon.example());
                vEvntShowroom.put(EvntAnon.name(), EvntAnon.title());
                vEventDesc.put(EvntAnon.name(), EvntAnon.desc());
            }else if(clazz.isAnnotationPresent(CondAnnotation.Condition.class)) {
                CondAnnotation.Condition CondAnon = (CondAnnotation.Condition) clazz.getAnnotation(CondAnnotation.Condition.class);
                String syntax = CondAnon.syntax();
                Skript.registerCondition(clazz, syntax);
                vCondTitle.put(CondAnon.name(), CondAnon.name());
                vCondSyntax.put(CondAnon.name(), CondAnon.syntax());
                vCondExample.put(CondAnon.name(), CondAnon.example());
                vCondShowroom.put(CondAnon.name(), CondAnon.title());
                vCondDesc.put(CondAnon.name(), CondAnon.desc());
            }
            classes = vEventTitle.size() + vCondTitle.size() + vEffTitle.size();

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
