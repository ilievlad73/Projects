/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class Triangle extends GeometricObject {

    private double x1, y1;     //coordonatele varfului de sus
    private double x2, y2;     //coordonate stanga jos
    private double x3, y3;     //coordonate dreapta jos
    public Rectangle dreptunghi_incadrator;

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public double getY1() {
        return y1;
    }

    public double getY2() {
        return y2;
    }

    public double getX3() {
        return x3;
    }

    public double getY3() {
        return y3;
    }

    public Triangle(int id, double x1, double y1, double x2, double y2, double x3, double y3) {
        this.id = id;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        double colt_jos_x = x2;
        double colt_jos_y = y2;
        double colt_sus_x = x3;
        double colt_sus_y = y1;
        if (x1 < x2) {
            colt_jos_x = x1;
        }
        if (x1 > x3) {
            colt_sus_x = x1;
        }
        dreptunghi_incadrator = new Rectangle(id, colt_jos_x, colt_jos_y, colt_sus_x, colt_sus_y);
    }

    public Triangle() {

    }

    public static double ArieTriunghi(double x1, double y1, double x2, double y2, double x3, double y3) {
        return (double) 1 / 2 * Math.abs(x1 * y2 + x2 * y3 + x3 * y1 - x3 * y2 - y3 * x1 - y1 * x2);
    }

    public boolean TestPunct(double x, double y) {        //se calculeaza aria in doua moduri
        double Test_arie = Triangle.ArieTriunghi(x, y, x2, y2, x3, y3) + Triangle.ArieTriunghi(x1, y1, x, y, x3, y3) + Triangle.ArieTriunghi(x1, y1, x2, y2, x, y);
        return Triangle.ArieTriunghi(x1, y1, x2, y2, x3, y3) == Test_arie;
    }

    public boolean TestDreptunghi(double x1, double y1, double x2, double y2) {
        return dreptunghi_incadrator.TestDreptunghi(x1, y1, x2, y2);
    }

    public boolean TestCerc(double R, double x, double y) {
        /*testarea intersectiei cu un cerc*/
        return dreptunghi_incadrator.TestCerc(R, x, y);
    }

    public boolean TestTriunghi(double x1, double y1, double x2, double y2, double x3, double y3) {
        /*testarea intersectiei cu un triunghi*/
        return dreptunghi_incadrator.TestTriunghi(x1, y1, x2, y2, x3, y3);
    }

    public boolean TestRomb(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        return dreptunghi_incadrator.TestDreptunghi(x2, y3, x4, y1);
    }

    public String CeSuntEu() {
        return "TRIUNGHI " + id + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + x3 + " " + y3;
    }

    public boolean suntInSW(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestTriunghi(x1, y1, x2, y2, x3, y3);
    }

    public boolean suntInSE(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestTriunghi(x1, y1, x2, y2, x3, y3);
    }

    public boolean suntInNW(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestTriunghi(x1, y1, x2, y2, x3, y3);
    }

    public boolean suntInNE(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestTriunghi(x1, y1, x2, y2, x3, y3);
    }
}
