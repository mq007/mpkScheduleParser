package mpkParser;

import java.util.List;

/**
 * Created by MQ on 2017-05-10.
 */
public class LaneStopInfo {
    private String stopName;
    private int direction;
    private String directionDescription;
    private int stopNumber;
    private List<String> weekdaySchedule;
    private List<String> saturdaySchedule;
    private List<String> sundaySchedule;

    public LaneStopInfo(String stopName, int direction, String directionDescription, int stopNumber, List<List<String>> schedule){
        this.stopName = stopName;
        this.direction = direction;
        this.directionDescription = directionDescription;
        this.stopNumber = stopNumber;
        setSchedule(schedule);
    }

    public void setSchedule(List<List<String>> schedule){
        weekdaySchedule = schedule.get(0);
        saturdaySchedule = schedule.get(1);
        sundaySchedule = schedule.get(2);
    }
}
