package fr.uha.ensisa.puissance4.util;

import java.util.ArrayList;
import java.util.HashMap;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class Noeud {
	private Noeud parent = null;
	private Grille grille = null;
	private ArrayList<Noeud> enfants = new ArrayList<Noeud>();
	private int profondeur;
	private Roles role = Roles.NONTERMINAL;
	private Case symboleJoueurCourant; // le symbole qui doit être joué en ayant cette grille.
	private int colonneJoue;
	private double utilite;
	
	public double getUtilite() {
		return utilite;
	}

	public Case getSymboleJoueurCourant() {
		return symboleJoueurCourant;
	}

	public static enum Roles {
		TERMINAL, NONTERMINAL
	}
	
	public Noeud(Grille etat, Case symboleJoueurCourant) {
		this.grille = etat;
		this.symboleJoueurCourant = symboleJoueurCourant;
		this.profondeur = 0;
	}
	
	public Noeud(Noeud parent) {
		this.parent = parent;
		this.profondeur = parent.profondeur + 1;
	}
	
	public Noeud(Noeud parent, Grille grille, int colonneJoue, Constantes.Case symboleJoueurCourant) {
		this.parent = parent;
		this.grille = grille;
		this.colonneJoue = colonneJoue;
		this.symboleJoueurCourant = symboleJoueurCourant;
		this.profondeur = parent.profondeur + 1;
	}
	
	public Noeud getParent() {
		return parent;
	}
	
	public void setParent(Noeud parent) {
		this.parent = parent;
		if(this.profondeur != parent.profondeur + 1) {
			this.profondeur = parent.profondeur + 1;
		}
	}
	
	public Grille getEtat() {
		return grille;
	}
	
	public void genererEnfants(HashMap<Grille, Integer> grillesJouables, Constantes.Case symboleJoueurCourant) {
		this.enfants = new ArrayList<Noeud>();
		for(Grille grille : grillesJouables.keySet()) {
			enfants.add(new Noeud(this, grille, grillesJouables.get(grille), symboleJoueurCourant));
		}
	}
	
	public void setEnfants(ArrayList<Noeud> enfants) {
		this.enfants = enfants;
	}
	
	public void setEnfants(Noeud enfant) {
		this.enfants.add(enfant);
	}

	public ArrayList<Noeud> getEnfants() {
		return this.enfants;
	}

	public int getProfondeur() {
		return profondeur;
	}
	
	public Roles getRole() {
		return role;
	}
	
	public void setRole(Roles role) {
		this.role = role;
	}
	
	/**
	 * Fonction d'utilité pour minimax.
	 * @return un double signifiant l'utilité du meilleur coup à jour.
	 */
	public double minimax() {
		
		if(this.role == Roles.TERMINAL) {
			this.utilite = this.grille.evaluer(symboleJoueurCourant);
			return this.utilite;
		} else {
			// Noeud non terminal, on calcule l'utilité suivant le type de joueur.
			if(profondeur % 2 == 0) {
				// Comportement max
				double max = Constantes.SCORE_MAX_NON_DEFINI;
				for(Noeud noeud : enfants) {
					double temp = noeud.minimax();
					max = temp > max ? temp : max;
				}
				this.utilite = max;
				return this.utilite;
			} else {
				// Comportement min
				double min = Constantes.SCORE_MIN_NON_DEFINI;
				for(Noeud noeud : enfants) {
					double temp = noeud.minimax();
					min = temp < min ? temp : min;
				}
				this.utilite = min;
				return this.utilite;
			}
		}
	}
	
	private Noeud enfantAGauche() {
		return this.enfants.get(0);
	}
	
	public double alphaBeta() {
		
		double alpha = Constantes.SCORE_MAX_NON_DEFINI;
		double beta = Constantes.SCORE_MIN_NON_DEFINI;
		
		Noeud explorateur = new Noeud(this);
		LIFO noeudsAExplorer = new LIFO();
		
		while(explorateur.role != Roles.TERMINAL) {
			noeudsAExplorer.push(explorateur);
			explorateur = explorateur.enfantAGauche();
		}
		
		// explorateur est terminal, il faut remonter et mettre à jour alpha et beta 

		while(explorateur != this) {
			double utiliteImmediate = this.grille.evaluer(symboleJoueurCourant);
			if(profondeur % 2 == 0) {
				// Comportement max
				if(alpha < utiliteImmediate) {
					alpha = utiliteImmediate;
				}
			} else {
				// Comportement min
				if(beta > utiliteImmediate) {
					beta = this.grille.evaluer(symboleJoueurCourant);
				}
			}
			// TODO Elaguage des branches
			// TODO Utilisation de la LIFO
		}
	
		return 0;
	}

	public int getColonneJoue() {
		return colonneJoue;
	}

	
}
