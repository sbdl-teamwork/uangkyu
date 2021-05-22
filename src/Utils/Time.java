/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 *
 * @author Alfian Andi Nugraha
 */
public class Time {
    private Date date = new Date();
    
    public static String DateFormat = "yyyy-MM-dd HH:mm:ss";

    public Date getDate() {
        return date;
    }

    public Time setDate(Date date) {
        this.date = date;
        return this;
    }
    
    /**
     * getDatetimeNow() digunakan untuk mengambil datetime sekarang
     * @return 
     */
    public String getDatetimeNow() {
        Calendar calendar = Calendar.getInstance();
        this.setDate(calendar.getTime());
        return parseDatetime();
    }
    
    /**
     * parseDatetime digunakan untuk mengubah date Java menjadi datetime di mysql
     * @return 
     */
    public String parseDatetime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Time.DateFormat);
        return dateFormat.format(this.getDate());
    }
}
