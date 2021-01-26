import java.util.Scanner;
public class Assignment4
{
   static int count;
   public static void main(String args[])
   {
      Scanner s=new Scanner(System.in);
      System.out.println("Please enter the input: ");
      String in=s.nextLine();
      String array[]=in.split(" ");
      int length=array.length;
      count=length-1;
      int a[]=new int[count];
      int target=Integer.parseInt(array[0]);
      for(int i=1;i<length;i++)
      {
         int n=Integer.parseInt(array[i]);
         a[i-1]=n;
      }
      int[] b=new int[count];
      knapsack(target,a,b,0);
   }
   static void knapsack(int target,int[] a,int[] b,int index)
   {
      if(index==count)
      {
         return;
      }
      target=target-a[index];
      if(target==0)
      {
         b[index]=a[index];
         for(int i=0;i<b.length;i++)
         {
            int n=b[i];
            if(n!=0)
            {
               System.out.print(n+" ");
            }
         }
         System.out.println();
         b[index]=0;
         target=target+a[index];
         knapsack(target,a,b,++index);
         return;
      }
      if(target<0)
      {
         target=target+a[index];
         knapsack(target,a,b,++index);
         return;
      }
      b[index]=a[index];
      knapsack(target,a,b,++index);
      b[index-1]=0;
      target=target+a[index-1];
      knapsack(target,a,b,index);
   }
}