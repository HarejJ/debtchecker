package project.emp.fri.si.debtchecker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewPaymentActivity extends AppCompatActivity {

    private EditText dateView;
    private ImageView submitButton;
    private AutoCompleteTextView recivierTextView;
    private EditText amountView;
    private String[] userNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_payment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        dateView = findViewById(R.id.new_payment_date_view);
        submitButton = findViewById(R.id.new_payment_submit_button);
        recivierTextView = findViewById(R.id.recivier_textView);
        amountView = findViewById(R.id.amount_textView);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(c.getTime());
        dateView.setText(formattedDate);

        final HashMap<String, String> usersData = new HashMap<>();
        String[] userNames = DBInterface.queryUsernamesIds().split("\n");

        String[] temp;
        String onlyUsernames = "";
        for (int i = 0; i < userNames.length; i++){
            temp = userNames[i].split(" ");
            usersData.put(temp[1], temp[0]);
            onlyUsernames += temp[1] + " ";
        }

        userNames = onlyUsernames.split(" ");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, userNames);
        recivierTextView.setAdapter(adapter);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = recivierTextView.getText().toString();
                double amount = Double.parseDouble(amountView.getText().toString().replace(',', '.'));
                try {
                    DBInterface.insertPayment(amount, MainActivity.loggedUser.getId() + "", usersData.get(recipient));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                recivierTextView.setText("");
                amountView.setText("");
                Toast.makeText(NewPaymentActivity.this, "Plačilo uspešno dodano!", Toast.LENGTH_SHORT).show();
                MainActivity.loggedUser.updatePayments();
            }
        });
    }

}
