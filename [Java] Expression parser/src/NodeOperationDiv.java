/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class NodeOperationDiv extends Node implements TreePart {

    @Override
    public String getType() {
        return type;
    }
    private String type = "op/";

    @Override
    public void accept(TreePartVisitor treeParVisitor) {
        treeParVisitor.visit(this);
    }

    @Override
    void setType(String type) {
        this.type = type;
    }

    /**
     * Functie care se ocupa de impartirea a doua numere
     *
     * @param left
     * @param right
     * @return rezultatul operatiei
     */
    public static String impartire(Node left, Node right) {
        //stange = int
        if (left.getType().equals("int")) {
            //dreapta = int
            if (right.getType().equals("int")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = Integer.parseInt(left.getInfo());
                int RValue = Integer.parseInt(right.getInfo());
                if (RValue == 0) {
                    return "NaN";
                }
                int result = LValue / RValue;

                return "" + result;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = Integer.parseInt(left.getInfo());
                Double RValue = Double.parseDouble(right.getInfo());
                if (RValue == 0) {
                    return "NaN";
                }
                Double result = LValue / RValue;
                return "" + result;
            }
            //dreapta = str
            if (right.getType().equals("str")) {
                if (left.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = Integer.parseInt(left.getInfo());
                int RValue = right.getInfo().length();
                if (RValue == 0) {
                    return "NaN";
                }
                int result = LValue / RValue;

                return "" + result;
            }
        }
        //stanga = dou
        if (left.getType().equals("dou")) {
            //dreapta = int
            if (right.getType().equals("int")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                Double LValue = Double.parseDouble(left.getInfo());
                int RValue = Integer.parseInt(right.getInfo());
                if (RValue == 0) {
                    return "NaN";
                }
                Double result = LValue / RValue;

                return "" + result;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                Double LValue = Double.parseDouble(left.getInfo());
                Double RValue = Double.parseDouble(right.getInfo());
                if (RValue == 0) {
                    return "NaN";
                }
                Double result = LValue / RValue;

                return "" + result;
            }
            //dreapta = str
            if (right.getType().equals("str")) {
                if (left.getInfo().equals("NaN")) {
                    return "NaN";
                }
                Double LValue = Double.parseDouble(left.getInfo());
                int RValue = right.getInfo().length();
                if (RValue == 0) {
                    return "NaN";
                }
                Double result = LValue / RValue;

                return "" + result;
            }
        }
        //stanga = str
        if (left.getType().equals("str")) {
            //dreapta = int
            if (right.getType().equals("int")) {
                if (right.getInfo().equals("NaN")) {
                    return left.getInfo();
                }
                String LValue = left.getInfo();
                int RValue = Integer.parseInt(right.getInfo());
                if (RValue <= 0) {
                    return left.getInfo();
                }
                String result = LValue;
                int sfarsit = LValue.length() / RValue;
                result = result.substring(0, sfarsit);

                return "" + result;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = left.getInfo().length();
                Double RValue = Double.parseDouble(right.getInfo());
                if (RValue == 0) {
                    return "NaN";
                }
                Double result = LValue / RValue;

                return "" + result;
            }
            if (right.getType().equals("str")) {
                int LValue = left.getInfo().length();
                int RValue = right.getInfo().length();
                if (RValue == 0) {
                    return "NaN";
                }
                int result = LValue / RValue;

                return "" + result;
            }
        }
        return null;
    }

    /**
     * Primeste 2 noduri si intoarce tipul rezultatului impartirii
     *
     * @param left
     * @param right
     * @return tipul rezultatului operatiei
     */
    public static String Cast(Node left, Node right) {
        //stanga este int
        if (left.getType().equals("int")) {
            if (right.getType().equals("int")) {
                return "int";
            }
            if (right.getType().equals("dou")) {
                return "dou";
            }
            if (right.getType().equals("str")) {
                return "int";
            }

        }
        //stanga este double
        if (left.getType().equals("dou")) {
            if (right.getType().equals("int")) {
                return "dou";
            }
            if (right.getType().equals("dou")) {
                return "dou";
            }
            if (right.getType().equals("str")) {
                return "dou";
            }

        }
        //stanga este str
        if (left.getType().equals("str")) {

            if (right.getType().equals("int")) {
                return "str";
            }
            if (right.getType().equals("dou")) {
                return "dou";
            }
            if (right.getType().equals("str")) {
                return "int";
            }
        }
        return null;
    }
}
