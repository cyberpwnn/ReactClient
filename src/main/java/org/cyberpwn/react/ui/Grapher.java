package org.cyberpwn.react.ui;

import org.cyberpwn.react.util.Average;
import org.cyberpwn.react.util.GList;

import javax.swing.*;
import java.awt.*;

public class Grapher extends JPanel {
    private static final long serialVersionUID = 1L;
    private int min;
    private boolean rcColor;
    private int max;
    private Color color;
    private GList<Double> data;
    private boolean stack;
    private boolean steg = false;

    public Grapher(int max, Color color, GList<Double> data) {
        if (max == 1) {
            steg = true;
        }

        this.color = color;
        this.max = max;
        this.data = data;
        rcColor = false;
        stack = false;
    }

    public Grapher(int max, Color color, GList<Double> data, boolean stack) {
        this(max, color, data);
        this.stack = stack;
    }

    public int posRaw(Double d) {
        return getHeight() - (int) ((d / (double) max) * (double) getHeight());
    }

    public int pos(Double double1) {
        int y = posRaw(double1);

        if (y <= 0) {
            y = 20;
        }

        if (y >= getHeight()) {
            y = getHeight() - 2;
        }

        return y;
    }

    public void render(Graphics gg, int vis, Color c) {
        Graphics2D g = (Graphics2D) gg;
        g.setStroke(new BasicStroke(0.2f));

        if (data.isEmpty()) {
            return;
        }

        GList<Double> data = new GList<>();

        double m = 100;

        if (this.data.size() > vis) {
            for (int i = 0; i < this.data.size(); i++) {
                if (this.data.size() > i + vis) {
                    Average a = new Average(i);

                    for (int j = i; j < i + vis; j++) {
                        a.put(this.data.get(j) - 0.001);
                    }

                    data.add(a.getAverage());

                    if (a.getAverage() > m) {
                        m = a.getAverage();
                    }
                } else {
                    data.add(this.data.get(i));
                }
            }
        }

        if (steg) {
            if (max > m) {
                max -= ((max - m) / 12) - (m / 20);
            }

            if (max < m) {
                max += ((m - max) / 12) + (m / 20);
            }
        }

        g.setColor(c);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int x = 0;
        int ly = 0;

        g.setColor(Color.DARK_GRAY.brighter());

        for (int i = x; i < getWidth(); i += getWidth() / 10) {
            for (int j = ly; j < getHeight(); j++) {
                g.drawRect(i, j, 1, 1);
            }
        }

        for (int i = ly; i < getHeight(); i += getHeight() / 10) {
            for (int j = x; j < getWidth(); j++) {
                g.drawRect(j, i, 1, 1);
            }
        }

        g.setColor(c);
        g.setStroke(new BasicStroke(2.0f));

        for (int i = x; i < getWidth(); i++) {
            g.setColor(color);

            if (data.size() > i) {
                if (data.size() - i < vis + 8) {
                    g.setStroke(new BasicStroke(0.1f));
                } else {
                    g.setStroke(new BasicStroke(2.0f));
                }

                if (stack) {
                    g.drawRect(i, pos(data.get(i)) - 12, 1, 12);
                } else {
                    g.drawRect(i, pos(data.get(i)), 1, 1);
                }

                if (i > 0) {
                    if (pos(data.get(i)) > ly && ly > 0) {
                        for (int j = ly; j < pos(data.get(i)); j++) {
                            g.drawRect(i, j, 1, 1);
                        }
                    } else if (pos(data.get(i)) < ly) {
                        for (int j = ly; j > pos(data.get(i)); j--) {
                            g.drawRect(i, j, 1, 1);
                        }
                    }
                }

                ly = pos(data.get(i));

                if (stack) {
                    for (int j = ly; j < getHeight(); j++) {
                        g.setColor(color.darker());
                        g.drawRect(i, j, 1, 1);
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics gg) {
        super.paintComponent(gg);
        render(gg, 64, color);
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public GList<Double> getData() {
        return data;
    }

    public void setData(GList<Double> data) {
        this.data = data;
    }

    public boolean isRcColor() {
        return rcColor;
    }

    public void setRcColor(boolean rcColor) {
        this.rcColor = rcColor;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}