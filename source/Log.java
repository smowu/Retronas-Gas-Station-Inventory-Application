import java.util.*;
import java.io.*;
import java.util.ArrayList;

public class Log {
    
    private Employee emp;
    private boolean userFound;
    
    public Employee getEmployee() {return emp;}
    public boolean isUserFound() {return userFound;}

    public void login() {
        
        boolean retry = false;
        do {
            Scanner in = new Scanner(System.in);

            Display loginMenuDisp = 
                new Display("Log In | For Staff Demo, ID: M0001 Pass: 123456",null,null,0);
            loginMenuDisp.display();
            
            System.out.print("\n ID Number: ");
            String tempID = in.nextLine();
            System.out.print(" Password : ");
            String tempPass = in.nextLine();
            findEmployee(tempID,tempPass);

            if (userFound) {
                successfulLogin();
                retry = false;
            } else {
                retry = failedLogin();
            }
                
        } while (retry);
    }
    
    public void findEmployee(String tempID, String tempPass) {
        
        ArrayList employeeList = FileHandler.readEmployeeList();
        
        userFound = false;
        for (int i = 0; i < employeeList.size() && !userFound; i++) {
            emp = (Employee) employeeList.get(i);
            String idNumber = emp.getIdNumber();
            String password = emp.getPassword();
            if (idNumber.equals(tempID) && password.equals(tempPass))
                userFound = true;
        }
        
    }
    
    public void successfulLogin() {
        System.out.println("\fSuccessfully logged in!\n");
        System.out.print("Please wait");
        Display.fakeLoadTime(1,4);
    }
    
    // Asks the user if they want to retry login after failed attempt
    public boolean failedLogin() {
        
        boolean exit = false, retry = false;
        do {
            ArrayList selList = new ArrayList(1);
            selList.add(0,"Retry");
            Display failedLoginDisp = 
                new Display("Wrong username or password!",null,selList,1);
            failedLoginDisp.display();
            
            switch (Display.getInput()) {
                case  "": 
                case "1": exit = retry = true;
                          break;
                case "0": exit = true; 
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
        } while (!exit);
        
        return retry;
    }
    
    public static boolean logout() {
        
        boolean exit = false, logout = false;
        do {
            ArrayList selList = new ArrayList(2);
            selList.add(0,"Yes");
            selList.add(1,"No");
            Display logoutMenuDisp = 
                new Display("Are you sure you want to log out?",null,selList,0);
            logoutMenuDisp.display();
            
            switch (Display.getInput()) {
                case  "": 
                case "1": System.out.print("\fLoging out");
                          Display.fakeLoadTime(1,4);
                          exit = logout = true; 
                          break;
                case "2": exit = true; 
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
        } while (!exit);
        
        return logout;
    }
} 