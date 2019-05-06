import java.io.InputStreamReader;
import java.util.Scanner;

public class MainOf1 {


    public static void main(String[] args) {

        int arraySize, commandCounter;
        commandCounter=1;
        System.out.println("Give number of operations: ");
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        arraySize=Integer.parseInt(scanner.nextLine());
        MyPrioQueue myPrioQueue = new MyPrioQueue(arraySize);
        System.out.println("Now, in "+arraySize+ " lines you should give " +arraySize+" operations to do on given structure:");

        while(commandCounter<=arraySize) {
            String keeper;
            keeper=scanner.nextLine();

            if(keeper.equals("empty")) {
                myPrioQueue.empty();
            }
            else if(keeper.equals("top")) {
                myPrioQueue.top();
            }
            else if(keeper.equals("print")) {
                myPrioQueue.printer();
            }
            else if(keeper.equals("pop")) {
                myPrioQueue.pop();
            }
            else if(keeper.equals("insert")) {
                int val = 0;
                int prio = 0;
                System.out.println("Inserted value: ");
                try {
                    val = Integer.parseInt(scanner.nextLine());
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Inserted priority: ");
                try {
                    prio = Integer.parseInt(scanner.nextLine());
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                myPrioQueue.insert(val, prio);
                System.out.println("");

            }
            else if(keeper.equals("priority")) {
                int forVal;
                int prio;
                System.out.println("For value: ");
                forVal = Integer.parseInt(scanner.nextLine());
                System.out.println("New priority: ");
                prio = Integer.parseInt(scanner.nextLine());
                myPrioQueue.priority(forVal, prio);
                System.out.println("");
            }
            else {
                System.out.println("You choosed wrong operation. Available: insert, priority, empty, top, pop, print.");
            }
            commandCounter++;
        }

    }
}
