package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
    private String nom;
    private Chef chef;
    private Gaulois[] villageois;
    private int nbVillageois = 0;
    private Marche marche;

    public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
        this.nom = nom;
        villageois = new Gaulois[nbVillageoisMaximum];
        this.marche = new Marche(nbEtal);
    }

    public String getNom() {
        return nom;
    }

    public void setChef(Chef chef) {
        this.chef = chef;
    }

    public void ajouterHabitant(Gaulois gaulois) {
        if (nbVillageois < villageois.length) {
            villageois[nbVillageois++] = gaulois;
        }
    }

    public Gaulois trouverHabitant(String nomGaulois) {
        if (chef != null && nomGaulois.equals(chef.getNom())) {
            return chef;
        }
        for (int i = 0; i < nbVillageois; i++) {
            if (villageois[i].getNom().equals(nomGaulois)) {
                return villageois[i];
            }
        }
        return null;
    }

    public String afficherVillageois() throws VillageSansChefException {
        if (chef == null) {
            throw new VillageSansChefException("Le village n'a pas de chef !");
        }
        StringBuilder chaine = new StringBuilder();
        if (nbVillageois == 0) {
            chaine.append("Il n'y a encore aucun habitant au village du chef ")
                  .append(chef.getNom()).append(".\n");
        } else {
            chaine.append("Au village du chef ").append(chef.getNom())
                  .append(" vivent les légendaires gaulois :\n");
            for (int i = 0; i < nbVillageois; i++) {
                chaine.append("- ").append(villageois[i].getNom()).append("\n");
            }
        }
        return chaine.toString();
    }

    public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
        int indiceEtalLibre = marche.trouverEtalLibre();
        if (indiceEtalLibre != -1) {
            marche.utiliserEtal(indiceEtalLibre, vendeur, produit, nbProduit);
            return vendeur.getNom() + " vend des " + produit + " à l'étal n°" + (indiceEtalLibre + 1) + ".";
        }
        return "Aucun étal disponible pour le moment.";
    }

    public String rechercherVendeursProduit(String produit) {
        Etal[] etals = marche.trouverEtals(produit);
        if (etals.length == 0) {
            return "Il n'y a pas de vendeur qui propose des " + produit + " au marché.";
        }
        StringBuilder sb = new StringBuilder("Les vendeurs qui proposent des " + produit + " sont :\n");
        for (Etal etal : etals) {
            sb.append("- ").append(etal.getVendeur().getNom()).append("\n");
        }
        return sb.toString();
    }

    public Etal rechercherEtal(Gaulois vendeur) {
        return marche.trouverVendeur(vendeur);
    }

    public String partirVendeur(Gaulois vendeur) {
        Etal etal = marche.trouverVendeur(vendeur);
        if (etal != null) {
            return etal.libererEtal();
        }
        return vendeur.getNom() + " n'occupe pas d'étal.";
    }

    public String afficherMarche() {
        return marche.afficherMarche();
    }

    private class Marche {
        private Etal[] etals;

        private Marche(int nbEtal) {
            etals = new Etal[nbEtal];
            for (int i = 0; i < nbEtal; i++) {
                etals[i] = new Etal();
            }
        }

        void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
            etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
        }

        int trouverEtalLibre() {
            for (int i = 0; i < etals.length; i++) {
                if (!etals[i].isEtalOccupe()) {
                    return i;
                }
            }
            return -1;
        }

        Etal[] trouverEtals(String produit) {
            int count = 0;
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) count++;
            }
            Etal[] result = new Etal[count];
            int index = 0;
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.contientProduit(produit)) result[index++] = etal;
            }
            return result;
        }

        Etal trouverVendeur(Gaulois gaulois) {
            for (Etal etal : etals) {
                if (etal.isEtalOccupe() && etal.getVendeur().equals(gaulois)) return etal;
            }
            return null;
        }

        String afficherMarche() {
            StringBuilder chaine = new StringBuilder();
            int nbEtalVide = 0;
            for (Etal etal : etals) {
                if (etal.isEtalOccupe()) {
                    chaine.append(etal.afficherEtal()).append("\n");
                } else {
                    nbEtalVide++;
                }
            }
            chaine.append("Il reste ").append(nbEtalVide).append(" étals non utilisés dans le marché.\n");
            return chaine.toString();
        }
    }
}
