package com.twocoms.rojgarkendra.dashboardscreen.controler;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.twocoms.rojgarkendra.R;
import com.twocoms.rojgarkendra.dashboardscreen.model.NavMenuModel;
import com.twocoms.rojgarkendra.dashboardscreen.view.ExpandableListAdapter;
import com.twocoms.rojgarkendra.dashboardscreen.view.ExpandableListViewAdapter1;
import com.twocoms.rojgarkendra.userprofilescreen.sample.controler.UserProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ExpandableListView expandableListView;
    NavigationView navigationView;
//    List<String> listDataHeader;
    ArrayList<NavMenuModel> listDataHeader1;
    //    HashMap<String, List<String>> listDataChild;
//    HashMap<NavMenuModel, List<String>> listDataChild1;
    ExpandableListAdapter listAdapter;
//    ExpandableListViewAdapter1 listAdapter1;
    ImageView menu;
    ImageView userProfileBtn;
//    public NavMenuModel navMenuModel;


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
        userProfileBtn = (ImageView)findViewById(R.id.user_profile_img);

        //prepareListData();

        prepareListData1();
        listAdapter = new ExpandableListAdapter(DashboardActivity.this,listDataHeader1);

//        listAdapter1 = new ExpandableListViewAdapter1(this, listDataHeader1, listDataChild1);


        // setting list adapter
        expandableListView.setAdapter(listAdapter);

        // Listview Group click listener
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                 Toast.makeText(getApplicationContext(), "Group Clicked " + listDataHeader1.get(groupPosition).getIconName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(), listDataHeader1.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),
                        listDataHeader1.get(groupPosition).getAllSubMenu().get(childPosition).getIconName(),
                        Toast.LENGTH_SHORT)
                .show();
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

//    private void prepareListData() {
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, List<String>>();
//
//        // Adding child data
//        listDataHeader.add("Job board");
//        listDataHeader.add("Interview");
//        listDataHeader.add("My documents");
//        listDataHeader.add("Success Stories");
//        listDataHeader.add("Goodies store");
//        listDataHeader.add("Logout");
//
//        // Adding child data
//        List<String> jobchild = new ArrayList<String>();
//        jobchild.add("Matching Vacancies");
//        jobchild.add("Hot jobs");
//        jobchild.add("Popular jobs");
//        jobchild.add("Jobs applied your batchmates (only for Edujobs Candidates)");
//        jobchild.add("All jobs");
//
//
//        List<String> job_pre_child = new ArrayList<String>();
//        /*nowShowing.add("The Conjuring");
//        nowShowing.add("Despicable Me 2");
//        nowShowing.add("Turbo");
//        nowShowing.add("Grown Ups 2");
//        nowShowing.add("Red 2");
//        nowShowing.add("The Wolverine");*/
//
//        List<String> intChild = new ArrayList<String>();
//        intChild.add("Upcoming interviews");
//        intChild.add("All applications");
//
//        List<String> successChild = new ArrayList<String>();
//
//        List<String> goodiesChild = new ArrayList<String>();
//
//
//        listDataChild.put(listDataHeader.get(0), jobchild); // Header, Child data
//        //listDataChild.put(listDataHeader.get(1), job_pre_child);
//        listDataChild.put(listDataHeader.get(1), intChild);
//        //listDataChild.put(listDataHeader.get(3), successChild);
//        //listDataChild.put(listDataHeader.get(4), goodiesChild);
//    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void prepareListData1() {
        listDataHeader1 = new ArrayList<NavMenuModel>();
//        listDataChild1 = new HashMap<NavMenuModel, List<String>>();

        NavMenuModel item1 = new NavMenuModel();
        item1.setIconName("Job board");
        //item1.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item1);

        ArrayList<NavMenuModel > allSUbmenu = new ArrayList<>();

        NavMenuModel navMenuModel = new NavMenuModel();
        navMenuModel.setIconImg(android.R.drawable.ic_delete);
        navMenuModel.setIconName("Matching Vacancies");
        allSUbmenu.add(navMenuModel);

        NavMenuModel navMenuModel2 = new NavMenuModel();
        navMenuModel2.setIconImg(android.R.drawable.ic_delete);
        navMenuModel2.setIconName("Hot jobs");
        allSUbmenu.add(navMenuModel2);

        NavMenuModel navMenuModel3 = new NavMenuModel();
        navMenuModel3.setIconImg(android.R.drawable.ic_delete);
        navMenuModel3.setIconName("Popular jobs");
        allSUbmenu.add(navMenuModel3);

        NavMenuModel navMenuModel4 = new NavMenuModel();
        navMenuModel4.setIconImg(android.R.drawable.ic_delete);
        navMenuModel4.setIconName("Jobs applied your batchmates (only for Edujobs Candidates)");
        allSUbmenu.add(navMenuModel4);

        NavMenuModel navMenuModel5 = new NavMenuModel();
        navMenuModel5.setIconImg(android.R.drawable.ic_delete);
        navMenuModel5.setIconName("All jobs");
        allSUbmenu.add(navMenuModel5);

        item1.setAllSubMenu(allSUbmenu);


      /*  NavMenuModel item2 = new NavMenuModel();
        item2.setIconName("Job Preferences");
        item2.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item2);

        allSUbmenu = new ArrayList<>();
        item2.setAllSubMenu(allSUbmenu);*/



        NavMenuModel item2 = new NavMenuModel();
        item2.setIconName("Interview");
        //item2.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item2);

        ArrayList<NavMenuModel > allSUbmenu2 = new ArrayList<>();

        NavMenuModel navMenuModel6 = new NavMenuModel();
        navMenuModel6.setIconImg(android.R.drawable.ic_delete);
        navMenuModel6.setIconName("Upcoming interviews");
        allSUbmenu2.add(navMenuModel6);

        NavMenuModel navMenuModel7 = new NavMenuModel();
        navMenuModel7.setIconImg(android.R.drawable.ic_delete);
        navMenuModel7.setIconName("All applications");
        allSUbmenu2.add(navMenuModel7);


        item2.setAllSubMenu(allSUbmenu2);


        NavMenuModel item3 = new NavMenuModel();
        item3.setIconName("Virtual Job Fair");
        item3.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item3);

        allSUbmenu = new ArrayList<>();

        item3.setAllSubMenu(allSUbmenu);



        NavMenuModel item4 = new NavMenuModel();
        item4.setIconName("My documents");
        item4.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item4);

        allSUbmenu = new ArrayList<>();

        item4.setAllSubMenu(allSUbmenu);

        NavMenuModel item5 = new NavMenuModel();
        item5.setIconName("Success Stories");
        item5.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item5);

        allSUbmenu = new ArrayList<>();

        item5.setAllSubMenu(allSUbmenu);

        NavMenuModel item6 = new NavMenuModel();
        item6.setIconName("Goodies store");
        item6.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item6);

        allSUbmenu = new ArrayList<>();

        item6.setAllSubMenu(allSUbmenu);

        NavMenuModel item7 = new NavMenuModel();
        item7.setIconName("Logout");
        item7.setIconImg(android.R.drawable.ic_delete);
        // Adding data header
        listDataHeader1.add(item7);

        allSUbmenu = new ArrayList<>();

        item7.setAllSubMenu(allSUbmenu);



//        NavMenuModel item2 = new NavMenuModel();
//        item2.setIconName("Interview");
//        item2.setIconImg(android.R.drawable.ic_delete);
//        listDataHeader1.add(item2);
//
//        NavMenuModel item3 = new NavMenuModel();
//        item3.setIconName("My documents");
//        item3.setIconImg(android.R.drawable.ic_delete);
//        listDataHeader1.add(item3);
//
//        NavMenuModel item4 = new NavMenuModel();
//        item4.setIconName("Success Stories");
//        item4.setIconImg(android.R.drawable.ic_delete);
//        listDataHeader1.add(item4);
//
//        NavMenuModel item5 = new NavMenuModel();
//        item5.setIconName("Goodies store");
//        item5.setIconImg(android.R.drawable.ic_delete);
//        listDataHeader1.add(item5);
//
//        NavMenuModel item6 = new NavMenuModel();
//        item6.setIconName("Logout");
//        item6.setIconImg(android.R.drawable.ic_delete);
//        listDataHeader1.add(item6);

        // Adding child data
//        List<String> jobChild = new ArrayList<String>();
//        jobChild.add("Matching Vacancies");
//        jobChild.add("Hot jobs");
//        jobChild.add("Popular jobs");
//        jobChild.add("Jobs applied your batchmates (only for Edujobs Candidates)");
//        jobChild.add("All jobs");
//
//        List<String> intChild = new ArrayList<String>();
//        intChild.add("Upcoming interviews");
//        intChild.add("All applications");
//
//        listDataChild1.put(listDataHeader1.get(0), jobChild);// Header, Child data
//        listDataChild1.put(listDataHeader1.get(1), intChild);

    }


}
