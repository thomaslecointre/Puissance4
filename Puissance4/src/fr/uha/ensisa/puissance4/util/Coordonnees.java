package fr.uha.ensisa.puissance4.util;

public class Coordonnees {
	public int colonne;
	public int ligne;
	
	public Coordonnees(int colonne, int ligne) {
		this.colonne = colonne;
		this.ligne = ligne;
	}
	
	@Override
	public boolean equals(Object obj) {
		Coordonnees autreCoordonnees = (Coordonnees) obj;
		return this.colonne == autreCoordonnees.colonne && this.ligne == autreCoordonnees.ligne;
	}
}
