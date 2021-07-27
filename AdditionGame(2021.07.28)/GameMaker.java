import org.json.JSONArray;

import java.sql.SQLException;
import java.util.Scanner;

public class GameMaker{
    //protected JSONArray questions; //출제한 문제 정보 저장 (문제, 답 -> 10+20, 30)
    static int gameCode=1; //게임코드 발급!!!
    DAO dao=DAO.getInstance();
    GameOption gameOption=GameOption.getInstance();

    public void generateGameCode(){
        //게임 코드 발급
        int countGameCode = dao.countGameCode();
        System.out.println("생성된 GameCode 개수 : " + countGameCode);
        gameCode=countGameCode+1;

    }

    /*설정된 난이도에 따라 해당 객체의 makeGame() 호출*/
    public void makeGame() throws SQLException {

        GameOption gameOption=GameOption.getInstance();
        generateGameCode();
        gameOption.loadUserSetting(GameManager.userID);

        System.out.println("현재 게임 코드 : " + gameCode);
        System.out.println("불러온 유저 문항수 :" + gameOption.quesNum);
        System.out.println("불러온 유저 난이도 :" + gameOption.level);

        switch(gameOption.level){
            case "상":
                GameMaker highLevelMaker=new HighLevelMaker();
                highLevelMaker.makeGame();
                break;
            case "중":
                GameMaker middleLevelMaker=new MiddleLevelMaker();
                middleLevelMaker.makeGame();
                break;
            case "하":
                GameMaker lowLevelMaker=new LowLevelMaker();
                lowLevelMaker.makeGame();
                break;
        }

        //return gameCode;

    }
}
