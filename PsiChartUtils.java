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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class PsiChartUtils {
    public static void createRollChart(){
        ListUtils.trimList(PsiDie.rollsList, PsiDie.trimAmount);
        double[] valuesRolls = new double[PsiDie.rollsList.size()];
        for (int i = 0; i < PsiDie.rollsList.size(); i++) {valuesRolls[i] = PsiDie.rollsList.get(i);}
        int binny = Collections.max(PsiDie.rollsList);
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("key", valuesRolls, binny);

        JFreeChart histogram = ChartFactory.createHistogram("Histogram PsiDie","Rolls","Frequency",
                dataset, PlotOrientation.VERTICAL,false,false,false);
        try {
            org.jfree.chart.ChartUtils.saveChartAsPNG(new File("C://Users//Erick Sanchez//Desktop//amountOfRolls.jpg"),
                    histogram, 600, 400);
        }catch(IOException ignored) {}
        //System.out.println("Histogram created");
    }
    public static void createBoxPlot() {
        final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
        dataset.add(PsiDie.rollsList, "Series", " total");
        dataset.add(PsiDie.d2_List, "Series", " d2");
        dataset.add(PsiDie.d4_List, "Series", " d4");
        dataset.add(PsiDie.d6_List, "Series", " d6");
        dataset.add(PsiDie.d8_List, "Series", " d8");
        dataset.add(PsiDie.d10_List, "Series", " d10");
        dataset.add(PsiDie.d12_List, "Series", " d12");

        final CategoryAxis xAxis = new CategoryAxis("Type");
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setDefaultToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart(
                "Box-and-Whisker PsiDie",
                new Font("SansSerif", Font.BOLD, 14),
                plot,
                false
        );

        try {
            org.jfree.chart.ChartUtils.saveChartAsPNG(new File("C://Users//Erick Sanchez//Desktop//amountOfRollsBox.jpg"),
                    chart, 600, 400);
        }catch(IOException ignored) {}
        //System.out.println("Box Plot created");
    }
}
