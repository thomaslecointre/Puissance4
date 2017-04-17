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
	 * Constructeur qui cr�e une copie de la grille donn�e en argument
	 * 
	 * @param original
	 */
	private Grille(Grille original) {
		this.grille = original.grille;
		this.utilite = original.utilite;
	}

	/**
	 * Renvoie le contenu de la case aux coordonn�es donn�es en argument
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
	 * Fonction servant � renvoyer les coordonn�es de la prochaine case dans la
	 * m�me direction d�termin�e par les param�tres fournis.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de d�part.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de d�part.
	 * @param colonne
	 *            l'indice colonne de la case d'arriv�e.
	 * @param ligne
	 *            l'indice ligne de la case d'arriv�e.
	 * @return Un tableau d'entiers repr�sentant les coordonn�es de la case qui
	 *         nous int�resse.
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

		// Aucune direction d�termin�e.
		return null;
	}

	/**
	 * Fonction servant � determiner si la case pass�e en param�tres se trouve �
	 * c�t� de cases vides.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de d�part.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de d�part.
	 * @return Un entier indiquant l'utilit� de la case analys�e.
	 */
	private int trouverCellulesVidesAutourDe(int colonneOriginale, int ligneOriginale,
			Constantes.Directions direction, Constantes.Case symboleJoueurCourant) {

		int succes = 1;
		int echec = 0;
		int nbCasesSymboleJoueur = 0;

		// Il faut trouver un alignement de 3 cases contenant soit du vide, soit
		// une appartenant au joueur courant dans une direction
		// quelconque partant de la case initiale. On va it�rer sur les voisins
		// afin de chercher des alignements potentiellement int�ressants.
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
				// Comme l'alignement d�t�rmin� pr�cedemment est de 1, on cherche seulement les cases vides autour selon la direction fournie.
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
			// Le cas o� on n'a aucun quadruplet valide.
			return echec;
		}
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
	 *            l'indice colonne apr�s la derni�re case du m�me symbole.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de d�part.
	 * @param ligne
	 *            l'indice ligne apr�s la derni�re case du m�me symbole.
	 * @param symboleJoueurCourant
	 *            le symbole utilis� pour l'analyse.
	 * @return Un entier renvoyant l'utilit� de la paire.
	 */
	private int completerLaPaire(int colonneOriginale, int colonne, int ligneOriginale, int ligne,
			Constantes.Case symboleJoueurCourant) {

		int succes = 2; // Si le quatri�me symbole est identique au
						// symboleJoueurCourant on a effectivement un triplet
						// mais s�par� par une case vide. Il convient d'�lever
						// l'utilit� � celle d'un triplet (return succes + 1;).
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
					} else if (grille[colonneOriginale - 2][ligneOriginale] == symboleJoueurCourant) {
						return succes + 1;
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
				// Ne pas chercher � analyser ce qui fait d�j� partie d'un
				// triplet.
				if (grille[colonneOriginale - 1][ligneOriginale - 1] == symboleJoueurCourant) {
					return echec;
				}
			}

			// D�but de l'analyse propre.
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
						// V�rifier si de l'autre c�t� c'est bon.
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
					// s�r d'avoir assez de cases � analyser puisque elles sont
					// filtr�es en amont (cf determinerTypeDeCluster()). On doit
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
					} else if (grille[colonneOriginale][ligneOriginale - 2] == symboleJoueurCourant) {
						return succes + 1;
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
				// Ne pas chercher � analyser ce qui fait d�j� partie d'un
				// triplet.
				if (grille[colonneOriginale + 1][ligneOriginale - 1] == symboleJoueurCourant) {
					return echec;
				}
			}

			// D�but de l'analyse propre.
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
						// V�rifier si de l'autre c�t� c'est bon.
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
					// s�r d'avoir assez de cases � analyser puisque elles sont
					// filtr�es en amont (cf determinerTypeDeCluster()). On doit
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

		// Aucune direction d'�tude n'a �t� trouv�e. Dans ce cas on renvo�t un
		// echec.
		return echec;
	}

	/**
	 * Fonction servant � indiquer si le triplet peut �tre compl�t� est donc
	 * rendu utile.
	 * 
	 * @param colonneOriginale
	 *            l'indice colonne de la case de d�part.
	 * @param colonne
	 *            l'indice colonne de la case se situant apr�s la derni�re case
	 *            du m�me symbole.
	 * @param ligneOriginale
	 *            l'indice ligne de la case de d�part.
	 * @param ligne
	 *            l'indice ligne de la case se situant apr�s la derni�re case du
	 *            m�me symbole.
	 * @param symboleJoueurCourant
	 *            le symbole utilis� pour l'analyse.
	 * @return un entier indiquant l'utilit� du triplet.
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

		// Direction sud-est (certaines cases ont d�j� �t� filtr�es. Cf
		// determinerTypeDeCluster())
		if (colonneOriginale < colonne) {
			// Cas o� on peut remonter vers le nord-ouest
			if (colonneOriginale > 0 && ligneOriginale > 0) {
				if (grille[colonneOriginale - 1][ligneOriginale - 1] == Constantes.Case.V) {
					return succes;
				} else {
					// Cas o� on doit descendre vers le sud-est
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
				// Cas o� on doit descendre vers le sud-est
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

		// Direction sud-ouest (certaines cases ont d�j� �t� filtr�es. Cf
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
		case 1:
			utilitePossible = trouverCellulesVidesAutourDe(colonneOriginale, ligneOriginale, Constantes.Directions.EST, symboleJoueurCourant);
			if (utilitePossible == 1) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilit� � minimum 1 pour le premier singleton
				// d�vouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 2:
			utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne, symboleJoueurCourant);
			if (utilitePossible == 2) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilit� � minimum 2 pour la premi�re paire
				// d�vouverte.
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
				// Mettre utilit� � minimum 3 pour le premier triplet d�couvert.
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
				// Mettre l'utilit� � minimum 1 pour le premier singleton
				// d�vouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 2:
			// Eliminer les cas o� la paire se trouve dans une zone o� on ne
			// peut pas gagner.
			if (!Constantes.pairesInutilesSE.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				if (utilitePossible == 2) {
					utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
					// Mettre l'utilit� � minimum 2 pour la premi�re paire
					// d�vouverte.
					if (utilite < utilitePossible) {
						utilite = utilitePossible;
					}
				}
			}
			break;
		case 3:
			// Eliminer les cas o� le triplet se trouve dans une zone o� on ne
			// peut pas gagner.
			if (!Constantes.tripletsInutilesSE.contains(new Integer[] { colonneOriginale, ligneOriginale })) {
				utilitePossible = completerLeTriplet(colonneOriginale, colonne, ligneOriginale, ligne,
						symboleJoueurCourant);
				if (utilitePossible == 3) {
					utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
					// Mettre l'utilit� � minimum 3 pour le premier triplet
					// d�couvert.
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
				// Mettre l'utilit� � minimum 1 pour le premier singleton
				// d�vouverte.
				if (utilite < utilitePossible) {
					utilite = utilitePossible;
				}
			}
			break;
		case 2:
			utilitePossible = completerLaPaire(colonneOriginale, colonne, ligneOriginale, ligne, symboleJoueurCourant);
			if (utilitePossible == 2) {
				utilite += (double) utilitePossible / (double) Constantes.NB_TOUR_MAX;
				// Mettre l'utilit� � minimum 2 pour la premi�re paire
				// d�vouverte.
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
				// Mettre l'utilit� � minimum 3 pour le premier triplet
				// d�couvert.
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
				// Mettre l'utilit� � minimum 1 pour le premier singleton
				// d�vouverte.
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
					// Mettre l'utilit� � minimum 2 pour la premi�re paire
					// d�vouverte.
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
					// Mettre l'utilit� � minimum 2 pour le premier triplet
					// d�vouvert.
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
	 * Donne un score � la grille en fonction du joueur
	 * 
	 * @param symboleJoueurCourant
	 * @return
	 */
	public double evaluer(Case symboleJoueurCourant) {

		// Joueur 1 cherche � maximiser ses chances � gagner
		// Joueur 2 cherche � minimiser les chances que max gagne

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
