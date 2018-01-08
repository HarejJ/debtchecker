package project.emp.fri.si.debtchecker;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
public class LoginActivity extends AppCompatActivity implements RegisterDialogFragment.CustomDialogListener {

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private Button mSignInBtnView;
    private View mProgressView;
    private View mLoginFormView;

    // Next activity
    Intent nextActivityIntent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mSignInBtnView = findViewById(R.id.sign_in_button);
        mProgressView = findViewById(R.id.login_progress);

        mSignInBtnView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int userId = tryLogin(mUsernameView.getText().toString(), mPasswordView.getText().toString());

                // Now enter the next activity if login was ok
                if (userId >= 0) {
                    nextActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                    nextActivityIntent.putExtra("userID", userId);
                    startActivity(nextActivityIntent);

                    // Just display the value
                    Toast.makeText(LoginActivity.this, "You are now logged in as: " + mUsernameView.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private int tryLogin(String user, String password) {

        try {
            String[] out = new DBInterface("login", this.mProgressView).execute(user).get().split(" ");

            if (out[0].equals("noResult")) {
                // Setup next activity for register activity and add extra data to it (in case a user selects yes)
                nextActivityIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                nextActivityIntent.putExtra("loginName", user);
                nextActivityIntent.putExtra("password", password);

                // Show a dialog fragment
                RegisterDialogFragment regDial = new RegisterDialogFragment();
                regDial.show(getFragmentManager(), "Register?");
            }else if (out[1].equals(DBInterface.encryptSHA256(password)))
                return Integer.parseInt(out[0]);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return -2;
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

        // Start a register activity, so the user can register officially
        startActivity(nextActivityIntent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

        // Do nothing -- this is just a cancel button (Lahko odstraniva to, če želiva.. ne vem)
    }
}

