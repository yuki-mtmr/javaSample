package day3;

public class Sample304 {

    public static void main(String[] args) {
        int dice = (int) (Math.random() * 7) + 1;
        System.out.println("サイコロの目:" + dice);

        if (dice >= 1 && dice <= 6) {
            if (dice % 2 == 0) {
                System.out.println("丁(チョウ)です");
            } else {
                System.out.println("半(ハン)です");
            }
        } else {
            System.out.println("範囲外の数値です。");
        }
    }
}
