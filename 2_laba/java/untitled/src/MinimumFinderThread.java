import java.util.concurrent.atomic.AtomicInteger;

class MinimumFinderThread extends Thread {
    private final int[] array;
    private final int startIndex;
    private final int endIndex;
    private final AtomicInteger minIndex;
    private final AtomicInteger minValue;

    public MinimumFinderThread(int[] array, int startIndex, int endIndex, AtomicInteger minIndex, AtomicInteger minValue) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.minIndex = minIndex;
        this.minValue = minValue;
    }

    @Override
    public void run() {
        int min = Integer.MAX_VALUE;
        int index = -1;
        for (int i = startIndex; i < endIndex; i++) {
            if (array[i] < min) {
                min = array[i];
                index = i;
            }
        }

        while (true) {
            int currentMinValue = minValue.get();
            if (min >= currentMinValue) {
                break;
            }
            boolean updated = minValue.compareAndSet(currentMinValue, min);
            if (updated) {
                minIndex.set(index);
                break;
            }
        }
    }
}