import java.util.ArrayList;

public class Filter {

    private String filterBy;    // Filter type - Date, Pump No, Petrol Type
    private String sortBy;      // Sorting type - Date, Pump No, Petrol Volume, Sales Price
    private String orderBy;     // Order type - Ascending or Decending
    private char order;         // Order type (char)
    
    // Constructor
    public Filter() {
        
        filterBy = "None";
        sortBy = "None";
        orderBy = "None";
        order = 'a';
        
    }
    
    // Setters
    public void setFilterBy(String filterBy) {this.filterBy = filterBy;}
    public void setSortBy(String sortBy) {this.sortBy = sortBy;}
    public void setOrderBy(String orderBy) {this.orderBy = orderBy;}
    public void setOrder(char order) {this.order = order;}
    
    // Getters
    public String getFilterBy() {return filterBy;}
    public String getSortBy() {return sortBy;}
    public String getOrderBy() {return orderBy;}
    public char getOrder() {return order;}

    // Filter type selections
    public ArrayList filterRequest(ArrayList tempList) {
        
        boolean exit = false;
        do {
            String input = "None";
            
            ArrayList sel = new ArrayList(3);
            sel.add(0,"Date");
            sel.add(1,"Pump No.");
            sel.add(2,"Petrol Type");
            Display filterDisp = new Display("Filter by..",null,sel,1);
            filterDisp.display();
            
            switch (Display.getInput()) {
                case  "": break;
                case "1": filterBy = "Date";
                          input = filterDate();
                          break;
                case "2": filterBy = "Pump No";
                          input = filterPumpNo();
                          break;
                case "3": filterBy = "Petrol Type";
                          input = filterPetrolType();
                          break;
                case "0": exit = true;
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
            if (!exit && !filterBy.equals("None") && !input.equals("None")) {
                tempList = filterBy(tempList,input);
                exit = true;
            }
            
        } while (!exit);
        
        return tempList;
    }
    
    // Filter process
    public ArrayList filterBy(ArrayList tempList, String input) {

        ArrayList tempTempList = new ArrayList(tempList);
        
        for (int i = 0; i < tempList.size(); i++) {
            
            Request req = (Request) tempList.get(i);
            
            if (filterBy.equals("Date")) {
                if (!req.getTimestamp().getDDMMYY().equals(input)) {
                    tempList.remove(i);
                    i--;
                }
            } else if (filterBy.equals("Pump No")) {
                int in = Integer.parseInt(input);
                if (req.getPumpNo() != in) {
                    tempList.remove(i);
                    i--;
                }
            } else if (filterBy.equals("Petrol Type")) {
                Petrol p = (Petrol) req.getPetrol();
                if (!p.getPetrolName().equals(input)) {
                    tempList.remove(i);
                    i--;
                }
            }
            
        }
        
        // The ArrayList will return to default if there is no result
        if (tempList.isEmpty()) {
            if (filterBy.equals("Date")) {
                Display.message("There was no request on " + input + ".",4);
            } else if (filterBy.equals("Pump No")) {
                Display.message("There was no request on Pump No " + input + ".",4);
            } else if (filterBy.equals("Petrol Type")) {
                Display.message("There was no " + input + " request.",4);
            } else {
                Display.message("List is empty.",4);
            }

            filterBy = "None";
            orderBy = "None";
            tempList = new ArrayList(tempTempList);
        }

        return tempList;
    }
    
    // Asks user for the date input to be filtered
    public String filterDate() {
        
        String requestDate = "None";
        
        boolean exit = false;
        do {
            requestDate = "None";
            boolean reqFound = false; 
    
            Display filterDateDisp = 
                new Display("Filter by Date","\n  [0] BACK \n",null,0);
            filterDateDisp.display();
            
            System.out.print("\n Date (dd/MM/yyyy) : ");
            requestDate = Display.getInput();
            requestDate = requestDate.replaceAll("\\s+","");
            
            int select = 0;
            if (!requestDate.equals("0")) {
               select = Time.isDateFormat(requestDate);
            }
            
            switch (select) {
                case  -1: Display.invalidMessage(1); 
                          break;
                case   0: requestDate = "None";
                          exit = true; 
                          break;
                default : exit = true; 
                          break;
            }
            
        } while (!exit);
        
        return requestDate;
    }
    
    // Selects the pump dispenser number to be filtered
    public String filterPumpNo() {
        
        String pumpNo = "None";
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
            if (select < 0 || select > pumpSel.size()) { 
                select = -1;
            }

            switch (select) {
                case  -1: Display.invalidMessage(1); 
                          break;
                case   0: exit = true; 
                          break;
                default : pumpNo = select + "";
                          exit = true;
                          break;
            }
            
        } while (!exit);
        
        return pumpNo;
    }
    
    // Selects the petrol type to be filtered
    public String filterPetrolType() {
        
        String petrolType = "None";
        
        boolean exit = false;
        do {
            ArrayList petrolList = FileHandler.readPetrolList();
            ArrayList petrolSel = new ArrayList(8);

            for (int i = 0; i < petrolList.size(); i++) {
                Petrol p = (Petrol) petrolList.get(i);
                String petrolStr = p.getPetrolName();
                petrolSel.add(i,petrolStr);
            }
            
            Display petrolSelectionMenuDisp = 
                new Display("Select Petrol Type",null,petrolSel,1);
            petrolSelectionMenuDisp.display();
            
            int select = Display.getIntInput();
            if (select < 0 || select > petrolSel.size()) 
                select = -1;

            switch (select) {
                case  -1: Display.invalidMessage(1); 
                          break;
                case   0: exit = true; 
                          break;
                default : Petrol petrol = (Petrol) petrolList.get(select-1);
                          petrolType = petrol.getPetrolName();
                          exit = true;
                          break;
            }
            
        } while (!exit);

        return petrolType;
    }
    
    // Sort type selection
    public ArrayList sortRequest(ArrayList tempList) {
        
        boolean exit = false;
        do {
            ArrayList sel = new ArrayList(4);
            sel.add(0,"Date");
            sel.add(1,"Pump No.");
            sel.add(2,"Petrol Volume");
            sel.add(3,"Sales Price");
            Display sortDisp = new Display("Sort by..",null,sel,1);
            sortDisp.display();
            
            switch (Display.getInput()) {
                case  "": break;
                case "1": sortBy = "Date";
                          break;
                case "2": sortBy = "Pump No";
                          break;
                case "3": sortBy = "Petrol Volume";
                          break;
                case "4": sortBy = "Sales Price";
                          break;
                case "0": exit = true;
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
            if (!exit && !sortBy.equals("None")) {
                orderBy();
                tempList = sortBy(tempList);
                exit = true;
            }
            
        } while (!exit);
        
        return tempList;
    }
    
    // Sorting process
    public ArrayList sortBy(ArrayList tempList) {

        for (int i = 0; i < tempList.size(); i++) {
            
            for (int j = 1; j < tempList.size()-i; j++) {
                Request req1 = (Request) tempList.get(j);
                Request req2 = (Request) tempList.get(j-1);
                
                // Determines the sorting logic
                boolean sort = false;
                if (sortBy.equals("Date")) {
                    
                    if (order == 'a') {
                        sort = 
                            (req2.getTimestamp().compareTo(req1.getTimestamp()) > 0);
                    } else if (order == 'd') {
                        sort = 
                            (req2.getTimestamp().compareTo(req1.getTimestamp()) < 0);
                    }
                    
                } else if (sortBy.equals("Pump No")) {
                    
                    if (order == 'a') {
                        sort = (req2.getPumpNo() > req1.getPumpNo());
                    } else if (order == 'd') {
                        sort = (req2.getPumpNo() < req1.getPumpNo());
                    }
                    
                } else if (sortBy.equals("Petrol Volume")) {
                    
                    Petrol p1 = (Petrol) req1.getPetrol();
                    Petrol p2 = (Petrol) req2.getPetrol();
                    if (order == 'a') {
                        sort = (p2.getPetrolVolume() > p1.getPetrolVolume());
                    } else if (order == 'd') {
                        sort = (p2.getPetrolVolume() < p1.getPetrolVolume());
                    }
                    
                } else if (sortBy.equals("Sales Price")) {
                    
                    if (order == 'a') {
                        sort = (req2.getTotalPrice() > req1.getTotalPrice());
                    } else if (order == 'd') {
                        sort = (req2.getTotalPrice() < req1.getTotalPrice());
                    }
                    
                }
                
                if (sort) {
                    Request reqTemp = req1;
                    tempList.set(j,req2);
                    tempList.set(j-1,reqTemp);
                }
            }
        }
        
        return tempList;
    }

    // Order of the ArrayList selection
    public char orderBy() {
        
        boolean exit = false;
        do {
            ArrayList sel = new ArrayList(2);
            sel.add(0,"Ascending");
            sel.add(1,"Descending");
            Display orderByDisp = new Display("Order by..",null,sel,1);
            orderByDisp.display();
            
            switch (Display.getInput()) {
                case  "": break;
                case "1": order = 'a';
                          orderBy = "Ascending";
                          exit = true;
                          break;
                case "2": order = 'd';
                          orderBy = "Descending";
                          exit = true;
                          break;
                case "0": exit = true;
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }

        } while (!exit);
        
        return order;
    }
    
} 