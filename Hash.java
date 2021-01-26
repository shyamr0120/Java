import java.io.*;
import java.util.*;
class Hash
{
    public static Scanner s;
    public void main()
    {
        File file = new File("input.txt");
        try
        {
            s = new Scanner(file);
        }
        catch(Exception e)
        {
            System.out.println("FILE NOT FOUND");
        }
        HashTable h = new HashTable(100);
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
    }
}
class HashTable
{
    private Item[] array;
    private int size;
    public HashTable(int s)
    {
        size = s;
        array = new Item[size];
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
                System.out.println("ERROR "+key+" already exists at location "+hashVal);
                return;
            }
            ++hashVal;
            hashVal %= size;
        }
        array[hashVal] = item;
        System.out.println("Stored "+key+" "+num+" at location "+hashVal);
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
                System.out.println(key+" found at location "+hashVal+" with value "+num);
                return;
            }
            ++hashVal;
            hashVal %= size;
        }
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