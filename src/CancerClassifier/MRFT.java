package CancerClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

//wrapper implementation for a matrix
class Phi implements Serializable{
	private static final long serialVersionUID = 1L;
	double[][] L;

	public Phi(double[][] l) {
		this.L = l;
	}

    public Phi(int i, int j) {
        double[][] l = new double[i][j];
		this.L = l;
	}

    public double getphiValue(int i, int j) {
        return L[i][j];
	}

    public void setphiValue(int i, int j, double value){
        this.L[i][j] = value;
    }

	@Override
	public String toString() {
		return "[Phi=" + Arrays.deepToString(L) +"]";
	}
}

//markov implements a matrix of Phis
class Markov implements Serializable{
	private static final long serialVersionUID = 2L;

	private ArrayList<ArrayList<Phi>> Phis;
	private ArrayList<ArrayList<Integer>> index;
	private int N;

	public Markov(int N){
		ArrayList<ArrayList<Phi>> phis = new ArrayList<ArrayList<Phi>>();
		this.Phis = phis;
		ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>();
		this.index = index;

		for (int i = 0; i < N; i++) {
			this.Phis.add(new ArrayList<Phi>());
			this.index.add(new ArrayList<Integer>());
		}

		this.N = N;
	}


	public boolean isTherePhi(int i, int j){
		boolean isThere = false;

		int minor = Math.min(i,j);
		int major = Math.max(i,j);
		
		if(minor < N && major < N){
			for (int y = 0; !isThere && y < this.index.get(minor).size(); y++) {
				if (this.index.get(minor).size() > 0 &&	this.index.get(minor).get(y) == major) {
					isThere = true;
				}
			}
		}
		return isThere;
	}

	public void setPhi(int i, int j, Phi p) {
		if (i < N && j < N) {
			this.Phis.get(i).add(p);
			this.index.get(i).add(j);
		} else {
			throw new AssertionError("At least one index (" +i+ " or "+j+ ") out of Markov's range (" +N + ").");
		}
	}
	
	

	//acrescentar expressao de erro caso passe o dom.
	public Phi getPhi(int i, int j) {
		int minor = Math.min(i,j);
		int major = Math.max(i,j);
		
		boolean isThere = false;
		int k = 0;
		
		if(minor < N && major < N){
			for (int y = 0; !isThere && y < N; y++) {
				if (this.index.get(minor).size() > 0 &&	this.index.get(minor).get(y) == major) {
					isThere = true;
					k = y;
				}
			}
			if (!isThere) {
				throw new AssertionError("Node not found");
			}
			return this.Phis.get(minor).get(k);
		}else{
			throw new AssertionError("Requested i  j are out of phi domain");
		}
		
	}


	public ArrayList<ArrayList<Phi>> getPhis() {
		return this.Phis;
	}

	public void setPhis(ArrayList<ArrayList<Phi>> Phis) {
		this.Phis = Phis;
	}

	public ArrayList<ArrayList<Integer>> getIndex() {
		return this.index;
	}

	public void setIndex(ArrayList<ArrayList<Integer>> index) {
		this.index = index;
	}

	public int getN() {
		return this.N;
	}

	public void setN(int N) {
		this.N = N;
	}

	@Override
	public String toString() {
		return "{" +
			" Phis='" + getPhis() + "'" +
			", index='" + getIndex() + "'" +
			", N='" + getN() + "'" +
			"}";
	}

	public static void main(String[] args) {
		double[][] matrizdophi01 = {{ 2, 2 }, { 2, 2 }, {2 , 2}};
		double[][] matrizdophi12 = {{ 1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 1.0, 1.0, 1.0, 1.0}};
		double[][] matrizdophi13 = {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}, {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
		double[][] matrizdophi14 = {{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0} , {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0}};
		
		Phi phi01 = new Phi(matrizdophi01);
		Phi phi12 = new Phi(matrizdophi12);
		Phi phi13 = new Phi(matrizdophi13);
		Phi phi14 = new Phi(matrizdophi14);

		Markov markov = new Markov(5);
		markov.setPhi(0, 1, phi01);
		markov.setPhi(1, 2, phi12);
		markov.setPhi(1, 3, phi13);
		markov.setPhi(1, 4, phi14);
		System.out.println(markov.toString());
		
		System.out.println("markov 0,1" + markov.getPhi(0, 1));
		System.out.println("markov 1,2" + markov.getPhi(1, 2));
		System.out.println("markov 1,4" + markov.getPhi(1, 4));
		System.out.println("markov 1,3" + markov.getPhi(1, 3));

		for(int i = 0; i < 10; i++){
			for (int j = 0; j < 10; j++) {
				System.out.println("is there: " + markov.isTherePhi(i, j) + "j: " + j + "; i:" +i);
			}
		}
	}
}

public class MRFT implements Serializable{
	private static final long serialVersionUID = 4L;

	//Atributos
	private int dim;		             //dimens�o do MRFT
	private Markov markov;	 	     	 //Datatype que armazena os Phis
	private ArrayList<Integer> special;	 //Aresta especial
	
    private int[] measurementsDomain; 

	//Construtor
	public MRFT(Dataset T, Tree A) {
       
        if (A.dim == T.measurementNumber){	//certifica-se de que a �rvore e o dataset dados t�m a mesma dimens�o
			
            this.dim = A.dim ;
			this.measurementsDomain = T.measurementsDomain;
		    this.special = A.first();	//Seleciona uma aresta como especial
            this.markov = add_PHI(T, A);
              
            
        }else {
            throw new AssertionError("The number of Tree Leafs (" +A.dim+ ") must match the number of measurements (" +T.measurementNumber+ ") in the dataset");
        }
	}
	
	@Override
	public String toString() {
		return "MRFT [dim=" + this.dim + "\n tree="  + "\n special=" + special + "\n markov=" + this.markov.toString() + "]";
	}
	
	//n�o gostei dessa solu��o 
	public int getMeasurementDim(int i){
        return this.measurementsDomain[i];
	}
	public int getDim() {
		return this.dim;
	}


	//Verifica se dois n�s i e j s�o ligados pela aresta especial
	public boolean specialQ(int i, int j ) {	
		ArrayList<Integer> edge = new ArrayList<Integer>();
		edge.add(i);
		edge.add(j);
		return edge.equals(this.special);
	}
	

	
	//Calcula o valor de phi para arestas normais (phi_normal) e para a aresta especial (phi_special)
	public double phi_normal(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi 
		return (T.Count(i, j, xi, xj) + delta)/(T.Count(i, xi) + delta*this.getMeasurementDim(j)); 	
	}
	public double phi_special(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi e xj
		return (T.Count(i,j, xi, xj) + delta)/(T.len() + delta*this.getMeasurementDim(i)*this.getMeasurementDim(j));     //Maria: alterei para T.len()  (estava T.len)
	}
	
	//Dado um dataset e uma �rvore, cria um Markov e adciona as matrizess PHI correspondentres a cada aresta da �rvore
	public Markov add_PHI(Dataset T, Tree A){ 
		
		Markov markov =  new Markov(this.dim);
		boolean found_special = false;
	
		for(int i=0; i < this.dim; i++) {		//percorrer todas as combina��es de n�s da �rvore											
			for(int j=i; j < this.dim; j++) {													
				if (A.branchQ(i,j)) { 			//verificar se os n�s em causa constituem uma aresta na �rvore
					
					int dimi = getMeasurementDim(i);
					int dimj = getMeasurementDim(j);
					
					boolean special = false;
					if(!found_special && this.specialQ(i,j)) {			//verificar se os n�s em causa constituem a aresta especial
						special = true;
						found_special = true;}
					
                    Phi PHI = new Phi(dimi,dimj);		//criar uma nova matriz PHI

					for (int xi=0; xi < dimi; xi++) { 				//percorrer todas as combina��es poss�veis de xi e xj						 
						for (int xj=0; xj < dimj; xj++) { 
							
							if (special) {
								PHI.setphiValue(xi,xj, phi_special(T, i, j, xi, xj, 0.2)); 	//adcionar os valores de phi(xi, xj) na posi��o correspondente da matriz PHI
							}else { 															
								PHI.setphiValue(xi,xj, phi_normal(T, i, j, xi, xj, 0.2)); 	
							}                           
						}
					}
                    markov.setPhi(i, j, PHI);			//adcionar a matriz PHI correspondente � aresta (i,j) no Markov
					
				}
			}
		}
		
		
		return markov; 
	}
				
	//Dado um vetor Xn, calcula a probabilidade de seus valores no MRFT
	public double Probability(int[] Xn) { 
		int dim = this.dim;
		double result = 1;
		if ( Xn.length == dim ) {					//certifica-se de que a dimens�o do vetor � a mesma do MRFT
		
			for(int i = 0; i < this.dim; i++) { 								
				for(int j = i; j < this.dim; j++) { 
			 				 										
					if (this.markov.isTherePhi(i,j)) {			                     	
						
						result = (result*(this.markov.getPhi(i, j).getphiValue(Xn[i],Xn[j]))); 
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
        Tree g = new Tree(5);
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



