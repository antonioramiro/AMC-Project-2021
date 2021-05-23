package CancerClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


// Weighted Graph implementation 
//public class WGraph extends Graph { -> s� quando a classe graph estiver implemtada
	
class Edge {
        int source;
       	int destiny;
       	int weight;
       	public Edge(int source, int destiny, int weight) {
       		this.source = source;
       		this.destiny = destiny;
       		this.weight = weight;
		}
}

public class WGraph implements Serializable{//(extends Graph) , usamos isto quando queremos usar a implementa��o da classe graph j� implementada anteriormente 
	private static final long serialVersionUID = 7L;

	int dim; //dimens�o da matriz de ajac�ncia
	//int [][] ma; //matriz de adjac�ncia em si
	double [][] wmtx; //matriz onde v�o ser guardados os valores dos pesos de cada aresta, ser� uma matriz sim�trica tal como a outra dado que o grafo n�o � direcionado 
	//no fundo cada entrada desta matriz refere-se a uma aresta, nessa entrada guardamos o peso dessa aresta
	
	public WGraph (int d) {
		this.dim = d;
		//this.ma = new int[d][d];
		this.wmtx = new double[d][d];
	}
	
	public int getDim() { //fun��o que nos d� a dimens�o da matriz de adjc�ncia (que acaba por ser a mesma dimens�o da matriz com os pesos)
		return this.dim; //se acharem por bem tamb�m podemos devolver a dimens�o da matriz dos pesos mas vai ser igual
	}
	
	public void add(int source, int destiny, double weight) { //adicionar um peso w a uma aresta (que vai do n� s ao n� d)
		int minor = Math.min(source, destiny); 
        int major = Math.max(source, destiny);
		if (minor >= 0 && minor < this.dim && major >= 0 && major < this.dim) { //verificar se as arestas est�o de acordo com a dimens�o da matriz
			//this.ma[s][d] = 1; //adiciono a aresta de qualquer maneira, n�o sei se preferem verificar antes se existe (d�vida(?))
			if (this.wmtx[minor][major] < 10e-10) {
				this.wmtx[minor][major] = weight;
			}
			
		}
		else {
			throw new AssertionError("this node does not belongs in this graph");
		}
			
	}
	
	public double get_weight(int source, int destiny) { //falta verificar se existe node/ramo
		int minor = Math.min(source, destiny); 
        int major = Math.max(source, destiny);
		return this.wmtx[minor][major];
	}
	
	public boolean indexisnotemptyQ (double [] wtmx) {
		boolean found = false;
		for (int i = 0; i < this.dim && !found; i++ ) {
				if (wtmx[i] != 0) {
					found = true;
				
				}
			
			}
		return found;
	}
	
	public ArrayList<ArrayList<Integer>> Edge_Sort() { //n�o funciona :(((
		ArrayList<ArrayList<Integer>> edgelist = new ArrayList<ArrayList<Integer>>();
		ArrayList<Double> weightlist = new ArrayList<Double>();
		
		
		for (int i = 0; i < this.dim ; i++) {
			for (int j = i+1;j < this.dim; j++) {
				boolean put = false;
				if (weightlist.isEmpty()) {
					System.out.println("primeiro if ahaha");
					ArrayList<Integer> edge = new ArrayList<Integer>(); 
					edge.add(i);
					edge.add(j);
					weightlist.add(this.wmtx[i][j]);
					edgelist.add(edge);
				}
				else {
					for (int k = 0; k < weightlist.size() && !put ; k++) {
						System.out.println("pr�ximo ahaha " + i + ", " + j +  " get_weight = " + this.get_weight(i, j) + " weightlist.get = " + weightlist.get(k));
						if (this.get_weight(i, j) >= weightlist.get(k)) {
							//n�o entra aqui
							ArrayList<Integer> edge = new ArrayList<Integer>(); 
							edge.add(i);
							edge.add(j);
							weightlist.add(k+1,this.wmtx[i][j]);
							edgelist.add(k+1,edge);
							put = true;
						}
						
					}
				}
			}
		}
		return edgelist;
	}
	

	

	
	
	public String toString() {
		return "WGraph [dim=" + dim + ", wmtx=" + Arrays.deepToString(wmtx);
	}
	
    //funciona	
    public static void main(String[] args) {
        WGraph wg = new WGraph(3);
        int[][] edges = { {0,1,10}, {1,2,7}, {2,0,3} };
        for (int[] e : edges) {
        	wg.add(e[0], e[1], e[2]);
        }
        System.out.println(wg);
        System.out.println(wg.Edge_Sort());
    }


}
        
        

    
