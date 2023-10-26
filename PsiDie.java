import java.util.Random;
public class PsiDie {
    static short totalNumOfRolls, numOfRolls_d100, numOfRolls_d20, numOfRolls_d12, numOfRolls_d10, numOfRolls_d8, numOfRolls_d6, numOfRolls_d4, numOfRolls_d2, result, pointsRolled;
    static Random myRand = new Random();
    static int testRuns = 2_500_000;
    static short[] totalRolls = new short[testRuns], totalRolls_d100 = new short[testRuns], totalRolls_d20 = new short[testRuns], totalRolls_d12  = new short[testRuns], totalRolls_d10  = new short[testRuns], totalRolls_d8 = new short[testRuns], totalRolls_d6 = new short[testRuns], totalRolls_d4 = new short[testRuns], totalRolls_d2 = new short[testRuns], points = new short[testRuns];
    static short trim = (short)(testRuns * 0.08);
    static byte currentFocusPoints = 1, psiDie, initialDieSize = 12;
    static boolean includePoints = false, increaseDieSize = false, include_d100, include_d20, include_d12, include_d10, include_d8, include_d6, include_d4;
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        initializeDieSize();
        testDice();
        processResults(totalRolls_d2, "d2");
        if(include_d4) processResults(totalRolls_d4, "d4");
        if(include_d6) processResults(totalRolls_d6, "d6");
        if(include_d8) processResults(totalRolls_d8, "d8");
        if(include_d10) processResults(totalRolls_d10, "d10");
        if(include_d12) processResults(totalRolls_d12, "d12");
        if(include_d20) processResults(totalRolls_d20, "d20");
        if(include_d100) processResults(totalRolls_d100, "d100");
        processResults(totalRolls, "Total");
        if(includePoints) processResults(points, "Points");
        // PsiChartUtils.createRollChart();
        // System.out.print(".");
        // PsiChartUtils.createBoxPlot();
        // System.out.print(".");
        System.out.print(Math.round((System.nanoTime() - startTime) / 1_000_000)/1_000f + "s");
    }
    public static void initializeDieSize(){
        include_d100 = initialDieSize >= 100;
        include_d20 = initialDieSize >= 20;
        include_d12 = initialDieSize >= 12;
        include_d10 = initialDieSize >= 10;
        include_d8 = initialDieSize >= 8;
        include_d6 = initialDieSize >= 6;
        include_d4 = initialDieSize >= 4;
    }
    public static void testDice(){
        for (int i = 0; i < testRuns; i++) {
            psiDie = initialDieSize;
            do { switch (psiDie) {
                    case 100 -> { numOfRolls_d100++; rollDice(false);
                        if(psiDie == 98) psiDie = 20; }
                    case 20 -> { numOfRolls_d20++; rollDice(include_d100); 
                        if(psiDie == 18) psiDie = 12;
                        else if(psiDie == 22) psiDie = 100; }
                    case 12 -> { numOfRolls_d12++; rollDice(include_d20);
                        if (psiDie == 14) psiDie = 20; }
                    case 10 -> { numOfRolls_d10++; rollDice(include_d12); }
                    case 8 -> { numOfRolls_d8++; rollDice(include_d10); }
                    case 6 -> { numOfRolls_d6++; rollDice(include_d8); }
                    case 4 -> { numOfRolls_d4++; rollDice(include_d6); }
                    case 2 -> { numOfRolls_d2++; rollDice(include_d4); }
                    default -> System.out.println(psiDie);
                } } while (psiDie > 0);
            totalRolls[i] = totalNumOfRolls;
            if (include_d20) totalRolls_d20[i] = numOfRolls_d20;
            if (include_d12) totalRolls_d12[i] = numOfRolls_d12;
            if (include_d10) totalRolls_d10[i] = numOfRolls_d10;
            if (include_d8) totalRolls_d8[i] = numOfRolls_d8;
            if (include_d6) totalRolls_d6[i] = numOfRolls_d6;
            if (include_d4) totalRolls_d4[i] = numOfRolls_d4;
            totalRolls_d2[i] = numOfRolls_d2;
            if (includePoints) points[i] = pointsRolled;
            totalNumOfRolls = pointsRolled = numOfRolls_d20 = numOfRolls_d12 = numOfRolls_d10 = numOfRolls_d8 =
                    numOfRolls_d6 = numOfRolls_d4 = numOfRolls_d2 = 0;
        }
    }
    public static void rollDice(boolean includeHigherDie){
        result = (short) (myRand.nextInt(psiDie) + 1);
        if (includePoints) pointsRolled += result;
        totalNumOfRolls++;
        if(result <= currentFocusPoints) { psiDie -= 2; }
        else if(increaseDieSize && includeHigherDie && result == psiDie) { psiDie += 2; }
    }
    public static void processResults(short[] array, String name){
        ArrayUtils.winsorizeArray(array);
        double arrayAverage = ArrayUtils.getArrayAverage(array);
        if (name.equals("Total") || name.equals("Points")) {
            System.out.println("-----" + name + " Rolls -----");
            System.out.println("Lowest: " + array[0]);
            System.out.println("Highest: " + array[testRuns - 1]);
            //System.out.println("Mode: " + ArrayUtils.getArrayMode(array));
        } else {
            double totalAverage = ArrayUtils.getArrayAverage(totalRolls);
            System.out.println("------" + name + " Rolls ------");
            System.out.println("Percentage: " + Math.round(arrayAverage / totalAverage * 10000)/100f + "%");
        }
        System.out.println("Mean: " + Math.round(arrayAverage*100)/100f);
        System.out.println("Median: " + ArrayUtils.getArrayMedian(array));
    }
}