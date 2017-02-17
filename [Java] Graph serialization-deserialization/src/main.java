
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        Graf g = new Graf();            //graful pe care vom face operatiile
        ArrayList<String> legaturi = new ArrayList<>();         //arraylistul unde vom pune legaturile

        BufferedReader in_settings = new BufferedReader(new FileReader(args[0]));
        String line = in_settings.readLine();       //citim setarile initiale la inceput
        String delims = " ";
        String[] tokens = line.split(delims);
        NodA.setVersiune(Integer.parseInt(tokens[1]));
        NodB.setVersiune(Integer.parseInt(tokens[2]));
        NodC.setVersiune(Integer.parseInt(tokens[3]));
        //NodAv,NodBv,NodCv vor fi setarile curente
        int NodAv = Integer.parseInt(tokens[1]);
        int NodBv = Integer.parseInt(tokens[2]);
        int NodCv = Integer.parseInt(tokens[3]);

        while ((line = in_settings.readLine()) != null) {
            tokens = line.split(delims);
            switch (tokens[0]) {
                case "Settings":
                    //salvam setarile anterioare
                    NodAv = NodA.getVersiuneCurenta();
                    NodBv = NodB.getVersiuneCurenta();
                    NodCv = NodC.getVersiuneCurenta();
                    //facem castul automat
                    NodA.setVersiune(Integer.parseInt(tokens[1]));
                    NodB.setVersiune(Integer.parseInt(tokens[2]));
                    NodC.setVersiune(Integer.parseInt(tokens[3]));
                    break;
                case "AddM":
                    g.AdaugaMuchie(tokens[1], tokens[2]);
                    break;
                case "Add":
                    String typeNod = tokens[1];
                    String nume = tokens[2];
                    for (int i = 3; i < tokens.length; i++) //adaugam legaturile
                    {
                        legaturi.add(tokens[i]);
                    }
                    g.addNod(typeNod, nume, legaturi);
                    legaturi.clear();
                    break;
                case "Del":
                    g.StergeNod(tokens[2]);
                    break;
                case "DelM":
                    g.StergeMuchie(tokens[1], tokens[2]);
                    break;
                case "Serialize": {
                    String NodulDePlecare = tokens[1];
                    String numeFisier = tokens[2];
                    PrintWriter writer = new PrintWriter(new FileWriter(numeFisier));
                    g.Serializare("", g.getNode(NodulDePlecare), writer);
                    writer.close();
                    break;
                }
                case "Deserialize": {
                    String numeFisier = tokens[1];  //pregatim fisierele
                    BufferedReader in = new BufferedReader(new FileReader(numeFisier));
                    String numeFisierLog = "Deserializare_" + numeFisier + "_CAST.log";
                    PrintWriter writer2 = new PrintWriter(new FileWriter(numeFisierLog));
                    g.getNoduri().clear(); // distrugem graful vechi
                    Graf.setId(0); 
                    g.Deserializare(in, 0, writer2, NodAv, NodBv, NodCv);
                    in.close();
                    writer2.close();
                    break;
                }
                default:
                    break;
            }
        }
    }

}
