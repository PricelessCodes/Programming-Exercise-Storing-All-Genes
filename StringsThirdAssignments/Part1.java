
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.io.*;

public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        
        int endIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (endIndex != -1)
        {
            if ((endIndex - startIndex) % 3 == 0)
            {
            return endIndex;
            }
            endIndex = dna.indexOf(stopCodon, endIndex + 3);
        }
        return dna.length();
    }
    
    public String findGene(String dna, int Index) {
        
        int startIndex = dna.toLowerCase().indexOf("atg", Index);
        
        if (startIndex == -1) {
            return "";
        }
        
        int endIndexTAA = findStopCodon(dna.toLowerCase(), startIndex, "taa");
        int endIndexTAG = findStopCodon(dna.toLowerCase(), startIndex, "tag");
        int endIndexTGA = findStopCodon(dna.toLowerCase(), startIndex, "tga");
        
        int endIndex = Math.min(endIndexTAA, Math.min(endIndexTAG, endIndexTGA));
        
        String result = ""; 
        if (endIndex != dna.length()) {
            result = dna.substring(startIndex, endIndex + 3);
        }
        return result;
    }
    
    public void testFindGene()
    {
        String dna = "cccatggggtttaaatgataataataggagatgagagagtaagagttt";
        System.out.println("Dna: " + dna);
        printAllGenes(dna);
    }
    
    public void printAllGenes(String dna)
    {
        int startIndex = 0; 
        while (true)
        {
            String gen = findGene(dna, startIndex);
            
            if (gen.isEmpty())
            {
                break;
            }
            System.out.println("Gen " + startIndex + ": " + gen);
            startIndex = dna.indexOf(gen, startIndex) + gen.length();
        }
    }
    
    public int countGenes(String dna)
    {
        int startIndex = 0;
        int count = 0;
        while (true)
        {
            String gen = findGene(dna, startIndex);
            
            if (gen.isEmpty())
            {
                break;
            }
            count++;
            System.out.println("Gen " + startIndex + ": " + gen);
            startIndex = dna.indexOf(gen, startIndex) + gen.length();
        }
        return count;
    }
    
    public StorageResource getAllGenes(String dna)
    {
        StorageResource sr = new StorageResource();
        int startIndex = 0; 
        while (true)
        {
            String gen = findGene(dna, startIndex);
            
            if (gen.isEmpty())
            {
                break;
            }
            sr.add(gen);
            //System.out.println("Gen " + startIndex + ": " + gen);
            startIndex = dna.indexOf(gen, startIndex) + gen.length();
        }
        return sr;
    }
    
    public void testGetAllGens(String dna)
    {
        StorageResource sr = getAllGenes(dna);
        
        for (String item : sr.data()) {
            
            System.out.println("Gen: " + item);
        }
    
    }
    
    public double cgRatio(String dna)
    {
        int count = 0;
        for (int i = 0; i < dna.length(); i++) {
            if (dna.charAt(i) == 'c' || dna.charAt(i) == 'C' || dna.charAt(i) == 'g' || dna.charAt(i) == 'G')
            {
                count++;
            }        
        }
        return ((double)count/dna.length());
    }
    
    public void countCTG(String dna)
    {
        System.out.println("C & G counts: " + cgRatio(dna));
    }
    
    public void processGenes(StorageResource sr)
    {
        int countLong = 0;
        int countHigh = 0;
        int numberGens = 0;
        int max = 0;
        String LongestGen = "";
        for (String item : sr.data()) {
            
            if(item.length() > 60)
            {
                System.out.println("Gen with length > 9: " + item);
                countLong++;
            }
            if (cgRatio(item) > 0.35)
            {
                System.out.println("Gen in sr whose C-G-ratio is higher than 0.35: " + item);
                countHigh++;
            }
            if (item.length() > max)
            {
                max = item.length();
                LongestGen = item;
            }
            numberGens++;
        }
        System.out.println("number of Gens with length > 9: " + countLong);
        System.out.println("number of strings in sr whose C-G-ratio is higher than 0.35: " + countHigh);
        System.out.println("length of the longest gene in sr is: " + max + " -> " + LongestGen);
        System.out.println("number of gens: " + numberGens);
    }
    
    public void testProcessGenes()
    {
        FileResource fr = new FileResource("brca1line.fa");
        String dna = fr.asString();
        System.out.println("Dna: " + dna);
        processGenes(getAllGenes(dna));
    }
    
    void FindingWebLinks()
    {
        URLResource ur = new URLResource("https://users.cs.duke.edu/~rodger/GRch38dnapart.fa");
        for (String s : ur.lines()) 
        {
            //System.out.println("coun:" + countGenes(s));
        System.out.println("Dna: " + s);
        //processGenes(getAllGenes(s));
        GetAllGensEndWith(s.toLowerCase());
        }
    }
    
    public void GetAllGensEndWith(String dna)
    {
        int count = 0;
        int Index = dna.indexOf("ctg", 0);
        while (Index != -1)
        {
            count++;
            Index = Index + 3;
            Index = dna.indexOf("ctg", Index);
        }
        System.out.println("Count: " + count);
    
    }
    
    public void Maintest()
    {
        //String dna = "cccatggggtttaaatgataataataggagatgagagagtaagagttt";
        String dna = "ATGCCATAG";
        System.out.println("Dna: " + dna);
        //testGetAllGens(dna);
        countCTG(dna);
    }
    
    public void realTesting() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            String s = fr.asString();
            System.out.println("read " + s.length() + " characters");
            //String result = findProtein(s);
            //System.out.println("found " + result);
        }
    }
}
