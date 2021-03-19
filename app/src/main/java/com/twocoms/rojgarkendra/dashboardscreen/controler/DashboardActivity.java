package com.twocoms.rojgarkendra.dashboardscreen.controler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.model.NavMenuModel;
import com.twocoms.rojgarkendra.dashboardscreen.view.ExpandableListAdapter;
import com.twocoms.rojgarkendra.documentsscreen.controler.MyDocumentsActivity;
import com.twocoms.rojgarkendra.global.model.AppConstant;
import com.twocoms.rojgarkendra.global.model.GlobalPreferenceManager;
import com.twocoms.rojgarkendra.goodiesscreen.controler.MyGoodiesStoreActivity;
import com.twocoms.rojgarkendra.goodiesscreen.controler.MyOrdersActivity;
import com.twocoms.rojgarkendra.interviewscreen.controler.AppliedApplicationActivity;
import com.twocoms.rojgarkendra.interviewscreen.controler.UpcomingInterviewActivity;
import com.twocoms.rojgarkendra.myprofile.controler.UserProfileActivity;
import com.twocoms.rojgarkendra.registrationscreen.controler.GetStartedActivity;
import com.twocoms.rojgarkendra.successstoryscreen.SuccessStoriesActivity;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ExpandableListView expandableListView;
    NavigationView navigationView;
    ArrayList<NavMenuModel> listDataHeader1;
    ExpandableListAdapter listAdapter;
    ImageView menu;
    ImageView userProfileBtn;
    String eduStr = "";
    ImageView userProfileImg;
    String profileURlStr = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initialization();
    }

    //
    void initialization() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        menu = (ImageView) findViewById(R.id.menu);
        navigationView = (NavigationView) findViewById(R.id.nv_navigation_view);
        expandableListView = (ExpandableListView) findViewById(R.id.left_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        userProfileBtn = (ImageView) findViewById(R.id.user_profile_img);
        eduStr = GlobalPreferenceManager.getStringForKey(DashboardActivity.this, AppConstant.KEY_IS_EDURP, "");
        profileURlStr = GlobalPreferenceManager.getStringForKey(DashboardActivity.this, AppConstant.KEY_PROFILE_URL, "").trim();
        userProfileImg = (ImageView) findViewById(R.id.user_img);

        Glide.with(this)
                .load(profileURlStr)
                .into(userProfileImg);
        //prepareListData();

        prepareListData1();
        listAdapter = new ExpandableListAdapter(DashboardActivity.this, listDataHeader1);

        expandableListView.setAdapter(listAdapter);

        // Listview Group click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                //Toast.makeText(getApplicationContext(), "Group Clicked " + listDataHeader1.get(groupPosition).getIconName(), Toast.LENGTH_SHORT).show();
                NavMenuModel navMenuModel = listDataHeader1.get(groupPosition);
                navigateScreenClickedOnDrawer(navMenuModel.getId());
                return false;
            }
        });

        // Listview Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                //Toast.makeText(getApplicationContext(), listDataHeader1.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader1.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader1.get(groupPosition).getAllSubMenu().get(childPosition).getIconName(),
                        Toast.LENGTH_SHORT)
                .show();*/
                NavMenuModel navMenuModel = listDataHeader1.get(groupPosition).getAllSubMenu().get(childPosition);
                navigateScreenClickedOnDrawer(navMenuModel.getId());
                return false;
            }
        });


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        userProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, UserProfileActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void prepareListData1() {
        listDataHeader1 = new ArrayList<NavMenuModel>();

        if (eduStr.equals("Y")) {
            NavMenuModel item1 = new NavMenuModel();
            item1.setIconName(AppConstant.NAME_JOBS);
            item1.setIconImg(R.drawable.bg_nav_dropdown);
            item1.setLeftImg(R.drawable.icon_job_board);
            item1.setId(AppConstant.ID_JOBS);
            // Adding data header
            listDataHeader1.add(item1);

            ArrayList<NavMenuModel> allSUbmenu = new ArrayList<>();

            NavMenuModel navMenuModel = new NavMenuModel();
            navMenuModel.setIconImg(R.drawable.icon_matching_vacancy);
            navMenuModel.setIconName(AppConstant.NAME_MATCHING_VACANCIES);
            navMenuModel.setId(AppConstant.ID_MATCHING_VACANCIES);
            allSUbmenu.add(navMenuModel);

            NavMenuModel navMenuModel2 = new NavMenuModel();
            navMenuModel2.setIconImg(R.drawable.icon_hot_jobs);
            navMenuModel2.setIconName(AppConstant.NAME_HOT_JOBS);
            navMenuModel2.setId(AppConstant.ID_HOT_JOBS);
            allSUbmenu.add(navMenuModel2);

            NavMenuModel navMenuModel3 = new NavMenuModel();
            navMenuModel3.setIconImg(R.drawable.icon_popular_jobs);
            navMenuModel3.setIconName(AppConstant.NAME_POPULARJOBS);
            navMenuModel3.setId(AppConstant.ID_POPULARJOBS);
            allSUbmenu.add(navMenuModel3);

            NavMenuModel navMenuModel4 = new NavMenuModel();
            navMenuModel4.setIconImg(R.drawable.icon_applied_by_my_team);
            navMenuModel4.setIconName(AppConstant.NAME_JOBS_APPLIED_BY_BATCHMATES);
            navMenuModel4.setId(AppConstant.ID_JOBS_APPLIED_BY_BATCHMATES);
            allSUbmenu.add(navMenuModel4);

            NavMenuModel navMenuModel5 = new NavMenuModel();
            navMenuModel5.setIconImg(R.drawable.icon_all_jobs);
            navMenuModel5.setId(AppConstant.ID_ALL_JOBS);
            navMenuModel5.setIconName(AppConstant.NAME_ALL_JOBS);
            allSUbmenu.add(navMenuModel5);

            item1.setAllSubMenu(allSUbmenu);

        } else {
            NavMenuModel item1 = new NavMenuModel();
            item1.setIconName(AppConstant.NAME_JOBS);
            item1.setIconImg(R.drawable.bg_nav_dropdown);
            item1.setLeftImg(R.drawable.icon_job_board);
            item1.setId(AppConstant.ID_JOBS);
            // Adding data header
            listDataHeader1.add(item1);

            ArrayList<NavMenuModel> allSUbmenu = new ArrayList<>();

            NavMenuModel navMenuModel = new NavMenuModel();
            navMenuModel.setIconImg(R.drawable.icon_matching_vacancy);
            navMenuModel.setIconName(AppConstant.NAME_MATCHING_VACANCIES);
            navMenuModel.setId(AppConstant.ID_MATCHING_VACANCIES);
            allSUbmenu.add(navMenuModel);

            NavMenuModel navMenuModel2 = new NavMenuModel();
            navMenuModel2.setIconImg(R.drawable.icon_hot_jobs);
            navMenuModel2.setId(AppConstant.ID_HOT_JOBS);
            navMenuModel2.setIconName(AppConstant.NAME_HOT_JOBS);
            allSUbmenu.add(navMenuModel2);

            NavMenuModel navMenuModel3 = new NavMenuModel();
            navMenuModel3.setIconImg(R.drawable.icon_popular_jobs);
            navMenuModel3.setIconName(AppConstant.NAME_POPULARJOBS);
            navMenuModel3.setId(AppConstant.ID_POPULARJOBS);
            allSUbmenu.add(navMenuModel3);

            NavMenuModel navMenuModel5 = new NavMenuModel();
            navMenuModel5.setIconImg(R.drawable.icon_all_jobs);
            navMenuModel5.setIconName(AppConstant.NAME_ALL_JOBS);
            navMenuModel5.setId(AppConstant.ID_ALL_JOBS);
            allSUbmenu.add(navMenuModel5);

            item1.setAllSubMenu(allSUbmenu);

        }

        ArrayList<NavMenuModel> allSUbmenu;

        NavMenuModel item2 = new NavMenuModel();
        item2.setIconName(AppConstant.NAME_INTERVIEW);
        item2.setIconImg(R.drawable.bg_nav_dropdown);
        item2.setLeftImg(R.drawable.icons_interview);
        item2.setId(AppConstant.ID_INTERVIEW);
        // Adding data header
        listDataHeader1.add(item2);

        ArrayList<NavMenuModel> allSUbmenu2 = new ArrayList<>();

        NavMenuModel navMenuModel6 = new NavMenuModel();
        navMenuModel6.setIconImg(R.drawable.icon_my_interview);
        navMenuModel6.setIconName(AppConstant.NAME_UPCOMING_INTERVIEW);
        navMenuModel6.setId(AppConstant.ID_UPCOMING_INTERVIEW);

        allSUbmenu2.add(navMenuModel6);

        NavMenuModel navMenuModel7 = new NavMenuModel();
        navMenuModel7.setIconImg(R.drawable.icons_all_application);
        navMenuModel7.setIconName(AppConstant.NAME_ALL_APPLIED_APPLICATION);
        navMenuModel7.setId(AppConstant.ID_ALL_APPLIED_APPLICATION);
        allSUbmenu2.add(navMenuModel7);
        item2.setAllSubMenu(allSUbmenu2);


        NavMenuModel item3 = new NavMenuModel();
        item3.setIconName(AppConstant.NAME_MY_DOCUMENTS);
        item3.setId(AppConstant.ID_MY_DOCUMENTS);
        item3.setLeftImg(R.drawable.icons_my_documents);

//            item3.setIconImg(R.drawable.bg_nav_exp_right);
        // Adding data header
        listDataHeader1.add(item3);
        allSUbmenu = new ArrayList<>();
        item3.setAllSubMenu(allSUbmenu);

        NavMenuModel item4 = new NavMenuModel();
        item4.setIconName(AppConstant.NAME_SUCCESS_STORIES);
        item4.setId(AppConstant.ID_SUCCESS_STORIES);
        item4.setLeftImg(R.drawable.icon_success_story);
        // Adding data header
        listDataHeader1.add(item4);
        allSUbmenu = new ArrayList<>();
        item4.setAllSubMenu(allSUbmenu);

        NavMenuModel item5 = new NavMenuModel();
        item5.setIconName(AppConstant.NAME_MY_GOODIES_STORES);
        item5.setIconImg(R.drawable.bg_nav_dropdown);
        item5.setLeftImg(R.drawable.icon_goodies_store);
        item5.setId(AppConstant.ID_GOODIES_STORE);
        // Adding data header
        listDataHeader1.add(item5);
        allSUbmenu = new ArrayList<>();

        NavMenuModel navMenuModel10 = new NavMenuModel();
        navMenuModel10.setIconImg(R.drawable.icon_my_store);
        navMenuModel10.setIconName(AppConstant.NAME_MY_GOODIES_STORES);
        navMenuModel10.setId(AppConstant.ID_MY_GOODIES_STORES);
        allSUbmenu.add(navMenuModel10);

        NavMenuModel navMenuModel11 = new NavMenuModel();
        navMenuModel11.setIconImg(R.drawable.icon_my_orders);
        navMenuModel11.setIconName(AppConstant.NAME_MY_ORDERS);
        navMenuModel11.setId(AppConstant.ID_MY_ORDERS);
        allSUbmenu.add(navMenuModel11);

        item5.setAllSubMenu(allSUbmenu);

        NavMenuModel item6 = new NavMenuModel();
        item6.setIconName(AppConstant.NAME_LOGOUT);
        item6.setId(AppConstant.ID_LOGOUT);
        item6.setLeftImg(R.drawable.icon_logout);

        // Adding data header
        listDataHeader1.add(item6);
        allSUbmenu = new ArrayList<>();
        item6.setAllSubMenu(allSUbmenu);


    }


    void navigateScreenClickedOnDrawer(String id) {
        switch (id) {
            case AppConstant.ID_MATCHING_VACANCIES:
                break;

            case AppConstant.ID_HOT_JOBS:
                break;

            case AppConstant.ID_POPULARJOBS:
                break;

            case AppConstant.ID_JOBS_APPLIED_BY_BATCHMATES:
                break;

            case AppConstant.ID_ALL_JOBS:
                break;

            case AppConstant.ID_UPCOMING_INTERVIEW:
                openUpcomingInterview();
                break;

            case AppConstant.ID_ALL_APPLIED_APPLICATION:
                openAppliedApplication();
                break;

            case AppConstant.ID_MY_DOCUMENTS:
                openMyDocuments();
                break;
            case AppConstant.ID_SUCCESS_STORIES:
                openSuccessStories();
                break;

            case AppConstant.ID_MY_GOODIES_STORES:
                openMyGoodiesScreen();
                break;

            case AppConstant.ID_MY_ORDERS:
                openMyGoodiesScreen();
                break;

            case AppConstant.ID_LOGOUT:
                drawerLayout.closeDrawers();
                openLogoutDialogue();
                break;
        }
    }

    void openMyGoodiesScreen() {
        Intent intent = new Intent(DashboardActivity.this, MyGoodiesStoreActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    void openMyOrders() {
        Intent intent = new Intent(DashboardActivity.this, MyOrdersActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    void openSuccessStories() {
        Intent intent = new Intent(DashboardActivity.this, SuccessStoriesActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    void openMyDocuments() {
        Intent intent = new Intent(DashboardActivity.this, MyDocumentsActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    void openAppliedApplication() {
        Intent intent = new Intent(DashboardActivity.this, AppliedApplicationActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }

    void openUpcomingInterview() {
        Intent intent = new Intent(DashboardActivity.this, UpcomingInterviewActivity.class);
        startActivity(intent);
        drawerLayout.closeDrawers();
    }


    void openLogoutDialogue() {
        drawerLayout.closeDrawer(Gravity.LEFT);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(AppConstant.LOGOUT_TEXT);
        builder1.setTitle(getResources().getString(R.string.app_name));
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        GlobalPreferenceManager.saveStringForKey(getApplicationContext(), AppConstant.KEY_IS_REGISTER, "N");
                        GlobalPreferenceManager.saveStringForKey(getApplicationContext(), AppConstant.KEY_CONTACT_VERIFIED, "0");
                        Intent intent = new Intent(getApplicationContext(), GetStartedActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawers();
        } else {
            drawerLayout.closeDrawers();
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(AppConstant.EXIT_TEXT);
            builder1.setTitle(getResources().getString(R.string.app_name));
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

}
