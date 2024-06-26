import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class intuneCompare {
    public static void main(String args[]) {
       
        //asset tags with coresponding emails
        Map<String, String> intuneMap = new HashMap<>();
        //asset tags with corresponding compliance data
        Map<String, String> complianceMap = new HashMap<>();
        //serial numbers with coresponding emails
        Map<String, String> serialEmail = new HashMap<>();
        //serial numbers with corresponding compliance data
        Map<String, String> serialComp = new HashMap<>();

        System.out.println("Here is the computers that are needed to be retired: ");
        System.out.println();

      //go through all of the data in intune and check to see if each one is related to the recycle spreadsheet
        for(String tag: readIntuneData(intuneMap, complianceMap)) {
            if(readRecylceData().contains(tag)) {
                String quote = "NCHS" + tag + ", Email: " + intuneMap.get(tag) + ", Compliance: " + complianceMap.get(tag);
                if(Data2024().contains(intuneMap.get(tag))) {
                    System.out.println(quote + ".  This is class of 2024 computer.");
                }
                else if(Data2025().contains(intuneMap.get(tag))) {
                    System.out.println(quote + ".  This is class of 2025 computer.");
                }
                else if(Data2026().contains(intuneMap.get(tag))) {
                    System.out.println(quote + ".  This is class of 2026 computer.");
                }
                else if(Data2027().contains(intuneMap.get(tag))) {               
                    System.out.println(quote + ".  This is class of 2027 computer.");
                }
                else {
                        System.out.println(quote + ".  This is a deployment/teacher computer.");                       
                }
                System.out.println(" ");
            }
        }
      //go through all of the data in intune and check to see if each one is related to the recycle spreadsheet
        for(String serial: readIntuneSerial(serialEmail, serialComp)) {
            if(readDataSerial().contains(serial)) {
                String quote = "NCHS" + serial + ", Email: " + serialEmail.get(serial) + ", Compliance: " + serialComp.get(serial);
                if(Data2024().contains(serialEmail.get(serial))) {
                    System.out.println(quote + ".  This is class of 2024 computer.");
                }
                else if(Data2025().contains(serialEmail.get(serial))) {
                    System.out.println(quote + ".  This is class of 2025 computer.");
                }
                else if(Data2026().contains(serialEmail.get(serial))) {
                    System.out.println(quote + ".  This is class of 2026 computer.");
                }
                else if(Data2027().contains(serialEmail.get(serial))) {               
                    System.out.println(quote + ".  This is class of 2027 computer.");
                }
                else {
                        System.out.println(quote + ".  This is a deployment/teacher computer.");                       
                }
                System.out.println(" ");
            }
        }
    }

    public static ArrayList<String> readIntuneData(Map<String, String> intuneMap, Map<String, String> complianceMap) {
        ArrayList<String> assetTags = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/IntuneDeviceData.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                String fullAsset = lineData.get(1);
                String[] assetTag = fullAsset.split("NCHS");
                if(!(assetTag.length <= 1)) {
                    assetTags.add(assetTag[1]);
                    intuneMap.put(assetTag[1], lineData.get(5));
                    complianceMap.put(assetTag[1], lineData.get(3));
                }
                data.add(lineData);
                line = br.readLine();
            }
            assetTags.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }

        return assetTags;
    }

    public static ArrayList<String> readRecylceData() {
        ArrayList<String> assetTags = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/20232024Recycle List.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                String assetTag = lineData.get(0);
                assetTags.add(assetTag);
                data.add(lineData);
                line = br.readLine();
            }
            assetTags.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return assetTags;
    }

    public static ArrayList<String> readIntuneSerial(Map<String, String> serialEmail,Map<String, String> serialComp ) {
        ArrayList<String> serialNumbers = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/IntuneDeviceData.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                if(lineData.size() > 9) {
                    serialNumbers.add(lineData.get(10));
                    serialEmail.put(lineData.get(10), lineData.get(5));
                    serialComp.put(lineData.get(10), lineData.get(3));
                }
                data.add(lineData);
                line = br.readLine();
            }
            serialNumbers.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return serialNumbers;
    }

    
    public static ArrayList<String> readDataSerial() {
        ArrayList<String> serialNumbers = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/20232024Recycle List.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));

                if(lineData.get(0).length() > 5) {
                    serialNumbers.add(lineData.get(0));
                }

                data.add(lineData);
                line = br.readLine();
            }
            serialNumbers.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return serialNumbers;
    }

    //read in all of the class data
    public static ArrayList<String> Data2024() {
        ArrayList<String> assetTags = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/Class2024.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                String email = lineData.get(8);
                assetTags.add(email);
                data.add(lineData);
                line = br.readLine();
            }
            assetTags.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return assetTags;
    }
    public static ArrayList<String> Data2025() {
        ArrayList<String> assetTags = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/Class2025.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                String email = lineData.get(8);
                assetTags.add(email);
                data.add(lineData);
                line = br.readLine();
            }
            assetTags.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return assetTags;
    }
    public static ArrayList<String> Data2026() {
        ArrayList<String> assetTags = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/Class2026.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                String email = lineData.get(8);
                assetTags.add(email);
                data.add(lineData);
                line = br.readLine();
            }
            assetTags.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return assetTags;
    }
    public static ArrayList<String> Data2027() {
        ArrayList<String> assetTags = new ArrayList<>();
        try {
            //create variables 
            List<List<String> > data = new ArrayList<>();
            String file = "/Users/aohara/Desktop/GitHub/RecycleFileOrganizer/Spreadsheets/Class2027.csv";
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            //use buffered reader to read in all data
            String line = br.readLine();
            while(line != null) {
                String[] lineArray = line.split(",");
                List<String> lineData = new ArrayList<>(Arrays.asList(lineArray));
                String email = lineData.get(8);
                assetTags.add(email);
                data.add(lineData);
                line = br.readLine();
            }
            assetTags.remove(0);
            br.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
        return assetTags;
    }
}
