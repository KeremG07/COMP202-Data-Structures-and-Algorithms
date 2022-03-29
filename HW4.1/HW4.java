import java.util.Scanner;
import java.util.ArrayList;

public class HW4 {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Graph graph = new Graph();
		
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			String[] parts = line.split(" ");
			if (parts[0].equals("end")) break;
			String src = parts[0];
			String dst = parts[1];
			int cost = Integer.parseInt(parts[2]);
			int latency = Integer.parseInt(parts[3]);
			
			graph.addVertex(src);
			graph.addVertex(dst);
			Edge edge = new Edge(src, dst, cost, latency);
			graph.addEdge(edge);
		}
		
		//System.out.println(Arrays.deepToString(graph.asArray(false)));
		//System.out.println(Arrays.deepToString(graph.asArray(true)));
		
		HW4 hw4 = new HW4();
		scan.close();
		
		System.out.println(hw4.totalLinkCost(graph));
		System.out.println(hw4.cheapestNetwork(graph));
		System.out.println(hw4.savedAmount(graph));
	
	}
	
	// You can add any methods you need, both to this file and Graph.java file

	// The method for task 1 
	int totalLinkCost(Graph graph) {
		// TODO Auto-generated method stub
		int totalcost = 0;
		for(int i=0; i < graph.edges.size(); i++) {
			totalcost += graph.edges.get(i).cost;
		}
		return totalcost;
	}

	// The method for task 2 
	int cheapestNetwork(Graph graph) {
		// TODO Auto-generated method stub
		String root = graph.vertices.get(0);
		
		ArrayList<String> visited = new ArrayList<String>();
		int totalCost = 0;
		
		visited.add(root);
		
		// While all vertices haven't been added yet
		while(visited.size() != graph.vertices.size()) {
			int minCost = 99999;
			Edge minEdge = null;
			
			for(Edge e : graph.edges) {
				if((!visited.contains(e.src) && visited.contains(e.dst)) || 
						(visited.contains(e.src) && !visited.contains(e.dst))) {
					if(e.cost <= minCost) {
						minCost = e.cost;
						minEdge = e;
					}
				}
			}
			
			if(minEdge != null) {
				totalCost += minEdge.cost;
								
				if(!visited.contains(minEdge.src)) {
					visited.add(minEdge.src);
				}
				if(!visited.contains(minEdge.dst)) {
					visited.add(minEdge.dst);
				}
			}
		}
		
		return totalCost;
	}

	// The method for task 3 
	int savedAmount(Graph graph) {
		// TODO Auto-generated method stub		
		return totalLinkCost(graph) - cheapestNetwork(graph);
	}
}
