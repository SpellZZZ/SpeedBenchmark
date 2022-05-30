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

import javax.swing.*;
import java.awt.*;


public class Chart extends JFrame {

    private static final XYSeries  series1 = new XYSeries("Line");


    public static ChartPanel initUI()  {


        XYSeriesCollection datasetXY = new XYSeriesCollection();
        datasetXY.addSeries(series1);

        JFreeChart chart = createChart(datasetXY);

        return new ChartPanel(chart);
    }


    private static JFreeChart createChart(final XYDataset dataset) {

        //chart
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

        chartStyle(chart);



        //xy render
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderStyle(renderer);

        //xyplot
        XYPlot plot = chart.getXYPlot();
        plotStyle(plot);
        plot.setRenderer(renderer);





        return chart;
    }


    private static void chartStyle(JFreeChart chart){
        chart.setTitle(new TextTitle("", new Font("Serif", Font.BOLD, 18)));
        chart.setBackgroundPaint(new Color(38, 35, 53));

        chart.getXYPlot().getDomainAxis().setLabelFont(new Font("Dialog", Font.BOLD, 18));
        chart.getXYPlot().getDomainAxis().setLabelPaint(new Color(	111, 138, 233));
        chart.getXYPlot().getDomainAxis().setTickLabelFont(new Font("Dialog", Font.BOLD, 12));
        chart.getXYPlot().getDomainAxis().setTickLabelPaint(new Color(105, 75, 107));


        chart.getXYPlot().getRangeAxis().setLabelFont(new Font("Dialog", Font.BOLD, 18));
        chart.getXYPlot().getRangeAxis().setLabelPaint(new Color(	111, 138, 233));
        chart.getXYPlot().getRangeAxis().setTickLabelFont(new Font("Dialog", Font.BOLD, 12));
        chart.getXYPlot().getRangeAxis().setTickLabelPaint(new Color(105, 75, 107));






    }
    private  static void renderStyle(XYLineAndShapeRenderer renderer){
        renderer.setSeriesPaint(0, new Color(	222, 68, 80));
        renderer.setSeriesStroke(0, new BasicStroke(1));

    }

    private  static void plotStyle(XYPlot plot){
        plot.setBackgroundPaint(new Color(36, 24, 38));
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);
    }




    public static void addValue(double bpm, double time) {
        //if(bpm <400 ) series1.add(time,bpm);
        series1.add(time,bpm);
    }

    public static void clear() {
        series1.clear();
    }







}