package ru.ssau.tk.itenion.operations;

import ru.ssau.tk.itenion.functions.MathFunction;
import ru.ssau.tk.itenion.functions.exponentialFunctions.ExpFunction;
import ru.ssau.tk.itenion.functions.powerFunctions.ConstantFunction;
import ru.ssau.tk.itenion.functions.powerFunctions.IdentityFunction;
import ru.ssau.tk.itenion.functions.powerFunctions.PowFunction;
import ru.ssau.tk.itenion.functions.powerFunctions.ZeroFunction;

public class SteppingIntegralOperator implements IntegralOperator<MathFunction> {
    private final double AROUND;

    public SteppingIntegralOperator(double around) {
        AROUND = around;
    }

    @Override
    public MathFunction integrate(MathFunction function) {
        // 2^(-20)
        double STEP = 9.5367431640625E-7;
        SteppingDifferentialOperator differentialOperator = new MiddleSteppingDifferentialOperator(STEP);
        MathFunction resultFunction = new ZeroFunction();
        MathFunction tempFunction = function;
        double k = 1;
        boolean isUnmodifiableTempFunction = false;
        int n = 0;
        double temp;
        double LIMIT_NUMBER_OF_ITERATIONS = 20;
        for (int i = 0; i < LIMIT_NUMBER_OF_ITERATIONS; i++) {
            k *= i != 0 ? i : 1;
            resultFunction = resultFunction.sum((new PowFunction((double) i).andThen(
                    new IdentityFunction().subtract(new ConstantFunction(AROUND)))).multiply(tempFunction.apply(AROUND) / k));
            if (!isUnmodifiableTempFunction) {
                temp = tempFunction.apply(1.);
                tempFunction = differentialOperator.derive(tempFunction);
                // 2^(-10)
                double ACCURACY = 9.765625E-4;
                n += Math.abs(temp - tempFunction.apply(1.)) < ACCURACY ? 1 : 0;
                if (n == 2 && tempFunction.apply(1.) != 0) {
                    tempFunction = new ExpFunction();
                    isUnmodifiableTempFunction = true;
                }
            }
        }
        return resultFunction;
    }
}
