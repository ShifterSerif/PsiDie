// import org.jfree.chart.ChartFactory;
// import org.jfree.chart.ChartUtils;
// import org.jfree.chart.JFreeChart;
// import org.jfree.chart.plot.PlotOrientation;
// import org.jfree.data.statistics.HistogramDataset;
// import org.jfree.data.statistics.HistogramType;

// import java.io.File;
// import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// import static java.lang.Math.round;

public class Psidie2 {
    static int result = 0;
    static int numOfRolls = 0;
    static int numOfd12 = 0;
    static int numOfd10 = 0;
    static int numOfd8 = 0;
    static int numOfd6 = 0;
    static int numOfd4 = 0;
    static int numOfd2 = 0;
    static int attemptedTotal = 500000;
    static int pointsMade = 0;
    static double totalRolls= 0;
    static double totald12 = 0;
    static double totald10 = 0;
    static double totald8 = 0;
    static double totald6 = 0;
    static double totald4 = 0;
    static double totald2 = 0;
    static double lowest = 0;
    static double highest = 0;
    static ArrayList<Integer> rollsList = new ArrayList<>();
    static ArrayList<Integer> pointList = new ArrayList<>();
    static ArrayList<Integer> d12List = new ArrayList<>();
    static ArrayList<Integer> d10List = new ArrayList<>();
    static ArrayList<Integer> d8List = new ArrayList<>();
    static ArrayList<Integer> d6List = new ArrayList<>();
    static ArrayList<Integer> d4List = new ArrayList<>();
    static ArrayList<Integer> d2List = new ArrayList<>();
    static double totalPoints = 0;
    static Random myRand = new Random();
    public static int psidieOriginal = 12;
    static int psidie = psidieOriginal;
    static boolean d12 = true;
    static boolean d10 = true;
    static boolean d8 = true;
    static boolean d6 = true;
    static boolean d4 = true;
    static boolean d2 = true;


    public static void main(String[] args) {
        testDice();

        Collections.sort(rollsList);
        trimList(rollsList, 5);
        System.out.println("------Rolls List------");
        printListInfo(rollsList);

//        Collections.sort(pointList);
//        trimList(pointList, 5);
//        System.out.println("------Points List------");
//        printListInfo(pointList);

        if(d2){
            Collections.sort(d2List);
            trimList(d2List, 5);
            System.out.println("------d2 List------");
            printListInfoDice(d2List);
        }
        if(d4){
            Collections.sort(d4List);
            trimList(d4List, 5);
            System.out.println("------d4 List------");
            printListInfoDice(d4List);
        }
        if(d6){
            Collections.sort(d6List);
            trimList(d6List, 5);
            System.out.println("------d6 List------");
            printListInfoDice(d6List);
        }
        if(d8){
            Collections.sort(d8List);
            trimList(d8List, 5);
            System.out.println("------d8 List------");
            printListInfoDice(d8List);
        }
        if(d10){
            Collections.sort(d10List);
            trimList(d10List, 5);
            System.out.println("------d10 List------");
            printListInfoDice(d10List);
        }
        if(d12){
            Collections.sort(d12List);
            trimList(d12List, 5);
            System.out.println("------d12 List------");
            printListInfoDice(d12List);
        }



        // createRollChart();
    }

    public static void testDice(){
        for (int i = 0; i < attemptedTotal; i++) {
            psidie = psidieOriginal;
            do {
                switch (psidie) {
                    case 12 -> {
                        {
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            numOfRolls++;
                            numOfd12++;
                        }
                        if (result == 6) psidie -= 2;
                    }
                    case 10 -> {
                        {
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            numOfRolls++;
                            numOfd10++;
                        }
                        if (result == 5 && d8) psidie -= 2;
                        if (result == 1 && d12) psidie += 2;
                    }
                    case 8 -> {
                        {
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            numOfRolls++;
                            numOfd8++;
                        }
                        if (result == 4 && d6) psidie -= 2;
                        if (result == 1 && d10) psidie += 2;
                    }
                    case 6 -> {
                        {
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            numOfRolls++;
                            numOfd6++;
                        }
                        if (result == 3 && d4) psidie -= 2;
                        if (result == 1 && d8) psidie += 2;
                    }
                    case 4 -> {
                        {
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            numOfRolls++;
                            numOfd4++;
                        }
                        if (result == 2 && d2) psidie = 2;
                        else if (result == 2) psidie = 0;
                        if (result == 1 && d6) psidie += 2;
                    }
                    case 2 -> {
                        {
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            numOfRolls++;
                            numOfd2++;
                        }
                        if (result == 2) psidie = 0;
                        if (result == 1 && d4) psidie += 2;
                    }
                    default -> System.out.println("Error " + i);
                }
            } while (psidie > 0);
            rollsList.add(numOfRolls);
            pointList.add(pointsMade);
            d12List.add(numOfd12);
            d10List.add(numOfd10);
            d8List.add(numOfd8);
            d6List.add(numOfd6);
            d4List.add(numOfd4);
            d2List.add(numOfd2);
            if (i == 0){
                lowest = numOfRolls;
                highest = numOfRolls;
            }
            if (numOfRolls < lowest) lowest = numOfRolls;
            if (numOfRolls > highest) highest = numOfRolls;
            totalRolls += numOfRolls;
            totalPoints += pointsMade;
            totald12 += numOfd12;
            totald10 += numOfd10;
            totald8 += numOfd8;
            totald6 += numOfd6;
            totald4 += numOfd4;
            totald2 += numOfd2;
            numOfRolls = 0;
            pointsMade = 0;
            numOfd12 = 0;
            numOfd10 = 0;
            numOfd8 = 0;
            numOfd6 = 0;
            numOfd4 = 0;
            numOfd2 = 0;
        }
    }
    public static void trimList(ArrayList<Integer> list, double trimPercent){
        int trim = (int) (list.size() * (trimPercent / 100));
        for (int i = 0; i < trim; i++) {
            list.remove(0);
            list.remove(list.size() - 1);
        }
    }
    public static int getListMedian(ArrayList<Integer> list){
        if (list.size() % 2 == 0){
            return (list.get(list.size() / 2) + list.get((list.size() / 2) - 1)) / 2;
        } else {
            return list.get(list.size() / 2);
        }
    }
    public static double getListTotal(ArrayList<Integer> list) {
        double total = 0;
        for (double d : list) {
            total += d;
        }
        return total;
    }
    public static double getListAverage(ArrayList<Integer> list) {
        return getListTotal(list) / list.size();
    }
    public static void printListInfo(ArrayList<Integer> list){
        System.out.println("Lowest: " + list.get(0));
        System.out.println("Highest: " + list.get(list.size() - 1));
        System.out.println("Median: " + getListMedian(list));
        System.out.println("Average: " + round(getListAverage(list),2));
    }
    public static void printListInfoDice(ArrayList<Integer> list){
        System.out.println("Percentage: " + round((double)getListAverage(list)/getListAverage(rollsList)*100,2) + "%");
        System.out.println("Median: " + getListMedian(list));
        System.out.println("Average: " + round(getListAverage(list),2));
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    // public static void createRollChart(){
    //     double[] valuesRolls = new double[rollsList.size()];
    //     for (int i = 0; i < rollsList.size(); i++) {valuesRolls[i] = rollsList.get(i);}
    //     int binny = (int)Math.round(Collections.max(rollsList));
    //     HistogramDataset dataset = new HistogramDataset();
    //     dataset.setType(HistogramType.RELATIVE_FREQUENCY);
    //     dataset.addSeries("key", valuesRolls, binny);

    //     JFreeChart histogram = ChartFactory.createHistogram("Psidie","Rolls","Frequency",dataset, PlotOrientation.VERTICAL,false,false,false);
    //     try {
    //         ChartUtils.saveChartAsPNG(new File("C://Users//Erick Sanchez//Desktop//amountOfRolls.jpg"), histogram, 600, 400);
    //     }catch(IOException ignored) {}
    // }

}
