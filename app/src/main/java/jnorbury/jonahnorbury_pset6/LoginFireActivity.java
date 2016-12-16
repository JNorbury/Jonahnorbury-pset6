package jnorbury.jonahnorbury_pset6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by jonah on 09-Dec-16.
 *
 * LoginFireActivity allows users to sign in to or register on the firebase server.
 * Users that register with a pre-existing account
 * are automatically signed in (if password is correct).
 *
 */

public class LoginFireActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mEmailfield;
    private EditText mPwdField;

    private ProgressDialog progress;

    private static final String TAG =
            "LoginFireActivity";

    // establish connection with server and start a listener for sign-in state
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fire);
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    // registers users with new password and email, making sure they suffice the standards of
    // email = x@y.z etc.
    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginFireActivity.this, R.string.registration_err_toast_txt,
                                    Toast.LENGTH_SHORT).show();
                        } else{

                        }

                    }
                });
    }

    // signs in user unless email and/or password not regstered/incorrect respectively.
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginFireActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(LoginFireActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


    // validateform checks the legitimacy of the text entries for email and password,
    // does not check authorization of user.
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailfield.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailfield.setError("Required.");
            valid = false;
        } else {
            mEmailfield.setError(null);
        }

        String password = mPwdField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPwdField.setError("Required.");
            valid = false;
        } else {
            mPwdField.setError(null);
        }

        return valid;
    }

    // from http://stackoverflow.com/questions/16611198/how-to-know-which-button-called-a-method
    // depending on the button, the respective method is called
    @Override
    public void onClick(View v){
        mEmailfield = (EditText) findViewById(R.id.emailET);
        mPwdField = (EditText) findViewById(R.id.passwordET);

        String password = mPwdField.getText().toString();
        String email = mEmailfield.getText().toString();

        switch (v.getId()) {
            case R.id.registerBTN:
                createAccount(email, password);
            case R.id.signinBTN:
                signIn(email, password);
        }
    }
}
