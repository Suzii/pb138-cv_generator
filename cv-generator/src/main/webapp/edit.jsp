<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en" data-ng-app="editApp">
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
    <body data-ng-controller="FormController" id="top">
        <!-- **************************** HEADING ************************** -->
        <div class="jumbotron">
            <div class="container">
                <h1><f:message key="heading" /></h1>
                <p><f:message key="intro-text" /></p>

                <div class="row">
                    <div class="col-sm-6">
                        <h2 data-ng-bind="data['personal-details']['given-names']"></h2>
                        <h3 data-ng-bind="data['personal-details'].surname"></h3>
                    </div>
                    <div class="col-sm-2 col-sm-offset-3">
                        <form action="${pageContext.request.contextPath}/edit/logout" method="GET">
                            <button class="btn btn-danger btn-block" >
                                <span class="glyphicon glyphicon-off " aria-hidden="true"></span> Log out
                            </button>
                        </form>

                        <a href="profile">
                            <span class="btn btn-primary btn-block" >
                                <span class="glyphicon glyphicon-user" aria-hidden="true"></span> Go to profile
                            </span>
                        </a>

                    </div>
                </div>
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
            <!-- page up button -->
            <a href="#" id="top-of-the-page" class="page-up">
                <span class="glyphicon glyphicon-triangle-top"></span> <br />
                <span> UP!</span>                
            </a>

            <!-- **************************** FORM ************************** -->
            <form action="${pageContext.request.contextPath}/edit/save" class="form-horizontal" method="POST" name="userForm"  id="cvForm" data-ng-submit="$event.preventDefault();
                                submit();" novalidate>
                <!-- **************************** PERSONAL DETAILS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="personal-details" /></div>
                    <div class="col-sm-6">
                        <!-- **************************** given names ************************** -->
                        <div class="form-group" data-ng-class="{'has-error': (!userForm['given-names'].$pristine || showErrors) && userForm['given-names'].$invalid}">
                            <label for="given-names" class="col-sm-2 control-label"><f:message key="given-names" />*</label>
                            <div class="col-sm-9">
                                <input type="text" name="given-names" id="given-names" data-ng-model="data['personal-details']['given-names']" class="form-control" required/>
                                <p data-ng-show="userForm['given-names'].$invalid && (!userForm['given-names'].$pristine || showError)" class="help-block">Given names are required.</p>
                            </div>
                        </div>
                        <!-- **************************** surname ************************** -->
                        <div class="form-group"  data-ng-class="{
                                'has-error': (!userForm.surname.$pristine || showError) && userForm.surname.$invalid}">
                            <label for="surname" class="col-sm-2 control-label"><f:message key="surname" />*</label>
                            <div class="col-sm-9">
                                <input type="text" name="surname" id="surname" data-ng-model="data['personal-details'].surname" class="form-control" required />
                                <p data-ng-show="userForm.surname.$invalid && (!userForm.surname.$pristine || showError)" class="help-block">Surname is required.</p>
                            </div>
                        </div>
                        <!-- **************************** date of birth ************************** -->
                        <div class="form-group"  data-ng-class="{'has-error': userForm.dateOfBirth.$invalid && (!userForm.dateOfBirth.$pristine || showError)}">
                            <label for="dateOfBirth" class="col-sm-2 control-label"><f:message key="dateOfBirth" />*</label>
                            <div class="col-sm-9" data-ng-controller="DateController">
                                <ng-form name="dateOfBirth" class="form-inline">
                                    <div class="form-group">
                                        <select class="form-control" id="dateOfBirth" placeholder="Day" data-ng-model="data['personal-details']['date-of-birth'].day" data-ng-options="d for d in days" required>
                                            <option value="" selected="Day">Day</option>
                                        </select>
                                        <select class="form-control" data-ng-model="data['personal-details']['date-of-birth'].month" data-ng-options="m for m in months" required>
                                            <option value="">Month</option>
                                        </select>
                                        <select class="form-control" data-ng-model="data['personal-details']['date-of-birth'].year" data-ng-options="y for y in years" required>
                                            <option value="" selected="">Year</option>
                                        </select>
                                        <p data-ng-show="userForm.dateOfBirth.$invalid && (!userForm.dateOfBirth.$pristine || showError)" class="help-block">Date of birth is required.</p>
                                    </div>
                                </ng-form>
                            </div>
                        </div>
                        <!-- **************************** phones ************************** -->
                        <div data-ng-repeat="phone in data['personal-details'].phones" class="form-group"  data-ng-class="{
                                'has-error'
                                        : phoneForm.phone.$invalid && (!phoneForm.phone.$pristine || showError)}">
                            <ng-form name="phoneForm">
                                <label for="phone-{{$index}}" class="col-sm-2 control-label"><f:message key="phone" /> {{$index + 1}}*</label>
                                <div class="col-sm-9">
                                    <input type="text" name="phone" id="phone-{{$index}}" data-ng-model="phone.value" class="form-control" required/>
                                    <p data-ng-show="phoneForm.phone.$invalid && (!phoneForm.phone.$pristine || showError)" class="help-block">Required.</p>
                                </div>
                                <a data-ng-click="$event.preventDefault();
                                                    deleteItem(data['personal-details'].phones, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <label data-ng-show="data['personal-details'].phones.length == 0" class="col-sm-6">Phones</label>
                        <button class="btn add btn-primary btn-sm col-sm-offset-9 col-sm-2" data-ng-click="$event.preventDefault();
                                            addPhone(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                        <!-- **************************** emails ************************** -->
                        <label data-ng-show="data['personal-details'].emails.length == 0" class="col-sm-6">Emails</label>
                        <div data-ng-repeat="email in data['personal-details'].emails" class="form-group"  data-ng-class="{
                                'has-error'
                                        : emailForm.email.$invalid && (!emailForm.email.$pristine || showError)}">
                            <ng-form name="emailForm">
                                <label for="email-{{$index}}" class="col-sm-2 control-label"><f:message key="email" /> {{$index + 1}}*</label>
                                <div class="col-sm-9">
                                    <div class="input-group">
                                        <div class="input-group-addon">@</div>
                                        <input type="email" name="email" id="email-{{$index}}" data-ng-model="email.value" class="form-control" required/>
                                    </div>
                                    <p class="help-block" data-ng-show="emailForm.email.$invalid && (!emailForm.email.$pristine || showError)">Please, enter a valid email</p>
                                </div>
                                <a data-ng-click="$event.preventDefault();
                                                    deleteItem(data['personal-details'].emails, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <button class="btn add btn-primary btn-sm col-sm-offset-9 col-sm-2" data-ng-click="$event.preventDefault();
                                            addEmail(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                        <!-- **************************** social ************************** -->
                        <label data-ng-show="data['personal-details'].social.length == 0" class="col-sm-6">Social</label>
                        <div data-ng-repeat="social in data['personal-details'].social" class="form-group"  data-ng-class="{
                                'has-error'
                                        : socialForm.social.$invalid && (!socialForm.social.$pristine || showError)}">
                            <ng-form name="socialForm">
                                <label for="social-{{$index}}" class="col-sm-2 control-label"><f:message key="social" /> {{$index + 1}}*</label>
                                <div class="col-sm-9">
                                    <div class="input-group">
                                        <div class="input-group-addon">http://</div>
                                        <input type="text" name="social" id="social-{{$index}}" data-ng-model="social.value" class="form-control" required/>
                                    </div>
                                    <p class="help-block" data-ng-show="socialForm.social.$invalid && (!socialForm.social.$pristine || showError)">Please, enter a valid link</p>
                                </div>
                                <a data-ng-click="$event.preventDefault();
                                                    deleteItem(data['personal-details'].social, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <button class="btn add btn-primary btn-sm col-sm-offset-9 col-sm-2" data-ng-click="$event.preventDefault();
                                            addSocial(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                    </div>
                    <div class="col-sm-6">
                        <!-- **************************** street ************************** -->
                        <div class="form-group" data-ng-class="{
                                'has-error'
                                        : userForm.street.$invalid && (!userForm.street.$pristine || showError)}">
                            <label for="street" class="col-sm-2 control-label"><f:message key="street" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="street" id="street" data-ng-model="data['personal-details'].address.street" class="form-control" required/>
                                <p class="help-block" data-ng-show="userForm.street.$invalid && (!userForm.street.$pristine || showError)">Please, enter your street</p>
                            </div>
                        </div>
                        <!-- **************************** number ************************** -->
                        <div class="form-group" data-ng-class="{
                                'has-error'
                                        :userForm.number.$invalid && (!userForm.number.$pristine || showError)}">
                            <label for="number" class="col-sm-2 control-label"><f:message key="number" /></label>
                            <div class="col-sm-10">
                                <input type="number" min="1" name="number" id="number" data-ng-model="data['personal-details'].address.number" class="form-control"/>
                                <p class="help-block" data-ng-show="userForm.number.$invalid && (!userForm.number.$pristine || showError)">Invalid value.</p>
                            </div>
                        </div>
                        <!-- **************************** city ************************** -->
                        <div class="form-group" data-ng-class="{
                                'has-error'
                                        : userForm.city.$invalid && (!userForm.city.$pristine || showError)}">
                            <label for="city" class="col-sm-2 control-label"><f:message key="city" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="city" id="city" data-ng-model="data['personal-details'].address.city" class="form-control" required/>
                                <p class="help-block" data-ng-show="userForm.city.$invalid && (!userForm.city.$pristine || showError)">Please, enter your city.</p>
                            </div>
                        </div>
                        <!-- **************************** postal code ************************** -->
                        <div class="form-group" data-ng-class="{
                                'has-error'
                                        : userForm['postal-code'].$invalid && (!userForm['postal-code'].$pristine || showError)}">
                            <label for="postal-code" class="col-sm-2 control-label"><f:message key="postal-code" /></label>
                            <div class="col-sm-10">
                                <input type="number" name="postal-code" id="postal-code" data-ng-model="data['personal-details'].address['postal-code']" class="form-control" required/>
                                <p class="help-block" data-ng-show="userForm['postal-code'].$invalid && (!userForm['postal-code'].$pristine || showError)">Invalid value.</p>
                            </div>
                        </div>
                        <!-- **************************** state ************************** -->
                        <div class="form-group" data-ng-class="{
                                'has-error'
                                        : userForm.state.$invalid && (!userForm.state.$pristine || showError)}">
                            <label for="state" class="col-sm-2 control-label"><f:message key="state" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="state" id="state" data-ng-model="data['personal-details'].address.state" class="form-control"/>
                                <p class="help-block" data-ng-show="userForm.state.$invalid && (!userForm.state.$pristine || showError)">Please, enter your state.</p>
                            </div>
                        </div>
                        <!-- **************************** country ************************** -->
                        <div class="form-group" data-ng-class="{
                                'has-error'
                                        : userForm.country.$invalid && (!userForm.country.$pristine || showError)}">
                            <label for="country" class="col-sm-2 control-label"><f:message key="country" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="country" id="country" data-ng-model="data['personal-details'].address.country" class="form-control" required/>
                                <p class="help-block" data-ng-show="userForm.country.$invalid && (!userForm.country.$pristine || showError)">Please, enter your country.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- **************************** EDUCATION ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="education" /></div>
                    <div data-ng-repeat="edu in data.education" class="box"  data-ng-class="{
                            'has-error'
                                    : eduForm.$invalid && (!eduForm.$pristine || showError)}">
                        <ng-form name="eduForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label for="edu-name-{{$index}}">School {{$index + 1}}</label> 
                                    <input type="text" name="edu-name-{{$index}}" id="edu-name-{{$index}}" placeholder="School..." data-ng-model="edu['name-of-school']"  class="form-control" required />
                                </div>
                                <div class="col-sm-5">
                                    <label for="edu-type-{{$index}}">Type</label> 
                                    <input type="text" name="edu-type-{{$index}}" id="edu-type-{{$index}}" placeholder="School..." data-ng-model="edu['name-of-education']"  class="form-control" required />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="edu-from-{{$index}}">From</label> 
                                    <input type="number" min="1900" name="edu-from-{{$index}}" id="edu-from-{{$index}}" placeholder="From..." data-ng-model="edu.from"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="edu-to-{{$index}}">To</label> 
                                    <input type="number" min="1900" name="edu-to-{{$index}}" id="edu-to-{{$index}}" placeholder="To..." data-ng-model="edu.to" class="form-control" />
                                </div>
                                <div class="col-sm-5">
                                    <label for="edu-note-{{$index}}">Note</label> 
                                    <input type="text" name="edu-note-{{$index}}" id="edu-note-{{$index}}" placeholder="Note..." data-ng-model="edu.note" class="form-control" />
                                </div>
                                <a data-ng-click="deleteItem(data.education, $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" data-ng-show="eduForm['edu-name-{{$index}}'].$invalid && (!eduForm['edu-name-{{$index}}'].$pristine || showError)">School is required.</p>
                            <p class="help-block" data-ng-show="eduForm['edu-type-{{$index}}'].$invalid && (!eduForm['edu-type-{{$index}}'].$pristine || showError)">Type of school is required.</p>
                            <p class="help-block" data-ng-show="eduForm['edu-from-{{$index}}'].$invalid && (!eduForm['edu-from-{{$index}}'].$pristine || showError)">'From' year value invalid.</p>
                            <p class="help-block" data-ng-show="eduForm['edu-to-{{$index}}'].$invalid && (!eduForm['edu-to-{{$index}}'].$pristine || showError)">'To' year value invalid.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" data-ng-click="$event.preventDefault();
                                                                                addEducation(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** EMPLOYMENT ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="employment" /></div>
                    <div data-ng-repeat="emp in data.employment" class="box"  data-ng-class="{
                            'has-error'
                                    : empForm.$invalid && (!empForm.$pristine || showError)}">
                        <ng-form name="empForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-6">
                                    <label for="emp-company-{{$index}}">Company {{$index + 1}}</label> 
                                    <input type="text" name="emp-company-{{$index}}" id="emp-company-{{$index}}" placeholder="Company..." data-ng-model="emp.company"  class="form-control" required />

                                </div>
                                <div class="col-sm-5">
                                    <label for="emp-position-{{$index}}">Position</label> 
                                    <input type="text" name="emp-position-{{$index}}" id="emp-position-{{$index}}" placeholder="Position..." data-ng-model="emp.position"  class="form-control" required />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="emp-from-{{$index}}">From</label> 
                                    <input type="number" min="1900" name="emp-from-{{$index}}" id="emp-from-{{$index}}" placeholder="From..." data-ng-model="emp.from"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="emp-to-{{$index}}">To</label> 
                                    <input type="number" min="1900" name="emp-to-{{$index}}" id="emp-to-{{$index}}" placeholder="To..." data-ng-model="emp.to" class="form-control" />
                                </div>
                                <div class="col-sm-5">
                                    <label for="emp-note-{{$index}}">Note</label> 
                                    <input type="text" name="emp-note-{{$index}}" id="emp-note-{{$index}}" placeholder="Note..." data-ng-model="emp.note" class="form-control" />
                                </div>
                                <a data-ng-click="deleteItem(data.employment, $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" data-ng-show="empForm['emp-position-{{$index}}'].$invalid && (!empForm['emp-position-{{$index}}'].$pristine || showError)">Position is required.</p>
                            <p class="help-block" data-ng-show="empForm['emp-company-{{$index}}'].$invalid && (!empForm['emp-company-{{$index}}'].$pristine || showError)">Company is required.</p>
                            <p class="help-block" data-ng-show="empForm['emp-from-{{$index}}'].$invalid && (!empForm['emp-from-{{$index}}'].$pristine || showError)">'From' year value invalid.</p>
                            <p class="help-block" data-ng-show="empForm['emp-to-{{$index}}'].$invalid && (!empForm['emp-to-{{$index}}'].$pristine || showError)">'To' year value invalid.</p>
                        </ng-form>
                    </div>         
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" data-ng-click="$event.preventDefault();
                                                                                addEmployment(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** LANGUAGE SKILLS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="language-skills" /></div>
                    <div data-ng-repeat="skill in data['language-skills']"  class="box"  data-ng-class="{
                            'has-error'
                                    : langForm.$invalid && (!langForm.$pristine || showError)}">
                        <ng-form name="langForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="lang-name-{{$index}}">Language {{$index + 1}}</label> 
                                    <input type="text" name="lang-name-{{$index}}" id="lang-name-{{$index}}" placeholder="Language..." data-ng-model="skill.name"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="lang-level-{{$index}}">Level</label> 
                                    <select name="lang-level-{{$index}}" id="lang-level-{{$index}}" placeholder="From..." data-ng-model="skill.level"  class="form-control" required >
                                        <option value="">Level...</option>
                                        <option>A1</option>
                                        <option>A2</option>
                                        <option>B1</option>
                                        <option>B2</option>
                                        <option>C1</option>
                                        <option>C2</option>
                                    </select>
                                </div>
                                <div class="col-sm-5">
                                    <label for="lang-note-{{$index}}">Note</label> 
                                    <input type="text" name="lang-note-{{$index}}" id="lang-note-{{$index}}" placeholder="Note..." data-ng-model="skill.note" class="form-control" />
                                </div>
                                <a data-ng-click="deleteItem(data['language-skills'], $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" data-ng-show="langForm['lang-name-{{$index}}'].$invalid && (!langForm['lang-name-{{$index}}'].$pristine || showError)">Language is required.</p>
                            <p class="help-block" data-ng-show="langForm['lang-level-{{$index}}'].$invalid && (!langForm['lang-level-{{$index}}'].$pristine || showError)">Level is required.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" data-ng-click="$event.preventDefault();
                                                                                addLanguageSkill(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** COMPUTER SKILLS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="computer-skills" /></div>
                    <div data-ng-repeat="skill in data['computer-skills']" class="box"  data-ng-class="{
                            'has-error'
                                    : compForm.$invalid && (!compForm.$pristine || showError)}">
                        <ng-form name="compForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="pc-name-{{$index}}">Skill {{$index + 1}}</label> 
                                    <input type="text" name="pc-name-{{$index}}" id="pc-name-{{$index}}" placeholder="Skill..." data-ng-model="skill.name"  class="form-control" required />
                                </div>

                                <div class="col-sm-5 col-sm-offset-3">
                                    <label for="pc-note-{{$index}}">Note</label> 
                                    <input type="text" name="pc-note-{{$index}}" id="pc-note-{{$index}}" placeholder="Note..." data-ng-model="skill.note" class="form-control" />
                                </div>
                                <a data-ng-click="deleteItem(data['computer-skills'], $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" data-ng-show="compForm['pc-name-{{$index}}'].$invalid && (!compForm['pc-name-{{$index}}'].$pristine || showError)">Skill is required.</p>
                            </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" data-ng-click="$event.preventDefault();
                                                                                addComputerSkill(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** DRIVING SKILLS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="driving-license" /></div>
                    <div data-ng-repeat="skill in data['driving-licence']" class="box"  data-ng-class="{
                            'has-error'
                                    : driveForm.$invalid && (!driveForm.$pristine || showError)}">
                        <ng-form name="driveForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="drive-name-{{$index}}">Type {{$index + 1}}</label> 
                                    <input type="text" name="drive-name-{{$index}}" id="drive-name-{{$index}}" placeholder="Type..." data-ng-model="skill.name"  class="form-control" required />
                                </div>
                                <div class="col-sm-5 col-sm-offset-3">
                                    <label for="drive-note-{{$index}}">Note</label> 
                                    <input type="text" name="drive-note-{{$index}}" id="drive-note-{{$index}}" placeholder="Note..." data-ng-model="skill.note" class="form-control" />
                                </div>

                                <a data-ng-click="deleteItem(data['driving-licence'], $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" data-ng-show="driveForm['drive-name-{{$index}}'].$invalid && (!driveForm['drive-name-{{$index}}'].$pristine || showError)">Type is required.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" data-ng-click="$event.preventDefault();
                                                                                addDrivingLicence(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** CERTIFICATES ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="certificates" /></div>
                    <div data-ng-repeat="cert in data.certificates" class="box"  data-ng-class="{
                            'has-error'
                                    : certForm.$invalid && (!certForm.$pristine || showError)}">
                        <ng-form name="certForm" class="form-group">
                            <div class="row">
                                <div class="col-sm-3">
                                    <label for="cert-name-{{$index}}">Certificate {{$index + 1}}</label> 
                                    <input type="text" name="cert-name-{{$index}}" id="cert-name-{{$index}}" placeholder="Certificate..." data-ng-model="cert.name"  class="form-control" required />
                                </div>
                                <div class="col-sm-3">
                                    <label for="cert-year-{{$index}}">Year</label> 
                                    <input type="number" min="1900" name="cert-year-{{$index}}" id="cert-year-{{$index}}" placeholder="Year..." data-ng-model="cert.year"  class="form-control" required />
                                </div>
                                <div class="col-sm-5">
                                    <label for="cert-note-{{$index}}">Note</label> 
                                    <input type="text" name="cert-note-{{$index}}" id="cert-note-{{$index}}" placeholder="Note..." data-ng-model="cert.note" class="form-control"  />
                                </div>
                                <a data-ng-click="deleteItem(data.certificates, $index)" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </div>
                            <p class="help-block" data-ng-show="certForm['cert-name-{{$index}}'].$invalid && (!certForm['cert-name-{{$index}}'].$pristine || showError)">Name is required.</p>
                            <p class="help-block" data-ng-show="certForm['cert-year-{{$index}}'].$invalid && (!certForm['cert-year-{{$index}}'].$pristine || showError)">Year is invalid.</p>
                        </ng-form>
                    </div>
                    <button class="btn btn-primary btn-sm col-sm-1 col-sm-offset-10" data-ng-click="$event.preventDefault();
                                                                                addCertificate(data);"> 
                        <i class="glyphicon glyphicon-plus"></i> Add
                    </button>
                </div>
                <!-- **************************** CHARACTERISTICS ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="characteristic" /></div>
                    <textarea data-ng-model="data.characteristic" class="form-control" ></textarea>
                </div>
                <!-- **************************** HOBBIES ************************** -->
                <div class="row">
                    <div class="well well-sm strong"><f:message key="hobbies" /></div>
                    <textarea data-ng-model="data.hobbies" class="form-control"></textarea>
                </div>
                <button class="btn btn-primary col-sm-2 col-sm-offset-5">Save</button>
            </form>
        </div>


        <div class="container">
            <pre>{{data| json : spacing}}</pre>
        </div>
        <%@ include file="/footer.jsp" %> 
    </body>
</html>