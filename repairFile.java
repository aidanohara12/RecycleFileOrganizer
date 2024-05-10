import java.util.Scanner;
import java.text.DecimalFormat;
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
        int input = 10;

        //create while loop to check what the user wants to do and then complete that task
        while(cont) {
            System.out.println("What would you like to do?");
            System.out.println("1: Put in asset tag");
            System.out.println("2: Computer has a problem");
            System.out.println("3: Delete an entry");
            System.out.println("4: Check Progress!");
            System.out.println("5: Exit");
            input = keyboard.nextInt();
            if(input == 1) {
                addInitials(date, keyboard);
            }
            else if(input == 2) {
                problem(keyboard);
            }
            else if(input == 3) {
                deleteEntry(keyboard);
            }
            else if(input == 4) {
                checkProgress();
            }
            else if(input == 5) {
                cont = false;
            }
            else {
                System.out.println("Please put valid instruction.");
            }

        }
        keyboard.close();
    }

    //create a function that puts users name onto document 
    public static void addInitials(String date, Scanner keyboard) {
        System.out.println("What is the asset tag of the newly erased computer?");
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
    public static void problem(Scanner keyboard) {
        //ask questions to user
        System.out.println("What is the asset tag of the troubled computer?");
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

    //create a delete entry variable
    public static void deleteEntry(Scanner keyboard) {
        //ask questions to user
        System.out.println("What entry would you like to delete?");
        String assetTag = keyboard.next();
        
        try
        {
            //create variables
            List<List<String>> data = new ArrayList<>();
            String file = "20232024Recycle List.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //used buffered reader to read in the data and delete the entry the user does not want
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                if(lineData.get(0).equals(assetTag)) {
                    if(lineData.size() >=4) {
                        lineData.remove(3);
                        lineData.remove(2);
                    }
                    else {
                        lineData.remove(2);
                    }
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

    //check progress function
    public static void checkProgress() {  
        int finished = -1;
        try
        {
            //create variables
            List<List<String>> data = new ArrayList<>();
            String file = "20232024Recycle List.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //used buffered reader to read in the data and delete the entry the user does not want
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                //if the line has more than 2 entries then that means it has been completed
                if(lineData.size() >= 3) {
                    finished++;
                }
                data.add(lineData);
                line = br.readLine();
            }
            br.close();

            //create a double to get the number in a percent 
            double finishedPercent = ((double)finished/(double)data.size()) * 100.0;

            //update user with new format
             DecimalFormat df = new DecimalFormat("###.#");
            System.out.println("");
            System.out.println("You are " + df.format(finishedPercent) + "% of the way done! Keep up the good work!");
            System.out.println("");
        }
        catch(Exception e)
        {
            System.out.print(e);
        }

    }
    

}