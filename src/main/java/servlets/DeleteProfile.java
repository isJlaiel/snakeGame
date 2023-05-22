package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DaoFactory;
import dao.UserDao;


@WebServlet("/DeleteProfile")
public class DeleteProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
    }


    public DeleteProfile() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("activeTab", "deleteProfile");
		this.getServletContext().getRequestDispatcher("/WEB-INF/EditProfile.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String username = (String) session.getAttribute("username");
		String password = request.getParameter("pswd");

		if(userDao.checkUser(username, password)) {
			userDao.deleteUser(username);
			session.invalidate();
			 this.getServletContext().getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);	

		}else {
        	request.setAttribute("statutDeleteProfile", "mots de passe incorrecte");


        }
    	doGet(request, response);

	}

}
