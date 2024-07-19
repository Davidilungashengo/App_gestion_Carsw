import objet.Camion;
import objet.Moto;
import objet.Vehicule;
import objet.Voiture;

import java.io.*;
import java.util.*;


import java.io.*;
import java.util.*;



class ParcVehicules {
    private HashMap<Integer, Vehicule> parc = new HashMap<>();

    public void ajouterVehicule(Vehicule v) {
        parc.put(v.getId(), v);
    }

    public void supprimerVehicule(int id) {
        parc.remove(id);
    }

    public void modifierVehicule(int id, Vehicule v) {
        if (parc.containsKey(id)) {
            parc.put(id, v);
        }
    }

    public Vehicule rechercherVehiculeParNom(String nom) {
        for (Vehicule v : parc.values()) {
            if (v.getNom().equalsIgnoreCase(nom)) {
                return v;
            }
        }
        return null;
    }

    public Vehicule rechercherVehiculeParId(int id) {
        return parc.get(id);
    }

    public List<Vehicule> listerVehiculesParLettre(char lettre) {
        List<Vehicule> result = new ArrayList<>();
        for (Vehicule v : parc.values()) {
            if (v.getNom().toLowerCase().charAt(0) == Character.toLowerCase(lettre)) {
                result.add(v);
            }
        }
        return result;
    }

    public int afficherNombreDeVehicules() {
        return parc.size();
    }

    public void mettreAJourPartiellementVehicule(int id, Map<String, String> attributs) {
        Vehicule v = parc.get(id);
        if (v != null) {
            for (Map.Entry<String, String> entry : attributs.entrySet()) {
                switch (entry.getKey().toLowerCase()) {
                    case "nom":
                        v.setNom(entry.getValue());
                        break;
                    case "marque":
                        v.setMarque(entry.getValue());
                        break;
                    case "annee":
                        v.setAnnee(Integer.parseInt(entry.getValue()));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void enregistrerDansFichier(String fichier) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fichier))) {
            for (Vehicule v : parc.values()) {
                writer.println(v.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chargerDepuisFichier(String fichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[1]);
                String nom = parts[2];
                String marque = parts[3];
                int annee = Integer.parseInt(parts[4]);

                switch (parts[0].toLowerCase()) {
                    case "voiture":
                        int nombreDePortes = Integer.parseInt(parts[5]);
                        parc.put(id, new Voiture(id, nom, marque, annee, nombreDePortes));
                        break;
                    case "camion":
                        int capaciteDeCharge = Integer.parseInt(parts[5]);
                        parc.put(id, new Camion(id, nom, marque, annee, capaciteDeCharge));
                        break;
                    case "moto":
                        int cylindree = Integer.parseInt(parts[5]);
                        parc.put(id, new Moto(id, nom, marque, annee, cylindree));
                        break;
                    default:
                        parc.put(id, new Vehicule(id, nom, marque, annee));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ParcVehicules parc = new ParcVehicules();

    public static void main(String[] args) {
        chargerDonnees();
        while (true) {
            afficherMenu();
            int choix = Integer.parseInt(scanner.nextLine());
            switch (choix) {
                case 1:
                    ajouterVehicule();
                    break;
                case 2:
                    supprimerVehicule();
                    break;
                case 3:
                    modifierVehicule();
                    break;
                case 4:
                    rechercherVehiculeParNom();
                    break;
                case 5:
                    rechercherVehiculeParId();
                    break;
                case 6:
                    listerVehiculesParLettre();
                    break;
                case 7:
                    afficherNombreDeVehicules();
                    break;
                case 8:
                    enregistrerDonnees();
                    break;
                case 9:
                    System.out.println("Au revoir!");
                    enregistrerDonnees();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("Menu:");
        System.out.println("1. Ajouter un véhicule");
        System.out.println("2. Supprimer un véhicule");
        System.out.println("3. Modifier un véhicule");
        System.out.println("4. Rechercher un véhicule par nom");
        System.out.println("5. Rechercher un véhicule par identifiant");
        System.out.println("6. Lister les véhicules par lettre");
        System.out.println("7. Afficher le nombre de véhicules");
        System.out.println("8. Enregistrer les données");
        System.out.println("9. Quitter");
        System.out.print("Choisissez une option: ");
    }

    private static void ajouterVehicule() {
        System.out.print("Entrez le type de véhicule (voiture, camion, moto): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("Entrez l'ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Entrez le nom: ");
        String nom = scanner.nextLine();
        System.out.print("Entrez la marque: ");
        String marque = scanner.nextLine();
        System.out.print("Entrez l'année: ");
        int annee = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case "voiture":
                System.out.print("Entrez le nombre de portes: ");
                int nombreDePortes = Integer.parseInt(scanner.nextLine());
                parc.ajouterVehicule(new Voiture(id, nom, marque, annee, nombreDePortes));
                break;
            case "camion":
                System.out.print("Entrez la capacité de charge (kg): ");
                int capaciteDeCharge = Integer.parseInt(scanner.nextLine());
                parc.ajouterVehicule(new Camion(id, nom, marque, annee, capaciteDeCharge));
                break;
            case "moto":
                System.out.print("Entrez la cylindrée (cc): ");
                int cylindree = Integer.parseInt(scanner.nextLine());
                parc.ajouterVehicule(new Moto(id, nom, marque, annee, cylindree));
                break;
            default:
                System.out.println("Type de véhicule invalide.");
        }
    }

    private static void supprimerVehicule() {
        System.out.print("Entrez l'ID du véhicule à supprimer: ");
        int id = Integer.parseInt(scanner.nextLine());
        parc.supprimerVehicule(id);
    }

    private static void modifierVehicule() {
        System.out.print("Entrez l'ID du véhicule à modifier: ");
        int id = Integer.parseInt(scanner.nextLine());
        Vehicule v = parc.rechercherVehiculeParId(id);
        if (v != null) {
            System.out.print("Entrez le nouveau nom (laissez vide pour ne pas changer): ");
            String nom = scanner.nextLine();
            if (!nom.isEmpty()) {
                v.setNom(nom);
            }
            System.out.print("Entrez la nouvelle marque (laissez vide pour ne pas changer): ");
            String marque = scanner.nextLine();
            if (!marque.isEmpty()) {
                v.setMarque(marque);
            }
            System.out.print("Entrez la nouvelle année (laissez vide pour ne pas changer): ");
            String anneeStr = scanner.nextLine();
            if (!anneeStr.isEmpty()) {
                int annee = Integer.parseInt(anneeStr);
                v.setAnnee(annee);
            }
            parc.modifierVehicule(id, v);
        } else {
            System.out.println("Véhicule non trouvé.");
        }
    }

    private static void rechercherVehiculeParNom() {
        System.out.print("Entrez le nom du véhicule: ");
        String nom = scanner.nextLine();
        Vehicule v = parc.rechercherVehiculeParNom(nom);
        if (v != null) {
            System.out.println(v);
        } else {
            System.out.println("Véhicule non trouvé.");
        }
    }

    private static void rechercherVehiculeParId() {
        System.out.print("Entrez l'ID du véhicule: ");
        int id = Integer.parseInt(scanner.nextLine());
        Vehicule v = parc.rechercherVehiculeParId(id);
        if (v != null) {
            System.out.println(v);
        } else {
            System.out.println("Véhicule non trouvé.");
        }
    }

    private static void listerVehiculesParLettre() {
        System.out.print("Entrez la lettre: ");
        char lettre = scanner.nextLine().charAt(0);
        List<Vehicule> vehicules = parc.listerVehiculesParLettre(lettre);
        for (Vehicule v : vehicules) {
            System.out.println(v);
        }
    }

    private static void afficherNombreDeVehicules() {
        System.out.println("Nombre de véhicules: " + parc.afficherNombreDeVehicules());
    }

    private static void enregistrerDonnees() {
        parc.enregistrerDansFichier("parc_vehicules.txt");
    }

    private static void chargerDonnees() {
        parc.chargerDepuisFichier("parc_vehicules.txt");
    }
}
