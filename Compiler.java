import java.util.*;
 import java.io.*;
    public class Compiler
    {
        public static Scanner s;
          public static int depth = 0;
        public void main()
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
            ArrayList<Token> x = new ArrayList<>();
            String delim[] = {";",",","(",")","[","]","{","}"};
            LinkedList<Token> tokenReverse = new LinkedList<Token>();
            Scannr.scanner(x, tokenReverse);
            LinkedList<TableToken> store = Table.createTable(tokenReverse);           
            Parser.parser(x);
            Semantic.semantic(store, tokenReverse, delim);
        }
        static class Scannr
        {
            public static void scanner(ArrayList<Token> x, LinkedList<Token> tokenReverse)
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
                                            int skip3 = 0;
                                            for(int l = 0; l<delim.length; l++)
                                            {
                                                String d = delim[l];
                                                
                                                if((String.valueOf(c)).equals(d))
                                                {
                                                    Token data = new Token("DELIMS", "", String.valueOf(c), 
                                                    Compiler.depth);
                                                    x.add(data);
                                                    tokenReverse.addLast(data);
                                                    skip3 = 1;
                                                }
                                            }
                                            if(skip3 == 1)
                                            {
                                                break;
                                            }
                                            Token data = new Token("RELATIONAL", "", String.valueOf(c), 
                                                    Compiler.depth);
                                            x.add(data);
                                            tokenReverse.addLast(data);
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
                                int skip3 = 0;
                                for(int l = 0; l<delim.length; l++)
                                {
                                    String d = delim[l];
                                    
                                    if((String.valueOf(c)).equals(d))
                                    {
                                        if((String.valueOf(c)).equals("{"))
                                        {
                                            depth++;
                                    }
                                    if((String.valueOf(c)).equals("}"))
                                    {
                                        depth--;
                                    }
                                        Token data = new Token("DELIMS", "", String.valueOf(c), 
                                                    Compiler.depth);
                                        x.add(data);
                                        tokenReverse.addLast(data);
                                        skip3 = 1;
                                        
                                    }
                                }
                                if(skip3 == 0)
                                {
                                    Token data = new Token("RELATIONAL", "", String.valueOf(c), 
                                                    Compiler.depth);
                                    x.add(data);
                                    tokenReverse.addLast(data);
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
                                Token data = new Token("KEYWORD", "", w, 
                                                    Compiler.depth);
                                x.add(data);
                                tokenReverse.addLast(data);
                                
                                for(int k = 0; k<delim.length; k++)
                                {
                                    String d = delim[k];
                                    
                                if((String.valueOf(c)).equals(d))
                                {
                                    data = new Token("DELIMS", "", String.valueOf(c), 
                                                Compiler.depth);
                                    x.add(data);
                                    tokenReverse.addLast(data);
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
                            Token data = new Token("DELIMS", "", String.valueOf(c), 
                                                Compiler.depth);
                            x.add(data);
                            tokenReverse.addLast(data);
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
                            Token data = new Token("NUM", x.get(0).getToken(), temp, 
                                                Compiler.depth);
                            x.add(data);
                            tokenReverse.addLast(data);
                            for(int k = 0; k<delim.length; k++)
                            {
                                String d = delim[k];
                                if((String.valueOf(c)).equals(d))
                                {
                                    data = new Token("DELIMS", "", String.valueOf(c), 
                                                Compiler.depth);
                                    x.add(data);
                                    tokenReverse.addLast(data);
                                }
                            }
                            exp = 0;
                            w = "";
                            continue;
                        }
                        try
                        {
                            Integer.parseInt(temp);
                            Token data = new Token("NUM", x.get(0).getToken(), temp, 
                                                Compiler.depth);
                            x.add(data);
                            tokenReverse.addLast(data);
                            for(int k = 0; k<delim.length; k++)
                            {
                                String d = delim[k];
                                if((String.valueOf(c)).equals(d))
                                {
                                    data = new Token("DELIMS", "", String.valueOf(c), 
                                                Compiler.depth);
                                    x.add(data);
                                    tokenReverse.addLast(data);
                                }
                            }
                            w = "";
                        }
                        catch(Exception e)
                        {
                            boolean check = temp.chars().allMatch(Character::isLetter);
                            if(check == true)
                            {
                                Token data = new Token("ID", "", temp, 
                                                Compiler.depth);
                                x.add(data);
                                tokenReverse.addLast(data);
                                for(int k = 0; k<delim.length; k++)
                                {
                                    String d = delim[k];
                                    if((String.valueOf(c)).equals(d))
                                    {
                                        data = new Token("DELIMS", "", String.valueOf(c), 
                                                Compiler.depth);
                                        x.add(data);
                                        tokenReverse.addLast(data);
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
                                            data = new Token("RELATIONAL", "", String.valueOf(c)+f, 
                                                Compiler.depth);
                                            x.add(data);
                                            tokenReverse.addLast(data);
                                            i++;
                                            break;
                                        }
                                        data = new Token("RELATIONAL", "", String.valueOf(c), 
                                                Compiler.depth);
                                        x.add(data);
                                        tokenReverse.addLast(data);
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
                                                Token data = new Token("ID", "", temp, 
                                                Compiler.depth);
                                                x.add(data);
                                                tokenReverse.addLast(data);
                                            }
                                            else
                                            {
                                                Token data = new Token("NUM", x.get(0).getToken(), temp, 
                                                Compiler.depth);
                                                x.add(data);
                                                tokenReverse.addLast(data);
                                            }
                                            y = "";
                                        }
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
                                        Token data = new Token("ID", "", temp, 
                                                Compiler.depth);
                                        x.add(data);
                                        tokenReverse.addLast(data);
                                    }
                                    else
                                    {
                                        Token data = new Token("NUM", x.get(0).getToken(), temp, 
                                                Compiler.depth);
                                        x.add(data);
                                        tokenReverse.addLast(data);
                                    }
                                    for(int k = 0; k<delim.length; k++)
                                    {
                                        String d = delim[k];
                                        if((String.valueOf(c)).equals(d))
                                        {
                                            Token data = new Token("DELIMS", "", String.valueOf(c), 
                                                Compiler.depth);
                                            x.add(data);
                                            tokenReverse.addLast(data);
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
        public static void parser(ArrayList<Token> x)
        {
            Token $ = new Token("EOF", "", "$", 0);
            x.add($);
            A(x);
        }

        public static void A(ArrayList<Token> x)
        {
            B(x);
        }

        public static void B(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("$")))
            {

            }
            else
            {
                C(x);
                E(x);
                D(x);
            }
        }

        public static void C(ArrayList<Token> x)
        {
            if(!(((x.get(index).getToken()).equals("int")) || ((x.get(index).getToken()).equals("void")) || ((x.get(index).getToken()).equals("float"))))
            {

            }
            index++;
        }

        public static void D(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("$")))
            {

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

            }
            else
            {
                if((x.get(index).getType()).equals("ID"))
                {
                    index++;
                    F(x);
                }
            }
        }

        public static void F(ArrayList<Token> x)
        {
            if((x.get(index).getToken().equals("(")))
            {
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
                if((x.get(index + 1).getType()).equals("NUM"))
                {
                    index++;
                    if((x.get(index + 1).getToken().equals("]")))
                    {
                        index++;
                        if((x.get(index + 1).getToken().equals(";")))
                        {
                            index = index + 2;
                        }
                    }
                }
            }
            else if((x.get(index).getToken().equals(";")))
            {
                index++;
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
            else if(((x.get(index).getToken()).equals("int")) || ((x.get(index).getToken()).equals("void")) || ((x.get(index).getToken()).equals("float")))
            {
                I(x);
                J(x);
                if((x.get(index).getToken()).equals(")"))
                {
                    index++;
                }
            }
        }

        public static void I(ArrayList<Token> x)
        {
            C(x);
            if((x.get(index).getType()).equals("ID"))
            {
                index++;
                K(x);
            }
        }

        public static void J(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals(","))
            {
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
                    index++;
                    if(((x.get(index).getToken()).equals("$")))
                    {

                    }
                }
            }
        }

        public static void N(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("int")) || ((x.get(index).getToken()).equals("void")) || ((x.get(index).getToken()).equals("float")))
            {
                C(x);
                if((x.get(index).getType()).equals("ID"))
                {
                    index++;
                    G(x);
                    N(x);
                }
            }
        }

        public static void O(ArrayList<Token> x)
        {
            if(((x.get(index).getType()).equals("ID")) || ((x.get(index).getToken()).equals("(")))
            {
                Q(x);
            }
            else if((x.get(index).getToken()).equals("{"))
            {
                L(x);
            }
            else if((x.get(index).getToken()).equals("if"))
            {
                R(x);
            }
            else if((x.get(index).getToken()).equals("while"))
            {
                T(x);
            }
            else if((x.get(index).getToken()).equals("return"))
            {
                U(x);
            }
        }

        public static void P(ArrayList<Token> x)
        {
            if(((x.get(index).getType()).equals("NUM")) || ((x.get(index).getType()).equals("ID")) ||
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
                    index++;
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
                }
            }
        }

        public static void S(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("else"))
            {
                index++;
                O(x);
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
                    index++;
                    V(x);
                    O(x);
                }
            }
        }

        public static void U(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("return"))
            {
                index++;
                Q(x);
            }
        }

        public static void V(ArrayList<Token> x)
        {
            if((x.get(index).getType()).equals("ID"))
            {
                index++;
                W(x);
            }
            else if((x.get(index).getToken()).equals("("))
            {
                index++;
                V(x);
                if((x.get(index).getToken()).equals(")"))
                {
                    index++;
                    gamma(x);
                    beta(x);
                    Z(x);
                }
            }
            else if((x.get(index).getType()).equals("NUM"))
            {
                index++;
                gamma(x);
                beta(x);
                Z(x);
            }
        }

        public static void W(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("("))
            {
                index++;
                theta(x);
                if((x.get(index).getToken()).equals(")"))
                {
                    index++;
                    gamma(x);
                    beta(x);
                    Z(x);
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
                index++;
                V(x);
            }
            else
            {
                gamma(x);
                beta(x);
                Z(x);
            }
        }

        public static void Y(ArrayList<Token> x)
        {
            if((x.get(index).getToken()).equals("["))
            {
                index++;
                V(x);
                if((x.get(index).getToken()).equals("]"))
                {
                    index++;
                }
            }
        }

        public static void Z(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("<")) || ((x.get(index).getToken()).equals(">")) ||
            ((x.get(index).getToken()).equals("<=")) || ((x.get(index).getToken()).equals(">=")) ||
            ((x.get(index).getToken()).equals("==")) || ((x.get(index).getToken()).equals("!=")))
            {
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

            }
            index++;
        }

        public static void beta(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("+")) || ((x.get(index).getToken()).equals("-")))
            {
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

            }
            index++;
        }

        public static void gamma(ArrayList<Token> x)
        {
            if(((x.get(index).getToken()).equals("*")) || ((x.get(index).getToken()).equals("/")))
            {
                lambda(x);
                omega(x);
                gamma(x);
            }
        }

        public static void lambda(ArrayList<Token> x)
        {
            if(!(((x.get(index).getToken()).equals("*")) || ((x.get(index).getToken()).equals("/"))))
            {

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
            }
            else if((x.get(index).getType()).equals("ID"))
            {
                index++;
                pi(x);
            }
            else if((x.get(index).getType()).equals("NUM"))
            {
                index++;
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
            }
            else
            {
                Y(x);
            }
        }

        public static void theta(ArrayList<Token> x)
        {
            if(((x.get(index).getType()).equals("ID")) || ((x.get(index).getToken()).equals("(")) ||
            ((x.get(index).getType()).equals("NUM")))
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
    
    static class Semantic
    {
        public static void semantic(LinkedList<TableToken> store, LinkedList<Token> tokenReverse, String delim[])
        {
      boolean c1 = A(store);
      boolean c2 = B(store);
      boolean c3 = C(store);
      boolean c4 = D(store, tokenReverse);
      J(store, tokenReverse, delim);
      E(store, tokenReverse);
      F(store, tokenReverse);
      H(store, tokenReverse);
      I(store, tokenReverse);
      G(store);
      if(c1 == true && c2 == true && c3 == true && c4 == true)
      {
        System.out.println("ACCEPT");
        System.exit(1);
      }
      else
      {
        System.out.println("REJECT");
        System.exit(1);
      }
    }


    public static boolean A(LinkedList<TableToken> store)
    {
      int count = 0;
      boolean l = false;

      for(int i = 0; i < store.size(); i++)
      {
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          if((store.get(i).getName()).equals("main"))
          {
            count++;
            if((store.get(i).getCall()).equals("DEFINITION"))
            {
              l = true;
            }
          }
          else
          {
            if((store.get(i).getCall()).equals("DEFINITION"))
            {
              l = false;
            }
          }
        }
      }
      if(count > 1)
      {
        System.out.println("REJECT");
        System.exit(1);
      }
      else if(count == 0)
      {
        System.out.println("REJECT");
        System.exit(1);
      }
      if(l == false)
      {
        System.out.println("REJECT");
        System.exit(1);
      }
      return true;
    }


    public static boolean B(LinkedList<TableToken> store)
    {
      for(int i = 0; i < store.size(); i++)
      {
        int check = 0;
        String function = "";
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          if((store.get(i).getCall()).equals("DEFINITION"))
          {
            check = store.get(i).getNumOfArguments();
            function = store.get(i).getName();
            for(int j = 0; j < store.size(); j++)
            {
              if((store.get(j).getCat()).equals("FUNCTION"))
              {
                if((store.get(j).getName()).equals(function))
                {
                  if((store.get(j).getCall()).equals("CALL"))
                  {
                    if(check == store.get(j).getNumOfArguments())
                    {
                      return true;
                    }
                    else
                    {
                      System.out.println("REJECT");
                      System.exit(1);
                    }
                  }
                }
              }
            }
          }
        }
      }
      return true;
    }


    public static boolean C(LinkedList<TableToken> store)
    {
        boolean f = true;
      boolean function = false;
      boolean variable = false;
      int count = 0;
      int counter = 0;
      for(int i = 0; i < store.size(); i++)
      {
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          count++;
        }
      }
      if(count == store.size())
      {
        variable = true;
      }
      for(int i = 0; i < store.size(); i++)
      {
        if((store.get(i).getCall()).equals("DEFINITION"))
        {
          if((store.get(i).getCat()).equals("VARIABLE"))
          {
            variable = true;
          }
          else if((store.get(i).getCat()).equals("FUNCTION"))
          {
            function = true;
          }
        }
        else
        {
          if((store.get(i).getCat()).equals("VARIABLE"))
          {
            variable = false;
          }
          else if((store.get(i).getCat()).equals("FUNCTION"))
          {
            function = false;
          }
          for(int j = i; j < store.size(); j++)
          {
            String var = "";
            String func = "";
            if((store.get(j).getCat()).equals("FUNCTION"))
            {
              if((store.get(j).getCall()).equals("CALL"))
              {
                func = store.get(j).getName();
                for(int k = 0; k < i; k++)
                {
                  if((store.get(k).getName()).equals(func))
                  {
                    if((store.get(k).getCat()).equals("FUNCTION"))
                    {
                      if((store.get(k).getCall()).equals("DEFINITION"))
                      {
                        function = true;
                      }
                    }
                  }
                }
              }
            }
            else if((store.get(j).getCat()).equals("VARIABLE"))
            {
              if((store.get(j).getCall()).equals("INSTANTIATION"))
              {
                var = store.get(j).getName();
                for(int k = 0; k < i; k++)
                {
                  if((store.get(k).getName()).equals(var))
                  {
                    if((store.get(k).getCat()).equals("VARIABLE"))
                    {
                      counter++;
                      if((store.get(k).getCall()).equals("DEFINITION"))
                      {
                        variable = true;
                      }
                    }
                  }
                }
                if(counter == 0)
                {
                  f = false;
                }
              }
            }
          }
        }
      }
      if(f == false)
      {
        System.out.println("REJECT");
        System.exit(1);
        return false;
      }
      if(function == true && variable == true)
      {
        return true;
      }
      else
      {
        System.out.println("REJECT");
        System.exit(1);
        return false;
      }
    }

     public static boolean D(LinkedList<TableToken> store, LinkedList<Token> tokenReverse)
    {
      boolean check = false;
      int count = 0;
      for(int i = 0; i < store.size(); i++)
      {
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          if((store.get(i).getCall()).equals("CALL"))
          {
            count++;
          }
        }
      }

      if(count > 0)
      {
        for(int i = 0; i < store.size(); i++)
        {
          String function = "";
          ArrayList<Arguments> paramList = new ArrayList<Arguments>();
          paramList.clear();
          if((store.get(i).getCat()).equals("FUNCTION"))
          {
            if((store.get(i).getCall()).equals("DEFINITION"))
            {
              function = store.get(i).getName();
              int counter = i;
              for(int j = 0; j < store.get(i).getNumOfArguments(); j++)
              {
                paramList.add(new Arguments(function, store.get(counter+1).getType(), store.get(counter+1).getType2()));
                counter++;
              }
              for(int k = 0; k < store.size(); k++)
              {
                if((store.get(k).getCat()).equals("FUNCTION"))
                {
                  if((store.get(k).getCall()).equals("CALL"))
                  {
                    if((store.get(k).getName()).equals(function))
                    {
                      int n = store.get(k).getNumOfArguments();
                      int nn = k+1;
                      if(((store.get(k).getType()).equals("void")) && ((paramList.get(0).getType()).equals("void")))
                      {
                        check = true;
                      }
                      else
                      {
                        for(int l = 0; l < n; l++)
                        {
                          if((tokenReverse.get(store.get(k).getIndex() + 1).getNumType()).equals(paramList.get(l).getType()))
                          {
                            check = true;
                          }
                          else
                          {
                            check = false;
                          }
                          if(nn < store.size())
                          {
                            if((store.get(nn).getType()).equals(paramList.get(l).getType()))
                            {
                              if((store.get(nn).getType2()).equals(paramList.get(l).getType2()))
                              {
                                check = true;
                                nn++;
                              }
                              else
                              {
                                int index = store.get(nn).getIndex() + 1;
                                if((tokenReverse.get(index).getToken()).equals("["))
                                {
                                  if((tokenReverse.get(index + 1).getNumType()).equals("int"))
                                  {
                                    check = true;
                                    nn++;
                                  }
                                  else
                                  {
                                    check = false;
                                  }
                                }
                                else
                                {
                                  check = false;
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      else
      {
        check = true;
      }
      if(check == true)
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    public static void E(LinkedList<TableToken> store, LinkedList<Token> tokenReverse)
    {
      boolean flag = false;
      for(int i = 0; i < store.size(); i++)
      {
        String function = "";
        int count = 0;
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          if((store.get(i).getCall()).equals("DEFINITION"))
          {
            if((store.get(i).getType()).equals("void"))
            {
              function = store.get(i).getName();
              for(int j = 0; j < tokenReverse.size(); j++)
              {
                if((tokenReverse.get(j).getToken()).equals(function))
                {
                  if((tokenReverse.get(j-1).getToken()).equals("void"))
                  {
                    for(int k = j+1; k < tokenReverse.size(); k++)
                    {
                      if((tokenReverse.get(k).getToken()).equals("{"))
                          {
                        for(int l = k+1; l < tokenReverse.size(); l++)
                        {
                          if((tokenReverse.get(l).getToken()).equals("return"))
                          {
                            count++;
                            if((tokenReverse.get(l+1).getToken()).equals(";"))
                            {
                              flag = true;
                              break;
                            }
                            else
                            {
                              flag = false;
                              System.out.println("REJECT");
                              System.exit(1);
                              break;
                            }
                          }
                          else if(((tokenReverse.get(l).getToken()).equals("}")) && count == 0)
                          {
                            flag = true;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    public static void F (LinkedList<TableToken> store, LinkedList<Token> tokenReverse)
    {
      boolean flag1 = false;
      boolean flag2 = false;
      int count = 0;
      int counter = 0;
      for(int i = 0; i < store.size(); i++)
      {
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          if((store.get(i).getCall()).equals("DEFINITION"))
          {
            if(((store.get(i).getType()).equals("int")) || ((store.get(i).getType()).equals("void")))
            {
              count++;
            }
            else if(((store.get(i).getType()).equals("float")) || ((store.get(i).getType()).equals("void")))
            {
              counter++;
            }
          }
        }
      }
      if(count == store.size())
      {
        flag2 = true;
      }
      else if(counter == store.size())
      {
        flag1 = true;
      }
      for(int i = 0; i < store.size(); i++)
      {
        String function = "";
        String checkToken = "";
        int count2 = 0;
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          if((store.get(i).getCall()).equals("DEFINITION"))
          {
            if((store.get(i).getType()).equals("int"))
            {
              function = store.get(i).getName();
              for(int j = 0; j < tokenReverse.size(); j++)
              {
                if((tokenReverse.get(j).getToken()).equals(function))
                {
                  if((tokenReverse.get(j-1).getToken()).equals("int"))
                  {
                    for(int k = j+1; k < tokenReverse.size(); k++)
                    {
                      if((tokenReverse.get(k).getToken()).equals("{"))
                          {
                        for(int l = k+1; l < tokenReverse.size(); l++)
                        {
                          if((tokenReverse.get(l).getToken()).equals("return"))
                          {
                            count2++;
                            if((tokenReverse.get(l+1).getNumType()).equals("int"))
                            {
                              flag1 = true;
                              break;
                            }
                            else if((tokenReverse.get(l+1).getType()).equals("ID"))
                            {
                              checkToken = tokenReverse.get(l+1).getToken();
                              for(int m = 0; m < store.size(); m++)
                              {
                                if((store.get(m).getCat()).equals("VARIABLE"))
                                {
                                  if((store.get(m).getCall()).equals("INSTANTIATION"))
                                  {
                                    if((store.get(m).getName()).equals(checkToken))
                                    {
                                      if((store.get(m).getType()).equals("int"))
                                      {
                                        flag1 = true;
                                        break;
                                      }
                                    }
                                  }
                                }
                              }
                            }
                            else
                            {
                              flag1 = false;
                              System.out.println("REJECT");
                              System.exit(1);
                              break;
                            }
                          }
                          else if(((tokenReverse.get(l).getToken()).equals("}")) && count == 0)
                          {
                            flag1 = false;
                            System.out.println("REJECT");
                            System.exit(1);
                            break;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            else if((store.get(i).getType()).equals("float"))
            {
              function = store.get(i).getName();
              for(int j = 0; j < tokenReverse.size(); j++)
              {
                if((tokenReverse.get(j).getToken()).equals(function))
                {
                  if((tokenReverse.get(j-1).getToken()).equals("float"))
                  {
                    for(int k = j+1; k < tokenReverse.size(); k++)
                    {
                      if((tokenReverse.get(k).getToken()).equals("{"))
                          {
                        for(int l = k+1; l < tokenReverse.size(); l++)
                        {
                          if((tokenReverse.get(l).getToken()).equals("return"))
                          {
                            count++;
                            if((tokenReverse.get(l+1).getNumType()).equals("float"))
                            {
                              flag2 = true;
                              break;
                            }
                            else if((tokenReverse.get(l+1).getType()).equals("ID"))
                            {
                              checkToken = tokenReverse.get(l+1).getToken();
                              for(int m = 0; m < store.size(); m++)
                              {
                                if((store.get(m).getCat()).equals("VARIABLE"))
                                {
                                  if((store.get(m).getCall()).equals("INSTANTIATION"))
                                  {
                                    if((store.get(m).getName()).equals(checkToken))
                                    {
                                      if((store.get(m).getType()).equals("float"))
                                      {
                                        flag1 = true;
                                        break;
                                      }
                                    }
                                  }
                                }
                              }
                            }
                            else
                            {
                              flag2 = false;
                              System.out.println("REJECT");
                              System.exit(1);
                              break;
                            }
                          }
                          else if(((tokenReverse.get(l).getToken()).equals("}")) && count == 0)
                          {
                            flag2 = false;
                            System.out.println("REJECT");
                            System.exit(1);
                            break;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

   

    public static void G(LinkedList<TableToken> store)
    {
      boolean check = true;
      for(int i = 0; i < store.size(); i++)
      {
        String variable = "";
        int s = 0;
        String function = "";
        int c = 0;
        String type = "";
        if((store.get(i).getCat()).equals("VARIABLE"))
        {
          if((store.get(i).getCall()).equals("DEFINITION"))
          {
            variable = store.get(i).getName();
            s = store.get(i).getScope();
            function = store.get(i).getCurrent();
            c = store.get(i).getIndex();
            type = store.get(i).getType2();
            for(int j = 0; j < store.size(); j++)
            {
              if((store.get(j).getCat()).equals("VARIABLE"))
              {
                if((store.get(j).getCall()).equals("DEFINITION"))
                {
                  if((store.get(j).getName()).equals(variable))
                  {
                    if(store.get(j).getScope() == s)
                    {
                      if(!(store.get(j).getIndex() == c))
                      {
                        if((store.get(j).getCurrent()).equals("GLOBAL"))
                        {
                          check = false;
                          System.out.println("REJECT");
                          System.exit(1);
                        }
                        else if(((store.get(j).getCurrent()).equals(function)) && (!(function.equals("GLOBAL"))))
                        {
                          check = false;
                          System.out.println("REJECT");
                          System.exit(1);
                        }
                        else
                        {
                          check = true;
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    
    public static void H(LinkedList<TableToken> store, LinkedList<Token> tokenReverse)
    {
      boolean check = false;
      int count = 0;

      for(int i = 0; i < tokenReverse.size(); i++)
      {
        if(((tokenReverse.get(i).getToken()).equals("+")) || ((tokenReverse.get(i).getToken()).equals("-")) || ((tokenReverse.get(i).getToken()).equals("/")) || ((tokenReverse.get(i).getToken()).equals("*"))){
          count++;
        }
      }

      if(count == 0)
      {
        check = true;
      }

      for(int i = 0; i < tokenReverse.size(); i++)
      {
        if(((tokenReverse.get(i).getToken()).equals("+")) || ((tokenReverse.get(i).getToken()).equals("-")) || 
        ((tokenReverse.get(i).getToken()).equals("/")) || ((tokenReverse.get(i).getToken()).equals("*")))
        {
          if(((tokenReverse.get(i-1).getNumType()).equals((tokenReverse.get(i+1).getNumType()))))
          {
            check = true;
            String temp1 = tokenReverse.get(i-1).getToken().trim();
            String temp2 = tokenReverse.get(i+1).getToken().trim();
            String v1 = "";
            String v2 = "";;
            for(int j = 0; j < store.size(); j++)
            {
              if((store.get(j).getName()).equals(temp1))
              {
                if((store.get(j).getCat()).equals("VARIABLE"))
                {
                  if((store.get(j).getCall()).equals("INSTANTIATION"))
                  {
                    v1 = store.get(j).getType2();
                  }
                }
              }
              else if((store.get(j).getName()).equals(temp2))
              {
                if((store.get(j).getCat()).equals("VARIABLE"))
                {
                  if((store.get(j).getCall()).equals("INSTANTIATION"))
                  {
                    v2 = store.get(j).getType2();
                  }
                }
              }
            }
            if(v1.equals(v2))
            {
              check = true;
            }
            else
            {
              check = false;
              System.out.println("REJECT");
              System.exit(1);
            }
          }
          else
          {
            check = false;
            System.out.println("REJECT");
            System.exit(1);
          }
        }
      }
    }

    public static void I(LinkedList<TableToken> store, LinkedList<Token> tokenReverse)
    {
      boolean check = true;
      for(int i = 0; i < store.size(); i++)
      {
        String variable = "";
        int s = 0;
        if((store.get(i).getCat()).equals("VARIABLE"))
        {
          variable = store.get(i).getName();
          s = store.get(i).getScope();
          for(int j = 0; j < tokenReverse.size(); j++)
          {
            if((tokenReverse.get(j).getToken()).equals(variable))
            {
              if(tokenReverse.get(j).getDepth() == s)
              {
                if((tokenReverse.get(j+1).getToken()).equals("="))
                {
                  if((tokenReverse.get(j+2).getNumType()).equals(store.get(i).getType()))
                  {
                    check = true;
                    j = j+3;
                  }
                  else
                  {
                    check = false;
                    System.out.println("REJECT");
                    System.exit(1);
                  }
                }
              }
            }
          }
        }
      }
    }

     public static void J(LinkedList<TableToken> store, LinkedList<Token> tokenReverse, String delim[])
    {
      boolean check = true;
      for(int i = 0; i < store.size(); i++)
      {
        int count = 0;
        String function = "";
        String type = "";
        if((store.get(i).getCat()).equals("FUNCTION"))
        {
          if((store.get(i).getCall()).equals("DEFINITION"))
          {
            function = store.get(i).getName();
            type = store.get(i).getType();
            for(int j = 0; j < tokenReverse.size(); j++)
            {
              if((tokenReverse.get(j).getToken()).equals(function))
              {
                for(int k = j+1; k < tokenReverse.size(); k++)
                {
                  if((tokenReverse.get(k).getToken()).equals("{"))
                      {
                    for(int l = k+1; l < tokenReverse.size(); l++)
                    {
                      if((tokenReverse.get(l).getToken()).equals("return"))
                      {
                        count++;
                        if((tokenReverse.get(l+2).getToken()).equals("["))
                        {
                          if(!((tokenReverse.get(l+3).getNumType()).equals(type)))
                          {
                            System.out.println("REJECT");
                            System.exit(1);
                            break;
                          }
                          else
                          {
                            check = true;
                            break;
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    }
    
    static class Table
    {
        public static LinkedList<TableToken> createTable(LinkedList<Token> tokenReverse)
        {
            LinkedList<TableToken> store = new LinkedList<TableToken>();
        int num = 0;
            for(int i = 0; i < tokenReverse.size(); i++)
            {
              int count = 0;
              int counter = i;
              int y = i-1;
              if((tokenReverse.get(i).getType()).equals("ID"))
              {
                if((tokenReverse.get(i+1).getToken()).equals("("))
                {
                  counter = i+2;
                  while(!((tokenReverse.get(counter).getToken()).equals(")")))
                  {
                    if((tokenReverse.get(counter).getToken()).equals(","))
                    {
                      count++;
                    }
                    counter++;
                  }
                  count++;
                  if(((tokenReverse.get(y).getToken()).equals("float")) || ((tokenReverse.get(y).getToken()).equals("int")) ||
                  ((tokenReverse.get(y).getToken()).equals("void")))
                  {
                    TableToken data = new TableToken("FUNCTION", tokenReverse.get(y).getToken(), 
                    tokenReverse.get(i).getToken(), tokenReverse.get(i).getDepth(), count, "DEFINITION", "", 0, "", i);
                    if((tokenReverse.get(y).getToken()).equals("int"))
                    {
                      tokenReverse.get(i).setNumType("int");
                    }
                    else if((tokenReverse.get(y).getToken()).equals("float"))
                    {
                      tokenReverse.get(i).setNumType("float");
                    }
                    else if((tokenReverse.get(y).getToken()).equals("void"))
                    {
                      tokenReverse.get(i).setNumType("void");
                    }
                    store.addLast(data);
                  }
                  else
                  {
                    TableToken data = new TableToken("FUNCTION", "", tokenReverse.get(i).getToken(), tokenReverse.get(i).getDepth(), count, "CALL", "", 0, "", i);
                    store.addLast(data);
                  }
                }
                else
                {
                  if(((tokenReverse.get(y).getToken()).equals("float")) || ((tokenReverse.get(y).getToken()).equals("int")))
                  {
                    if((tokenReverse.get(y).getToken()).equals("int"))
                    {
                      tokenReverse.get(i).setNumType("int");
                    }
                    else if((tokenReverse.get(y).getToken()).equals("float"))
                    {
                      tokenReverse.get(i).setNumType("float");
                    }
                    if((tokenReverse.get(i+1).getToken()).equals("["))
                    {
                      if((tokenReverse.get(i+2).getNumType()).equals("int"))
                      {
                        num = Integer.parseInt(tokenReverse.get(i+2).getToken());
                      }
                      else if((tokenReverse.get(i+2).getToken()).equals("]"))
                      {
                        num = 0;
                      }
                      else
                      {
                        System.out.println("REJECT");
                        System.exit(1);
                      }
                      TableToken data = new TableToken("VARIABLE", tokenReverse.get(y).getToken().trim(), 
                      tokenReverse.get(i).getToken().trim(), tokenReverse.get(i).getDepth(), 0, "DEFINITION", "ARRAY", num, "", i);
                      store.addLast(data);
                    }
                    else
                    {
                      TableToken data = new TableToken("VARIABLE", tokenReverse.get(y).getToken().trim(), tokenReverse.get(i).getToken().trim(), tokenReverse.get(i).getDepth(), 0, "DEFINITION", "", 0, "", i);
                      store.addLast(data);
                    }
                  }
                  else
                  {
                    if((tokenReverse.get(i-1).getToken()).equals("void"))
                    {
                      System.out.println("REJECT");
                      System.exit(1);
                    }
                    else
                    {
                      if((tokenReverse.get(i+1).getToken()).equals("["))
                      {
                        if((tokenReverse.get(i+2).getNumType()).equals("int"))
                        {
                          num = Integer.parseInt(tokenReverse.get(i+2).getToken());
                        }
                        else if((tokenReverse.get(i+2).getToken()).equals("]"))
                        {
                          num = 0;
                        }
                        else
                        {
                          System.out.println("REJECT");
                          System.exit(1);
                        }
                        TableToken data = new TableToken("VARIABLE", "", tokenReverse.get(i).getToken().trim(), tokenReverse.get(i).getDepth(), 0, "INSTANTIATION", "ARRAY" , num, "", i);
                        store.addLast(data);
                      }
                      else
                      {
                        TableToken data = new TableToken("VARIABLE", "", tokenReverse.get(i).getToken().trim(), tokenReverse.get(i).getDepth(), 0, "INSTANTIATION", "", 0, "", i);
                        store.addLast(data);
                      }
                    }
                  }
                }
              }
            }
            for(int j = 0; j < store.size(); j++)
            {
              String variable = "";
              String function = "";
              if((store.get(j).getCat()).equals("FUNCTION"))
              {
                if((store.get(j).getCall()).equals("DEFINITION"))
                {
                  function = store.get(j).getName();
                  for(int k = 0; k < store.size(); k++)
                  {
                    if((store.get(k).getName()).equals(function))
                    {
                      if((store.get(k).getCat()).equals("FUNCTION"))
                      {
                        if((store.get(k).getCall()).equals("CALL"))
                        {
                          store.get(k).setType(store.get(j).getType());
                          if((store.get(j).getType()).equals("int"))
                          {
                            tokenReverse.get(store.get(k).getIndex()).setNumType("int");
                          }
                          else if((store.get(j).getType()).equals("float"))
                          {
                            tokenReverse.get(store.get(k).getIndex()).setNumType("float");
                          }
                        }
                      }
                    }
                  }
                }
              }
              else if((store.get(j).getCat()).equals("VARIABLE"))
              {
                if((store.get(j).getCall()).equals("DEFINITION"))
                {
                  variable = store.get(j).getName();
                  String isArray = store.get(j).getType2();
                  int l = store.get(j).getSize();
                  String type = "";
                  for(int k = 0; k < store.size(); k++)
                  {
                    if((store.get(k).getName()).equals(variable))
                    {
                      if((store.get(k).getCat()).equals("VARIABLE"))
                      {
                        if((store.get(k).getCall()).equals("INSTANTIATION"))
                        {
                          store.get(k).setType(store.get(j).getType());
                          store.get(k).setType2(isArray);
                          store.get(k).setSize(l);
                          if((store.get(j).getType()).equals("int"))
                          {
                            tokenReverse.get(store.get(k).getIndex()).setNumType("int");
                          }
                          else if((store.get(j).getType()).equals("float"))
                          {
                            tokenReverse.get(store.get(k).getIndex()).setNumType("float");
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            return store;
        }
    }
    static class Token
    {
        String token;
        String type;
        String numType;
        int depth;
        public Token(String ty, String nt, String t, int d)
        {
            token = t;
            type = ty;
            numType = nt;
            depth = d;
        }

        public String getToken()
        {
            return token;
        }

        public String getType()
        {
            return type;
        }
        
        public String getNumType()
        {
            return numType;
        }
        public void setNumType(String z)
        {
            numType = z;
        }
        public int getDepth()
        {
            return depth;
        }
    }
    
    static class TableToken
    {
     String name;       
     String type;                 
     String cat; 
     String call;             
     String type2; 
     String current; 
     int s;                  
     int num;                        
     int size;              
     int index;              

     public TableToken(String c, String t1, String n, int ss,
      int nn, String cc, String t2, int siz, String curr, int i)
      {
       cat = c;
       type = t1;
       name = n;
       s = ss;
       num = nn;
       call = cc;
       type2 = t2;
       size = siz;
       current = curr;
       index = i;
     }


     public String getCat()
     {
       return cat;
     }

     public String getType()
     {
       return type;
     }

     public String getName()
     {
       return name;
     }

     public int getScope()
     {
       return s;
     }

     public int getNumOfArguments()
     {
       return num;
     }

     public String getCall()
     {
       return call;
     }

     public String getType2()
     {
       return type2;
     }

     public int getSize()
     {
       return size;
     }

     public String getCurrent()
     {
       return current;
     }

     public int getIndex()
     {
       return index;
     }

     public void setType(String z)
     {
       type = z;
     }

     public void setType2(String z)
     {
       type2 = z;
     }
     public void setScope(int z)
     {
       s = z;
     }

     public void setSize(int z)
     {
       size = z;
     }
  }
  
  static class Arguments
  {
    String name;       
    String type;            
    String type2;        

    public Arguments(String f, String p, String v)
    {
      name = f;
      type = p;
      type2 = v;
    }

    public String getName()
    {
      return name;
    }

    public String getType()
    {
      return type;
    }

    public String getType2()
    {
      return type2;
    }
  }
}