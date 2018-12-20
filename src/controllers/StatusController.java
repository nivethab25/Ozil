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
 * Servlet implementation class StatusController
 */
@WebServlet("/updateStatus")
public class StatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatusController() {
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
		String status = request.getParameter("status");
		int taskId = Integer.parseInt(request.getParameter("taskId"));
		String isDefect = request.getParameter("defect");
		
		System.out.println(status+" "+taskId);
		try {
			OzilUtil.initProject();
			boolean isSuccessful = true;
			if(isDefect.equalsIgnoreCase("false"))
				isSuccessful = OzilUtil.updateStatus(taskId, status);
			else
				isSuccessful = OzilUtil.updateDefectStatus(taskId, status);
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
