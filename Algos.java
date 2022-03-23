

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
        if(id.i.borneSup()<id.c){return null;};

        boolean res = false;
        Solution solution = null;
        InstanceDec idMainCopy = new InstanceDec(new Instance(id.i),id.c);

        //Sommes nous sur une pièce ?
        if(idMainCopy.i.piecePresente(idMainCopy.i.getStartingP())){
            idMainCopy.c = idMainCopy.c - 1;
            idMainCopy.i.retirerPiece(idMainCopy.i.getStartingP());
        }

        //On a toutes les pièces
        if(idMainCopy.c <= 0){
            return new Solution(idMainCopy.i.getStartingP());
        }

        //On a plus de pas, retourner la courante non?
        if(idMainCopy.i.getK() <= 0){
            return null;
        }

        //Si on peux aller en haut
        if(idMainCopy.i.getStartingP().getL() -1 >= 0){

            InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
            //Move and decrement the nb of steps
            instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL() - 1,idMainCopy.i.getStartingP().getC()));
            instanceCopy.i.setK(instanceCopy.i.getK()-1);
            solution = algoFPT1(instanceCopy);
            if(solution != null){
                solution.add(0,idMainCopy.i.getStartingP());
                return solution;
            }
        }
        //Si on peux aller en bas
        if(idMainCopy.i.getStartingP().getL() +1 < idMainCopy.i.getNbL()){

            InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
            //Move and decrement the nb of steps
            instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL() + 1,idMainCopy.i.getStartingP().getC()));
            instanceCopy.i.setK(instanceCopy.i.getK()-1);
            solution = algoFPT1(instanceCopy);

            System.out.println("nb piece copy :" + instanceCopy.c);
            if(solution != null){
                solution.add(0,idMainCopy.i.getStartingP());
                return solution;
            }
        }

        //Si on peux aller en à gauche
        if(idMainCopy.i.getStartingP().getC() -1 >= 0){

            InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
            //Move and decrement the nb of steps
            instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL(),idMainCopy.i.getStartingP().getC() -1));
            instanceCopy.i.setK(instanceCopy.i.getK()-1);
            solution = algoFPT1(instanceCopy);

            if(solution != null){
                solution.add(0,idMainCopy.i.getStartingP());
                return solution;
            }
        }

        //Si on peux aller en bas
        if(idMainCopy.i.getStartingP().getC() + 1 > idMainCopy.i.getNbC()){

            InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
            //Move and decrement the nb of steps
            instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL() - 1,idMainCopy.i.getStartingP().getC() +1));
            instanceCopy.i.setK(instanceCopy.i.getK()-1);
            solution = algoFPT1(instanceCopy);
            if(solution != null){
                solution.add(0,idMainCopy.i.getStartingP());
                return solution;
            }
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


        if(table.containsKey(id)){
            System.out.println("already computed");
            return table.get(id);
        }
        else{
            if(id.i.borneSup()<id.c){
                table.put(id,null);
                return null;
            };

            boolean res = false;
            Solution solution = null;
            InstanceDec idMainCopy = new InstanceDec(new Instance(id.i),id.c);

            //Sommes nous sur une pièce ?
            if(idMainCopy.i.piecePresente(idMainCopy.i.getStartingP())){
                idMainCopy.c = idMainCopy.c - 1;
                idMainCopy.i.retirerPiece(idMainCopy.i.getStartingP());
            }

            //On a toutes les pièces
            if(idMainCopy.c <= 0){
                table.put(id,new Solution(idMainCopy.i.getStartingP()));
                return new Solution(idMainCopy.i.getStartingP());
            }

            //On a plus de pas, retourner la courante non?
            if(idMainCopy.i.getK() <= 0){
                table.put(id,null);
                return null;
            }

            //Si on peux aller en haut
            if(idMainCopy.i.getStartingP().getL() -1 >= 0){

                InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
                //Move and decrement the nb of steps
                instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL() - 1,idMainCopy.i.getStartingP().getC()));
                instanceCopy.i.setK(instanceCopy.i.getK()-1);
                solution = algoFPT1DP(instanceCopy,table);
                if(solution != null){
                    solution.add(0,idMainCopy.i.getStartingP());
                    table.put(id,solution);
                    return solution;
                }
            }
            //Si on peux aller en bas
            if(idMainCopy.i.getStartingP().getL() +1 < idMainCopy.i.getNbL()){

                InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
                //Move and decrement the nb of steps
                instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL() + 1,idMainCopy.i.getStartingP().getC()));
                instanceCopy.i.setK(instanceCopy.i.getK()-1);
                solution = algoFPT1DP(instanceCopy,table);

                System.out.println("nb piece copy :" + instanceCopy.c);
                if(solution != null){
                    solution.add(0,idMainCopy.i.getStartingP());
                    table.put(id,solution);
                    return solution;
                }
            }

            //Si on peux aller en à gauche
            if(idMainCopy.i.getStartingP().getC() -1 >= 0){

                InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
                //Move and decrement the nb of steps
                instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL(),idMainCopy.i.getStartingP().getC() -1));
                instanceCopy.i.setK(instanceCopy.i.getK()-1);
                solution = algoFPT1DP(instanceCopy,table);

                if(solution != null){
                    solution.add(0,idMainCopy.i.getStartingP());
                    table.put(id,solution);
                    return solution;
                }
            }

            //Si on peux aller en bas
            if(idMainCopy.i.getStartingP().getC() + 1 > idMainCopy.i.getNbC()){

                InstanceDec instanceCopy = new InstanceDec(new Instance(idMainCopy.i),idMainCopy.c);
                //Move and decrement the nb of steps
                instanceCopy.i.setStartingP(new Coord(idMainCopy.i.getStartingP().getL() - 1,idMainCopy.i.getStartingP().getC() +1));
                instanceCopy.i.setK(instanceCopy.i.getK()-1);
                solution = algoFPT1DP(instanceCopy,table);
                if(solution != null){
                    solution.add(0,idMainCopy.i.getStartingP());
                    table.put(id,solution);
                    return solution;
                }
            }
            table.put(id,null);
            return null;
        }
    }


    public static Solution algoFPT1DPClient(InstanceDec id){
        //si il est possible de collecter >= id.c pièces dans id.i, alors retourne une Solution de valeur >= c, sinon retourne null
        //doit faire appel à algoFPT1DP

        //à completer
        return null;

    }



}
