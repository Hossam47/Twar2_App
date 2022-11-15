package com.hossam.emergency.ui.list_cases;

import android.app.Activity;
import android.location.Location;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.algorithem.SortAlgorithms;
import com.hossam.emergency.ui.cases.CaseModel;
import com.hossam.emergency.dialogs.InitDialog;
import com.hossam.emergency.gps_provider.LocationModel;
import com.hossam.emergency.process.GPSAvailability;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.utils.StringResouces;
import com.hossam.emergency.utils.ToastStyle;

import java.util.ArrayList;

import static com.hossam.emergency.firebase.FirebaseContract.getCasesCountryReference;
import static com.hossam.emergency.firebase.FirebaseContract.getCasesReference;
import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

public class ControllerListCases implements ProviderListCases {

    ArrayList<CaseModel> caseModelArrayList;
    AdapterListCases adapterListCases;
    RecyclerView.LayoutManager layoutManager;
    SortAlgorithms sortAlgorithms = new SortAlgorithms();
    GPSAvailability gpsAvailability;

    ToastStyle toastStyle;
    private final Activity activity;
    private final StyleBarUi styleBarUi;
    private final RecyclerView recyclerView;
    StringResouces stringResouces = StringResouces.getInstance();
    int currentItems, scrollOutItems, totalItems, key = 0;
    private final int getItemsIndex = 10;
    private int getItemsview = 10;
    private boolean isScrolling, showedAllItems, refresh = false;
    private boolean timeClicked, fireClicked, gpsClicked, savedClicked;
    Twar2App twar2App;
    private View view;
    private final InitDialog initDialog = new InitDialog();


    public ControllerListCases(Activity activity, StyleBarUi styleBarUi, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        this.activity = activity;
        this.styleBarUi = styleBarUi;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;

        gpsAvailability = GPSAvailability.getInstance();
        twar2App = (Twar2App) activity.getApplicationContext();

        initRecyclerView(key);
//        updatedLoadMore(layoutManager);
        initUI();
        styleBar();
        initSwipeRefresh();

        toastStyle = new ToastStyle(activity);
    }


    @Override
    public void initRecyclerView(final int key) {

        caseModelArrayList = new ArrayList<>();
//        getCasesReference().limitToLast(getItemsIndex).orderByChild("deleted").equalTo(false).addListenerForSingleValueEvent(new ValueEventListener() {


        // get cases by country user
        getCasesReference()
                .orderByChild("deleted").equalTo(false).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                if (caseModelArrayList != null) {
//                    caseModelArrayList.clear();
//                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    CaseModel caseModel = snapshot.getValue(CaseModel.class);

//                    if (!caseModel.isDeleted()) {

                    if (key >= 0 && key < 3 && !caseModel.isSaved()) {

                        caseModelArrayList.add(caseModel);

                    } else if (key == 3 && caseModel.isSaved()) {

                        caseModelArrayList.add(caseModel);
                    }
//                    }
                }

                switch (key) {

                    case 0:
                        sortAlgorithms.getMaxTime(caseModelArrayList);
                        adapterListCases = new AdapterListCases(caseModelArrayList, activity);
                        recyclerView.setAdapter(adapterListCases);
                        makeRefresh();
                        checkData(caseModelArrayList);

                        break;

                    case 1:
                        sortAlgorithms.getMaxRate(caseModelArrayList);
                        adapterListCases = new AdapterListCases(caseModelArrayList, activity);
                        recyclerView.setAdapter(adapterListCases);
                        makeRefresh();
                        checkData(caseModelArrayList);
                        break;

                    case 2:

                        getMainReference().child("location").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                LocationModel locationModel = dataSnapshot.getValue(LocationModel.class);
                                Location location = new Location("");
                                location.setLatitude(locationModel.getLatitude());
                                location.setLongitude(locationModel.getLongitude());

                                sortAlgorithms.getNearDistance(caseModelArrayList, location);
                                adapterListCases = new AdapterListCases(caseModelArrayList, activity);
                                recyclerView.setAdapter(adapterListCases);
                                makeRefresh();
                                checkData(caseModelArrayList);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        break;


                    case 3:
                        sortAlgorithms.getMaxTime(caseModelArrayList);
                        adapterListCases = new AdapterListCases(caseModelArrayList, activity);
                        recyclerView.setAdapter(adapterListCases);
                        makeRefresh();

                        checkData(caseModelArrayList);
                        break;
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void initUI() {

//        view = initDialog.initDialog(R.layout.rate_dialog, activity);
//        initDialog.showDialog();

//        title = view.findViewById(R.id.title_confirmation_dialog);
//        yes = view.findViewById(R.id.yes_confirmation_dialog);
//        cancel = view.findViewById(R.id.cancel_confirmation_dialog);

    }

    @Override
    public void styleBar() {

        styleBarUi.getTimelineFilterContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                key = 0;
                timeClicked = true;
                styleBarUi.setStyleBar(key);
                initRecyclerView(key);

            }
        });


        styleBarUi.getFireFilterContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                key = 1;
                fireClicked = true;
                styleBarUi.setStyleBar(key);
                initRecyclerView(key);

            }
        });

        styleBarUi.getNearFilterContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gpsAvailability.checkGPS(activity)) {

                    getMainReference().child("location").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {

                                key = 2;
                                gpsClicked = true;

                                styleBarUi.setStyleBar(key);
                                initRecyclerView(key);


                            } else {

//                                toastStyle.positiveToast("يابنى اصبر");

                            }

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else if (!gpsAvailability.checkGPS(activity)) {

                    Toast.makeText(activity, stringResouces.getStringResource(R.string.location_message,activity), Toast.LENGTH_LONG).show();
//                    toastStyle.negativeToast("ياعم افتح ام ال GPS الاول متقرفناش معاك ");
                } else {

                    toastStyle.positiveToast("يابنى اصبر ");
                }
            }
        });

        styleBarUi.getSavedFilterContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                key = 3;
                savedClicked = true;

                styleBarUi.setStyleBar(key);
                initRecyclerView(key);

            }
        });
    }

    @Override
    public void checkData(ArrayList<CaseModel> caseModels) {

        if (caseModels.size() == 0) {
            styleBarUi.getNoDataImage().setVisibility(View.VISIBLE);
        } else {
            styleBarUi.getNoDataImage().setVisibility(View.GONE);
        }
    }


//    @Override
//    public void updatedLoadMore(final RecyclerView.LayoutManager layoutManager) {
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true;
//                }
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//                }
//
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                }
//
//            }
//
//            @Override
//            public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
//
//                currentItems = layoutManager.getChildCount();
//                scrollOutItems = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
//                totalItems = layoutManager.getItemCount();
//
//                if (isScrolling && (currentItems + scrollOutItems == totalItems) && showedAllItems == false && totalItems >= 10) {
//
//                    styleBarUi.getProgressListCases().setVisibility(View.VISIBLE);
//
//                    getItemsIndex += 10;
//                    isScrolling = false;
//                    ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
//
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            initRecyclerView(key);
//                            styleBarUi.getProgressListCases().setVisibility(View.GONE);
//
//                        }
//                    }, 1000);
//
//
//                    getCasesReference().orderByChild("deleted").equalTo(false).addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            if (dataSnapshot.exists()) {
//
//                                if (totalItems == dataSnapshot.getChildrenCount()) {
//
//                                    showedAllItems = true;
//
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//
//            }
//
//        });
//
//    }

    @Override
    public void initSwipeRefresh() {

        styleBarUi.getSwipeContainer().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        refresh = true;
                        getItemsview = 5;

                        initRecyclerView(key);

                        styleBarUi.getSwipeContainer().setRefreshing(false);

                    }
                }, 1000);
            }
        });
        styleBarUi.getSwipeContainer().setColorSchemeResources(android.R.color.holo_red_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_dark);
    }

    private void makeRefresh() {

        if (refresh) {
            refresh = false;
            recyclerView.scrollToPosition(0);
        } else if (timeClicked) {
            recyclerView.scrollToPosition(0);
            timeClicked = false;
        } else if (fireClicked) {
            recyclerView.scrollToPosition(0);
            fireClicked = false;
        } else if (gpsClicked) {
            recyclerView.scrollToPosition(0);
            gpsClicked = false;
        } else if (savedClicked) {
            recyclerView.scrollToPosition(0);
            savedClicked = false;
        } else if (!refresh) {
            recyclerView.scrollToPosition(getItemsIndex - getItemsview);
        }


    }
}
