/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class TreePartDisplayVisitor implements TreePartVisitor {

    @Override
    public void visit(NodeInt intreg) {

    }

    @Override
    public void visit(NodeString string) {

    }

    @Override
    public void visit(NodeDouble doubl) {

    }

    @Override
    public void visit(Node aThis) {

    }

    @Override
    public void visit(NodeOperationSub operatie) {
        String aux = NodeOperationSub.scadere(operatie.left, operatie.right);
        operatie.setType(NodeOperationSub.Cast(operatie.left, operatie.right));
        operatie.setInfo(aux);
        
    }

    @Override
    public void visit(NodeOperationMul operatie) {
        String aux = NodeOperationMul.inmultire(operatie.left, operatie.right);
        operatie.setType(NodeOperationMul.Cast(operatie.left, operatie.right));
        operatie.setInfo(aux);
        
    }

    @Override
    public void visit(NodeOperationDiv operatie) {
        String aux = NodeOperationDiv.impartire(operatie.left, operatie.right);
        operatie.setType(NodeOperationDiv.Cast(operatie.left, operatie.right));
        operatie.setInfo(aux);
        
    }

    @Override
    public void visit(NodeOperationAdd operatie) {
        String aux = NodeOperationAdd.adunare(operatie.left, operatie.right);
        operatie.setType(NodeOperationAdd.Cast(operatie.left, operatie.right));
        operatie.setInfo(aux);
        
    }

}
