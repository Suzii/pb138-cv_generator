<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />

<html ng-app="editApp">
    <head>
        <%@ include file="/meta-data.html" %> 
        <script src="${pageContext.request.contextPath}/js/edit.js"></script>
        <script>
                    var userData = undefined;
            <c:if test="${not empty userData}">
            var userData = ${userData}
            </c:if>
        </script>
    </head>
    <body ng-controller="FormController">
        <!-- **************************** HEADING ************************** -->
        <div class="jumbotron">
            <div class="container">
                <h1><f:message key="heading" /></h1>
                <p><f:message key="intro-text" /></p>

                <div class="row">
                    <div class="col-sm-6">
                        <h2 ng-bind="data['personal-details']['given-names']"></h2>
                        <h3 ng-bind="data['personal-details'].surname"></h3>
                    </div>
                    <div class="col-sm-2 col-sm-offset-3">
                        <form action="${pageContext.request.contextPath}/edit/logout" method="GET">
                            <button class="btn btn-danger btn-block" >
                                <span class="glyphicon glyphicon-off " aria-hidden="true"></span> Log out
                            </button>
                        </form>

                        <a href="profile">
                            <button class="btn btn-primary btn-block" >
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Go to profile
                            </button>
                        </a>

                    </div>
                </div>
            </div>
        </div>
        <a name="top" ></a>
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
            <!-- page up button -->
            <a href="#" class="page-up">
                <span class="glyphicon glyphicon-triangle-top"></span> <br />
                <span> UP!</span>                
            </a>

            <!-- **************************** FORM ************************** -->
            <form action="${pageContext.request.contextPath}/edit/save" class="form-horizontal" method="POST" name="userForm"  id="cvForm" ng-submit="$event.preventDefault();
                                submit();" novalidate>
                <!-- **************************** PERSONAL DETAILS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="personal-details" /></div>
                    <div class="col-sm-6">
                        <!-- **************************** given names ************************** -->
                        <div class="form-group" ng-class="{'has-error': (!userForm['given-names'].$pristine || showErrors) &&  userForm['given-names'].$invalid}">
                            <label for="given-names" class="col-sm-2 control-label"><f:message key="given-names" />*</label>
                            <div class="col-sm-9">
                                <input type="text" name="given-names" ng-model="data['personal-details']['given-names']" class="form-control" required/>
                                <p ng-show="userForm['given-names'].$invalid && (!userForm['given-names'].$pristine || showError)" class="help-block">Given names are required.</p>
                            </div>
                        </div>
                        <!-- **************************** surname ************************** -->
                        <div class="form-group"  ng-class="{
                                'has-error': (!userForm.surname.$pristine || showError) && userForm.surname.$invalid}">
                            <label for="surname" class="col-sm-2 control-label"><f:message key="surname" />*</label>
                            <div class="col-sm-9">
                                <input type="text" name="surname" ng-model="data['personal-details'].surname" class="form-control" required />
                                <p ng-show="userForm.surname.$invalid && (!userForm.surname.$pristine || showError)" class="help-block">Surname is required.</p>
                            </div>
                        </div>
                        <!-- **************************** date of birth ************************** -->
                        <div class="form-group"  ng-class="{'has-error': userForm.dateOfBirth.$invalid && (!userForm.dateOfBirth.$pristine || showError)}">
                            <label for="dateOfBirth" class="col-sm-2 control-label"><f:message key="dateOfBirth" />*</label>
                            <div class="col-sm-9" ng-controller="DateController">
                                <ng-form name="dateOfBirth" class="form-inline">
                                    <div class="form-group">
                                        <select class="form-control" ng-model="data['personal-details']['date-of-birth'].day" ng-options="d for d in days" required>
                                            <option value="Day" selected="Day">Day</option>
                                        </select>
                                        <select class="form-control" ng-model="data['personal-details']['date-of-birth'].month" ng-options="m for m in months" required>
                                            <option value="Month">Month</option>
                                        </select>
                                        <select class="form-control" ng-model="data['personal-details']['date-of-birth'].year" ng-options="y for y in years" required>
                                            <option value="Year" selected="">Year</option>
                                        </select>
                                        <p ng-show="userForm.dateOfBirth.$invalid && (!userForm.dateOfBirth.$pristine || showError)" class="help-block">Date of birth is required.</p>
                                    </div>
                                </ng-form>
                            </div>
                        </div>
                        <!-- **************************** phones ************************** -->
                        <div ng-repeat="phone in data['personal-details'].phones" class="form-group"  ng-class="{
                                'has-error'
                                        : phoneForm.phone.$invalid && (!phoneForm.phone.$pristine || showError)}">
                            <ng-form name="phoneForm">
                                <label for="phone" class="col-sm-2 control-label"><f:message key="phone" /> {{$index + 1}}*</label>
                                <div class="col-sm-9">
                                    <input type="text" name="phone" ng-model="phone.value" class="form-control" required/>
                                    <p ng-show="phoneForm.phone.$invalid && (!phoneForm.phone.$pristine || showError)" class="help-block">Required.</p>
                                </div>
                                <a ng-click="$event.preventDefault();
                                            deleteItem(data['personal-details'].phones, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <label ng-show="data['personal-details'].phones.length == 0" class="col-sm-6">Phones</label>
                        <button class="btn add btn-primary btn-sm col-sm-offset-9 col-sm-2" ng-click="$event.preventDefault();
                                    addPhone(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                        <!-- **************************** emails ************************** -->
                        <label ng-show="data['personal-details'].emails.length == 0" class="col-sm-6">Emails</label>
                        <div ng-repeat="email in data['personal-details'].emails" class="form-group"  ng-class="{
                                'has-error'
                                        : emailForm.email.$invalid && (!emailForm.email.$pristine || showError)}">
                            <ng-form name="emailForm">
                                <label for="email" class="col-sm-2 control-label"><f:message key="email" /> {{$index + 1}}*</label>
                                <div class="col-sm-9">
                                    <div class="input-group">
                                        <div class="input-group-addon">@</div>
                                        <input type="email" name="email" ng-model="email.value" class="form-control" required/>
                                    </div>
                                        <p class="help-block" ng-show="emailForm.email.$invalid && (!emailForm.email.$pristine || showError)">Please, enter a valid email</p>
                                </div>
                                <a ng-click="$event.preventDefault();
                                            deleteItem(data['personal-details'].emails, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <button class="btn add btn-primary btn-sm col-sm-offset-9 col-sm-2" ng-click="$event.preventDefault();
                                    addEmail(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                        <!-- **************************** social ************************** -->
                        <label ng-show="data['personal-details'].social.length == 0" class="col-sm-6">Social</label>
                        <div ng-repeat="social in data['personal-details'].social" class="form-group"  ng-class="{
                                'has-error'
                                        : socialForm.social.$invalid && (!socialForm.social.$pristine || showError)}">
                            <ng-form name="socialForm">
                                <label for="social" class="col-sm-2 control-label"><f:message key="social" /> {{$index + 1}}*</label>
                                <div class="col-sm-9">
                                    <div class="input-group">
                                        <div class="input-group-addon">http://</div>
                                        <input type="text" name="social" ng-model="social.value" class="form-control" required/>
                                    </div>
                                        <p class="help-block" ng-show="socialForm.social.$invalid && (!socialForm.social.$pristine || showError)">Please, enter a valid link</p>
                                </div>
                                <a ng-click="$event.preventDefault();
                                            deleteItem(data['personal-details'].social, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <button class="btn add btn-primary btn-sm col-sm-offset-9 col-sm-2" ng-click="$event.preventDefault();
                                    addSocial(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                    </div>
                    <div class="col-sm-6">
                        <!-- **************************** street ************************** -->
                        <div class="form-group" ng-class="{
                                'has-error'
                                        : userForm.street.$invalid && (!userForm.street.$pristine || showError)}">
                            <label for="street" class="col-sm-2 control-label"><f:message key="street" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="street" ng-model="data['personal-details'].address.street" class="form-control" required/>
                                <p class="help-block" ng-show="userForm.street.$invalid && (!userForm.street.$pristine || showError)">Please, enter your street</p>
                            </div>
                        </div>
                        <!-- **************************** number ************************** -->
                        <div class="form-group" ng-class="{
                                'has-error'
                                        :userForm.number.$invalid && (!userForm.number.$pristine || showError)}">
                            <label for="number" class="col-sm-2 control-label"><f:message key="number" /></label>
                            <div class="col-sm-10">
                                <input type="number" min="1" name="number" ng-model="data['personal-details'].address.number" class="form-control"/>
                                <p class="help-block" ng-show="userForm.number.$invalid && (!userForm.number.$pristine || showError)">Invalid value.</p>
                            </div>
                        </div>
                        <!-- **************************** city ************************** -->
                        <div class="form-group" ng-class="{
                                'has-error'
                                        : userForm.city.$invalid && (!userForm.city.$pristine || showError)}">
                            <label for="city" class="col-sm-2 control-label"><f:message key="city" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="city" ng-model="data['personal-details'].address.city" class="form-control" required/>
                                <p class="help-block" ng-show="userForm.city.$invalid && (!userForm.city.$pristine || showError)">Please, enter your city.</p>
                            </div>
                        </div>
                        <!-- **************************** postal code ************************** -->
                        <div class="form-group" ng-class="{
                                'has-error'
                                        : userForm['postal-code'].$invalid && (!userForm['postal-code'].$pristine || showError)}">
                            <label for="postal-code" class="col-sm-2 control-label"><f:message key="postal-code" /></label>
                            <div class="col-sm-10">
                                <input type="number" name="postal-code" ng-model="data['personal-details'].address['postal-code']" class="form-control" required/>
                                <p class="help-block" ng-show="userForm['postal-code'].$invalid && (!userForm['postal-code'].$pristine || showError)">Invalid value.</p>
                            </div>
                        </div>
                        <!-- **************************** state ************************** -->
                        <div class="form-group" ng-class="{
                                'has-error'
                                        : userForm.state.$invalid && (!userForm.state.$pristine || showError)}">
                            <label for="state" class="col-sm-2 control-label"><f:message key="state" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="state" ng-model="data['personal-details'].address.state" class="form-control"/>
                                <p class="help-block" ng-show="userForm.state.$invalid && (!userForm.state.$pristine || showError)">Please, enter your state.</p>
                            </div>
                        </div>
                        <!-- **************************** country ************************** -->
                        <div class="form-group" ng-class="{
                                'has-error'
                                        : userForm.country.$invalid && (!userForm.country.$pristine || showError)}">
                            <label for="country" class="col-sm-2 control-label"><f:message key="country" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="country" ng-model="data['personal-details'].address.country" class="form-control" required/>
                                <p class="help-block" ng-show="userForm.country.$invalid && (!userForm.country.$pristine || showError)">Please, enter your country.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- **************************** EDUCATION ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="education" /></div>
                    <div ng-repeat="edu in data.education" class="box"  ng-class="{
                            'has-error'
                                    : eduForm.$invalid && (!eduForm.$pristine || showError)}">
                        <ng-form name="eduForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label for="value">School {{$index + 1}}</label> 
                                    <input type="text" name="value" placeholder="School..." ng-model="edu['name-of-school']"  class="form-control" required />
                                </div>
                                <div class="col-sm-5">
                                    <label for="value">Type</label> 
                                    <input type="text" name="value" placeholder="School..." ng-model="edu['name-of-education']"  class="form-control" required />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="from">From</label> 
                                    <input type="number" min="1900" name="from" placeholder="From..." ng-model="edu.from"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="to">To</label> 
                                    <input type="number" min="1900" name="to" placeholder="To..." ng-model="edu.to" class="form-control" />
                                </div>
                                <div class="col-sm-5">
                                    <label for="note">Note</label> 
                                    <input type="text" name="note" placeholder="Note..." ng-model="edu.note" class="form-control" />
                                </div>
                                <a ng-click="deleteItem(data.education, $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" ng-show="eduForm.value.$invalid && (!eduForm.value.$pristine || showError)">School is required.</p>
                            <p class="help-block" ng-show="eduForm.from.$invalid && (!eduForm.from.$pristine || showError)">'From' year value invalid.</p>
                            <p class="help-block" ng-show="eduForm.to.$invalid && (!eduForm.to.$pristine || showError)">'To' year value invalid.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" ng-click="$event.preventDefault();
                                addEducation(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** EMPLOYMENT ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="employment" /></div>
                    <div ng-repeat="emp in data.employment" class="box"  ng-class="{
                            'has-error'
                                    : empForm.$invalid && (!empForm.$pristine || showError)}">
                        <ng-form name="empForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label for="company">Company {{$index + 1}}</label> 
                                    <input type="text" name="company" placeholder="company..." ng-model="emp.company"  class="form-control" required />

                                </div>
                                <div class="col-sm-5">
                                    <label for="position">Position</label> 
                                    <input type="text" name="position" placeholder="position..." ng-model="emp.position"  class="form-control" required />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="from">From</label> 
                                    <input type="number" min="1900" name="from" placeholder="From..." ng-model="emp.from"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="to">To</label> 
                                    <input type="number" min="1900" name="to" placeholder="To..." ng-model="emp.to" class="form-control" />
                                </div>
                                <div class="col-sm-5">
                                    <label for="note">Note</label> 
                                    <input type="text" name="note" placeholder="Note..." ng-model="emp.note" class="form-control" />
                                </div>
                                <a ng-click="deleteItem(data.employment, $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" ng-show="empForm.company.$invalid && (!empForm.company.$pristine || showError)">Company is required.</p>
                            <p class="help-block" ng-show="empForm.position.$invalid && (!empForm.position.$pristine || showError)">Position is required.</p>
                            <p class="help-block" ng-show="empForm.from.$invalid && (!empForm.from.$pristine || showError)">'From' year value invalid.</p>
                            <p class="help-block" ng-show="empForm.to.$invalid && (!empForm.to.$pristine || showError)">'To' year value invalid.</p>
                        </ng-form>
                    </div>         
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" ng-click="$event.preventDefault();
                                addEmployment(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** LANGUAGE SKILLS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="language-skills" /></div>
                    <div ng-repeat="skill in data['language-skills']"  class="box"  ng-class="{
                            'has-error'
                                    : langForm.$invalid && (!langForm.$pristine || showError)}">
                        <ng-form name="langForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="name">Language {{$index + 1}}</label> 
                                    <input type="text" name="name" placeholder="Language..." ng-model="skill.name"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="level">Level</label> 
                                    <select name="level" placeholder="From..." ng-model="skill.level"  class="form-control" required >
                                        <option>A1</option>
                                        <option>A2</option>
                                        <option>B1</option>
                                        <option>B2</option>
                                        <option>C1</option>
                                        <option>C2</option>
                                    </select>
                                </div>
                                <div class="col-sm-5">
                                    <label for="note">Note</label> 
                                    <input type="text" name="note" placeholder="Note..." ng-model="skill.note" class="form-control" />
                                </div>
                                <a ng-click="deleteItem(data['language-skills'], $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" ng-show="langForm.name.$invalid && (!langForm.name.$pristine || showError)">Language is required.</p>
                            <p class="help-block" ng-show="langForm.level.$invalid && (!langForm.level.$pristine || showError)">Level is required.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" ng-click="$event.preventDefault();
                                addLanguageSkill(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** COMPUTER SKILLS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="computer-skills" /></div>
                    <div ng-repeat="skill in data['computer-skills']" class="box"  ng-class="{
                            'has-error'
                                    : compForm.$invalid && (!compForm.$pristine || showError)}">
                        <ng-form name="compForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="name">Skill {{$index + 1}}</label> 
                                    <input type="text" name="name" placeholder="Skill..." ng-model="skill.name"  class="form-control" required />
                                </div>

                                <div class="col-sm-5 col-sm-offset-3">
                                    <label for="note">Note</label> 
                                    <input type="text" name="note" placeholder="Note..." ng-model="skill.note" class="form-control" />
                                </div>
                                <a ng-click="deleteItem(data['computer-skills'], $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" ng-show="compForm.name.$invalid && (!compForm.name.$pristine || showError)">Skill is required.</p>
                            <p class="help-block" ng-show="compForm.level.$invalid && (!compForm.level.$pristine || showError)">Level is required.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" ng-click="$event.preventDefault();
                                addComputerSkill(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** DRIVING SKILLS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="driving-license" /></div>
                    <div ng-repeat="skill in data['driving-licence']" class="box"  ng-class="{
                            'has-error'
                                    : driveForm.$invalid && (!driveForm.$pristine || showError)}">
                        <ng-form name="driveForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="name">Type {{$index + 1}}</label> 
                                    <input type="text" name="name" placeholder="Type..." ng-model="skill.name"  class="form-control" required />
                                </div>
                                <div class="col-sm-5 col-sm-offset-3">
                                    <label for="note">Note</label> 
                                    <input type="text" name="note" placeholder="Note..." ng-model="skill.note" class="form-control" />
                                </div>

                                <a ng-click="deleteItem(data['driving-licence'], $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" ng-show="driveForm.name.$invalid && (!driveForm.name.$pristine || showError)">Type is required.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" ng-click="$event.preventDefault();
                                addDrivingLicence(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** CERTIFICATES ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="certificates" /></div>
                    <div ng-repeat="cert in data.certificates" class="box"  ng-class="{
                            'has-error'
                                    : certForm.$invalid && (!certForm.$pristine || showError)}">
                        <ng-form name="certForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="name">Certificate {{$index + 1}}</label> 
                                    <input type="text" name="name" placeholder="Certificate..." ng-model="cert.name"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="year">Year</label> 
                                    <input type="number" min="1900" name="year" placeholder="Year..." ng-model="cert.year"  class="form-control" required />
                                </div>
                                <div class="col-sm-5">
                                    <label for="note">Note</label> 
                                    <input type="text" name="note" placeholder="Note..." ng-model="cert.note" class="form-control"  />
                                </div>
                                <a ng-click="deleteItem(data.certificates, $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" ng-show="certForm.year.$invalid && (!certForm.year.$pristine || showError)">Year is invalid.</p>
                            <p class="help-block" ng-show="certForm.name.$invalid && (!certForm.name.$pristine || showError)">Name is required.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" ng-click="$event.preventDefault();
                                addCertificate(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** CHARACTERISTICS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="characteristic" /></div>
                    <textarea ng-model="data.characteristic" class="form-control" ></textarea>
                </div>
                <!-- **************************** HOBBIES ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="hobbies" /></div>
                    <textarea ng-model="data.hobbies" class="form-control"></textarea>
                </div>
                <a href="#">
                    <button class="btn btn-primary col-sm-2 col-sm-offset-5">Save</button>
                </a>
            </form>
        </div>


        <div class="container">
            <pre>{{data| json : spacing}}</pre>
        </div>
        <%@ include file="/footer.html" %> 
    </body>
</html>