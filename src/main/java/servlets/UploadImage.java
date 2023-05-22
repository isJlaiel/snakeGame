package servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

import dao.DaoFactory;
import dao.UserDao;


@WebServlet("/UploadImage")
@MultipartConfig
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private UserDao userDao;


	public void init() throws ServletException {
		DaoFactory daoFactory = DaoFactory.getInstance();
		this.userDao = daoFactory.getUserDao();
	}

    public UploadImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Part filePart = request.getPart("image");
	    InputStream fileContent = filePart.getInputStream();
	    userDao.updateImage((String) session.getAttribute("username"), fileContent);
	    
	    response.sendRedirect("/WEB-INF/EditProfile");

	}
}
