/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class NodeFactory {

    public static NodeFactory INSTANCE = null;

    public Node getNode(String NodType) {
        switch (NodType) {
            case "int":
                return new NodeInt();
            case "dou":
                return new NodeDouble();
            case "str":
                return new NodeString();
            case "op+":
                return new NodeOperationAdd();
            case "op-":
                return new NodeOperationSub();
            case "op*":
                return new NodeOperationMul();
            case "op/":
                return new NodeOperationDiv();
            case "opi":
                return new NodeOperationParanteazaInchisa();
            case "opd":
                return new NodeOperationParanteazaDeschisa();
        }
        return null;
    }

    private NodeFactory() {

    }

    public static NodeFactory getFactory() {
        if (INSTANCE == null) {
            INSTANCE = new NodeFactory();
        }
        return INSTANCE;
    }
}
