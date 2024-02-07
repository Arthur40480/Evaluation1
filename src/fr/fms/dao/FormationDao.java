package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.Formation;

public class FormationDao implements Dao<Formation>{

	/**
	 * Méthode qui crée un article en base sans prendre en compte l'id (généré automatiquement)
	 * @param Formation à ajouter dans la table des formations
	 */
	@Override
	public boolean create(Formation obj) {
		String str = "INSERT INTO T_Formations (Name, Description, Duration, Remote, UnitaryPrice, IdCategory) VALUES (?,?,?,?,?,?);";	
		try (PreparedStatement ps = connection.prepareStatement(str)){
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setInt(3, obj.getDuration());
			ps.setBoolean(4, obj.isRemote());
			ps.setDouble(5, obj.getUnitaryPrice());
			ps.setInt(6, obj.getCategory());
			if( ps.executeUpdate() == 1)	return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la création d'une formation " + e.getMessage());
		} 	
		return false;
	}
	
	/**
	 * Méthode qui renvoi toutes les infos d'une formation à partir de son id s'il existe dans la table T_Formations
	 * @param id de la formation 
	 * @return formation si trouvé, null sinon
	 */
	@Override
	public Formation read(int id) {
		try (Statement statement = connection.createStatement()){
			String str = "SELECT * FROM T_Formations where IdFormation=" + id + ";";									
			ResultSet rs = statement.executeQuery(str);
			if(rs.next()) return new Formation(rs.getInt(1), rs.getString(2), rs.getString(3) , rs.getInt(4) , rs.getBoolean(5), rs.getDouble(6), rs.getInt(7));
		} catch (SQLException e) {
			logger.severe("pb sql sur la lecture d'une formation " + e.getMessage());
		} 	
		return null;
	}
	
	/**
	 * Méthode qui met à jour une formation si elle existe (à partir de son id) dans la table T_Formations
	 * @param formation concerné
	 * @return vrai si trouvé, faux sinon
	 */
	@Override
	public boolean update(Formation obj) {
		String str = "UPDATE T_Formations set Name=?, Description=?, Duration=?, Remote=?, UnitaryPrice=?, IdCategory=? where IdFormation=?;";
		try (PreparedStatement ps = connection.prepareStatement(str)){				
			ps.setString(1, obj.getName());
			ps.setString(2, obj.getDescription());
			ps.setInt(3, obj.getDuration());
			ps.setBoolean(4, obj.isRemote());
			ps.setDouble(5, obj.getUnitaryPrice());
			ps.setInt(6, obj.getCategory());
			ps.setInt(7, obj.getId());
			if( ps.executeUpdate() == 1)	return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la mise à jour d'une formation " + e.getMessage());
		} 	
		return false;
	}
	
	/**
	 * Méthode qui supprime une formation à partir de son id (s'il existe) dans la table T_Formations
	 * @param formation concerné
	 * @return vrai si suppression ok faux sinon
	 */
	@Override
	public boolean delete(Formation obj) {
		try (Statement statement = connection.createStatement()){
			String str = "DELETE FROM T_Formations where IdFormation=" + obj.getId() + ";";									
			statement.executeUpdate(str);		
			return true;
		} catch (SQLException e) {
			logger.severe("pb sql sur la suppression d'une formation " + e.getMessage());
		} 	
		return false;
	}
	
	/**
	 * Méthode qui renvoi toutes les formations de la table T_Formations
	 * @return liste de formations
	 */
	@Override
	public ArrayList<Formation> readAll() {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations";		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					boolean rsRemote = resultSet.getBoolean(5);	
					double rsUnitaryPrice = resultSet.getDouble(6);
					int rsIdCategory = resultSet.getInt(7);
					formations.add((new Formation(rsId, rsName, rsDescription, rsDuration, rsRemote, rsUnitaryPrice, rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi de toutes les formations " + e.getMessage());
		}	
		return formations;
	}
	
	/**
	 * Méthode qui renvoi toute les formations d'une catégorie
	 * @param id de la catégorie
	 * @return liste de formation
	 */
	public ArrayList<Formation> readAllByCat(int id) {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations where idCategory=" + id;		
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					boolean rsRemote = resultSet.getBoolean(5);	
					double rsUnitaryPrice = resultSet.getDouble(6);
					int rsIdCategory = resultSet.getInt(7);
					formations.add((new Formation(rsId, rsName, rsDescription, rsDuration, rsRemote, rsUnitaryPrice, rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi de toutes les formations d'une catégorie " + e.getMessage());
		}			
		return formations;
	}
	
	/**
	 * Méthode qui renvoi toute les formations qui contienne un mot-clé
	 * @param keyword mot-clé
	 * @return liste de formation
	 */
	public ArrayList<Formation> readAllByKeyword(String keyword) {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations WHERE Name LIKE '%" + keyword + "%';";
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					boolean rsRemote = resultSet.getBoolean(5);	
					double rsUnitaryPrice = resultSet.getDouble(6);
					int rsIdCategory = resultSet.getInt(7);
					formations.add((new Formation(rsId, rsName, rsDescription, rsDuration, rsRemote, rsUnitaryPrice, rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi de toutes les formations d'une catégorie " + e.getMessage());
		}			
		return formations;
		
	}
	
	public ArrayList<Formation> readAllRemoteFormation() {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations WHERE Remote=true;";
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					boolean rsRemote = resultSet.getBoolean(5);	
					double rsUnitaryPrice = resultSet.getDouble(6);
					int rsIdCategory = resultSet.getInt(7);
					formations.add((new Formation(rsId, rsName, rsDescription, rsDuration, rsRemote, rsUnitaryPrice, rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi de toutes les formations d'une catégorie " + e.getMessage());
		}			
		return formations;
	}
	
	public ArrayList<Formation> readAllOnsiteFormation() {
		ArrayList<Formation> formations = new ArrayList<Formation>();
		String strSql = "SELECT * FROM T_Formations WHERE Remote=false;";
		try(Statement statement = connection.createStatement()){
			try(ResultSet resultSet = statement.executeQuery(strSql)){ 			
				while(resultSet.next()) {
					int rsId = resultSet.getInt(1);	
					String rsName = resultSet.getString(2);
					String rsDescription = resultSet.getString(3);
					int rsDuration = resultSet.getInt(4);
					boolean rsRemote = resultSet.getBoolean(5);	
					double rsUnitaryPrice = resultSet.getDouble(6);
					int rsIdCategory = resultSet.getInt(7);
					formations.add((new Formation(rsId, rsName, rsDescription, rsDuration, rsRemote, rsUnitaryPrice, rsIdCategory)));						
				}	
			}
		} catch (SQLException e) {
			logger.severe("pb sql sur renvoi de toutes les formations d'une catégorie " + e.getMessage());
		}			
		return formations;
	}

}

