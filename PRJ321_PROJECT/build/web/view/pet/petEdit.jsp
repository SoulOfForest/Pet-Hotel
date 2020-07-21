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
    <div class="ui breadcrumb">
        <c:if test="${sessionScope.user.getRole() == 'manager'}">
            <a class="section" href="/statistics">Home</a>
            <%@include file="../../icons/next.svg" %>
        </c:if> 
        <a class="section" href="/pets">Pets</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">Edit Pet</a>
    </div>
    <div class="ui services">
        <div class="modal micromodal-slide" id="modal-1" aria-hidden="true">
            <div class="modal__overlay" tabindex="-1" data-micromodal-close>
                <div class="modal__container" role="dialog" aria-modal="true" aria-labelledby="modal-1-title">
                    <header class="modal__header">
                        <h2 class="modal__title" id="modal-1-title">
                            Update Pet Confirmation
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
        <h3>Edit Pet</h3>
        <div class="user-edit">
            <div class="container-left">
                <form action="/pets/edit" name="f1" method="post" enctype="multipart/form-data">      
                    <div class="form-field">
                        <label for="id">ID: </label>
                        <input type="text" name="id" id="id" value="${requestScope.pet.getID()}" style="font-weight: bold;border: none;" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="owner">Owner: </label>
                        <input type="text" name="owner" id="owner" value="${requestScope.pet.getOwner()}"/>
                    </div>
                    <div class="form-field">
                        <label for="name">Name: </label>
                        <input type="text" name="name" id="name" value="${requestScope.pet.getName()}"/>
                    </div>
                    <div class="form-field">
                        <label for="type">Type: </label>
                        <select class="ui dropdown" name="type" id="type">
                            <option value="dog" ${requestScope.pet.getType() == 'dog' ? "selected='selected'" : ""}>Dog</option>
                            <option value="cat" ${requestScope.pet.getType() == 'cat' ? "selected='selected'" : ""}>Cat</option>
                        </select>
                    </div>
                    <div class="form-field">
                        <label for="breed">Breed: </label>
                        <input type="text" name="breed" id="breed" value="${requestScope.pet.getBreed()}"/>
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
            <div class="container-right">
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
<script src="../../js/petEdit.js"></script>
<script>
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
</script>