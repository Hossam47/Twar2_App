package com.hossam.emergency.facebook_account_kit;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;

import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import static com.hossam.emergency.constants.ConstantTags.REQUEST_CODE;

public class ControllerFacebookAccountKit implements ProviderAccountKit {


    Activity activity;

    public ControllerFacebookAccountKit(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void verificationPhone() {

        Intent intent = new Intent(activity, AccountKitActivity.class);

        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE, AccountKitActivity.ResponseType.TOKEN);

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build());

        activity.startActivityForResult(intent, REQUEST_CODE);

    }

    @Override
    public void verificationEmail() {

        Intent intent = new Intent(activity, AccountKitActivity.class);

        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(LoginType.EMAIL, AccountKitActivity.ResponseType.TOKEN);

        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, (Parcelable) configurationBuilder);

        activity.startActivityForResult(intent, REQUEST_CODE);
    }
}
