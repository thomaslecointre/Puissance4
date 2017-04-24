package fr.uha.ensisa.puissance4.util;

import java.util.ArrayList;
import java.util.LinkedList;

public class FIFO extends LinkedList<Noeud> {

	public FIFO() {
		
	}
	
	
	/**
	 * Renvoît et supprime le dernier élément de la liste. S'il n'y a plus d'élément, null est renvoyé.
	 */
	@Override
	public Noeud pop() {
		if(!this.isEmpty()) {
			return this.removeLast();
		}
		return null;
	}
	
	
	/**
	 * Ajoute l'élément à la première position de la liste.
	 */
	@Override
	public void push(Noeud noeud) {
		this.addFirst(noeud);
	}

	/**
	 * Ajoute tous les éléments de la collection à la liste, conformément à la description de push().
	 * @param enfants les éléments à ajouter.
	 */
	public void push(ArrayList<Noeud> enfants) {
		for(Noeud noeud : enfants) {
			push(noeud);
		}
	}
}
