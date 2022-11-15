package com.hossam.emergency.edit_case;

import android.app.Activity;
import android.text.TextUtils;

import com.hossam.emergency.R;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.utils.ToastStyle;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;

public class PresenterEditCase implements MVPEditCase.Presenter {

    CaseModel caseModel;
    ToastStyle toastStyle;
    private final Activity activity;
    private final MVPEditCase.View view;

    public PresenterEditCase(Activity activity, CaseModel caseModel, MVPEditCase.View view) {
        this.activity = activity;
        this.view = view;
        this.caseModel = caseModel;
        toastStyle = new ToastStyle(activity);
    }

    @Override
    public void setOldData() {

        view.initUiOldCasedata(caseModel.getTitle(), caseModel.getDescription(),
                caseModel.isShow_mobile(), caseModel.isShow_profile());
    }

    @Override
    public void validNewData(String title, String desc, boolean show_mobile, boolean show_image) {

        if (!title.isEmpty() && !desc.isEmpty()) {

            pushNewCaseData(title, desc, show_mobile, show_image);

            toastStyle.positiveToast(activity.getResources().getString(R.string.edit_case_message_case));
            activity.finish();


        } else if (TextUtils.isEmpty(title)) {

            toastStyle.negativeToast(activity.getResources().getString(R.string.title_message_case));

        } else if (TextUtils.isEmpty(desc)) {

            toastStyle.negativeToast(activity.getResources().getString(R.string.desc_message_case));

        }
    }

    @Override
    public void pushNewCaseData(String title, String desc, boolean show_mobile, boolean show_image) {

        getCasesReference().child(caseModel.getCase_id()).child("title").setValue(title);
        getCasesReference().child(caseModel.getCase_id()).child("description").setValue(desc);
        getCasesReference().child(caseModel.getCase_id()).child("show_mobile").setValue(show_mobile);
        getCasesReference().child(caseModel.getCase_id()).child("show_profile").setValue(show_image);

    }
}
