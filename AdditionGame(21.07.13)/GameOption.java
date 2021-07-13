import org.json.JSONArray;

import java.util.Scanner;

public class GameOption {
    private JSONArray questions; //출제한 문제 정보 저장 (문제, 답 -> 10+20, 30)
    static int quesNum=10; //게임 문항 수
    static String level="중"; //게임 난이도 (상, 중, 하)

    /*옵션(난이도, 문항수) 선택*/
    public void setOption(){
        Scanner optionInput=new Scanner(System.in);
        System.out.print("(1) 난이도 설정  (2)문항수 설정 :");
        int option=optionInput.nextInt();
        if(option==1){
            setLevel();
        }else if(option==2){
            setQuesNum();
        }
    }

    /*난이도 설정*/
    public void setLevel(){
        Scanner levelInput=new Scanner(System.in);
        System.out.print("\n게임 난이도를 설정하세요. \n(1)상 (2)중 (3)하 :");
        int input=levelInput.nextInt();

        if(input==1){
            level="상";
        }else if(input==2){
            level="중";
        }else if(input==3){
            level="하";
        }
        System.out.println("난이도가 "+level+"(으)로 설정되었습니다.\n");

        System.out.println("[Enter]를 치세요.");
        levelInput.nextLine();
        levelInput.nextLine();
    }

    /*문항수 설정*/
    public void setQuesNum(){
        Scanner quesNumInput=new Scanner(System.in);
        System.out.print("\n게임 문항수를 설정하세요: ");
        quesNum=quesNumInput.nextInt();
        System.out.println("문항수가 "+quesNum+"(으)로 설정되었습니다.\n");

        System.out.println("[Enter]를 치세요.");
        quesNumInput.nextLine();
        quesNumInput.nextLine();
    }

}
