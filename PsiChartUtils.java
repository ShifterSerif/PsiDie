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
        ListUtils.trimList(PsiDie.totalRolls, PsiDie.trimAmount);
        double[] valuesRolls = new double[PsiDie.totalRolls.size()];
        for (int i = 0; i < PsiDie.totalRolls.size(); i++) {valuesRolls[i] = PsiDie.totalRolls.get(i);}
        int binny = Collections.max(PsiDie.totalRolls);
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
        dataset.add(PsiDie.totalRolls, "Series", " total");
        if(PsiDie.include_d2) dataset.add(PsiDie.totalRolls_d2, "Series", " d2");
        if(PsiDie.include_d4) dataset.add(PsiDie.totalRolls_d4, "Series", " d4");
        if(PsiDie.include_d6) dataset.add(PsiDie.totalRolls_d6, "Series", " d6");
        if(PsiDie.include_d8) dataset.add(PsiDie.totalRolls_d8, "Series", " d8");
        if(PsiDie.include_d10) dataset.add(PsiDie.totalRolls_d10, "Series", " d10");
        if(PsiDie.include_d12) dataset.add(PsiDie.totalRolls_d12, "Series", " d12");
        if(PsiDie.include_d20) dataset.add(PsiDie.totalRolls_d20, "Series", " d20");

        final CategoryAxis xAxis = new CategoryAxis("Type");
        final NumberAxis yAxis = new NumberAxis("Value");
        yAxis.setAutoRangeIncludesZero(false);
        final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
        renderer.setFillBox(false);
        renderer.setDefaultToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
        final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);

        final JFreeChart chart = new JFreeChart("Box-and-Whisker PsiDie", new Font("SansSerif", Font.BOLD,
                14), plot, false);

        try {
            org.jfree.chart.ChartUtils.saveChartAsPNG(new File("C://Users//Erick Sanchez//Desktop//amountOfRollsBox.jpg"),
                    chart, 600, 400);
        }catch(IOException ignored) {}
        //System.out.println("Box Plot created");
    }
}
