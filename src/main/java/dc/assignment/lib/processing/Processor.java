package dc.assignment.lib.processing;


import dc.assignment.utils.GlobalState;

public class Processor extends Thread {

    private Event event[] = new Event[0];
    private String name;

    public Processor(String name){
        this.name = name;
    }

    public Event[] getEventList(){
        return event;
    }

    public String getProcessorName(){
        return name;
    }


    public void setEventList(Event event[]){
        this.event = event;
    }


    public void run(){
        for (Event event : this.getEventList()){
            try {
                Thread.sleep(event.getLaunchDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GlobalState globalState = GlobalState.init();
            event.setEventColor(globalState.getGlobalState().get(this.getProcessorName()).getColor());
            event.start();
        }
    }
}
