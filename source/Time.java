import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Time {
    
    private Date dateTime;
    private String strDay, strMonth;                // Monday, February, etc...
    private String HHMMSS, DDMMYY;                  // 22:04:20 (24H), 14/01/22
    
    // Constructors
    public Time() {
        dateTime = new Date();
        setTime(dateTime);
    }
    
    public Time(String time) {
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        try {
            dateTime = df.parse(time);
            setTime(dateTime);
        } catch (ParseException pe) {
            Display.message("Time formatting error!",3);
            strDay = "No Data";
            strMonth = "No Data";
            HHMMSS = "No Data";
            DDMMYY = "No Data";
        }

    }
    
    public void setTime(Date date) {
        SimpleDateFormat formatter = 
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat formatterStrDay = new SimpleDateFormat("EEEE");
        SimpleDateFormat formatterStrMonth = new SimpleDateFormat("MMMM");
        
        strDay = formatterStrDay.format(date);
        strMonth = formatterStrMonth.format(date);
        HHMMSS = formatter.format(date).substring(11,19);
        DDMMYY = formatter.format(date).substring(0,10);
    }

    // Getters
    public Date getDateTime() {return dateTime;}
    public String getHHMMSS() {return HHMMSS;}
    public String getDDMMYY() {return DDMMYY;}
    public String getStrDay() {return strDay;}
    public String getStrMonth() {return strMonth;}
    
    public static String[] getWeek() {
        String[] dayOfTheWeek = {
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", 
            "Saturday", "Sunday"
        };
        return dayOfTheWeek;
    }
    
    public static String[] getMonth() {
        String[] month = {
            "January", "February", "March", "April", "May", "June", 
            "July", "August", "September", "October", "November", "December"
        };
        return month;
    }
    
    public String toString() {
        return DDMMYY + " " + HHMMSS;
    }
    
    // Comparing time
    public int compareTo(Time t) {
        return dateTime.compareTo(t.dateTime);
    }
    
    // Checks if input String is a date format
    public static int isDateFormat(String date) {
        
        int i = 1;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        try { 
            Date dateTime = df.parse(date);
        } catch (ParseException pe) {
            i = -1;
        }
        
        return i;
    }
    
} 