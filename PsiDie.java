import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

//Ideas - Psi die goes down/ up if it equals your remaining amount of focus points
//        using it to use amp without losing focus point?
//        circumstance or status bonus?
//        Psyche and/or amp sets it to max?

public class PsiDie {
    static int numOfRolls, numOfRolls_d12, numOfRolls_d10, numOfRolls_d8, numOfRolls_d6, numOfRolls_d4,
            numOfRolls_d2, result, pointsMade, lowestNumOfRolls, highestNumOfRolls = 0;
    static ArrayList<Integer> total_Rolls = new ArrayList<>();
    static ArrayList<Integer> points = new ArrayList<>();
    static ArrayList<Integer> total_d12 = new ArrayList<>();
    static ArrayList<Integer> total_d10 = new ArrayList<>();
    static ArrayList<Integer> total_d8 = new ArrayList<>();
    static ArrayList<Integer> total_d6 = new ArrayList<>();
    static ArrayList<Integer> total_d4 = new ArrayList<>();
    static ArrayList<Integer> total_d2 = new ArrayList<>();
    static Random myRand = new Random();
        static int testRuns = 50_000;
        static int trimAmount = 10;
        public static int initialDieSize = 8;
        static int psiDie = initialDieSize;
        static boolean include_d12 = false;
        static boolean include_d10 = false;
        static boolean include_d8 = true;
        static boolean include_d6 = true;
        static boolean include_d4 = true;
        static boolean include_d2 = false;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        testDice();

        processTotal(total_Rolls, "Rolls");
        processTotal(points, "Points");
        if(include_d2){processDice(total_d2, "d2");}
        if(include_d4){processDice(total_d4, "d4");}
        if(include_d6){processDice(total_d6, "d6");}
        if(include_d8){processDice(total_d8, "d8");}
        if(include_d10){processDice(total_d10, "d10");}
        if(include_d12){processDice(total_d12, "d12");}

        PsiChartUtils.createRollChart();
        PsiChartUtils.createBoxPlot();

        System.out.println("\n"+(System.nanoTime() - startTime) / 1_000_000_000 + "s");
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
            total_Rolls.add(numOfRolls);
            total_d12.add(numOfRolls_d12);
            total_d10.add(numOfRolls_d10);
            total_d8.add(numOfRolls_d8);
            total_d6.add(numOfRolls_d6);
            total_d4.add(numOfRolls_d4);
            total_d2.add(numOfRolls_d2);
            points.add(pointsMade);
            if (i == 0){ lowestNumOfRolls = numOfRolls; highestNumOfRolls = numOfRolls;}
            if (numOfRolls < lowestNumOfRolls) lowestNumOfRolls = numOfRolls;
            if (numOfRolls > highestNumOfRolls) highestNumOfRolls = numOfRolls;
            numOfRolls = pointsMade = numOfRolls_d12 = numOfRolls_d10 = numOfRolls_d8 = numOfRolls_d6 =
                    numOfRolls_d4 = numOfRolls_d2 = 0;
        }
    }

    public static void processTotal(ArrayList<Integer> list, String name){
        Collections.sort(list);
        ListUtils.winsorizeList(list, trimAmount);
        System.out.println("------" + name + " List------");
        System.out.println("Lowest: " + list.get(0));
        System.out.println("Highest: " + list.get(list.size() - 1));
        System.out.println("Median: " + ListUtils.getListMedian(list));
        System.out.println("Average: " + Math.round(ListUtils.getListAverage(list)));
    }

    public static void processDice(ArrayList<Integer> list, String name){
        Collections.sort(list);
        ListUtils.winsorizeList(list, trimAmount);
        System.out.println("------" + name + " List------");
        System.out.println("Percentage: " + Math.round(ListUtils.getListAverage(list)/
                ListUtils.getListAverage(total_Rolls)*100) + "%");
        System.out.println("Median: " + ListUtils.getListMedian(list));
        System.out.println("Average: " + Math.round(ListUtils.getListAverage(list)));
    }
}