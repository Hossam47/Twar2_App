package com.hossam.emergency.edit_case;

public interface MVPEditCase {

    interface View {

        void initUiOldCasedata(String title, String desc, boolean show_mobile, boolean show_image);

        void initUiNewCaseData();

    }

    interface Presenter {

        void setOldData();

        void validNewData(String title, String desc, boolean show_mobile, boolean show_image);

        void pushNewCaseData(String title, String desc, boolean show_mobile, boolean show_image);
    }


}
