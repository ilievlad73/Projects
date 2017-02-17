/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class NodeOperationAdd extends Node implements TreePart {

    private String type = "op+";

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

    /**
     * Functie care se ocupa de adunarea a doua numere
     *
     * @param left
     * @param right
     * @return rezultatul operatiei 
     */
    public static String adunare(Node left, Node right) {
        //stanga = int
        if (left.getType().equals("int")) {
            //dreapta = int
            if (right.getType().equals("int")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = Integer.parseInt(left.getInfo());
                int RValue = Integer.parseInt(right.getInfo());
                int result = LValue + RValue;

                return "" + result;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = Integer.parseInt(left.getInfo());
                Double RValue = Double.parseDouble(right.getInfo());
                Double result = LValue + RValue;

                return "" + result;
            }
            //dreapta = str
            if (right.getType().equals("str")) {
                if (left.getInfo().equals("NaN")) {
                    return "NaN" + right.getInfo();
                }
                int LValue = Integer.parseInt(left.getInfo());
                String RValue = right.getInfo();
                String result = LValue + RValue;

                return result;
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
                Double result = LValue + RValue;

                return "" + result;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                Double LValue = Double.parseDouble(left.getInfo());
                Double RValue = Double.parseDouble(right.getInfo());
                Double result = LValue + RValue;

                return "" + result;
            }
            //dreapta = str
            if (right.getType().equals("str")) {
                if (left.getInfo().equals("NaN")) {
                    return "NaN" + right.getInfo();
                }
                Double LValue = Double.parseDouble(left.getInfo());
                String RValue = right.getInfo();
                LValue = Math.round(LValue * 100.0) / 100.0;
                String result = LValue + RValue;

                return result;
            }
        }
        //stanga = str
        if (left.getType().equals("str")) {
            //drepta = int
            if (right.getType().equals("int")) {
                if (right.getInfo().equals("NaN")) {
                    return left.getInfo() + "NaN";
                }
                String LValue = left.getInfo();
                int RValue = Integer.parseInt(right.getInfo());
                String result = LValue + RValue;

                return result;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (right.getInfo().equals("NaN")) {
                    return left.getInfo() + "NaN";
                }
                String LValue = left.getInfo();
                Double RValue = Double.parseDouble(right.getInfo());
                RValue = Math.round(RValue * 100.0) / 100.0;
                String result = LValue + RValue;

                return result;
            }
            //dreapta = str
            if (right.getType().equals("str")) {
                String RValue = right.getInfo();
                String LValue = left.getInfo();
                String result = LValue + RValue;

                return result;
            }
        }
        return null;
    }

    /**Primeste 2 noduri si intoarce tipul rezultatului adunarii 
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
                return "str";
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
                return "str";
            }

        }
        //stanga este str
        if (left.getType().equals("str")) {

            if (right.getType().equals("int")) {
                return "str";
            }
            if (right.getType().equals("dou")) {
                return "str";
            }
            if (right.getType().equals("str")) {
                return "str";
            }
        }
        return null;
    }
}
