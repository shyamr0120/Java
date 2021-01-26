import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.*;
import java.text.*;

class Server2
{

   public static void main(String argv[]) throws Exception
   {
      //Establishes Connnection
      ServerSocket welcomeSocket = new ServerSocket(2541);
      System.out.println("The server started. To stop it press <CTRL><C>.");      
      Socket connectionSocket = welcomeSocket.accept();
      
      //Declare Variables
      int choice = 0;
      String clientInput;
      boolean exitFlag = false;
           
      BufferedReader inputStream = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
      
      while(!exitFlag){
      
         clientInput = inputStream.readLine();
         choice = Integer.parseInt(clientInput);
         System.out.println(choice);
         switch(choice){
            case 7:
               try{
                  connectionSocket.close();
                  welcomeSocket.close();
                  inputStream.close();
               }catch(Exception ex){
               
               }
               
               exitFlag = true;
               break;
               
            default:    
                  
                 MyThread thread = new MyThread(connectionSocket, choice);
                 thread.start();
                 
                 break;
         }        
      }
   }  
}       

class MyThread extends Thread{
   
   Socket connectionSocket;
   int choice;
   Process process;
   Runtime run;
   BufferedReader br;
   String clientInput, command, line, tempLine;
   
   public MyThread(Socket s, int choice){
      this.connectionSocket = s;
      this.choice = choice;
      this.tempLine = null;
         
   }
   
   public void run(){   
      
      try{
         PrintWriter outputStream = new PrintWriter(connectionSocket.getOutputStream(), true);
           
         switch(choice){

            //Current Date and Time
            case 1:
					run = Runtime.getRuntime();
					command = "date";

					process = run.exec(command);
					br = new BufferedReader(new InputStreamReader(process.getInputStream()));
					tempLine = br.readLine();
					outputStream.println(tempLine);
               System.out.println("Server processing Date/Time");
                
               
               tempLine = "";
               
					break;
            //uptime            	
            case 2:
               run = Runtime.getRuntime();
					command = "uptime";

					process = run.exec(command);
					br = new BufferedReader(new InputStreamReader(process.getInputStream()));
					tempLine = br.readLine();
					outputStream.println(tempLine);
               System.out.println("Server processing Uptime");
               
					tempLine = "";
					break;
               
            //memory use
            case 3:
               run = Runtime.getRuntime();
					command = "free";

					process = run.exec(command);
					br = new BufferedReader(new InputStreamReader(process.getInputStream()));

					while ((line = br.readLine()) != null) 
						tempLine = tempLine + line;

					outputStream.println(tempLine);
               System.out.println("Server processing Memory Use");
               
					tempLine = "";
					break;
            //NETSTAT
            case 4:
               run = Runtime.getRuntime();
					command = "netstat";

					process = run.exec(command);
					br = new BufferedReader(new InputStreamReader(process.getInputStream()));

					while ((line = br.readLine()) != null) 
						tempLine = tempLine + line;

					outputStream.println(tempLine);
               System.out.println("Server processing Netstat");

					tempLine = "";
					break;
               
            //Current Users
            case 5:
               run = Runtime.getRuntime();
					command = "who";

					process = run.exec(command);
					br = new BufferedReader(new InputStreamReader(process.getInputStream()));
					
					while ((line = br.readLine()) != null) 
						tempLine = tempLine + line;

					outputStream.println(tempLine);
               System.out.println("Server processing Current Users");

					tempLine = "";
					break;
               
         //Running Processes
            case 6:
					run = Runtime.getRuntime();
					command = "ps -e";

					process = run.exec(command);
					br = new BufferedReader(new InputStreamReader(process.getInputStream()));

					while ((line = br.readLine()) != null) 
						tempLine = tempLine + line;

					outputStream.println(tempLine);
               System.out.println("Server processing Running Processes");

					tempLine = "";
					break;
         	
            default:
               break;           
         }
      } catch(IOException e){
      } 
   }
}