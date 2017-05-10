package mpkParser.html;

import mpkParser.ActualDate;
import mpkParser.LaneStopInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MQ on 2017-05-10.
 */
public class ScheduleParser {

    private String todayDate;
    private String lane;

    public ScheduleParser(){
        todayDate = ActualDate.getDate();
        lane = "";
    }

    private String getUrlAddress(String lane, int direction, int num){
        return "http://rozklady.mpk.krakow.pl/?lang=PL&akcja=index&rozklad=" + ActualDate.getDate() + "&linia=" + lane + "__" + direction + "__" + num;
    }

    public List<String> getStopsNames(int direction){
        Document doc = null;
        List<String> stops = new ArrayList<>();
        String urlAddress = getUrlAddress(lane, direction, 1);
        try {
            doc = Jsoup.connect(urlAddress).get();
            String y = ".main > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1)" +
                    " > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)" +
                    " > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)";

            Elements hours = doc.select(y).select("tr").select("a[href]");

            for(Element trr : hours){
                if(!trr.text().equals("")){
                    stops.add(trr.text());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return stops;
        }
    }

    public void setLane(String lane){
        this.lane = lane;
    }

    public List<LaneStopInfo> getScheduleForLane(int direction){

        List<String> stopNames = getStopsNames(direction);
        List<LaneStopInfo> laneStopInfoList = new ArrayList<>();
        String directionDescription = stopNames.get(0) + stopNames.get(stopNames.size()-1);
        Document doc = null;

        for(int i=1; i<=2; ++i){

            List<List<String>> listOfSchedule = new ArrayList<>();
            List<String> weekdays = new ArrayList<>();
            List<String> saturdays = new ArrayList<>();
            List<String> sundays = new ArrayList<>();

            String urlAddress = getUrlAddress(lane, direction, i);
            System.out.println("Adres: " + urlAddress);

            try {
                doc = Jsoup.connect(urlAddress).get();
                String x = ".main > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1)" +
                        " > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)" +
                        " > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr";

                Elements table = doc.select(x);

                for(Element zz : table){
                    if(zz.children().size() == 4 ){
                        String hour = zz.child(0).text();
                        if(hour.length() == 1){
                            hour = "0" + hour;
                        }
                        String weekday = zz.child(1).text();
                        String saturday = zz.child(2).text();
                        String sunday = zz.child(3).text();

                        if(!weekday.equals("")){
                            //System.out.println("\nweekday:");
                            String [] weekdayMinutes = weekday.split(" ");
                            for(int j=0; j<weekdayMinutes.length; ++j){
                                //System.out.println(hour+":"+weekdayMinutes[j]);
                                weekdays.add(hour+":"+weekdayMinutes[j]);
                            }
                        }

                        if(!saturday.equals("")){
                            //System.out.println("\nsaturday:");
                            String [] saturdayMinutes = saturday.split(" ");
                            for(int j=0; j<saturdayMinutes.length; ++j){
                                //System.out.println(hour+":"+saturdayMinutes[j]);
                                saturdays.add(hour+":"+saturdayMinutes[j]);
                            }
                        }

                        if(!sunday.equals("")){
                            //System.out.println("\nsunday:");
                            String [] sundayMinutes = sunday.split(" ");
                            for(int j=0; j<sundayMinutes.length; ++j){
                                //System.out.println(hour+":"+sundayMinutes[j]);
                                sundays.add(hour+":"+sundayMinutes[j]);
                            }
                        }
                    }
                }

                listOfSchedule.add(weekdays);
                listOfSchedule.add(saturdays);
                listOfSchedule.add(sundays);

                laneStopInfoList.add(new LaneStopInfo(stopNames.get(i-1), direction, directionDescription, i, listOfSchedule));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return laneStopInfoList;
    }
}
