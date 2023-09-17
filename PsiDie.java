import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PsiDie {
    static int totalNumOfRolls, numOfRolls_d20, numOfRolls_d12, numOfRolls_d10, numOfRolls_d8, numOfRolls_d6,
            numOfRolls_d4, numOfRolls_d2, result, pointsMade, lowestNumOfRolls, highestNumOfRolls = 0;
    static ArrayList<Integer> totalRolls = new ArrayList<>();
    static ArrayList<Integer> points = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d20 = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d12 = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d10 = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d8 = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d6 = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d4 = new ArrayList<>();
    static ArrayList<Integer> totalRolls_d2 = new ArrayList<>();
    static Random myRand = new Random();
        static int testRuns = 12_000;
        static int trimAmount = 8;
        static int currentFocusPoints = 3;
        public static int initialDieSize = 4;
        static int psiDie = initialDieSize;
        static boolean include_d20 = false;
        static boolean include_d12 = false;
        static boolean include_d10 = false;
        static boolean include_d8 = false;
        static boolean include_d6 = false;
        static boolean include_d4 = true;
        static boolean include_d2 = true;
        static boolean increaseDieSize = true;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        testDice();

        if(include_d2){processDiceResults(totalRolls_d2, "d2");}
        if(include_d4){processDiceResults(totalRolls_d4, "d4");}
        if(include_d6){processDiceResults(totalRolls_d6, "d6");}
        if(include_d8){processDiceResults(totalRolls_d8, "d8");}
        if(include_d10){processDiceResults(totalRolls_d10, "d10");}
        if(include_d12){processDiceResults(totalRolls_d12, "d12");}
        if(include_d20){processDiceResults(totalRolls_d20, "d20");}
        processTotalResults(totalRolls, "Total");

        PsiChartUtils.createBoxPlot();
        PsiChartUtils.createRollChart();

        System.out.println("Mode: " + ListUtils.getListMode(totalRolls));

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
            totalRolls.add(totalNumOfRolls);
            totalRolls_d20.add(numOfRolls_d20);
            totalRolls_d12.add(numOfRolls_d12);
            totalRolls_d10.add(numOfRolls_d10);
            totalRolls_d8.add(numOfRolls_d8);
            totalRolls_d6.add(numOfRolls_d6);
            totalRolls_d4.add(numOfRolls_d4);
            totalRolls_d2.add(numOfRolls_d2);
            points.add(pointsMade);
            if (i == 0){ lowestNumOfRolls = totalNumOfRolls; highestNumOfRolls = totalNumOfRolls;}
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
            //if(psiDie == 2 && result == 2) result = 0;   // which order should this go in?
        }
        else if(result == psiDie && includeHigherDie && increaseDieSize) {
            psiDie += 2;
            if (psiDie == 14) psiDie = 20;
        }
    }
    public static void processTotalResults(ArrayList<Integer> list, String name){
        Collections.sort(list);
        ListUtils.winsorizeList(list, trimAmount);
        System.out.println("------" + name + " Rolls List------");
        System.out.println("Lowest: " + list.get(0));
        System.out.println("Highest: " + list.get(list.size() - 1));
        System.out.println("Mean: " + (float)Math.round(ListUtils.getListAverage(list)*100)/100);
        System.out.println("Median: " + ListUtils.getListMedian(list));
    }
    public static void processDiceResults(ArrayList<Integer> list, String name){
        Collections.sort(list);
        ListUtils.winsorizeList(list, trimAmount);
        float listAverage = (float)Math.round(ListUtils.getListAverage(list)*100)/100f;
        System.out.println("------" + name + " Rolls List------");
        System.out.println("Percentage: " + (float)Math.round(listAverage/
                ListUtils.getListAverage(totalRolls)*10000)/100 + "%");
        System.out.println("Mean: " + listAverage);
        System.out.println("Median: " + ListUtils.getListMedian(list));
    }
}