package ru.ssau.tk.itenion.io;

import ru.ssau.tk.itenion.functions.factory.ArrayTabulatedFunctionFactory;
import ru.ssau.tk.itenion.functions.factory.LinkedListTabulatedFunctionFactory;
import ru.ssau.tk.itenion.ui.AlertWindows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TabulatedFunctionFileReader {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("input/function.txt"))) {
            System.out.println(FunctionsIO.readTabulatedFunction(reader, new LinkedListTabulatedFunctionFactory()).toString());
        } catch (IOException e) {
            AlertWindows.showError(e);
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("input/function.txt"));
            System.out.println(FunctionsIO.readTabulatedFunction(reader, new ArrayTabulatedFunctionFactory()).toString());
        } catch (IOException e) {
            try {
                reader.close();
            } catch (IOException ex) {
                AlertWindows.showError(ex);
            }
        }
    }
}
