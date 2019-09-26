package ru.ssau.tk.sergunin.lab.functions;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {
    private double[] xValues;
    private double[] yValues;
    private int count;

    ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        if (xValues.length <= 2 || xValues.length != yValues.length)
            throw new IllegalArgumentException("less than minimum length");
        this.count = xValues.length;
        this.xValues = Arrays.copyOf(xValues, count);
        this.yValues = Arrays.copyOf(yValues, count);
    }

    ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        if(count<=2)throw new IllegalArgumentException("less than minimum length");
        this.count = count;
        if (xFrom > xTo) {
            xFrom = xFrom - xTo;
            xTo = xFrom + xTo;
            xFrom = -xFrom + xTo;
        }
        xValues = new double[count];
        yValues = new double[count];
        double buff = xFrom;
        double step = (xTo - xFrom) / (count - 1);
        if (xFrom != xTo) {
            for (int i = 0; i < count; i++) {
                xValues[i] = buff;
                yValues[i] = source.apply(buff);
                buff += step;
            }
        } else {
            double funcXFrom = source.apply(xFrom);
            for (int i = 0; i < count; i++) {
                xValues[i] = xFrom;
                yValues[i] = funcXFrom;
                buff += step;
            }
        }
    }

    @Override
    protected int floorIndexOfX(double x) {
        int i;
        if (x < leftBound()) {
            throw new IllegalArgumentException("ErrorfloorIndexOfX");
        }
        for (i = 0; i < count; i++) {
            if (xValues[i] > x) {
                return i - 1;
            }
        }
        return count;
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, xValues[0], xValues[1], yValues[0], yValues[1]);
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, xValues[count - 2], xValues[count - 1], yValues[count - 2], yValues[count - 1]);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if (count == 1) {
            return x;
        }
        return interpolate(x, xValues[floorIndex], xValues[floorIndex + 1], yValues[floorIndex], yValues[floorIndex + 1]);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        checkOutOfBounds(index);
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        checkOutOfBounds(index);
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        checkOutOfBounds(index);
        yValues[index] = value;
    }

    @Override
    public int indexOfX(double x) {
        int i;
        for (i = 0; i < count; i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        int i;
        for (i = 0; i < count; i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    public void insert(double x, double y) {
        if (indexOfX(x) != -1) {
            setY(indexOfX(x), y);
        } else {
            int index = floorIndexOfX(x);
            double[] xTmp = new double[count + 1];
            double[] yTmp = new double[count + 1];
            if (index == 0) {
                xTmp[0] = x;
                yTmp[0] = y;
                System.arraycopy(xValues, 0, xTmp, 1, count);
                System.arraycopy(yValues, 0, yTmp, 1, count);
                count++;
            } else if (index == count) {
                System.arraycopy(xValues, 0, xTmp, 0, count);
                System.arraycopy(yValues, 0, yTmp, 0, count);
                xTmp[count] = x;
                yTmp[count] = y;
                count++;
            } else {
                System.arraycopy(xValues, 0, xTmp, 0, index);
                System.arraycopy(yValues, 0, yTmp, 0, index);
                xTmp[index] = x;
                yTmp[index] = y;
                System.arraycopy(xValues, index, xTmp, index + 1, (count - index));
                System.arraycopy(yValues, index, yTmp, index + 1, (count - index));
                count++;
            }
            this.xValues = xTmp;
            this.yValues = yTmp;
        }
    }

    @Override
    public void remove(int index) {
        double[] xTmp = new double[count - 1];
        double[] yTmp = new double[count - 1];
        if (index == 0) {
            System.arraycopy(xValues, 1, xTmp, 0, count-1);
            System.arraycopy(yValues, 1, yTmp, 0, count-1);
        } else if (index == (count-1)) {
            System.arraycopy(xValues, 0, xTmp, 0, count - 1);
            System.arraycopy(yValues, 0, yTmp, 0, count - 1);
        } else {
            System.arraycopy(xValues, 0, xTmp, 0, index);
            System.arraycopy(yValues, 0, yTmp, 0, index);
            System.arraycopy(xValues, index + 1, xTmp, index, (count - index - 1));
            System.arraycopy(yValues, index + 1, yTmp, index, (count - index - 1));
        }
        count--;
        this.xValues = xTmp;
        this.yValues = yTmp;
    }

    private void checkOutOfBounds(int index){
        if (index < 0 || index >= count) {
            throw new ArrayIndexOutOfBoundsException("ErrorOutOfBounds");
        }
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }
}
