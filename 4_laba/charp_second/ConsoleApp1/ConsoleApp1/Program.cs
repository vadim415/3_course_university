using System;
using System.Threading;

class DiningPhilosophers
{
    static int NUM_PHILOSOPHERS = 5;
    static int[] forks = new int[NUM_PHILOSOPHERS];
    static Semaphore forkSem = new Semaphore(4, 4);

    static void Main()
    {
        Thread[] threads = new Thread[NUM_PHILOSOPHERS];
        for (int i = 0; i < NUM_PHILOSOPHERS; i++)
        {
            forks[i] = 0;
            threads[i] = new Thread(new ParameterizedThreadStart(Philosopher));
            threads[i].Start(i);
        }
    }

    static void Philosopher(object obj)
    {
        int id = (int)obj;
        int leftFork = id;
        int rightFork = (id + 1) % NUM_PHILOSOPHERS;

        while (true)
        {
            // Wait for the forks to become available
            forkSem.WaitOne();
            lock (forks)
            {
                while (forks[leftFork] == 1 || forks[rightFork] == 1 || CountForks() == 4)
                {
                    Monitor.Wait(forks);
                }
                forks[leftFork] = 1;
                forks[rightFork] = 1;
            }

            // Eat for a while
            Console.WriteLine("Philosopher " + id + " is eating");
            Thread.Sleep(1000);

            // Release the forks
            lock (forks)
            {
                forks[leftFork] = 0;
                forks[rightFork] = 0;
                Monitor.PulseAll(forks);
            }
            forkSem.Release();
        }
    }

    static int CountForks()
    {
        int count = 0;
        for (int i = 0; i < NUM_PHILOSOPHERS; i++)
        {
            if (forks[i] == 1)
            {
                count++;
            }
        }
        return count;
    }
}
