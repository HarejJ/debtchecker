package project.emp.fri.si.debtchecker;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements DecisionDialogFragment.CustomDialogListener {

    // UI references.
    private EditText mNameView;
    private EditText mSurnameView;
    private EditText mEmailView;
    private EditText mNicknameView;
    private EditText mUsernameView;
    private EditText mPasswordView0;
    private EditText mPasswordView1;
    private Button mRegisterBtnView;
    private View mProgressView;

    // Next activity
    Intent nextActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        mNameView = findViewById(R.id.register_atribute_name);
        mSurnameView = findViewById(R.id.register_atribute_surname);
        mEmailView = findViewById(R.id.register_atribute_email);
        mUsernameView = findViewById(R.id.register_atribute_username);
        mNicknameView = findViewById(R.id.register_atribute_nickname);
        mPasswordView0 = findViewById(R.id.register_atribute_password0);
        mPasswordView1 = findViewById(R.id.register_atribute_password1);
        mRegisterBtnView = findViewById(R.id.register_button);
        mProgressView = findViewById(R.id.login_progress);

        mRegisterBtnView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkInputs()) {
                    DecisionDialogFragment decisionDial = new DecisionDialogFragment();
                    decisionDial.show(getFragmentManager(), "Sure?");
                } else {
                    Toast.makeText(RegisterActivity.this, "Some data isn't entered correctly. Check again!", Toast.LENGTH_LONG).show();
                }
            }
        });

        mEmailView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // Check if email contains @ and has letters before and after @
                // TO DO!!!
                String content = ((EditText) v).getText().toString();
                if (!content.contains("@"))
                    ((EditText) v).setError("Email not correct");
            }
        });

        View.OnFocusChangeListener passFocus = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!mPasswordView0.getText().toString().equals(mPasswordView1.getText().toString())) {
                    mPasswordView0.setError("Passwords do not match");
                    mPasswordView1.setError("Passwords do not match");
                }
            }
        };
        mPasswordView0.setOnFocusChangeListener(passFocus);
        mPasswordView1.setOnFocusChangeListener(passFocus);

        // Set the values entered in the previus activity (login)
        String prev0 = getIntent().getStringExtra("loginName");
        String prev1 = getIntent().getStringExtra("password");

        if (prev0.contains("@"))
            mEmailView.setText(prev0);
        else
            mUsernameView.setText(prev0);

        mPasswordView0.setText(prev1);
        mPasswordView1.setText(prev1);
    }


    private boolean makeRegistration() {

        return DBInterface.insertUser(mNameView.getText().toString(), mSurnameView.getText().toString(), mEmailView.getText().toString(), mNicknameView.getText().toString(), mUsernameView.getText().toString(), mPasswordView0.getText().toString());
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

        boolean registrationOK = makeRegistration();

        if (registrationOK) {
            Toast.makeText(RegisterActivity.this, "You have registered successfully as: " + mUsernameView.getText().toString(), Toast.LENGTH_LONG).show();

            Intent nextActivityIntent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(nextActivityIntent);
        } else {
            Toast.makeText(RegisterActivity.this, "Registration failed. Retrying...", Toast.LENGTH_LONG).show();
        }

        registrationOK = makeRegistration();

        if (!registrationOK) {
            Toast.makeText(RegisterActivity.this, "Registration failed again. Try again later!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

        // Do nothing -- this is just a cancel button (Lahko odstraniva to, če želiva.. ne vem)
    }

    private boolean checkInputs() {

        if (mNameView.getText().toString().length() > 50) {
            mNameView.setError("Max 50 characters");
            return false;
        }

        if (mSurnameView.getText().toString().length() > 50) {
            mSurnameView.setError("Max 50 characters");
            return false;
        }

        if (mEmailView.getText().toString().length() > 50) {
            mEmailView.setError("Max 50 characters");
            return false;
        }

        if (mNicknameView.getText().toString().length() > 50) {
            mNicknameView.setError("Max 50 characters");
            return false;
        }

        if (mUsernameView.getText().toString().length() > 50) {
            mUsernameView.setError("Max 50 characters");
            return false;
        }

        if (mPasswordView0.getText().toString().length() > 50) {
            mPasswordView0.setError("Max 50 characters");
            return false;
        }

        if (mPasswordView1.getText().toString().length() > 50) {
            mPasswordView1.setError("Max 50 characters");
            return false;
        }

        if (mNameView.getText().toString().isEmpty()) {
            mNameView.setError("Field empty");
            return false;
        }

        if (mUsernameView.getText().toString().isEmpty()) {
            mUsernameView.setError("Field empty");
            return false;
        }

        if (mEmailView.getText().toString().isEmpty()) {
            mEmailView.setError("Field empty");
            return false;
        }

        if (mPasswordView0.getText().toString().isEmpty()) {
            mPasswordView0.setError("Field empty");
            return false;
        }

        if (mPasswordView1.getText().toString().isEmpty()) {
            mPasswordView1.setError("Field empty");
            return false;
        }

        if (!mPasswordView0.getText().toString().equals(mPasswordView1.getText().toString())) {
            mPasswordView0.setError("Passwords do not match");
            mPasswordView1.setError("Passwords do not match");
            return false;
        }


        return true;
    }
}

