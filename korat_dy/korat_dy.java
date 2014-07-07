package korat_dy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class korat_dy {                               
    static final String local_path = "";
    static final int min = 3;
    static final int max = 4;
    static final String token = "--class graph.Graph --args";
    public static void main(String[] args) {
            try {
                System.setProperty("java.class.path", local_path);
                System.setProperty("user.dir", local_path);
                Process proc = Runtime.getRuntime().exec("javac -cp " + local_path + "lib/korat-1.0.jar " + local_path + "graph/Graph.java" );
                proc.waitFor();
                for(int i=min;i<=max;i++) {
                    Process proc3 = Runtime.getRuntime().exec(

                            "java -cp " + local_path +"lib/korat-1.0.jar:. korat.Korat "
                            +
                            token
                            +
                            i
                            );
                     
                    BufferedReader br = new BufferedReader(new InputStreamReader(proc3.getInputStream()));
                    BufferedReader br2 = new BufferedReader(new InputStreamReader(proc3.getErrorStream()));
                    String line;
                    while ( (line = br.readLine()) != null || (line = br2.readLine()) != null)
                        System.out.println(line);
                    proc3.waitFor();
                }
                
                System.out.print("<---------------------------------------------->\n");
                System.out.println("exit");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (java.lang.InterruptedException e) {
                e.printStackTrace();
            }
    }

    public korat_dy() {
    }

    static public native int getMaxBySort(int [] arr);
}
