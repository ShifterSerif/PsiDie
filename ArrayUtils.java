import java.util.Arrays;

public class ArrayUtils {
    public static void winsorizeArray(short[] array, float trimPercent){
        Arrays.sort(array);
        int trim = (int)(array.length * (trimPercent / 100));
        int shortenedTrim = trim / 20;
        for(int i = 0; i < trim; i++){
            if(i < shortenedTrim){array[i] = array[shortenedTrim];}
            array[array.length-1-i] = array[array.length-1-trim];
        }
    }
    public static float getArrayTotal(short[] array) {
        float total = 0;
        for(float d : array) {total += d;}
        return total;
    }
    public static float getArrayAverage(short[] array){return getArrayTotal(array) / array.length;}
    public static int getArrayMedian(short[] array){
        if(array.length % 2 == 0){
            return (array[array.length / 2] + array[(array.length / 2) - 1]) / 2;
        } else {
            return array[array.length / 2];
        }
    }
    public static short getArrayMax(short[] array) {
        short max = array[0];
        for (int i = 1; i < array.length; i++) if (array[i] > max) max = array[i];
        return max;
    }
    public static void printArray(short[] array){
        for (short s : array) System.out.print(s + " ");
        System.out.println();
    }
    public static short getArrayMode(short[] array){
short mode = array[0];
        int modeCount = 0;
        for (int i = 0; i < array.length; i++){
            int count = 0;
            for (short s : array) if (s == array[i]) count++;
            if (count > modeCount){
                modeCount = count;
                mode = array[i];
            }
        }
        return mode;
    }
}
