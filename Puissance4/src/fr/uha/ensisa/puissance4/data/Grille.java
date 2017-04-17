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
	 * Constructeur qui crée une copie de la grille donnée en argument
	 * 
	 * @param original
	 */
	private Grille(Grille original) {
		this.grille = original.grille;
		this.utilite = original.utilite;
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
	 * Indique s'il y a encore de la place dans la colonne indiquÃ©e
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
	 * Ajoute le symbole indiqué dans la colonne indiquée ce qui permet de jouer
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
	 * Renvoie l'état de la partie
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
		// Vérification alignement horizontaux
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
		// Vérification alignement verticaux
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
		// Vérification alignement diagonaux (bas-droite vers haut-gauche)
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

		// Vérification alignement diagonaux (bas-gauche vers haut-droit)
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
	 * Fonction servant à renvoyer les coordonnées de la prochaine case dans la
	 * même direction déterminée par les paramètres fournis.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de départ.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de départ.
	 * @param colonne
	 *            l'indice colonne de la case d'arrivée.
	 * @param ligne
	 *            l'indice ligne de la case d'arrivée.
	 * @return Un tableau d'entiers représentant les coordonnées de la case qui
	 *         nous intéresse.
	 */
	private Integer[] trouverLaDirection(int colonneOriginale, int ligneOriginale, int colonne, Integer ligne) {

		// Nord
		if (colonneOriginale == colonne && ligneOriginale > ligne) {
			return new Integer[] { colonne, ligne - 1 };
		}

		// Nord-est
		if (colonneOriginale < colonne && ligneOriginale > ligne) {
			return new Integer[] { colonne + 1, ligne - 1 };
		}

		// Est
		if (colonneOriginale < colonne && ligneOriginale == ligne) {
			return new Integer[] { colonne + 1, ligne };
		}

		// Sud-est
		if (colonneOriginale < colonne && ligneOriginale < ligne) {
			return new Integer[] { colonne + 1, ligne + 1 };
		}

		// Sud
		if (colonneOriginale == colonne && ligneOriginale < ligne) {
			return new Integer[] { colonne, ligne + 1 };
		}

		// Sud-ouest
		if (colonneOriginale > colonne && ligneOriginale < ligne) {
			return new Integer[] { colonne - 1, ligne + 1 };
		}

		// Ouest
		if (colonneOriginale > colonne && ligneOriginale == ligne) {
			return new Integer[] { colonne - 1, ligne };
		}

		// Nord-ouest
		if (colonneOriginale > colonne && ligneOriginale > ligne) {
			return new Integer[] { colonne - 1, ligne - 1 };
		}

		// Aucune direction déterminée.
		return null;
	}

	/**
	 * Fonction servant à determiner si la case passée en paramètres se trouve à
	 * côté de cases vides.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de départ.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de départ.
	 * @return Un entier indiquant l'utilité de la case analysée.
	 */
	private int trouverCellulesVidesAutourDe(int colonneOriginale, int ligneOriginale,
			Constantes.Directions direction, Constantes.Case symboleJoueurCourant) {

		int succes = 1;
		int echec = 0;
		int nbCasesSymboleJoueur = 0;

		// Il faut trouver un alignement de 3 cases contenant soit du vide, soit
		// une appartenant au joueur courant dans une direction
		// quelconque partant de la case initiale. On va itérer sur les voisins
		// afin de chercher des alignements potentiellement intéressants.
		HashSet<Integer[]> pairesAValider = new HashSet<Integer[]>();
		switch (direction) {
		case EST:
			pairesAValider.add(new Integer[] { colonneOriginale - 1, ligneOriginale });
			pairesAValider.add(new Integer[] { colonneOriginale + 1, ligneOriginale });
			break;
		case SUDEST:
			pairesAValider.add(new Integer[] { colonneOriginale - 1, ligneOriginale - 1 });
			pairesAValider.add(new Integer[] { colonneOriginale + 1, ligneOriginale + 1 });
			break;
		case SUD:
			pairesAValider.add(new Integer[] { colonneOriginale, ligneOriginale - 1 });
			pairesAValider.add(new Integer[] { colonneOriginale, ligneOriginale + 1 });
			break;
		case SUDOUEST:
			pairesAValider.add(new Integer[] { colonneOriginale + 1, ligneOriginale - 1 });
			pairesAValider.add(new Integer[] { colonneOriginale - 1, ligneOriginale + 1 });
			break;
		default:
			// La direction fournie n'est pas reconnue
			return echec;
		}

		HashSet<Integer[]> tripletsAValider = new HashSet<Integer[]>();
		HashSet<Integer[]> quadrupletsAValider = new HashSet<Integer[]>();

		for (Integer[] voisin : pairesAValider) {
			try {
				// Comme l'alignement détérminé précedemment est de 1, on cherche seulement les cases vides autour selon la direction fournie.
				if (grille[voisin[0]][voisin[1]] == Constantes.Case.V) {
					tripletsAValider.add(trouverLaDirection(colonneOriginale, ligneOriginale, voisin[0], voisin[1]));
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}
		}

		if (tripletsAValider.isEmpty()) {
			return echec;
		} else {
			for (Integer[] paire : tripletsAValider) {
				try {
					if (grille[paire[0]][paire[1]] == Constantes.Case.V || grille[paire[0]][paire[1]] == symboleJoueurCourant) {
						quadrupletsAValider
								.add(trouverLaDirection(colonneOriginale, ligneOriginale, paire[0], paire[1]));
						if(grille[paire[0]][paire[1]] == symboleJoueurCourant) {
							nbCasesSymboleJoueur++;
						}
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
		}

		if (quadrupletsAValider.isEmpty()) {
			return echec;
		} else {
			for (Integer[] triplet : quadrupletsAValider) {
				try {
					if (grille[triplet[0]][triplet[1]] == Constantes.Case.V || grille[triplet[0]][triplet[1]] == symboleJoueurCourant) {
						if(grille[triplet[0]][triplet[1]] == symboleJoueurCourant) {
							nbCasesSymboleJoueur++;
						}
						return succes + nbCasesSymboleJoueur;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					continue;
				}
			}
			// Le cas où on n'a aucun quadruplet valide.
			return echec;
		}
	}

	/**
	 * Fonction renvoyant l'utilité d'une paire de cases du même symbole.
	 * <p>
	 * Si la paire est complètement extensible (peut être étendue jusqu'à quatre
	 * cases de la même valeur) alors elle a d'utilité. Sinon 0 est renvoyé.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de départ
	 * @param colonne
	 *            l'indice colonne après la dernière case du même symbole.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de départ.
	 * @param ligne
	 *            l'indice ligne après la dernière case du même symbole.
	 * @param symboleJoueurCourant
	 *            le symbole utilisé pour l'analyse.
	 * @return Un entier renvoyant l'utilité de la paire.
	 */
	private int completerLaPaire(int colonneOriginale, int colonne, int ligneOriginale, int ligne,
			Constantes.Case symboleJoueurCourant) {

		int succes = 2; // Si le quatrième symbole est identique au
						// symboleJoueurCourant on a effectivement un triplet
						// mais séparé par une case vide. Il convient d'élever
						// l'utilité à celle d'un triplet (return succes + 1;).
		int echec = 0;

		// Direction est
		if (ligneOriginale == ligne) {
			if (colonneOriginale > 0) {
				// Si on trouve déjà une case du même type à l'ouest du cluster
				// alors il ne sert à rien de continuer l'analyse puisqu'une
				// utilité plus importante aura déjà été déterminée.
				if (grille[colonneOriginale - 1][ligneOriginale] == symboleJoueurCourant) {
					return echec;
				}
			}
			switch (Constantes.NB_COLONNES - colonneOriginale) {
			case 2:
				// Paire pleine à l'est
				if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
					if (grille[colonneOriginale - 2][ligneOriginale] == Constantes.Case.V) {
						return succes;
					} else if (grille[colonneOriginale - 2][ligneOriginale] == symboleJoueurCourant) {
						return succes + 1;
					} else {
						return echec;
					}
				} else {
					return echec;
				}
			case 3:
				// Paire à une case du mur est
				if (grille[colonne][ligne] == Constantes.Case.V) {
					if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
						return succes;
					} else if (grille[colonneOriginale - 1][ligneOriginale] == symboleJoueurCourant) {
						return succes + 1;
					} else {
						return echec;
					}
				} else {
					if (grille[colonneOriginale - 1][ligneOriginale] == Constantes.Case.V) {
						if (grille[colonneOriginale - 2][ligneOriginale] == Constantes.Case.V) {
							return succes;
						} else if (grille[colonneOriginale - 2][ligneOriginale] == symboleJoueurCourant) {
							return succes + 1;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			case Constantes.NB_COLONNES - 1:
				// Paire à une case du mur ouest
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
						} else if (grille[colonne + 1][ligne] == symboleJoueurCourant) {
							return succes + 1;
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
					} else if (grille[colonne + 1][ligne] == symboleJoueurCourant) {
						return succes + 1;
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
					} else if (grille[colonneOriginale - 2][ligneOriginale] == symboleJoueurCourant) {
						return succes + 1;
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
						} else if (grille[colonne + 1][ligne] == symboleJoueurCourant) {
							return succes + 1;
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
		if (colonneOriginale < colonne) {

			if (colonneOriginale > 0 && ligneOriginale > 0) {
				// Ne pas chercher à analyser ce qui fait déjà partie d'un
				// triplet.
				if (grille[colonneOriginale - 1][ligneOriginale - 1] == symboleJoueurCourant) {
					return echec;
				}
			}

			// Début de l'analyse propre.
			// Peut remonter au moins 2 fois vers le nord-ouest.
			if (colonneOriginale > 1 && ligneOriginale > 1) {
				if (grille[colonneOriginale - 1][ligneOriginale - 1] == Constantes.Case.V) {
					if (grille[colonneOriginale - 2][ligneOriginale - 2] == Constantes.Case.V) {
						return succes;
					} else if (grille[colonneOriginale - 2][ligneOriginale - 2] == symboleJoueurCourant) {
						return succes + 1;
					} else {
						if (colonne < Constantes.NB_COLONNES && ligne < Constantes.NB_LIGNES) {
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
					if (colonne < Constantes.NB_COLONNES - 1 && ligne < Constantes.NB_LIGNES - 1) {
						if (grille[colonne][ligne] == Constantes.Case.V) {
							if (grille[colonne + 1][ligne + 1] == Constantes.Case.V) {
								return succes;
							} else if (grille[colonne + 1][ligne + 1] == symboleJoueurCourant) {
								return succes + 1;
							} else {
								return echec;
							}
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			} else {
				// On sait alors que l'on peut remonter une seule fois vers le
				// nord-ouest.
				if (colonneOriginale > 0 && ligneOriginale > 0) {
					if (grille[colonneOriginale - 1][ligneOriginale - 1] == Constantes.Case.V) {
						// Vérifier si de l'autre côté c'est bon.
						if (colonne < Constantes.NB_COLONNES && ligne < Constantes.NB_LIGNES) {
							if (grille[colonne][ligne] == Constantes.Case.V) {
								return succes;
							} else {
								return echec;
							}
						} else {
							return echec;
						}
					} else {
						// On doit descendre au moins deux fois vers le
						// sud-est.
						if (colonne < Constantes.NB_COLONNES - 1 && ligne < Constantes.NB_LIGNES - 1) {
							if (grille[colonne][ligne] == Constantes.Case.V) {
								if (grille[colonne + 1][ligne + 1] == Constantes.Case.V) {
									return succes;
								} else if (grille[colonne + 1][ligne + 1] == symboleJoueurCourant) {
									return succes + 1;
								} else {
									return echec;
								}
							} else {
								return echec;
							}
						} else {
							return echec;
						}
					}
				} else {
					// On est sur un des murs nord ou ouest. Dans ce cas on est
					// sûr d'avoir assez de cases à analyser puisque elles sont
					// filtrées en amont (cf determinerTypeDeCluster()). On doit
					// descendre vers le sud-est.
					if (grille[colonne][ligne] == Constantes.Case.V) {
						if (grille[colonne + 1][ligne + 1] == Constantes.Case.V) {
							return succes;
						} else if (grille[colonne + 1][ligne + 1] == symboleJoueurCourant) {
							return succes + 1;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			}
		}

		// Direcetion sud
		if (colonneOriginale == colonne) {
			if (ligneOriginale > 0) {
				// Si on trouve déjà une case du même type à l'ouest du cluster
				// alors il ne sert à rien de continuer l'analyse puisqu'une
				// utilité plus importante aura déjà été déterminée.
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
					} else if (grille[colonneOriginale][ligneOriginale - 2] == symboleJoueurCourant) {
						return succes + 1;
					} else {
						return echec;
					}
				} else {
					return echec;
				}
			case 3:
				// Paire à une case du mur sud
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
						} else if (grille[colonneOriginale][ligneOriginale - 2] == symboleJoueurCourant) {
							return succes + 1;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			case Constantes.NB_LIGNES - 1:
				// Paire à une case du mur nord
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
						} else if (grille[colonne][ligne + 1] == symboleJoueurCourant) {
							return succes + 1;
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
					} else if (grille[colonne][ligne + 1] == symboleJoueurCourant) {
						return succes + 1;
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
					} else if (grille[colonneOriginale][ligneOriginale - 2] == symboleJoueurCourant) {
						return succes + 1;
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
						} else if (grille[colonne][ligne + 1] == symboleJoueurCourant) {
							return succes + 1;
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
		if (colonneOriginale > colonne) {

			if (colonneOriginale < Constantes.NB_COLONNES - 1 && ligneOriginale > 0) {
				// Ne pas chercher à analyser ce qui fait déjà partie d'un
				// triplet.
				if (grille[colonneOriginale + 1][ligneOriginale - 1] == symboleJoueurCourant) {
					return echec;
				}
			}

			// Début de l'analyse propre.
			// Peut remonter au moins 2 fois vers le nord-est.
			if (colonneOriginale < Constantes.NB_COLONNES - 2 && ligneOriginale > 1) {
				if (grille[colonneOriginale + 1][ligneOriginale - 1] == Constantes.Case.V) {
					if (grille[colonneOriginale + 2][ligneOriginale - 2] == Constantes.Case.V) {
						return succes;
					} else if (grille[colonneOriginale + 2][ligneOriginale - 2] == symboleJoueurCourant) {
						return succes + 1;
					} else {
						if (colonne >= 0 && ligne < Constantes.NB_LIGNES) {
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
					// On doit descendre vers le sud-ouest.
					if (colonne > 0 && ligne < Constantes.NB_LIGNES - 1) {
						if (grille[colonne][ligne] == Constantes.Case.V) {
							if (grille[colonne - 1][ligne + 1] == Constantes.Case.V) {
								return succes;
							} else if (grille[colonne - 1][ligne + 1] == symboleJoueurCourant) {
								return succes + 1;
							} else {
								return echec;
							}
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			} else {
				// On sait alors que l'on peut remonter une seule fois vers le
				// nord-est.
				if (colonneOriginale < Constantes.NB_COLONNES - 1 && ligneOriginale > 0) {
					if (grille[colonneOriginale + 1][ligneOriginale - 1] == Constantes.Case.V) {
						// Vérifier si de l'autre côté c'est bon.
						if (colonne >= 0 && ligne < Constantes.NB_LIGNES) {
							if (grille[colonne][ligne] == Constantes.Case.V) {
								return succes;
							} else {
								return echec;
							}
						} else {
							return echec;
						}
					} else {
						// On doit descendre au moins deux fois vers le
						// sud-ouest.
						if (colonne > 1 && ligne < Constantes.NB_LIGNES - 1) {
							if (grille[colonne][ligne] == Constantes.Case.V) {
								if (grille[colonne - 1][ligne + 1] == Constantes.Case.V) {
									return succes;
								} else if (grille[colonne - 1][ligne + 1] == symboleJoueurCourant) {
									return succes + 1;
								} else {
									return echec;
								}
							} else {
								return echec;
							}
						} else {
							return echec;
						}
					}
				} else {
					// On est sur un des murs sud ou est. Dans ce cas on est
					// sûr d'avoir assez de cases à analyser puisque elles sont
					// filtrées en amont (cf determinerTypeDeCluster()). On doit
					// descendre vers le sud-est.
					if (grille[colonne][ligne] == Constantes.Case.V) {
						if (grille[colonne + 1][ligne - 1] == Constantes.Case.V) {
							return succes;
						} else if (grille[colonne + 1][ligne - 1] == symboleJoueurCourant) {
							return succes + 1;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			}
		}

		// Aucune direction d'étude n'a été trouvée. Dans ce cas on renvoît un
		// echec.
		return echec;
	}

	/**
	 * Fonction servant à indiquer si le triplet peut être complété est donc
	 * rendu utile.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de départ.
	 * @param colonne
	 *            l'indice colonne de la case se situant après la dernière case
	 *            du même symbole.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de départ.
	 * @param ligne
	 *            l'indice ligne de la case se situant après la dernière case du
	 *            même symbole.
	 * @param symboleJoueurCourant
	 *            le symbole utilisé pour l'analyse.
	 * @return un entier indiquant l'utilité du triplet.
	 */
	private int completerLeTriplet(int colonneOriginale, int colonne, int ligneOriginale, int ligne,
			Case symboleJoueurCourant) {

		int succes = 3;
		int echec = 0;

		// Direction est
		if (ligneOriginale == ligne) {
			if (colonneOriginale > 0) {
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

		// Direction sud-est (certaines cases ont déjà été filtrées. Cf
		// determinerTypeDeCluster())
		if (colonneOriginale < colonne) {
			// Cas où on peut remonter vers le nord-ouest
			if (colonneOriginale > 0 && ligneOriginale > 0) {
				if (grille[colonneOriginale - 1][ligneOriginale - 1] == Constantes.Case.V) {
					return succes;
				} else {
					// Cas où on doit descendre vers le sud-est
					if (colonne < Constantes.NB_COLONNES && ligne < Constantes.NB_LIGNES) {
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
				// Cas où on doit descendre vers le sud-est
				if (grille[colonne][ligne] == Constantes.Case.V) {
					return succes;
				} else {
					return echec;
				}
			}
		}

		// Direction sud
		if (colonneOriginale == colonne) {
			if (ligneOriginale > 0) {
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

		// Direction sud-ouest (certaines cases ont déjà été filtrées. Cf
		// determinerTypeDeCluster())
		if (colonneOriginale > colonne) {
			if (colonne >= 0 && ligne < Constantes.NB_LIGNES) {
				if (grille[colonne][ligne] == Constantes.Case.V) {
					return succes;
				} else {
					if (colonneOriginale < Constantes.NB_COLONNES - 1 && ligneOriginale > 0) {
						if (grille[colonneOriginale + 1][ligneOriginale - 1] == Constantes.Case.V) {
							return succes;
						} else {
							return echec;
						}
					} else {
						return echec;
					}
				}
			} else {
				if (grille[colonneOriginale + 1][ligneOriginale - 1] == Constantes.Case.V) {
					return succes;
				} else {
					return echec;
				}
			}
		}

		return echec;
	}

	/**
	 * Fonction servant à déterminer le nombre de cases alignées.
	 * 
	 * @param colonne
	 *            l'indice colonne de la grille.
	 * @param ligne
	 *            l'indice ligne de la grille.
	 * @param symboleJoueurCourant
	 *            le symbole utilisé pour l'analyse.
	 * @return un entier renvoyant l'utilité de la grille.
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
		case 1:
			utilitePossible = trouverCellulesVidesAutourDe(colonneOriginale, ligneOriginale, Constantes.Directions.EST, symboleJoueurCourant);
			if (utilitePossible == 1) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilité à minimum 1 pour le premier singleton
				// dévouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 2:
			utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne, symboleJoueurCourant);
			if (utilitePossible == 2) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilité à minimum 2 pour la première paire
				// dévouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 3:
			utilitePossible = completerLeTriplet(colonneOriginale, colonne, ligneOriginale, ligne,
					symboleJoueurCourant);
			if (utilitePossible == 3) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre utilité à minimum 3 pour le premier triplet découvert.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
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
		case 1:
			utilitePossible = trouverCellulesVidesAutourDe(colonneOriginale, ligneOriginale, Constantes.Directions.SUDEST, symboleJoueurCourant);
			if (utilitePossible == 1) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilité à minimum 1 pour le premier singleton
				// dévouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 2:
			// Eliminer les cas où la paire se trouve dans une zone où on ne
			// peut pas gagner.
			if (!Constantes.pairesInutilesSE.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				if (utilitePossible == 2) {
					utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
					// Mettre l'utilité à minimum 2 pour la première paire
					// dévouverte.
					if (utilite < utilitePossible) {
						utilite = utilitePossible;
					}
				}
			}
			break;
		case 3:
			// Eliminer les cas où le triplet se trouve dans une zone où on ne
			// peut pas gagner.
			if (!Constantes.tripletsInutilesSE.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				utilitePossible = completerLeTriplet(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				if (utilitePossible == 3) {
					utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
					// Mettre l'utilité à minimum 3 pour le premier triplet
					// découvert.
					if (utilite < utilitePossible) {
						utilite = utilitePossible;
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
		case 1:
			utilitePossible = trouverCellulesVidesAutourDe(colonneOriginale, ligneOriginale, Constantes.Directions.SUD, symboleJoueurCourant);
			if (utilitePossible == 1) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilité à minimum 1 pour le premier singleton
				// dévouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 2:
			utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne, symboleJoueurCourant);
			if (utilitePossible == 2) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilité à minimum 2 pour la première paire
				// dévouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 3:
			utilitePossible = completerLeTriplet(colonneOriginale, colonne, ligneOriginale, ligne,
					symboleJoueurCourant);
			if (utilitePossible == 3) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilité à minimum 3 pour le premier triplet
				// découvert.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		}

		// Direct sud-ouest

		colonne = colonneOriginale;
		ligne = ligneOriginale;
		nbAlignes = 0;

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
			utilitePossible = trouverCellulesVidesAutourDe(colonneOriginale, ligneOriginale, Constantes.Directions.SUDOUEST, symboleJoueurCourant);
			if (utilitePossible == 1) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilité à minimum 1 pour le premier singleton
				// dévouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 2:
			if (!Constantes.pairesInutilesSO.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				if (utilitePossible == 2) {
					utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
					// Mettre l'utilité à minimum 2 pour la première paire
					// dévouverte.
					if (utilite < utilitePossible) {
						utilite = utilitePossible;
					}
				}
			}
			break;
		case 3:
			if (!Constantes.tripletsInutilesSO.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				if (utilitePossible == 3) {
					utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
					// Mettre l'utilité à minimum 2 pour le premier triplet
					// dévouvert.
					if (utilite < utilitePossible) {
						utilite = utilitePossible;
					}
				}
			}
			break;
		}

		return 0;

	}

	/**
	 * Donne un score à la grille en fonction du joueur
	 * 
	 * @param symboleJoueurCourant
	 * @return
	 */
	public double evaluer(Case symboleJoueurCourant) {

		// Joueur 1 cherche à maximiser ses chances à gagner
		// Joueur 2 cherche à minimiser les chances que max gagne

		if (symboleJoueurCourant == Constantes.SYMBOLE_J1 || symboleJoueurCourant.equals(Constantes.SYMBOLE_J2)) {
			for (int ligne = 0; ligne < Constantes.NB_LIGNES; ligne++) {
				for (int colonne = 0; colonne < Constantes.NB_COLONNES; colonne++) {
					if (grille[colonne][ligne] == Constantes.SYMBOLE_J1) {
						determinerTypeDeCluster(colonne, ligne, Constantes.SYMBOLE_J1);
					}
				}
			}
			return utilite;
		}
		
		// Erreur de symbole
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
