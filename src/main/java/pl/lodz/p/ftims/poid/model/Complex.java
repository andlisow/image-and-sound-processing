package main.java.pl.lodz.p.ftims.poid.model;

/**
 * @author alisowsk
 */
public class Complex {
    private final double r;
    private final double i;

    public Complex(double real, double imaginary) {
        r = real;
        i = imaginary;
    }

    // return abs/modulus/magnitude and angle/phase/argument
    public double abs()   { return Math.hypot(r, i); }  // Math.sqrt(r*r + i*i)

    public double phase() { return Math.atan2(i, r); }  // between -pi and pi

    public Complex plus(Complex b) {
        Complex a = this;
        double real = a.r + b.r;
        double imag = a.i + b.i;
        return new Complex(real, imag);
    }

    public Complex minus(Complex b) {
        Complex a = this;
        double real = a.r - b.r;
        double imag = a.i - b.i;
        return new Complex(real, imag);
    }

    public Complex times(Complex b) {
        Complex a = this;
        double real = a.r * b.r - a.i * b.i;
        double imag = a.r * b.i + a.i * b.r;
        return new Complex(real, imag);
    }

    public Complex times(double alpha) {
        return new Complex(alpha * r, alpha * i);
    }

    public Complex conjugate() {  return new Complex(r, -i); }

    public double getR() { return r; }

    public double getI() { return i; }

}
