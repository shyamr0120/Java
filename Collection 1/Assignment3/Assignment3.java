import java.util.Scanner;
public class Assignment3
{
   public static void main(String args[])
   {
      Scanner s = new Scanner(System.in);
      int[][] a = new int[3][3];
      int[][] b = new int[3][3];
      System.out.println("Enter 18 numbers: ");
      for(int i=0;i<a.length;i++)
      {
         for(int j=0;j<a.length;j++)
         {
            a[i][j]=s.nextInt();
         }
      }
      for(int i=0;i<b.length;i++)
      {
         for(int j=0;j<b.length;j++)
         {
            b[i][j]=s.nextInt();
         }
      }
      System.out.println();
      boolean equals = Strict.equals(a,b);
      if(equals==true)
      {
         System.out.println("The two arrays are strictly identical.");
      }
      else
      {
         System.out.println("The two arrays are not strictly identical.");
      }
      System.out.println();
      int howmany = Strict.howmany(a,b);
      System.out.println("There are "+howmany+" cell values that are identical in the two arrays.");
      System.out.println();
      int diagonal = Strict.diagonal(a,b);
      System.out.println("There are "+diagonal+" identical cell values along the diagonal from the two arrays.");
      System.out.println();
      double average = Strict.average(a,b);
      System.out.printf("The avearge of all the cell values from the arrays is %.2f", average);
      System.out.println();
      System.out.println();
      System.out.println("The odd values in each array are:");
      Strict.display(a,b);
      System.out.println();
      boolean silly = Strict.silly(a,b);
      if(silly==true)
      {
         System.out.println("The silly method returns true.");
      }
      else
      {
         System.out.println("The silly method returns false.");
      }
   }
}
class Strict
{
   public static boolean equals(int[][] m1, int[][] m2)
   {
      for(int i=0;i<m1.length;i++)
      {
         for(int j=0;j<m1.length;j++)
         {
            if(m1[i][j]!=m2[i][j])
            {
               return false;
            }
         }
      }
      return true;
   }
   public static int howmany(int[][] m1, int[][] m2)
   {
      int n=0;
      for(int i=0;i<m1.length;i++)
      {
         for(int j=0;j<m1.length;j++)
         {
            if(m1[i][j]==m2[i][j])
            {
               n++;
            }
         }
      }
      return n;
   }
   public static int diagonal(int[][] m1, int[][] m2)
   {
      int n=0;
      for(int i=0;i<m1.length;i++)
      {
         if(m1[i][i]==m2[i][i])
         {
            n++;
         }
      }
      return n;
   }
   public static double average(int[][] m1, int[][] m2)
   {
      double sum=0.0;
      for(int i=0;i<m1.length;i++)
      {
         for(int j=0;j<m1.length;j++)
         {
            sum = sum+m1[i][j]+m2[i][j];
         }
      }
      sum = sum/18;
      return sum;
   }
   public static void display(int[][] m1, int[][] m2)
   {
      for(int i=0;i<m1.length;i++)
      {
         for(int j=0;j<m1.length;j++)
         {
            if(m1[i][j]%2!=0)
            {
               System.out.print(m1[i][j]);
               if(m1[i][j]>9)
               {
                  System.out.print("   ");
               }
               else
               {
                  System.out.print("    ");
               }
            }
            else
            {
               System.out.print("     ");
            }
         }
         System.out.println();
      }
      System.out.println();
      for(int i=0;i<m2.length;i++)
      {
         for(int j=0;j<m2.length;j++)
         {
            if(m2[i][j]%2!=0)
            {
               System.out.print(m2[i][j]);
               if(m2[i][j]>9)
               {
                  System.out.print("   ");
               }
               else
               {
                  System.out.print("    ");
               }
            }
            else
            {
               System.out.print("     ");
            }
         }
         System.out.println();
      }
   }
   public static boolean silly(int[][] m1, int[][] m2)
   {
      for(int i=0;i<m1.length;i++)
      {
         for(int j=0;j<m1.length;j++)
         {
            if(m1[i][j]>1&&m1[i][j]<=10)
            {
              
            }
            else
            {
               return false;
            }
            if(m2[i][j]>1&&m2[i][j]<=10)
            {
               continue;
            }
            else
            {
               return false;
            }
         }
      }
      return true;
   }
}
