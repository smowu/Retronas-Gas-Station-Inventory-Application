import java.util.ArrayList;

public class Cash extends Payment {
    
    private double balance; // Cash balance after payment is made
    
    // Constructor
    public Cash() {
        super();
        this.balance = 0.00;
    }
    
    // Getter
    public double getBalance() {return balance;}
    
    // Input cash payment amount
    public void makePayment(double total) {
        
        double amountPaid = 0.00;
        boolean retry = false; 
        do {
            Display creditCard = 
                new Display("Enter Cash Paid | TOTAL: " + String.format("RM %.2f",total),
                            null,null,0);
            creditCard.display();
            
            System.out.print("\n Cash Paid (RM) : ");
            try {
                amountPaid = Double.parseDouble(Display.getInput());
            } catch (NumberFormatException e) {
                amountPaid = 0.00;
            }
            setAmountPaid(amountPaid);
            retry = validateCash(total);
            
        } while (!getConfirm() && retry);
    }
    
    public boolean validateCash(double total) {
        
        boolean retry = false;
        if (getAmountPaid() < total) {
            
            boolean exit = false;
            do {
                ArrayList sel = new ArrayList(1);
                sel.add(0,"Retry");
                Display invalid = new Display("Invalid Amount!",null,sel,2);
                invalid.display();
                
                switch (Display.getInput()) {
                    case  "": 
                    case "1": retry = exit = true; 
                              break;
                    case "0": exit = true; 
                              break;
                    default : Display.invalidMessage(1); 
                              break;
                }
                
            } while (!exit);
            
        } else {
            calcBalance(total);
            setConfirm(true);
        }
        
        return retry;
    }
    
    public double calcBalance(double total) {
        return balance = getAmountPaid() - total;
    }
    
    public void dispBalance() {
        
        String showBalance = String.format("\n Balance : RM %.2f",balance);
        Display balanceDisp = 
            new Display("Successful Cash Payment",null,null,5);
        balanceDisp.setAddText(showBalance,2);
        balanceDisp.display();
        switch (Display.getInput()) {
            default: break;
        }
        
    }
    
} 