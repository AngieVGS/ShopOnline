package views;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import models.entity.Purchase;

public class PanelPointsGraphic extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<Purchase> purchases;
	public static final int MINUTES_PER_HOUR = 60;

	public PanelPointsGraphic(ArrayList<Purchase> purchase) {
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
		setSize(700, 300);
		setTitle("Plot Chart");
		setIconImage(new ImageIcon(getClass().getResource(Constants.PLOT_CHART_ICON_PATH)).getImage());

		purchases = purchase;
		XYDataset xydataset = XYDataset();
		JFreeChart jfreechart = ChartFactory.createXYLineChart("Purchases per minute", "Minute", "Purchases", xydataset,
				PlotOrientation.VERTICAL, true, true, false);

		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setBackgroundPaint(Color.WHITE);
		xyplot.setDomainGridlinePaint(Color.BLACK);
		xyplot.setRangeGridlinePaint(Color.BLACK);

		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
		xylineandshaperenderer.setBaseShapesVisible(true);

		XYItemLabelGenerator xy = new StandardXYItemLabelGenerator();
		xylineandshaperenderer.setBaseItemLabelGenerator(xy);
		xylineandshaperenderer.setBaseItemLabelsVisible(true);
		xylineandshaperenderer.setBaseLinesVisible(true);
		xylineandshaperenderer.setBaseItemLabelsVisible(true);

		ChartPanel chartPanel = new ChartPanel(jfreechart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		setContentPane(chartPanel);

		setVisible(true);
	}

	@SuppressWarnings("static-access")
	private int calculatePurchasesPerMinute(int minute) {
		int purchasesOnThisMinute = 0;
		for (Purchase purchase : purchases) {
			if (purchase.getDate().MINUTE == minute) {
				purchasesOnThisMinute++;
			}
		}
		return purchasesOnThisMinute;
	}

	private XYDataset XYDataset() {
		int minute = 1;
		XYSeries sPurchases = new XYSeries("Purchases");
		while (minute < MINUTES_PER_HOUR) {
			int purchases = calculatePurchasesPerMinute(minute);
			sPurchases.add(minute, purchases);
			minute++;
		}
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(sPurchases);
		return xyseriescollection;
	}
}