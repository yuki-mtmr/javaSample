package exday1;

public class SampleClassEx01 {
    private int value = 0;
    private static int num = 0;

    public SampleClassEx01(int value) {
        this.value = value;

        num++;
    }

    public SampleClassEx01() {
        this(100);
    }

    public static int getNumberOfInstance() {
        return num;
    }

    public int getValue() {
        return this.value;
    }
}
