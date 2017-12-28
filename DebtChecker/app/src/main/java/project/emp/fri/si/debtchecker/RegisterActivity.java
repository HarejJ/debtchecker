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
public class RegisterActivity extends AppCompatActivity implements RegisterDialogFragment.RegisterDialogListener {

    // UI references.
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private Button mSignInBtnView;
    private View mProgressView;
    private View mLoginFormView;

    // Next activity
    Intent nextActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSignInBtnView = (Button) findViewById(R.id.sign_in_button);
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);

        mSignInBtnView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean loginOK = tryLogin(mUsernameView.getText().toString(), mPasswordView.getText().toString());

                // Now enter the next activity if login was ok
                if (loginOK) {
                    nextActivity = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(nextActivity);

                    // Just display the value
                    Toast.makeText(RegisterActivity.this, "You are now logged in as: " + mUsernameView.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        // Setup next activity for register activity and add extra data to it
        nextActivity = new Intent(RegisterActivity.this, M);
    }


    private boolean tryLogin(String user, String password) {

        try {
            String out = new DBInterface("login", this.mProgressView).execute(user).get();
            String enPass = DBInterface.encryptSHA256(password);

            if (out.equals(enPass))
                return true;

            if (out.equals("noResult")) {
                DialogFragment regDial = new RegisterDialogFragment();
                regDial.show(getFragmentManager(), "Register?");
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    // The dialog fragment receives a reference to this Activity through the
    // Fragment.onAttach() callback, which it uses to call the following methods
    // defined by the NoticeDialogFragment.NoticeDialogListener interface
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // User touched the dialog's positive button

        // Start a register activity, so the user can register officially


    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

        // Do nothing -- this is just a cancel button (Lahko odstraniva to, če želiva.. ne vem)
    }
}

