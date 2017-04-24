package fr.uha.ensisa.puissance4.util;

import java.util.ArrayList;

public abstract class Constantes {
	public static final int MODE_CONSOLE = 0;
	public static final int MODE_LIGNE_DE_COMMANDE = 1;
	public static final int MODE_INTERFACE_GRAPHIQUE = 2;
	
	public static final int JOUEUR_HUMAN = 1;
	public static final int JOUEUR_IA = 2;
	
	public static final int JOUEUR_1 = 1;
	public static final int JOUEUR_2 = 2;
	
	public static final int IA_MINIMAX = 0;
	public static final int IA_ALPHABETA = 1;
	
	public static final String[] IA_ALGOS = {"Minimax", "Alpha-Beta"};
	
	public static final String[] IA_NAMES = {"HAL", "Skynet", "Ultron", "R2-D2", "Rick Deckard", "IDA"};
	
	/**
	 * V = case vide
	 * X = case symbole joueur 1
	 * O = case symbole joueur 2
	 * @author weber
	 *
	 */
	public enum Case {V, X, O};
	
	//Affectation  des symboles aux joueurs
	public static final Case SYMBOLE_J1 = Case.X;
	public static final Case SYMBOLE_J2 = Case.O;
	
	//DÃ©finition de la taille de la grille
	public static final int NB_COLONNES = 7;
	public static final int NB_LIGNES = 6;
	
	//DÃ©finition du nombre de tours max (dÃ©pendant de la taille de la grille)
	public static final int NB_TOUR_MAX = NB_COLONNES*NB_LIGNES;
	
	//Ã‰tats de la partie
	public static final int PARTIE_EN_COURS = 0;
	public static final int MATCH_NUL = 1;
	public static final int VICTOIRE_JOUEUR_1 = 2;
	public static final int VICTOIRE_JOUEUR_2 = 3;
	

	public static final double SCORE_MAX_NON_DEFINI = Double.NEGATIVE_INFINITY;
	public static final double SCORE_MIN_NON_DEFINI = Double.POSITIVE_INFINITY;
	public static final int COUP_NON_DEFINI = -1;
	
	public static final int MIN = 0;
	public static final int MAX = 1;
	
	/* Certaines cases sont problématiques conçernant l'analyse. On préfère les hôter au lieu de les parcourir. */
	public static ArrayList<Coordonnees> pairesInutilesSE = coordonneesPairesInutilesSE();
	public static ArrayList<Coordonnees> pairesInutilesSO = coordonneesPairesInutilesSO();
	public static ArrayList<Coordonnees> tripletsInutilesSE = coordonneesTripletsInutilesSE();
	public static ArrayList<Coordonnees> tripletsInutilesSO = coordonneesTripletsInutilesSO();
	
	private static ArrayList<Coordonnees> coordonneesPairesInutilesSE() {
		pairesInutilesSE = new ArrayList<Coordonnees>();
		pairesInutilesSE.add(new Coordonnees(NB_COLONNES - 3, 0));
		pairesInutilesSE.add(new Coordonnees(NB_COLONNES - 2, 0));
		pairesInutilesSE.add(new Coordonnees(NB_COLONNES - 2, 1));
		pairesInutilesSE.add(new Coordonnees(NB_COLONNES - 1, 1));
		pairesInutilesSE.add(new Coordonnees(NB_COLONNES - 1, 2));
		pairesInutilesSE.add(new Coordonnees(0, NB_LIGNES - 3));
		pairesInutilesSE.add(new Coordonnees(0, NB_LIGNES - 2));
		pairesInutilesSE.add(new Coordonnees(1, NB_LIGNES - 2));
		pairesInutilesSE.add(new Coordonnees(1, NB_LIGNES - 1));
		pairesInutilesSE.add(new Coordonnees(2, NB_LIGNES - 1));
		return pairesInutilesSE;
	}

	private static ArrayList<Coordonnees> coordonneesPairesInutilesSO() {
		pairesInutilesSO = new ArrayList<Coordonnees>();
		pairesInutilesSO.add(new Coordonnees(1, 0));
		pairesInutilesSO.add(new Coordonnees(2, 0));
		pairesInutilesSO.add(new Coordonnees(0, 1));
		pairesInutilesSO.add(new Coordonnees(1, 1));
		pairesInutilesSO.add(new Coordonnees(0, 2));
		pairesInutilesSO.add(new Coordonnees(NB_COLONNES - 1, NB_LIGNES - 3));
		pairesInutilesSO.add(new Coordonnees(NB_COLONNES - 1, NB_LIGNES - 2));
		pairesInutilesSO.add(new Coordonnees(NB_COLONNES - 2, NB_LIGNES - 2));
		pairesInutilesSO.add(new Coordonnees(NB_COLONNES - 2, NB_LIGNES - 1));
		pairesInutilesSO.add(new Coordonnees(NB_COLONNES - 3, NB_LIGNES - 1));
		return pairesInutilesSO;
	}

	private static ArrayList<Coordonnees> coordonneesTripletsInutilesSE() {
		tripletsInutilesSE = new ArrayList<Coordonnees>();
		tripletsInutilesSE.add(new Coordonnees(NB_COLONNES - 3, 0));
		tripletsInutilesSE.add(new Coordonnees(NB_COLONNES - 2, 1));
		tripletsInutilesSE.add(new Coordonnees(NB_COLONNES - 1, 2));
		tripletsInutilesSE.add(new Coordonnees(0, NB_LIGNES - 3));
		tripletsInutilesSE.add(new Coordonnees(1, NB_LIGNES - 2));
		return tripletsInutilesSE;
	}
	
	private static ArrayList<Coordonnees> coordonneesTripletsInutilesSO() {
		tripletsInutilesSO = new ArrayList<Coordonnees>();
		tripletsInutilesSO.add(new Coordonnees(2, 0));
		tripletsInutilesSO.add(new Coordonnees(1, 1));
		tripletsInutilesSO.add(new Coordonnees(0, 2));
		tripletsInutilesSO.add(new Coordonnees(NB_COLONNES - 1, NB_LIGNES - 3));
		tripletsInutilesSO.add(new Coordonnees(NB_COLONNES - 2, NB_LIGNES - 2));
		tripletsInutilesSO.add(new Coordonnees(NB_COLONNES - 3, NB_LIGNES - 1));
		return tripletsInutilesSO;
	}
	
	public static enum Directions {
		EST, SUDEST, SUD, SUDOUEST
	}
	
}
