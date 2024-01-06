<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
  <form method="post" action="<%=request.getContextPath()%>/user?type=login">
    <input type="text" name="userName"/> <br>
    <input type="password" name="pwd"/> <br>
    <input type="submit" value="提交"/> <br>
  </form>

  <a href="<%=request.getContextPath()%>/user?type=list">列出用户列表</a>
</body>
</html>
