<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>My Stories</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<!-- MATERIAL DESIGN ICONIC FONT -->
<link rel="stylesheet"
	href="fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">

<!-- STYLE CSS -->
<link rel="stylesheet" href="css/style.css">
</head>

<body>

	<%@ page import="java.sql.ResultSet"%>
	<%@ page import="java.sql.Statement"%>
	<%@ page import="java.sql.Connection"%>
	<%@ page import="java.sql.DriverManager"%>

	<form method="post" style="background-image: url('images/jsp.jpg'); position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto; min-width: 100%; min-height: 100%;">
	<br><br><br><br>
	<div class = "head" ><h1>Stories</h1></div>
<br>
<div><input type = "button" class="btn" background = "#1c32a0" onClick = "window.location.href= 'createStory.html'" value = "Create a Story"/></div>
<br><br><br><br>
<div style="height: 300px; overflow: auto"  >
		<table class = "myTable">
			<thead>
			<tr>
				<th>Story Title</th>
				<th>Story Description</th>
				<th>Add Tasks</th>
				<th>Log Defects</th>
			</tr>
			</thead>
			<tfoot>
<tr>
<td colspan="3">
<div class="links"><a href="#">&laquo;</a> <a class="active" href="#">1</a> <a href="#">2</a> <a href="#">3</a> <a href="#">4</a> <a href="#">&raquo;</a></div>
</td>
</tr>
</tfoot>
<tbody>
			<%
try
{
Class.forName("org.sqlite.JDBC");
Connection conn = DriverManager.getConnection("jdbc:sqlite:" +  "OzilUsersDB.db"); 
//Connection conn = DriverManager.getConnection("jdbc:sqlite:" +  "C:\\Users\\AlexanderM\\Google Drive\\cs440gitrepo\\Code\\OzilWebProject\\OzilUsersDB.db");          
Statement stmt=conn.createStatement();
ResultSet rs=stmt.executeQuery("select * from story;");
while(rs.next())
{

%>
			<tr>
				<td><%=rs.getString("title") %></td>
				<td><%=rs.getString("description") %></td>
				<td><a href ="createTask.html?storyID=<%= rs.getInt("storyId")%>&storyTitle=<%= rs.getString("title")%>">Create Task</a></td>
			<td><a href ="logDefect.html?storyID=<%= rs.getInt("storyId")%>&storyTitle=<%= rs.getString("title")%>">Log Defect</a></td>
			</tr>

			<%

}
%></tbody>
		</table>
		</div>
		<%
rs.close();
stmt.close();
conn.close();
}
catch(Exception e)
{
e.printStackTrace();
}
%>
<br>
<div><input type = "button" class="btn" background = "#1c32a0" onClick = "window.location.href= 'dashboard.html'" value = "Return to Home"/>
</div>	
	</form>
</body>
</html>