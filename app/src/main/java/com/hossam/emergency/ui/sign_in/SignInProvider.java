package com.hossam.emergency.ui.sign_in;

public interface SignInProvider {

    void validData();

    void convertToSignUp();

    void signInAccout(String email, String pass);

    void forgetPassword();
}
