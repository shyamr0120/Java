import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;
import java.text.*;

class Server2
{

   public static void main(String args[]) throws Exception
   {
      ServerSocket ws = new ServerSocket(4242);
      System.out.println("The server started. Press CTRL C to exit.");      
      Socket cs = ws.accept();
      
      int c = 0;
      String ci;
      boolean exit = false;
           
      BufferedReader is = new BufferedReader(new InputStreamReader(cs.getInputStream()));

      while(!exit)
	{
      
         ci = is.readLine();
         c = Integer.parseInt(ci);
         switch(c)
	{
            case 7:
               try{
                  cs.close();
                  ws.close();
                  is.close();
               }
		catch(Exception e)
		{
               
               }
               
               exit = true;
               break;
               
            default:    
                  
                 CustomThread thread = new CustomThread(cs, c);
                 thread.start();
                 
                 break;
         }        
      }
   }  
}       

class CustomThread extends Thread
{
   
   Socket cs;
   int c;
   Process pro;
   Runtime run;
   BufferedReader br;
   String ci;
   String com;
   String line;
   String temp;
   public CustomThread(Socket s, int cc)
{
      cs = s;
      c = cc;
      temp = null;
         
   }
   
   public void run(){   
      
      try{
         PrintWriter os = new PrintWriter(cs.getOutputStream(), true);
           
         switch(c)
	{

            case 1:
					run = Runtime.getRuntime();
					com = "date";

					pro = run.exec(com);
					br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
					temp = br.readLine();
					os.println(temp);
               System.out.println("Server processing Date/Time");
               System.out.println("Thread ID "+Thread.currentThread().getId()); 
               
               temp = "";
               
					break;          	
            case 2:
               run = Runtime.getRuntime();
					com = "uptime";

					pro = run.exec(com);
					br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
					temp = br.readLine();
					os.println(temp);
               System.out.println("Server processing Uptime");
               System.out.println("Thread ID "+Thread.currentThread().getId());

					temp = "";
					break;
               
            case 3:
               run = Runtime.getRuntime();
					com = "free";

					pro = run.exec(com);
					br = new BufferedReader(new InputStreamReader(pro.getInputStream()));

					while ((line = br.readLine()) != null) 
						temp = temp + line;

					os.println(temp);
               System.out.println("Server processing Memory Use");
              System.out.println("Thread ID "+Thread.currentThread().getId());
 
					temp = "";
					break;
            case 4:
               run = Runtime.getRuntime();
					com = "netstat";

					pro = run.exec(com);
					br = new BufferedReader(new InputStreamReader(pro.getInputStream()));

					while ((line = br.readLine()) != null) 
						temp = temp + line;

					os.println(temp);
               System.out.println("Server processing Netstat");
		System.out.println("Thread ID "+Thread.currentThread().getId());

					temp = "";
					break;
               
            case 5:
               run = Runtime.getRuntime();
					com = "who";

					pro = run.exec(com);
					br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
					
					while ((line = br.readLine()) != null) 
						temp = temp + line;

					os.println(temp);
               System.out.println("Server processing Current Users");
		System.out.println("Thread ID "+Thread.currentThread().getId());

					temp = "";
					break;
               
            case 6:
					run = Runtime.getRuntime();
					com = "ps -e";

					pro = run.exec(com);
					br = new BufferedReader(new InputStreamReader(pro.getInputStream()));

					while ((line = br.readLine()) != null) 
						temp = temp + line;

					os.println(temp);
               System.out.println("Server processing Running Processes");
		System.out.println("Thread ID "+Thread.currentThread().getId());

					temp = "";
					break;
         	
            default:
               break;           
         }
      } catch(Exception e){
      } 
   }
}
