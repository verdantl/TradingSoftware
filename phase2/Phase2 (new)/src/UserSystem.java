public abstract class UserSystem implements Runnable{
    protected boolean running = false;
    @Override
    public abstract void run();

    protected void init(){
        running = true;
    }

    protected void stop(){
        running = false;
    }

    public abstract String getNextUser();

    protected abstract int getNextSystem();
}
