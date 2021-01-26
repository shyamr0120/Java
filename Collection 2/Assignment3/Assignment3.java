import java.util.*;
class Link
   {
   public int dData;            
   public Link next;              
// -------------------------------------------------------------
   public Link(int dd)           
      { dData = dd; }
// -------------------------------------------------------------
   public void displayLink()     
      { System.out.print(dData + " "); }
   } 
////////////////////////////////////////////////////////////////
class LinkList
   {
   private Link current;
   private Link first;
// -------------------------------------------------------------
   public LinkList()            
      { first = null; }          
// -------------------------------------------------------------
   public boolean isEmpty()      
      { return (first==null); }
// -------------------------------------------------------------
   public void create(int dd) 
      {
         Link firstLink = new Link(1); 
         first = firstLink;
         current=first;                         
         for(int i=2;i<=dd;i++)
         {
            Link newLink = new Link(i);
            current.next=newLink;       
            current = newLink;
         }
         current.next=first;
         current=first;
         if(current.next==current)
         {
            current.next=null;
         }            
      }
// -------------------------------------------------------------
public int find(int key, int counter)      
      { 
      Link search=current;                          
      while(counter!=0)        
         {
         if(search.dData==key)
         {
            current=search;
            return 1;
         }
         if(search.next == null)   
            return 0;                
         else                            
            search = search.next;
            counter--;
         }
         return 0;
      }
// -------------------------------------------------------------
   public void delete(int pass)     
      {                           
          while(current.next!=null&&current.next!=current)
          {
               for(int i=1;i<pass;i++)
               {
                  current=current.next;
               }
               Link temp = current;
               current=current.next;
               current=current.next;
               temp.next=current;
            }
            current.next=null;       
      }
// -------------------------------------------------------------
   public void displayList()
      {
      Link print = current;       
      while(print != null)  
         {
         print.displayLink();  
         print = print.next; 
         }
      System.out.println("");
      }
// -------------------------------------------------------------
}
public class Assignment3
   {
   public static void main(String[] args)
      {
      LinkList link = new LinkList(); // make stack
      
      String exit="";
      Scanner s = new Scanner(System.in);
      while(exit.equals("stop")==false)
      {
         
         int a[]=new int[3];
         for(int i=0;i<3;i++)
         {
               try
               {
               a[i]=s.nextInt();
               }
               catch(Exception e)
               {
                  if(s.next().equals("stop"))
                  {
                     exit="stop";
                     break;
                  }
                  else
                  {
                     System.out.println("Invalid Input");
                     exit="continue";
                     break;
                  }
               }
         }
         if(exit.equals("stop"))
         {
            break;
         }
         if(exit.equals("continue"))
         {
            exit="";
            continue;
         }
         link.create(a[0]);
         int check = link.find(a[1],a[0]);
         if(check==0)
         {
            System.out.println("Invalid Input");
            continue;
         }
         if(a[2]<=0)
         {
            System.out.println("Invalid Input");
            continue;
         }
         link.delete(a[2]);
         link.displayList();
        }
      }  
   }  