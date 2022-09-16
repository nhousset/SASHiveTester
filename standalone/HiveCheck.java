/************************************************************/
/* HiveCheck                                                */
/*                                                          */
/* The purpose of this code is to verify Hive connectivity  */
/* to a Hadoop cluster.                                     */
/*                                                          */
/* Execution:                                               */
/* java HiveCheck <parameters>                              */
/*                                                          */
/* Parameters syntax:                                       */
/* Run "java HiveCheck" without parameters                  */
/************************************************************/
import java.io.*;
import java.util.*;
import java.sql.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class HiveCheck
       {
        public static void main(String[] args) throws SQLException,ClassNotFoundException,InstantiationException,IllegalAccessException
               {

                Driver      hiveDriver = null;
                Connection  con = null;
                String      connection_string = "", sql = "show tables";
                Statement   stmt = null;
                ResultSet   res = null;
                int         table_count = 0, count = 1;
                Properties  props = new Properties();
                Boolean     debug = false;
                PrintStream filterOut = new PrintStream(System.out) {public void println(String l) {if ((!l.startsWith("SLF4J")) && (!l.startsWith("log4j"))) super.println(l);}};System.setErr(filterOut);

                /************************************************************/
                /* Check arguments                                          */
                /*                                                          */
                /* With no arguments, print the help info.                  */
                /************************************************************/
                if (args.length == 0)
                   {
                    System.out.println("");
                    System.out.println("This Java program verifies Hive connectivity to a Hadoop cluster.");
                    System.out.println("");
                    System.out.println("Syntax:");
                    System.out.println("");
                    System.out.println("> java HiveCheck <Authentication type> <Hive server> <Hive service port number> [<Kerberos Principal> | <user> <password>]");
                    System.out.println("");
                    System.out.println("  - <Authentication type>      : Required. SIMPLE, LDAP, or KERBEROS");
                    System.out.println("  - <Hive server>              : Required. Name of the server where the Hive service is running");
                    System.out.println("  - <Hive service port number> : Required. Hive service port number");
                    System.out.println("  - <Kerberos Principal>       : With KERBEROS authentication, the HiveServer2 principal (i.e. hive/_HOST@MY.DOMAIN.COM)");
                    System.out.println("  - <user> <password>          : With LDAP authentication, a valid set of credentials to connect to the cluster");
                    System.out.println("  - <debug flag>               : Whether exceptions should be printed on screen [DEBUG | NODEBUG]. Default is NODEBUG");
                    System.out.println("");
                    System.exit(1);
                   }

                /************************************************************/
                /* Make sure we have enough arguments.                      */
                /************************************************************/
                if (
                    (args.length < 3) |
                    (args.length > 6)
                   )
                   {
                    System.out.println("");
             	    System.out.println("Invalid syntax. Invoke the program without arguments to display help info");
            	    System.exit(1);
                   }

                /************************************************************/
                /* With SIMPLE authentication, make sure we have server and */
                /* port number.                                             */
                /************************************************************/
                if (args[0].equalsIgnoreCase("SIMPLE"))
                   {
                    if (
                        (args.length == 3) |
                        (
                         (args.length == 4) &&
                         (
                          (args[3].equalsIgnoreCase("DEBUG")) |
                          (args[3].equalsIgnoreCase("NODEBUG"))
                         )
                        )
                       )
                       {
                        if (
                            (args.length == 4) &&
                            (args[3].equalsIgnoreCase("DEBUG"))
                           )
                           debug = true;

                        System.out.println("");
                        System.out.println("Connecting to the Hive server using SIMPLE authentication:" );
                        System.out.println("");
                        System.out.println("  - Hive server name         : " + args[1] );
                        System.out.println("  - Hive service port number : " + args[2] );
                        System.out.println("  - Debug mode               : " + debug );
                        System.out.println("");
                       }
	                else
                       {
                        System.out.println("");
                        System.out.println("Invalid syntax. Invoke the program without arguments to display help info");
                        System.exit(1);
                       }
                   }


                /************************************************************/
                /* With KERBEROS authentication we need to have server name */
                /* port number, and a valid principal to connect to Hive.   */
                /************************************************************/
                if (args[0].equalsIgnoreCase("KERBEROS"))
                   {
                    if (
			            (args.length == 4) |
			            (
			             (args.length == 5) &&
			             (
			              (args[4].equalsIgnoreCase("DEBUG")) |
			              (args[4].equalsIgnoreCase("NODEBUG"))
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
                        System.out.println("Connecting to the Hive server using KERBEROS authentication:" );
                        System.out.println("");
                        System.out.println("  - Hive server name         : " + args[1] );
                        System.out.println("  - Hive service port number : " + args[2] );
                        System.out.println("  - Kerberos principal       : " + args[3] );
                        System.out.println("  - Debug mode               : " + debug );
                        System.out.println("");
	                   }
	                else
	                   {
                        System.out.println("");
	                    System.out.println("Invalid syntax. Invoke the program without arguments to display help info");
	                    System.exit(1);
	                   }
                   }

                /************************************************************/
                /* With LDAP authentication, make sure we have server, port */
                /* number, and a set of valid credentials.                  */
                /************************************************************/
                if (args[0].equalsIgnoreCase("LDAP"))
                   {
                    if (
			            (args.length == 5) |
			            (
			             (args.length == 6) &&
			             (
		                  (args[5].equalsIgnoreCase("DEBUG")) |
		                  (args[5].equalsIgnoreCase("NODEBUG"))
		                 )
		                )
		               )
                       {
                        if (
		                    (args.length == 6) &&
	                        (args[5].equalsIgnoreCase("DEBUG"))
			               )
                           debug = true;

                        System.out.println("");
                        System.out.println("Connecting to the Hive server using LDAP authentication:" );
                        System.out.println("");
                        System.out.println("  - Hive server name         : " + args[1] );
                        System.out.println("  - Hive service port number : " + args[2] );
                        System.out.println("  - LDAP user                : " + args[3] );
                        System.out.println("  - Password                 : " + args[4] );
                        System.out.println("  - Debug mode               : " + debug );
                        System.out.println("");
	                   }
	                else
	                   {
                        System.out.println("");
	                    System.out.println("Invalid syntax. Invoke the program without arguments to display help info");
	                    System.exit(1);
	                   }
                   }

                /************************************************************/
                /* Build a connection string given the authentication type  */
                /************************************************************/
                System.setErr(filterOut);

                if (args[0].equalsIgnoreCase("SIMPLE"))
                   connection_string = "jdbc:hive2://" + args[1] + ":" + args[2] + "/default";
                else if (args[0].equalsIgnoreCase("KERBEROS"))
                        connection_string = "jdbc:hive2://" + args[1] + ":" + args[2] + "/default;principal=" + args[3];
                     else if (args[0].equalsIgnoreCase("LDAP"))
                             connection_string = "jdbc:hive2://" + args[1] + ":" + args[2] + "/default;user=" + args[3] + ";password=" + args[4];

                System.out.println( "Full connection string is: " + connection_string );
                System.out.println("");
                hiveDriver = (Driver)Class.forName("org.apache.hive.jdbc.HiveDriver").newInstance();

                /************************************************************/
                /* Set the logging level...                                 */
                /************************************************************/
                Logger rootLogger = Logger.getRootLogger();

                if (!debug)
                   rootLogger.setLevel(Level.OFF);
                else
                   rootLogger.setLevel(Level.ALL);

                /************************************************************/
                /* Running in silent mode...                                */
                /************************************************************/
                if (!debug)
                   {
                    try {
                         con = hiveDriver.connect(connection_string, props);
                        }
                    catch (Exception ex)
                          {
     		               System.out.println("ERROR: Unable to connect to Hive. Verify that server name and port number are correct.");
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
	                con = hiveDriver.connect(connection_string, props);
	               }

                stmt = con.createStatement();

                /************************************************************/
                /* Connect and see if "SHOW TABLES" works                   */
                /************************************************************/
                System.out.println("Running: " + sql);
                res = stmt.executeQuery(sql);

                while (res.next())
                      {
	                   table_count++;

                       printProgBar(count++);
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

		        if (table_count >= 0)
		           {
	                printProgBar(100);
                    System.out.println("");
                    System.out.println("HIVE connectivity was successful.");
                    System.out.println("");
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
                System.out.print("\rReading: " + bar.toString());
               }
     }
