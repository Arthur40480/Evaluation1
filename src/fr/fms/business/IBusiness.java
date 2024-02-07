package fr.fms.business;
import java.util.ArrayList;
import fr.fms.entities.Formation;
import fr.fms.entities.User;
import fr.fms.entities.Category;

public interface IBusiness {	
	/**
	 * méthode qui ajoute une formation au panier
	 * @param formation à ajouter
	 */
	public void addToCart(Formation formation);		
	
	/**
	 * méthode qui retire une formation au panier si elle est dedans
	 * @param id de la formation à retirer
	 */
	public void rmFromCart(int id);		
	
	/**
	 * méthode qui renvoi sous la forme d'une liste tous les éléments du panier (gestion en mémoire)
	 * @return Liste de formation du panier
	 */
	public ArrayList<Formation> getCart();	
	
	/**
	 * méthode qui réalise la commande en base avec l'idUser + total de la commande en cours + date du jour + contenu du panier :
	 * - la méthode va céer une commande en base -> idOrder + montant + date + idUser
	 * - puis va ajouter autant de commandes minifiées associées : orderItem -> idOrderItem + idFormation + Quantity + Price + idOrder
	 * @param idUser est l'identifiant du client qui à passé commande
	 * @return 1 si tout est ok 0 si pb 
	 */
	public int order(int idUser);		
	
	/**
	 * méthode qui permet de créer une formation en base
	 * @param formation concernée
	 * @return true si ok, sinon false
	 */
	public boolean createFormation(Formation formation);
	
	/**
	 * Méthode qui permet de mettre à jour une formation
	 * @param formation concernée
	 * @return true si ok, sinon false
	 */
	public boolean updateFormation(Formation formation);
	
	/**
	 * Méthode qui permet de supprimer une formation
	 * @param formation concernée
	 * @return true si ok, sinon false
	 */
	public boolean deleteFormation(Formation formation);
	
	/**
	 * méthode qui renvoi toutes les formations de la table t_formations en bdd
	 * @return Liste de formations en base
	 */
	public ArrayList<Formation> readArticles();	
	
	/**
	 * méthode renvoie la formation correspondante à l'id
	 * @param id de la formation à renvoyer
	 * @return formation correspondante si trouvé, null sinon
	 */
	public Formation readOneArticle(int id);	
	
	/**
	 * méthode qui renvoi toutes les catégories de la table t_catégories en bdd
	 * @return Liste de catégories en base
	 */
	public ArrayList<Category> readCategories();
	
	/**
	 * méthode qui renvoi toutes les formations d'une catégorie
	 * @param id de la catégorie
	 * @return Liste de formation
	 */
	public ArrayList<Formation> readArticlesByCatId(int idCat);
	
	/**
	 * méthode qui renvoi toutes les formations qui contiennent un mot-clé
	 * @param string est le mot-clé
	 * @return Liste de formation
	 */
	public ArrayList<Formation> readFormationsByKeyword(String keyword);
	
	/**
	 * méthode qui renvoi toutes les formations qui sont en distanciel
	 * @return Liste de formation
	 */
	public ArrayList<Formation> readRemoteFormations();
	
	/**
	 * méthode qui renvoi toutes les formations qui sont en présentiel
	 * @return Liste de formation
	 */
	public ArrayList<Formation> readOnsiteFormations();
	
	/**
	 * méthode qui renvoi le User correspondant à l'id
	 * @return user correspondant si trouvé, null sinon
	 */
	public User readUserById(int id);
	
	/**
	 * méthode qui permet de créer une catégorie en base
	 * @param category concernée
	 * @return true si ok, sinon false
	 */
	public boolean createCategory(Category category);
	
	/**
	 * méthode renvoie la catégorie correspondante à l'id
	 * @param category concernée
	 * @return true si ok, sinon false
	 */
	public Category readCategory(int id);
	
	/**
	 * Méthode qui permet de mettre à jour une catégorie
	 * @param category concernée
	 * @return true si ok, sinon false
	 */
	public boolean updateCategory(Category category);
	
	/**
	 * Méthode qui permet de supprimer une catégorie
	 * @param category concernée
	 * @return true si ok, sinon false
	 */
	public boolean deleteCategory(Category category);
}
