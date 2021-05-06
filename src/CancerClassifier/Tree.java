package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree {
	int dim;
	int[][] ma;

	// i
	public Tree(int d) {
		this.dim = d;
		this.ma = new int[d][d];
	}

	// ii
	public int getDim() {
		return this.dim;
	}

	// iii
	@Override
	public String toString() {
		return "Tree [dim=" + dim + ", ma=" + Arrays.deepToString(ma) + "]";
	}

	// iv
	public void addEdge(int o, int d) {
		if (o>=0 && o<this.dim && d>=0 && d<this.dim) {
			this.ma[o][d] = 1;
		} else {
			throw new AssertionError("node not in graph");
		}
	} // O(1) (lista de adj. -> O(1))

	// v
	public void removeEdge(int o, int d) {
		if (o>=0 && o<this.dim && d>=0 && d<this.dim) {
			this.ma[o][d] = 0;
		} else {
			throw new AssertionError("node not in graph");
		}
	} // O(1) (lista de adj. -> O(n))

	// vi
	public boolean edgeQ(int o, int d) {
		if (o>=0 && o<this.dim && d>=0 && d<this.dim) {
			return ma[o][d] == 1;
		} else {
			throw new AssertionError("node not in graph");
		}
	} // O(1) (lista de adj. -> O(n))

	// vii
	public LinkedList<Integer> offspring(int o){
		LinkedList<Integer> r = new LinkedList<Integer>();
		if (o>=0 && o<this.dim) {
			for (int i = 0; i < ma.length; i++) {
				if (ma[o][i] == 1) {
					r.add(i);
				}
			}
			return r;
		} else {
			throw new AssertionError("node not in graph");
		}
	} // O(n)

	// viii
	public ArrayList<Integer> BFS(int o){
		if ( o>=0 && o<this.dim ) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			Queue<Integer> q = new LinkedList<Integer>();
			boolean[] visited = new boolean[this.dim];
			q.add(o);
			while(! q.isEmpty() ) {
				System.out.println(q);
				int node = q.remove(); // remove o mais antigo
				if (!visited[node]) {
					r.add(node);
					visited[node] = true;
					for(int i : this.offspring(node)) {
						q.add(i);
					}
				}
			}
			return r;
		} else {
			throw new AssertionError("node not in graph");
		}
	}

	public ArrayList<Integer> DFS(int o) {
		if ( o>=0 && o<this.dim ) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			Stack<Integer> stack = new Stack<Integer>();
			boolean[] visited = new boolean[this.dim];
			stack.push(o);
			while(! stack.isEmpty() ) {
				System.out.println(stack);
				int node = stack.pop(); // remove o mais recente
				if (!visited[node]) {
					r.add(node);
					visited[node] = true;
					for(int i : this.offspring(node)) {
						stack.push(i);
					}
				}
			}
			return r;
		} else {
			throw new AssertionError("node not in graph");
		}
	}

	// x
	public boolean pathQ(int o, int d) {
		if (o>=0 && o<this.dim && d>=0 && d<this.dim) {
			boolean[] visited = new boolean[this.dim];
			Queue<Integer> q = new LinkedList<Integer>();
			boolean found = false;
			q.add(o);
			while(!q.isEmpty() && !found) {
				int node = q.remove();
				if (!visited[node]) {
					found = (node == d);
					visited[node] = true;
					for (int i : this.offspring(node)) {
						q.add(i);
					}
				}
			}
			return found;
		} else {
			throw new AssertionError("node not in graph");
		}
	}

	public static void main(String[] args) {
		Tree g = new Tree(5);
		int[][] edges = {{0,1}, {0,3}, {1,1}, {1,2}, {2,1}, {3,2}, {2,4}, {4,3}};
		for(int[] e : edges) {
			g.addEdge(e[0], e[1]);
		}
		System.out.println(g);
		//g.removeEdge(1, 2);
		//System.out.println(g);
		System.out.println(g.edgeQ(1, 1));
		System.out.println("offspring:" + g.offspring(0));
		System.out.println("BFS:"+g.BFS(0));
		System.out.println("DFS:"+g.DFS(0));
		System.out.println("pathQ1:"+g.pathQ(0,4));
		System.out.println("pathQ2:"+g.pathQ(3,0));
	}
}
