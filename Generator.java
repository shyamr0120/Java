import java.util.*;
import java.io.*;
 public class Generator
 {
    public static Scanner s;
    public void main()
    {
        try
        {
            File file = new File("test10.txt");
            s = new Scanner(file);
        }
        catch(Exception e)
        {
            System.out.println("FILE NOT FOUND");
            System.exit(1);
        }
        ArrayList<Token> x = new ArrayList<>();
        Scannr.scanner(x);
        Parser.parser(x);
    }
    static class Scannr
    {
        public static void scanner(ArrayList<Token> x)
        {
            int skip = 0;
            String spec[] = {"+","-","*","/","<","<=",">",">=","==","!=",
            "=",";",",","(",")","[","]","{","}"};
            String spec2[] = {"+","-","*","<","<=",">",">=","==","!=","=","/"};
            String delim[] = {";",",","(",")","[","]","{","}"};
            String key[] = {"else","if","int","float","return","void","while"};
            int clear = 0;
            while(s.hasNext())
            {
                int exp = 0;
                String w = "";
                String line = s.nextLine();
                if(line.trim().length() == 0)
                {
                    continue;
                }
                //System.out.println("INPUT: "+line);
                line = line.trim();
                line = line + " ";
                for(int i = 0; i<line.length(); i++)
                {
                    int next = 0;
                    char c = line.charAt(i);
                    w = w + c;
                    if(w.equals("/"))
                    {
                        String w2 = w + line.charAt(i+1);
                        if(w2.equals("//"))
                        {
                            break;
                        }
                        if(w2.equals("/*"))
                        {
                            skip++;
                            i++;
                            w = "";
                            continue;
                        }
                    }
                    if(skip>0)
                    {
                        if(w.equals("*"))
                        {
                            String w2 = w + line.charAt(i+1);
                            if(w2.equals("*/"))
                            {
                                skip--;
                                i++;
                                w = "";
                                continue;
                            }
                        }
                    }
                    if(w.equals(" "))
                    {
                        w = "";
                        continue;
                    }
                    if(skip>0)
                    {
                        w = "";
                        continue;
                    }
                    if(c == ' ')
                    {
                        w = w.trim();
                        clear = 1;
                    }
                    for(int j = 0; j<spec.length; j++)
                    {
                        int skip2 = 0;
                        if(w.equals(spec[j]))
                        {
                            if(w.equals("<")||w.equals("=")||w.equals(">"))
                            {
                                String ww = w + line.charAt(i+1);
                                for(int k = 0; k<spec.length; k++)
                                {
                                    if(ww.equals(spec[k]))
                                    {
                                        //System.out.println(ww);
                                        int skip3 = 0;
                                        for(int l = 0; l<delim.length; l++)
                                        {
                                            String d = delim[l];
                                            if((String.valueOf(c)).equals(d))
                                            {
                                                Token data = new Token(String.valueOf(c), 1);
                                                x.add(data);
                                                skip3 = 1;
                                            }
                                        }
                                        if(skip3 == 1)
                                        {
                                            break;
                                        }
                                        Token data = new Token(ww, 2);
                                        x.add(data);
                                        i++;
                                        skip2 = 1;
                                        break;
                                    }
                                }
                            }
                            if(skip2 == 1)
                            {
                                w = "";
                                next = 1;
                                break;
                            }
                            //System.out.println(w);
                            int skip3 = 0;
                            for(int l = 0; l<delim.length; l++)
                            {
                                String d = delim[l];
                                if((String.valueOf(c)).equals(d))
                                {
                                    Token data = new Token(String.valueOf(c), 1);
                                    x.add(data);
                                    skip3 = 1;
                                }
                            }
                            if(skip3 == 0)
                            {
                                Token data = new Token(w, 2);
                                x.add(data);
                            }
                            w = "";
                            next = 1;
                            break;
                        }
                    }
                    for(int j = 0; j<key.length; j++)
                    {
                        if(w.equals(key[j]) && !Character.isDigit(line.charAt(i+1)) && !Character.isLetter(line.charAt(i+1)))
                        {
                            //System.out.println("KEYWORD: "+w);
                            Token data = new Token(w, 3);
                            x.add(data);
                            for(int k = 0; k<delim.length; k++)
                            {
                                String d = delim[k];
                                if((String.valueOf(c)).equals(d))
                                {
                                    data = new Token(String.valueOf(c), 1);
                                    x.add(data);
                                }
                            }
                            w = "";
                            next = 1;
                            break;
                        }
                    }
                    for(int j = 0; j<delim.length; j++)
                    {
                        if(w.equals(delim[j]))
                        {
                            Token data = new Token(w, 1);
                            x.add(data);
                            w = "";
                            next = 1;
                            break;
                        }
                    }
                    if(next == 1)
                    {
                        continue;
                    }
                    int trim = 0;
                    String cc = Character.toString(c);
                    String temp = ""; 
                    if(cc.equals(".") && w.chars().allMatch(Character::isLetter)==false)
                    {
                        exp = 1;
                        continue;
                    }
                    for(int j = 0; j<spec.length; j++)
                    {
                        if((cc.equals("+")||cc.equals("-")) && exp == 1)
                        {
                            break;
                        }
                        if(cc.equals(spec[j]))
                        {
                            for(int k = 0; k<w.length()-1; k++)
                            {
                                temp = temp + w.charAt(k);
                            }
                            trim = 1;
                            clear = 1;
                            break;
                        }   
                    }
                    if(trim == 0)
                    {
                        temp = w;
                    }
                    if(clear == 1)
                    {
                        clear = 0;
                        if(exp == 1 && temp.chars().allMatch(Character::isLetter)==false)
                        {
                            //System.out.println("NUM: "+temp);
                            Token data = new Token(temp, 4);
                            x.add(data);
                            for(int k = 0; k<delim.length; k++)
                            {
                                String d = delim[k];
                                if((String.valueOf(c)).equals(d))
                                {
                                    data = new Token(String.valueOf(c), 1);
                                    x.add(data);
                                }
                            }
                            exp = 0;
                            w = "";
                            continue;
                        }
                        try
                        {
                            Integer.parseInt(temp);
                            //System.out.println("NUM: "+temp);
                            Token data = new Token(temp, 4);
                            x.add(data);
                            for(int k = 0; k<delim.length; k++)
                            {
                                String d = delim[k];
                                if((String.valueOf(c)).equals(d))
                                {
                                    data = new Token(String.valueOf(c), 1);
                                    x.add(data);
                                }
                            }
                            for(int k = 0; k<spec2.length; k++)
                            {
                                String d = spec2[k];
                                if((String.valueOf(c)).equals(d))
                                {
                                    char f = line.charAt(i+1);
                                    if(f == '=' || f == '>' || f== '<')
                                    {
                                        data = new Token(String.valueOf(c)+f, 2);
                                        x.add(data);
                                        i++;
                                        break;
                                    }
                                    data = new Token(String.valueOf(c), 2);
                                    x.add(data);
                                }
                            }
                            w = "";
                        }
                        catch(Exception e)
                        {
                            boolean check = temp.chars().allMatch(Character::isLetter);
                            if(check == true)
                            {
                                //System.out.println("ID: "+temp);
                                Token data = new Token(temp, 5);
                                x.add(data);
                                for(int k = 0; k<delim.length; k++)
                                {
                                    String d = delim[k];
                                    if((String.valueOf(c)).equals(d))
                                    {
                                        data = new Token(String.valueOf(c), 1);
                                        x.add(data);
                                    }
                                }
                                for(int k = 0; k<spec2.length; k++)
                                {
                                    String d = spec2[k];
                                    if((String.valueOf(c)).equals(d))
                                    {
                                        char f = line.charAt(i+1);
                                        if(f == '=' || f == '>' || f== '<')
                                        {
                                            data = new Token(String.valueOf(c)+f, 2);
                                            x.add(data);
                                            i++;
                                            break;
                                        }
                                        data = new Token(String.valueOf(c), 2);
                                        x.add(data);
                                    }
                                }
                            }
                            else
                            {
                                String t = "";
                                String y = "";
                                for(int j = 0; j<temp.length(); j++)
                                {
                                    t = "";
                                    t = t + temp.charAt(j);
                                    check = t.matches("[a-zA-Z0-9]*");
                                    if(temp.charAt(j) == '.')
                                    {
                                        y = y + temp.charAt(j);
                                        continue;
                                    }
                                    if(check == false)
                                    {
                                        if(x.equals(""))
                                        {
                                        }
                                        else
                                        {
                                            check = y.chars().allMatch(Character::isLetter);
                                            if(check == true)
                                            {
                                                //System.out.println("ID: "+x);
                                                Token data = new Token(temp, 5);
                                                x.add(data);
                                            }
                                            else
                                            {
                                                //System.out.println("NUM: "+x);
                                                Token data = new Token(temp, 4);
                                                x.add(data);
                                            }
                                            y = "";
                                        }
                                        //System.out.println("ERROR: "+temp.charAt(j));
                                        System.out.println("REJECT");
                                        System.exit(1);
                                        continue;
                                    }
                                    y = y + temp.charAt(j);
                                }
                                if(y.equals(""))
                                {
                                }
                                else
                                {
                                    check = y.chars().allMatch(Character::isLetter);
                                    if(check == true)
                                    {
                                        //System.out.println("ID: "+x);
                                        Token data = new Token(temp, 5);
                                        x.add(data);
                                    }
                                    else
                                    {
                                        //System.out.println("NUM: "+x);
                                        Token data = new Token(temp, 4);
                                        x.add(data);
                                    }
                                    for(int k = 0; k<delim.length; k++)
                                    {
                                        String d = delim[k];
                                        if((String.valueOf(c)).equals(d))
                                        {
                                            Token data = new Token(String.valueOf(c), 1);
                                            x.add(data);
                                        }
                                    }
                                }
                            }
                            w = "";
                        }   
                    }
                }
            }
        }
    }
    static class Parser
    {    
        public static int index = 0;
        public static int num = 0;
        public static int fn = 0;
        public static int temp = 0;
        public static int count = 0;
        public static int bpw = 0;
        public static int bpo = 0;
        public static int bpe = 0;
        public static int br = 0;
        public static int brw = 0;
        public static int bf = 0;
        public static int bif = 0;
        public static int bif2 = 0;
        public static int belse = 0;
        public static int breturn = 0;
        public static int bcall = 0;
        public static int bcall2 = 0;
        public static int atrue = 0;
        public static int bparam = 0;
        public static int barray = 0;
        public static int barray2 = 0;
        public static int buffer = 0;
        public static int paren = 0;
        public static int skip = 0;
        public static String quad1 = "";
        public static String quad2 = "";
        public static String quad3 = "";
        public static String quad4 = "";
        public static String quad3f = "";
        public static String arrayt = "";
        public static ArrayList<Quadruple> hold;
        public static ArrayList<Quadruple> hold2;
        public static ArrayList<String> functions;
        public static ArrayList<String> print;
        public static Quadruple[] prez;
        public static void parser(ArrayList<Token> x)
        {
            //System.out.println(x);
            hold = new ArrayList<>();
            hold2 = new ArrayList<>();
            functions = new ArrayList<>();
            print = new ArrayList<>();
            Token $ = new Token("$", 7);
            x.add($);
            A(x);
            for(int i = 0; i<print.size(); i++)
            {
                System.out.println(print.get(i));
            }
            //System.out.println("ACCEPT");
            //System.exit(1);
        }
        
        public static void A(ArrayList<Token> x)
        {
            B(x);
        }
        
        public static void B(ArrayList<Token> x)
        {
            try
            {
                if(((x.get(index).getToken()).equals("$"))||(x.get(index+1).getToken()).equals("$"))
                {
                    if(bf>0)
                        {
                                print.add(++num+"      end       func       "+quad3f);
                                bf--;
                        }
                    for(int i = 0; i<print.size(); i++)
                    {
                        System.out.println(print.get(i));
                    }
                    //System.out.println("ACCEPT");
                    System.exit(1);
                }
                else
                {
                    quad1 = "func";
                    C(x);
                    E(x);
                    D(x);
                    index++;
                    try
                    {
                        if(bf>0)
                        {
                            if(((x.get(index).getToken().equals("int")) || (x.get(index).getToken().equals("float"))
                            || (x.get(index).getToken().equals("void")) || (x.get(index).getToken().equals("$"))))
                            {
                                print.add(++num+"      end       func       "+quad3f);
                                bf--;
                            }
                            else
                            {
                                P(x);
                            }
                        }
                    }
                    catch(Exception e)
                    {
                        print.add(++num+"      end       func       "+quad3f);
                        bf--;
                    }
                    B(x);
                }
            }
            catch(Exception e)
            {
                for(int i = 0; i<print.size(); i++)
                {
                    System.out.println(print.get(i));
                }
                System.exit(1);
            }
        }
        
        public static void C(ArrayList<Token> x)
        {
            if(!(((x.get(index).getToken()).equals("int")) || ((x.get(index).getToken()).equals("void")) || ((x.get(index).getToken()).equals("float"))))
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
            if(quad1.equals("func"))
            {
                quad3 = x.get(index).getToken();
            }
            index++;
        }
        
        public static void D(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("$")))
            {
                //System.out.println("ACCEPT");
                //System.exit(1);
            }
            else
            {
                if(((x.get(index).getToken()).equals("int")) ||((x.get(index).getToken()).equals("void")) ||((x.get(index).getToken()).equals("float")))
                {
                    C(x);
                    E(x);
                    D(x);
                }
            }
        }
        
        public static void E(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("$")))
            {
                //System.out.println("ACCEPT");
                //System.exit(1);
            }
            else
            {
                if((x.get(index).getType()) == 5)
                {
                    quad2 = x.get(index).getToken();
                    if(quad1.equals("func"))
                    {
                        quad3f = quad2;
                    }
                    index++;
                    F(x);
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
        }
        
        public static void F(ArrayList<Token> x)
        {
            if((x.get(index).getToken().equals("(")))
            {
                if(quad1.equals("func"))
                {
                    if(quad2.equals("main"))
                    {
                        quad4 = "0";
                    }
                    else
                    {
                        functions.add(quad2);
                        quad4 = Integer.toString(++fn);
                        functions.add(Integer.toString(fn));
                    }
                    print.add(++num+"      "+quad1+"      "+quad2+
                    "      "+quad3+"      "+quad4);
                    quad1 = "";
                    quad2 = "";
                    quad3 = "";
                    quad4 = "";
                    bf++;
                }
                index++;
                H(x);
                L(x);
            }
            else
            {
                G(x);
            }
        }
        
        public static void G(ArrayList<Token> x)
        {
            if((x.get(index).getToken().equals("[")))
            {
                if((x.get(index + 1).getType()) == 4 || (x.get(index + 1).getType()) == 5)
                {
                    if((x.get(index + 1).getType()) == 4 )
                    {
                        int amult = Integer.parseInt(x.get(index + 1).getToken());
                        index++;
                        if((x.get(index + 1).getToken().equals("]")))
                        {
                            amult = 4*amult;
                            index++;
                            if((x.get(index + 1).getToken().equals(";")))
                            {
                                print.add(++num+"      alloc     "+amult+"                  "+x.get(index-3).getToken());
                                index = index + 2;
                            }
                            else
                            {
                                //System.out.println("REJECT");
                                //System.exit(1);
                            }
                        }
                    }
                    else if((x.get(index + 1).getType()) == 5 )
                    {
                        String amult = x.get(index+1).getToken();
                        index++;
                        if((x.get(index + 1).getToken().equals("]")))
                        {
                            print.add(++num+"      mult      "+amult+"          4       "+"t"+Integer.toString(temp++));
                            index++;
                            if((x.get(index + 1).getToken().equals(";")))
                            {
                                arrayt = "t"+Integer.toString(temp++);
                                print.add(++num+"      disp      "+quad2+"          "+"t"+Integer.toString(temp-2)+"       "+arrayt);
                                print.add(++num+"      alloc     "+arrayt+"                  "+amult);
                                index = index + 2;
                            }
                            else
                            {
                                //System.out.println("REJECT");
                                //System.exit(1);
                            }
                        }
                    }
                    else
                    {
                        //System.out.println("REJECT");
                        //System.exit(1);
                    }
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else if((x.get(index).getToken().equals(";")))
            {
                quad3 = "       ";
                print.add(++num+"      "+quad1+"     "+quad2+
                "      "+quad3+"      "+quad4);
                quad1 = "";
                quad2 = "";
                quad3 = "";
                quad4 = "";
                index++;
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }
        
        public static void H(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("void")))
            {
                if(((x.get(index + 1).getToken()).equals(")")))
                {
                    index = index + 2;
                }
            }
            else if(((x.get(index).getToken()).equals("int")) || ((x.get(index).getToken()).equals("float")))
            {
                quad1 = "param";
                I(x);
                J(x);
                if((x.get(index).getToken()).equals(")"))
                {
                    while(bparam>0)
                    {
                        String bpam = x.get((index-4)-3*(--bparam)).getToken();
                        print.add(++num+"      alloc     4                   "+bpam);
                    }
                    quad1 = "alloc";
                    quad3 = "       ";
                    print.add(++num+"      "+quad1+"     4      "+quad3+"      "+quad4);
                    quad1 = "";
                    quad2 = "";
                    quad3 = "";
                    quad4 = "";
                    index++;
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
        }
        
        public static void I(ArrayList<Token> x)
        {
            C(x);
            if((x.get(index).getType()) == 5)
            {
                quad4 = x.get(index).getToken();
                print.add(++num+"      "+quad1+"                         "+quad4);
                quad1 = "";
                quad2 = "";
                quad3 = "";
                index++;
                K(x);
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }
        
        public static void J(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals(","))
            {
                quad1 = "param";
                bparam++;
                index++;
                I(x);
            }
        }
        
        public static void K(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("["))
            {
                if((x.get(index + 1).getToken()).equals("]"))
                {
                    index = index + 2;
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
        }
        
        public static void L(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("{"))
            {
                index++;
                N(x);
                P(x);
                index--;
                if((x.get(index + 1).getToken()).equals("}"))
                {
                    if(br>0)
                    {
                        quad1 = "end";
                        quad2 = "block";
                        print.add(++num+"      "+quad1+"       "+quad2);
                        br--;
                        quad1 = "BR";
                        print.add(++num+"      "+quad1+"                            "+bpw);
                        brw = num + 1;
                        String temper = print.get(bpo-1+buffer)+"                  "+brw;
                        print.set(bpo-1+buffer, temper);
                        quad1 = "";
                        quad2 = "";
                        quad3 = "";
                        quad4 = "";
                    }
                    if(bif2>0)
                    {
                        String temper = print.get(bpe-1+buffer)+"                  "+(num+1);
                        print.set(bpe-1+buffer, temper);
                        bif2--;
                    }
                    index++;
                    if(((x.get(index).getToken()).equals("$")))
                    {
                        //System.out.println("ACCEPT");
                        //System.exit(1);
                    }
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }
        
        public static void N(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("int")) || ((x.get(index).getToken()).equals("void")) || ((x.get(index).getToken()).equals("float")))
            {
                quad1 = "alloc";
                if((x.get(index).getToken()).equals("int"))
                {
                    quad2 = "4";
                }
                C(x);
                if((x.get(index).getType() == 5))
                {
                    quad4 = x.get(index).getToken();
                    index++;
                    G(x);
                    N(x);
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
        }
        
        public static void O(ArrayList<Token> x)
        {
            if(((x.get(index).getType()) == 5) || ((x.get(index).getToken()).equals("(")))
            {
                for(int i = 0; i<functions.size(); i++)
                {
                    String bc = functions.get(i);
                    if(bc.equals((x.get(index).getToken())))
                    {
                        bcall2++;
                        break;
                    }
                }
                Q(x);
            }
            else if((x.get(index).getToken()).equals("{"))
            {
                bpo = num + 1;
                print.add("bpo = "+(num+1));
                buffer++;
                quad1 = "BRLEQ";
                quad2 = "t"+Integer.toString(temp-1);
                print.add(++num+"      "+quad1+"     "+quad2);
                quad1 = "";
                quad2 = "";
                quad3 = "";
                quad4 = "";
                quad1 = "block";
                print.add(++num+"      "+quad1);
                quad1 = "";
                quad2 = "";
                quad3 = "";
                quad4 = "";
                L(x);
            }
            else if((x.get(index).getToken()).equals("if"))
            {
                bif++;
                bif2++;
                R(x);
            }
            else if((x.get(index).getToken()).equals("else"))
            {
                belse++;
                quad1 = "br";
                bpo = num + 1;
                print.add("bpo = "+bpo);
                print.add(++num+"      "+quad1);
                String temper = print.get(bpe-1+buffer)+"                  "+(num+1);
                print.set(bpe-1+buffer, temper);
                buffer++;
                quad1 = "";
                quad2 = "";
                quad3 = "";
                quad4 = "";
                S(x);
            }
            else if((x.get(index).getToken()).equals("while"))
            {
                br++;
                T(x);
            }
            else if((x.get(index).getToken()).equals("return"))
            {
                U(x);
            }
        }
        
        public static void P(ArrayList<Token> x)
        {
            if(((x.get(index).getType()) == 4) || ((x.get(index).getType()) == 5) ||
            ((x.get(index).getToken()).equals("if")) || ((x.get(index).getToken()).equals("while")) ||
            ((x.get(index).getToken()).equals("return")) || ((x.get(index).getToken()).equals(";")) ||
            ((x.get(index).getToken()).equals("(")) || ((x.get(index).getToken()).equals("{")))
            {
                O(x);
                P(x);
            }
        }
        
        public static void Q(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals(";"))
            {
                index++;
            }
            else
            {
                V(x);
                if((x.get(index).getToken()).equals(";"))
                {
                    if(!(hold.isEmpty()))
                    {
                        paren = 0;
                        X(x);
                    }
                    if(breturn > 0)
                    {
                        quad1 = "return";
                        quad4 = "t"+Integer.toString(temp-1);
                        print.add(++num+"      "+quad1+"                       "+quad4);
                        quad1 = "";
                        quad2 = "";
                        quad3 = "";
                        quad4 = "";
                        breturn--;
                    }
                    index++;
                    if((x.get(index).getToken()).equals("else"))
                    {
                        O(x);
                    }
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
        }
        
        public static void R(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("if"))
            {
                if((x.get(index + 1).getToken()).equals("("))
                {
                    index++;
                    V(x);
                    if((x.get(index - 1).getToken()).equals(")"))
                    {
                        S(x);
                    }
                    else
                    {
                        //System.out.println("REJECT");
                        //System.exit(1);
                    }
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }
        
        public static void S(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("else"))
            {
                index++;
                O(x);
                X(x);
                belse--;
                bif2--;
                String temper = print.get(bpo-1+buffer)+"                            "+(num+1);
                print.set(bpo-1+buffer, temper);
            }
            else
            {
                O(x);
            }
        }
        
        public static void T(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("while"))
            {
                if((x.get(index + 1).getToken()).equals("("))
                {
                    bpw = num + 1;
                    print.add("bpw = "+(num+1));
                    buffer++;
                    index++;
                    V(x);
                    O(x);
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }
        
        public static void U(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("return"))
            {
                breturn++;
                index++;
                Q(x);
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }
        
        public static void V(ArrayList<Token> x)
        {
            if((x.get(index).getType()) == 5)
            {
                if((x.get(index-1).getToken()).equals("="))
                {
                    quad4 = quad2;
                    if((x.get(index-2).getToken()).equals("]"))
                    {
                        quad4 = x.get(index-5).getToken();
                    }
                }
                if(bcall>0)
                {
                    String nfunc = "";
                    if(x.get(index).getType()==4||x.get(index).getType()==5)
                    {
                        print.add(++num+"      arg                           "+x.get(index).getToken());
                    }
                    while((x.get(index+1).getToken()).equals(","))
                    {
                        index = index + 2;
                        print.add(++num+"      arg                           "+x.get(index).getToken());
                    }
                    bcall--;
                    for(int i = 0; i<functions.size(); i++)
                    {
                        String bc = functions.get(i);
                        if(bc.equals(quad2))
                        {
                            nfunc = functions.get(i+1);
                            break;
                        }
                    }
                    print.add(++num+"      call      "+quad2+
                    "        "+nfunc+"        "+"t"+Integer.toString(temp++));
                    atrue = 1;
                }
                if(barray>0)
                {
                    char c = x.get(index).getToken().charAt(0);
                    if(Character.isDigit(c)==true)
                    {
                        int amult = Integer.parseInt(x.get(index).getToken());
                        amult = 4*amult;
                        arrayt = "t"+Integer.toString(temp++);
                        print.add(++num+"      disp      "+quad2+"          "+amult+"       "+arrayt);
                    }
                    else
                    {
                        String amult = x.get(index).getToken();
                        print.add(++num+"      mult      "+amult+"          4       "+"t"+Integer.toString(temp++));
                        arrayt = "t"+Integer.toString(temp++);
                        print.add(++num+"      disp      "+quad2+"          "+"t"+Integer.toString(temp-2)+"       "+arrayt);
                    }
                }
                quad2 = x.get(index).getToken();
                index++;
                W(x);
            }
            else if((x.get(index).getToken()).equals("("))
            {
                if((x.get(index-1).getToken()).equals("="))
                {
                    quad4 = quad2;
                }
                index++;
                V(x);
                if((x.get(index).getToken()).equals(")"))
                {
                    if(bif > 0)
                    {
                        bpe = num + 1;
                        print.add("bpe = "+bpe);
                        quad1 = "brle";
                        quad2 = "t"+Integer.toString(temp-1);
                        print.add(++num+"      "+quad1+"      "+quad2);
                        buffer++;
                        quad1 = "";
                        quad2 = "";
                        quad3 = "";
                        quad4 = "";
                        bif--;
                    }
                    index++;
                    gamma(x);
                    beta(x);
                    Z(x);
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else if((x.get(index).getType()) == 4)
            {
                if((x.get(index-1).getToken()).equals("="))
                {
                    quad4 = quad2;
                }
                if(barray>0)
                {
                    char c = x.get(index).getToken().charAt(0);
                    if(Character.isDigit(c)==true)
                    {
                        int amult = Integer.parseInt(x.get(index).getToken());
                        amult = 4*amult;
                        arrayt = "t"+Integer.toString(temp++);
                        print.add(++num+"      disp      "+quad2+"          "+amult+"       "+arrayt);
                    }
                    else
                    {
                        String amult = x.get(index).getToken();
                        print.add(++num+"      mult      "+amult+"          4       "+"t"+Integer.toString(temp++));
                        arrayt = "t"+Integer.toString(temp++);
                        print.add(++num+"      disp      "+quad2+"          "+"t"+Integer.toString(temp-2)+"       "+arrayt);
                    }
                }
                if(quad1.equals("assign")&&belse>0)
                {
                    quad4 = quad2;
                }
                quad2 = x.get(index).getToken();
                index++;
                gamma(x);
                beta(x);
                Z(x);
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }
        
        public static void W(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("("))
            {
                if(quad1.equals("assign"))
                {
                    for(int i = 0; i<functions.size(); i++)
                    {
                        String bc = functions.get(i);
                        if(bc.equals(quad2))
                        {
                            bcall++;
                            break;
                        }
                    }
                }
                index++;
                theta(x);
                if((x.get(index).getToken()).equals(")"))
                {
                    if(bcall2>0)
                    {
                        String nfunc = "";
                        if(x.get(index).getType()==4||x.get(index).getType()==5)
                        {
                            print.add(++num+"      arg                           "+x.get(index).getToken());
                        }
                        while((x.get(index+1).getToken()).equals(","))
                        {
                            index = index + 2;
                            print.add(++num+"      arg                           "+x.get(index).getToken());
                        }
                        bcall2--;
                        for(int i = 0; i<functions.size(); i++)
                        {
                            String bc = functions.get(i);
                            if(bc.equals(quad2))
                            {
                                nfunc = functions.get(i+1);
                                break;
                            }
                        }
                        print.add(++num+"      call      "+quad2+
                        "        "+nfunc+"        "+"t"+Integer.toString(temp++));
                    }
                    index++;
                    gamma(x);
                    beta(x);
                    Z(x);
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else
            {
                Y(x);
                X(x);
            }
        }
        
        public static void X(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("="))
            {
                quad1 = "assign";
                index++;
                V(x);
            }
            else
            {
                if(quad1.equals("assign"))
                {
                    quad3 = "";
                    Quadruple q = new Quadruple(quad1, quad2, quad3, quad4);
                    hold.add(q);
                    count++;
                }
                gamma(x);
                beta(x);
                Z(x);
                prez = new Quadruple[count];
                int assn = 0;
                String assn4 = "";
                int assn2 = 1;
                int assnd = 0;
                String assn2s = "";
                int compr = 0;
                int compr2 = 0;
                int compr3 = 0;
                String comprs = "";
                String comprs2 = "";
                String comprs3 = "";
                int cot = 0;
                for(int i = 0; i<count; i++)
                {
                    prez[i] = hold.get(i);
                    cot = 1;
                }
                if(cot == 0)
                {
                    return;
                }
                String triumph = hold.get(hold.size()-1).getQuad1();
                hold.clear();
                for(int i = 0; i<count; i++)
                {
                    Quadruple op = prez[i];
                    if(op.getQuad1().equals("mult")||op.getQuad1().equals("div"))
                    {
                        op.setQuad4("t"+Integer.toString(temp++));
                        try
                        {
                            Quadruple op2 = prez[i-1];
                            if(!(op2.getQuad4().equals("")))
                            {
                                if(op2.getQuad1().equals("mult")||op2.getQuad1().equals("div"))
                                {
                                    op.setQuad2(op2.getQuad4());
                                }
                            }
                        }
                        catch(Exception e)
                        {
                        }
                        hold.add(op);
                        print.add(++num+"      "+op.getQuad1()+"       "+op.getQuad2()+
                        "         "+op.getQuad3()+"        "+op.getQuad4());
                    }
                }
                for(int i = 0; i<count; i++)
                {
                    Quadruple op = prez[i];
                    if(!(op.getQuad1().equals("mult")||op.getQuad1().equals("div")))
                    {
                        if(op.getQuad1().equals("assign"))
                        {
                            assn = 1;
                            assn4 = op.getQuad4();
                            if(assn4.equals(""))
                            {
                                assnd = 1;
                                continue;
                            }
                            if(i<count-1)
                            {
                                Quadruple op3 = prez[i+1];
                                if(!(op3.getQuad4()==""))
                                {
                                    assn2 = 0;
                                }
                            }
                            assn2s = op.getQuad2();
                            continue;
                        }
                        if(op.getQuad1().equals("comp"))
                        {
                            compr = 1;
                            comprs3 = op.getQuad3();
                            comprs = op.getQuad2();
                            if(i>0)
                            {
                                Quadruple op2 = prez[i-1];
                                if(!(op2.getQuad4()==""))
                                {
                                    comprs = "t"+Integer.toString(temp-1);
                                }
                            }
                            if(i<count-1)
                            {
                                compr2 = 1;
                                //System.out.println(hold);
                                if(triumph.equals("add")||triumph.equals("sub"))
                                {
                                    compr3 = 1;
                                    continue;
                                }
                                for(int j = hold.size()-1; j>=0; j--)
                                {
                                    if(((hold.get(j)).getQuad1()).equals("mult") || (((hold.get(j)).getQuad1()).equals("div")))
                                    {
                                        comprs2 = hold.get(j).getQuad4();
                                        break;
                                    }
                                }
                            }
                            /*op.setQuad4("t"+Integer.toString(temp++));
                            hold.add(op);
                            print.add(++num+"      "+op.getQuad1()+"      "+op.getQuad2()+
                            "        "+op.getQuad3()+"         "+op.getQuad4());*/
                            continue;
                        }
                        if(i>0)
                        {
                            Quadruple op2 = prez[i-1];
                            if(!(op2.getQuad4()=="")&&!(op2.getQuad1()=="assign")&&!(op2.getQuad1()=="comp"))
                            {
                                op.setQuad2(op2.getQuad4());
                            }
                        }
                        if(i<count-1)
                        {
                            Quadruple op3 = prez[i+1];
                            int j = i+2;
                            while((!(op3.getQuad4()=="")&&(op3.getQuad1()=="mult")||(op3.getQuad1()=="div")==true))
                            {
                                op.setQuad3(op3.getQuad4());
                                if(j==count)
                                {
                                    break;
                                }
                                op3 = prez[j++];
                            }
                        }
                        op.setQuad4("t"+Integer.toString(temp++));
                        hold.add(op);
                        print.add(++num+"      "+op.getQuad1()+"       "+op.getQuad2()+
                        "         "+op.getQuad3()+"        "+op.getQuad4());
                    }
                }
                if(assn == 1 && paren == 0)
                {
                    if(!(arrayt.equals("")))
                    {
                        assn4 = arrayt;
                    }
                        if(assn2 == 1 && atrue == 0)
                        {
                            print.add(++num+"      assign               "+assn2s+
                            "        "+assn4);
                        }
                        else
                        {
                            print.add(++num+"      assign    "+"t"+Integer.toString(temp-1)+
                            "                  "+assn4);
                        }
                    arrayt = "";
                    //System.out.println(++num+"      assign    "+"t"+Integer.toString(temp-1)+
                     //       "                  "+assn4);
                }
                if(compr == 1)
                {
                    if(compr2 == 1)
                    {
                        if(compr3 == 1)
                        {
                            print.add(++num+"      comp      "+comprs+
                            "        "+"t"+Integer.toString(temp-1)+"        "+"t"+Integer.toString(temp++));
                        }
                        else
                        {
                            print.add(++num+"      comp      "+comprs+
                            "        "+comprs2+"        "+"t"+Integer.toString(temp++));
                        }
                    }
                    else
                    {
                        print.add(++num+"      comp      "+comprs+
                        "         "+comprs3+"        "+"t"+Integer.toString(temp++));
                    }
                    arrayt = "";
                }
                atrue = 0;
                hold.clear();
                count = 0;
                if(paren>0 && assnd == 0)
                {
                    if(assn2 == 1 && atrue == 0)
                    {
                        Quadruple op = new Quadruple("assign", "", assn2s, assn4);
                        hold.add(op);
                    }
                    else
                    {
                        Quadruple op = new Quadruple("assign", "", "", assn4);
                        hold.add(op);
                    }
                    paren--;
                    count++;
                }
                arrayt = "";
                quad1 = "";
                quad2 = "";
                quad3 = "";
                quad4 = "";
            }
        }
        
        public static void Y(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("["))
            {
                barray++;
                barray2++;
                quad2 = x.get(index-1).getToken();
                index++;
                V(x);
                if((x.get(index).getToken()).equals("]"))
                {
                    index++;
                    barray--;
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
        }
        
        public static void Z(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("<")) || ((x.get(index).getToken()).equals(">")) ||
            ((x.get(index).getToken()).equals("<=")) || ((x.get(index).getToken()).equals(">=")) ||
            ((x.get(index).getToken()).equals("==")) || ((x.get(index).getToken()).equals("!=")) /*|| 
            ((x.get(index+1).getToken()).equals("+")) || ((x.get(index+1).getToken()).equals("-")) || 
            ((x.get(index+1).getToken()).equals("*")) || ((x.get(index+1).getToken()).equals("/"))*/)
            {
               
                /*if(((x.get(index+1).getToken()).equals("+")) || ((x.get(index+1).getToken()).equals("-")) || 
                ((x.get(index+1).getToken()).equals("*")) || ((x.get(index+1).getToken()).equals("/")))
                {
                    paren++;
                    X(x);
                }*/
                alpha(x);
                omega(x);
                gamma(x);
                beta(x);
            }
        }
        
        public static void alpha(ArrayList<Token> x)
        {
            if(!(((x.get(index).getToken()).equals("<")) || ((x.get(index).getToken()).equals(">")) ||
            ((x.get(index).getToken()).equals("<=")) || ((x.get(index).getToken()).equals(">=")) ||
            ((x.get(index).getToken()).equals("==")) || ((x.get(index).getToken()).equals("!="))))
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
            if(!quad3.equals(""))
            {
                quad2 = quad3;
            }
            quad1 = "comp";
            index++;
        }

        public static void beta(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("+")) || ((x.get(index).getToken()).equals("-")))
            {
                if(!quad3.equals(""))
                {
                    quad2 = quad3;
                }
                epsilon(x);
                omega(x);
                gamma(x);
                beta(x);
            }
        }

        public static void epsilon(ArrayList<Token> x)
        {
            if(!(((x.get(index).getToken()).equals("+")) || ((x.get(index).getToken()).equals("-"))))
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
            if(((x.get(index).getToken()).equals("+")))
            {
                quad1 = "add";
            }
            else
            {
                quad1 = "sub";
            }
            index++;
        }

        public static void gamma(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("*")) || ((x.get(index).getToken()).equals("/")))
            {
                if(!quad3.equals(""))
                {
                    quad2 = quad3;
                }
                lambda(x);
                omega(x);
                gamma(x);
            }
        }

        public static void lambda(ArrayList<Token> x)
        {
            if(!(((x.get(index).getToken()).equals("*")) || ((x.get(index).getToken()).equals("/"))))
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
            if(((x.get(index).getToken()).equals("*")))
            {
                quad1 = "mult";
            }
            else
            {
                quad1 = "div";
            }
            index++;
        }

        public static void omega(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("("))
            {
                index++;
                V(x);
                if((x.get(index).getToken()).equals(")"))
                {
                    index++;
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else if((x.get(index).getType()) == 5)
            {
                quad3 = x.get(index).getToken();
                if((x.get(index-2).getToken()).equals("]") || (x.get(index-2).getToken()).equals(")"))
                {
                    paren = 1;
                    quad2 = "t"+Integer.toString(temp-1);
                }
                Quadruple q = new Quadruple(quad1, quad2, quad3, quad4);
                hold.add(q);
                index++;
                count++;
                pi(x);
            }
            else if((x.get(index).getType()) == 4)
            {
                quad3 = x.get(index).getToken();
                if((x.get(index-2).getToken()).equals("]") || (x.get(index-2).getToken()).equals(")"))
                {
                    paren = 1;
                    quad2 = "t"+Integer.toString(temp-1);
                }
                Quadruple q = new Quadruple(quad1, quad2, quad3, quad4);
                hold.add(q);
                index++;
                count++;
            }
            else
            {
                //System.out.println("REJECT");
                //System.exit(1);
            }
        }

        public static void pi(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("("))
            {
                index++;
                theta(x);
                if((x.get(index).getToken().equals(")")))
                {
                    index++;
                }
                else
                {
                    //System.out.println("REJECT");
                    //System.exit(1);
                }
            }
            else
            {
                Y(x);
            }
        }

        public static void theta(ArrayList<Token> x)
        {
            if(((x.get(index).getType()) == 5) || ((x.get(index).getToken()).equals("(")) ||
            ((x.get(index).getType()) == 4))
            {
                V(x);
                zeta(x);
            }
        }

        public static void zeta(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals(","))
            {
                index++;
                V(x);
                zeta(x);
            }
        }
    }
    static class Token
    {
        String token;
        int type;
        public Token(String t, int ty)
        {
            token = t;
            type = ty;
        }

        public String getToken()
        {
            return token;
        }

        public int getType()
        {
            return type;
        }
        
        public String toString()
        {
            return "\n" + getType() + " " +getToken()+ "\n";
        }
    }
    static class Quadruple
    {
        String quad1 = "";
        String quad2 = "";
        String quad3 = "";
        String quad4 = "";
        
        public Quadruple(String q1, String q2, String q3, String q4)
        {
            quad1 = q1;
            quad2 = q2;
            quad3 = q3;
            quad4 = q4;
        }
        
        public String getQuad1()
        {
            return quad1;
        }
        
        public String getQuad2()
        {
            return quad2;
        }
        
        public String getQuad3()
        {
            return quad3;
        }
        
        public String getQuad4()
        {
            return quad4;
        }
        
        public void setQuad1(String q1)
        {
            quad1 = q1;
        }
        
        public void setQuad2(String q2)
        {
            quad2 = q2;
        }
        
        public void setQuad3(String q3)
        {
            quad3 = q3;
        }
        
        public void setQuad4(String q4)
        {
            quad4 = q4;
        }
        
        public String toString()
        {
            return "\n" + getQuad1() + " " + getQuad2() + "  "+ getQuad3() + "  " + getQuad4() + "\n";
        }
    }
}