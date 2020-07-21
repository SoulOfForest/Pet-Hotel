<%-- 
    Document   : audit
    Created on : Jun 27, 2020, 5:23:25 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<link href="../../css/admin.css" rel="stylesheet" type="text/css"/>
<link href="../../css/feedback.css" rel="stylesheet" type="text/css"/>
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

    td[data-label=Actions] {
        display: flex;
        justify-content: flex-start;
    }

    .ui.button svg {
        width: 20px !important;
        height: 20px !important;
    }

    .modal__content ul li {
        margin: 5px 0px;
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
        <a class="section active">Feedbacks</a>
    </div>
    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 0px; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Feedbacks</span>
                <%@include file="../../icons/positive-charges.svg" %>
            </div>
        </h4>
        <div class="ui utilities">
            <div class="modal micromodal-slide" id="modal-1" aria-hidden="true">
                <div class="modal__overlay" tabindex="-1" data-micromodal-close>
                    <div class="modal__container" role="dialog" aria-modal="true" aria-labelledby="modal-1-title">
                        <header class="modal__header">
                            <h2 class="modal__title" id="modal-1-title">
                                Booking Feedback
                            </h2>
                            <button class="modal__close" aria-label="Close modal" data-micromodal-close></button>
                        </header>
                        <main class="modal__content" id="modal-1-content">
                            <h3 style="font-size: 15px;">Booking Detail</h3>
                            <ul style="padding: 10px;">

                                <li>Full Name: <strong>Kien Le Trung</strong></li>
                                <li>                 
                                    User Email: <strong>kien1@gmail.com</strong>
                                </li>
                                <li>                 
                                    Pet: <strong>Kinyobi</strong>
                                </li>
                                <li>Departure: <strong>22: 00</strong> ~ Arrival <strong>23: 00</strong>.</li>

                                <li>                 
                                    Total Fee: <strong>89$</strong>
                                </li>

                            </ul>
                            <div class="user-feedback">
                                <h3>How do you feel about our services ?</h3>
                                <p>( Include User Experiences, Booking Service, Pet Care, ... )</p>
                                <textarea rows="10" cols="50"></textarea>
                            </div>
                        </main>
                        <footer class="modal__footer">
                            <button class="ui primary button">
                                Save
                            </button>
                            <button class="modal__btn" data-micromodal-close aria-label="Close this dialog window">Close</button>
                        </footer>
                    </div>
                </div>
            </div>
            <a class="ui button" href="/bookings">
                <div>
                    <%@include file="../../icons/register.svg" %>
                    <span>Bookings</span>
                </div>
            </a>
            <a class="ui button" href="/pets">
                <div>
                    <%@include file="../../icons/dog.svg" %>
                    <span>Pets</span>
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
            <form class="user-form" action="/feedbacks" method="POST">
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
                        <label for="bookingID">Booking ID: </label>
                        <input type="text" name="bookingID" id="bookingID"/>
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
                        <label for="createdAt">Published At: </label>
                        <div>
                            <input id="publishedAt" type="text" name="publishedAt" placeholder="Start date" autocomplete="off">
                            <span style="margin: 0px 10px;">~</span>
                            <input id="publishedTo" type="text" name="publishedTo" placeholder="End date" autocomplete="off">
                        </div>
                    </div>

                    <div class="form-field">
                        <label for="status">Status: </label>
                        <select class="ui dropdown" id="status" name="status">
                            <option value="Done">Done</option>
                            <option value="Not">Not</option>
                        </select>
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
                </div>
            </form>
        </div>
        <div class="search-display" style="position: relative;">
            <c:if test="${requestScope.feedbacksPerPage.size() > 0}">
                <table class="ui celled table booking-table">
                    <thead>
                        <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                        <th><input type="checkbox" id="checkAll"/></th>
                        </c:if>
                    <th>ID</th>
                    <th>Owner</th>
                    <th>Booking ID</th>
                    <th>Status</th>
                        <c:if test="${sessionScope.user.getRole() != 'pet owner'}">            
                        <th>Created At</th>
                        <th>Published At</th>
                        </c:if>         
                    <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.feedbacksPerPage}" var="feedback">

                            <tr>
                                <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                                    <td data-label="check">
                                        <input type="checkbox" id="check"/>
                                    </td>
                                </c:if>

                                <td data-label="ID" data-tooltip="${feedback.getId()}" data-position="top center">
                                    ${feedback.getId().substring(0, 3)}...
                                </td>
                                <td data-label="User" style="color: var(--theme)" data-tooltip="${feedback.getUserID()}" data-position="top center">
                                    <c:if test="${sessionScope.user.getRole() == 'pet owner'}">
                                        <strong>${feedback.getUser()}</strong>
                                    </c:if>
                                    <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                                        <a href="/view?id=${feedback.getUserID()}">${feedback.getUser()}</a>
                                    </c:if>
                                </td>
                                <td data-label="Booking" style="color: var(--theme)" data-tooltip="${feedback.getBookingID()}" data-position="top center">
                                    <a href="/bookings/view?id=${feedback.getBookingID()}">${feedback.getBookingID()}</a>
                                </td>   
                                <td data-label="Status">
                                    <c:if test="${feedback.getStatus() == 'Done'}">
                                        <%@include file="../../icons/check.svg" %>
                                    </c:if>
                                    <c:if test="${feedback.getStatus() != 'Done'}">
                                        Not Yet 
                                    </c:if>
                                </td>
                                <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                                    <td data-label="CreatedAt">
                                        ${feedback.getCreatedAt()}
                                    </td>
                                    <td data-label="PublishedAt">
                                        ${feedback.getPublishedAt() != null ? feedback.getPublishedAt() : ''}
                                    </td>         
                                </c:if>
                                <td data-label="Actions" style="display: flex;justify-content: center;align-items: center;">
                                    <c:if test="${sessionScope.user.getRole() == 'pet owner' && feedback.getStatus() != 'Done'}">
                                        <span style="border: none;cursor: pointer;" data-tooltip="Take Feedback" data-position="top center" id="takeFb">
                                            <%@include file="../../icons/edit.svg" %>
                                        </span>
                                    </c:if>
                                    <span style="border: none;cursor: pointer;" data-tooltip="View Feedback" data-position="top center" id="seeFb">
                                        <%@include file="../../icons/eye.svg" %>
                                    </span>
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
                        <span>Showing ${(currentPage - 1) * itemsPerPage + 1} to ${currentPage * itemsPerPage > feedbacks.size() ? feedbacks.size() : currentPage  * itemsPerPage} of ${feedbacks.size()} entries</span>
                    </div>
                    <div style="margin-left: 10px;">
                        <form class="listPerPage" action="/feedbacks${requestScope.action != null ? '?action=search' : ''}" method="POST" autocomplete="off">
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
                        <c:set var="end" value="${page * itemsPerPage > feedbacks.size() ? feedbacks.size() : page * itemsPerPage}"></c:set>                            

                            <a aria-current="false" aria-disabled="false" 
                               tabindex="0" value="${page}" 
                            type="pageItem" 
                            href="/feedbacks?itemsPerPage=${requestScope.itemsPerPage}${requestScope.action != null ? '&action=search' : ''}&start=${start}&end=${end}" 
                            class="item page ${page == currentPage ? 'active' : ''}">
                            ${page}
                        </a>

                    </c:forEach>
                </div>                
            </div>     
        </c:if>
        <c:if test="${requestScope.feedbacksPerPage.size() == 0}">
            <div style="text-align: center;">
                <td rowspan="5">
                    <%@include file="../../icons/mailbox.svg" %>
                    <p>No Data</p>
                </td>
            </div>
        </c:if>
    </div>      
</t:wrapper>
<script src="../../js/feedback.js"></script>
<script src="https://unpkg.com/micromodal/dist/micromodal.min.js"></script>