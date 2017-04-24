package fr.uha.ensisa.puissance4.jeu.algosIA;

import java.util.HashMap;

import fr.uha.ensisa.puissance4.data.FabriqueDeGrille;
import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.FIFO;
import fr.uha.ensisa.puissance4.util.Noeud;



public class Minimax extends Algorithm {

	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
		
	}
	

	@Override
	public int choisirCoup() {
		
		// Construction de l'arbe de recherche
		Noeud racine = new Noeud(grilleDepart.clone(), symboleMax);
		Noeud explorateur = null;
		FIFO noeudsExplorables = new FIFO();
		HashMap<Grille, Integer> grillesJouables = null;
		explorateur = racine;
		
		while(explorateur != null) {
			if(explorateur.getProfondeur() % 2 == 0) {
				grillesJouables = FabriqueDeGrille.genererGrilles(explorateur.getEtat(), symboleMax);
				explorateur.genererEnfants(grillesJouables, symboleMax);
			} else {
				grillesJouables = FabriqueDeGrille.genererGrilles(explorateur.getEtat(), symboleMin);
				explorateur.genererEnfants(grillesJouables, symboleMax);
			}
			
			if(explorateur.getProfondeur() < this.levelIA - 1) {
				noeudsExplorables.push(explorateur.getEnfants());
			} else {
				for(Noeud enfant : explorateur.getEnfants()) {
					enfant.setRole(Noeud.Roles.TERMINAL);
				}
			}
			explorateur = noeudsExplorables.pop();
		}
		
		// Recherche du meilleur coup
		double utilite = racine.minimax();
		
		for(Noeud noeud : racine.getEnfants()) {
			if(utilite == noeud.getUtilite()) {
				return noeud.getColonneJoue();
			}
		}
		// Seulement si on ne trouve pas de coup à jouer.
		return 0;
	}
}
