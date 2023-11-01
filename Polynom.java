import java.util.ArrayList;
import java.util.Random;

public class Polynom {
    private ArrayList<Double> polynom;

    public Polynom() {
        this.polynom = new ArrayList<>();
        this.polynom.add(0.0);
    }

    public Polynom(ArrayList<Double> polynom) {
        if (polynom != null) this.polynom = new ArrayList<>(polynom);
        else throw new IllegalArgumentException();
    }

    public Polynom(int n) {
        if (n > -1) {
            this.polynom = new ArrayList<>(n + 1);
            Random random = new Random();
            for (int i = 0; i  < n + 1; ++i)
                polynom.add((double)(random.nextInt(21) - 10));
        } else throw new IllegalArgumentException();
    }


    public double calculateValue(double x) {
        double result = 0.0;
        for (int i = 0; i < polynom.size(); i++) {
            result += polynom.get(i) * Math.pow(x, i);
        }
        return result;
    }

    public double getCoefficient(int degree) {
        if (degree < polynom.size()) {
            return polynom.get(degree);
        }
        else throw new NullPointerException();
    }

    public int getSize() {
        return polynom.size();
    }

    public Polynom multiply(Polynom other) {
        int newSize = this.getSize() + other.getSize() - 1;
        ArrayList<Double> resultCoefficients = new ArrayList<>(newSize);

        for (int i = 0; i < newSize; i++) {
            double resultCoefficient = 0.0;
            for (int j = 0; j <= i; j++) {
                if (j < this.getSize() && (i - j) < other.getSize()) {
                    resultCoefficient += this.getCoefficient(j) * other.getCoefficient(i - j);
                }
            }
            resultCoefficients.add(resultCoefficient);
        }

        return new Polynom(resultCoefficients);
    }

    public Polynom multiply(double constant) {
        ArrayList<Double> resultCoefficients = new ArrayList<>(this.getSize());
        for (int i = 0; i < this.getSize(); i++) {
            resultCoefficients.add(this.getCoefficient(i) * constant);
        }
        return new Polynom(resultCoefficients);
    }

    public Polynom add(Polynom other) {
        int maxSize = Math.max(this.getSize(), other.getSize());
        ArrayList<Double> resultCoefficients = new ArrayList<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            double coefficient1 = this.getCoefficient(i);
            double coefficient2 = other.getCoefficient(i);
            resultCoefficients.add(coefficient1 + coefficient2);
        }

        return new Polynom(resultCoefficients);
    }

    public Polynom derivative(){
        if(this.polynom.size() > 1) {
            Polynom derivative = new Polynom();
            derivative.polynom = new ArrayList<>(this.polynom.size() - 1);
            for (int i = 1; i < this.polynom.size(); ++i) {
                derivative.polynom.add(i * this.polynom.get(i));
            }
            return derivative;
        }

        else return new  Polynom();
    }

    public Polynom antiderivative(double k){
        Polynom antiderivative = new Polynom();
        antiderivative.polynom = new ArrayList<>(this.polynom.size() + 1);
        antiderivative.polynom.add(0, k);
        if(this.polynom.size() > 1) {
            for (int i = 0; i < this.polynom.size(); ++i) {
                antiderivative.polynom.add(this.polynom.get(i) / (i + 1));
            }
        }
        return antiderivative;
    }

    public double integral(double a, double b){
        Polynom polynom = this.antiderivative(0.0);
        return polynom.calculateValue(b) - polynom.calculateValue(a);
    }

    public double integralNumeralMethod(double a, double b, double accuracy){
            int N = 1;
            double result = 0;
            double trueValue = this.integral(a, b);
            do {
                N *= 2;
                double h = (b - a) / (N - 1);
                for (int i = 0; i < N - 1; ++i) {
                    result += this.calculateValue(a + i * h);
                }
                result *= h;
            } while (trueValue - result > accuracy);
            return result;

    }

    public String toString(){
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < this.polynom.size(); ++i){
            if(i != this.polynom.size() - 1){
                str.append(this.polynom.get(i));
                str.append(" ");
            }
            else str.append(this.polynom.get(i));
        }
        return str.toString();
    }
}
