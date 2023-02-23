import java.util.Arrays;
import java.util.Random;

public class RandomArrayGenerator {
    public int arraySize = 100;
    public final int[] randomArray = new int[arraySize];

    public RandomArrayGenerator() {
        Random random = new Random();

        for (int i = 0; i < arraySize; i++) {
            randomArray[i] = random.nextInt(101);
        }
        randomArray[54] = random.nextInt(-10, 0);
        System.out.println(Arrays.toString(randomArray));
    }
}