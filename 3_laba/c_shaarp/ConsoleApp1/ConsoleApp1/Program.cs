using System;
using System.Threading;

class Program
{
    static int numManufacturers = 3; // number of manufacturers
    static int numConsumers = 4; // number of consumers
    static int bufferSize = 5; // maximum buffer size
    static int numItems = 40; // total number of items to produce/consume

    static SemaphoreSlim mutex = new SemaphoreSlim(1); // binary semaphore for mutual exclusion
    static SemaphoreSlim full = new SemaphoreSlim(0); // counting semaphore for full buffer
    static SemaphoreSlim empty = new SemaphoreSlim(bufferSize); // counting semaphore for empty buffer

    static int[] buffer = new int[bufferSize]; // multilayered buffer
    static int inIndex = 0; // index for the next producer to add an item
    static int outIndex = 0; // index for the next consumer to remove an item
    static int numItemsProduced = 0; // number of items produced
    static int numItemsConsumed = 0; // number of items consumed

    static void Main()
    {
        // start the manufacturers
        for (int i = 0; i < numManufacturers; i++)
        {
            int manufacturerId = i;
            new Thread(() => Manufacturer(manufacturerId)).Start();
        }

        // start the consumers
        for (int i = 0; i < numConsumers; i++)
        {
            int consumerId = i;
            new Thread(() => Consumer(consumerId)).Start();
        }
    }

    static void Manufacturer(int manufacturerId)
    {
        while (numItemsProduced < numItems)
        {
            // produce item
            int item = numItemsProduced + 1;
            numItemsProduced++;

            // wait for empty buffer
            empty.Wait();

            // acquire mutex
            mutex.Wait();

            // add item to buffer
            buffer[inIndex] = item;
            Console.WriteLine("Manufacturer {0} produced item {1}", manufacturerId, item);
            inIndex = (inIndex + 1) % bufferSize;

            // release mutex
            mutex.Release();

            // signal full buffer
            full.Release();
        }
    }

    static void Consumer(int consumerId)
    {
        while (numItemsConsumed < numItems)
        {
            // wait for full buffer
            full.Wait();

            // acquire mutex
            mutex.Wait();

            // remove item from buffer
            int item = buffer[outIndex];
            Console.WriteLine("Consumer {0} consumed item {1}", consumerId, item);
            outIndex = (outIndex + 1) % bufferSize;
            numItemsConsumed++;

            // release mutex
            mutex.Release();

            // signal empty buffer
            empty.Release();
        }
    }
}
