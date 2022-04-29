import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        boolean exit = false;
        do {
            Time currentTime = new Time();           
            ArrayList selList = new ArrayList(3);
            selList.add(0,"Select Pump Station");
            selList.add(1,"Staff Login");
            selList.add(2,"About Program");
            Display mainMenuDisp = new Display(
                currentTime.getStrDay() + " " + currentTime.toString(), 
                menuText(),selList,4
            );
            mainMenuDisp.display();
            
            switch (Display.getInput()) {
                case  "": break;
                case "1": Request req = new Request();
                          req.startRequest();
                          break;
                case "2": Log log = new Log();
                          log.login();
                          if (log.isUserFound()) {
                              Employee emp = log.getEmployee();
                              emp.dispEmployeeMenu();
                          }
                          break;
                case "3": About.dispAbout(); 
                          break;
                case "0": Display.endProgramMessage();
                          exit = true; 
                          break;
                default : Display.invalidMessage(1);
                          break;
            }   
            
        } while (!exit);
        
    }
    
    // Main Menu Title
    public static String menuText() {
        return 
        "\n WELCOME TO RETRONAS GAS STATION" +
        "\n";
    } 
} 