import java.util.ArrayList;

public class Request {

    private String requestID;                           // 12-digit String format
    private Time timestamp;                             // Time of request creation
    private int pumpNo;                                 // Petrol dispenser number
    private Petrol petrol;                              // Petrol data
    private double totalPrice;                          // Price for the request
    private Payment payment;                            // Payment details
    
    private boolean cancel;                             // Request cancellation
    
    // Constructors
    public Request() {
        
        requestID = generateRequestID();
        pumpNo = 0;
        petrol = new Petrol();
        totalPrice = 0.00;
        payment = null;
        cancel = false;
        
    }
    
    public Request(String id, Time time, int no, Petrol petrol, double total) {
        
        this.requestID = id;
        this.timestamp = time;
        this.pumpNo = no;
        this.petrol = petrol;
        this.totalPrice = total;
        this.payment = null;
        
    }
    
    // Setters
    public void setRequestID(String id) {this.requestID = id;}
    public void setTimestamp(Time time) {this.timestamp = time;}
    public void setPumpNo(int no) {this.pumpNo = no;}
    public void setPetrolType(Petrol petrol) {this.petrol = petrol;}
    public void setTotalPrice(double total) {this.totalPrice = total;}
    public void setPayment(Payment payment) {this.payment = payment;}
    
    // Getters
    public String getRequestID() {return requestID;}
    public Time getTimestamp() {return timestamp;}
    public int getPumpNo() {return pumpNo;}
    public Petrol getPetrol() {return petrol;}
    public double getTotalPrice() {return totalPrice;}
    public Payment getPayment() {return payment;}
    
    public String toString() {
    
        return String.format(
            "\n Request ID      : %s" +
            "\n Date            : %s %s" +
            "\n Pump No.        : %d" +
            "\n%s" +
            "%s" +      // Petrol toString()
            "\n%s" +
            "\n Total Price     : RM %.2f",
            requestID, timestamp.getStrDay(), timestamp.toString(), pumpNo, 
            Display.minusBar(60), petrol.toString(), Display.equalBar(60), totalPrice
        );
    
    }
    
    // To be printed on the receipt
    public String receiptToString() {
        
        return String.format(
            "\n  Pump No.        : %d" +
            "%s",
            pumpNo, petrol.receiptToString()
        );
        
    }
    
    // To be logged in a text file as a String
    public String logToString() {
    
        return String.format(
            "%s$" + 
            "%s$" +
            "%d$" +
            "%s$" +
            "%.2f$" +
            "%.2f$",
            requestID, timestamp.toString(), pumpNo, petrol.getPetrolName(),
            petrol.getPricePerLitre(), totalPrice
        );
        
    }
    
    // Generates requestID based on the time of the request
    public String generateRequestID() {
        
        timestamp = new Time();
        String id = 
            timestamp.toString().substring(8,10) + 
            timestamp.toString().substring(3,5) + 
            timestamp.toString().substring(0,2) + 
            timestamp.toString().substring(11,13) +
            timestamp.toString().substring(14,16) + 
            timestamp.toString().substring(17,19);
            
        return id;
    }
    
    // Beginning of the request menu & displays pump selections
    public void startRequest() {
        
        boolean exit = false;
        do {
            ArrayList pumpSel = new ArrayList(16); 

            for (int i = 0; i < 8; i++) {
                String pumpStr = "Pump " + (i+1);
                pumpSel.add(i,pumpStr);
            }
            
            Display pumpSelectionMenuDisp = 
                new Display("Select Pump Station",null,pumpSel,2);
            pumpSelectionMenuDisp.display();
            
            int select = Display.getIntInput();
            if (select < 0 || select > pumpSel.size()) 
                select = -1;

            switch (select) {
                case  -1: Display.invalidMessage(1); 
                          break;
                case   0: exit = cancelRequest(); 
                          break;
                default : pumpNo = select;
                          selectFuelType();
                          break;
            }
            
        } while (!exit && !cancel);
        
    }
    
    public boolean cancelRequest() {
        
        boolean exit = false;
        do {
            ArrayList selList = new ArrayList(1);
            selList.add(0,"Confirm");
            Display cancelRequestDisp = new Display("Cancel Request?",null,selList,1);
            cancelRequestDisp.display();
            
            switch (Display.getInput()) {
                case  "": 
                case "1": exit = cancel = true;
                          break;
                case "0": exit = true; 
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
        } while (!exit);
        
        return cancel;
    }
    
    public void selectFuelType() {
        
        boolean exit = false;
        do {
            ArrayList petrolList = FileHandler.readPetrolList();    
            ArrayList petrolSel = new ArrayList(8);                 
                                                                    
            for (int i = 0; i < petrolList.size(); i++) {
                Petrol p = (Petrol) petrolList.get(i);
                String petrolStr = String.format(
                    "%-"+8+"s (RM%.2f/Litre)", 
                    p.getPetrolName(), p.getPricePerLitre()
                );
                petrolSel.add(i,petrolStr);
            }
            
            Display petrolSelectionMenuDisp = 
                new Display("Select Petrol Type","\n Pump Station " + pumpNo + "\n",
                            petrolSel,12);
            petrolSelectionMenuDisp.display();
            
            String strSelect = Display.getInput();
            int select = 0;
            // Determining valid input
            if (strSelect.equalsIgnoreCase("C")) {
                select = -2;
            } else {
                try {                                               
                    select = Integer.parseInt(strSelect);           
                    if (select < 0 || select > petrolSel.size()) 
                        throw new Exception();
                } catch (Exception e) {
                    select = -1;
                }
            }

            switch (select) {
                case  -2: exit = cancelRequest(); 
                          break;
                case  -1: Display.invalidMessage(1); 
                          break;
                case   0: exit = true; 
                          break;
                default : petrol = (Petrol) petrolList.get(select-1);
                          enterPaymentAmount();
                          break;
            }
            
        } while (!exit && !cancel);
        
    }
    
    public void enterPaymentAmount() {
        
        boolean exit = false;
        do {
            String strSummary =
                "\n Pump No     : " + pumpNo + 
                "\n Petrol Type : " + petrol.getPetrolName() + "\n";
            
            Display enterPaymentAmountDisp = 
                new Display("Enter Payment Amount",strSummary,null,12);
            enterPaymentAmountDisp.display();
            
            System.out.print("\n Enter Payment Amount (RM) : ");
            
            String strInput = Display.getInput();
            double paymentAmount = 0.00;
            int intInput = 1;
            
            if (strInput.equalsIgnoreCase("C")) {
                intInput = -2;
            } else {
                try {
                    paymentAmount = Double.parseDouble(strInput);
                    if (paymentAmount < 0) { 
                        throw new Exception();
                    } else if (paymentAmount == 0) {
                        intInput = 0;  
                    }
                } catch (Exception e) {
                    intInput = -1;
                }
            }

            switch (intInput) {
                case  -2: exit = cancelRequest(); 
                          break;
                case  -1: Display.invalidMessage(1); 
                          break;
                case   0: exit = true; 
                          break;
                default : totalPrice = paymentAmount;
                          reviewRequest();
                          break;
            }
            
        } while (!exit && !cancel);
    
    }
    
    // A brief summary of the request
    public void reviewRequest() {
    
        boolean exit = false;
        do {
            boolean validAmount = false;
            // Minimum petrol volume required
            if (petrol.calcPetrolVolume(totalPrice) >= 2.00) {      
                validAmount = true;                                 
            }

            ArrayList selList = new ArrayList(2);
            selList.add(0,"Back / Edit Payment Amount");
            selList.add(1,"Confirm");
            Display reviewRequestDisp = 
                new Display("Review Request",toString() + "\n",selList,2);
            reviewRequestDisp.display();
            
            switch (Display.getInput()) {
                case  "": break;
                case "1": exit = true;
                          break;
                case "2": if (validAmount) {
                              choosePayment(totalPrice);
                              // Checks payment confirmation
                              exit = cancel = payment.getConfirm();
                              if (exit) {                           
                                  confirmedRequest();
                              } else {
                                  Display.message("Payment Canceled.",3);
                              }
                          } else {
                              Display.message(
                                  "The minimum petrol amount required is " + 
                                  "2.00 Litre(s)!",3);
                          }
                          break;
                case "0": exit = cancel = cancelRequest(); 
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
        } while (!exit && !cancel);
    
    }
    
    // Choose payment method
    public void choosePayment(double total) {

        boolean exit = false;
        do {
            String dispAmountToPay = String.format("\n TOTAL : RM %.2f\n",total);
            ArrayList sel = new ArrayList(2);
            sel.add(0,"Credit Card");
            sel.add(1,"Cash");
            Display makePayment = 
                new Display("Choose Payment Method",dispAmountToPay,sel,1);
            makePayment.display();
            
            switch (Display.getInput()) {
                case  "": break;
                case "1": CreditCard card = new CreditCard();
                          card.makePayment(total);
                          payment = card;
                          break;
                case "2": Cash cash = new Cash();
                          cash.makePayment(total);
                          payment = cash;
                          break;
                case "0": exit = true; 
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
            if (payment.getConfirm()) {
                Display.message("Successful Payment!",2);
                if (payment instanceof Cash) {
                    Cash cash = (Cash) payment;
                    cash.dispBalance();
                }
                exit = true;
            }  
            
        } while (!exit);
        
    }
    
    public void confirmedRequest() {
        
        Sales.storeRequestData(this);
        Receipt.confirmPrintReceipt(this);
        System.out.println("\fYou may start fueling now.");
        System.out.println("\nInput any key to return to Main Menu.");
        
        switch (Display.getInput()) {
            default: break;
        }
        
    }

} 