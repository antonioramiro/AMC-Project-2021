package CancerClassifier;

import java.util.Arrays;

// Weighted Graph implementation 
//public class WGraph extends Graph { -> só quando a classe graph estiver implemtada
	
class Edge {
        int s;
       	int d;
       	int w;
       	public Edge(int s, int d, int w) {
       		this.s = s;
       		this.d = d;
       		this.w = w;
		}
}

public class WGraph {//(extends Graph) , usamos isto quando queremos usar a implementação da classe graph já implementada anteriormente 
	int dim; //dimensão da matriz de ajacência
	int [][] ma; //matriz de adjacência em si
	int [][] wmtx; //matriz onde vão ser guardados os valores dos pesos de cada aresta, será uma matriz simétrica tal como a outra dado que o grafo não é direcionado 
	//no fundo cada entrada desta matriz refere-se a uma aresta, nessa entrada guardamos o peso dessa aresta
	
	public WGraph (int d) {
		this.dim = d;
		this.ma = new int[d][d];
		this.wmtx = new int[d][d];
	}
	
	public int getDim() { //função que nos dá a dimensão da matriz de adjcência (que acaba por ser a mesma dimensão da matriz com os pesos)
		return this.dim; //se acharem por bem também podemos devolver a dimensão da matriz dos pesos mas vai ser igual
	}
	
	public void add(int s, int d, int w) { //adicionar um peso w a uma aresta (que vai do nó s ao nó d)
		if (s >= 0 && s < this.dim && d >= 0 && d < this.dim) { //verificar se as arestas estão de acordo com a dimensão da matriz
			this.ma[s][d] = 1; //adiciono a aresta de qualquer maneira, não sei se preferem verificar antes se existe (dúvida(?))
			this.wmtx[s][d] = w; 
		}
		else {
			throw new AssertionError("this node does not belongs in this graph");
		}
			
	}
	
	
	public String toString() {
		return "WGraph [dim=" + dim + ", ma=" + Arrays.deepToString(ma) + ", wmtx=" + Arrays.deepToString(wmtx);
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
