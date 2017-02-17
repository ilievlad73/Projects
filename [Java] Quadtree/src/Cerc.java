/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class Cerc extends GeometricObject {

    private double R;
    private double x;
    private double y;

    public Rectangle dreptunghi_incadrator;

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return R;
    }

    public Cerc(int id, double R, double x, double y) {
        this.id = id;
        this.R = R;
        this.x = x;
        this.y = y;
        dreptunghi_incadrator = new Rectangle(id, x - R, y - R, x + R, y + R);
    }

    public Cerc() {

    }

    public boolean TestPunct(double x, double y) {
        return Math.sqrt((this.x - x) * (this.x - x) + (this.y - y) * (this.y - y)) <= R;
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
        return "CERC " + id + " " + R + " " + x + " " + y;
    }

    public boolean suntInSW(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(this.id, x1, y1, x2, y2);
        return test.TestCerc(R, x, y);
    }

    public boolean suntInSE(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(this.id, x1, y1, x2, y2);
        return test.TestCerc(R, x, y);
    }

    public boolean suntInNW(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(this.id, x1, y1, x2, y2);
        return test.TestCerc(R, x, y);
    }

    public boolean suntInNE(double x1, double y1, double x2, double y2) {
        Rectangle test = new Rectangle(this.id, x1, y1, x2, y2);
        return test.TestCerc(R, x, y);
    }

}
