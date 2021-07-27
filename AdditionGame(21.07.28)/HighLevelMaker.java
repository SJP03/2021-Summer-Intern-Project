import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HighLevelMaker extends GameMaker{
    //int gameCode;

    /*public HighLevelMaker(int gameCode){
        this.gameCode=gameCode;
    }*/

    public void makeGame() throws SQLException {

        //questions=new JSONArray();
        GameOption gameOption = GameOption.getInstance();
        //Connection conn=DBConnect.getConnection();

        /*난이도 (상) 문제 만드는 부분*/
        for (int i = 0; i < gameOption.quesNum; i++) {
            int random1 = (int) ((Math.random() * (100 - 10)) + 10); //두 자릿수 양수 랜덤 생성
            int random2 = (int) ((Math.random() * (100 - 10)) + 10); //두 자릿수 양수 랜덤 생성
            String random1_String = Integer.toString(random1);
            String random2_String = Integer.toString(random2);
            String question_String = random1_String + "+" + random2_String;
            int answer = random1 + random2;

            dao.saveQuestion(GameMaker.gameCode, i+1, question_String, answer );

            /*JSONObject jsonObject = new JSONObject();
            jsonObject.put("문제", random1_String + "+" + random2_String);
            jsonObject.put("정답", random1 + random2);

            questions.put(jsonObject);*/


        }
        //return gameCode;
    }
}
