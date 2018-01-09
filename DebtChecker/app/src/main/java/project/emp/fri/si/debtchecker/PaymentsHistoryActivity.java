package project.emp.fri.si.debtchecker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PaymentsHistoryActivity extends AppCompatActivity {

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainLayout = findViewById(R.id.paymentHistory_mainLayout);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        Payment[] listOfPayments = MainActivity.loggedUser.getPayments();

        // id znesek datum idPlacnik idPrejemnik
        String[] userToWriteData;
        String date, name, surname;
        double amount;
        LinearLayout contentLayout;
        LinearLayout contentInnerLayout;
        TextView nameView;
        TextView dateView;
        TextView amountView;
        for (int i = 0; i < listOfPayments.length; i++){
            int payerId = listOfPayments[i].getPayerId();
            int recipientId = listOfPayments[i].getRecipientId();

            date = listOfPayments[i].getDate();
            amount = listOfPayments[i].getAmount();

            if (payerId == MainActivity.loggedUser.getId()){
                userToWriteData = DBInterface.queryUserAll(recipientId).split(" ");
            } else {
                userToWriteData = DBInterface.queryUserAll(payerId).split(" ");
            }

            name = userToWriteData[1];
            surname = userToWriteData[2];

            contentLayout = new LinearLayout(this);
            contentLayout.setOrientation(LinearLayout.VERTICAL);

            contentInnerLayout = new LinearLayout(this);
            contentInnerLayout.setOrientation(LinearLayout.HORIZONTAL);

            contentLayout.addView(contentInnerLayout);
            mainLayout.addView(contentLayout);

            nameView = new TextView(this);
            nameView.setText(name+" "+surname);

            dateView = new TextView(this);
            dateView.setText(date);

            amountView = new TextView(this);
            amountView.setText(amount+"");

            contentInnerLayout.addView(nameView);
            contentInnerLayout.addView(dateView);
            contentLayout.addView(amountView);
        }
    }
}
