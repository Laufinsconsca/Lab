package ru.ssau.tk.sergunin.lab.functions;

import ru.ssau.tk.sergunin.lab.alt_ui.Selectable;

@Selectable(name = "Синус", priority = 12)
public class SinFunction implements MathFunction {
    @Override
    public double apply(double x) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        return java.lang.Math.sin(x);
    }
}