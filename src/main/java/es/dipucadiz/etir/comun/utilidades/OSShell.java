package es.dipucadiz.etir.comun.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import es.dipucadiz.etir.comun.config.GadirConfig;

public class OSShell {
   public static BufferedReader stdout, stderr;
   public static String outputLines = "";
   public static int errores = 0;
   private static final String cadenaConexionBatch = GadirConfig.leerParametro("servidor.batch.cadena.conexion");

   private static boolean DEBUG = false;
   
   public static String runAndGet(String args) {
	   errores = 0;
	   outputLines = "";
	   
	   if(DEBUG) System.out.println("\nrunAndGet: [Inicio de ejecución en host de " + args.toString() + "]");
       try {
			if (Utilidades.isNotEmpty(cadenaConexionBatch)) {
				args = "/usr/bin/ssh " + cadenaConexionBatch + " " + args;
			}

           Runtime r;
           r = Runtime.getRuntime();
           Process p = r.exec(args);
           InputStream is = p.getInputStream();
           stdout = new BufferedReader(new InputStreamReader(is));
           is = p.getErrorStream();
           stderr = new BufferedReader(new InputStreamReader(is));
           outputLines = "";
           Thread stdoutThread = new Thread() { 
                   public void run() {
                       try {
                           int l;
                           String line;
                           boolean iniciado = false;
                           for(l = 0; (line = stdout.readLine()) != null; ) {
                               if (line.length() > 0) {
                                   l++;
                                   outputLines += (!iniciado?"":"\n") + line;
                                   iniciado = true;
                               }
                               if(DEBUG) System.out.println("runAndGet: Línea leía: " + line);
                           }
                           if(DEBUG) System.out.println("\nrunAndGet: Leídas " + l + " líneas desde stdout.");
                           stdout.close();
                       } 
                       catch(IOException ie) { 
                    	   if(DEBUG) System.out.println("runAndGet: IO exception en stdout: " + ie);
                       }
                   }
               };
           stdoutThread.start();
           Thread stderrThread = new Thread() { 
                   public void run() {
                       try {
                           int l;
                           String line;
                           for(l = 0; (line = stderr.readLine()) != null; ) {
                               if (line.length() > 0) l++;
                               if(DEBUG) System.out.println("runAndGet: ERROR leía: " + line);                               
                               if(DEBUG) System.out.print("runAndGet: ,");
                           }
                           errores = l;
                           if(DEBUG) System.out.println("\nrunAndGet: Leídas " + l + " líneas desde stderr.");
                           stderr.close();
                       } 
                       catch(IOException ie) { 
                    	   if(DEBUG) System.out.println("runAndGet: IO exception en stderr: " + ie);
                       }
                   }
               };
           stderrThread.start();
           OutputStream os = p.getOutputStream();
           PrintStream ps2 = new PrintStream(os);
           ps2.close();
           stdoutThread.join();
           stderrThread.join();
           p.waitFor();
       }
       catch (Exception e) { 
    	   if(DEBUG) System.out.println("runAndGet: Exception: " + e);
       }
       
       if(DEBUG) System.out.println("runAndGet: Listo.");
       if(DEBUG) System.out.println("runAndGet: [Fin de ejecución en host de " + args.toString() + "]\n");
       return outputLines;
   }
}
