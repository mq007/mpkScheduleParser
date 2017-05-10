package mpkParser.html;

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
    private String urlAdress = "http://rozklady.mpk.krakow.pl/?lang=PL&rozklad=20170510";

    public void getLanesFromSite(){
        Document doc = null;
        try {
            doc = Jsoup.connect(urlAdress).get();
            Elements header = doc.select(".t_info_left");
            Elements data = doc.select(".linia_table_left");

            List<String> headers = new ArrayList<>();
            List<String> laneNumber = new ArrayList<>();

            String text = "";

            for(Element l : header){
                text = l.text();
                text = text.substring(0, text.length()-1);
                if(!text.contains("Opis")){
                    headers.add(text);
                    System.out.println(text);
                }
            }

            for(Element l : data){
                text = l.text();
                laneNumber.add(text);
                System.out.println(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTramsAndBusesNumbers(List<String> header, List<String> laneNumber){

    }
}
