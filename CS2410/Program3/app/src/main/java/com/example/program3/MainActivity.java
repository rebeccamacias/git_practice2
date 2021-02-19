package com.example.program3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.widget.GridLayout;

import java.net.CacheRequest;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<CalcButtonData> calcButtonData = new ArrayList<CalcButtonData>() {
        {
            add(new CalcButtonData("7", 1, 0, 1));
            add(new CalcButtonData("8", 1, 1, 1));
            add(new CalcButtonData("9", 1, 2, 1));
            add(new CalcButtonData("C", 1, 3, 1 , CalcButtonData.ButtonType.CLEAR));
            add(new CalcButtonData("4", 2, 0, 1));
            add(new CalcButtonData("5", 2, 1, 1));
            add(new CalcButtonData("6", 2, 2, 1));
            add(new CalcButtonData("/", 2, 3, 1, CalcButtonData.ButtonType.EXPRESSION));
            add(new CalcButtonData("1", 3, 0, 1));
            add(new CalcButtonData("2", 3, 1, 1));
            add(new CalcButtonData("3", 3, 2, 1));
            add(new CalcButtonData("*", 3, 3, 1, CalcButtonData.ButtonType.EXPRESSION));
            add(new CalcButtonData(".", 4, 0, 1));
            add(new CalcButtonData("0", 4, 1, 2));
            add(new CalcButtonData("-", 4, 3, 1, CalcButtonData.ButtonType.EXPRESSION));
            add(new CalcButtonData("+", 5, 3, 1, CalcButtonData.ButtonType.EXPRESSION));
            add(new CalcButtonData("=", 5, 0, 3 , CalcButtonData.ButtonType.EQUAL));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createLayout();
        createCalcDisplay();
        createButtons();
    }

    private void createLayout() {
        GridLayout mainLayout = new GridLayout(this);
        mainLayout.setColumnCount(4);
        mainLayout.setId(R.id.mainLayout);
        setContentView(mainLayout);
    }

    private void createCalcDisplay() {
        CalcDisplayView calcDisplayView = new CalcDisplayView(this);
        calcDisplayView.setId(R.id.calculatorNumDisplay);
        GridLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.addView(calcDisplayView);
    }

    private void createButtons() {
        GridLayout mainLayout = findViewById(R.id.mainLayout);
        CalcDisplayView calcDisplayView = findViewById(R.id.calculatorNumDisplay);
        calcButtonData.forEach(data -> {
            CalcButton button = new CalcButton(
                    this,
                    data,
                    (view) -> {
                        if(data.getType() == CalcButtonData.ButtonType.CLEAR){
                            calcDisplayView.setText("");
                        } else if (data.getType() == CalcButtonData.ButtonType.EQUAL){
                            String expression = calcDisplayView.getText().toString();
                            double result = CalcJobs.evaluate(expression);
                                calcDisplayView.setText("" + result);
                        } else if (data.getType() == CalcButtonData.ButtonType.EXPRESSION) {
                            calcDisplayView.setText(calcDisplayView.getText().toString() + " " + data.getButtonText() + " ");
                        } else {
                            calcDisplayView.setText(
                                    calcDisplayView.getText().toString() + data.getButtonText()
                            );
                        }
                    }
            );
            mainLayout.addView(button);
        });
        }
}