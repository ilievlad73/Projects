/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public interface NodABC {

    /**
     *
     * @return numele din nou
     */
    public String getNume();

    /**seteaza numele din nod
     *
     * @param nume
     */
    public void setNume(String nume);

    /**
     *
     * @return id-ul nodului
     */
    public int getId();

    /**seteaza id-ul nodului
     *
     * @param id
     */
    public void setId(int id);

    /**
     *
     * @return valoarea variabilei vizitat
     */
    public boolean getVizitat();

    /**seteaza variabila vizitat cu valoare vizitat
     *
     * @param vizitat
     */
    public void setVizitat(boolean vizitat);

    /**adauga un nod la lista de noduri adiacente
     *
     * @param x
     */
    public void addVecini(NodABC x);

    /**
     *
     * @return numarul de noduri adiacente
     */
    public int nrVecini();

    /**
     *
     * @return tipul de nod in care ne aflam
     */
    public String typeNode();

    /**
     *
     * @return returneaza modalitatea de a stoca nodurile adiacente
     */
    public String getTypeVecini();

    /**
     *
     * @param i
     * @return nodul adiacent cu indicele i
     */
    public NodABC getVecinul(int i);

    /**
     *
     * @return versiunea folosita
     */
    public int getVersiune();

    /**scoate un nod din lista de noduri adiacente dupa nume
     *
     * @param nume
     */
    public void remove(String nume);

    /**
     *
     * @param nod
     * @return true daca nodul este in lista de adiacenta
     */
    public boolean VerificaExistenta(NodABC nod);

}
