public abstract class UserSystem implements Runnable{
    @Override
    public abstract void run();

    protected abstract void init();
    protected abstract void stop();

    public abstract String getNextUser();

    protected abstract int getNextSystem();
}
