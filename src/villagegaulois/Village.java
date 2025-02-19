package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
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