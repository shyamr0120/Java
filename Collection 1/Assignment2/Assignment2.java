import java.util.Scanner;
public class Assignment2
{
   public static void main(String[] args)
   {
      Scanner s = new Scanner(System.in);
      System.out.print("Enter a single odd positive integer: ");
      System.out.println();
      int num = s.nextInt();
      double sum1 = 0.0;
      double sum2 = 0.0;
      for (int i=3;i<=num;i+=2)
      {
         double frac1 = ((double)(i-2)/i);
         sum1 = sum1+frac1;
      }
      System.out.printf("The sum of the first series is %.12f.", sum1);
      System.out.println();
      for (int j=1;j<=num;j++)
      {
         double frac2 = 1.0/(2*j-1);
         if(j%2==0)
         {
            sum2 = sum2-frac2;
         }
         else
         {
            sum2 = sum2+frac2;
         }
       }
       sum2 = sum2*4;
       System.out.printf("The sum of the second series is %.12f.", sum2);
   }
}