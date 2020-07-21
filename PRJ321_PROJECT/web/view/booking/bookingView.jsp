<%-- 
    Document   : petView
    Created on : Jun 27, 2020, 10:26:15 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
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
        .booked {
            background-color: rgb(16, 142, 233);
            color: white;
            border-color: rgb(16, 142, 233) !important;
            font-size: 12px !important;
            padding: 3px !important;
        }
        .progress {
            background-color: rgb(135, 208, 104);
            color: white;
            border-color: rgb(135, 208, 104) !important;
            font-size: 12px !important;
            padding: 3px !important;
        }

        .completed {
            background-color: #fafafa;
            color: black;
            border-color: #d9d9d9 !important;
            font-size: 12px !important;
            padding: 3px !important;
        }
        .cancelled {
            background-color: rgb(255, 85, 0);
            color: white;
            border-color: rgb(255, 85, 0) !important;
            font-size: 12px !important;
            padding: 3px !important;
        }
        
        .form-field {
            grid-template-columns: 2fr 6fr;
        }
    </style>
    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if>    
        <a class="section" href="/bookings">Bookings</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">View Booking</a>
    </div>
    <div class="ui services">
        <h3>View Booking</h3>
        <div class="ui utilities">     
            <c:if test="${sessionScope.user.getRole() != 'pet owner' || (sessionScope.user.getRole() == 'pet owner') && requestScope.booking.getStatus() == 'booked'}">
                <a class="ui button" href="/bookings/edit?id=${requestScope.booking.getId()}&owner=${requestScope.booking.getUserID()}">
                    <div>
                        <%@include file="../../icons/pencil.svg" %>
                        <span>Edit</span>
                    </div>
                </a>         
            </c:if>
            <c:if test="${sessionScope.user.getRole() == 'manager'}">
                <a class="ui button" href="/bookings/delete?id=${requestScope.booking.getId()}">
                    <div>
                        <%@include file="../../icons/bin.svg" %>
                        <span>Delete</span>
                    </div>
                </a>
                <a class="ui button normal" href="/logs?itemsPerPage=5&action=search&start=0&end=5&id=${requestScope.booking.getId()}">
                    <div>
                        <%@include file="../../icons/search.svg" %>
                        <span>Audit Logs</span>
                    </div>
                </a>   
            </c:if>
        </div>
        <div class="user-edit">
            <div class="container-left">
                <form>      
                    <div class="form-field">
                        <label for="ID">ID : </label>
                        <input type="text" name="ID" id="ID" value="${requestScope.booking.getId()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
     
                    <div class="form-field">
                        <label for="owner">Owner : </label>
                        <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                            <a href="/view?id=${requestScope.booking.getUserID()}" STYLE="font-weight: bold; color: var(--theme); padding: 10px">
                                ${requestScope.booking.getUserName()} ( ${requestScope.booking.getUserEmail()} )              
                            </a>
                        </c:if>
                        <c:if test="${sessionScope.user.getRole() == 'pet owner'}">
                            <input type="text" name="owner" id="owner" value="${booking.getUserName()} ( ${booking.getUserEmail()} )" 
                                   STYLE="border:none" readonly/>
                        </c:if>
                    </div>
                    <div class="form-field">
                        <label for="pet">Pet: </label>
                        <a href="/pets/view?id=${requestScope.booking.getPetId()}" STYLE="font-weight: bold; color: var(--theme); padding: 10px">
                            ${requestScope.booking.getPetName()} 
                        </a>
                    </div>
                    <div class="form-field">
                        <label for="arrival">Arrival: </label>
                        <input type="text" name="arrival" id="arrival" value="${requestScope.booking.getArrival()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="departure">Departure: </label>
                        <input type="text" name="departure" id="departure" value="${requestScope.booking.getDeparture()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <c:if test="${requestScope.booking.getOwnerNotes() != ''}">
                        <div class="form-field">
                            <label for="ownerNotes">Notes: </label>
                            <input id="ownerNotes" name="ownerNotes" value="${requestScope.booking.getOwnerNotes()}" STYLE="border:none; font-weight: bold" readonly />

                        </div>
                    </c:if>
                    <c:if test="${requestScope.booking.getCancelNotes() != ''}">
                        <div class="form-field">
                            <label for="cancellationNotes">Cancellation Notes: </label>
                            <input id="cancellationNotes" name="cancellationNotes" value="${requestScope.booking.getCancelNotes()}" STYLE="border:none; font-weight: bold" readonly />
                        </div>
                    </c:if>
                    <c:if test="${requestScope.booking.getManagerNotes() != ''}">
                        <div class="form-field">
                            <label for="managerNotes">Employee Notes: </label>
                            <input id="managerNotes" name="managerNotes" value="${requestScope.booking.getManagerNotes()}" STYLE="border:none; font-weight: bold" readonly />
                        </div>
                    </c:if>
                    <div class="form-field">
                        <label for="status">Status: </label>
                        <c:if test="${requestScope.booking.getStatus() != 'progress'}">
                            <span class="${booking.getStatus()}" style="width: 15%;
                                  text-align: center;
                                  border-radius: 6px;">${booking.getStatus()}</span>
                        </c:if>
                        <c:if test="${requestScope.booking.getStatus() == 'progress'}">
                            <span class="${booking.getStatus()}" style="width: 15%;
                                  text-align: center;
                                  border-radius: 6px;">In Progress</span>
                        </c:if>
                    </div>
                    <div class="form-field">
                        <label for="extraServices">Extra Services: </label>
                        <span style="padding: 10px">
                            <c:import url="/icons/${requestScope.booking.isExtraServices() ? 'check' : 'close'}.svg" />
                        </span>
                    </div>
                    <div class="form-field">
                        <label for="fee">Total Fee: </label>
                        <input type="text" name="fee" id="fee" value="$${requestScope.booking.getFee()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="createdAt">Created At: </label>
                        <input type="text" name="createdAt" id="createdAt" value="${requestScope.booking.getCreatedAt()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="updatedAt">Updated At: </label>
                        <input type="text" name="updatedAt" id="updatedAt" value="${requestScope.booking.getUpdatedAt()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                </form>

            </div> 
            <div class="container-right" style="justify-content: flex-start;">
                <c:if test="${requestScope.booking.getReceipt() == null}">
                    <h4>No Receipt!</h4>
                </c:if>
                <c:if test="${requestScope.booking.getReceipt() != null}">
                    <h4>Receipt</h4>
                    <c:if test="${requestScope.pet.getAvatar() != null}"> 
                        <img src="/retrieveImage?id=${requestScope.pet.getAvatar()}" alt="pet avatar" width="300" height="300" />
                    </c:if>
                    <c:if test="${requestScope.pet.getAvatar() == null && requestScope.pet.getType() == 'dog'}"> 
                        <img src="../../images/dog.jpg" alt="pet avatar" width="300" height="300" />
                    </c:if>
                    <c:if test="${requestScope.pet.getAvatar() == null && requestScope.pet.getType() == 'cat'}"> 
                        <img src="../../images/cat.jpg" alt="pet avatar" width="300" height="300" />
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>    
</t:wrapper>