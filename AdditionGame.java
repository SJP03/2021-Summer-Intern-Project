
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.json.simple.*;

public class AdditionGame {
	int[] answer=new int[10]; //문제의 정답을 저장하기 위한 배열
	int[] input=new int[10]; //사용자 입력(사용자가 입력하는 정답)을 저장하기 위한 배열
	int ansCount=0; //사용자가 맞힌 문제의 개수
	long start, end, timeSpent; //계산 소요 시간 측정을 위한 변수
	String[] expression=new String[10];
	
	public void play() {
		long start = System.currentTimeMillis(); //시간 재기
		System.out.println();
		System.out.println("연산 결과를 입력해주세요.(정수만 입력 가능) 총 10문제가 주어집니다.");
		
		Scanner scanner=new Scanner(System.in);
		for(int i=0;i<10;i++){  
			int t1=(int)((Math.random() * (100 - 10)) + 10); //두 자릿수 양수 랜덤 생성
			int t2=(int)((Math.random() * (100 - 10)) + 10); //두 자릿수 양수 랜덤 생성
			String t1_String=Integer.toString(t1);
			String t2_String=Integer.toString(t2);
			expression[i]=t1_String+"+"+t2_String;
			answer[i]=t1+t2;
			System.out.print("["+(i+1)+"] "+t1 +" + "+t2+" = "); 
			try{
				input[i]=scanner.nextInt(); 
			}catch(InputMismatchException e) {
				scanner.nextLine();
				//scanner=new Scanner(System.in); 메모리 사용량 면에서 좋지 않음
				System.out.println("정수만 입력 가능합니다. 정답을 다시 입력해주세요.");
				i--;
			}
		}
		long end = System.currentTimeMillis();
		timeSpent=(long) ((end - start)/1000.0);
		
		checkAnswer();
	}
	
	public void checkAnswer() {
		System.out.println();
		System.out.println("결과를 채점합니다. (채점 결과는 O/X로 표시됩니다.)");
	    
		for(int i=0; i<10; i++) {
			if(answer[i]!=input[i]){ //정답이 아니면
				System.out.println("["+(i+1)+"] X -> 정답은 "+answer[i]+"입니다.");
				
			}else{ //정답이면
				System.out.println("["+(i+1)+"] O");
				ansCount++; //정답 개수 ++
			}
		}
		
		System.out.println();
		System.out.println("계산 소요 시간: "+timeSpent+"초");
		System.out.println("정답률: "+(ansCount%11)*10+"%");

	}
	
	/*public void saveResult() throws IOException {
		File file=new File("result.txt");
		FileWriter fw=new FileWriter(file);
		for(int i=0; i<10; i++) {
			fw.write("문제: "+expression[i]+"="+answer[i]+" 사용자 정답: "+input[i]+"\n");
		}
	}*/
	
	public void checkScore(){
		System.out.println();
		JSONArray jsonArray=new JSONArray();
		
	}
}
