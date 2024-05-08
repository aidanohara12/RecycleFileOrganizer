import java.util.Scanner;
import java.lang.invoke.CallSite;
import java.util.*;
import java.text.ParseException; 
import java.text.SimpleDateFormat; 
import java.util.Date; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class repairFile {
   
    public static void main(String args[]) { 
        boolean cont = true;
        //first get the initials of the user
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter Initials of user");
        String initials = keyboard.next();

        //create date and name
        SimpleDateFormat ft 
                = new SimpleDateFormat("MM-dd-yyyy"); 
    
        String date = ft.format(new Date());

        date = date + " " + initials;

        //create while loop to check what the user wants to do and then complete that task
        while(cont) {
            System.out.println("What would you like to do?");
            System.out.println("1: Put in asset tag");
            System.out.println("2: Computer has a problem");
            System.out.println("3: Exit");
            int input = keyboard.nextInt();
            if(input == 1) {
                initials(date);
            }
            else if(input == 2) {
                problem();
            }
            else if(input == 3) {
                cont = false;
            }
            else {
                System.out.println("Please put valid instruction.");
            }

        }

        

    }

    //create a function that puts users name onto document 
    public static void initials(String date) {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("What Asset Tagged Computer has been eraced");
        String assetTag = keyboard.next();
    
        try
        {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "20232024Recycle List.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            
            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                if(lineData.get(0).equals(assetTag)) {
                    if(lineData.size() >= 3) {
                        System.out.println("This asset tag has already been added");
                        System.out.println(" ");
                        return;
                    }
                    else {
                        lineData.add(date);
                    }
                }
                data.add(lineData);
                line = br.readLine();
            }
            br.close();

            //check to see if asset tag given exists
            if(!checkAssetTag(assetTag, data)) {
                return;
            }

            //write back to the file with the changes
            BufferedWriter write = new BufferedWriter(new FileWriter(file));
            for(List<String> list: data) {
                write.write(String.join(",", list));
                write.newLine();
            }
            //close writer and then alert the user that it has been updated
            write.close();
            System.out.println("");
            System.out.println("Name and Date are Updated!");
            System.out.println("");
            

       }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }

    //create functiont hat checks to add a reason why the computer could not be erased
    public static void problem() {
        //ask questions to user
        Scanner keyboard = new Scanner(System.in);
        System.out.println("What Asset Tagged Computer has a problem");
        String assetTag = keyboard.next();
        System.out.println("What is the problem in one word");
        String problem = keyboard.next();
        try
        {
            //create variables
            List<List<String>> data = new ArrayList<>();
            String file = "20232024Recycle List.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //used buffered reader to read in the data 
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                if(lineData.get(0).equals(assetTag)) {
                    lineData.add("xxx");
                    lineData.add(problem);
                }
                data.add(lineData);
                line = br.readLine();
            }
            br.close();
            if(!checkAssetTag(assetTag, data)) {
                return;
            }
            //write back to file with correct chanfes
            BufferedWriter write = new BufferedWriter(new FileWriter(file));
            for(List<String> list: data) {
                write.write(String.join(",", list));
                write.newLine();
            }
            //update user
            write.close();
            System.out.println("");
            System.out.println("Problem is updated!");
            System.out.println("");
        }
       
        catch(Exception e)
        {
            System.out.print(e);
        }
    }

    //create functiont that goes through the data and correctly changes whether or not the asset tag exists in the file
    public static boolean checkAssetTag(String tag, List<List<String>> list) {
        for(List<String> set: list) {
            for(String asset: set) {
                if(asset.equals(tag)) {
                    return true;
                }
            }
        }
        System.out.println("");
        System.out.println("Asset Tag does not exist");
        System.out.println("");
        return false;
    }

}