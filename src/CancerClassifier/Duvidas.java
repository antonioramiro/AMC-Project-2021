package CancerClassifier;

import java.util.ArrayList;
import java.util.Arrays;

/*public class Matriz {

	


	
    //funciona	
    public static void main(String[] args) {
    	
    //	int[][] edges = { {0,1,3}, {1,2,7}, {2,0,1} };
    	//int[][] edges1 = { {0,1,3}, {1,2,7}, {2,0,1} };
    	//double [][] m = new double[3][3];
    	double[][] m = new double [3][3]; 
    	
    	
    	//System.out.println("m=" + Arrays.deepToString(m)); 
    	//ArrayList<Double> list = new ArrayList<Double>();
    	
    	double[][] A = {{1, 2},
				{3, 4}}; // 2x2
    	System.out.println("A = " + Arrays.deepToString(A));
    	

     // Part 1: create new ArrayList.
        // ... Add 3 elements.
    	ArrayList<ArrayList<>> = new ArrayList<elements>();
        ArrayList<double [][]> elements = new ArrayList<>();
        ArrayList<Integer> elements2 = new ArrayList<>();
        elements2.add(1);
        elements.add(A);
        elements.add(A);
        //elements.add(15, m);
        //elements.add(20, m);
        
        for (int i = 0; i < elements2.size(); i++) {
			System.out.println(elements2.get(i));
        }
        
        for (int i = 0; i < elements.size(); i++) {
			System.out.println(Arrays.deepToString(elements.get(i)));
        }
    }
       /* ArrayList<ArrayList<Double[][]>> elements1 = new ArrayList<ArrayList>;
        elements1.add(10, elements);
        elements1.add(15, elements);
        elements1.add(20, elements); */


        // Part 2: get count by calling size.
        //System.out.println("elements =" + Arrays.toString(m));
        //System.out.println("Count elements: " + elements.size());
        //System.out.println("Count elements1: " + elements1.size());

        // Part 3: clear and print size.
        //elements.clear();
        //System.out.println("Count: " + elements.size());


class matrizes {
	double[][] L;
	public matrizes(double [][] l) {
		this.L = l;
	}
	@Override
	public String toString() {
		return "matrizes [L=" + Arrays.deepToString(L) + "]";
	}
	
}

public class Duvidas {

	public static void main(String[] args) {
		double[][] z = {{1,2}, {3,4}};
		matrizes mm = new matrizes(z);
		
		System.out.println(Arrays.deepToString(z));
		System.out.println(mm);
		
		ArrayList<ArrayList<matrizes>> X = new ArrayList<ArrayList<matrizes>>();
		
		
		X.add(new ArrayList<matrizes>());
		X.get(0).add(mm);
		
		System.out.println(X);
		
		X.get(0).add(mm);
		
		System.out.println(X);
	}
}