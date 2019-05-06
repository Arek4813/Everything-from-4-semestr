public class MyPrioQueue {

    static Element[] priorityQueue;
    static int whatIsNextFreeArrayIndex;

    public MyPrioQueue(int size) {
        priorityQueue = new Element[size];
    }

    boolean empty() {
        if(whatIsNextFreeArrayIndex==0) {
            System.out.println("INFORM THAT ARRAY IS EMPTY");
            return true;
        }
        else {
            System.out.println("INFORM THAT ARRAY IS NOT EMPTY");
            return false;
        }
    }

    void printer() {
        int tmp=0;
        System.out.println("I'm printing the whole structure as (x, p) where x - value, p - priority --->\n");
        while(tmp<whatIsNextFreeArrayIndex) {
            System.out.print("("+ priorityQueue[tmp].getValue()+","+priorityQueue[tmp].getPriority()+") ");
            tmp++;
        }
        System.out.println("");
    }

    static void swap(Element element1, Element element2){
        Element tmpelement = new Element(100, 100);
        tmpelement.setPriority(element1.getPriority());
        tmpelement.setValue(element1.getValue());
        element1.setPriority(element2.getPriority());
        element1.setValue(element2.getValue());
        element2.setPriority(tmpelement.getPriority());
        element2.setValue(tmpelement.getValue());
    }


    public void insert(int value, int priority) {

        Element element = new Element(value, priority);
        if (priority >= 0) {
            if (whatIsNextFreeArrayIndex == 0) {
                priorityQueue[0]=element;

            } else {
                priorityQueue[whatIsNextFreeArrayIndex] = element;
                priorityQueue[whatIsNextFreeArrayIndex].setPriority(priority);
                priorityQueue[whatIsNextFreeArrayIndex].setValue(value);
                int temp = whatIsNextFreeArrayIndex;
                while (priorityQueue[temp].getPriority() < priorityQueue[(temp - 1) / 2].getPriority() && temp >= 1) {
                    swap(priorityQueue[temp], priorityQueue[(temp-1)/2]);
                    temp = (temp - 1) / 2;
                }
            }
            whatIsNextFreeArrayIndex++;
        } else {
            System.out.println("You should give greater than or equal 0 priority");
        }
        System.out.println("INSERT done");
    }

    void top() {
        if(whatIsNextFreeArrayIndex==0) {
            System.out.println("-------------------------------------");
        }
        else {
            System.out.println("Value with the highest priority: "+ priorityQueue[0].getValue());
        }
    }

    Element pop() {

        Element tmpelement = new Element(-100, -100);
        if(whatIsNextFreeArrayIndex==0) {
            System.out.println("-------------------------------------");
        } else {
            System.out.println("Value with the highest priority: "+ priorityQueue[0].getValue());
            tmpelement.setPriority(priorityQueue[0].getPriority());
            tmpelement.setValue(priorityQueue[0].getValue());
            swap(priorityQueue[0], priorityQueue[whatIsNextFreeArrayIndex-1]);
            priorityQueue[whatIsNextFreeArrayIndex-1].setPriority(-100);
            priorityQueue[whatIsNextFreeArrayIndex-1].setValue(-100);
            whatIsNextFreeArrayIndex--;
            int temp=0;
            while(true) {
                if(2 * temp + 1 < whatIsNextFreeArrayIndex) {
                    if (priorityQueue[temp].getPriority() > priorityQueue[2 * temp + 1].getPriority()) {
                        temp = swapperInPop(temp);
                    }
                    else if(2*temp+2<= whatIsNextFreeArrayIndex) {
                        break;
                    }
                }
                else if (2 * temp < whatIsNextFreeArrayIndex && whatIsNextFreeArrayIndex > 1) {
                    if((priorityQueue[temp].getPriority() > priorityQueue[2 * temp + 2].getPriority()) && 2 * temp + 2 < whatIsNextFreeArrayIndex) {
                        temp = swapperInPop(temp);
                    }
                    else if(2*temp+1== whatIsNextFreeArrayIndex){
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
        return tmpelement;
    }

    private int swapperInPop(int temp) {
        if ((priorityQueue[2 * temp + 1].getPriority() <= priorityQueue[2 * temp + 2].getPriority() && 2 * temp + 1 != whatIsNextFreeArrayIndex) || (2 * temp + 2 == whatIsNextFreeArrayIndex && priorityQueue[2 * temp + 1].getValue() != -100 && priorityQueue[temp].getPriority() > priorityQueue[2 * temp + 1].getPriority())) {
            swap(priorityQueue[temp], priorityQueue[2 * temp + 1]);
            temp = 2 * temp + 1;
        } else {
            if (2 * temp + 2 != whatIsNextFreeArrayIndex) {
                swap(priorityQueue[temp], priorityQueue[2 * temp + 2]);
                temp = 2 * temp + 2;
            }
        }
        return temp;
    }


    int parent(int i) {
        int s = (i-1)/2;
        return s;
    }

    void recurse(int temp, int priority, int forValue) {
        if (priorityQueue[temp].getPriority()<=priority) {
            if(2*temp+1<whatIsNextFreeArrayIndex) {
                recurse(2 * temp + 1, priority, forValue);
            }
            if(2*temp+2<whatIsNextFreeArrayIndex) {
                recurse(2 * temp + 2, priority, forValue);
            }
        }
        else {
            if(priorityQueue[temp].getValue()==forValue) {
                priorityQueue[temp].setPriority(priority);
                int i=temp;
                while (i != 0 && priorityQueue[parent(i)].getPriority() > priorityQueue[i].getPriority()) {
                    swap(priorityQueue[parent(i)], priorityQueue[i]);
                    i = parent(i);
                }
            }
            if(2*temp+1<whatIsNextFreeArrayIndex) {
                recurse(2 * temp + 1, priority, forValue);
            }
            if(2*temp+2<whatIsNextFreeArrayIndex) {
                recurse(2 * temp + 2, priority, forValue);
            }
        }
    }

    void priority(int forValue, int priority) {
        int temp;
        if(whatIsNextFreeArrayIndex==0) {
            System.out.println("-------------------------------------");
        } else {
            if(priority>=0) {
                temp = 0;
                recurse(temp, priority, forValue);
            }
            else {
                System.out.println("You should give greater than or equal 0 priority");
            }
        }
        System.out.println("PRIORITY done");
    }

}
