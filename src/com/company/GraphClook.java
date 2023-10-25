package com.company;

import javax.swing.*;
import javax.swing.JPanel;
import java.util.*;
import java.awt.*;



public class GraphClook extends JPanel {
    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 8;
    private int numberYDivisions = 10;
    private ArrayList<Integer> frag;
    private ArrayList<Integer> orderArr;
    int thm;
    private ArrayList<Integer> distances;



    public GraphClook(ArrayList<Integer> frag, ArrayList<Integer> orderArr, int thm, ArrayList<Integer> distances) {
        this.frag = frag;
        this.orderArr = orderArr;
        this.thm = thm;
        this.distances = distances;
    }

    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (frag.size() - 1);
        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / distances.size();

        ArrayList<Point> graphPoints = new ArrayList<>();
        for (int i = 0; i < orderArr.size(); i++) {
            int y1 = (int) (i * yScale + padding + labelPadding);
            int x1 = (int) ((orderArr.get(i)) * xScale + padding + labelPadding);
            graphPoints.add(new Point(x1, y1));
        }

        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding + labelPadding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2* padding - labelPadding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = -1; i <= distances.size(); i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = ((i * (getHeight() - padding * 2 - labelPadding)) / distances.size() + padding + labelPadding);
            int y1 = y0;

            g2.setColor(gridColor);
            g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
            g2.setColor(Color.BLACK);
            if (i > 0) {
                String yLabel = Integer.toString(distances.get(i-1));
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }


        // and for x axis
        for (int i = 0; i < frag.size(); i++) {
            if (frag.size() > 1) {
                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (frag.size()-1) + padding + labelPadding;
                int x1 = x0;
                int y0 = padding + labelPadding;
                int y1 = y0 - pointWidth;
                if ((i % ((int) ((frag.size() / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, getHeight() - padding - 1 - pointWidth, x1, padding + labelPadding);
                    g2.setColor(Color.BLACK);
                    String xLabel = frag.get(i) + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 - metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // create x and y axes
        g2.drawLine(padding + labelPadding, getHeight() - padding, padding + labelPadding, padding + labelPadding);
        g2.drawLine(padding + labelPadding, padding + labelPadding, getWidth() - padding, padding + labelPadding);


        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = graphPoints.get(i + 1).x;
            int y2 = graphPoints.get(i + 1).y;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }
        g2.setColor(Color.BLACK);
        //g2.drawString(" Distances: " + distances.toString(),2*padding+labelPadding,getHeight()-padding);
        g2.drawString(" Total Head Movement: " + thm,2*padding+labelPadding,getHeight()-(2*padding));

    }
    static void createAndShowGui(ArrayList<Integer> frag, ArrayList<Integer> orderArr, int thm, ArrayList<Integer> distances) {

        GraphClook mainPanel = new GraphClook(frag,orderArr,thm,distances);
        mainPanel.setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}