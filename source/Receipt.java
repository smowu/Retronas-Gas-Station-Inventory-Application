import java.util.ArrayList;

public class Receipt {
    
    public static void confirmPrintReceipt(Request req) {
        
        boolean exit = false;
        do {
            ArrayList sel = new ArrayList(2);
            sel.add(0,"Yes");
            sel.add(1,"No");
            Display printReceipt = 
                new Display("Do you want to print receipt?",null,sel,0);
            printReceipt.display();
            
            switch (Display.getInput()) {
                case  "":
                case "1": printReceipt(req);
                          exit = true;
                          break;
                case "2": exit = true;
                          break;
                default : Display.invalidMessage(1);
                          break;
            } 
            
        } while (!exit);
        
    }
    
    public static void printReceipt(Request req) {
        
        System.out.print("\fPrinting Receipt");
        Display.fakeLoadTime(3,5);
        
        Display dispReceipt = new Display("Receipt",null,null,5);
        dispReceipt.setAddText(toString(req),2);
        dispReceipt.display();
        
        switch (Display.getInput()) {
            default: break;
        }
        
    }
    
    public static String toString(Request req) {
        
        String strPayment = "";
        Payment payment = req.getPayment();
        if (payment instanceof Cash) {
            
            Cash cash = (Cash) payment;
            strPayment = String.format(
                "\n  CASH            :" + "%" + 22 + ".2f" +
                "\n  Change          :" + "%" + 22 + ".2f",
                cash.getAmountPaid(), cash.getBalance()
            );
            
        } else if (payment instanceof CreditCard) {
            
            CreditCard card = (CreditCard) payment;
            strPayment = String.format(
                "\n  CREDIT          :" + "%" + 22 + ".2f",
                card.getAmountPaid()
            );
            
        }
        
        return String.format(
            "\n" +
            "\n            RETRONAS GAS BERHAD           " +
            "\n         COMPANY REGIS: XXXXXXX-X         " +
            "\n     KM8, TAPAH ROAD, KAMPUNG BARU 5,     " +
            "\n     35400 TAPAH ROAD, PERAK, MALAYSIA    " +
            "\n             TEL : XX-XXXXXXXX            " +
            "\n" +
            "\n                 -INVOICE-                " +
            "\n" +
            "\n  ReceiptID#      : " + req.getRequestID() +
            "\n  Date            : " + req.getTimestamp() +
            "\n -----------------------------------------" +
            "%s" +
            "\n -----------------------------------------" +
            "\n                      TOTAL    RM%" + 8 + ".2f" +
            "\n -----------------------------------------" +
            "%s" +
            "\n" +
            "\n           *** CUSTOMER COPY ***          " +
            "\n   Please keep this receipt as proof of   " +
            "\n     payment and for future references.   " +
            "\n *****************************************" +
            "\n              HAPPY FUELING :)            " +
            "\n      THANK YOU AND PLEASE COME AGAIN!    " +
            "\n",
            req.receiptToString(), req.getTotalPrice(), strPayment
        );
    }
} 