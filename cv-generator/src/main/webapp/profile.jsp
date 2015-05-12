<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<html ng-app="profileApp">
    <head>
        <%@ include file="/meta-data.html" %> 
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

                <h2 ng-bind="info['given-names']"></h2>
                <h3 ng-bind="info.surname"></h3>

                <div class="row">
                    <div class="col-sm-5 col-sm-offset-1">
                        <form action="${pageContext.request.contextPath}/profile/download" class="form-inline" method="GET">
                            <div class="form-group">
                                <select class="form-control" required>
                                    <option value="en" selected="selected">English</option>
                                    <option value="sk">Slovak</option>
                                </select>
                                <button class="btn btn-danger " >
                                    <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> Download PDF
                                </button>
                            </div>
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
            <div class="row">
                <div class="col-sm-3">Street</div>
                <div class="col-sm-6">{{info.address.street}}</div>
            </div>


        </div>
        <div class="container">
            <pre>{{data| json : spacing}}</pre>
        </div>

    </body>
</html>