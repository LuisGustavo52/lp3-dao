package view;

import java.sql.Date;

import model.ModelException;
import model.dao.DAOFactory;
import model.dao.PostDAO;
import model.dao.UserDAO;
import model.entities.Post;
import model.entities.User;

public class Main {
	public static void main(String[] args) {
		User emerson = new User(7);
		emerson.setName("Luis mestre das sombras");
		emerson.setEmail("luis@gmail.com");
		emerson.setSex('M');
		
		Post post1 = new Post(9);
		post1.setContent("Quero muito jogar LOL");
		post1.setPost_date(Date.valueOf("2024-12-26"));
		post1.setUser(emerson);
		
		UserDAO userDao = (UserDAO) DAOFactory.getDAO(UserDAO.class);
		PostDAO postDao = (PostDAO) DAOFactory.getDAO(PostDAO.class);
		
		User user = null;
		Post post = null;
		try {
			user = userDao.findById(7);
			post = postDao.findById(9);
				
		} catch (ModelException me) {
			System.out.println(me.getMessage());
			System.out.println(me.getCause().getMessage());
		}
		
		
		//*for (User user : dao.listAll()) {
			System.out.println("Nome: "+user.getName());
			System.out.println("Email:"+user.getEmail());
			System.out.println("Sexo: "+user.getSex());
			System.out.println("Id: "+user.getId());
			System.out.println();
		//}
		
			System.out.println("Conteudo: "+post.getContent());
			System.out.println("Data:"+post.getPost_date().toString());
			System.out.println("Usuario: "+post.getUser().getName());
			System.out.println("Id: "+post.getId());
			System.out.println();
				}
}
