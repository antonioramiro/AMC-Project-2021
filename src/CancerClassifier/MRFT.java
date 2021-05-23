package CancerClassifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

//wrapper implementation for a Phi matrix
class Phi implements Serializable{
	private static final long serialVersionUID = 1L;
	private double[][] L;

	public Phi(double[][] l) {
		this.L = l;
	}

    public Phi(int xi, int xj) {
        double[][] l = new double[xi][xj];
		this.L = l;
	}

    public double getphiValue(int xi, int xj) {
        return L[xi][xj];
	}

    public void setphiValue(int xi, int xj, double value){
        this.L[xi][xj] = value;
    }

	@Override
	public String toString() {
		return "[Phi=" + Arrays.deepToString(L) +"]";
	}
}

/*Markov implements a matrix-like object with Phi matrixes as elements
  This implementation takes advantage of the properties of the matrix (sparse and simetrical)   
  Inspired by: Gundersen e Steihaug in:
  Storage Formats for Sparse Matrices in Java, de Mikel Luján, Anila Usman, Patrick Hardie, T. L. Freeman e
  John R. Gurd.  (https://link.springer.com/content/pdf/10.1007%2F11428831_45.pdf, Figura 2) 
*/ 
class Markov implements Serializable{
	private static final long serialVersionUID = 2L;
	
	//list of lists with Phi matrixes as elements
	private ArrayList<ArrayList<Phi>> Phis;  
 
	///list of lists with Phi matrix location indexes
	private ArrayList<ArrayList<Integer>> index;

	//markov dimension
	private int N;

	//creates Markov
	public Markov(int N){
		ArrayList<ArrayList<Phi>> phis = new ArrayList<ArrayList<Phi>>(); //creates Phi list
		this.Phis = phis;
		ArrayList<ArrayList<Integer>> index = new ArrayList<ArrayList<Integer>>(); //creates index list
		this.index = index;

		//adds Phi matrixes and corresponding indexes
		for (int i = 0; i < N; i++) {
			this.Phis.add(new ArrayList<Phi>());
			this.index.add(new ArrayList<Integer>());
		}
		this.N = N; //sets dimension
	}

	//looks for phi in position (i,j)
	public boolean isTherePhi(int i, int j){
		boolean isThere = false;

		//sets major index and minor index  
		int minor = Math.min(i,j);
		int major = Math.max(i,j);
		
		//checks if major is present on minor line 
		if(minor < N && major < N){
			for (int y = 0; !isThere && y < this.index.get(minor).size(); y++) { //goes through every line
				if (this.index.get(minor).size() > 0 &&	this.index.get(minor).get(y) == major) { //goes through columns (remember: markov represents a simetric matrix) 
					isThere = true;
				}
			}
		}
		return isThere;
	}

	//setter do Phi, adiciona à linha do íncie 
	public void setPhi(int i, int j, Phi p) {
		
		//sets major index and minor index  
		int minor = Math.min(i,j);
		int major = Math.max(i,j);
	
		if (i < N && j < N) { //checks if i and j are compatible with markov dimension
			this.Phis.get(minor).add(p); //adds Phi matrix on markov Phi list
			this.index.get(minor).add(major); //adds corresponding index on markov index list
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


	//returns Phi list
	private ArrayList<ArrayList<Phi>> getPhis() {
		return this.Phis;
	}

	//returns index list
	private ArrayList<ArrayList<Integer>> getIndex() {
		return this.index;
	}

	//returns markov dimension
	public int getN() {
		return this.N;
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

//Markov Ramdom Field Tree creates a Markov of Phi matrixes, according to a given dataset and a given tree
public class MRFT implements Serializable{
	private static final long serialVersionUID = 4L;

	//Atributes
	private int dim;		             //MRFT dimension
	private Markov markov;	 	     	 //Markov of Phi matrixes
	private ArrayList<Integer> special;	 //Special tree Branch 
    private int[] measurementsDomain; 	 //List that contains the number of possible values for each dataset measurement

	//Constructor
	public MRFT(Dataset T, Tree A) {
		this.dim = A.getDimension(); //MRFT dimension = number of tree leaves
        if (this.dim == T.getMeasurementNumber()){	//checks if the tree and the dataset have the same dimension
			this.measurementsDomain = T.measurementDim();
		    this.special = A.first();	//Sets special branch 
            this.markov = add_PHI(T, A); //populates markov with Phi matrixes
        }else {
            throw new AssertionError("The number of Tree Leafs (" + this.dim + ") must match the number of measurements (" +T.getMeasurementNumber()+ ") in the dataset");
        }
	}
	
	@Override
	public String toString() {
		return "MRFT [dim=" + this.dim + "\n tree="  + "\n special=" + special + "\n markov=" + this.markov.toString() + "]";
	}
	
	public int getMeasurementDim(int i){
        return this.measurementsDomain[i];
	}
	public int getDim() {
		return this.dim;
	}


	//checks if two leaves are connected by the special branch
	public boolean specialQ(int i, int j ) {	
		ArrayList<Integer> edge = new ArrayList<Integer>();
		edge.add(i);
		edge.add(j);
		return edge.equals(this.special);
	}
	

	
	//phi value for normal branches
	public double phi_normal(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi 
		return (T.Count(i, j, xi, xj) + delta)/(T.Count(i, xi) + delta*this.getMeasurementDim(j)); 	
	}
	//phi value for special branch
	public double phi_special(Dataset T, int i, int j, int xi, int xj, double delta) { 			//phi de xi e xj
		return (T.Count(i,j, xi, xj) + delta)/(T.len() + delta*this.getMeasurementDim(i)*this.getMeasurementDim(j));     //Maria: alterei para T.len()  (estava T.len)
	}
	
	//pupulates Markov with Phi matrixes containing phi values
	public Markov add_PHI(Dataset T, Tree A){ 
		Markov markov =  new Markov(this.dim); //creates markov
		boolean found_special = false;
		for(int i=0; i < this.dim; i++) {		//goes through each pair of tree leaves 									
			for(int j=i; j < this.dim; j++) {													
				if (A.branchQ(i,j)) { 			//checks if there is a branch between leaves
					int dimi = getMeasurementDim(i); //number of possible xi measurements of i
					int dimj = getMeasurementDim(j); //number of possible xj measurements of j
					boolean special = false;
					if(!found_special && this.specialQ(i,j)) { //checks if i and j are connected by the special branch
						special = true;
						found_special = true;}
                    Phi PHI = new Phi(dimi,dimj);		//creates Phi matrix 
					for (int xi=0; xi < dimi; xi++) { 		//goes through possible pair of values xi and xj					 
						for (int xj=0; xj < dimj; xj++) { 
							
							//adds phi values o Phi matrix
							if (special) {
								PHI.setphiValue(xi,xj, phi_special(T, i, j, xi, xj, 0.2)); 	
							}else { 															
								PHI.setphiValue(xi,xj, phi_normal(T, i, j, xi, xj, 0.2)); 	
							}                           
						}
					}
                    markov.setPhi(i, j, PHI); //adds Phi matrix to markov
				}
			}
		}
		return markov; 
	}
				
	//given a vector Xn with n measurement values, returns the probability of such values according to MRFT
	public double Probability(int[] Xn) { 
		int dim = this.dim;
		boolean connections =  false;
		double result = 1;
		if ( Xn.length == dim ) { 	//checks if Xn has the right length, according to MRFT dimension 
			for(int i = 0; i < this.dim; i++) {  //goes through every pair of Xn elements
				for(int j = i; j < this.dim; j++) { 	 										
					if (this.markov.isTherePhi(i,j)) { //checks if there is a Phi matrix in position (i,j)	                     	
						if (! connections) connections = true;
						result = (result*(this.markov.getPhi(i, j).getphiValue(Xn[i],Xn[j])));  //updates result
					}
				}	
			}
			if (! connections) { //probability = 0 if there are no branches
				return 0;
			}else {
				return result;
			}
		}		
		else {
			throw new AssertionError("Tree's and array's dimension must be the same");
		}
	}

    
		
	public static void main(String[] args) {
        //Creating tree
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
        
        //Creating MRFT
        MRFT mkv = new MRFT(ds1,g);
        int[] m9 = {3,3,3,3,3};
        double a = mkv.Probability(m9);
        System.out.println(a);
    }
    
}
