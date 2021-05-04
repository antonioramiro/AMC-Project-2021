package CancerClassifier;

//To allow ArrayList use
import java.util.ArrayList;

import javax.xml.crypto.Data;

//Dataset stores an ArrayList of DataPoints, and its length.
public class Dataset {

    //Class Attributes
    int len;
    ArrayList<DataPoint> dataList;
    
    //Constructor of empty Dataset
    public Dataset(){
        this.len = 0;
        this.dataList = new ArrayList<DataPoint>();
    }

    //Constructor of filled Dataset
    public Dataset(int l,  ArrayList<DataPoint> dL){
        if(dL.size() == l){
            this.len = l;
            this.dataList = dL;
        } else {
            throw new IllegalArgumentException("'len', the first argument of Dataset must match with ArrayList's size.");
        }

    }
    
    //Counting datapoints in the dataset, in which there is xi in index i and xi in index j, simultaneously
    public int Count(int i, int j, int xi, int xj){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isThere(i, j, xi, xj)) counter++;
        }
        return counter;
    }

    //Adding a single DataPoint to the dataset
    public void Add(DataPoint v){
        this.len++;
        this.dataList.add(v);
    }

    //Returning a sub-Dataset, depending on which classification is requested
    public Dataset Fiber(String classification){
        Dataset fiber = new Dataset();
        for (DataPoint dp: this.dataList){
            if (dp.getClassification() == classification){
                fiber.Add(dp);
            }
        }
        return fiber;

    }

    //Print
    @Override
    public String toString() {
        return "{" +
            " len='" + getLen() + "'" +
            ", dataList='" + getDataList() + "'" +
            "}";
    }

    //Getter for length
    public int getLen() {
        return this.len;
    }

    //Getter for the Array
    public ArrayList<DataPoint> getDataList() {
        return this.dataList;
    }


    public static void main(String[] args) {
        
        //Empty Dataset
        Dataset ds1 = new Dataset();
        System.out.println("Empty Dataset: " + ds1);

        //Filled Dataset
        int[] m7 = {1,2,3,4,5,6,7,8,9,10};
        String c7 = "benign";
        DataPoint dp7 = new DataPoint(m7,c7);

        int[] m8 = {1,2,3,4,5,6,7,8,9,12};
        String c8 = "malignant";
        DataPoint dp8 = new DataPoint(m8,c8);

        ArrayList<DataPoint> dl = new ArrayList<DataPoint>();
        for(int i = 0; i < 93; i++){
            dl.add(dp7);
        };
        dl.add(dp8);
        
        Dataset ds2 = new Dataset(94,dl);
        System.out.println("Filled Dataset: " + ds2);

        //Adding to Dataset
        int[] m6 = {1,2,3,4,5,6,7,8,9,10};
        String c6 = "benign";
        DataPoint dp6 = new DataPoint(m6,c6);
        ds1.Add(dp6);
        System.out.println("Dataset with 1 datapoint: " + ds1);

        //Getting the fiber of malignant tumours
        System.out.println(ds2.Fiber("malignant")); 

        //Counting the number of combinations in the dataset
        System.out.println(ds2.Count(0, 9, 1, 10));
        

        
    }
    
}
