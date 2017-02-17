
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class QuadTree {

    Node root = new Node();
    static double eroare;

    public class Node {

        double xjos, yjos;
        double xsus, ysus;
        Node NW, NE, SE, SW;                         // four subtrees
        ValueTree value;           // associated data

        Node(GeometricObject figura) {
            this();
            this.value.AddFigura(figura);
        }

        Node() {
            this.value = new ValueTree();
        }

        Node(ValueTree value) {
            this();
            for (int i = value.size() - 1; i >= 0; i--) {
                this.value.AddFigura(value.figuri.get(i));
            }

        }

        public void setCoordonate(double xjos, double yjos, double xsus, double ysus) {
            this.xjos = xjos;
            this.yjos = yjos;
            this.xsus = xsus;
            this.ysus = ysus;
        }
    }

    public QuadTree(double x1, double y1, double x2, double y2) {
        eroare = Math.abs((x2 - x1)) * 20 / 100;
    }

    public static void IntersectiePunct(Node nod, Array id_uri, double x1, double y1, double x2, double y2, Punct x) {
        if (nod.NE == null && nod.NW == null && nod.SE == null && nod.SW == null) {  //am ajuns la frunza
            for (int i = 0; i < nod.value.size(); i++) {
                if (nod.value.figuri.get(i).TestPunct(x.x, x.y) == true
                        && id_uri.verifica(nod.value.figuri.get(i).id) == false) {
                    id_uri.setValoare(nod.value.figuri.get(i).id);
                }
            }
        }
        if (x.testDreptunghi(x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2) == true && nod.SW != null) {
            IntersectiePunct(nod.SW, id_uri, x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2, x);
        }
        if (x.testDreptunghi(x1 + (x2 - x1) / 2, y1, x2, y1 + (y2 - y1) / 2) == true && nod.SE != null) {
            IntersectiePunct(nod.SE, id_uri, x1 + (x2 - x1) / 2, y1, x2, y1 + (y2 - y1) / 2, x);
        }
        if (x.testDreptunghi(x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2) == true && nod.NW != null) {
            IntersectiePunct(nod.NW, id_uri, x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2, x);
        }
        if (x.testDreptunghi(x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2) == true && nod.NE != null) {
            IntersectiePunct(nod.NE, id_uri, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2, x);
        }
    }

    public static void IntersectieFiguri(Node nod, Array id_uri, double x1, double y1, double x2, double y2, Rectangle figura) {
        if (nod.NE == null && nod.NW == null && nod.SE == null && nod.SW == null) {  //am ajuns la frunza
            for (int i = 0; i < nod.value.size(); i++) {
                //System.out.println(nod.value.figuri.get(i).CeSuntEu());
                if (nod.value.figuri.get(i).TestDreptunghi(figura.x1, figura.y1, figura.x2, figura.y2) == true
                        && id_uri.verifica(nod.value.figuri.get(i).id) == false) {
                    id_uri.setValoare(nod.value.figuri.get(i).id);
                }
            }
        }
        if (figura.suntInSW(x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2) == true && nod.SW != null) {
            IntersectieFiguri(nod.SW, id_uri, x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2, figura);
        }
        if (figura.suntInSE(x1 + (x2 - x1) / 2, y1, x2, y1 + (y2 - y1) / 2) == true && nod.SE != null) {
            IntersectieFiguri(nod.SE, id_uri, x1 + (x2 - x1) / 2, y1, x2, y1 + (y2 - y1) / 2, figura);
        }
        if (figura.suntInNW(x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2) == true && nod.NW != null) {
            IntersectieFiguri(nod.NW, id_uri, x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2, figura);
        }
        if (figura.suntInNE(x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2) == true && nod.NE != null) {
            IntersectieFiguri(nod.NE, id_uri, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2, figura);
        }
    }

    public void insert(Node h, double x1, double y1, double x2, double y2, GeometricObject figura) {

        if (figura.suntInSW(x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2) == true) {
            h.SW = insertDupaRoot(h.SW, x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2, figura);
        }
        if (figura.suntInSE(x1 + (x2 - x1) / 2, y1, x2, y1 + (y2 - y1) / 2) == true) {
            h.SE = insertDupaRoot(h.SE, x1 + (x2 - x1) / 2, y1, x2, y1 + (y2 - y1) / 2, figura);
        }
        if (figura.suntInNW(x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2) == true) {
            h.NW = insertDupaRoot(h.NW, x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2, figura);
        }
        if (figura.suntInNE(x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2) == true) {
            h.NE = insertDupaRoot(h.NE, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2, figura);
        }
    }

    public Node insertDupaRoot(Node h, double x1, double y1, double x2, double y2, GeometricObject figura) {
        if (h == null) {
            Node aux = new Node(figura);
            aux.setCoordonate(x1, y1, x2, y2);          //daca am ajuns la null construim frunza
            return aux;
        }
        if (figura.suntInNE(x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2) == true) {
            if (h.NE != null && h.NE.value.size() == 0) {
                h.NE = insertDupaRoot(h.NE, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2, figura);
            } else {
                if (h.value.verificaFigura(figura) == false) {
                    h.value.AddFigura(figura);
                }
                h = PrimaImpartire(h, x1, y1, x2, y2);
            }
        }
        if (figura.suntInNW(x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2) == true) {
            if (h.NW != null && h.NW.value.size() == 0) {
                h.NW = insertDupaRoot(h.NW, x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2, figura);
            } else {
                if (h.value.verificaFigura(figura) == false) {
                    h.value.AddFigura(figura);
                }
                h = PrimaImpartire(h, x1, y1, x2, y2);
            }
        }
        if (figura.suntInSE(x1 + (x2 - x1) / 2, y1, x2, y2 / 2) == true) {
            if (h.SE != null && h.SE.value.size() == 0) {
                h.SE = insertDupaRoot(h.SE, x1 + (x2 - x1) / 2, y1, x2, y2 / 2, figura);
            } else {
                if (h.value.verificaFigura(figura) == false) {
                    h.value.AddFigura(figura);
                }
                h = PrimaImpartire(h, x1, y1, x2, y2);
            }

        }
        if (figura.suntInSW(x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2) == true) {
            if (h.SW != null && h.SW.value.size() == 0) {
                h.SW = insertDupaRoot(h.SW, x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2, figura);
            } else {
                if (h.value.verificaFigura(figura) == false) {
                    h.value.AddFigura(figura);
                }

                h = PrimaImpartire(h, x1, y1, x2, y2);
            }
        }
        return h;
    }

    public Node PrimaImpartire(Node initial, double x1, double y1, double x2, double y2) {
        Node aux = initial;
        int lungime = initial.value.size();
        ValueTree incercam = new ValueTree();
        incercam = initial.value.transfer();
        aux.value.goleste();

        for (int i = 0; i < incercam.size(); i++) {
            if (incercam.figuri.get(i).suntInSW(x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2) == true) {
                aux.SW = inserareSimpla(aux.SW, x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2, incercam.figuri.get(i));
            }
            if (incercam.figuri.get(i).suntInSE(x1 + (x2 - x1) / 2, y1, x2, y2 - (y2 - y1) / 2) == true) {
                aux.SE = inserareSimpla(aux.SE, x1 + (x2 - x1) / 2, y1, x2, y2 - (y2 - y1) / 2, incercam.figuri.get(i));
            }
            if (incercam.figuri.get(i).suntInNW(x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2) == true) {
                aux.NW = inserareSimpla(aux.NW, x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2, incercam.figuri.get(i));
            }
            if (incercam.figuri.get(i).suntInNE(x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2) == true) {
                aux.NE = inserareSimpla(aux.NE, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2, incercam.figuri.get(i));
            }

        }

        if (Math.abs(x2 - x1) < eroare || areRost(aux) == true) {
            Node ceva = new Node(incercam);
            ceva.setCoordonate(x1, y1, x2, y2);
            return ceva;
        }

        if (aux.NE != null && aux.NE.value != null && aux.value.size() > 0) {
            aux.NE = PrimaImpartire(aux.NE, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2, x2, y2);
        }
        if (aux.NW != null && aux.NW.value != null && aux.value.size() > 0) {
            aux.NW = PrimaImpartire(aux.NW, x1, y1 + (y2 - y1) / 2, x2 - (x2 - x1) / 2, y2);
        }
        if (aux.SW != null && aux.SW.value != null && aux.value.size() > 0) {
            aux.SW = PrimaImpartire(aux.SW, x1, y1, x2 - (x2 - x1) / 2, y2 - (y2 - y1) / 2);
        }
        if (aux.SE != null && aux.SE.value != null && aux.value.size() > 0) {
            aux.SE = PrimaImpartire(aux.SE, x1 + (x2 - x1) / 2, y1, x2, y2 - (y2 - y1) / 2);
        }
        return aux;
    }

    public boolean areRostAdaugare(Node nod, int lungime) {
        return (nod.NE == null || (nod.NE.value.size() == lungime))
                && (nod.NW == null || (nod.NW.value.size() == lungime))
                && (nod.SE == null || (nod.SE.value.size() == lungime))
                && (nod.SW == null || (nod.SW.value.size() == lungime));
    }

    public Node inserareSimpla(Node h, double x1, double y1, double x2, double y2, GeometricObject figura) {
        if (h == null) {
            Node aux = new Node(figura);
            aux.setCoordonate(x1, y1, x2, y2);
            return aux;
        } else if (h.value.verificaFigura(figura) == false) {
            h.value.AddFigura(figura);
        }
        return h;
    }

    public void afisareTreeDFS(Node root) {
        if (root != null) {
            // System.out.println("intra");
            System.out.println(root.value.toString());
            afisareTreeDFS(root.NE);
            afisareTreeDFS(root.NW);
            afisareTreeDFS(root.SE);
            afisareTreeDFS(root.SW);
        }
    }

    public void afisareTreeBFS(Node root) {
        if (root == null) {
            System.out.println("Empty tree");
        } else {
            Queue<Node> q = new LinkedList<>();
            q.clear();
            q.add(root);
            while (!q.isEmpty()) {
                Node temp = q.remove();
                System.out.print(temp.xjos + "  " + temp.yjos + "  " + temp.xsus + "  " + temp.ysus);
                System.out.println(temp.value.toString());
                if (temp.NE != null) {
                    q.add(temp.NE);
                }
                if (temp.NW != null) {
                    q.add(temp.NW);
                }
                if (temp.SW != null) {
                    q.add(temp.SW);
                }
                if (temp.SE != null) {
                    q.add(temp.SE);
                }

            }
        }
    }

    public boolean areRost(Node nod) {

        ArrayList<Node> noduri = new ArrayList<>();
        if (nod.NE != null) {
            noduri.add(nod.NE);
        }
        if (nod.SE != null) {
            noduri.add(nod.SE);
        }
        if (nod.SW != null) {
            noduri.add(nod.SW);
        }
        if (nod.NW != null) {
            noduri.add(nod.NW);
        }
        for (int i = 0; i < noduri.size() - 1; i++) {
            if (ValueTree.verificaListe(noduri.get(i).value, noduri.get(i + 1).value) == false) {
                return false;
            }
        }
        return true;
    }

    public void Stergere(Node root, int id) {
        if (root == null) {
            return;
        }
        if (root.value.findId(id) != -1) {        // cautam figura dupa id
            root.value.seteazaId(id);           // punem pe 0 id-ul
        }
        Stergere(root.NE, id);
        if (root.NE != null) {
            if (root.NE.NE == null && root.NE.NW == null && root.NE.SE == null && root.NE.SW == null // daca e frunza
                    && root.NE.value.findId(0) != -1 && root.NE.value.size() == 1) {
                root.NE = null;
            } else if (root.NE.NE == null && root.NE.NW == null && root.NE.SE == null && root.NE.SW == null // daca e pe o frunza dar frunza mai are ceva in ea
                    && root.NE.value.findId(0) != -1 && root.NE.value.size() > 1) {
                root.NE.value.removeDupaId(0);

            } else if (areRost(root.NE) == true) {
                if (root.NE.NE != null) {
                    Node aux = root.NE;
                    root.NE = root.NE.NE;
                    root.NE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.NE.NW != null) {
                    Node aux = root.NE;
                    root.NE = root.NE.NW;
                    root.NE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.NE.SE != null) {
                    Node aux = root.NE;
                    root.NE = root.NE.SE;
                    root.NE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.NE.SW != null) {
                    Node aux = root.NE;
                    root.NE = root.NE.SW;
                    root.NE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
            }
        }
        Stergere(root.NW, id);
        if (root.NW != null) {
            if (root.NW.NE == null && root.NW.NW == null && root.NW.SE == null && root.NW.SW == null // daca e frunza
                    && root.NW.value.findId(0) != -1 && root.NW.value.size() == 1) {
                root.NW = null;
            } else if (root.NW.NE == null && root.NW.NW == null && root.NW.SE == null && root.NW.SW == null // daca e pe o frunza dar frunza mai are ceva in ea
                    && root.NW.value.findId(0) != -1 && root.NW.value.size() > 1) {
                root.NW.value.removeDupaId(0);
            } else if (areRost(root.NW) == true) {
                if (root.NW.NE != null) {
                    Node aux = root.NW;
                    root.NW = root.NW.NE;
                    root.NW.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.NW.NW != null) {
                    Node aux = root.NW;
                    root.NW = root.NW.NW;
                    root.NW.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.NW.SE != null) {
                    Node aux = root.NW;
                    root.NW = root.NW.SE;
                    root.NW.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.NW.SW != null) {
                    Node aux = root.NW;
                    root.NW = root.NW.SW;
                    root.NW.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
            }
        }
        Stergere(root.SW, id);
        if (root.SW != null) {
            if (root.SW.NE == null && root.SW.NW == null && root.SW.SE == null && root.SW.SW == null // daca e frunza
                    && root.SW.value.findId(0) != -1 && root.SW.value.size() == 1) {
                root.SW = null;
            } else if (root.SW.NE == null && root.SW.NW == null && root.SW.SE == null && root.SW.SW == null // daca e pe o frunza dar frunza mai are ceva in ea
                    && root.SW.value.findId(0) != -1 && root.SW.value.size() > 1) {
                root.SW.value.removeDupaId(0);
            } else if (areRost(root.SW) == true) {
                if (root.SW.NE != null) {
                    Node aux = root.SW;
                    root.SW = root.SW.NE;
                    root.SW.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.SW.NW != null) {
                    Node aux = root.SW;
                    root.SW = root.SW.NW;
                    root.SW.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.SW.SE != null) {
                    Node aux = root.SW;
                    root.SW = root.SW.SE;
                    root.SW.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.NE.SW != null) {
                    Node aux = root.NE;
                    root.NE = root.NE.SW;
                    root.NE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
            }
        }
        Stergere(root.SE, id);
        if (root.SE != null) {
            if (root.SE.NE == null && root.SE.NW == null && root.SE.SE == null && root.SE.SW == null // daca e frunza
                    && root.SE.value.findId(0) != -1 && root.SE.value.size() == 1) {
                root.SE = null;
            } else if (root.SE.NE == null && root.SE.NW == null && root.SE.SE == null && root.SE.SW == null // daca e pe o frunza dar frunza mai are ceva in ea
                    && root.SE.value.findId(0) != -1 && root.SE.value.size() > 1) {
                root.SE.value.removeDupaId(0);
            } else if (areRost(root.SE) == true) {
                if (root.SE.NE != null) {
                    Node aux = root.SE;
                    root.SE = root.SE.NE;
                    root.SE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.SE.NW != null) {
                    Node aux = root.SE;
                    root.SE = root.SE.NW;
                    root.SE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.SE.SE != null) {
                    Node aux = root.SE;
                    root.SE = root.SE.SE;
                    root.SE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
                if (root.SE.SW != null) {
                    Node aux = root.SE;
                    root.SE = root.SE.SW;
                    root.SE.setCoordonate(aux.xjos, aux.yjos, aux.xsus, aux.ysus);
                }
            }
        }
    }

}
