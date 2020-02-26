package com.dmt.tranbaphuc1999.ctms;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import es.dmoral.toasty.Toasty;

import static com.dmt.tranbaphuc1999.ctms.MainActivity.sharedPreferencesUser;


public class Connection_CTMS {

    SharedPreferences.Editor editor =  sharedPreferencesUser.edit();

    private HttpURLConnection conn;

    private final String USER_AGENT = "Mozilla/5.0";
    private String cookie = "";

    private String LoginPage = "http://ctms.fithou.net.vn/login.aspx";
    private String HomePage = "http://ctms.fithou.net.vn/";
    private String ScheduleClassPage = "http://ctms.fithou.net.vn/Lichhoc.aspx?sid=";
    private String ScorePage = "http://ctms.fithou.net.vn/KetquaHoctap.aspx?sid=";
    private String ScheduleRoomPage = "http://ctms.fithou.net.vn/index.aspx";
    private String ActiveCodePage = "http://ctms.fithou.net.vn/services/ActiveCode.aspx";
    private String BuyServicesPage = "http://ctms.fithou.net.vn/services/BuyServices.aspx";
    private String BillPage = "http://ctms.fithou.net.vn/HocphiDsHoadonSV.aspx?sid=";
    private String ChangePasswordPage = "http://ctms.fithou.net.vn/ChangePassword.aspx";
    private String ScheduleExamPage = "http://ctms.fithou.net.vn/Lichthi.aspx?sid=";
    private String EducationProgramPage = "http://ctms.fithou.net.vn/ChuongtrinhDaotaoSinhvien.aspx?sid=";
    private String News = "http://fithou.edu.vn";

    static String DataScheduleClassPage = "";
    static String DataScorePage = "";
    static String DataScheduleRoomPage = "";
    static String DataBuyServicesPage = "";
    static String DataBillPage = "";
    static String DataScheduleExamPage = "";
    static String DataEducationProgramPage = "";
    static String DataNews = "";

    static DataBase dataBase;


//    Khởi tạo DataBase
    public Connection_CTMS(Context ctx) {
        dataBase = new DataBase(ctx,"databasectms.sqlite",null,1);
    }


//    Hàm lấy HTML
    public synchronized String GetPageContent(String url) throws IOException, NullPointerException, NoSuchElementException, IllegalStateException {

        switch (url){
            case "LoginPage": url=LoginPage; break;       /*  Tạo một switch để lọc link theo chuỗi yêu cầu được truyền vào*/
            case "HomePage": url=HomePage; break;
            case "ScheduleClassPage": url=ScheduleClassPage; break;
            case "ScorePage": url=ScorePage; break;
            case "ScheduleRoomPage": url=ScheduleRoomPage; break;
            case "ActiveCodePage": url=ActiveCodePage; break;
            case "BuyServicesPage": url=BuyServicesPage; break;
            case "BillPage": url=BillPage; break;
            case "ChangePasswordPage": url=ChangePasswordPage; break;
            case "ScheduleExamPage": url=ScheduleExamPage; break;
            case "EducationProgramPage": url=EducationProgramPage; break;
            case "News": url=News; break;
        }
        URL obj = null;
        try {
            obj = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            return "error";
        }
        BufferedReader in;
        try {
            conn = (HttpURLConnection) obj.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);


        // Mặc định là GET
        conn.setRequestMethod("GET");

        conn.setUseCaches(false);

        // Hoạt động như một trình duyệt
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        conn.addRequestProperty("Cookie",cookie);
//        Log.d("coockie",cookie);
        conn.getRequestProperties();
        conn.setDoOutput(true);
        //  System.out.println("Huhahahahah :"+conn.getHeaderFields());
//    if (cookies != null) {
//        for (String cookie : this.cookies) {
//            conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//        }
//    }
//        int responseCode = conn.getResponseCode();
//    System.out.println("\nSending 'GET' request to URL : " + url);
//     System.out.println("headers fields are :"+conn.getHeaderFields());
//    System.out.println("Response Code : " + responseCode);

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        } catch (SocketTimeoutException e) {
            return "time up";
        }catch (IllegalStateException e) {
            return "error";
        }


        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine+"\n");
        }
        in.close();

        // Get the response cookies
//    setCookies(conn.getHeaderFields().get("Set-Cookie"));      //This is where I have tried to capture the session id from cookie but could it doesnot contain session id.
        return response.toString();
    }

//    Hàm gửi các yêu cầu POST
    private String sendPost(String url, String postParams) throws Exception {

        URL obj = new URL(url);
        conn = (HttpURLConnection) obj.openConnection();

        // Acts like a browser
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Host", "ctms.fithou.net.vn");
        conn.setRequestProperty("User-Agent", USER_AGENT);
        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//    for (String cookie : this.cookies) {
        conn.addRequestProperty("Cookie", cookie);
//    }
        conn.setRequestProperty("Connection", "keep-alive");
        conn.setRequestProperty("Referer", url);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));

        conn.setDoOutput(true);
        conn.setDoInput(true);

        // Send post request
        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(postParams);
        wr.flush();
        wr.close();

//        int responseCode = conn.getResponseCode();
//    System.out.println("\nSending 'POST' request to URL : " + url);
//    System.out.println("Post parameters : " + postParams);
//    System.out.println("Response Code : " + responseCode);
        //  System.out.println("HEADERS:"+conn.getRequestMethod());

        BufferedReader in =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        // System.out.println(response.toString());
        return response.toString();

    }


//    Hàm đăng nhập
    public String Login(String email, String pass) {

        try{
//          Lấy Cookie nè
            conn = (HttpURLConnection) new URL(HomePage).openConnection();

            for(int i=1;i<conn.getHeaderFields().size();i++) {
                if(conn.getHeaderFieldKey(i).compareTo("Set-Cookie")==0) {
                    String[] words=conn.getHeaderField(i).split(";");
                    cookie = words[0];
                    break;
                }
            }


//          Đăng nhập với Cookie và tài khoản
            String page;
            page = GetPageContent("LoginPage");


//            Kiểm tra xem có kết nối với Server được không
            if(page.equals("time up"))
                return page;

//            Gửi yêu cầu đăng nhập
            String postParams;
            postParams = ParamsLogin(page, email.trim(),encryptMD5(pass.trim()));
            String pagehome = sendPost(LoginPage, postParams);

//           Kiểm tra xem đăng nhập thành công hay chưa và các vấn đề liên quan
            if(pagehome.contains(email)&&pagehome.contains("Xin chào mừng")||pagehome.contains(email)&&pagehome.contains("Thay đổi mật khẩu")){
                String pagehomeTest;
                pagehomeTest = GetPageContent("ScheduleClassPage");

                if(pagehomeTest.contains("Bạn nhận được thông báo này do chưa đăng ký sử dụng dịch vụ"))
                    return "expired";
                else return "success";
            }
            else if(pagehome.contains("Tài khoản này đang đăng nhập ở một máy tính khác")){
                return "Logined";
            } else if(pagehome.contains("Sai Tên đăng nhập")){
                return "failure";
            }

        }catch (SocketTimeoutException e) {
            return "time up";
        }catch (ConnectException e){
            return "time up";
        }catch (Exception e) {
            return "time up";
        }

        return "error";
    }


//    Hàm đăng xuất
    public String Logout() {

//        Kiểm tra xem đăng xuất trước đó hay chưa
        String pagehome;
        try {
            pagehome = GetPageContent("HomePage");
        } catch (IOException e) {
            return "time up";
        }

        if(!(pagehome.contains("Xin chào mừng")))
            return "success";
            else {
                try {
                    String postParamsLogout = ParamsLogout(pagehome);
                    String pagehomeAfter = sendPost(HomePage, postParamsLogout);

    //            Kiểm tra xem đăng xuất thành công hay chưa
                    if (!(pagehomeAfter.contains("Xin chào mừng") || pagehome.contains("đến với hệ thống!")))
                        return "success";
                    else return "failure";
                } catch (IOException e) {
                    return "time up";
                } catch (Exception e) {
                    return "error";
                }
            }
    }

//    Lấy lịch thi
    public String Take_Exam_Schedule(){
        String pageExamScedule;
        try {
            pageExamScedule = GetPageContent("ScheduleExamPage");
        } catch (IOException e) {
            return "time up";
        }

        if(pageExamScedule.equals("time up"))
            return pageExamScedule;
        else {
            try {
                ReadExamSchedule(pageExamScedule,"Soon");
                if(!(sharedPreferencesUser.getBoolean("Fisrt",false))) {
                    String postParamsExamSchedule = ParamsExamSchedule(pageExamScedule);
                    String pageDoneXeamSchedule = sendPost(ScheduleExamPage, postParamsExamSchedule);
                    ReadExamSchedule(pageDoneXeamSchedule,"Done");
                }
                return "success";
            } catch (IOException e) {
                return "time up";
            } catch (Exception e) {
                return "error";
            }
        }
    }


    //    Lấy thông tin hóa đơn
    public String Take_Bill(){
        String pageBill;
        try {
            pageBill = GetPageContent("BillPage");
        } catch (IOException e) {
            return "time up";
        }

        if(pageBill.equals("time up"))
            return pageBill;
        else {
            try {
                String ParamsBill = ParamsBill(pageBill);
                String pageListlBill = sendPost(BillPage, ParamsBill);
                ReadBill(pageListlBill);
                return "success";
            } catch (IOException e) {
                return "time up";
            } catch (Exception e) {
                return "error";
            }
        }
    }

    //    Lấy giáo viên
    public String Take_teacher_sub(int id){
        String pageBill;
        try {
                pageBill = GetPageContent("http://ctms.fithou.net.vn/HosoGiangvien.aspx?cid="+id);
            ReadTeacher_Sub(pageBill,id);

        } catch (IOException e) {
            return "time up";
        }
        return "success";
    }

//    //    Đọc bản tin
//    public String Read_Teacher(String html) throws NullPointerException {
//
//        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblBantin(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR, ChiTiet VARCHAR, Link CHAR)");
//
//        dataBase.QuerySQL("DELETE FROM tblBantin");
//
//        Document document = Jsoup.parse(html,"UTF-8");
//        if(document!=null){
//            Elements box = document.select("div[class=box]");
//            Elements div_article = box.select("div[class=article]");
//            Elements news = div_article.select("a");
//
//            String TieuDe, ChiTiet, Link;
//            for (int i=0;i<18;i++){
//                if(i%2!=0){
//                    TieuDe= news.get(i).text();
//                    ChiTiet= news.get(i).attr("title");
//                    Link= "http://fithou.edu.vn"+news.get(i).attr("href");
//
//                    dataBase.QuerySQL("INSERT INTO tblBantin VALUES(null, '"+TieuDe+"','"+ChiTiet+"','"+Link+"')");
//                }
//            }
//
////          Thêm vào SQLite
//            return "success";
//        }
//
//        return "error";
//    }


    //    Lấy lịch học tuần sau
    public String Take_Schedule_Class_Next(String Week){
            try {
                String ParamsSchedule = ParamsTuanTuChon(DataScheduleClassPage,Week);
                String pageNext = sendPost(ScheduleClassPage, ParamsSchedule);
                ReadScheduleClass(pageNext,Week);
                return "success";
            } catch (IOException e) {
                return "time up";
            } catch (Exception e) {
                return "error";
            }
    }


    //    Lấy lịch phòng tuần sau
    public String Take_Schedule_Room_Next(String Week){
        try {
            String ParamsSchedule = ParamsTuanTuChon(DataScheduleRoomPage,Week);
            String pageNext = sendPost(ScheduleRoomPage, ParamsSchedule);
            ReadScheduleRoom(pageNext,Week);
            return "success";
        } catch (IOException e) {
            return "time up";
        } catch (Exception e) {
            return "error";
        }
    }



//    Đọc html lịch học trong tuần
    public String ReadScheduleClass(String html,String Week) throws NullPointerException {

//        Tạo bảng tblLichHoc nếu chưa có
        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblLichHoc(Id INTEGER PRIMARY KEY AUTOINCREMENT, Tuan CHAR(10), Thu CHAR(10), Ngay CHAR(10), GioBD CHAR(10), GioKT CHAR(10), Phong CHAR(3), Mon VARCHAR(50), GV VARCHAR(30), TT CHAR(10))");

        if(Week.equals(" "))
        dataBase.QuerySQL("DELETE FROM tblLichHoc");
        else dataBase.QuerySQL("DELETE FROM tblLichHoc WHERE Tuan = '"+Week+"'");


        Document document = Jsoup.parse(html,"UTF-8");

        if(document!=null){
            Element week = document.getElementById("LeftCol_Lichhoc1_txtNgaydautuan");
            String timeWeek = week.attr("value");
            Elements divs = document.select("div[style=margin-bottom:10px]");
            for(Element div : divs){
                String thu = div.getElementsByTag("b").text();
                String[] catthu=thu.split(" ");
                Element table = div.selectFirst("table");
                Element body = table.select("tbody").first();
                Elements rows = body.select("tr");

                for(int i=1;i<rows.size();i++){
                    Elements cells = rows.get(i).select("td");
                    String gio = cells.get(1).getElementsByTag("td").text();
                    String[] catgio = gio.split(" ");
                    String phong = cells.get(2).getElementsByTag("td").text();
                    String monhoc = cells.get(3).getElementsByTag("td").text();
                    String giangvien = cells.get(4).getElementsByTag("td").text();
                    if(giangvien.equals("")) giangvien = "Giảng viên coi thi";
                    String trangthai = cells.get(6).getElementsByTag("td").text();

//                    Thêm dữ liệu vào dataBase
                    dataBase.QuerySQL("INSERT INTO tblLichHoc VALUES(null, '"+timeWeek+"','"+catthu[0]+" "+catthu[1]+"','"+catthu[2]+"','"+catgio[0]+"','"+catgio[2]+"','"+phong+"','"+monhoc+"','"+giangvien+"','"+trangthai+"')");
                }
            }
            return "success";
        }
        return "error";
    }


//    Đọc html lịch thời khóa biểu phòng trong tuần
    public String ReadScheduleRoom ( String html,String Week ) throws NullPointerException {
//        Tạo bảng tblLichPhong,tblChiTietLichPhong nếu chưa có
        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblLichPhong(IdPhong INTEGER PRIMARY KEY AUTOINCREMENT, Tuan CHAR(10), Ngay CHAR(10), Phong CHAR(5), TT CHAR(15))");
        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblChiTietLichPhong(IdChiTiet INTEGER PRIMARY KEY AUTOINCREMENT, Tuan CHAR(10), Ngay CHAR(10), Phong CHAR(5), Gio CHAR(14), Mon CHAR(10), GV VARCHAR(30), TT CHAR(10))");

        if(Week.equals(" ")){
            dataBase.QuerySQL("DELETE FROM tblLichPhong");
            dataBase.QuerySQL("DELETE FROM tblChiTietLichPhong");
        }
        else{
            dataBase.QuerySQL("DELETE FROM tblChiTietLichPhong WHERE Tuan = '"+Week+"'");
            dataBase.QuerySQL("DELETE FROM tblLichPhong WHERE Tuan = '"+Week+"'");
        }

        Document document = Jsoup.parse(html,"UTF-8");
        if(document!=null){

            Element week = document.getElementById("LeftCol_ThoikhoabieuWeekView1_txtNgaydautuan");
            String timeWeek = week.attr("value");

            Element tableParent = document.selectFirst("table[class=CenterElement][id=LeftCol_ThoikhoabieuWeekView1_grvThoikhoabieu]");
            Element tbodyParent = tableParent.selectFirst("tbody");
            Elements trParent = tbodyParent.select("tr[valign=top]");
            Element trParentDay = tbodyParent.selectFirst("tr");
            Elements thDay = trParentDay.select("th");
            for(int u=0;u<trParent.size()-1;u++) {

                Element tdPhong = trParent.get(u).selectFirst("td");
                String Room = tdPhong.getElementsByTag("td").text(); /*lấy số phòng*/

                for(int i=1;i<thDay.size();i++) {
                    String S="K",C="K";
                    String day = thDay.get(i).getElementsByTag("th").text();
                    String[] a= day.split(" ");

                    Elements tdParent = trParent.get(u).select("td[align=left][valign=top]");

                    Elements tableChild = tdParent.get(i-1).select("table");

                    String Time, Sub, Teacher, Status;
                    Elements tr;
                    if(tableChild.size()!=0){
                        for(Element table : tableChild) {
                             tr = table.select("tr");
                             Time = tr.get(0).getElementsByTag("td").text();
                             Sub = tr.get(1).getElementsByTag("td").text();
                             Teacher = tr.get(2).getElementsByTag("td").text();
                             Status = tr.get(4).getElementsByTag("span").text();
//                        Thêm dữ liệu vào dataBase
                            dataBase.QuerySQL("INSERT INTO tblChiTietLichPhong VALUES(null,'"+timeWeek+"', '"+a[2].substring(0,5)+"','"+Room+"','"+Time+"','"+Sub+"','"+Teacher+"','"+Status+"')");

//                            Kiểm tra trạng thái học tập của từng buổi rồi dùng biến S và C này đưa ra trạng thái của ngày đó
                            if(Time.equals("6h45 -> 9h00")||Time.equals("9h10 -> 11h25")){
                                switch (Status){
                                    case "Học": S="H"; break;
                                    case "Thi": S="T"; break;
                                    case "Nghỉ": S="N"; break;
                                }
                            } else if(Time.equals("12h45 -> 15h00")||Time.equals("15h10 -> 17h25")||Time.equals("17h30 -> 20h10")){
                                    switch (Status){
                                        case "Học": C="H"; break;
                                        case "Thi": C="T"; break;
                                        case "Nghỉ": C="N"; break;
                                    }

                            }
                        }
                        dataBase.QuerySQL("INSERT INTO tblLichPhong VALUES(null,'"+timeWeek+"', '"+a[2].substring(0,5)+"','"+Room+"','"+S+C+"')");
                    } else {
                        dataBase.QuerySQL("INSERT INTO tblLichPhong VALUES(null,'"+timeWeek+"', '"+a[2].substring(0,5)+"','"+Room+"','KK')");
                    }

                }
            }
        }
        return "error";
    }


//    Đọc html điểm
    public String ReadScore(String html,String email) throws NullPointerException {

//        Tạo bảng tblDiem nếu chưa có
        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblDiem(Id INTEGER PRIMARY KEY AUTOINCREMENT, Mon VARCHAR(50), GV VARCHAR(30), TC INTEGER, DCC FLOAT, DKT FLOAT, DT FLOAT)");
        dataBase.QuerySQL("DELETE FROM tblDiem");


        Document document = Jsoup.parse(html,"UTF-8");
        if(document!=null){


            if(!(sharedPreferencesUser.contains("Fisrt")&& sharedPreferencesUser.getString("EmailOld","").equals(email))) {
                editor.remove("EmailOld");        /*  Email cũ */
                editor.remove("NameUser");         /* Tên người dùng*/
                editor.remove("CodeUser");          /*Mã sinh viên*/
                editor.remove("ClassUser");         /*Lớp hành chính*/
                editor.remove("CourseUser");           /* Khóa học*/
                editor.remove("TCUser");          /*  Tổng tín chỉ*/
                editor.remove("TBCUser");         /*  Trung bình tích lũy*/
                editor.remove("HeUser");             /*   Hệ học :)))))))))))*/
                editor.remove("FacultyUser");     /*  Khoa    */
                editor.remove("BirthDayUser");    /*  Ngày sinh*/
                editor.remove("BranchUser");           /* Ngành học*/

                editor.commit();

                editor.putString("EmailOld",email);
                Element tableInfor = document.selectFirst("table[align=center][class=ThongtinSV]");
                Elements rowsInfor = tableInfor.select("tr");
                Elements cell1 = rowsInfor.get(0).select("td");
                editor.putString("NameUser",cell1.get(1).text());
                editor.putString("BirthDayUser",cell1.get(3).text());
                Elements cell2 = rowsInfor.get(1).select("td");
                editor.putString("HeUser",cell2.get(1).text());
                editor.putString("CodeUser",cell2.get(3).text());
                Elements cell3 = rowsInfor.get(2).select("td");
                editor.putString("FacultyUser",cell3.get(1).text());
                editor.putString("BranchUser",cell3.get(3).text());
                Elements cell4 = rowsInfor.get(3).select("td");
                editor.putString("CourseUser",cell4.get(1).text());
                editor.putString("ClassUser",cell4.get(3).text());

                editor.commit();
            }


            Element table = document.selectFirst("table[border=1][cellpadding=5][cellspacing=0][class=RowEffect CenterElement]");
            Element body = table.select("tbody").first();
            Elements rows = body.select("tr");

            String mon, gv;
            Elements cells;
            for(Element row : rows){
                 cells = row.select("td");
                 mon = cells.get(0).getElementsByTag("td").text();
                int sotin = Integer.parseInt(cells.get(1).getElementsByTag("td").text());
//                String lop = cells.get(2).getElementsByTag("td").text();
                 gv = cells.get(3).getElementsByTag("td").text();
                if(!(cells.get(4).getElementsByTag("td").text().equals(""))){
                    float chuyencan = Float.parseFloat(cells.get(4).getElementsByTag("td").text());
                    float kiemtra = Float.parseFloat(cells.get(5).getElementsByTag("td").text());
                    float thi=0;
                    if(!(cells.get(6).getElementsByTag("td").text().contains("?"))){
                        thi = Float.parseFloat(cells.get(6).getElementsByTag("td").text());
                    }
//                Thêm vào SQLite
                    dataBase.QuerySQL("INSERT INTO tblDiem VALUES(null, '"+mon+"','"+gv+"','"+sotin+"','"+chuyencan+"','"+kiemtra+"','"+thi+"')");
                }

            }
            return "success";
        }
        return "error";
    }


//    Đọc hóa đơn
    public String ReadBill(String html) throws NullPointerException {
        Document document = Jsoup.parse(html, "UTF-8");
//        Tạo bảng tblHoaDon vaf tblChiTietHoaDon nếu chưa có
        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblHoaDon(Id INTEGER PRIMARY KEY AUTOINCREMENT, NgayLap VARCHAR, MaHD CHAR(10), GV VARCHAR(30), SoTin CHAR(2), TT CHAR, GT CHAR, DaNop CHAR, CanNop CHAR)");

        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblChiTietHoaDon(Id INTEGER PRIMARY KEY AUTOINCREMENT, MaHD CHAR(10), Mon VARCHAR(50), SoTin CHAR(2), DonGia CHAR, ThanhTien CHAR)");
        if(sharedPreferencesUser.getBoolean("Fisrt",false)) {
//        Bóc tách danh sách hóa đơn

            String Ngay, MaHD, GV, Tinchi, Link;
            Elements tds;
            if (document != null) {
                Element table = document.selectFirst("table[id=LeftCol_DsHoadonHocphi1_grvHoadon]");
                Elements trs = table.select("tr");
                     tds = trs.get(trs.size()-1).select("td");
                     Ngay = tds.get(6).text();
                     MaHD = tds.get(4).text();
                     GV = tds.get(5).text();
                      Tinchi = tds.get(7).text();
                     Link = "http://ctms.fithou.net.vn/HocphiChitietHoadonSV.aspx?bid=" + MaHD;

                    dataBase.QuerySQL("DELETE FROM tblHoaDon WHERE MaHD = '"+MaHD+"'");             /*Xóa hóa đơn gần nhất*/

                    ReadDetailBill(Link, MaHD, Ngay, GV, Tinchi);
                return "success";
            }
        } else {
            dataBase.QuerySQL("DELETE FROM tblHoaDon");              /*Xóa dữ liệu hóa đơn cũ*/
            dataBase.QuerySQL("DELETE FROM tblChiTietHoaDon");  /*Xóa dữ liệu chi tiết hóa đơn cũ*/
            //        Bóc tách danh sách hóa đơn
            if (document != null) {
                Element table = document.selectFirst("table[id=LeftCol_DsHoadonHocphi1_grvHoadon]");
                Elements trs = table.select("tr");

                Elements tds;
                String Ngay, MaHD, GV, Tinchi, Link;
                for (int i = 1; i < trs.size(); i++) {
                     tds = trs.get(i).select("td");
                     Ngay = tds.get(6).text();
                     MaHD = tds.get(4).text();
                     GV = tds.get(5).text();
                     Tinchi = tds.get(7).text();
                     Link = "http://ctms.fithou.net.vn/HocphiChitietHoadonSV.aspx?bid=" + MaHD;
                    ReadDetailBill(Link, MaHD, Ngay, GV, Tinchi);
                }
                return "success";
            }
        }

        return "error";
    }


//    Đọc chi tiết hóa đơn
    public String ReadDetailBill(String Link, String MaHD, String Ngay, String GV, String Tinchi ) throws NullPointerException {

            String html = null;
            try {
                html = GetPageContent(Link);
            } catch (IOException e) {
                e.printStackTrace();
            }

            dataBase.QuerySQL("DELETE FROM tblChiTietHoaDon WHERE MaHD = '" + MaHD + "'");
//        Bóc tách chi tiết hóa đơn
            Document document = Jsoup.parse(html, "UTF-8");
            if (document != null) {
                Element table = document.selectFirst("table[align=center][border=1][cellpadding=3][cellspacing=0]");
                Elements trs = table.select("tr");

                String Mon, TC, DonGia, ThanhTien;
                Elements tds;
                for (int i = 1; i < trs.size() - 4; i++) {
                    tds = trs.get(i).select("td");
                     Mon = tds.get(2).text();
                     TC = tds.get(3).text();
                     DonGia = tds.get(4).text();
                     ThanhTien = tds.get(5).text();

//                Thêm vào SQLite
                    dataBase.QuerySQL("INSERT INTO tblChiTietHoaDon VALUES(null, '" + MaHD + "','" + Mon + "','" + TC + "','" + DonGia + "','" + ThanhTien + "')");
                }

//            Lấy tổng tiền, giảm trừ, đã nộp và cần nộp
                String TT = trs.get(trs.size() - 4).select("td").get(1).text();
                String GT = trs.get(trs.size() - 3).select("td").get(1).text();
                String DaTT = trs.get(trs.size() - 2).select("td").get(1).text();
                String CanNop = trs.get(trs.size() - 1).select("td").get(1).text();

//            Thêm vào SQLite
                dataBase.QuerySQL("INSERT INTO tblHoaDon VALUES(null," + Ngay + "','" + MaHD + "','" + GV + "','" + Tinchi + "','" + TT + "','" + GT + "','" + DaTT + "','" + CanNop + "')");
                return "success";
            }
        return "error";
    }




//    Đọc lịch thi
    public String ReadExamSchedule(String html, String STT) throws NullPointerException {


        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblLichThi(Id INTEGER PRIMARY KEY AUTOINCREMENT, STT VARCHAR, Ngay CHAR(10), Gio CHAR(5), Phong CHAR(3), Mon VARCHAR(50), MaDS CHAR(5))");

        dataBase.QuerySQL("DELETE FROM tblLichThi WHERE STT = '"+STT+"'");

        Document document = Jsoup.parse(html,"UTF-8");
        if(document!=null){
            Element table = document.selectFirst("table[border=1][align=center][class=RowEffect]");
            Elements trs = table.select("tr");

            String Ngay, Gio, Phong, Mon, MaDS;
            Elements tds;
            for(int i=1;i<trs.size();i++){
                tds = trs.get(i).select("td");
                Ngay = tds.get(1).text().split(" ")[1];
                Gio = tds.get(1).text().split(" ")[0];
                Phong = tds.get(2).text();
                Mon = tds.get(3).text();
                MaDS = tds.get(4).text();
                dataBase.QuerySQL("INSERT INTO tblLichThi VALUES(null, '"+STT+"','"+Ngay+"','"+Gio+"','"+Phong+"','"+Mon+"','"+MaDS+"')");
            }
//                Thêm vào SQLite
            return "success";
        }

        return "error";
    }



    //    Đọc bản tin
    public String Read_News(String html) throws NullPointerException {

        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblBantin(Id INTEGER PRIMARY KEY AUTOINCREMENT, TieuDe VARCHAR, ChiTiet VARCHAR, Link CHAR)");

        dataBase.QuerySQL("DELETE FROM tblBantin");

        Document document = Jsoup.parse(html,"UTF-8");
        if(document!=null){
            Elements box = document.select("div[class=box]");
            Elements div_article = box.select("div[class=article]");
            Elements news = div_article.select("a");

            String TieuDe, ChiTiet, Link;
            for (int i=0;i<18;i++){
                if(i%2!=0){
                    TieuDe= news.get(i).text();
                    ChiTiet= news.get(i).attr("title");
                    Link= "http://fithou.edu.vn"+news.get(i).attr("href");

                    dataBase.QuerySQL("INSERT INTO tblBantin VALUES(null, '"+TieuDe+"','"+ChiTiet+"','"+Link+"')");
                }
            }

//          Thêm vào SQLite
            return "success";
        }

        return "error";
    }



//    Params đăng nhập
    public String ParamsLogin(String html, String username, String password)
            throws UnsupportedEncodingException {


        Document doc = Jsoup.parse(html);

        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("ctl00$LeftCol$UserLogin1$txtUsername"))
                value = username;
            if (key.equals("ctl00$LeftCol$UserLogin1$txtPassword"))
                value = password;
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }

        Log.d("sadasd", "ParamsLogin: "+result);
        return result.toString();
    }


//    Params Đăng kí dịch vụ CTMS
    public String ParamsRegisterService(String html)
            throws UnsupportedEncodingException {




        Document doc = Jsoup.parse(html);

        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

//	        if (key.equals("__VIEWSTATEGENERATOR"))
//	            value = value+"&=1,1,1,1";
//	        if (key.equals("__CALLBACKID"))
//	            value = "ctl00$LeftCol$MuaDichVu1";
//	        if (key.equals("__CALLBACKPARAM"))
//	            value = "BuyItem:1:1";
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return (result+"&__CALLBACKID=ctl00$LeftCol$MuaDichVu1&__CALLBACKPARAM=BuyItem:1:1");
    }



//    Params Đăng xuất
    public String ParamsLogout(String html)
            throws UnsupportedEncodingException {

        Document doc = Jsoup.parse(html);

        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return (result+"&__CALLBACKID=ctl00$QuanlyMenu1&__CALLBACKPARAM=logout");
    }


//    Lấy Bill
    public String ParamsBill(String html)
            throws UnsupportedEncodingException {

        Document doc = Jsoup.parse(html);
        Elements inputElements = doc.getElementsByTag("input");
//        String fromDate = sharedPreferencesUser.getString("BirthDayUser","");
        List<String> paramList = new ArrayList<String>();

        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("ctl00$LeftCol$DsHoadonHocphi1$txtNgaylap1"))
                value = "01/01/2000";

            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        // build parameters list
        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }


//    Lấy lịch thi
    public String ParamsExamSchedule(String html)
            throws UnsupportedEncodingException {

        Document doc = Jsoup.parse(html);
        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (!(value.equals("rbtnTatca")||value.equals("rbtnChuathi")))
                paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }


//    Lấy lịch phòng và lịch học trong tuần theo yêu cầu
    public String ParamsTuanTuChon(String html, String date)
            throws UnsupportedEncodingException {

        Document doc = Jsoup.parse(html);
        Elements inputElements = doc.getElementsByTag("input");
        List<String> paramList = new ArrayList<String>();
        for (Element inputElement : inputElements) {
            String key = inputElement.attr("name");
            String value = inputElement.attr("value");

            if (key.equals("ctl00$LeftCol$ThoikhoabieuWeekView1$txtNgaydautuan")||key.equals("ctl00$LeftCol$Lichhoc1$txtNgaydautuan"))
                value = date;
            paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
        }

        StringBuilder result = new StringBuilder();
        for (String param : paramList) {
            if (result.length() == 0) {
                result.append(param);
            } else {
                result.append("&" + param);
            }
        }
        return result.toString();
    }


//    Hàm mã hóa mật khẩu sang MD5
    public String encryptMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    //    Đọc html môn giáo viên dạy
    public void ReadTeacher_Sub(String html, int id) throws NullPointerException {
        dataBase.QuerySQL("CREATE TABLE IF NOT EXISTS tblTeacher_Sub(Id INTEGER PRIMARY KEY AUTOINCREMENT, IdGV INTEGER, Mon VARCHAR)");

        Document document = Jsoup.parse(html,"UTF-8");
        if(document!=null){
            Element doc = document.selectFirst("div[id=dvChuyenmon]");
            Elements Subs = doc.select("li");

            for(int i=0;i<Subs.size();i++){
                String Sub = Subs.get(i).text();
                dataBase.QuerySQL("INSERT INTO tblTeacher_Sub VALUES(null, '"+id+"','"+Sub+"')");
            }

        }
    }





//    Chỗ dưới chưa dùng







    //    Đọc html danh sách môn học
    public void ReadSubjects(String html,int i) throws NullPointerException {
//	  System.out.println("bat dau tim");
//	  for(int i=1;i<200;i++) {
        Document document = Jsoup.parse(html,"UTF-8");
        if(document!=null){
            Element mon = document.selectFirst("div[id=dvDecuongChitietChuongtrinhdaotao]");
            if(mon!=null) {
//        	  String mona = mon.text();
                String smon = mon.getElementsByTag("h3").first().text();
                System.out.println(i+" = "+smon);
            }
            else System.out.println(i+" = Không có môn nào");
//          }
        }
    }



//    Đọc html danh sách giáo viên
    public void ReadTeacher(String html,int i) throws NullPointerException {
//	  for(int i=1;i<200;i++) {
        Document document = Jsoup.parse(html,"UTF-8");
        if(document!=null){
            Element gv = document.selectFirst("div[id=topbackground][class=noprint]");
            if(gv!=null) {
//        	  String giaovien = gv.text();
                String giaovien = gv.getElementsByTag("h2").text();
//                Log.d("thong", i+"  GV."+giaovien.substring(11));
                Log.d("thong", "arrayList.add(new List_Teacher(\"GV. "+giaovien.substring(11)+"\","+i+"));");
            }
            else Log.d("thong", i+"  Không có giáo viên");
        }

//	  }
    }
}