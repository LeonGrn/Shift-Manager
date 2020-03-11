package com.example.shiftmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    private Button SignUp_BTN_signup;
    private TextView SignUp_TXT_firstName, SignUp_TXT_lastName, SignUp_TXT_phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViews();

        SignUp_BTN_signup.setOnClickListener(signUpListener);


    }

    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mobileNumber = SignUp_TXT_phoneNumber.getText().toString().trim();
            String firstName = SignUp_TXT_firstName.getText().toString().trim();
            String lastName = SignUp_TXT_lastName.getText().toString().trim();

            if (firstName.isEmpty()) {
                SignUp_TXT_firstName.setError("Enter first name");
                SignUp_TXT_firstName.requestFocus();
                return;
            }
            if (lastName.isEmpty()) {
                SignUp_TXT_lastName.setError("Enter last name");
                SignUp_TXT_lastName.requestFocus();
                return;
            }
            if (mobileNumber.isEmpty() || mobileNumber.length() < 12) {
                SignUp_TXT_phoneNumber.setError("Enter a valid mobile");
                SignUp_TXT_phoneNumber.requestFocus();
                return;
            }
            moveToVerifyCodeActivity(mobileNumber, firstName, lastName);
        }
    };

    /**
     *
     */
    private void findViews() {
        SignUp_BTN_signup = findViewById(R.id.SignUp_BTN_signup);
        SignUp_TXT_firstName = findViewById(R.id.SignUp_TXT_firstName);
        SignUp_TXT_lastName = findViewById(R.id.SignUp_TXT_lastName);
        SignUp_TXT_phoneNumber = findViewById(R.id.SignUp_TXT_phoneNumber);
    }

    /**
     *
     */
    private void moveToVerifyCodeActivity(String mobileNumber, String firstName, String lastName) {
        Intent intent = new Intent(getApplicationContext(), VerifyCodeActivity.class);
        intent.putExtra("mobile", mobileNumber);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        startActivity(intent);
        finish();
    }

}
