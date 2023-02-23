import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[5];
        Semaphore table = new Semaphore(4);

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Semaphore(1);
        }

        Philosopher[] philosophers = new Philosopher[5];
        philosophers[0] = new Philosopher(0, forks[4], forks[0], table);
        for (int i = 1; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher(i, forks[i - 1], forks[i], table);
        }

        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }

        try {
            Thread.sleep(10000);
            for (int i = 0; i < threads.length; i++) {
                threads[i].interrupt();
            }
            for (int i = 0; i < threads.length; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}