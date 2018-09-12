package Neelkamal;

import java.util.ArrayList;
import java.util.LinkedList;

public class ParallelAverager {

    public class IntegerHolder {
        private int value;
        public IntegerHolder(int value){
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    int numThreads;
    ArrayList<ParallelAveragerWorker> workers = new ArrayList<ParallelAveragerWorker>();
    IntegerHolder totalElements;

    public ParallelAverager(int numThreads) {
        this.numThreads = numThreads;
        totalElements = new IntegerHolder(0);
    }



    public static void main(String[] args) {
        int numThreads = 4; // number of threads for the maximizer
        int numElements = 1000; // number of integers in the list

        ParallelAverager averager = new ParallelAverager(numThreads);
        LinkedList<Integer> list = new LinkedList<Integer>();

        // populate the list
        // TODO: change this implementation to test accordingly
        for (int i=0; i<numElements; i++)
            list.add(i);

        // run the maximizer
        try {
            System.out.println(averager.avg(list));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Finds the maximum by using <code>numThreads</code> instances of
     * <code>ParallelAveragerWorker</code> to find avg contributions and then
     * combining the results.
     * @param list <code>LinkedList</code> containing <code>Integers</code>
     * @return Maximum element in the <code>LinkedList</code>
     * @throws InterruptedException
     */
    public double avg(LinkedList<Integer> list) throws InterruptedException {
        double avg = 0; // initialize avg as 0
        Thread.currentThread().setPriority(10);

        System.out.println(numThreads);
        // run numThreads instances of ParallelAveragerWorker
        for (int i=0; i < numThreads; i++) {
            workers.add(i, new ParallelAveragerWorker(list, totalElements));
            workers.get(i).start();
        }
        // wait for threads to finish
        for (int i=0; i<workers.size(); i++)
            workers.get(i).join();

        // take the sum of all avg contributions
        for (int i=0; i<workers.size(); i++) {
            double avgContribution = workers.get(i).getAvgContribution();
            System.out.println("Avg Contribution by "+workers.get(i).getName()+": "+avgContribution);
            avg += avgContribution;
        }

        return avg;
    }
}
