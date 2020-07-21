<%-- 
    Document   : audit
    Created on : Jun 27, 2020, 5:23:25 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<link href="../../css/newBooking.css" rel="stylesheet" type="text/css"/>
<style>
    .ui.dropdown.selection {
        width: 100% !important;
    }
    .user-form.user-role {
        grid-template-columns: 1.5fr 2fr;
    }
    
    

    .user-form.user-role .container {
        border: 1px solid #d6d6d6;
        padding: 20px;
        border-radius: 6px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        /*        text-align: center;*/
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
    }
    .user-form.user-role .container:hover {
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }

    .user-form.user-role .container.right.user-role {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    ul {
        padding: 10px;
    }

    ul li {
        margin: 20px;
        font-size: 15px;
    }

    ul li:first-child {
        margin-top: 5px;
    }

    ul li div {
        display: flex;
        align-items: center;
    }
</style>
<t:wrapper>
    <c:if test="${requestScope.periodInvalid != null}">
        <div class="periodInvalid"></div>
    </c:if>
    <c:if test="${requestScope.ownerEmpty != null}">
        <div class="ownerEmpty"></div>
    </c:if>
    <c:if test="${requestScope.periodEmpty != null}">
        <div class="periodEmpty"></div>
    </c:if>
    <c:if test="${requestScope.nameEmpty != null}">
        <div class="nameEmpty"></div>
    </c:if>
    <c:if test="${requestScope.petInvalid != null}">
        <div class="petInvalid"></div>
    </c:if>
    <c:if test="${requestScope.periodFuture != null}">
        <div class="periodFuture"></div>
    </c:if>
    <c:if test="${requestScope.fullCapacity != null}">
        <div class="fullCapacity"></div>
    </c:if>
        
    <input type="hidden" id="currentUser" value="${sessionScope.user.getEmail()}"/>    
    <input type="hidden" id="small" value="${sessionScope.setting.getSmallPetFee()}"/>
    <input type="hidden" id="medium" value="${sessionScope.setting.getMediumPetFee()}"/>
    <input type="hidden" id="large" value="${sessionScope.setting.getFee()}"/>
    <input type="hidden" id="extra" value="${sessionScope.setting.getExtraFee()}"/>

    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if> 
        <a class="section" href="/bookings">Bookings</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">New Booking</a>
    </div>
    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 0px; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">New Booking</span>
                <%@include file="../../icons/register.svg" %>
            </div>
        </h4>
        <div class="user-search ${sessionScope.user.getRole() == 'pet owner' ? 'user-role' : ''}">
            <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                <h4>Search For Owner</h4>
            </c:if>
            <div class="user-form ${sessionScope.user.getRole() == 'pet owner' ? 'user-role' : ''}">
                <div class="container left">
                    <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                        <div class="form-field">
                            <label for="owner">Owner:  </label>
                            <input type="text" name="owner" id="owner"/>
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

                        <div class="form-action">
                            <button class="search-action btn-action">
                                <div>
                                    <%@include file="../../icons/looking.svg" %>
                                    <span>Search</span>
                                </div>
                            </button>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.user.getRole() == 'pet owner'}">
                        <div class="booking-price">
                            <h2>Benefits that pets will get: </h2>
                            <ul style="color: var(--theme)">
                                <li>
                                    <div>
                                        <%@include file="../../icons/dog-food.svg" %>
                                        <span style="margin-left: 10px;">Wet/dry food included</span>
                                    </div>
                                </li>
                                <li>
                                    <div>
                                        <%@include file="../../icons/walking.svg" %>
                                        <span style="margin-left: 10px;">3 walks daily</span>
                                    </div>
                                </li>
                                <li>
                                    <div>
                                        <%@include file="../../icons/social.svg" %>
                                        <span style="margin-left: 10px;">Social interaction</span>
                                    </div>
                                </li>
                                <li>
                                    <div>
                                        <%@include file="../../icons/vaccine.svg" %>
                                        <span style="margin-left: 10px;">Monitoring health</span>
                                    </div>
                                </li>
                            </ul>
                            <div class="price-boarding">
                                <h2>Pet Price Boarding ( Depend on pet size - in US dollars ): </h2>                                
                                <ul style="color: var(--theme)">
                                    <li>
                                        <div>
                                            <span style="margin-left: 10px;">
                                                Small: <%@include file="../../icons/fee.svg" %> <strong>${sessionScope.setting.getSmallPetFee()}</strong> per day
                                            </span>
                                        </div>
                                    </li>
                                    <li>
                                        <div>
                                            <span style="margin-left: 10px;">
                                                Medium: <%@include file="../../icons/fee.svg" %> <strong>${sessionScope.setting.getMediumPetFee()}</strong> per day
                                            </span>
                                        </div>
                                    </li>
                                    <li>
                                        <div>
                                            <span style="margin-left: 10px;">
                                                Large: <%@include file="../../icons/fee.svg" %> <strong>${sessionScope.setting.getFee()}</strong> per day
                                            </span>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                            <div class="extra-services">
                                <h2>Extra Services (Optional - <span style="font-size: 15px; color:var(--theme)"> <%@include file="../../icons/fee.svg" %> ${sessionScope.setting.getExtraFee()} / 1 Booking</span>):  </h2>
                                <p style="font-size: 15px;">
                                    Typically charge extra for additional services such as <strong style="color: var(--theme);">teeth cleaning, flea treatments and nail clipping</strong>
                                </p>
                            </div>
                            <div class="extra-services" style="margin-top:15px;">
                                <h2>Notice:  </h2>       
                                <p><strong>Notice</strong>: All payments will be paid when you take your pet to the pet hotel! No prepayment needed</p>
                                <span style="display: flex;align-items: center">
                                    <%@include file="../../icons/alert.svg" %>
                                    <strong style="margin-left: 15px;">So If You See Any Prepayment, Watch out !</strong>
                                </span>
                            </div>
                        </div>
                    </c:if>
                </div>
                <div class="container right ${sessionScope.user.getRole() == 'pet owner' ? 'user-role' : ''}">   

                    <form action="/bookings" method="POST" enctype="multipart/form-data">
                        <c:if test="${sessionScope.user.getRole() == 'pet owner'}">
                            <div style="display: flex;justify-content: center;align-items: center;margin-bottom: 40px;">
                                <span style="font-size:25px;margin-right: 10px">           
                                    Start Booking In Here
                                </span>
                                <%@include file="../../icons/location.svg" %>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                            <div class="form-field">
                                <label for="owner">Owner: </label>
                                <input type="text" name="owner" id="owner" readonly style="border:0;font-weight: bold"/>
                            </div>
                        </c:if>
                        <div class="form-field">
                            <label for="pet">Pet:  </label>
                            <select class="ui dropdown" id="owner-pets" name="pet" style="width: 100% !important">

                            </select>
                        </div>
                        <div class="form-field">
                            <label for="period">Period: </label>
                            <div class="group-input">
                                <input id="periodAt" type="text" name="periodAt" placeholder="Start date" autocomplete="off">
                                <span style="margin: 0px 10px;">~</span>
                                <input id="periodTo" type="text" name="periodTo" placeholder="End date" autocomplete="off">
                            </div>
                        </div>
                        <div class="form-field">
                            <label for="notes">Notes: </label>
                            <textarea id="notes" name="notes" rows="4"></textarea>
                        </div>
                        
                        <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                            <div class="form-field">
                                <label for="employeeNotes">Employee Notes: </label>
                                <textarea id="employeeNotes" name="employeeNotes" rows="4"></textarea>
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
                            <div class="form-field">
                                <label for="cancellationNotes">Cancellation Notes: </label>
                                <textarea id="cancellationNotes" name="cancellationNotes" rows="4"></textarea>
                            </div>
                            <div class="form-field">
                                <label for="receipt">Receipt: </label>
                                <input type="file" class="receipt" name="receipt" accept="image/*"/>
                            </div>

                        </c:if>   
                        <div class="form-field">
                            <label for="notes">Extra Services: </label>
                            <div style="justify-content: flex-start">
                                <input type="checkbox" name="extra" value="extra" style="display: inline-block; width: auto"/>
                                <span style="color: var(--theme); margin-left: 10px">(Include Grooming, Exercising, Teeth Cleaning, Nail Clipping)</span>
                            </div>
                        </div>
                        <div class="form-action">
                            <button class="search-action btn-action" type="submit">
                                <div>                              
                                    <%@include file="../../icons/save.svg" %>
                                    <span>Save</span>
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
        </div>
    </div>      
</t:wrapper>
<script src="../../js/newBooking.js"></script>
<script>
    window.addEventListener('DOMContentLoaded', function () {
        if (document.querySelector('.periodInvalid') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'WARNING',
                    message: 'Arrival Date Must After Depature Date !',
                });
            }, 50);
        }

        if (document.querySelector('.nameEmpty') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'WARNING',
                    message: 'Pet Can\'t be Empty !',
                });
            }, 50);
        }

        if (document.querySelector('.ownerEmpty') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'WARNING',
                    message: 'Owner Can\'t be Empty !',
                });
            }, 50);
        }

        if (document.querySelector('.periodEmpty') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'WARNING',
                    message: 'Period Range Can\'t be Empty !',
                });
            }, 50);
        }

        if (document.querySelector('.petInvalid') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'WARNING',
                    message: 'Booking Failed ! Pet is Already Booked !',
                });
            }, 50);
        }

        if (document.querySelector('.periodFuture') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'WARNING',
                    message: 'The Period Must Be In The Future !',
                });
            }, 50);
        }

        if (document.querySelector('.fullCapacity') != null) {
            setTimeout(() => {
                iziToast.error({
                    title: 'ERROR',
                    message: 'Booking is Full ! See You Next Time :)',
                });
            }, 50);
        }
    })
</script>