import java.util.ArrayList;

import fr.fms.dao.CategoryDao;
import fr.fms.dao.FormationDao;
import fr.fms.dao.UserDao;
import fr.fms.entities.Category;
import fr.fms.entities.Formation;
import fr.fms.entities.User;

public class TestApplication {
	
	public static void main(String[] args) {
		UserDao dao = new UserDao();
		FormationDao formationDao = new FormationDao();
		
		// Test de la méthode readAllByKeyword():
//		String keyword = "Developpement";
//		String keyword2 = "Cuisine";
//		
//		System.out.println(formationDao.readAllByKeyword(keyword));
//		System.out.println(formationDao.readAllByKeyword(keyword2));
				
		// Test de la méthode readAllRemoteFormation():
		System.out.println(formationDao.readAllRemoteFormation());
		
		// Test de la méthode readAllOnsiteFormation():
		System.out.println(formationDao.readAllOnsiteFormation());
	}
}
