package ru.ssau.tk.itenion.functions;

import org.testng.annotations.Test;
import ru.ssau.tk.itenion.functions.powerFunctions.IdentityFunction;

import static org.testng.Assert.assertEquals;

public class IdentityFunctionTest {

    @Test
    public void testApply() {
        MathFunction x = new IdentityFunction();
        double ACCURACY = 0.0001;
        assertEquals(x.apply(5.), 5, ACCURACY);
    }
}