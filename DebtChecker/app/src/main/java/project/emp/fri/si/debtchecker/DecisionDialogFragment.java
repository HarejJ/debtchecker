package project.emp.fri.si.debtchecker;

import android.app.Dialog;
import android.os.Bundle;

/**
 * Created by Jan on 29. 12. 2017.
 */

public class DecisionDialogFragment extends CustomDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        savedInstanceState = new Bundle();
        savedInstanceState.putInt("mainMsgID", R.string.prompt_user_sure);
        savedInstanceState.putInt("positiveMsgID", R.string.anwser_yes);
        savedInstanceState.putInt("negativeMsgID", R.string.anwser_no);
        return super.onCreateDialog(savedInstanceState);
    }
}
