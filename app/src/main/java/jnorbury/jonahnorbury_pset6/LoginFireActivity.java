package jnorbury.jonahnorbury_pset6;

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

import static android.R.attr.onClick;
import static jnorbury.jonahnorbury_pset6.R.id.password;
import static jnorbury.jonahnorbury_pset6.R.string.email;

public class LoginFireActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText mEmailfield;
    private EditText mPwdField;

    private static final String TAG =
            "LoginFireActivity";

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

    private void createAccount(String email, String password) {
//        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressDialog();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginFireActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressDialog();

        // [START sign_in_with_email]
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
                        }

                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            mStatusTextView.setText(R.string.auth_failed);
//                        }
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
//        updateUI(null);
    }

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
    @Override
    public void onClick(View v){
        mEmailfield = (EditText) findViewById(R.id.emailET);
        mPwdField = (EditText) findViewById(R.id.passwordET);

        String password = mPwdField.getText().toString();
        String email = mEmailfield.getText().toString();

        switch (v.getId()) {
            case R.id.registerBTN:
                createAccount(email, password);
                break;
            case R.id.signinBTN:
                signIn(email, password);
//            case R.id.signoutBTN:
//                signOut();
        }
    }
}
