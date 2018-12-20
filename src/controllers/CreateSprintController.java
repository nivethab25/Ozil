package controllers;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.OzilUtil;
import entities.Sprint;

/**
 * 
 * @author AlexanderM
 * Skeleton Controller for creating a new sprint within a project.
 */
public class CreateSprintController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -552577015968571545L;

	public CreateSprintController() {
		super();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String title = request.getParameter("sprintTitle");
		Date sprintBegin = new Date(request.getDateHeader("sprintBegin"));
		Date sprintEnd = new Date(request.getDateHeader("sprintEnd"));
		
		Sprint sprint = new Sprint(title,sprintBegin, sprintEnd);
		
		try {
			boolean res = OzilUtil.createSprint(sprint);
			if(res) {
				response.sendRedirect("dashboard.html");
			}
			
		}
		catch(ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
	}

}
