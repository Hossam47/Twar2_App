package com.hossam.emergency.ui.following_cases;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.reactions.ReactionController;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.detalis_cases.DetailsCases;
import com.hossam.emergency.utils.CoolButton;
import com.hossam.emergency.utils.CoolTextView;
import com.hossam.emergency.utils.StringResouces;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterFollowingCases extends RecyclerView.Adapter<AdapterFollowingCases.ViewHolder> {

    ArrayList<FollowingCasesModel> casesList;
    Activity activity;
    FollowingCasesModel followingCasesModel;
    StringResouces stringResouces = StringResouces.getInstance();
    ReactionController reactionController;
    GetTime getTime = GetTime.getInstance();
    Twar2App twar2App;

    public AdapterFollowingCases(ArrayList<FollowingCasesModel> casesList, Activity activity) {
        this.casesList = casesList;
        this.activity = activity;
        reactionController = new ReactionController(activity);
        twar2App = (Twar2App) activity.getApplication();
    }

    @Override
    public AdapterFollowingCases.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.following_cases_item, parent, false);

        AdapterFollowingCases.ViewHolder viewHolder = new AdapterFollowingCases.ViewHolder(layout);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterFollowingCases.ViewHolder holder, int position) {

        followingCasesModel = casesList.get(position);

        Query query = getCasesReference().child(followingCasesModel.getCaseId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                CaseModel caseModel = dataSnapshot.getValue(CaseModel.class);

                if (caseModel != null && !caseModel.isDeleted()) {

                    setRecyclerData(holder, caseModel);

                } else  {
                    holder.containerFollowingCases.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                    holder.containerFollowingCases.setLayoutParams(new RelativeLayout.MarginLayoutParams(0, 0));
                    holder.containerFollowingCases.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return casesList.size();
    }

    public void setRecyclerData(RecyclerView.ViewHolder viewHolder, Object model1) {

        final CaseModel model = (CaseModel) model1;
        AdapterFollowingCases.ViewHolder holder = (AdapterFollowingCases.ViewHolder) viewHolder;


        getTitle(holder, model);
        getOnContainerClick(holder, model);
        getCountComments(holder, model);
        getCountView(holder, model);
        getCountFires(holder, model);
        getPrettyTime(holder, model);
        deleteButton(holder, model);

    }

    public void getTitle(AdapterFollowingCases.ViewHolder holder, CaseModel model) {

        holder.titleFollowingCases.setText(model.getTitle());

    }

    public void getOnContainerClick(AdapterFollowingCases.ViewHolder holder, final CaseModel model) {

        holder.containerFollowingCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailsCases.class);
                intent.putExtra("case_model", model);
                activity.startActivity(intent);
            }
        });
    }

    public void getCountComments(AdapterFollowingCases.ViewHolder holder, CaseModel model) {

        reactionController.countComments(model.getCase_id(), holder.commentsNumbersFollowingCases);
    }

    public void getCountView(AdapterFollowingCases.ViewHolder holder, CaseModel model) {

        reactionController.countViews(model.getCase_id(), holder.viewNumbersFollowingCases);
    }

    public void getCountFires(AdapterFollowingCases.ViewHolder holder, CaseModel model) {

        reactionController.countReaction(model.getCase_id(), holder.fireNumbersFollowingCases);
    }

    public void getPrettyTime(AdapterFollowingCases.ViewHolder holder, CaseModel model) {

        holder.timeFollowingCases.setText(getTime.getStyleTime(activity).format(new Date(model.getTime())));
    }

    public void deleteButton(AdapterFollowingCases.ViewHolder holder, final CaseModel model) {

        holder.deleteButtonFollowingCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getMainReference().child("following_cases").child(model.getCase_id()).setValue(null);

                Toast.makeText(activity, stringResouces.getStringResource(R.string.delete_post_message, activity), Toast.LENGTH_LONG).show();

            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_following_cases)
        CoolTextView titleFollowingCases;
        @BindView(R.id.time_following_cases)
        CoolTextView timeFollowingCases;
        @BindView(R.id.view_image_following_cases)
        ImageView viewImageFollowingCases;
        @BindView(R.id.view_numbers_following_cases)
        CoolTextView viewNumbersFollowingCases;
        @BindView(R.id.fire_image_following_cases)
        ImageView fireImageFollowingCases;
        @BindView(R.id.fire_numbers_following_cases)
        CoolTextView fireNumbersFollowingCases;
        @BindView(R.id.comments_image_following_cases)
        ImageView commentsImageFollowingCases;
        @BindView(R.id.comments_numbers_following_cases)
        CoolTextView commentsNumbersFollowingCases;
        @BindView(R.id.reactions_container_following)
        LinearLayout reactionsContainerFollowing;
        @BindView(R.id.delete_button_following_cases)
        CoolButton deleteButtonFollowingCases;
        @BindView(R.id.container_following_cases_items)
        RelativeLayout containerFollowingCases;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }

}
