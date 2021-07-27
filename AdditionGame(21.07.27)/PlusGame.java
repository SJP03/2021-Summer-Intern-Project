//import java.util.ArrayList;
//import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;


public class PlusGame {
    public static void main(String[] args) throws IOException {
        GameManager plusGame = new GameManager();
        plusGame.printLoginUI();
    }
}

class GameManager {  // 게임 전반을 운영하는 클래스

    private GameMaker gameMaker = new GameMaker();       // 문제를 출제하는 클래스
    private GamePlayer gamePlayer = new GamePlayer();    // 문제를 풀고 채점하는 클래스
    private GameRecord gameRecord = new GameRecord();    // 결과를 저장하고 불러오는 클래스
    private Scanner screenInput = new Scanner(System.in);
    GameOption gameOption=GameOption.getInstance();
    DAO dao = DAO.getInstance();

    private static final int COMMAND_STARTGAME = 1;
    private static final int COMMAND_VIEWRESULT = 2;
    private static final int COMMAND_SETOPTION=3;
    private static final int COMMAND_EXIT = 4;

    public static String userID;

    public void play() {
        String userName= dao.getUserName(userID);
        printMessage(userName+"님, 덧셈게임에 오신 것을 환영합니다");
        while(true){
            int playCount=gameRecord.loadRecord();  // 파일에서 저장된 점수 데이터를 읽어 옴
            printMainUI(playCount);

            try {
                int command = screenInput.nextInt();

                if(command == COMMAND_STARTGAME) {
                    //gameMaker.makeGame(gameMaker.gameCode);
                    gamePlayer.play(gameMaker.gameCode);

                    gamePlayer.getResult();
                    //gameRecord.saveResult(result);

                    printMessage("게임을 완료하였습니다.\n");

                }else if(command == COMMAND_VIEWRESULT) {
                    String resultString = gameRecord.getRecord();
                    printMessage(resultString);
                    System.out.println("[Enter]를 치세요.");
                    screenInput.nextLine();
                    screenInput.nextLine();

                }else if(command==COMMAND_SETOPTION){
                    gameOption.setOption();

                }else if(command == COMMAND_EXIT){
                    printMessage("게임을 종료합니다.");
                    gameRecord.saveRecord(); //파일에 저장하기
                    break;
                }

            } catch (Exception e) {
                printMessage("Error - 입력값 에러\n");
                //e.printStackTrace();
                screenInput.nextLine();   //  예외가 발생하면 버퍼를 비움   screenInput = new Scanner(System.in);
            }
        }
    }

    public void printLoginUI() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {

            System.out.println("덧셈게임에 오신 것을 환영합니다");
            System.out.println("==========================================================");
            System.out.print("1. 로그인   ");
            System.out.println("2. 회원가입   ");
            System.out.println("==========================================================");
            System.out.print("Command> ");

            try {
                int command = screenInput.nextInt();
                if (command == 1) {                                             //로그인 화면
                    System.out.print("아이디: ");
                    userID = br.readLine();

                    System.out.print("비밀번호: ");
                    String password = br.readLine();

                    /*System.out.println("1. 로그인 성공 -> 메인 화면으로   2. 비밀번호 불일치   3. 해당 계정 존재하지 않음");
                    int num=screenInput.nextInt();
                    if(num==1){
                        play();
                        break;
                    }else if(num==2){
                        System.out.println("비밀번호가 일치하지 않습니다. 다시 로그인해주세요. \n");
                    }else if(num==3){
                        System.out.println("해당 계정이 존재하지 않습니다.\n");
                    }*/

                    if (dao.login(userID, password) == 1) {
                        //로그인 성공 -> 메인 화면으로
                        dao.registerGameSetting();
                        play();
                        break;
                    } else if (dao.login(userID, password) == 0) {
                        //비밀번호 불일치
                        System.out.println("비밀번호가 일치하지 않습니다. 다시 로그인해주세요. \n");
                    } else if (dao.login(userID, password) == -1) {
                        //아이디가 db에 없음
                        System.out.println("해당 계정이 존재하지 않습니다.\n");
                    }

                } else if (command == 2) {                                       //회원가입 화면
                    System.out.println("회원가입을 위한 정보를 입력해주세요.");
                    System.out.print("이름: ");
                    String name = br.readLine();
                    System.out.print("아이디: ");
                    String userID = br.readLine();
                    System.out.print("비밀번호: ");
                    String password = br.readLine();
                    while (true) {
                        System.out.print("비밀번호 확인: ");
                        String password2 = br.readLine();
                        if (password2.equals(password)) {
                            break;
                        }
                    }
                    System.out.println();
                    if (dao.memberJoin(name, userID, password) == 1) {
                        //회원가입 성공 -> 로그인 화면으로
                    } else if ((dao.memberJoin(name, userID, password) == -1)) {
                        //회원가입 오류
                        System.out.println("회원가입 오류 - 다시 회원가입 해주세요.");
                    }
                }
            } catch (Exception e) {
                printMessage("Error - 입력값 에러\n");
                //e.printStackTrace();
                screenInput.nextLine();   //  예외가 발생하면 버퍼를 비움   screenInput = new Scanner(System.in);
            }
        }
    }

    private void printMainUI(int playCount){
        // 메인 UI 출력
        System.out.println("==========================================================");
        System.out.println("1. 게임시작                       [누적 플레이 횟수] "+playCount+"회");
        System.out.println("2. 점수보기                       [현재 난이도] "+gameOption.level);
        System.out.println("3. 게임옵션                       [현재 문항수] "+gameOption.quesNum);
        System.out.println("4. 종료 " );
        System.out.println("==========================================================");
        System.out.print("Command> ");
    }

    private void printMessage(String str){
        System.out.println(str);
    }

}

