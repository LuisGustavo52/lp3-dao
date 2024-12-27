package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ModelException;
import model.entities.User;

class MySQLUserDAO implements UserDAO{
	
	
	@Override
	public boolean save(User user) throws ModelException{
			String sqlInsert = "INSERT INTO users VALUES "
					+ " (DEFAULT, ?, ?, ?);";
			
			
			MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
			dbHandler.preparedStatement(sqlInsert);
			
			dbHandler.setString(1, user.getName());
			dbHandler.setString(2, String.valueOf(user.getSex()));
			dbHandler.setString(3, user.getEmail());
			
			int rowsAffected =  dbHandler.executeUpdate();
			
			
			dbHandler.close();
			
			return rowsAffected > 0;		
			}
	@Override
	public List<User> listAll() throws ModelException{

			String sqlQuery = "SELECT * FROM users";
			
			MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
			dbHandler.statement();
			
			dbHandler.executeQuery(sqlQuery);

			List<User> listUsers = new ArrayList<>();
			
			while (dbHandler.next()) {
				int userId = dbHandler.getInt("id");
				String userName = dbHandler.getString("nome");
				String userSex = dbHandler.getString("sexo");
				String userEmail = dbHandler.getString("email");
				
				User newUser = new User();
				newUser.setId(userId);
				newUser.setName(userName);
				newUser.setSex(userSex.charAt(0));
				newUser.setEmail(userEmail);
				
				listUsers.add(newUser);
			}
			
			dbHandler.close();
			
			return listUsers;
	}

	@Override
	public boolean update(User user) throws ModelException{
			String sqlUpdate = "UPDATE users "
					+ " SET nome = ?, sexo = ?, email = ? "
					+ " WHERE id = ?;";
			
			MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
			dbHandler.preparedStatement(sqlUpdate);
			
			dbHandler.setString(1, user.getName());
			dbHandler.setString(2, String.valueOf(user.getSex()));
			dbHandler.setString(3, user.getEmail());
			dbHandler.setInt(4, user.getId());
			
			int rowsAffected =  dbHandler.executeUpdate();
			
			
			dbHandler.close();
			
			return rowsAffected > 0;
	}

	@Override
	public boolean delete(User user) throws ModelException{
		String sqlDelete = "DELETE FROM users WHERE id = ?;";
		
		MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
		dbHandler.preparedStatement(sqlDelete);
		
		dbHandler.setInt(1, user.getId());
		
		int rowsAffected =  dbHandler.executeUpdate();
		dbHandler.close();
		
		return rowsAffected > 0;
	}

	@Override
	public User findById(int id) throws ModelException{
		String sqlFind = "SELECT * FROM users WHERE id = ?";
		
		User user = null;
		
		MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
		dbHandler.preparedStatement(sqlFind);
		dbHandler.setInt(1, id);
		
		dbHandler.executeQuery();

		while (dbHandler.next()) {
			int userId = dbHandler.getInt("id");
			String userName = dbHandler.getString("nome");
			String userSex = dbHandler.getString("sexo");
			String userEmail = dbHandler.getString("email");
			
			user = new User(id);
			user.setName(userName);
			user.setSex(userSex.charAt(0));
			user.setEmail(userEmail);
			
			break;
			}
		
		return user;
	}

}
