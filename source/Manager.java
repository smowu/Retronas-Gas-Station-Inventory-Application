import java.util.ArrayList;

public class Manager extends Employee {
    
    // Constructors
    public Manager() {super();}
    public Manager(String id, String pass, String n, String ic, String email) {
        super(id, pass, n, ic, email);
    }
    
    public String toString() {return super.toString();}
    
    public void dispEmployeeMenu() {
        
        boolean logout = false;
        do {
            ArrayList selList = new ArrayList(5);
            selList.add(0,"View Sales");
            selList.add(1,"View Profile");
            
            Display managerMenuDisp = 
                new Display("Manager Menu",dispEmployeeSummary(),selList,3);
            managerMenuDisp.display();
            
            switch (Display.getInput()) {
                case "1": Sales.dispSales();
                          break;
                case "2": dispEmployeeProfile(); 
                          break;
                case "0": logout = Log.logout(); 
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
        } while (!logout);
    }

}  