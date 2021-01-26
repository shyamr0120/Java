import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class IQL
{
	static Nodes root;
	static int count;
	static int eN;
	static int max;
	static long time1;
	static long time2;
	static ArrayList<Integer> obs;
	static ArrayList<Nodes> list;
	static ArrayList<Nodes> closed;
	public static void main(String args[])
	{
		int choice = 1;
		while(choice!=0)
		{
			Scanner s = new Scanner(System.in);
			System.out.println("Enter 0 to Exit, 1 to run IQL");
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
			
			obs = new ArrayList<Integer>();
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
			
			String gi = "";
			
			if(env==1)
			{
				s = new Scanner(System.in);
				System.out.println("Enter goal index e.g.: 0,0."
						+ " Range is from (0,0) to (99,99).");
				gi = s.nextLine();
			}
			if(env==2)
			{
				s = new Scanner(System.in);
				System.out.println("Enter goal index e.g.: 0,0."
						+ " Range is from (0,0) to (199,199).");
				gi = s.nextLine();
			}
			if(env==3)
			{
				s = new Scanner(System.in);
				System.out.println("Enter goal index e.g.: 0,0."
						+ " Range is from (0,0) to (299,299).");
				gi = s.nextLine();
			}
			
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
			
			System.out.println();
			System.out.println("Creating Environment...");
			System.out.println();
			
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
			
			count = 0;
			eN = nnodes*(nnodes-1)*2;
			list = new ArrayList<Nodes>();
			
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
				System.out.println("Running IQL...");
				System.out.println();
				try
				{
					time1 = System.currentTimeMillis();
					iql(sumg);
					plotIQL(sums, sumg, graph);
					time2 = System.currentTimeMillis();
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
		
		n = new Nodes(-1,-1);
		while(count<max)
		{
			count = 0;
			for(int i=0; i<list.size(); i++)
			{
				p = list.get(i);
				if(p.getL()==1)
				{
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
		}
	}
	
	public static void plotIQL (int sums, int sumg, Graph graph) throws Exception
	{
		Nodes current = list.get(sums);
		Nodes goal = list.get(sumg);
		closed.add(current);
		int counter = 0;
		
		while(!(current.x==goal.x&&current.y==goal.y))
		{
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
			}
			else if(sum1>0.0&&sum1>=sum2&&sum1>=sum3&&sum1>=sum4&&sum1>=sum5&&sum1>=sum6&&sum1>=sum7&&sum1>=sum8&&current.left!=null)
			{
				temp = current.left;
			}
			else if(sum3>sum1&&sum3>sum2&&sum3>=sum4&&sum3>=sum5&&sum3>=sum6&&sum3>=sum7&&sum3>=sum8&&current.pl!=null)
			{
				temp = current.pl;
			}
			else if(sum4>sum1&&sum4>sum2&&sum4>sum3&&sum4>=sum5&&sum4>=sum6&&sum4>=sum7&&sum5>=sum8&&current.pr!=null)
			{
				temp = current.pr;
			}
			else if(sum5>sum1&&sum5>sum2&&sum5>sum3&&sum5>sum4&&sum5>=sum6&&sum5>=sum7&&sum5>=sum8&&current.nw!=null)
			{
				temp = current.nw;
			}
			else if(sum6>sum1&&sum6>sum2&&sum6>sum3&&sum6>sum4&&sum6>sum5&&sum6>=sum7&&sum6>=sum8&&current.ne!=null)
			{
				temp = current.ne;
			}
			else if(sum7>sum1&&sum7>sum2&&sum7>sum3&&sum7>sum4&&sum7>sum5&&sum7>sum6&&sum7>=sum8&&current.sw!=null)
			{
				temp = current.sw;
			}
			else if(sum8>sum1&&sum8>sum2&&sum8>sum3&&sum8>sum4&&sum8>sum5&&sum8>sum6&&sum8>sum7&&current.se!=null)
			{
				temp = current.se;
			}
			
			int x = temp.x;
			int y = temp.y;
			if(counter>100000)
			{
				throw new Exception();
			}
			
			String index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp2 = graph.getNode(index);
			
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
			for(int j=0; j<obs.size()-1; j+=2)
			{
				int xx = obs.get(j);
				int yy = obs.get(j+1);
				if(x==xx&&y==yy)
				{
					temp.setObstacle(1);
					break;
				}
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