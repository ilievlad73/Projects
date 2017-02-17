
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public abstract class Node implements TreePart {

    Node right, left;

    protected String info;
    protected String name;

    abstract String getType();

    abstract void setType(String type);

    public Node setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public Node setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    private static final HashMap<String, Node> map = new HashMap<>();

    /**
     * se initializeaza tabela si se introduc si operatorii
     *
     */
    public static void InitializareTabela() {
        NodeFactory fabrica = NodeFactory.getFactory();
        Node.addToHashMap("+", fabrica.getNode("op+"), "+");
        Node.addToHashMap("-", fabrica.getNode("op-"), "-");
        Node.addToHashMap("/", fabrica.getNode("op/"), "/");
        Node.addToHashMap("*", fabrica.getNode("op*"), "*");
        Node.addToHashMap("(", fabrica.getNode("opd"), "(");
        Node.addToHashMap(")", fabrica.getNode("opi"), ")");
    }

    /**
     * Se introduce in tabela un nou nod
     *
     * @param key
     * @param node
     * @param value
     */
    public static void addToHashMap(String key, Node node, String value) {
        node.setInfo(value)
                .setName(key);
        map.put(key, node);
    }

    /**Se creaza arborele de parsare
     *
     * @param s
     * @return arboerele format
     */
    public static Node parseUpn(String s) {

        Stack<Node> stackRezultat = new Stack<>();
        Stack<Node> stackOperanzi = new Stack<>();
        NodeFactory fabrica = NodeFactory.getFactory();

        for (String token : s.split("\\s")) {
            if (!token.equals("")) {

                Node aux = map.get(token);
                Node node = fabrica.getNode(aux.getType());
                node.setName(aux.getName())
                        .setInfo(aux.getInfo());
                while (true) {
                    if (node.VerificaOperant()) {
                        stackRezultat.push(node);
                        break;
                    } else if (stackOperanzi.empty()) {
                        stackOperanzi.push(node);
                        break;
                    } else if (stackOperanzi.peek().VerificaPrioritate(node)) {
                        stackOperanzi.push(node);
                        break;
                    } else if (stackOperanzi.peek().Verifica(node)) {
                        Node rezultatpartial = stackOperanzi.pop();
                        rezultatpartial.right = stackRezultat.pop();
                        rezultatpartial.left = stackRezultat.pop();
                        stackRezultat.push(rezultatpartial);

                    } else if (node.getType().equals("opd")) {
                        stackOperanzi.push(node);
                        break;
                    } else if (node.getType().equals("opi")) {
                        while (!stackOperanzi.peek().getType().equals("opd")) {
                            Node rezultatpartial = stackOperanzi.pop();
                            rezultatpartial.right = stackRezultat.pop();
                            rezultatpartial.left = stackRezultat.pop();
                            stackRezultat.push(rezultatpartial);
                        }
                        stackOperanzi.pop();
                        break;
                    }
                }
            }
        }
        while (!stackOperanzi.empty()) {
            Node rezultatpartial = stackOperanzi.pop();
            rezultatpartial.right = stackRezultat.pop();
            rezultatpartial.left = stackRezultat.pop();
            stackRezultat.push(rezultatpartial);
        }
        return stackRezultat.pop();
    }

    private boolean VerificaPrioritate(Node x) {
        if (x.getType().equals("op*") || x.getType().equals("op/")) {
            if (this.getType().equals("op+") || this.getType().equals("op-")) {
                return true;
            }
        }
        return this.getType().equals("opd") && x.getType().equals("opi") == false;
    }

    private boolean VerificaOperant() {
        return !(this.getType().equals("op+")
                || this.getType().equals("op-")
                || this.getType().equals("op*")
                || this.getType().equals("op/")
                || this.getType().equals("opi")
                || this.getType().equals("opd"));
    }

    private boolean Verifica(Node x) {
        if (x.getType().equals("op+")) {
            if (this.getType().equals("op-")) {
                return true;
            }
        }
        if (x.getType().equals("op-")) {
            if (this.getType().equals("op+")) {
                return true;
            }
        }
        if (x.getType().equals("op+")) {
            if (this.getType().equals("op+")) {
                return true;
            }
        }
        if (x.getType().equals("op-")) {
            if (this.getType().equals("op-")) {
                return true;
            }
        }

        if (x.getType().equals("op-")) {
            if (this.getType().equals("op*")) {
                return true;
            }
        }
        if (x.getType().equals("op-")) {
            if (this.getType().equals("op/")) {
                return true;
            }
        }
        if (x.getType().equals("op+")) {
            if (this.getType().equals("op*")) {
                return true;
            }
        }
        if (x.getType().equals("op+")) {
            if (this.getType().equals("op/")) {
                return true;
            }
        }

        if (x.getType().equals("op/")) {
            if (this.getType().equals("op*")) {
                return true;
            }
        }
        if (x.getType().equals("op*")) {
            if (this.getType().equals("op/")) {
                return true;
            }
        }
        if (x.getType().equals("op*")) {
            if (this.getType().equals("op*")) {
                return true;
            }
        }
        if (x.getType().equals("op/")) {
            if (this.getType().equals("op/")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void accept(TreePartVisitor treeParVisitor) {
        treeParVisitor.visit(this);
    }

}
