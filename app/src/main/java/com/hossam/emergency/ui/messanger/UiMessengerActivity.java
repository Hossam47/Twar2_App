package com.hossam.emergency.ui.messanger;

import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class UiMessengerActivity {

    private ImageView imageIcon, iconSenderMessenger;
    private AutoCompleteTextView messageEditText;
    private RelativeLayout messageContainer;

    public UiMessengerActivity(ImageView imageIcon, ImageView iconSenderMessenger, AutoCompleteTextView messageEditText, RelativeLayout messageContainer) {
        this.imageIcon = imageIcon;
        this.iconSenderMessenger = iconSenderMessenger;
        this.messageEditText = messageEditText;
        this.messageContainer = messageContainer;
    }

    public ImageView getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageView imageIcon) {
        this.imageIcon = imageIcon;
    }

    public ImageView getIconSenderMessenger() {
        return iconSenderMessenger;
    }

    public void setIconSenderMessenger(ImageView iconSenderMessenger) {
        this.iconSenderMessenger = iconSenderMessenger;
    }

    public AutoCompleteTextView getMessageEditText() {
        return messageEditText;
    }

    public void setMessageEditText(AutoCompleteTextView messageEditText) {
        this.messageEditText = messageEditText;
    }

    public RelativeLayout getMessageContainer() {
        return messageContainer;
    }

    public void setMessageContainer(RelativeLayout messageContainer) {
        this.messageContainer = messageContainer;
    }
}
