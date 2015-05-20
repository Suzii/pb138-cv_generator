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
                <p>Create your new account here</p>
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
            <form name="signupForm" action="${pageContext.request.contextPath}/signup/submit" method="POST" class="form-horizontal" id="loginForm">
                <div class="form-group" >
                    <label for="login" class="col-sm-2 control-label"><f:message key="username" /></label>
                    <div class="col-sm-4">
                        <input type="text" id="login" name="login" value="" class="form-control" required/>
                    </div>
                </div>
                <div class="form-group" >
                    <label for="password" class="col-sm-2 control-label"><f:message key="password" /></label>
                    <div class="col-sm-4">
                        <input type="password" id="password" name="password" value="" class="form-control" required />
                    </div>
                </div>
                <div class="form-group" >
                    <label for="password2" class="col-sm-2 control-label"><f:message key="password" /> check</label>
                    <div class="col-sm-4">
                        <input type="password" id="password2" name="password2" value="" class="form-control" required />
                    </div>
                </div>
                <input type="Submit" class="btn btn-primary col-sm-offset-2" value="Sign up" />
            </form>
        </div>
        <%@ include file="/footer.jsp" %> 
    </body>
</html>