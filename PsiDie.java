import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PsiDie {
    static int totalNumOfRolls, numOfRolls_d20, numOfRolls_d12, numOfRolls_d10, numOfRolls_d8, numOfRolls_d6,
            numOfRolls_d4, numOfRolls_d2, result, pointsMade, lowestNumOfRolls, highestNumOfRolls = 0;
    static ArrayList<Integer> totalRollsList = new ArrayList<>();
    static ArrayList<Integer> pointsList = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d20List = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d12List = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d10List = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d8List = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d6List = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d4List = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d2List = new ArrayList<>();
    static Random myRand = new Random();
        static int testRuns = 1_000;
    static int[] totalRolls = new int[testRuns];

        static int trimAmount = 10;
        static int currentFocusPoints = 1;
        public static int initialDieSize = 20;
        static int psiDie = initialDieSize;
        static boolean includePoints = false;
        static boolean include_d20 = false;
        static boolean include_d12 = true;
        static boolean include_d10 = true;
        static boolean include_d8 = true;
        static boolean include_d6 = true;
        static boolean include_d4 = true;
        static boolean include_d2 = true;
        static boolean increaseDieSize = false;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        if(initialDieSize > 12 && !include_d20) initialDieSize = 12;
        if(initialDieSize > 10 && !include_d12) initialDieSize = 10;
        if(initialDieSize > 8 && !include_d10) initialDieSize = 8;
        if(initialDieSize > 6 && !include_d8) initialDieSize = 6;
        if(initialDieSize > 4 && !include_d6) initialDieSize = 4;
        if(initialDieSize > 2 && !include_d4) initialDieSize = 2;
        testDice();

        if(include_d2) processResults(totalRolls_d2List, "d2");
        if(include_d4) processResults(totalRolls_d4List, "d4");
        if(include_d6) processResults(totalRolls_d6List, "d6");
        if(include_d8) processResults(totalRolls_d8List, "d8");
        if(include_d10) processResults(totalRolls_d10List, "d10");
        if(include_d12) processResults(totalRolls_d12List, "d12");
        if(include_d20) processResults(totalRolls_d20List, "d20");
        processResults(totalRollsList, "Total");
        if(includePoints) processResults(pointsList, "Points");

        PsiChartUtils.createBoxPlot();
        System.out.print(".");
        PsiChartUtils.createRollChart();
        System.out.print(".");

        System.out.println((System.nanoTime() - startTime) / 1_000_000_000 + "s");
    }

    public static void testDice(){
        for (int i = 0; i < testRuns; i++) {
            psiDie = initialDieSize;
            do { switch (psiDie) {
                    case 20 -> {
                        numOfRolls_d20++;
                        rollDice(include_d12, false);
                    }
                    case 12 -> {
                        numOfRolls_d12++;
                        rollDice(include_d10, include_d20);
                    }
                    case 10 -> {
                        numOfRolls_d10++;
                        rollDice(include_d8, include_d12);
                    }
                    case 8 -> {
                        numOfRolls_d8++;
                        rollDice(include_d6, include_d10);
                    }
                    case 6 -> {
                        numOfRolls_d6++;
                        rollDice(include_d4, include_d8);
                    }
                    case 4 -> {
                        numOfRolls_d4++;
                        rollDice(true, include_d6);
                    }
                    case 2 -> {
                        numOfRolls_d2++;
                        rollDice(true, include_d4);
                    }
                    default -> System.out.println(psiDie);
                } } while (psiDie > 0);
            totalRollsList.add(totalNumOfRolls);
            if (include_d20) totalRolls_d20List.add(numOfRolls_d20);
            if (include_d12) totalRolls_d12List.add(numOfRolls_d12);
            if (include_d10) totalRolls_d10List.add(numOfRolls_d10);
            if (include_d8) totalRolls_d8List.add(numOfRolls_d8);
            if (include_d6) totalRolls_d6List.add(numOfRolls_d6);
            if (include_d4) totalRolls_d4List.add(numOfRolls_d4);
            if (include_d2) totalRolls_d2List.add(numOfRolls_d2);
            if (includePoints) pointsList.add(pointsMade);
            if (totalNumOfRolls < lowestNumOfRolls) lowestNumOfRolls = totalNumOfRolls;
            if (totalNumOfRolls > highestNumOfRolls) highestNumOfRolls = totalNumOfRolls;
            totalNumOfRolls = pointsMade = numOfRolls_d20 = numOfRolls_d12 = numOfRolls_d10 = numOfRolls_d8 =
                    numOfRolls_d6 = numOfRolls_d4 = numOfRolls_d2 = 0;
        }
    }
    public static void rollDice(boolean includeLowerDie, boolean includeHigherDie){
        result = myRand.nextInt(psiDie) + 1;
        pointsMade += result;
        totalNumOfRolls++;
        if(result <= currentFocusPoints && includeLowerDie) {
            psiDie -= 2;
            if(psiDie == 18) psiDie = 12;
            if(psiDie == 2 && !include_d2) psiDie = 0;
        }
        else if(result == psiDie && includeHigherDie && increaseDieSize) {
            psiDie += 2;
            if (psiDie == 14) psiDie = 20;
        }
    }
    public static void processResults(ArrayList<Integer> list, String name){
        Collections.sort(list);
        System.out.println(list.size());
        ListUtils.winsorizeList(list, trimAmount);
        float listAverage = ListUtils.getListAverage(list);
        if (list == totalRollsList || list == pointsList){
            System.out.println("-----" + name + " Rolls List-----");
            System.out.println("Lowest: " + list.get(0));
            System.out.println("Highest: " + list.get(list.size() - 1));
            //System.out.println("Mode: " + ListUtils.getListMode(list));
        } else {
            float totalAverage = ListUtils.getListAverage(totalRollsList);
            System.out.println("------" + name + " Rolls List------");
            System.out.println("Percentage: " + Math.round(listAverage / totalAverage * 10000)/100f + "%");
        }
        System.out.println("Mean: " + Math.round(listAverage*100)/100f);
        System.out.println("Median: " + ListUtils.getListMedian(list));
    }
}