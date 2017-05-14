package mpkParser;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mpkParser.html.LanesParser;
import mpkParser.html.ScheduleParser;

import java.util.List;

public class Main extends Application {

    private Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("mpkScheduleParser");
        Scene scene = new Scene(new VBox(), 1000, 800);
        window.setScene(scene);
        window.show();
        //window.setFullScreen(true);
    }


    public static void main(String[] args) {
        //DataAdder.truncateTable("rozklad_weekday");
        //DataAdder.truncateTable("rozklad_saturday");
        //DataAdder.truncateTable("rozklad_sunday");

        long startTime = System.currentTimeMillis();
        LanesParser lp = new LanesParser();
        lp.getLanesFromSite();
        List<String> laneList = lp.lanesToOneList();
        ScheduleParser sp = new ScheduleParser();
        for(int i=0; i<1; ++i){
            sp.getScheduleForLane(laneList.get(i), 0);
            sp.getScheduleForLane(laneList.get(i), 1);
        }

        long stopTime = System.currentTimeMillis() - startTime;
        System.out.println((double)(stopTime/1000.0));

        //launch(args);
    }
}


// Parent root = FXMLLoader.load(getClass().getResource("/mpkParser/fxml/main.fxml"));