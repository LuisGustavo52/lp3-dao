package model.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.ModelException;
import model.entities.Post;
import model.entities.User;

public class MySQLPostDAO implements PostDAO{

	@Override
	public boolean save(Post post) throws ModelException {
		String sqlInsert = "INSERT INTO posts VALUES "
				+ " (DEFAULT, ?, ?, ?);	";
		
		
		MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
		dbHandler.preparedStatement(sqlInsert);
		
		dbHandler.setString(1, post.getContent());
		dbHandler.setDate(2, post.getPost_date());
		dbHandler.setInt(3, post.getUser().getId());
		
		int rowsAffected =  dbHandler.executeUpdate();
		
		
		dbHandler.close();
		
		return rowsAffected > 0;		
	}

	@Override
	public List<Post> listAll() throws ModelException {

		String sqlQuery = "SELECT * FROM posts";
		
		MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
		dbHandler.statement();
		
		dbHandler.executeQuery(sqlQuery);

		List<Post> listPosts = new ArrayList<>();
		
		while (dbHandler.next()) {
			int postId = dbHandler.getInt("id");
			String postContent = dbHandler.getString("content");
			Date postDate = dbHandler.getDate("post_date");
			
			MySQLUserDAO userDAO = new MySQLUserDAO();
			User postUser = userDAO.findById(dbHandler.getInt("user_id"));
			
			Post newPost = new Post();
			newPost.setId(postId);
			newPost.setContent(postContent);
			newPost.setPost_date(postDate);
			newPost.setUser(postUser);
			
			listPosts.add(newPost);
		}
		
		dbHandler.close();
		
		return listPosts;

	}

	@Override
	public boolean update(Post post) throws ModelException {
		String sqlUpdate = "UPDATE posts "
				+ " SET content = ?, post_date = ?, user_id = ? "
				+ " WHERE id = ?;";
		
		MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
		dbHandler.preparedStatement(sqlUpdate);
		
		dbHandler.setString(1, post.getContent());
		dbHandler.setDate(2, post.getPost_date());
		dbHandler.setInt(3, post.getUser().getId());
		dbHandler.setInt(4, post.getId());
		
		int rowsAffected =  dbHandler.executeUpdate();
		
		dbHandler.close();
		
		return rowsAffected > 0;
	}

	@Override
	public boolean delete(Post post) throws ModelException {
		String sqlDelete = "DELETE FROM posts WHERE id = ?;";
		
		MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
		dbHandler.preparedStatement(sqlDelete);
		
		dbHandler.setInt(1, post.getId());
		
		int rowsAffected =  dbHandler.executeUpdate();
		dbHandler.close();
		
		return rowsAffected > 0;
	}

	@Override
	public Post findById(int id) throws ModelException {
		String sqlFind = "SELECT * FROM posts WHERE id = ?";
		
		Post post = null;
		
		MySQLDataBaseHandler dbHandler = new MySQLDataBaseHandler();
		dbHandler.preparedStatement(sqlFind);
		dbHandler.setInt(1, id);
		
		dbHandler.executeQuery();

		while (dbHandler.next()) {
			int postId = dbHandler.getInt("id");
			String postContent = dbHandler.getString("content");
			Date postDate = dbHandler.getDate("post_date");
			
			MySQLUserDAO userDAO = new MySQLUserDAO();
			User postUser = userDAO.findById(dbHandler.getInt("user_id"));
			
			post = new Post();
			post.setId(postId);
			post.setContent(postContent);
			post.setPost_date(postDate);
			post.setUser(postUser);
			
			break;
			}
		
		return post;
	}

}
