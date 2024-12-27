package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import model.ModelException;

public class MySQLDataBaseHandler {
	private Connection connection;
	private PreparedStatement preparedStatement;
	private Statement statement;
	private ResultSet resultSet;
	
	public void statement() throws ModelException {
		try {
			connection = MySQLConnectionFactory.getConnection();
			
			statement = connection.createStatement();
			
		} catch (SQLException sqle) {
			raiseError("Erro ao rodar SQL", sqle);
		} 
		catch (ClassNotFoundException cnfe) {
			raiseError("Classe do Driver JDBC não encontrada.", cnfe);
		}
		catch (Exception e) {
			raiseError("Erro não conhecido ao criar conexão.", e);
		}
	}
	
	public void preparedStatement(String sql) throws ModelException{
		try {
			connection = MySQLConnectionFactory.getConnection();
			preparedStatement = connection.prepareStatement(sql);
		
		} catch (SQLException sqle) {
			raiseError("Erro ao rodar SQL", sqle);
		} 
		catch (ClassNotFoundException cnfe) {
			raiseError("Classe do Driver JDBC não encontrada.", cnfe);
		}
		catch (Exception e) {
			raiseError("Erro não conhecido ao criar conexão.", e);
		}
	}
	
	private void raiseError(String message, Exception e) throws ModelException{
		try{
			connection.close();
		}catch (Exception e2) {}
		
		throw new ModelException(message, e);
	}
	
	public void setString (int index, String value) throws ModelException {
		try {
			preparedStatement.setString(index, value);
		}catch (SQLException sqle) {
			raiseError("Erro ao setar String no prepaareStatement", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao setar String no prepareStatement", e);
		}
	}
	
	public void setInt (int index, int value) throws ModelException {
		try {
			preparedStatement.setInt(index, value);
		}catch (SQLException sqle) {
			raiseError("Erro ao setar Int no pStatement", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao setar Int no pStatement", e);
		}
	}
	
	public void setDate(int index, java.sql.Date value) throws ModelException {
		try {
			preparedStatement.setDate(index, value);
		} catch (SQLException sqle) {
			raiseError("Erro ao setar Data do pStatement", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao setar Data do pStatement", e);
		}
		
	}
	
	public int executeUpdate() throws ModelException {
		try {
			return preparedStatement.executeUpdate();
		}catch (SQLException sqle) {
			raiseError("Erro ao executar DML", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao executar DML", e);
		}
		
		return 0;
	}

	public void executeQuery() throws ModelException {
		try {
			resultSet = preparedStatement.executeQuery();
		}catch (SQLException sqle) {
			raiseError("Erro ao executar DQL", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao executar DQL", e);
		}	
	}
	
	public void executeQuery(String sqlQuery) throws ModelException{
		try {
			resultSet =  statement.executeQuery(sqlQuery);
		}catch (SQLException sqle) {
			raiseError("Erro ao executar DQL", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao executar DQL", e);
		}	
	}
	
	public boolean next() throws ModelException {
		try {
			return resultSet.next();
		}catch (SQLException sqle) {
			raiseError("Erro ao percorrer RS", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao percorrer RS", e);
		}
		
		return false;
	}
	
	public void close() {
		if(preparedStatement!=null) {
			try {
				preparedStatement.close();
			} catch (SQLException e) {}
		}

		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {}
		}
		
		if(resultSet!=null) {
			try {
				resultSet.close();
			} catch (SQLException e) {}
		}
		
		if(statement!=null) {
			try {
				statement.close();
			} catch (SQLException e) {}
		}
	}

	public int getInt(String string) throws ModelException {
		try {
			return resultSet.getInt(string);
		}catch (SQLException sqle) {
			raiseError("Erro ao recuperar Int do RS", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao recuperar Int do RS", e);
		}
		
		return 0;
	}

	public String getString(String string) throws ModelException {
		try {
			return resultSet.getString(string);
		}catch (SQLException sqle) {
			raiseError("Erro ao recuperar String do RS", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao recuperar String do RS", e);
		}
		
		return "";
	}

	public java.sql.Date getDate(String string) throws ModelException {
		try {
			return resultSet.getDate(string);
		} catch (SQLException sqle) {
			raiseError("Erro ao recuperar Data do RS", sqle);
		} 
		catch (Exception e) {
			raiseError("Erro não conhecido ao recuperar Data do RS", e);
		}
		
		return null;
	}

	
	
}
