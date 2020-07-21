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
    </style>
    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if> 
        <a class="section" href="/pets">Pets</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">View Pet</a>
    </div>
    <div class="ui services">
        <h3>View Pet</h3>
        <div class="ui utilities">     
            <a class="ui button" href="/pets/edit?id=${requestScope.pet.getID()}">
                <div>
                    <%@include file="../../icons/pencil.svg" %>
                    <span>Edit</span>
                </div>
            </a>
            <a class="ui button" href="/pets/delete?id=${requestScope.pet.getID()}">
                <div>
                    <%@include file="../../icons/ban.svg" %>
                    <span>Delete</span>
                </div>
            </a>
            <c:if test="${sessionScope.user.getRole() == 'manager'}">   
                <a class="ui button normal" href="/logs?itemsPerPage=5&action=search&start=0&end=5&id=${requestScope.pet.getID()}">
                    <div href="/logs">
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
                        <input type="text" name="ID" id="ID" value="${requestScope.pet.getID()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="owner">Owner : </label>
                        <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                            <a href="/view?id=${requestScope.pet.getOwnerID()}" STYLE="font-weight: bold; color: var(--theme); padding: 10px">${requestScope.pet.getOwner()}</a>
                        </c:if>
                        <c:if test="${sessionScope.user.getRole() == 'pet owner'}">
                            <input type="text" name="owner" id="owner" value="${requestScope.pet.getOwner()}" 
                                   STYLE="border:none" readonly/>
                        </c:if>
                    </div>
                    <div class="form-field">
                        <label for="name">Name: </label>
                        <input type="text" name="name" id="name" value="${requestScope.pet.getName()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="type">Type:  </label>
                        <input type="text" name="type" id="type" value="${requestScope.pet.getType()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="breed">Breed:  </label>
                        <input type="text" name="breed" id="breed" value="${requestScope.pet.getBreed()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="size">Size:  </label>
                        <input type="text" name="size" id="size" value="${requestScope.pet.getSize()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="size">Booking:  </label>
                        <c:if test="${requestScope.currentBooking != null}">
                            <a href="/bookings/view?id=${requestScope.currentBooking.getId()}" STYLE="color: var(--theme)">
                                ${requestScope.currentBooking.getDeparture()} - ${requestScope.currentBooking.getArrival()} ( ${requestScope.currentBooking.getStatus() == 'progress' ? 'In Progress' : requestScope.currentBooking.getStatus()} )
                            </a>
                        </c:if>
                        <c:if test="${requestScope.currentBooking == null}">
                            <input type="text" name="size" id="size" value="This Pet Haven't Booked Yet !" STYLE="border:none; font-weight: bold" readonly/>
                        </c:if>
                    </div>
                    <div class="form-field">
                        <label for="createdAt">Created At:  </label>
                        <input type="text" name="createdAt" id="createdAt" value="${requestScope.pet.getCreatedAt()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="updatedAt">Updated At:  </label>
                        <input type="text" name="updatedAt" id="updatedAt" value="${requestScope.pet.getUpdatedAt()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                </form>

            </div> 
            <div class="container-right" style="justify-content: flex-start;">
                <h4>Avatar</h4>

                <c:if test="${requestScope.pet.getAvatar() != null}"> 
                    <img src="/retrieveImage?id=${requestScope.pet.getAvatar()}" alt="pet avatar" width="300" height="300" />
                </c:if>
                <c:if test="${requestScope.pet.getAvatar() == null && requestScope.pet.getType() == 'dog'}"> 
                    <img src="../../images/dog.jpg" alt="pet avatar" width="300" height="300" />
                </c:if>
                <c:if test="${requestScope.pet.getAvatar() == null && requestScope.pet.getType() == 'cat'}"> 
                    <img src="../../images/cat.jpg" alt="pet avatar" width="300" height="300" />
                </c:if>
            </div>
        </div>
    </div>    
</t:wrapper>