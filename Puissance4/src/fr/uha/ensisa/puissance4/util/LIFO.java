package fr.uha.ensisa.puissance4.util;

import java.util.HashSet;
import java.util.LinkedList;

public class LIFO extends LinkedList<Noeud> {

	public LIFO() {
		
	}
	
	
	/**
	 * Renvo�t et supprime le premier �l�ment de la pile. S'il n'y a plus d'�l�ment, null est renvoy�.
	 */
	@Override
	public Noeud pop() {
		if(!this.isEmpty()) {
			return this.removeFirst();
		}
		return null;
	}
	
	
	/**
	 * Empile l'�l�ment.
	 */
	@Override
	public void push(Noeud noeud) {
		this.addFirst(noeud);
	}

	/**
	 * Ajoute tous les �l�ments de la collection � la pile, conform�ment � la description de push().
	 * @param enfants les �l�ments � ajouter.
	 */
	public void push(HashSet<Noeud> enfants) {
		for(Noeud noeud : enfants) {
			push(noeud);
		}
	}
}
