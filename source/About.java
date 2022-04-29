public class About {
    
    public static void dispAbout() {
        
        About about = new About();
        Display aboutScreenDisp = new Display("About", about.toString() + "\n",null,5);
        aboutScreenDisp.display();
        switch (Display.getInput()) {
            default: break;
        }
        
    }
    
    public String toString(){
        
        return  String.format(
            "\n" +
            "\n Developed by " +
            "\n " + Display.minusBar(12) +
            "\n Group A4CS1104A" +
            "\n" +
            "\n Durrani Afiq Bin Saidin               (2020769853)" +
            "\n Muhammad Khairuddin Bin Mohd Zulkifli (2020938421)" +
            "\n Muhammad Aiman Bin Bahari             (2019418168)" +
            "\n"
        );
        
    }
} 