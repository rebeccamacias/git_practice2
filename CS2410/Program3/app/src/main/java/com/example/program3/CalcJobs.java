package com.example.program3;

public class CalcJobs {
    public static double evaluate (String expression){

        String[] tokens = expression.split(" ");
        if (tokens.length == 0){
            return Double.NaN;
        }
        if (tokens.length == 1) {
            return Double.parseDouble(tokens[0]);
        }

        if (tokens.length == 2){
            return Double.NaN;
        }

        String mode = "value";
        double currentValue = Double.parseDouble(tokens[0]);
        String operation = tokens[1];

        for (int i = 2; i < tokens.length; i++){
            if (mode.equals("value")) {
                double foundValue = Double.parseDouble(tokens[i]);
                if (operation.equals("+")){
                    currentValue = currentValue + foundValue;
                }
                if (operation.equals("-")){
                    currentValue = currentValue - foundValue;
                }
                if (operation.equals("*")){
                    currentValue = currentValue * foundValue;
                }
                if (operation.equals("/")){
                    currentValue = currentValue / foundValue;
                }
                mode = "operator";
            } else {
                operation = tokens[i];
                mode = "value";
            }
        }

        return currentValue;
    }
}
