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
        ArrayList<String> lastFive = new ArrayList<>();
        //create while loop to check what the user wants to do and then complete that task
        while(cont) {
            System.out.println("What would you like to do?");
            System.out.println("1: Put in asset tag");
            System.out.println("2: Computer has a problem");
            System.out.println("3: Delete an entry");
            System.out.println("4: Check Progress!");
            System.out.println("5: Get last 5 entries added");
            System.out.println("6: See how many you did today!");
            System.out.println("7: Exit");
            input = keyboard.nextInt();
            if(input == 1) {
                addInitials(date, keyboard, lastFive);
            }
            else if(input == 2) {
                problem(keyboard);
            }
            else if(input == 3) {
                deleteEntry(keyboard);
            }
            else if(input == 4) {
                checkProgress(false);
            }
            else if(input == 5) {
                lastFiveEntries(lastFive);
            }
            else if(input == 6) {
                progressToday();
            }
            else if(input == 7) {
                cont = false;
            }
            else {
                System.out.println("Please put valid instruction.");
            }

        }
        keyboard.close();
    }

    //create a function that puts users name onto document 
    public static void addInitials(String date, Scanner keyboard, ArrayList<String> lastFive) {
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
                    inputProgress(false);
                    if(lineData.size() >= 3) {
                        System.out.println("This asset tag has already been added");
                        System.out.println(" ");
                        return;
                    }
                    else {
                        lineData.add(date);
                        if(lastFive.size() >= 5) {
                            lastFive.remove(0);
                            lastFive.add(assetTag);
                        }
                        else {
                            lastFive.add(assetTag);
                        }
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
            checkProgress(true);
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
                    inputProgress(false);
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
            checkProgress(true);
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
                    inputProgress(true);
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
    public static void checkProgress(boolean check) {  
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

            if(check) {
                if(finishedPercent >= 34.8 && finishedPercent <= 35.2) {
                    System.out.println("");
                    System.out.println("35% of the way done woooooooooooooo");
                    System.out.println("");
                }
                else if(finishedPercent >= 39.8 && finishedPercent <= 40.3) {
                    System.out.println("");
                    System.out.println("40% of the way done woooooooooooooo");
                    System.out.println("");
                }
                else if(finishedPercent >= 49.8 && finishedPercent <= 50.2) {
                    System.out.println("");
                    System.out.println("50% of the way done woooooooooooooo");
                    System.out.println("");
                    int count = 0;
                    while(count < 50) {
                        System.out.println("WOOOOOOO");
                        count++;
                    }
                }
                else if(finishedPercent >= 59.8 && finishedPercent <= 60.2) {
                    System.out.println("");
                    System.out.println("60% of the way done woooooooooooooo");
                    System.out.println("");
                }
                else if(finishedPercent >= 69.8 && finishedPercent <= 70.2) {
                    System.out.println("");
                    System.out.println("70% of the way done woooooooooooooo");
                    System.out.println("");
                }
                else if(finishedPercent >= 79.8 && finishedPercent <= 80.2) {
                    System.out.println("");
                    System.out.println("80% of the way done woooooooooooooo");
                    System.out.println("");
                }
                else if(finishedPercent >= 89.8 && finishedPercent <= 90.2) {
                    System.out.println("");
                    System.out.println("90% of the way done woooooooooooooo");
                    System.out.println("");
                }
                else if(finishedPercent >= 99.8 && finishedPercent <= 100.2) {
                    System.out.println("");
                    System.out.println("YOU ARE DONE YOU LEGEND LETS GOOOOOOOO");
                    System.out.println("");
                    int count = 0;
                    while(count < 100) {
                        System.out.println("WOOOOOOO");
                        count++;
                    }
                }
            }

            if(!check) {
            //update user with new format
             DecimalFormat df = new DecimalFormat("###.#");
             System.out.println("");
             System.out.println("You are " + df.format(finishedPercent) + "% of the way done! Keep up the good work!");
             System.out.println("");
            }
        }
        catch(Exception e)
        {
            System.out.print(e);
        }

    }

    //get the last five added entries and print them out
    public static void lastFiveEntries(ArrayList<String> lastFive) {
        if(lastFive.size() == 0) {
            System.out.println("");
            System.out.println("You have no entries added in this session!");
            System.out.println("");
            return;
        }
        System.out.println("");
        System.out.println("Here are your last 5 entries into the csv file:");
        for(String tag: lastFive) {
            System.out.println(tag);
        }
        System.out.println("");
    }

    //add to a seperate spreadsheet about how many entries were added that day
    public static void inputProgress(boolean delete) {
        SimpleDateFormat ft 
        = new SimpleDateFormat("MM-dd-yyyy"); 

        String date = ft.format(new Date());

        if(!delete) {
            try{
                String file = "progress.csv";
                List<List<String>> data = new ArrayList<>();
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                int added = 0;
        
                //used buffered reader to read in the data and delete the entry the user does not want
                String line = br.readLine();
                while(line != null) {
                    String[] lineArray = line.split(",");
                    List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                    if(lineData.size() > 0) {
                        if(lineData.get(0).equals(date)) {
                            added++;
                            int count = Integer.parseInt(lineData.get(1));
                            count++;
                            lineData.set(1, Integer.toString(count));
                            data.add(lineData);
                            break;
                        }
                    }
                    data.add(lineData);
                    line = br.readLine();
                }
                if(added == 0) {
                    List<String> lineData = new ArrayList<>(2);
                    lineData.add(date);
                    lineData.add("1");
                    data.add(lineData);
                }
                br.close();
        
        
        
        
                //write back to file with correct chanfes
                BufferedWriter write = new BufferedWriter(new FileWriter(file));
                for(List<String> list: data) {
                    write.write(String.join(",", list));
                    write.newLine();
                }
                //update user
                write.close();
            }
            catch(Exception e)
            {
                System.out.print(e);
            }
        }
        else if(delete) {
            try{
                String file = "progress.csv";
                List<List<String>> data = new ArrayList<>();
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                int added = 0;
        
                //used buffered reader to read in the data and delete the entry the user does not want
                String line = br.readLine();
                while(line != null) {
                    String[] lineArray = line.split(",");
                    List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                    if(lineData.size() > 0) {
                        if(lineData.get(0).equals(date)) {
                            added++;
                            int count = Integer.parseInt(lineData.get(1));
                            if(count == 0) {
                                break;
                            }
                            count--;
                            lineData.set(1, Integer.toString(count));
                            data.add(lineData);
                            break;
                        }
                    }
                    data.add(lineData);
                    line = br.readLine();
                }
                br.close();
                //write back to file with correct chanfes
                BufferedWriter write = new BufferedWriter(new FileWriter(file));
                for(List<String> list: data) {
                    write.write(String.join(",", list));
                    write.newLine();
                }
                //update user
                write.close();
            }
            catch(Exception e)
            {
                System.out.print(e);
            }
        }  
    }

    //print out however many entries are added today
    public static void progressToday() {
        SimpleDateFormat ft = new SimpleDateFormat("MM-dd-yyyy"); 
        String date = ft.format(new Date());
        try {
            //create variables
            String file = "progress.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //used buffered reader to read in the data and delete the entry the user does not want
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                //print out how many done today
                if(lineData.get(0).equals(date)) {
                    System.out.println("");
                    System.out.println("You have done " + lineData.get(1) + " computers today!");
                    System.out.println("");
                }
                line = br.readLine();
            }
            br.close();
        }
    catch(Exception e)
    {
        System.out.print(e);
    }
    
    }
}