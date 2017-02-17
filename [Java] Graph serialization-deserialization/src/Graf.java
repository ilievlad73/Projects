
import java.io.BufferedReader;
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
public class Graf {

    private ArrayList<NodABC> noduri = new ArrayList<>();
    private static int id_curent = 0;

    public ArrayList<NodABC> getNoduri() {
        return noduri;
    }

    public static void setId(int id) {
        id_curent = id;
    }

    /**
     * Se adauga la graf un nou nod cu tipul type valoarea de pe nod = nume si
     * prin legaturi legam nodul la restul grafului
     *
     * @param type
     * @param nume
     * @param legaturi
     */
    public void addNod(String type, String nume, ArrayList<String> legaturi) {

        FactoryNodABC NodFactory = FactoryNodABC.getFactory();
        NodABC nou = NodFactory.getNodeABC(type);
        nou.setId(id_curent++);
        nou.setNume(nume);
        noduri.add(nou);

        for (int j = 0; j < legaturi.size(); j++) {
            NodABC aux = getNode(legaturi.get(j));
            noduri.get(noduri.size() - 1).addVecini(aux);
            aux.addVecini(noduri.get(noduri.size() - 1));
        }

    }

    /**
     * Se returneaza nodul cu numele primit ca parametru
     *
     * @param nume
     * @return
     */
    public NodABC getNode(String nume) {
        for (int i = 0; i < noduri.size(); i++) {
            if (noduri.get(i).getNume().equals(nume)) {
                return noduri.get(i);
            }
        }
        return null;
    }

    /**
     * Se adauga muchie intre doua noduri in functie de nume
     *
     * @param node1
     * @param node2
     */
    public void AdaugaMuchie(String node1, String node2) {
        NodABC nod1 = null, nod2 = null;
        for (int i = 0; i < noduri.size(); i++) {
            if (noduri.get(i).getNume().equals(node1)) {
                nod1 = noduri.get(i);
            }
            if (noduri.get(i).getNume().equals(node2)) {
                nod2 = noduri.get(i);
            }
        }
        if (nod1 != null && nod1.VerificaExistenta(nod2) == false) {
            nod1.addVecini(nod2);
        }
        if (nod2 != null && nod2.VerificaExistenta(nod1) == false) {
            nod2.addVecini(nod1);
        }
    }

    /**
     * se adauga o muchie noua in graf in functie de id-ul nodurilor
     *
     * @param node1
     * @param node2
     */
    public void AdaugaMuchieDupaId(int node1, int node2) {
        NodABC nod1 = null, nod2 = null;
        for (int i = 0; i < noduri.size(); i++) {
            if (noduri.get(i).getId() == node1) {
                nod1 = noduri.get(i);
            }
            if (noduri.get(i).getId() == node2) {
                nod2 = noduri.get(i);
            }
        }

        if (nod1 != null && nod1.VerificaExistenta(nod2) == false) {
            nod1.addVecini(nod2);
        }
        if (nod2 != null && nod2.VerificaExistenta(nod1) == false) {
            nod2.addVecini(nod1);
        }
    }

    /**
     * Se sterge o muchie in functie de numele nodurilor
     *
     * @param node1
     * @param node2
     */
    public void StergeMuchie(String node1, String node2) {
        NodABC nod1 = null, nod2 = null;
        for (int i = 0; i < noduri.size(); i++) {
            if (noduri.get(i).getNume().equals(node1)) {
                nod1 = noduri.get(i);
            } else if (noduri.get(i).getNume().equals(node2)) {
                nod2 = noduri.get(i);
            }
        }
        if (nod1 != null) {
            nod1.remove(node2);
        }
        if (nod2 != null) {
            nod2.remove(node1);
        }
    }

    /**
     * Se sterge un nod din graf
     *
     * @param nume
     */
    public void StergeNod(String nume) {
        for (int i = 0; i < noduri.size(); i++) {
            if (noduri.get(i).getNume().equals(nume)) {
                noduri.remove(i);
                break;
            }
        }
        for (int i = 0; i < noduri.size(); i++) {
            noduri.get(i).remove(nume);
        }
    }

    /**
     * Se realizeaza serializarea recursiv folosindu-se parcurgerea in adancime
     * . Variabila taburi ne ajuta sa identam graful serializat
     *
     * @param taburi
     * @param a
     * @param writer
     */
    public void Serializare(String taburi, NodABC a, PrintWriter writer) {
        writer.println(taburi + "<Object class=\"" + a.typeNode() + "\" Version=\"" + a.getVersiune() + "\" id=\"" + a.getId() + "\">");
        writer.println(taburi + "\t" + "<Nume>" + a.getNume() + "</Nume>");
        writer.println(taburi + "\t<" + a.getTypeVecini() + ">");
        a.setVizitat(true);
        for (int i = 0; i < a.nrVecini(); i++) {
            NodABC aux = a.getVecinul(i);
            if (aux.getVizitat()) {
                writer.println(taburi + "\t" + "\t" + "<Reference class=\"" + aux.typeNode() + "\" Version=\"" + aux.getVersiune() + "\" id=\"" + aux.getId() + "\">");
            } else if (!aux.getVizitat()) {
                Serializare(taburi + "\t" + "\t", aux, writer);
                aux.setVizitat(true);
            }
        }
        writer.println(taburi + "\t" + "</" + a.getTypeVecini() + ">");
        writer.println(taburi + "</Object>");
    }

    /**
     * Functie auxiliara folosita la deserializare adauga un nod nou cu nume si
     * id. Se deosebeste de functia addNod deoarece seteaza id-ul primit ca
     * param. addNod pune id-urile folosindu-se de variabila statica a clasei
     *
     * @param type
     * @param id
     * @param nume
     */
    public void addNodDeserializare(String type, int id, String nume) {
        FactoryNodABC NodFactory = FactoryNodABC.getFactory();
        NodABC nou = NodFactory.getNodeABC(type);
        nou.setId(id);
        nou.setNume(nume);
        noduri.add(nou);

    }

    /**
     * Functia care realizeaza deserializarea.Pleaca de la un nod si merge
     * recursiv in functie de LIST,VECTOR,SET. Iese din recursivitate in
     * momentul in care gaseste /List,/Vector sau /Set creaza si fisierul de
     * log-uri. Schimba si versiunea daca se poate prin intermediul
     * NodAv,NodBv,NodCv
     *
     * @param in
     * @param nod_curent
     * @param writer
     * @param NodAv
     * @param NodBv
     * @param NodCv
     * @throws IOException
     */
    public void Deserializare(BufferedReader in, int nod_curent, PrintWriter writer, int NodAv, int NodBv, int NodCv) throws IOException {
        String line;
        if (nod_curent == 0) {       //introducem primul nod in lista de noduri
            line = in.readLine();
            String typeNod = line.substring(15, 19);
            int id = Integer.parseInt(line.substring(37, line.length() - 2));
            line = in.readLine();
            String linie = line.trim();
            String nume = linie.substring(6, linie.length() - 7);
            if (typeNodeCurent(typeNod) > typeNodeTrecut(typeNod, NodAv, NodBv, NodCv)) {
                writer.println("OK cast " + typeNod + " " + nume + " from Version=\"" + typeNodeTrecut(typeNod, NodAv, NodBv, NodCv) + "\"  to Version=\"" + typeNodeCurent(typeNod) + "\"");
            }
            if (typeNodeCurent(typeNod) < typeNodeTrecut(typeNod, NodAv, NodBv, NodCv)) {
                writer.println("Fail cast " + typeNod + " " + nume + " from Version=\"" + typeNodeTrecut(typeNod, NodAv, NodBv, NodCv) + "\"  to Version=\"" + typeNodeCurent(typeNod) + "\"");
            }
            addNodDeserializare(typeNod, id, nume);
        }
        while ((line = in.readLine()) != null) {
            String linie = line.trim();
            String set = linie.substring(1, linie.length() - 1);
            if (set.equals("/LIST") || set.equals("/VECTOR") || set.equals("/SET")) {
                break;
            }
            if (set.equals("LIST") || set.equals("VECTOR") || set.equals("SET")) {
                Deserializare(in, nod_curent + 1, writer, NodAv, NodBv, NodCv);
            } else {
                String referinta_obiect = linie.substring(1, 4);
                if (referinta_obiect.equals("Obj")) {
                    String typeNod = linie.substring(15, 19);
                    int id = Integer.parseInt(linie.substring(37, linie.length() - 2));
                    line = in.readLine();
                    linie = line.trim();
                    String nume = linie.substring(6, linie.length() - 7);

                    if (typeNodeCurent(typeNod) > typeNodeTrecut(typeNod, NodAv, NodBv, NodCv)) {
                        writer.println("OK cast " + typeNod + " " + nume + " from Version=\"" + typeNodeTrecut(typeNod, NodAv, NodBv, NodCv) + "\"  to Version=\"" + typeNodeCurent(typeNod) + "\"");
                    }
                    if (typeNodeCurent(typeNod) < typeNodeTrecut(typeNod, NodAv, NodBv, NodCv)) {
                        writer.println("Fail cast " + typeNod + " " + nume + " from Version=\"" + typeNodeTrecut(typeNod, NodAv, NodBv, NodCv) + "\"  to Version=\"" + typeNodeCurent(typeNod) + "\"");
                    }

                    addNodDeserializare(typeNod, id, nume);
                    this.AdaugaMuchieDupaId(id, this.noduri.get(nod_curent - 1).getId());
                }
                if (referinta_obiect.equals("Ref")) {
                    int id = Integer.parseInt(linie.substring(40, linie.length() - 2));
                    this.AdaugaMuchieDupaId(id, this.noduri.get(nod_curent - 1).getId());
                }
            }

        }
    }

    /**
     * Functie auxiliara pentru deserializare folosita pentru a schimba
     * versiunea. si pentru a genera fisierul de log-uri.
     *
     * @param abc
     * @param NodAv
     * @param NodBv
     * @param NodCv
     * @return
     */
    public int typeNodeTrecut(String abc, int NodAv, int NodBv, int NodCv) {
        switch (abc) {
            case "NodA":
                return NodAv;
            case "NodB":
                return NodBv;
            case "NodC":
                return NodCv;
        }
        return 0;
    }

    /**
     *
     * @param abc
     * @return versiunea curenta a nodului
     */
    public int typeNodeCurent(String abc) {
        switch (abc) {
            case "NodA":
                return NodA.getVersiuneCurenta();
            case "NodB":
                return NodB.getVersiuneCurenta();
            case "NodC":
                return NodC.getVersiuneCurenta();
        }
        return 0;
    }

    /**
     * Functie auxiliara folosita pentru a schimba versiunea
     *
     * @param abc
     * @param NodAv
     * @param NodBv
     * @param NodCv
     */
    public void SchimbaTypeNodeViitor(String abc, int NodAv, int NodBv, int NodCv) {
        switch (abc) {
            case "NodA":
                NodA.setVersiune(NodAv);
                break;
            case "NodB":
                NodB.setVersiune(NodBv);
                break;
            case "NodC":
                NodC.setVersiune(NodCv);
                break;
        }

    }
}
