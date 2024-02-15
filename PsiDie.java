import java.util.Random;
// check out Cobertura to see how many steps for optimization purposes
public class PsiDie {
    static short totalNumOfRolls, numOfRolls_d100, numOfRolls_d20, numOfRolls_d12, numOfRolls_d10, numOfRolls_d8, numOfRolls_d6, numOfRolls_d4, numOfRolls_d2, pointsRolled;
    static Random myRand = new Random();
    static int testRuns = 2_500_000;
    static short[] totalRolls = new short[testRuns], totalRolls_d100 = new short[testRuns], totalRolls_d20 = new short[testRuns], totalRolls_d12  = new short[testRuns], totalRolls_d10  = new short[testRuns], totalRolls_d8 = new short[testRuns], totalRolls_d6 = new short[testRuns], totalRolls_d4 = new short[testRuns], totalRolls_d2 = new short[testRuns], points = new short[testRuns];
    static short[][] allRolls = {totalRolls_d2, totalRolls_d4, totalRolls_d6, totalRolls_d8, totalRolls_d10, totalRolls_d12, totalRolls_d20, totalRolls_d100, totalRolls, points};
    static int trimmedLength = (int) (testRuns * 0.02);
    static byte currentFocusPoints = 2, result, psiDie, initialDieSize = 12;
    static boolean showPoints = false, increaseDieSize = true, include_d100 = initialDieSize >= 100, 
    include_d20 = initialDieSize >= 20, include_d12 = initialDieSize >= 12, include_d10 = initialDieSize >= 10, 
    include_d8 = initialDieSize >= 8, include_d6 = initialDieSize >= 6, include_d4 = initialDieSize >= 4;
    static boolean[] includeDice = {true, include_d4, include_d6, include_d8, include_d10, include_d12, include_d20, include_d100, true, showPoints};
    static String[] names = {"d2", "d4", "d6", "d8", "d10", "d12", "d20", "d100", "Total", "Points"};
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        testDice();
        
        for(int i = 0; i < allRolls.length; i++){
            if(includeDice[i]){ processResults(allRolls[i], names[i]); }
        }
        
        // ChartUtils.createRollChart();
        // System.out.print(".");
        // ChartUtils.createBoxPlot();
        // System.out.print(".");
        System.out.print(Math.round((System.nanoTime() - startTime) / 1_000_000)/1_000f + "s");
    }
    /**
     * The function `testDice()` simulates rolling all of the and keeps track of the
     * number of rolls for each type of dice.
     */
    public static void testDice(){
        for (int i = 0; i < testRuns; i++) {
            psiDie = initialDieSize;
            // The code block is a do-while loop that rolls dice based on the value of `psiDie`.
            do { switch (psiDie) {
                    case 100 -> { numOfRolls_d100++; rollDice(false);
                        if (psiDie < 100) { psiDie = 20; } }
                    case 20 -> { numOfRolls_d20++; rollDice(include_d100); 
                        if (psiDie != 20){
                            if (psiDie < 20) { psiDie = 12; }
                            else { psiDie = 100; } } }
                    case 12 -> { numOfRolls_d12++; rollDice(include_d20);
                        if (psiDie > 12) psiDie = 20; }
                    case 10 -> { numOfRolls_d10++; rollDice(include_d12); }
                    case 8 -> { numOfRolls_d8++; rollDice(include_d10); }
                    case 6 -> { numOfRolls_d6++; rollDice(include_d8); }
                    case 4 -> { numOfRolls_d4++; rollDice(include_d6); }
                    case 2 -> { numOfRolls_d2++; rollDice(include_d4); }
                    default -> System.out.println(psiDie);
                } } while (psiDie > 0);
            // This block of code is updating the values of various variables and arrays after each
            // round of dice rolling.
            totalRolls[i] = totalNumOfRolls;
            totalRolls_d100[i] = numOfRolls_d100;
            totalRolls_d20[i] = numOfRolls_d20;
            totalRolls_d12[i] = numOfRolls_d12;
            totalRolls_d10[i] = numOfRolls_d10;
            totalRolls_d8[i] = numOfRolls_d8;
            totalRolls_d6[i] = numOfRolls_d6;
            totalRolls_d4[i] = numOfRolls_d4;
            totalRolls_d2[i] = numOfRolls_d2;
            points[i] = pointsRolled;
            totalNumOfRolls = pointsRolled = numOfRolls_d100 = numOfRolls_d20 = numOfRolls_d12 = numOfRolls_d10 = numOfRolls_d8 = numOfRolls_d6 = numOfRolls_d4 = numOfRolls_d2 = 0;
        }
    }
    /**
     * The function "rollDice" rolls a dice and updates the points rolled, total number of rolls, and
     * the size of the dice based on certain conditions.
     * 
     * @param includeHigherDie A boolean value indicating whether or not to include a higher die in the
     * roll.
     */
    public static void rollDice(boolean includeHigherDie){
        result = (byte) (myRand.nextInt(psiDie) + 1);
        pointsRolled += result;
        totalNumOfRolls++;
        if(result <= currentFocusPoints) psiDie -= 2;
        else if(increaseDieSize && includeHigherDie && result == psiDie) psiDie += 2;
    }
    /**
     * The function processes an array of short values and a name, and then prints out various
     * statistics about the array.
     * 
     * @param array An array of short values representing the results of some rolls or points.
     * @param name The "name" parameter is a String that represents the name of the rolls being
     * processed. It can be either "Total" or "Points".
     */
    public static void processResults(short[] array, String name){
        ArrayUtils.winsorizeArray(array);
        double arrayAverage = ArrayUtils.getArrayAverage(array);
        if (name.equals("Total") || name.equals("Points")) {
            System.out.println("----- " + name + " Rolls -----");
            System.out.println("Lowest: " + array[0]);
            System.out.println("Highest: " + array[testRuns - 1]);
            // System.out.println("Mode: " + ArrayUtils.getArrayMode(array));
            // System.out.println("Range: " + ArrayUtils.getArrayRange(array));
        } else {
            double totalAverage = ArrayUtils.getArrayAverage(totalRolls);
            System.out.println("------ " + name + " Rolls ------");
            System.out.println("Percentage: " + Math.round(arrayAverage / totalAverage * 10000)/100f + "%");
        }
        System.out.println("Mean: " + Math.round(arrayAverage*100)/100f);
        System.out.println("Median: " + ArrayUtils.getArrayMedian(array));
    }
}