package Neelkamal;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelAveragerWorker extends Thread {

    protected LinkedList<Integer> list;
    protected double contributingTotal = 0; // initialize to 0
    protected ParallelAverager.IntegerHolder totalElements; //initialize to 0

    public ParallelAveragerWorker(LinkedList<Integer> list, ParallelAverager.IntegerHolder totalElements) {
        this.list = list;
        this.totalElements = totalElements;
    }

    /**
     * Update <code>partialMax</code> until the list is exhausted.
     */
    public void run() {
        try {
            sleep(10);
            setPriority(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            int number;
            // check if list is not empty and removes the head
            // synchronization needed to avoid atomicity violation
            synchronized(list) {
                if (list.isEmpty()) {
                    return; // list is empty
                }
                number = list.remove();
            }

            synchronized (totalElements){
                totalElements.setValue(totalElements.getValue() + 1);
            }
            // update partialMax according to new value
            contributingTotal += number;
        }
    }

    public double getAvgContribution() {
        return contributingTotal/totalElements.getValue();
    }

}
