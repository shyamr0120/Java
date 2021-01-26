import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
/*
   Michael Frederick
   N00725913
   Data Structures
   COP3530
   Ken Martin
   
   Assignment 6
*/

public class n00725913
{
    public static void main(String[] args) {
        Program program = new Program();
        program.runProgram(args);
    }//end main()
} // end class n00725913

class Program{
    File initialFile;
    File searchFile;
    File deletionFile;
    
    int numberOfStrings;
    int arraySize;
    
    public void runProgram(String[] args){
        boolean valid = initializeFiles(args);
        if(valid){
            this.numberOfStrings = findNumberOfStrings();
            this.arraySize = getNextPrime(this.numberOfStrings);
            if(this.arraySize == -1){
                System.out.printf("Initial File is two large%n");
                return;
            }
            
            //Create Hash Tables for Linear Probing and Quadratic probing
            HashTable linearTable = new HashTable("A", this.arraySize);
            HashTable quadTable = new HashTable("B", this.arraySize);
            
            //Fill the tables
            linearTable = fillTableLinear(this.initialFile, linearTable);
            quadTable = fillTableQuad(this.initialFile, quadTable);

            //Show Results
            System.out.printf("Average Probe Length: %30d%n", linearTable.displayTable());
            System.out.printf("Average Probe Length: %30d%n", quadTable.displayTable());
            
            //Find strings in those tables
            System.out.printf("%nSearch Linear%n");
            DataItem[] results = findStringsInTableLinear(this.searchFile, linearTable);
            displayResults(results);
            System.out.printf("%nSearch Quadratic%n");
            results = findStringsInTableQuadratic(this.searchFile, quadTable);
            displayResults(results);
            
            //Delete strings in those tables
            System.out.printf("%nDelete Linear%n");
            results = deleteStringsInTableLinear(this.deletionFile, linearTable);
            displayResults(results);
            System.out.printf("%nDelete Quadratic%n");
            results = deleteStringsInTableQuadratic(this.deletionFile, quadTable);
            displayResults(results);
            
        }
        System.out.printf("%nEnd of Program%n");
        return;
    }
    
    private DataItem[] deleteStringsInTableQuadratic(File file, HashTable table){
        DataItem[] results = new DataItem[this.arraySize];
        
        try{
            int i = 0;
            String key;
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNext()){
                key = fileScanner.nextLine().trim();
                results[i] = table.deleteQuad(key);
                i++;
            }
        }
        catch (FileNotFoundException ex){
            System.out.printf("File: %s was not found%n", file.getName());
        }
        return results;
    }
    
    private DataItem[] findStringsInTableQuadratic(File file, HashTable table){
        DataItem[] results = new DataItem[this.arraySize];
        
        try{
            int i = 0;
            String key;
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNext()){
                key = fileScanner.nextLine().trim();
                results[i] = table.findQuad(key);
                i++;
            }
        }
        catch (FileNotFoundException ex){
            System.out.printf("File: %s was not found%n", file.getName());
        }
        return results;
    }
    
    private DataItem[] deleteStringsInTableLinear(File file, HashTable table){
        DataItem[] results = new DataItem[this.arraySize];
        
        try{
            int i = 0;
            String key;
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNext()){
                key = fileScanner.nextLine().trim();
                results[i] = table.deleteLinear(key);
                i++;
            }
        }
        catch (FileNotFoundException ex){
            System.out.printf("File: %s was not found%n", file.getName());
        }
        return results;
    }
    
    private DataItem[] findStringsInTableLinear(File file, HashTable table){
        DataItem[] results = new DataItem[this.arraySize];
        
        try{
            int i = 0;
            String key;
            Scanner fileScanner = new Scanner(file);
            while(fileScanner.hasNext()){
                key = fileScanner.nextLine().trim();
                results[i] = table.findLinear(key);
                i++;
            }
        }
        catch (FileNotFoundException ex){
            System.out.printf("File: %s was not found%n", file.getName());
        }
        return results;
    }
    
    private void displayResults(DataItem[] results){
        ArrayList<DataItem> success = new ArrayList<DataItem>();
        ArrayList<DataItem> failure = new ArrayList<DataItem>();
        
        System.out.printf("%-40s | %7s | %7s | %24s | %24s%n", "String", "Success", "Failure" , "Probe Length for Success", "Probe Length for Failure");
        for(DataItem item : results){
            if(item == null){
                //End the loop when I've run out of results
                break;
            }
            if(item.getFind()){
                //Success
                System.out.printf("%-40s | %7s | %7s | %24s | %24s%n", item.getKey(), "Yes", "", item.getProbeLength(), "");
                success.add(item);
            }
            else{
                //Failure
                System.out.printf("%-40s | %7s | %7s | %24s | %24s%n", item.getKey(), "", "Yes", "", item.getProbeLength());
                failure.add(item);
            }
        }
        
        //Print the Average probe length
        //Convert the ArrayList into DataItem arrays
        DataItem[] successArray = new DataItem[success.size()];
        successArray = success.toArray(successArray);
        DataItem[] failureArray = new DataItem[failure.size()];
        failureArray = failure.toArray(successArray);
        System.out.printf("%-63s %23d | %24d%n", "Average Probe Length:", getAverageLength(successArray), getAverageLength(failureArray));
        
        return;
    }
    
    private int getAverageLength(DataItem[] results){
        int total = 0;
        int average = 0;
        
        /*for(DataItem item : results){
            total += item.getProbeLength();
        }*/
        
        average = total / results.length;
        
        return average;
    }
    
    private HashTable fillTableLinear(File file, HashTable table){
        try{
            Scanner fileReader = new Scanner(file);
            for(int i = 0; i < this.numberOfStrings; i++){
                table.insertLinear(new DataItem(fileReader.nextLine().trim()));
            }
        }
        catch(FileNotFoundException ex){
            System.out.printf(ex.toString());
        }
        
        return table;
    }
    
    private HashTable fillTableQuad(File file, HashTable table){
        try{
            Scanner fileReader = new Scanner(file);
            for(int i = 0; i < this.numberOfStrings; i++){
                table.insertQuad(new DataItem(fileReader.nextLine().trim()));
            }
        }
        catch(FileNotFoundException ex){
            System.out.printf(ex.toString());
        }
        
        return table;
    }
    
    // Method from pg. 541 of Data Structures and Algorithms in Java by Robert Lafore
    private int getNextPrime(int min){
        for(int i = min + 1; i < Integer.MAX_VALUE - 1; i++){
            if(isPrime(i) && (i / 2) >= this.numberOfStrings){
                return i;
            }
        }
        return -1;
    }
    
    // Method from pg. 541 of Data Structures and Algorithms in Java by Robert Lafore
    private boolean isPrime(int p){
        for(int i = 2; (i * i) <= p; i++){
            if( (p % i) == 0){
                return false;
            }
        }
        return true;
    }
    
    private int findNumberOfStrings(){
        int number = 0;
        try{
            Scanner fileScanner = new Scanner(this.initialFile);
            while (fileScanner.hasNext()){
                number++;
                //I'm not inserting the values yet
                fileScanner.nextLine();
            }
        }
        catch (Exception ex){
            //Something bad happened
            System.out.printf(ex.toString());
        }
        
        return number;
    }
    
    private boolean initializeFiles(String[] args){
        boolean valid = true;
        this.initialFile = new File(args[0]);
        if(!initialFile.exists()){
            System.out.printf("Initial File Not Found%n");
            valid = false;
        }
        this.searchFile = new File(args[1]);
        if(!this.searchFile.exists()){
            System.out.printf("Search File Not Found%n");
            valid = false;
        }
        this.deletionFile = new File(args[2]);
        if(!this.deletionFile.exists()){
            System.out.printf("Deletion File Not Found%n");
            valid = false;
        }
        return valid;
    }
}//end of class Program

//Taken from Data Structures and Algorithms in Java by Robert Lafore
////////////////////////////////////////////////////////////////
class DataItem
   {                                // (could have more data)
   private String data;               // data item (key)
   private int probeLength;
   private boolean find;
//--------------------------------------------------------------
   public DataItem(String str){ 
       this.data = str; 
       this.probeLength = 0;
       this.find = true;
   }
//--------------------------------------------------------------
   public String getKey()
      { return data; }
//--------------------------------------------------------------
   public int getProbeLength(){
       return this.probeLength;
   }
//--------------------------------------------------------------
   public void increaseProbeLength(){
       this.probeLength++;
   }
//--------------------------------------------------------------
   public void setProbeLength(int newLength){
       this.probeLength = newLength;
   }
//--------------------------------------------------------------
   public boolean getFind(){
       return this.find;
   }
//--------------------------------------------------------------
   public void setFind(boolean newFind){
       this.find = newFind;
   }
//--------------------------------------------------------------
   }  // end class DataItem
////////////////////////////////////////////////////////////////
class HashTable
   {
   private DataItem[] hashArray;    // array holds hash table
   private int arraySize;
   private DataItem nonItem;        // for deleted items
   private String tableName;
// -------------------------------------------------------------
   public HashTable(String tableName, int size)       // constructor
      {
      this.arraySize = size;
      this.hashArray = new DataItem[arraySize];
      this.nonItem = new DataItem("");   // deleted item key is the empty string
      this.tableName = tableName;
      }
// -------------------------------------------------------------
   public int displayTable()
      {
      int average;
      int total = 0;
      int items = 0;
      System.out.printf("%7s%n", this.tableName);
      System.out.printf("%s | %-40s | %s |%n", "Index", "String", "Probe Length for Insertion");
      for(int j=0; j<arraySize; j++)
         {
            if(hashArray[j] != null){
                System.out.printf("%5d | %-40s | %-26d |%n", j, hashArray[j].getKey(), hashArray[j].getProbeLength());
                items++;
                total += hashArray[j].getProbeLength();
            }
         }
      
      average = (total / items);
      return average;
      }
// -------------------------------------------------------------
   public int hashFunc(String key)
      {
        char[] charArray = key.toCharArray();
        int hashValue = charArray[0];
        int temp;
        for(int i = 1; i < charArray.length; i++){
            temp = charArray[i];
            hashValue = (hashValue * 26 + temp) % arraySize;
        }
        return hashValue;
      }
// -------------------------------------------------------------
   public void insertLinear(DataItem item) // insert a DataItem
   // (assumes table not full)
      {
      String key = item.getKey();      // extract key
      int hashVal = hashFunc(key);  // hash the key
                                    // until empty cell or -1,
      while(hashArray[hashVal] != null &&
            !hashArray[hashVal].getKey().equals(this.nonItem.getKey()))
         {
         item.increaseProbeLength();
         ++hashVal;                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         }
      item.increaseProbeLength();
      hashArray[hashVal] = item;    // insert item
      }  // end insert()
// -------------------------------------------------------------
   public DataItem deleteLinear(String key)  // delete a DataItem
      {
      int hashVal = hashFunc(key);  // hash the key
      int length = 1;
      while(hashArray[hashVal] != null)  // until empty cell,
        {                               // found the key?
         if(hashArray[hashVal].getKey().equals(key)){
            DataItem temp = hashArray[hashVal]; // save item
            hashArray[hashVal] = nonItem;       // delete item
            temp.setProbeLength(length);
            return temp;                        // return item
        }
         length++;
         ++hashVal;                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
        }
      DataItem fail = new DataItem(key);
      fail.setFind(false);
      fail.setProbeLength(length);
      return fail;                  // can't find item
      }  // end delete()
// -------------------------------------------------------------
   public DataItem findLinear(String key)    // find item with key
      {
      int hashVal = hashFunc(key);  // hash the key
      int length = 1;
      while(hashArray[hashVal] != null)  // until empty cell,
         {                               // found the key?
         if(hashArray[hashVal].getKey().equals(key)){
            DataItem temp = hashArray[hashVal];
            temp.setProbeLength(length);
            return temp;   // yes, return item
         }
         length++;
         ++hashVal;                 // go to next cell
         hashVal %= arraySize;      // wraparound if necessary
         }
      DataItem fail = new DataItem(key);
      fail.setFind(false);
      fail.setProbeLength(length);
      return fail;                  // can't find item
      }
// -------------------------------------------------------------
   public void insertQuad(DataItem item) // insert a DataItem
   // (assumes table not full)
      {
      int length = 1;
      int index;
      String key = item.getKey();      // extract key
      int hashVal = hashFunc(key);  // hash the key
                                    // until empty cell or -1,
      index = hashVal;
      while(hashArray[index] != null &&
            !hashArray[index].getKey().equals(this.nonItem.getKey()))
         {
         length++;
         index = hashVal + (length * length);
         index %= arraySize;      // wraparound if necessary
         }
      item.setProbeLength(length);
      hashArray[index] = item;    // insert item
      }  // end insert()
// -------------------------------------------------------------
   public DataItem deleteQuad(String key)  // delete a DataItem
      {
      int hashVal = hashFunc(key);  // hash the key
      int index = hashVal;
      int length = 1;

      while(hashArray[index] != null)  // until empty cell,
        {                               // found the key?
         if(hashArray[index].getKey().equals(key)){
            DataItem temp = hashArray[index]; // save item
            temp.setProbeLength(length);
            hashArray[index] = nonItem;       // delete item
            return temp;                        // return item
         }//end if
         length++;
         index = hashVal + (length * length);   // go to next cell
         index %= arraySize;      // wraparound if necessary
        }
      DataItem fail = new DataItem(key);
      fail.setFind(false);
      fail.setProbeLength(length);
      return fail;                  // can't find item
      }  // end delete()
// -------------------------------------------------------------
   public DataItem findQuad(String key)    // find item with key
      {
      int hashVal = hashFunc(key);  // hash the key
      int length = 1;
      int index = hashVal;

      while(hashArray[index] != null)  // until empty cell,
         {                               // found the key?
         if(hashArray[index].getKey().equals(key)){
            return hashArray[index];   // yes, return item
         }
         length++;
         index = hashVal + (length * length);                 // go to next cell
         index %= arraySize;      // wraparound if necessary
         }
      DataItem fail = new DataItem(key);
      fail.setFind(false);
      fail.setProbeLength(length);
      return fail;                  // can't find item
      }
// -------------------------------------------------------------

   }  // end class HashTable
////////////////////////////////////////////////////////////////