
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class test {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {

        File f = new File("quadtree.in");
        Scanner in = new Scanner(f);

        PrintWriter writer = new PrintWriter("quadtree.out"); //deschidem fisierul in care scriem

        String line = in.nextLine();
        String delims = "[ ]+";
        String[] tokens = line.split(delims);       // punem mana pe coordonatele hartii
        double x1 = Double.parseDouble(tokens[0]);
        double y1 = Double.parseDouble(tokens[1]);
        double x2 = Double.parseDouble(tokens[2]);
        double y2 = Double.parseDouble(tokens[3]);
        QuadTree q = new QuadTree(x1, y1, x2, y2);
        while (in.hasNext()) {
            line = in.nextLine();
            tokens = line.split(delims);
            if (tokens[0].equals("11")) {   //inserare

                if (tokens[1].equals("1")) {    //dreptunghi
                    Rectangle r = new Rectangle(Integer.parseInt(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Double.parseDouble(tokens[5]),
                            Double.parseDouble(tokens[6]));
                    q.insert(q.root, x1, y1, x2, y2, r);
                }
                if (tokens[1].equals("2")) {    //triunghi
                    Triangle t = new Triangle(Integer.parseInt(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Double.parseDouble(tokens[5]),
                            Double.parseDouble(tokens[6]),
                            Double.parseDouble(tokens[7]),
                            Double.parseDouble(tokens[8]));
                    q.insert(q.root, x1, y1, x2, y2, t);
                }
                if (tokens[1].equals("3")) {    //cerc
                    Cerc c = new Cerc(Integer.parseInt(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Double.parseDouble(tokens[5]));
                    q.insert(q.root, x1, y1, x2, y2, c);
                }
                if (tokens[1].equals("4")) {    //romb
                    Romb r = new Romb(Integer.parseInt(tokens[2]),
                            Double.parseDouble(tokens[7]),
                            Double.parseDouble(tokens[8]),
                            Double.parseDouble(tokens[5]),
                            Double.parseDouble(tokens[6]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Double.parseDouble(tokens[9]),
                            Double.parseDouble(tokens[10]));
                    q.insert(q.root, x1, y1, x2, y2, r);
                }
            }
            if (tokens[0].equals("22")) {
                q.Stergere(q.root, Integer.parseInt(tokens[1]));
            }
            if (tokens[0].equals("33")) {   //intersectia cu puncte
                Punct x = new Punct(Double.parseDouble(tokens[1]), Double.parseDouble(tokens[2]));
                Array id_uri = new Array(100000);
                QuadTree.IntersectiePunct(q.root, id_uri, x1, y1, x2, y2, x);
                id_uri.sortare();
                id_uri.PrintForward(writer);
            }
            if (tokens[0].equals("44")) {

                if (tokens[1].equals("1")) {    //dreptunghi
                    Rectangle r = new Rectangle(0,
                            Double.parseDouble(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Double.parseDouble(tokens[5]));
                    Array id_uri = new Array(100000);
                    QuadTree.IntersectieFiguri(q.root, id_uri, x1, y1, x2, y2, r);
                    id_uri.sortare();
                    id_uri.PrintForward(writer);
                }
                if (tokens[1].equals("2")) {    //triunghi
                    Triangle t = new Triangle(0,
                            Double.parseDouble(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]),
                            Double.parseDouble(tokens[5]),
                            Double.parseDouble(tokens[6]),
                            Double.parseDouble(tokens[7]));
                    Array id_uri = new Array(100000);
                    QuadTree.IntersectieFiguri(q.root, id_uri, x1, y1, x2, y2, t.dreptunghi_incadrator);
                    id_uri.sortare();
                    id_uri.PrintForward(writer);
                }
                if (tokens[1].equals("3")) {    //cerc
                    Cerc c = new Cerc(0,
                            Double.parseDouble(tokens[2]),
                            Double.parseDouble(tokens[3]),
                            Double.parseDouble(tokens[4]));
                    Array id_uri = new Array(100000);
                    QuadTree.IntersectieFiguri(q.root, id_uri, x1, y1, x2, y2, c.dreptunghi_incadrator);
                    id_uri.sortare();
                    id_uri.PrintForward(writer);
                }
                if (tokens[1].equals("4")) {    //romb
                    Romb r = new Romb(0,
                            Double.parseDouble(tokens[6]), //x1
                            Double.parseDouble(tokens[7]), //y1
                            Double.parseDouble(tokens[4]), //x2
                            Double.parseDouble(tokens[5]), //y2
                            Double.parseDouble(tokens[2]), //x3
                            Double.parseDouble(tokens[3]), //y3
                            Double.parseDouble(tokens[8]), //x4
                            Double.parseDouble(tokens[9])); //y4
                    Array id_uri = new Array(100000);
                    QuadTree.IntersectieFiguri(q.root, id_uri, x1, y1, x2, y2, r.dreptunghi_incadrator);
                    id_uri.sortare();
                    id_uri.PrintForward(writer);
                }
            }
        }
        in.close();
        writer.close();
        Cerc c = new Cerc(0,57.0, 430.0, -323.0);
        //System.out.println(c.TestPunct(475, -294));
       // q.afisareTreeBFS(q.root);
    }
}
