import java.util.ArrayList;

public class Sales {
    
    private ArrayList requestList;              // List of logged requests
    private ArrayList tempRequestList;          // Temporary request list
    
    private String popularPetrol;               // Most requested petrol type
    private int numOfPopularPetrolReq;          // Total amount of request of the petrol
    private String bestPetrolBySales;           // Petrol with highest collection
    private double bestPetrolSales;             // Total collection of the highest collection
    
    private double[] weeklySales;               // Total collection on weekly
    private double[] monthlySales;              // Total collection on monthly
    private double totalSales;                  // Overall sales collection
    private double totalVolumeSold;             // Total volume sold
    
    // Constructor
    public Sales() {
        requestList = FileHandler.readRequestLogs();
        clearFilter();
        setTotalVolumeSold();
        setSalesData();
        setBestPetrol();
    }
    
    // Sets the general sales data
    public void setSalesData() {
        
        weeklySales = new double[7];
        monthlySales = new double[12];
        
        for (int i = 0; i < requestList.size(); i++) {
            Request req = (Request) requestList.get(i);
            Time timestamp = (Time) req.getTimestamp();
            
            // Sets the total sales
            totalSales += req.getTotalPrice(); 
            
            // Sets the weekly sales
            for (int j = 0; j < 7; j++) {
                if (timestamp.getStrDay().equals(Time.getWeek()[j])) {
                    weeklySales[j] += req.getTotalPrice();
                }
            }
            
            // Sets the monthly sales
            for (int k = 0; k < 7; k++) {
                if (timestamp.getStrMonth().equals(Time.getMonth()[k])) {
                    monthlySales[k] += req.getTotalPrice();
                }
            }
        }
        
    }
    
    // Calculate the total petrol volume sold
    public void setTotalVolumeSold() {
        
        for (int i = 0; i < requestList.size(); i++) {  
            Request req = (Request) requestList.get(i);
            Petrol petrol = (Petrol) req.getPetrol();
            totalVolumeSold += petrol.getPetrolVolume(); 
        }
        
    }
    
    public void setBestPetrol() {
        
        popularPetrol = "No Data";
        bestPetrolBySales = "No Data";
        
        int ron95Req = 0;
        int ron97Req = 0;
        int dieselReq = 0;
        
        double ron95Sales = 0.00;
        double ron97Sales = 0.00;
        double dieselSales = 0.00;
        
        for (int i = 0; i < requestList.size(); i++) {
            
            Request req = (Request) requestList.get(i);
            Petrol petrol = (Petrol) req.getPetrol();
            
            if (petrol.getPetrolName().equals("Ron95")) {
                ron95Req++;
                ron95Sales += req.getTotalPrice();
            } else if (petrol.getPetrolName().equals("Ron97")) {
                ron97Req++;
                ron97Sales += req.getTotalPrice();
            } else if (petrol.getPetrolName().equals("Diesel")) {
                dieselReq++;
                dieselSales += req.getTotalPrice();
            }
            
            // Determine best petrol by number of request
            if (ron95Req > ron97Req && ron95Req > dieselReq) {
                popularPetrol = "Ron95";
                numOfPopularPetrolReq = ron95Req;
            } else if (ron97Req > ron95Req && ron97Req > dieselReq) {
                popularPetrol = "Ron97";
                numOfPopularPetrolReq = ron97Req;
            } else if (dieselReq > ron95Req && dieselReq > ron97Req) {
                popularPetrol = "Diesel";
                numOfPopularPetrolReq = dieselReq;
            }
            
            // Determine best petrol by number of sales
            if (ron95Sales > ron97Sales && ron95Sales > dieselSales) {
                bestPetrolBySales = "Ron95";
                bestPetrolSales = ron95Sales;
            } else if (ron97Sales > ron95Sales && ron97Sales > dieselSales) {
                bestPetrolBySales = "Ron97";
                bestPetrolSales = ron97Sales;
            } else if (dieselSales > ron95Sales && dieselSales > ron97Sales) {
                bestPetrolBySales = "Diesel";
                bestPetrolSales = dieselSales;
            }
        }
    }

    // Strings of sales summary
    public String toString() {
        
        String grandSummary = String.format(
            "\n Total Requests : %d" +
            "\n Total Sales    : RM %.2f" +
            "\n Total Volume   : %.2f Litre(s)" +
            "\n",
            requestList.size(), totalSales, totalVolumeSold
        );
        
        String popularPetrolSummary = String.format(
            "\n [Most requested petrol]" +
            "\n" + Display.minusBar(60) +
            "\n Petrol Name    : %s" +
            "\n Total Request  : %d" +
            "\n",
            popularPetrol, numOfPopularPetrolReq
        );
        
        String bestPetrolSalesSummary = String.format(
            "\n [Most popular petrol by sales collection]" +
            "\n" + Display.minusBar(60) +
            "\n Petrol Name    : %s" +
            "\n Total Sales    : RM %.2f" +
            "\n",
            bestPetrolBySales, bestPetrolSales
        );
                                              
        return 
            grandSummary + 
            Display.equalBar(60) +
            popularPetrolSummary +
            Display.equalBar(60) +
            bestPetrolSalesSummary;
    }
    
    public String weeklyToString() {
        
        String weeklyToString = "";
        for (int i = 0; i < 7; i++) {
            weeklyToString += String.format(
                "\n %-" + 10 + "s : RM %.2f", 
                Time.getWeek()[i], weeklySales[i]
            );
        }      
        return weeklyToString;
    }
    
    public String monthlyToString() {
        
        String monthlyToString = "";
        for (int i = 0; i < 12; i++) {
            monthlyToString += String.format(
                "\n %-" + 10 + "s : RM %.2f", 
                Time.getMonth()[i], monthlySales[i]
            );
        }      
        return monthlyToString;
    }
    
    public void viewWeeklySales() {
        Display dispWeeklySales = 
            new Display("Weekly Sales",weeklyToString() + "\n",null,5);
        dispWeeklySales.display();
        switch (Display.getInput()) {
            default: break;
        }
    }
    
    public void viewMonthlySales() {
        Display dispMonthlySales = 
            new Display("Monthly Sales",monthlyToString() + "\n",null,5);
        dispMonthlySales.display();
        switch (Display.getInput()) {
            default: break;
        }
    }
    
    // Logging the request data
    public static void storeRequestData(Request req) {
        FileHandler.writeRequestLogs(req);
    }
    
    // Displays the sales summary
    public static void dispSales() {
        boolean exit = false;
        do {
            Sales sales = new Sales();
            ArrayList sel = new ArrayList(3);
            sel.add(0,"View Weekly Sales");
            sel.add(1,"View Monthly Sales");
            sel.add(2,"View Request Logs");
            Display dispSales = new Display("Sales Summary",sales.toString(),sel,1);
            dispSales.display();
            
            switch (Display.getInput()) {
                case  "": break;
                case "1": sales.viewWeeklySales();
                          break;
                case "2": sales.viewMonthlySales();
                          break;
                case "3": sales.viewRequestLogs();
                          break;
                case "0": exit = true;
                          break;
                default : Display.invalidMessage(1); 
                          break;
            }
            
        } while (!exit);
    }

    // Displays logged request data with its filters
    public void viewRequestLogs() {

        boolean exit = false;
        Filter filt = new Filter();
        
        do {
            ArrayList requestSel = new ArrayList(4096);
            Display requestLogsMenuDisp = null;
            
            if (tempRequestList.size() != 0) {
            
                for (int i = 0; i < tempRequestList.size(); i++) {
                    Request req = (Request) tempRequestList.get(i);
                    Petrol petrol = (Petrol) req.getPetrol();
                    String requestStr = 
                        String.format(
                            "%s | Pump  %d | %"+6+"s | %"+6+".2fL | RM %"+6+".2f", 
                            req.getRequestID(), req.getPumpNo(), 
                            petrol.getPetrolName(), petrol.getPetrolVolume(), 
                            req.getTotalPrice());
                                      
                    requestSel.add(i,requestStr);
                }
                
                String strSortSel = Display.equalBar(60) +
                    "\n  [R] Search by Request ID " +
                    "\n  [F] Filter by.." + 
                    "\n  [S] Sort by.." +
                    "\n  [C] Clear Filter(s)";
                
                requestLogsMenuDisp = 
                    new Display("Request Logs",null,requestSel,1);
                
                requestLogsMenuDisp.setAddText(strSortSel,2);
                    
            } else {
                requestLogsMenuDisp = 
                    new Display("Request Logs","\n Log is empty!\n",null,1);
            }
            
            String addTextStr = 
                    "\n Filtered by : " + filt.getFilterBy() + 
                    "\n Sorted by   : " + filt.getSortBy() +
                    "\n Order by    : " + filt.getOrderBy() +
                    "\n" + Display.minusBar(60);
            
            requestLogsMenuDisp.setAddText(addTextStr +
                "\n      REQUEST ID   | PUMP NO |  TYPE  | VOLUME  |  SALES ",1);
            requestLogsMenuDisp.display();
            
            String selectStr = Display.getInput();
            int select = 0;
            
            if (selectStr.equalsIgnoreCase("R")) {
                select = -2;
            } else if (selectStr.equalsIgnoreCase("F")) {
                select = -3;
            } else if (selectStr.equalsIgnoreCase("S")) {
                select = -4;
            } else if (selectStr.equalsIgnoreCase("C")) {
                select = -5;
            } else {
                try {
                    select = Integer.parseInt(selectStr);
                    if (select < 0 || select > requestSel.size())
                        throw new Exception();
                } catch (Exception e) {
                    select = -1;
                }
            }

            switch (select) {
                case  -5: clearFilter();
                          filt = new Filter();
                          break;
                case  -4: tempRequestList = filt.sortRequest(tempRequestList); 
                          break;
                case  -3: clearFilter();
                          filt.setSortBy("None");
                          filt.setOrderBy("Ascending");
                          tempRequestList = filt.filterRequest(tempRequestList); 
                          break;
                case  -2: searchRequest();
                          break;
                case  -1: Display.invalidMessage(1); 
                          break;
                case   0: exit = true; 
                          break;
                default : Request req = (Request) tempRequestList.get(select-1);
                          viewSelectedRequest(req);
                          break;
            }
            
        } while (!exit);
    }
    
    // Displays the detailed info on the request 
    public void viewSelectedRequest(Request req) {
        
        Display requestDisp = 
            new Display("Request ID: " + req.getRequestID(),
                        req.toString() + "\n",null,5);
        requestDisp.display();
        switch (Display.getInput()) {
            default: break;
        }
        
    }

    // Search a request data by the Request ID
    public void searchRequest() {
        
        String requestID = "";
        boolean reqFound = false; 

        Display searchIDDisp = new Display("Search by Request ID",null,null,0);
        searchIDDisp.display();
        
        System.out.print("\n Request ID : ");
        requestID = Display.getInput();
        requestID = requestID.replaceAll("\\s+","");
        
        for (int i = 0; i < requestList.size(); i++) {
            Request req = (Request) requestList.get(i);
            if (req.getRequestID().equals(requestID)) {
                reqFound = true;
                viewSelectedRequest(req);
            }
        }
        
        if (!reqFound) {  
            Display.message("Request ID \"" + requestID + "\" is not found!",3);
        }

    }
    
    // Resets the list to default
    public void clearFilter() {
        tempRequestList = FileHandler.readRequestLogs();
    }

} 