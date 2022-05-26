

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class Chart extends JFrame {

    static XYSeries  series1 = new XYSeries("Line");
    static XYSeriesCollection datasetXY;



    public static ChartPanel initUI()  {


        datasetXY = new XYSeriesCollection();
        datasetXY.addSeries(series1);

        XYDataset dataset = datasetXY;
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 1));
        chartPanel.setBackground(Color.white);

        return chartPanel;
    }



    private static JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Line chart",
                "Time",
                "BPM",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1));



        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);


        chart.setTitle(new TextTitle("Chart",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }



    public static void addValue(double bpm, double time) {

        if(bpm <400 ) series1.add(time,bpm);
    }

    public static void clear() {

        series1.clear();

    }


}
