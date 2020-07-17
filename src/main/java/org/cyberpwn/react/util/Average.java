package org.cyberpwn.react.util;

public class Average {
    private int limit;
    private GList<Double> data;
    private double average;

    public Average(int limit) {
        this.limit = limit;
        this.data = new GList<Double>();
        this.average = 0.0;
    }

    public void put(double d) {
        data.add(d);
        M.lim(data, limit);
        average = M.avg(data);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public GList<Double> getData() {
        return data;
    }

    public void setData(GList<Double> data) {
        this.data = data;
    }

    public double getAverage() {
        return average;
    }
}
