<%-- 
    Document   : audit
    Created on : Jun 27, 2020, 5:23:25 PM
    Author     : ViruSs0209
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Log"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<link href="../../css/audit.css" rel="stylesheet" type="text/css"/>
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
    
    .ui.dropdown.selection {
        width: 100% !important;
    }
</style>
<%
    ArrayList<Log> logs = (ArrayList<Log>) request.getAttribute("logs");
%>

<t:wrapper>
    <div class="ui breadcrumb">
        <a class="section" href="/statistics">Home</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">Audit Logs</a>
    </div>
    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 0px; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Audit Logs</span>
                <%@include file="../../icons/search.svg" %>
            </div>
        </h4>
        <div class="ui utilities">        
            <button class="ui button" id="export-to-excel">
                <div>
                    <%@include file="../../icons/excel.svg" %>
                    <span>Export to Excel</span>
                </div>
            </button>
        </div>
        <div class="user-search">
            <form class="user-form" action="/logs" method="POST" autocomplete="off">
                <div class="container left">
                    <div class="form-field">
                        <label for="period">Period: </label>
                        <div>
                            <input id="periodAt" type="text" name="periodAt" placeholder="Start date">
                            <span style="margin: 0px 10px;">~</span>
                            <input id="periodTo" type="text" name="periodTo" placeholder="End date">
                        </div>
                    </div>
                    <div class="form-field">
                        <label for="entity">Entity: </label>
                        <select class="ui dropdown" id="entity" name="entity">
                            <option value="User">User</option>
                            <option value="Pet">Pet</option>
                            <option value="Booking">Booking</option>
                            <option value="Settings">Settings</option>
                        </select>
                    </div>
                    <div class="form-field">
                        <label for="Action">Action: </label>
                        <select class="ui dropdown" id="Action" name="Action">
                            <option value="Updated">Updated</option>
                            <option value="Created">Created</option>
                            <option value="Removed">Removed</option>
                        </select>
                    </div>
                </div>
                <div class="container right">
                    <div class="form-field">
                        <label for="email">User Email: </label>
                        <input type="text" name="email" id="email"/>
                    </div>
                    <div class="form-field">
                        <label for="entityID">Entity ID: </label>
                        <input type="text" name="entityID" id="entityID"/>
                    </div>
                    <div class="form-action">
                        <button class="search-action btn-action">
                            <div>
                                <%@include file="../../icons/search.svg" %>
                                <span>Search</span>
                            </div>
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
    <div class="search-display">

        <table class="ui celled table">
            <thead>
            <th>Date</th>
            <th>User Email</th>
            <th>Entity</th>
            <th>Action</th>
            <th>Entity ID</th>
            <th>Actions</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.logsByPage}" var="log"  varStatus="loop">
                    <tr>
                        <td data-label="Date">
                            ${log.getCreatedAt()}
                        </td>
                        <td data-label="Email">${log.getUserEmail()}</td>
                        <td data-label="Entity">${log.getEntity()}</td>
                        <td data-label="Action">${log.getAction()}</td>
                        <td data-label="EntityID">
                            ${log.getEntityID()}
                        </td>
                        <td data-label="Actions">
                            <a style="color: var(--theme) !important;" href="#">View</a>
                            <pre style="display: none;">
                                ${log.getContent()}
                            </pre>
                        </td>
                    </tr>
                </c:forEach>                  
            </tbody>
        </table>
    </div>
    <c:if test="${requestScope.logs.size() == 0}">
        <div style="text-align: center;margin-top: 30px">
            <%@include file="../../icons/mailbox.svg" %>
            <p>No Data</p>
        </div>
    </c:if>
    <div class="pagnination-search" onchange="document.querySelector('.listPerPage').submit();">
        <div class="pagnination-custom">
            <div>
                <span>Showing ${(currentPage - 1) * itemsPerPage + 1} to ${currentPage * itemsPerPage > logs.size() ? logs.size() : currentPage  * itemsPerPage} of ${logs.size()} entries</span>
            </div>
            <div style="margin-left: 10px;">
                <form class="listPerPage" action="/logs${requestScope.action != null ? '?action=search' : ''}" method="POST" autocomplete="off">
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
            <c:forEach items="${requestScope.pages}" var="page" >
                <c:set var="start" value="${(page - 1) * itemsPerPage}"></c:set>
                <c:set var="end" value="${page * itemsPerPage > logs.size() ? logs.size() : page * itemsPerPage}"></c:set>                            


                    <a aria-current="false" aria-disabled="false" 
                       tabindex="0" value="${page}" 
                    type="pageItem" 
                    href="/logs?itemsPerPage=${requestScope.itemsPerPage}${requestScope.action != null ? '&action=search' : ''}&start=${start}&end=${end}" 

                    class="item page ${page == currentPage ? 'active' : ''}">
                    ${page}
                </a>

            </c:forEach>
        </div>                
    </div>
</div>      
</t:wrapper>
<script src="../../js/audit.js"></script>