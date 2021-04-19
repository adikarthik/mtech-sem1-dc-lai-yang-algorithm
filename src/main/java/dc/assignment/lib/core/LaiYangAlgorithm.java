package dc.assignment.lib.core;

import dc.assignment.utils.Color;
import dc.assignment.lib.processing.Event;
import dc.assignment.lib.processing.Processor;
import dc.assignment.utils.GlobalState;
import dc.assignment.utils.EventTracker;

import java.util.Scanner;

public class LaiYangAlgorithm {


    public void start(){
        EventTracker global = EventTracker.init();
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the no of processes : ");
        int noOfProcessor = scan.nextInt();

        GlobalState globalState = GlobalState.init();
        Processor processor [] = new Processor[noOfProcessor];

        for (int i=0; i < noOfProcessor ; i++){

            System.out.println("Enter the initial value for Processor "+(i+1));
            int amount = scan.nextInt();

            globalState.setGlobalState("processor" + (i + 1), Color.WHITE, amount);

            Processor process = new Processor("processor"+(i+1));
           if(noOfProcessor!=1) {
               System.out.println("enter the no of events including snapshot");
               int noOfEvents = scan.nextInt();
               Event[] eventArrayList = new Event[noOfEvents];

               for (int j = 0; j < noOfEvents; j++) {
                   String eventName = process.getProcessorName() + (j + 1);

                   global.setEventList(eventName, false);

                   System.out.println("enter 1 if it is send event else it will be considered snapshot");
                   int snapshot = scan.nextInt();

                   if (snapshot == 1) {
                       System.out.println("Enter the amount which needs to be transferred to the destination processor");
                       int eventAmount = scan.nextInt();

                       System.out.println("Enter the Destination Processor no");
                       int destProcessor = scan.nextInt();

                       if (destProcessor > noOfProcessor + 1) {
                           System.out.println("destination process in not present defaulting to process 1");
                           destProcessor = 1;
                       }
                       System.out.println("Enter the transit time of the Event and enter the time delay after which event should be launched");
                       int transitTaken = scan.nextInt();
                       int launchDelay = scan.nextInt();

                       Event event = new Event(eventName, eventAmount, "processor" + (destProcessor), transitTaken, launchDelay, process.getProcessorName());
                       eventArrayList[j] = event;
                   } else {
                       System.out.println("This Event is a Snapshot init marking ");
                       System.out.println("the Snapshot name is " + eventName);
                       System.out.println("Enter the time after which the snapshot init needs to be there");
                       int launchDelay = scan.nextInt();
                       eventArrayList[j] = new Event(eventName, launchDelay, process.getProcessorName());
                   }

               }
               process.setEventList(eventArrayList);
           }
            processor[i] = process;
        }

        if(noOfProcessor>1) {
            System.out.println("================Processing The State=================");
        }
        for (Processor process : processor){
            process.start();
        }

        int count = 0;

        while (!global.getEventList() && count < 300){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
        System.out.println("================The final state of the Processors is=================");
        globalState.displayAll();

    }

}
