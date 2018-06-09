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
    public static void setupSyntaxFile() {
        File file = new File(Vixio.getInstance().getDataFolder(), "Syntaxes.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }
        } catch (IOException x) {

        }
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("-=Conditions=-");
            bw.newLine();
            for (Registration reg : Vixio.getInstance().conditions) {
                boolean multipleSyntax = reg.getSyntaxes().length == 2;
                if (multipleSyntax) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("\tsyntax: {\"");
                    for (int i = 0; i < reg.getSyntaxes().length; i++) {
                        if (i + 1 == reg.getSyntaxes().length) {
                            builder.append(reg.getSyntaxes()[i] + "\"");
                        } else {
                            builder.append(reg.getSyntaxes()[i] + "\",");
                        }
                    }
                    builder.append("}");
                    bw.write(builder.toString());
                    bw.newLine();
                } else {
                    bw.write("\tsyntax: " + reg.getSyntax());
                    bw.newLine();
                }
            }
            bw.write("-=Effects=-");
            bw.newLine();
            for (Registration reg : Vixio.getInstance().effects) {
                if (reg.getUserFacing() != null) {
                    bw.write("\tsyntax: " + reg.getUserFacing());
                    bw.newLine();
                } else {
                    boolean multipleSyntax = reg.getSyntaxes().length == 2;
                    if (multipleSyntax) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("\tsyntax: {\"");
                        for (int i = 0; i < reg.getSyntaxes().length; i++) {
                            if (i + 1 == reg.getSyntaxes().length) {
                                builder.append(reg.getSyntaxes()[i] + "\"");
                            } else {
                                builder.append(reg.getSyntaxes()[i] + "\",");
                            }
                        }

                        builder.append("}");
                        bw.write(builder.toString());
                        bw.newLine();
                    } else {
                        bw.write("\tsyntax: " + reg.getSyntax());
                        bw.newLine();
                    }
                }
            }
            bw.write("-=Expressions=-");
            bw.newLine();

            for (Registration reg : Vixio.getInstance().expressions) {
                boolean multipleSyntax = reg.getSyntaxes().length == 2;
                if (reg.getUserFacing() != null) {
                    bw.write("\tsyntax: " + reg.getUserFacing());
                    bw.newLine();
                } else {
                    if (multipleSyntax) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("\tsyntax: {\"");
                        for (int i = 0; i < reg.getSyntaxes().length; i++) {
                            if (i + 1 == reg.getSyntaxes().length) {
                                builder.append(reg.getSyntaxes()[i] + "\"");
                            } else {
                                builder.append(reg.getSyntaxes()[i] + "\",");
                            }
                        }
                        builder.append("}");
                        bw.write(builder.toString());
                        bw.newLine();

                    } else {
                        bw.write("\tsyntax: " + reg.getSyntax());
                        bw.newLine();
                    }
                }
            }
            bw.write("-=Events=-");
            bw.newLine();
            for (Registration reg : Vixio.getInstance().events) {
                boolean multipleSyntax = reg.getSyntaxes().length == 2;
                if (multipleSyntax) {
                    StringBuilder builder = new StringBuilder();
                    builder.append("\tsyntax: {\"");
                    for (int i = 0; i < reg.getSyntaxes().length; i++) {
                        if (i + 1 == reg.getSyntaxes().length) {
                            builder.append(reg.getSyntaxes()[i] + "\"");
                        } else {
                            builder.append(reg.getSyntaxes()[i] + "\",");
                        }
                    }
                    builder.append("}");
                    bw.write(builder.toString());
                    bw.newLine();
                } else {
                    bw.write("\tsyntax: " + reg.getSyntax());
                    bw.newLine();
                }
            }
            bw.flush();
            bw.close();
            //getEventValues(bw, 0, EventJDAEvent.class, EventGuildMessageReceived.class, AnyEvent.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void generateJson() {
        File file = new File(Vixio.getInstance().getDataFolder(), "syntaxes.json");
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
            }
        } catch (IOException x) {

        }
        try {
            FileWriter fw;
            fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            // Effects
            bw.write("\t{");
            bw.newLine();
            bw.write("\t\t\"effects\": [{");
            int effects = 0;
            for (Registration reg : Vixio.getInstance().effects) {
                effects = effects + 1;
                bw.newLine();
                bw.write("\t\t{");
                bw.newLine();
                bw.write("\t\t\t\"examples\": [");
                bw.newLine();
                bw.write("\t\t\t\t[\"" + reg.getExample() + "\"]");
                bw.newLine();
                bw.write("\t\t\t],");
                bw.newLine();
                bw.write("\t\t\t\"name\": \"" + reg.getName() + "\",");
                bw.newLine();
                bw.write("\t\t\t\"description\": \"" + reg.getDesc() + "\",");
                bw.newLine();
                bw.write("\t\t\t\"syntaxes\": [{");
                bw.newLine();
                if (reg.getUserFacing() != null) {
                    bw.write("\t\t\t\t\"syntax\": \"" + reg.getUserFacing() + "\"");
                    bw.newLine();
                } else {
                    boolean multipleSyntax = reg.getSyntaxes().length == 2;
                    if (multipleSyntax) {
                        int multiSyntax = 0;
                        for (String syntax : reg.getSyntaxes()) {
                            multiSyntax = multiSyntax + 1;
                            if (multiSyntax == 1) {
                                bw.write("\t\t\t\t\"syntax\": \"" + syntax + "\"");
                                bw.newLine();
                                bw.write("\t\t\t}, {");
                                bw.newLine();
                            } else {
                                if (multiSyntax != reg.getSyntaxes().length) {
                                    bw.write("\t\t\t\t\"syntax\": \"" + syntax + "\"");
                                    bw.newLine();
                                } else {
                                    bw.write("\t\t\t\t\"syntax\": \"" + syntax + "\"");
                                    bw.newLine();
                                }
                            }
                        }
                    } else {
                        bw.write("\t\t\t\t\"syntax\": \"" + reg.getSyntax() + "\"");
                        bw.newLine();
                    }
                }
                if (effects != Vixio.getInstance().effects.size()) {
                    bw.write("\t\t\t}],");
                    bw.newLine();
                    bw.write("\t\t},");
                } else {
                    bw.write("\t\t\t}]");
                    bw.newLine();
                    bw.write("\t\t}");
                }
            }
            bw.newLine();
            bw.write("\t\t}],");
            bw.newLine();
            bw.write("\t},");


//            bw.write("-=Events=-");
//            bw.newLine();
//            for (Registration reg : Vixio.getInstance().events) {
//                if (reg.getEvent() != null) {
//                    bw.write("syntax: " + reg.getSyntax());
//                    bw.newLine();
//                    List<String> values = getEventValues(reg.getEvent());
//                    bw.write("Event values: " + values.subList(0, values.size()));
//                    bw.newLine();
//                }
//            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> getEventValues(Class<? extends Event>... classes) {

        Method m = getMethod(EventValues.class, "getEventValuesList", int.class);
        List<?> values = invokeMethod(m, null, 0);
        List<String> eventValues = new ArrayList<>();
        if (values != null)
            for (Class<?> c : classes) {
                for (Object eventValue : values) {
                    Class<?> event = getField(eventValue.getClass(), eventValue, "event");
                    if (event != null && (c.isAssignableFrom(event) || event.isAssignableFrom(c))) {
                        Class<?> ret = getField(eventValue.getClass(), eventValue, "c");
                        eventValues.add("event-" + ret.getSimpleName().toLowerCase().replaceAll("updatingmessage", "message"));
                    }
                }
            }
        return eventValues;
    }

    public static Method getMethod(Class<?> clz, String method, Class<?>... parameters) {
        try {
            return clz.getDeclaredMethod(method, parameters);
        } catch (Exception e) {

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Class<?> clz, String method, Object instance, Object... parameters) {
        try {
            Class<?>[] parameterTypes = new Class<?>[parameters.length];
            int x = 0;
            for (Object obj : parameters)
                parameterTypes[x++] = obj.getClass();
            Method m = clz.getDeclaredMethod(method, parameterTypes);
            m.setAccessible(true);
            return (T) m.invoke(instance, parameters);
        } catch (Exception e) {

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T invokeMethod(Method method, Object instance, Object... parameters) {
        try {
            method.setAccessible(true);
            return (T) method.invoke(instance, parameters);
        } catch (Exception e) {

        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getField(Class<?> from, Object obj, String field) {
        try {
            Field f = from.getDeclaredField(field);
            f.setAccessible(true);
            return (T) f.get(obj);
        } catch (Exception e) {

        }
        return null;

    }
}
