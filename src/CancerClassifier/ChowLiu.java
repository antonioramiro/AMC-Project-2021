package CancerClassifier;

import java.util.ArrayList;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;



public class ChowLiu {
	
	int dim_Dataset; //m do eneunciado
	static int dim_measurement;
	//double [][] wtmx;
	 
	
	
	public ChowLiu (Dataset T) {
		this.dim_Dataset = T.len();
		this.dim_measurement = T.measurementNumber;
	}
	
	//WGraph wg = new WGraph(5);
	
	

	
	public static WGraph Fibfrequency (WGraph wg, Dataset T, int i, int j) { //recebe um dataset e calcula a frequencia de cada fibra, assumindo que todos os nï¿½s estï¿½o ligados 
		double fibfrequency = 0;
		double m = T.len();
		System.out.println("m = " + m);
		//for (int j = 0; j < T.measurementNumber; j++) {
		for (int xi = 0; xi < T.measurementDim(i); xi++) { //calcula a frequencia de cada xi na fibra i
		System.out.println("T.measurementDim(i) = " + T.measurementDim(i));
			for (int xj = 0; xj < T.measurementDim(j); xj++) { //calcula a frequencia de cada xj na fibra j
					System.out.println("T.measurementDim(j) = " + T.measurementDim(j));
					if ((T.Count(i, j, xi, xj)/m) == 0 || (T.Count(i, xi)/m) == 0 || (T.Count(j, xj)/m) == 0) {
							fibfrequency = fibfrequency + 16;
							
							//wg.add(i, j, fibfrequency);
					}
					else {
							fibfrequency = fibfrequency + 15 + (T.Count(i, j, xi, xj)/m)*(Math.log10((T.Count(i, j, xi, xj)/m)/((T.Count(i, xi)/m)*(T.Count(j, xj)/m)))); //formula do prof
							
							//wg.add(i, j, fibfrequency);
					}
			System.out.println(" fibfrequency = " + fibfrequency);
			System.out.println("count 1 = " + T.Count(i, j, xi, xj)/m);
			System.out.println("count2 = " + T.Count(i, xi)/m);
					
				}
			}
		System.out.println("i = " + i + ", j = " + j);
		System.out.println(wg);
		System.out.println("Adicionando");
		wg.add(i, j, fibfrequency); //depois de calcular o peso da aresta, adiciona-a ao WGraph
		
	return wg;
	}
		
	
	
	
	public static LinkedList<Integer> offspring(Tree t, int o){ //descendentes do nï¿½ o no grafo
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
	
	

	public ArrayList<Integer> DFS (int o, Tree t) { //dï¿½-nos os descendentes de cada ï¿½rvore, em profundidade
		if ( o>= 0 && o< t.dim) {
			ArrayList<Integer> r = new ArrayList<Integer>();
			Stack<Integer> stack = new Stack<Integer>();
			boolean [] visited = new boolean[t.dim]; //lista de visitados
			stack.push(o); //adicionamos o primeiro nï¿½
			while (!stack.isEmpty()) {
				int node = stack.pop(); // remove o mais recente
				if (!visited[node]) {
					r.add(node);
					visited[node]=true;
					for (int i: ChowLiu.offspring(t, node) ) {
						stack.push(node);
					}
					
				}
			}
			return r;
		}
		else {
			throw new AssertionError("node not in graph");
		}
	}
		
	
		
	public static boolean pathQ(int o, int d, Tree t) {
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
					for (int i : ChowLiu.offspring(t, node)) {
						q.add(i);
					}
				}
			}
			
		}return found;
	}

	


	
	/*public WGraph MaximalTree(WGraph wg) {
		double peso = 0;
		int source = 0;
		int destiny = 0;
		//int s = 0;
		boolean finish = false;
		boolean found = false;
		boolean sem_peso = false;
		Tree mst = new Tree();
		if (!finish) {
			for (int i = 0; i < wg.dim && !found; i++) {
				for (int j = 0; j < wg.dim && !sem_peso; j++) {
					System.out.println("dentro do ciclo for: j = " + j + ", i = " + i);
					if (peso < wg.get_weight(i, j)) {
						peso = wg.get_weight(i, j);
						source = i;
						destiny = j;
						found = true;
						System.out.println("Dentro do if, peso = " + peso + ", source = " + source + ", destiny = " + destiny);
					}
			
				System.out.println("source = " + source + ", destiny = " + destiny);
				}
			
				if (!mst.indexisnotemptyQ(mst.wmtx[source]) && !mst.indexisnotemptyQ(mst.wmtx[destiny])) {
					System.out.println("Adicionando ï¿½ MST");
					mst.addLeaf(source, destiny);
					peso = 0;
					source = destiny;
				}
			}
			
		
		System.out.println("Acaba o ciclo");
		finish = true;
		
			
		}
		return mst;
		
	}*/

	
	public static Tree MaximalTree (ArrayList<ArrayList<Integer>> edge_list) {
		Tree maximalTree = new Tree(); //ï¿½rvore final que vamos devolver
		ArrayList<Integer> visited = new ArrayList<Integer>(); //lista de indices visitados
		maximalTree.addLeaf(edge_list.get(0).get(0), edge_list.get(0).get(1)); //adiciona a primeira aresta (que supostamente ï¿½ a que tem maior peso)
		
		//System.out.println("maximalTree1 = " + maximalTree);
		visited.add(edge_list.get(0).get(0));
		visited.add(edge_list.get(0).get(1)); // ï¿½ isto que estï¿½ mal
		//System.out.println("get = " + edge_list.get(0).get(1));
		//System.out.println("visited inicio = " + visited);
		//System.out.println("edge list inicio = " + edge_list);
		//int dim = edge_list.size();
		edge_list.remove(0);
		
		
		while (!edge_list.isEmpty()) {
			//for (int i = 0; i < dim; i++) { //verificando que nï¿½o hï¿½ ciclos
				//System.out.println("edge list size = " + edge_list.size());
				for (int k = 0; k < visited.size() && !edge_list.isEmpty(); k++) { 
					//System.out.println("k = " + k);
					//System.out.println("edge list k = " + edge_list);
					//System.out.println("visited = " + visited);
					//System.out.println("i = " + i);
					//System.out.println("visited size = " + visited.size());
					//System.out.println("visited list = " + visited);
					
					//int currentEdge = edge_list.get(i).get(0);
					//visited.get(k) != edge_list.get(0).get(0) && visited.get(k) != edge_list.get(0).get(1) && 
					if (ChowLiu.pathQ(edge_list.get(0).get(0), edge_list.get(0).get(1), maximalTree) || ChowLiu.pathQ(edge_list.get(0).get(1), edge_list.get(0).get(0), maximalTree)) { //se formar um ciclo
						edge_list.remove(0);
						//System.out.println("edge list = " + edge_list);
					}
					else {
						maximalTree.addLeaf(edge_list.get(0).get(0), edge_list.get(0).get(1)); //adiciona a folha ï¿½ nossa ï¿½rvore final
						//System.out.println("edge list1 = " + edge_list);
						//System.out.println("maximalTree = " + maximalTree);
						//System.out.println("edge list2 = " + edge_list);
						//System.out.println("contains: " + visited.contains(edge_list.get(i).get(0)));
						//System.out.println("maximalTree3 = " + maximalTree);
						
						
						if (!edge_list.isEmpty() && !visited.contains(edge_list.get(0).get(0))) {
							//System.out.println("contains: " + edge_list.get(i).get(0));
							visited.add(edge_list.get(0).get(0));
							//System.out.println("visited list = " + visited);
							}
						if (!edge_list.isEmpty() && !visited.contains(edge_list.get(0).get(1))) {
							visited.add(edge_list.get(0).get(1));
							//System.out.println("visited list = " + visited);
							}
						edge_list.remove(0);
						//System.out.println("edge list = " + edge_list);
					}
					
					
				}
				
				//System.out.println("edge list2 = " + edge_list);
				
		}
		return maximalTree;
		}
			
	/*	
	public static boolean hasPath(int root, Tree tree, int x) 
    { 
        // if root is NULL 
        // there is no path 
        if (tree.dim == 0) { 
            return false; 
        }
        tree.
        // push the node's value in 'arr' 
             
        
        // if it is the required node 
        // return true 
        else {
        	for (int i = 0; i < tree.dim; i++) {
        		if (x == i && hasPath(root, tree, i))
        		hasPath(root, tree, i);
        	return true; 
        }
        	
        }
            
        
        // else check whether the required node lies 
        // in the left subtree or right subtree of  
        // the current node 
        if (hasPath(root.left, arr, x) || 
            hasPath(root.right, arr, x)) 
            return true; 
        
        // required node does not lie either in the  
        // left or right subtree of the current node 
        // Thus, remove current node's value from  
        // 'arr'and then return false     
        arr.remove(arr.size()-1); 
        return false;             
    } 
  
    // function to print the path from root to the 
    // given node if the node lies in the binary tree 
    public static void printPath(Node root, int x) 
    { 
        // ArrayList to store the path 
        ArrayList<Integer> arr=new ArrayList<>();
      
        // if required node 'x' is present 
        // then print the path 
        if (hasPath(root, arr, x)) 
        { 
            for (int i=0; i<arr.size()-1; i++)     
                System.out.print(arr.get(i)+"->");
            System.out.print(arr.get(arr.size() - 1));    
        } 
        
        // 'x' is not present in the binary tree  
        else
            System.out.print("No Path"); 
    } 
		*/

		
	public static ArrayList<ArrayList<Integer>> orderedBranchList(WGraph wg){

		System.out.println(wg);
		System.out.println("");

		ArrayList<ArrayList<Integer>> finalList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Double> weightList = new ArrayList<Double>();

		int graphDim = wg.dim;
		if(graphDim == 0){
			throw new AssertionError("A non-empty graph has to be provided");
		}

		int finalListSize = 0;
		boolean empty = true;
		
		

		for(int i = 0; i < graphDim; i++){
			for(int j = 0; j < graphDim; j++){
				
				System.out.println("i,j: " + i + "," + j);
				int minor = Math.min(i, j); 
				int major = Math.max(i, j);
				double currentWeight = wg.get_weight(minor,major);

				if(currentWeight != 0){


					if(empty){ //primeiro nó da árvore

						ArrayList<Integer> currentEdge = new ArrayList<Integer>();
						currentEdge.add(minor);
						currentEdge.add(major);
						finalList.add(currentEdge);
						weightList.add(currentWeight);

						System.out.println("2currentWeight:" + currentWeight);
						System.out.println("1weightList:" + weightList);
						System.out.println("1first Edge:" + currentEdge);
						System.out.println("1finalList:" + finalList);


						empty = false;
					

					} else {
						ArrayList<Integer> currentEdge = new ArrayList<Integer>();
						currentEdge.add(minor);
						currentEdge.add(major);

						boolean inserted = false;

						for(int k = 0; k < finalListSize && !inserted; k++){
							
							System.out.println("k:" + k);

							if(currentWeight >= weightList.get(k)){

								
								
								finalList.add(k,currentEdge);
								weightList.add(k,currentWeight);
								inserted = true;

								System.out.println("2currentWeight:" + currentWeight);
								System.out.println("2weightList:" + weightList);
								System.out.println("2currentEdge:" + currentEdge);
								System.out.println("2finalList:" + finalList);

							}

							

						}

					}

					finalListSize++;
					System.out.println("finalListSize:" + finalListSize);
				
					}
					
				System.out.println("");
			}
			System.out.println("");
		}


		return finalList;
	}

	@Override
	public String toString() {
		return "MST [dim_Dataset=" + dim_Dataset + ", dim_measurement=" + dim_measurement + "]";
	}
	
	public static void main(String[] args) {
		Dataset ds1 = new Dataset(5);
        int[] m6 = {1,0,0,4,1};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);
        int[] m7 = {3,1,1,4,9};
        int c7 = 0;
        DataPoint dp7 = new DataPoint(m7,c7);
        int[] m8 = {1,1,1,4,7};
        int c8 = 1;
        DataPoint dp8 = new DataPoint(m8,c8);
        ds1.Add(dp6);
        /*ds1.Add(dp8);
        ds1.Add(dp7);*/
        for(int i = 0; i < 400; i++){
            ds1.Add(dp6);
        }
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
        wg1.add(1, 2, 2);
        wg1.add(4, 3, 1);
        wg1.add(3, 4, 3);
        wg1.add(2, 4, 5.2);
		wg1.add(4, 0, 16);
		wg1.add(4, 3, 1);
		wg1.add(3, 1, 13.3);
		wg1.add(4, 2, 6);
		wg1.add(0, 0, 1.32);

		System.out.println(ChowLiu.orderedBranchList(wg1));
		
	}

}
