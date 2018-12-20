package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.OzilUtil;

/**
 * Servlet implementation class AssignTaskController
 */
@WebServlet("/assignTo")
public class AssignTaskController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignTaskController() {
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
		String user = request.getParameter("user");
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		String isDefect = request.getParameter("defect");
		
		System.out.println(user+" "+taskId);
		try {
			OzilUtil.initProject();
			int uid = OzilUtil.getUserId(user);
			boolean isSuccessful = true;
			
			if(isDefect.equalsIgnoreCase("false"))
				isSuccessful = OzilUtil.assignTo(taskId, uid);
			else
				isSuccessful = OzilUtil.assignDefectTo(taskId, uid);
			
			System.out.println(isSuccessful);
			if(isSuccessful) {
				
			}
			else {
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
