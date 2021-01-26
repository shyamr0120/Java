import java.util.Scanner;
import java.text.DecimalFormat;
public class Assignment1
{
   public static void main(String[] args)
   {
      Scanner s = new Scanner(System.in);
      System.out.print("Enter the driving distance: ");
      System.out.println();
      double miles = s.nextDouble();
      System.out.print("Enter miles per gallon: ");
      System.out.println();
      double mpg = s.nextDouble();
      System.out.print("Enter price per gallon: ");
      System.out.println();
      double price = s.nextDouble();
      double total_cost = (miles/mpg)*price;
      DecimalFormat df = new DecimalFormat("##0.00");
      System.out.println("The cost of driving is $"+ df.format(total_cost));
   }
}