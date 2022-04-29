public class Petrol{

    private String petrolName;                  // Ron95, Ron97, Diesel, etc..
    private double pricePerLitre;               // Petrol prices per litre in RM
    private double petrolVolume;                // Petrol volume in litre(s)
    
    // Constructors
    public Petrol() {
        
        petrolName = "No Data";
        pricePerLitre = 0.00;
        petrolVolume = 0.00;
        
    }
    
    public Petrol(String petrolName, double pricePerLitre) {
        
        this.petrolName = petrolName;
        this.pricePerLitre = pricePerLitre;
        this.petrolVolume = 0;

    }
    
    // Setters
    public void setPetrolName(String name) {this.petrolName = name;}
    public void setPricePerLitre(double price) {this.pricePerLitre = price;}
    public void setPetrolVolume(double volume) {this.petrolVolume = volume;}
    
    // Getters
    public String getPetrolName() {return petrolName;}
    public double getPricePerLitre() {return pricePerLitre;}
    public double getPetrolVolume() {return petrolVolume;}
    
    public String toString() {
    
        return String.format(
            "\n Petrol Type     : %s" +
            "\n Price Per Litre : RM %.2f" +
            "\n Petrol Volume   : %.2f Litre(s)",
            petrolName, pricePerLitre, petrolVolume
        );
    
    }
    
    // To be printed on the receipt
    public String receiptToString() {
        
        return String.format(
            "\n  Petrol Type     : %s" +
            "\n  Price Per Litre : RM %.2f" +
            "\n  Petrol Volume   : %.2f Litre(s)",
            petrolName, pricePerLitre, petrolVolume
        );
        
    }
    
    public double calcPetrolVolume(double paymentAmount) {
        return petrolVolume = paymentAmount / pricePerLitre;
    }
    
} 