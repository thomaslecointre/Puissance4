package fr.uha.ensisa.puissance4;

import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.util.Constantes;

public abstract class Puissance4 {

	public static void main(String[] args) {

		int mode = Constantes.MODE_CONSOLE;
		// Indique la bonne interface et la lance dans un thread différent
		switch (mode) {
		default:
			Console console = new Console();
			console.start();
			break;
		}

		/*
		 * Grille grille = new Grille();
		 * 
		 * for (int ligne = 0; ligne < Constantes.NB_LIGNES; ligne++) { for (int
		 * colonne = 0; colonne < Constantes.NB_COLONNES; colonne++) {
		 * grille.getMatrice()[colonne][ligne] = Constantes.Case.X; } }
		 */
	}

}
