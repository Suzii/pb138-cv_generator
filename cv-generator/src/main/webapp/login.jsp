<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en" >
    <head>
        <%@ include file="/meta-data.html" %> 
    </head>
    <body>
        <div class="jumbotron">
            <div class="container">
                <h1><f:message key="heading" /></h1>
                <p><f:message key="intro-text" /></p>
            </div>
        </div>
        <div class="container">

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${error}"/>
                </div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-info" role="alert">
                    <c:out value="${msg}"/>
                </div>
            </c:if>
            <form action="${pageContext.request.contextPath}/login/submit" method="POST" class="form-horizontal" id="loginForm">
                <div class="form-group" >
                    <label for="login" class="col-sm-2 control-label"><f:message key="username" /></label>
                    <div class="col-sm-4">
                        <input type="text" id="login" name="login" value="" class="form-control" required/>
                    </div>
                </div>
                <div class="form-group" >
                    <label for="password" class="col-sm-2 control-label"><f:message key="password" /></label>
                    <div class="col-sm-4">
                        <input type="password" id="password" name="password" value="" class="form-control" required/>
                    </div>
                </div>
                <input type="Submit" class="btn btn-primary col-sm-offset-2 col-sm-1" value="Login" />
            </form>
            <a href="signup">
                <span class="btn btn-success col-sm-offset-1 col-sm-1" >
                    <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Sign up
                </span>
            </a>

        </div>
                    
        <%@ include file="/footer.jsp" %> 
    </body>
</html>