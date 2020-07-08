public class SignupPrompts {

    public void adminOrTrader(){
        System.out.println("Enter [0] if you would like to signup for an admin request." +
                " Enter [1] to sign up for an account. Enter [2] to go back to the login screen");
    }
    public void displayCreateUserName(){
        System.out.println("Please enter the username you would like to create. Do not include any spaces.");
    }

    public void displayUserAvailable(boolean value){
        if (value){
            System.out.println("Username is available.");
        }
        else{
            System.out.println("Username is unavailable. Try another.");
        }
    }

    public void commandNotRecognized(){
        System.out.println("Command not recognized. Returning to Login.");
    }

    public void displayCreatePassword(){
        System.out.println("Please enter the password you would like to use for this account.");
    }

    public void displayAccountSuccessful(){
        System.out.println("Account successfully created! Returning to login menu...");
    }
}
