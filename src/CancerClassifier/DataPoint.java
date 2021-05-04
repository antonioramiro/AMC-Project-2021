package CancerClassifier;

import java.util.Arrays;

//DataPoint stores an array of 10 measurements (index 0 to 9) and its respective classification as benign or malignant
public class DataPoint {

    //Class Attributes
    double[] measurements;
    String classification;

    //Constructor of null DataPoint
    public DataPoint(){
        this.classification = null;
        this.measurements = null;
    }

    //Constructor of DataPoint with specific measurements and classification. Array length and classification must be as expected.
    public DataPoint(double[] m, String c){
        if ((m.length == 10) && (c == "benign" && c != "malignant") || (c != "benign" && c == "malignant")){   
            this.measurements = m;
            this.classification = c;
        }else{
            throw new IllegalArgumentException("A Data Point must have 10 measurements and a classification of either benign of malignant.");
        }
    }

    //getter for measurements
    public double[] getMeasurements() {
        return this.measurements;
    }

    //getter for tumour classification
    public String getClassification() {
        return this.classification;
    }

    //Searching the datapoint for xi in index i and xi in index j, simultaneously
    public boolean isThere(int i, int j, double xi, double xj){
        if (i >= 10 || j >= 10 || i < 0 || j < 0){
            throw new ArrayIndexOutOfBoundsException("You shall provide indexes between 0 to 9");
        } else {
            return (this.measurements[i] == xi && this.measurements[j] == xj);
        }
    }


    @Override
    public String toString() {
        return "(Measurements: " + Arrays.toString(getMeasurements()) + "; " +
               "Classification: " + getClassification() + ")";
    }


    public static void main(String[] args) {
        
        //Empty DataPoint
        DataPoint dp1 = new DataPoint();
        System.out.println("Empty DataPoint:" + dp1.toString());

        //Filled DataPoint
        double[] m2 = {1,2,3,4,5,6,7,8,9,10};
        String c2 = "benign";
        DataPoint dp2 = new DataPoint(m2,c2);
        System.out.println("Filled DataPoint:" + dp2.toString());

        
        /*Forcing exceptions
        double[] m3 = {1,2,3,4,5,6,7,8,9};
        String c3 = "benign";
        DataPoint dp3 = new DataPoint(m3,c3);
        System.out.println("Exception Length:" + dp3.toString());
        
        double[] m4 = {1,2,3,4,3,5,6,7,8,9};
        String c4 = "neutral";
        DataPoint dp4 = new DataPoint(m4,c4);
        System.out.println("Exception Length:" + dp4.toString());
        */

        //Testing the isThere function
        double[] m5 = {1,2,3,4,5,6,7,8,9,10};
        String c5 = "benign";
        DataPoint dp5 = new DataPoint(m5,c5);
        System.out.println("Supposed to succeed: " + dp5.isThere(0,1,1d,2d));
        System.out.println("Supposed to fail: " + dp5.isThere(0,1,1d,3d));

    }


    
}
