package project.emp.fri.si.debtchecker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private EditText mName, mSurname, mEmail, mUsername, mNewPassword0, mNewPassword1, mOldPassword;
    private Button mConfirmBtn, mUpdateBtn;
    private LinearLayout layoutOldPassword;

    private String oldName, oldSurname, oldEmail, oldUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mName = findViewById(R.id.settings_name);
        mSurname = findViewById(R.id.settings_surname);
        mEmail = findViewById(R.id.settings_email);
        mUsername = findViewById(R.id.settings_username);
        mNewPassword0 = findViewById(R.id.settings_password0);
        mNewPassword1 = findViewById(R.id.settings_password1);
        mOldPassword = findViewById(R.id.settings_password_old);

        mConfirmBtn = findViewById(R.id.settings_confirm_button);
        mUpdateBtn = findViewById(R.id.settings_update_button);

        layoutOldPassword = findViewById(R.id.settings_layout_old_password);


        // Fill up fields with data!
        mName.setText(MainActivity.loggedUser.getName());
        mSurname.setText(MainActivity.loggedUser.getSurname());
        mEmail.setText(MainActivity.loggedUser.getEmail());
        mUsername.setText(MainActivity.loggedUser.getUsername());

        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConfirmBtn.getText().toString().equals(getResources().getString(R.string.settings_button_edit))) {
                    mName.setEnabled(true);
                    mSurname.setEnabled(true);
                    mEmail.setEnabled(true);
                    mUsername.setEnabled(true);
                    mNewPassword0.setEnabled(true);
                    mNewPassword1.setEnabled(true);

                    mConfirmBtn.setText(R.string.settings_button_confirm);
                    mUpdateBtn.setEnabled(false);

                    mOldPassword.setText("");
                    layoutOldPassword.setVisibility(View.INVISIBLE);
                } else if (checkInputs()) {
                    mName.setEnabled(false);
                    mSurname.setEnabled(false);
                    mEmail.setEnabled(false);
                    mUsername.setEnabled(false);
                    mNewPassword0.setEnabled(false);
                    mNewPassword1.setEnabled(false);

                    mConfirmBtn.setText(R.string.settings_button_edit);
                    mUpdateBtn.setEnabled(true);

                    layoutOldPassword.setVisibility(View.VISIBLE);
                }
            }
        });

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DBInterface.encryptSHA256(mOldPassword.getText().toString()).equals(MainActivity.loggedUser.getPassword())) {
                    String password = MainActivity.loggedUser.getPassword();
                    if (mNewPassword0.getText().toString().length() > 0)
                        password = DBInterface.encryptSHA256(mNewPassword0.getText().toString());

                    DBInterface.updateUser(MainActivity.loggedUser.getId(), mName.getText().toString(), mSurname.getText().toString(), mEmail.getText().toString(), mUsername.getText().toString(), password);

                    Toast.makeText(SettingsActivity.this, "Podatki uspešno posodobljeni", Toast.LENGTH_SHORT).show();

                    mUpdateBtn.setEnabled(false);
                    mOldPassword.setText("");
                    layoutOldPassword.setVisibility(View.INVISIBLE);
                } else {
                    mOldPassword.setError("Geslo ni pravilno!");
                }
            }
        });
    }

    private void upateOldStrings() {
        oldName = mName.getText().toString();
        oldSurname = mSurname.getText().toString();
        oldEmail = mEmail.getText().toString();
        oldUsername = mUsername.getText().toString();
    }

    private boolean checkInputs() {

        if (mName.getText().toString().length() > 50) {
            mName.setError("Max 50 črk");
            return false;
        }

        if (mSurname.getText().toString().length() > 50) {
            mSurname.setError("Max 50 črk");
            return false;
        }

        if (mEmail.getText().toString().length() > 50) {
            mEmail.setError("Max 50 črk");
            return false;
        }

        if (mUsername.getText().toString().length() > 50) {
            mUsername.setError("Max 50 črk");
            return false;
        }

        if (mNewPassword0.getText().toString().length() > 50) {
            mNewPassword0.setError("Max 50 črk");
            return false;
        }

        if (mNewPassword1.getText().toString().length() > 50) {
            mNewPassword1.setError("Max 50 črk");
            return false;
        }

        if (mName.getText().toString().length() < 3) {
            mName.setError("Min 3 črke");
            return false;
        }

        if (mUsername.getText().toString().length() < 3) {
            mUsername.setError("Min 3 črke");
            return false;
        }

        if (mEmail.getText().toString().length() < 3) {
            mEmail.setError("Min 3 črke");
            return false;
        }

        if (!mNewPassword0.getText().toString().equals(mNewPassword1.getText().toString())) {
            mNewPassword0.setError("Gesli se ne ujemata");
            mNewPassword1.setError("Gesli se ne ujemata");
            return false;
        }


        return true;
    }

}
