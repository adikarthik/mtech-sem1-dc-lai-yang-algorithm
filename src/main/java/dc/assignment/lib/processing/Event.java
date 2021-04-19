package dc.assignment.lib.processing;


import dc.assignment.utils.Color;
import dc.assignment.utils.GlobalState;
import dc.assignment.utils.EventTracker;
import dc.assignment.utils.ProcessorState;

public class Event extends Thread{

    private  int amount;
    private String destProcess;
    private boolean snapshot=false;
    private Color color;
    private int timeTaken;
    private int launchDelay;
    private String eventName;
    private GlobalState globalState = GlobalState.init();
    private String sourceProcessName;
    private final String snapshotString = "This is the snapshot of the processor ";

    public Event (String eventName,int launchDelay,String sourceProcessName){
        this.eventName = eventName;
        this.snapshot = true;
        this.launchDelay = launchDelay;
        this.sourceProcessName = sourceProcessName;
    }
    public Event(String eventName ,int amount, String destProcess, int timeTaken, int launchDelay,String sourceProcessName){
        this.amount = amount;
        this.destProcess = destProcess;
        this.timeTaken = timeTaken;
        this.launchDelay = launchDelay;
        this.eventName = eventName;
        this.sourceProcessName= sourceProcessName;
    }

    public void setEventColor(Color color){
        this.color = color;
    }

    public Color getEventColor(){
        return  this.color;
    }


    public int getAmount() {
        return amount;
    }

    public boolean getSnapshot(){
        return this.snapshot;
    }

    public String getDestProcess() {
        return destProcess;
    }

    public int getTimeTaken(){
        return timeTaken;
    }

    public int getLaunchDelay(){ return  launchDelay;}

    public String getEventName() {
        return eventName;
    }

    public void run (){

        ProcessorState sourceState = globalState.getGlobalState().get(sourceProcessName);

        if (this.getSnapshot() && (sourceState.getColor() == Color.WHITE) ){
            System.out.println(snapshotString + " of the processor :"+ sourceProcessName);

            globalState.setGlobalState(sourceProcessName, Color.RED, sourceState.getValue());
            globalState.displayGlobalState(sourceProcessName);

        } else {


            globalState.setGlobalState(sourceProcessName, sourceState.getColor(), sourceState.getValue() - this.getAmount());

            System.out.println("The channel amount from source process "+ sourceProcessName + " to destination process "+this.getDestProcess()+
                               " is "+ this.getAmount() );
            try {
                Thread.sleep(this.getTimeTaken());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("the event has reached the destination process : " + this.getDestProcess());
            ProcessorState destState = globalState.getGlobalState().get(this.getDestProcess());
            Color destStateColor = destState.getColor();

            Color color = Color.WHITE;
            if(this.getEventColor() == Color.RED && destStateColor == Color.WHITE){
                color = Color.RED;
                System.out.println(this.snapshotString + " of the processor on receiving red in "+ this.getDestProcess());
            }
            else if (this.getEventColor() == Color.RED || destStateColor == Color.RED){
                color = Color.RED;
            }

            globalState.setGlobalState(this.getDestProcess(), color, destState.getValue() + this.getAmount());
            globalState.displayGlobalState(this.getDestProcess());

        }
        EventTracker tracker = EventTracker.init();
        tracker.setEventList(this.getEventName(),true);
    }
}
