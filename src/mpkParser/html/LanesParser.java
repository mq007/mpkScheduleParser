package mpkParser.html;

import mpkParser.ActualDate;
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

public class LanesParser {
    private String urlAddress = "http://rozklady.mpk.krakow.pl/?lang=PL&akcja=index&rozklad=";
    private List<String> headers = new ArrayList<>();
    private List<String> laneNumbers = new ArrayList<>();

    public void getLanesFromSite(){
        Document doc = null;
        try {
            urlAddress = urlAddress + ActualDate.getDate();
            doc = Jsoup.connect(urlAddress).get();

            Elements header = doc.select(".t_info_left");
            Elements data = doc.select(".linia_table_left");

            headers.clear();
            laneNumbers.clear();

            String text = "";

            for(Element l : header){
                text = l.text();
                text = text.substring(0, text.length()-1);
                if(!text.contains("Opis")){
                    headers.add(text);
                }
            }

            for(Element l : data){
                text = l.text();
                laneNumbers.add(text);
            }

            setTramsAndBusesNumbers(headers, laneNumbers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTramsAndBusesNumbers(List<String> header, List<String> laneNumber){
        for(int i=0; i< header.size(); ++i){
            Lanes.setLanesNumbers(header.get(i), laneNumber.get(i));
        }
    }

    public List<String> lanesToOneList(){
        List<String> lanesList = new ArrayList<>();
        for(int i=0; i<laneNumbers.size(); ++i){
            String [] tmp = laneNumbers.get(i).split(" ");
            for(int j=0; j<tmp.length; ++j){
                lanesList.add(tmp[j]);
            }
        }
        return lanesList;
    }

    public void saveLaneInfo(){
        String actualDate = ActualDate.getDate();
        String urlAddressWithoutLane = "http://rozklady.mpk.krakow.pl/?lang=PL&akcja=index&rozklad=" + actualDate + "&linia=";
        String urlAddress = "";
        List<String> lanesList = lanesToOneList();

        for(int i=0; i<lanesList.size(); ++i){
            urlAddress = urlAddressWithoutLane + lanesList.get(i);
            try {
                Document doc = Jsoup.connect(urlAddress).get();

                String cssQuery1 = ".main > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)" +
                        " > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1)" +
                        " > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)" +
                        " > tr:nth-child(1) > td:nth-child(3) > a:nth-child(2)";

                String cssQuery2 = ".main > tbody:nth-child(2) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)" +
                        " > tr:nth-child(1) > td:nth-child(2) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1)" +
                        " > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > table:nth-child(1) > tbody:nth-child(1)" +
                        " > tr:nth-child(1) > td:nth-child(3) > a:nth-child(4)";

                System.out.println(doc.select(cssQuery1).text());
                System.out.println(doc.select(cssQuery2).text());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
