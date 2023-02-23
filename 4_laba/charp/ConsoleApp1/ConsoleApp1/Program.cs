using System;
using System.Threading;

public class DiningPhilosophersToken
{
    private static int numPhilosophers = 5;
    private static Semaphore[] forks = new Semaphore[numPhilosophers];
    private static Semaphore waiter = new Semaphore(numPhilosophers - 1, numPhilosophers - 1);
    private static Semaphore token = new Semaphore(1, 1);

    public static void Main()
    {
        for (int i = 0; i < numPhilosophers; i++)
        {
            forks[i] = new Semaphore(1, 1);
        }

        Thread[] threads = new Thread[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++)
        {
            threads[i] = new Thread(new ParameterizedThreadStart(Philosopher));
            threads[i].Start(i);
        }

        for (int i = 0; i < numPhilosophers; i++)
        {
            threads[i].Join();
        }
    }

    public static void Philosopher(object data)
    {
        int id = (int)data;

        while (true)
        {
            Console.WriteLine("Philosopher " + id + " is thinking");
            Thread.Sleep(500);

            waiter.WaitOne();

            token.WaitOne();

            forks[id].WaitOne();
            forks[(id + 1) % numPhilosophers].WaitOne();

            token.Release();

            Console.WriteLine("Philosopher " + id + " is eating");
            Thread.Sleep(500);

            forks[id].Release();
            forks[(id + 1) % numPhilosophers].Release();

            waiter.Release();
        }
    }
}
