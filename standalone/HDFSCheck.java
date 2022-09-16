/************************************************************/
/* HDFSCheck                                                */
/*                                                          */
/* The purpose of this code is to verify HDFS connectivity  */
/* to a Hadoop cluster.                                     */
/*                                                          */
/* Execution:                                               */
/* java HDFSCheck <parameters>                              */
/*                                                          */
/* Parameters syntax:                                       */
/* Run "java HDFSCheck" without parameters                  */
/************************************************************/
import java.io.*;
import java.util.*;
import java.sql.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLClassLoader;
import java.net.URL;
import java.lang.reflect.Method;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class HDFSCheck
       {
        public static void main(String[] args) throws IOException, URISyntaxException
               {
                PrintStream   filterOut = new PrintStream(System.out) {public void println(String l) {if ((!l.startsWith("SLF4J")) && (!l.startsWith("log4j"))) super.println("l = " + l);}};System.setErr(filterOut);
                Configuration configuration = null;
                InputStream   inputStream = null;
                FileSystem    hdfs = null;
                Path          file = null;
                int           progress = 0;
                Boolean       found_coresite = false, debug = false;
                String        splitChar, OS = System.getProperty("os.name");

                /************************************************************/
                /* Check arguments                                          */
                /*                                                          */
                /* With no arguments, print the help info.                  */
                /************************************************************/
                if (args.length == 0)
                   {
                    System.out.println("");
                    System.out.println("This Java program verifies HDFS connectivity to a Hadoop cluster.");
                    System.out.println("");
                    System.out.println("Syntax:");
                    System.out.println("");
                    System.out.println("> java HDFSCheck <HDFS server> <HDFS service port number> <HDFS | MAPRFS> <local file name to write to HDFS>");
                    System.out.println("");
                    System.out.println("  - <HDFS server>                    : Required. Name of the server where the HDFS service is running");
                    System.out.println("  - <HDFS service port number>       : Required. HDFS service port number");
                    System.out.println("  - <Filesystem type (HDFS | MAPRFS) : Required. Filesystem type (HDFS | MAPRFS)");
                    System.out.println("  - <Local file name>                : Name of a local file to write to HDFS");
                    System.out.println("  - <debug flag>                     : Whether exceptions should be printed on screen [DEBUG | NODEBUG]. Default is NODEBUG");
                    System.out.println("");
                    System.exit(1);
                   }

                /************************************************************/
                /* Make sure we have the right number of arguments.         */
                /************************************************************/
                if (
                    (args.length < 4) |
                    (args.length > 5)
                   )
                   {
                    System.out.println("");
             	    System.out.println("Invalid syntax. Invoke the program without arguments to display help info");
            	    System.exit(1);
                   }

                /************************************************************/
                /* Do we have everything we need?                           */
                /************************************************************/
                if (
                    (
        		     (args[2].equalsIgnoreCase("HDFS")) |
        		     (args[2].equalsIgnoreCase("MAPRFS"))
        		    ) &&
        		    (
        		     (args.length == 4) |
    	             (
        		      (args.length == 5) &&
                      (
    	               (args[4].equalsIgnoreCase("DEBUG")) |
        		       (args[4].equalsIgnoreCase("NODEBUG"))
        		      )
        		     )
    	            )
        		   )
        		   {
                    if (
        		        (args.length == 5) &&
        		        (args[4].equalsIgnoreCase("DEBUG"))
        		       )
                       debug = true;

                    System.out.println("");
                    System.out.println("Connecting to the HDFS server using port " + args[1]);
                    System.out.println("");
                    System.out.println("  - HDFS server name         : " + args[0] );
                    System.out.println("  - HDFS service port number : " + args[1] );
                    System.out.println("  - Filesystem type          : " + args[2] );
                    System.out.println("  - File to write to HDFS    : " + args[3] );
                    System.out.println("  - Debug mode               : " + debug );
                    System.out.println("");
        		   }
                else if (
                		 !(args[2].equalsIgnoreCase("HDFS")) &&
            			 !(args[2].equalsIgnoreCase("MAPRFS"))
            			)
                        {
                         System.out.println("");
                         System.out.println("Invalid value for filesystem type. Valid values are HDFS or MAPRFS.");
            	         System.exit(1);
    	                }
                     else
                        {
                         System.out.println("");
            	         System.out.println("Invalid syntax. Invoke the program without arguments to display help info");
            	         System.exit(1);
                        }

                /************************************************************/
                /* Set the logging level...                                 */
                /************************************************************/
                Logger rootLogger = Logger.getRootLogger();

                if (!debug)
                   rootLogger.setLevel(Level.OFF);
                else
                   rootLogger.setLevel(Level.ALL);

                /************************************************************/
                /* Is core-site.xml in the CLASSPATH ?                      */
                /************************************************************/
                if (OS.length() >= 7 && OS.substring(0,7).toLowerCase().equals("windows"))
                   splitChar = ";";
                else
                   splitChar = ":";

                final java.lang.String list = java.lang.System.getProperty("java.class.path");

                for (final java.lang.String path : list.split(splitChar))
                    {
                     final java.io.File object = new java.io.File(path);

                     if (object.isDirectory())
                        {
                         for (java.lang.String entry : object.list())
                             {
                              final java.io.File thing = new java.io.File(entry);

                              if (thing.toString().equals("core-site.xml"))
                                 found_coresite = true;
                             }
                        }
                     else if (object.isFile())
                             {
                              if (object.toString().contains("core-site.xml"))
                                 found_coresite = true;
                             }
                    }

                if (!found_coresite)
                   {
                    System.out.println("ERROR: core-site.xml not found in your CLASSPATH.");
                    System.exit(-1);
        		   }

                /************************************************************/
                /* core-site.xml is in the CLASSPATH.                       */
                /************************************************************/
                try {
                     inputStream = new BufferedInputStream(new FileInputStream(args[3]));
        		    }
        		catch (IOException ex)
        		      {
        		       System.out.println("ERROR: File " + args[3] + " doesn't exist in the current directory.");
        		       System.out.println("");
        		       System.exit(-1);
                      }

                configuration = new Configuration();
                hdfs = FileSystem.get(new URI(args[2].toLowerCase() + "://" + args[0] + ":" + args[1]), configuration);
                file = new Path(args[2].toLowerCase() + "://" + args[0] + ":" + args[1] + "/tmp/" + args[3]);
                System.out.println("Full connection string is " + args[2].toLowerCase() + "://" + args[0] + ":" + args[1] + "/tmp/" + args[3]);
                System.out.println("");

                /************************************************************/
                /* If the file already exists, remove it                    */
                /************************************************************/
                if (!debug)
                   {
            	    /************************************************************/
        		    /* Running in silent mode...                                */
                    /************************************************************/
                    try {
                         if (hdfs.exists(file))
                            {
                             System.out.println("Output file already exists on HDFS. Deleting it...");
                             hdfs.delete(file, true);
         		    	    }
        		        }
        		    catch (IOException ex)
        		          {
        		           System.out.println("ERROR: Unable to connect to HDFS. Verify that server name and port number are correct.");
        		           System.out.println("       If Kerberos is installed, run the kinit command to obtain a valid ticket before executing this script again.");
        		           System.out.println("");
        		           System.exit(-1);
                          }
                   }
                else
                   {
             	    /************************************************************/
        		    /* Running in debug mode...                                 */
                    /************************************************************/
        		    if (hdfs.exists(file))
                       {
                        System.out.println("Output file already exists on HDFS. Deleting it...");
                        hdfs.delete(file, true);
                       }
        		   }

                /************************************************************/
                /* Open a OutputStream to write the data                    */
                /************************************************************/
                OutputStream outputStream = hdfs.create(file);

                if (!debug)
                   {
             	    /************************************************************/
        		    /* Running in silent mode...                                */
                    /************************************************************/
                    try
                      {
                       IOUtils.copyBytes(inputStream, outputStream, 4096, false);

                       progress++;

                       printProgBar(progress);
                       try {
            	    	    /************************************************************/
     	                    /* Why don't you take a 1/10 of a second nap?               */
                            /************************************************************/
                            Thread.sleep(100);
                           }
        		       catch (InterruptedException ex)
         		             {
        		              Thread.currentThread().interrupt();
                             }
    	              }
        		    catch (IOException e)
        		          {
                           if (OS.length() >= 7 && OS.substring(0,7).toLowerCase().equals("windows"))
                              ;
                           else
                              {
        	 	               IOUtils.closeStream(inputStream);
            			       IOUtils.closeStream(outputStream);
            			      }

                           throw e;
                          }
         		    finally
        		          {
                           printProgBar(100);
         		    	   System.out.println("");
        		    	   System.out.println("HDFS connectivity was successful.");
                           System.out.println("");

                           if (OS.length() >= 7 && OS.substring(0,7).toLowerCase().equals("windows"))
                              ;
                           else
                              {
                               IOUtils.closeStream(inputStream);
                               IOUtils.closeStream(outputStream);
            			      }
        		          }
     		       }
	            else
	               {
        	        /************************************************************/
    		        /* Running in debug mode...                                 */
                    /************************************************************/
                    IOUtils.copyBytes(inputStream, outputStream, 4096, false);

                    progress++;

                    printProgBar(progress);
                    try {
                         /************************************************************/
	                     /* Why don't you take a 1/10 of a second nap?               */
                         /************************************************************/
                         Thread.sleep(100);
                        }
		            catch (InterruptedException ex)
		                  {
		                   Thread.currentThread().interrupt();
                          }

                    printProgBar(100);
		            System.out.println("");
		            System.out.println("HDFS connectivity was successful.");
                    System.out.println("");

                    if (OS.length() >= 7 && OS.substring(0,7).toLowerCase().equals("windows"))
                       ;
                    else
                       {
                        IOUtils.closeStream(inputStream);
                        IOUtils.closeStream(outputStream);
                       }
	           	   }

                System.exit(1);
               }

        public static void printProgBar(int percent)
               {
                StringBuilder bar = new StringBuilder("[");

                for (int i = 0; i < 50; i++)
                    {
                     if ( i < (percent/2))
                        {
                         bar.append("=");
                        }
                     else if ( i == (percent/2))
                             {
                              bar.append(">");
                             }
                          else
                             {
                              bar.append(" ");
                             }
                    }

                bar.append("]   " + percent + "%     ");
                System.out.print("\rWriting: " + bar.toString());
               }
       }
