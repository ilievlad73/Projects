/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public interface TreePartVisitor {

    /**se viziteaza nodul cu intreg in el
     *
     * @param intreg
     */
    public void visit(NodeInt intreg);

    /**se viziteaza nodul cu string in el
     *
     * @param string
     */
    public void visit(NodeString string);

    /**se viziteaza nodul cu double in el
     *
     * @param doubl
     */
    public void visit(NodeDouble doubl);

    /**se viziteaza nodul cu operatia de adunare
     *
     * @param operatie
     */
    public void visit(NodeOperationAdd operatie);
    
    /**se viziteaza nodul cu operatia de scadere
     *
     * @param operatie
     */
    public void visit(NodeOperationSub operatie);
    
    /**se viziteaza nodul cu operatia de inmultire
     *
     * @param operatie
     */
    public void visit(NodeOperationMul operatie);
    
    /**se viziteaza nodul cu operatia de impartire
     *
     * @param operatie
     */
    public void visit(NodeOperationDiv operatie);

    /**se viziteaza un nod oarecare
     *
     * @param aThis
     */
    public void visit(Node aThis);
   
}
