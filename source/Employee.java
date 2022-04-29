public abstract class Employee {
    
    private String idNumber;                    // M0001, C0001, etc..
    private String password;                    
    private String name;
    private String ic;                          // 12-digit IC Number
    private String position;                    // Manager, Cashier, etc..
    private String email;                       // manager@email.com, etc..
    
    // Constructors
    public Employee() {
        
        idNumber = "No Data";
        password = "No Data";
        name = "No Data";
        ic = "No Data";
        position = "No Data";
        email = "No Data";
        
    }
    
    public Employee(String id, String pass, String n, String ic, String email) {
        
        this.idNumber = id;
        this.password = pass;
        this.name = n;
        this.ic = ic;
        setPosition();
        this.email = email;
        
    }
    
    // Setters
    public void setIdNumber(String id) {this.idNumber = id;}
    public void setPassword(String pass) {this.password = pass;}
    public void setName(String name) {this.name = name;}
    public void setIC(String ic) {this.ic = ic;}
    public void setPosition() {
        if (this instanceof Manager) {
            this.position = "Manager";
        } else {
            this.position = "No Data";
        }
    }
    public void setEmail(String email) {this.email = email;}
    
    // Getters
    public String getPosition() {return position;}
    public String getIdNumber() {return idNumber;}
    public String getPassword() {return password;}
    public String getName() {return name;}
    public String getIC() {return ic;}
    public String getEmail() {return email;}

    // Displays the summary of the employee menu
    public String dispEmployeeSummary() {
        return "\n WELCOME BACK " + name + "!" +
               "\n" + Display.equalBar(60) +
               "\n ID      : " + idNumber + 
               "\n Name    : " + name +
               "\n";
    }
    
    public String toString() {
        
        return String.format(
            "\n ID Number           : %s" +
            "\n Position            : %s" +
            "\n Name                : %s" +
            "\n IC Number           : %s" +
            "\n Email Address       : %s",
            idNumber, position, name, ic, email
        );
        
    }
    
    public abstract void dispEmployeeMenu();
    
    // Displays the details of the employee
    public void dispEmployeeProfile() {
        
        Display profileDisp = new Display("Your Profile", toString() + "\n",null,5);
        profileDisp.display();
        switch (Display.getInput()) {
            default: break;
        }
        
    }
    
} 