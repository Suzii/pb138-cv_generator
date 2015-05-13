<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<html>
    <head>
        <%@ include file="/meta-data.html" %> 
    </head>
    <body>
        <div class="jumbotron">
            <div class="container">
                <h1>404 not found</h1>
                <p>This site does not exist.</p>
                <a href="${pageContext.request.contextPath}/login">
                    <button class="btn btn-primary col-sm-offset-1 col-sm-3" >
                        <span class="glyphicon glyphicon-home" aria-hidden="true"></span> Home
                    </button>
                </a>
            </div>
        </div>  
        <%@ include file="/footer.html" %> 
    </body>
</html>