package CancerClassifier;

import java.io.Serializable;
import java.util.ArrayList;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ChowLiu implements Serializable{
	private static final long serialVersionUID = 6L;

	int dim_Dataset; //m do eneunciado
	int dim_measurement;
	WGraph wg;
	 
	
	
	public ChowLiu (Dataset T) {
		this.dim_Dataset = T.len();
		this.dim_measurement = T.measurementNumber;
		this.wg = createWGraph(T);
		
		
		
	}

	
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
		
	
	
	
	public LinkedList<Integer> offspring(Tree t, int o){ //descendentes do n� o no grafo
		LinkedList<Integer> children = new LinkedList<Integer>();
		if (o>=0 && o<t.dim) {
			for (int i = 0; i < t.dim; i++) {
				if (t.branchQ(o, i)) {
					children.add(i);
				}
			}
			return children;
		} else {
			throw new AssertionError("node not in graph");
		}
	}
	
	

	public ArrayList<Integer> DFS (int o, Tree t) { //d�-nos os descendentes de cada �rvore, em profundidade
		if ( o>= 0 && o< t.dim) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			Stack<Integer> stack = new Stack<Integer>();
			boolean [] visited = new boolean[t.dim]; //lista de visitados
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
		
	
		
	public boolean pathQ(int o, int d, Tree t) {
		boolean found = false;
		if (o>=0 && o<t.dim && d>=0 && d<t.dim) {
			boolean[] visited = new boolean[t.dim];
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
						//System.out.println("edge list = " + edge_list);
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

	@Override
	public String toString() {
		return "MST [dim_Dataset=" + dim_Dataset + ", dim_measurement=" + dim_measurement + "]";
	}
	
	public Tree getTree() {
		System.out.println("ordered" + orderedBranchList());
		return MaximalTree(orderedBranchList());
		
	}
	
	
	
	public static void main(String[] args) {
        
        Dataset a = FileHandling.getDataset("Datasets/bcancer.csv");
        
        ChowLiu teste = new ChowLiu(a);
        
        ArrayList<ArrayList<Integer>> z = teste.orderedBranchList();
        System.out.println("orered list: " + z);
        Tree tree = teste.MaximalTree(z);
        System.out.println("MaximalTree: " + tree);
        
        /*
        
        ArrayList<ArrayList<Integer>> z = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> x1 = new ArrayList<Integer>();
        ArrayList<Integer> x2 = new ArrayList<Integer>();
        ArrayList<Integer> x3 = new ArrayList<Integer>();
        ArrayList<Integer> x4 = new ArrayList<Integer>();
        ArrayList<Integer> x5 = new ArrayList<Integer>();
        ArrayList<Integer> x6 = new ArrayList<Integer>();
        ArrayList<Integer> x7 = new ArrayList<Integer>();
        x1.add(0);
        x1.add(1);
        
        x3.add(0);
        x3.add(2);
        
        x2.add(1);
        x2.add(2);
        
        x4.add(0);
        x4.add(3);
        
        x5.add(1);
        x5.add(4);
        
        x6.add(2);
        x6.add(4);
        
        x7.add(7);
        x7.add(5);
        //System.out.println("arraylist x = " + x);
        z.add(x1);
        z.add(x3);
        z.add(x2);
        z.add(x4);
        z.add(x5);
        z.add(x6);
        z.add(x7); 
        
        
        System.out.println(teste.MaximalTree(z));
        
        
        //MST mst = new MST(ds1);
        WGraph wg = new WGraph(5);
        //wg = MST.Fibfrequency(wg, ds1, 1, 2);
        wg.add(1, 2, 2.3);
        wg.add(1, 3, 1.2);
        wg.add(1, 4, 3.3);
        wg.add(2, 4, 5.19);
        //System.out.println("DataPoint: " + dp6);
        //System.out.println(ds1);
        //System.out.println(wg);
        // System.out.println("teste:" + Math.log10(2));
        //System.out.println(ds1);
        //System.out.println("Peso da aresta 1,2 = " + wg.get_weight(1, 2));
        //System.out.println("Fibfrequency = " + MST.Fibfrequency(wg, ds1,1,2)); //imprime um weighted graph 
        //System.out.println(wg);

		WGraph wg1 = new WGraph(5);
        //wg = MST.Fibfrequency(wg, ds1, 1, 2);
        wg1.add(1, 2, 2d);
        wg1.add(4, 3, 1d);
        wg1.add(3, 4, 3d);
        wg1.add(2, 4, 5.2);
		wg1.add(4, 0, 16d);
		wg1.add(4, 3, 1d);
		wg1.add(3, 1, 13.3);
		wg1.add(4, 2, 6d);
		wg1.add(0, 0, 1.32);

		System.out.println(ChowLiu.orderedBranchList(wg1));
		*/
	}

}
