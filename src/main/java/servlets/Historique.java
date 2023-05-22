package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import beans.ScoreObject;
import dao.DaoFactory;
import dao.UserDao;

/**
 * Servlet implementation class Historique
 */
@WebServlet("/Historique")
public class Historique extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;


    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.userDao = daoFactory.getUserDao();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.getServletContext().getRequestDispatcher("/WEB-INF/Score.jsp").forward(request, response);	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  StringBuilder sb = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    try {
		        String line;
		        while ((line = reader.readLine()) != null) {
		            sb.append(line);
		        }
		        String jsonData = sb.toString();
		        JSONObject jsonObj = new JSONObject(jsonData);
		        ScoreObject Scoreobject = new ScoreObject();
		       
		       String name =jsonObj.getString("name");
		        int scoreTotal =jsonObj.getInt("scoreTotal");
		        int nbPartie =jsonObj.getInt("nbPartie");
		        
				userDao.updateHistorique(name,scoreTotal,nbPartie);

		        this.getServletContext().getRequestDispatcher("/WEB-INF/Dashboard.jsp").forward(request, response);	

	}catch (Exception e) {
		// TODO: handle exception
	}}

}
