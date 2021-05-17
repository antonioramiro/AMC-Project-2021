package CancerClassifier;

public class MST {
	
	int dim_Dataset; //m do eneunciado
	int dim_measurement;
	//double [][] wtmx;
	 
	
	
	public MST (Dataset T) {
		this.dim_Dataset = T.len();
		this.dim_measurement = T.measurementNumber;
	}
	
	WGraph wg = new WGraph(dim_measurement);
	
	

	
	public void Fibfrequency (Dataset T) { //recebe um dataset e calcula a frequencia de cada fibra, assumindo que todos os nós estão ligados 
		double fibfrequency = 0;
		int m = this.dim_Dataset;
		for (int i = 0; i < T.measurementNumber; i++) {
			for (int j = 0; j < T.measurementNumber; j++) {
				for (int xi = 0; xi < T.measurementDim(i); xi++) { //calcula a frequencia de cada xi na fibra i
				System.out.println("T.measurementDim(i) = " + T.measurementDim(i));
					for (int xj = 0; xj < T.measurementDim(j); xj++) { //calcula a frequencia de cada xj na fibra j
						System.out.println("T.measurementDim(j) = " + T.measurementDim(j));
						if ((T.Count(i, j, xi, xj)/m)/((T.Count(i, xi)/m)*(T.Count(j, xj)/m)) == 0) {
							fibfrequency = 0;
						}
						else {
							fibfrequency = (T.Count(i, j, xi, xj)/m)*(Math.log10((T.Count(i, j, xi, xj)/m)/((T.Count(i, xi)/m)*(T.Count(j, xj)/m)))); //formula do prof
						}
					}
				}
			wg.add(i, j, fibfrequency); //depois de calcular o peso da aresta, adiciona-a ao WGraph
			}
		}
		

		
		
		
	}


	
	public WGraph MaximalTree(WGraph wg) {
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
		
	}
	
		
		


	@Override
	public String toString() {
		return "MST [dim_Dataset=" + dim_Dataset + ", dim_measurement=" + dim_measurement + "]";
	}
	
	public static void main(String[] args) {
		Dataset ds1 = new Dataset();
        int[] m6 = {1,0,3,4,1};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);
        int[] m7 = {3,1,2,4,9};
        int c7 = 0;
        DataPoint dp7 = new DataPoint(m7,c7);
        int[] m8 = {1,0,2,4,7};
        int c8 = 1;
        DataPoint dp8 = new DataPoint(m8,c8);
        ds1.Add(dp6);
        ds1.Add(dp8);
        ds1.Add(dp7);
        for(int i = 0; i < 8; i++){
            ds1.Add(dp7);
        }
        MST mst = new MST(ds1);
        WGraph wg = new WGraph(7);
        wg.add(1, 2, 2);
        wg.add(1, 3, 1);
        wg.add(1, 4, 3);
        wg.add(2, 4, 5);
        System.out.println("DataPoint: " + dp6);
        System.out.println(ds1);
        
        // System.out.println("teste:" + Math.log10(2));
        System.out.println(wg);
        System.out.println("Peso da aresta 1,2 = " + wg.get_weight(1, 2));
        System.out.println(mst.MaximalTree(wg)); //imprime um weighted graph 
        System.out.println(mst);
		
	}

}
