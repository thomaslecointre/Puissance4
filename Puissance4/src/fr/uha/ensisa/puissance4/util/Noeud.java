package fr.uha.ensisa.puissance4.util;

import java.util.HashMap;
import java.util.HashSet;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class Noeud {
	private Noeud parent;
	private Grille grille;
	private HashSet<Noeud> enfants = new HashSet<Noeud>();
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
	
	public Noeud(Grille etat, Case symbole) {
		this.grille = etat;
		this.symboleJoueurCourant = symbole;
		this.profondeur = 0;
	}
	
	public Noeud(Noeud parent) {
		this.parent = parent;
		this.profondeur = parent.profondeur + 1;
	}
	
	public Noeud(Noeud parent, Grille etat, int colonneJoue) {
		this.parent = parent;
		this.grille = etat;
		this.colonneJoue = colonneJoue;
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
	
	public void genererEnfants(HashMap<Grille, Integer> grillesJouables) {
		this.enfants = new HashSet<Noeud>();
		for(Grille grille : grillesJouables.keySet()) {
			enfants.add(new Noeud(this, grille, grillesJouables.get(grille)));
		}
	}
	
	public void setEnfants(HashSet<Noeud> enfants) {
		this.enfants = enfants;
	}
	
	public void setEnfants(Noeud enfant) {
		this.enfants.add(enfant);
	}

	public HashSet<Noeud> getEnfants() {
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
	
	public double utilite() {
		
		if(this.role == Roles.TERMINAL) {
			this.utilite = this.grille.evaluer(symboleJoueurCourant);
			return this.utilite;
		} else {
			// Noeud non terminal, on calcule l'utilité suivant le type de joueur.
			if(profondeur % 2 == 0) {
				// Comportement max
				double max = Constantes.SCORE_MAX_NON_DEFINI;
				for(Noeud noeud : enfants) {
					double temp = noeud.utilite();
					max = temp > max ? temp : max;
				}
				this.utilite = max;
				return this.utilite;
			} else {
				// Comportement min
				double min = Constantes.SCORE_MIN_NON_DEFINI;
				for(Noeud noeud : enfants) {
					double temp = noeud.utilite();
					min = temp < min ? temp : min;
				}
				this.utilite = min;
				return this.utilite;
			}
		}
	}

	public int getColonneJoue() {
		return colonneJoue;
	}
}
