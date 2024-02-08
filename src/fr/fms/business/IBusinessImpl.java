package fr.fms.business;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import fr.fms.dao.FormationDao;
import fr.fms.dao.UserDao;
import fr.fms.dao.CategoryDao;
import fr.fms.dao.OrderDao;
import fr.fms.dao.CustomerDao;
import fr.fms.dao.Dao;
import fr.fms.dao.DaoFactory;
import fr.fms.entities.Formation;
import fr.fms.entities.Category;
import fr.fms.entities.Customer;
import fr.fms.entities.Order;
import fr.fms.entities.OrderItem;
import fr.fms.entities.User;

public class IBusinessImpl implements IBusiness {	
	private HashMap<Integer,Formation> cart;
	private Dao<Formation> formationDao = DaoFactory.getFormationDao();
	private Dao<User> userDao = DaoFactory.getUserDao();
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
	public boolean createFormation(Formation formation) {
		return ((FormationDao) formationDao).create(formation);
	}
	
	@Override
	public boolean updateFormation(Formation formation) {
		return ((FormationDao) formationDao).update(formation);
	}
	
	@Override
	public boolean deleteFormation(Formation formation) {
		return ((FormationDao) formationDao).delete(formation);
	}
	
	@Override
	public ArrayList<Formation> readFormations() {
		return formationDao.readAll();
	}
	
	@Override
	public Formation readOneFormation(int id) {
		return formationDao.read(id);
	}

	@Override
	public ArrayList<Formation> readFormationsByCatId(int id) {
		return ((FormationDao) formationDao).readAllByCat(id);
	}
	
	@Override
	public ArrayList<Formation> readFormationsByKeyword(String keyword) {
		return ((FormationDao) formationDao).readAllByKeyword(keyword);
	}
	
	@Override
	public ArrayList<Formation> readRemoteFormations() {
		return((FormationDao) formationDao).readAllRemoteFormation();
	}
	
	@Override
	public ArrayList<Formation> readOnsiteFormations() {
		return((FormationDao) formationDao).readAllOnsiteFormation();
	}
	
	@Override 
	public User readUserById(int id) {
		return ((UserDao) userDao).read(id);
	}
	
	@Override
	public Customer readCustomerById(int id) {
		return ((CustomerDao) customerDao).read(id);
	}
	
	@Override
	public boolean createCategory(Category category) {
		return ((CategoryDao) categoryDao).create(category);
	}
	
	@Override
	public Category readCategory(int id) {
		return ((CategoryDao) categoryDao).read(id);
	}
	
	@Override
	public boolean updateCategory(Category category) {
		return ((CategoryDao) categoryDao).update(category);
	}
	
	@Override
	public boolean deleteCategory(Category category) {
		return ((CategoryDao) categoryDao).delete(category);
	}
	
	@Override
	public ArrayList<Category> readCategories() {
		return categoryDao.readAll();
	}
	
	@Override
	public ArrayList<Order> readAllOrderByCustomerId(int id){
		return((OrderDao) orderDao).readAllByCustomerId(id);
	}

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
