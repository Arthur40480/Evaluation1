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
		for(User formation : dao.readAll()) {
			System.out.println(formation);
		}
	}
}
