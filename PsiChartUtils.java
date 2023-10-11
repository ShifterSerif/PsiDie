import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PsiChartUtils {
    static ArrayList<Short> totalRollsList = new ArrayList<>(), totalRolls_d20List = new ArrayList<>(),
            totalRolls_d12List = new ArrayList<>(), totalRolls_d10List = new ArrayList<>(),
            totalRolls_d8List = new ArrayList<>(), totalRolls_d6List = new ArrayList<>(),
            totalRolls_d4List = new ArrayList<>(), totalRolls_d2List = new ArrayList<>();
    static int chartWidth = 1200, chartHeight = 800;

    public static void createRollChart(){
        int trimmedLength = (int) (PsiDie.totalRolls.length * (PsiDie.trimAmount/100));
        double[] valuesRolls = new double[(PsiDie.totalRolls.length - (trimmedLength * 2))];
        for (int i = 0; i < (PsiDie.totalRolls.length - (trimmedLength * 2)); i++) {
            valuesRolls[i] = PsiDie.totalRolls[i + trimmedLength];
        }
        int numOfBars = 18;
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("key", valuesRolls, numOfBars);

        JFreeChart histogram = ChartFactory.createHistogram("Histogram PsiDie","Rolls",
                "Frequency", dataset, PlotOrientation.VERTICAL,false,false,false);
        try {
            org.jfree.chart.ChartUtils.saveChartAsPNG(
                    new File("C://Users//Erick Sanchez//Desktop//amountOfRolls.jpg"),
                    //new File("C://Users//Shifter//Desktop//amountOfRolls.jpg"),
                    histogram, chartWidth, chartHeight);
        }catch(IOException ignored) {}
    }
    public static void createBoxPlot() {
        ArrayToList();

        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        dataset.add(totalRollsList, "Series", " total");
        if(PsiDie.include_d2) dataset.add(totalRolls_d2List, "Series", " d2");
        if(PsiDie.include_d4) dataset.add(totalRolls_d4List, "Series", " d4");
        if(PsiDie.include_d6) dataset.add(totalRolls_d6List, "Series", " d6");
        if(PsiDie.include_d8) dataset.add(totalRolls_d8List, "Series", " d8");
        if(PsiDie.include_d10) dataset.add(totalRolls_d10List, "Series", " d10");
        if(PsiDie.include_d12) dataset.add(totalRolls_d12List, "Series", " d12");
        if(PsiDie.include_d20) dataset.add(totalRolls_d20List, "Series", " d20");

        final CategoryAxis xAxis = new CategoryAxis("Type");
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setDefaultToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart("Box-and-Whisker PsiDie", new Font("SansSerif",
                Font.BOLD, 14), plot, false);

        try {
            org.jfree.chart.ChartUtils.saveChartAsPNG(
                    new File("C://Users//Erick Sanchez//Desktop//amountOfRollsBox.jpg"),
                    //new File("C://Users//Shifter//Desktop//amountOfBoxRollsBox.jpg"),
                    chart, chartWidth, chartHeight);
        }catch(IOException ignored) {System.out.println("BoxPlot Exception");}
    }
    public static void ArrayToList(){
        for (int i = 0; i < PsiDie.totalRolls.length; i++) {
            totalRollsList.add(PsiDie.totalRolls[i]);
        }
        if (PsiDie.include_d20) for (int i = 0; i < PsiDie.totalRolls_d20.length; i++) {
            totalRolls_d20List.add(PsiDie.totalRolls_d20[i]);
        }
        if (PsiDie.include_d12) for (int i = 0; i < PsiDie.totalRolls_d12.length; i++) {
            totalRolls_d12List.add(PsiDie.totalRolls_d12[i]);
        }
        if (PsiDie.include_d10) for (int i = 0; i < PsiDie.totalRolls_d10.length; i++) {
            totalRolls_d10List.add(PsiDie.totalRolls_d10[i]);
        }
        if (PsiDie.include_d8) for (int i = 0; i < PsiDie.totalRolls_d8.length; i++) {
            totalRolls_d8List.add(PsiDie.totalRolls_d8[i]);
        }
        if (PsiDie.include_d6) for (int i = 0; i < PsiDie.totalRolls_d6.length; i++) {
            totalRolls_d6List.add(PsiDie.totalRolls_d6[i]);
        }
        if (PsiDie.include_d4) for (int i = 0; i < PsiDie.totalRolls_d4.length; i++) {
            totalRolls_d4List.add(PsiDie.totalRolls_d4[i]);
        }
        if (PsiDie.include_d2) for (int i = 0; i < PsiDie.totalRolls_d2.length; i++) {
            totalRolls_d2List.add(PsiDie.totalRolls_d2[i]);
        }
    }
}
