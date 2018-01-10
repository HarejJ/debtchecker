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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ScrollView mainScrollView = findViewById(R.id.paymentHistory_scrollViewLayout);
        mainScrollView.fullScroll(View.FOCUS_DOWN);
        LinearLayout mainLayout = findViewById(R.id.paymentHistory_mainLayout);
        //mainLayout.setGravity(Gravity.START);

        ArrayList<Payment> listOfPayments = MainActivity.loggedUser.getPayments();

        // Get a progressBar and set it min and max
        ProgressBar progress = findViewById(R.id.paymentHistory_progressBar);
        progress.setMax(listOfPayments.size());

        new LoadPaymentHistoryTask(this, progress, mainLayout, listOfPayments).execute();
    }

    public int pxToDp(int dp) {
        int padding_in_dp = dp;
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
        return padding_in_px;
    }
}
