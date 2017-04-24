package fr.uha.ensisa.puissance4.util;

import java.util.HashSet;
import java.util.LinkedList;

public class LIFO extends LinkedList<Noeud> {

	public LIFO() {
		
	}
	
	
	/**
	 * Renvoît et supprime le premier élément de la pile. S'il n'y a plus d'élément, null est renvoyé.
	 */
	@Override
	public Noeud pop() {
		if(!this.isEmpty()) {
			return this.removeFirst();
		}
		return null;
	}
	
	
	/**
	 * Empile l'élément.
	 */
	@Override
	public void push(Noeud noeud) {
		this.addFirst(noeud);
	}

	/**
	 * Ajoute tous les éléments de la collection à la pile, conformément à la description de push().
	 * @param enfants les éléments à ajouter.
	 */
	public void push(HashSet<Noeud> enfants) {
		for(Noeud noeud : enfants) {
			push(noeud);
		}
	}
}
