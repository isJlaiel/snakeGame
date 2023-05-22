package businessLogic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import beans.User;
import dao.UserDao;

public class Verification {

    private Map<String, String> erreurs = new HashMap<String, String>();
    
    private UserDao userDao;
    
    public Verification( UserDao userDao) {
    	this.userDao=userDao;
    }


	public User validForm(HttpServletRequest request) {
		
	    String username = request.getParameter("username").trim();
		String email= request.getParameter("email").trim();
		String password = request.getParameter("pswd").trim();
		String password_repeat= request.getParameter("pswd-repeat").trim();
		
	    User user = new User();
	    user.setEmail( email );
	    user.setPassword(password);
	    user.setUsername(username);
	     
	    validationName(username);
	    validationEmail(email);
	    String pswdChiffre=   ValidatePassword(password,password_repeat);
	    user.setPassword(pswdChiffre);
			if ( erreurs.isEmpty() ) {
			    userDao.createUser(user);
	        }
	    return user;
	}
	
	private String ValidatePassword( String password, String password_repeat )  {
	    if (password != null && password.length() != 0 && password_repeat != null && password_repeat.length() != 0) {
	        if (!password.equals(password_repeat)) {

	        	erreurs.put("passwordDifferent","Les mots de passe entrés sont différents,merci de les saisir à nouveau."  );
	        } if (password.trim().length() < 8) {
	        	erreurs.put("errorPaswordLength","Les mots de passe doivent contenir au moins 8 caractères.");
	    }
	    }
	    ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
	    passwordEncryptor.setAlgorithm( "SHA-256" );
	    passwordEncryptor.setPlainDigest( false );
	    String pswdChiffre = passwordEncryptor.encryptPassword( password );
	    return (pswdChiffre);
	}
	private void validationName( String username )  {
	    if ( username != null && username.length() < 3 ) {
        	erreurs.put("usernameErreur","Merci de saisir un username valide ."  );
	    }
	    if( userDao.usernameExists(username)) {
        	erreurs.put("usernameexists","Username existe déjà, connectez-vous ou choisisez un nouveau"  );
	    }
	}
	
	private void validationEmail( String email )  {
	 if(userDao.emailExists(email)) {
     	erreurs.put("emailexists","Email existe déjà, connectez-vous ou choisisez un nouveau"  );
	    }
	}
	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Map<String, String> erreurs) {
		this.erreurs = erreurs;
	}

}
