public class Main {
    public static void main(String[] args) {
        BreakThread breakThread = new BreakThread();

        new MainThread(1, breakThread).start();
        new MainThread(2, breakThread).start();
        new MainThread(3, breakThread).start();
        new MainThread(4, breakThread).start();
        new MainThread(5, breakThread).start();
        new MainThread(6, breakThread).start();

        new Thread(breakThread).start();
    }
}