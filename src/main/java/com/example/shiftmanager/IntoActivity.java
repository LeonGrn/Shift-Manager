package com.example.shiftmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class IntoActivity extends AppCompatActivity
{
    ProgressBar intro_progressbar;
    private ImageView into_IMG_logo;
    private LinearLayout into_LAY_linear;
    private Button into_btn_signup;
    private TextView or_txt_or;
    private Button into_btn_go;
    private EditText SignIn_txt_phoneNumber;
    private TextView into_TXT_welcomeText;
    private UserDBManager mUserDBManager;
    MySharePreferences msp;

    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_into);
        findViews();
        init();
        msp = new MySharePreferences(getApplicationContext());
        animateLogo();



        if (msp.getInt(Keys.USER_LOGGED_IN , 0) == 1) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            Intent intent = new Intent(IntoActivity.this , MainActivity.class);
            startActivity(intent);
            finish();
        }

        msp.putInt(Keys.USER_LOGGED_IN, 1);

        into_btn_go.setOnClickListener(signInListener);
        into_btn_signup.setOnClickListener(signUpListener);
    }

    private void init() {
        mUserDBManager = new UserDBManager(getApplicationContext());
    }


    private void animateLogo()
    {
        into_IMG_logo.animate().setStartDelay(4000).scaleX(.5f).scaleY(.5f).translationYBy(-450).setDuration(1000).setListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animator)
            {
                intro_progressbar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animator)
            {
                animateWelcomeText();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();
    }

    View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(IntoActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        }
    };

    View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String mobileNumber = SignIn_txt_phoneNumber.getText().toString().trim();
            if (mobileNumber.isEmpty() || mobileNumber.length() < 13) {
                SignIn_txt_phoneNumber.setError("Enter a valid mobile");
                SignIn_txt_phoneNumber.requestFocus();
                return;
            }
//            Intent intent = new Intent(IntoActivity.this, VerifyCodeActivity.class);
//            intent.putExtra("mobile" , mobileNumber);
//            startActivity(intent);
//            finish();
            mUserDBManager.checkIfNumberExist(mobileNumber);
        }
    };

    private void animateWelcomeText() {
        into_LAY_linear.animate().setStartDelay(100).setDuration(1000).alpha(1).translationYBy(-20).start();
    }

    private void findViews()
    {
        into_IMG_logo = findViewById(R.id.into_IMG_logo);
        intro_progressbar = findViewById(R.id.intro_BAR_progress);
        into_btn_signup = findViewById(R.id.into_btn_signup);
        or_txt_or = findViewById(R.id.or_txt_or);
        into_btn_go = findViewById(R.id.into_btn_go);
        SignIn_txt_phoneNumber = findViewById(R.id.SignIn_txt_phoneNumber);
        into_TXT_welcomeText =findViewById(R.id.into_TXT_welcomeText);
        into_LAY_linear = findViewById(R.id.into_LAY_linear);
    }
}