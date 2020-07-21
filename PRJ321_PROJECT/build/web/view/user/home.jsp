<%-- 
    Document   : home
    Created on : Jun 14, 2020, 12:22:48 AM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<link href="../../css/admin.css" rel="stylesheet" type="text/css"/>
<link href="../../css/Chart.min.css" type="text/css" rel="stylesheet"/>
<style>
    .charts {
        display: grid;
        grid-template-columns: 1fr 1fr;
        margin-top: 40px;
        grid-gap: 20px;
    }
    .ui.services {
        padding: 20px !important;
    }

    .ui.services h4 svg {
        fill: var(--theme);
    }

    .booked {
        background-color: rgb(16, 142, 233);
        color: white;
        border-color: rgb(16, 142, 233) !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }
    .progress {
        background-color: rgb(135, 208, 104);
        color: white;
        border-color: rgb(135, 208, 104) !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }

    .completed {
        background-color: rgba(250, 250, 250, 1);
        color: black;
        border-color: #d9d9d9 !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }
    .cancelled {
        background-color: rgb(255, 85, 0);
        color: white;
        border-color: rgb(255, 85, 0) !important;
        font-size: 12px !important;
        padding: 3px 8px !important;
    }

    .chart {
        border: 1px solid #d6d6d6;
        padding: 20px;
        border-radius: 6px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        text-align: center;
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
    }
    .chart:hover {
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }

    td[data-label=Status] {
        text-align: left !important;
    }

    .statistics {
        display: grid;
        grid-template-columns: 1fr 1fr 1fr 1fr;
        grid-gap: 0px 10px;
        margin-top: 20px;
    }

    .statistics  > div {
        padding: 10px 30px;
        border: 1px solid #d6d6d6;
        border-radius: 6px;
        box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        transition: all 0.3s cubic-bezier(.25,.8,.25,1);
        
    }
    
    .statistics  div.contents .icon span {
        padding: 5px;
        border: 1px solid #f5365c;
        display: flex;
        justify-content: center;
        align-items: center;
        width: 50px;
        height: 50px;
        border-radius: 50%;
        background-color: #f5365c;
        box-shadow: 0 0 2rem 0 rgba(136, 152, 170, .15) !important;
    }
    
    .statistics div.contents > div {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-top: 5px;   
    }
    
    .statistics div.contents .header {
        color: #8898aa;
        font-weight: bold;
        text-transform: uppercase;
    }
    
    .statistics div.contents .header strong {
        color: black !important;
    }
    
    .statistics  div.contents .icon span svg {
        fill: white !important;
        width: 20px;
        height: 20px;
    }
    
    .statistics  > div:hover {
        box-shadow: 0 14px 28px rgba(0,0,0,0.25), 0 10px 10px rgba(0,0,0,0.22);
    }
    
    .totalUsers .header .icon span {
        background-color: #fb6340 !important;
        border-color: #fb6340 !important;
    }
    
    .totalFees .header .icon span {
        background-color: #ffd600 !important;
        border-color: #ffd600 !important;
    }
    
    .bookingsByWeek .header .icon span {
        background-color: #11cdef !important;
        border-color: #11cdef !important;
    }

</style>
<t:wrapper>
    <div class="ui breadcrumb">
        <a class="section" href="/statistics">Home</a>
        <%@include file="../../icons/next.svg" %>
    </div>
    <div class="ui services">
        <h4 class="ui horizontal inverted divider" style="margin-top: 30px; color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Analytics</span>
                <%@include file="../../icons/statistics.svg" %>
            </div>
        </h4>
        <div class="statistics">
            <div class="totalBookings">
                <div class="contents">             
                    <div class="header">
                        <p>
                            Total Bookings
                        </p>
                        <div class="icon">                  
                            <span>
                                <%@include file="../../icons/register.svg" %>
                            </span>
                        </div> 
                    </div>
                    <div class="rate"></div>
                </div>           
            </div>
            <div class="totalUsers">
                <div class="contents">             
                    <div class="header">
                        <p>
                            New Users
                        </p>
                        <div class="icon">                  
                            <span>
                                <%@include file="../../icons/user.svg" %>
                            </span>
                        </div> 
                    </div>
                    <div class="rate"></div>
                </div>    
            </div>
            <div class="totalFees">
                <div class="contents">             
                    <div class="header">
                        <p>
                            Total Fees
                        </p>
                        <div class="icon">                  
                            <span>
                                <%@include file="../../icons/fee.svg" %>
                            </span>
                        </div> 
                    </div>
                    <div class="rate"></div>
                </div>   
            </div>
            <div class="bookingsByWeek">
                <div class="contents">             
                    <div class="header">
                        <p>
                            Total Fees By Weeks
                        </p>
                        <div class="icon">                  
                            <span>
                                <%@include file="../../icons/fee.svg" %>
                            </span>
                        </div> 
                    </div>
                    <div class="rate"></div>
                </div>   
            </div>
        </div>
        <div class="charts">
            <div class="chart">
                <div class="chart-container" style="position: relative; height:40vh; width:100%">
                    <canvas id="fee-realtime"></canvas>
                </div>
            </div>
            <div class="chart" style="position: relative;">
                <div class="chart-container" style="position: relative; height:40vh; width:100%">
                    <canvas id="feeChart"></canvas>
                </div>
            </div>
            <div class="chart">
                <div class="chart-container" style="position: relative; height:40vh; width:100%">
                    <canvas id="usersChart"></canvas>
                </div>
            </div>
            <div class="chart">
                <div class="chart-container" style="position: relative; height:40vh; width:100%">
                    <canvas id="bookingsChart"></canvas>
                </div>
            </div>
            <div class="page-visit">
                <table class="ui celled table" style="margin-top: 0px !important">
                    <thead>
                        <tr>
                            <th colspan="3" style="text-align: left !important;font-weight: bold; font-size: 18px;background-color: #fff">Page visits</th>
                        </tr>
                        <tr>
                            <th>Page Name</th>
                            <th>Visitor</th>
                            <th>Bounce Rate</th>                           
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="chart" style="display: flex; align-items: center;">
                <div class="chart-container" style="position: relative; height:40vh; width:100%">
                    <canvas id="bookingStatus"></canvas>
                </div>
            </div>
        </div>
                       
        <h4 class="ui horizontal inverted divider" style="margin-top: 40px;color:black">
            <div style="display: flex; justify-content: center; align-items: center">
                <span style="margin-right: 10px">Booking Recently</span>
                <%@include file="../../icons/currentBooking.svg" %>
            </div>
        </h4>
        <div class="ui current-Booking" style="margin-top: 40px;">      
            <div class="search-display" style="position: relative;">
                <table class="ui celled table booking-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Owner</th>
                            <th>Pet</th>
                            <th>Arrival</th>
                            <th>Departure</th>
                            <th>Status</th>
                            <th>Extra Services</th>
                            <th>Total Fee</th>
                            <th>Created At</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>

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
        </div>
    </div>
</t:wrapper>
<script src="../../js/Chart.min.js" type="text/javascript"></script>
<script src="../../js/home.js"></script>