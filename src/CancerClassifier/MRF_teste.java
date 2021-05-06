package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;

public class MRF_teste {
	//Construtor que recebe uma árvore, e um dataset e coloca os phis em cada aresta da árvore (que podem ser vistos como uma matriz).

		//atributos
		private int MRFTdim;						//dimensão da MRFT
		private ArrayList<double[][]>[][] markov;				//matriz das matrizes dos phis
		private ArrayList<Integer> special;			//aresta especial
		
		//Construtor
		public void MRFT(Dataset T, Graph A) {
			this.markov = add_PHI(T, A);
			this.MRFTdim = A.getDim();
			this.special = set_special(A);
		}
		
		@Override
		public String toString() {
			return "MRFT [MRFTdim=" + MRFTdim + ", PHI_tree=" + Arrays.toString(markov) + ", special=" + special + "]";
		}

		//escolhe uma aresta para ser a aresta especial
		public ArrayList<Integer> set_special(Graph A) { 					//escolhe uma aresta especial
			int i=0;														//vai de 0 ao menor nó que faz aresta com 0
			int j=1;
			ArrayList<Integer> special = new ArrayList<Integer>();			
			while(j< A.getDim()) {											//j varia dentro da dimensão do grafo
				if(A.edgeQ(i,j)) {											
					special.add(i);
					special.add(j);
				}else {
					j++;
				}
			}
			return special;
		}
		
		//verifica se a aresta que une dois nós é a aresta especial
		public boolean specialQ(int i, int j ) {
			ArrayList<Integer> edge = new ArrayList<Integer>();
			edge.add(i);
			edge.add(j);
			return edge == this.special;
		}
		
		//phi para arestas normais (phi_normal) e aresta especial (phi_special)
		public double phi_normal(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi 
			return (T.Count(i, j, xi, xj) + delta)/(T.Count(i, xi) + delta*T.Dim(j)); 	
		}
		public double phi_special(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi e xj
			return (T.Count(i,j, xi, xj) + delta)/(T.len() + delta*T.Dim(i)*T.Dim(j));     //Maria: alterei para T.len()  (estava T.len)
		}
		
		public ArrayList<Double[][]> add_PHI(Dataset T, Graph A){ //recebe a árvore e faz uma matriz PHI pra cada aresta da árvore		
			int dim = A.getDim();
			
			ArrayList<ArrayList<Double[][]>> markov1 =  new ArrayList<ArrayList<Double[][]>>();
			
			ArrayList<Double[][]> markov = new ArrayList<Double[][]>(); //passar pra markov
			
			for(int i=0; i< dim; i++) {  															//selecionar a aresta q começa em i 
				for(int j=i+1; j< dim; j++) { 														//e termina em j
					if (A.edgeQ(i,j)) { 															//não usar arestas de i pra i 
						
						Double[][] PHI = new Double[T.Dim(i)][T.Dim(j)];
						
						for (int xi=0; xi < T.Dim(i); xi++) { 										//pra cada valor possível de xi 
							for (int xj=0; xj < T.Dim(j); xj++) { 									//e cada valor possível de xj
								int value = 0;
								boolean found_special = false;
								if(!found_special && specialQ(i,j)) {	 							//se i->j é uma aresta da árvore
									PHI[xi][xj] = phi_special(T, i, j, xi, xj, 0.2); 				//calcula a função phi (xi,xj)
									found_special = true;
								}else { 															//se i-> não é uma aresta da árvore
									PHI[xi][xj] = phi_normal(T, i, j, xi, xj, 0.2);
									//calcula a função phi(xi,xj)
								}
								markov.get(i)
								//markov[i][j] = PHI[xi][xj];											//matriz de todos os nós que tem phis onde há aresta entre 2 nós
							}
						}
					}
				}
			}
			return markov;
		}
					
		public double Prob(Dataset T, Graph A, int[] Xn) { //não deve ser void
			int dim = A.getDim();
			double result = 1;
			
			for(int i=0; i< dim; i++) {  															//selecionar a aresta q começa em i 
				for(int j=i+1; j< dim; j++) { 														//e termina em j
					if (A.edgeQ(i,j)) { 
						result = result*((this.markov[i][j])[Xn[i]][Xn[j]]);
					}
				}
			}
			return result;
			//para cada i do vetor
			//para cada j do vetor diferente de i
				//se i,j for aresta
					//
}
