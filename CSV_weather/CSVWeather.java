
/**
 * Write a description of CSVWeather here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;
import java.lang.*;

public class CSVWeather {
    public CSVRecord coldestHourInFile​(CSVParser parser){
        CSVRecord lowestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            lowestSoFar = getLowestTemperatureOfTwo(currentRow, lowestSoFar);
        }
        //The lowestSoFar is the answer
        return lowestSoFar;
    }
    
    public CSVRecord getLowestTemperatureOfTwo (CSVRecord currentRow, CSVRecord lowestSoFar) {
        if (lowestSoFar == null) {
            lowestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double lowestTemp = Double.parseDouble(lowestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature < lowestSoFar’s
            if (currentTemp < lowestTemp && currentTemp > -100 ) {
                //If so update lowestSoFar to currentRow
                lowestSoFar = currentRow;
            }
        }
        return lowestSoFar;
    }
    
    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVRecord lowest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + lowest.get("TemperatureF") +
                   " at " + lowest.get("TimeEST"));
    }
    
    public String fileWithColdestTemperature(){
        CSVRecord lowestSoFar = null;
        CSVRecord coldestRecord = null;
        File coldestDay = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = coldestHourInFile(fr.getCSVParser());
            coldestRecord = lowestSoFar;
            // use method to compare two records
            lowestSoFar = getLowestTemperatureOfTwo(currentRow, lowestSoFar);
            if(!lowestSoFar.equals(coldestRecord))
                coldestDay = f;
        }
        return coldestDay.getName();
    }
    
    public void testFileWithColdestTemperature(){
        String fileName = fileWithColdestTemperature();
        System.out.println("Coldest day was in file  " + fileName);
        FileResource fr = new FileResource("nc_weather/2013/"+ fileName);
        CSVRecord lowest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest temperature on that day was " + lowest.get("TemperatureF") ) ;
        System.out.println("All the Temperatures on the coldest day were :");
        for (CSVRecord currentRow : fr.getCSVParser()) {
            // print temperature at every hour
            System.out.println( currentRow.get("DateUTC") + 
                   "  " + currentRow.get("TimeEST") + " " +  currentRow.get("TemperatureF") );
        }
    }
    
    public CSVRecord getLowestHumidityOfTwo (CSVRecord currentRow, CSVRecord lowestSoFar) {
        if (lowestSoFar == null) {
            lowestSoFar = currentRow;
        }
        //Otherwise
        else {
            if (currentRow.get("Humidity").compareTo("N/A") != 0)
            {
                double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
                double lowestHumidity = Double.parseDouble(lowestSoFar.get("Humidity"));
                
                //Check if currentRow’s humidity < lowestSoFar’s
                if (currentHumidity < lowestHumidity ) {
                    //If so update lowestSoFar to currentRow
                    lowestSoFar = currentRow;
                }
            }
        }
        return lowestSoFar;
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            lowestSoFar = getLowestHumidityOfTwo(currentRow, lowestSoFar);
        }
        //The lowestSoFar is the answer
        return lowestSoFar;
    }            
   
    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVRecord lowestHumidity = lowestHumidityInFile(fr.getCSVParser());
        System.out.println("lowest humidity was " + lowestHumidity.get("Humidity") +
                   " at " + lowestHumidity.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles(){
        CSVRecord lowestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            // use method to compare two records
            lowestSoFar = getLowestHumidityOfTwo(currentRow, lowestSoFar);
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInManyFiles(){
        CSVRecord lowest = lowestHumidityInManyFiles();
        System.out.println("Lowest humidity was " + lowest.get("Humidity") +
                   " at " + lowest.get("DateUTC"));
        
    }
    
    public double averageTemperatureInFile(CSVParser parser){
        double sum = 0;
        int count = 0;    
        for (CSVRecord currentRow : parser) {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            count++;
            sum += currentTemp;
        }
       return sum / count;     
    }
    
    public void testAverageTemperatureInFile(){
        FileResource fr = new FileResource();
        double averageTemperature = averageTemperatureInFile(fr.getCSVParser());
        System.out.println("Average Temperature in file is "  + averageTemperature);
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sum = 0;
        int count = 0;    
        for (CSVRecord currentRow : parser) {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
            if (currentHumidity >= value){
                count++;
                sum += currentTemp;
            }
        }
        if (sum == 0 ) 
            return 0; 
        else return sum / count;
    }
    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        int humidity = 80;
        double averageTemp = averageTemperatureWithHighHumidityInFile(fr.getCSVParser(), humidity);
        if (averageTemp != 0)
            System.out.println("Average Temp with Humidity (" + humidity + ") is " + averageTemp);
        else
            System.out.println("No temperatures with humidity greater than or equal to " + humidity);
    }
}
