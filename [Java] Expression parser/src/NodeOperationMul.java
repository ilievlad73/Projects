/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class NodeOperationMul extends Node implements TreePart {

    @Override
    public String getType() {
        return type;
    }
    private String type = "op*";

    @Override
    public void accept(TreePartVisitor treeParVisitor) {
        treeParVisitor.visit(this);
    }

    @Override
    void setType(String type) {
        this.type = type;
    }

    /**
     * Functie care se ocupa de inmultirea a doua numere
     *
     * @param left
     * @param right
     * @return rezultatul operatiei
     */
    public static String inmultire(Node left, Node right) {
        //stanga = int
        if (left.getType().equals("int")) {
            //dreapta este int
            if (right.getType().equals("int")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = Integer.parseInt(left.getInfo());
                int RValue = Integer.parseInt(right.getInfo());
                int rezultat = LValue * RValue;

                return "" + rezultat;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = Integer.parseInt(left.getInfo());
                double RValue = Double.parseDouble(right.getInfo());
                double rezultat = LValue * RValue;

                return "" + rezultat;
            }
            //dreapta = str
            if (right.getType().equals("str")) {
                if (left.getInfo().equals("NaN")) {
                    return "";
                }
                int LValue = Integer.parseInt(left.getInfo());
                String RValue = right.getInfo();
                if (LValue <= 0) {
                    return "";
                }
                StringBuilder rezultat = new StringBuilder();
                for (int i = 0; i < LValue; i++) {
                    rezultat.append(RValue);
                }

                return rezultat.toString();
            }

        }
        //stanga = double
        if (left.getType().equals("dou")) {
            //dreapta = int
            if (right.getType().equals("int")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                double LValue = Double.parseDouble(left.getInfo());
                int RValue = Integer.parseInt(right.getInfo());
                double rezultat = LValue * RValue;

                return "" + rezultat;
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (left.getInfo().equals("NaN") || right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                double LValue = Double.parseDouble(left.getInfo());
                double RValue = Double.parseDouble(right.getInfo());
                double rezultat = LValue * RValue;

                return "" + rezultat;
            }
            //dreapta = str
            if (right.getType().equals("str")) {

                if (left.getInfo().equals("NaN")) {
                    return "NaN";
                }
                double LValue = Double.parseDouble(left.getInfo());
                int RValue = right.getInfo().length();
                double rezultat = LValue * RValue;

                return "" + rezultat;
            }

        }
        //stanga = str
        if (left.getType().equals("str")) {
            //dreapta = int
            if (right.getType().equals("int")) {
                if (right.getInfo().equals("NaN")) {
                    return "";
                }
                String LValue = left.getInfo();
                int RValue = Integer.parseInt(right.getInfo());

                if (RValue <= 0) {
                    return "";
                }
                StringBuilder rezultat = new StringBuilder();
                for (int i = 0; i < RValue; i++) {
                    rezultat.append(LValue);
                }

                return rezultat.toString();
            }
            //dreapta = dou
            if (right.getType().equals("dou")) {
                if (right.getInfo().equals("NaN")) {
                    return "NaN";
                }
                int LValue = left.getInfo().length();
                double RValue = Double.parseDouble(right.getInfo());
                double rezultat = LValue * RValue;

                return "" + rezultat;
            }
            //dreapta = str
            if (right.getType().equals("str")) {
                int LValue = left.getInfo().length();
                int RValue = right.getInfo().length();
                int rezultat = LValue * RValue;

                return "" + rezultat;
            }

        }
        return null;
    }

    /**
     * Primeste 2 noduri si intoarce tipul rezultatului inmultirii
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
                return "dou";
            }

        }
        if (left.getType().equals("str")) {
            //dreapta este int
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
