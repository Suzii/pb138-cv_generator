<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"  data-ng-app="profileApp">
    <head>
        <%@ include file="/meta-data.html" %> 
        <script src="${pageContext.request.contextPath}/js/profile.js"></script>
        <script>
            var userData = undefined;
            <c:if test="${not empty userData}">
            var userData = ${userData}
            </c:if>
        </script>
    </head>
    <body data-ng-controller="ProfileController">
        <div class="jumbotron">
            <div class="container">
                <h1><f:message key="heading" /></h1>
                <p><f:message key="intro-text" /></p>

                <div class="row">
                    <div class="col-sm-6">
                        <h2 data-ng-bind="info['given-names']"></h2>
                        <h3 data-ng-bind="info.surname"></h3>
                    </div>
                    <div class="col-sm-2">
                        <form action="${pageContext.request.contextPath}/profile/download" class="form-inline" name="langForm" method="GET">
                            <div class="form-group">
                                <label class="sr-only" for="lang">Language</label>
                                <select class="form-control" name="lang" id="lang" required>
                                    <option value="" selected="selected">Select language</option>
                                    <option value="en">English</option>
                                    <option value="sk">Slovak</option>
                                </select>
                            </div>
                            <button class="btn btn-default">
                                <span class="glyphicon glyphicon-floppy-save" aria-hidden="true"></span> Download PDF
                            </button>
                        </form>
                    </div>
                    <div class="col-sm-2 col-sm-offset-1">
                        <form action="${pageContext.request.contextPath}/edit/logout" method="GET">
                            <button class="btn btn-danger btn-block" >
                                <span class="glyphicon glyphicon-off " aria-hidden="true"></span> Log out
                            </button>
                        </form>

                        <a href="edit">
                            <span class="btn btn-primary btn-block" >
                                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit
                            </span>
                        </a>
                    </div>
                </div>
            </div>
        </div>


        <div class="container">
            <div class="row">
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
            </div>
            <div class="row">
                <div class="col-sm-6">
                    <div class="row">
                        <h3> Contact info: </h3>
                        <div class="col-sm-2"><strong>Emails:</strong></div>
                        <div class="col-sm-4">
                            <a data-ng-repeat="email in info.emails" data-ng-href="mailto:{{email.value}}" target="_blank">{{email.value}}<br /></a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-2"><strong>Social:</strong></div>
                        <div class="col-sm-4">
                            <a data-ng-repeat="social in info.social" data-ng-href="http://{{social.value}}" target="_blank">{{social.value}}<br /></a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-2"><strong>Phones: </strong></div>
                        <div class="col-sm-4">
                            <span data-ng-repeat="phone in info.phones">{{phone.value}}<br /></span>
                        </div>
                    </div>

                </div>
                <div class="col-sm-6">
                    <h3> Address: </h3>
                    <address>
                        <strong>{{info['given-names']}} {{info.surname}}</strong> <br />
                        {{info.address.street}} {{info.address.number}} <br />
                        {{info.address.city}}, {{info.address['postal-code']}} <br />
                        {{info.address.state}} <br />
                        {{info.address.country}} <br />
                    </address>
                </div>
            </div>
        </div>

        <!--div class="container">
            <pre>{{data| json : spacing}}</pre>
        </div-->
        <%@ include file="/footer.jsp" %> 
    </body>
</html>