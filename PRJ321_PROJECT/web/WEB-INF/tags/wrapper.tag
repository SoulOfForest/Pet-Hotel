<%-- 
    Document   : wrapper
    Created on : Jun 27, 2020, 4:41:30 PM
    Author     : ViruSs0209
--%>

<%@tag import="model.User"%>
<%@tag import="model.Feedback"%>
<%@tag import="model.MenuItem"%>
<%@tag import="java.util.Arrays"%>
<%@tag import="java.util.ArrayList"%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="message"%>

<%-- any content can be specified here e.g.: --%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Document</title>

        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Parisienne&display=swap" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../../css/jquery.datetimepicker.min.css"/>
        <link href="../../css/themes/${sessionScope.setting.getTheme()}.css" rel="stylesheet" type="text/css" />
        <link href="../../css/semantic.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="../../css/iziToast.min.css"/>
        <link rel="stylesheet" href="../../css/jquery.sweet-modal.min.css" />
    </head>
    <style>
        .sidebar-header svg {
            margin-bottom: 20px;
        }
    </style>
    <body>
        <input type="hidden" id="current" value="${sessionScope.user.getRole() == 'pet owner' ? 'petowner' : sessionScope.user.getRole()}" />
        <input type="hidden" id="USER_EMAIL" value="${sessionScope.user.getEmail()}" />
        <%
            ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>(Arrays.asList(
                    new MenuItem("Statistics", "graph.svg", "/statistics"),
                    new MenuItem("Users", "add.svg", "/users"),
                    new MenuItem("Audit Logs", "search.svg", "/logs"),
                    new MenuItem("Settings", "settings.svg", "/settings"),
                    new MenuItem("Pets", "logo.svg", "/pets"),
                    new MenuItem("Bookings", "register.svg", "/bookings"),
                    new MenuItem("FeedBacks", "positive-charges.svg", "/feedbacks")
            ));

            User user = (User) request.getSession().getAttribute("user");
            ArrayList<Feedback> feedbacks = (ArrayList<Feedback>) request.getSession().getAttribute("feedbacks");

            String currentMenu = "users";

            if (user.getRole().equals("employee") || user.getRole().equals("pet owner")) {
                currentMenu = "bookings";
            }

            if (request.getAttribute("currentMenu") != null) {
                currentMenu = (String) request.getAttribute("currentMenu");
            }
        %>
        <div class="page-wrapper" style="height: 100%;">     
            <div id="theme" style="display: none;">
                ${sessionScope.setting.getTheme()}
            </div>
            <div class="ui menu side active">
                <a class="sidebar-header" style="color: var(--theme);margin-bottom: 10px !important" href="/index">
                    <c:import url="/icons/dog.svg"/>
                    Pet Hotel
                </a>
                <%
                    for (MenuItem m : menuItems) {

                        String url = "/icons/";

                        url += m.getIcon();

                        if (user.getRole().equals("pet owner")
                                && (m.getName().equals("Audit Logs") || m.getName().equals("Settings") || m.getName().equals("Users") || m.getName().equals("Statistics"))) {

                %>              
                <%        } else if (user.getRole().equals("employee")
                        && (m.getName().equals("Audit Logs") || m.getName().equals("Settings") || m.getName().equals("Statistics"))) {

                } else {
                %>
                <a class="item <%= m.getName().equalsIgnoreCase(currentMenu) ? "active" : ""%>" href="<%= m.getHref()%>">
                    <c:import url="<%= url%>"/>
                    <span><%= m.getName()%></span>
                    <%
                        if (m.getName().equalsIgnoreCase("feedbacks") && feedbacks != null && feedbacks.size() != 0) {
                    %>
                    <span style="margin-left: 20px;padding: 1px;background-color: var(--theme);border-radius: 50%;width: 20px;height: 20px;color: white;display: flex;align-items: center;justify-content: center;font-size: 14px;">
                        ${sessionScope.feedbacks.size()}
                    </span>
                    <%
                        }
                    %>
                </a>
                <%
                        }
                    }
                %>
            </div>
            <div class="content-wrapper">
                <div class="layout-header">
                    <div class="toggle-sidebar">
                        <input type="checkbox" name="toggle" id="toggle" checked />
                    </div>

                    <div class="currentDate" style="display: flex;">             
                        <div class="clock-icon" style="margin-right: 10px;">

                        </div>
                        <div class="show-currentDate" id="currentDate">

                        </div>
                    </div>
                    <div class="user-info">
                        <div class="user-dropdown">
                            <div class="user-avatar">
                                <c:if test="${sessionScope.user.getAvatar() != null}"> 
                                    <img src="/retrieveImage?id=${sessionScope.user.getAvatar()}" alt="user avatar" />
                                </c:if>                      
                                <c:if test="${sessionScope.user.getAvatar() == null && sessionScope.user.isGender()}"> 
                                    <img src="../../images/male_default.jpg" alt="user avatar" />
                                </c:if>
                                <c:if test="${sessionScope.user.getAvatar() == null && !sessionScope.user.isGender()}"> 
                                    <img src="../../images/female_default.jpg" alt="user avatar" />
                                </c:if>
                            </div>
                            <%
                                User currentUser = (User) request.getSession().getAttribute("user");
                            %>
                            <div class="text"><%= currentUser.getUserName()%></div>
                            <div class="ui dropdown dropdown">
                                <%@include file="../../icons/dropdown.svg" %>
                                <div class="menu">
                                    <a class="item" href="/profile"><%@include file="../../icons/profile.svg" %> Edit Profile</a>
                                    <a class="item" href="/logout"><%@include file="../../icons/logout.svg" %> Sign Out</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="layout-content">
                    <div class="wrapper">
                        <jsp:doBody/>
                    </div>
                </div>
            </div>            
            <script
                src="https://code.jquery.com/jquery-3.1.1.min.js"
                integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
                crossorigin="anonymous"
            ></script>
            <script src="../../js/semantic.min.js"></script>
            <script src="../../js/jquery.datetimepicker.full.min.js"></script>       
            <script src="../../js/iziToast.min.js"></script>
            <script src="../../js/FileSaver.min.js"></script>
            <script src="../../js/xlsx.full.min.js"></script>
            <script src="../../js/jquery.sweet-modal.min.js"></script>
            <script src="../../api/countriesAPI.js"></script>   
            <script src="https://unpkg.com/micromodal/dist/micromodal.min.js"></script> 
            <script src="../../js/common.js"></script>
    </body>
</html>
