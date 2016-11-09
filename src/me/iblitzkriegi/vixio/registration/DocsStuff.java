package me.iblitzkriegi.vixio.registration;

import me.iblitzkriegi.vixio.Vixio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Blitz on 11/2/2016.
 */
public class DocsStuff {
    public static void setUpSyntaxes() {
        File file = new File(Vixio.pluginFile, "Syntaxs.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
        }
        int it = 0;
        for (String s : VixioAnnotationParser.vExpressions) {
            if (it == 0) {
                try {
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(file, true);
                    } catch (IOException r) {
                        r.printStackTrace();
                    }
                    BufferedWriter bw = new BufferedWriter(fw);
                    try {
                        bw.write("-=Expressions=-");

                    } catch (IOException b) {
                        b.printStackTrace();
                    }
                    try {
                        bw.newLine();
                    } catch (IOException t) {
                        t.printStackTrace();
                    }
                    try {
                        bw.flush();
                    } catch (IOException m) {
                        m.printStackTrace();
                    }
                    try {
                        bw.close();
                    } catch (IOException p) {
                        p.printStackTrace();
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
                it++;
            }
            String towrite = s.replaceAll("expression: ", "");
            try {
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file, true);
                } catch (IOException r) {
                    r.printStackTrace();
                }
                BufferedWriter bw = new BufferedWriter(fw);
                try {
                    bw.write(towrite);

                } catch (IOException b) {
                    b.printStackTrace();
                }
                try {
                    bw.newLine();
                } catch (IOException t) {
                    t.printStackTrace();
                }
                try {
                    bw.flush();
                } catch (IOException m) {
                    m.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException p) {
                    p.printStackTrace();
                }
            } catch (Exception x) {
                x.printStackTrace();
            }

        }
        int itt = 0;
        for (String s : VixioAnnotationParser.vEffects) {
            if (itt == 0) {
                try {
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(file, true);
                    } catch (IOException r) {
                        r.printStackTrace();
                    }
                    BufferedWriter bw = new BufferedWriter(fw);
                    try {
                        bw.write("-=Effects=-");

                    } catch (IOException b) {
                        b.printStackTrace();
                    }
                    try {
                        bw.newLine();
                    } catch (IOException t) {
                        t.printStackTrace();
                    }
                    try {
                        bw.flush();
                    } catch (IOException m) {
                        m.printStackTrace();
                    }
                    try {
                        bw.close();
                    } catch (IOException p) {
                        p.printStackTrace();
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
                itt++;
            }
            String towrite = s.replaceAll("effect: ", "");
            try {
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file, true);
                } catch (IOException r) {
                    r.printStackTrace();
                }
                BufferedWriter bw = new BufferedWriter(fw);
                try {
                    bw.write(towrite);

                } catch (IOException b) {
                    b.printStackTrace();
                }
                try {
                    bw.newLine();
                } catch (IOException t) {
                    t.printStackTrace();
                }
                try {
                    bw.flush();
                } catch (IOException m) {
                    m.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException p) {
                    p.printStackTrace();
                }
            } catch (Exception x) {
                x.printStackTrace();
            }

        }
        int ittt = 0;
        for (String s : VixioAnnotationParser.vEvents) {
            if (ittt == 0) {
                try {
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(file, true);
                    } catch (IOException r) {
                        r.printStackTrace();
                    }
                    BufferedWriter bw = new BufferedWriter(fw);
                    try {
                        bw.write("-=Events=-");

                    } catch (IOException b) {
                        b.printStackTrace();
                    }
                    try {
                        bw.newLine();
                    } catch (IOException t) {
                        t.printStackTrace();
                    }
                    try {
                        bw.flush();
                    } catch (IOException m) {
                        m.printStackTrace();
                    }
                    try {
                        bw.close();
                    } catch (IOException p) {
                        p.printStackTrace();
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
                ittt++;
            }
            String towrite = s.replaceAll("event: ", "");
            try {
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file, true);
                } catch (IOException r) {
                    r.printStackTrace();
                }
                BufferedWriter bw = new BufferedWriter(fw);
                try {
                    bw.write(towrite);

                } catch (IOException b) {
                    b.printStackTrace();
                }
                try {
                    bw.newLine();
                } catch (IOException t) {
                    t.printStackTrace();
                }
                try {
                    bw.flush();
                } catch (IOException m) {
                    m.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException p) {
                    p.printStackTrace();
                }
            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        int itttt = 0;
        for (String s : VixioAnnotationParser.vConditions) {
            if (itttt == 0) {
                try {
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(file, true);
                    } catch (IOException r) {
                        r.printStackTrace();
                    }
                    BufferedWriter bw = new BufferedWriter(fw);
                    try {
                        bw.write("-=Conditions=-");

                    } catch (IOException b) {
                        b.printStackTrace();
                    }
                    try {
                        bw.newLine();
                    } catch (IOException t) {
                        t.printStackTrace();
                    }
                    try {
                        bw.flush();
                    } catch (IOException m) {
                        m.printStackTrace();
                    }
                    try {
                        bw.close();
                    } catch (IOException p) {
                        p.printStackTrace();
                    }
                } catch (Exception x) {
                    x.printStackTrace();
                }
                itttt++;
            }
            String towrite = s.replaceAll("condition: ", "");
            try {
                FileWriter fw = null;
                try {
                    fw = new FileWriter(file, true);
                } catch (IOException r) {
                    r.printStackTrace();
                }
                BufferedWriter bw = new BufferedWriter(fw);
                try {
                    bw.write(towrite);

                } catch (IOException b) {
                    b.printStackTrace();
                }
                try {
                    bw.newLine();
                } catch (IOException t) {
                    t.printStackTrace();
                }
                try {
                    bw.flush();
                } catch (IOException m) {
                    m.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException p) {
                    p.printStackTrace();
                }

            } catch (Exception x) {
                x.printStackTrace();
            }
        }
        finishSyntax();
    }

    private static void finishSyntax() {
        File file = new File(Vixio.pluginFile, "Syntaxs.txt");
        try {
            FileWriter fw = null;
            try {
                fw = new FileWriter(file, true);
            } catch (IOException r) {
                r.printStackTrace();
            }
            BufferedWriter bw = new BufferedWriter(fw);
            try {
                bw.write("-=Statistics=-");
                bw.newLine();
                bw.write("Conditions: " + VixioAnnotationParser.vConditions.size());
                bw.newLine();
                bw.write("Events:" + VixioAnnotationParser.vEvents.size());
                bw.newLine();
                bw.write("Expressions: " + VixioAnnotationParser.vExpressions.size());
                bw.newLine();
                bw.write("Effects: " + VixioAnnotationParser.vEffects.size());
                bw.newLine();
                bw.write("Classes: " + VixioAnnotationParser.classes);

            } catch (IOException b) {
                b.printStackTrace();
            }
            try {
                bw.newLine();
            } catch (IOException t) {
                t.printStackTrace();
            }
            try {
                bw.flush();
            } catch (IOException m) {
                m.printStackTrace();
            }
            try {
                bw.close();

            } catch (IOException p) {
                p.printStackTrace();
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}

