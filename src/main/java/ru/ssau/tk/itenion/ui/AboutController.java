package ru.ssau.tk.itenion.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.ssau.tk.itenion.functions.MathFunction;
import ru.ssau.tk.itenion.functions.Nameable;
import ru.ssau.tk.itenion.functions.tabulatedFunctions.TabulatedFunction;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

@ConnectableItem(name = "About function", type = Item.CONTROLLER, pathFXML = "about.fxml")
public class AboutController implements TabVisitor, OpenableWindow, Initializable {
    @FXML
    Label baseMathFunction;
    @FXML
    Label leftBorder;
    @FXML
    Label rightBorder;
    @FXML
    Label numberOfPoints;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        baseMathFunction.setWrapText(true);
    }

    @FXML
    public void setInfo() {
        state().accept(this);
    }

    private String getNameForce(MathFunction mathFunction) {
        Field field = mathFunction.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        try {
            return ((Nameable) field.get(mathFunction)).getName();
        } catch (IllegalAccessException e) {
            AlertWindows.showError(e);
        } catch (ClassCastException e) {
            try {
                getNameForce((MathFunction) field.get(mathFunction));
            } catch (IllegalAccessException ex) {
                AlertWindows.showError(ex);
            }
        }
        return "";
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void visit(TabController.TFState tfState) {
        TabulatedFunction function = tfState.getFunction();
        try {
            baseMathFunction.setText(function.getName());
        } catch (ClassCastException e) {
            baseMathFunction.setText(getNameForce(function.getMathFunction()));
        }
        leftBorder.setText(function.leftBound() + "");
        rightBorder.setText(function.rightBound() + "");
        numberOfPoints.setText(function.getCount() + "");
    }

    @Override
    public void visit(TabController.VMFState vmfState) {
        baseMathFunction.setText(vmfState.getFunction().getName());
    }
}
