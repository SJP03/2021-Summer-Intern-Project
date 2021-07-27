
import org.json.JSONObject;

import java.sql.*;
import java.text.SimpleDateFormat;

public class DAO {

    private static Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private static DAO dao =new DAO();

    public static DAO getInstance(){
        conn=getConnection();
        return dao;
    }

    public static Connection getConnection() {
        try {
            if(conn==null){
                String jdbcDriver="jdbc:mysql://localhost/plusgame";
                String dbUser="root";
                String dbPass="@Seongjun12";

                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(jdbcDriver, dbUser,dbPass); //URL, 아이디, 패스워드를 담은 객체 생성
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close() throws SQLException {
        if(conn!=null){
            if(!conn.isClosed()){
                conn.close();
            }
        }
    }

    //로그인 소스 시작

    public int login(String userID, String userPassword) {
        String SQL = "SELECT user_pw FROM user WHERE user_id = ?";

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                if(rs.getString(1).equals(userPassword)) {
                    System.out.println();
                    return 1; // 로그인 성공
                } else
                    return 0; // 비밀번호 불일치
            }

            return -1; //해당 아이디가 없음

        }catch(Exception e) {
            e.printStackTrace();    // 예외처리
        }

        return -2; // 데이터베이스 오류
    }

    public int memberJoin(String name, String userID, String password){
        String SQL = "insert into user (user_id, user_pw, user_name) values (?,?,?)";

        try{
            pstmt=conn.prepareStatement(SQL);

            pstmt.setString(1, userID);
            pstmt.setString(2, password);
            pstmt.setString(3, name);

            if(pstmt.executeUpdate()==1){
                System.out.println("회원가입이 완료 되었습니다. \n로그인 해주세요. \n");
            }

            return 1; //회원가입 성공

        } catch (SQLException e) {
            e.printStackTrace();
            //return -1;
        }
        return -1; //회원가입 오류
    }

    public String getUserName(String userID) {

        String userName=null;
        String query="SELECT user_name FROM user WHERE user_id='"+userID+"'";
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(query);
            while(rs.next()){
                //한 행씩 반복 처리
                userName=rs.getString("user_name");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userName;
    }

    public void registerGameSetting(){
        String query="insert game_setting set user_id='"+GameManager.userID+"'";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {

        }
    }

    public ResultSet loadUserSetting(String userID){
        String query="select level, question_num from game_setting where user_id='"+userID+"'";
        try {
            stmt = conn.createStatement();
            rs=stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void updateLevel(String level){
        String query="update game_setting set level='"+level+"' where user_id='"+GameManager.userID+"'";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuesNum(int quesNum){
        String query="update game_setting set question_num="+quesNum+" where user_id='"+GameManager.userID+"'";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getLevel(){
        String level=null;
        String query="select level from game_setting where user_id='"+GameManager.userID+"'";
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(query);
            rs.next();
            level=rs.getString("level");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return level;
    }

    public void saveQuestion(int gameCode, int gameNum, String question, int answer){
        String query = "insert into question (game_code, game_num, question, answer) values (?, ?, ?, ?)";

        try{
            pstmt=conn.prepareStatement(query);

            pstmt.setInt(1, gameCode);
            pstmt.setInt(2, gameNum);
            pstmt.setString(3, question);
            pstmt.setInt(4, answer);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            //return -1;
        }
    }

    public ResultSet loadQuestion(int gameCode){
        //String question;
        String query = "select * from question where game_code="+gameCode;
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void saveUserAnswer(String userID, int gameCode, JSONObject answer){
        /*System.out.println("answer Object in saveUserAnswer()" + answer);
        String query="insert into game (user_id, user_answer) values('"+userID+"','"+answer+"')";
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        String query="insert into game (user_id, game_code, user_answer) values (?, ?, ?)";

        try{
            pstmt=conn.prepareStatement(query);

            pstmt.setString(1, userID);
            pstmt.setInt(2, gameCode);
            pstmt.setString(3, String.valueOf(answer));

            pstmt.executeUpdate();
            System.out.println("saveUserAnswer() complete");
        } catch (SQLException e) {
            //return -1;
        }
    }

    public ResultSet loadAnswer(int gameCode){
        String query="select answer from question where game_code=?";

        try{
            pstmt=conn.prepareStatement(query);

            pstmt.setInt(1, gameCode);

            rs=pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void saveGameData(String score, String level, String timeSpent, int quesNum, String date){
        System.out.println("saveGameData()");
        String query = "update game set score=?, level=?, play_time=?, question_num=?, start_time=? where game_code=?";
        try{
            pstmt=conn.prepareStatement(query);

            pstmt.setString(1, score);
            pstmt.setString(2, level);
            pstmt.setString(3, timeSpent);
            pstmt.setInt(4, quesNum);
            pstmt.setString(5, date);
            pstmt.setInt(6, GameMaker.gameCode);

            pstmt.executeUpdate();
            System.out.println("saveGameData() complete");
        } catch (SQLException e) {
            e.printStackTrace();
            //return -1;
        }
    }

    public int countGameCode(){
        int countGameCode=0;
        String query="select count(game_code) from game";
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            rs.next();
            countGameCode = rs.getInt("count(game_code)");

        } catch (SQLException e) {
            e.printStackTrace();
            //return -1;
        }

        return countGameCode;
    }

    public void createUserSetting(String userID){
        String query="insert into game_setting (user_id) values (?)";
        try{
            pstmt=conn.prepareStatement(query);
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ResultSet loadUserData(String userID){
        String query="select * from game where user_id='"+userID+"'";
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet loadSortedData(String userID){
        String query = "SELECT * FROM game ORDER BY score DESC;";
        try{
            stmt=conn.createStatement();
            rs=stmt.executeQuery(query);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
}

