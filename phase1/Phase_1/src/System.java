public abstract class System implements Runnable {

    public abstract void run();

    protected abstract void update();

    protected abstract void stop();
}