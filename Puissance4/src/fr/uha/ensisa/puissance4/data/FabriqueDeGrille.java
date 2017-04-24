package fr.uha.ensisa.puissance4.data;

import java.util.HashMap;
import java.util.HashSet;

import fr.uha.ensisa.puissance4.util.Constantes;

public abstract class FabriqueDeGrille {
	
	public static HashMap<Grille, Integer> genererGrilles(Grille grilleDepart, Constantes.Case symboleJoueurCourant) {
		
		if(grilleDepart == null || symboleJoueurCourant == null) {
			return null;
		}
		
		HashMap<Grille, Integer> grilles = new HashMap<Grille, Integer>();
		for(int colonne = 0; colonne < Constantes.NB_COLONNES; colonne++) {
			if(grilleDepart.isCoupPossible(colonne)) {
				Grille clone = grilleDepart.clone();
				clone.ajouterCoup(colonne, symboleJoueurCourant);
				grilles.put(clone, colonne);
			}
		}
		return grilles;
	}
}
