package com.hossam.emergency.ui.sign_in;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.hossam.emergency.R;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.ui.main.MainActivity;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui_component.UIComponenet;
import com.hossam.emergency.utils.StringResouces;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("RestrictedApi")
public class SignInActivity extends UIComponenet implements ActivityHelper {

    static final String TAG = "LoginActivity";
    static final int RC_SIGN_IN = 9001;
    @BindView(R.id.email_sign_in)
    EditText email;
    @BindView(R.id.password_sign_in)
    EditText password;
    @BindView(R.id.remember_sign_in)
    CheckBox remember;
    @BindView(R.id.forget_sign_in)
    TextView forget;

    //    @BindView(R.id.facebook_login_fragment)
//    LoginButton loginButton;
//
    @BindView(R.id.btn_sign_in)
    Button sign_in;
    @BindView(R.id.btn_sign_up_sign_in)
    Button sign_up;
    @BindView(R.id.google_sign_in)
    ImageView google;

//    @BindView(R.id.terms_sign_in)
//    TextView terms;
//    @BindView(R.id.privacy_sign_in)
//    TextView privacy;

    UiSignIn uiSignIn;
    ControllerSignIn controller;
    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final StringResouces resouces = StringResouces.getInstance();
    private Twar2App twar2App;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        bindActivity();
        initActivity();


    }

    @Override
    public void bindActivity() {
        overridePendingTransition(R.anim.fade, R.anim.fade);
        initProgress(resouces.getStringResource(R.string.please_waiting_progress_message, this), this);

        //  newtonCradleLoading =findViewById(R.id.newton_cradle_loading);

        uiSignIn = new UiSignIn(email, password, forget, null, null, remember, sign_in, sign_up, null, null);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build();
        // [END config_signin]

        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void initActivity() {
        twar2App = (Twar2App) getApplicationContext();
        controller = new ControllerSignIn(this, uiSignIn, twar2App);
        controller.convertToSignUp();
        controller.forgetPassword();

//        google.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signInGoogle();
//            }
//        });

        fbLogin();


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        showProgress();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    finish();

                } else {

                    Toast.makeText(SignInActivity.this, "Error in sign in", Toast.LENGTH_SHORT).show();

                }

                cancelProgress();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == RC_SIGN_IN) {
//
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                firebaseAuthWithGoogle(account);
//
//            } catch (ApiException e) {
//
//            }
//        }
    }


    private void fbLogin() {

        mCallbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook_login_debug" + loginResult.getAccessToken());
                handleFacebookAccessToken(loginResult.getAccessToken());
                Toast.makeText(SignInActivity.this, "facebook Ok",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

                Toast.makeText(SignInActivity.this, "facebook Cancel",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(SignInActivity.this, "facebook error : " + error,
                        Toast.LENGTH_LONG).show();
            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.hossam.emergency",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    private void handleFacebookAccessToken(AccessToken token) {

        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            startActivity(new Intent(SignInActivity.this, MainActivity.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }
//    private void signInGoogle() {
//        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }

}
