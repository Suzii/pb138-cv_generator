<%@ page import="cz.muni.fi.pb138.cv.generator.LoginServlet" %>
<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
HI! Type 'x' to proceed.
<form action="${pageContext.request.contextPath}/login/submit" method="POST" class="form-horizontal" id="loginForm">
<!--   form action="${pageContext.request.contextPath}/login/start" method="post" class="form-horizontal"-->
    <div class="form-group" >
        <label for="name" class="col-sm-2 control-label"><f:message key="name" /></label>
        <div class="col-sm-10">
            <input type="text" id="login" name="name" value="" class="form-control"/>
            <input type="text" id="password" name="passwd" value="" class="form-control"/>
        </div>
    </div>
    <input type="Submit" class="btn btn-lg btn-primary pull-right" value="Start" />
</form>
<p>Status: ${data}</p>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="http://crypto-js.googlecode.com/svn/tags/3.1.2/build/rollups/md5.js"></script>
<script src="js/login.js"></script>
