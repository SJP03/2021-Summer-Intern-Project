import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class GamePlayer {
    private ArrayList<Integer> userAnswer=new ArrayList<>(); //사용자의 입력(답) 저장
    private JSONObject gameData; //게임 플레이 한 번(총 10문제)에 대한 정보
    private JSONArray questions; //GameMaker가 출제한 문제와 정답
    private Date date; //게임을 시작한 시간
    private long timeSpent; //게임을 진행한 시간
    int gameCode;

    GameOption gameOption=GameOption.getInstance();
    DAO dao=DAO.getInstance();

    public void play(int gameCode) throws SQLException {
        //this.questions=questions;

        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("게임을 시작합니다.");
        System.out.println("[난이도] "+gameOption.level+"  [문항수] "+gameOption.quesNum);
        System.out.println("--------------------------------------------");

        date=new Date();
        long start = System.currentTimeMillis(); //게임 시작시간 측정
        /*사용자가 문제에 대한 답을 입력하는 부분*/

        /*Scanner ansInput=new Scanner(System.in);
        userAnswer=new ArrayList<>();
        for(int i=0;i<questions.length();i++){
            JSONObject tempJSONObject=(JSONObject) questions.get(i);
            System.out.print("["+(i+1)+"] "+tempJSONObject.getString("문제")+"= ");
            userAnswer.add(ansInput.nextInt());
        }*/

        ResultSet question=dao.loadQuestion(gameCode);

        Scanner ansInput=new Scanner(System.in);
        JSONObject answer = new JSONObject();
        userAnswer=new ArrayList<>();

        int i=1;
        if(question.next()){
            do{
                System.out.print("["+i+"] "+question.getString("question")+"= ");
                //사용자 정답을 json 형식으로 변환
                int tempAns = ansInput.nextInt();
                answer.put(i+"번", tempAns);
                userAnswer.add(tempAns);
                i++;
            }while(question.next());
        }

        //game 테이블의 user_answer에 저장
        dao.saveUserAnswer(GameManager.userID, gameCode, answer);

        long end = System.currentTimeMillis(); //게임 완료시간 측정

        timeSpent=(long) ((end - start)/1000.0); //게임을 진행한 시간 계산
    }

    /*게임 결과를 출력하고 해당 게임 데이터를 저장하는 함수*/
    public void getResult() throws SQLException {
        int ansCount=0; //정답 개수

        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("결과를 채점합니다. (채점 결과는 O/X로 표시됩니다.)" );
        System.out.println("--------------------------------------------------");
        System.out.println("[채점 결과] ");

        /*사용자의 답을 채점하는 부분*/

        /*for(int i=0; i<questions.length(); i++) {
            if(userAnswer.get(i)!=((JSONObject)questions.get(i)).getInt("정답")){ //오답이면
                System.out.println((i+1)+") X -> 정답:"+((JSONObject)questions.get(i)).getInt("정답"));
            }else{
                System.out.println((i+1)+") O");
                ansCount++;
            }
        }*/

        //DB에서 정답 불러오기
        ResultSet answer=dao.loadAnswer(GameMaker.gameCode);

        int j=0;
        if(answer.next()){
            do{
                if(userAnswer.get(j)!=answer.getInt("answer")){
                    System.out.println((j+1)+") X -> 정답:"+answer.getInt("answer"));
                }else{
                    System.out.println((j+1)+") O");
                    ansCount++;
                }
                j++;
            }while(answer.next());
        }


        double score=(100.0/gameOption.quesNum)*ansCount; //100점 만점으로 점수 계산
        String score_String=String.format("%.2f",score); //점수를 소수점 2자리까지 출력하도록
        System.out.print("\n[점수] "+score_String+"점");
        System.out.println("  [소요시간] "+timeSpent+"초");


        /*게임 플레이 한 번에 대한 정보를 저장하는 부분*/

        /*gameData =new JSONObject();
        JSONArray quesInfo=new JSONArray();

        for(int i=0;i<questions.length();i++) {
            JSONObject temObject = (JSONObject) questions.get(i);
            JSONObject eachQuesInfo=new JSONObject();
            eachQuesInfo.put("문제", temObject.getString("문제"));
            eachQuesInfo.put("정답", temObject.getInt("정답"));
            eachQuesInfo.put("사용자 답", userAnswer.get(i));
            quesInfo.put(eachQuesInfo);
        }
        gameData.put("문제 정보", quesInfo);
        gameData.put("점수", score);
        gameData.put("시작 시간", date);
        gameData.put("소요 시간", timeSpent);
        gameData.put("문항수", gameOption.quesNum);
        gameData.put("난이도",gameOption.level);

        System.out.println();*/

        //return gameData;
        String date_String=date.toString();
        String timeSpent_String=Long.toString(timeSpent);

        dao.saveGameData(score_String, gameOption.level, timeSpent_String, gameOption.quesNum, date_String);
        GameMaker.gameCode++;
    }
}
