package com.hossam.emergency.ui.following_cases;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.ui.detalis_cases.DetailsCases;
import com.hossam.emergency.reactions.ReactionController;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.time.GetTime;
import com.hossam.emergency.utils.CoolButton;
import com.hossam.emergency.utils.CoolTextView;
import com.hossam.emergency.utils.StringResouces;
import com.victor.loading.rotate.RotateLoading;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCurrentUserID;
import static com.hossam.emergency.firebase.FirebaseContract.getFollowingReference;

public class ContollerFollowingCases implements ProviderFollowingCases {

    StringResouces stringResouces = StringResouces.getInstance();
    ReactionController reactionController;
    GetTime getTime = GetTime.getInstance();
    FirebaseRecyclerAdapter<FollowingCasesModel, ContollerFollowingCases.ViewHolder> firebaseAdapter;
    private final Activity activity;
    private final RecyclerView recyclerView;
    private final ImageView no_cases_follow_container;
    RotateLoading rotateLoadingFollowing;
    Twar2App twar2App;

    public ContollerFollowingCases(Activity activity, RecyclerView recyclerView, ImageView no_cases_follow_container, RotateLoading rotateLoadingFollowing) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.no_cases_follow_container = no_cases_follow_container;
        this.rotateLoadingFollowing = rotateLoadingFollowing;

        twar2App = (Twar2App) activity.getApplication();
        initRecyclerView(getFollowingReference().child(getCurrentUserID()).orderByChild("time"));
        reactionController = new ReactionController(activity);
    }


    public void getFollowingCasesData(final FollowingCasesModel model, final ContollerFollowingCases.ViewHolder holder,
                                      final int postion) {

        Query query =  getCasesReference().child(model.getCaseId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {

                    CaseModel caseModel = dataSnapshot.getValue(CaseModel.class);

                    if (!caseModel.isDeleted()) {

                        setRecyclerData(holder, caseModel);

                    } else if (caseModel.isDeleted()) {

                        holder.containerFollowingCases.setLayoutParams(new RelativeLayout.LayoutParams(0, 0));
                        holder.containerFollowingCases.setLayoutParams(new RelativeLayout.MarginLayoutParams(0, 0));
                        holder.containerFollowingCases.setVisibility(View.GONE);
                        holder.containerCardFollowingCases.setVisibility(View.GONE);
                        holder.main_container_following_cases_items.setVisibility(View.GONE);
                        //  setRecyclerData(holder, caseModel);

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void initRecyclerView(Query query) {

        firebaseAdapter = new FirebaseRecyclerAdapter<FollowingCasesModel, ContollerFollowingCases.ViewHolder>(

                FollowingCasesModel.class,
                R.layout.following_cases_item,
                ContollerFollowingCases.ViewHolder.class,
                query

        ) {
            @Override
            protected void populateViewHolder(ContollerFollowingCases.ViewHolder holder, FollowingCasesModel model, int position) {


                getFollowingCasesData(model, holder, position);


            }

        };

        recyclerView.setAdapter(firebaseAdapter);
        firebaseAdapter.notifyDataSetChanged();

        noCases(true);

    }

    @Override
    public void setRecyclerData(RecyclerView.ViewHolder viewHolder, Object model1) {

        final CaseModel model = (CaseModel) model1;
        ContollerFollowingCases.ViewHolder holder = (ContollerFollowingCases.ViewHolder) viewHolder;


        getTitle(holder, model);
        getOnContainerClick(holder, model);
        getCountComments(holder, model);
        getCountView(holder, model);
        getCountFires(holder, model);
        getPrettyTime(holder, model);
        deleteButton(holder, model);

    }

    @Override
    public void getTitle(ContollerFollowingCases.ViewHolder holder, CaseModel model) {

        holder.titleFollowingCases.setText(model.getTitle());

    }

    @Override
    public void getOnContainerClick(ContollerFollowingCases.ViewHolder holder, final CaseModel model) {

        holder.containerFollowingCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, DetailsCases.class);
                intent.putExtra("case_model", model);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public void getCountComments(ContollerFollowingCases.ViewHolder holder, CaseModel model) {

        reactionController.countComments(model.getCase_id(), holder.commentsNumbersFollowingCases);
    }

    @Override
    public void getCountView(ContollerFollowingCases.ViewHolder holder, CaseModel model) {

        reactionController.countViews(model.getCase_id(), holder.viewNumbersFollowingCases);
    }

    @Override
    public void getCountFires(ContollerFollowingCases.ViewHolder holder, CaseModel model) {

        reactionController.countReaction(model.getCase_id(), holder.fireNumbersFollowingCases);
    }

    @Override
    public void getPrettyTime(ContollerFollowingCases.ViewHolder holder, CaseModel model) {

        holder.timeFollowingCases.setText(getTime.getStyleTime(activity).format(new Date(model.getTime())));
    }

    @Override
    public void deleteButton(ContollerFollowingCases.ViewHolder holder, final CaseModel model) {

        holder.deleteButtonFollowingCases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFollowingReference().child(getCurrentUserID()).child(model.getCase_id()).setValue(null);

                Toast.makeText(activity, stringResouces.getStringResource(R.string.unfollow_post_message, activity), Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void noCases(boolean show) {

        getFollowingReference().child(getCurrentUserID()).orderByChild("time").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() > 0) {
                    no_cases_follow_container.setVisibility(View.GONE);
                    rotateLoadingFollowing.stop();
                } else {
                    no_cases_follow_container.setVisibility(View.VISIBLE);
                    rotateLoadingFollowing.stop();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

        @BindView(R.id.main_container_following_cases_items)
        RelativeLayout main_container_following_cases_items;

        @BindView(R.id.container_card_following_cases_items)
        CardView containerCardFollowingCases;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

    }


}
