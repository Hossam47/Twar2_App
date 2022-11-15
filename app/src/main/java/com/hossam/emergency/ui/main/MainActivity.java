package com.hossam.emergency.ui.main;

import static com.hossam.emergency.firebase.FirebaseContract.getMainReference;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.foursquare.api.types.Venue;
import com.foursquare.placepicker.PlacePicker;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hossam.emergency.R;
import com.hossam.emergency.constants.DimenConstant;
import com.hossam.emergency.firebase.UserState;
import com.hossam.emergency.gps_provider.GpsController;
import com.hossam.emergency.interfaces.ActivityHelper;
import com.hossam.emergency.navigation.ControllerNav;
import com.hossam.emergency.services.FCMService;
import com.hossam.emergency.services.ServiceLocation;
import com.hossam.emergency.services.Twar2App;
import com.hossam.emergency.ui.cases.CasesActivity;
import com.hossam.emergency.ui.sign_up.User;
import com.hossam.emergency.utils.CoolTextView;

import java.util.List;
import java.util.Locale;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, ActivityHelper,
        EasyPermissions.PermissionCallbacks,
        EasyPermissions.RationaleCallbacks {

    private static final String TAG = MainActivity.class.getName();
    //
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.design_bottom_sheet)
    RelativeLayout container;

    @BindView(R.id.continer_searach)
    CardView continerSearach;

    @BindView(R.id.im_case_recycler)
    RecyclerView recyclerView;

    @BindView(R.id.card_main)
    RelativeLayout card_view_main;

//    @BindView(R.id.main_continer_searach)
//    CardView search_container;

    @BindView(R.id.gps_enable)
    CoolTextView gps_state;

    @BindView(R.id.network_enable)
    CoolTextView network_state;

    @BindView(R.id.gps_image)
    ImageView gps_image;

    @BindView(R.id.network_image)
    ImageView network_image;

    @BindView(R.id.see_more_container)
    LinearLayout seeMoreBtn;

    @BindView(R.id.icon_fire)
    ImageView iconFire;

//    @BindView(R.id.title_fire)
//    CoolTextView titleFire;

    @BindView(R.id.count_fire)
    CoolTextView countFire;

    @BindView(R.id.fire_container)
    LinearLayout fireContainer;

//    @BindView(R.id.title_inbox)
//    CoolTextView titleInbox;

    @BindView(R.id.count_inbox)
    CoolTextView countInbox;

    @BindView(R.id.inbox_container)
    LinearLayout inboxContainer;

    @BindView(R.id.icon_notification)
    ImageView iconNotification;

//    @BindView(R.id.title_notification)
//    CoolTextView titleNotification;

    @BindView(R.id.count_notification)
    CoolTextView countNotification;

    @BindView(R.id.notification_container)
    LinearLayout notificationContainer;

    @BindView(R.id.toolbar_shadow)
    View toolbarShadow;

    @BindView(R.id.ads_sheet_banner_chat)
    AdView ads_sheet_banner_chat;

    @BindString(R.string.on_click_back)
    String on_back_message;

    UiMainActivity uiMainActivity;
    ControllerMain controllerMain;
    AutocompleteSupportFragment autocompleteFragment;
    ControllerMaps controllerMaps = ControllerMaps.getInstance();
    ControllerNav controllerNav;
    GoogleMap mMap;
    GpsController gpsController;
    UserState userState = UserState.getInstance();
    SharedPreferences sharedPref;
    BottomSheetBehavior behavior;

    int REQUEST_PLACE_PICKER = 449;

    private static final String[] LOCATION_AND_STORAGE =
            {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            };
    Bundle mapViewBundle = null;
    private static final int RC_LOCATION_STORAGE = 124;

    private AppEventsLogger logger;
    private Twar2App twar2AppContext;
    boolean doubleBackToExitPressedOnce = false;
    private final String MAP_BUNDLE_KEY = "MapBundleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_BUNDLE_KEY);
        }

        logger = AppEventsLogger.newLogger(this);

        twar2AppContext = (Twar2App) getApplicationContext();
        sharedPref = twar2AppContext.getSharedPref();

        bindActivity();
        initActivity();

        getMainReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                if (user.getCountry() != null) {

                    twar2AppContext.saveCachesCountry(user.getCountry());
//                    toastStyle.positiveToast(twar2AppContext.getUserCountry());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void bindActivity() {

        overridePendingTransition(R.anim.fade, R.anim.fade);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.google_api_key), Locale.US);
        }
        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.main_autocomplete_fragment);

        initBottomsheet();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        uiMainActivity = new UiMainActivity(fab, recyclerView);

        uiMainActivity.setGps_state(gps_state);
        uiMainActivity.setNetwork_state(network_state);
        uiMainActivity.setNetwork_image(network_image);
        uiMainActivity.setGps_image(gps_image);
        uiMainActivity.setSee_more(seeMoreBtn);

        uiMainActivity.setCountFire(countFire);
        uiMainActivity.setFireContainer(fireContainer);
//        uiMainActivity.setTitleFire(titleFire);

        uiMainActivity.setInboxContainer(inboxContainer);
//        uiMainActivity.setTitleInbox(titleInbox);
        uiMainActivity.setCountInbox(countInbox);

        uiMainActivity.setNotificationContainer(notificationContainer);
//        uiMainActivity.setTitleNotification(titleNotification);
        uiMainActivity.setCountNotification(countNotification);


    }


    @Override
    public void initActivity() {

        logSentFriendRequestEvent();

        locationAndContactsTask();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_main);
        mapFragment.onCreate(mapViewBundle);
        mapFragment.getMapAsync(this);


        controllerMain = new ControllerMain(this, uiMainActivity);

        controllerMain.initController();

        controllerNav = new ControllerNav(this);

        gpsController = new GpsController(this);

//        interstitialAd(this);

//        interstitialAdsGoogle(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.setTrafficEnabled(false);

        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.retro_map));

        gpsController.getCurrentLocation(googleMap);
        controllerMaps.initAutoCompelete(mMap, autocompleteFragment);

        getMainReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                gpsController.setCasesMarkers(mMap, dataSnapshot.hasChild("location"));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void initBottomsheet() {

        View bottomSheet = findViewById(R.id.design_bottom_sheet);
//        View bottomtop = findViewById(R.id.design_top_sheet);

        behavior = BottomSheetBehavior.from(bottomSheet);
//        final BottomSheetBehavior behaviorTop = BottomSheetBehavior.from(bottomtop);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:

                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:

                        fab.setVisibility(View.GONE);
                        toolbar.setVisibility(View.INVISIBLE);
                        continerSearach.setVisibility(View.GONE);
                        toolbarShadow.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:

                        fab.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        continerSearach.setVisibility(View.VISIBLE);
                        toolbarShadow.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        fab.setVisibility(View.VISIBLE);

                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivity(new Intent(MainActivity.this, CasesActivity.class));

//                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
//                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//                } else {
//                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//                showPlacePicker();

            }
        });
    }

    private void pickPlace() {
        Intent intent = new Intent(this, PlacePicker.class);
        startActivityForResult(intent, 9001);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == PlacePicker.PLACE_PICKED_RESULT_CODE) {

            Venue place = data.getParcelableExtra(PlacePicker.EXTRA_PLACE);
            LatLng location = new LatLng(place.getLocation().getLat(), place.getLocation().getLng());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,
                    DimenConstant.zoom_case), 1000, null);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else if (behavior.getState() == 3) {

            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        } else if (!doubleBackToExitPressedOnce) {

            Toast.makeText(this, on_back_message, Toast.LENGTH_SHORT).show();

        } else if (doubleBackToExitPressedOnce) {

            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_cases) {

            controllerNav.myCases();

        }
        else if (id == R.id.nav_following_cases) {

            controllerNav.followingCases();

        }
        else if (id == R.id.nav_profile) {

            controllerNav.userProfile();

        } else if (id == R.id.nav_sign_out) {

            controllerNav.signOut();
        } else if (id == R.id.rate_nav) {

            controllerNav.rateUs();
        } else if (id == R.id.share_nav) {

            controllerNav.share();
        }
//        else if (id == R.id.nav_slideshow) {
//
//            startActivity(new Intent(this, SettingsActivity.class));
//        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action__btn_refresh) {
            mMap.clear();
            onMapReady(mMap);
            controllerMain.getCountNotification();
            controllerMain.getCountFire();
            controllerMain.getCountInbox();
            controllerMain.initServices();
//            toastStyle.positiveToast("Refresh");
            return true;
        } else if (id == R.id.action_current_location) {
            gpsController.getCurrentLocation(mMap);
            return true;
        } else if (id == R.id.action_search_location) {

//            InformationDialog informationDialog = new InformationDialog(this);
//
//            if (informationDialog.getCheckValue()) {
//                pickPlace();
//
//            } else if (!informationDialog.getCheckValue()) {
//
//                informationDialog.startDialog();
//
//                informationDialog.getAcceptButton().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        pickPlace();
//                        informationDialog.endDialog();
//                        informationDialog.initCheckBox();
//                    }
//                });
//
//                informationDialog.getCancelButton().setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
////                        Toast.makeText(MainActivity.this, "انت ايه ال جابك هنا ", Toast.LENGTH_LONG).show();
//                        informationDialog.endDialog();
//                        informationDialog.initCheckBox();
//                    }
//                });
//            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (controllerMain != null) {

            controllerMain.initServices();

            startService(new Intent(getApplicationContext(), FCMService.class));
            startService(new Intent(getApplicationContext(), ServiceLocation.class));

            userState.userOnline(getCurrentUser());

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        gpsController.stopGpsLocation();
        userState.userOffline(getCurrentUser());
        stopService(new Intent(getApplicationContext(), ServiceLocation.class));
    }


    private boolean hasLocationAndContactsPermissions() {
        return EasyPermissions.hasPermissions(this, LOCATION_AND_STORAGE);
    }


    @AfterPermissionGranted(RC_LOCATION_STORAGE)
    public void locationAndContactsTask() {
        if (!hasLocationAndContactsPermissions()) {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_STORAGE,
                    LOCATION_AND_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
    }

    @Override
    public void onRationaleDenied(int requestCode) {
    }


    private String getCurrentUser() {
        return sharedPref.getString("current_user_id", "");
    }

    private String getCurrentUserCountry() {
        return sharedPref.getString("current_user_country", "");
    }


    private void logSentFriendRequestEvent() {

        logger.logEvent("sentFriendRequest");
        logger.logEvent(AppEventsConstants.EVENT_NAME_AD_CLICK);

    }


}
