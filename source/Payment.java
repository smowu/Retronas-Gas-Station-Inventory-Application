import java.util.ArrayList;

public abstract class Payment {
    
    private double amountPaid;                  // Amount paid by the customer in RM
    private boolean confirm;                    // Payment confirmation
    
    // Constructor
    public Payment() {
        
        this.amountPaid = 0.00;
        this.confirm = false;
        
    }
    
    // Setters
    public void setAmountPaid(double amountPaid) {this.amountPaid = amountPaid;}
    public void setConfirm(boolean confirm) {this.confirm = confirm;}
    
    // Getters
    public double getAmountPaid() {return amountPaid;}
    public boolean getConfirm() {return confirm;}
    
    public abstract void makePayment(double total);

} 