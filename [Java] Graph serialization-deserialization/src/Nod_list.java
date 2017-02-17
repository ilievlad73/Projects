
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class Nod_list implements VeciniType {

    private LinkedList<NodABC> vecini;

    public Nod_list() {
        this.vecini = new LinkedList<>();
    }

    @Override
    public NodABC get(int i) {
        return vecini.get(i);
    }

    @Override
    public void add(NodABC a) {
        vecini.add(a);
    }

    @Override
    public int size() {
        return vecini.size();
    }


    @Override
    public void remove(String x) {
        for (int i = 0; i < vecini.size(); i++) {
            if (vecini.get(i).getNume().equals(x)) {
                vecini.remove(i);
            }
        }
    }


    @Override
    public boolean VerificaExistenta(NodABC nod) {
        
        for (int i = 0; i < vecini.size(); i++) {
            if (vecini.get(i).getId() == nod.getId()) {
                return true;
            }
        }
        return false;
    }
}
