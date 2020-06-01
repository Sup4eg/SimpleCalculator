package domain.entity;

import domain.repository.ICalculator;

public class Calculator {
    private ICalculator iCalculator;
    public Calculator(ICalculator iCalculator) {
        this.iCalculator = iCalculator;
    }

    public Calculator() {}

    public double add(double o1, double o2) {
        return iCalculator.add(o1, o2);
    }

    public double subtract(double o1, double o2) {
        return iCalculator.subtract(o1, o2);
    }

    public double multiply(double o1, double o2) {
        return iCalculator.multiply(o1, o2);
    }

    public double divide(double o1, double o2) {
        return iCalculator.divide(o1, o2);
    }

    public double double15() {
        return 15.0;
    }
}
