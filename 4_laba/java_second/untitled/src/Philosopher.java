import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    private final int id;
    private final Semaphore leftFork;
    private final Semaphore rightFork;
    private final Semaphore table;
    private boolean isEating;

    public Philosopher(int id, Semaphore leftFork, Semaphore rightFork, Semaphore table) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.table = table;
        this.isEating = false;
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + id + " is thinking.");
        Thread.sleep(1000);
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + id + " is eating.");
        Thread.sleep(1000);
    }

    private void takeForks() throws InterruptedException {
        table.acquire();
        while (leftFork.availablePermits() == 0 || rightFork.availablePermits() == 0) {
            if (leftFork.availablePermits() > 0 && rightFork.availablePermits() == 0) {
                leftFork.acquire();
                table.release();
                Thread.yield();
                table.acquire();
            }
            if (rightFork.availablePermits() > 0 && leftFork.availablePermits() == 0) {
                rightFork.acquire();
                table.release();
                Thread.yield();
                table.acquire();
            }
        }
        leftFork.acquire();
        rightFork.acquire();
        table.release();
    }

    private void returnForks() {
        leftFork.release();
        rightFork.release();
    }

    @Override
    public void run() {
        try {
            while (true) {
                think();
                takeForks();
                isEating = true;
                eat();
                isEating = false;
                returnForks();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isEating() {
        return isEating;
    }
}


