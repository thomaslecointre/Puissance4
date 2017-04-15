package fr.uha.ensisa.puissance4.data;

import java.util.HashSet;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class Grille {

	private Case[][] grille;
	private double utilite = 0;

	public Grille() {
		grille = new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for (int i = 0; i < Constantes.NB_COLONNES; i++)
			for (int j = 0; j < Constantes.NB_LIGNES; j++) {
				grille[i][j] = Case.V;
			}
	}

	/**
	 * Constructeur qui créé une copie de la grille donné en argument
	 * 
	 * @param original
	 */
	private Grille(Grille original) {
		// À compléter
	}

	/**
	 * Renvoie le contenu de la case aux coordonnées données en argument
	 * 
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public Case getCase(int ligne, int colonne) {
		return grille[colonne][ligne];
	}

	/**
	 * Indique s'il y a encore de la place dans la colonne indiquée
	 * 
	 * @param colonne
	 * @return
	 */
	public boolean isCoupPossible(int colonne) {
		if (colonne >= 0 && colonne < Constantes.NB_COLONNES) {
			return grille[colonne][Constantes.NB_LIGNES - 1] == Case.V;
		} else {
			return false;
		}
	}

	/**
	 * Ajoute le symbole indiqu� dans la colonne indiqu�e ce qui permet de jouer
	 * ce coup
	 * 
	 * @param colonne
	 * @param symbole
	 */
	public void ajouterCoup(int colonne, Case symbole) {
		for (int j = 0; j < Constantes.NB_LIGNES; j++) {
			if (grille[colonne][j] == Case.V) {
				grille[colonne][j] = symbole;
				break;
			}
		}

	}

	/**
	 * Renvoie l'�tat de la partie
	 * 
	 * @param symboleJoueurCourant
	 * @param tour
	 * @return
	 */
	public int getEtatPartie(Case symboleJoueurCourant, int tour) {
		int victoire;
		if (symboleJoueurCourant == Constantes.SYMBOLE_J1) {
			victoire = Constantes.VICTOIRE_JOUEUR_1;
		} else {
			victoire = Constantes.VICTOIRE_JOUEUR_2;
		}
		int nbAlignes = 0;
		// V�rification alignement horizontaux
		for (int i = 0; i < Constantes.NB_LIGNES; i++) {
			for (int j = 0; j < Constantes.NB_COLONNES; j++) {
				if (grille[j][i] == symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes = 0;
				if (nbAlignes == 4) {
					return victoire;
				}
			}
			nbAlignes = 0;
		}
		// V�rification alignement verticaux
		for (int j = 0; j < Constantes.NB_COLONNES; j++) {
			for (int i = 0; i < Constantes.NB_LIGNES; i++) {
				if (grille[j][i] == symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes = 0;
				if (nbAlignes == 4) {
					return victoire;
				}
			}
			nbAlignes = 0;
		}
		// V�rification alignement diagonaux (bas-droite vers haut-gauche)
		for (int i = 0; i < Constantes.NB_LIGNES - 3; i++)
			for (int j = 0; j < Constantes.NB_COLONNES - 3; j++) {
				for (int x = 0; i + x < Constantes.NB_LIGNES && j + x < Constantes.NB_COLONNES; x++) {
					if (grille[j + x][i + x] == symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes = 0;
					if (nbAlignes == 4) {
						return victoire;
					}
				}
				nbAlignes = 0;
			}

		// V�rification alignement diagonaux (bas-gauche vers haut-droit)
		for (int i = 0; i < Constantes.NB_LIGNES - 3; i++)
			for (int j = Constantes.NB_COLONNES - 1; j >= 3; j--) {
				for (int x = 0; i + x < Constantes.NB_LIGNES && j - x >= 0; x++) {
					if (grille[j - x][i + x] == symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes = 0;
					if (nbAlignes == 4) {
						return victoire;
					}
				}
				nbAlignes = 0;
			}

		if (tour == Constantes.NB_TOUR_MAX) {
			return Constantes.MATCH_NUL;
		}

		return Constantes.PARTIE_EN_COURS;
	}

	/**
	 * Fonction servant � determiner si la case pass�e en param�tres se trouve �
	 * c�t� de cases vides.
	 * 
	 * @param colonne
	 *            l'indice colonne fournie.
	 * @param ligne
	 *            l'indice ligne fournie.
	 * @return Un bool�en indiquant si la case est bien � c�t� de cases vides,
	 *         donc est extensible.
	 */
	private boolean trouverCellulesVidesAutourDe(int colonne, int ligne) {
		HashSet<Integer[]> options = new HashSet<Integer[]>();
		options.add(new Integer[] { colonne - 1, ligne });
		options.add(new Integer[] { colonne - 1, ligne - 1 });
		options.add(new Integer[] { colonne, ligne - 1 });
		options.add(new Integer[] { colonne + 1, ligne - 1 });
		options.add(new Integer[] { colonne + 1, ligne });
		options.add(new Integer[] { colonne + 1, ligne + 1 });
		options.add(new Integer[] { colonne, ligne + 1 });
		options.add(new Integer[] { colonne - 1, ligne + 1 });

		for (Integer[] voisin : options) {
			try {
				if (grille[voisin[0]][voisin[1]] == Constantes.Case.V) {
					return true;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}
		return false;
	}

	/**
	 * Fonction renvoyant l'utilit� d'une paire de cases du m�me symbole.
	 * <p>
	 * Si la paire est compl�tement extensible (peut �tre �tendue jusqu'� quatre
	 * cases de la m�me valeur) alors elle a d'utilit�. Sinon 0 est renvoy�.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de d�part
	 * @param colonne
	 *            l'indice colonne de la case d'arriv�e.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de d�part.
	 * @param ligne
	 *            l'indice ligne de la case d'arriv�e.
	 * @param symboleJoueurCourant
	 *            le symbole utilis� pour l'analyse.
	 * @return Un entier renvoyant l'utilit� de la paire.
	 */
	private int completerLaPaire(int colonneOriginale, int colonne, int ligneOriginale, int ligne,
			Constantes.Case symboleJoueurCourant) {
		
		int succes = 2;
		int echec = 0;

		// Direction est
		if (ligneOriginale == ligne) {
			if (colonneOriginale > 0) {
				// Si on trouve d�j� une case du m�me type � l'ouest du cluster
				// alors il ne sert � rien de continuer l'analyse puisqu'une
				// utilit� plus importante aura d�j� �t� d�termin�e.
				if (grille[colonneOriginale - 1][ligneOriginale] == symboleJoueurCourant) {
					return echec;
				}
			}
			switch (Constantes.NB_COLONNES - colonneOriginale) {
			case 2:
				// Paire pleine � l'est
				if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
					if (grille[colonneOriginale - 2][ligneOriginale] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					return echec;
				}
			case 3:
				// Paire � une case du mur est
				if (grille[colonne][ligne] == Constantes.Case.V) {
					if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
						if (grille[colonneOriginale - 2][ligneOriginale] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			case Constantes.NB_COLONNES - 1:
				// Paire � une case du mur ouest
				if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						if (grille[colonne + 1][ligne] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			case Constantes.NB_COLONNES:
				// Paire sur le mur ouest
				if (grille[colonne][ligne] == Constantes.Case.V) {
					if (grille[colonne + 1][ligne] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					return echec;
				}
			default:
				if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
					if (grille[colonneOriginale - 2][ligneOriginale] == Constantes.Case.V) {
						return succes;
					} else {
						if (grille[colonne][ligne] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					}
				} else {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						if (grille[colonne + 1][ligne] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			}
		}

		// Direction sud-est
		// TODO
		if (ligneOriginale < ligne) {
			if (Constantes.NB_COLONNES - colonneOriginale == Constantes.NB_COLONNES
					|| Constantes.NB_LIGNES - ligneOriginale == Constantes.NB_LIGNES) {
				// TODO
			}
		}

		// Direcetion sud
		if (colonneOriginale == colonne) {
			if (ligneOriginale > 0) {
				// Si on trouve d�j� une case du m�me type � l'ouest du cluster
				// alors il ne sert � rien de continuer l'analyse puisqu'une
				// utilit� plus importante aura d�j� �t� d�termin�e.
				if (grille[colonneOriginale][ligneOriginale - 1] == symboleJoueurCourant) {
					return echec;
				}
			}
			switch (Constantes.NB_LIGNES - ligneOriginale) {
			case 2:
				// Paire pleine au sud
				if (grille[colonneOriginale][ligneOriginale - 1] == Constantes.Case.V) {
					if (grille[colonneOriginale][ligneOriginale - 2] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					return echec;
				}
			case 3:
				// Paire � une case du mur sud
				if (grille[colonne][ligne] == Constantes.Case.V) {
					if (grille[colonneOriginale][ligneOriginale - 1] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					if (grille[colonneOriginale][ligneOriginale - 1] == Constantes.Case.V) {
						if (grille[colonneOriginale][ligneOriginale - 2] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			case Constantes.NB_LIGNES - 1:
				// Paire � une case du mur nord
				if (grille[colonneOriginale][ligneOriginale - 1] == Constantes.Case.V) {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						if (grille[colonne][ligne + 1] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			case Constantes.NB_LIGNES:
				// Paire sur le mur nord
				if (grille[colonne][ligne] == Constantes.Case.V) {
					if (grille[colonne][ligne + 1] == Constantes.Case.V) {
						return succes;
					} else {
						return echec;
					}
				} else {
					return echec;
				}
			default:
				if (grille[colonneOriginale][ligneOriginale - 1] == Constantes.Case.V) {
					if (grille[colonneOriginale][ligneOriginale - 2] == Constantes.Case.V) {
						return succes;
					} else {
						if (grille[colonne][ligne] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					}
				} else {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						if (grille[colonne][ligne + 1] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			}
		}

		// Direction sud-ouest
		// TODO
		/*
		 * if (colonneOriginale > colonne) { switch (Constantes.NB_LIGNES -
		 * ligneOriginale) { case 2: // Paire pleine au sud-ouest if
		 * (grille[colonneOriginale + 1][ligneOriginale - 1] ==
		 * Constantes.Case.V) { if (grille[colonneOriginale + 2][ligneOriginale
		 * - 2] == Constantes.Case.V) { return 2; } else { return 0; } } else {
		 * return 0; } case 3: // Paire � une case du coin sud-ouest if
		 * (grille[colonne][ligne] == Constantes.Case.V) { if
		 * (grille[colonneOriginale + 1][ligneOriginale - 1] ==
		 * Constantes.Case.V) { return 2; } else { return 0; } } else { if
		 * (grille[colonneOriginale + 1][ligneOriginale - 1] ==
		 * Constantes.Case.V) { if (grille[colonneOriginale + 2][ligneOriginale
		 * - 2] == Constantes.Case.V) { return 2; } else { return 0; } } else {
		 * return 0; } } case Constantes.NB_COLONNES - 1: // Paire � une case du
		 * coin nord-est if (grille[colonneOriginale + 1][ligneOriginale - 1] ==
		 * Constantes.Case.V) { if (grille[colonne][ligne] == Constantes.Case.V)
		 * { return 2; } else { return 0; } } else { if (grille[colonne][ligne]
		 * == Constantes.Case.V) { if (grille[colonne - 1][ligne + 1] ==
		 * Constantes.Case.V) { return 2; } else { return 0; } } else { return
		 * 0; } } case Constantes.NB_COLONNES: // Paire dans le coin nord-est if
		 * (grille[colonne][ligne] == Constantes.Case.V) { if (grille[colonne -
		 * 1][ligne + 1] == Constantes.Case.V) { return 2; } else { return 0; }
		 * } else { return 0; } default: if (grille[colonneOriginale +
		 * 1][ligneOriginale - 1] == Constantes.Case.V) { if
		 * (grille[colonneOriginale + 2][ligneOriginale - 2] ==
		 * Constantes.Case.V) { return 2; } else { if (grille[colonne][ligne] ==
		 * Constantes.Case.V) { return 2; } else { return 0; } } } else { if
		 * (grille[colonne][ligne] == Constantes.Case.V) { if (grille[colonne -
		 * 1][ligne + 1] == Constantes.Case.V) { return 2; } else { return 0; }
		 * } else { return 0; } } }
		 * 
		 * }
		 */
		return 0;
	}

	private int completerLeTriplet(int colonneOriginale, int colonne, int ligneOriginale, int ligne,
			Case symboleJoueurCourant) {

		int succes = 3;
		int echec = 0;

		// Direction est
		if (ligneOriginale == ligne) {
			if (colonneOriginale > echec) {
				if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
					return succes;
				} else {
					if (colonne < Constantes.NB_COLONNES) {
						if (grille[colonne][ligne] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			} else {
				if (grille[colonne][ligne] == Constantes.Case.V) {
					return succes;
				} else {
					return echec;
				}
			}
		}

		// Direction sud
		if (colonneOriginale == colonne) {
			if (ligneOriginale > echec) {
				if (grille[ligneOriginale - 1][colonneOriginale] == Constantes.Case.V) {
					return succes;
				} else {
					if (ligne < Constantes.NB_LIGNES) {
						if (grille[ligne][colonne] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			} else {
				if (grille[colonne][ligne] == Constantes.Case.V) {
					return succes;
				} else {
					return echec;
				}
			}
		}
		return echec;
	}

	/**
	 * Fonction servant � d�terminer le nombre de cases align�es.
	 * 
	 * @param colonne
	 *            l'indice colonne de la grille.
	 * @param ligne
	 *            l'indice ligne de la grille.
	 * @param symboleJoueurCourant
	 *            le symbole utilis� pour l'analyse.
	 * @return un entier renvoyant l'utilit� de la grille.
	 */
	private int determinerTypeDeCluster(int colonne, int ligne, Constantes.Case symboleJoueurCourant) {
		
		int utilitePossible;
		
		int nbAlignes = 0;
		int colonneOriginale = colonne;
		int ligneOriginale = ligne;

		// Direction est
		while (colonne < Constantes.NB_COLONNES && nbAlignes < 4 && grille[colonne][ligne] == symboleJoueurCourant) {
			nbAlignes++;
			colonne++;
		}

		if (nbAlignes == 4) {
			return 4;
		}

		// switch case pour est
		switch (nbAlignes) {
		case 2:
			utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
					symboleJoueurCourant);
			if (utilitePossible == 2) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilit� � minimum 2 pour la premi�re paire
				// d�vouverte.
				if (utilite < 2) {
					utilite = 2;
				}
			}
			break;
		case 3:
			utilitePossible = completerLeTriplet(colonneOriginale, colonne, ligneOriginale, ligne,
					symboleJoueurCourant);
			if (utilitePossible == 3) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre utilit� � minimum 3 pour le premier triplet d�couvert.
				if (utilite < 3) {
					utilite = 3;
				}
			}
			break;
		}

		// Direction sud-est
		colonne = colonneOriginale;
		ligne = ligneOriginale;
		nbAlignes = 0;

		while (colonne < Constantes.NB_COLONNES && ligne < Constantes.NB_LIGNES && nbAlignes < 4
				&& grille[colonne][ligne] == symboleJoueurCourant) {
			nbAlignes++;
			ligne++;
			colonne++;
		}

		if (nbAlignes == 4) {
			return 4;
		}

		// switch case pour sud-est
		switch (nbAlignes) {
		case 2:
			if (!Constantes.pairesInutilesSE.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				utilite = utilitePossible > utilite ? utilitePossible : utilite;
			}
			break;
		case 3:
			if (!Constantes.tripletsInutilesSE.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				// Tester pour voir si la case au nord-ouest du cluster est
				// vide.
				if (colonneOriginale > 0 && ligneOriginale > 0) {
					if (grille[colonneOriginale - 1][ligneOriginale - 1] == Constantes.Case.V) {
						utilite = 3 > utilite ? 3 : utilite;
					}
				}
				// Tester pour voir si la case au sud-est du cluster est vide.
				if (colonne < Constantes.NB_COLONNES && ligne < Constantes.NB_LIGNES) {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						utilite = 3 > utilite ? 3 : utilite;
					}
				}
			}
			break;
		}

		// Direction sud
		colonne = colonneOriginale;
		ligne = ligneOriginale;
		nbAlignes = 0;
		while (ligne < Constantes.NB_LIGNES && nbAlignes < 4 && grille[colonne][ligne] == symboleJoueurCourant) {
			nbAlignes++;
			ligne++;
		}

		if (nbAlignes == 4) {
			return 4;
		}

		// switch case pour sud
		switch (nbAlignes) {
		case 2:
			utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
					symboleJoueurCourant);
			utilite = utilitePossible > utilite ? utilitePossible : utilite;
			break;
		case 3:
			// Tester pour voir si la case au nord du cluster est vide.
			if (ligneOriginale > 0) {
				if (grille[colonneOriginale][ligneOriginale - 1] == Constantes.Case.V) {
					utilite = 3 > utilite ? 3 : utilite;
				}
			}
			// Tester pour voir si la case au sud du cluster est vide.
			if (ligne < Constantes.NB_LIGNES) {
				if (grille[colonne][ligne] == Constantes.Case.V) {
					utilite = 3 > utilite ? 3 : utilite;
				}
			}
			break;
		}

		// Direct sud-ouest

		colonne = colonneOriginale;
		ligne = ligneOriginale;
		nbAlignes = 0;

		if (!Constantes.pairesInutilesSO.contains(new Integer[] { colonneOriginale, ligneOriginale })) {

			while (colonne >= 0 && ligne < Constantes.NB_LIGNES && nbAlignes < 4
					&& grille[colonne][ligne] == symboleJoueurCourant) {
				nbAlignes++;
				ligne++;
				colonne--;
			}

			if (nbAlignes == 4) {
				return 4;
			}

			// switch case pour sud-ouest
			switch (nbAlignes) {
			case 1:
				if (trouverCellulesVidesAutourDe(colonneOriginale, ligne)) {
					utilite = 1 > utilite ? 1 : utilite;
				}
				break;
			case 2:
				utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				utilite = utilitePossible > utilite ? utilitePossible : utilite;
				break;
			case 3:
				// Tester pour voir si la case au nord-est du cluster est vide.
				if (colonneOriginale < Constantes.NB_COLONNES - 1 && ligneOriginale > 0) {
					if (grille[colonneOriginale + 1][ligneOriginale - 1] == Constantes.Case.V) {
						utilite = 3 > utilite ? 3 : utilite;
					}
				}
				// Tester pour voir si la case au sud-ouest du cluster est vide.
				if (colonne >= 0 && ligne < Constantes.NB_LIGNES) {
					if (grille[colonne][ligne] == Constantes.Case.V) {
						utilite = 3 > utilite ? 3 : utilite;
					}
				}
				break;
			}
		}

		return 0;
	}

	/**
	 * Donne un score � la grille en fonction du joueur
	 * 
	 * @param symboleJoueurCourant
	 * @return
	 */
	public double evaluer(Case symboleJoueurCourant) {
		
		// Joueur 1 cherche � maximiser ses chances � gagner
		// Joueur 2 cherche � minimiser les chances que max gagne
		
		if (symboleJoueurCourant == Constantes.SYMBOLE_J1) {
			for (int i = 0; i < Constantes.NB_LIGNES; i++) {
				for (int j = 0; j < Constantes.NB_COLONNES; j++) {
					if (grille[j][i] == Constantes.SYMBOLE_J1) {
						determinerTypeDeCluster(j, i, Constantes.SYMBOLE_J1);
					}
				}
			}
		} else {
			if (symboleJoueurCourant.equals(Constantes.SYMBOLE_J2)) {

			}
		}
		return 0;
	}

	/**
	 * Clone la grille
	 */
	public Grille clone() {
		Grille copy = new Grille(this);
		return copy;
	}

}
