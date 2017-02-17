/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class Punct {

    double x, y;

    public Punct() {
    }

    public Punct(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean testDreptunghi(double x1, double y1, double x2, double y2) {

        return !(x > x2 || x < x1 || y > y2 || y < y1);
    }

}
