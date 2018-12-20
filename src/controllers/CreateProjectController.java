package controllers;




import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.OzilUtil;
import entities.Project;

@WebServlet("/createProject")
public class CreateProjectController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4669423593548046577L;

	public CreateProjectController() {
		super();
	}
	
	protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("descrip");
		
		Project project = new Project (title, description,OzilUtil.getCurrentUser().getUserID() );
		try {
			boolean res = OzilUtil.createProject(project);
			if(res) {
				response.sendRedirect("dashbard.html");
			}
		}
		catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
