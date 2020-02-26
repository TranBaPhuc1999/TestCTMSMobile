package com.dmt.tranbaphuc1999.ctms;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.dmt.tranbaphuc1999.ctms.MainActivity.sharedPreferencesUser;

public class Nav_List_Teacher extends AppCompatActivity {

    ListView listView;
    ArrayList<List_Teacher> arrayList;
    Adapter_Nav_List_Teacher adapter_nav_list_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__list__teacher);

        //Init
        listView = (ListView) findViewById(R.id.listViewTeacher);
        arrayList = new ArrayList<>();

        //Set Adapter for ListView
        Add();
        adapter_nav_list_teacher = new Adapter_Nav_List_Teacher(this,R.layout.row_nav_list_teacher,arrayList);
        listView.setAdapter(adapter_nav_list_teacher);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Boolean check = false;
            Cursor dataDetail = Connection_CTMS.dataBase.GetData("SELECT * FROM tblTeacher_Sub ");
                while(dataDetail.moveToNext()){
                        if(arrayList.get(position).getId()==dataDetail.getInt(1)){
                            check=true;
                            break;
                        }
                }
                if (check) {
                    Detail_Teacher(arrayList.get(position));
                } else {
                    try {
                        new Take_teacher_sub(Nav_List_Teacher.this,arrayList.get(position).getId()).execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Detail_Teacher(arrayList.get(position));
                }

            }
        });

    }


    //Add element for arrayList
    private void Add() {
        arrayList.add(new List_Teacher("GV. Lương Cao Đông",1));
        arrayList.add(new List_Teacher("GV. Trương Công Đoàn",2));
        arrayList.add(new List_Teacher("GV. Lê Hữu Dũng",3));
        arrayList.add(new List_Teacher("GV. Trần Tiến Dũng (b)",4));
        arrayList.add(new List_Teacher("GV. Trần Duy Hùng",5));
        arrayList.add(new List_Teacher("GV. Phạm Công Hoà",6));
        arrayList.add(new List_Teacher("GV. Nguyễn Thành Huy",7));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Thuý Lan",8));
        arrayList.add(new List_Teacher("GV. Đinh Tuấn Long",9));
        arrayList.add(new List_Teacher("GV. Dương Thăng Long",10));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Quỳnh Như",11));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Tâm",12));
        arrayList.add(new List_Teacher("GV. Lê Thị Thanh Thuỳ",13));
        arrayList.add(new List_Teacher("GV. Nguyễn Đức Tuấn",14));
        arrayList.add(new List_Teacher("GV. Nguyễn Địch",15));
        arrayList.add(new List_Teacher("GV. Trịnh Thị Xuân",16));
        arrayList.add(new List_Teacher("GV. Nguyễn Hoài Anh",17));
        arrayList.add(new List_Teacher("GV. Thạc Bình Cường",18));
        arrayList.add(new List_Teacher("GV. Bùi Công Cường",19));
        arrayList.add(new List_Teacher("GV. Phạm Văn Ất",20));
        arrayList.add(new List_Teacher("GV. Trần Lan Hương",21));
        arrayList.add(new List_Teacher("GV. Vũ Chấn Hưng",23));
        arrayList.add(new List_Teacher("GV. Ngô Thế Khánh",24));
        arrayList.add(new List_Teacher("GV. Đinh Hoài Nam",25));
        arrayList.add(new List_Teacher("GV. Nguyễn Đức Nghĩa",26));
        arrayList.add(new List_Teacher("GV. Nguyễn Lan Phương",27));
        arrayList.add(new List_Teacher("GV. Đặng Thành Phu",28));
        arrayList.add(new List_Teacher("GV. Hoàng Mạnh Quân",29));
        arrayList.add(new List_Teacher("GV. Thái Thanh Sơn",30));
        arrayList.add(new List_Teacher("GV. Đỗ Thị Tố Hoa",31));
        arrayList.add(new List_Teacher("GV. Nguyễn Minh Thư",32));
        arrayList.add(new List_Teacher("GV. Nguyễn Tô Thành",33));
        arrayList.add(new List_Teacher("GV. Phạm Phương Thảo",34));
        arrayList.add(new List_Teacher("GV. Đinh Thị Duy Thanh",35));
        arrayList.add(new List_Teacher("GV. Thái Thanh Tùng",36));
        arrayList.add(new List_Teacher("GV. Nguyễn Thanh Tùng",37));
        arrayList.add(new List_Teacher("GV. Lê Thanh Vân",38));
        arrayList.add(new List_Teacher("GV. Mai Thanh Trúc",39));
        arrayList.add(new List_Teacher("GV. Đào Thanh Tĩnh",40));
        arrayList.add(new List_Teacher("GV. Nguyễn Hoàng Vân",41));
        arrayList.add(new List_Teacher("GV. Vũ Việt Anh",42));
        arrayList.add(new List_Teacher("GV. Nguyễn Tài Hào",43));
        arrayList.add(new List_Teacher("GV. Ninh Đức Thành",44));
        arrayList.add(new List_Teacher("GV. Trương Tiến Tùng",46));
        arrayList.add(new List_Teacher("GV. Nguyễn Mai Lan",47));
        arrayList.add(new List_Teacher("GV. Thái Thanh Long",48));
        arrayList.add(new List_Teacher("GV. Lê Văn Phùng",49));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Thanh Thủy",50));
        arrayList.add(new List_Teacher("GV. Vương Thu Trang",51));
        arrayList.add(new List_Teacher("GV. Đinh Thị Việt Nga",52));
        arrayList.add(new List_Teacher("GV. Vương Hùng Tấn",53));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Việt Hương",54));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Thu Thủy",55));
        arrayList.add(new List_Teacher("GV. Lê Thanh Quang",56));
        arrayList.add(new List_Teacher("GV. Nguyễn Thùy Linh",57));
        arrayList.add(new List_Teacher("GV. Nguyễn Ái Dân",58));
        arrayList.add(new List_Teacher("GV. Nguyễn Chiến Thắng",59));
        arrayList.add(new List_Teacher("GV. Vũ Thị Bích Hiệp",60));
        arrayList.add(new List_Teacher("GV. Trần Thị Tuyết",61));
        arrayList.add(new List_Teacher("GV. Phạm Ngọc Tuấn",62));
        arrayList.add(new List_Teacher("GV. Hoàng Thị Bình",63));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Hoàng Anh",64));
        arrayList.add(new List_Teacher("GV. Nguyễn Ngọc Bích",66));
        arrayList.add(new List_Teacher("GV. Trần Hữu Trường",67));
        arrayList.add(new List_Teacher("GV. Tôn Tích Ái",68));
        arrayList.add(new List_Teacher("GV. Nguyễn Quốc Đoàn",69));
        arrayList.add(new List_Teacher("GV. Đoàn Văn Đức",70));
        arrayList.add(new List_Teacher("GV. Phạm Quý Dương",71));
        arrayList.add(new List_Teacher("GV. Đoàn Đức Hạnh",73));
        arrayList.add(new List_Teacher("GV. Lương Minh Hạnh",74));
        arrayList.add(new List_Teacher("GV. Đào Thị Hợp",75));
        arrayList.add(new List_Teacher("GV. Đặng Đức Kim",78));
        arrayList.add(new List_Teacher("GV. Lê Ngọc Liệu",79));
        arrayList.add(new List_Teacher("GV. Vũ Văn Lương",81));
        arrayList.add(new List_Teacher("GV. Nguyễn Hữu Mạnh",82));
        arrayList.add(new List_Teacher("GV. Nguyễn Hồng Nhật",83));
        arrayList.add(new List_Teacher("GV. Đặng Trần Phú",84));
        arrayList.add(new List_Teacher("GV. Phạm Nguyên Phương",86));
        arrayList.add(new List_Teacher("GV. Nguyễn Thanh Quang",87));
        arrayList.add(new List_Teacher("GV. Trần Thọ Quang",88));
        arrayList.add(new List_Teacher("GV. Trần Ngọc Thăng",90));
        arrayList.add(new List_Teacher("GV. Nguyễn Thanh Thủy",93));
        arrayList.add(new List_Teacher("GV. Trần Xuân Tiếp",94));
        arrayList.add(new List_Teacher("GV. Nguyễn Trọng Toàn",95));
        arrayList.add(new List_Teacher("GV. Nguyễn Đăng Tuấn",96));
        arrayList.add(new List_Teacher("GV. Bùi Đức Tiến",97));
        arrayList.add(new List_Teacher("GV. Đỗ Thị Huyền Trang",98));
        arrayList.add(new List_Teacher("GV. Hoàng Quốc Tuấn",101));
        arrayList.add(new List_Teacher("GV. Nguyễn Đức Hiểu",102));
        arrayList.add(new List_Teacher("GV. Cao Mạnh Toàn",103));
        arrayList.add(new List_Teacher("GV. Phạm Hoàng Lan",104));
        arrayList.add(new List_Teacher("GV. Trần Thái Sơn",105));
        arrayList.add(new List_Teacher("GV. Trần Thị Thuý",106));
        arrayList.add(new List_Teacher("GV. Trần Lan Thu",107));
        arrayList.add(new List_Teacher("GV. Hồ Văn Hương",108));
        arrayList.add(new List_Teacher("GV. Trần Hữu Tráng",111));
        arrayList.add(new List_Teacher("GV. Nguyễn Văn Quang",112));
        arrayList.add(new List_Teacher("GV. Lê Trường Sơn",113));
        arrayList.add(new List_Teacher("GV. Nguyễn Xuân Quế",114));
        arrayList.add(new List_Teacher("GV. Dương Hoài Văn",116));
        arrayList.add(new List_Teacher("GV. Mai Thị Thanh",117));
        arrayList.add(new List_Teacher("GV. Nguyễn Long Giang",118));
        arrayList.add(new List_Teacher("GV. Lê Thị Nhân",119));
        arrayList.add(new List_Teacher("GV. Mai Thị Thúy Hà",120));
        arrayList.add(new List_Teacher("GV. Mai Hồng Hà",122));
        arrayList.add(new List_Teacher("GV. Đỗ Xuân Chợ",123));
        arrayList.add(new List_Teacher("GV. Đặng Hải Đăng",126));
        arrayList.add(new List_Teacher("GV. Nguyễn Tiến Dũng",127));
        arrayList.add(new List_Teacher("GV. Trần Tiến Dũng (c)",128));
        arrayList.add(new List_Teacher("GV. Nguyễn Quang Hoan",129));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Phan Mai",131));
        arrayList.add(new List_Teacher("GV. Võ Thành Trung",132));
        arrayList.add(new List_Teacher("GV. Nguyễn Thế Hoá",133));
        arrayList.add(new List_Teacher("GV. Chờ Phân Công",134));
        arrayList.add(new List_Teacher("GV. Lê Thị Minh Thảo",135));
        arrayList.add(new List_Teacher("GV. Đinh Thị Hồng Trang",136));
        arrayList.add(new List_Teacher("GV. Vũ Xuân Hạnh",137));
        arrayList.add(new List_Teacher("GV. Hoàng Anh Dũng",139));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Huyền",140));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Ánh Nguyệt",141));
        arrayList.add(new List_Teacher("GV. Nguyễn Kim Chi",142));
        arrayList.add(new List_Teacher("GV. Nguyễn Nam Chi",143));
        arrayList.add(new List_Teacher("GV. Dương Chí Bằng",144));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Kim Ngân",145));
        arrayList.add(new List_Teacher("GV. Vũ Tuấn Anh",146));
        arrayList.add(new List_Teacher("GV. Phạm Hùng Cường",147));
        arrayList.add(new List_Teacher("GV. Hoàng Thị Thanh",148));
        arrayList.add(new List_Teacher("GV. Lê Thị Giang",149));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Phi Doan",150));
        arrayList.add(new List_Teacher("GV. Đào Xuân Phúc",151));
        arrayList.add(new List_Teacher("GV. Hoàng Thu Huyền",152));
        arrayList.add(new List_Teacher("GV. Nguyễn Ngân Trâm",153));
        arrayList.add(new List_Teacher("GV. Phùng Khắc Dũng",154));
        arrayList.add(new List_Teacher("GV. Phùng Trọng Quế",155));
        arrayList.add(new List_Teacher("GV. Nguyễn Văn Hộ",156));
        arrayList.add(new List_Teacher("GV. Trần Thị Hồng Oanh",157));
        arrayList.add(new List_Teacher("GV. Nguyễn Thị Thiết",158));
        arrayList.add(new List_Teacher("GV. Lê Thị Thiên Hương",159));
        arrayList.add(new List_Teacher("GV. Ngô Thị Sâm",160));
        arrayList.add(new List_Teacher("GV. Bùi Văn Long",161));
    }


    //Create Button Search
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem actionmenu = menu.findItem(R.id.secrch);
        SearchView searchView = (SearchView) actionmenu.getActionView();
        searchView.setQueryHint("Nhập tên giảng viên");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            //When change the editText
            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)){
                    adapter_nav_list_teacher.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter_nav_list_teacher.filter(newText);
                }
                return true;
            }
        });

        return true;
    }


    //Button Search on Click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.secrch)
            return  true;
        return super.onOptionsItemSelected(item);
    }


    //    Cài đặt chi tiết lịch phòng của từng ô
    public void Detail_Teacher(final List_Teacher teacher){

//        Tạo dialog
        final Dialog dialog = new Dialog(Nav_List_Teacher.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_teacher);
        dialog.setCanceledOnTouchOutside(true);


        TextView textViewMore = (TextView) dialog.findViewById(R.id.text_more);
        TextView textViewName = (TextView) dialog.findViewById(R.id.textViewname);
        ListView listViewSub = (ListView) dialog.findViewById(R.id.list_teacher);

        textViewName.setText(teacher.get__nameTeacher());

        ArrayList<String> detail= new ArrayList<>();

        Cursor dataDetail = Connection_CTMS.dataBase.GetData("SELECT * FROM tblTeacher_Sub WHERE IdGV='"+teacher.getId()+"' ");
        while(dataDetail.moveToNext()){
            detail.add(dataDetail.getString(2));
        }

        ArrayAdapter<String> adapterNavDetail = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,detail);
        listViewSub.setAdapter(adapterNavDetail);
        justifyListViewHeightBasedOnChildren(listViewSub);

//        textViewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Nav_List_Teacher.this, Web_view.class);
//                intent.putExtra("url","http://ctms.fithou.net.vn/HosoGiangvien.aspx?cid="+teacher.getId());
//
//                startActivity(intent);
//            }
//        });

        dialog.show();
    }

    //Cài đặt chiều cao cố định cho list để không có thanh cuộn
    public void justifyListViewHeightBasedOnChildren (ListView listView) {

        ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();

        if (adapter == null) {
            return;
        }
        ViewGroup vg = listView;
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, vg);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams par = listView.getLayoutParams();
        par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
        listView.setLayoutParams(par);
        listView.requestLayout();
    }
}
