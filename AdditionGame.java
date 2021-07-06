
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.json.simple.*;

public class AdditionGame {
	int[] answer=new int[10]; //������ ������ �����ϱ� ���� �迭
	int[] input=new int[10]; //����� �Է�(����ڰ� �Է��ϴ� ����)�� �����ϱ� ���� �迭
	int ansCount=0; //����ڰ� ���� ������ ����
	long start, end, timeSpent; //��� �ҿ� �ð� ������ ���� ����
	String[] expression=new String[10];
	
	public void play() {
		long start = System.currentTimeMillis(); //�ð� ���
		System.out.println();
		System.out.println("���� ����� �Է����ּ���.(������ �Է� ����) �� 10������ �־����ϴ�.");
		
		Scanner scanner=new Scanner(System.in);
		for(int i=0;i<10;i++){  
			int t1=(int)((Math.random() * (100 - 10)) + 10); //�� �ڸ��� ��� ���� ����
			int t2=(int)((Math.random() * (100 - 10)) + 10); //�� �ڸ��� ��� ���� ����
			String t1_String=Integer.toString(t1);
			String t2_String=Integer.toString(t2);
			expression[i]=t1_String+"+"+t2_String;
			answer[i]=t1+t2;
			System.out.print("["+(i+1)+"] "+t1 +" + "+t2+" = "); 
			try{
				input[i]=scanner.nextInt(); 
			}catch(InputMismatchException e) {
				scanner.nextLine();
				//scanner=new Scanner(System.in); �޸� ��뷮 �鿡�� ���� ����
				System.out.println("������ �Է� �����մϴ�. ������ �ٽ� �Է����ּ���.");
				i--;
			}
		}
		long end = System.currentTimeMillis();
		timeSpent=(long) ((end - start)/1000.0);
		
		checkAnswer();
	}
	
	public void checkAnswer() {
		System.out.println();
		System.out.println("����� ä���մϴ�. (ä�� ����� O/X�� ǥ�õ˴ϴ�.)");
	    
		for(int i=0; i<10; i++) {
			if(answer[i]!=input[i]){ //������ �ƴϸ�
				System.out.println("["+(i+1)+"] X -> ������ "+answer[i]+"�Դϴ�.");
				
			}else{ //�����̸�
				System.out.println("["+(i+1)+"] O");
				ansCount++; //���� ���� ++
			}
		}
		
		System.out.println();
		System.out.println("��� �ҿ� �ð�: "+timeSpent+"��");
		System.out.println("�����: "+(ansCount%11)*10+"%");

	}
	
	/*public void saveResult() throws IOException {
		File file=new File("result.txt");
		FileWriter fw=new FileWriter(file);
		for(int i=0; i<10; i++) {
			fw.write("����: "+expression[i]+"="+answer[i]+" ����� ����: "+input[i]+"\n");
		}
	}*/
	
	public void checkScore(){
		System.out.println();
		JSONArray jsonArray=new JSONArray();
		
	}
}
