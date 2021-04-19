package dc.assignment.utils;

import java.util.Hashtable;

public class GlobalState {
    private  Hashtable<String, ProcessorState> globalState;

    private static GlobalState constant = null;

    private GlobalState(){
        globalState = new Hashtable<String, ProcessorState>();
    }


    public synchronized  static GlobalState init() {
        if (constant == null){
            constant = new GlobalState();
        }
        return constant;
    }


    public synchronized  void setGlobalState(String processor, Color color, int value) {
        ProcessorState state = new ProcessorState();
        state.setValue(value);
        state.setColor(color);
        globalState.put(processor, state);
    }

    public synchronized  Hashtable<String, ProcessorState> getGlobalState(){
        return this.globalState;
    }

    public synchronized  void displayGlobalState(String stateKey) {
        ProcessorState currentState = globalState.get(stateKey);
        System.out.println("Processor : " + stateKey + "\nCurrent color : " + currentState.getColor() + "\nCurrent amount : " + currentState.getValue());
    }

    public void displayAll(){
        for (String stateKey : globalState.keySet()){
        ProcessorState currentState = globalState.get(stateKey);
        System.out.println("Processor : " +stateKey+ "\nCurrent color : "+ currentState.getColor()+"\nCurrent amount : "+ currentState.getValue());
        }
    }
}
