/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class NodeString extends Node implements TreePart {

    private String type = "str";

    public NodeString() {

    }

    public NodeString(String name, String info) {
        this.name = name;
        this.info = info;
    }

    @Override
    public String getType() {
        return "str";
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
