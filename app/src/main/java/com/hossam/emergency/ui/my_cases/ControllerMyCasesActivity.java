package com.hossam.emergency.ui.my_cases;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.confirmation_dialog.ConfirmationDialog;
import com.hossam.emergency.ui.detalis_cases.DetailsCases;
import com.hossam.emergency.edit_case.EditCaseActivity;
import com.hossam.emergency.reactions.ReactionController;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.utils.CoolTextView;
import com.hossam.emergency.utils.FillColor;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;

public class ControllerMyCasesActivity implements ProviderMyCases {

    Activity activity;
    FillColor fillColor = FillColor.getInstance();
    StringResouces stringResouces = StringResouces.getInstance();
    Query userCasesQuery = getCasesReference();
    UiMyCases uiMyCases;
    ReactionController reactionController;
    private final GetTime getTime = GetTime.getInstance();
    ToastStyle toastStyle;
    ConfirmationDialog confirmationDialog;
    Twar2App twar2App;

    public ControllerMyCasesActivity(Activity activity, UiMyCases uiMyCases) {
        this.activity = activity;
        this.uiMyCases = uiMyCases;
        toastStyle = new ToastStyle(activity);
        reactionController = new ReactionController(activity);

        twar2App = (Twar2App) activity.getApplication();
        initRecyclerView(userCasesQuery);
    }

    @Override
    public void initRecyclerView(Query query) {

        FirebaseRecyclerAdapter<CaseModel, ControllerMyCasesActivity.ViewHolder> firebaseAdapter
                = new FirebaseRecyclerAdapter<CaseModel, ControllerMyCasesActivity.ViewHolder>(

                CaseModel.class,
                R.layout.my_cases_items,
                ControllerMyCasesActivity.ViewHolder.class,
                getCasesReference().orderByChild("user_id").equalTo(getCurrentUserID())

        ) {
            @Override
            protected void populateViewHolder(ControllerMyCasesActivity.ViewHolder holder, CaseModel model, int position) {

                if (!model.isDeleted()) {

                    setRecyclerData(holder, model);

                } else if (model.isDeleted()) {

                    holder.containerMyCasesItems.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                    holder.containerMyCasesItems.setLayoutParams(new RelativeLayout.MarginLayoutParams(0, 0));
                    holder.containerMyCasesItems.setVisibility(View.GONE);
                }

            }

        };

        uiMyCases.getMyCasesRecycler().setAdapter(firebaseAdapter);
        firebaseAdapter.notifyDataSetChanged();
        noCases(true);
    }

    @Override
    public void setRecyclerData(RecyclerView.ViewHolder viewHolder, Object model1) {

        final CaseModel model = (CaseModel) model1;
        ControllerMyCasesActivity.ViewHolder holder = (ControllerMyCasesActivity.ViewHolder) viewHolder;

        getTitle(holder, model);
        getOnContainerClick(holder, model);
        getCountComments(holder, model);
        getCountView(holder, model);
        getCountFires(holder, model);
        getPrettyTime(holder, model);
        deleteMycase(holder, model);
        savedMycase(holder, model);
        manageLayout(holder, model);
        editMycase(holder, model);
    }

    @Override
    public void getTitle(ViewHolder holder, CaseModel model) {

        holder.titleMyCases.setText(model.getTitle());

    }

    @Override
    public void getOnContainerClick(ViewHolder holder, final CaseModel model) {

        holder.containerMyCasesItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailsCases.class);
                intent.putExtra("case_model", model);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void getCountComments(ViewHolder holder, CaseModel model) {

//        reactionController.countComments(model.getCase_id(), holder.commentsNumbersMyCases);
    }

    @Override
    public void getCountView(ViewHolder holder, CaseModel model) {

//        reactionController.countViews(model.getCase_id(), holder.viewNumbersMyCases);
    }

    @Override
    public void getCountFires(ViewHolder holder, CaseModel model) {

//        reactionController.countReaction(model.getCase_id(), holder.fireNumbersMyCases);
    }

    @Override
    public void getPrettyTime(ViewHolder holder, CaseModel model) {

        holder.timeMyCases.setText(getTime.getStyleTime(activity).format(new Date(model.getTime())));
    }

    @Override
    public void deleteMycase(ViewHolder holder, final CaseModel model) {

        holder.delete_my_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCasesReference().child(model.getCase_id()).child("deleted").setValue(true);

                toastStyle.positiveToast(stringResouces.getStringResource(R.string.delete_post_message, activity));

            }
        });
    }

    @Override
    public void editMycase(ViewHolder holder, final CaseModel model) {

        holder.container_edit_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, EditCaseActivity.class);
                intent.putExtra("case_model", model);
                activity.startActivity(intent);

            }
        });
    }

    @Override
    public void savedMycase(ViewHolder holder, final CaseModel model) {

        holder.saved_my_case.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCasesReference().child(model.getCase_id()).child("saved").setValue(true);

                toastStyle.positiveToast(stringResouces.getStringResource(R.string.saved_post_message, activity));

            }
        });

    }

    @Override
    public void manageLayout(ViewHolder holder, CaseModel model) {

        if (model.isSaved()) {

            holder.saved_my_case.setVisibility(View.GONE);
            holder.container_edit_case.setVisibility(View.GONE);

        } else {

            holder.text_title_saved_case.setText("Case is live ");
            fillColor.caseIsLive(holder.is_saved_icon, activity);
        }
    }

    @Override
    public void noCases(boolean show) {

        getCasesReference().orderByChild("user_id")
                .equalTo(getCurrentUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 0) {

                    uiMyCases.no_cases_container.setVisibility(View.GONE);
                    uiMyCases.getRotateLoadingMycases().stop();
                } else {
                    uiMyCases.no_cases_container.setVisibility(View.VISIBLE);

                    uiMyCases.getRotateLoadingMycases().stop();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_my_cases)
        CoolTextView titleMyCases;

//        @BindView(R.id.case_image_mycases)
//        AppCompatImageView imageMycases;
        @BindView(R.id.time_my_cases)
        CoolTextView timeMyCases;

//        @BindView(R.id.view_image_my_cases)
//        ImageView viewImageMyCases;

        @BindView(R.id.is_saved_icon)
        ImageView is_saved_icon;

//        @BindView(R.id.view_numbers_my_cases)
//        CoolTextView viewNumbersMyCases;

//        @BindView(R.id.fire_image_my_cases)
//        ImageView fireImageMyCases;
//
//        @BindView(R.id.fire_numbers_my_cases)
//        CoolTextView fireNumbersMyCases;
//
//        @BindView(R.id.comments_image_my_cases)
//        ImageView commentsImageMyCases;
//
//        @BindView(R.id.comments_numbers_my_cases)
//        CoolTextView commentsNumbersMyCases;

//        @BindView(R.id.reactions_container)
//        LinearLayout reactionsContainer;

        @BindView(R.id.container_my_cases_items)
        RelativeLayout containerMyCasesItems;

        @BindView(R.id.container_saved_my_case)
        RelativeLayout saved_my_case;

        @BindView(R.id.container_delete_my_case)
        RelativeLayout delete_my_case;

        @BindView(R.id.container_edit_case)
        RelativeLayout container_edit_case;

        @BindView(R.id.saved_case_container)
        LinearLayout title_saved_case;

        @BindView(R.id.delete_title_my_cases)
        CoolTextView deleteButton;

        @BindView(R.id.text_title_saved_case)
        CoolTextView text_title_saved_case;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }


}
