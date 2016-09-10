
/**
 * Write a description of BabyNames here
 * Mini project of Java Programming: Solving Problems with Software 
 * an online course by coursera.
 * 
 * @author (abdelrahman mostafa) 
 * @version (9/9/2016)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;

public class BabyNames {
    public void totalBirths(CSVParser parser){
        int totalUniqueNames = 0;
        int totalUniqueBoys = 0;
        int totalUniqueGirls = 0;
        int totalBirths = 0;
		int totalBoys = 0;
		int totalGirls = 0;
		for (CSVRecord rec : parser ) {
			int numBorn = Integer.parseInt(rec.get(2));
			totalBirths += numBorn;
			if (rec.get(1).equals("M")) {
				totalBoys += numBorn;
				totalUniqueBoys += 1;
			}
			else {
				totalGirls += numBorn;
				totalUniqueGirls += 1;
			}
		}
		totalUniqueNames = totalUniqueBoys + totalUniqueGirls;
		System.out.println("total births = " + totalBirths);
		System.out.println("total girls = " + totalGirls);
		System.out.println("total boys = " + totalBoys);
        System.out.println("unique boys Names = " + totalUniqueBoys);
		System.out.println("unique girls Names = " + totalUniqueGirls);
		System.out.println("unique total Names = " + totalUniqueNames);
    }
    
    public void testTotalBirths () {
		FileResource fr = new FileResource();
		//FileResource fr = new FileResource("data/yob2014.csv");
		totalBirths(fr.getCSVParser(false));
	}
	
	public int getRank(int year, String name, String gender){
	    String fileName = "yob" + year + "short.csv";
	    FileResource fr = new FileResource("testing/"+fileName);
	    int rank = 0;
	    boolean found = false;
	    for (CSVRecord rec : fr.getCSVParser(false)) {
	         if (rec.get(1).equals(gender)){
	             if (rec.get(0).equals(name)){
	               rank += 1;
	               found = true;
	               break;
	             }
	             else{
	                 rank += 1;
	            }
	         }
	    }
	    if (found == true ) return rank;
	    else return -1;
	}
	public void testGetRank(){
	    int rank = getRank(2012,"Mason", "M");
	    System.out.println("Rank  = " + rank );
	}
	
	public String getName(int year, int rank, String gender){
	    String fileName = "yob" + year + "short.csv";
	    FileResource fr = new FileResource("testing/"+fileName);
	    int r = 0;
	    String foundName = null;
	    boolean found = false;
	    for (CSVRecord rec : fr.getCSVParser(false)) {
	         if (rec.get(1).equals(gender)){
	             r += 1;
	             if (r == rank){
	               foundName = rec.get(0);    
	               found = true;
	               break;
	             }
	         }
	    }
	    if (found == true ) return foundName;
	    else return "NO NAME";
	}
	
	public void testGetName(){
	   String name = getName(2012, 2 , "M");
	   System.out.println("Name  = " + name );
	   String name1 = getName(2012, 2 , "F");
	   System.out.println("Name  = " + name1 );
	}
	
	public void whatIsNameInYear​(String name, int year, int newYear, String gender){
	    int rank = getRank(2012, name, gender);
	    String newName = getName(newYear, rank , gender);
	    System.out.println(name + " born in " + year +  " would be " + newName 
	                       + " in " + newYear);
	}
	
	public void testWhatIsNameInYear(){
	   whatIsNameInYear​("Isabella", 2012, 2014, "F");
	   whatIsNameInYear​("Mason", 2012, 2014, "F");
	}
	
	public int yearOfHighestRank(String name, String gender){
	    double inf = Double.POSITIVE_INFINITY;
	    int highestRank = (int) inf;
	    int highestYear = -1;
	    DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            String year = f.getName().substring(3, 7);
            int rank = getRank(Integer.parseInt(year), name, gender);
            if (rank != -1 &&  rank <= highestRank){
                highestRank = rank;
                highestYear = Integer.parseInt(year);
            }
        }
        return highestYear;
	}
	
	public void testYearOfHighestRank(){
	    int highestRankedYear = yearOfHighestRank("Mason", "F");
	    System.out.println("Highest Rank is " + highestRankedYear);
	}
	public double getAverageRank(String name, String gender){
	    double averageRank = -1;
	    int sumRanks = 0;
	    int count = 0;
	    boolean found = false;
	    DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            String year = f.getName().substring(3, 7);
            int rank = getRank(Integer.parseInt(year), name, gender);
            if (rank != -1){
                found = true;
                count+=1;
                sumRanks += rank;
            }
        }
        if (found)  
            averageRank = 1.0 * sumRanks / count;
        else
            averageRank = -1;
        return averageRank;
	}
	public void testGetAverageRank(){
	    double avgRank = getAverageRank("Mason", "M");
	    System.out.println("Average Rank is " + avgRank);
	    double avgRank2 = getAverageRank("Jacob", "M");
	    System.out.println("Average Rank is " + avgRank2);
	}
	public int getTotalBirthsRankedHigher​(int year, String name, String gender){
	    int rank = getRank(year,name, gender);
	    int r = 0;
	    int totalBirths = 0;
	    String fileName = "yob" + year + "short.csv";
	    FileResource fr = new FileResource("testing/"+fileName);
	    for (CSVRecord rec : fr.getCSVParser(false)) {
	         if (rec.get(1).equals(gender)){
	             int numBorn = Integer.parseInt(rec.get(2));
	             r = getRank(year,rec.get(0), gender);
	             if (r < rank)
	               totalBirths += numBorn;
	         }
	    }
	    if(totalBirths > 0) return totalBirths;
	    else return -1;
	}
	
	public void testGetTotalBirthsRankedHigher​(){
	    int total = getTotalBirthsRankedHigher​(2012, "Ethan", "M");
	    System.out.println("Total Births Ranked Higher  = " + total );
	}
	
}
