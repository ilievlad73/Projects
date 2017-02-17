/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public interface VeciniType {

    /**
     *
     * @return numarul de noduri adiacente
     */
    public int size();

    /**adauga un nod la lista de noduri adiacente
     *
     * @param a
     */
    public void add(NodABC a);

    /**elimina un nod din lista dupa nume
     *
     * @param name
     */
    public void remove(String name);

    /**
     *
     * @param i
     * @return nodul cu indicele i din lista de vecini
     */
    public NodABC get(int i);

    /**verifica daca un nod este in lista de noduri adiacente
     *
     * @param nod
     * @return true daca nodul exista in lista de noduri adiacente
     */
    public boolean VerificaExistenta(NodABC nod);
    
}
