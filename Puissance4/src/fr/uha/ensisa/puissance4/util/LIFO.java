package fr.uha.ensisa.puissance4.util;

import java.util.LinkedList;

public class LIFO extends LinkedList<Noeud> {

	public LIFO() {
		
	}
	
	@Override
	public void push(Noeud noeud) {
		this.addFirst(noeud);
	}
	
	@Override
	public Noeud pop() {
		return this.removeFirst();
	}
}
