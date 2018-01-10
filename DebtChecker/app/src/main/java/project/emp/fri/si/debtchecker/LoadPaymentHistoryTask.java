package project.emp.fri.si.debtchecker;

import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Jan on 9. 01. 2018.
 */

public class LoadPaymentHistoryTask extends AsyncTask<Void, Integer, Void> {

    private PaymentsHistoryActivity activityInstance;
    private ProgressBar progress;
    private LinearLayout mainLayout;
    private ArrayList<Payment> payments;
    private HashMap<Integer, String[]> allUsedUsers;

    LoadPaymentHistoryTask(PaymentsHistoryActivity activityInstance, ProgressBar progress, LinearLayout mainLayout, ArrayList<Payment> payments) {

        this.activityInstance = activityInstance;
        this.progress = progress;
        this.mainLayout = mainLayout;
        this.payments = payments;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        allUsedUsers = new HashMap<>();

        for (Payment p : this.payments) {
            if (p.getPayerId() == MainActivity.loggedUser.getId()) {
                if(!allUsedUsers.containsKey(p.getRecipientId()))
                    allUsedUsers.put(p.getRecipientId(), DBInterface.queryUserAll(p.getRecipientId()).split(" "));
            }else{
                if(!allUsedUsers.containsKey(p.getPayerId()))
                    allUsedUsers.put(p.getPayerId(), DBInterface.queryUserAll(p.getPayerId()).split(" "));
            }
        }

        progress.setVisibility(View.VISIBLE);
        mainLayout.setGravity(Gravity.CENTER);

    }

    @Override
    protected Void doInBackground(Void... voids) {

        Collections.sort(this.payments);

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
        int currentElement = 0;

        for (Payment p : this.payments) {
            int payerId = p.getPayerId();
            int recipientId = p.getRecipientId();

            date = p.getDate();
            amount = p.getAmount();

            if (payerId == MainActivity.loggedUser.getId()) {
                userToWriteData = allUsedUsers.get(recipientId);
                textColor = true; //rdeče
            } else {
                userToWriteData = allUsedUsers.get(payerId);
                textColor = false;
            }

            name = userToWriteData[1];
            surname = userToWriteData[2];

            contentLayout = new LinearLayout(activityInstance);
            contentLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            contentLayout.setPadding(pxToDp(20), pxToDp(5), pxToDp(20), pxToDp(5));
            if (lineColor)
                contentLayout.setBackgroundColor(activityInstance.getResources().getColor(R.color.paymentHistoryColor));
            contentLayout.setOrientation(LinearLayout.VERTICAL);

            contentInnerLayout = new LinearLayout(activityInstance);
            contentInnerLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            contentInnerLayout.setOrientation(LinearLayout.HORIZONTAL);

            contentLayout.addView(contentInnerLayout);

            nameView = new TextView(activityInstance);
            nameView.setTextSize(24);
            nameView.setTextColor(activityInstance.getResources().getColor(R.color.colorPrimary));
            nameView.setText(name + " " + surname);

            dateView = new TextView(activityInstance);
            dateView.setTextSize(24);
            dateView.setTextColor(activityInstance.getResources().getColor(R.color.colorPrimary));
            dateView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dateView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            dateView.setText(sDateFormat.format(date));

            amountView = new TextView(activityInstance);
            amountView.setTextSize(18);

            if (textColor) {
                amountView.setTextColor(activityInstance.getResources().getColor(R.color.paymentHistoryPaied));
                amountView.setText("-" + amount + "€");
            } else {
                amountView.setTextColor(activityInstance.getResources().getColor(R.color.paymentHistoryRecived));
                amountView.setText("+" + amount + "€");
            }
            lineColor = !lineColor;

            contentInnerLayout.addView(nameView);
            contentInnerLayout.addView(dateView);
            contentLayout.addView(amountView);

            allEntrys.add(contentLayout);
            publishProgress(++currentElement);
        }

        progress.setVisibility(View.GONE);
        mainLayout.setGravity(Gravity.START);
        for (LinearLayout ll : allEntrys)
            mainLayout.addView(ll);

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... integers) {
        super.onProgressUpdate(integers);

        progress.setProgress(integers[0]);
    }

    private int pxToDp(int dp) {
        double scale = activityInstance.getResources().getDisplayMetrics().density;
        return (int) Math.round(dp * scale + 0.5f);
    }
}
