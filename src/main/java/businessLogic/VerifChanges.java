package businessLogic;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import dao.UserDao;

public class VerifChanges {
	private Map<String, String> erreurs ;
    private UserDao userDao;
    
    public VerifChanges( UserDao userDao) {
    	this.userDao=userDao;
    }
		public void PasswordChange(HttpServletRequest request) {

			String oldPassword= request.getParameter("oldPassword");
			String Newpassword = request.getParameter("newPassword");
			String confirmNewPassword = request.getParameter("ConfirmNewPassword");
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("username");
			System.out.println("valueeee of username ");
			System.out.println(userName);
			this.erreurs = new HashMap<String, String>();
			if(oldPassword != null && oldPassword.trim().length()!=0 && !userDao.checkUser(userName, oldPassword)) {
				erreurs.put("oldPassword","merci de saisir un pswd valide");
				
			}
			if (Newpassword != null && Newpassword.trim().length() != 0 && confirmNewPassword != null && confirmNewPassword.trim().length() != 0) {
		        if (!Newpassword.equals(confirmNewPassword)) {

		        	erreurs.put("passwordDifferent","Les mots de passe entrés sont différents,merci de les saisir à nouveau."  );
		        } if (Newpassword.trim().length() < 8) {
		        	erreurs.put("errorPaswordLength","Les mots de passe doivent contenir au moins 8 caractères.");

		    }
		    }
			if(erreurs.isEmpty()) {
				System.out.print("i m in verifChange");

		    ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		    passwordEncryptor.setAlgorithm( "SHA-256" );
		    passwordEncryptor.setPlainDigest( false );
		    String NewPswdChiffre = passwordEncryptor.encryptPassword( Newpassword );
		    System.out.println("I m hereee and this is username :");
		    System.out.println(userName);
		    
		    userDao.changePassword(userName, NewPswdChiffre);
		}
		}
			public Map<String, String> getErreurs() {
				return erreurs;
			}

			public void setErreurs(Map<String, String> erreurs) {
				this.erreurs = erreurs;
			}
}
