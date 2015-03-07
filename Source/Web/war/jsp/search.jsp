<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<p>
search page 

</p>
<form action="request" method="post">
<input type="hidden" name="friend_id" value=${it.fid } />
<input type="hidden" name="user_id" value=${it.uid } />
<input type="hidden" name="friend_name" value=${it.fname } />
<input type="hidden" name="user_name" value=${it.uname } />
<input type="submit" name="request" value="send request to ${it.fname}"  />
</form>

</body>

</html>