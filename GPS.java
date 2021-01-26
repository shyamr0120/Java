import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class GPS
{
	static Nodes root;
	static int count;
	static int found;
	static int path1;
	static int path2;
	static int eN;
	static int lock;
	static int max;
	static int dir = 0;
	static long time1;
	static long time2;
	static long time3;
	static long time4;
	static ArrayList<Integer> obs;
	static ArrayList<Nodes> list;
	static ArrayList<Nodes> line;
	static ArrayList<Nodes> open;
	static ArrayList<Nodes> closed;
	static ArrayList<Nodes> nodes;
	static ArrayList<Nodes> fail;
	static ArrayList<Integer> attacks;
	public static void main(String args[])
	{
		int choice = 1;
		while(choice!=0)
		{
			Scanner s = new Scanner(System.in);
			System.out.println("Enter 0 to Exit, 1 to run program");
			choice = s.nextInt();
			System.out.println();
			if(choice==0)
			{
				break;
			}
			if(choice!=1)
			{
				System.out.println("Invalid choice. Please make a valid choice.");
				continue;
			}
			
			s = new Scanner(System.in);
			System.out.println("Enter which Environment you would like to test"
					+ " (1: 100x100 2:200x200 3:300x300)");
			int env = s.nextInt();
			System.out.println();
			
			if(env!=1&&env!=2&&env!=3)
			{
				System.out.println("Invalid choice. Please make a valid choice.");
				continue;
			}
			
			attacks = new ArrayList<Integer>();
			obs = new ArrayList<Integer>();
			open = new ArrayList<Nodes>();
			closed = new ArrayList<Nodes>();
			fail = new ArrayList<Nodes>();
			
			int nnodes = 0;
			
			if(env == 1)
			{
				nnodes = 100;
			}
			
			if(env == 2)
			{
				nnodes = 200;
			}
			
			if(env == 3)
			{
				nnodes = 300;
			}
			max = nnodes*nnodes;
			
			s = new Scanner(System.in);
			System.out.println("Enter Obstacle Percentage"
					+ " (1: 10% 2:20% 3:30%)");
			int per = s.nextInt();
			System.out.println();
			
			if(per!=1&&per!=2&&per!=3)
			{
				System.out.println("Invalid choice. Please make a valid choice.");
				continue;
			}
			
			double per2 = (double)(nnodes*nnodes)/10 * per;
			per = (int) per2;
			
			String si = "";
			
			if(env==1)
			{
				s = new Scanner(System.in);
				System.out.println("Enter starting index e.g.: 0,0."
						+ " Range is from (0,0) to (99,99).");
				si = s.nextLine();
			}
			
			if(env==2)
			{
				s = new Scanner(System.in);
				System.out.println("Enter starting index e.g.: 0,0."
						+ " Range is from (0,0) to (199,199).");
				si = s.nextLine();
			}
			
			if(env==3)
			{
				s = new Scanner(System.in);
				System.out.println("Enter starting index e.g.: 0,0."
						+ " Range is from (0,0) to (299,299).");
				si = s.nextLine();
			}
			
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
			
			if(sums>nnodes*nnodes)
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
			
			if(sumg>nnodes*nnodes)
			{
				System.out.println("Invalid goal index. Please input a valid index.");
				System.out.println();
				continue;
			}
			
			int nobs = 0;
			Random random = new Random();
			while(nobs<per)
			{
				int rx = random.nextInt(nnodes);
				int ry = random.nextInt(nnodes);
				if(rx==sx&&ry==sy)
				{
					continue;
				}
				if(rx==gx&&ry==gy)
				{
					continue;
				}
				obs.add(rx);
				obs.add(ry);
				nobs++;
			}
			
			found = 0;
			path1 = -1;
			path2 = 0;
			count = 0;
			eN = nnodes*(nnodes-1)*2;
			list = new ArrayList<Nodes>();
			line = new ArrayList<Nodes>();
			nodes = new ArrayList<Nodes>();
			
			Graph graph = new SingleGraph("grid");
			Generator gen = new GridGenerator();
	
			gen.addSink(graph);
			gen.begin();
	
			for(int i=0; i<nnodes-1; i++) 
			{
				gen.nextEvents();
			}
			gen.end();
			
			createTree(nnodes);
			
			addPaths(list, nnodes-1);
			
			Nodes start = list.get(0);
			Nodes goal = list.get(0);
			
			sums=-1;
			sumg=-1;
			int exit = 0;
			
			for(int i=0; i<nnodes; i++)
			{
				for(int j=0; j<nnodes; j++)
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
			for(int i=0; i<nnodes; i++)
			{
				for(int j=0; j<nnodes; j++)
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
			
			start = list.get(sums);
			goal = list.get(sumg);
			String index = "";
			
			for(int i=0; i<list.size(); i++)
			{
				Nodes temp2 = list.get(i);
				int xx = temp2.x;
				int yy = temp2.y;
		
				index = Integer.toString(xx)
						+"_"+Integer.toString(yy);
				Node temp3 = graph.getNode(index);
				temp3.addAttribute("ui.hide");
			}
			
			for(int i=0; i<list.size(); i++)
			{
				Nodes temp2 = list.get(i);
				int xx = temp2.x;
				int yy = temp2.y;
				int o = temp2.getObstacle();
				if(o==1)
				{
					index = Integer.toString(xx)
							+"_"+Integer.toString(yy);
					Node temp3 = graph.getNode(index);
					temp3.removeAttribute("ui.hide");
					temp3.addAttribute("ui.style", "size: 5px; fill-color: gray;");
				}
			}
			
			for(int i=0; i<nnodes-1; i++)
			{
				for(int j=0; j<nnodes-1; j++)
				{
					index = Integer.toString(i)
							+"_"+Integer.toString(j);
					String index2 = Integer.toString(i+1)
							+"_"+Integer.toString(j+1);
					graph.addEdge(Integer.toString(eN++), index, index2);
				}
			}
			
			for(int i=0; i<nnodes-1; i++)
			{
				for(int j=1; j<nnodes; j++)
				{
					index = Integer.toString(i)
							+"_"+Integer.toString(j);
					String index2 = Integer.toString(i+1)
							+"_"+Integer.toString(j-1);
					graph.addEdge(Integer.toString(eN++), index, index2);
				}
			}
			
			for(int i=0; i<eN; i++)
			{

				Edge e = graph.getEdge(i);
				e.addAttribute("ui.hide");
			}
			
			if(choice==1)
			{
				time1 = System.currentTimeMillis();
				open.add(start);
				astar(goal);
				plotAStar(graph);
				time2 = System.currentTimeMillis();
			}
			
			int x = start.x;
			int y = start.y;
			index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp = graph.getNode(index);
			temp.removeAttribute("ui.hide");
			temp.addAttribute("ui.style", "size: 5px; fill-color: blue;");
			
			x = goal.x;
			y = goal.y;
			index = Integer.toString(x)
					+"_"+Integer.toString(y);
			temp = graph.getNode(index);
			temp.removeAttribute("ui.hide");
			temp.addAttribute("ui.style", "size: 5px; fill-color: green;");
			
			double stime = time2-time1;
			System.out.println("Path Found");
			System.out.println();
			System.out.println("Time Taken: "+stime+" milliseconds");
			System.out.println();
			graph.display(false);
		}
		System.exit(0);
	}
	
	public static boolean validify(int x, int y)
	{
		Nodes fin = closed.get(closed.size()-1);
		int fx = fin.x;
		int fy = fin.y;
		
		if(dir == 1)
		{
			if(fx==x && fy==(y+1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		if(dir == 2)
		{
			if(fx==(x+1) && fy==(y))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		if(dir == 3)
		{
			if(fx==(x-1) && fy==(y))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		if(dir == 4)
		{
			if(fx==x && fy==(y-1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		if(dir == 5)
		{
			if(fx==(x-1) && fy==(y+1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		if(dir == 6)
		{
			if(fx==(x+1) && fy==(y+1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		if(dir == 7)
		{
			if(fx==(x-1) && fy==(y-1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		if(dir == 8)
		{
			if(fx==(x+1) && fy==(y-1))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return false;
	}
	
	public static void astar(Nodes goal)
	{
		Nodes starter = open.get(0);
		int size = open.size();
		int sx = starter.x;
		int sy = starter.y;
		int cattack = 0;
		int atx = 0;
		int aty = 0;
		for(int i=0; i<open.size(); i++)
		{
			Nodes current = open.get(i);
			int x = current.x;
			int y = current.y;
			
			if(x==sx&&y==sy)
			{
				cattack++;
				if(cattack%5==0)
				{
					Random random = new Random();
					x = random.nextInt(99);
					y = random.nextInt(99);
				}
				
				boolean check = true;
				if(closed.size()>1)
				{
					check = validify(x, y);
				}
				
				if(check == false)
				{
					Nodes fin = closed.get(closed.size()-1);
					int fx = fin.x;
					int fy = fin.y;
					Nodes tempy = new Nodes(x, y);
					
					if(closed.size()>1)
					{
						fail.add(tempy);
						int addy = closed.size();
						attacks.add(addy);
					}
					
					
					if(dir == 1)
					{
						x = fx;
						y = fy+1;
					}
					if(dir == 2)
					{
						x = fx+1;
						y = fy;
					}
					if(dir == 3)
					{
						x = fx-1;
						y = fy;
					}
					if(dir == 4)
					{
						x = fx;
						y = fy-1;
					}
					if(dir == 5)
					{
						x = fx-1;
						y = fy+1;
					}
					if(dir == 6)
					{
						x = fx+1;
						y = fy+1;
					}
					if(dir == 7)
					{
						x = fx-1;
						y = fy-1;
					}
					if(dir == 8)
					{
						x = fx+1;
						y = fy-1;
					}				
					
				}
				
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
				double sum5 = 100000;
				double sum6 = 100000;
				double sum7 = 100000;
				double sum8 = 100000;
				
				if(current.left!=null&&(current.left).getObstacle()==0)
				{
					Nodes temp = current.left;
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
						sum1 = Math.sqrt(s1+s2);
					}
				}
				if(current.right!=null&&(current.right).getObstacle()==0)
				{
					Nodes temp = current.right;
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
						sum2 = Math.sqrt(s1+s2);
					}
				}
				if(current.pl!=null&&(current.pl).getObstacle()==0)
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
				if(current.pr!=null&&(current.pr).getObstacle()==0)
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
				if(current.nw!=null&&(current.nw).getObstacle()==0)
				{
					Nodes temp = current.nw;
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
						sum5 = Math.sqrt(s1+s2);
					}
				}
				if(current.ne!=null&&(current.ne).getObstacle()==0)
				{
					Nodes temp = current.ne;
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
						sum6 = Math.sqrt(s1+s2);
					}
				}
				if(current.sw!=null&&(current.sw).getObstacle()==0)
				{
					Nodes temp = current.sw;
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
						sum7 = Math.sqrt(s1+s2);
					}
				}
				if(current.se!=null&&(current.se).getObstacle()==0)
				{
					Nodes temp = current.se;
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
						sum8 = Math.sqrt(s1+s2);
					}
				}
				
				if(sum2<sum1&&sum2<=sum3&&sum2<=sum4&&sum2<sum5&&sum2<=sum6&&sum2<=sum7&&sum2<=sum8&&current.right!=null)
				{
					Nodes temp = current.right;
					sx = temp.x;
					sy = temp.y;
					dir = 2;
				}
				else if(sum1<=sum2&&sum1<=sum3&&sum1<=sum4&&sum1<sum5&&sum1<=sum6&&sum1<=sum7&&sum1<=sum8&&current.left!=null)
				{
					Nodes temp = current.left;
					sx = temp.x;
					sy = temp.y;
					dir = 1;
				}
				else if(sum3<sum1&&sum3<sum2&&sum3<=sum4&&sum3<sum5&&sum3<=sum6&&sum3<=sum7&&sum3<=sum8&&current.pl!=null)
				{
					Nodes temp = current.pl;
					sx = temp.x;
					sy = temp.y;
					dir = 3;
				}
				else if(sum4<sum1&&sum4<sum2&&sum4<sum3&&sum4<sum5&&sum4<=sum6&&sum4<=sum7&&sum4<=sum8&&current.pr!=null)
				{
					Nodes temp = current.pr;
					sx = temp.x;
					sy = temp.y;
					dir = 4;
				}
				else if(sum5<sum1&&sum5<sum2&&sum5<sum3&&sum5<sum4&&sum5<=sum6&&sum5<=sum7&&sum5<=sum8&&current.nw!=null)
				{
					Nodes temp = current.nw;
					sx = temp.x;
					sy = temp.y;
					dir = 5;
				}
				else if(sum6<sum1&&sum6<sum2&&sum6<sum3&&sum6<sum4&&sum6<sum5&&sum6<=sum7&&sum6<=sum8&&current.ne!=null)
				{
					Nodes temp = current.ne;
					sx = temp.x;
					sy = temp.y;
					dir = 6;
				}
				else if(sum7<sum1&&sum7<sum2&&sum7<sum3&&sum7<sum4&&sum7<sum5&&sum7<sum6&&sum7<=sum8&&current.sw!=null)
				{
					Nodes temp = current.sw;
					sx = temp.x;
					sy = temp.y;
					dir = 7;
				}
				else if(sum8<sum1&&sum8<sum2&&sum8<sum3&&sum8<sum4&&sum8<sum5&&sum8<sum6&&sum8<sum7&&current.se!=null)
				{
					Nodes temp = current.se;
					sx = temp.x;
					sy = temp.y;
					dir = 8;
				}
				
				
				size = open.size();
				i = -1;
			}
		}
	}
	
	public static void plotAStar(Graph graph)
	{
		int counter = 0;
		for(int i=0; i<closed.size(); i++)
		{
			boolean check = false;
			for(int j=0; j<attacks.size(); j++)
			{
				if(i==attacks.get(j))
				{
					check = true;
					break;
				}
			}
			
			if(check==true)
			{
				Nodes temp = fail.get(counter);
				counter++;
				int x = temp.x;
				int y = temp.y;
				String index = Integer.toString(x)
						+"_"+Integer.toString(y);
				Node temp2 = graph.getNode(index);
				temp2.removeAttribute("ui.hide");
				temp2.addAttribute("ui.style", "size: 5px; fill-color: purple;");
			}
			
			Nodes temp = closed.get(i);
			int x = temp.x;
			int y = temp.y;
			String index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp2 = graph.getNode(index);
			temp2.removeAttribute("ui.hide");
			temp2.addAttribute("ui.style", "size: 5px; fill-color: red;");
			
			for(int j=i+1; j<closed.size(); j++)
			{
				Nodes temp3 = closed.get(j);
				String index2 = Integer.toString(temp3.x)
						+"_"+Integer.toString(temp3.y);
				Edge e = temp2.getEdgeBetween(index2);
				e.removeAttribute("ui.hide");
				e.addAttribute("ui.style", "fill-color: red;");
				break;
			}
		}
	}
	
	public static void createTree(int num)
	{
		root = new Nodes(0,0);
		list.add(root);
		for(int i=0; i<num; i++)
		{
			for(int j=0; j<num; j++)
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
	
	public static void addPaths(ArrayList<Nodes> list, int n)
	{
		for(int i=0; i<list.size(); i++)
		{
			Nodes temp = list.get(i);
			int x = temp.x;
			int y = temp.y;
			int size = obs.size();
			for(int j=0; j<obs.size()-1; j+=2)
			{
				int xx = obs.get(j);
				int yy = obs.get(j+1);
				if(x==xx&&y==yy)
				{
					temp.setObstacle(1);
					//System.out.println(x+" "+y);
					break;
				}
			}
			if(i==list.size()-1)
			{
				i = list.size()-1;
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
			if(temp.ne==null)
			{
				for(int j=0; j<list.size(); j++)
				{
					Nodes temp2 = list.get(j);
					int xx = temp2.x;
					int yy = temp2.y;
					if(xx==(x+1)&&yy==(y+1))
					{
						temp.ne=temp2;
						count++;
						break;
					}
				}
			}
			if(temp.nw==null)
			{
				for(int j=0; j<list.size(); j++)
				{
					Nodes temp2 = list.get(j);
					int xx = temp2.x;
					int yy = temp2.y;
					if(xx==(x-1)&&yy==(y+1))
					{
						temp.nw=temp2;
						count++;
						break;
					}
				}
			}
			if(temp.se==null)
			{
				for(int j=0; j<list.size(); j++)
				{
					Nodes temp2 = list.get(j);
					int xx = temp2.x;
					int yy = temp2.y;
					if(xx==(x+1)&&yy==(y-1))
					{
						temp.se=temp2;
						count++;
						break;
					}
				}
			}
			if(temp.sw==null)
			{
				for(int j=0; j<list.size(); j++)
				{
					Nodes temp2 = list.get(j);
					int xx = temp2.x;
					int yy = temp2.y;
					if(xx==(x-1)&&yy==(y-1))
					{
						temp.sw=temp2;
						count++;
						break;
					}
				}
			}
		}
	}
}

class Nodes
{
	int x;
	int y;
	private int L;
	private double Q;
	int visited;
	private int obstacle;
	Nodes left;
	Nodes right;
	Nodes pl;
	Nodes pr;
	Nodes ne;
	Nodes nw;
	Nodes se;
	Nodes sw;
	Nodes(int xx, int yy)
	{
		x=xx;
		y=yy;
		L=0;
		Q=0;
		visited=0;
		obstacle=0;
		left=null;
		right=null;
		pl=null;
		pr=null;
		ne=null;
		nw=null;
		se=null;
		sw=null;
	}
	public void setL(int n)
	{
		L = n;
	}
	public void setQ(double n)
	{
		Q = n;
	}
	public void setObstacle(int n)
	{
		obstacle = n;
	}
	public int getL()
	{
		return L;
	}
	public double getQ()
	{
		return Q;
	}
	public int getObstacle()
	{
		return obstacle;
	}
}