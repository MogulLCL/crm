<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + 	request.getServerPort() + request.getContextPath() + "/";
%>
<base href="<%=basePath%>">
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>

        $.ajax({
            url: "",
            data: {

            },
            type: "post",
            dataType: "json",
            success :function (data) {

            }
        })


</script>
</body>
</html>
