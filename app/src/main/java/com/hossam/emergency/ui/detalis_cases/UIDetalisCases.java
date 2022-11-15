package com.hossam.emergency.ui.detalis_cases;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.hossam.emergency.utils.CoolTextView;

public class UIDetalisCases {

    private RecyclerView recyclerView, recyclerView_Comments;
    private LinearLayout call_container, street_container, map_container, followCaseDetailsPartOne, containerMainFeature, directMessageBtnContainer;
    private RelativeLayout messageContainer, saved_message_container;
    private CoolTextView desc, titleFollowDetailsPartOne, directMessageDetailsPartOne;
    private ImageView imageDetails, imageIcon, senderIcon, iconFollowDetailsPartOne, imageDirectMessageDetailsPartOne;
    private CoolTextView nameDetails, distanceDetails, timeDetails, titleDetails;
    private CoolTextView buttonPhone, buttonMessage, buttonStreet, buttonMap, number_comments;
    private EditText comment;


    public UIDetalisCases(RecyclerView recyclerView, RecyclerView recyclerView_Comments, CoolTextView desc,
                          ImageView imageDetails, ImageView imageIcon, CoolTextView nameDetails, CoolTextView distanceDetails,
                          CoolTextView timeDetails, CoolTextView titleDetails, ImageView senderIcon, CoolTextView buttonPhone, CoolTextView buttonMessage,
                          CoolTextView buttonStreet, CoolTextView buttonMap, EditText comment) {

        this.recyclerView = recyclerView;
        this.recyclerView_Comments = recyclerView_Comments;
        this.desc = desc;
        this.imageDetails = imageDetails;
        this.imageIcon = imageIcon;
        this.nameDetails = nameDetails;
        this.distanceDetails = distanceDetails;
        this.timeDetails = timeDetails;
        this.titleDetails = titleDetails;
        this.senderIcon = senderIcon;
        this.buttonPhone = buttonPhone;
        this.buttonMessage = buttonMessage;
        this.buttonStreet = buttonStreet;
        this.buttonMap = buttonMap;
        this.comment = comment;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public CoolTextView getDesc() {
        return desc;
    }

    public void setDesc(CoolTextView desc) {
        this.desc = desc;
    }

    public RecyclerView getRecyclerView_Comments() {
        return recyclerView_Comments;
    }

    public void setRecyclerView_Comments(RecyclerView recyclerView_Comments) {
        this.recyclerView_Comments = recyclerView_Comments;
    }

    public ImageView getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(ImageView imageDetails) {
        this.imageDetails = imageDetails;
    }

    public ImageView getImageIcon() {
        return imageIcon;
    }

    public void setImageIcon(ImageView imageIcon) {
        this.imageIcon = imageIcon;
    }

    public CoolTextView getNameDetails() {
        return nameDetails;
    }

    public void setNameDetails(CoolTextView nameDetails) {
        this.nameDetails = nameDetails;
    }

    public CoolTextView getDistanceDetails() {
        return distanceDetails;
    }

    public void setDistanceDetails(CoolTextView distanceDetails) {
        this.distanceDetails = distanceDetails;
    }

    public CoolTextView getTimeDetails() {
        return timeDetails;
    }

    public void setTimeDetails(CoolTextView timeDetails) {
        this.timeDetails = timeDetails;
    }

    public CoolTextView getTitleDetails() {
        return titleDetails;
    }

    public void setTitleDetails(CoolTextView titleDetails) {
        this.titleDetails = titleDetails;
    }

    public ImageView getSenderIcon() {
        return senderIcon;
    }

    public void setSenderIcon(ImageView senderIcon) {
        this.senderIcon = senderIcon;
    }

    public CoolTextView getButtonPhone() {
        return buttonPhone;
    }

    public void setButtonPhone(CoolTextView buttonPhone) {
        this.buttonPhone = buttonPhone;
    }

    public CoolTextView getButtonMessage() {
        return buttonMessage;
    }

    public void setButtonMessage(CoolTextView buttonMessage) {
        this.buttonMessage = buttonMessage;
    }

    public CoolTextView getButtonStreet() {
        return buttonStreet;
    }

    public void setButtonStreet(CoolTextView buttonStreet) {
        this.buttonStreet = buttonStreet;
    }

    public CoolTextView getButtonMap() {
        return buttonMap;
    }

    public void setButtonMap(CoolTextView buttonMap) {
        this.buttonMap = buttonMap;
    }

    public EditText getComment() {
        comment.setBackground(null);
        return comment;
    }

    public CoolTextView getNumber_comments() {
        return number_comments;
    }

    public void setNumber_comments(CoolTextView number_comments) {
        this.number_comments = number_comments;
    }

    public void setComment(EditText comment) {
        this.comment = comment;
    }

    public LinearLayout getCall_container() {
        return call_container;
    }

    public void setCall_container(LinearLayout call_container) {
        this.call_container = call_container;
    }

    public LinearLayout getStreet_container() {
        return street_container;
    }

    public void setStreet_container(LinearLayout street_container) {
        this.street_container = street_container;
    }

    public LinearLayout getMap_container() {
        return map_container;
    }


    public RelativeLayout getSaved_message_container() {
        return saved_message_container;
    }

    public void setSaved_message_container(RelativeLayout saved_message_container) {
        this.saved_message_container = saved_message_container;
    }

    public void setMap_container(LinearLayout map_container) {
        this.map_container = map_container;
    }

    public RelativeLayout getMessageContainer() {
        return messageContainer;
    }

    public LinearLayout getFollowCaseDetailsPartOne() {
        return followCaseDetailsPartOne;
    }

    public void setFollowCaseDetailsPartOne(LinearLayout followCaseDetailsPartOne) {
        this.followCaseDetailsPartOne = followCaseDetailsPartOne;
    }

    public CoolTextView getTitleFollowDetailsPartOne() {
        return titleFollowDetailsPartOne;
    }

    public void setTitleFollowDetailsPartOne(CoolTextView titleFollowDetailsPartOne) {
        this.titleFollowDetailsPartOne = titleFollowDetailsPartOne;
    }

    public ImageView getIconFollowDetailsPartOne() {
        return iconFollowDetailsPartOne;
    }

    public void setIconFollowDetailsPartOne(ImageView iconFollowDetailsPartOne) {
        this.iconFollowDetailsPartOne = iconFollowDetailsPartOne;
    }

    public void setMessageContainer(RelativeLayout messageContainer) {
        this.messageContainer = messageContainer;
    }

    public LinearLayout getContainerMainFeature() {
        return containerMainFeature;
    }

    public void setContainerMainFeature(LinearLayout containerMainFeature) {
        this.containerMainFeature = containerMainFeature;
    }

    public LinearLayout getDirectMessageBtnContainer() {
        return directMessageBtnContainer;
    }

    public void setDirectMessageBtnContainer(LinearLayout directMessageBtnContainer) {
        this.directMessageBtnContainer = directMessageBtnContainer;
    }

    public CoolTextView getDirectMessageDetailsPartOne() {
        return directMessageDetailsPartOne;
    }

    public void setDirectMessageDetailsPartOne(CoolTextView directMessageDetailsPartOne) {
        this.directMessageDetailsPartOne = directMessageDetailsPartOne;
    }

    public ImageView getImageDirectMessageDetailsPartOne() {
        return imageDirectMessageDetailsPartOne;
    }

    public void setImageDirectMessageDetailsPartOne(ImageView imageDirectMessageDetailsPartOne) {
        this.imageDirectMessageDetailsPartOne = imageDirectMessageDetailsPartOne;
    }
}
