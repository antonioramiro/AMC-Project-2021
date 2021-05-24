package DiagnosisAssistant;

import java.io.Serializable;
import java.util.ArrayList;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

//implements the Chow Liu algorithm 
public class ChowLiu implements Serializable{
	private static final long serialVersionUID = 6L;

	int dim_Dataset;
	int dim_measurement;
	WGraph wg;
	 

	//Constructor, receives a dataset
	public ChowLiu (Dataset T) {
		this.dim_Dataset = T.len();
		this.dim_measurement = T.getMeasurementNumber();
		this.wg = createWGraph(T);
		
		
		
	}

	//creates a Weighted Graph form the dataset
	public WGraph createWGraph (Dataset T) {
		int dim = this.dim_measurement;
		WGraph wg = new WGraph(dim);
	
		for (int i = 0; i < dim; i++ ) {
			for (int j = i; j < dim; j++ ) {
				if (i != j) {
					wg.add(i, j, this.WeightSetter(T, i, j));
				}
				
			}
		}
		return wg;
	}

	//calculates the weight of the edge (i,j) 
	public double WeightSetter (Dataset T, int i, int j) { //recebe um dataset e calcula a frequencia de cada fibra, assumindo que todos os n�s est�o ligados 
		double fibfrequency = 0;
		double m = T.len();
		
		
		for (int xi = 0; xi < T.measurementDim(i); xi++) { //calcula a frequencia de cada xi na fibra i
		
			for (int xj = 0; xj < T.measurementDim(j); xj++) { //calcula a frequencia de cada xj na fibra j
					
					if ((T.Count(i, j, xi, xj)/m) == 0 || (T.Count(i, xi)/m) == 0 || (T.Count(j, xj)/m) == 0) {
							fibfrequency = fibfrequency + 0;
							
				
					}
					else {
							fibfrequency = fibfrequency + (T.Count(i, j, xi, xj)/m)*(Math.log10((T.Count(i, j, xi, xj)/m)/((T.Count(i, xi)/m)*(T.Count(j, xj)/m)))); //formula do prof
						
					}
					
				}
			}
		
	return fibfrequency;
	}
		
	
	
	//returns the list of the descendants for the node "o"
	public LinkedList<Integer> offspring(Tree t, int o){ 
		LinkedList<Integer> children = new LinkedList<Integer>();
		if (o>=0 && o<t.getDimension()) {
			for (int i = 0; i < t.getDimension(); i++) {
				if (t.branchQ(o, i)) {
					children.add(i);
				}
			}
			return children;
		} else {
			throw new AssertionError("node not in graph");
		}
	}
	
	

	//returns the list of the descendants of the node o in depth
	public ArrayList<Integer> DFS (int o, Tree t) { 
		if ( o>= 0 && o< t.getDimension()) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			Stack<Integer> stack = new Stack<Integer>();
			boolean [] visited = new boolean[t.getDimension()]; //lista de visitados
			stack.push(o); 
			while (!stack.isEmpty()) {
				int node = stack.pop(); 
				if (!visited[node]) {
					r.add(node);
					visited[node]=true;
					for (int i: this.offspring(t, node) ) {
						stack.push(i);
					}
					
				}
			}
			return r;
		}
		else {
			throw new AssertionError("Node not in graph.");
		}
	}
		
	
	//returns whether the path from the leaf o the leaf d in the tree
	public boolean pathQ(int o, int d, Tree t) {
		boolean found = false;
		if (o>=0 && o<t.getDimension() && d>=0 && d<t.getDimension()) {
			boolean[] visited = new boolean[t.getDimension()];
			Queue<Integer> q = new LinkedList<Integer>();
			
			q.add(o);
			while(!q.isEmpty() && !found) {
				int node = q.remove();
				if (!visited[node]) {
					found = (node == d);
					visited[node] = true;
					for (int i : this.offspring(t, node)) {
						q.add(i);
					}
				}
			}
		}return found;
	}
	
	//returns the list of the branches of the tree ordered by their weight (from the heaviest to the lightest)
	public ArrayList<ArrayList<Integer>> orderedBranchList(){
		WGraph wg = this.wg;


		ArrayList<ArrayList<Integer>> finalList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Double> weightList = new ArrayList<Double>();

		int graphDim = wg.dim;
		if(graphDim == 0){
			throw new AssertionError("A non-empty graph has to be provided");
		}

		boolean empty = true;
		
		for(int i = 0; i < graphDim; i++){
			for(int j = i; j < graphDim; j++){

				if (i != j) {
				
				int minor = Math.min(i, j); 
				int major = Math.max(i, j);
				double currentWeight = wg.get_weight(minor,major);

				if(currentWeight != 0){


					if(empty){ 

						ArrayList<Integer> currentEdge = new ArrayList<Integer>();
						currentEdge.add(minor);
						currentEdge.add(major);
						finalList.add(currentEdge);
						weightList.add(currentWeight);

						empty = false;
						
					

					} else {
						ArrayList<Integer> currentEdge = new ArrayList<Integer>();
						currentEdge.add(minor);
						currentEdge.add(major);

						
						if ( currentWeight <= weightList.get(weightList.size()-1)) {
							finalList.add(currentEdge);
							weightList.add(currentWeight);
							
						}
						else {
							boolean inserted = false;
							int wLSize = weightList.size();
							
							for(int k = 0; !inserted && k < wLSize; k++){
							
							if(currentWeight >= weightList.get(k)){

								finalList.add(k,currentEdge);
								weightList.add(k,currentWeight);
								inserted = true;
							}
						}
						}
						
					}

				}
			}
			}
		}


		return finalList;
	}
	
	//constructs the tree from the ordered list obtained from the previous function
	public Tree MaximalTree (ArrayList<ArrayList<Integer>> list) {
		
		ArrayList<ArrayList<Integer>> edge_list = list;
		
		Tree maximalTree = new Tree(this.dim_measurement); ;
		ArrayList<Integer> visited = new ArrayList<Integer>(); //lista de indices visitados
		maximalTree.addLeaf(edge_list.get(0).get(0), edge_list.get(0).get(1)); //adiciona a primeira aresta (que supostamente � a que tem maior peso)
		
		visited.add(edge_list.get(0).get(0));
		visited.add(edge_list.get(0).get(1)); 

		edge_list.remove(0);
		
			for (int k = 0; !edge_list.isEmpty() && k < visited.size(); k++) { 
				if (this.pathQ(edge_list.get(0).get(0), edge_list.get(0).get(1), maximalTree) || this.pathQ(edge_list.get(0).get(1), edge_list.get(0).get(0), maximalTree)) { //se formar um ciclo
					edge_list.remove(0);
					
				}
				else {
					if(!(maximalTree.leafQ(edge_list.get(0).get(0)) && maximalTree.leafQ(edge_list.get(0).get(1)))){
						maximalTree.addLeaf(edge_list.get(0).get(0), edge_list.get(0).get(1)); //adiciona a folha � nossa �rvore final
				
						
						if (!edge_list.isEmpty() && !visited.contains(edge_list.get(0).get(0))) {
							visited.add(edge_list.get(0).get(0));
						}
						if (!edge_list.isEmpty() && !visited.contains(edge_list.get(0).get(1))) {
							visited.add(edge_list.get(0).get(1));
						}
						edge_list.remove(0);
					}
				}	
			}
		return maximalTree;
		}
		
	

	@Override
	public String toString() {
		return "MST [dim_Dataset=" + dim_Dataset + ", dim_measurement=" + dim_measurement + "]";
	}
	
	//returns the constructed MST
	public Tree getTree() {
		return MaximalTree(orderedBranchList());
		
	}
	
	
	
	public static void main(String[] args) {
        
        Dataset a = FileHandling.getDataset("Datasets/bcancer.csv");
        
        ChowLiu teste = new ChowLiu(a);
        
        ArrayList<ArrayList<Integer>> z = teste.orderedBranchList();
        System.out.println("orered list: " + z);
        Tree tree = teste.MaximalTree(z);
        System.out.println("MaximalTree: " + tree);
        
	}

}
