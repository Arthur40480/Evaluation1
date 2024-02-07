package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import fr.fms.entities.Formation;
import fr.fms.entities.Order;

public class OrderDao implements Dao<Order> {
	
	/**
	 * Méthode qui créer une commande
	 * @param commande concernée
	 * @return true si ok, sinon false
	 */
	@Override
	public boolean create(Order obj) {
		String str = "INSERT INTO T_Orders (Amount , IdCustomer) VALUES (?,?);";	
		try (PreparedStatement ps = connection.prepareStatement(str,Statement.RETURN_GENERATED_KEYS)){	
			ps.setDouble(1, obj.getAmount());
			ps.setInt(2, obj.getIdCustomer());
			ps.executeUpdate();
			try(ResultSet generatedKeySet = ps.getGeneratedKeys()){
				if(generatedKeySet.next()) {
					obj.setIdOrder(generatedKeySet.getInt(1));
					return true;
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Order read(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Order obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Order> readAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Méthode qui permet de récupérer les commandes associès à un idCustomer
	 * @param id du customer
	 * @return commandes
	 */
	public ArrayList<Order> readAllByCustomerId(int id) {
		ArrayList<Order> commandes = new ArrayList<Order>();
		String str = "SELECT * FROM T_Orders WHERE IdCustomer=?";
		try(PreparedStatement ps = connection.prepareStatement(str)) {
			ps.setInt(1, id);
			try(ResultSet resultSet = ps.executeQuery()) {
				while(resultSet.next()) {
					int idOrder = resultSet.getInt(1);
					float amount = resultSet.getFloat(2);
					Date date = resultSet.getDate(3);
					int idCustomer = resultSet.getInt(4);
					commandes.add(new Order(idOrder, amount, date, idCustomer));
				}				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return commandes;
	}
}

