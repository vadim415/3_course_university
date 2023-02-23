using System;
using System.Threading;

class Program
{
    static int[] arr;
    static int min = int.MaxValue;
    static int index = 0;
    static int numOfThreads = 6;
    static int chunkSize;

    static void Main(string[] args)
    {
        // Generate the array
        int arrSize = 100;
        arr = new int[arrSize];
        Random rand = new Random();
        for (int i = 0; i < arrSize; i++)
        {
            arr[i] = rand.Next(100);
        }
        arr[37] = rand.Next(-20, 0);

        Console.WriteLine("array:");
        for (int i = 0; i < 100; i++)
        {
            Console.Write(arr[i] + " ");
        }
        Console.WriteLine();

        // Set the chunk size
        chunkSize = arrSize / numOfThreads;

        // Create the threads
        Thread[] threads = new Thread[numOfThreads];
        for (int i = 0; i < numOfThreads; i++)
        {
            int start = i * chunkSize;
            int end = (i == numOfThreads - 1) ? arrSize : (i + 1) * chunkSize;
            threads[i] = new Thread(() => FindMin(start, end));
            threads[i].Start();
        }

        // Wait for the threads to finish
        for (int i = 0; i < numOfThreads; i++)
        {
            threads[i].Join();
        }

        // Print the result
        Console.WriteLine("The minimum element is: " + min);
        Console.WriteLine("The index is: " + index);
    }

    static void FindMin(int start, int end)
    {
        int localMin = int.MaxValue;
        for (int i = start; i < end; i++)
        {
            if (arr[i] < localMin)
            {
                localMin = arr[i];
            }
        }

        // Update the global min using a lock
        lock (typeof(Program))
        {
            if (localMin < min)
            {
                min = localMin;
                index = Array.IndexOf(arr, localMin);
            }
        }
    }
}