package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Historique;
import beans.User;
import dao.DaoFactory;
import dao.UserDao;

@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   private UserDao userDao;
	   
	    public void init() throws ServletException {
	        DaoFactory daoFactory = DaoFactory.getInstance();
	        this.userDao = daoFactory.getUserDao();
	    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<User> userList = userDao.getAllUsers();
		Collections.sort(userList, new Comparator<User>() {
		    @Override
		    public int compare(User u1, User u2) {
		        return (u2.getScore() -u1.getScore()); 
		    }
		});
		int totalNbParie = 0;

		for (User user : userList) {
		    totalNbParie += user.getNbPartie();
		}
		request.setAttribute("totalNbParie", totalNbParie);

		request.setAttribute("userList", userList);

		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		List<Historique> historiqueList = userDao.getHistorique(username);
		request.setAttribute("HistoriqueList", historiqueList);

		this.getServletContext().getRequestDispatcher("/WEB-INF/Dashboard.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
