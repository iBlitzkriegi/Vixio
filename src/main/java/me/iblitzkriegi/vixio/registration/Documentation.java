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
            bw.write(tab("Conditions:", 0));
            for (Registration registration : Vixio.getInstance().conditions) {
                bw.write(tab("name: " + registration.getName(), 1));
                bw.write(tab("description: " + registration.getDesc(), 2));
                bw.write(tab("patterns:", 2));
                if (registration.getUserFacing() == null) {
                    for (String pattern : registration.getSyntaxes()) {
                        bw.write(tab("- " + pattern, 3));
                    }
                } else {
                    bw.write(tab("- " + registration.getUserFacing(), 3));
                }
                bw.write(tab("description: " + registration.getExample(), 2));

            }
            bw.write(tab("Effects:", 0));
            for (Registration registration : Vixio.getInstance().effects) {
                bw.write(tab("name: " + registration.getName(), 1));
                bw.write(tab("description: " + registration.getDesc(), 2));
                bw.write(tab("patterns:", 2));
                if (registration.getUserFacing() == null) {
                    for (String pattern : registration.getSyntaxes()) {
                        bw.write(tab("- " + pattern, 3));
                    }
                } else {
                    bw.write(tab("- " + registration.getUserFacing(), 3));
                }
                bw.write(tab("description: " + registration.getExample(), 2));

            }
            bw.write(tab("Expressions:", 0));
            for (Registration registration : Vixio.getInstance().expressions) {
                bw.write(tab("name: " + registration.getName(), 1));
                bw.write(tab("description: " + registration.getDesc(), 2));
                bw.write(tab("patterns:", 2));
                if (registration.getUserFacing() == null) {
                    for (String pattern : registration.getSyntaxes()) {
                        bw.write(tab("- " + pattern, 3));
                    }
                } else {
                    bw.write(tab("- " + registration.getUserFacing(), 3));
                }
                bw.write(tab("description: " + registration.getExample(), 2));

            }
            bw.write(tab("Events:", 0));
            for (Registration registration : Vixio.getInstance().events) {
                bw.write(tab("name: " + registration.getName(), 1));
                bw.write(tab("description: " + registration.getDesc(), 2));
                bw.write(tab("patterns:", 2));
                if (registration.getUserFacing() == null) {
                    for (String pattern : registration.getSyntaxes()) {
                        bw.write(tab("- " + pattern, 3));
                    }
                } else {
                    bw.write(tab("- " + registration.getUserFacing(), 3));
                }
                bw.write(tab("description: " + registration.getExample(), 2));

            }
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void generateJson() {
        //TODO REVIST! Could be heavily shortened
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
            bw.write("{\n");
            bw.write("\t\"events\": [\n");
            int events = Vixio.getInstance().events.size();
            int i = 0;
            for (Registration registration : Vixio.getInstance().events) {
                i++;
                if (registration.getEvent() != null) {
                    bw.write(tab("{", 2));
                    bw.write(tab("\"description\": \"" + escapeString(registration.getDesc()) + "\",", 3));
                    bw.write(tab("\"name\": \"" + registration.getName() + "\",", 3));
                    bw.write(tab("\"patterns\": [", 3));
                    if (registration.getSyntaxes().length > 1) {
                        int patterns = 0;
                        for (String pattern : registration.getSyntaxes()) {
                            patterns++;
                            if (patterns < registration.getSyntaxes().length) {
                                bw.write(tab("\"" + pattern + "\",", 4));
                            } else {
                                bw.write(tab("\"" + pattern + "\"", 4));
                            }
                        }

                    } else {
                        bw.write(tab("\"" + registration.getSyntax() + "\"", 4));
                    }
                    bw.write(tab("],", 3));
                    if (registration.getEvent() != null) {
                        List<String> eventValues = getEventValues(registration.getEvent());
                        bw.write(tab("\"eventvalues\": [", 3));
                        int eventValue = 0;
                        for (String s : eventValues) {
                            eventValue++;
                            if (eventValue < eventValues.size()) {
                                bw.write(tab("\"" + s + "\",", 4));
                            } else {
                                bw.write(tab("\"" + s + "\"", 4));
                            }
                        }
                    }
                    bw.write(tab("],", 3));
                    bw.write(tab("\"examples\": [", 3));
                    int examples = 0;
                    String[] splitExample = registration.getExample().split(",");
                    for (String example : splitExample) {
                        examples++;
                        if (examples < splitExample.length) {
                            bw.write(tab("\"" + escapeString(example) + "\",", 4));
                        } else {
                            bw.write(tab("\"" + escapeString(example) + "\"", 4));
                        }
                    }
                    bw.write(tab("]", 3));
                    if (i < events) {
                        bw.write(tab("},", 2));
                    } else {
                        bw.write(tab("}", 2));
                        bw.write(tab("],", 1));
                    }
                }
            }
            //CONDITIONS
            bw.write(tab("\"conditions\": [", 1));
            int conditions = Vixio.getInstance().conditions.size();
            int conditionCount = 0;
            for (Registration registration : Vixio.getInstance().conditions) {
                conditionCount++;
                bw.write(tab("{", 2));
                bw.write(tab("\"description\": \"" + escapeString(registration.getDesc()) + "\",", 2));
                bw.write(tab("\"name\": \"" + registration.getName() + "\",", 2));
                bw.write(tab("\"patterns\": [", 3));
                if (registration.getUserFacing() != null) {
                    bw.write(tab("\"" + registration.getUserFacing() + "\"", 4));
                } else {
                    if (registration.getSyntaxes().length > 1) {
                        int patterns = 0;
                        for (String pattern : registration.getSyntaxes()) {
                            patterns++;
                            if (patterns < registration.getSyntaxes().length) {
                                bw.write(tab("\"" + pattern + "\",", 4));
                            } else {
                                bw.write(tab("\"" + pattern + "\"", 4));
                            }
                        }

                    } else {
                        bw.write(tab("\"" + registration.getSyntax() + "\"", 4));
                    }
                }
                bw.write(tab("],", 3));
                bw.write(tab("\"examples\": [", 3));
                int examples = 0;
                String[] splitExample = registration.getExample().split(",");
                for (String example : splitExample) {
                    examples++;
                    if (examples < splitExample.length) {
                        bw.write(tab("\"" + escapeString(example) + "\",", 4));
                    } else {
                        bw.write(tab("\"" + escapeString(example) + "\"", 4));
                    }
                }
                bw.write(tab("]", 3));
                if (conditionCount < conditions) {
                    bw.write(tab("},", 2));
                } else {
                    bw.write(tab("}", 2));
                    bw.write(tab("],", 1));
                }

            }
            bw.write(tab("\"effects\": [", 1));
            int effects = Vixio.getInstance().effects.size();
            int effectsCount = 0;
            for (Registration registration : Vixio.getInstance().effects) {
                effectsCount++;
                bw.write(tab("{", 2));
                bw.write(tab("\"description\": \"" + escapeString(registration.getDesc()) + "\",", 2));
                bw.write(tab("\"name\": \"" + registration.getName() + "\",", 2));
                bw.write(tab("\"patterns\": [", 3));
                if (registration.getUserFacing() != null) {
                    bw.write(tab("\"" + registration.getUserFacing() + "\"", 4));
                } else {
                    if (registration.getSyntaxes().length > 1) {
                        int patterns = 0;
                        for (String pattern : registration.getSyntaxes()) {
                            patterns++;
                            if (patterns < registration.getSyntaxes().length) {
                                bw.write(tab("\"" + pattern + "\",", 4));
                            } else {
                                bw.write(tab("\"" + pattern + "\"", 4));
                            }
                        }

                    } else {
                        bw.write(tab("\"" + registration.getSyntax() + "\"", 4));
                    }
                }
                bw.write(tab("],", 3));
                bw.write(tab("\"examples\": [", 3));
                int examples = 0;
                String[] splitExample = registration.getExample().split(",");
                for (String example : splitExample) {
                    examples++;
                    if (examples < splitExample.length) {
                        bw.write(tab("\"" + escapeString(example) + "\",", 4));
                    } else {
                        bw.write(tab("\"" + escapeString(example) + "\"", 4));
                    }
                }
                bw.write(tab("]", 3));
                if (effectsCount < effects) {
                    bw.write(tab("},", 2));
                } else {
                    bw.write(tab("}", 2));
                    bw.write(tab("],", 1));
                }

            }
            bw.write(tab("\"expressions\": [", 1));
            int expressions = Vixio.getInstance().expressions.size();
            int expressionsCount = 0;
            for (Registration registration : Vixio.getInstance().expressions) {
                expressionsCount++;
                bw.write(tab("{", 2));
                bw.write(tab("\"description\": \"" + escapeString(registration.getDesc()) + "\",", 2));
                bw.write(tab("\"name\": \"" + registration.getName() + "\",", 2));
                bw.write(tab("\"patterns\": [", 3));
                if (registration.getUserFacing() != null) {
                    bw.write(tab("\"" + registration.getUserFacing() + "\"", 4));
                } else {
                    if (registration.getSyntaxes().length > 1) {
                        int patterns = 0;
                        for (String pattern : registration.getSyntaxes()) {
                            patterns++;
                            if (patterns < registration.getSyntaxes().length) {
                                bw.write(tab("\"" + pattern + "\",", 4));
                            } else {
                                bw.write(tab("\"" + pattern + "\"", 4));
                            }
                        }

                    } else {
                        bw.write(tab("\"" + registration.getSyntax() + "\"", 4));
                    }
                }
                bw.write(tab("],", 3));
                bw.write(tab("\"examples\": [", 3));
                int examples = 0;
                String[] splitExample = registration.getExample().split(",");
                for (String example : splitExample) {
                    examples++;
                    if (examples < splitExample.length) {
                        bw.write(tab("\"" + escapeString(example) + "\",", 4));
                    } else {
                        bw.write(tab("\"" + escapeString(example) + "\"", 4));
                    }
                }
                bw.write(tab("]", 3));
                if (expressionsCount < expressions) {
                    bw.write(tab("},", 2));
                } else {
                    bw.write(tab("}", 2));
                    bw.write(tab("]", 1));
                    bw.write("}");
                }

            }
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

    public static String tab(String s, int tabs) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tabs; i++) {
            builder.append("\t");
        }
        builder.append(s + "\n");
        return builder.toString();
    }

    public static String escapeString(String s) {
        return s.replaceAll("\t", "\\\\t")
                .replaceAll("\"", "\\\"");
    }
}
