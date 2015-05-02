<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<html>
    <head>
        <title><f:message key="title" /></title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/md5.js"></script>
        <script src="js/login.js"></script>

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
        
    </body>
</html>