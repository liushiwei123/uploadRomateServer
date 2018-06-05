<%@ page language="java" pageEncoding="UTF-8"%>
 <!DOCTYPE HTML>
 <html>
   <head>
         <title>文件上传</title>
       </head>

   <body>
     <form action="${pageContext.request.contextPath}/uploadFile" enctype="multipart/form-data" method="post">
             <%--上传用户：<input type="text" name="username"><br/>--%>
             上传文件1：<input type="file" name="file"><br/>
             <input type="submit" value="提交">
         </form>
  </body>
 </html>
