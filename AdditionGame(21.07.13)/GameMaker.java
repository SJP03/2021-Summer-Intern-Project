import org.json.JSONArray;
import java.util.Scanner;

public class GameMaker extends GameOption{
    protected JSONArray questions; //출제한 문제 정보 저장 (문제, 답 -> 10+20, 30)

    /*설정된 난이도에 따라 해당 객체의 makeGame() 호출*/
    public JSONArray makeGame() {

        switch(level){
            case "상":
                GameMaker highLevelMaker=new HighLevelMaker();
                this.questions=highLevelMaker.makeGame();
                break;
            case "중":
                GameMaker middleLevelMaker=new MiddleLevelMaker();
                this.questions=middleLevelMaker.makeGame();
                break;
            case "하":
                GameMaker lowLevelMaker=new LowLevelMaker();
                this.questions=lowLevelMaker.makeGame();
                break;
        }

        return questions;

    }
}
