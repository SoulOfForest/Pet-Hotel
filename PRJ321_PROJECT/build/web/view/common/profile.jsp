<%-- 
    Document   : userEdit
    Created on : Jun 29, 2020, 5:56:45 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </style>
    <div class="ui breadcrumb">  
         <a class="section" href="/${sessionScope.user.getRole() == 'pet owner' ? 'bookings' : 'statistics'}">Home</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">Edit Profile</a>
    </div>
    <div class="ui services">
        <h3>Edit Profile</h3>
        <div class="user-edit">
            <div class="container-left">
                <form action="/profile" name="f1" method="post" enctype="multipart/form-data">      
                    <div class="form-field">
                        <label for="email">Email: </label>
                        <input type="text" name="email" id="email" value="${sessionScope.user.getEmail()}" STYLE="border:none; font-weight: bold" readonly/>
                    </div>
                    <div class="form-field">
                        <label for="firstName">First Name: </label>
                        <input type="text" name="firstName" id="firstName" value="${sessionScope.user.getFirstName()}"/>
                    </div>
                    <div class="form-field">
                        <label for="lastName">Last Name: </label>
                        <input type="text" name="lastName" id="lastName" value="${sessionScope.user.getLastName()}"/>
                    </div>
                    <div class="form-field">
                        <label for="country">Country:  </label>
                        <select name="country" id="country" class="ui dropdown" style="width: 100% !important;">
                            <option value="${sessionScope.user.getCountry()}">${sessionScope.user.getCountry()}</option>
                        </select>
                    </div>
                    <div class="form-field">
                        <label for="age">Age:  </label>
                        <input type="number" name="age" id="age" value="${sessionScope.user.getAge()}"/>
                    </div>
                    <div class="form-field">
                        <label for="gender">Gender:  </label>
                        <select class="ui dropdown" name="gender" id="gender" style="width: 100% !important;">
                            <option value="Female" ${!sessionScope.user.isGender() ? 'selected="selected"': ""}>Female</option>
                            <option value="Male" ${sessionScope.user.isGender() ? 'selected="selected"': ""}>Male</option>
                        </select>
                    </div>
                    <div class="form-field">
                        <label for="avatar">Avatar:  </label>
                        <input type="file" name="avatar" id="avatar" accept="image/*">
                    </div>
                    <div class="image-preview">
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

                <c:if test="${sessionScope.user.getAvatar() != null}"> 
                    <img src="/retrieveImage?id=${sessionScope.user.getAvatar()}" alt="user avatar" width="300" height="300" />
                </c:if>                      
                <c:if test="${sessionScope.user.getAvatar() == null && sessionScope.user.isGender()}"> 
                    <img src="../../images/male_default.jpg" alt="user avatar" width="300" height="300"/>
                </c:if>
                <c:if test="${sessionScope.user.getAvatar() == null && !sessionScope.user.isGender()}"> 
                    <img src="../../images/female_default.jpg" alt="user avatar" width="300" height="300" />
                </c:if>
            </div>
        </div>
    </div>    
</t:wrapper>
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
