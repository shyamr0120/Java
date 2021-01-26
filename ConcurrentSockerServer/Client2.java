import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client2
{
   public static void main(String args[]) throws Exception
   {
	long totalSum = 0;
      if(args.length > 0)
      {
         String host = args[0];
     
         Scanner ui = new Scanner(System.in);
         Scanner n = new Scanner(System.in);
         System.out.println("Enter port number: ");
	Scanner portNum = new Scanner(System.in);
	int pNum = portNum.nextInt();
         Socket clientSocket = new Socket(host, 4242);
      	
      
         PrintWriter os = new PrintWriter(clientSocket.getOutputStream(), true);
      	
      	 
         BufferedReader is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      	
         while(true)
         {
            String c = "0";
            int num = 1;
                    	
         
            System.out.println("1. Host current Date and Time");
            System.out.println("2. Host uptime");
            System.out.println("3. Host memory use");
            System.out.println("4. Host Netstat");
            System.out.println("5. Host current users");
            System.out.println("6. Host running processes");
            System.out.println("7. Quit");
            
            
            c = ui.nextLine();
            
            if(c.equals("1")||c.equals("2")||c.equals("3")||c.equals("4")||c.equals("5")||c.equals("6")){
               System.out.println("Enter Number of Requests");
              
               num = n.nextInt();

            	}
		else if(c.equals("7"))
		{
               System.out.println("Program is exiting");
            	}
            
            if(c.equals("1") || c.equals("2") || c.equals("3") || c.equals("4")
            		|| c.equals("5") || c.equals("6"))
		{
                  
   	            Clients threads[] = new Clients[num];

					 System.out.println();
                                System.out.println("==========================================");
                                System.out.println("OUTPUT");
                                System.out.println("==========================================");
	
   	   				for(int i = 0; i < num; i++) {
     	   					
     	   				

   	   					threads[i] = new Clients(os, is, c);
   	   					threads[i].start();
						

   	   				}
   	   				
   	   				for(int i = 0; i < num; i++){   					
   	   					try{                       
   	   						threads[i].join();
							  System.out.println();
					System.out.println("Thread ID: "+threads[i].id);
					System.out.println();
                                         System.out.println(threads[i].result);
                                        System.out.println();
					totalSum = totalSum +  threads[i].totalTime;
					System.out.println("Turn-Around Time: "+threads[i].totalTime);
					System.out.println();
                                        System.out.println("==========================================");
                                System.out.println();
                         
   	   					} catch(Exception e) {
       	   						System.out.println(e);
   	   					} 
   				      }
			System.out.println("Total Turn-Around Time: "+totalSum);
			System.out.println("Average Turn-Around Time: "+(double)(totalSum/num));
			System.out.println();
			totalSum = 0;
            }else if(c.equals("7")) {
            
               os.println(c);
               System.exit(0);
               break;
            }
            else{
               System.out.println("Invalid Input");
            }
         }
         clientSocket.close();
         os.close();
         is.close();
         n.close();
         ui.close();
      }
      else
      {
         System.out.println("No arguments given");
      }
   }	
}

class Clients extends Thread {
   PrintWriter os;
   BufferedReader is;
   String c;
	public String result;
	public long totalTime;
	public long id;

   public Clients(PrintWriter o, BufferedReader i, String cc){
      os = o;
      is = i;
      c =  cc;
   }

   public void run(){
		long startTime;
		long endTime;
		
		startTime = System.currentTimeMillis();
		id = Thread.currentThread().getId();
      try {
         os.println(c);
      } 
      catch (Exception e) {
         e.printStackTrace();
      }
		
	try {
			result = is.readLine();
		   endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
		} catch(Exception e) {
			System.out.println(e);
		}
      return;
   }
}
