import java.io.*;
import java.util.*;
class Pass2
{
    public static Scanner s;
    public static Scanner s2;
    public static Scanner s3;
    public static int base;
    public static String star;
    public void main()
    {
        File file = new File("Test.txt");
        String sicops[] = {"LDB","SSK","WD","STX","OR","AND","LDA","JGT","STL","STI","LPS",
            "LDT","LDCH","LDL","JSUB","LDX","MULF","J","STT","COMP","TIX","LDT","STA",
            "TD","STB","STS","DIVF","COMPF","JEQ","DIV","SUBF","JLT","RD","LDS","MUL","SUB",
            "ADDF","RSUB","STCH","STSW","STF","LDF","END","WORD"};
        String sicops1[] = {"FLOAT","SIO","HIO","TIO","FIX","NORM"};
        String sicops2[] = {"MULR","TIXR","SVC","SHIFTR","SUBR","DIVR","CLEAR","RMO","COMPR",
            "SHIFTL","ADDR"};
        try
        {
            s = new Scanner(file);
            s2 = new Scanner(file);
            s3 = new Scanner(file);
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
                star = w;
                sloc = loc;
                break;
            }
        }
        int counter = 0;
        int count = 0;
        int a[] = new int [1];
        int b[] = new int [1];
        int z[] = new int [1];
        String opcode[] = new String [1];
        a[0] = -1;
        b[0] = -1;
        z[0] = -1;
        opcode[0] = "-1";
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
                if(w.equals("BASE")||w.equals("LTORG"))
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
                    count++;
                    continue;
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
            if(w.equals("BASE")||w.equals("LTORG"))
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
                count++;
                continue;
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
        String line = "";;
        while(s2.hasNext())
        {
            line = s2.nextLine();
            String ww = "";
            for(int i = 10; i<18; i++)
            {
                char d = line.charAt(i);
                if(d == ' ')
                {
                    break;
                }
                ww = ww + d;
            }
            if(ww.equals("BASE"))
            {
                String t = "";
                for(int i = 19; i<29; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    t = t + d;
                }
                base = h.find(t);
            }
        }
        try
        {
            s2 = new Scanner(file);
        }
        catch(Exception e)
        {
            
        }
        System.out.println(Integer.toHexString(sloc)+"  "+start);
        line = s2.nextLine();
        int re = 1;
        while(s2.hasNext())
        {
            line = s2.nextLine();
            line = line + " ";
            String word = "";
            String ww = "";
            int n = 0;
            int n2 = 0;
            int w = 0;
            int bb = 0;
            if(count == a[check])
            {
                word = Integer.toString(counter);
                counter++;
                int num = h.find(word);
                if(line.charAt(9)== '+')
                {
                    n = 1;
                }
                if(line.charAt(18)== '#')
                {
                    n2 = 1;
                }
                if(line.charAt(18)== '@')
                {
                    n2 = 2;
                }
                for(int i = 10; i<18; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    ww = ww + d;
                }
                String line2 = "";
                try
                {
                    line2 = s2.nextLine();
                }
                catch(Exception e)
                {
                    System.out.println(Integer.toHexString(num)+"  "+line);
                    break;
                }
                line2 = line2 + " ";
                String nn = "";
                String t = "";
                for(int i = 0; i<8; i++)
                {
                    char d = line2.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    nn = nn + d;
                }
                for(int i = 19; i<29; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    t = t + d;
                }
                int num2 = h.find(nn);
                if(nn.equals(""))
                {
                    nn = Integer.toString(counter);
                    num2 = h.find(nn);
                }
                int num3 = h.find(t);
                int sum = num3-num2;
                if(ww.equals("BASE")||ww.equals("LTORG")||ww.equals("RESW")||ww.equals("RESB")||
                ww.equals("BYTE"))
                {
                    if(ww.equals("BASE")||ww.equals("LTORG"))
                    {
                    }
                    else
                    {
                        opcode = expand2(opcode,"9999");
                    }
                    System.out.println(Integer.toHexString(num)+"  "+line);
                }
                else
                {
                    String opc = calcOpcode(ww, n, n2, t,Integer.toHexString(sum),
                    Integer.toHexString(num3), bb);
                    if(bb == 1)
                    {
                        int tempe = Integer.parseInt(opc);
                        tempe = tempe*29;
                        opcode = expand2(opcode,(Integer.toString(tempe)));
                    }
                    else
                    {
                        opcode = expand2(opcode,opc);
                    }
                    System.out.println(Integer.toHexString(num)+"  "+line+"  "+opc);
                }
                count++;
                check++;
                re++;
                try
                {
                    s2 = new Scanner(file);
                }
                catch(Exception e)
                {
                    System.out.println("FILE NOT FOUND");
                }
                for(int j = 0; j<re;j++)
                {
                    s2.nextLine();
                }
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
                if(line.charAt(9)== '+')
                {
                    n = 1;
                }
                if(line.charAt(18)== '#')
                {
                    n2 = 1;
                }
                if(line.charAt(18)== '@')
                {
                    n2 = 2;
                }
                for(int i = 10; i<18; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    ww = ww + d;
                }
                String line2 = "";
                try
                {
                    line2 = s2.nextLine();
                }
                catch(Exception e)
                {
                    System.out.println(Integer.toHexString(num)+"  "+line);
                    break;
                }
                line2 = line2 + " ";
                String nn = "";
                String t = "";
                for(int i = 0; i<8; i++)
                {
                    char d = line2.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    nn = nn + d;
                }
                for(int i = 19; i<29; i++)
                {
                    char d = line.charAt(i);
                    if(d == ' ')
                    {
                        break;
                    }
                    t = t + d;
                }
                int num2 = h.find(nn);
                if(nn.equals(""))
                {
                    nn = Integer.toString(counter);
                    num2 = h.find(nn);
                }
                int num3 = h.find(t);
                int sum = num3-num2;
                if(ww.equals("BASE")||ww.equals("LTORG")||ww.equals("RESW")||ww.equals("RESB")||
                ww.equals("BYTE"))
                {
                    if(ww.equals("BASE")||ww.equals("LTORG"))
                    {
                    }
                    else
                    {
                        opcode = expand2(opcode,"9999");
                    }
                    System.out.println(Integer.toHexString(num)+"  "+line);
                }
                else
                {
                    String opc = calcOpcode(ww, n, n2, t,Integer.toHexString(sum),
                    Integer.toHexString(num3), bb);
                    if(bb == 1)
                    {
                        int tempe = Integer.parseInt(opc);
                        tempe = tempe*29;
                        opcode = expand2(opcode,(Integer.toString(tempe)));
                    }
                    else
                    {
                        opcode = expand2(opcode,opc);
                    }
                    System.out.println(Integer.toHexString(num)+"  "+line+"  "+opc);
                }
                count++;
                re++;
                try
                {
                    s2 = new Scanner(file);
                }
                catch(Exception e)
                {
                    System.out.println("FILE NOT FOUND");
                }
                for(int j = 0; j<re;j++)
                {
                    s2.nextLine();
                }
                continue;
            }
            int num = h.find(word);
            if(line.charAt(9)== '+')
            {
                n = 1;
            }
            if(line.charAt(18)== '#')
            {
                n2 = 1;
            }
            if(line.charAt(18)== '@')
            {
                n2 = 2;
            }
            for(int i = 10; i<18; i++)
            {
                char d = line.charAt(i);
                if(d == ' ')
                {
                    break;
                }
                ww = ww + d;
            }
            String line2 = "";
            try
            {
                line2 = s2.nextLine();
            }
            catch(Exception e)
            {
                System.out.println(Integer.toHexString(num)+"  "+line);
                break;
            }
            line2 = line2 + " ";
            String nn = "";
            String t = "";
            for(int i = 0; i<8; i++)
            {
                char d = line2.charAt(i);
                if(d == ' ')
                {
                    break;
                }
                nn = nn + d;
            }
            for(int i = 19; i<29; i++)
            {
                char d = line.charAt(i);
                if(d == ' ')
                {
                    break;
                }
                t = t + d;
            }
            int num2 = h.find(nn);
            if(nn.equals(""))
            {
                nn = Integer.toString(counter);
                num2 = h.find(nn);
            }
            int num3 = 0;
            int sum = 0;
            int flag = 0;
            try
            {
                num3 = h.find(t);
                sum = num3-num2;
            }
            catch(Exception e)
            {
                num3 = Integer.parseInt(t);
                sum = num3;
                flag = 1;
            }
            if(sum>2047||sum<-2048)
            {
                if(flag == 0)
                {
                    sum = num3 - base;
                    bb = 1;
                }
            }
            if(ww.equals("BASE")||ww.equals("LTORG")||ww.equals("RESW")||ww.equals("RESB")||
                ww.equals("BYTE"))
            {
                if(ww.equals("BASE")||ww.equals("LTORG"))
                {
                }
                else
                {
                    opcode = expand2(opcode,"9999");
                }
                System.out.println(Integer.toHexString(num)+"  "+line);
            }
            else
            {
                String opc = calcOpcode(ww, n, n2, t,Integer.toHexString(sum),
                Integer.toHexString(num3), bb);
                if(bb == 1)
                {
                    int tempe = Integer.parseInt(opc);
                    tempe = tempe*29;
                    opcode = expand2(opcode,(Integer.toString(tempe)));
                }
                else
                {
                    opcode = expand2(opcode,opc);
                }
                System.out.println(Integer.toHexString(num)+"  "+line+"  "+opc);
            }
            int num4 = h.location(word);
            z = expand(z,num4);
            count++;
            re++;
            try
            {
                s2 = new Scanner(file);
            }
            catch(Exception e)
            {
                System.out.println("FILE NOT FOUND");
            }
            for(int j = 0; j<re;j++)
            {
                s2.nextLine();
            }
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
        opcode = expand2(opcode,"9999");
        System.out.println();
        line = s3.nextLine();
        int oc = 1;
        while(s3.hasNext())
        {
            line = s3.nextLine();
            String word = "";
            String w = "";
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
                oc++;
                continue;
            }
            int num = h.find(word);
            if(opcode[oc].equals("9999"))
            {
                System.out.println(Integer.toHexString(num));
                System.out.println("000000");
                oc++;
                System.out.println("!");
                continue;
            }
            String www = "";
            for(int i=10; i<17; i++)
            {
                char d = line.charAt(i); 
                if(d == ' ')
                {
                    break;
                }
                www = www + d;
            }
            int tempe = Integer.parseInt(opcode[oc]);
            if(tempe%29==0)
            {
                System.out.println(Integer.toHexString(num));
                System.out.println("000"+star);
                tempe = tempe/29;
                String tl = Integer.toString(tempe);
                int length = tl.length();
                for(int j = 0; j<(6-length); j++)
                {
                    tl = "0"+tl;
                }
                System.out.println(tl);
            }
            else
            {
                System.out.println(Integer.toHexString(num));
                System.out.println("000000");
                System.out.println(opcode[oc]);
            }
            www = "";
            oc++;
            int go = 1;
            int pass = 0;
            while(go == 1)
            {
                if(opcode[oc].equals("9999"))
                {
                    System.out.println("!");
                    for(int j=10; j<17; j++)
                    {
                        char d = line.charAt(j); 
                        if(d == ' ')
                        {
                            break;
                        }
                        www = www + d;
                    }
                    if(www.equals("WORD"))
                    {
                        s3.nextLine();
                        oc++;
                        break;
                    }
                    if(pass == 0)
                    {
                        break;
                    }
                    try
                    {
                        s3.nextLine();
                    }
                    catch(Exception e)
                    {
                        break;
                    }
                    break;
                }
                System.out.println(opcode[oc]);
                oc++;
                pass = 1;
                s3.nextLine();
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
     public static String[] expand2(String n[], String x)
    {
        String b[] = new String [n.length+1];
        for(int i = 0; i<b.length-1; i++)
        {
            b[i] = n[i];
        }
        b[n.length] = x;
        return b;
    }
    public static String calcOpcode(String w, int ee, int nn, String num, String pc, String dir, int base)
    {
        String opc = "";
        int n = 1;
        int i = 1;
        int x = 0;
        int b = 0;
        int p = 0;
        int e = 0;
        e = ee;
        int length = num.length();
        if(nn == 1)
        {
            n = 0;
        }
        if(nn == 2)
        {
            i = 0;
        }
        if(w.equals("WORD"))
        {
            for(int j = 0; j<(6-length); j++)
            {
                num = "0"+num;
            }
            return num;
        }
        String sicops[] = {"/LDB","SSK","WD","STX","OR","AND","/LDA","JGT","STL","STI","LPS",
            "/LDT","LDCH","LDL","SUMF","JSUB","/LDX","MULF","/J","STT","COMP","TIX","LDT","/STA",
            "TD","STB","STS","DIVF","COMPF","JEQ","DIV","SUBF","JLT","RD","/LDS","MUL","/SUB",
            "ADDF","/RSUB","STCH","STSW","STF","LDF","END","WORD","/ADD"};
        String sicops1[] = {"FLOAT","SIO","HIO","TIO","FIX","NORM"};
        String sicops2[] = {"MULR","TIXR","SVC","SHIFTR","SUBR","DIVR","CLEAR","RMO","COMPR",
            "SHIFTL","/ADDR"};
        if(w.equals("LDA"))
        {
            opc = "00";
        }
        if(w.equals("SSK"))
        {
            opc = "EC";
        }
        if(w.equals("ADD"))
        {
            opc = "18";
        }
        if(w.equals("SUB"))
        {
            opc = "1C";
        }
        if(w.equals("STA"))
        {
            opc = "0C";
        }
        if(w.equals("ADDR"))
        {
            opc = "90";
        }
        if(w.equals("LDT"))
        {
            opc = "74";
        }
        if(w.equals("RSUB"))
        {
            opc = "4C";
        }
        if(w.equals("J"))
        {
            opc = "3C";
        }
        if(w.equals("LDX"))
        {
            opc = "04";
        }
        if(w.equals("LDS"))
        {
            opc = "6C";
        }
        if(w.equals("LDB"))
        {
            opc = "68";
        }
        if(w.equals("WD"))
        {
            opc = "DC";
        }
        if(w.equals("STX"))
        {
            opc = "10";
        }
        if(w.equals("OR"))
        {
            opc = "44";
        }
        if(w.equals("AND"))
        {
            opc = "40";
        }
        if(w.equals("JGT"))
        {
            opc = "34";
        }
        if(w.equals("STL"))
        {
            opc = "14";
        }
        if(w.equals("STI"))
        {
            opc = "D4";
        }
        if(w.equals("LPS"))
        {
            opc = "D0";
        }
        if(w.equals("LDCH"))
        {
            opc = "50";
        }
        if(w.equals("LDL"))
        {
            opc = "08";
        }
        if(w.equals("JSUB"))
        {
            opc = "48";
        }
        if(w.equals("MULF"))
        {
            opc = "60";
        }
        if(w.equals("COMP"))
        {
            opc = "28";
        }
        if(w.equals("TIX"))
        {
            opc = "2C";
        }
        if(w.equals("LDT"))
        {
            opc = "74";
        }
        if(w.equals("TD"))
        {
            opc = "E0";
        }
        if(w.equals("STB"))
        {
            opc = "78";
        }
        if(w.equals("STS"))
        {
            opc = "7C";
        }
        if(w.equals("DIVF"))
        {
            opc = "64";
        }
        if(w.equals("COMPF"))
        {
            opc = "88";
        }
        if(w.equals("JEQ"))
        {
            opc = "30";
        }
        if(w.equals("DIV"))
        {
            opc = "24";
        }
        if(w.equals("SUBF"))
        {
            opc = "5C";
        }
        if(w.equals("JLT"))
        {
            opc = "38";
        }
        if(w.equals("RD"))
        {
            opc = "D8";
        }
        if(w.equals("MUL"))
        {
            opc = "20";
        }
        if(w.equals("ADDF"))
        {
            opc = "58";
        }
        if(w.equals("STCH"))
        {
            opc = "54";
        }
        if(w.equals("STSW"))
        {
            opc = "E8";
        }
        if(w.equals("LDF"))
        {
            opc = "70";
        }
        if(w.equals("STF"))
        {
            opc = "80";
        }
        if(w.equals("FLOAT"))
        {
            opc = "C0";
        }
        if(w.equals("SIO"))
        {
            opc = "F0";
        }
        if(w.equals("HIO"))
        {
            opc = "F4";
        }
        if(w.equals("TIO"))
        {
            opc = "F8";
        }
        if(w.equals("FIX"))
        {
            opc = "C4";
        }
        if(w.equals("NORM"))
        {
            opc = "C8";
        }
        if(w.equals("MULR"))
        {
            opc = "98";
        }
        if(w.equals("TIXR"))
        {
            opc = "B8";
        }
        if(w.equals("SVC"))
        {
            opc = "B0";
        }
        if(w.equals("SHIFTR"))
        {
            opc = "A8";
        }
        if(w.equals("SUBR"))
        {
            opc = "94";
        }
        if(w.equals("COMPR"))
        {
            opc = "A0";
        }
        if(w.equals("DIV"))
        {
            opc = "24";
        }
        if(w.equals("CLEAR"))
        {
            opc = "B4";
        }
        if(w.equals("RMO"))
        {
            opc = "AC";
        }
        if(w.equals("SHIFTL"))
        {
            opc = "A4";
        }
        if(base == 1)
        {
            int op = Integer.parseInt(opc,16);
            op = op + 3;
            opc = Integer.toHexString(op);
            length = opc.length();
            for(int j = 0; j<(2-length); j++)
            {
                opc = "0"+opc;
            }
            if(e==1)
            {
                opc = opc + "500";
                opc = opc + pc;
                return opc;
            }
            else
            {
                opc = opc + "4";
            }
            length = pc.length();
            if(length>3)
            {
                String temp = "";
                for(int k = 1; k<=3; k++)
                {
                    char d = pc.charAt(length-k);
                    temp = d + temp;
                }
                pc = temp;
                length = pc.length();
            }
            for(int j = 0; j<(3-length); j++)
            {
                pc = "0"+pc;
            }
            opc = opc + pc;
            return opc;
        }
        if(n==1&&i==1)
        {
            int op = Integer.parseInt(opc,16);
            op = op + 3;
            opc = Integer.toHexString(op);
            length = opc.length();
            for(int j = 0; j<(2-length); j++)
            {
                opc = "0"+opc;
            }
            if(e==1)
            {
                opc = opc + "100";
                opc = opc + dir;
                return opc;
            }
            else
            {
                opc = opc + "2";
            }
            length = pc.length();
            if(length>3)
            {
                String temp = "";
                for(int k = 1; k<=3; k++)
                {
                    char d = pc.charAt(length-k);
                    temp = d + temp;
                }
                pc = temp;
                length = pc.length();
            }
            for(int j = 0; j<(3-length); j++)
            {
                pc = "0"+pc;
            }
            opc = opc + pc;
            return opc;
        }
        if(n==1&&i==0)
        {
            int op = Integer.parseInt(opc,16);
            op = op + 2;
            opc = Integer.toHexString(op);
            if(e==1)
            {
                opc = opc + "100";
                opc = opc + dir;
            }
            else
            {
                opc = opc + "1";
                opc = opc + pc;
            }
            return opc;
        }
        if(n==0&&i==1)
        {
            int op = Integer.parseInt(opc,16);
            op = op + 1;
            opc = Integer.toHexString(op);
            if(e==1)
            {
                opc = opc + "100";
                opc = opc + dir;
            }
            else
            {
                opc = opc + "2";
                opc = opc + pc;
            }
            return opc;
        }
        return opc;
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
    private int o;
    public Item(String string, int ii)
    {
        s = string;
        i = ii;
    }
    public int getOpcode()
    {
        return o;
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