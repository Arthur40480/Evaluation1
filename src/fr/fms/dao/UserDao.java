package fr.fms.dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import fr.fms.entities.User;

public class UserDao implements Dao<User> {

	/**
	 * Méthode qui crée un utilisateur en base sans prendre en compte l'id (généré automatiquement)
	 * @param User à ajouter dans la table T_Users
	 * @return vrai si l'opération a réussie, faux sinon
	 */
	@Override
	public boolean create(User obj) {
		String str = "INSERT INTO T_Users (Login,Password) VALUES (?,?);";
		try (PreparedStatement ps = connection.prepareStatement(str)){
				ps.setString(1, obj.getLogin());
				ps.setString(2, obj.getPassword());			
				if( ps.executeUpdate() == 1)	return true;				
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'un utilisateur ");
		} 				
		return false;
	}

	/**
	 * Méthode qui renvoi toutes les infos d'un utilisateur à partir de son id s'il existe dans la table T_Users
	 * @param id de l'utilisateur
	 * @return user si trouvé, null sinon
	 */
	@Override
	public User read(int id) {
		try (Statement statement = connection.createStatement()){
				String str = "SELECT * FROM T_Users where IdUser=" + id + ";";									
				ResultSet rs = statement.executeQuery(str);
				if(rs.next()) 
					return new User(rs.getInt(1) , rs.getString(2) , rs.getString(3));
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'un user " + e.getMessage());
		} 	
		return null;
	}

	/**
	 * Méthode qui met à jour un user s'il existe (à partir de son id) dans la table T_Users
	 * @param l'user concerné
	 * @return vrai si mise à jour ok, faux sinon
	 */
	@Override
	public boolean update(User obj) {
		try (Statement statement = connection.createStatement()){
				String str = "UPDATE T_Users set Login='" + obj.getLogin() +"' , " +
							"Password='" 		+ obj.getPassword() +"'" + " where idUser=" + obj.getId() + ";";			
				if(statement.executeUpdate(str) == 1) return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mise à jour d'un utilisateur " + e.getMessage());
		} 	
		return false;
	}

	/**
	 * Méthode qui supprime un user à partir de son id (s'il existe) dans la table T_Users
	 * @param user
	 * @return vrai si suppression ok faux sinon
	 */
	@Override
	public boolean delete(User obj) {
		try (Statement statement = connection.createStatement()){
				String str = "DELETE FROM T_Users where IdUser=" + obj.getId() + ";";									
				if(statement.executeUpdate(str) == 1) return true;		
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'un utilisateur ");
		}
		return false;
	}

	/**
	 * Méthode qui renvoi tous les users de la table T_Users
	 * @return liste d'utilisateurs
	 */
	@Override
	public ArrayList<User> readAll() {
		ArrayList<User> users = new ArrayList<User>();
		String strSql = "SELECT * FROM T_Users";		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsLogin = resultSet.getString(2);
					String rsPassword = resultSet.getString(3);							
					users.add((new User(rsId,rsLogin,rsPassword)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur le renvoi de la liste des utilisateurs");
		}			
		return users;
	}
	
	/**
	 * Méthode renvoi un objet User correspondant au login et password saisi
	 * @param login
	 * @param password
	 * @return User si correspond, null sinon
	 */
	public User findUserByCredentials(String login, String password) {
		String str = "SELECT * FROM T_Users where Login=? and Password=?;";
		try (PreparedStatement ps = connection.prepareStatement(str)){
			ps.setString(1, login);									
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) 
				return new User(rs.getInt(1) , rs.getString(2) , rs.getString(3));
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi d'un utilisateur à partir des credentials ");
		} 	
		return null;
	}
	
	/**
	 * Méthode renvoi un utilisateur à partir de son login
	 * @param login
	 * @return user
	 */
	public User findUserByLogin(String login) {
		String str = "SELECT * FROM T_Users where Login=?;";
		try (PreparedStatement ps = connection.prepareStatement(str)){
			ps.setString(1, login);									
			try (ResultSet rs = ps.executeQuery()){
				if(rs.next()) 
					return new User(rs.getInt(1) , rs.getString(2) , rs.getString(3));
				}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi d'un utilisateur à partir de son login ");
		} 	
		return null;
	}
}
