<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p> Welcome b2a ya ${it.ID} </p>
<p> This is should be user home page </p>
<p> Current implemented services "http://fci-swe-apps.appspot.com/rest/RegistrationService --- {requires: uname, email, password}" </p>
<p> and "http://fci-swe-apps.appspot.com/rest/LoginService --- {requires: uname,  password}" </p>
<p> you should implement sendFriendRequest service and addFriend service
</body>
<form action="search" method="post">
name : <input name="searchname" type="text" /> 
<input type="hidden" value=${it.ID } name="activeid" />
<input name="SEARCH" type="submit" value="search"/>
</form><br>

<form action="login" method="post">
<input name="signout" type="submit" value="sign out"/>
</form>
<form  action="request" method="post">
<input name="signout" type="submit" value="view request"/>
</form>

</html>