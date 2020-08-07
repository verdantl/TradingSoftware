package com.example.phase2.phase2;

public interface Loginable {

    boolean checkUsername(String username);

    boolean login(String username, String password);

    void changePassword(String username, String password);

}
