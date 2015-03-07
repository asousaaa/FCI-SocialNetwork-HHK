<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>

</body>
<p>
request page


</p>
<form action="acceptrequest" method="post">
<input type="hidden" name="friend_id" value=${it.fid } />
<input type="hidden" name="user_id" value=${it.uid } />
${it.fname} send you request <input type="submit"  value="accept"/> 

</form>
</html>