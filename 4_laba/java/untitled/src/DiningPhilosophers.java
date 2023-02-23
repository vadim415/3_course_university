import java.util.concurrent.Semaphore;

public class DiningPhilosophers {
    private static final int NUM_PHILOSOPHERS = 5;
    private static final Semaphore[] forks = new Semaphore[NUM_PHILOSOPHERS];
    private static final Semaphore waiter = new Semaphore(1);

    public static void main(String[] args) {
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            forks[i] = new Semaphore(1);
        }

        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            Thread t = new Thread(new Philosopher(i));
            t.start();
        }
    }

    static class Philosopher implements Runnable {
        private final int id;
        private final Semaphore leftFork;
        private final Semaphore rightFork;

        public Philosopher(int id) {
            this.id = id;
            this.leftFork = forks[id];
            this.rightFork = forks[(id + 1) % NUM_PHILOSOPHERS];
        }

        private void think() throws InterruptedException {
            System.out.println("Philosopher " + id + " is thinking");
            Thread.sleep(1000);
        }

        private void eat() throws InterruptedException {
            System.out.println("Philosopher " + id + " is eating");
            Thread.sleep(1000);
        }

        private void takeForks() throws InterruptedException {
            leftFork.acquire();
            rightFork.acquire();
        }

        private void releaseForks() {
            leftFork.release();
            rightFork.release();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    think();

                    waiter.acquire();
                    takeForks();
                    waiter.release();

                    eat();

                    releaseForks();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
