/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class FactoryNodABC {

    public static FactoryNodABC INSTANCE = null;

    /**
     * Functia care ne retureaza un NodABC NodA,NodB sau NodC
     *
     * @param NodType
     * @return
     */
    public NodABC getNodeABC(String NodType) {
        switch (NodType) {
            case "NodA":
                return new NodA();
            case "NodB":
                return new NodB();
            case "NodC":
                return new NodC();

        }
        return null;
    }

    private FactoryNodABC() {

    }

    public static FactoryNodABC getFactory() {
        if (INSTANCE == null) {
            INSTANCE = new FactoryNodABC();
        }
        return INSTANCE;
    }
}
