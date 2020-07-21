<%-- 
    Document   : audit
    Created on : Jun 27, 2020, 5:23:25 PM
    Author     : ViruSs0209
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<style>
    .ui.dropdown.selection {
        width: 100% !important;
    }
    .user-form.user-role {
        display: inline-block;
        width: 50%;
    }
    .user-search.user-role {
        display: flex;
        justify-content: center;
        align-items: center;
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
<%

    ArrayList<User> searchedUsers = new ArrayList<User>();
    if (request.getSession().getAttribute("searchedUsers") != null) {
        searchedUsers = (ArrayList<User>) request.getSession().getAttribute("searchedUsers");
    }
%>
<t:wrapper>
    <c:if test="${requestScope.ownerEmpty != null}">
        <div class="ownerEmpty"></div>
    </c:if>
    <c:if test="${requestScope.breedEmpty != null}">
        <div class="breedEmpty"></div>
    </c:if>
    <c:if test="${requestScope.nameEmpty != null}">
        <div class="nameEmpty"></div>
    </c:if>
    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if> 
        <a class="section" href="/pets">Pets</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">New Pet</a>
    </div>
    <div class="ui services">
        <div class="modal micromodal-slide" id="modal-1" aria-hidden="true">
            <div class="modal__overlay" tabindex="-1" data-micromodal-close>
                <div class="modal__container" role="dialog" aria-modal="true" aria-labelledby="modal-1-title">
                    <header class="modal__header">
                        <h2 class="modal__title" id="modal-1-title">
                            Adding Pet Confirmation
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
                <span style="margin-right: 10px">New Pet</span>
                <%@include file="../../icons/new.svg" %>
            </div>
        </h4>
        <div class="user-search ${sessionScope.user.getRole() == 'pet owner' ? 'user-role': ''}">
            <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                <h4>Search For Owner</h4>
            </c:if>
            <div class="user-form ${sessionScope.user.getRole() == 'pet owner' ? 'user-role': ''}">

                <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                    <div class="container left">

                        <div class="form-field">
                            <label for="email">Owner:  </label>
                            <input type="text" name="email" id="email"/>
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
                    </div>
                </c:if>                

                <div class="container right">     
                    <form action="/pets" method="POST" enctype="multipart/form-data" autocomplete="off">
                        <c:if test="${sessionScope.user.getRole() != 'pet owner'}">
                            <div class="form-field">
                                <label for="owner">Owner: </label>
                                <input type="text" name="owner" id="owner" readonly="true"/>
                            </div>
                        </c:if>
                        <c:if test="${sessionScope.user.getRole() == 'pet owner'}">
                            <div class="form-field">
                                <label for="owner">Owner: </label>
                                <input type="text" name="owner" id="owner" value="${sessionScope.user.getEmail()}" readonly="true" style="border: none; font-weight: bold"/>
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
                        <div class="form-field">
                            <label for="type">Type: </label>
                            <select class="ui dropdown" name="type" id="type">
                                <option value="dog">Dog</option>
                                <option value="cat">Cat</option>
                            </select>
                        </div>
                        <div class="form-field">
                            <label for="size">Size: </label>
                            <select class="ui dropdown" name="size" id="size">
                                <option value="small">Small</option>
                                <option value="medium">Medium</option>
                                <option value="large">Large</option>
                            </select>
                        </div>

                        <div class="form-field">
                            <label for="avatar">Avatar:  </label>
                            <input type="file" name="avatar" id="avatar" accept="image/*">
                        </div>
                        <div class="image-preview" style="width: 300px;">
                            <img src="" alt="Image Preview" class="image--preview" style="width: 100%;display: none"/>
                        </div>
                        <div class="form-action">
                            <button class="search-action btn-action" type="submit">
                                <div>                              
                                    <svg id="Capa_1" enable-background="new 0 0 512.007 512.007" height="16" viewBox="0 0 512.007 512.007" width="16" xmlns="http://www.w3.org/2000/svg"><g><path d="m511.927 126.537c-.279-2.828-1.38-5.666-3.315-8.027-.747-.913 6.893 6.786-114.006-114.113-2.882-2.882-6.794-4.395-10.612-4.394-9.096 0-329.933 0-338.995 0-24.813 0-45 20.187-45 45v422c0 24.813 20.187 45 45 45h422c24.813 0 45-20.187 45-45 .001-364.186.041-339.316-.072-340.466zm-166.927-96.534v98c0 8.271-6.729 15-15 15h-19v-113zm-64 0v113h-139c-8.271 0-15-6.729-15-15v-98zm64 291h-218v-19c0-8.271 6.729-15 15-15h188c8.271 0 15 6.729 15 15zm-218 161v-131h218v131zm355-15c0 8.271-6.729 15-15 15h-92c0-19.555 0-157.708 0-180 0-24.813-20.187-45-45-45h-188c-24.813 0-45 20.187-45 45v180h-52c-8.271 0-15-6.729-15-15v-422c0-8.271 6.729-15 15-15h52v98c0 24.813 20.187 45 45 45h188c24.813 0 45-20.187 45-45v-98h2.787l104.213 104.214z"/></g></svg>
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
<script src="../../js/newPet.js"></script>
<script>
    window.addEventListener('DOMContentLoaded', function () {
        const fileUpload = document.querySelector('input[type=file]');
        const imagePreview = document.querySelector('.image--preview');

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
        // Check For Empty Input Field
        if (document.querySelector('.ownerEmpty') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'Warning',
                    message: 'Owner Can\'t Be Empty !',
                });
            }, 50);
        }

        if (document.querySelector('.breedEmpty') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'Warning',
                    message: 'Breed Can\'t Be Empty !',
                });
            }, 50);
        }

        if (document.querySelector('.nameEmpty') != null) {
            setTimeout(() => {
                iziToast.warning({
                    title: 'Warning',
                    message: 'Name Can\'t Be Empty !',
                });
            }, 50);
        }


    })
</script>
