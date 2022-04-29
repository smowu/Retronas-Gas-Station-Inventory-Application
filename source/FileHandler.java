import java.util.*;
import java.io.*;

public class FileHandler {
    
    // Filepath selector
    public static String getFilepath(int category) {
        String filepath = "";
        switch (category) {
            case  0: filepath = "EmployeeList.txt"; break;
            case  1: filepath = "PetrolList.txt"; break;
            case  2: filepath = "Logs.txt"; break;
            default: break;
        }
        return filepath;
    }
    
    // Reads the petrol list text file
    public static ArrayList readPetrolList() {
        
        ArrayList petrolList = new ArrayList(16);
        String filepath = getFilepath(1);
        
        try {
            BufferedReader readPetrol = new BufferedReader(new FileReader(filepath));

            String input;
            while ((input = readPetrol.readLine()) != null) { 
                StringTokenizer stPetrol = new StringTokenizer(input,"$");
                String petrolName = stPetrol.nextToken();
                double petrolPricePerLitre = Double.parseDouble(stPetrol.nextToken());
                petrolList.add(new Petrol(petrolName, petrolPricePerLitre));
            }
            readPetrol.close();
            
        } catch (FileNotFoundException e) {
            Display.message("Petrol file not found!"+"\nError: "+ e,2);
        } catch (IOException ioe) {
            Display.message("Failed to read petrol file!"+"\nError: "+ ioe,2);
        }
        
        return petrolList;
    }
    
    // Reads the request logs text file
    public static ArrayList readRequestLogs() {
        
        ArrayList requestList = new ArrayList(4096);
        String filepath = getFilepath(2);

        try {
            BufferedReader readLogs = new BufferedReader(new FileReader(filepath));

            String input;
            while ((input = readLogs.readLine()) != null) { 
                StringTokenizer stLog = new StringTokenizer(input,"$");
                
                String id = stLog.nextToken();
                String time = stLog.nextToken();
                int pumpNo = Integer.parseInt(stLog.nextToken());
                String petrolName = stLog.nextToken();
                double pricePerLitre = Double.parseDouble(stLog.nextToken());
                double pricePaid = Double.parseDouble(stLog.nextToken());
                
                Time timestamp = new Time(time);
                Petrol petrol = new Petrol(petrolName, pricePerLitre);
                petrol.calcPetrolVolume(pricePaid);

                Request req = new Request(id, timestamp, pumpNo, petrol, pricePaid);
                requestList.add(req);
            }
            readLogs.close();
            
        } catch (FileNotFoundException e) {
            Display.message("Logs file not found!"+"\nError: "+ e,2);
        } catch (IOException ioe) {
            Display.message("Failed to read logs file!"+"\nError: "+ ioe,2);
        }
 
        return requestList;
    }
    
    // Writes the request logs text file
    public static void writeRequestLogs(Request req) {
        
        String filepath = getFilepath(2);
        ArrayList requestList = readRequestLogs();
        
        try {
            PrintWriter writeLogs = 
                new PrintWriter(new BufferedWriter(new FileWriter(filepath)));
            
            requestList.add(req);
                
            for (int i = 0; i < requestList.size(); i++) {
                Request r = (Request) requestList.get(i);
                writeLogs.println(r.logToString());
            }
            writeLogs.close();    
            
        } catch (FileNotFoundException e) {
            Display.message("Logs file not found!"+"\nError: "+ e,2);
        } catch (IOException ioe) {
            Display.message("Failed to write logs file!"+"\nError: "+ ioe,2);
        }
        
    }
    
    // Reads the employee list text file
    public static ArrayList readEmployeeList() {
        
        ArrayList employeeList = new ArrayList(128);
        String filepath = getFilepath(0);
        try {
            BufferedReader readFile = new BufferedReader(new FileReader(filepath));
            String input;
            while ((input = readFile.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(input,"|");
                if (st.hasMoreTokens()) {
                    String idNumber = st.nextToken();
                    String password = st.nextToken();
                    String name = st.nextToken();
                    String ic = st.nextToken();
                    String email = st.nextToken();
                    
                    if (idNumber.charAt(0) == 'M') {
                        Manager m = new Manager(idNumber, password, name, ic, email);
                        employeeList.add(m);
                    }
                }   
            }
            readFile.close();
            
        } catch (FileNotFoundException e) {
            Display.message("Employee file not found!"+"\nError: "+ e,2);
        } catch (IOException ioe) {
            Display.message("Failed to read employee file!"+"\nError: "+ ioe,2);
        } 
        
        return employeeList;
    }
    
    // Filters employee list to managers only
    public static ArrayList getManagerList() {
        
        ArrayList employeeList = readEmployeeList();
        ArrayList managerList = new ArrayList(128);
        
        for (int i = 0; i < employeeList.size(); i++) {
            
            Employee emp = (Employee) employeeList.get(i);
            if (emp instanceof Manager) {
                managerList.add(emp);
            }
            
        }
        return managerList;
    }

} 