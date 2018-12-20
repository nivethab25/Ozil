package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Defect;
import entities.OzilUtil;
import entities.Status;

/**
 * Servlet implementation class LogDefectController
 */
@WebServlet("/logDefect")
public class LogDefectController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogDefectController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String title = request.getParameter("title");
		String description = request.getParameter("descrip");
		String priority = request.getParameter("priority");
		Status status = Status.toDo;
		int storyID = Integer.parseInt(request.getParameter("storyId"));
		System.out.println("\nDescription : "+description);
		Defect defect = new Defect(title, description, priority, status, storyID);
		try {
			OzilUtil.initProject();
			boolean res = OzilUtil.logDefect(defect);
			if(res)
				response.sendRedirect("defects.jsp");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
