
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class Nod_set implements VeciniType  {

    private Set<NodABC> vecini;

    public Nod_set() {
        this.vecini = new HashSet<>();
    }

    @Override
    public int size() {
        return vecini.size();
    }

    @Override
    public void add(NodABC a) {
        vecini.add(a);
    }

    @Override
    public void remove(String x) {
        Iterator<NodABC> iter = vecini.iterator();
        while (iter.hasNext()) {
            NodABC aux = iter.next();
            if (aux.getNume().equals(x)) {
                vecini.remove(aux);
                break;
            }
        }
    }
    @Override
    public NodABC get(int i) {
        Iterator<NodABC> iter = vecini.iterator();
        for (int j = 0; j < i; j++) {
            iter.next();
        }
        return iter.next();
    }
    @Override
    public boolean VerificaExistenta(NodABC nod) {
        Iterator<NodABC> iter = vecini.iterator();
        while (iter.hasNext()) {
            NodABC aux = iter.next();
            if (aux.getId() == nod.getId()) {
                return true;
            }
        }
        return false;
    }
}
