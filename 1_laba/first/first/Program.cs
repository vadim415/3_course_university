
class Program
{
    static void Main(string[] args)
    {
        (new Program()).Start();
    }

    void Start()
    {
        (new Thread(Calculator)).Start();
        (new Thread(Calculator)).Start();
        (new Thread(Calculator)).Start();
        (new Thread(Calculator)).Start();
        (new Thread(Calculator)).Start();

        Thread thread1 = new Thread(Calculator);
        thread1.Start();

        (new Thread(Stoper)).Start();
    }

    void Calculator()
    {
        long sum = 0;
        int i = 1, step = 50;
        do
        {
            sum += step;
            i++;
        } while (!canStop);
        Console.WriteLine($"сума: {sum}, доданки: {i}, Id потоку: {Thread.CurrentThread.ManagedThreadId}");
    }

    private bool canStop = false;

    public bool CanStop { get => canStop; }

    public void Stoper()
    {
        Thread.Sleep(10 * 1000);
        canStop = true;
    }
}