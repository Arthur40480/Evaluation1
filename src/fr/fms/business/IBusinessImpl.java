package fr.fms.business;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import fr.fms.dao.FormationDao;
import fr.fms.dao.Dao;
import fr.fms.dao.DaoFactory;
import fr.fms.entities.Formation;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;
import fr.fms.entities.Order;
import fr.fms.entities.OrderItem;

public class IBusinessImpl implements IBusiness {	
	private HashMap<Integer,Formation> cart;
	//private Dao<Article> articleDao = DaoFactory.getArticleDao();
	private FormationDao formationDao = new FormationDao();
	//private Dao<User> userDao = DaoFactory.getUserDao();
	private Dao<Category> categoryDao = DaoFactory.getCategoryDao();
	private Dao<Order> orderDao = DaoFactory.getOrderDao();
	private Dao<OrderItem> orderItemDao = DaoFactory.getOrderItemDao();
	private Dao<Customer> customerDao = DaoFactory.getCustomerDao();
	
	public IBusinessImpl() {
		this.cart = new HashMap<Integer, Formation>();
	}

	@Override
	public void addToCart(Formation formation) {
		Formation art = cart.get(formation.getId());
		if(art != null) {
			art.setQuantity(art.getQuantity() + 1);
		}
		else cart.put(formation.getId(), formation);
	}

	@Override
	public void rmFromCart(int id) {
		Formation formation = cart.get(id);
		if(formation != null) {
			if(formation.getQuantity() == 1)	cart.remove(id);
			else formation.setQuantity(formation.getQuantity() - 1);
		}				
	}

	@Override
	public ArrayList<Formation> getCart() {
		return new ArrayList<Formation> (cart.values());
	}

	@Override
	public int order(int idCustomer) {	
		if(customerDao.read(idCustomer) != null) {
			double total = getTotal(); 
			Order order = new Order(total, new Date(), idCustomer);
			if(orderDao.create(order)) {	
				for(Formation formation : cart.values()) {	
					orderItemDao.create(new OrderItem(0, formation.getId(), formation.getQuantity(), formation.getUnitaryPrice(), order.getIdOrder()));
				}
				return order.getIdOrder();
			}
		}
		return 0;
	}

	@Override
	public ArrayList<Formation> readArticles() {
		return formationDao.readAll();
	}
	
	@Override
	public ArrayList<Category> readCategories() {
		return categoryDao.readAll();
	}

	@Override
	public Formation readOneArticle(int id) {
		return formationDao.read(id);
	}

	@Override
	public ArrayList<Formation> readArticlesByCatId(int id) {
		return ((FormationDao) formationDao).readAllByCat(id);
	}
	
	@Override
	public ArrayList<Formation> readFormationsByKeyword(String keyword) {
		return ((FormationDao) formationDao).readAllByKeyword(keyword);
	}

	/**
	 * renvoi le total de la commande en cours
	 * @return total
	 */
	public double getTotal() {
		double [] total = {0};
		cart.values().forEach((a) -> total[0] += a.getUnitaryPrice() * a.getQuantity()); 	
		return total[0];
	}

	public boolean isCartEmpty() {
		return cart.isEmpty();
	}
	
	public void clearCart() {
		cart.clear();		
	}

	public Category readOneCategory(int id) {
		return categoryDao.read(id);
	}
}