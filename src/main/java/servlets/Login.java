package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoFactory;
import dao.UserDao;



@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;
  
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
    }

    
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.getServletContext().getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("usernameLogin");
		String password = request.getParameter("passwordLogin");
		
		
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		
		if (userDao.checkUser(username, password)) {
			session.setAttribute("username", username);
			request.setAttribute("statut", "success");
			response.sendRedirect("http://localhost:8080/Snake/Dashboard");

		}else {
			request.setAttribute("statut", "failed");
			doGet(request, response);
		}		
		
	
	

}

}
