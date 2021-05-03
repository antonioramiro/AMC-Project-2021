package CancerClassifier;

import java.util.Arrays;

public class Graph { //implementação básica de um grafo (esta em uma das aulas)
	int dim;
	int [][] ma;
	
	public Graph(int d) {
		this.dim = d;
		this.ma = new int [d][d];
	}
	
	public int getDim() {
		return this.dim;
		
	}

	@Override
	public String toString() {
		return "Graph [dim=" + dim + ", ma=" + Arrays.toString(ma) + "]";
	}
	
	

    
}
