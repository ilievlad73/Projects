
import java.io.PrintWriter;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class Array {

    public int[] tablou;

    public Array() {
    }

    public Array(int n) {
        this.tablou = new int[n];
    }

    class ArrayIterator {

        public int iterator = 0;

        public ArrayIterator() {

        }

        public ArrayIterator(int n) {
            iterator = n;
        }

        public boolean hasNext() {
            return this.iterator < tablou.length;
        }
    }
    ArrayIterator i = new ArrayIterator();

    public void PrintForward(PrintWriter writer) {
        int limita = i.iterator;
        if (limita == 0) {
            writer.print("NIL");
        } else {
            writer.write(""+tablou[0]);
            for (int j = 1; j < limita; j++) {
                writer.write(" " + tablou[j]);
            }
        }
        writer.println();
    }

    public void setValoare(int valoare) {
        if (i.hasNext() == true) {
            tablou[i.iterator] = valoare;
            i.iterator++;
        }
    }

    public boolean verifica(int valoare) {
        int limita = i.iterator;
        for (int j = 0; j < limita; j++) {
            if (tablou[j] == valoare) {
                return true;
            }
        }
        return false;
    }

    public void sortare() {
        Arrays.sort(tablou, 0, i.iterator);
    }
}
