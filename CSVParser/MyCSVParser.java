
/**
 * Write a description of MyCSVParser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class MyCSVParser {
    
    public void  tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println(countryInfo(parser, "United States"));
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "gold", "diamonds");
        parser = fr.getCSVParser();
        System.out.println(numberOfExporters(parser, "gold"));
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999");
    }
    
    public String countryInfo(CSVParser parser, String country){
        // consider notfound at first 
        String info = "NOTFOUND";
        for (CSVRecord record : parser){
            if (record.get("Country").equals(country)){
                info = country + ": " +  record.get("Exports") + ": "+ record.get("Value (dollars)") ;
           }
        }
        return info;
    }
    
    public void listExportersTwoProducts​(CSVParser parser, String exportItem1, String exportItem2){
        for (CSVRecord record : parser){
            String exports = record.get("Exports");
            if (exports.contains(exportItem1) && exports.contains(exportItem2) )
                System.out.println(record.get("Country"));
           
        }
    }
        
    public int numberOfExporters(CSVParser parser, String exportItem){
        int count = 0;
        for (CSVRecord record : parser){
            String exports = record.get("Exports");
            if (exports.contains(exportItem))
                count++;
        }
        return count;
        
    }
    public void bigExporters(CSVParser parser, String amount){
        for (CSVRecord record : parser){
            if (Long.parseLong(record.get("Value (dollars)").replace(",", "").substring(1)) > Long.parseLong(amount.replace(",", "").substring(1))){
                System.out.println (record.get("Country") + " " + record.get("Value (dollars)")) ;
           }
        }
        
    }
        
    
}
        
    


    
