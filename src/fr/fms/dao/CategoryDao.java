package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Category;

public class CategoryDao implements Dao<Category> {
	
	/**
	 * Méthode qui crée une catégorie en base sans prendre en compte l'id (généré automatiquement)
	 * @param categorie à ajouter dans la table des catégories
	 */
	@Override
	public boolean create(Category obj) {
		String str = "INSERT INTO T_Categories (CatName, Description) VALUES (?,?);";	
		try (PreparedStatement ps = connection.prepareStatement(str)){
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			if( ps.executeUpdate() == 1)	return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'une catégorie " + e.getMessage());
		} 	
		return false;
	}
	
	/**
	 * Méthode qui renvoi toutes les infos d'une catégorie à partir de son id si elle existe dans la table T_Categories
	 * @param id de la categorie 
	 * @return categorie si trouvé, null sinon
	 */
	@Override
	public Category read(int id) {
		try (Statement statement = connection.createStatement()){
			String str = "SELECT * FROM T_Categories where IdCategory=" + id + ";";									
			ResultSet rs = statement.executeQuery(str);
			if(rs.next()) return new Category(rs.getInt(1) , rs.getString(2) , rs.getString(3));
		} catch (SQLException e) {
			e.printStackTrace();
		} 	
		return null;
	}
	
	/**
	 * Méthode qui met à jour une catégorie si elle existe (à partir de son id) dans la table T_Categories
	 * @param Categorie concerné
	 * @return vrai si trouvé, faux sinon
	 */
	@Override
	public boolean update(Category obj) {
		String str = "UPDATE T_Categories set CatName=?, Description=? where IdCategory=?;";
		try (PreparedStatement ps = connection.prepareStatement(str)){				
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setInt(3, obj.getId());
			if( ps.executeUpdate() == 1)	return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mise à jour d'une catégorie " + e.getMessage());
		} 	
		return false;
	}
	
	/**
	 * Méthode qui supprime une catégorie à partir de son id (s'il existe) dans la table T_Categories
	 * @param categorie concerné
	 * @return vrai si suppression ok faux sinon
	 */
	@Override
	public boolean delete(Category obj) {
		try (Statement statement = connection.createStatement()){
			String str = "DELETE FROM T_Categories where IdCategory=" + obj.getId() + ";";									
			statement.executeUpdate(str);		
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'une catégorie " + e.getMessage());
		} 	
		return false;
	}
	
	/**
	 * Méthode qui renvoi toutes les catégories de la table T_Categories
	 * @return liste de catégories
	 */
	@Override
	public ArrayList<Category> readAll() {
		ArrayList<Category> categories = new ArrayList<Category>();
		String sql = "select * from T_Categories";
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(sql)){
				while(resultSet.next()) {
					categories.add(new Category(resultSet.getInt("idCategory"), resultSet.getString(2), resultSet.getString(3)));
				}
				return categories;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}
}
