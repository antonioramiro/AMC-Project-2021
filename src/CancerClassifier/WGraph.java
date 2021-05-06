package CancerClassifier;

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

public class WGraph {//(extends Graph) , usamos isto quando queremos usar a implementa��o da classe graph j� implementada anteriormente 
	int dim; //dimens�o da matriz de ajac�ncia
	//int [][] ma; //matriz de adjac�ncia em si
	int [][] wmtx; //matriz onde v�o ser guardados os valores dos pesos de cada aresta, ser� uma matriz sim�trica tal como a outra dado que o grafo n�o � direcionado 
	//no fundo cada entrada desta matriz refere-se a uma aresta, nessa entrada guardamos o peso dessa aresta
	
	public WGraph (int d) {
		this.dim = d;
		//this.ma = new int[d][d];
		this.wmtx = new int[d][d];
	}
	
	public int getDim() { //fun��o que nos d� a dimens�o da matriz de adjc�ncia (que acaba por ser a mesma dimens�o da matriz com os pesos)
		return this.dim; //se acharem por bem tamb�m podemos devolver a dimens�o da matriz dos pesos mas vai ser igual
	}
	
	public void add(int s, int d, int w) { //adicionar um peso w a uma aresta (que vai do n� s ao n� d)
		if (s >= 0 && s < this.dim && d >= 0 && d < this.dim) { //verificar se as arestas est�o de acordo com a dimens�o da matriz
			//this.ma[s][d] = 1; //adiciono a aresta de qualquer maneira, n�o sei se preferem verificar antes se existe (d�vida(?))
			this.wmtx[s][d] = w; 
		}
		else {
			throw new AssertionError("this node does not belongs in this graph");
		}
			
	}
	
	
	public String toString() {
		return "WGraph [dim=" + dim + ", wmtx=" + Arrays.deepToString(wmtx);
	}
	
    //funciona	
    public static void main(String[] args) {
        WGraph wg = new WGraph(3);
        int[][] edges = { {0,1,3}, {1,2,7}, {2,0,1} };
        for (int[] e : edges) {
        	wg.add(e[0], e[1], e[2]);
        }
        System.out.println(wg);
    }
}
        
        

    
