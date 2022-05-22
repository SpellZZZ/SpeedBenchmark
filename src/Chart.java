
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author imssbora
 *

public class Chart extends JFrame {

    private static final long serialVersionUID = 1L;

    public Chart(String title) {
        super(title);
        // Create dataset
        XYDataset dataset = createDataset();
        // Create chart
        JFreeChart chart = ChartFactory.createXYStepChart(
                "XY Step Chart | WWW.BORAJI.COM", // Chart title
                "X-Axis", // X-Axis Label
                "Y-Axis", // Y-Axis Label
                dataset
        );

        XYPlot plot=(XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(255, 0, 0, 60));

        ChartPanel panel = new ChartPanel(chart);
        setContentPane(panel);
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        XYSeries series1 = new XYSeries("Series1");
        series1.add(2, 4);
        series1.add(3, 6);
        series1.add(5, 2);
        series1.add(8, 5);
        series1.add(1, 8);

        XYSeries series2 = new XYSeries("Series2");
        series2.add(5, 6);
        series2.add(9, 5);
        series2.add(10, 9);
        series2.add(18, 11);
        series2.add(15, 18);

        // Add series to dataset
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        return dataset;
    }

    public static void main2() {
        SwingUtilities.invokeLater(() -> {
            Chart example = new Chart("XY Step Chart Example");
            example.setSize(800, 400);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
} */