/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class Rectangle extends GeometricObject {

    public  double x1, y1;      // coordonatele de jos
    public  double x2, y2;      // coordonatele de sus

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

    public Rectangle(int id, double x1, double y1, double x2, double y2) {
        this.id = id;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Rectangle() {

    }

    public boolean TestPunct(double x, double y) {
        /*testarea apartenentei unui punct la dreptunghi*/
        return !(x > x2 || x < x1 || y > y2 || y < y1);
    }

    public boolean TestDreptunghi(double x1, double y1, double x2, double y2) {
        return !(this.y2 < y1 || this.x2 < x1 || this.y1 > y2 || this.x1 > x2);
    }

    public boolean TestCerc(double R, double x, double y) {
        /*testarea intersectiei cu un cerc*/
        return this.TestDreptunghi(x - R, y - R, x + R, y + R);
    }

    public boolean TestTriunghi(double x1, double y1, double x2, double y2,
            double x3, double y3) {
        /*testarea intersectiei cu un triunghi*/
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
        return this.TestDreptunghi(colt_jos_x, colt_jos_y, colt_sus_x, colt_sus_y);
    }

    public boolean TestRomb(double x1, double y1, double x2, double y2,
            double x3, double y3, double x4, double y4) {
        return this.TestDreptunghi(x2, y3, x4, y1);
    }

    public String CeSuntEu() {
        return "DREPTUNGHI " + id + " " + x1 + " " + y1 + " " + x2 + " " + y2;
    }

    public boolean suntInSW(double x1, double y1, double x2, double y2) {
        //Rectangle test = new Rectangle(x1, y1, x2, y2);
        return this.TestDreptunghi(x1, y1, x2, y2);
    }

    public boolean suntInSE(double x1, double y1, double x2, double y2) {
        return this.TestDreptunghi(x1, y1, x2, y2);
    }

    public boolean suntInNW(double x1, double y1, double x2, double y2) {
        return this.TestDreptunghi(x1, y1, x2, y2);
    }

    public boolean suntInNE(double x1, double y1, double x2, double y2) {
        return this.TestDreptunghi(x1, y1, x2, y2);
    }
}
