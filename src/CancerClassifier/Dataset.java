package CancerClassifier;

//To allow ArrayList use
import java.util.ArrayList;

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
        this.len = l;
        this.dataList = dL;
    }
    
    //Counting datapoints in the dataset, in which there is xi in index i and xi in index j, simultaneously
    public int Count(int i, int j, double xi, double xj){
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

    //
    @Override
    public String toString() {
        return "{" +
            " len='" + getLen() + "'" +
            ", dataList='" + getDataList() + "'" +
            "}";
    }


    public int getLen() {
        return this.len;
    }

    public ArrayList<DataPoint> getDataList() {
        return this.dataList;
    }



    public static void main(String[] args) {
        
    }
    
}
