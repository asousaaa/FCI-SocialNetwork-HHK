<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> Welcome b2a ya ${it.name} </p>
</body>
<form action="search" method="post">
name : <input name="searchname" type="text" /> 
<input type="hidden" value=${it.ID } name="activeid" />
<input type="hidden" value=${it.name } name="activename" />
<input name="SEARCH" type="submit" value="search"/>
</form><br>

<form action="login" method="post">
<input name="signout" type="submit" value="sign out"/>
</form>
<form  action="viewrequest" method="post">
<input type="hidden" value=${it.ID } name="activeid" />
<input name="signout" type="submit" value="view request"/>
</form>

<form action="notifiy" method="post">
<input type="hidden" value="1" name="userid" />
<input name="signout" type="submit" value="notifcation"/>
</form>

</html>