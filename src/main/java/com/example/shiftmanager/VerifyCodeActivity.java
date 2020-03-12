package com.example.shiftmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.raycoarana.codeinputview.CodeInputView;

import java.util.concurrent.TimeUnit;

public class VerifyCodeActivity extends AppCompatActivity {

    private String mVerificationId;
    private Button SignUp_BTN_signup;
    private FirebaseAuth mAuth;
    private CodeInputView VerifyCode_TXT_code;
    private UserDBManager mUserDBManager;
    private String mobileNumber, firstName, lastName;
    MySharePreferences msp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        init();
        msp = new MySharePreferences(getApplication().getApplicationContext());
        //getting mobile number from the previous activity
        //and sending the verification code to the number
        Intent intent = getIntent();
        mobileNumber = intent.getStringExtra("mobile");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        msp.putString(Keys.CREATE_USER , mobileNumber);
        msp.putString(Keys.SAVE_NAME , firstName);

        sendVerificationCode(mobileNumber);


    }

    View.OnClickListener signupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String code = VerifyCode_TXT_code.getCode();
            if (code.isEmpty() || code.length() < 6) {
                VerifyCode_TXT_code.setError("Enter valid code");
                VerifyCode_TXT_code.requestFocus();
                return;
            }

            //verifying the code entered manually
            verifyVerificationCode(code);
        }
    };

    private void init()
    {
        findViews();
        //Init Firebase
        mAuth = FirebaseAuth.getInstance();
        mUserDBManager = new UserDBManager(getApplicationContext());
        //Init signUp button
        SignUp_BTN_signup.setOnClickListener(signupListener);
    }

    /**
     * the method is sending verification code
     * the country id is concatenated
     * you can take the country id as user input as well
     *
     * @param mobile
     */
    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }

    /**
     *
     */
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                VerifyCode_TXT_code.setCode(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.i("ERROR", e.getMessage());
            Toast.makeText(VerifyCodeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };


    /**
     *
     */
    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }


    /**
     *
     */
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyCodeActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            Intent intent = new Intent(VerifyCodeActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            finish();
                            mUserDBManager.signInToProfile(mobileNumber, firstName, lastName);
                        } else {
                            //verification unsuccessful.. display an error message
                            Toast.makeText(VerifyCodeActivity.this, "Wrong Code... Try Again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     *
     */
    private void findViews() {
        SignUp_BTN_signup = findViewById(R.id.SignUp_BTN_signup);
        VerifyCode_TXT_code = findViewById(R.id.VerifyCode_TXT_code);
    }
}
