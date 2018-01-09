package project.emp.fri.si.debtchecker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

public class PaymentsHistoryActivity extends AppCompatActivity {

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

        ScrollView mainScrollView = findViewById(R.id.paymentHistory_scrollViewLayout);
        mainScrollView.fullScroll(View.FOCUS_DOWN);
        LinearLayout mainLayout = findViewById(R.id.paymentHistory_mainLayout);
        //mainLayout.setGravity(Gravity.START);

        ArrayList<Payment> listOfPayments = MainActivity.loggedUser.getPayments();

        // Get a progressBar and set it min and max
        ProgressBar progress = findViewById(R.id.paymentHistory_progressBar);
        progress.setMax(listOfPayments.size());

        new LoadPaymentHistoryTask(this, progress, mainLayout, listOfPayments).execute();

        /*
        Collections.sort(listOfPayments);

        String[] userToWriteData;
        String name, surname;
        Date date;
        double amount;
        LinearLayout contentLayout;
        LinearLayout contentInnerLayout;
        TextView nameView;
        TextView dateView;
        TextView amountView;
        boolean lineColor = true;
        boolean textColor;

        ArrayList<LinearLayout> allEntrys = new ArrayList<>();

        SimpleDateFormat sDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        for (Payment p : listOfPayments) {
            int payerId = p.getPayerId();
            int recipientId = p.getRecipientId();

            date = p.getDate();
            amount = p.getAmount();

            if (payerId == MainActivity.loggedUser.getId()) {
                userToWriteData = DBInterface.queryUserAll(recipientId).split(" ");
                textColor = true; //rdeče
            } else {
                userToWriteData = DBInterface.queryUserAll(payerId).split(" ");
                textColor = false;
            }

            name = userToWriteData[1];
            surname = userToWriteData[2];

            contentLayout = new LinearLayout(this);
            contentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            contentLayout.setPadding(pxToDp(20), pxToDp(5), pxToDp(20), pxToDp(5));
            if (lineColor)
                contentLayout.setBackgroundColor(getResources().getColor(R.color.paymentHistoryColor));
            contentLayout.setOrientation(LinearLayout.VERTICAL);

            contentInnerLayout = new LinearLayout(this);
            contentInnerLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            contentInnerLayout.setOrientation(LinearLayout.HORIZONTAL);

            contentLayout.addView(contentInnerLayout);

            nameView = new TextView(this);
            nameView.setTextSize(24);
            nameView.setTextColor(getResources().getColor(R.color.colorPrimary));
            nameView.setText(name + " " + surname);

            dateView = new TextView(this);
            dateView.setTextSize(24);
            dateView.setTextColor(getResources().getColor(R.color.colorPrimary));
            dateView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dateView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            dateView.setText(sDateFormat.format(date));

            amountView = new TextView(this);
            amountView.setTextSize(18);

            if (textColor) {
                amountView.setTextColor(getResources().getColor(R.color.paymentHistoryPaied));
                amountView.setText("-" + amount + "€");
            } else {
                amountView.setTextColor(getResources().getColor(R.color.paymentHistoryRecived));
                amountView.setText("+" + amount + "€");
            }

            lineColor = !lineColor;


            contentInnerLayout.addView(nameView);
            contentInnerLayout.addView(dateView);
            contentLayout.addView(amountView);

            allEntrys.add(contentLayout);
        }

        progress.setVisibility(View.GONE);
        mainLayout.setGravity(Gravity.START);
        for (LinearLayout ll : allEntrys)
            mainLayout.addView(ll);
        */
    }

    public int pxToDp(int dp) {
        int padding_in_dp = dp;
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        return padding_in_px;
    }
}
