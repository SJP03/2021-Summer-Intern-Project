import org.json.JSONArray;

import java.sql.SQLException;
import java.util.Scanner;

public class GameMaker{
    //protected JSONArray questions; //출제한 문제 정보 저장 (문제, 답 -> 10+20, 30)
    static int gameCode=1; //게임코드 발급!!!
    DAO dao=DAO.getInstance();

    public void generateGameCode(){
        //게임 코드 발급 -> 유저마다 다르게
        int countGameCode = dao.countUserGameCode(GameManager.userID);
        System.out.println(countGameCode);
        gameCode=countGameCode+1;

    }

    /*설정된 난이도에 따라 해당 객체의 makeGame() 호출*/
    public void makeGame(int gameCode) throws SQLException {
        GameOption gameOption=GameOption.getInstance();

        switch(dao.getLevel()){
            case "상":
                GameMaker highLevelMaker=new HighLevelMaker();
                highLevelMaker.makeGame(gameCode);
                break;
            case "중":
                GameMaker middleLevelMaker=new MiddleLevelMaker();
                middleLevelMaker.makeGame(gameCode);
                break;
            case "하":
                GameMaker lowLevelMaker=new LowLevelMaker();
                lowLevelMaker.makeGame(gameCode);
                break;
        }

        //return gameCode;

    }
}
