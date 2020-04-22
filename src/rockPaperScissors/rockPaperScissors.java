package rockPaperScissors;

import java.util.Scanner;

public class rockPaperScissors {

    private String hand[] = {"グー","チョキ","パー"};
    private String result[] = {"あいこです","あなたの勝ちです","あなたの負けです"};
    private int resultN = 0; //初期値あいこ
    private int you = 3;
    private int opponent = (int) (Math.random() * 3);


    public void play() {
        boolean b;
        System.out.println("出す手を決めろ");
        while (you >= 3) {
            System.out.println("0:" + hand[0] + "\n" + "1:" + hand[1] + "\n" + "2:" + hand[2]);
            Scanner scan = new Scanner(System.in);
            do {
                try {
                    you = Integer.parseInt(scan.nextLine());
                    b = false;                             // <- if ok, set false
                } catch(NumberFormatException e){
                    System.out.println("数字を入力して下さい");
                    b = true;                              // <- if catch block is entered, set true
                }
            } while (b);
            if (you >= 3) {
                System.out.println("0から2までの数字を入力してください");
            }
        }

        result(you, opponent);

        System.out.println("あなたは" + hand[you] + "を出しました");
        System.out.println("対戦相手は" + hand[opponent] + "を出しました");
        System.out.println("結果:" + result[resultN]);
    }

    public int result(int you, int opponent) {
        if (you != opponent) {
            if (you == 0) {
                if (opponent == 1) {
                    resultN = 1;
                } else {
                    resultN = 2;
                }
            } else if (you == 1) {
                if (opponent == 2) {
                    resultN = 1;
                } else {
                    resultN = 2;
                }
            } else {
                if (opponent == 0) {
                    resultN = 1;
                } else {
                    resultN = 2;
                }
            }
        }
        return resultN;
    }
}

