import java.io.*;
import java.util.*;
class Hash
{
    public static PrintWriter p;
    public static void main(String args[])
    {
        int linenum = 0;
        File file = new File(args[0]);
        try
        {
            p = new PrintWriter(new FileWriter(args[1]));
        }
        catch(Exception e)
        {
            System.out.println("FILE NOT FOUND");
        }
    
        try
        {
            Scanner s = new Scanner(file);
            try
            {
            if(file.exists()){
    			
    		    FileReader fr = new FileReader(file);
    		    LineNumberReader lnr = new LineNumberReader(fr);
    		    
    		    
    	            while (lnr.readLine() != null){
    	        	linenum++;
    	            }
    	 
    	 
    	            lnr.close();
    	           }
    	       }
    	       catch(Exception e)
    	       {
    	       }
    	       HashTable h = new HashTable(linenum);
    	       h.print(p);
            while(s.hasNext())
        {
            String word = "";
            int pass = 0;
            String line = s.nextLine();
            for(int i=0; i<line.length(); i++)
            {
                char c = line.charAt(i);
                if(c == ' ')
                {
                    String w = "";
                    for(int j=i+1; j<line.length(); j++)
                    {
                        char d = line.charAt(j);
                        w = w+d;
                    }
                    int n = Integer.parseInt(w);
                    Item aItem = new Item(word,n);
                    h.insert(aItem);
                    pass = 1;
                    break;
                }
                word = word + c;
            }
            if(pass == 0)
            {
                h.find(word);
            }
        }
        h.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("FILE NOT FOUND");
        }
    }
}
class HashTable
{
    public static PrintWriter p;
    private Item[] array;
    private int size;
    public HashTable(int s)
    {
        size = s;
        array = new Item[size];
    }
    public void print(PrintWriter print)
    {
        try
        {
            p = print;
        }
        catch(Exception e)
        {
            System.out.println("FILE NOT FOUND");
        }
    }
    public void close()
    {
        p.close();
    }
    public int hashFunction(String key)
    {
        int hashVal=0;
         for(int j=0; j<key.length(); j++)
         {
            int letter = key.charAt(j);
            hashVal = (hashVal*26 + letter)%size;
         }
         return hashVal;
    }
    public void insert(Item item)
    {
        String key = item.getWord();
        int num = item.getNumber();
        int hashVal = hashFunction(key);
        while(array[hashVal]!= null)
        {
            if((array[hashVal].getWord()).equals(key))
            {
                p.printf("ERROR %s already exists at location %d\r\n",key,hashVal);
                return;
            }
            ++hashVal;
            hashVal %= size;
        }
        array[hashVal] = item;
        p.printf("Stored %s %d at location %d\r\n",key,num,hashVal);
    }
    public void find(String key)
    {
        int num = 0;
        int hashVal = hashFunction(key);
        while(array[hashVal]!= null)
        {
            if(array[hashVal].getWord().equals(key))
            {
                num = array[hashVal].getNumber();
                p.printf("%s found at location %d with value %d\r\n",key,hashVal,num);
                return;
            }
            ++hashVal;
            hashVal %= size;
        }
        p.printf("ERROR %s not found\r\n",key);
    }
}
class Item
{
    private String s;
    private int i;
    public Item(String string, int ii)
    {
        s = string;
        i = ii;
    }
    public int getNumber()
    {
        return i;
    }
    public String getWord()
    {
        return s;
    }
}