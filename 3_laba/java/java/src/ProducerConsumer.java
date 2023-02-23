import java.util.LinkedList;

public class ProducerConsumer {
    private LinkedList<Integer> buffer = new LinkedList<>();
    private int maxSize;

    public ProducerConsumer(int maxSize) {
        this.maxSize = maxSize;
    }

    public void produce(int numProductions) {
        for (int i = 0; i < numProductions; i++) {
            synchronized (this) {
                while (buffer.size() == maxSize) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int item = (int) (Math.random() * 100); // produce an item
                buffer.add(item);
                System.out.println("Producer " + Thread.currentThread().getName() + " produced " + item);
                notifyAll();
            }
        }
    }

    public void consume(int numConsumptions) {
        for (int i = 0; i < numConsumptions; i++) {
            synchronized (this) {
                while (buffer.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int item = buffer.remove();
                System.out.println("Consumer " + Thread.currentThread().getName() + " consumed " + item);
                notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        int maxSize = 5;
        int numProducers = 3;
        int numConsumers = 4;
        int numProductions = 40;
        int numConsumptions = numProductions * numProducers / numConsumers;

        ProducerConsumer pc = new ProducerConsumer(maxSize);

        for (int i = 0; i < numProducers; i++) {
            new Thread(() -> pc.produce(numProductions), "Producer" + i).start();
        }

        for (int i = 0; i < numConsumers; i++) {
            new Thread(() -> pc.consume(numConsumptions), "Consumer" + i).start();
        }
    }
}
