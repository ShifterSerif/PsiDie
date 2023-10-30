import java.util.Arrays;
public class ArrayUtils {
    public static void winsorizeArray(short[] array){
        Arrays.sort(array);
        short lowerTrimValue = array[PsiDie.trim];
        short upperTrimValue = array[PsiDie.testRuns-1-PsiDie.trim];
        for(int i = 0; i < PsiDie.trim; i++){
            array[i] = lowerTrimValue;
            array[PsiDie.testRuns-1-i] = upperTrimValue;
        }
    }  
    public static int getArrayTotal(short[] array) {
        int total = 0;
        for (short s : array) total += s;
        return total;
    }
    public static double getArrayAverage(short[] array){ return (double) getArrayTotal(array) / PsiDie.testRuns; }
    public static int getArrayMedian(short[] array){ return array[PsiDie.testRuns / 2]; }
    public static short getArrayUnsortedMax(short[] array) {
        short max = array[0];
        for (short s : array) if (s > max) max = s;
        return max;
    }
    public static void printArray(short[] array){
        for (short s : array) System.out.print(s + " ");
        System.out.println();
    }
    public static short getArrayMode(short[] array) {
        short mode = array[0];
        int count = 1, maxCount = 1;
        for (int i = 1; i < array.length; ++i) {
            if (array[i] == array[i - 1]) ++count;
            else {
                if (count > maxCount) {
                    maxCount = count;
                    mode = array[i - 1];
                }
                count = 1;
            }
        }
        if (count > maxCount) mode = array[array.length - 1];
        return mode;
    }
    public static int getArrayRange(short[] array){ return (array[array.length-1] - array[0]);}
}