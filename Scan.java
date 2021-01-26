import java.util.*;
import java.io.*;
class Scan
{
    public static Scanner s;
    void main()
    {
        try
        {
            File file = new File("Test.txt");
            s = new Scanner(file);
        }
        catch(Exception e)
        {
            System.out.println("FILE NOT FOUND");
            System.exit(1);
        }
        int skip = 0;
        String spec[] = {"+","-","*","/","<","<=",">",">=","==","!=",
        "=",";",",","(",")","[","]","{","}"};
        String key[] = {"else","if","int","float","return","void","while"};
        int clear = 0;
        while(s.hasNext())
        {
            String w = "";
            String line = s.nextLine();
            if(line.trim().length() == 0)
            {
                continue;
            }
            System.out.println("INPUT: "+line);
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
                if(w.equals("//"))
                {
                    break;
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
                                    System.out.println(ww);
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
                        System.out.println(w);
                        w = "";
                        next = 1;
                        break;
                    }
                }
                for(int j = 0; j<key.length; j++)
                {
                    if(w.equals(key[j]))
                    {
                        System.out.println("KEYWORD: "+w);
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
                for(int j = 0; j<spec.length; j++)
                {
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
                    try
                    {
                        Integer.parseInt(temp);
                        System.out.println("NUM: "+temp);
                        if(c!=' ')
                        {
                            System.out.println(c);
                        }
                        w = "";
                    }
                    catch(Exception e)
                    {
                        boolean check = temp.chars().allMatch(Character::isLetter);
                        if(check == true)
                        {
                            System.out.println("ID: "+temp);
                            if(c!=' ')
                            {
                                System.out.println(c);
                            }
                        }
                        else
                        {
                            String t = "";
                            String x = "";
                            for(int j = 0; j<temp.length(); j++)
                            {
                                t = "";
                                t = t + temp.charAt(j);
                                check = t.matches("[a-zA-Z0-9]*");
                                if(check == false)
                                {
                                    if(x.equals(""))
                                    {
                                    }
                                    else
                                    {
                                        check = x.chars().allMatch(Character::isLetter);
                                        if(check == true)
                                        {
                                            System.out.println("ID: "+x);
                                        }
                                        else
                                        {
                                            System.out.println("NUM: "+x);
                                        }
                                        x = "";
                                    }
                                    System.out.println("ERROR: "+temp.charAt(j));
                                    continue;
                                }
                                x = x + temp.charAt(j);
                            }
                            if(x.equals(""))
                            {
                            }
                            else
                            {
                                check = x.chars().allMatch(Character::isLetter);
                                if(check == true)
                                {
                                    System.out.println("ID: "+x);
                                }
                                else
                                {
                                    System.out.println("NUM: "+x);
                                }
                                if(c!=' ')
                                {
                                    System.out.println(c);
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