package graphs;

import graphs.Vertex.color;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

class Vertex {
	public char value;
	public color state;
	public enum color {
		white, grey, black;
	}
	public Vertex(char c) {
		value = c;
		state = color.white;
	}
}
public class GraphDFS {

	public int[][] matrix;
	public Vertex[] vertices;
	public int max_vertices;
	
	public int[] recursiveStack;
	public int count = 0;
	
	public GraphDFS(int n) {
		matrix = new int[n][n];
		vertices = new Vertex[n];
		recursiveStack = new int[n];
		max_vertices = n;
	}
	
	public void addVertex(char v) {
		vertices[count++] = new Vertex(v);
	}
	
	public void addEdge(int source, int dest) {
		matrix[source][dest] = 1;
	}
	
	public int getUnvisitedNeighbours(int source) {
		for(int i = 0; i < max_vertices;i++) {
			if(matrix[source][i] == 1 && vertices[i].state == color.white ) {
				return i;
			}
		}
		
		return -1;
	}
	
	public boolean checkNeighbours(int source) {
		for(int i = 0; i < max_vertices;i++) {
			if(matrix[source][i] == 1 && vertices[i].state == color.grey ) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean DFS_Cycles() {

		max_vertices = count;
	
		Deque<Integer> stack = new ArrayDeque<Integer>();
		
		Vertex vertex = vertices[0];
		//vertex.visited = true;
		System.out.println("Visiting:"+vertex.value);
		
		vertex.state = color.grey;
		
		stack.add(0);
		 
		while(!stack.isEmpty()) {
			Integer v = stack.peek();
			if(checkNeighbours(v))
				return true;
			int neigh = getUnvisitedNeighbours(v);
			if(neigh == -1){
				vertices[v].state = color.black;
				stack.pop();
			} else {
				Vertex vneigh= vertices[neigh];
				vneigh.state = color.grey;
				System.out.println("Visiting:"+vneigh.value);
				stack.push(neigh);
			}
		}
		
		return false;
	}
	
	
	
	public LinkedList<Vertex>  topologicalSort() {
		Deque<Integer> stack = new ArrayDeque<Integer>();
		LinkedList<Vertex> topologylist = new LinkedList<Vertex>();
		
		stack.add(0);//add the root node to the frontier
		
		while(!stack.isEmpty()) {
			Integer current = stack.peek();
			//retrieve the vertex
			Vertex vertex = vertices[current];
			vertex.state = color.grey;
			
			int neigh = getUnvisitedNeighbours(current); //get white(unvisited) neighbours
			
			if(neigh != -1) { //no neighbours left!
				Vertex vneigh= vertices[neigh]; 
				vneigh.state = color.grey;
				stack.push(neigh);
			} else {
				vertex.state = color.black;
				stack.pop();
				topologylist.addFirst(vertex);
			}
			
		}
		
		return topologylist;
		
	}
	
	public static void main(String[] args) {
		GraphDFS theGraph = new GraphDFS(20);
		
		 theGraph.addVertex('A');    // 0  (start for dfs)
	      theGraph.addVertex('B');    // 1
	      theGraph.addVertex('C');    // 2
	      theGraph.addVertex('D');    // 3
	      theGraph.addVertex('E');    // 4

	      theGraph.addEdge(0, 1);     // AB
	      theGraph.addEdge(0, 3);     // AD
	      theGraph.addEdge(0, 4);	//AE
	      
	      theGraph.addEdge(1, 3);     // BD
	      theGraph.addEdge(1, 2);     // BC
	      
	      theGraph.addEdge(3, 2);     // DC
	      theGraph.addEdge(3, 4); //DE
	      theGraph.addEdge(3, 0); //DA
	      
	     
	      LinkedList<Vertex>  list = theGraph.topologicalSort();
	      
	      for(Vertex v : list) {
	    	  System.out.println(v.value);
	      }

	}

}
