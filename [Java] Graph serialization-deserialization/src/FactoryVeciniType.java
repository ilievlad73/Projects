/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilie
 */
public class FactoryVeciniType {

    public static FactoryVeciniType INSTANCE = null;

    /**
     * Functie care returneaza modalitatea in care vom stoca vecinii
     *
     * @param versiune
     * @return
     */
    public VeciniType getVecini(int versiune) {
        switch (versiune) {
            case 1:
                return new Nod_list();
            case 2:
                return new Nod_vector();
            case 3:
                return new Nod_set();
        }
        return null;
    }

    private FactoryVeciniType() {

    }

    public static FactoryVeciniType getFactory() {
        if (INSTANCE == null) {
            INSTANCE = new FactoryVeciniType();
        }
        return INSTANCE;
    }
}
