package CancerClassifier;


import java.util.ArrayList;


public class Classifier {
    ArrayList<MRFT> arrayMRFT;
    double[] Frequency;
           
	public Classifier(ArrayList<MRFT> arrayMRFT, double[] Frequency){
		this.arrayMRFT = arrayMRFT;
        this.Frequency = Frequency;
	    }

    public int Classify(int[] Xn){
    	int classDimension = arrayMRFT.size(); 
        double p = 0;
        int diagnosis = -1;

        for(int i = 0; i < classDimension; i++){  //
            double classLikelyhood = Frequency[i] * (arrayMRFT.get(i).Probability(Xn));
            if(p < classLikelyhood){
                diagnosis = i;
                p = classLikelyhood;
            }         	
        }
        
        if(diagnosis == -1){
            throw new AssertionError("Could not assess clinical state");
        }
        return diagnosis;
    }

    public static void main(String[] args) {
        //Creating graph
        Tree g = new Tree();
		int[][] edges = {{0,1}, {1,2}, {1,3},{0,4},{4,5}};
		for(int[] e : edges) {
			g.addLeaf(e[0], e[1]);
		}

        //Creating Dataset
        Dataset ds1 = new Dataset(6);
        int[] m6 = {1,0,3,4,3,5};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);
        int[] m7 = {1,0,3,4,3,5};
        int c7 = 1;
        DataPoint dp7 = new DataPoint(m7,c7);
        int[] m8 = {1,0,3,4,3,5};
        int c8 = 1;
        DataPoint dp8 = new DataPoint(m8,c8);
        ds1.Add(dp6);
        for(int i = 0; i < 30; i++){
            ds1.Add(dp7);
        }
        ds1.Add(dp8);
        System.out.println("Dataset: " + ds1);
        
        //Creating MRFT
        MRFT mkv = new MRFT(ds1,g);
        int[] m9 = {1,0,3,4,3,5};
        
        ArrayList<MRFT> a = new ArrayList<MRFT>();
        a.add(mkv);
        a.add(mkv);
        
        //double[] f = new double[2];
        double[] f = {0.5,0.5};

        Classifier c = new Classifier(a, f);
        System.out.println(c.Classify(m9));
    }

    
}