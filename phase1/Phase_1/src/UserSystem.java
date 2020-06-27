public abstract class UserSystem implements Runnable {

    public abstract void run();

    protected abstract void update();

    protected abstract void stop();
}
