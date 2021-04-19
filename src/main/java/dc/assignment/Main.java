package dc.assignment;


import dc.assignment.lib.core.LaiYangAlgorithm;

public class Main {

    public static void main(String args[]) {
        System.out.println("================Starting Lai Yang Algorithm=================");
        LaiYangAlgorithm laiyangAlgorithm = new LaiYangAlgorithm();
        try {
            laiyangAlgorithm.start();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("================End of Lai Yang Algorithm=================");

    }

}
