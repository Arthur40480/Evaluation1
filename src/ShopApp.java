
import java.util.ArrayList;
import java.util.Scanner;
import fr.fms.authentication.Authenticate;
import fr.fms.business.IBusinessImpl;
import fr.fms.entities.Formation;
import fr.fms.entities.User;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;

public class ShopApp {
	private static Scanner scan = new Scanner(System.in); 
	private static IBusinessImpl business = new IBusinessImpl();
	private static Authenticate authenticate = new Authenticate();
	
	public static final String TEXT_YELLOW = "\u001B[33m";
	public static final String TEXT_BLUE = "\u001B[36m";
	public static final String TEXT_RESET = "\u001B[0m";	
	private static final String COLUMN_ID = "ID";
	private static final String COLUMN_NAME = "NAME";
	private static final String COLUMN_DESCRIPTION = "DESCRIPTION";
	private static final String COLUMN_DURATION = "DURATION";
	private static final String COLUMN_REMOTE = "REMOTE";
	private static final String COLUMN_PRICE = "PRIX";
	
	private static int idUser = 0;
	private static String login = null; 
	private static boolean isAdmin = false;
	

	public static void main(String[] args) {
		System.out.println("Bonjour et bienvenu dans ma boutique, voici la liste d'articles en stock\n");
		displayFormations();
		int choice = 0;
		while(choice != 11) {
			displayMenu();
			choice = scanInt();
			switch(choice) {
				case 1 : addFormationToCart();				
					break;					
				case 2 : removeFormationToCart();
					break;					
				case 3 : displayCart(true);
					break;					
				case 4 : displayFormations();
					break;
				case 5 : displayRemoteFormations();
					break;
				case 6 : displayOnsiteFormations();
					break;
				case 7 : displayFormationsByKeyword();
					break;
				case 8 : displayCategories();
					break;
				case 9 : displayFormationsByCategoryId();
					break;
				case 10 : connection();
					break;
				case 11 : System.out.println("à bientôt dans notre boutique :)");
					break;					
				default : 
					if(isAdmin) {
						switch(choice) {
						case 12 : displayCategoryMenu();
							break;
						case 13 : displayFormationsMenu();
							break;
						case 14 : displayCustomerOrders();
							break;
						default : System.out.println("veuillez saisir une valeur entre 1 et 14");
						}
					}else {
						System.out.println("veuillez saisir une valeur entre 1 et 11");
					}
			}
		}
	}

	/**
	 * Méthode qui affiche le menu principale
	 */
	public static void displayMenu() {	
		System.out.println();
		if(isAdmin)	System.out.print(TEXT_YELLOW + "Compte ADMINISTRATEUR ");
		if(login != null && isAdmin == false)	System.out.print(TEXT_BLUE + "Compte : " + login);
		System.out.println("\n" + "Pour réaliser une action, tapez le code correspondant");
		System.out.println("1 : Ajouter un article au panier");
		System.out.println("2 : Retirer un article du panier");
		System.out.println("3 : Afficher mon panier + total pour passer commande");
		System.out.println("4 : Afficher tous les articles en stock");
		System.out.println("5 : Afficher toutes les formations en distanciel");
		System.out.println("6 : Afficher toutes les formations en présentiel");
		System.out.println("7 : Rechercher un article via un mot-clé");
		System.out.println("8 : Afficher toutes les catégories en base");
		System.out.println("9 : Afficher tous les articles d'une catégorie");
		System.out.println("10 : Connexion(Deconnexion) à votre compte");
		System.out.println("11 : Sortir de l'application");
		if(isAdmin) {
			System.out.println();
			System.out.println("------- OPTIONS ADMINISTRATEUR -------");
			System.out.println("12 : Gérer les catégories");
			System.out.println("13 : Gérer les formations");
			System.out.println("14 : Afficher les commandes d'un client");
		}
	}
	
	/**
	 * Méthode qui affiche le menu des catégories si admin
	 */
	public static void displayCategoryMenu() {
		int choice = 0;
		while(choice != 4) {
			System.out.println("\n" + "Pour réaliser une action, tapez le code correspondant");
			System.out.println("1: Ajouter une catégorie");
			System.out.println("2: Mettre à jour une catégorie");
			System.out.println("3: Supprimer une catégorie");
			System.out.println("4: Menu Principale");
			choice = scanInt();
			scan.nextLine();
			
			switch(choice) {
				case 1: adminCreateCategory();
					break;
				case 2: adminUpdateCategory();
					break;
				case 3: adminDeleteCategory();
					break;
				case 4: 
					break;
				default : System.out.println("veuillez saisir une valeur entre 1 et 4");
			}
		}
	}
	
	/**
	 * Méthode qui affiche le menu des formations si admin
	 */
	public static void displayFormationsMenu() {
		int choice = 0;
		while(choice != 4) {
			System.out.println("\n" + "Pour réaliser une action, tapez le code correspondant");
			System.out.println("1: Ajouter une formation");
			System.out.println("2: Mettre à jour une formation");
			System.out.println("3: Supprimer une formation");
			System.out.println("4: Menu Principale");
			choice = scanInt();
			scan.nextLine();
			
			switch(choice) {
				case 1: adminCreateFormation();
					break;
				case 2: adminUpdateFormation();
					break;
				case 3: adminDeleteFormation();
					break;
				case 4: 
					break;
				default : System.out.println("veuillez saisir une valeur entre 1 et 4");
			}
		}
	}
	
	/**
	 * Méthode qui créer une formation
	 */
	public static void adminCreateFormation() {
		System.out.println("Saisissez le nom de la formation (50 caractère max)");
		String catName = scan.nextLine();
		System.out.println("Saisissez la description de la formation (100 caractère max)");
		String description = scan.nextLine();
		System.out.println("Saisissez la durée (En jours) de la formation");
		int duration = scanInt();
		System.out.println("En remote ? Oui/Non");
		boolean remote;
		if(scan.next().equalsIgnoreCase("Oui")) {
			remote = true;
		}else {
			remote = false;
		}
		System.out.println("Saisissez le prix (En €) de la formation");
		double price = scan.nextDouble();
		System.out.println("Saisissez l'id de la catégorie à laquelle appartient la formation");
		int idCategory = scanInt();
		if(business.readCategory(idCategory) != null) {
			Formation newFormation = new Formation(catName, description, duration, remote, price, idCategory);
			if(business.createFormation(newFormation)) {
				System.out.println("Ajout de la formation ok !");
			}
		}else {
			System.out.println("l'id de la catégorie que vous avez saisie n'existe pas !");
		}
	}
	
	/**
	 * Méthode qui modifie une formation
	 */
	public static void adminUpdateFormation() {
		System.out.println("Selectionner l'id de la formation à mettre à jour");
		int id = scanInt();
		scan.nextLine();
		if(business.readOneFormation(id) != null) {
			System.out.println("Saisissez le nom de la formation (50 caractère max)");
			String catName = scan.nextLine();
			System.out.println("Saisissez la description de la formation (100 caractère max)");
			String description = scan.nextLine();
			System.out.println("Saisissez la durée (En jours) de la formation");
			int duration = scanInt();
			System.out.println("En remote ? Oui/Non");
			boolean remote;
			if(scan.next().equalsIgnoreCase("Oui")) {
				remote = true;
			}else {
				remote = false;
			}
			System.out.println("Saisissez le prix (En €) de la formation");
			double price = scan.nextDouble();
			System.out.println("Saisissez l'id de la catégorie à laquelle appartient la formation");
			int idCategory = scanInt();
			if(business.readCategory(idCategory) != null) {
				Formation newFormation = new Formation(id, catName, description, duration, remote, price, idCategory);
				if(business.updateFormation(newFormation)) {
					System.out.println("Mise à jour de la formation ok !");
				}
			} else{
				System.out.println("l'id de la catégorie que vous avez saisie n'existe pas !");
			}
		}else {
			System.out.println("la formation que vous souhaitez mettre à jour n'existe pas, pb de saisi id");
		}
	}
	
	/**
	 * Méthode qui supprime une formation
	 */
	public static void adminDeleteFormation() {
		System.out.println("Selectionner l'id de la formation à supprimer");
		int id = scanInt();
		if(business.readOneFormation(id) != null) {			
			if(business.deleteFormation(business.readOneFormation(id))) {
				System.out.println("Suppression de la formation ok !");	
			}
		}else {
			System.out.println("la catégorie que vous souhaitez supprimer n'existe pas, pb de saisi id");
		}
	}
	
	/**
	 * Méthode qui affiche tous les articles en base en centrant le texte 
	 */
	public static void displayFormations() { 		
	    String header = String.format("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s |",
                "ID", "Name", "Description", "Duration", "Remote", "Price");
	    String separator = "+------+------------------------------------------+---------------------------------------------------------------------------------------+----------+--------+---------+";
	    System.out.println(separator);
	    System.out.println(header);
	    System.out.println(separator);
	    business.readFormations().forEach(article -> {
	    	System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s |%n",
       article.getId(), article.getName(), article.getDescription(),
       article.getDuration() + "jours", article.isRemote(), article.getUnitaryPrice() + "€");
	    });
	    System.out.println(separator);
	}
	
	/**
	 * Méthode qui affiche tous les articles par catégorie en utilisant printf
	 */
	private static void displayFormationsByCategoryId() {
		System.out.println("saisissez l'id de la catégorie concerné");
		int id = scanInt();
		Category category = business.readOneCategory(id);
	    String separator = "+------+------------------------------------------+---------------------------------------------------------------------------------------+----------+--------+---------+";
		if(category != null) {
			System.out.println();
			System.out.printf("                                                                 AFFICHAGE PAR CATEGORIE    %n");
			System.out.printf("                                                                        %-10s               %n",category.getName());
			System.out.println(separator);
			System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s |%n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_REMOTE, COLUMN_PRICE);
			System.out.println(separator);
			business.readFormationsByCatId(id).forEach( f -> System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s |%n",f.getId(), f.getName(), f.getDescription(), f.getDuration() + "jours", f.isRemote(), f.getUnitaryPrice() + "€"));
			System.out.println(separator);
		}
		else System.out.println("cette catégorie n'existe pas !");
	}
	
	/**
	 * Méthode qui affiche toutes les formations suivant un mot-clé
	 */
	public static void displayFormationsByKeyword() {
	    String separator = "+------+------------------------------------------+---------------------------------------------------------------------------------------+----------+--------+---------+";
		System.out.println("saisissez un mot clé");
		String keyword = scan.next();
		ArrayList<Formation> formationList = business.readFormationsByKeyword(keyword);
		if(formationList.size() == 0) {
			System.out.println("Aucune formation correspondante n'a été trouvée pour le mot-clé '" + keyword + "'.");
		} else {
			System.out.println(separator);
			System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s |%n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_REMOTE, COLUMN_PRICE);
			System.out.println(separator);
			formationList.forEach(f -> System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s |%n", f.getId(), f.getName(), f.getDescription(), f.getDuration() + "jours", f.isRemote(), f.getUnitaryPrice() + "€"));
			System.out.println(separator);
		}
	}
	
	/**
	 * Méthode qui affiche toutes les formations en distanciel
	 */
	public static void displayRemoteFormations() {
	    String separator = "+------+------------------------------------------+---------------------------------------------------------------------------------------+----------+---------+";
		ArrayList<Formation> formationList = business.readRemoteFormations();
		if(formationList.size() == 0) {
			System.out.println("Aucune formation en distanciel pour le moment");
		} else {
			System.out.printf("                                                                 FORMATION EN DISTANCIEL    %n");
			System.out.println(separator);
			System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-7s |%n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_PRICE);
			System.out.println(separator);
			formationList.forEach(f -> System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-7s |%n", f.getId(), f.getName(), f.getDescription(), f.getDuration() + "jours", f.getUnitaryPrice() + "€"));
			System.out.println(separator);
		}
	}
	
	/**
	 * Méthode qui affiche toutes les formations en présentiel
	 */
	public static void displayOnsiteFormations() {
	    String separator = "+------+------------------------------------------+---------------------------------------------------------------------------------------+----------+---------+";
		ArrayList<Formation> formationList = business.readOnsiteFormations();
		if(formationList.size() == 0) {
			System.out.println("Aucune formation en présentiel pour le moment");
		} else {
			System.out.printf("                                                                 FORMATION EN PRESENTIEL    %n");
			System.out.println(separator);
			System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-7s |%n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION, COLUMN_DURATION, COLUMN_PRICE);
			System.out.println(separator);
			formationList.forEach(f -> System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-7s |%n", f.getId(), f.getName(), f.getDescription(), f.getDuration() + "jours", f.getUnitaryPrice() + "€"));
			System.out.println(separator);
		}
	}

	/**
	 * Méthode qui affiche toutes les catégories
	 */
	private static void displayCategories() {
	    String separator = "+------+----------------------------------+-------------------------------------------------------------+";
	    System.out.println(separator);
		System.out.printf("| %-4s | %-32s | %-59s |%n", COLUMN_ID, COLUMN_NAME, COLUMN_DESCRIPTION);
		System.out.println(separator);
		business.readCategories().forEach(c -> System.out.printf("| %-4s | %-32s | %-59s |%n", c.getId(), c.getName(), c.getDescription()));	
		System.out.println(separator);
	}
	
	/**
	 * Méthode qui créer une catégorie
	 */
	private static void adminCreateCategory() {
		System.out.println("Saisissez le nom de la catégorie (30 caractère max)");
		String catName = scan.nextLine();
		System.out.println("Saisissez la description de la catégorie (100 caractère max)");
		String description = scan.nextLine();
		Category newCategory = new Category(catName, description);
		if(business.createCategory(newCategory)) {
			System.out.println("Ajout de la catégorie ok !");
		}
	}
	
	/**
	 * Méthode qui met à jour une catégorie
	 */
	private static void adminUpdateCategory() {
		System.out.println("Selectionner l'id de la catégorie à mettre à jour");
		int id = scanInt();
		scan.nextLine();
		if(business.readCategory(id) != null) {
			System.out.println("Saisissez le nom de la catégorie (30 caractère max)");
			String catName = scan.nextLine();
			System.out.println("Saisissez la description de la catégorie (100 caractère max)");
			String description = scan.nextLine();
			Category updatedCategory = new Category(id, catName, description);
			if(business.updateCategory(updatedCategory)) {
				System.out.println("Mise à jour de la catégorie ok !");
			}
		}else {
			System.out.println("la catégorie que vous souhaitez mettre à jour n'existe pas, pb de saisi id");
		}
	}
	
	/**
	 * Méthode qui supprime une catégorie
	 */
	private static void adminDeleteCategory() {
		System.out.println("Selectionner l'id de la catégorie à supprimer");
		int id = scanInt();
		if(business.readCategory(id) != null) {
			System.out.println("Attention, la suppression de cette catégorie entraînera la suppression de toutes les formations liées à celle-ci !");
			System.out.println("Souhaitez vous la supprimer quand même ? Oui/Non :");
			if(scan.next().equalsIgnoreCase("Oui")) {
				if(business.deleteCategory(business.readCategory(id))) {
					System.out.println("Suppression de la catégorie ok !");
				}
			}
		}else {
			System.out.println("la catégorie que vous souhaitez supprimer n'existe pas, pb de saisi id");
		}
	}
	
	/**
	 * Méthode qui supprime un article du panier
	 */
	public static void removeFormationToCart() {
		System.out.println("Selectionner l'id de l'article à supprimer du panier");
		int id = scanInt();
		business.rmFromCart(id);
		displayCart(false);
	}

	/**
	 * Méthode qui ajoute un article au panier
	 */
	public static void addFormationToCart() {
		System.out.println("Selectionner l'id de l'article à ajouter au panier");
		int id = scanInt();
		Formation formation = business.readOneFormation(id);
		if(formation != null) {
			business.addToCart(formation);
			displayCart(false);
		}
		else System.out.println("l'article que vous souhaitez ajouter n'existe pas, pb de saisi id");
	} 
	
	/**
	 * Méthode qui affiche le contenu du panier + total de la commande + propose de passer commande
	 */
	private static void displayCart(boolean flag) {
		if(business.isCartEmpty()) 	System.out.println("PANIER VIDE");
		else {
			System.out.println("CONTENU DU PANIER :");
		    String header = String.format("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s | %-6s |",
	                "ID", "Name", "Description", "Duration", "Remote", "Price", "Quantity");
		    String separator = "+------+------------------------------------------+---------------------------------------------------------------------------------------+----------+--------+---------+----------+";
		    System.out.println(separator);
		    System.out.println(header);
		    System.out.println(separator);
			business.getCart().forEach(f -> System.out.printf("| %-4s | %-40s | %-85s | %-8s | %-6s | %-7s | %-8s |%n", f.getId(), f.getName(), f.getDescription(), f.getDuration() + "jours", f.isRemote(), f.getUnitaryPrice()  + "€", String.valueOf(f.getQuantity())));
			if(flag) {
				System.out.println(separator);
				System.out.println();
				System.out.println("MONTANT TOTAL : " + business.getTotal());
				System.out.println("Souhaitez vous passer commande ? Oui/Non :");
				if(scan.next().equalsIgnoreCase("Oui")) {
					nextStep();
				}
			}else {
				System.out.println(separator);
			}
		}
	}
	
	/**
	 * Méthode qui affiche les commandes d'un client via sont id
	 */
	public static void displayCustomerOrders() {
	    String header = String.format("| %-4s | %-20s | %-20s |", "ID", "MONTANT", "DATE");
	    String separator = "+------+----------------------+----------------------+";
		System.out.println("Saisissez l'id du client pour lequel vous souhaitez afficher les commandes.");
		int id = scanInt();
		if(business.readCustomerById(id) != null) {
			if(business.readAllOrderByCustomerId(id).size() > 0) {
				System.out.println("HISTORIQUE DES COMMANDES DU CLIENT N°" + id);
				System.out.println(separator);
				System.out.println(header);
				System.out.println(separator);
				business.readAllOrderByCustomerId(id).forEach( o -> System.out.printf("| %-4s | %-20s | %-20s |%n", o.getIdOrder(), o.getAmount() + "€", o.getDate()));
				System.out.println(separator);
				System.out.println();
			}else {
				System.out.println("Ce client n'a pas encore effectué de commande !");
			}
		} else {
			System.out.println("Le client que vous avez saisi n'existe pas !");
		}
	}
	
	/**
	 * Méthode qui passe la commande, l'utilisateur doit être connecté
	 * si c'est le cas, l'utilisateur sera invité à associé un client à sa commande
	 * si le client n'existe pas, il devra le créer
	 * puis une commande associée au client sera ajoutée en base
	 * De même, des commandes minifiées seront associées à la commande
	 * une fois toutes les opérations terminées correctement, le panier sera vidé et un numéro de commande attribué
	 */
	private static void nextStep() {
		if(login == null)	{ 
			System.out.println("Vous devez être connecté pour continuer");
			connection();
		}
		if(login != null) {
			int idCustomer = newCustomer(idUser);	
			if(idCustomer != 0) {
				int idOrder = business.order(idCustomer);	
				if(idOrder == 0)	System.out.println("pb lors du passage de commande");
				else {
					System.out.println("Votre commande a bien été validé, voici son numéro : " + idOrder);
					business.clearCart();
				}
			}
		}
	}

	/**
	 * Méthode qui ajoute un client en base s'il n'existe pas déjà 
	 * @return id du client afin de l'associer à la commande en cours
	 */
	private static int newCustomer(int idUser) {
		System.out.println("Avez vous déjà un compte client ? Saisissez une adresse email valide :");
		String email = scan.next();		
		if(isValidEmail(email)) {	
			Customer customer = authenticate.existCustomerByEmail(email);
			if(customer == null) {
				System.out.println("Nous n'avons pas de compte client associé, nous allons le créer ");
				scan.nextLine();	
				System.out.println("saisissez votre nom :");
				String name = scan.nextLine();
				System.out.println("saisissez votre prénom :");
				String fName = scan.next();					
				System.out.println("saisissez votre tel :");
				String tel = scan.next();		
				scan.nextLine(); 
				System.out.println("saisissez votre adresse :");
				String address = scan.nextLine();
				Customer cust = new Customer(name, fName, email, tel, address, idUser); 
				if(authenticate.addCustomer(cust))	
					return authenticate.existCustomerByEmail(email).getIdCustomer();
			}
			else {
				System.out.println("Nous allons associer la commande en cours avec le compte client : " + customer);
				return customer.getIdCustomer();
			}
		}
		else System.out.println("vous n'avez pas saisi un email valide");	
		return 0;
	}

	/**
	 * Méthode qui réalise la connexion/deconnexion d'un utilisateur
	 * si l'utilisateur n'existe pas, il lui est proposé d'en créer un
	 */
	private static void connection() {
		User userCurrent = null;
		if(login != null) {
			System.out.println("Souhaitez vous vous déconnecter ? Oui/Non");
			String response = scan.next();
			if(response.equalsIgnoreCase("Oui")) {
				System.out.println("A bientôt " + login + TEXT_RESET);
				login = null;
				idUser = 0;
				isAdmin = false;
			}				
		}
		else {
			System.out.println("saisissez votre login : ");
			String log = scan.next();
			System.out.println("saisissez votre password : ");
			String pwd = scan.next();
			
			int id = authenticate.existUser(log,pwd);
			System.out.println("ID :" + id);
			if(id > 0)	{
				login = log;
				idUser = id;
				
				userCurrent = business.readUserById(id);
				System.out.println("Utilisateur récupérer via l'id: " + userCurrent);
				
				if(userCurrent.isAdmin() == false) {
					System.out.print(TEXT_BLUE);
				}else {
					System.out.print(TEXT_YELLOW);
					isAdmin = true;	
				}
			}else {
				System.out.println("login ou password incorrect");
				System.out.println("Nouvel utilisateur, pour créer un compte, tapez ok");
				String ok = scan.next();
				if(ok.equalsIgnoreCase("ok")) {
					newUser();
				}
			}
		}
	}
	
	/**
	 * Méthode qui ajoute un nouvel utilisateur en base
	 */
	public static void newUser() {
		System.out.println("saisissez un login :");
		String login = scan.next();			
		int id = authenticate.existUser(login);	
		if(id == 0) { 
			System.out.println("saisissez votre password :");
			String password = scan.next();
			authenticate.addUser(login,password, false);		
			System.out.println("Ne perdez pas ces infos de connexion...");
			stop(2);
			System.out.println("création de l'utilisateur terminé, merci de vous connecter");
		}
		else	System.out.println("Login déjà existant en base, veuillez vous connecter");
	}
	
	public static void stop(int time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static int scanInt() {
		while(!scan.hasNextInt()) {
			System.out.println("saisissez une valeur entière svp");
			scan.next();
		}
		return scan.nextInt();
	}
	
	public static boolean isValidEmail(String email) {
		String regExp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z][a-z]+$";	
		return email.matches(regExp);
	}
}
