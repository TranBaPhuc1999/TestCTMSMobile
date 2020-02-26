package com.dmt.tranbaphuc1999.ctms;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

//    Khai báo một đống thứ để dùng
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    NavigationView navView;
    View header;
    Toolbar toolbar;
    ConstraintLayout constraintLayoutNav_Header;
    Button buttonSenFeedBack;
    TextView textViewLogout, textViewChangePass, textViewName, textViewEmail;

    private boolean finishAfterCurrentTask = false;

//    Cái này riêng cho ExpandableListView này
    ExpandableListView expandableListView;
    List<SubjectsExpandableListView> listSubjectsGroup;
    HashMap<SubjectsExpandableListView,List<String>> listSubjectsChild;
    ExpandableListViewAdapter_Main expandableListViewAdapter_main;

//    Cái này cho TabLayout
    com.dmt.tranbaphuc1999.ctms.CoordinatorTabLayout mCoordinatorTabLayout;

//    Danh sách hình ảnh và màu cho TabLayout
    int[] mImageArray;
    int[] mColorArray;

//    Cái này cho list thông tin sinh viên
    LinearLayout linearLayoutMain, linearLayoutInfor;
    ListView listViewInfor;
    ImageView imageViewInforDown, imageViewInforUp;

//    Shared Preferences để lưu user
    static SharedPreferences sharedPreferencesUser;
    SharedPreferences.Editor editor = sharedPreferencesUser.edit();



    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Take_Data(MainActivity.this).execute();

//        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        Ánh xạ
        Init();



//        Thêm và cài Header cho Nav
        header = navView.getHeaderView(0);
        constraintLayoutNav_Header = (ConstraintLayout) header.findViewById(R.id.constraintLayoutNav_Header);
        constraintLayoutNav_Header.setBackgroundResource(ChangeNavHeaderBackground.Change());

        textViewName = (TextView) header.findViewById(R.id.textViewName);          /* TextView tên và email sinh viên*/
        textViewEmail = (TextView) header.findViewById(R.id.textViewEmail);
//        Ánh xạ hai cái nút mũi tên này
        imageViewInforDown = (ImageView) header.findViewById(R.id.imageViewInforDown);
        imageViewInforUp = (ImageView) header.findViewById(R.id.imageViewInforUp);
        imageViewInforUp.setVisibility(View.GONE); /*Ẩn cái nút mũi tên lên khi mới mở*/


//        Thêm dữ liệu list thông tin sinh viên
        addListViewInfor();
        linearLayoutInfor.setVisibility(View.GONE);


//        Thêm và cài đặt ExpandableListView
        AddExpandableListView();

        expandableListView.setAdapter(expandableListViewAdapter_main);
        OpenGroup();
        ChildClick();




        setSupportActionBar(toolbar);

//        Thêm Tablayout
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager()); /*Chưa rõ cái này lắm, kệ đi*/
        mViewPager.setAdapter(mSectionsPagerAdapter);

        CustomTabLayout();

        mCoordinatorTabLayout = (com.dmt.tranbaphuc1999.ctms.CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout . setImageArray (mImageArray, mColorArray)
                .setupWithViewPager (mViewPager);



//        Vẽ cho Nav, chưa tìm hiểu kĩ
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


//        Chắc là cài hiển thị Nav nếu nó không có
        if (navView != null) {
            setupDrawerContent(navView);
        }

        ChangeArrow();
        SendFeedBack();
        ClickLogout();
    }


    // Cài đặt khi click các mục con trong ExpandableListView thì chuyển đến Màn hình khác,
    // Cái nào chưa tạo thì đặt Toasty là chưa xâu dựng
    private void ChildClick() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                switch (listSubjectsChild.get(listSubjectsGroup.get(groupPosition)).get(childPosition)){
                    case "Xem điểm" : startActivity(new Intent(MainActivity.this,Nav_Show_Score.class));   break;
                    case "Lịch thi" : startActivity(new Intent(MainActivity.this,Nav_Show_Exam_Schedule.class));  break;
                    case "Đăng kí lớp tín chỉ" :
                        Toasty.warning(MainActivity.this, "Chức năng chưa được xây dựng!", Toast.LENGTH_SHORT, true).show(); break;
                    case "Hóa đơn học phí" : startActivity(new Intent(MainActivity.this,Nav_bill.class)); ; break;
                    case "Dịch vụ OCC" : startActivity(new Intent(MainActivity.this,Nav_Service_OCC.class));  break;
                    case "Từ chối sử dụng CTMS" :
                        Toasty.warning(MainActivity.this, "Chức năng chưa được xây dựng!", Toast.LENGTH_SHORT, true).show(); break;
                    case "Chương trình đào tạo" : startActivity(new Intent(MainActivity.this,Nav_List_Subjects.class));  break;
                    case "Hồ sơ giảng viên" : startActivity(new Intent(MainActivity.this,Nav_List_Teacher.class));  break;
                    case "Hướng dẫn sử dụng" : Toasty.warning(MainActivity.this, "Chức năng chưa được xây dựng!", Toast.LENGTH_SHORT, true).show(); break;
                    case "Thông tin phần mềm" : Toasty.warning(MainActivity.this, "Chức năng chưa được xây dựng!", Toast.LENGTH_SHORT, true).show(); break;
                }

                return true;

            }
        });

    }


//    Ánh xạ các thành phần cơ bản
    private void Init(){
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListViewMain);
        listSubjectsGroup = new ArrayList<>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSubjectsChild = new HashMap<SubjectsExpandableListView, List<String>>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navView = (NavigationView) findViewById(R.id.nav_view);
        listViewInfor = (ListView) findViewById(R.id.listViewInfor);
        mViewPager = (ViewPager) findViewById(R.id.vp);
        expandableListViewAdapter_main = new ExpandableListViewAdapter_Main(MainActivity.this,listSubjectsGroup,listSubjectsChild);
        linearLayoutMain = (LinearLayout) findViewById(R.id.linearLayoutMain);
        linearLayoutInfor = (LinearLayout) findViewById(R.id.linearLayoutInfor);
        buttonSenFeedBack = (Button) findViewById(R.id.buttonSendfeedback);
        textViewChangePass = (TextView) findViewById(R.id.textView_Change_Pass);
        textViewLogout = (TextView) findViewById(R.id.textView_Logout);
    }


//    Thêm dữ liệu cho ExpandableListView
    private void AddExpandableListView(){

//        Đống tên chỉ mục đầu
        listSubjectsGroup.add(new SubjectsExpandableListView(R.drawable.icon_personal,"Cá nhân"));
        listSubjectsGroup.add(new SubjectsExpandableListView(R.drawable.icon_service_occ,"Dịch vụ"));
        listSubjectsGroup.add(new SubjectsExpandableListView(R.drawable.icon_share,"Chia sẻ"));
        listSubjectsGroup.add(new SubjectsExpandableListView(R.drawable.icon_help,"Trợ giúp/Giới thiệu"));

        List<String> name0 = new ArrayList<>();
        name0.add("Xem điểm");
        name0.add("Lịch thi");
        name0.add("Đăng kí lớp tín chỉ");
        name0.add("Hóa đơn học phí");

        List<String> name1 = new ArrayList<>();
        name1.add("Dịch vụ OCC");
        name1.add("Từ chối sử dụng CTMS");

        List<String> name2 = new ArrayList<>();
        name2.add("Tài liệu học tập");
        name2.add("Hình ảnh đẹp");

        List<String> name3 = new ArrayList<>();
        name3.add("Chương trình đào tạo");
        name3.add("Hồ sơ giảng viên");
        name3.add("Hướng dẫn sử dụng");
        name3.add("Thông tin phần mềm");

        listSubjectsChild.put(listSubjectsGroup.get(0),name0);
        listSubjectsChild.put(listSubjectsGroup.get(1),name1);
        listSubjectsChild.put(listSubjectsGroup.get(2),name2);
        listSubjectsChild.put(listSubjectsGroup.get(3),name3);
    }


//  Tự động đóng một nhóm khi mở một nhóm khác tại Expandablelistview trong Nav
    private void OpenGroup(){
        final int[] lastExpandedPosition = {-1}; /*Cài giá trị đầu là -1*/
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                if (lastExpandedPosition[0] != -1   /*Nếu giá trị này khác -1 và và mục đang ở */
                        && i != lastExpandedPosition[0]) {
                    expandableListView.collapseGroup(lastExpandedPosition[0]); /*Đóng cái mục cuối cùng vừa mở*/
                }
                lastExpandedPosition[0] = i;  /*Gán giá trị mục cuối cùng vừa mở là mục vừa mở*/
            }
        });
    }


//  Chắc là hàm cho nút quay lại :)
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//  Khởi tạo Menu Options (trên góc)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



//    Hàm mặc định khi tạo view Nav, chưa kiểm tra xem tác dụng nó là gì :)))
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


//    Cài đặt cho TabLayout tại Main
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Fragment_Tabbar_Schedule_Room fragmentTabbarScheduleRoom;
        private Fragment_Tabbar_Schedule_Class fragmentTabbarScheduleClass;
        private Fragment_Tabbar_News fragmentTabbarNews;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentTabbarScheduleRoom = new Fragment_Tabbar_Schedule_Room();
            fragmentTabbarScheduleClass = new Fragment_Tabbar_Schedule_Class();
            fragmentTabbarNews = new Fragment_Tabbar_News();

        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position){
                case 0:
                    return fragmentTabbarScheduleRoom;
                case 1:
                    return fragmentTabbarScheduleClass;
                case 2:
                    return fragmentTabbarNews;
            }
            return null;
        }


        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }


//    Thêm thông tin sinh viên của Nav view
    private void addListViewInfor(){
        List<String> arrayListInfor = new ArrayList<>();
        textViewName.setText(sharedPreferencesUser.getString("NameUser","").substring(2));       /*Cài đặt tên và email trên header Nav*/
        textViewEmail.setText(sharedPreferencesUser.getString("EmailOld",""));

        arrayListInfor.add("Khoa"+sharedPreferencesUser.getString("FacultyUser",""));      /*       Các thông tin tại list mặt sau của nav*/
        arrayListInfor.add("Hệ"+sharedPreferencesUser.getString("HeUser",""));
        arrayListInfor.add("Khóa học"+sharedPreferencesUser.getString("CourseUser",""));
        arrayListInfor.add("Ngành học"+sharedPreferencesUser.getString("BranchUser",""));
        arrayListInfor.add("Lớp hành chính"+sharedPreferencesUser.getString("ClassUser",""));
        arrayListInfor.add("Mã sinh viên"+sharedPreferencesUser.getString("CodeUser",""));
        arrayListInfor.add("Ngày sinh"+sharedPreferencesUser.getString("BirthDayUser",""));
        arrayListInfor.add("Tổng số tín chỉ: 39");
        arrayListInfor.add("Trung bình tích lũy: 3.31");


        Adapter_ListView_Infor adapter_listView_infor = new Adapter_ListView_Infor(MainActivity.this,R.layout.row_nav_show_infor,arrayListInfor);
        listViewInfor.setAdapter(adapter_listView_infor);
    }


//    Thêm màu và ảnh cho TabLayout và Action Bar
    private void CustomTabLayout(){
//        Thêm mảng hình ảnh cho TabLayout nè, nên viết ra hàm riêng
        mImageArray = new int[]{
                R.drawable.hou_image_1,
                R.drawable.hou_image_2,
                R.drawable.hou_image_3};

//        Thêm mảng màu cho TabLayout nè, nên viết ra hàm riêng
        mColorArray =  new  int [] {
                R.color.blue1,
                R.color.blue2,
                R.color.blue3};
    }


//    Đổi icon mũi tên lên xuống và list dưới trong header của Nav
    private void ChangeArrow(){

        imageViewInforDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewInforDown.setVisibility(View.GONE);
                imageViewInforUp.setVisibility(View.VISIBLE);
                linearLayoutMain.setVisibility(View.GONE);
                linearLayoutInfor.setVisibility(View.VISIBLE);
            }
        });

        imageViewInforUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewInforUp.setVisibility(View.GONE);
                imageViewInforDown.setVisibility(View.VISIBLE);
                linearLayoutInfor.setVisibility(View.GONE);
                linearLayoutMain.setVisibility(View.VISIBLE);
            }
        });

    }


//    Cài đặt click nút gửi phản hồi
    private void SendFeedBack(){
        buttonSenFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Nav_Help.class));
            }
        });
    }


//    Cài đặt click nút Đăng xuất
    private void ClickLogout(){
        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);     /*   Cài đặt các thuộc tính cơ bản của dialog*/
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_yes_no);
                dialog.setCanceledOnTouchOutside(false);

                TextView textViewQuestion = (TextView) dialog.findViewById(R.id.textViewQuestionYesNo);
                Button buttonNo = (Button) dialog.findViewById(R.id.buttonNo);
                Button buttonYes = (Button) dialog.findViewById(R.id.buttonYes);

                textViewQuestion.setText("Bạn có muốn đăng xuất không?");     /*  Click không thì tắt dialog, có thì đăng xuất thử CTMS và chuyển đến trang đăng nhập*/

                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                buttonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(new AsyncTask_Logout(MainActivity.this).execute().get().equals("success")){
                                startActivity(new Intent(MainActivity.this,Login.class));
                            } else
                                Toasty.warning(MainActivity.this, "Đăng xuất thất bại, thử lại sau!", Toast.LENGTH_LONG, true).show();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                });
                dialog.show();
            }
        });

    }

}
