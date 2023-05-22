package servlets;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.DaoFactory;
import dao.UserDao;

@WebServlet("/EditProfile")

public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDao userDao;

	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.userDao = daoFactory.getUserDao();
	}


	public EditProfile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		request.setAttribute("username", userDao.getUsersInfo(username).getUsername());
		request.setAttribute("email", userDao.getUsersInfo(username).getEmail());
		request.setAttribute("firstname", userDao.getUsersInfo(username).getFirstName());
		request.setAttribute("lastname", userDao.getUsersInfo(username).getLastName());
		request.setAttribute("bio", userDao.getUsersInfo(username).getBio());

		byte[] image = userDao.getImage(username);
		if (image != null && image.length > 0) {
			String base64Image = Base64.getEncoder().encodeToString(image);
			request.setAttribute("image", "data:image/jpeg;base64," + base64Image);
			this.getServletContext().getRequestDispatcher("/WEB-INF/EditProfile.jsp").forward(request, response);

		} else {
			response.getWriter().println("Image not found.");
			this.getServletContext().getRequestDispatcher("/WEB-INF/EditProfile.jsp").forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String oldUsername = (String) session.getAttribute("username");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String bio = request.getParameter("bio");

		userDao.updateProfile(oldUsername, username, email, firstname, lastname, bio);
		session.setAttribute("username", username);

		doGet(request, response);
	}

}
