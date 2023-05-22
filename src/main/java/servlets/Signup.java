package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import businessLogic.Verification;
import dao.DaoFactory;
import dao.UserDao;


@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	  private UserDao userDao;

	    public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.userDao = daoFactory.getUserDao();
	    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 this.getServletContext().getRequestDispatcher("/WEB-INF/Signup.jsp").forward(request, response);	
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Verification verifForm = new Verification(userDao);
        User user =  verifForm.validForm(request);
        request.setAttribute( "form", verifForm );
        request.setAttribute("user", user);
        
        if(verifForm.getErreurs().isEmpty()) {
    		HttpSession session = request.getSession();
		session.setAttribute("username", user.getUsername());
		request.setAttribute("statut", "success");
		response.sendRedirect("http://localhost:8080/Snake/Dashboard");

	}else {
		request.setAttribute("statut", "failed");
		doGet(request, response);
	}	
		
	}

}
