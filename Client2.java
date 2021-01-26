import java.io.*;
import java.net.*;
import java.util.Scanner;

class Client2
{
   static String OUTPUT = "";
   public static void main(String argv[]) throws Exception
   {
      if(argv.length > 0)
      {
         String hostname = argv[0];
      	
      	//Setting up Scanners to get user's inputs
         Scanner userInput = new Scanner(System.in);
         Scanner numberOfThreads = new Scanner(System.in);
         
      	//Create the Socket
         Socket clientSocket = new Socket(hostname, 2541);
      	
      	//Send user input to the Server
         PrintWriter outputStream = new PrintWriter(clientSocket.getOutputStream(), true);
      	
      	//Get Server output 
         BufferedReader inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      	
         while(true)
         {
            String choice = "0";
            int num = 1;
            double avgTime = 0;
            int counter = 0;
            boolean check = true;
                    	
         	//Menu
            System.out.println("1. Host current Date and Time");
            System.out.println("2. Host uptime");
            System.out.println("3. Host memory use");
            System.out.println("4. Host Netstat");
            System.out.println("5. Host current users");
            System.out.println("6. Host running processes");
            System.out.println("7. Quit");
            
            //Get user Input
            choice = userInput.nextLine();
            
            if(choice.equals("1")||choice.equals("2")||choice.equals("3")||choice.equals("4")||choice.equals("5")||choice.equals("6")){
               System.out.println("Enter Number of Threads");
               //Get number of threads
               num = numberOfThreads.nextInt();
            }else if(choice.equals("7")){
               System.out.println("Program is exiting");
            }
            else{
               System.out.println("Invalid Input");
            }
            
            if(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")
            		|| choice.equals("5") || choice.equals("6")){
                  
                  threadTime time = new threadTime(num);
   	            ClientThreads thread[] = new ClientThreads[num];//CHANGE TO 75 to see if times change
   	            	
   	   				for(int i = 0; i < num; i++) {
     	   					// Creates a new thread
   	   					thread[i] = new ClientThreads(outputStream, inputStream, choice, time);
   	   					thread[i].start();                   	
   	   				}
   	   				
   	   				for(int i = 0; i < num; i++){   					
   	   					try{                       
   	   						thread[i].join();                         
   	   					} catch(InterruptedException e) {
       	   						System.out.println(e);
   	   					} 
   	                  counter = i + 1;                
   	                  if(num == 1){
     	                     System.out.println(thread[i].getResults());
   	                  }
   	   					if ((counter % 5 == 0) || (counter == 1) ) {
   	   						avgTime = time.getAverage(counter); 
                          if (check){   //what is this for?
                              System.out.println(OUTPUT);
                              check = false;
                           }
   	   						System.out.println("Average time of " + counter + " thread(s): " + avgTime);
   	   					}
   				      }
            }else if(choice.equals("7")) {
            
               outputStream.println(choice);
               System.exit(0);
               break;
            }
            else{
               System.out.println("Invalid Input");
            }
         }
         clientSocket.close();
         outputStream.close();
         inputStream.close();
         numberOfThreads.close();
         userInput.close();
      }
      else
      {
         System.out.println("No arguments given");
      }
   }	
}

class ClientThreads extends Thread {
   private PrintWriter outputStream;
   private BufferedReader inputStream;
   private String choice;
   private threadTime time;
   private String allResults;

   public ClientThreads(PrintWriter os, BufferedReader is, String c, threadTime t){
      this.outputStream = os;
      this.inputStream = is;
      this.choice =  c;
      this.time = t;
   }

   public void run(){
      long totalTime;
		long startTime;
		long endTime;
		
		startTime = System.currentTimeMillis();
		
      try {
         outputStream.println(choice);
      } 
      catch (Exception e) {
         e.printStackTrace();
      }
		
	try {
			String allResults = inputStream.readLine();
         Client2.OUTPUT = allResults;//diff
		   endTime = System.currentTimeMillis();
			totalTime = endTime - startTime;
			time.add(totalTime);
		} catch(IOException e) {
			System.out.println(e);
		}
      return;
   }
   
   public String getResults(){
      return allResults;
   }
}

class threadTime {
	long[] times;
   int counter;

	public threadTime(int n) {
      times = new long[n];
		counter = 0;
	}

	public synchronized void add (long t) {
		times[counter++] = t;
	}
	
	
	public double getAverage (int num) {
		double avg = 0;
		
		for (int i = 0; i < num; i++)
			avg += (double)times[i];
	
		   avg = avg / (double)times.length;
		
		return avg;
	}	
}