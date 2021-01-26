import java.io.*;
import java.util.*;
class Pass
{
    public static Scanner s;
    public static Scanner s2;
    public static void main(String args[])
    {
        File file = new File(args[0]);
        String sicops[] = {"LDB","SSK","WD","STX","OR","AND","LDA","JGT","STL","STI","LPS",
            "LDT","LDCH","LDL","SUMF","JSUB","LDX","MULF","J","STT","COMP","TIX","LDT","STA",
            "TD","STB","STS","DIVF","COMPF","JEQ","DIV","SUBF","JLT","RD","LDS","MUL","SUB",
            "ADDF","RSUB","STCH","STSW","STF","LDF","END","WORD"};
        String sicops1[] = {"FLOAT","SIO","HIO","TIO","FIX","NORM"};
        String sicops2[] = {"MULR","TIXR","SVC","SHIFTR","SUBR","DIVR","CLEAR","RMO","COMPR",
            "SHIFTL","ADDR"};
        try
        {
            s = new Scanner(file);
            s2 = new Scanner(file);
        }
        catch(Exception e)
        {
            System.out.println("FILE NOT FOUND");
        }
        HashTable h = new HashTable(1000);
        String start = s.nextLine();
        String ws = "";
        int sloc = 0;
        int loc = 0;
        for(int i=10;i<17;i++)
        {
            char c = start.charAt(i);
            ws = ws + c;
            if(ws.equals("START"))
            {
                String w = "";
                for(int j=19; j<start.length(); j++)
                {
                    char d = start.charAt(j);
                    w = w+d;
                }
                loc = Integer.parseInt(w, 16);
                sloc = loc;
                break;
            }
        }
        int counter = 0;
        int count = 0;
        int a[] = new int [1];
        int b[] = new int [1];
        int z[] = new int [1];
        a[0] = -1;
        b[0] = -1;
        z[0] = -1;
        while(s.hasNext())
        {
            String word = "";
            int bit = 0;
            String line = s.nextLine();
            line = line + " ";
            String w = "";
            if(line.trim().isEmpty())
            {
                b = expand(b,count);
                count++;
                continue;
            }
            for(int i=0; i<8; i++)
            {
                char c = line.charAt(i); 
                if(c == ' ')
                {
                    break;
                }
                word = word + c;
            }
            if(word.equals(""))
            {
                for(int i=10; i<17; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    w = w + d;
                }
                if(w.equals("RESB"))
                {
                    String ww = "";
                    int n = 0;
                    for(int i = 19; i<29; i++)
                    {
                        char d = line.charAt(i);
                        if(d == ' ')
                        {
                            break;
                        }
                        ww = ww + d;
                    }
                    System.out.println("ERROR: Undefined Label At Address "+Integer.toHexString(loc));
                    word = Integer.toString(counter);
                    counter++;
                    Item aItem = new Item(word,loc);
                    h.insert(aItem);
                    n = Integer.parseInt(ww);
                    loc = loc + n;
                    count++;
                    continue;
                }
                if(w.equals("RESW"))
                {
                    String ww = "";
                    int n = 0;
                    for(int i = 19; i<29; i++)
                    {
                        char d = line.charAt(i);
                        if(d == ' ')
                        {
                            break;
                        }
                        ww = ww + d;
                    }
                    System.out.println("ERROR: Undefined Label At Address "+Integer.toHexString(loc));
                    word = Integer.toString(counter);
                    counter++;
                    Item aItem = new Item(word,loc);
                    h.insert(aItem);
                    n = Integer.parseInt(ww);
                    loc = loc + (3*n);
                    count++;
                    continue;
                }
                if(w.equals("BYTE"))
                {
                    int n = 0;
                    for(int i = 21; i<29; i++)
                    {
                        char d = line.charAt(i);
                        if(d == 39)
                        {
                            break;
                        }
                        n++;
                    }
                    System.out.println("ERROR: Undefined Label At Address "+Integer.toHexString(loc));
                    word = Integer.toString(counter);
                    counter++;
                    Item aItem = new Item(word,loc);
                    h.insert(aItem);
                    if((line.charAt(19)) == 'X')
                    {
                        n = n/2;
                    }
                    loc = loc + n;
                    count++;
                    continue;
                }
                for(int i=0; i<sicops.length; i++)
                {
                    if(w.equals(sicops[i]))
                    {
                        bit = 1;
                        break;
                    }
                }
                if(bit == 0)
                {
                    for(int i=0; i<sicops1.length; i++)
                    {
                        if(w.equals(sicops1[i]))
                        {
                            bit = 2;
                            break;
                        }
                    }
                }
                if(bit == 0)
                {
                    for(int i=0; i<sicops2.length; i++)
                    {
                        if(w.equals(sicops2[i]))
                        {
                            bit = 3;
                            break;
                        }
                    }
                }
                if(bit == 0)
                {
                    b = expand(b,count);
                    count++;
                    System.out.println("ERROR: Invalid Mneumonic '"+w+"' (line will be ignored)");
                    continue;
                }
                if(!w.equals("END"))
                {
                    System.out.println("ERROR: Undefined Label At Address "+Integer.toHexString(loc));
                }
                word = Integer.toString(counter);
                counter++;
                Item aItem = new Item(word,loc);
                h.insert(aItem);
                if(bit == 2)
                {
                    loc = loc + 1;
                    count++;
                    continue;
                }
                if(bit == 3)
                {
                    loc = loc + 2;
                    count++;
                    continue;
                }
                if((line.charAt(9))=='+')
                {
                    loc = loc + 4;
                }
                else
                {
                    loc = loc + 3;
                }
                count++;
                continue;
            }
            for(int i=10; i<17; i++)
            {
                char d = line.charAt(i);
                if(d == ' ')
                {
                    break;
                }
                w = w + d;
            }
            if(w.equals("RESB"))
            {
                String ww = "";
                int n = 0;
                for(int i = 19; i<29; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    ww = ww + d;
                }
                Item aItem = new Item(word,loc);
                h.insert(aItem);
                n = Integer.parseInt(ww);
                loc = loc + n;
                count++;
                continue;
            }
            if(w.equals("RESW"))
            {
                String ww = "";
                int n = 0;
                for(int i = 19; i<29; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    ww = ww + d;
                }
                Item aItem = new Item(word,loc);
                h.insert(aItem);
                n = Integer.parseInt(ww);
                loc = loc + (3*n);
                count++;
                continue;
            }
            if(w.equals("BYTE"))
            {
                int n = 0;
                for(int i = 21; i<29; i++)
                {
                    char d = line.charAt(i);
                    if(d == 39)
                    {
                        break;
                    }
                    n++;
                }
                Item aItem = new Item(word,loc);
                h.insert(aItem);
                if((line.charAt(19)) == 'X')
                {
                    n = n/2;
                }
                loc = loc + n;
                count++;
                continue;
            }
            for(int i=0; i<sicops.length; i++)
            {
                if(w.equals(sicops[i]))
                {
                    bit = 1;
                    break;
                }
            }
            if(bit == 0)
            {
                for(int i=0; i<sicops1.length; i++)
                {
                    if(w.equals(sicops1[i]))
                    {
                        bit = 2;
                        break;
                    }
                }
            }
            if(bit == 0)
            {
                for(int i=0; i<sicops2.length; i++)
                {
                    if(w.equals(sicops2[i]))
                    {
                        bit = 3;
                        break;
                    }
                }
            }
            if(bit == 0)
            {
                b = expand(b,count);
                count++;
                System.out.println("ERROR: Invalid Mneumonic '"+w+"' (line will be ignored)");
                continue;
            }
            Item aItem = new Item(word,loc);
            int error = h.insert(aItem);
            if(error == 1)
            {
                a = expand(a,count);
                word = Integer.toString(counter);
                aItem = new Item(word,loc);
                counter++;
                h.insert(aItem);
            }
            if(bit == 2)
            {
                loc = loc + 1;
                count++;
                continue;
            }
            if(bit == 3)
            {
                loc = loc + 2;
                count++;
                continue;
            }
            if((line.charAt(9))=='+')
            {
                loc = loc + 4;
            }
            else
            {
                loc = loc + 3;
            }
            count++;
        }
        a = expand(a,-1);
        b = expand(b,-1);
        counter = 0;
        count = 0;
        int check = 1;
        int check2 = 1;
        System.out.println(Integer.toHexString(sloc)+"  "+start);
        String line = s2.nextLine();
        while(s2.hasNext())
        {
            line = s2.nextLine();
            String word = "";
            String w = "";
            if(count == a[check])
            {
                word = Integer.toString(counter);
                counter++;
                int num = h.find(word);
                System.out.println(Integer.toHexString(num)+"  "+line);
                count++;
                check++;
                continue;
            }
            if(count == b[check2])
            {
                System.out.println("----  "+line);
                count++;
                check2++;
                continue;
            }
            for(int i=0; i<8; i++)
            {
                char c = line.charAt(i); 
                if(c == ' ')
                {
                    break;
                }
                word = word + c;
            }
            if(word.equals(""))
            {
                word = Integer.toString(counter);
                counter++;
                int num = h.find(word);
                System.out.println(Integer.toHexString(num)+"  "+line);
                count++;
                continue;
            }
            int num = h.find(word);
            System.out.println(Integer.toHexString(num)+"  "+line);
            int num2 = h.location(word);
            z = expand(z,num2);
            count++;
        }
        for(int i=0; i<z.length; i++)
        {
            int temp = z[i];
            for(int j=i+1; j<z.length; j++)
            {
                if(z[i] > z[j])
                {
                    z[i] = z[j];
                    z[j] = temp;
                    temp = z[i];
                }
            }
        }
        System.out.println();
        System.out.println("Table Location     Label     Address     Use     Csect");
        for(int i = 1; i<z.length; i++)
        {
            for(int j = 0; j<1000; j++)
            {
                if(z[i] == j)
                {
                    Item aItem = h.loc2(j);
                    String word = aItem.getWord();
                    int address = h.find(word);
                    System.out.println(j+"                "+word+"       "
                    +Integer.toHexString(address)+"         main      main");
                }
            }
        }
    }
    public static int[] expand(int n[], int x)
    {
        int b[] = new int [n.length+1];
        for(int i = 0; i<b.length-1; i++)
        {
            b[i] = n[i];
        }
        b[n.length] = x;
        return b;
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
    public int insert(Item item)
    {
        int error = 0;
        String key = item.getWord();
        int num = item.getNumber();
        int hashVal = hashFunction(key);
        while(array[hashVal]!= null)
        {
            if((array[hashVal].getWord()).equals(key))
            {
                System.out.println("ERROR: Duplicate Label '"+key+"'");
                return 1;
            }
            ++hashVal;
            hashVal %= size;
        }
        array[hashVal] = item;
        return 0;
    }
    public int find(String key)
    {
        int num = 0;
        int hashVal = hashFunction(key);
        while(array[hashVal]!= null)
        {
            if(array[hashVal].getWord().equals(key))
            {
                num = array[hashVal].getNumber();
                return num;
            }
            ++hashVal;
            hashVal %= size;
        }
        return num;
    }
    public int location(String key)
    {
        int num = 0;
        int hashVal = hashFunction(key);
        while(array[hashVal]!= null)
        {
            if(array[hashVal].getWord().equals(key))
            {
                num = hashVal;
                return num;
            }
            ++hashVal;
            hashVal %= size;
        }
        return num;
    }
    public Item loc2(int n)
    {
        return array[n];
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
