

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class NodB implements NodABC {

    /**
     * Modalitatea prin care vom stoca nodurilea adiacente
     *
     */
    private VeciniType vecini;

    private static int versiune;
    private String nume;
    private int id;
    private boolean vizitat;

    NodB() {
        FactoryVeciniType VeciniType = FactoryVeciniType.getFactory();
        vecini = VeciniType.getVecini(versiune);
    }

    @Override
    public String getNume() {
        return nume;
    }

    @Override
    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public boolean getVizitat() {
        return vizitat;
    }

    @Override
    public void setVizitat(boolean vizitat) {
        this.vizitat = vizitat;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return this.id;
    }

    static void setVersiune(int i) {
        versiune = i;
    }

    @Override
    public int getVersiune() {
        return versiune;
    }

    /**
     *
     * @return versiunea curenta
     */
    public static int getVersiuneCurenta() {
        return versiune;
    }

    @Override
    public String typeNode() {
        return "NodB";
    }

    @Override
    public void addVecini(NodABC x) {
        vecini.add(x);
    }

    @Override
    public int nrVecini() {
        return vecini.size();
    }

    @Override
    public String getTypeVecini() {
        if (vecini instanceof Nod_list) {
            return "LIST";
        }
        if (vecini instanceof Nod_vector) {
            return "VECTOR";
        }
        if (vecini instanceof Nod_set) {
            return "SET";
        }
        return null;
    }

    @Override
    public NodABC getVecinul(int i) {
        return vecini.get(i);
    }

    @Override
    public void remove(String nume) {
        vecini.remove(nume);
    }

    @Override
    public boolean VerificaExistenta(NodABC nod) {

        return vecini.VerificaExistenta(nod);
    }
}
