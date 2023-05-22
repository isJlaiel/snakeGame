package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import beans.Historique;
import beans.User;

public class UserDaoImpl implements UserDao {
	private DaoFactory daoFactory;

	UserDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public static void closeSource(ResultSet resultSet, PreparedStatement statement, Connection connexion) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture du Statement : " + e.getMessage());
			}
		}
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("Échec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	@Override
	public void createUser(User user) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion
					.prepareStatement("INSERT INTO users (username, email , password , score) VALUES(?, ?, ?,?);");
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setInt(4, 0);

			int statut;
			if (usernameExists(user.getUsername()) || emailExists(user.getEmail())) {
				statut = 0;
			} else 
				statut = preparedStatement.executeUpdate();
			/* Analyse du statut retourné par la requête d'insertion */

			if (statut == 0) {
				throw new DaoException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new DaoException("An account with this email already exists");
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			closeSource(null, preparedStatement, connexion);
		}
	}

	@Override
	public boolean usernameExists(String username) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String usernameDB;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("Select username from users where username = ?");
			preparedStatement.setString(1, username);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				usernameDB = rs.getString("username");
				if (usernameDB.equals(username)) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			closeSource(rs, preparedStatement, connexion);
		}

		return false;
	}

	@Override
	public boolean emailExists(String email) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String emailDB;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("Select email from users where  email = ?");
			preparedStatement.setString(1, email);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				emailDB = rs.getString("email");

				if (emailDB.equals(email)) {
					return true;
				}
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			closeSource(rs, preparedStatement, connexion);
		}

		return false;
	}

	@Override
	public boolean checkUser(String username, String password) {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			connexion = daoFactory.getConnection();
			System.out.println("is here checkuser " + username);
			preparedStatement = connexion.prepareStatement("Select username, password from users where username = ?");
			preparedStatement.setString(1, username);
			// preparedStatement.setString(2, password);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				String encryptedPassword = rs.getString("password");
				ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
				passwordEncryptor.setAlgorithm("SHA-256");
				passwordEncryptor.encryptPassword("secret");
				boolean result = passwordEncryptor.checkPassword(password, encryptedPassword);
				if (result)
					return true;
			}
		} catch (SQLException e) {
			System.out.print("----------------------------------------------------");
			System.out.print(e);
			throw new DaoException(e);
		} finally {
			closeSource(rs, preparedStatement, connexion);
		}
		return false;
	}

	
	@Override
	public User getUsersInfo(String oldUsername) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int id=0;
		User user = new User();


		try {
			id = getID(oldUsername);
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("Select * from users where id=? ");
			preparedStatement.setInt(1, id);

			rs = preparedStatement.executeQuery();
			// Récupération des données
			while (rs.next()) {
				String username = rs.getString(2);
				String email = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String bio = rs.getString(6);
				
				user.setUsername(username);
				user.setEmail(email);
				user.setFirstName(firstname);
				user.setLastName(lastname);
				user.setBio(bio);


			}

			// SET PASSWORD FOR 'root'@'localhost' = PASSWORD('rym2002');

		} catch (SQLException e) {
			System.out.print("--------erreur-----");
			System.out.print(e);
			throw new DaoException(e);
		} finally {
			closeSource(rs, preparedStatement, connexion);
		}
		return user;

	}

	@Override
	public void updateProfile(String oldUsername, String username, String email, String firstname, String lastname, String bio) throws DaoException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int id=0;
			
			try {
				
				id = getID(oldUsername);
				connexion = daoFactory.getConnection();
				preparedStatement = connexion
						.prepareStatement("update users set username=?, email=?, firstname=?, lastname=?, bio=? where id=?");
				preparedStatement.setString(1, username);
				preparedStatement.setString(2, email);
				preparedStatement.setString(3, firstname);
				preparedStatement.setString(4, lastname);
				preparedStatement.setString(5, bio);
				preparedStatement.setInt(6, id);
				
				preparedStatement.executeUpdate();

					
			} catch (SQLException e) {
				System.out.print("--------erreur-----");
				System.out.print(e);
				throw new DaoException(e);
			} finally {
				closeSource(rs, preparedStatement, connexion);
			}
		}

	@Override
	public int getID(String username) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int id = 0;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement("Select id from users where username=? ");
			preparedStatement.setString(1, username);

			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				id = rs.getInt(1);
				
			}


		} catch (SQLException e) {
			System.out.print("--------erreur-----");
			System.out.print(e);
			throw new DaoException(e);
		} finally {
			closeSource(rs, preparedStatement, connexion);
		}
		return id;

	}

	@Override
	public void updateImage(String username, InputStream fileContent) throws DaoException {

		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int id=0;
			
			try {
				
				id = getID(username);
				connexion = daoFactory.getConnection();
				preparedStatement = connexion
						.prepareStatement("update users set image = ? where id=?");
				preparedStatement.setBinaryStream(1,fileContent);
				preparedStatement.setInt(2, id);
				preparedStatement.executeUpdate();
				
		
			} catch (SQLException e) {
				System.out.print("--------erreur-----");
				System.out.print(e);
				throw new DaoException(e);
			} finally {
				closeSource(rs, preparedStatement, connexion);
			}
		}

	@Override
	public byte[] getImage(String username) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int id=0;
		byte[] image = null;

	    try {
	    	id = getID(username);
			connexion = daoFactory.getConnection();
			preparedStatement = connexion
					.prepareStatement("Select image from users where id=?");
			preparedStatement.setInt(1,id);
			rs=preparedStatement.executeQuery();
			
	      if (rs.next()) {
	        image = rs.getBytes("image");
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			closeSource(rs, preparedStatement, connexion);
		}
		return image;
	}
	@Override
	public void deleteUser(String username) throws DaoException {
		try 
		{
			    Connection connexion = daoFactory.getConnection();
				 PreparedStatement preparedStatement = connexion.prepareStatement(" DELETE FROM users WHERE username = ?;");
				preparedStatement.setString(1, username);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.out.print("Error deleting user");
				throw new DaoException("Error deleting user", e);
			} finally {
		//closeSource(null, preparedStatement, connexion);
		}
		}

	@Override
	public void changePassword(String username, String newPassword) {
		Connection connection = null;
		  PreparedStatement preparedStatement = null;

		  try {
		    connection = daoFactory.getConnection();
		    preparedStatement = connection.prepareStatement("UPDATE users SET password=? WHERE username=?");
		    preparedStatement.setString(1, newPassword);
		    preparedStatement.setString(2, username);
		    preparedStatement.executeUpdate();
		  } catch (SQLException e) {
			  System.out.print(e);
				throw new DaoException(e);
				}
		  }
	public void updateHistorique(String username, int score) throws DaoException {
		Connection connection = null;
		  PreparedStatement preparedStatement = null;

		  try {
		    connection = daoFactory.getConnection();
		    preparedStatement = connection.prepareStatement("UPDATE users SET score=?,nbPartie = nbPartie + 1 WHERE username=?");
		    preparedStatement.setInt(1, score);
		    preparedStatement.setString(2, username);
		    preparedStatement.executeUpdate();
		  } catch (SQLException e) {
			  System.out.print(e);
				throw new DaoException(e);
				}
	}
	@Override
	public void updateScore(String username, int score) throws DaoException {
		Connection connection = null;
		  PreparedStatement preparedStatement = null;
		    ResultSet res = null;
		  try {
		    connection = daoFactory.getConnection();
		    preparedStatement = connection.prepareStatement("SELECT score FROM users WHERE username=?");
	        preparedStatement.setString(1, username);
	        res = preparedStatement.executeQuery();         
	        int oldScore = 0;
	        if (res.next()) {
	            oldScore = res.getInt("score");
	        }
	      
		    preparedStatement = connection.prepareStatement("UPDATE users SET score=?,nbPartie = nbPartie + 1 WHERE username=?");
		    preparedStatement.setInt(1, (score+oldScore));
		    preparedStatement.setString(2, username);
		    preparedStatement.executeUpdate();
		  } catch (SQLException e) {
			  System.out.print(e);
				throw new DaoException(e);
				}finally {
					closeSource(res, preparedStatement, connection);
				}
	}
    public ArrayList<User> getAllUsers() throws DaoException {
    	ArrayList<User> userList = new ArrayList<>();
		Connection connection = null;
		  PreparedStatement preparedStatement = null;

		  try {
		    connection = daoFactory.getConnection();
		    preparedStatement = connection.prepareStatement(" SELECT username, image, score, nbPartie FROM users");
		    ResultSet rs = preparedStatement.executeQuery();
	        
	        while (rs.next()) {
	            String username = rs.getString("username");
	            String image = rs.getString("image");
	            int score = rs.getInt("score");
	            int nbPartie = rs.getInt("nbPartie");

	            User user = new User();
	            user.setUsername(username);
	            user.setScore(score);
	            user.setImage(image);
	            user.setNbPartie(nbPartie);
	            userList.add(user);
	        }
		  } catch (SQLException e) {
			  System.out.print(e);
				throw new DaoException(e);
				}finally {
					closeSource(null, preparedStatement, connection);
				}
		  return userList;
	
    }

	@Override
	public void updateHistorique(String name, int scoreTotal, int nbPartie) throws DaoException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = connection.prepareStatement("INSERT INTO historique (nom, scoreTotal, nbPartie, date) VALUES (?, ?, ?, CURDATE())");
	        preparedStatement.setString(1, name);
	        preparedStatement.setInt(2, scoreTotal);
	        preparedStatement.setInt(3, nbPartie);
	        preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	        System.out.print(e);
	        throw new DaoException(e);
	    }
	}
	public List<Historique> getHistorique(String userName) throws DaoException {
	    List<Historique> histList = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet res = null;

	    try {
	        connection = daoFactory.getConnection();
	        preparedStatement = connection.prepareStatement("SELECT * FROM historique WHERE nom = ?");
	        preparedStatement.setString(1, userName);
	        res = preparedStatement.executeQuery();

	        while (res.next()) {
	            Historique historique = new Historique();
	            historique.setNom(res.getString("nom"));
	            historique.setScoreTotal(res.getInt("scoreTotal"));
	            historique.setNbPartie(res.getInt("nbPartie"));
	            historique.setDate(res.getDate("date"));
	            histList.add(historique);
	        }
	    } catch (SQLException e) {
	        throw new DaoException(e);
	    } finally {
	    	closeSource( res,preparedStatement,connection);
	    }

	    return histList;
	}


}
