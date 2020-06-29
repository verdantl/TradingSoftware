public class SignupSystem {

    //Since signup system only needs to create a new user, it only needs the traderActions class (at least thats what i think)
    //Note that it doesn't extend UserSystem as the run method doesn't need a user- We can change this, idk if its a good idea atm
    TraderActions traderActions;
    SignupPrompts signupPrompts;

    //Here we instantiate traderActions so we can use it.
    public SignupSystem(/*TraderActions traderActions*/){
        //this.traderActions = traderActions;
        this.signupPrompts = new SignupPrompts();
    }
    //This is the run method
    //It makes use of signup prompts to ask the user to create an account. Theres no need to return a user because
    //The user gets added to traderActions in HERE and we ask them to log in again in LoginSystem
    public void run(){}

    //Now its time to go back to loginSystem
}
