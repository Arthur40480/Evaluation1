import java.util.ArrayList;

import fr.fms.dao.CategoryDao;
import fr.fms.dao.FormationDao;
import fr.fms.entities.Category;
import fr.fms.entities.Formation;

public class TestApplication {
	
	public static void main(String[] args) {
		CategoryDao dao = new CategoryDao();
		for(Category formation : dao.readAll()) {
			System.out.println(formation);
		}
	}
}
