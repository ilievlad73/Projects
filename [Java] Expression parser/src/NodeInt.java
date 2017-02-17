/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class NodeInt extends Node implements TreePart {

    private String type = "int";

    public NodeInt() {

    }

    public NodeInt(String name, String info) {
        this.name = name;
        this.info = info;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void accept(TreePartVisitor treeParVisitor) {
        treeParVisitor.visit(this);
    }

    @Override
    void setType(String type) {
        this.type = type;
    }

}
