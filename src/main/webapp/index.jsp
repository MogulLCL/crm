<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<header>
<script src="https://how2j.cn/study/jquery.min.js"></script>
</header>
<script >

    $(function(){
        $.ajax({
            url: "test",
            type: "get",
            dataType: "json",
            data: "m=sss",
            success : function (data) {
                alert(data.data);
            }
        });
    });

</script>
</html>
