package CancerClassifier;

import java.util.Arrays;

//DataPoint stores an array of 10 measurements (index 0 to 9) and its respective classification as benign or malignant
public class DataPoint {

    //Class Attributes
    int[] measurements;
    int classification;

    //Constructor of DataPoint with specific measurements and classification. Array length and classification must be as expected.
    public DataPoint(int[] m, int c){
        if (m.length == 4){   
            this.measurements = m;
            this.classification = c;
        }else{
            throw new AssertionError("A Data Point must have 10 measurements.");
        }
    }

    //getter for measurements
    public int[] getMeasurements() {
        return this.measurements;
    }

    //getter for tumour classification
    public int getClassification() {
        return this.classification;
    }

    //Searching the datapoint for xi in index i and xi in index j, simultaneously
    public boolean isThere(int i, int j, int xi, int xj){
        if (i >= 10 || j >= 10 || i < 0 || j < 0){
            throw new AssertionError("You shall provide indexes between 0 to 9");
        } else {
            return (this.measurements[i] == xi && this.measurements[j] == xj);
        }
    }

    //Searching the datapoint for xi in index i 
    public boolean isThere(int i, int xi){
        return this.measurements[i] == xi;    
    }

    @Override
    public String toString() {
        return "(Measurements: " + Arrays.toString(getMeasurements()) + "; " +
               "Classification: " + getClassification() + ") \n";
    }

    public static void main(String[] args) {
        
        //Creating DataPoint
        int[] m2 = {1,2,3,4,5,6,7,8,9,10};
        int c2 = 0;
        DataPoint dp2 = new DataPoint(m2,c2);
        System.out.println("DataPoint:" + dp2.toString());

        
        /*Forcing exceptions
        double[] m3 = {1,2,3,4,5,6,7,8,9};
        int c3 = 1;
        DataPoint dp3 = new DataPoint(m3,c3);
        System.out.println("Exception Length:" + dp3.toString());
        
        double[] m4 = {1,2,3,4,3,5,6,7,8,9};
        int c4 = "neutral";
        DataPoint dp4 = new DataPoint(m4,c4);
        System.out.println("Exception Length:" + dp4.toString());
        */

        //Testing the isThere function
        int[] m5 = {1,2,3,4,5,6,7,8,9,10};
        int c5 = 0;
        DataPoint dp5 = new DataPoint(m5,c5);
        System.out.println("Supposed to succeed: " + dp5.isThere(0,1,1,2));
        System.out.println("Supposed to fail: " + dp5.isThere(0,1,1,3));

    }


    
}
