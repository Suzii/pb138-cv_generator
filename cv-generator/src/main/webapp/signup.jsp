<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<html ng-app="signupApp">
    <head>
        <title><f:message key="title" /></title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
        <link rel="stylesheet" href="css/main.css">
        <script src="js/signup.js"></script>

    </head>
    <body ng-controller="SignupController">
        <div class="jumbotron">
            <div class="container">
                <h1><f:message key="heading" /></h1>
                <p><f:message key="intro-text" /></p>
                <p>Create new account here</p>

            </div>
        </div>
        <div class="container">

            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${error}"/>
                </div>
            </c:if>
            <c:if test="${not empty msg}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${msg}"/>
                </div>
            </c:if>
            <form name="signupForm" action="${pageContext.request.contextPath}/signup/submit" method="POST" class="form-horizontal" id="loginForm" novalidate>
                <div class="form-group" >
                    <label for="login" class="col-sm-2 control-label"><f:message key="username" /></label>
                    <div class="col-sm-4">
                        <input type="text" id="login" name="login" value="" class="form-control" required/>
                    </div>
                </div>
                <div class="form-group" >
                    <label for="password" class="col-sm-2 control-label"><f:message key="password" /></label>
                    <div class="col-sm-4">
                        <input type="password" id="password" name="password" value="" class="form-control" ng-model="passwd" required />
                    </div>
                </div>
                <div class="form-group" >
                    <label for="password2" class="col-sm-2 control-label"><f:message key="password" /> check</label>
                    <div class="col-sm-4">
                        <input type="password" id="password2" name="password2" value="" class="form-control" ng-model="passwd2" required />
                    </div>
                </div>
                    <div class="has-error col-sm-offset-2">
                        <p ng-show="signupForm.$invalid" class="help-block">All fields are required.</p>
                        <p ng-show="passwd !== passwd2" class="help-block">Passwords do not match.</p>
                    </div>
                <input type="Submit" class="btn btn-primary col-sm-offset-2 col-sm-1" value="Sign up" ng-disabled="signupForm.$invalid || passwd !== passwd2"/>
            </form>
        </div>
    </body>
</html>