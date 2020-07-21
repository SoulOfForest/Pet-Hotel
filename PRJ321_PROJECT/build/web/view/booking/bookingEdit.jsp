<%-- 
    Document   : petEdit
    Created on : Jul 6, 2020, 7:36:01 PM
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
            grid-template-columns: 1.25fr 1fr;
            border: 1px solid #d9d9d9;
            padding: 20px;
            margin-top: 30px;
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

        .edit-owner {
            transition: .2s all ease-in;
        }

        .edit-owner:hover {
            transform: scale(1.2);
            fill: var(--theme);
        }

        .ui.dropdown.selection {
            width: 100% !important;
        }

        .user-edit.user-role {
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container-left.user-role {
            display: inline-block;
        }

        .form-field {  
            grid-template-columns: 2fr 6fr !important;
        }

        .modal {
            font-family: -apple-system,BlinkMacSystemFont,avenir next,avenir,helvetica neue,helvetica,ubuntu,roboto,noto,segoe ui,arial,sans-serif;
        }

        .modal__overlay {
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(0,0,0,0.6);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 999;
        }

        .modal__container {
            background-color: #fff;
            padding: 30px;
            max-width: 500px;
            max-height: 100vh;
            border-radius: 4px;
            overflow-y: auto;
            box-sizing: border-box;
        }

        .modal__header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal__title {
            margin-top: 0;
            margin-bottom: 0;
            font-weight: 600;
            font-size: 1.25rem;
            line-height: 1.25;
            color: #00449e;
            box-sizing: border-box;
        }

        .modal__close {
            background: transparent;
            border: 0;
            margin-left: 5px;
        }

        .modal__header .modal__close:before { content: "\2715"; }

        .modal__content {
            margin-top: 2rem;
            margin-bottom: 2rem;
            line-height: 1.5;
            color: rgba(0,0,0,.8);
        }

        .modal__content ul li {
            margin: 5px 0px;
        }

        .modal__btn {
            font-size: .875rem;
            padding-left: 1rem;
            padding-right: 1rem;
            padding-top: .5rem;
            padding-bottom: .5rem;
            background-color: #e6e6e6;
            color: rgba(0,0,0,.8);
            border-radius: .25rem;
            border-style: none;
            border-width: 0;
            cursor: pointer;
            -webkit-appearance: button;
            text-transform: none;
            overflow: visible;
            line-height: 1.15;
            margin: 0;
            will-change: transform;
            -moz-osx-font-smoothing: grayscale;
            -webkit-backface-visibility: hidden;
            backface-visibility: hidden;
            -webkit-transform: translateZ(0);
            transform: translateZ(0);
            transition: -webkit-transform .25s ease-out;
            transition: transform .25s ease-out;
            transition: transform .25s ease-out,-webkit-transform .25s ease-out;
        }

        .modal__btn:focus, .modal__btn:hover {
            -webkit-transform: scale(1.05);
            transform: scale(1.05);
        }

        .modal__btn-primary {
            background-color: #00449e;
            color: #fff;
        }



        /**************************\
          Demo Animation Style
        \**************************/
        @keyframes mmfadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        @keyframes mmfadeOut {
            from { opacity: 1; }
            to { opacity: 0; }
        }

        @keyframes mmslideIn {
            from { transform: translateY(15%); }
            to { transform: translateY(0); }
        }

        @keyframes mmslideOut {
            from { transform: translateY(0); }
            to { transform: translateY(-10%); }
        }

        .micromodal-slide {
            display: none;
        }

        .micromodal-slide.is-open {
            display: block;
        }

        .micromodal-slide[aria-hidden="false"] .modal__overlay {
            animation: mmfadeIn .3s cubic-bezier(0.0, 0.0, 0.2, 1);
        }

        .micromodal-slide[aria-hidden="false"] .modal__container {
            animation: mmslideIn .3s cubic-bezier(0, 0, .2, 1);
        }

        .micromodal-slide[aria-hidden="true"] .modal__overlay {
            animation: mmfadeOut .3s cubic-bezier(0.0, 0.0, 0.2, 1);
        }

        .micromodal-slide[aria-hidden="true"] .modal__container {
            animation: mmslideOut .3s cubic-bezier(0, 0, .2, 1);
        }

        .micromodal-slide .modal__container,
        .micromodal-slide .modal__overlay {
            will-change: transform;
        }
    </style>
    <c:if test="${requestScope.periodInvalid != null}">
        <div class="periodInvalid"></div>
    </c:if>
    <c:if test="${requestScope.periodEmpty != null}">
        <div class="periodEmpty"></div>
    </c:if>
    <c:if test="${requestScope.nameEmpty != null}">
        <div class="nameEmpty"></div>
    </c:if>
    <c:if test="${requestScope.periodFuture != null}">
        <div class="periodFuture"></div>
    </c:if>

    <input type="hidden" id="currentPet" value="${sessionScope.theme.getSmallPetFee()}"/>
    <input type="hidden" id="small" value="${sessionScope.theme.getSmallPetFee()}"/>
    <input type="hidden" id="medium" value="${sessionScope.theme.getMediumPetFee()}"/>
    <input type="hidden" id="large" value="${sessionScope.theme.getFee()}"/>
    <input type="hidden" id="extra" value="${sessionScope.theme.getExtraFee()}"/>    
    <input type="hidden" id="userID" value="${sessionScope.user.getUserID()}"/>
    <input type="hidden" id="userRole" value="${sessionScope.user.getRole()}"/>

    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if> 
        <a class="section" href="/bookings">Bookings</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">Edit Booking</a>
    </div>
    <div class="ui services">
        <div class="modal micromodal-slide" id="modal-1" aria-hidden="true">
            <div class="modal__overlay" tabindex="-1" data-micromodal-close>
                <div class="modal__container" role="dialog" aria-modal="true" aria-labelledby="modal-1-title">
                    <header class="modal__header">
                        <h2 class="modal__title" id="modal-1-title">
                            Update Booking Confirmation
                        </h2>
                        <button class="modal__close" aria-label="Close modal" data-micromodal-close></button>
                    </header>
                    <main class="modal__content" id="modal-1-content">
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
        <h4 class="ui horizontal inverted divider" style="margin-top: 0px; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Edit Booking</span>
                <%@include file="../../icons/register.svg" %>
            </div>
        </h4>
        <div class="user-edit ${sessionScope.user.getRole() == 'pet owner' ? 'user-role' : ''}">
            <div class="container-left ${sessionScope.user.getRole() == 'pet owner' ? 'user-role' : ''}">
                <form action="/bookings/edit" name="f1" method="post" enctype="multipart/form-data"> 
                    <input type="hidden" id="fee" value="${sessionScope.theme.getFee()}"/>
                    <div class="form-field">
                        <label for="id">ID: </label>
                        <input type="text" name="id" id="id" value="${requestScope.booking.getId()}" 
                               style="font-weight: bold;border: none;position: relative;padding: 0" 
                               readonly
                               />
                    </div>
                    <div class="form-field">
                        <label for="owner">Owner: </label>
                        <c:if test="${sessionScope.user.getRole() == 'manager'}">
                            <div style="justify-content: flex-start !important;"> 
                                <input type="text" name="owner" id="owner" value="${requestScope.booking.getUserEmail()}" style="font-weight: bold;border: none; width:auto"/>             
                                <span data-tooltip="Edit Owner" data-position="top center" style="cursor: pointer;">
                                    <%@include file="../../icons/3d.svg"%>
                                </span>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.user.getRole() == 'pet owner'}">
                            <input type="hidden" name="owner" id="owner" value="${booking.getUserEmail()}" 
                                   STYLE="border:none;padding:0" readonly/>
                            <span>${booking.getUserName()} ( ${booking.getUserEmail()} )</span>
                        </c:if>
                        <c:if test="${sessionScope.user.getRole() == 'employee'}">
                            <input type="hidden" name="owner" id="owner" value="${booking.getUserEmail()}" 
                                   <a href="/view?id=${booking.getUserID()}" style="color: var(--theme)">${booking.getUserName()} ( ${booking.getUserEmail()} )</a>
                        </c:if>
                    </div>
                    <div class="form-field">
                        <label for="pet">Pet: </label>
                        <c:if test="${sessionScope.user.getRole() != 'employee'}">
                            <select name="pet" class="ui dropdown" id="owner-pets">
                                <c:forEach items="${pets}" var="pet">
                                    <option value="${pet.getID()}" ${pet.getID() == booking.getPetId() ? "selected='selected'" : ''}>
                                        ${pet.getName()} ( ${pet.getSize()} )
                                    </option>
                                </c:forEach>
                            </select>
                        </c:if>
                        <c:if test="${sessionScope.user.getRole() == 'employee'}">
                            <input type="hidden" name="pet" value="${booking.getPetId()}"/>
                            <a href="/pets/view?id=${booking.getPetId()}" style="color: var(--theme)" id="owner-pets">${booking.getPetName()} ( ${booking.getPetId()} )</a>
                        </c:if>
                    </div>
                    <div class="form-field">
                        <label for="createdAt">Period: </label>
                        <div>
                            <input id="createdAt" type="text" name="createdAt" placeholder="Start date" value="${requestScope.booking.getDeparture()}">
                            <span style="margin: 0px 10px;">~</span>
                            <input id="createdTo" type="text" name="createdTo" placeholder="End date" value="${requestScope.booking.getArrival()}">
                        </div>
                    </div>
                    <div class="form-field">
                        <label for="notes">Notes: </label>
                        <textarea id="notes" name="notes" rows="4" cols="50">${requestScope.booking.getOwnerNotes().trim()}</textarea>
                    </div>
                    <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                        <div class="form-field">
                            <label for="employeeNotes">Employee Notes: </label>
                            <textarea id="employeeNotes" name="employeeNotes" rows="4" cols="50" style="text-align: left !important;">${requestScope.booking.getManagerNotes().trim()}</textarea>
                        </div>                  
                        <div class="form-field">
                            <label for="cancellationNotes">Cancellation Notes: </label>
                            <textarea id="cancellationNotes" name="cancellationNotes" rows="4" cols="50">${requestScope.booking.getCancelNotes().trim()}</textarea>
                        </div>
                    </c:if>
                    <div class="form-field">
                        <label for="status">Status:  </label>
                        <select class="ui dropdown" name="status" id="status-selector">
                            <c:if test="${sessionScope.user.getRole() != 'employee'}">
                                <option value="booked" ${requestScope.booking.getStatus() == 'booked' ? "selected='selected'" : ""}>Booked</option>
                            </c:if>
                            <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                                <option value="progress" ${requestScope.booking.getStatus() == 'progress' ? "selected='selected'" : ""}>In Progress</option>
                                <option value="completed" ${requestScope.booking.getStatus() == 'completed' ? "selected='selected'" : ""}>Completed</option>
                            </c:if>
                            <option value="cancelled" ${requestScope.booking.getStatus() == 'cancelled' ? "selected='selected'" : ""}>Cancelled</option>               
                        </select>
                    </div>
                    <div class="form-field">
                        <label for="notes">Extra Services: </label>
                        <div style="justify-content: flex-start">
                            <input type="checkbox" name="extra" value="extra" style="display: inline-block; width: auto" ${requestScope.booking.isExtraServices() ? "checked='checked'" : ''}/>
                            <span style="color: var(--theme); margin-left: 10px">(Include Grooming, Exercising, Teeth Cleaning, Nail Clipping)</span>
                        </div>
                    </div>
                    <div class="form-field fee-field">
                        <label for="fee">Total Fee: </label>
                        <input type="text" name="fee" id="totalFee" value="$${requestScope.booking.getFee()}" style="border:none;font-weight: bold" readonly/>
                    </div>
                    <c:if test="${sessionScope.user.getRole() == 'manager'}">
                        <div class="form-field">
                            <label for="receipt">Receipt: </label>
                            <input type="file" class="receipt" name="receipt" accept="image/*"/>
                        </div>
                    </c:if>
                    <div class="image-preview" style="width: 300px;">
                        <img src="" alt="Image Preview" class="image--preview" style="width: 100%;display: none"/>
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
            <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
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
            </c:if>
        </div>
    </div>    
</t:wrapper>
<script src="../../js/bookingEdit.js"></script>
<script>

    window.addEventListener('DOMContentLoaded', function () {
        const fileUpload = document.querySelector('input[type=file]');
        const imagePreview = document.querySelector('.image--preview');

        if (fileUpload) {
            fileUpload.addEventListener('change', function () {
                const file = this.files[0];

                if (file) {
                    const reader = new FileReader();

                    reader.onload = function () {
                        imagePreview.setAttribute("src", this.result);
                        imagePreview.style.display = 'block';
                    }

                    reader.readAsDataURL(file);
                }
            })
        }

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

        if (document.querySelector('.periodEmpty') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'WARNING',
                    message: 'Period Range Can\'t be Empty !',
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
    })
</script>