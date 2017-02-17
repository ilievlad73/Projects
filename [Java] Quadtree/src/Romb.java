/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class Romb extends GeometricObject {

    private double x1, y1;     //coordonatele varfului de sus
    private double x2, y2;     //coordonate stanga mijloc
    private double x3, y3;     //coordonate varfului de jos
    private double x4, y4;     //coordonate dreapta mijloc

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

    public double getX4() {
        return x4;
    }

    public double getY4() {
        return y4;
    }

    public Romb(int id, double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        this.id = id;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
        dreptunghi_incadrator = new Rectangle(id, x2, y3, x4, y1);
    }

    public Romb() {

    }

    public boolean TestPunct(double x, double y) {
        /*testarea apartenentei unui punct la romb*/

        Triangle sus = new Triangle(0, x1, y1, x2, y2, x4, y4);
        Triangle jos = new Triangle(0, x2, y2, x3, y3, x4, y4);    //verificam daca apartine unui din triunghiuri
        return sus.TestPunct(x, y) || jos.TestPunct(x, y);
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
        return "ROMB " + id + " " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + x3 + " " + y3 + " " + x4 + " " + y4;
    }

    public boolean suntInSW(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestRomb(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public boolean suntInSE(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestRomb(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public boolean suntInNW(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestRomb(x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public boolean suntInNE(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(0, x1, y1, x2, y2);
        return test.TestRomb(x1, y1, x2, y2, x3, y3, x4, y4);
    }
}
