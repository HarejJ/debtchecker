package project.emp.fri.si.debtchecker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by Jan on 28. 12. 2017.
 */

@SuppressLint("ValidFragment")
public class CustomDialogFragment extends DialogFragment {

    // Integer values for string dialogs used
    private int messageID;
    private int positiveID;
    private int negativeID;


    @SuppressLint("ValidFragment")
    CustomDialogFragment(int mainMsgID, int positiveMsgID, int negativeMsgID) {
        super();
        this.messageID = mainMsgID;
        this.positiveID = positiveMsgID;
        this.negativeID = negativeMsgID;
    }


    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface CustomDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);

        void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    CustomDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (CustomDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(this.messageID)
                .setPositiveButton(this.positiveID, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(CustomDialogFragment.this);
                    }
                })
                .setNegativeButton(this.negativeID, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(CustomDialogFragment.this);
                    }
                });
        return builder.create();
    }


}
