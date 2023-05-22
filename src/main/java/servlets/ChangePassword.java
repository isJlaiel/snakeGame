package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import businessLogic.VerifChanges;
import dao.DaoFactory;
import dao.UserDao;


@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private UserDao userDao;

	    public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.userDao = daoFactory.getUserDao();
	    }
    public ChangePassword() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// mettre à jour l'attribut "activeTab" dans la session
		session.setAttribute("activeTab", "changePassword");
		this.getServletContext().getRequestDispatcher("/WEB-INF/EditProfile.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		  VerifChanges verifchange = new VerifChanges(userDao);
		  
		  verifchange.PasswordChange(request);

	        request.setAttribute( "verifchangePassword", verifchange );
	        if(verifchange.getErreurs().isEmpty()) {
	        request.setAttribute("statutPassword", "success");
	        }else {
	        	request.setAttribute("statutPassword", "failed");

	        }
        	doGet(request, response);


	        }}
