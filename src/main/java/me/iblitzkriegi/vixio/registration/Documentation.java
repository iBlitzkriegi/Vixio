package me.iblitzkriegi.vixio.registration;

import ch.njol.skript.registrations.EventValues;
import me.iblitzkriegi.vixio.Vixio;
import org.bukkit.event.Event;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Blitz on 7/22/2017.
 */
public class Documentation {
    private static List<Class<?>> events = new ArrayList<>();
    public static void setupSyntaxFile(){
        File file = new File(Vixio.getInstance().getDataFolder(), "Syntaxes.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }
        }catch (IOException x){

        }

        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("-=Effects=-");
            bw.newLine();
            for(Registration reg : Vixio.getInstance().effects) {
                boolean t = reg.getSyntaxes().length == 2 ? true : false;
                if(t){
                    bw.write("\tsyntax: " + "{\"" + reg.getSyntaxes()[0] + "\", \"" + reg.getSyntaxes()[1] + "\"}");
                }else{
                    bw.write("\tsyntax: " + reg.getSyntaxes()[0]);
                }
                bw.newLine();
            }
            bw.write("-=Expressions=-");
            bw.newLine();
            for(Registration reg : Vixio.getInstance().expressions){
                boolean t = reg.getSyntaxes().length == 2 ? true : false;
                if(t){
                    bw.write("\tsyntax: " + "{\"" + reg.getSyntaxes()[0] + "\", \"" + reg.getSyntaxes()[1] + "\"}");
                }else{
                    bw.write("\tsyntax: " + reg.getSyntaxes()[0]);
                }
                bw.newLine();


            }
            bw.write("-=Events=-");
            bw.newLine();
            for(Registration reg : Vixio.getInstance().events){
                boolean t = reg.getSyntaxes().length == 2 ? true : false;
                if(t){
                    bw.write("\tsyntax: " + "{\"" + reg.getSyntaxes()[0] + "\", \"" + reg.getSyntaxes()[1] + "\"}");
                }else{
                    bw.write("\tsyntax: " + reg.getSyntaxes()[0]);
                }
                bw.newLine();
            }
            bw.flush();
            bw.close();
            //getEventValues(bw, 0, EventJDAEvent.class, EventGuildMessageReceived.class, AnyEvent.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void getEventValues(BufferedWriter bw, int time, Class<? extends Event>... classes) throws IOException {
        bw.write("-=Events=-");
        bw.newLine();
        Method m = getMethod(EventValues.class, "getEventValuesList", int.class);
        List<?> values = invokeMethod(m, null, time);
        if (values != null)
            for (Class<?> c : classes) {
                bw.write("on " + Vixio.eventsSyntax.get(c.getSimpleName()).getSyntaxes()[0]);
                bw.newLine();
                bw.write("\tEvent values:");
                bw.newLine();
                for (Object eventValue : values) {
                    Class<?> event = getField(eventValue.getClass(), eventValue, "event");
                    if (event != null && (c.isAssignableFrom(event) || event.isAssignableFrom(c))) {
                        Class<?> ret = getField(eventValue.getClass(), eventValue, "c");
                        bw.write("\t\t- event-" + ret.getSimpleName().toLowerCase());
                        bw.newLine();
                    }
                }
            }
        bw.flush();
        bw.close();
    }
    public static Method getMethod(Class<?> clz, String method, Class<?>... parameters){
        try {
            return clz.getDeclaredMethod(method, parameters);
        } catch (Exception e){

        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Class<?> clz, String method, Object instance, Object... parameters){
        try {
            Class<?>[] parameterTypes = new Class<?>[parameters.length];
            int x = 0;
            for (Object obj : parameters)
                parameterTypes[x++] = obj.getClass();
            Method m = clz.getDeclaredMethod(method, parameterTypes);
            m.setAccessible(true);
            return (T) m.invoke(instance, parameters);
        } catch (Exception e){

        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Method method, Object instance, Object... parameters){
        try {
            method.setAccessible(true);
            return (T) method.invoke(instance, parameters);
        } catch (Exception e){

        }
        return null;
    }
    @SuppressWarnings("unchecked")
    public static <T> T getField(Class<?> from, Object obj, String field){
        try{
            Field f = from.getDeclaredField(field);
            f.setAccessible(true);
            return (T) f.get(obj);
        } catch (Exception e){

        }
        return null;

    }
}
