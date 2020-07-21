<%-- 
    Document   : admin
    Created on : Jun 25, 2020, 10:34:13 AM
    Author     : ViruSs0209
--%>

<%@page import="model.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<link href="../css/admin.css" rel="stylesheet" type="text/css"/>
<style>
    .pagnination-search {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .pagnination-search .pagnination-custom {
        display: flex;
        align-items: center;
    }

    .ui.table td {
        padding: 3px !important;
    }
</style>
<t:wrapper>
    <c:if test="${requestScope.updatedSuccess != null}">
        <div class="updatedSuccess"></div>
    </c:if>
    <c:if test="${requestScope.updatedSettings != null}">
        <div class="updatedSettings"></div>
    </c:if>
    <c:if test="${requestScope.updatePerson != null}">
        <div class="updatePerson"></div>
    </c:if>
    <c:if test="${requestScope.disable != null}">
        <div class="disable"></div>
    </c:if>
    <c:if test="${requestScope.updatedSettings != null}">
        <div class="updatedSettings"></div>
    </c:if>

    <input type="hidden" id="userID" value="${sessionScope.user.getUserID()}"/>

    <div class="ui breadcrumb">
        <a class="section" href="/statistics">Home</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">Users</a>
    </div>

    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 0; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Users</span>
                <%@include file="../../icons/add.svg" %>
            </div>
        </h4>
        <div class="ui utilities">
            <c:if test="${sessionScope.user.getRole() == 'manager'}">
                <a class="ui button" href="/logs">
                    <div>
                        <%@include file="../../icons/search.svg" %>
                        <span>Audit Logs</span>
                    </div>
                </a>
                <button class="ui button disabled" id="remove">
                    <div>
                        <%@include file="../../icons/bin.svg" %>
                        <span>Remove</span>
                    </div>
                </button>
                <button class="ui button disabled" id="enable">
                    <div>
                        <%@include file="../../icons/tick.svg" %>
                        </svg>
                        <span>Enable</span>
                    </div>
                </button>
                <button class="ui button disabled" id="disable">
                    <div>
                        <%@include file="../../icons/ban.svg" %>
                        <span>Disable</span>
                    </div>
                </button>
            </c:if>
            <button class="ui button" id="export-to-excel">
                <div>
                    <%@include file="../../icons/excel.svg" %>
                    <span>Export to Excel</span>
                </div>
            </button>
        </div>
        <div class="view-users">
            <span>View By</span>
            <div class="view-util">
                <button class="users enabled">Users</button>
                <button class="roles">Roles</button>
            </div>
        </div>
        <div class="user-search">
            <form class="user-form" action="/users" method="POST" autocomplete="off">
                <div class="container left">
                    <div class="form-field">
                        <label for="userID">ID: </label>
                        <input type="text" name="id" id="userID"/>
                    </div>
                    <div class="form-field">
                        <label for="email">Email: </label>
                        <input type="text" name="email" id="email"/>
                    </div>
                    <div class="form-field">
                        <label for="status">Status: </label>
                        <select class="ui dropdown" name="status">
                            <option value="enabled">Enabled</option>
                            <option value="disabled">Disabled</option>
                        </select>
                    </div>
                </div>
                <div class="container right">
                    <div class="form-field">
                        <label for="createdAt">Create At: </label>
                        <div>
                            <input id="createdAt" type="text" name="createdAt" placeholder="Start date">
                            <span style="margin: 0px 10px;">~</span>
                            <input id="createdTo" type="text" name="createdTo" placeholder="End date">
                        </div>
                    </div>
                    <div class="form-field">
                        <label for="name">Name: </label>
                        <input type="text" name="name" id="name"/>
                    </div>
                    <div class="form-field">
                        <label for="role">Role: </label>
                        <select class="ui dropdown" name="role">
                            <option value="all">All</option>
                            <option value="manager">Manager</option>
                            <option value="employee">Employee</option>
                            <option value="pet owner">Pet Owner</option>
                        </select>
                    </div>

                </div>
                <div class="form-action" style="text-align: left !important;">
                    <button class="search-action btn-action" type="submit">
                        <div>
                            <%@include file="../../icons/looking.svg" %>
                            <span>Search</span>
                        </div>
                    </button>
                    </button>
                    <button class="reset-action btn-action">
                        <div>
                            <%@include file="../../icons/loop.svg" %>
                            <span>Reset</span>
                        </div>
                    </button>
                </div>
            </form>
        </div>
    </div>
    <div class="search-display" style="position: relative;">

        <c:choose>          
            <c:when test="${requestScope.users.size() > 0}">  
                <table class="ui celled table">
                    <thead>
                        <tr>
                            <th><input type="checkbox" id="checkAll"/></th>
                            <th>Avatar</th>
                            <th>Email</th>
                            <th>Name</th>
                            <th>Roles</th>
                            <th>Status</th>
                            <th>CreatedAt</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.usersByPage}" var="user">

                            <tr>
                                <td data-label="check">
                                    <input type="checkbox" id="check"/>
                                </td>
                                <td data-label="avatar">
                                    <c:if test="${user.getAvatar() != null}"> 
                                        <img src="/retrieveImage?id=${user.getAvatar()}" alt="user avatar" />
                                    </c:if>                      
                                    <c:if test="${user.getAvatar() == null && user.isGender()}"> 
                                        <img src="../../images/male_default.jpg" alt="user avatar" />
                                    </c:if>
                                    <c:if test="${user.getAvatar() == null && !user.isGender()}"> 
                                        <img src="../../images/female_default.jpg" alt="user avatar" />
                                    </c:if>
                                </td>
                                <td data-label="Email">${user.getEmail()}</td>
                                <td data-label="Name">${user.getFirstName()} ${user.getLastName()}</td>
                                <td data-label="Roles">${user.getRole()}</td>
                                <td data-label="Status">
                                    <span class="${user.getStatus() == 'enabled' ? 'enabled' : 'disabled'}">${user.getStatus()}</span>
                                </td>
                                <td data-label="CreatedAt">
                                    ${user.getCreatedAt()}
                                </td>

                                <td data-label="Actions">
                                    <c:if test="${user.getUserID() != sessionScope.user.getUserID()}">
                                        <a href="/view?id=${user.getUserID()}" data-tooltip="View User" data-position="top center">
                                            <%@include file="../../icons/eye.svg" %>
                                        </a>
                                        <c:if test="${sessionScope.user.getRole() == 'manager' || (sessionScope.user.getRole() == 'employee' && user.getRole() == 'pet owner')}">
                                            <a href="/edit?id=${user.getUserID()}" class="edit" data-tooltip="Edit User" data-position="top center">
                                                <%@include file="../../icons/edit.svg" %>
                                            </a>
                                        </c:if>
                                    </c:if>
                                </td>
                            </tr>                         
                        </c:forEach>
                    </tbody>

                </table>

                <div class="ui segment" style="
                     position: absolute;
                     top: 0 !important;
                     left: 0 !important;
                     width: 100%;
                     height: 100%;
                     ">
                    <div class="ui active dimmer">
                        <div class="ui text loader">Loading</div>
                    </div>
                    <p></p>
                </div>

                <div class="pagnination-search" onchange="document.querySelector('.listPerPage').submit();">
                    <div class="pagnination-custom">
                        <div>
                            <span>Showing ${(currentPage - 1) * itemsPerPage + 1} to ${currentPage * itemsPerPage > users.size() ? users.size() : currentPage  * itemsPerPage} of ${users.size()} entries</span>
                        </div>
                        <div style="margin-left: 10px;">
                            <form class="listPerPage" action="/users?${requestScope.action != null ? 'action=search' : ''}" method="POST" autocomplete="off">
                                <select class="ui dropdown" name="itemsPerPage" style="width: 100%;">
                                    <option value="5" ${Integer.parseInt(requestScope.itemsPerPage) == 5 ? "selected='selected'" : ""}>5 / page</option>
                                    <option value="10" ${Integer.parseInt(requestScope.itemsPerPage) == 10 ? "selected='selected'" : ""}>10 / page</option>
                                    <option value="15" ${Integer.parseInt(requestScope.itemsPerPage) == 15 ? "selected='selected'" : ""}>15 / page</option>
                                    <option value="20" ${Integer.parseInt(requestScope.itemsPerPage) == 20 ? "selected='selected'" : ""}>20 / page</option>
                                </select>
                            </form>
                        </div>

                    </div>
                    <div aria-label="Pagination Navigation" role="navigation" class="ui pagination menu">

                        <c:forEach items="${pages}" var="page" >
                            <c:set var="start" value="${(page - 1) * itemsPerPage}"></c:set>
                            <c:set var="end" value="${page * itemsPerPage > users.size() ? users.size() : page * itemsPerPage}"></c:set>                            

                                <a aria-current="false" aria-disabled="false" 
                                   tabindex="0" value="${page}" 
                                type="pageItem" 
                                href="/users?itemsPerPage=${requestScope.itemsPerPage}${requestScope.action != null ? '&action=search' : ''}&start=${start}&end=${end}" 
                                class="item page ${page == currentPage ? 'active' : ''}">
                                ${page}
                            </a>

                        </c:forEach>  

                    </div>                          
                </div>
            </c:when> 
            <c:when test="${requestScope.usersByPage.size() == 0}">
                <div style="text-align: center;">
                    <td rowspan="5">
                        <%@include file="../../icons/mailbox.svg" %>
                        <p>No Data</p>
                    </td>
                </div>
            </c:when>
        </c:choose>

    </div>

</div>  
</t:wrapper>
<script src="../js/admin.js"></script>
<script>
                    window.addEventListener('DOMContentLoaded', function () {

                        if (document.querySelector('.updatedSuccess') != null) {
                            setTimeout(() => {
                                iziToast.success({
                                    title: 'OK',
                                    message: 'Update Profile Successfully !',
                                });
                            }, 50);
                        }

                        if (document.querySelector('.updatePerson') != null) {
                            setTimeout(() => {
                                iziToast.success({
                                    title: 'OK',
                                    message: 'Update Person Successfully !',
                                });
                            }, 50);
                        }

                        if (document.querySelector('.disable') != null) {
                            setTimeout(() => {
                                iziToast.success({
                                    title: 'OK',
                                    message: 'User(s) disabled successfully',
                                });
                            }, 50);
                        }

                        if (document.querySelector('.updatedSettings') != null) {
                            setTimeout(() => {
                                iziToast.success({
                                    title: 'OK',
                                    message: 'Update Settings Successfully !',
                                });
                            }, 50);
                        }
                    })
</script>

