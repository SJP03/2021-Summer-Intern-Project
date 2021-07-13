import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Random;

public class MiddleLevelMaker extends GameMaker{
    public JSONArray makeGame(){
        questions=new JSONArray();
        Random random=new Random();

        /*난이도 (중) 문제 만드는 부분*/
        for (int i = 0; i < quesNum; i++) {
            int a1=random.nextInt(9);
            int a2=random.nextInt(8)+1;
            int a3=(a2*10)+a1;
            int t1=10-a1;
            int b1=random.nextInt(t1);
            int b2=random.nextInt(8)+1;
            int b3=(b2*10)+b1;

            String a3_String = Integer.toString(a3);
            String b3_String = Integer.toString(b3);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("문제", a3_String + "+" + b3_String);
            jsonObject.put("정답", a3 + b3);

            questions.put(jsonObject);
        }

        return questions;
    }

}
