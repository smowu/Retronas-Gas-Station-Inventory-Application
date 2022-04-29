import java.util.ArrayList;

public class CreditCard extends Payment {
    
    private String cardNo;              // 16-digit card number
    private String expiryDate;          // Card expiry date (MM/yy)
    private String cvc;                 // 3-digit code
    
    // Constructor
    public CreditCard() {super();}

    // Input card information
    public void makePayment(double total) {
        
        boolean retry = false; 
        do {
            Display creditCard = 
                new Display("Enter Credit Card Information",null,null,0);
            creditCard.display();
            
            System.out.print("\n Card Number (16-Digits) : ");
            cardNo = Display.getInput();
            System.out.print(" Exipry Date     (MM/YY) : ");
            expiryDate = Display.getInput();
            System.out.print(" CVC          (3-Digits) : ");
            cvc = Display.getInput();
            
            retry = validateCard(total);
            
        } while (!getConfirm() && retry);
        
        clearCard();
    }
    
    public boolean validateCard(double total) {
        
        boolean retry = false;
        if (!isCardNo() || !isExpiryDate() || !isCVC()) {
            
            boolean exit = false;
            do {
                ArrayList sel = new ArrayList(1);
                sel.add(0,"Retry");
                Display invalidCard = new Display(
                    "Invalid Card | Wrong format input or Expired Card", 
                    null,sel,2
                );
                invalidCard.display();
                
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
            
        } else {
            setAmountPaid(total);
            setConfirm(true);
        }
        
        return retry;
    }
    
    // Validate card number format
    public boolean isCardNo() {
        if (!Display.isLongNumeric(cardNo) || cardNo.length() != 16) {
            return false;
        } else { 
            return true;
        }
    }
    
    // Checks today's date with the card expiry date
    public boolean isExpiryDate() {
        
        Time current = new Time();
        boolean valid = false;
        boolean format = 
            (expiryDate.length() == 5 && expiryDate.substring(2,3).equals("/"));
        int cardMonth = 0, cardYear = 0;
        int currentMonth = Integer.parseInt(current.getDDMMYY().substring(3,5));
        int currentYear = Integer.parseInt(current.getDDMMYY().substring(8,10));
       
        try {
            cardMonth = Integer.parseInt(expiryDate.substring(0,2));
            cardYear = Integer.parseInt(expiryDate.substring(3,5));
        } catch (Exception e) {
            valid = false;
        }
        
        format = (cardMonth > 0 && cardMonth < 13);
        
        if (currentYear <= cardYear && format) {
            if (currentYear == cardYear && currentMonth > cardMonth) {
                valid = false;
            } else { 
                valid = true;
            }
        } 
        
        return valid;
    }
    
    // Validate CVC format
    public boolean isCVC() {
        if (!Display.isLongNumeric(cvc) || cvc.length() != 3) {
            return false;
        } else { 
            return true;
        }
    }
    
    // Clears card info
    public void clearCard() {
        cardNo = "";
        expiryDate = "";
        cvc = "";
    }
    
} 