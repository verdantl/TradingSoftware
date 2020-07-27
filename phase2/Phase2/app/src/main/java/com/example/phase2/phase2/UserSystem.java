package com.example.phase2.phase2;

public abstract class UserSystem implements Runnable{
    protected boolean running = false;

    /**
     * Runs the loop of the system
     */
    @Override
    public abstract void run();

    /**
     * Initiates the running loop of the system
     */
    protected void init(){
        running = true;
    }

    /**
     * Stops the running loop of the system
     */
    protected void stop(){
        running = false;
    }

    /**
     * Returns a string representing the next user of the system
     * @return a string representing the username of the next user
     */
    public String getNextUser(){
        return null;
    }

    /**
     * Gets the next system that should be run
     * @return an integer representing the next system that should be run in the programet
     */
    protected int getNextSystem(){
        return 0;
    }
}
