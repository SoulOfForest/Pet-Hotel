<%-- 
    Document   : audit
    Created on : Jun 27, 2020, 5:23:25 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<link href="../../css/audit.css" rel="stylesheet" type="text/css"/>
<link href="../../css/admin.css" rel="stylesheet" type="text/css"/>
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

    .ui.selection.dropdown {
        width: 100% !important;
    }


</style>    
<t:wrapper>
    <c:if test="${requestScope.addSuccess != null}">
        <div class="addSuccess"></div>
    </c:if>
    <c:if test="${requestScope.addFail != null}">
        <div class="addFail"></div>
    </c:if>
    <c:if test="${requestScope.updatePet != null}">
        <div class="updatePet"></div>
    </c:if>
    <c:if test="${requestScope.disable != null}">
        <div class="disable"></div>
    </c:if>
    <c:if test="${requestScope.deletedFail != null}">
        <div class="deletedFail"></div>
    </c:if>

    <input type="hidden" id="userID" value="${sessionScope.user.getUserID()}"/>
    <input type="hidden" id="userRole" value="${sessionScope.user.getRole()}"/>
    <input type="hidden" id="userEmail" value="${sessionScope.user.getEmail()}"/>

    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if> 
        <a class="section active">Pets</a>
    </div>
    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 0px; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Pets</span>
                <%@include file="../../icons/logo.svg" %>
            </div>
        </h4>
        <div class="ui utilities">
            <a class="ui button" href="/pets/new">
                <div>
                    <%@include file="../../icons/plus.svg" %>
                    <span>New</span>
                </div>
            </a>
            <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                <c:if test="${sessionScope.user.getRole() != 'employee'}">
                    <a class="ui button" href="/logs">
                        <div>
                            <%@include file="../../icons/search.svg" %>
                            <span>Audit Logs</span>
                        </div>
                    </a>
                </c:if>
            </c:if> 
            <button class="ui button disabled" id="remove">
                <div>
                    <%@include file="../../icons/bin.svg" %>
                    <span>Delete</span>
                </div>
            </button>
            <button class="ui button" id="export-to-excel">
                <div>
                    <%@include file="../../icons/excel.svg" %>
                    <span>Export to Excel</span>
                </div>
            </button>
        </div>
        <div class="user-search">
            <form class="user-form" action="/pets/search" method="POST" autocomplete="off">
                <div class="container left">
                    <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                        <div class="form-field">
                            <label for="owner">Owner:  </label>
                            <input type="text" name="owner" id="owner"/>
                        </div>
                    </c:if>
                    <div class="form-field">
                        <label for="petName">Name: </label>
                        <input id="petName" type="text" name="petName" placeholder="">
                    </div>
                    <div class="form-field">
                        <label for="breed">Breed: </label>
                        <input type="text" name="breed" id="breed"/>
                    </div>

                </div>
                <div class="container right">
                    <div class="form-field">
                        <label for="type">Type: </label>
                        <select class="ui dropdown" name="type">
                            <option value="dog">Dog</option>
                            <option value="cat">Cat</option>
                        </select>
                    </div>
                    <div class="form-field">
                        <label for="size">Size: </label>
                        <select class="ui dropdown" name="size">
                            <option value="small">Small</option>
                            <option value="medium">Medium</option>
                            <option value="large">Large</option>
                        </select>
                    </div>
                    <div class="form-action">
                        <button class="search-action btn-action" type="submit">
                            <div>
                                <%@include file="../../icons/looking.svg" %>
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
    <div class="search-display" style="position: relative;">
        <c:choose>          
            <c:when test="${requestScope.pets.size() > 0}">
                <table class="ui celled table">
                    <thead>
                    <th><input type="checkbox" id="checkAll"/></th>
                    <th>Owner</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Breed</th>
                    <th>Size</th>
                    <th>Created At</th>
                    <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.petsByPage}" var="pet">

                            <tr>
                                <td data-label="check">
                                    <input type="checkbox" id="check"/>
                                </td>
                                <td data-label="petID" style="display: none;">
                                    <input type="hidden" id="id" value="${pet.getID()}"/>
                                </td>
                                <td data-label="petOwner">${pet.getOwner()}</td>
                                <td data-label="Name">${pet.getName()}</td>
                                <td data-label="Type">${pet.getType()}</td>
                                <td data-label="Breed">${pet.getBreed()}</td>
                                <td data-label="Size">${pet.getSize()}</td>
                                <td data-label="CreatedAt">
                                    ${pet.getCreatedAt()}
                                </td>

                                <td data-label="Actions">
                                    <a href="/pets/view?id=${pet.getID()}" data-tooltip="View Pet" data-position="top center">
                                        <%@include file="../../icons/eye.svg" %>
                                    </a>
                                    <a href="/pets/edit?id=${pet.getID()}" class="edit" data-tooltip="Update Pet" data-position="top center">
                                        <%@include file="../../icons/edit.svg" %>
                                    </a>
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
            </div>
            <div class="pagnination-search" onchange="document.querySelector('.listPerPage').submit();">
                <div class="pagnination-custom">
                    <div>
                        <span>Showing ${(currentPage - 1) * itemsPerPage + 1} to ${currentPage * itemsPerPage > pets.size() ? pets.size() : currentPage  * itemsPerPage} of ${pets.size()} entries</span>
                    </div>
                    <div style="margin-left: 10px;">
                        <form class="listPerPage" action="/pets/search${requestScope.action != null ? '?action=search' : ''}" method="POST" autocomplete="off">
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
                        <c:set var="end" value="${page * itemsPerPage > pets.size() ? pets.size() : page * itemsPerPage}"></c:set>                            

                            <a aria-current="false" aria-disabled="false" 
                               tabindex="0" value="${page}" 
                            type="pageItem" 
                            href="/pets?itemsPerPage=${requestScope.itemsPerPage}${requestScope.action != null ? '&action=search' : ''}&start=${start}&end=${end}" 
                            class="item page ${page == currentPage ? 'active' : ''}">
                            ${page}
                        </a>

                    </c:forEach>
                </div>                
            </div>
        </c:when> 
        <c:when test="${requestScope.pets.size() == 0}">
            <div style="text-align: center;">
                <td rowspan="5">
                    <%@include file="../../icons/mailbox.svg" %>
                    <p>No Data</p>
                </td>
            </div>
        </c:when>
    </c:choose>
</div>      
</t:wrapper>
<script src="../../js/pet.js"></script>
<script>
                window.addEventListener('DOMContentLoaded', function () {

                    if (document.querySelector('.addSuccess') != null) {
                        setTimeout(() => {
                            iziToast.success({
                                title: 'OK',
                                message: 'Adding Pet Successfully !',
                            });
                        }, 50);
                    }

                    if (document.querySelector('.addFail') != null) {
                        setTimeout(() => {
                            iziToast.success({
                                title: 'OK',
                                message: 'Adding Pet Failed !',
                            });
                        }, 50);
                    }

                    if (document.querySelector('.updatePet') != null) {
                        setTimeout(() => {
                            iziToast.success({
                                title: 'OK',
                                message: 'Update Pet Successfully !',
                            });
                        }, 50);
                    }

                    if (document.querySelector('.disable') != null) {
                        setTimeout(() => {
                            iziToast.success({
                                title: 'OK',
                                message: 'Pet(s) deleted successfully',
                            });
                        }, 50);
                    }

                    if (document.querySelector('.deletedFail') != null) {
                        setTimeout(() => {
                            iziToast.error({
                                title: 'Error',
                                message: 'There is a booking for this pet, So It can\'t be deleted',
                            });
                        }, 50);
                    }
                })
</script>