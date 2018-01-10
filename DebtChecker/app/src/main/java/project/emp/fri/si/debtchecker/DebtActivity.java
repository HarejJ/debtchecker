package project.emp.fri.si.debtchecker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class DebtActivity extends AppCompatActivity {

    private LinearLayout main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debt);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        main_layout = findViewById(R.id.debts_activity_main_layoutId);

        ArrayList<Payment> listOfPayments = MainActivity.loggedUser.getPayments();
        Collections.sort(listOfPayments);

        final HashMap<Integer, Double> usersData = new HashMap<>();

        for (Payment p : listOfPayments){
            int payerId = p.getPayerId();
            int recipientId = p.getRecipientId();

            if (payerId == MainActivity.loggedUser.getId()) {
                if (usersData.get(recipientId) == null)
                    usersData.put(recipientId, 0.0);
                usersData.put(recipientId, usersData.get(recipientId) - p.getAmount());
            } else {
                if (usersData.get(payerId) == null)
                    usersData.put(payerId, 0.0);
                usersData.put(payerId, usersData.get(payerId) + p.getAmount());
            }
        }

        LinearLayout outerVerticalLinearLayout;
        LinearLayout innerHorizontalLinearLayout;
        TextView userNameTextView;
        TextView amountTextView;

        String username;
        double value;
        boolean lineColor = false;
        for (int i : usersData.keySet()){
            username = DBInterface.queryUserNames(i);
            value = usersData.get(i);

            outerVerticalLinearLayout = new LinearLayout(this);
            outerVerticalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            outerVerticalLinearLayout.setOrientation(LinearLayout.VERTICAL);
            if (lineColor)
                outerVerticalLinearLayout.setBackgroundColor(getResources().getColor(R.color.paymentHistoryColor));

            innerHorizontalLinearLayout = new LinearLayout(this);
            innerHorizontalLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            innerHorizontalLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

            userNameTextView = new TextView(this);
            userNameTextView.setGravity(Gravity.CENTER);
            userNameTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            userNameTextView.setTextSize(20);
            userNameTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            userNameTextView.setText(username);

            amountTextView = new TextView(this);
            amountTextView.setGravity(Gravity.END);
            amountTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            amountTextView.setTextSize(24);
            amountTextView.setText(value+"");

            lineColor = !lineColor;

            main_layout.addView(outerVerticalLinearLayout);
            outerVerticalLinearLayout.addView(innerHorizontalLinearLayout);
            innerHorizontalLinearLayout.addView(userNameTextView);
            innerHorizontalLinearLayout.addView(amountTextView);

        }

    }

}
