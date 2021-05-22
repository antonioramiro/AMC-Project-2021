

package CancerClassifier;

import java.io.Serializable;
import java.util.ArrayList;

public class MRFT implements Serializable{

	//Atributos
	private int dim;		             //dimensï¿½o do MRFT
	private Markov markov;	 	     	 //Datatype que armazena os Phis
	private ArrayList<Integer> special;	 //Aresta especial
    private Tree A;			
    private int[] measurementsDomain; 
	
	//Construtor
	public MRFT(Dataset T, Tree A) {
       
        if (A.dim == T.measurementNumber){	//certifica-se de que a ï¿½rvore e o dataset dados tï¿½m a mesma dimensï¿½o
        
            this.dim = A.dim ;
            this.special = set_special(A);
            this.markov = add_PHI(T, A);
            this.measurementsDomain = T.measurementsDomain;
            
            this.A = A;
            ArrayList<Integer> sp = new ArrayList<Integer>();
            sp.add(0);
            sp.add(1);
            
        }else {
            throw new AssertionError("The number of Tree Leafs (" +A.dim+ ") must match the number of measurements (" +T.measurementNumber+ ") in the dataset");
        }
	}
	
	@Override
	public String toString() {
		return "MRFT [dim=" + this.dim + "\n tree=" + A.toString() + "\n special=" + special + "\n markov=" + this.markov.toString() + "]";
	}
	
	//não gostei dessa solução 
	public int measurementDim(int i){
        return this.measurementsDomain[i];
	}

	//Seleciona uma aresta como especial
	public ArrayList<Integer> set_special(Tree A){
		ArrayList<Integer> special = new ArrayList<Integer>();
		special.add(0);
		special.add(1);
		return special;
	}

	//Verifica se dois nï¿½s i e j sï¿½o ligados pela aresta especial
	public boolean specialQ(int i, int j ) {	
		ArrayList<Integer> edge = new ArrayList<Integer>();
		edge.add(i);
		edge.add(j);
		return edge.equals(this.special);
	}
	

	
	//Calcula o valor de phi para arestas normais (phi_normal) e para a aresta especial (phi_special)
	public double phi_normal(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi 
		return (T.Count(i, j, xi, xj) + delta)/(T.Count(i, xi) + delta*this.measurementDim(j)); 	
	}
	public double phi_special(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi e xj
		return (T.Count(i,j, xi, xj) + delta)/(T.len() + delta*this.measurementDim(i)*this.measurementDim(j));     //Maria: alterei para T.len()  (estava T.len)
	}
	
	//Dado um dataset e uma ï¿½rvore, cria um Markov e adciona as matrizess PHI correspondentres a cada aresta da ï¿½rvore
	public Markov add_PHI(Dataset T, Tree A){ 
		
		Markov markov =  new Markov(this.dim);
		boolean found_special = false;	

		for(int i=0; i < this.dim; i++) {		//percorrer todas as combinaï¿½ï¿½es de nï¿½s da ï¿½rvore											
			for(int j=i; j < this.dim; j++) { 														
				if (A.branchQ(i,j)) { 			//verificar se os nï¿½s em causa constituem uma aresta na ï¿½rvore
					
					int dimi = measurementDim(i);
					int dimj = measurementDim(j);
					
					boolean special = false;
					if(!found_special && this.specialQ(i,j)) {			//verificar se os nï¿½s em causa constituem a aresta especial
						special = true;
						found_special = true;}
					
                    Phi PHI = new Phi(dimi,dimj);		//criar uma nova matriz PHI

					for (int xi=0; xi < dimi; xi++) { 				//percorrer todas as combinaï¿½ï¿½es possï¿½veis de xi e xj						 
						for (int xj=0; xj < dimj; xj++) { 									
							if (special) {
								PHI.setPhi(xi,xj, phi_special(T, i, j, xi, xj, 0.2)); 	//adcionar os valores de phi(xi, xj) na posiï¿½ï¿½o correspondente da matriz PHI
							}else { 															
								PHI.setPhi(xi,xj, phi_normal(T, i, j, xi, xj, 0.2)); 	
							}                           
						}
					}
                    markov.setMarkov(i, j, PHI);			//adcionar a matriz PHI correspondente ï¿½ aresta (i,j) no Markov
				}
			}
		}
		return markov; 
	}
				
	//Dado um vetor Xn, calcula a probabilidade de seus valores no MRFT
	public double Probability(int[] Xn) { 
		int dim = this.dim;
		double result = 1;
		if ( Xn.length == dim ) {					//certifica-se de que a dimensï¿½o do vetor ï¿½ a mesma do MRFT
			for(int i = 0; i < dim; i++) { 								
				for(int j = i; j < dim; j++) { 			 										
					if (A.branchQ(i,j)) {			                     	
						System.out.println(i+ "," + j);
						result = (result*(this.markov.getMarkov(i, j).getPhi(Xn[i],Xn[j]))); 
					}
				}	
			}
			return result;
		}		
		else {
			throw new AssertionError("Tree's and array's dimension must be the same");
		}
	}

    
		
	public static void main(String[] args) {
        //Creating graph
        Tree g = new Tree();
		int[][] edges = {{0,1}, {1,2}, {1,3},{1,4}};
		for(int[] e : edges) {
			g.addLeaf(e[0], e[1]);
		}

        //Creating Dataset
        Dataset ds1 = new Dataset(5);
        int[] m6 = {3,3,3,3,3};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);
        int[] m7 = {3,3,3,3,3};
        int c7 = 0;
        DataPoint dp7 = new DataPoint(m7,c7);
        int[] m8 = {3,3,3,3,3};
        int c8 = 0;
        DataPoint dp8 = new DataPoint(m8,c8);
        ds1.Add(dp6);
        for(int i = 0; i < 30; i++){
            ds1.Add(dp7);
        }
        ds1.Add(dp8);
        System.out.println("Dataset: " + ds1);

        //Creating MRFT
        MRFT mkv = new MRFT(ds1,g);
        int[] m9 = {3,3,3,3,3};
        double a = mkv.Probability(m9);
        System.out.println(a);
    }
    
}



