
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class AdditionGameTester {
	public static void main(String[] args) throws IOException {
		Scanner scanner=new Scanner(System.in);
		AdditionGame additionGame=new AdditionGame();

		System.out.println("***********************************");
		System.out.println("          Addition Game");
		System.out.println("***********************************");
		System.out.print("  (1) 게임하기  (2) 점수 확인  (3) 종료 : ");
		
		while(true) {
			int menu=scanner.nextInt();
			if(menu==3) {
				System.out.println();
				System.out.println("게임을 종료합니다.");
				break;
			}
			
			switch(menu) {
			case 1: 
				additionGame.play();
				break;
			case 2:
				additionGame.checkScore();
				break;
			}
		}
		
		return;
	}
}

