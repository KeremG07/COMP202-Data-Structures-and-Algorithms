import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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
		
		System.out.println(hw4.totalTransitTime(graph));
		System.out.println(hw4.cheapestTransitTime(graph));
		System.out.println(hw4.timeIncrease(graph));
	
	}
	
	// You can add any methods you need, both to this file and Graph.java file
	
	// Unused adjacency list constructor
	ArrayList<ArrayList<String>> constructAdj(Graph graph) {
		ArrayList<ArrayList<String>> adj = new ArrayList<ArrayList<String>>();
		
		for(int i=0; i<graph.vertices.size(); i++) {
			String v = graph.vertices.get(i);
			for(Edge e : graph.edges) {
				if(e.src.equals(v) && !adj.get(i).contains(e.dst)) {
					adj.get(i).add(e.dst);
				}
				else if(e.dst.equals(v) && !adj.get(i).contains(e.src)) {
					adj.get(i).add(e.src);
				}
			}
		}
		
		return adj;
	}
	
	
	Graph CreateMSTofCost(Graph graph) {
		Graph mst = new Graph();
		
		mst.vertices.add(graph.vertices.get(0));
		
		// While all vertices haven't been added yet
		while(mst.vertices.size() != graph.vertices.size()) {
			int minCost = 99999;
			Edge minEdge = null;
			
			for(Edge e : graph.edges) {
				if((!mst.vertices.contains(e.src) && mst.vertices.contains(e.dst)) || 
						(mst.vertices.contains(e.src) && !mst.vertices.contains(e.dst))) {
					if(e.cost <= minCost) {
						minCost = e.cost;
						minEdge = e;
					}
				}
			}
			
			if(minEdge != null) {
				mst.edges.add(minEdge);
				if(!mst.vertices.contains(minEdge.src)) {
					mst.vertices.add(minEdge.src);
				}
				if(!mst.vertices.contains(minEdge.dst)) {
					mst.vertices.add(minEdge.dst);
				}
			}
		}
		
		return mst;
	}
	
	int singleSourceDijkstra(Graph graph, int vertexIndex) {
		int total = 0;
		
		ArrayList<String> unvisited = new ArrayList<String>();
		HashMap<String, Integer> map = new HashMap<>();
		
		for(String v : graph.vertices) {
			unvisited.add(v);
			map.put(v, 99999);
		}
		
		map.replace(graph.vertices.get(vertexIndex), 0);
		unvisited.remove(graph.vertices.get(vertexIndex));
		
		while(!unvisited.isEmpty()) {
			for(Edge e : graph.edges) {
				if(!unvisited.contains(e.src) && unvisited.contains(e.dst)) {
					if(map.get(e.src) + e.latency < map.get(e.dst)) {
						map.replace(e.dst, map.get(e.src) + e.latency);
					}
				}
				if(unvisited.contains(e.src) && !unvisited.contains(e.dst)) {
					if(map.get(e.dst) + e.latency < map.get(e.src)) {
						map.replace(e.src, map.get(e.dst) + e.latency);
					}
				}
			}
			int minLat = 99999;
			String minKeyVertex = null;
			for(String v : unvisited) {
				if(map.get(v) < minLat) {
					minLat = map.get(v);
					minKeyVertex = v;
				}
			}
			unvisited.remove(minKeyVertex);
		}
		
		for(String v : graph.vertices) {
			total += map.get(v);
		}
		
		return total;
	}
	
	int sumOfDijkstra(Graph graph) {
		int total = 0;
		
		for(int i=0; i<graph.vertices.size(); i++) {
			total += singleSourceDijkstra(graph, i);
		}
		
		return total;
	}
	
	int FloydWarshallSum(Graph graph) {
		int vc = graph.vertices.size();				// Vertex Count
		int latency[][] = new int[vc][vc];
		HashMap<String, Integer> map = new HashMap<>();			// to assign all vertices to an integer
		
		for(int i=0; i<vc; i++) {
			for(int j=0; j<vc; j++) {
				latency[i][j] = 99999;				// Set every distance to infinity
			}
		}
		
		for(int i=0; i<vc; i++) {
			map.put(graph.vertices.get(i), i);					// assign vertices to integers here
			latency[i][i] = 0;									// travel latency is zero for the vertex itself
		}
		
		for(Edge e : graph.edges) {
			latency[map.get(e.src)][map.get(e.dst)] = e.latency;		// Place every existing latency value
			latency[map.get(e.dst)][map.get(e.src)] = e.latency;
		}
		
		for(int k=0; k<vc; k++) {
			for(int i=0; i<vc; i++) {
				for(int j=0; j<vc; j++) {
					if(latency[i][j] > latency[i][k] + latency[k][j]) {
						latency[i][j] = latency[i][k] + latency[k][j];
					}
				}
			}
		}
		
		// Now that we have the latency graph implemented,
		// Let's calculate total latency by summing all elements of the matrix.
		
		int sum = 0;
		for(int k=0; k<vc; k++) {
			for(int i=0; i<vc; i++) {
				sum += latency[k][i];
			}
		}
		
		return sum;
	}
	
	// The method for task 1 
	int totalTransitTime(Graph graph) {
		// TODO Auto-generated method stub
		
		// I have implemented both algorithms just out of curiosity.
		
		// Floyd-Warshall Algorithm
		return FloydWarshallSum(graph);
		
		// Dijkstra's Algorithm
		// return sumOfDijkstra(graph);
	}

	// The method for task 2 
	int cheapestTransitTime(Graph graph) {
		// TODO Auto-generated method stub
		Graph mst = CreateMSTofCost(graph);
		
		// Using Floyd-Warshall Sum on an already Minimum Spanning Tree 
		// will result in finding the total travel latency to each vertex
		// from each vertex.
		
		return FloydWarshallSum(mst);
	}

	// The method for task 3 
	int timeIncrease(Graph graph) {
		// TODO Auto-generated method stub
		return cheapestTransitTime(graph) - totalTransitTime(graph);
	}
	
}
