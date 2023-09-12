import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PsidieOld {
    public static int psidieOriginal = 12;
    static int result = 0;
    static int amount = 0;
    static int psidie = psidieOriginal;
    static int pointsMade = 0;
    static double total = 0;
    static double average;
    static double median;
    static int attemptedTotal = 10000;
    static double lowest = 0;
    static double highest = 0;
    static ArrayList<Double> rollsList = new ArrayList<>();
    static ArrayList<Double> pointList = new ArrayList<>();
    static double totalPoints = 0;
    static double averagePoints = 0;
    static Random myRand = new Random();

    public static void main(String[] args) {


        for (int i = 0; i < attemptedTotal; i++) {
            psidie = psidieOriginal;
            do {
                switch (psidie) {
                    case 12 -> {
                        result = myRand.nextInt(psidie) + 1;
                        pointsMade += result;
                        totalPoints += result;
                        amount++;
                        if (result == 6) psidie = 10;
                    }
                    case 10 -> {{
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            totalPoints += result;
                            amount++;
                            if (result == 5) psidie = 8;
                        }
                        if (result == 1) psidie += 2;
                    }
                    case 8 -> {{
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            totalPoints += result;
                            amount++;
                            if (result == 4) psidie -= 2;
                        }
                        if (result == 1) psidie += 2;
                    }
                    case 6 -> {{
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            totalPoints += result;
                            amount++;
                            if (result == 3) psidie -= 2;
                        }
                        if (result == 1) psidie += 2;
                    }
                    case 4 -> {{
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            totalPoints += result;
                            amount++;
                            if (result == 2) psidie = 0;
                        }
                        if (result == 1) psidie += 2;
                    }
                    case 2 -> {{
                            result = myRand.nextInt(psidie) + 1;
                            pointsMade += result;
                            totalPoints += result;
                            amount++;
                            if (result == 2) psidie = 0;
                        }
                        if (result == 1) psidie += 2;
                    }
                    default -> System.out.println("Error " + i);
                }
            } while (psidie > 0);
            rollsList.add((double)amount);
            pointList.add((double)pointsMade);
            if (i == 0){
                lowest = amount;
                highest = amount;
            }
            if (amount < lowest) lowest = amount;
            if (amount > highest) highest = amount;
            total += amount;
            amount = 0;
            pointsMade = 0;
        }
        average = total /(double)attemptedTotal;
        averagePoints = totalPoints / (double)attemptedTotal;

        Collections.sort(rollsList);
        Collections.sort(pointList);
        //System.out.println(pointList);
        System.out.println("Average: " + average);
        rollMedian();
        System.out.println("Highest: " + highest);
        System.out.println("Lowest: " + lowest);
        System.out.println("Total Points on average: " + averagePoints);

        // remove 25% of top and bottom and find mean
        trimAverageRolls();

        // remove 25% of top and bottom and find median
        trimMedianRolls();

        // remove 25% of top and bottom and find mean for result
        trimAveragePoints();

        // trim 25% of top and bottom to remove outliers and find median for pointsList
        trimMedianPoints();

        //create chart for rolls
        createRollChart();

        //create chart for points
        createPointChart();
    }

    private static void trimMedianPoints() {
        int pointListSizeOrig = pointList.size();
        //Trims
        for(int i = 0; i < pointListSizeOrig*0.25; i++){
            //rollsList.remove(0);
            pointList.remove(pointList.size()-1);
        }

        totalPoints = 0;
        highest = 0;
        lowest = 100;
        for (Double aDouble : pointList) {
            totalPoints = totalPoints + aDouble;

            if (aDouble < lowest)
                lowest = aDouble;
            if (aDouble > highest)
                highest = aDouble;
        }
        System.out.println("Points Trimmed Median: " + averagePoints);
    }

    private static void trimMedianRolls() {
        int rollsListSizeOrig = rollsList.size();
        //Trims
        for(int i = 0; i < rollsListSizeOrig*0.25; i++){
            //rollsList.remove(0);
            rollsList.remove(rollsList.size()-1);
        }

        //find the median of rollsList
        if (rollsList.size() % 2 == 0)
            median = ((double)rollsList.get(rollsList.size()/2) + (double)rollsList.get(rollsList.size()/2 - 1))/2;
        else
            median = (double) rollsList.get(rollsList.size()/2);

        System.out.println("Rolls Trimmed Median: " + median);
    }

    private static void rollMedian() {
        if (rollsList.size() % 2 == 0)
            median = ((double)rollsList.get(rollsList.size()/2) + (double)rollsList.get(rollsList.size()/2 - 1))/2;
        else
            median = (double) rollsList.get(rollsList.size()/2);
        System.out.println("Median: " + median);
    }

    public static void trimAverageRolls(){
        int rollsListSizeOrig = rollsList.size();
        //Trims
        for(int i = 0; i < rollsListSizeOrig*0.25; i++){
            //rollsList.remove(0);
            rollsList.remove(rollsList.size()-1);
        }

        total = 0;
        highest = 0;
        lowest = 100;
        for(int i = 0; i < rollsList.size(); i++){
            total = total + rollsList.get(i);

            if (rollsList.get(i) < lowest)
                lowest = rollsList.get(i);
            if (rollsList.get(i) > highest)
                highest = rollsList.get(i);
        }

        average = (double) total / rollsList.size();

        System.out.println("Rolls Trimmed Average: " + average);
        System.out.println("Rolls Trimmed Highest: " + highest);
        System.out.println("Rolls Trimmed Lowest: " + lowest);
    }

    public static void trimAveragePoints(){
        int pointListSizeOrig = pointList.size();
        //Trims
        for(int i = 0; i < pointListSizeOrig*0.25; i++){
            //pointList.remove(0);
            pointList.remove(pointList.size()-1);
        }

        total = 0;
        highest = 0;
        lowest = 100;
        for(int i = 0; i < pointList.size(); i++){
            total = total + pointList.get(i);

            if (pointList.get(i) < lowest)
                lowest = pointList.get(i);
            if (pointList.get(i) > highest)
                highest = pointList.get(i);
        }

        average = (double) total / pointList.size();

        System.out.println("Points Trimmed Average: " + average);
        System.out.println("Points Trimmed Highest: " + highest);
        System.out.println("Points Trimmed Lowest: " + lowest);
    }

    public static void createRollChart(){
        double[] valuesRolls = new double[rollsList.size()];
        for (int i = 0; i < rollsList.size(); i++) {valuesRolls[i] = rollsList.get(i);}
        int binny = (int)Math.round(Collections.max(rollsList));
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("key", valuesRolls, binny);

        JFreeChart histogram = ChartFactory.createHistogram("Psidie","Rolls","Frequency",dataset, PlotOrientation.VERTICAL,false,false,false);
        try {

            ChartUtils.saveChartAsPNG(new File("C://Users//Erick Sanchez//Desktop//amountOfRolls.png"), histogram, 600, 400);
        }catch(IOException e) {}
    }

    public static void createPointChart(){
        double[] valuesPoints = new double[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {valuesPoints[i] = pointList.get(i);}
        int binny = (int)Math.round(Collections.max(pointList));
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("key", valuesPoints, binny);

        JFreeChart histogram = ChartFactory.createHistogram("Psidie","Points","Frequency",dataset, PlotOrientation.VERTICAL,false,false,false);
        try {
            ChartUtils.saveChartAsPNG(new File("C://Users//Erick Sanchez//Desktop//amountOfPoints.png"), histogram, 600, 400);
        }catch(IOException e) {}
    }
}
