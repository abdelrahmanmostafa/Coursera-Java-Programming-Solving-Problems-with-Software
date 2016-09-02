
/**
 * Write a description of GeneFinder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class GeneFinder {
    
    public void printAllStarts(String dna) {
        int start = 0;
        int i = 0;
        while (true){
            int index = dna.indexOf("ctg", start);
            if (index == -1) {
                break;
            }
            System.out.println (" starts at " + index);
            i++;
            start = index + 3;
                
        }
        System.out.println("ctg" + i);
    }
    
    public StorageResource storeAll(String dna) {
        int start = 0; 
        StorageResource store = new StorageResource();
        String dnaLower = dna.toLowerCase();
        while (true){
            int index = dnaLower.indexOf("atg", start);
            if (index == -1) {
                break;
            }
            //System.out.println (" starts at " + index );
            int stop = findStopIndex(dnaLower, index+3);
            
            //System.out.println (" stops at " + stop);
            
            if ( stop == dna.length() )
                start = index + 3;
            else {
                String gene = dna.substring(index,stop+3);
                //if (gene.length() > 6){
                    store.add(gene);
                    //System.out.println(gene);
                //}
                start = stop + 3;
            }
        }
        return store;
    }
    
        public int findStopIndex(String dna, int index) {
            int stopTAG = dna.indexOf("tag", index);
            int stopTGA = dna.indexOf("tga", index);
            int stopTAA = dna.indexOf("taa", index);
            
            if (stopTAG == -1 || ((stopTAG - index) % 3) != 0 )
                stopTAG = dna.length();
                
            if (stopTGA == -1 || ((stopTGA - index) % 3) != 0 )
                stopTGA = dna.length();
            
            if (stopTAA == -1 || ((stopTAA - index) % 3) != 0 )
                stopTAA = dna.length();
                
            int min = Math.min (stopTAG, Math.min(stopTGA,stopTAA));
            //System.out.println(min);
            return min;
        }
        
    
    
     public float cgRatio(String dna){
        int cgCount = 0;
        String dnaLower = dna.toLowerCase();
        for(char ch : dnaLower.toCharArray()){
            if (ch == 'c' || ch == 'g')
                cgCount++;
        }
        return (float)cgCount / dna.length();
    }
    
    public void testFinder() {
       String a = "CATGTAATAGATGAATGACTGATAGATATGCTTGTATGCTATGAAAATGTGAAATGACCCA";
       //printAll(a); 
    }
    
    public void testStorageFinder(){
        FileResource fr = new FileResource();
        int max = 6;
        String dnaStrand = fr.asString();
        StorageResource genesFound = storeAll(dnaStrand);
        //printAllStarts(dnaStrand.toLowerCase());
        //printGenes(genesFound);
        for (String gene : genesFound.data()){    
            System.out.println(gene.length());
            if ( gene.length() > max )
                max = gene.length();
          }
        System.out.println("max: " + max);
        System.out.println("number of genes found = "+ genesFound.size());
       
    }
    
    public void printGenes(StorageResource sr){
        int c1 = 0;
        int c2 = 0;
        for (String gene : sr.data()){    
            if (gene.length() > 60){
            c1++;    
            System.out.println(gene);
          }
          if (cgRatio(gene) > 0.35){
              c2++;    
              System.out.println(gene);
            }
         }
         System.out.println("number of genes +60 = "+ c1);
         System.out.println("number of genes +35% cgratio = "+ c2);
       
        
    }
}

