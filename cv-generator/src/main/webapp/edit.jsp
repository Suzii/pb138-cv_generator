<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="texts" />
<html ng-app="editApp">
    <head>
        <title><f:message key="title" /></title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="css/main.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
        <script src="js/edit.js"></script>

    </head>
    <body ng-controller="FormController">
        <!-- **************************** HEADING ************************** -->
        <div class="jumbotron">
            <div class="container">
                <h1><f:message key="heading" /></h1>
                <p><f:message key="intro-text" /></p>
            </div>
        </div>
        <div class="container">
            <c:if test="${not empty chyba}">
                <div class="alert alert-danger" role="alert">
                    <c:out value="${chyba}"/>
                </div>
            </c:if>


            <!-- **************************** FORM ************************** -->
            <form action="${pageContext.request.contextPath}/edit/save" class="form-horizontal" method="POST" name="userForm"  id="cvForm" ng-submit="$event.preventDefault();
                                submit();" novalidate>
                <!-- **************************** PERSONAL DETAILS ************************** -->
                <div class="well well-sm strong"><f:message key="personal-details" /></div>
                <div class="row">
                    <div class="col-sm-6">
                        <!-- **************************** given names ************************** -->
                        <div class="form-group" ng-class="{'has-error': userForm['given-names'].$invalid && !userForm['given-names'].$pristine}">
                            <label for="given-names" class="col-sm-2 control-label"><f:message key="given-names" /></label>
                            <div class="col-sm-9">
                                <input type="text" name="given-names" ng-model="data['personal-details']['given-names']" class="form-control" required/>
                                <p ng-show="userForm['given-names'].$invalid && !userForm['given-names'].$pristine" class="help-block">Given names are required.</p>
                            </div>
                        </div>
                        <!-- **************************** surname ************************** -->
                        <div class="form-group"  ng-class="{'has-error': userForm.surname.$invalid && !userForm.surname.$pristine}">
                            <label for="surname" class="col-sm-2 control-label"><f:message key="surname" /></label>
                            <div class="col-sm-9">
                                <input type="text" name="surname" ng-model="data['personal-details'].surname" class="form-control" required />
                                <p ng-show="userForm.surname.$invalid && !userForm.surname.$pristine" class="help-block">Surname is required.</p>
                            </div>
                        </div>
                        <!-- **************************** phones ************************** -->
                        <div ng-repeat="phone in data['personal-details'].phones" class="form-group"  ng-class="{'has-error': phoneForm.phone.$invalid && !phoneForm.phone.$pristine}">
                            <ng-form name="phoneForm">
                                <label for="phone" class="col-sm-2 control-label"><f:message key="phone" /> {{$index + 1}}</label>
                                <div class="col-sm-9">
                                    <input type="text" name="phone" ng-model="phone.value" class="form-control" required/>
                                    <p ng-show="phoneForm.phone.$invalid && !phoneForm.phone.$pristine" class="help-block">Required.</p>
                                </div>
                                <a ng-click="$event.preventDefault();
                                                    deleteItem(data['personal-details'].phones, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <button class="btn btn-default btn-sm col-sm-offset-9 col-sm-2" ng-click="$event.preventDefault();
                                            addPhone(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                        <!-- **************************** emails ************************** -->
                        <div ng-repeat="email in data['personal-details'].emails" class="form-group"  ng-class="{'has-error': emailForm.email.$invalid && !emailForm.email.$pristine}">
                            <ng-form name="emailForm">
                                <label for="email" class="col-sm-2 control-label"><f:message key="email" /> {{$index + 1}}</label>
                                <div class="col-sm-9">
                                    <input type="email" name="email" ng-model="email.value" class="form-control" required/>
                                    <p class="help-block" ng-show="emailForm.email.$invalid && !emailForm.email.$pristine">Please, enter a valid email</p>
                                </div>
                                <a ng-click="$event.preventDefault();
                                                    deleteItem(data['personal-details'].emails, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <button class="btn btn-default btn-sm col-sm-offset-9 col-sm-2" ng-click="$event.preventDefault();
                                            addEmail(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                        <!-- **************************** social ************************** -->
                        <div ng-repeat="social in data['personal-details'].social" class="form-group"  ng-class="{'has-error': socialForm.social.$invalid && !socialForm.social.$pristine}">
                            <ng-form name="socialForm">
                                <label for="social" class="col-sm-2 control-label"><f:message key="social" /> {{$index + 1}}</label>
                                <div class="col-sm-9">
                                    <input type="text" name="social" ng-model="social.value" class="form-control" required/>
                                    <p class="help-block" ng-show="socialForm.social.$invalid && !socialForm.social.$pristine">Please, enter a valid link</p>
                                </div>
                                <a ng-click="$event.preventDefault();
                                                    deleteItem(data['personal-details'].emails, $index);" class="col-sm-1">
                                    <i class="glyphicon glyphicon-remove"></i>
                                </a>
                            </ng-form>
                        </div>
                        <button class="btn btn-default btn-sm col-sm-offset-9 col-sm-2" ng-click="$event.preventDefault();
                                            addSocial(data['personal-details']);"> 
                            <i class="glyphicon glyphicon-plus"></i> Add
                        </button>
                    </div>
                    <div class="col-sm-6">
                        <!-- **************************** street ************************** -->
                        <div class="form-group" ng-class="{'has-error': userForm.street.$invalid && !userForm.street.$pristine}">
                            <label for="street" class="col-sm-2 control-label"><f:message key="street" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="street" ng-model="data['personal-details'].address.street" class="form-control" required/>
                                <p class="help-block" ng-show="userForm.street.$invalid && !userForm.street.$pristine">Please, enter your street</p>
                            </div>
                        </div>
                        <!-- **************************** number ************************** -->
                        <div class="form-group" ng-class="{'has-error': userForm.number.$invalid && !userForm.number.$pristine}">
                            <label for="number" class="col-sm-2 control-label"><f:message key="number" /></label>
                            <div class="col-sm-10">
                                <input type="number" min="1" name="number" ng-model="data['personal-details'].address.number" class="form-control"/>
                                <p class="help-block" ng-show="userForm.number.$invalid && !userForm.number.$pristine">Invalid value.</p>
                            </div>
                        </div>
                        <!-- **************************** city ************************** -->
                        <div class="form-group" ng-class="{'has-error': userForm.city.$invalid && !userForm.city.$pristine}">
                            <label for="city" class="col-sm-2 control-label"><f:message key="city" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="city" ng-model="data['personal-details'].address.city" class="form-control" required/>
                                <p class="help-block" ng-show="userForm.city.$invalid && !userForm.city.$pristine">Please, enter your city.</p>
                            </div>
                        </div>
                        <!-- **************************** post code ************************** -->
                        <div class="form-group" ng-class="{'has-error': userForm['post-code'].$invalid &&  !userForm['post-code'].$pristine}">
                            <label for="post-code" class="col-sm-2 control-label"><f:message key="post-code" /></label>
                            <div class="col-sm-10">
                                <input type="number" name="post-code" ng-model="data['personal-details'].address['post-code']" class="form-control"/>
                                <p class="help-block" ng-show="userForm['post-code'].$invalid && !userForm['post-code'].$pristine">Invalid value.</p>
                            </div>
                        </div>
                        <!-- **************************** state ************************** -->
                        <div class="form-group" ng-class="{'has-error': userForm.state.$invalid && !userForm.state.$pristine}">
                            <label for="state" class="col-sm-2 control-label"><f:message key="state" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="state" ng-model="data['personal-details'].address.state" class="form-control"/>
                                <p class="help-block" ng-show="userForm.state.$invalid && !userForm.state.$pristine">Please, enter your state.</p>
                            </div>
                        </div>
                        <!-- **************************** country ************************** -->
                        <div class="form-group" ng-class="{'has-error': userForm.country.$invalid && !userForm.country.$pristine}">
                            <label for="country" class="col-sm-2 control-label"><f:message key="country" /></label>
                            <div class="col-sm-10">
                                <input type="text" name="country" ng-model="data['personal-details'].address.country" class="form-control" required/>
                                <p class="help-block" ng-show="userForm.country.$invalid && !userForm.country.$pristine">Please, enter your country.</p>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- **************************** EDUCATION ************************** -->
                <div class="well well-sm strong"><f:message key="education" /></div>
                <div ng-repeat="edu in data.education" class="form-group"  ng-class="{'has-error': eduForm.$invalid && !eduForm.$pristine}">
                    <ng-form name="eduForm" class="form-inline">
                        <label for="value">School {{$index + 1}}</label> 
                        <input type="text" name="value" placeholder="School..." ng-model="edu.value"  class="form-control" required />
                        <label for="from">From</label> 
                        <input type="number" min="1900" name="from" placeholder="From..." ng-model="edu.from"  class="form-control" required />
                        <label for="to">To</label> 
                        <input type="number" min="1900" name="to" placeholder="To..." ng-model="edu.to" class="form-control" />
                        <a ng-click="deleteItem(data.education, $index)">
                            <i class="glyphicon glyphicon-remove"></i>
                        </a>
                        <p class="help-block" ng-show="eduForm.value.$invalid && !eduForm.value.$pristine">School is required.</p>
                        <p class="help-block" ng-show="eduForm.from.$invalid && !eduForm.from.$pristine">'From' year value invalid.</p>
                        <p class="help-block" ng-show="eduForm.to.$invalid && !eduForm.to.$pristine">'To' year value invalid.</p>
                    </ng-form>
                </div>
                <button class="btn btn-default btn-sm" ng-click="$event.preventDefault();
                                    addEducation(data);"> 
                    <i class="glyphicon glyphicon-plus"></i> Add
                </button>
                <!-- **************************** EMPLOYMENT ************************** -->
                <div class="well well-sm strong"><f:message key="employment" /></div>
                <div ng-repeat="emp in data.employment" class="form-group"  ng-class="{'has-error': empForm.$invalid && !empForm.$pristine}">
                    <ng-form name="empForm" class="form-inline">
                        <label for="company">Company {{$index + 1}}</label> 
                        <input type="text" name="company" placeholder="company..." ng-model="emp.company"  class="form-control" required />
                        <label for="position">Position</label> 
                        <input type="text" name="position" placeholder="position..." ng-model="emp.position"  class="form-control" required />
                        <label for="from">From</label> 
                        <input type="number" min="1900" name="from" placeholder="From..." ng-model="emp.from"  class="form-control" required />
                        <label for="to">To</label> 
                        <input type="number" min="1900" name="to" placeholder="To..." ng-model="emp.to" class="form-control" />
                        <label for="note">Note</label> 
                        <input type="text" name="note" placeholder="Note..." ng-model="emp.note" class="form-control" />
                        <a ng-click="deleteItem(data.employment, $index)">
                            <i class="glyphicon glyphicon-remove"></i>
                        </a>
                        <p class="help-block" ng-show="empForm.company.$invalid && !empForm.company.$pristine">Company is required.</p>
                        <p class="help-block" ng-show="empForm.position.$invalid && !empForm.position.$pristine">Position is required.</p>
                        <p class="help-block" ng-show="empForm.from.$invalid && !empForm.from.$pristine">'From' year value invalid.</p>
                        <p class="help-block" ng-show="empForm.to.$invalid && !empForm.to.$pristine">'To' year value invalid.</p>
                    </ng-form>
                </div>                            
                <button class="btn btn-default btn-sm" ng-click="$event.preventDefault();
                                    addEmployment(data);"> 
                    <i class="glyphicon glyphicon-plus"></i> Add
                </button>
                <!-- **************************** LANGUAGE SKILLS ************************** -->
                <div class="well well-sm strong"><f:message key="language-skills" /></div>
                <div ng-repeat="skill in data['language-skills']"  class="form-group"  ng-class="{'has-error': langForm.$invalid && !langForm.$pristine}">
                    <ng-form name="langForm" class="form-inline">
                        <label for="name">Language {{$index + 1}}</label> 
                        <input type="text" name="name" placeholder="Language..." ng-model="skill.name"  class="form-control" required />
                        <label for="level">Level</label> 
                        <select type="number" name="level" placeholder="From..." ng-model="skill.level"  class="form-control" required >
                            <option>Beginner</option>
                            <option>Intermediate</option>
                            <option>Advanced</option>
                        </select>
                        <label for="note">Note</label> 
                        <input type="text" name="note" placeholder="Note..." ng-model="skill.note" class="form-control" />
                        <a ng-click="deleteItem(data['language-skills'], $index)">
                            <i class="glyphicon glyphicon-remove"></i>
                        </a>
                        <p class="help-block" ng-show="langForm.name.$invalid && !langForm.name.$pristine">Language is required.</p>
                        <p class="help-block" ng-show="langForm.level.$invalid && !langForm.level.$pristine">Level is required.</p>
                    </ng-form>
                </div>                            
                <button class="btn btn-default btn-sm" ng-click="$event.preventDefault();
                                    addLanguageSkill(data);"> 
                    <i class="glyphicon glyphicon-plus"></i> Add
                </button>
                <!-- **************************** COMPUTER SKILLS ************************** -->
                <div class="well well-sm strong"><f:message key="computer-skills" /></div>
                <div ng-repeat="skill in data['computer-skills']" class="form-group"  ng-class="{'has-error': compForm.$invalid && !compForm.$pristine}">
                    <ng-form name="compForm" class="form-inline">
                        <label for="name">Skill {{$index + 1}}</label> 
                        <input type="text" name="name" placeholder="Skill..." ng-model="skill.name"  class="form-control" required />
                        <label for="level">Level</label> 
                        <select type="number" name="from" placeholder="From..." ng-model="skill.level"  class="form-control" required >
                            <option>Beginner</option>
                            <option>Intermediate</option>
                            <option>Advanced</option>
                        </select>
                        <label for="note">Note</label> 
                        <input type="text" name="note" placeholder="Note..." ng-model="skill.note" class="form-control" required />
                        <a ng-click="deleteItem(data['computer-skills'], $index)">
                            <i class="glyphicon glyphicon-remove"></i>
                        </a>
                        <p class="help-block" ng-show="compForm.name.$invalid && !compForm.name.$pristine">Skill is required.</p>
                        <p class="help-block" ng-show="compForm.level.$invalid && !compForm.level.$pristine">Level is required.</p>
                    </ng-form>
                </div>
                <button class="btn btn-default btn-sm" ng-click="$event.preventDefault();
                                    addComputerSkill(data);"> 
                    <i class="glyphicon glyphicon-plus"></i> Add
                </button>
                <!-- **************************** DRIVING SKILLS ************************** -->
                <div class="well well-sm strong"><f:message key="driving-license" /></div>
                <div ng-repeat="skill in data['driving-licence']" class="form-group"  ng-class="{'has-error': driveForm.$invalid && !driveForm.$pristine}">
                    <ng-form name="driveForm" class="form-inline">
                        <label for="name">Type {{$index + 1}}</label> 
                        <input type="text" name="name" placeholder="Type..." ng-model="skill.name"  class="form-control" />
                        <label for="note">Note</label> 
                        <input type="text" name="note" placeholder="Note..." ng-model="skill.note" class="form-control" />
                        <a ng-click="deleteItem(data['driving-licence'], $index)">
                            <i class="glyphicon glyphicon-remove"></i>
                        </a>
                        <p class="help-block" ng-show="driveForm.name.$invalid && !driveForm.name.$pristine">Skill is required.</p>
                    </ng-form>
                </div>
                <button class="btn btn-default btn-sm" ng-click="$event.preventDefault();
                                    addDrivingLicence(data);"> 
                    <i class="glyphicon glyphicon-plus"></i> Add
                </button>
                <!-- **************************** CERTIFICATES ************************** -->
                <div class="well well-sm strong"><f:message key="certificates" /></div>
                <div ng-repeat="cert in data.certificates"']" class="form-group"  ng-class="{'has-error': certForm.$invalid && !certForm.$pristine}">
                    <ng-form name="certForm" class="form-inline">
                        <label for="name">Certificate {{$index + 1}}</label> 
                        <input type="text" name="name" placeholder="Skill..." ng-model="cert.name"  class="form-control"  />
                        <label for="year">Year</label> 
                        <input type="number" min="1900" name="year" placeholder="Year..." ng-model="cert.year"  class="form-control"  />
                        <label for="note">Note</label> 
                        <input type="text" name="note" placeholder="Note..." ng-model="cert.note" class="form-control"  />
                        <a ng-click="deleteItem(data.certificates, $index)">
                            <i class="glyphicon glyphicon-remove"></i>
                        </a>
                        <p class="help-block" ng-show="certForm.year.$invalid && !certForm.year.$pristine">Year is invalid.</p>
                    </ng-form>
                </div>
                <button class="btn btn-default btn-sm" ng-click="$event.preventDefault();
                                    addCertificate(data);"> 
                    <i class="glyphicon glyphicon-plus"></i> Add
                </button>
                <!-- **************************** CHARACTERISTICS ************************** -->
                <div class="well well-sm strong"><f:message key="characteristic" /></div>
                <textarea ng-model="data.characteristic" class="form-control" ></textarea>
                <!-- **************************** HOBBIES ************************** -->
                <div class="well well-sm strong"><f:message key="hobbies" /></div>
                <textarea ng-model="data.hobbies" class="form-control"></textarea>

                <button type="submit" class="btn btn-primary col-sm-2 col-sm-offset-5">Save</button>
            </form>
        </div>


        <div class="container">
            <pre>{{data| json : spacing}}</pre>
        </div>
        <footer class="footer"> 
            <div class="container">
                <p class="text-muted">&copy; 2015</p>
            </div>
        </footer>

    </body>
</html>