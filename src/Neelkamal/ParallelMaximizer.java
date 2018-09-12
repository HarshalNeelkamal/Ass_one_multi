package Neelkamal;

//import java.util.LinkedList;
import java.util.*;

/**
 * This class runs <code>numThreads</code> instances of
 * <code>Neelkamal.ParallelMaximizerWorker</code> in parallel to find the maximum
 * <code>Integer</code> in a <code>LinkedList</code>.
 */
public class ParallelMaximizer {

    int numThreads;
    ArrayList<ParallelMaximizerWorker> workers = new ArrayList<Neelkamal.ParallelMaximizerWorker>();

    public ParallelMaximizer(int numThreads) {
        this.numThreads = numThreads;
    }



    public static void main(String[] args) {
        int numThreads = 4; // number of threads for the maximizer
        int numElements = 1000; // number of integers in the list

        ParallelMaximizer maximizer = new ParallelMaximizer(numThreads);
        LinkedList<Integer> list = new LinkedList<Integer>();

        // populate the list
        // TODO: change this implementation to test accordingly
        for (int i=0; i<numElements; i++)
            list.add(i);

        // run the maximizer
        try {
            System.out.println(maximizer.max(list));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Finds the maximum by using <code>numThreads</code> instances of
     * <code>Neelkamal.ParallelMaximizerWorker</code> to find partial maximums and then
     * combining the results.
     * @param list <code>LinkedList</code> containing <code>Integers</code>
     * @return Maximum element in the <code>LinkedList</code>
     * @throws InterruptedException
     */
    public int max(LinkedList<Integer> list) throws InterruptedException {
        int max = Integer.MIN_VALUE; // initialize max as lowest value

        System.out.println(numThreads);
        // run numThreads instances of Neelkamal.ParallelMaximizerWorker
        for (int i=0; i < numThreads; i++) {
            workers.add(i, new ParallelMaximizerWorker(list));
            workers.get(i).start();
        }
        // wait for threads to finish
        for (int i=0; i<workers.size(); i++)
            workers.get(i).join();

        // take the highest of the partial maximums
        for (int i=0; i<workers.size(); i++) {
            int partialMax = workers.get(i).getPartialMax();
            System.out.println("partial max for "+workers.get(i).getName()+": "+partialMax);
            if(partialMax > max){
                max = partialMax;
            }
        }

        return max;
    }

}
