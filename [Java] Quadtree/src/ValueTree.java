
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class ValueTree {

    public ArrayList<GeometricObject> figuri;

    public ValueTree() {
        this.figuri = new ArrayList<>();
    }

    public ValueTree(int max) {
        this.figuri = new ArrayList<>(max);
    }

    public void AddFigura(GeometricObject figura) {
        this.figuri.add(figura);
    }

    public int size() {
        return this.figuri.size();
    }

    public void RemoveFigura(int index) {
        if (this.size() > index) {
            this.figuri.remove(index);
        }
    }

    public int findId(int id) {
        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.figuri.get(i).id == id) {
                return i;
            }
        }
        return -1;
    }

    public void seteazaId(int id) {
        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.figuri.get(i).id == id) {
                this.figuri.get(i).id = 0;
            }
        }
    }

    public void removeDupaId(int id) {

        if (!(this.findId(id) == -1)) {

            int index = this.findId(id);
            RemoveFigura(index);
        }
    }

    public static boolean verificaListe(ValueTree x, ValueTree y) {
        if (x.size() != y.size()) {
            return false;
        } else {
            for (int i = x.size() - 1; i >= 0; i--) {
                if (y.findId(x.figuri.get(i).id) == -1) {
                    return false;
                }
            }
            return true;
        }
    }

    public boolean verificaFigura(GeometricObject figura) {
        for (int i = this.size() - 1; i >= 0; i--) {
            if (this.figuri.get(i).id == figura.id) {
                return true;
            }
        }
        return false;           // false daca nu exista  true daca exista
    }

    public ValueTree transfer() {
        ValueTree aux = new ValueTree();
        aux.figuri = new ArrayList<>(this.figuri);
        return aux;
    }

    public void goleste() {
        for (int i = this.size() - 1; i >= 0; i--) {
            RemoveFigura(i);
        }
    }

    @Override
    public String toString() {
        String a = " ";
        for (int i = 0; i < this.size(); i++) {
            a += " " + figuri.get(i).CeSuntEu();
        }
        return a;
    }

}
