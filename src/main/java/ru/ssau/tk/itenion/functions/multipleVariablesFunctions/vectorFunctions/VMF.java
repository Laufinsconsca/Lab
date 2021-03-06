package ru.ssau.tk.itenion.functions.multipleVariablesFunctions.vectorFunctions;

import Jama.Matrix;
import ru.ssau.tk.itenion.enums.SupportedSign;
import ru.ssau.tk.itenion.enums.Variable;
import ru.ssau.tk.itenion.functions.MathFunction;
import ru.ssau.tk.itenion.functions.TabHolderMathFunction;
import ru.ssau.tk.itenion.functions.multipleVariablesFunctions.vectorArgumentMathFunctions.VAMF;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface VMF extends TabHolderMathFunction<VMF> {
    Matrix getJacobiMatrix(Matrix x);

    void add(VAMF VAMF);

    ArrayList<Double> apply(ArrayList<Double> x);

    Matrix apply(Matrix x);

    boolean isCanBeSolved();

    boolean isCanBePlotted();

    Optional<Matrix> getIndexForPlot();

    MathFunction getMathFunction(Variable variable, int index);

    List<SupportedSign> getGeneratedSigns(int i);
}
