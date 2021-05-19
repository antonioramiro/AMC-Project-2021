package CancerClassifier;

import java.util.ArrayList;

public class MST {
	
	int dim_Dataset; //m do eneunciado
	int dim_measurement;
	//double [][] wtmx;
	 
	
	
	public MST (Dataset T) {
		this.dim_Dataset = T.len();
		this.dim_measurement = T.measurementNumber;
	}
	
	//WGraph wg = new WGraph(5);
	
	

	
	public static WGraph Fibfrequency (WGraph wg, Dataset T, int i, int j) { //recebe um dataset e calcula a frequencia de cada fibra, assumindo que todos os nós estão ligados 
		double fibfrequency = 0;
		int m = T.len();
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
					System.out.println("Adicionando à MST");
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
		Tree maximalTree = new Tree(); //árvore final que vamos devolver
		ArrayList<Integer> visited = new ArrayList<Integer>(); //lista de indices visitados
		maximalTree.addLeaf(edge_list.get(0).get(0), edge_list.get(0).get(1)); //adiciona a primeira aresta (que supostamente é a que tem maior peso)
		visited.add(edge_list.get(0).get(0));
		visited.add(edge_list.get(0).get(1));
		System.out.println("visited inicio = " + visited);
		int dim = edge_list.size();
		
		while (!edge_list.isEmpty()) {
			for (int i = 1; i < dim; i++) { //verificando que não há ciclos
				for (int k = 0; k < visited.size(); k++) { 
					System.out.println("k = " + k);
					System.out.println("i = " + i);
					//int currentEdge = edge_list.get(i).get(0);
					if (!maximalTree.branchQ(edge_list.get(i).get(0), visited.get(k)) && !maximalTree.branchQ(edge_list.get(i).get(1), visited.get(k))) { //se não formar um ciclo
						maximalTree.addLeaf(edge_list.get(i).get(0), edge_list.get(i).get(1)); //adiciona a folha à nossa árvore final
						edge_list.remove(0);
						System.out.println("contains: " + visited.contains(edge_list.get(i).get(0)));
						if (!visited.contains(edge_list.get(i).get(0))) {
							System.out.println("contains: " + edge_list.get(i).get(0));
							visited.add(edge_list.get(i).get(0));
							}
						if (!visited.contains(edge_list.get(i).get(1))) {
							visited.add(edge_list.get(i).get(1));
							}
						
						
				}
				}
				
		}
		}
			
		return maximalTree;
	}
		


	@Override
	public String toString() {
		return "MST [dim_Dataset=" + dim_Dataset + ", dim_measurement=" + dim_measurement + "]";
	}
	
	public static void main(String[] args) {
		Dataset ds1 = new Dataset();
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
        wg = MST.Fibfrequency(wg, ds1, 1, 2);
        /*wg.add(1, 2, 2);
        wg.add(1, 3, 1);
        wg.add(1, 4, 3);
        wg.add(2, 4, 5);*/
        //System.out.println("DataPoint: " + dp6);
        System.out.println(ds1);
        //System.out.println(wg);
        // System.out.println("teste:" + Math.log10(2));
        //System.out.println(ds1);
        //System.out.println("Peso da aresta 1,2 = " + wg.get_weight(1, 2));
        //System.out.println("Fibfrequency = " + mst.Fibfrequency(ds1,1,2)); //imprime um weighted graph 
        //System.out.println(wg);
		
	}

}
