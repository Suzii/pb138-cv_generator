<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<html ng-app="profileApp">
    <head>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <title><f:message key="title" /></title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
        <script src="js/profile.js"></script>

        <script>
            var userData = undefined;
            <c:if test="${not empty userData}">
            var userData = ${userData}
            </c:if>
        </script>
    </head>
    <body ng-controller="ProfileController">
        <div class="jumbotron">
            <div class="container">
                <h1><f:message key="heading" /></h1>
                <p><f:message key="intro-text" /></p>
                <p>HI! Perfect, you have your own profile in our database!</p>
                <p>You can download a pdf version of your CV now!</p>

                <div class="row">
                    <div class="col-sm-6">
                        <form action="${pageContext.request.contextPath}/profile/download" method="GET">
                            <button class="btn btn-danger col-sm-offset-1 col-sm-3" >
                                <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> Download PDF
                            </button>
                        </form>
                    </div>
                    <div class="col-sm-6">
                        <form action="${pageContext.request.contextPath}/profile/edit" method="GET">
                            <button class="btn btn-primary col-sm-offset-1 col-sm-3" >
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
                            </button>
                        </form>
                    </div>    

                </div>

            </div>
        </div>
        <div class="container">

            Wow, this is where the profile should be!
            <div class="container">
                <pre>{{data| json : spacing}}</pre>
            </div>
        </div>
    </body>
</html>