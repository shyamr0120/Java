import java.io.*;
import java.util.*;               
////////////////////////////////////////////////////////////////
class Node
{
   public int f;              
   public char c;       
   public Node leftChild;         
   public Node rightChild;      
   public Node parent;
   public int weight;

   public void displayNode()   
   {
      System.out.print('{');
      System.out.print(c);
      System.out.print(", ");
      System.out.print(f);
      System.out.print("} ");
   }
} 
class Array
{
   int a[];
   Array()
   {
      a = new int[5];
      for(int i=0;i<a.length;i++)
      {
         a[i]=2;
      }
   }
   public String print()
   {
      String s = "";
      for(int i=1;i<a.length;i++)
      {
         if((a[i])!=2)
         {
            s = s + Integer.toString(a[i]);
         }
      }
      return s;
   }

   public Array copy()
   {
      Array array = new Array();
      for(int i=0;i<a.length;i++)
      {
         array.a[i] = a[i];
      }
      return array;
   }
}
////////////////////////////////////////////////////////////////
class Tree
   {
   private Node root;             
   private int count;
   private int count2;
   private Node n[];
   private String code[];

   public Tree()                  
   {
      root = null;
      n=new Node[7];
      code = new String[7];
   }
   public Node getRoot()
   {
      return root;
   }
   public String[] getCode()
   {
      return code;
   }
   public void create(char character, int frequency)
   {
      Node newNode = new Node();
      newNode.c = character;
      newNode.f = frequency;
      n[count++] = newNode;
   }
   public void sort()
   {
      while(n.length>1)
      {
         for(int i=0;i<n.length-1;i++)
         {
            int small = n[i].f;
            int swap = i;
            for(int j=i+1;j<n.length;j++)
            {
               if(small>((n[j]).f))
               {
                  small = (n[j]).f;
                  swap = j;
               }
            }
            Node temp = n[i];
            n[i] = n[swap];
            n[swap] = temp;
         }
         insert(n[0],n[1]);
      }
      root = n[0];
   }
   public void insert(Node a, Node b)
   {  
      Node newNode = new Node();    
      newNode.c = 'X';           
      newNode.f = (a.f)+(b.f);
      if((a.f)<(b.f))
      {
         newNode.leftChild = a;
         a.parent = newNode;
         a.weight = 0;
         newNode.rightChild = b;
         b.parent = newNode;
         b.weight = 1;
      }
      else
      {
         newNode.leftChild = b;
         b.parent = newNode;
         b.weight = 0;
         newNode.rightChild = a;
         a.parent = newNode;
         a.weight = 1;
      }
      Node[] n2 = new Node[n.length-1];
      n2[0] = newNode;
      for(int i=1;i<n2.length;i++)
      {
         n2[i] = n[i+1];
      }
      n = n2;
   } 
      public void displayTree2(Node localRoot)
      {
      if(localRoot != null)
         {
            if(localRoot.c == 'X')
            System.out.print(localRoot.f + " ");
            else
            System.out.print(localRoot.c + " ");
            displayTree2(localRoot.leftChild);
            displayTree2(localRoot.rightChild);
         }
      }
      public void displayTree()
      {
      Stack globalStack = new Stack();
      globalStack.push(root);
      int nBlanks = 32;
      boolean isRowEmpty = false;
      System.out.println("Huffman Tree:");
      System.out.println(
      ".......................................................................");
      while(isRowEmpty==false)
         {
         Stack localStack = new Stack();
         isRowEmpty = true;

         for(int j=0; j<nBlanks; j++)
            System.out.print(' ');

         while(globalStack.isEmpty()==false)
            {
            Node temp = (Node)globalStack.pop();
            if(temp != null)
               {
                  if(temp.c == 'X')
                  {
                     int dig = temp.f;
                     dig = dig/10;
                     if(dig == 0)
                     System.out.print(" ");
                     System.out.print(temp.f);
                  }
                  else
                  {
                     System.out.print(" "+temp.c);
                  }
                  localStack.push(temp.leftChild);
                  localStack.push(temp.rightChild);

               if(temp.leftChild != null ||
                                   temp.rightChild != null)
                  isRowEmpty = false;
               }
            else
               {
               System.out.print("--");
               localStack.push(null);
               localStack.push(null);
               }
            for(int j=0; j<nBlanks*2-2; j++)
               System.out.print(' ');
            }
         System.out.println();
         System.out.println();
         nBlanks /= 2;
         while(localStack.isEmpty()==false)
            globalStack.push( localStack.pop() );
         }
      System.out.println(
      ".......................................................................");
   }
   public void code(Node localRoot, Array ar)
   {
   if(localRoot != null)
      {
      int some = localRoot.weight;
      ar.a[count2++] = localRoot.weight;
      code(localRoot.leftChild, ar);
      if((localRoot.c)!='X')
      {
         int insert = (localRoot.c)-65;
         String codes = ar.print();
         code[insert] = codes;
         Array temp = ar;
         ar = new Array();
         ar = temp.copy();
         count2--;
         ar.a[count2] = 2;
         return;
      }
      code(localRoot.rightChild, ar);
      ar.a[count2] = 2;
      count2--;
      ar.a[count2] = 2;
      }    
   }
   public void code_table()
   {
      System.out.println("Code Table:");
      for(int i=0;i<code.length;i++)
      {
         char ch = (char)(i + 65);
         System.out.print(ch + "  ");
         System.out.println(code[i]);
      }
   }
   public void traverse(String file)
   {
      Node localRoot = root;
      for(int i=0;i<file.length();i++)
      {
         char weight = file.charAt(i);
         if(weight=='0')
         {
            localRoot = localRoot.leftChild;
         }
         if(weight=='1')
         {
            localRoot = localRoot.rightChild;
         }
         if(localRoot.leftChild==null&&localRoot.rightChild==null)
         {
            System.out.print(localRoot.c);
            localRoot = root;
         }
      }
   }
}
////////////////////////////////////////////////////////////////
class TreeApp
   {
   public static void main(String args[]) throws FileNotFoundException,IOException
      {
      int value;
      Tree theTree = new Tree();
      
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
      
      String file = "";
      int countA = 0;
      int countB = 0;
      int countC = 0;
      int countD = 0;
      int countE = 0;
      int countF = 0;
      int countG = 0;

      while(input.hasNext())
      {
         file = file+input.nextLine();
      }
      for(int i=0;i<file.length();i++)
      {
         char c = file.charAt(i);
         if(c=='A')
         {
            countA++;
         }
         if(c=='B')
         {
            countB++;
         }
         if(c=='C')
         {
            countC++;
         }
         if(c=='D')
         {
            countD++;
         }
         if(c=='E')
         {
            countE++;
         }
         if(c=='F')
         {
            countF++;
         }
         if(c=='G')
         {
            countG++;
         }
      }
      
      theTree.create('A',countA);
      theTree.create('B',countB);
      theTree.create('C',countC);
      theTree.create('D',countD);
      theTree.create('E',countE);
      theTree.create('F',countF);
      theTree.create('G',countG);
      
      theTree.sort();   
            
      Array ar = new Array();
      theTree.code(theTree.getRoot(), ar);
        
      String e_file = "";
      String[] code = theTree.getCode();
            
      for(int i=0;i<file.length();i++)
      {
         char c = file.charAt(i);
         if(c=='A'||c=='B'||c=='C'||c=='D'||c=='E'||c=='F'||c=='G')
         {
            int index = c-65;
            e_file = e_file + code[index];
         }
      }
      
      String efile = "";
      for(int i=0;i<e_file.length();i++)
      {
         char c = e_file.charAt(i);
         if(i%8==0)
         {
            efile = efile + " ";
         }
         efile = efile + c;
      }
      
      Scanner s = new Scanner(System.in);
      int option = 0;
      while(option!=5)
      {
         System.out.println("What option would you like to execute?:");
         
         System.out.println("1.A");
         System.out.println("2.B");
         System.out.println("3.C");
         System.out.println("4.D");
         System.out.println("5.Exit");
         
         option = s.nextInt();
         
         if(option == 1)
         {
            System.out.println();
            theTree.displayTree();
            System.out.println();
         }
         if(option == 2)
         {
            System.out.println();
            theTree.code_table();
            System.out.println();
         }
         if(option == 3)
         {
            System.out.println();
            System.out.println("Binary Encoding of the Portion of the File Containg Characters A Through G:");
            System.out.println();
            System.out.println(efile);
            System.out.println();
         }
         if(option == 4)
         {
            System.out.println();
            System.out.println("Original Portion of File Containing Characters A Through G:");
            System.out.println();
            theTree.traverse(e_file);
            System.out.println();
            System.out.println();
         }
      }

    }
}