package mpkParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MQ on 2017-05-10.
 */
public class Lanes {

    private Lanes(){

    }

    private static List<String> tramDay = new ArrayList<>();
    private static List<String> tramNight = new ArrayList<>();
    private static List<String> busCityDay = new ArrayList<>();
    private static List<String> busCityNight = new ArrayList<>();
    private static List<String> busAgglomerationDay = new ArrayList<>();
    private static List<String> busAgglomerationNight = new ArrayList<>();
    private static List<String> busCityAccelerated = new ArrayList<>();
    private static List<String> busAgglomerationAccelerated = new ArrayList<>();
    private static List<String> busCitySupport = new ArrayList<>();
    private static List<String> busCitySubstitute = new ArrayList<>();


    public static void setLanesNumbers(String typeOfLane, String lanes){
        List<String> lanesInList = parseLanesToList(lanes);
        if(typeOfLane.equals("Linie tramwajowe")){
            setTramDay(lanesInList);
        }else if(typeOfLane.equals("Linie tramwajowe nocne")){
            setTramNight(lanesInList);
        }else if(typeOfLane.equals("Linie autobusowe miejskie")){
            setBusCityDay(lanesInList);
        }else if(typeOfLane.equals("Linie autobusowe aglomeracyjne")){
            setBusAgglomerationDay(lanesInList);
        }else if(typeOfLane.equals("Linie autobusowe aglomeracyjne przyspieszone")){
            setBusAgglomerationAccelerated(lanesInList);
        }else if(typeOfLane.equals("Linie autobusowe miejskie wspomagające")){
            setBusCitySupport(lanesInList);
        }else if(typeOfLane.equals("Linie autobusowe miejskie przyspieszone")){
            setBusCityAccelerated(lanesInList);
        }else if(typeOfLane.equals("Linie autobusowe miejskie nocne")){
            setBusCityNight(lanesInList);
        }else if(typeOfLane.equals("Linie autobusowe zastępcze")){
            setBusCitySubstitute(lanesInList);
        }else if(typeOfLane.equals("Linie aglomeracyjne nocne")){
            setBusAgglomerationNight(lanesInList);
        }
    }

    private static List<String> parseLanesToList(String lanes){
        String [] numbers = lanes.split(" ");
        List<String> out = new ArrayList<>();

        for(int i=0; i<numbers.length; ++i){
            out.add(numbers[i]);
        }

        return out;
    }

    private static void setTramDay(List<String> tramDay) {
        Lanes.tramDay = tramDay;
    }

    private static void setTramNight(List<String> tramNight) {
        Lanes.tramNight = tramNight;
    }

    private static void setBusCityDay(List<String> busCityDay) {
        Lanes.busCityDay = busCityDay;
    }

    private static void setBusCityNight(List<String> busCityNight) {
        Lanes.busCityNight = busCityNight;
    }

    private static void setBusAgglomerationDay(List<String> busAgglomerationDay) {
        Lanes.busAgglomerationDay = busAgglomerationDay;
    }

    private static void setBusAgglomerationNight(List<String> busAgglomerationNight) {
        Lanes.busAgglomerationNight = busAgglomerationNight;
    }

    private static void setBusCityAccelerated(List<String> busCityAccelerated) {
        Lanes.busCityAccelerated = busCityAccelerated;
    }

    private static void setBusAgglomerationAccelerated(List<String> busAgglomerationAccelerated) {
        Lanes.busAgglomerationAccelerated = busAgglomerationAccelerated;
    }

    private static void setBusCitySupport(List<String> busCitySupport) {
        Lanes.busCitySupport = busCitySupport;
    }

    private static void setBusCitySubstitute(List<String> busCitySubstitute) {
        Lanes.busCitySubstitute = busCitySubstitute;
    }

    public static List<String> getTramDay() {
        return tramDay;
    }

    public static List<String> getTramNight() {
        return tramNight;
    }

    public static List<String> getBusCityDay() {
        return busCityDay;
    }

    public static List<String> getBusCityNight() {
        return busCityNight;
    }

    public static List<String> getBusAgglomerationDay() {
        return busAgglomerationDay;
    }

    public static List<String> getBusAgglomerationNight() {
        return busAgglomerationNight;
    }

    public static List<String> getBusCityAccelerated() {
        return busCityAccelerated;
    }

    public static List<String> getBusAgglomerationAccelerated() {
        return busAgglomerationAccelerated;
    }

    public static List<String> getBusCitySupport() {
        return busCitySupport;
    }

    public static List<String> getBusCitySubstitute() {
        return busCitySubstitute;
    }
}
