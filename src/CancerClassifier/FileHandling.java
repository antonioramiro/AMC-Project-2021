package CancerClassifier;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


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
      for (int i = 0; i < firstValues.length - 1; i++) {
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
      System.out.println("An error occurred.");
      e.printStackTrace();
    } 
    return null;
  }


  

  public static void main(String[] args) {
    
    System.out.println("\n");
    Dataset a = FileHandling.getDataset("Datasets/bcancer.csv");
    System.out.println(a.len());

  }
}
