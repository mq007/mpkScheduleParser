package mpkParser.html;

import mpkParser.ActualDate;
import mpkParser.DataAdder;
import mpkParser.LaneStopInfo;
import mpkParser.Lanes;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017-05-10.
 */
public class ScheduleParser {

    private String todayDate;

    public ScheduleParser(){
        todayDate = ActualDate.getDate();
    }

    private String getUrlAddress(String lane, int direction, int num){
        return "http://rozklady.mpk.krakow.pl/?lang=PL&akcja=index&rozklad=" + todayDate + "&linia=" + lane + "__" + direction + "__" + num;
    }

    public List<String> getStopsNames(String lane, int direction){
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


    public String isTram(String lane){
        if (Lanes.getTramDay().contains(lane) || Lanes.getTramNight().contains(lane)){
            return "tramwajowa";
        }else{
            return "autobusowa";
        }
    }

    public String isAgglomeration(String lane){
        if (Lanes.getBusAgglomerationNight().contains(lane) || Lanes.getBusAgglomerationDay().contains(lane) || Lanes.getBusAgglomerationAccelerated().contains(lane)){
            return "1";
        }else {
            return "0";
        }
    }

    public String isDaily(String lane){
        if (Lanes.getTramNight().contains(lane) || Lanes.getBusAgglomerationNight().contains(lane) || Lanes.getBusCityNight().contains(lane)){
            return "0";
        }else{
            return "1";
        }
    }

    public void getScheduleForLane(String lane, int direction){
        List<String> stopNames = getStopsNames(lane, direction);

        String directionPointStart;
        String directionPointStop;

        if(direction == 0){
            directionPointStart = stopNames.get(0);
            directionPointStop = stopNames.get(stopNames.size()-1);
        }else{
            directionPointStart = stopNames.get(stopNames.size()-1);
            directionPointStop = stopNames.get(0);
        }

        String typeOfLane = isTram(lane);
        String zone = isAgglomeration(lane);
        String dayLane = isDaily(lane);

        String query = "('"+ lane +"', '"+ direction + "', '" + typeOfLane + "', '" + zone + "', '" + dayLane + "', '" + directionPointStart + "', '" + directionPointStop + "')";
        String laneAddTableID = "Linie";
        //DataAdder.insertIntoQuery(laneAddTableID, query);

        String queryWeekday = "";
        String querySaturday = "";
        String querySunday = "";

        for(int i=0; i<stopNames.size(); ++i){

            String urlAddress = getUrlAddress(lane, direction, i+1);
            //System.out.println(urlAddress);
            String time;

            try {
                Document doc = Jsoup.connect(urlAddress).get();
                String x = ".main > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1)" +
                        " > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)" +
                        " > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr";

                Elements table = doc.select(x);

                for(Element zz : table){
                    int childrenSize = zz.children().size();
                    String hour = zz.child(0).text();
                    if(!hour.contains("Godzina") || !hour.contains("Zakłócenia")){
                        if(hour.length() == 1){
                            hour = "0" + hour;
                        }
                        if(childrenSize == 2){
                            String weekday = zz.child(1).text();
                            if(!weekday.equals("")){
                                String [] weekdayMinutes = weekday.split(" ");
                                for(int j=0; j<weekdayMinutes.length; ++j){
                                    time = hour+":"+weekdayMinutes[j].substring(0,2) + ":00";
                                    queryWeekday = queryWeekday.concat("\n('" + lane + "', '" + direction + "', '" + (i+1) + "', '" + stopNames.get(i) + "', '" + time + "'),");
                                }
                            }
                        }
                        if(childrenSize == 3){
                            String saturday = zz.child(2).text();
                            if(!saturday.equals("")){
                                String [] saturdayMinutes = saturday.split(" ");
                                for(int j=0; j<saturdayMinutes.length; ++j){
                                    time = hour+":"+saturdayMinutes[j].substring(0,2) + ":00";
                                    querySaturday = querySaturday.concat("\n('" + lane + "', '" + direction + "', '" + (i+1) + "', '" + stopNames.get(i) + "', '" + time + "'),");
                                }
                            }
                        }
                        if(childrenSize == 4){
                            String sunday = zz.child(3).text();
                            if(!sunday.equals("")){
                                String [] sundayMinutes = sunday.split(" ");
                                for(int j=0; j<sundayMinutes.length; ++j){
                                    time = hour+":"+sundayMinutes[j].substring(0,2) + ":00";
                                    querySunday = querySunday.concat("\n('" + lane + "', '" + direction + "', '" + (i+1) + "', '" + stopNames.get(i) + "', '" + time + "'),");
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(queryWeekday.length() > 0){
            queryWeekday = queryWeekday.substring(0, queryWeekday.length()-1);
            //DataAdder.insertIntoQuery("Rozklad_weekday", queryWeekday);
        }
        if(querySaturday.length() > 0){
            querySaturday = querySaturday.substring(0, querySaturday.length()-1);
            //DataAdder.insertIntoQuery("Rozklad_saturday", querySaturday);
        }
        if(querySunday.length() > 0){
            querySunday = querySunday.substring(0, querySunday.length()-1);
            //DataAdder.insertIntoQuery("Rozklad_sunday", querySunday);
        }

        System.out.println("Linia: " + lane + "   kierunek: " + direction + " zrobione!");
    }
}
