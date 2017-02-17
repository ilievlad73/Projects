
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ilie
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        NodeFactory fabrica = NodeFactory.getFactory();
        Node.InitializareTabela();
        BufferedReader in = null;
        in = new BufferedReader(new FileReader("arbore.in"));
        PrintWriter writer = new PrintWriter(new FileWriter("arbore.out")); //deschidem fisierul in care scriem
        String line;
        while ((line = in.readLine()) != null) {
            if (line.substring(0, 3).equals("int")) {
                String numar = line.substring(line.indexOf("=") + 2, line.indexOf(";"));
                String nume = line.substring(4, line.indexOf("=") - 1);
                Node.addToHashMap(nume, fabrica.getNode("int"), numar);
            } else if (line.substring(0, 6).equals("double")) {
                String numar = line.substring(line.indexOf("=") + 2, line.indexOf(";"));
                String nume = line.substring(7, line.indexOf("=") - 1);
                Node.addToHashMap(nume, fabrica.getNode("dou"), numar);
            } else if (line.substring(0, 6).equals("string")) {
                String numar = line.substring(line.indexOf("=") + 3, line.indexOf(";") - 1);
                String nume = line.substring(7, line.indexOf("=") - 1);
                Node.addToHashMap(nume, fabrica.getNode("str"), numar);
            } else if (line.subSequence(0, 4).equals("eval")) {
                String expresie = line.substring(4, line.indexOf(";"));
                Node radacina = Node.parseUpn(expresie);
                Tree copac = new Tree(radacina);
                copac.accept(new TreePartDisplayVisitor());

                if (!radacina.getType().equals("dou") || radacina.getInfo().equals("NaN")) {
                    writer.println(radacina.getInfo());
                } else {
                    writer.println(Double.toString(Math.round(Double.parseDouble(radacina.getInfo()) * 100.0) / 100.0));
                }
            }
        }
        in.close();
        writer.close();
    }
}
