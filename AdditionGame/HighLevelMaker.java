import org.json.JSONArray;
import org.json.JSONObject;

public class HighLevelMaker extends GameMaker{

    public JSONArray makeGame(){

        questions=new JSONArray();

        /*난이도 (상) 문제 만드는 부분*/
        for (int i = 0; i < quesNum; i++) {
            int random1 = (int) ((Math.random() * (100 - 10)) + 10); //두 자릿수 양수 랜덤 생성
            int random2 = (int) ((Math.random() * (100 - 10)) + 10); //두 자릿수 양수 랜덤 생성
            String random1_String = Integer.toString(random1);
            String random2_String = Integer.toString(random2);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("문제", random1_String + "+" + random2_String);
            jsonObject.put("정답", random1 + random2);

            questions.put(jsonObject);
        }

        return questions;
    }
}
