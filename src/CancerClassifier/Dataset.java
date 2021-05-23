package CancerClassifier;


//To allow ArrayList use
import java.util.ArrayList;
import java.util.Arrays;

class DataPoint {
	
    //Class Attributes
    private int[] measurements;
    private int classification;

    //Constructor of DataPoint with specific measurements and classification. Array length and classification must be as expected.
    public DataPoint(int[] m, int c){
        this.measurements = m;
        this.classification = c;
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
            return (this.measurements[i] == xi && this.measurements[j] == xj);
    }

    //Searching the datapoint for xi in index i 
    public boolean isThere(int i, int xi){
        return this.measurements[i] == xi;    
    }

    //Searching the datapoint for xi in index i 
    public boolean isClass(int i){
        return this.classification == i;    
    }

    @Override
    public String toString() {
        return "(Measurements: " + Arrays.toString(getMeasurements()) + "; " +
               "Classification: " + getClassification() + ") \n";
    }

}

//Dataset stores an ArrayList of DataPoints, and its length.
public class Dataset {
	
    //Class Attributes
    private ArrayList<DataPoint> dataList;
    private int[] measurementsDomain; //domain of the dataset measurements
    private int classDomain; //domain of the class
    private int measurementNumber;
   
    //Constructor of empty Dataset
    public Dataset(int measurementNumber){
        this.dataList = new ArrayList<DataPoint>();
        this.measurementNumber = measurementNumber;
        int[] emptyDomain = new int[this.measurementNumber];
        this.measurementsDomain = emptyDomain;
        this.classDomain = 0;
        
    }

    //Adding a single DataPoint to the dataset
    public void Add(DataPoint v){
        this.dataList.add(v);
        for(int j = 0; j < this.measurementNumber; j++){
            if(v.getMeasurements()[j] >= this.measurementsDomain[j]){
                this.measurementsDomain[j] = v.getMeasurements()[j] + 1;
            }
            if(v.getClassification() >= this.classDomain){
                this.classDomain = v.getClassification() + 1;
            }
        }
    }

    //Returns dataset size/length
    public int len(){
        return this.dataList.size();
    }

    //returns the dimension of a measurement i
    public int measurementDim(int i){
        return this.measurementsDomain[i];
    }

    //returns the dimensions of the measurement
    public int[] measurementDim(){
        return this.measurementsDomain;
    }

    //returns the dimension of a class i
    public int classDim(){
        return this.classDomain;
    }    
    
    //Counting datapoints in the dataset, in which there is xi in index i and xi in index j, simultaneously
    public int Count(int i, int j, int xi, int xj){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isThere(i, j, xi, xj)) counter++;
        }
        return counter;
    }

    //Counting datapoints in the dataset, in which there is xi in index i 
    public int Count(int i, int xi){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isThere(i, xi)) counter++;
        }
        return counter;
    }

    public int countClassification(int i){
        int counter = 0;
        for (DataPoint dp: this.dataList){
            if (dp.isClass(i)) counter++;
        }
        return counter;
    }

    //returning the number of times a class exists
    public double classFrequency(int i) {
		return Double.valueOf(this.countClassification(i))/Double.valueOf(this.len());	
	}

    //Returning a sub-Dataset, depending on which classification is requested, the domain remains unaltered, regardless of 
    //the fiber class and its distribution, given that the domain maximizers are known to exist, even if not present in
    //the current fiber
    public Dataset Fiber(int classification){
        Dataset fiber = new Dataset(this.measurementNumber);
        for (DataPoint dp: this.dataList){
            if (dp.getClassification() == classification){
                fiber.Add(dp);
            }
        }
        return fiber;
    }

    //must be deleted
    public void setDomain(int[] measurementsDomain){
        this.measurementsDomain = measurementsDomain;
    }
    
    public int getMeasurementNumber () {
    	return this.measurementNumber;
    }

    //To String method
    @Override
    public String toString() {
        return "Dataset: \n"
               + "Sample size =" + this.len() + "\n"
               + "Measurements Domain =" + Arrays.toString(this.measurementsDomain) + ";\n" 
               + "Class Domain =" + this.classDomain + ";\n"
               + "Data =" + this.dataList
               + "\n";
    }

    public static void main(String[] args) { 
       
        //Empty Dataset
        Dataset ds1 = new Dataset(10);
        System.out.println("Empty Dataset: " + ds1);

        //Creating Datapoints
        int[] m6 = {1,0,3,4,5,6,7,8,9,10};
        int c6 = 0;
        DataPoint dp6 = new DataPoint(m6,c6);

        int[] m7 = {1,2,3,31,5,6,7,8,9,10};
        int c7 = 0;
        DataPoint dp7 = new DataPoint(m7,c7);

        int[] m8 = {1,2,3,4,5,6,22,8,9,12};
        int c8 = 1;
        DataPoint dp8 = new DataPoint(m8,c8);

        //Adding to Dataset
        ds1.Add(dp6);
        System.out.println("Dataset with 1 datapoint: " + ds1 + "\n");

        for(int i = 0; i < 3; i++){
            ds1.Add(dp7);
        }
        System.out.println("Dataset with 3 datapoints: " + ds1 + "\n");

        ds1.Add(dp8);
        System.out.println("Dataset with 4 datapoints: " + ds1 + "\n");

        //Getting the size of the dataset
        System.out.println("Dataset lenght: " + ds1.len());

        //Dim of the class
        System.out.println("Dataset nr of measurements " + ds1.classDim() + "\n");

        //Getting the fiber of 0-class tumours
        System.out.println("0-class Fiber: \n" + ds1.Fiber(0) + "\n"); 

        //Getting the fiber of 1-class tumours
        System.out.println("1-class Fiber: \n" + ds1.Fiber(1) + "\n"); 

        //Counting the number of combinations in the dataset
        System.out.println("Number of times 1 shows in variable indexed 0, and 10 in 9, respectively and simultaneosly: "
                           + ds1.Count(0, 9, 1, 10)); 
        
        //Calculating frequency of a class
        System.out.println("Frequency of class " + ds1.classFrequency(1));
    }    
}