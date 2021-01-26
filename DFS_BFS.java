import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class DFS_BFS
{
	static Nodes root;
	static int count;
	static int eN;
	static int max;
	static int pN;
	static int pL;
	static int p1;
	static int sumL;
	static int constraint;
	static int energy;
	static long time1;
	static long time2;
	static int ex;
	static int arr[][];
	static ArrayList<Integer> obs;
	static ArrayList<Nodes> list;
	static ArrayList<Nodes> closed;
	static ArrayList<Nodes> nodes;
	public static void main(String args[])
	{
		int choice = 1;
		while(choice!=0)
		{
			Scanner s = new Scanner(System.in);
			System.out.println("Enter 0 to Exit, 1 to compare DFS and BFS");
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
					+ " (1: 10x10 2:50x50 3:100x100)");
			int env = s.nextInt();
			System.out.println();
			
			if(env!=1&&env!=2&&env!=3)
			{
				System.out.println("Invalid choice. Please make a valid choice.");
				continue;
			}
			
			obs = new ArrayList<Integer>();
			closed = new ArrayList<Nodes>();
			nodes = new ArrayList<Nodes>();
			
			int nnodes = 0;
			
			if(env == 1)
			{
				nnodes = 10;
			}
			
			if(env == 2)
			{
				nnodes = 50;
			}
			
			if(env == 3)
			{
				nnodes = 100;
			}
			max = nnodes*nnodes;
			arr = new int[nnodes][nnodes];
			
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
			int sx = 0;
			int sy = 0;
			
			if(env==1)
			{
				sx = 5;
				sy = 5;
				p1 = nnodes*sx/2;
			}
			if(env==2)
			{
				sx = 25;
				sy = 25;
				p1 = nnodes*sx/2;
			}
			if(env==3)
			{
				sx = 50;
				sy = 50;
				p1 = nnodes*sx/2;
			}
			
			
			constraint = nnodes*3;
			
			System.out.println();
			System.out.println("Creating Environment...");
			System.out.println();
			
			if(choice==1||choice==2)
			{
				int nobs = 0;
				Random random = new Random();
				while(nobs<per)
				{
					int rx = random.nextInt(nnodes);
					int ry = random.nextInt(nnodes);
					if(rx==sx||ry==sy)
					{
						continue;
					}
					obs.add(rx);
					obs.add(ry);
					arr[rx][ry] = 1;
					nobs++;
				}
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
			
			int sums=-1;
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
			
			start = list.get(sums);
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
			
			energy = constraint;
			time1 = 0;
			time2 = 0;
			int len1 = 0;
			int len2 = 0;
			pN = 1;
			sumL = 0;
			p1 = sx*nnodes/2;
			
			if(choice==1)
			{
				System.out.println("Running DFS...");
				System.out.println();
				time1 = System.nanoTime();
				
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						dfs(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}

						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				for(int i=0; i<list.size(); i++)
				{
					Nodes temp = list.get(i);
					temp.visited = 0;
				}
				
				p1 = sx*nnodes/2;
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						dfs2(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}
						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				for(int i=0; i<list.size(); i++)
				{
					Nodes temp = list.get(i);
					temp.visited = 0;
				}

				p1 = sx*nnodes/2;
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						dfs3(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}
						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				for(int i=0; i<list.size(); i++)
				{
					Nodes temp = list.get(i);
					temp.visited = 0;
				}
				
				p1 = sx*nnodes/2;
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						dfs4(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}
						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				time2 = System.nanoTime();
				pN--;
				
				plot(graph);
			}
			
			
			int x = start.x;
			int y = start.y;
			index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp = graph.getNode(index);
			temp.removeAttribute("ui.hide");
			temp.addAttribute("ui.style", "size: 5px; fill-color: blue;");
			
			int miss = nnodes*nnodes;
			
			for(int i = 0; i<nnodes; i++)
			{
				for(int j = 0; j<nnodes; j++)
				{
					if(arr[i][j]==1)
					{
						miss--;
					}
				}
			}
			
			float unex = ((float)miss/(float)(nnodes*nnodes))*100;
			double stime = (time2/1000)-(time1/1000);
			System.out.println();
			System.out.println("Time Taken: "+stime+" microseconds");
			System.out.println();
			System.out.println("Total Number of Trips: "+pN);
			System.out.println();
			System.out.println("Energy Consumed: "+sumL);
			System.out.println();
			System.out.println("Number of Points Missed: "+miss);
			System.out.println();
			System.out.println("Percentage Unexplored: "+unex+"%");
			System.out.println();
			System.out.println();
			graph.display(false);
			
			nodes = new ArrayList<Nodes>();
			
			count = 0;
			eN = nnodes*(nnodes-1)*2;
			list = new ArrayList<Nodes>();
			
			graph = new SingleGraph("grid");
			gen = new GridGenerator();
	
			gen.addSink(graph);
			gen.begin();
	
			for(int i=0; i<nnodes-1; i++) 
			{
				gen.nextEvents();
			}
			gen.end();
			
			createTree(nnodes);
			
			addPaths(list, nnodes-1);
			
			start = list.get(0);
			
			sums=-1;
			exit = 0;
			
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
			
			start = list.get(sums);
			index = "";
			
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
			
			energy = constraint;
			time1 = 0;
			time2 = 0;
			len1 = 0;
			len2 = 0;
			pN = 1;
			sumL = 0;
			p1 = sx*nnodes/2;
			
			if(choice==1)
			{
				System.out.println("Running BFS...");
				System.out.println();
				time1 = System.nanoTime();
				
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						bfs(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}

						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				for(int i=0; i<list.size(); i++)
				{
					Nodes tempy = list.get(i);
					tempy.visited = 0;
				}
				
				p1 = sx*nnodes/2;
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						bfs2(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}
						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				for(int i=0; i<list.size(); i++)
				{
					Nodes tempy = list.get(i);
					tempy.visited = 0;
				}

				p1 = sx*nnodes/2;
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						bfs3(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}
						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				for(int i=0; i<list.size(); i++)
				{
					Nodes tempy = list.get(i);
					tempy.visited = 0;
				}
				
				p1 = sx*nnodes/2;
				while(p1>0)
				{
					ex = 0;
					len1 = nodes.size();
					try
					{
						bfs4(start, 0);
					}
					catch(Exception e)
					{
						len2 = nodes.size();
						if((len2-len1-1)==0)
						{
							break;
						}
						energy = constraint;
						sumL = sumL + (len2-len1-1);
						pL = 0;
						pN++;
						Nodes n = new Nodes(-1,-1);
						nodes.add(n);
						continue;
					}
					len2 = nodes.size();
					if((len2-len1-1)==0)
					{
						break;
					}
					energy = constraint;
					sumL = sumL + (len2-len1-1);
					pL = 0;
					pN++;
					Nodes n = new Nodes(-1,-1);
					nodes.add(n);
				}
				
				time2 = System.nanoTime();
				pN--;
				
				plot(graph);
			}
			
			x = start.x;
			y = start.y;
			index = Integer.toString(x)
					+"_"+Integer.toString(y);
			temp = graph.getNode(index);
			temp.removeAttribute("ui.hide");
			temp.addAttribute("ui.style", "size: 5px; fill-color: blue;");
			
			miss = nnodes*nnodes;
			
			for(int i = 0; i<nnodes; i++)
			{
				for(int j = 0; j<nnodes; j++)
				{
					if(arr[i][j]==1)
					{
						miss--;
					}
				}
			}
			
			unex = ((float)miss/(float)(nnodes*nnodes))*100;
			stime = (time2/1000)-(time1/1000);
			System.out.println();
			System.out.println("Time Taken: "+stime+" microseconds");
			System.out.println();
			System.out.println("Total Number of Trips: "+pN);
			System.out.println();
			System.out.println("Energy Consumed: "+sumL);
			System.out.println();
			System.out.println("Number of Points Missed: "+miss);
			System.out.println();
			System.out.println("Percentage Unexplored: "+unex+"%");
			System.out.println();
			System.out.println();
			graph.display(false);
		}
		System.exit(0);
	}
	
	public static void dfs(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.left!=null)
		{
			Nodes temp = current.left;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				dfs(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.right!=null)
		{
			Nodes temp = current.right;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				dfs(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.left==null&&current.right==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.right==null&&(current.left).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.right!=null&&current.left==null&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.right!=null&&(current.left).visited==1&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.left==null&&current.right==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.right==null&&(current.left).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.right!=null&&current.left==null&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.right!=null&&(current.left).visited==1&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void dfs2(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pr!=null)
		{
			Nodes temp = current.pr;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				dfs2(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.right!=null)
		{
			Nodes temp = current.right;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				dfs2(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.pr==null&&current.right==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.right==null&&(current.pr).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.right!=null&&current.pr==null&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.right!=null&&(current.pr).visited==1&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.pr==null&&current.right==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.right==null&&(current.pr).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.right!=null&&current.pr==null&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.right!=null&&(current.pr).visited==1&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void dfs3(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pr!=null)
		{
			Nodes temp = current.pr;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				dfs3(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pl!=null)
		{
			Nodes temp = current.pl;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				dfs3(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.pr==null&&current.pl==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.pl==null&&(current.pr).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pl!=null&&current.pr==null&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.pl!=null&&(current.pr).visited==1&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.pr==null&&current.pl==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.pl==null&&(current.pr).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pl!=null&&current.pr==null&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.pl!=null&&(current.pr).visited==1&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void dfs4(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.left!=null)
		{
			Nodes temp = current.left;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				dfs4(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pl!=null)
		{
			Nodes temp = current.pl;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				dfs4(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.left==null&&current.pl==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.pl==null&&(current.left).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pl!=null&&current.left==null&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.pl!=null&&(current.left).visited==1&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.left==null&&current.pl==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.pl==null&&(current.left).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pl!=null&&current.left==null&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.pl!=null&&(current.left).visited==1&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void bfs(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.right!=null)
		{
			Nodes temp = current.right;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				bfs(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.left!=null)
		{
			Nodes temp = current.left;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				bfs(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.left==null&&current.right==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.right==null&&(current.left).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.right!=null&&current.left==null&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.right!=null&&(current.left).visited==1&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.left==null&&current.right==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.right==null&&(current.left).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.right!=null&&current.left==null&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.right!=null&&(current.left).visited==1&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void bfs2(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.right!=null)
		{
			Nodes temp = current.right;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				bfs2(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pr!=null)
		{
			Nodes temp = current.pr;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				bfs2(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.pr==null&&current.right==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.right==null&&(current.pr).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.right!=null&&current.pr==null&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.right!=null&&(current.pr).visited==1&&(current.right).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.pr==null&&current.right==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.right==null&&(current.pr).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.right!=null&&current.pr==null&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.right!=null&&(current.pr).visited==1&&(current.right).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void bfs3(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pl!=null)
		{
			Nodes temp = current.pl;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				bfs3(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pr!=null)
		{
			Nodes temp = current.pr;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				bfs3(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.pr==null&&current.pl==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.pl==null&&(current.pr).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pl!=null&&current.pr==null&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pr!=null&&current.pl!=null&&(current.pr).visited==1&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.pr==null&&current.pl==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.pl==null&&(current.pr).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pl!=null&&current.pr==null&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pr!=null&&current.pl!=null&&(current.pr).visited==1&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void bfs4(Nodes current, int dis) throws Exception
	{
		nodes.add(current);
		
		//System.out.print(current.x+","+current.y+"   ");
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.pl!=null)
		{
			Nodes temp = current.pl;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				dis = dis + 1;
				energy = energy - 1;
				dfs4(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			return;
		}
		
		if(current.left!=null)
		{
			Nodes temp = current.left;
			if(temp.obstacle==1)
			{
				temp.visited = 1;
				arr[temp.x][temp.y] = 1;
			}
			if(temp.visited!=1)
			{
				pL++;
				energy = energy - 1;
				dis = dis + 1;
				bfs4(temp, dis);
				nodes.add(current);
				//System.out.print(current.x+","+current.y+"   ");
			}
		}
		
		
		if((energy)<=dis||ex==1)
		{
			if(ex==1)
			{
				pL++;
			}
			ex = 1;
			if(current.left==null&&current.pl==null)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.pl==null&&(current.left).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.pl!=null&&current.left==null&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			if(current.left!=null&&current.pl!=null&&(current.left).visited==1&&(current.pl).visited==1)
			{
				arr[current.x][current.y] = 1;
				current.visited = 1;
				p1--;
				//System.out.println(1);
			}
			return;
		}
		
		dis = dis - 1;
		energy = energy - 1;
		pL++;
		if(current.left==null&&current.pl==null)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.pl==null&&(current.left).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.pl!=null&&current.left==null&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		if(current.left!=null&&current.pl!=null&&(current.left).visited==1&&(current.pl).visited==1)
		{
			arr[current.x][current.y] = 1;
			current.visited = 1;
			p1--;
			//System.out.println(1);
		}
		
	}
	
	public static void plot(Graph graph)
	{
		for(int i=0; i<nodes.size(); i++)
		{
			Nodes temp = nodes.get(i);
			int x = temp.x;
			int y = temp.y;
			if(x==-1)
			{
				continue;
			}
			String index = Integer.toString(x)
					+"_"+Integer.toString(y);
			Node temp2 = graph.getNode(index);
			//System.out.println(index);
			temp2.removeAttribute("ui.hide");
			temp2.addAttribute("ui.style", "size: 5px; fill-color: red;");
			for(int j=i+1; j<nodes.size(); j++)
			{
				Nodes temp3 = nodes.get(j);
				int x2 = temp3.x;
				if(x2==-1)
				{
					continue;
				}
				String index2 = Integer.toString(temp3.x)
						+"_"+Integer.toString(temp3.y);
				try
				{
					Edge e = temp2.getEdgeBetween(index2);
					e.removeAttribute("ui.hide");
					e.addAttribute("ui.style", "fill-color: red;");
				}
				catch(Exception e)
				{
					
				}
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
	int obstacle;
	Nodes left;
	Nodes right;
	Nodes pl;
	Nodes pr;
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
	}
	public void setVisited(int n)
	{
		visited = n;
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
	public int getVisited()
	{
		return visited;
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