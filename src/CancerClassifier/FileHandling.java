package CancerClassifier;

//Import
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

//Serialization 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class FileHandling {

  public static Dataset getDataset(String path) {
    try {
      File csvFile = new File(path);
      Scanner csvReader = new Scanner(csvFile);

      //procedimento para a primeira linha, que definirá o nr de medições do dataset
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

      int classification = Integer.parseInt(values[measurementNumber]);
      int[] measurements = new int[measurementNumber];

      for (int i = 0; i < firstValues.length - 1; i++) {
        int thisMeasurement = Integer.parseInt(values[i]);
        measurements[i] = thisMeasurement;
      }    

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


  public static void exportClassifier(Dataset data){
    ClassifierPackager  object = new ClassifierPackager(data);
    String filename = "file.classifier";
      
    // Serialization 
    try
    {   
        //Saving of object in a file
        FileOutputStream file = new FileOutputStream(filename);
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


    ClassifierPackager object1 = null;
    
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
        System.out.println("a = " + cp.getFreq(1));

        return cp;

    }
      
    catch(IOException ex)
    {
        System.out.println("IOException is caught");
    }
      
    catch(ClassNotFoundException ex)
    {
        System.out.println("ClassNotFoundException is caught");
    }

    return null;
  }

  public static void main(String[] args) {
    
    System.out.println("\n");
    Dataset a = FileHandling.getDataset("Datasets/bcancer.csv");
    System.out.println(a);

    FileHandling.exportClassifier(a);
    FileHandling.importClassifier("file.classifier");
  }
}
