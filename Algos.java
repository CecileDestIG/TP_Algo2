

import java.util.*;

public class Algos {

    public static boolean egalEnsembliste(ArrayList<?> a1, ArrayList<?> a2){
        //retourn vrai ssi les a1 à les même éléments que a2 (peut importe l'ordre)
        return a1.containsAll(a2) && a2.containsAll(a1);
    }


    public static Solution greedySolver(Instance i) {

        //calcule la solution obtenue en allant chercher à chaque étape la pièce restante la plus proche
        //(si plusieurs pièces sont à la même distance, on fait un choix arbitraire)

        return i.calculerSol(i.greedyPermut());
    }


    /*
    public static boolean algoTestFPT(InstanceDec id){
        boolean res = false;
        boolean res1 = false;
        boolean res2 = false;
        boolean res3 = false;
        boolean res4 = false;

        int b = 0;
        ArrayList<Coord> listePiece = new ArrayList<>(id.i.getListeCoordPieces());
        for(int i = 0; i<listePiece.size();i++){
            if (id.i.getStartingP().getC()==listePiece.get(i).getC() && id.i.getStartingP().getL()==listePiece.get(i).getL()){
                b=1;
            }
        }
        int npp = id.c - b;
        int kp = id.i.getK() - 1;

        if (npp == 0){
            return true;
        }
        if (id.i.getK()==0){
            return false;
        }
        //cas on se déplace vers la gauche
        if(id.i.getStartingP().getL() -1 > 0){
            System.out.println("gauche");
            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);
            System.out.println(new Coord(id.i.getStartingP().getL() - 1,id.i.getStartingP().getC()));
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL() - 1,id.i.getStartingP().getC()));
            instanceCopy.c=npp;
            instanceCopy.i.setK(kp);
            res1 = res || algoTestFPT(instanceCopy);
        }
        //cas on se déplace vers la droite
        if(id.i.getStartingP().getL() +1 < id.i.getNbL()){
            System.out.println("droite");
            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);

            System.out.println(new Coord(id.i.getStartingP().getL() + 1,id.i.getStartingP().getC()));
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL() + 1,id.i.getStartingP().getC()));
            instanceCopy.c=npp;
            instanceCopy.i.setK(kp);
            res2 = res || algoTestFPT(instanceCopy);
        }
        //cas on se déplace vers le bas
        if(id.i.getStartingP().getC() + 1 > id.i.getNbC()){
            System.out.println("bas");
            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);

            System.out.println(new Coord(id.i.getStartingP().getL(),id.i.getStartingP().getC() + 1));
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL(),id.i.getStartingP().getC() + 1));
            instanceCopy.c=npp;
            instanceCopy.i.setK(kp);
            res3 = res || algoTestFPT(instanceCopy);
        }
        //cas on se déplace vers le haut
        if(id.i.getStartingP().getC() -1 > 0){
            System.out.println("haut");
            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);
            System.out.println(new Coord(id.i.getStartingP().getL(),id.i.getStartingP().getC() - 1));
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL(),id.i.getStartingP().getC() - 1));
            instanceCopy.c=npp;
            instanceCopy.i.setK(kp);
            res4 = res || algoTestFPT(instanceCopy);
        }
        res = res1 || res2 || res3 || res4;
        return res;
    }
*/
    public static Solution algoFPT1(InstanceDec id) {
        //algorithme qui décide id (c'est à dire si opt(id.i) >= id.c) en branchant (en 4^k) dans les 4 directions pour chacun des k pas
        //retourne une solution de valeur >= c si une telle solution existe, et null sinon
        //Ne doit pas modifier le paramètre
        //Rappel : si c==0, on peut retourner la solution égale au point de départ puisque l'on est pas obligé d'utiliser les k pas
        // (on peut aussi retourner une solution plus longue si on veut)
        //Remarque : quand vous aurez codé la borneSup, pensez à l'utiliser dans cet algorithme pour ajouter un cas de base
        boolean res = false;
        Solution solution = null;


        if(id.c == 0){return new Solution(id.i.getStartingP());}

        if(id.i.getK() <= 0){return null;}

        if(id.i.getStartingP().getL() -1 > 0){

            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL() - 1,id.i.getStartingP().getC()));
            solution = algoFPT1(instanceCopy);
            if(solution == null){ return solution;}
        }

        if(id.i.getStartingP().getL() +1 < id.i.getNbL()){

            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL() + 1,id.i.getStartingP().getC()));
            solution = algoFPT1(instanceCopy);
            if(solution == null){ return solution;}
        }

        if(id.i.getStartingP().getC() -1 > 0){

            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL(),id.i.getStartingP().getC() - 1));
            solution = algoFPT1(instanceCopy);
            if(solution == null){ return solution;}
        }

        if(id.i.getStartingP().getC() + 1 > id.i.getNbC()){

            InstanceDec instanceCopy = new InstanceDec(new Instance(id.i),id.c);
            instanceCopy.i.setStartingP(new Coord(id.i.getStartingP().getL() - 1,id.i.getStartingP().getC() + 1));
            solution = algoFPT1(instanceCopy);
            if(solution == null){ return solution;}
        }








        return null;
    }




    public static Solution algoFPT1DP(InstanceDec id,  HashMap<InstanceDec,Solution> table) {
        //même spécification que algoFPT1, si ce n'est que
        // - si table.containsKey(id), alors id a déjà été calculée, et on se contente de retourner table.get(id)
        // - sinon, alors on doit calculer la solution s pour id, la ranger dans la table (table.put(id,res)), et la retourner

        //Remarques
        // - ne doit pas modifier l'instance id en param (mais va modifier la table bien sûr)
        // - même si le branchement est le même que dans algoFPT1, ne faites PAS appel à algoFPT1 (et donc il y aura de la duplication de code)


        //à compléter
        return null;
    }


    public static Solution algoFPT1DPClient(InstanceDec id){
        //si il est possible de collecter >= id.c pièces dans id.i, alors retourne une Solution de valeur >= c, sinon retourne null
        //doit faire appel à algoFPT1DP

        //à completer
        return null;

    }



}
