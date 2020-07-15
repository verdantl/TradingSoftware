public abstract class UserSystem implements Runnable{
    //Ok so for this class i was thinking of removing the implement runnable cuz i needed to edit the parameter of the run method.
    //SO it takes in a parametre as a user
    // If there was a specific reason to include
    //runnable then we can use that but we gotta fix the error
    public abstract void run(/*User user*/);

    protected abstract void update();

    protected abstract void stop();
}
