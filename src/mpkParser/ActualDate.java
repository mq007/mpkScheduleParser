package mpkParser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by MQ on 2017-05-10.
 */
public class ActualDate {
    private ActualDate(){

    }

    public static String getDate(){
        Date today = new Date();
        LocalDate localDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String year  = Integer.toString(localDate.getYear());
        String month = Integer.toString(localDate.getMonthValue());
        String day   = Integer.toString(localDate.getDayOfMonth());

        String date = year;

        if(month.length() == 1){
            date = date + "0" + month;
        }else{
            date = date + month;
        }

        if(day.length() == 1){
            date = date + "0" + day;
        }else{
            date = date + day;
        }

        return date;
    }

}
