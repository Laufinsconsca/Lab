package ru.ssau.tk.sergunin.lab.functions;

import ru.ssau.tk.sergunin.lab.ui.Item;
import ru.ssau.tk.sergunin.lab.ui.ConnectableItem;

@ConnectableItem(name = "Степенная функция", priority = 6, type = Item.FUNCTION, parameter = true)
public class PowFunction implements MathFunction {
    private final double pow;

    public PowFunction(double pow) {
        this.pow = pow;
    }

    @Override
    public double apply(double x) {
        return Math.pow(x, pow);
    }
}
