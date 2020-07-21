<%-- 
    Document   : audit
    Created on : Jun 27, 2020, 5:23:25 PM
    Author     : ViruSs0209
--%>

<%@page import="model.Setting"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<link href="../../css/jquery.minicolors.css" rel="stylesheet" type="text/css"/>
<link href="../../css/settings.css" rel="stylesheet" type="text/css"/>
<style>
    .fee-setting {
        grid-template-columns: 1fr 4fr !important;
    }
</style>
<t:wrapper>
    <c:if test="${requestScope.updatedSuccess != null}">
        <div class="updatedSuccess"></div>
    </c:if>
    <div class="ui breadcrumb">
        <a class="section" href="/statistics">Home</a>
        <%@include file="../../icons/next.svg" %>
        <a class="section active">Settings</a>
    </div>
    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 0px;color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Settings</span>
                <%@include file="../../icons/settings.svg" %>
            </div>
        </h4>
        <div class="ui utilities">        
            <a class="ui button" href="/logs">
                <div>
                    <%@include file="../../icons/search.svg" %>
                    <span>Audit Logs</span>
                </div>
            </a>
        </div>
        <div class="user-settings">
            <form action="/settings" method="POST">
                <div class="fee-setting form-field">
                    <label for="fee-setting" style="display: flex; align-items: center">
                        <%@include file="../../icons/fee.svg"%>  
                        <span style="margin-left: 10px;">Small Pet Fee: </span>
                    </label>
                    <input type="number" name="fee" id="fee" value="${sessionScope.setting.getSmallPetFee()}"/>
                    <span>
                        
                    </span>
                </div>
                <div class="fee-setting form-field">
                    <label for="fee-setting" style="display: flex; align-items: center">
                        <%@include file="../../icons/fee.svg" %> 
                        <span style="margin-left: 10px;">Medium Pet Fee: </span>
                    </label>
                    <input type="number" name="mediumFee" id="mediumFee" value="${sessionScope.setting.getMediumPetFee()}"/>
                </div>
                <div class="fee-setting form-field">
                    <label for="fee-setting" style="display: flex; align-items: center">
                        <%@include file="../../icons/fee.svg" %>  
                        <span style="margin-left: 10px;">Large Pet Fee: </span>
                    </label>
                    <input type="number" name="largeFee" id="fee" value="${sessionScope.setting.getFee()}"/>
                </div>
                <div class="fee-setting form-field">
                    <label for="fee-setting" style="display: flex; align-items: center">
                        <%@include file="../../icons/fee.svg" %> 
                        <span style="margin-left: 10px;">Extra Fee:</span>
                    </label>
                    <input type="number" name="extraFee" id="fee" value="${sessionScope.setting.getExtraFee()}"/>
                </div>
                <div class="fee-setting form-field">
                    <label for="fee-setting">
                        <%@include file="../../icons/available.svg" %> 
                        <span style="margin-left: 10px;">Capacity: </span>
                    </label>
                    <input type="number" name="capacity" id="capacity" value="${sessionScope.setting.getCapacity()}"/>
                </div>
                <div class="theme-setting fee-setting form-field">
                    <label for="theme">
                        <%@include file="../../icons/paint.svg" %> 
                        <span style="margin-left: 10px;">Theme: </span>
                    </label>
                    <div class="theme-selector" style="
                         display: flex;
                         align-items: center;
                         justify-content: flex-start;
                         ">
                        <select class="ui dropdown" id="theme" name="theme">
                            <c:forEach items="${requestScope.themes}" var="theme">
                                <option class="item" id="${theme.getColor()}" value="${theme.getId()}" ${sessionScope.setting.getTheme() == theme.getId() ? "selected='selected'" : ""}>
                                    <div>${theme.getTheme()}</div>
                                </option>             
                            </c:forEach>
                        </select>
                        <div class="theme-preview" style="width:40px;height:40px;border-radius: 20px;margin-left: 20px;background-color: ${currentTheme}"></div>
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
</t:wrapper>
<script src="../../js/jquery.minicolors.js"></script>
<script src="../../js/settings.js"></script>
<script>
    window.addEventListener('DOMContentLoaded', function () {
        if (document.querySelector('.updatedSuccess') != null) {
            setTimeout(() => {
                iziToast.success({
                    title: 'OK',
                    message: 'Update Settings Successfully !',
                });
            }, 50);
        }
    })
</script>