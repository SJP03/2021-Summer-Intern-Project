import org.json.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GameRecord {
    private JSONObject gameData; //게임 플레이 한 번에 대한 정보
    private JSONObject totGameData=new JSONObject(); //전체 게임 플레이에 대한 정보, ex)1번째 게임, 2번째 게임, 3번째 게임, ...
    private int playCount=0; //몇 번째 플레이인지 저장

    /*파일로 저장된 게임 데이터를 불러오는 함수*/
    public int loadRecord()  {
        String gameData_String="";
        try{
            String file_read = "";
            String fileName = "./GameData.json";
            StringBuffer readBuffer = new StringBuffer();
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = "";
                while ((line = reader.readLine()) != null) {
                    readBuffer.append(line);
                }
                gameData_String = readBuffer.toString();
            } catch (Exception e) {
                //e.printStackTrace();
            }
            totGameData = new JSONObject(gameData_String);
            playCount=0;
            for(String key:totGameData.keySet()){
                playCount++;
            }
        }catch(Exception e){
            totGameData = new JSONObject();
        }
        return playCount;
    }

    public void saveResult(JSONObject gameData) {
        this.gameData =gameData;
        totGameData.put((playCount+1)+"번째 게임", gameData);
        playCount++;
        saveRecord();
    }

    /*게임 순위 or 전체 점수를 보여주는 함수*/
    public String getRecord(){
        System.out.print("(1)게임 순위 보기  (2)전체 점수 보기 :");
        Scanner screenInput=new Scanner(System.in);
        int menu=screenInput.nextInt();
        switch(menu) {
            case 1:
                System.out.println();
                Map<String, Double> map=new HashMap<>();
                for(String key:totGameData.keySet()){
                    JSONObject tempJSONObject=(JSONObject) totGameData.get(key);
                    map.put(String.valueOf(key), tempJSONObject.getDouble("점수"));
                }

                List keySet=new ArrayList<>(map.keySet());
                Collections.sort(keySet, (value1, value2) -> (map.get(value2).compareTo(map.get(value1))));
                int i=1;
                for(Object key:keySet){
                    if(i==4) break;
                    System.out.println(i+"위: "+key+" -> 점수: "+map.get(key)+"점");
                    i++;
                }
                break;

            case 2:
                if(playCount==0){
                    System.out.println("이전 게임 내역이 없습니다.");
                }
                int j=1;
                for(Iterator iter=totGameData.keySet().iterator();iter.hasNext();) {
                    String key = (String) iter.next();
                    JSONObject eachGameData=(JSONObject) totGameData.get(String.valueOf(j)+"번째 게임");
                    JSONArray eachQuesInfoArray=(JSONArray) eachGameData.get("문제 정보");
                    System.out.println();
                    System.out.println("==================================================");
                    System.out.println(j + "번째 게임 데이터  (" + eachGameData.get("시작 시간") + ")");
                    System.out.println("==================================================");
                    System.out.println("[점수] " + eachGameData.getDouble("점수") + "점");
                    System.out.println("[소요 시간] " + eachGameData.get("소요 시간") + "초");
                    System.out.println("[난이도] " + eachGameData.get("난이도"));
                    System.out.println("[문항수] "+eachGameData.getInt("문항수")+"문제");
                    System.out.println("[문제 정보]");
                    for (int k = 0; k < eachQuesInfoArray.length(); k++) {
                        JSONObject eachQuesInfo = (JSONObject) eachQuesInfoArray.get(k);
                        System.out.print((k + 1) + "번.  ");
                        System.out.print("문제: " + eachQuesInfo.getString("문제"));
                        System.out.print(",   정답: " + eachQuesInfo.getInt("정답"));
                        System.out.print(",   사용자 답: " + eachQuesInfo.getInt("사용자 답"));
                        System.out.println();
                    }
                    j++;
                }
                break;
        }


        return "";
    }

    /*게임 데이터를 파일로 저장하는 함수*/
    public void saveRecord(){
        String totGameData_String=totGameData.toString();
        try{
            FileWriter file = new FileWriter("GameData.json");
            file.write(totGameData_String);
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
