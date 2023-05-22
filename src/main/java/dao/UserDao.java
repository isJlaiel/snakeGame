package dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import beans.Historique;
import beans.User;

public interface UserDao {
	
	void createUser(User user)  throws DaoException;
	boolean usernameExists(String username) throws DaoException;
	boolean emailExists(String email)  throws DaoException;
	public boolean checkUser(String username, String password)  throws DaoException;
	public int getID(String username) throws DaoException;
	void updateProfile(String oldUsername,String username, String email, String firstname, String lastname, String bio)
			throws DaoException;
	void updateImage(String username,InputStream fileContent) throws DaoException;
	byte[] getImage(String username) throws DaoException;
	User getUsersInfo(String oldUsername) throws DaoException;
	public void deleteUser(String username) throws DaoException ;
	public void changePassword(String username, String newPassword)throws DaoException;
	void updateScore(String username,int score) throws DaoException;
	ArrayList<User> getAllUsers()throws DaoException;
	void updateHistorique(String name, int scoreTotal, int nbPartie)throws DaoException;
	public List<Historique> getHistorique(String userName) throws DaoException ;
}
