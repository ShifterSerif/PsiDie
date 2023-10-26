import java.util.Arrays;

public class ArrayUtils {
    public static void winsorizeArray(short[] array){
        Arrays.sort(array);
        int shortenedTrim = PsiDie.trim / 20;
        short trimValue = array[shortenedTrim];
        for(int i = 0; i < PsiDie.trim; i++){
            if(i < shortenedTrim){ array[i] = trimValue;}
            array[PsiDie.testRuns-1-i] = array[PsiDie.testRuns-1-PsiDie.trim];
        }
    }
    public static float getArrayTotal(short[] array) {
        float total = 0;
        for(short d : array) {total += d;}
        return total;
    }
    public static double getArrayAverage(short[] array){ return getArrayTotal(array) / PsiDie.testRuns; }
    public static int getArrayMedian(short[] array){ return array[PsiDie.testRuns / 2]; }
    public static short getArrayMax(short[] array) {
        short max = array[0];
        for (int i = 1; i < PsiDie.testRuns; i++) 
            if (array[i] > max) max = array[i];
        return max;
    }
    public static void printArray(short[] array){
        for (short s : array) System.out.print(s + " ");
        System.out.println();
    }
    public static short getArrayMode(short[] array){
        short mode = array[0];
        int modeCount = 0;
        for (int i = 0; i < PsiDie.testRuns; i++){
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