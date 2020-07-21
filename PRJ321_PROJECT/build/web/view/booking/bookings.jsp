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

    .form-field {
        grid-template-columns: 2fr 6fr !important;
    }

    .pagnination-search .pagnination-custom {
        display: flex;
        align-items: center;
    }

    .booked {
        background-color: rgb(16, 142, 233);
        color: white;
        border-color: rgb(16, 142, 233) !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }
    .progress {
        background-color: rgb(135, 208, 104);
        color: white;
        border-color: rgb(135, 208, 104) !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }

    .completed {
        background-color: rgba(250, 250, 250, 1);
        color: black;
        border-color: #d9d9d9 !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }
    .cancelled {
        background-color: rgb(255, 85, 0);
        color: white;
        border-color: rgb(255, 85, 0) !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }

    td[data-label=Actions] {
        display: flex;
        justify-content: flex-start;
    }
</style>
<t:wrapper>
    <c:if test="${requestScope.createBooking != null}">
        <div class="createBooking"></div>
    </c:if>
    <c:if test="${requestScope.updateBooking != null}">
        <div class="updateBooking"></div>
    </c:if>
    <c:if test="${requestScope.fail != null}">
        <div class="fail"></div>
    </c:if>
    <c:if test="${requestScope.bookingDeleted != null}">
        <div class="bookingDeleted"></div>
    </c:if>
    <c:if test="${requestScope.login != null}">
        <div class="login"></div>
    </c:if>
    <c:if test="${requestScope.updatedSuccess != null}">
        <div class="updatedSuccess"></div>
    </c:if>

    <input type="hidden" id="userID" value="${sessionScope.user.getUserID()}"/>
    <input type="hidden" id="userRole" value="${sessionScope.user.getRole()}"/>    

    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if> 
        <a class="section active">Bookings</a>
    </div>
    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 0px; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Bookings</span>
                <%@include file="../../icons/register.svg" %>
            </div>
        </h4>
        <div class="ui utilities">
            <a class="ui button" href="/bookings/new">
                <div>
                    <%@include file="../../icons/plus.svg" %>
                    <span>New</span>
                </div>
            </a>
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
                        <span>Delete</span>
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
        <div class="user-search">
            <form class="user-form" action="/bookings/search" method="POST">
                <div class="container left">

                    <div class="form-field">
                        <label for="id">ID: </label>
                        <input type="text" name="id" id="id"/>
                    </div>
                    <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                        <div class="form-field">
                            <label for="owner">Owner: </label>
                            <input type="text" name="owner" id="owner"/>
                        </div>
                    </c:if>
                    <div class="form-field">
                        <label for="period">Period: </label>
                        <div>
                            <input id="periodAt" type="text" name="periodAt" placeholder="Start date" autocomplete="off">
                            <span style="margin: 0px 10px;">~</span>
                            <input id="periodTo" type="text" name="periodTo" placeholder="End date" autocomplete="off">
                        </div>
                    </div>
                    <div class="form-field">
                        <label for="totalFee">Total Fee:  </label>
                        <div>
                            <input id="totalFee" type="number" min="0" name="feeFrom" placeholder="" autocomplete="off">
                            <span style="margin: 0px 10px;">~</span>
                            <input id="totalFee" type="number" min="0" name="feeTo" placeholder="" autocomplete="off">
                        </div>
                    </div>
                </div>
                <div class="container right">
                    <div class="form-field">
                        <label for="createdAt">Create At: </label>
                        <div>
                            <input id="createdAt" type="text" name="createdAt" placeholder="Start date" autocomplete="off">
                            <span style="margin: 0px 10px;">~</span>
                            <input id="createdTo" type="text" name="createdTo" placeholder="End date" autocomplete="off">
                        </div>
                    </div>
                    <div class="form-field">
                        <label for="petName">Pet Name: </label>
                        <input type="text" name="petName" id="petName"/>
                    </div>
                    <div class="form-field">
                        <label for="status">Status:  </label>
                        <select class="ui dropdown" name="status">
                            <option value="booked">Booked</option>
                            <option value="progress">In Progress</option>
                            <option value="cancelled">Cancelled</option>
                            <option value="completed">Completed</option>
                        </select>
                    </div>
                    <div class="form-field" style="padding: 10px 0px;">
                        <label for="extra">Extra Services: </label>
                        <div style="justify-content: flex-start">
                            <input type="checkbox" name="extra" value="extra" style="display: inline-block; width: auto"/>
                        </div>
                    </div>
                    <div class="form-action">
                        <button class="search-action btn-action" type="submit">
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
    <div class="search-display" style="position: relative;">
        <c:if test="${requestScope.bookingsPerPage.size() > 0}">
            <table class="ui celled table booking-table">
                <thead>
                <th><input type="checkbox" id="checkAll"/></th>
                <th>ID</th>
                <th>Owner</th>
                <th>Pet</th>
                <th>Arrival</th>
                <th>Departure</th>
                <th>Status</th>
                <th>Total Fee</th>
                <th>Extra Services</th>
                <th>Created At</th>
                <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.bookingsPerPage}" var="booking">

                        <tr>
                            <td data-label="check">
                                <input type="checkbox" id="check"/>
                            </td>
                            <td data-label="ID" data-tooltip="${booking.getId()}" data-position="top center">
                                ${booking.getId().substring(0,3)}...
                            </td>
                            <td data-label="PetOwner" style="color: var(--theme)">
                                <a href="/view?id=${booking.getUserID()}" data-tooltip="${booking.getUserEmail()}" data-position="top center">${booking.getUserName()}</a>
                            </td>
                            <td data-label="Pet" style="color: var(--theme)">${booking.getPetName()}</td>
                            <td data-label="Arrival" data-tooltip="${booking.getArrival()}" data-position="top center">
                                ${booking.getArrival().toString().substring(0, booking.getArrival().toString().indexOf(" "))}...
                            </td>
                            <td data-label="Departure" data-tooltip="${booking.getDeparture()}" data-position="top center">
                                ${booking.getDeparture().toString().substring(0, booking.getDeparture().toString().indexOf(" "))}...
                            </td>
                            <td data-label="Status" style="
                                text-align: left !important;
                                ">
                                <c:if test="${booking.getStatus() != 'progress'}">
                                    <span class="${booking.getStatus()}">${booking.getStatus()}</span>
                                </c:if>
                                <c:if test="${booking.getStatus() == 'progress'}">
                                    <span class="${booking.getStatus()}">In Progress</span>
                                </c:if>
                            </td>
                            <td data-label="Fee">
                                $${booking.getFee()}
                            </td>
                            <td data-label="extraServices">
                                <c:import url="/icons/${booking.isExtraServices() ? 'check': 'close'}.svg"/>
                            </td>
                            <td data-label="CreatedAt" data-tooltip="${booking.getCreatedAt()}" data-position="top center">
                                ${booking.getCreatedAt().toString().substring(0, booking.getCreatedAt().toString().indexOf(" "))}...
                            </td>
                            <td data-label="Actions">
                                <a href="/bookings/view?id=${booking.getId()}" data-tooltip="View Booking" data-position="top center">
                                    <%@include file="../../icons/eye.svg" %>
                                </a>
                                <c:if test="${sessionScope.user.getRole() == 'manager' || (sessionScope.user.getRole() == 'employee' && (booking.getStatus() != 'cancelled' && booking.getStatus() != 'completed' )) || (sessionScope.user.getRole() == 'pet owner' && booking.getStatus() == 'booked')}">
                                    <a href="/bookings/edit?id=${booking.getId()}&owner=${booking.getUserID()}" class="edit" data-tooltip="Edit Booking" data-position="top center">
                                        <%@include file="../../icons/edit.svg" %>
                                    </a>
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
        </div>
        <div class="pagnination-search" onchange="document.querySelector('.listPerPage').submit();">
            <div class="pagnination-custom">
                <div>
                    <span>Showing ${(currentPage - 1) * itemsPerPage + 1} to ${currentPage * itemsPerPage > bookings.size() ? bookings.size() : currentPage  * itemsPerPage} of ${bookings.size()} entries</span>
                </div>
                <div style="margin-left: 10px;">
                    <form class="listPerPage" action="/bookings/search${requestScope.action != null ? '?action=search' : ''}" method="POST" autocomplete="off">
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
                    <c:set var="end" value="${page * itemsPerPage > bookings.size() ? bookings.size() : page * itemsPerPage}"></c:set>                            

                        <a aria-current="false" aria-disabled="false" 
                           tabindex="0" value="${page}" 
                        type="pageItem" 
                        href="/bookings?itemsPerPage=${requestScope.itemsPerPage}${requestScope.action != null ? '&action=search' : ''}&start=${start}&end=${end}" 
                        class="item page ${page == currentPage ? 'active' : ''}">
                        ${page}
                    </a>

                </c:forEach>
            </div>                
        </div>
    </c:if>
    <c:if test="${requestScope.bookingsPerPage.size() == 0}">
        <div style="text-align: center;">
            <td rowspan="5">
                <%@include file="../../icons/mailbox.svg" %>
                <p>No Data</p>
            </td>
        </div>
    </c:if>
</div>      
</t:wrapper>
<script src="../../js/booking.js"></script>
<script>
        window.addEventListener('DOMContentLoaded', function () {
            if (document.querySelector('.createBooking') != null) {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'Create Booking Successfully !',
                    });
                }, 50);
            }

            if (document.querySelector('.updateBooking') != null) {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'Update Booking Successfully !',
                    });
                }, 50);
            }

            if (document.querySelector('.fail') != null) {
                setTimeout(() => {
                    iziToast.error({
                        title: 'Error',
                        message: 'Update Booking UnSuccessfully !',
                    });
                }, 50);
            }

            if (document.querySelector('.periodInvalid') != null) {
                setTimeout(() => {
                    iziToast.warning({
                        title: 'WARNING',
                        message: 'Arrival Date Must After Depature Date !',
                    });
                }, 50);
            }

            if (document.querySelector('.bookingDeleted') != null) {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'Booking Deleted Successfully !',
                    });
                }, 50);
            }

            if (document.querySelector('.login') != null) {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'Login Successfully !',
                    });
                }, 50);
            }

            if (document.querySelector('.updatedSuccess') != null) {
                setTimeout(() => {
                    iziToast.success({
                        title: 'OK',
                        message: 'Update Profile Successfully !',
                    });
                }, 50);
            }

        })
</script>