<html>

<head>
  <title>OpenReports</title>     
</head>

<style type="text/css">

table.dialog
{
	border: 1px solid #000666;
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	font-size: 10pt;		
	margin-top: 0;
	margin-left: 0;
	margin-right: 0;text-align: left;	
	background: #EEE
}
table.dialog th
{
	background: #CCC;		
}
table.dialog td
{
	background: #eee;		
}
table.dialog td.dialogButtons
{	
	text-align: right;		
}
.standardButton 
{
	width=90pt;
	font-weight: bold;
}
</style>
    
<body marginwidth="0" marginheight="0" >

<br>	
<br>

<div align="center">  
	
	<% if (request.getParameter("failed") != null) { %>
		<strong>Login Failed. Please try again.</strong><br/><br/>
	<% } %>
		
  <form action="<%=response.encodeURL("j_security_check")%>" method="post">
  <table class="dialog">
    <tr>
      <th colspan="2"><font color="#990000">OpenReports Login:</font></th>
    </tr>
    <tr>
      <td class="boldText">User Name</td>
      <td><input type="text" name="j_username"></td>
    </tr>
    <tr>
      <td class="boldText">Password</td>
      <td><input type="password" name="j_password"></td>
    </tr>  
    <tr>
      <td class="dialogButtons" colspan="2"><input class="standardButton" type="submit" value="Login"></td>
    </tr>
  </table>
  </form> 
  
  <br>
	
</div>



</body>

</html>



