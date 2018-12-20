<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Defects Logging Results</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- MATERIAL DESIGN ICONIC FONT -->
<link rel="stylesheet"
	href="fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">

<!-- STYLE CSS -->
<link rel="stylesheet" href="css/style.css">

<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<script>
function statusChanged(value, id){
$.ajax({
url: "http://localhost:8080/Ozil/updateStatus",
type: "post",
data: {
taskId:id,
status:value,
defect:true
}
});
}

function assignTo(usr, id){
	$.ajax({
	url: "http://localhost:8080/Ozil/assignTo",
	type: "post",
	data: {
	taskId:id,
	user:usr,
	defect:true
	}
	});
	}
</script>
</head>

<body>

	<%@ page import="java.sql.ResultSet"%>
	<%@ page import="java.sql.Statement"%>
	<%@ page import="java.sql.Connection"%>
	<%@ page import="java.sql.DriverManager"%>
	<%@ page import="entities.Status"%>
	<%@ page import="entities.OzilUtil"%>
	<%@ page import="java.util.*"%>

	<form method="post"
		style="background-image: url('images/jsp.jpg'); position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto; min-width: 100%; min-height: 100%;">
		<br> <br> <br> <br>
		<div class="head">
			<h1>Defects</h1>
		</div>
		<br>
		<div style="height: 700px; overflow: auto">
			<table class="myTable">
				<thead>
					<tr>
						<th>Story</th>
						<th>Defect Title</th>
						<th>Defect Description</th>
						<th>Priority</th>
						<th>Status</th>
						<th>Assign To</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<td colspan="6">
							<div class="links">
								<a href="#">&laquo;</a> <a class="active" href="#">1</a> <a
									href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">&raquo;</a>
							</div>
						</td>
					</tr>
				</tfoot>
				<tbody>
					<%
						try {
							OzilUtil.initProject();
							HashMap<Integer, String> userNidMap = OzilUtil.getUsers();
							System.out.println(userNidMap);
							Class.forName("org.sqlite.JDBC");
							Connection conn = DriverManager.getConnection("jdbc:sqlite:" + "OzilUsersDB.db");
							//Connection conn = DriverManager.getConnection("jdbc:sqlite:" +  "C:\\Users\\AlexanderM\\Google Drive\\cs440gitrepo\\Code\\OzilWebProject\\OzilUsersDB.db");          
							Statement stmt = conn.createStatement();
							ResultSet rs = stmt.executeQuery(
									"select defect.title as title, defect.description as description, priority, status, story.title as sname, defectId, userId from defect inner join story on defect.storyId = story.storyId;");
							while (rs.next()) {
					%>
					<%
						String defaultStatus = rs.getString("status");
								System.out.println("\n\nnew Status: " + defaultStatus);
								String status1 = "", status2 = "";
								if (defaultStatus != null) {
									if (defaultStatus.equals(Status.inProgress.display())) {
										defaultStatus = "In Progress";
										status1 = "Done";
										status2 = "To Do";
									}

									else if (defaultStatus.equals(Status.toDo.display())) {
										defaultStatus = "To Do";
										status1 = "In Progress";
										status2 = "Done";
									}

									else {
										defaultStatus = "Done";
										status1 = "To Do";
										status2 = "In Progress";
									}

								}
					%>
					<tr>
						<td><%=rs.getString("sname")%></td>
						<td><%=rs.getString("title")%></td>
						<td><%=rs.getString("description")%></td>
						<td><%=rs.getString("priority")%></td>
						<td><select type="text" class="form-control" name="status"
							onChange="statusChanged(this.options[this.selectedIndex].value, <%=rs.getInt("defectId")%>)">
								<option value="<%=defaultStatus%>" selected><%=defaultStatus%></option>
								<option value="<%=status1%>"><%=status1%></option>
								<option value="<%=status2%>"><%=status2%></option>
						</select></td>
						<td><select type="text" class="form-control" name="assign"
							onChange="assignTo(this.options[this.selectedIndex].value,<%=rs.getInt("defectId")%>)">
								<%
									String userFromDb = userNidMap.get(rs.getInt("userId"));
											String displayUser = userFromDb != null ? userFromDb : new String("unassigned");
								%>
								<option value=<%=displayUser%> selected><%=displayUser%></option>
								<%
									String usr = null;
											for (String user : userNidMap.values()) {
												usr = user;
												if (!usr.equals(displayUser)){
								%>
								<option value="<%=usr%>"><%=usr%></option>
								<%
												}
									System.out.println("\n" + usr+" " +displayUser);
											}
								%>
						</select></td>
					</tr>

					<%
						}
					%>
				</tbody>
			</table>
		</div>
		<%
			rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		%>
		<br>
		<div>
			<input type="button" class="btn" background="#1c32a0"
				onClick="window.location.href= 'dashboard.html'"
				value="Return to Home" />
		</div>
	</form>
</body>
</html>