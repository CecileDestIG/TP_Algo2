import java.util.ArrayList;

class HillClimbing {

    public static Solution hillClimbingWithRestart(IFactory f, int nbRestart) {


        //prérequis : nbRestart >= 1
        //effectue nbRestart fois l'algorithme de hillClimbing, en partant à chaque fois d'un élément donné par f


        IElemHC currentBest = f.getRandomSol();
        int currentBestVal = currentBest.getVal();
        IElemHC currentElement;
        int currentVal;

        for(int i = 0; i < nbRestart; i++){
            currentElement = f.getRandomSol();
            currentVal = currentElement.getVal();

            if(currentVal> currentBestVal){
                currentBest = currentElement;
                currentBestVal = currentVal;
            }
        }
        return currentBest.getSol();
    }

    public static IElemHC hillClimbing(IElemHC s){

        //effectue une recherche locale en partant de s :
        // - en prenant à chaque étape le meilleur des voisins de la solution courante (ou un des meilleurs si il y a plusieurs ex aequo)
        // - en s'arrêtant dès que la solution courante n'a pas de voisin strictement meilleur qu'elle
        // (meilleur au sens de getVal strictement plus grand)

        //faire une vraie copie
        IElemHC currentBest = s;
        int currentBestVal = Integer.MIN_VALUE;
        boolean trouve  = true;
        while (trouve){
            ArrayList<? extends IElemHC> elements = s.getVoisins();
            trouve = false;
            for(IElemHC element : elements){
                if(currentBestVal < element.getVal()){
                    currentBest = element;
                    currentBestVal = element.getVal();
                    trouve = true;
                }
            }
        }
        return currentBest;
    }
}