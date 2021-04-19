package dc.assignment.utils;


import java.util.Hashtable;

public class EventTracker {

    private static EventTracker eventTracker = null;
    private Hashtable<String,Boolean> eventList = new Hashtable<String, Boolean>();

    public synchronized static EventTracker init(){
        if (eventTracker == null){
            eventTracker = new EventTracker();
        }
            return eventTracker;
    }


    public  boolean getEventList() {
        boolean flag = true;
        for (String eventkey : eventList.keySet()){
            flag = flag && eventList.get(eventkey);
        }
        return flag;
    }

    public void setEventList(String eventName,boolean flag) {
        this.eventList.put(eventName,flag);
    }
}
