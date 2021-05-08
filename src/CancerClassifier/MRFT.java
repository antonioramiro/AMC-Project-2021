package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;



public class MRFT{

	//atributos
	private int dim;		             //nr de nós / dimensão da MRFT
	private Markov markov;	 	     	 //datatype que armazena os phis
	private ArrayList<Integer> special;	 //aresta especial
    private Tree A;
	
	//Construtor
	public MRFT(Dataset T, Tree A) {
       
        if (A.dim == T.measurementNumber){
        
            this.dim = A.dim ;
            this.special = set_special(A);
            this.markov = add_PHI(T, A);
            
            this.A = A;
            ArrayList<Integer> sp = new ArrayList<Integer>();
            sp.add(0);
            sp.add(1);
            
        }else {
            throw new AssertionError("The number of Tree Leafs musts match the number of measurements in the dataset");
        }
	}
	
	@Override
	public String toString() {
		return "MRFT [dim=" + this.dim + "\n tree=" + A.toString() + "\n special=" + special + "\n markov=" + this.markov.toString() + "]";
	}

	public ArrayList<Integer> set_special(Tree A){
		ArrayList<Integer> special = new ArrayList<Integer>();
		special.add(0);
		special.add(1);
		return special;
	}

	
	//verifica se a aresta que une dois n�s � a aresta especial
	public boolean specialQ(int i, int j ) {	
		ArrayList<Integer> edge = new ArrayList<Integer>();
		edge.add(i);
		edge.add(j);
		return edge.equals(this.special);
//		return edge == this.special;
	}
	

	
	//phi para arestas normais (phi_normal) e aresta especial (phi_special)
	public double phi_normal(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi 
		return (T.Count(i, j, xi, xj) + delta)/(T.Count(i, xi) + delta*T.measurementDim(j)); 	
	}
	public double phi_special(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi e xj
		return (T.Count(i,j, xi, xj) + delta)/(T.len() + delta*T.measurementDim(i)*T.measurementDim(j));     //Maria: alterei para T.len()  (estava T.len)
	}
	
	public Markov add_PHI(Dataset T, Tree A){ //recebe a �rvore e faz uma matriz PHI pra cada aresta da �rvore		
		
		Markov markov =  new Markov(this.dim);
		boolean found_special = false;	

		for(int i=0; i < this.dim; i++) {   															//selecionar a aresta q come�a em i 
			for(int j=i; j < this.dim; j++) { 														//e termina em j
				if (A.branchQ(i,j)) { 	
					
					boolean special = false;
					if(!found_special && this.specialQ(i,j)) {	
						special = true;
						found_special = true;}
					
                    Phi p = new Phi(T.measurementDim(i),T.measurementDim(j));

					for (int xi=0; xi < T.measurementDim(i); xi++) { 										//pra cada valor poss�vel de xi 
						for (int xj=0; xj < T.measurementDim(j); xj++) { 									//e cada valor poss�vel de xj
							if (special) {
                          					//se i->j � uma aresta da �rvore
								p.setPhi(xi,xj, phi_special(T, i, j, xi, xj, 0.2)); //calcula a fun��o phi (xi,xj)
							}else { 															//se i-> n�o � uma aresta da �rvore
								p.setPhi(xi,xj, phi_normal(T, i, j, xi, xj, 0.2)); 	 				//calcula a fun��o phi(xi,xj)
							}                           
						}
					}
                    markov.setMarkov(i, j, p);
                   // System.out.println("phi("+i+","+j+"):" + p.toString());
                   // System.out.println("markov: " + markov.toString());   
				}
			}
			
		}
        System.out.println("markov: " + markov.toString());
		return markov; 
	}
				
	public double Probability(int[] Xn) { 
		int dim = this.dim;
		double result = 1;
		if ( Xn.length == dim ) {
			for(int i = 0; i < dim; i++) { 														//selecionar a aresta q come�a em i 
				for(int j = i; j < dim; j++) { 			 										//e termina em j
					if (A.branchQ(i,j)) {
                        System.out.println("Branches - i: "+i+"; j: "+j);

                        /*System.out.println("fi("+i+","+j+") = " + this.markov.getMarkov(i, j));

                        System.out.println("Xn =" + Arrays.toString(Xn));
                        System.out.println("Xn(" +i+","+j+") = (" + Xn[i] + " , " + Xn[j] + ");");*/
                        if(i==1 && j ==3){
                            System.out.println(markov.toString());
                            System.out.println(this.markov.getMarkov(i, j)); 
                        }
                        

						result = (result*(this.markov.getMarkov(i, j).getPhi(Xn[i],Xn[j]))); //ajudem me a 

                        /*
                        System.out.println("fi("+i+","+j+") = " + this.markov.getMarkov(i, j).getPhi(Xn[i],Xn[j]));
                        System.out.println("r:" + result);*/
					}
				}	
			}
			return result;
		}
		
		else {
			throw new AssertionError("Tree's and array's dimension must be the same");
		}
		//para cada i do vetor
		//para cada j do vetor diferente de i
			//se i,j for aresta
				//		
	}

    public int classFrequency(Dataset T, int i, int xi) {
		int freq;
		freq = T.Count(i, xi)/T.measurementDim(i);
		return freq;
		
	}
		
	public static void main(String[] args) {
        //Creating graph
        Tree g = new Tree();
		int[][] edges = {{0,1}, {1,2}, {1,3},{1,4}};
		for(int[] e : edges) {
			g.addLeaf(e[0], e[1]);
		}

        //Creating Dataset
        Dataset ds1 = new Dataset();
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



//Prob: dado um vetor de dados Xn retorna a probabilidade destes dados no dataset.
	
	
	//COISAS DO DATASET:
	//1. seria bom ter uma fun��o do Datase que retornasse Dim(i) = valor m�ximo de xi 
	//2. a fun��o Count precisa de uma vers�o de apenas 2 argumentos Count(i, xi) -- ver pdfs

	
    //OUTROS PROBLEMAS:
	//COMO ASSOCIAR AS ARESTAS �S MATRIZES PHI
	//COMO CALCULAR AS PROBABILIDADES 
	
	//TO-DO
		//corriggir erros
		//fun��o prob
		//implementar �rvores
		//testar

//nao preciso de uma matriz inteira 


//atributos
//private Dataset T; 	//dataset
//private Tree A;

//metodo construtor
//public MRFT(Dataset T, Tree A) {
	//this.T = T;
	//this.A = A;
//}	

//getters and setters
//public Dataset getT() {
	//return T;
//}
//public void setT(Dataset t) {
	//	T = t;
//}
//public Tree getA() {
//	return A;
//}
//public void setA(Tree a) {
//	A = a;
//}	
