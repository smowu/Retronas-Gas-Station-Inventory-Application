import java.util.Scanner;
import java.util.concurrent.*;
import java.util.ArrayList;

public class Display {
    
    private String header;                      // Header text/title
    private String content;                     // Content to be displayed
    private ArrayList selection;                // ArrayList of input selections
    private int exit;                           // Exit types/text
    private String[] addText;                   // Additional text/content
    
    // Constructor
    public Display(String header, String cont, ArrayList selection, int exit) {
        this.header = header;
        this.content = cont;
        this.selection = selection;
        this.addText = new String[3];
        this.exit = exit;
    }
    
    // Setters
    public void setHeader(String header) {this.header = header;}
    public void setContent(String cont) {this.content = cont;}
    public void setSelection(ArrayList selection) {this.selection = selection;}
    public void setAddText(String text, int type) {this.addText[type] = text;}
    
    // Header title
    public void dispHeader() {
        System.out.println(" [" + header + "]");
    }
    
    // Displays an array of selections
    public void dispSelections() {
        for (int i = 0; i < selection.size(); i++) {
            String button = "[" + (i + 1) + "]";
            System.out.print(
                String.format("\n " + "%" + 4 + "s %s", button, selection.get(i)));
        }
        System.out.print('\n');
    }
    
    // Displays the user interface
    public void display() {
        
        System.out.print('\f');
        if (header != null) {                    
            dispHeader();                       // Header  
        }
        
        if (content != null) {
            System.out.print(hashBar(60));      
            System.out.print(content);          // Main content
        }
        
        if (selection != null) {
            
            System.out.print(hashBar(60));
            if (addText[1] != null) {
                System.out.println(addText[1]); // Additional Text 1
                System.out.print(equalBar(60));
            }
            dispSelections();                   // Array of selections
            
        } else {
            System.out.print(hashBar(60));
        }
        
        if (addText[2] != null) {
            System.out.println(addText[2]);     // Additional Text 2
        }
            
        if (exit != 0) {
            
            String text = "";
            if (exit == 1 || exit == 12) {
                text = "BACK";
            } else if (exit == 2) {
                text = "CANCEL";
            } else if (exit == 3) {
                text = "LOG OUT";               // Main selection(s)
            } else if (exit == 4) {
                text = "EXIT PROGRAM";
            } else if (exit == 5) {
                text = "OK";
            }
            
            if (exit != 5 && selection != null) {
                System.out.print(minusBar(60));
            }
            System.out.println("\n  [0] " + text);
            
            if (exit == 12) {
                System.out.println("  [C] CANCEL");
            }
        }
    }
    
    // Display/print a message with a timer
    public static void message(String message, int timer) {
        System.out.print("\f" + message);
        setTimer(timer);
    }
    
    // Displaying an invalid input error
    public static void invalidMessage(int messageType) {
        if (messageType == 1) {
            System.out.print("\fInvalid selection!");
        } else {
            System.out.print("\fInvalid input!");
        }
        setTimer(1);
    }
    
    // Program termination message
    public static void endProgramMessage() {
        System.out.print('\f');
        message("The program has been terminated.",3);
        System.out.print('\f');
    }
    
    // Simulating a fake loading time
    public static void fakeLoadTime(int min, int max) {
        int randomNum = ThreadLocalRandom.current().nextInt(min,max);
        for (int i = 0; i < randomNum; i++) {
            System.out.print(".");
            setTimer(1);
        }
    }
    
    public static void setTimer(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (Exception e) {}
    }

    // Pre-defined UI character bars
    public static String hashBar(int i) {
        return charBar('#',i);
    }
    public static String equalBar(int i) {
        return charBar('=',i);
    }
    public static String minusBar(int i) {
        return charBar('-',i);
    }
    
    public static String charBar(char c, int i) {
        String bar = "";
        for (int j = 0; j < i; j++) { 
            bar += c;
        }
        return bar;
    }
    
    // Gets user inputs
    public static String getInput() {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        return input;
    }
    public static int getIntInput() {
        int input = -1;
        Scanner in = new Scanner(System.in);
        try {
            input = in.nextInt();
        } catch (Exception e) {}
        return input;
    }
    
    // Checks if the string input is a Long type
    public static boolean isLongNumeric(String str) { 
        try {  
            Long.parseLong(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
} 