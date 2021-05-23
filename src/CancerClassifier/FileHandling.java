package CancerClassifier;

//Import Dataset Needs
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner; 

//Serialization Needs
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

<<<<<<< Updated upstream

=======
//crossvalidation needs (dataset partitioning)
import java.util.Collections;
import java.io.FileWriter; 

//As the name suggests, handles files
>>>>>>> Stashed changes
public class FileHandling {

  //getDataset receives the path and imports the .csv onto a variable of the type Dataset
  public static Dataset getDataset(String path) {

    try {
      File csvFile = new File(path);
      Scanner csvReader = new Scanner(csvFile);

<<<<<<< Updated upstream
      //procedimento para a primeira linha, que definirá o nr de medições do dataset
=======
      //for the first line, reading the line
      if(!csvReader.hasNextLine()){
        csvReader.close();
        throw new AssertionError("CSV file empty.");
      }
>>>>>>> Stashed changes
      String firstData = csvReader.nextLine();
      String[] firstValues = (firstData.split(","));
      int measurementNumber = firstValues.length - 1;

      int[] firstMeasurements = new int[measurementNumber];
      for (int i = 0; i < measurementNumber; i++) {
        int thisMeasurement = Integer.parseInt(firstValues[i]);
        firstMeasurements[i] = thisMeasurement;
      }

      int firstClassification = Integer.parseInt(firstValues[measurementNumber]);

      Dataset newDataset = new Dataset(measurementNumber);
      DataPoint first = new DataPoint(firstMeasurements, firstClassification);
      newDataset.Add(first);

      // procedimento para as restantes linhas
      while (csvReader.hasNextLine()) {
        String data = csvReader.nextLine();
        String[] values = (data.split(","));

<<<<<<< Updated upstream
      int classification = Integer.parseInt(values[measurementNumber]);
      int[] measurements = new int[measurementNumber];

      for (int i = 0; i < firstValues.length - 1; i++) {
        int thisMeasurement = Integer.parseInt(values[i]);
        measurements[i] = thisMeasurement;
      }    

=======
        //Setting classification
        int classification = Integer.parseInt(values[measurementNumber]);
        
        //Setting measurements
        int[] measurements = new int[measurementNumber];
        for (int i = 0; i < measurementNumber; i++) {
          int thisMeasurement = Integer.parseInt(values[i]);
          measurements[i] = thisMeasurement;
        }    

        //creating and adding datapooint do dataset
>>>>>>> Stashed changes
        DataPoint dp = new DataPoint(measurements, classification);
        newDataset.Add(dp);
      }
      csvReader.close();
      
      return newDataset;

      
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
    return null;
  }


  public static void exportClassifier(Dataset data, String datasetName){
    ClassifierPackager  object = new ClassifierPackager(data);
    String filename = datasetName + ".classifier";
      
    // Serialization 
    try
    {   
        //Saving of object in a file
        FileOutputStream file = new FileOutputStream("Classifiers/" + filename);
        ObjectOutputStream out = new ObjectOutputStream(file);
          
        // Method for serialization of object
        out.writeObject(object);
          
        out.close();
        file.close();
          
        System.out.println("Object has been serialized");

    }
      
    catch(IOException ex)
    {
        System.out.println("IOException is caught");
        ex.printStackTrace();
    }
  }

  public static ClassifierPackager importClassifier(String path){
    // Deserialization
    try
    {   
        // Reading the object from a file
        FileInputStream file = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(file);
          
        // Method for deserialization of object
        ClassifierPackager cp = (ClassifierPackager)in.readObject();
          
        in.close();
        file.close();
          
        System.out.println("Object has been deserialized ");
       
        return cp;

    }
      
    catch(IOException ex)
    {
      ex.printStackTrace();
      System.out.println("IOException is caught");
    }
      
    catch(ClassNotFoundException ex)
    {
      ex.printStackTrace();
      System.out.println("ClassNotFoundException is caught");
    }

    ClassifierPackager error = new ClassifierPackager(null);
    return error;
  }

<<<<<<< Updated upstream
  public static void main(String[] args) {
=======
  //partitioning dataset in parts to use for cross validation
  public static void datasetPartition(String pathDatasetStart, String pathDatasetFinal, int partitionNumber) {

    try {
      //initializing the instance of the file to be read and the csv reader of said file
      File csvFile = new File(pathDatasetStart);
      Scanner csvReader = new Scanner(csvFile);
      ArrayList<String> csv = new ArrayList<String>();
          
      while (csvReader.hasNextLine()) {
        csv.add(csvReader.nextLine());
      }
      //closing the file 
      csvReader.close();

      //to disturb ordered datasets and kill biases
      Collections.shuffle(csv);
      int csvSize = csv.size();
      int partitionSize = (int) Math.floor(csvSize/5.0) ;
      
      //Saving of object in a file
      
      for (int i = 0; i < partitionNumber; i++){
        FileWriter csvWriter = new FileWriter(pathDatasetFinal+i+".csv");
        for (int j = i*partitionSize; j < (i+1)*partitionSize; j++){
          csvWriter.write(csv.get(j));
          csvWriter.write(System.lineSeparator());
        }
      
        csvWriter.close();
        System.out.println("Successfully wrote dataset partition " + i);
      }
      

     
      
    }catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    } 

  }

  //reading multiple dataset files onto one for cross validation
  public static Dataset getDatasetsMinusOne(String DatasetFolder, int TestingDatasetNr, int partitionNr) {
    Dataset newDataset = null;
    int measurementNumber = 0;

    try {
      boolean empty = true;
      for(int i = 0; i < partitionNr; i++){
        if(i != TestingDatasetNr){
          

          //initializing the instance of the file to be read and the csv reader of said file
          int index = partitionNr - 1;
          File csvFile = new File(DatasetFolder + "/" + index + ".csv");
          Scanner csvReader = new Scanner(csvFile);

          if(empty){ 
            empty = false;    
           
            //for the first line, reading the line
            if(!csvReader.hasNextLine()){
              csvReader.close();
              throw new AssertionError("CSV file empty.");
            }
            String firstData = csvReader.nextLine();
            String[] firstValues = (firstData.split(",")); //parsing the string, splitting the commas
            measurementNumber = firstValues.length - 1; //getting how many measurements the line has (the last one, -1 is the class)

            //now that we know the measurement number, we can create an int array, and convert the previous String array to int.array
            int[] firstMeasurements = new int[measurementNumber];
            for (int k = 0; k < measurementNumber; k++) {
              int thisMeasurement = Integer.parseInt(firstValues[k]);
              firstMeasurements[k] = thisMeasurement;
            }

            //setting the class of the line (last item) to a variable
            int firstClassification = Integer.parseInt(firstValues[measurementNumber]);

            //creating a datapoint w/ the measurements and the classification
            DataPoint first = new DataPoint(firstMeasurements, firstClassification);

            //creating a dataset, w/ the now know number of measurements
            newDataset = new Dataset(measurementNumber);
            
            //adding the first datapoint to the dataset
            newDataset.Add(first);
          
          }
          //having the measurement number, repeat previous procedure to remaining lines
          while (csvReader.hasNextLine()) {
            System.out.println("Reading Partition - Next Line" + i);
            System.out.println();
            //initialize data separated by commas array
            String data = csvReader.nextLine();
            String[] values = (data.split(","));

            //Setting classification
            int classification = Integer.parseInt(values[measurementNumber]);
            
            //Setting measurements
            int[] measurements = new int[measurementNumber];
            for (int l = 0; l < measurementNumber; l++) {
              int thisMeasurement = Integer.parseInt(values[l]);
              measurements[l] = thisMeasurement;
            }    

            //creating and adding datapooint do dataset
            DataPoint dp = new DataPoint(measurements, classification);
            newDataset.Add(dp);
          }

          //closing the file 
          csvReader.close();
          
          //returning the Dataset
             
          
        }
          

      }
      return newDataset;
    }

      //catching exception (usually found when the file is not in the correct path)
     catch (FileNotFoundException e) {
      e.printStackTrace();
    } 

    //if try fails, return null (since try failed, the previous catch instance was executed)
    return null;
  }

    public static void main(String[] args) {
    
    //importing a dataset from path
    Dataset T = FileHandling.getDataset("Datasets/diabetes.csv");
    //proving dataset correct importing (w/o spamming the console)
    System.out.println("Dataset domain: " + Arrays.toString(T.measurementDim()));

    //exporting the classifier
    FileHandling.exportClassifier(T,"diabetes");
  
    //importing the classifier
    ClassifierPackager cp = FileHandling.importClassifier("Classifiers/diabetes.classifier");
>>>>>>> Stashed changes
    
    //System.out.println("\n");
    Dataset a = FileHandling.getDataset("Datasets/diabetes.csv");
    System.out.println("Dataset example: " + Arrays.toString(a.measurementsDomain));

<<<<<<< Updated upstream
    FileHandling.exportClassifier(a,"diabetes");
    ClassifierPackager b = FileHandling.importClassifier("Classifiers/diabetes.classifier");
    System.out.println(b);
    
    int[] d = {1,3,0,0,0,0,0,0};
    Classifier c = new Classifier(b.getMrft(), b.getFreq());
    int result = c.Classify(d);
    System.out.println(result);
    
=======
    //constructing a classifier w/ both arrays
    Classifier c = new Classifier(cp.getMrft(), cp.getFreq());

    //classify the measurements
    int result = c.Classify(measurements);
    System.out.println("Diagnosis: " + result);

    //Creating 5 files for the CrossValidation
    FileHandling.datasetPartition("Datasets/hepatitis.csv","Datasets/CrossValidation/hepatitis/",5);

    //Import datasets except #1
    System.out.println(getDatasetsMinusOne("Datasets/CrossValidation/hepatitis/",1,5));
>>>>>>> Stashed changes
  }
}
