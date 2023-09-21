import java.util.Arrays;

public class ArrayUtils {
    public static void winsorizeArray(int[] array, double trimPercent){
        Arrays.sort(array);
        int trim = (int)(array.length * (trimPercent / 100));
        for(int i = 0; i < trim; i++){
            if(i < trim / 10){
                array[i] = array[trim];
            }
            array[array.length-1-i] = array[array.length-1-trim];
        }
    }
    public static int getArrayTotal(int[] array) {
        int total = 0;
        for(float d : array) {total += d;}
        return total;
    }
    public static float getArrayAverage(int[] array){
        return (float) getArrayTotal(array) / array.length;
    }
    public static int getArrayMedian(int[] array){
        if(array.length % 2 == 0){
            return (array[array.length / 2] + array[(array.length / 2) - 1]) / 2;
        } else {
            return array[array.length / 2];
        }
    }
    public static int getArrayMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) if (array[i] > max) max = array[i];
        return max;
    }
}
