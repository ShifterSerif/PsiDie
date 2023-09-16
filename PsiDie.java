import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

    //Ideas-Psi die goes down/ up if it equals your remaining amount of focus points
    //      using it to use amp without losing focus point?
    //      circumstance or status bonus?
    //      amp sets it to max?
    //      adds generic amp?
    //      Class archetype?
    //      Magic Missile-like where you can choose how many actions to use to roll the psi die?
    //      Add a reaction?

public class PsiDie {
    static int numOfRollsTotal, numOfRolls_d20, numOfRolls_d12, numOfRolls_d10, numOfRolls_d8, numOfRolls_d6,
            numOfRolls_d4, numOfRolls_d2, result, pointsMade, lowestNumOfRolls, highestNumOfRolls = 0;
    static ArrayList<Integer> total_Rolls = new ArrayList<>();
    static ArrayList<Integer> points = new ArrayList<>();
    static ArrayList<Integer> total_d20 = new ArrayList<>();
    static ArrayList<Integer> total_d12 = new ArrayList<>();
    static ArrayList<Integer> total_d10 = new ArrayList<>();
    static ArrayList<Integer> total_d8 = new ArrayList<>();
    static ArrayList<Integer> total_d6 = new ArrayList<>();
    static ArrayList<Integer> total_d4 = new ArrayList<>();
    static ArrayList<Integer> total_d2 = new ArrayList<>();
    static Random myRand = new Random();
        static int testRuns = 80_000;
        static int trimAmount = 8;
        static int currentFocusPoints = 3;
        public static int initialDieSize = 20;
        static int psiDie = initialDieSize;
        static boolean include_d20 = true;
        static boolean include_d12 = true;
        static boolean include_d10 = true;
        static boolean include_d8 = true;
        static boolean include_d6 = true;
        static boolean include_d4 = true;
        static boolean include_d2 = true;
        static boolean increaseDieSize = false;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        testDice();

        if(include_d2){processDiceResults(total_d2, "d2");}
        if(include_d4){processDiceResults(total_d4, "d4");}
        if(include_d6){processDiceResults(total_d6, "d6");}
        if(include_d8){processDiceResults(total_d8, "d8");}
        if(include_d10){processDiceResults(total_d10, "d10");}
        if(include_d12){processDiceResults(total_d12, "d12");}
        if(include_d20){processDiceResults(total_d20, "d20");}
        processTotalResults(total_Rolls, "Total Rolls");

        PsiChartUtils.createBoxPlot();
        PsiChartUtils.createRollChart();

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
            total_Rolls.add(numOfRollsTotal);
            total_d20.add(numOfRolls_d20);
            total_d12.add(numOfRolls_d12);
            total_d10.add(numOfRolls_d10);
            total_d8.add(numOfRolls_d8);
            total_d6.add(numOfRolls_d6);
            total_d4.add(numOfRolls_d4);
            total_d2.add(numOfRolls_d2);
            points.add(pointsMade);
            if (i == 0){ lowestNumOfRolls = numOfRollsTotal; highestNumOfRolls = numOfRollsTotal;}
            if (numOfRollsTotal < lowestNumOfRolls) lowestNumOfRolls = numOfRollsTotal;
            if (numOfRollsTotal > highestNumOfRolls) highestNumOfRolls = numOfRollsTotal;
            numOfRollsTotal = pointsMade = numOfRolls_d20 = numOfRolls_d12 = numOfRolls_d10 = numOfRolls_d8 =
                    numOfRolls_d6 = numOfRolls_d4 = numOfRolls_d2 = 0;
        }
    }

    public static void rollDice(boolean includeLowerDie, boolean includeHigherDie){
        result = myRand.nextInt(psiDie) + 1;
        pointsMade += result;
        numOfRollsTotal++;
        if(result <= currentFocusPoints && includeLowerDie) {
            psiDie -= 2;
            if(psiDie == 18) psiDie = 12;
            if(psiDie == 2 && !include_d2) psiDie = 0;
        }
        if(result == psiDie && includeHigherDie && increaseDieSize) {
            psiDie += 2;
            if (psiDie == 14) psiDie = 20;
        }
    }
    public static void processTotalResults(ArrayList<Integer> list, String name){
        Collections.sort(list);
        ListUtils.winsorizeList(list, trimAmount);
        System.out.println("------" + name + " List------");
        System.out.println("Lowest: " + list.get(0));
        System.out.println("Highest: " + list.get(list.size() - 1));
        System.out.println("Median: " + ListUtils.getListMedian(list));
        System.out.println("Average: " + Math.round(ListUtils.getListAverage(list)));
    }
    public static void processDiceResults(ArrayList<Integer> list, String name){
        Collections.sort(list);
        ListUtils.winsorizeList(list, trimAmount);
        System.out.println("------" + name + " List------");
        System.out.println("Percentage: " + Math.round(ListUtils.getListAverage(list)/
                ListUtils.getListAverage(total_Rolls)*100) + "%");
        System.out.println("Median: " + ListUtils.getListMedian(list));
        System.out.println("Average: " + Math.round(ListUtils.getListAverage(list)));
    }
}