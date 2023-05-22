package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import beans.ScoreObject;
import dao.DaoFactory;
import dao.UserDao;

/**
 * Servlet implementation class Score
 */
@WebServlet("/Score")
public class Score extends HttpServlet {
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
		        int score =jsonObj.getInt("score");
		        System.out.println(jsonObj.getString("name"));
		        System.out.println(jsonObj.getInt("score"));
				userDao.updateScore(name,score);

		        request.setAttribute("Scoreobject", Scoreobject);
		        this.getServletContext().getRequestDispatcher("/WEB-INF/Dashboard.jsp").forward(request, response);	

		    
		    } catch (Exception e) {
		        System.out.println(e)	;	    }

}}
