/*Shyam Rajendren*/
import java.util.*;
import java.io.*;
public class Assignment4
{
   private static int num=-1;
   public static void main(String[] args) throws FileNotFoundException
   {
      ArrayList<vehicle> list = new ArrayList<vehicle>();
      String line ="";
      Scanner input = new Scanner(System.in);
      try
      {
         input = new Scanner(new File(args[0]));
      }
      catch(FileNotFoundException e)
      {
         System.out.print("File Not Found");
         System.exit(1);
      }
      while(input.hasNext())
      {
         num++;
         int i=0;
         String data[] = new String[10];
         line=input.nextLine();
         while(!(line.trim().length()==0) && input.hasNext())
         {
            data[i]=line;
            line=input.nextLine();
            i++;
         }
         data[i]=line;
         if(data[0].equalsIgnoreCase("vehicle"))
         {
            list.add(new vehicle(data[1],data[2],data[3],data[4]));
         }
         else if(data[0].equalsIgnoreCase("car"))
         {
            list.add(new car(data[1],data[2],data[3],data[4],data[5],data[6]));
         }
         else if(data[0].equalsIgnoreCase("american car"))
         {
            list.add(new american_car(data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8]));
         }
         else if(data[0].equalsIgnoreCase("foreign car"))
         {
            list.add(new foreign_car(data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8]));
         }
         else if(data[0].equalsIgnoreCase("truck"))
         {
            list.add(new truck(data[1],data[2],data[3],data[4],data[5],data[6],data[7]));
         }
         else if(data[0].equalsIgnoreCase("bicycle"))
         {
            list.add(new bicycle(data[1],data[2],data[3],data[4],data[5]));
         }
      }
      printAll(list);
      sort(list);
      numOfRecords();
      bicycle_trucks(list);
      vehicles987(list);
   }
   public static void printAll(ArrayList<vehicle> vlist)
   {
      for(int i=0;i<=num;i++)
      {
         if(vlist.get(i) instanceof foreign_car)
         {
            System.out.println("foreign car");
         }
         else if(vlist.get(i) instanceof american_car)
         {
            System.out.println("american car");
         }
         else if(vlist.get(i) instanceof car)
         {
            System.out.println("car");
         }
         else if(vlist.get(i) instanceof truck)
         {
            System.out.println("truck");
         }
         else if(vlist.get(i) instanceof bicycle)
         {
            System.out.println("bicycle");
         }
         else if(vlist.get(i) instanceof vehicle)
         {
            System.out.println("vehicle");
         }
         System.out.println(vlist.get(i).toString());
      }
   }
   public static void sort(ArrayList <vehicle> vlist)
   {
      Collections.sort(vlist, new Comparator<vehicle>()
      {
         public int compare(vehicle v1, vehicle v2)
         {
            return  v1.getEmail().compareTo(v2.getEmail());
         }
      });
      System.out.println("=============");
      System.out.println(" SORTED LIST ");
      System.out.println("=============");
      printAll(vlist);
   }
   public static void numOfRecords()
   {
      System.out.println("The number of records are: "+(num+1));
      System.out.println();
   }
   public static void bicycle_trucks(ArrayList <vehicle> vlist)
   {
      System.out.println("==========================");
      System.out.println(" ONLY BICYCLES AND TRUCKS ");
      System.out.println("==========================");
      for(int i=0;i<=num;i++)
      {
         if(vlist.get(i) instanceof truck)
         {
            System.out.println("truck");
            System.out.println(vlist.get(i).toString());
         }
         else if(vlist.get(i) instanceof bicycle)
         {
            System.out.println("bicycle");
            System.out.println(vlist.get(i).toString());
         }
      }
   }
   public static void vehicles987(ArrayList <vehicle> vlist)
   {
      System.out.println("===========================");
      System.out.println(" VEHICLES IN AREA CODE 987 ");
      System.out.println("===========================");
      for(int i=0;i<=num;i++)
      {
         String check=vlist.get(i).getPhone();
         if("(987)".equals(check.substring(0,5)))
         {
            if(vlist.get(i) instanceof foreign_car)
            {
               System.out.println("foreign car");
            }
            else if(vlist.get(i) instanceof american_car)
            {
               System.out.println("american car");
            }
            else if(vlist.get(i) instanceof car)
            {
               System.out.println("car");
            }
            else if(vlist.get(i) instanceof truck)
            {
               System.out.println("truck");
            }
            else if(vlist.get(i) instanceof bicycle)
            {
               System.out.println("bicycle");
            }
            else if(vlist.get(i) instanceof vehicle)
            {
               System.out.println("vehicle");
            }
            System.out.println(vlist.get(i).toString());
         }
      }
   }
}
class vehicle
{
   private String owner_name="";
   private String address="";
   private String phone="";
   private String email="";
   public vehicle(String o,String a,String p,String e)
   {
      setOwner_Name(o);
      setAddress(a);
      setPhone(p);
      setEmail(e);
   }
   public void setOwner_Name(String o)
   {
      owner_name=o;
   }
   public String getOwner_Name()
   {
      return owner_name;
   }
   public void setAddress(String a)
   {
      address=a;
   }
   public String getAddress()
   {
      return address;
   }
   public void setPhone(String p)
   {
      phone=p;
   }
   public String getPhone()
   {
      return phone;
   }
   public void setEmail(String e)
   {
      email=e;
   }
   public String getEmail()
   {
      return email;
   }
   public String toString()
   {
      return String.format(owner_name+"%n"+address+"%n"+phone+"%n"+email+"%n");
   }
}
class car extends vehicle
{
   private boolean convertible=false;
   private String color="";
   public car(String o,String a,String p,String e,String con,String c)
   {
      super(o,a,p,e);
      setConvertible(con);
      setColor(c);
   }
   public void setConvertible(String con)
   {
      convertible=Boolean.parseBoolean(con);
   }
   public boolean getConvertible()
   {
      return convertible;
   }
   public void setColor(String c)
   {
      color=c;
   }
   public String getColor()
   {
      return color;
   }
   public String toString()
   {
      System.out.print(super.toString());
      return String.format(convertible+"%n"+color+"%n");
   }
}
class american_car extends car
{
   private boolean detroit=false;
   private boolean union_shop=false;
   public american_car(String o,String a,String p,String e,String con,String c,String d,String us)
   {
      super(o,a,p,e,con,c);
      setDetroit(d);
      setUnion_Shop(us);
   }
   public void setDetroit(String d)
   {
      detroit=Boolean.parseBoolean(d);
   }
   public boolean getDetroit()
   {
      return detroit;
   }
   public void setUnion_Shop(String us)
   {
      union_shop=Boolean.parseBoolean(us);
   }
   public boolean getUnion_Shop()
   {
      return union_shop;
   }
   public String toString()
   {
      System.out.print(super.toString());
      return String.format(detroit+"%n"+union_shop+"%n");
   }
}
class foreign_car extends car
{
   private String country_of_manufacture="";
   private float import_duty=0;
   public foreign_car(String o,String a,String p,String e,String con,String c,String com,String id)
   {
      super(o,a,p,e,con,c);
      setCountry_of_Manufacture(com);
      setImport_Duty(id);
   }
   public void setCountry_of_Manufacture(String com)
   {
      country_of_manufacture=com;
   }
   public String getCountry_of_Manufacture()
   {
      return country_of_manufacture;
   }
   public void setImport_Duty(String id)
   {
      import_duty=Float.parseFloat(id);
   }
   public float getImport_Duty()
   {
      return import_duty;
   }
   public String toString()
   {
      System.out.print(super.toString());
      return String.format(country_of_manufacture+"%n"+import_duty+"%n");
   }
}
class truck extends vehicle
{
   private float num_of_tons=0;
   private float cost_of_trucks=0;
   private String date="";
   public truck(String o,String a,String p,String e,String not,String cot,String d)
   {
      super(o,a,p,e);
      setNum_Of_Tons(not);
      setCost_Of_Trucks(cot);
      setDate(d);
   }
   public void setNum_Of_Tons(String not)
   {
      num_of_tons=Float.parseFloat(not);
   }
   public float getNum_Of_Tons()
   {
      return num_of_tons;
   }
   public void setCost_Of_Trucks(String cot)
   {
      cost_of_trucks=Float.parseFloat(cot);
   }
   public float getCost_Of_Trucks()
   {
      return cost_of_trucks;
   }
   public void setDate(String d)
   {
      date=d;
   }
   public String getDate()
   {
      return date;
   }
   public String toString()
   {
      System.out.print(super.toString());
      return String.format(num_of_tons+"%n"+cost_of_trucks+"%n"+date+"%n");
   }
}
class bicycle extends vehicle
{
   private int num_of_speeds=0;
   public bicycle(String o,String a,String p,String e,String nos)
   {
      super(o,a,p,e);
      setNum_Of_Speeds(nos);
   }
   public void setNum_Of_Speeds(String nos)
   {
      num_of_speeds=Integer.parseInt(nos);
   }
   public int getNum_Of_Speeds()
   {
      return num_of_speeds;
   }
   public String toString()
   {
      System.out.print(super.toString());
      return String.format(num_of_speeds+"%n");
   }
}