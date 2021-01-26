import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class IQLS
{
	static Nodes root;
	static int count;
	static int found;
	static int path1;
	static int path2;
	static int eN;
	static int lock;
	static int max;
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
	public static void main(String args[])
	{
		int choice = 1;
		while(choice!=0)
		{
			Scanner s = new Scanner(System.in);
			System.out.println("Enter 0 to Exit, 1 to run IQL, 2 to run A*");
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
			System.out.println("Enter which Environment you would like to test"
					+ " (1: 100x100 2:200x200 3:300x300)");
			int env = s.nextInt();
			System.out.println();
			
			if(env!=1&&env!=2&&env!=3)
			{
				System.out.println("Invalid choice. Please make a valid choice.");
				continue;
			}
			
			obs = new ArrayList<Integer>();
			open = new ArrayList<Nodes>();
			closed = new ArrayList<Nodes>();
			
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
			
			if(choice==2)
			{
				time1 = System.currentTimeMillis();
				open.add(start);
				astar(goal);
				plotAStar(graph);
				time2 = System.currentTimeMillis();
			}
			else
			{
				try
				{
					iql(sumg);
					plotIQL(sums, sumg, graph);
				}
				catch(Exception e)
				{
					System.out.println("Path Does Not Exist");
					continue;
				}
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

	public static void iql(int index)
	{
		Nodes goal = list.get(index);
		goal.setL(1);
		goal.setQ(100);
		double gamma = 0.9;
		Nodes p = goal;
		double s1 = (goal.x-p.x)*(goal.x-p.x);
		double s2 = (goal.y-p.y)*(goal.y-p.y);
		double dp = Math.sqrt(s1+s2);
		double dn = 0;
		Nodes n = new Nodes (0,0);
		int count = 1;
		
		while(true)
		{
			dn = 0;
			if(p.left==null)
			{
				break;
			}
			n = p.left;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						double hold = gamma*n.getQ();
						if(hold==0.0)
						{
							hold = 0;
						}
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}
		p = goal;
		while(true)
		{
			dn = 0;
			if(p.right==null)
			{
				break;
			}
			n = p.right;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}
		p = goal;
		while(true)
		{
			dn = 0;
			if(p.pl==null)
			{
				break;
			}
			n = p.pl;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}
		p = goal;
		while(true)
		{
			dn = 0;
			if(p.pr==null)
			{
				break;
			}
			n = p.pr;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}
		
		/*gamma = 0.1;
		p = goal;
		while(true)
		{
			dn = 0;
			if(p.nw==null)
			{
				break;
			}
			n = p.nw;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}
		p = goal;
		while(true)
		{
			dn = 0;
			if(p.ne==null)
			{
				break;
			}
			n = p.ne;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}
		p = goal;
		while(true)
		{
			dn = 0;
			if(p.sw==null)
			{
				break;
			}
			n = p.sw;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}
		p = goal;
		while(true)
		{
			dn = 0;
			if(p.se==null)
			{
				break;
			}
			n = p.se;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			if(dn>dp)
			{
				if(n.getL()==1)
				{
					if(p.getL()==0)
					{
						p.setQ(gamma * n.getQ());
						p.setL(1);
						count++;
					}
					else if(p.getL()==1)
					{
						n.setQ(p.getQ()/gamma);
						n.setL(1);
					}
				}
				else if(p.getL()==1)
				{
					if(n.getL()==0)
					{
						n.setQ(gamma * p.getQ());
						n.setL(1);
						count++;
					}
					else if(n.getL()==1)
					{
						p.setQ(n.getQ()/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}*/
		
		n = new Nodes(-1,-1);
		System.out.println(count);
		while(count<max)
		{
			count = 0;
			for(int i=0; i<list.size(); i++)
			{
				p = list.get(i);
				if(p.getL()==1)
				{
					//System.out.println(p.getQ());
					count++;
					continue;
				}
				
				s1 = (goal.x-p.x)*(goal.x-p.x);
				s2 = (goal.y-p.y)*(goal.y-p.y);
				dp = Math.sqrt(s1+s2);
				if(p.left!=null)
				{
					n = p.left;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					
					/*if(dn>dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma/n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}*/
					if(dn<dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma * n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}
				}
				
				if(p.right!=null)
				{
					n = p.right;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					
					/*if(dn>dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma/n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}*/
					if(dn<dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma * n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}
				}
				
				if(p.pl!=null)
				{
					n = p.pl;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					
					/*if(dn>dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma/n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}*/
					if(dn<dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma * n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}
				}
				
				if(p.pr!=null)
				{
					n = p.pr;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					
					/*if(dn>dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma/n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}*/
					if(dn<dp)
					{
						if(n.getL()==1)
						{
							if(p.getL()==0)
							{
								p.setQ(gamma * n.getQ());
								p.setL(1);
								count++;
								continue;
							}
						}
					}
				}
			}
			System.out.println(count);
		}
	}
	
	public static void iql2(Nodes goal)
	{
		int size = list.size();
		goal.setL(1);
		goal.setQ(100);
		double gamma = 0.1;
		Nodes p = goal;
		Nodes current = p;
		double s1 = (goal.x-p.x)*(goal.x-p.x);
		double s2 = (goal.y-p.y)*(goal.y-p.y);
		double dp = Math.sqrt(s1+s2);
		double dn = 100000;
		int count = 0;
		int obs = 0;
		int unr = 0;
		int rep = 0;
		
		while(count<max)
		{
			count = 0;
			obs = 0;
			for(int i=0; i<list.size(); i++)
			{
				dn = 100000;
				unr = 0;
				p = list.get(i);
				if(p.getObstacle()==1)
				{
					obs++;
					count++;
					continue;
				}
				if(p.getL()==1)
				{
					count++;
				}
				
				s1 = (goal.x-p.x)*(goal.x-p.x);
				s2 = (goal.y-p.y)*(goal.y-p.y);
				dp = Math.sqrt(s1+s2);
				
				Nodes n = new Nodes(0,0);
				
				if(p.left!=null&&(p.left).getObstacle()==0)
				{
					n = p.left;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
				
				dn=100000;
				if(p.right!=null&&(p.right).getObstacle()==0)
				{
					n = p.right;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
				
				dn=100000;
				if(p.pl!=null&&(p.pl).getObstacle()==0)
				{
					n = p.pl;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
				
				dn=100000;
				if(p.pr!=null&&(p.pr).getObstacle()==0)
				{
					n = p.pr;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
				
				
				dn=100000;
				if(p.nw!=null&&(p.nw).getObstacle()==0)
				{
					n = p.nw;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
				
				dn=100000;
				if(p.ne!=null&&(p.ne).getObstacle()==0)
				{
					n = p.ne;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
				
				dn=100000;
				if(p.sw!=null&&(p.sw).getObstacle()==0)
				{
					n = p.sw;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
				
				dn=100000;
				if(p.se!=null&&(p.se).getObstacle()==0)
				{
					n = p.se;
					s1 = (goal.x-n.x)*(goal.x-n.x);
					s2 = (goal.y-n.y)*(goal.y-n.y);
					dn = Math.sqrt(s1+s2);
					unr = 1;
				}
				
				if(dn<dp)
				{
					if(n.getL()==1)
					{
						if(p.getL()==0)
						{
							p.setQ(gamma * n.getQ());
							p.setL(1);
							lock++;
						}
						else if(p.getL()==1)
						{
							n.setQ(p.getQ()/gamma);
							n.setL(1);
						}
					}
					else if(p.getL()==1)
					{
						if(n.getL()==0)
						{
							n.setQ(gamma * p.getQ());
							n.setL(1);
							lock++;
						}
						else if(n.getL()==1)
						{
							p.setQ(n.getQ()/gamma);
							p.setL(1);
						}
					}
				}
			}
			
			if(unr==0)
			{
				count++;
			}
			System.out.println(count);
			System.out.println(obs);
			if(rep==count)
			{
				break;
			}
			rep = count;
		}
		
		/*Nodes n = new Nodes(0,0);
		
		if(current.left!=null&&(current.left).obstacle==0)
		{
			n = current.left;
		}
		else if(current.right!=null&&(current.right).obstacle==0)
		{
			n = current.right;
		}
		else if(current.pl!=null&&(current.pl).obstacle==0)
		{
			n = current.pl;
		}
		else if(current.pr!=null&&(current.pr).obstacle==0)
		{
			n = current.pr;
		}
		else if(current.nw!=null&&(current.nw).obstacle==0)
		{
			n = current.nw;
		}
		else if(current.ne!=null&&(current.ne).obstacle==0)
		{
			n = current.ne;
		}
		else if(current.sw!=null&&(current.sw).obstacle==0)
		{
			n = current.sw;
		}
		else  if(current.se!=null&&(current.se).obstacle==0)
		{
			n = current.se;
		}
		
		if(dn<dp)
		{
			if(n.L==1)
			{
				if(p.L==0)
				{
					p.setQ(gamma * n.Q);
					p.setL(1);
					lock++;
				}
				else if(p.L==1)
				{
					n.setQ(p.Q/gamma);
					n.setL(1);
				}
			}
			else if(p.L==1)
			{
				if(n.L==0)
				{
					n.setQ(gamma * p.Q);
					n.setL(1);
					lock++;
				}
				else if(n.L==1)
				{
					p.setQ(n.Q/gamma);
					p.setL(1);
				}
			}
		}
		
		while(lock<max)
		{
			current = p;
			s1 = (goal.x-p.x)*(goal.x-p.x);
			s2 = (goal.y-p.y)*(goal.y-p.y);
			dp = Math.sqrt(s1+s2);
			dn = 0;
			double sum1 = 100000;
			double sum2 = 100000;
			double sum3 = 100000;
			double sum4 = 100000;
			double sum5 = 100000;
			double sum6 = 100000;
			double sum7 = 100000;
			double sum8 = 100000;
			
			Nodes temp = new Nodes(0,0);
			if(current.left!=null&&(current.left).obstacle==0)
			{
				temp = current.left;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum1 = Math.sqrt(s1+s2);
			}
			if(current.right!=null&&(current.right).obstacle==0)
			{
				temp = current.right;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum2 = Math.sqrt(s1+s2);
			}
			if(current.pl!=null&&(current.pl).obstacle==0)
			{
				temp = current.pl;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum3 = Math.sqrt(s1+s2);
			}
			if(current.pr!=null&&(current.pr).obstacle==0)
			{
				temp = current.pr;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum4 = Math.sqrt(s1+s2);
			}
			if(current.nw!=null&&(current.nw).obstacle==0)
			{
				temp = current.nw;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum5 = Math.sqrt(s1+s2);
			}
			if(current.ne!=null&&(current.ne).obstacle==0)
			{
				temp = current.ne;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum6 = Math.sqrt(s1+s2);
			}
			if(current.sw!=null&&(current.sw).obstacle==0)
			{
				temp = current.sw;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum7 = Math.sqrt(s1+s2);
			}
			if(current.se!=null&&(current.se).obstacle==0)
			{
				temp = current.se;
				s1 = (goal.x-temp.x)*(goal.x-temp.x);
				s2 = (goal.y-temp.y)*(goal.y-temp.y);
				sum8 = Math.sqrt(s1+s2);
			}
			
			if(sum2<sum1&&sum2<=sum3&&sum2<=sum4&&sum2<sum5&&sum2<=sum6&&sum2<=sum7&&sum2<=sum8&&current.right!=null)
			{
				temp = current.right;
			}
			else if(sum1<=sum2&&sum1<=sum3&&sum1<=sum4&&sum1<sum5&&sum1<=sum6&&sum1<=sum7&&sum1<=sum8&&current.left!=null)
			{
				temp = current.left;
			}
			else if(sum3<sum1&&sum3<sum2&&sum3<=sum4&&sum3<sum5&&sum3<=sum6&&sum3<=sum7&&sum3<=sum8&&current.pl!=null)
			{
				temp = current.pl;
			}
			else if(sum4<sum1&&sum4<sum2&&sum4<sum3&&sum4<sum5&&sum4<=sum6&&sum4<=sum7&&sum4<=sum8&&current.pr!=null)
			{
				temp = current.pr;
			}
			else if(sum5<sum1&&sum5<sum2&&sum5<sum3&&sum5<sum4&&sum5<=sum6&&sum5<=sum7&&sum5<=sum8&&current.nw!=null)
			{
				temp = current.nw;
			}
			else if(sum6<sum1&&sum6<sum2&&sum6<sum3&&sum6<sum4&&sum6<sum5&&sum6<=sum7&&sum6<=sum8&&current.ne!=null)
			{
				temp = current.ne;
			}
			else if(sum7<sum1&&sum7<sum2&&sum7<sum3&&sum7<sum4&&sum7<sum5&&sum7<sum6&&sum7<=sum8&&current.sw!=null)
			{
				temp = current.sw;
			}
			else if(sum8<sum1&&sum8<sum2&&sum8<sum3&&sum8<sum4&&sum8<sum5&&sum8<sum6&&sum8<sum7&&current.se!=null)
			{
				temp = current.se;
			}
			
			n = temp;
			s1 = (goal.x-n.x)*(goal.x-n.x);
			s2 = (goal.y-n.y)*(goal.y-n.y);
			dn = Math.sqrt(s1+s2);
			
			if(dn<dp)
			{
				if(n.L==1)
				{
					if(p.L==0)
					{
						p.setQ(gamma * n.Q);
						p.setL(1);
						lock++;
					}
					else if(p.L==1)
					{
						n.setQ(p.Q/gamma);
						n.setL(1);
					}
				}
				else if(p.L==1)
				{
					if(n.L==0)
					{
						n.setQ(gamma * p.Q);
						n.setL(1);
						lock++;
					}
					else if(n.L==1)
					{
						p.setQ(n.Q/gamma);
						p.setL(1);
					}
				}
			}
			p = n;
		}*/
	}
	
	public static void plotIQL (int sums, int sumg, Graph graph) throws Exception
	{
		Nodes current = list.get(sums);
		Nodes goal = list.get(sumg);
		closed.add(current);
		int counter = 0;
		double psum = -1;
		
		while(!(current.x==goal.x&&current.y==goal.y))
		{
			double sum = 0;
			double sum1 = 0;
			double sum2 = 0;
			double sum3 = 0;
			double sum4 = 0;
			double sum5 = 0;
			double sum6 = 0;
			double sum7 = 0;
			double sum8 = 0;
			
			Nodes temp = new Nodes(-1,-1);
			if(current.left!=null&&(current.left).getObstacle()==0)
			{
				temp = current.left;
				sum1 = temp.getQ();
			}
			if(current.right!=null&&(current.right).getObstacle()==0)
			{
				temp = current.right;
				sum2 = temp.getQ();
			}
			if(current.pl!=null&&(current.pl).getObstacle()==0)
			{
				temp = current.pl;
				sum3 = temp.getQ();
			}
			if(current.pr!=null&&(current.pr).getObstacle()==0)
			{
				temp = current.pr;
				sum4 = temp.getQ();
			}
			if(current.nw!=null&&(current.nw).getObstacle()==0)
			{
				temp = current.nw;
				sum5 = temp.getQ();
			}
			if(current.ne!=null&&(current.ne).getObstacle()==0)
			{
				temp = current.ne;
				sum6 = temp.getQ();
			}
			if(current.sw!=null&&(current.sw).getObstacle()==0)
			{
				temp = current.sw;
				sum7 = temp.getQ();
			}
			if(current.se!=null&&(current.se).getObstacle()==0)
			{
				temp = current.se;
				sum8 = temp.getQ();
			}
			
			if(sum2>sum1&&sum2>=sum3&&sum2>=sum4&&sum2>=sum5&&sum2>=sum6&&sum2>=sum7&&sum2>=sum8&&current.right!=null)
			{
				temp = current.right;
				sum = sum2;
			}
			else if(sum1>0.0&&sum1>=sum2&&sum1>=sum3&&sum1>=sum4&&sum1>=sum5&&sum1>=sum6&&sum1>=sum7&&sum1>=sum8&&current.left!=null)
			{
				temp = current.left;
				sum = sum1;
			}
			else if(sum3>sum1&&sum3>sum2&&sum3>=sum4&&sum3>=sum5&&sum3>=sum6&&sum3>=sum7&&sum3>=sum8&&current.pl!=null)
			{
				temp = current.pl;
				sum = sum3;
			}
			else if(sum4>sum1&&sum4>sum2&&sum4>sum3&&sum4>=sum5&&sum4>=sum6&&sum4>=sum7&&sum5>=sum8&&current.pr!=null)
			{
				temp = current.pr;
				sum = sum4;
			}
			else if(sum5>sum1&&sum5>sum2&&sum5>sum3&&sum5>sum4&&sum5>=sum6&&sum5>=sum7&&sum5>=sum8&&current.nw!=null)
			{
				temp = current.nw;
				sum = sum5;
			}
			else if(sum6>sum1&&sum6>sum2&&sum6>sum3&&sum6>sum4&&sum6>sum5&&sum6>=sum7&&sum6>=sum8&&current.ne!=null)
			{
				temp = current.ne;
				sum = sum6;
			}
			else if(sum7>sum1&&sum7>sum2&&sum7>sum3&&sum7>sum4&&sum7>sum5&&sum7>sum6&&sum7>=sum8&&current.sw!=null)
			{
				temp = current.sw;
				sum = sum7;
			}
			else if(sum8>sum1&&sum8>sum2&&sum8>sum3&&sum8>sum4&&sum8>sum5&&sum8>sum6&&sum8>sum7&&current.se!=null)
			{
				temp = current.se;
				sum = sum8;
			}
			
			int x = temp.x;
			int y = temp.y;
			if(counter>100000)
			{
				counter = 10000;
				//throw new Exception();
				break;
			}
			/*if(counter>1000)
			{
				if(x==-1)
				{
					String index = Integer.toString(current.x)
							+"_"+Integer.toString(current.y);
					Node temp2 = graph.getNode(index);
					temp = closed.get(closed.size()-1);
					temp2.addAttribute("ui.hide");
					closed.remove(closed.size()-1);
					current = closed.get(closed.size()-1);
					counter = 0;
					continue;	
				}
			}*/
			
			String index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp2 = graph.getNode(index);
			/*if(counter>1000)
			{
				temp.setQ(0);
				temp2.addAttribute("ui.hide");
				closed.remove(closed.size()-1);
				current = closed.get(closed.size()-1);
				counter = 0;
				continue;	
			}*/
			/*if(sum<psum)
			{
				temp.setQ(0);
				temp2.addAttribute("ui.hide");
				if(closed.size()==1)
				{
					current = list.get(sums);
					continue;
				}
				closed.remove(closed.size()-1);
				current = closed.get(closed.size()-1);
				psum = sum;
				continue;
			}*/
			int tx = -1;
			int ty = -1;
			int loop = 0;
			int co = 0;
			Nodes ch = new Nodes(-1, -1);
			Nodes cur = new Nodes(-1, -1);
			
			for(int i=closed.size()-1; i>=0; i--)
			{
				Nodes t = closed.get(i);
				tx = t.x;
				ty = t.y;
				co++;
				if(x==tx&&y==ty)
				{
					ch = closed.get(i+1);
					ch.setQ(0);
					cur = t;
					loop = 1;
					break;
				}
			}
			
			if(loop == 1)
			{
				for(int i=0; i<co-1; i++)
				{
					Nodes a = closed.get(closed.size()-1);
					index = Integer.toString(a.x)
							+"_"+Integer.toString(a.y);
					temp2 = graph.getNode(index);
					temp2.addAttribute("ui.hide");
					closed.remove(closed.size()-1);
				}
				current = cur;
				continue;
			}
			
			temp2.removeAttribute("ui.hide");
			temp2.addAttribute("ui.style", "size: 5px; fill-color: red;");
			closed.add(temp);
			current = temp;
			psum = sum;
			counter++;
			
		}
		for(int i=0; i<closed.size()-1; i++)
		{
			Nodes temp = closed.get(i);
			String index = Integer.toString(temp.x)
					+"_"+Integer.toString(temp.y);
			Node temp3 = graph.getNode(index);
			Nodes temp2 = closed.get(i+1);
			String index2 = Integer.toString(temp2.x)
					+"_"+Integer.toString(temp2.y);
			Edge e = temp3.getEdgeBetween(index2);
			e.removeAttribute("ui.hide");
			e.addAttribute("ui.style", "fill-color: red;");
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
				}
				else if(sum1<=sum2&&sum1<=sum3&&sum1<=sum4&&sum1<sum5&&sum1<=sum6&&sum1<=sum7&&sum1<=sum8&&current.left!=null)
				{
					Nodes temp = current.left;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum3<sum1&&sum3<sum2&&sum3<=sum4&&sum3<sum5&&sum3<=sum6&&sum3<=sum7&&sum3<=sum8&&current.pl!=null)
				{
					Nodes temp = current.pl;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum4<sum1&&sum4<sum2&&sum4<sum3&&sum4<sum5&&sum4<=sum6&&sum4<=sum7&&sum4<=sum8&&current.pr!=null)
				{
					Nodes temp = current.pr;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum5<sum1&&sum5<sum2&&sum5<sum3&&sum5<sum4&&sum5<=sum6&&sum5<=sum7&&sum5<=sum8&&current.nw!=null)
				{
					Nodes temp = current.nw;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum6<sum1&&sum6<sum2&&sum6<sum3&&sum6<sum4&&sum6<sum5&&sum6<=sum7&&sum6<=sum8&&current.ne!=null)
				{
					Nodes temp = current.ne;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum7<sum1&&sum7<sum2&&sum7<sum3&&sum7<sum4&&sum7<sum5&&sum7<sum6&&sum7<=sum8&&current.sw!=null)
				{
					Nodes temp = current.sw;
					sx = temp.x;
					sy = temp.y;
				}
				else if(sum8<sum1&&sum8<sum2&&sum8<sum3&&sum8<sum4&&sum8<sum5&&sum8<sum6&&sum8<sum7&&current.se!=null)
				{
					Nodes temp = current.se;
					sx = temp.x;
					sy = temp.y;
				}
				
				size = open.size();
				i = -1;
			}
		}
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
			temp2.removeAttribute("ui.hide");
			temp2.addAttribute("ui.style", "size: 5px; fill-color: red;");
			for(int j=i+1; j<closed.size(); j++)
			{
				Nodes temp3 = closed.get(j);
				if(temp.left!=null&&(temp.left).x==x&&(temp.left).y==y)
				{
					i=j-1;
				}
				if(temp.right!=null&&(temp.right).x==x&&(temp.right).y==y)
				{
					i=j-1;
				}
				if(temp.pl!=null&&(temp.pl).x==x&&(temp.pl).y==y)
				{
					i=j-1;
				}
				if(temp.pr!=null&&(temp.pr).x==x&&(temp.pr).y==y)
				{
					i=j-1;
				}
				if(temp.nw!=null&&(temp.nw).x==x&&(temp.nw).y==y)
				{
					i=j-1;
				}
				if(temp.ne!=null&&(temp.ne).x==x&&(temp.ne).y==y)
				{
					i=j-1;
				}
				if(temp.sw!=null&&(temp.sw).x==x&&(temp.sw).y==y)
				{
					i=j-1;
				}
				if(temp.se!=null&&(temp.se).x==x&&(temp.se).y==y)
				{
					i=j-1;
				}
			}
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
			//System.out.println(x+", "+y);
		}
		/*path2 = closed.size()-1;
		System.out.println("Path Length Given by "
				+ "A* is "+path2);
		System.out.println("Time Taken by "
				+ "A* is "+(time4-time3)+" nanoseconds");*/
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