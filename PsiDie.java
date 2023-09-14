import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


//Ideas - Psi die goes down/ up if it equals your remaining amount of focus points
//        using it to use amp without losing focus point?
//        circumstance or status bonus?
//        Psyche and/or amp sets it to max?

public class PsiDie {
    static int result = 0;
    static int numOfRolls = 0;
    static int numOfRolls_d12 = 0;
    static int numOfRolls_d10 = 0;
    static int numOfRolls_d8 = 0;
    static int numOfRolls_d6 = 0;
    static int numOfRolls_d4 = 0;
    static int numOfRolls_d2 = 0;
    static int testRuns = 50_000;
    static int pointsMade = 0;
    static double totalRolls = 0;
    static double total_d12s = 0;
    static double total_d10s = 0;
    static double total_d8s = 0;
    static double total_d6s = 0;
    static double total_d4s = 0;
    static double total_d2s = 0;
    static double lowestNumOfRolls = 0;
    static double highestNumOfRolls = 0;
    static ArrayList<Integer> rollsList = new ArrayList<>();
    static ArrayList<Integer> pointList = new ArrayList<>();
    static ArrayList<Integer> d12_List = new ArrayList<>();
    static ArrayList<Integer> d10_List = new ArrayList<>();
    static ArrayList<Integer> d8_List = new ArrayList<>();
    static ArrayList<Integer> d6_List = new ArrayList<>();
    static ArrayList<Integer> d4_List = new ArrayList<>();
    static ArrayList<Integer> d2_List = new ArrayList<>();
    static double totalPoints = 0;
    static Random myRand = new Random();
    public static int initialDieSize = 6;
    static int psiDie = initialDieSize;
    static boolean include_d12 = false;
    static boolean include_d10 = false;
    static boolean include_d8 = false;
    static boolean include_d6 = true;
    static boolean include_d4 = true;
    static boolean include_d2 = false;
    static int trimAmount = 10;


    public static void main(String[] args) {
        long startTime = System.nanoTime();
        testDice();

        Collections.sort(rollsList);
        ListUtils.winsorizeList(rollsList, trimAmount);
        System.out.println("------Rolls List------");
        printListInfo(rollsList);

        Collections.sort(pointList);
        ListUtils.winsorizeList(pointList, trimAmount);
        System.out.println("------Points List------");
        printListInfo(pointList);

        if(include_d2){
            Collections.sort(d2_List);
            ListUtils.winsorizeList(d2_List, trimAmount);
            System.out.println("------d2 List------");
            printListInfoDice(d2_List);
        }
        if(include_d4){
            Collections.sort(d4_List);
            ListUtils.winsorizeList(d4_List, trimAmount);
            System.out.println("------d4 List------");
            printListInfoDice(d4_List);
        }
        if(include_d6){
            Collections.sort(d6_List);
            ListUtils.winsorizeList(d6_List, trimAmount);
            System.out.println("------d6 List------");
            printListInfoDice(d6_List);
        }
        if(include_d8){
            Collections.sort(d8_List);
            ListUtils.winsorizeList(d8_List, trimAmount);
            System.out.println("------d8 List------");
            printListInfoDice(d8_List);
        }
        if(include_d10){
            Collections.sort(d10_List);
            ListUtils.winsorizeList(d10_List, trimAmount);
            System.out.println("------d10 List------");
            printListInfoDice(d10_List);
        }
        if(include_d12){
            Collections.sort(d12_List);
            ListUtils.winsorizeList(d12_List, trimAmount);
            System.out.println("------d12 List------");
            printListInfoDice(d12_List);
        }

        System.out.println();
        PsiChartUtils.createRollChart();
        PsiChartUtils.createBoxPlot();

        System.out.println((System.nanoTime() - startTime) / 1_000_000_000 + "s");
    }

    public static void testDice(){
        for (int i = 0; i < testRuns; i++) {
            psiDie = initialDieSize;
            do {
                switch (psiDie) {
                    case 12 -> {{
                        result = myRand.nextInt(psiDie) + 1;
                        pointsMade += result;
                        numOfRolls++;
                        numOfRolls_d12++;
                        }
                        if (result == 6) psiDie -= 2;
                    }
                    case 10 -> {{
                        result = myRand.nextInt(psiDie) + 1;
                        pointsMade += result;
                        numOfRolls++;
                        numOfRolls_d10++;
                        }
                        if (result == 5 && include_d8) psiDie -= 2;
                        if (result == 1 && include_d12) psiDie += 2;
                    }
                    case 8 -> {{
                        result = myRand.nextInt(psiDie) + 1;
                        pointsMade += result;
                        numOfRolls++;
                        numOfRolls_d8++;
                        }
                        if (result == 4 && include_d6) psiDie -= 2;
                        if (result == 1 && include_d10) psiDie += 2;
                    }
                    case 6 -> {{
                        result = myRand.nextInt(psiDie) + 1;
                        pointsMade += result;
                        numOfRolls++;
                        numOfRolls_d6++;
                        }
                        if (result == 3 && include_d4) psiDie -= 2;
                        if (result == 1 && include_d8) psiDie += 2;
                    }
                    case 4 -> {{
                        result = myRand.nextInt(psiDie) + 1;
                        pointsMade += result;
                        numOfRolls++;
                        numOfRolls_d4++;
                        }
                        if (result == 2) if (include_d2) psiDie = 2; psiDie = 0;
                        if (result == 1 && include_d6) psiDie += 2;
                    }
                    case 2 -> {{
                        result = myRand.nextInt(psiDie) + 1;
                        pointsMade += result;
                        numOfRolls++;
                        numOfRolls_d2++;
                        }
                        if (result == 2) psiDie = 0;
                        if (result == 1 && include_d4) psiDie += 2;
                    }
                }
            } while (psiDie > 0);
            rollsList.add(numOfRolls);
            pointList.add(pointsMade);
            d12_List.add(numOfRolls_d12);
            d10_List.add(numOfRolls_d10);
            d8_List.add(numOfRolls_d8);
            d6_List.add(numOfRolls_d6);
            d4_List.add(numOfRolls_d4);
            d2_List.add(numOfRolls_d2);
            if (i == 0){ lowestNumOfRolls = numOfRolls; highestNumOfRolls = numOfRolls;}
            if (numOfRolls < lowestNumOfRolls) lowestNumOfRolls = numOfRolls;
            if (numOfRolls > highestNumOfRolls) highestNumOfRolls = numOfRolls;
            totalRolls += numOfRolls;
            totalPoints += pointsMade;
            total_d12s += numOfRolls_d12;
            total_d10s += numOfRolls_d10;
            total_d8s += numOfRolls_d8;
            total_d6s += numOfRolls_d6;
            total_d4s += numOfRolls_d4;
            total_d2s += numOfRolls_d2;
            numOfRolls = 0;
            pointsMade = 0;
            numOfRolls_d12 = 0;
            numOfRolls_d10 = 0;
            numOfRolls_d8 = 0;
            numOfRolls_d6 = 0;
            numOfRolls_d4 = 0;
            numOfRolls_d2 = 0;
        }
    }

    public static void printListInfo(ArrayList<Integer> list){
        System.out.println("Lowest: " + list.get(0));
        System.out.println("Highest: " + list.get(list.size() - 1));
        System.out.println("Median: " + ListUtils.getListMedian(list));
        System.out.println("Average: " + Math.round(ListUtils.getListAverage(list)));
    }
    public static void printListInfoDice(ArrayList<Integer> list){
        System.out.println("Percentage: " + Math.round(ListUtils.getListAverage(list)/ListUtils.getListAverage(rollsList)*100) + "%");
        System.out.println("Median: " + ListUtils.getListMedian(list));
        System.out.println("Average: " + Math.round(ListUtils.getListAverage(list)));
    }
}