package fr.uha.ensisa.puissance4.util;

import java.util.ArrayList;
import java.util.LinkedList;

public class FIFO extends LinkedList<Noeud> {

	public FIFO() {
		
	}
	
	
	/**
	 * Renvo�t et supprime le dernier �l�ment de la liste. S'il n'y a plus d'�l�ment, null est renvoy�.
	 */
	@Override
	public Noeud pop() {
		if(!this.isEmpty()) {
			return this.removeLast();
		}
		return null;
	}
	
	
	/**
	 * Ajoute l'�l�ment � la premi�re position de la liste.
	 */
	@Override
	public void push(Noeud noeud) {
		this.addFirst(noeud);
	}

	/**
	 * Ajoute tous les �l�ments de la collection � la liste, conform�ment � la description de push().
	 * @param enfants les �l�ments � ajouter.
	 */
	public void push(ArrayList<Noeud> enfants) {
		for(Noeud noeud : enfants) {
			push(noeud);
		}
	}
}
