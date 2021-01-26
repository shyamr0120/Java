// hash.java
// demonstrates hash table with linear probing
// to run this program: C:>java HashTableApp
import java.io.*;
import java.util.*;
////////////////////////////////////////////////////////////////
class DataItem
   {                                // (could have more data)
   private String iData;               // data item (key)
//--------------------------------------------------------------
   public DataItem(String ii)          // constructor
      { iData = ii; }
//--------------------------------------------------------------
   public String getKey()
      { return iData; }
//--------------------------------------------------------------
   }  // end class DataItem
////////////////////////////////////////////////////////////////
class HashTable
   {
   private DataItem[] A;
   private DataItem[] B;
   private int arraySize;
   private int[] probeLength;
   private int[] probeLength2;
   private int number;
   private double aplS;
   private double aplF;
   private int S;
   private int F;
   private DataItem nonItem;        // for deleted items
// -------------------------------------------------------------
   public HashTable(int size, int n)       // constructor
      {
      arraySize = size;
      number = n;
      probeLength = new int[arraySize];
      probeLength2 = new int[arraySize];
      A = new DataItem[arraySize];
      B = new DataItem[arraySize];
      aplS = 0;
      aplF = 0;
      S = 0;
      F = 0;
      nonItem = new DataItem("X");   // deleted item key is -1
      }
// -------------------------------------------------------------
   public void displayTableA()
      {
      double apl = 0;
      System.out.println("            A");
      System.out.println("index    string                                            probe length for insertion");
      for(int j=0; j<arraySize; j++)
      {
         if(A[j] != null)
         {
            String string = A[j].getKey();
            System.out.print(" "+j);
            System.out.print("       "+string);
            for(int i=0;i<(51-string.length());i++)
            {
               System.out.print(" ");
            }
            apl = apl + probeLength[j];
            System.out.print(probeLength[j]);
            System.out.println();
         }
      }
      System.out.println();
      apl = apl/number;
      System.out.printf("Average Probe Length:                %.2f",apl);
      System.out.println();
      }
   public void displayTableB()
   {
   double apl = 0;
   System.out.println("            B");
   System.out.println("index    string                                            probe length for insertion");
   for(int j=0; j<arraySize; j++)
   {
      if(B[j] != null)
      {
         String string = B[j].getKey();
         System.out.print(" "+j);
         System.out.print("       "+string);
         for(int i=0;i<(51-string.length());i++)
         {
            System.out.print(" ");
         }
         apl = apl + probeLength[j];
         System.out.print(probeLength[j]);
         System.out.println();
      }
   }
   System.out.println();
   apl = apl/number;
   System.out.printf("Average Probe Length:                %.2f",apl);
   }

// -------------------------------------------------------------
   public int hashFunc(String key)
      {
         int hashVal=0;
         for(int j=0; j<key.length(); j++)
         {
            int letter = key.charAt(j);
            hashVal = (hashVal*26 + letter)%arraySize;
         }
         //System.out.println(hashVal);
         return hashVal;
      }
// -------------------------------------------------------------
   public void insert(DataItem item) // insert a DataItem
   // (assumes table not full)
      {
      int probe = 1;
      String key = item.getKey();      // extract key
      int hashVal = hashFunc(key);  // hash the key
                                    // until empty cell or -1,
      while(A[hashVal] != null &&
                      A[hashVal].getKey() != "X")
         {
         ++hashVal;                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         probe++;
         }
      probeLength[hashVal] = probe;
      A[hashVal] = item;    // insert item
      
      probe = 1;
      hashVal = hashFunc(key);
      int ohashVal = hashVal;
      int count = 1;
      while(B[hashVal] != null &&
                      B[hashVal].getKey() != "X")
         {
         hashVal = ohashVal + (count*count);                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         probe++;
         count++;
         }
      probeLength2[hashVal] = probe;
      B[hashVal] = item;    // insert item
      }  // end insert()
// -------------------------------------------------------------
   public void deleteA(String key)  // delete a DataItem
      {
      int hashVal = hashFunc(key);  // hash the key
      
      int probe = 1;
      //System.out.println(A[hashVal].getKey());
      while(A[hashVal] != null)  // until empty cell,
         {                               // found the key?
         if((A[hashVal].getKey()).equals(key))
            {
            S++;
            DataItem temp = A[hashVal]; // save item
            A[hashVal] = nonItem;       // delete item
            aplS = aplS+probe;
            System.out.print(key);
            for(int i=0;i<(51-key.length());i++)
            {
               System.out.print(" ");
            }
            System.out.print("yes               ");
            System.out.print(probe);
            System.out.println();
            return;
            }
            probe++;
         ++hashVal;                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         }
         F++;
         System.out.print(key);
         for(int i=0;i<(60-key.length());i++)
         {
            System.out.print(" ");
         }
         System.out.print("yes                                 ");
         System.out.print(probe);
         System.out.println();
         aplF = aplF+probe;
         return;
      }  // end delete()
   public void deleteB(String key)  // delete a DataItem
      {
      int hashVal = hashFunc(key);  // hash the key
      
      int probe = 1;
      int count = 1;
      //System.out.println(A[hashVal].getKey());
      while(B[hashVal] != null)  // until empty cell,
         {                               // found the key?
         if((B[hashVal].getKey()).equals(key))
            {
            S++;
            DataItem temp = B[hashVal]; // save item
            B[hashVal] = nonItem;       // delete item
            aplS = aplS+probe;
            System.out.print(key);
            for(int i=0;i<(51-key.length());i++)
            {
               System.out.print(" ");
            }
            System.out.print("yes               ");
            System.out.print(probe);
            System.out.println();
            return;
            }
            probe++;
         hashVal = hashVal + (count*count);                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         count++;
         }
         F++;
         System.out.print(key);
         for(int i=0;i<(60-key.length());i++)
         {
            System.out.print(" ");
         }
         System.out.print("yes                                 ");
         System.out.print(probe);
         System.out.println();
         aplF = aplF+probe;
         return;
      }  // end delete()

      public double get_aplS()
      {
         aplS = aplS/S;
         return aplS;
      }
      public double get_aplF()
      {
         aplF = aplF/F;
         return aplF;
      }
      public void resetS()
      {
         aplS = 0;
         S = 0;
      }
      public void resetF()
      {
         aplF = 0;
         F = 0;
      }

// -------------------------------------------------------------
   public void findA(String key)    // find item with key
      {
      int hashVal = hashFunc(key);  // hash the key
      
      int probe = 1;
      //System.out.println(A[hashVal].getKey());
      while(A[hashVal] != null)  // until empty cell,
         {                               // found the key?
         if((A[hashVal].getKey()).equals(key))
            {
            S++;
            aplS = aplS+probe;
            System.out.print(key);
            for(int i=0;i<(51-key.length());i++)
            {
               System.out.print(" ");
            }
            System.out.print("yes               ");
            System.out.print(probe);
            System.out.println();
            return;
            }
            probe++;
         ++hashVal;                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         }
         F++;
         System.out.print(key);
         for(int i=0;i<(60-key.length());i++)
         {
            System.out.print(" ");
         }
         System.out.print("yes                                 ");
         System.out.print(probe);
         System.out.println();
         aplF = aplF+probe;
         return;
      }
   public void findB(String key)    // find item with key
      {
      int hashVal = hashFunc(key);  // hash the key
      
      int probe = 1;
      int count = 1;
      //System.out.println(A[hashVal].getKey());
      while(B[hashVal] != null)  // until empty cell,
         {                               // found the key?
         if((B[hashVal].getKey()).equals(key))
            {
            S++;
            aplS = aplS+probe;
            System.out.print(key);
            for(int i=0;i<(51-key.length());i++)
            {
               System.out.print(" ");
            }
            System.out.print("yes               ");
            System.out.print(probe);
            System.out.println();
            return;
            }
            probe++;
         hashVal = hashVal + (count*count);                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         count++;
         }
         F++;
         System.out.print(key);
         for(int i=0;i<(60-key.length());i++)
         {
            System.out.print(" ");
         }
         System.out.print("yes                                 ");
         System.out.print(probe);
         System.out.println();
         aplF = aplF+probe;
         return;
      }

// -------------------------------------------------------------
   }  // end class HashTable
////////////////////////////////////////////////////////////////
public class n01185608
   {
   public static void main(String[] args) throws IOException
      {
      DataItem aDataItem;
      int size = 0;
      int n = 0;
      String aKey;
                                    // get sizes
      //System.out.print("Enter size of hash table: ");
      //System.out.print("Enter initial number of items: ");
                                    // make table
      
      Scanner input = new Scanner(System.in);
      Scanner input2 = new Scanner(System.in);
      Scanner input3 = new Scanner(System.in);
      try
      {
         input = new Scanner(new File(args[0]));
         input2 = new Scanner(new File(args[1]));
         input3 = new Scanner(new File(args[2]));
      }
      catch(FileNotFoundException e)
      {
         System.out.print("File Not Found");
         System.exit(1);
      }
      
      String file = "";
      String file2 = "";
      String file3 = "";
      
      while(input.hasNext())
      {
         file = file + input.nextLine() + " ";
         n++;
      }
      
      size=2*n;
      boolean check = false;
      while(check!=true)
      {
         check = true;
         size++;
         for(int i=2; i<n; i++)
         {
            if(n%i==0)
            {
               check = false;
               break;
            }
         }
      }
      //System.out.println(n);
      
      HashTable theHashTable = new HashTable(size,n);
      
      String word = "";
      for(int i=0;i<file.length();i++)
      {
         char c = file.charAt(i);
         if(c==' ')
         {
            aDataItem = new DataItem(word);
            theHashTable.insert(aDataItem);
            word = "";
            continue;
         }
         word = word + c;
      }
      
      theHashTable.displayTableA();
      System.out.println();
      theHashTable.displayTableB();
      System.out.println();
      System.out.println();
      
      n=0;
      while(input2.hasNext())
      {
         file2 = file2 + input2.nextLine() + " ";
         n++;
      }
      
      System.out.println("                           A");
      System.out.println("String                                            Success  Failure  Probe length for success   Probe length for failure");
      word = "";
      for(int i=0;i<file2.length();i++)
      {
         char c = file2.charAt(i);
         if(c==' ')
         {
            theHashTable.findA(word);
            word = "";
            continue;
         }
         word = word + c;
      }
      double aplS = theHashTable.get_aplS();
      double aplF = theHashTable.get_aplF();
      System.out.printf("Average Probe Length:                                                %.2f                       %.2f",aplS,aplF);
      System.out.println();
      System.out.println();
      
      theHashTable.resetS();
      theHashTable.resetF();
      
      System.out.println("                           B");
      System.out.println("String                                            Success  Failure  Probe length for success   Probe length for failure");
      word = "";
      for(int i=0;i<file2.length();i++)
      {
         char c = file2.charAt(i);
         if(c==' ')
         {
            theHashTable.findB(word);
            word = "";
            continue;
         }
         word = word + c;
      }
      aplS = theHashTable.get_aplS();
      aplF = theHashTable.get_aplF();
      System.out.printf("Average Probe Length:                                                %.2f                       %.2f",aplS,aplF);
      System.out.println();
      System.out.println();
      
      theHashTable.resetS();
      theHashTable.resetF();

      
      n=0;
      while(input3.hasNext())
      {
         file3 = file3 + input3.nextLine() + " ";
         n++;
      }
      
      System.out.println("                           A");
      System.out.println("String                                            Success  Failure  Probe length for success   Probe length for failure");
      word = "";
      for(int i=0;i<file3.length();i++)
      {
         char c = file3.charAt(i);
         if(c==' ')
         {
            theHashTable.deleteA(word);
            word = "";
            continue;
         }
         word = word + c;
      }
      aplS = theHashTable.get_aplS();
      aplF = theHashTable.get_aplF();
      System.out.printf("Average Probe Length:                                                %.2f                       %.2f",aplS,aplF);
      System.out.println();
      System.out.println();
      
      theHashTable.resetS();
      theHashTable.resetF();

      n=0;
      while(input3.hasNext())
      {
         file3 = file3 + input3.nextLine() + " ";
         n++;
      }
      
      System.out.println("                           B");
      System.out.println("String                                            Success  Failure  Probe length for success   Probe length for failure");
      word = "";
      for(int i=0;i<file3.length();i++)
      {
         char c = file3.charAt(i);
         if(c==' ')
         {
            theHashTable.deleteB(word);
            word = "";
            continue;
         }
         word = word + c;
      }
      aplS = theHashTable.get_aplS();
      aplF = theHashTable.get_aplF();
      System.out.printf("Average Probe Length:                                                %.2f                       %.2f",aplS,aplF);


      }  // end main()
//--------------------------------------------------------------
   public static String getString() throws IOException
      {
      InputStreamReader isr = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(isr);
      String s = br.readLine();
      return s;
      }
//--------------------------------------------------------------
   public static char getChar() throws IOException
      {
      String s = getString();
      return s.charAt(0);
      }
//-------------------------------------------------------------
   public static int getInt() throws IOException
      {
      String s = getString();
      return Integer.parseInt(s);
      }
//--------------------------------------------------------------
   }  // end class HashTableApp
////////////////////////////////////////////////////////////////