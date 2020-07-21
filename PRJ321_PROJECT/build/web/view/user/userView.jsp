<%-- 
    Document   : userEdit
    Created on : Jun 29, 2020, 5:56:45 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:wrapper>

    <style>
        .user-edit {
            display: grid;
            grid-template-columns: 1fr 1fr;
        }
        .container-right {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        .container-right h4 {
            font-size: 20px;
        }
        .container-right img {
            margin-top: 20px;
            border-radius: 50%;
        }
        div.enabled {
            color: #52c41a;
            background-color: #f6ffed;
            border-color: #b7eb8f;
            font-size: 13px;
            padding: 5px;
            border: 1px solid;
            border-radius: 4px;
            width: 100px;
            text-align: center;
        }
        div.disabled {
            color: #f5222d;
            background: #fff1f0;
            border-color: #ffa39e;
            font-size: 13px;
            padding: 5px;
            border: 1px solid;
            border-radius: 4px;
            width: 100px;
            text-align: center;
        }
    </style>
    <div class="ui breadcrumb">
        <a class="section" href="/statistics">Home</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section" href="/users">Users</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">View User</a>
    </div>
    <div class="ui services">
        <h3>View User</h3>
        <div class="ui utilities">     
            <c:if test="${sessionScope.user.getRole() == 'manager' || (sessionScope.user.getRole() == 'employee' && requestScope.user.getRole() == 'pet owner')}">
                <a class="ui button" href="/edit?id=${requestScope.user.getUserID()}">

                    <div>
                        <%@include file="../../icons/pencil.svg" %>
                        <span>Edit</span>
                    </div>

                </a>
            </c:if>
            <c:if test="${sessionScope.user.getRole() == 'manager'}">
                <a class="ui button ${requestScope.user.getStatus() == 'disabled' ? "disabled" : ""}" href="/disable?id=${requestScope.user.getUserID()}">
                    <div>
                        <%@include file="../../icons/ban.svg" %>
                        <span>Disable</span>
                    </div>
                </a>

                <a class="ui button normal" href="/logs">
                    <div href="/logs">
                        <%@include file="../../icons/search.svg" %>
                        <span>Audit Logs</span>
                    </div>
                </a>

                <a class="ui button normal" href="/logs?id=${requestScope.user.getUserID()}">
                    <div>
                        <%@include file="../../icons/eye.svg" %>
                        <span>Activity</span>
                    </div>
                </a>  
            </c:if>
        </div>
        <div class="user-edit">
            <div class="container-left">
                <form>      
                    <div class="form-field">
                        <label for="ID">ID : </label>
                        <input type="text" name="ID" id="ID" value="${requestScope.user.getUserID()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="email">Email : </label>
                        <input type="text" name="email" id="email" value="${requestScope.user.getEmail()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <c:set var="Name" value="${requestScope.user.getFirstName()} ${requestScope.user.getLastName()}"></c:set>
                        <div class="form-field">
                            <label for="firstName">Name: </label>
                            <input type="text" name="firstName" id="firstName" value="${Name}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="country">Country:  </label>
                        <input type="text" name="country" id="country" value="${requestScope.user.getCountry()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="age">Age:  </label>
                        <input type="number" name="age" id="age" value="${requestScope.user.getAge()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="gender">Gender:  </label>
                        <input type="text" name="gender" id="gender" value="${requestScope.user.isGender() ? 'Male' : 'Female'}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="createdAt">Created At:  </label>
                        <input type="text" name="createdAt" id="createdAt" value="${requestScope.user.getCreatedAt()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="updatedAt">Updated At:  </label>
                        <input type="text" name="updatedAt" id="updatedAt" value="${requestScope.user.getUpdatedAt()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="role">Role :  </label>
                        <input type="text" name="role" id="role" value="${requestScope.user.getRole()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="role">Status :  </label>
                        <div class="${user.getStatus() == 'enabled' ? 'enabled' : 'disabled'}">${requestScope.user.getStatus()}</div>
                    </div>
                </form>

            </div> 
            <div class="container-right" style="justify-content: flex-start;">
                <h4>Avatar</h4>

                <c:if test="${requestScope.user.getAvatar() != null}"> 
                    <img src="/retrieveImage?id=${requestScope.user.getAvatar()}" alt="user avatar" width="300" height="300" />
                </c:if>                      
                <c:if test="${requestScope.user.getAvatar() == null && requestScope.user.isGender()}"> 
                    <img src="../../images/male_default.jpg" alt="user avatar" width="300" height="300"/>
                </c:if>
                <c:if test="${requestScope.user.getAvatar() == null && !requestScope.user.isGender()}"> 
                    <img src="../../images/female_default.jpg" alt="user avatar" width="300" height="300" />
                </c:if>
            </div>
        </div>
    </div>    
</t:wrapper>