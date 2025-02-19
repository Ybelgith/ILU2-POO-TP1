package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder(
				"Le vendeur " + vendeur.getNom() + " quitte son étal, ");
		int produitVendu = quantiteDebutMarche - quantite;
		if (produitVendu > 0) {
			chaine.append(
					"il a vendu " + produitVendu + " parmi " + produit + ".\n");
		} else {
			chaine.append("il n'a malheureusement rien vendu.\n");
		}
		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		if (etalOccupe) {
			StringBuilder chaine = new StringBuilder();
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " à " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'étal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()
						+ ", est ravi de tout trouver sur l'étal de "
						+ vendeur.getNom() + "\n");
			}
			return chaine.toString();
		}
		return null;
	}

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}
	
	//(1) Classe interne Marche
    public static class Marche {
        private Etal[] etals;
      
   
        public Marche(int nombreEtals) { //construct
            etals = new Etal[nombreEtals];
            for (int i = 0; i < nombreEtals; i++) {
                etals[i] = new Etal(); 
            }
        }
        
        void utiliserEtal(int indiceEtal, Gaulois vendeur,
        		String produit, int nbProduit) {
        	  if (indiceEtal >= 0 && indiceEtal < etals.length) {
        	        Etal etal = etals[indiceEtal];
        	        if (!etal.isEtalOccupe()) {
        	            etal.occuperEtal(vendeur, produit, nbProduit);
        	        } else {
        	            System.out.println("L'étal est déjà occupé !");
        	        }
        	    } else {
        	        System.out.println("Indice d'étal invalide !");
        	    }
        	}
        
        //4
        int trouverEtalLibre() {
        	for(int i = 0 ; i < etals.length; i++) {
        		if(!etals[i].isEtalOccupe()) {
        			return i;
        		}
        		}
        	return -1;
        	}
        
    
        //5
        public Etal[] trouverEtals(String produit) {
          
            int count = 0;
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].contientProduit(produit)) {
                    count++;
                }
            }

         
            Etal[] result = new Etal[count];
            int index = 0;

           
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].contientProduit(produit)) {
                    result[index++] = etals[i];
                }
            }

            return result;
        }
        
        //6
        public Etal trouverVendeur(Gaulois gaulois) {
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].getVendeur() != null && etals[i].getVendeur().equals(gaulois)) {
                    return etals[i]; // Retourne l'étal où le vendeur est installé
                }
            }
            return null; 
        }
        
        //7
        public String afficherMarche() {
            StringBuilder sb = new StringBuilder();

            int nbEtalVide = 0;
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].isEtalOccupe()) {
                    sb.append(etals[i].afficherEtal()); 
                } else {
                    nbEtalVide++;
                }
            }

            
            sb.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");

            return sb.toString();
        }
        

        

        
    }
}
    
    
    
        
    
    
 

