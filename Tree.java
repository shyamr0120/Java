import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class Tree
{
	static Nodes root;
	static int count;
	static int found;
	static int path1;
	static int path2;
	static long time1;
	static long time2;
	static long time3;
	static long time4;
	static ArrayList<Nodes> list;
	static ArrayList<Nodes> open;
	static ArrayList<Nodes> closed;
	static ArrayList<Nodes> nodes;
	public static void main(String args[])
	{
		int choice = 1;
		while(choice!=0)
		{
			Scanner s = new Scanner(System.in);
			System.out.println("Enter: 0 to Exit, "
					+ "1 to perform DFS, "
					+ "2 to perform A*");
			choice = s.nextInt();
			System.out.println();
			if(choice==0)
			{
				break;
			}
			if(choice!=1&&choice!=2)
			{
				System.out.println("Invalid choice. Please make a valid choice.");
				continue;
			}
			
			s = new Scanner(System.in);
			System.out.println("Enter starting index e.g.: 0,0."
					+ " Range is from (0,0) to (32,32).");
			String si = s.nextLine();
			
			int sx = 0;
			int sy = 0;
			
			try
			{
				String hold = "";
				for(int i=0; i<si.length(); i++)
				{
					char c = si.charAt(i);
					if(c==',')
					{
						sx = Integer.parseInt(hold);
						hold = "";
						continue;
					}
					hold = hold+c;
				}
				sy = Integer.parseInt(hold);
			}
			catch(Exception e)
			{
				System.out.println("Invalid format for starting index. Please try again.");
				System.out.println();
				continue;
			}
			int sums = sy*sx;
			
			if(sums>32*32)
			{
				System.out.println("Invalid starting index. Please input a valid index.");
				System.out.println();
				continue;
			}
			
			s = new Scanner(System.in);
			System.out.println("Enter goal index e.g.: 0,0"
					+ " Range is from (0,0) to (32,32).");
			String gi = s.nextLine();
			
			int gx = 0;
			int gy = 0;
			
			try
			{
				String hold = "";
				for(int i=0; i<gi.length(); i++)
				{
					char c = gi.charAt(i);
					if(c==',')
					{
						gx = Integer.parseInt(hold);
						hold = "";
						continue;
					}
					hold = hold+c;
				}
				gy = Integer.parseInt(hold);
			}
			catch(Exception e)
			{
				System.out.println("Invalid format for goal index. Please try again.");
				System.out.println();
				continue;
			}
			int sumg = gy*gx;
			
			if(sumg>32*32)
			{
				System.out.println("Invalid goal index. Please input a valid index.");
				System.out.println();
				continue;
			}
			
			found = 0;
			path1 = -1;
			path2 = 0;
			count = 0;
			list = new ArrayList<Nodes>();
			open = new ArrayList<Nodes>();
			closed = new ArrayList<Nodes>();
			nodes = new ArrayList<Nodes>();
			Graph graph = new SingleGraph("grid");
			Generator gen = new GridGenerator();
	
			gen.addSink(graph);
			gen.begin();
	
			for(int i=0; i<32; i++) 
			{
				gen.nextEvents();
			}
			gen.end();
			
			createTree();
			
			addPaths(list, 32);
			//System.out.println(count);
			
			Nodes start = list.get(0);
			Nodes goal = list.get(0);
			
			sums=-1;
			sumg=-1;
			int exit = 0;
			
			for(int i=0; i<33; i++)
			{
				for(int j=0; j<33; j++)
				{
					sums++;
					if(i==sx&&j==sy)
					{
						exit=1;
						break;
					}
				}
				if(exit==1)
				{
					break;
				}
			}
			exit=0;
			for(int i=0; i<33; i++)
			{
				for(int j=0; j<33; j++)
				{
					sumg++;
					if(i==gx&&j==gy)
					{
						exit=1;
						break;
					}
				}
				if(exit==1)
				{
					break;
				}
			}
			
			Nodes temp = list.get(0);
			if(sums<=sumg)
			{
				start = list.get(sums);
				goal = list.get(sumg);
				if(choice==1)
				{
					time1 = System.nanoTime();
					dfs(start, goal);
					Nodes current = start;
					while(found==0)
					{
						current = dfs2(current,goal);
					}
					time2 = System.nanoTime();
					plotDFS(graph);
				}
				else
				{
					open.add(start);
					time3 = System.nanoTime();
					astar(goal);
					time4 = System.nanoTime();
					plotAStar(graph);
				}
				System.out.println();
				if(choice==2)
				{
					temp = closed.get(0);
				}
				else
				{
					temp = nodes.get(0);
				}
				int x = temp.x;
				int y = temp.y;
				String index = Integer.toString(x)
						+"_"+Integer.toString(y);
				Node temp2 = graph.getNode(index);
				temp2.addAttribute("ui.style", "fill-color: blue;");
				if(choice==2)
				{
					temp = closed.get(closed.size()-1);
				}
				else
				{
					temp = nodes.get(nodes.size()-1);
				}
				x = temp.x;
				y = temp.y;
				index = Integer.toString(x)
						+"_"+Integer.toString(y);
				temp2 = graph.getNode(index);
				temp2.addAttribute("ui.style", "fill-color: green;");
			}
			else
			{
				start = list.get(sumg);
				goal = list.get(sums);
				if(choice==1)
				{
					time1 = System.nanoTime();
					dfs(start, goal);
					Nodes current = start;
					while(found==0)
					{
						current = dfs2(current,goal);
					}
					time2 = System.nanoTime();
					plotDFS(graph);
				}
				else
				{
					open.add(start);
					time3 = System.nanoTime();
					astar(goal);
					time4 = System.nanoTime();
					plotAStar(graph);
				}
				System.out.println();
				if(choice==2)
				{
					temp = closed.get(closed.size()-1);
				}
				else
				{
					temp = nodes.get(nodes.size()-1);
				}
				int x = temp.x;
				int y = temp.y;
				String index = Integer.toString(x)
						+"_"+Integer.toString(y);
				Node temp2 = graph.getNode(index);
				temp2.addAttribute("ui.style", "fill-color: blue;");
				if(choice==2)
				{
					temp = closed.get(0);
				}
				else
				{
					temp = nodes.get(0);
				}
				x = temp.x;
				y = temp.y;
				index = Integer.toString(x)
						+"_"+Integer.toString(y);
				temp2 = graph.getNode(index);
				temp2.addAttribute("ui.style", "fill-color: green;");
			}
				
			graph.display(false);
		}
		System.exit(0);
	}
	public static void plotDFS(Graph graph)
	{
		for(int i=0; i<nodes.size(); i++)
		{
			Nodes temp = nodes.get(i);
			int x = temp.x;
			int y = temp.y;
			String index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp2 = graph.getNode(index);
			temp2.addAttribute("ui.style", "fill-color: red;");
			for(int j=i+1; j<nodes.size(); j++)
			{
				Nodes temp3 = nodes.get(j);
				String index2 = Integer.toString(temp3.x)
						+"_"+Integer.toString(temp3.y);
				try
				{
					Edge e = temp2.getEdgeBetween(index2);
					e.addAttribute("ui.style", "fill-color: red;");
				}
				catch(Exception e)
				{
					
				}
				break;
			}
			//System.out.println(x+", "+y);
		}
		System.out.println("Path Length Given by "
				+ "DFS is "+path1);
		System.out.println("Time Taken by "
				+ "DFS is "+(time2-time1)+ " nanoseconds");
		System.out.println();
	}
	public static void plotAStar(Graph graph)
	{
		for(int i=0; i<closed.size(); i++)
		{
			Nodes temp = closed.get(i);
			int x = temp.x;
			int y = temp.y;
			String index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp2 = graph.getNode(index);
			temp2.addAttribute("ui.style", "fill-color: red;");
			for(int j=i+1; j<closed.size(); j++)
			{
				Nodes temp3 = closed.get(j);
				String index2 = Integer.toString(temp3.x)
						+"_"+Integer.toString(temp3.y);
				Edge e = temp2.getEdgeBetween(index2);
				e.addAttribute("ui.style", "fill-color: red;");
				break;
			}
			//System.out.println(x+", "+y);
		}
		path2 = closed.size()-1;
		System.out.println("Path Length Given by "
				+ "A* is "+path2);
		System.out.println("Time Taken by "
				+ "A* is "+(time4-time3)+" nanoseconds");
	}
	public static void createTree()
	{
		root = new Nodes(0,0);
		list.add(root);
		for(int i=0; i<33; i++)
		{
			for(int j=0; j<33; j++)
			{
				if(i==0&&j==0)
				{
					continue;
				}
				Nodes n = new Nodes(i,j);
				list.add(n);
			}
		}
	}
	public static void astar(Nodes goal)
	{
		Nodes starter = open.get(0);
		int size = open.size();
		int sx = starter.x;
		int sy = starter.y;
		for(int i=0; i<open.size(); i++)
		{
			Nodes current = open.get(i);
			int x = current.x;
			int y = current.y;
			if(x==sx&&y==sy)
			{
				if(goal.x==x&&goal.y==y)
				{
					closed.add(current);
					break;
				}
				closed.add(current);
				open.remove(i);
				size = open.size();
				double sum1 = 100000;
				double sum2 = 100000;
				double sum3 = 100000;
				double sum4 = 100000;
				if(current.left!=null)
				{
					Nodes temp = current.left;
					open.add(temp);
					double s1 = (goal.x-temp.x)*(goal.x-temp.x);
					double s2 = (goal.y-temp.y)*(goal.y-temp.y);
					sum1 = Math.sqrt(s1+s2);
				}
				if(current.right!=null)
				{
					Nodes temp = current.right;
					open.add(temp);
					double s1 = (goal.x-temp.x)*(goal.x-temp.x);
					double s2 = (goal.y-temp.y)*(goal.y-temp.y);
					sum2 = Math.sqrt(s1+s2);
				}
				if(current.pl!=null)
				{
					Nodes temp = current.pl;
					int xx = temp.x;
					int yy = temp.y;
					int skip = 0;
					for(int j=0; j<closed.size(); j++)
					{
						Nodes temp2 = closed.get(j);
						if(temp2.x==xx&&temp2.y==yy)
						{
							skip=1;
							break;
						}
					}
					if(skip==0)
					{
						open.add(temp);
						double s1 = (goal.x-temp.x)*(goal.x-temp.x);
						double s2 = (goal.y-temp.y)*(goal.y-temp.y);
						sum3 = Math.sqrt(s1+s2);
					}
				}
				if(current.pr!=null)
				{
					Nodes temp = current.pr;
					int xx = temp.x;
					int yy = temp.y;
					int skip = 0;
					for(int j=0; j<closed.size(); j++)
					{
						Nodes temp2 = closed.get(j);
						if(temp2.x==xx&&temp2.y==yy)
						{
							skip=1;
							break;
						}
					}
					if(skip==0)
					{
						open.add(temp);
						double s1 = (goal.x-temp.x)*(goal.x-temp.x);
						double s2 = (goal.y-temp.y)*(goal.y-temp.y);
						sum4 = Math.sqrt(s1+s2);
					}
				}
				if(sum2<sum1&&sum2<=sum3&&sum2<=sum4&&current.right!=null)
				{
					Nodes temp = current.right;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum1<=sum2&&sum1<=sum3&&sum1<=sum4&&current.left!=null)
				{
					Nodes temp = current.left;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum3<sum1&&sum3<sum2&&sum3<=sum4&&current.pl!=null)
				{
					Nodes temp = current.pl;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum4<sum1&&sum4<sum2&&sum4<sum3&&current.pr!=null)
				{
					Nodes temp = current.pr;
					sx = temp.x;
					sy = temp.y;
				}
				size = open.size();
				i = -1;
			}
		}
	}
	public static void addPaths(ArrayList<Nodes> list, int n)
	{
		for(int i=0; i<list.size(); i++)
		{
			Nodes temp = list.get(i);
			int x = temp.x;
			int y = temp.y;
			if(x==n&&y==n)
			{
				continue;
			}
			if(temp.left==null)
			{
				if(y!=n)
				{
					for(int j=0; j<list.size(); j++)
					{
						Nodes temp2 = list.get(j);
						int xx = temp2.x;
						int yy = temp2.y;
						if(xx==x&&yy==(y+1))
						{
							temp.left=temp2;
							count++;
							break;
						}
					}
				}
			}
			if(temp.right==null)
			{
				if(x!=n)
				{
					for(int j=0; j<list.size(); j++)
					{
						Nodes temp2 = list.get(j);
						int xx = temp2.x;
						int yy = temp2.y;
						if(xx==(x+1)&&yy==y)
						{
							temp.right=temp2;
							count++;
							break;
						}
					}
				}
			}
			if(temp.pl==null)
			{
				for(int j=0; j<list.size(); j++)
				{
					Nodes temp2 = list.get(j);
					int xx = temp2.x;
					int yy = temp2.y;
					if(xx==(x-1)&&yy==y)
					{
						temp.pl=temp2;
						count++;
						break;
					}
				}
			}
			if(temp.pr==null)
			{
				for(int j=0; j<list.size(); j++)
				{
					Nodes temp2 = list.get(j);
					int xx = temp2.x;
					int yy = temp2.y;
					if(xx==x&&yy==(y-1))
					{
						temp.pr=temp2;
						count++;
						break;
					}
				}
			}
		}
	}
	public static Nodes dfs2(Nodes current, Nodes goal)
	{
		if(found!=1)
		{
			current.visited=1;
			if(current.pl!=null)
			{
				Nodes temp = current.pl;
				if(temp.visited!=1)
				{
					dfs(temp, goal);
				}
				return temp;
			}
			if(current.pr!=null)
			{
				Nodes temp = current.pr;
				if(temp.visited!=1)
				{
					dfs(temp, goal);			
				}
				return temp;
			}
		}
		return current;
	}
	public static void dfs(Nodes current, Nodes goal)
	{
		if(found!=1)
		{
			nodes.add(current);
			path1++;
			if(goal.x==current.x&&goal.y==current.y)
			{
				found = 1;
				return;
			}
			current.visited=1;
			if(current.left!=null)
			{
				Nodes temp = current.left;
				if(temp.visited!=1)
				{
					dfs(temp, goal);
				}
			}
			if(current.right!=null)
			{
				Nodes temp = current.right;
				if(temp.visited!=1)
				{
					dfs(temp, goal);			
				}
			}
		}
	}
}
class Nodes
{
	int x;
	int y;
	int visited;
	Nodes left;
	Nodes right;
	Nodes pl;
	Nodes pr;
	Nodes(int xx, int yy)
	{
		x=xx;
		y=yy;
		visited=0;
		left=null;
		right=null;
		pl=null;
		pr=null;
	}
}
