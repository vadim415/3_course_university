import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MinimumElementFinder {
    public static void main(String[] args) throws InterruptedException {
        RandomArrayGenerator array = new RandomArrayGenerator();

        final int numThreads = 6;
        final int chunkSize = array.arraySize / numThreads;

        MinimumFinderThread[] threads = new MinimumFinderThread[numThreads];
        AtomicInteger minIndex = new AtomicInteger(-1);
        AtomicInteger minValue = new AtomicInteger(Integer.MAX_VALUE);

        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * chunkSize;
            int endIndex = (i == numThreads - 1) ? array.arraySize : (i + 1) * chunkSize;
            threads[i] = new MinimumFinderThread(array.randomArray, startIndex, endIndex, minIndex, minValue);
            threads[i].start();
        }

        for (MinimumFinderThread thread : threads) {
            thread.join();
        }

        System.out.println("Minimum element found: " + minValue.get());
        System.out.println("Minimum element index: " + minIndex.get());
    }

    private static int[] generateArray(int size, int range) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * range);
        }
        String arrStr = Arrays.toString(array);
        System.out.println(arrStr);
        return array;
    }


}
