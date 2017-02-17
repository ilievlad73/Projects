/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public abstract class GeometricObject {

    protected int id;

    public abstract boolean TestPunct(double x, double y);

    public abstract boolean TestDreptunghi(double x1, double y1, double x2, double y2);

    public abstract boolean TestCerc(double R, double x, double y);

    public abstract boolean TestTriunghi(double x1, double y1, double x2, double y2, double x3, double y3);

    public abstract boolean TestRomb(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4);

    public abstract String CeSuntEu();

    public abstract boolean suntInSW(double x1, double y1, double x2, double y2);

    public abstract boolean suntInSE(double x1, double y1, double x2, double y2);

    public abstract boolean suntInNE(double x1, double y1, double x2, double y2);

    public abstract boolean suntInNW(double x1, double y1, double x2, double y2);

}
