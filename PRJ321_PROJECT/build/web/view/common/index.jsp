<%-- 
    Document   : index
    Created on : Jul 14, 2020, 12:11:09 PM
    Author     : ViruSs0209
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Dogger &mdash; Website Template by Colorlib</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">    
        <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700, 900|Vollkorn:400i" rel="stylesheet">
        <link rel="stylesheet" href="../../fonts/icomoon/style.css">

        <link rel="stylesheet" href="../../css/index/bootstrap.min.css">
        <link rel="stylesheet" href="../../css/index/jquery-ui.css">
        <link rel="stylesheet" href="../../css/index/owl.carousel.min.css">
        <link rel="stylesheet" href="../../css/index/owl.theme.default.min.css">
        <link rel="stylesheet" href="../../css/index/owl.theme.default.min.css">

        <link rel="stylesheet" href="../../css/index/jquery.fancybox.min.css">

        <link rel="stylesheet" href="../../css/index/bootstrap-datepicker.css">

        <link rel="stylesheet" href="../../fonts/flaticon/font/flaticon.css">

        <link rel="stylesheet" href="../../css/index/aos.css">

        <link rel="stylesheet" href="../../css/iziToast.min.css"/>

        <link rel="stylesheet" href="../../css/index/style.css">

        <style>
            .nav-link svg {
                fill: #00bcd4 !important;
            }

            .text-primary svg {
                fill: #337898 !important;
            }

            .site-menu ul li a {
                padding: 10px;
            }

            .user-avatar img {
                width: 40px;
                height: 40px;
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
            }

            .modal__header .modal__close:before { content: "\2715"; }

            .modal__content {
                margin-top: 2rem;
                margin-bottom: 2rem;
                line-height: 1.5;
                color: rgba(0,0,0,.8);
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
    </head>
    <body data-spy="scroll" data-target=".site-navbar-target" data-offset="300" id="home-section">
        <c:if test="${sessionScope.feedbacks != null && sessionScope.feedbacks.size() != 0}">
            <div class="hasFeedback"></div>
        </c:if>
        <c:if test="${requestScope.login != null}">
            <div class="login"></div>
        </c:if>

        <input type="hidden" id="userID" value="${sessionScope.user.getUserID()}"/>
        <input type="hidden" id="userRole" value="${sessionScope.user.getRole()}"/>

        <div class="modal micromodal-slide" id="modal-1" aria-hidden="true">
            <div class="modal__overlay" tabindex="-1" data-micromodal-close>
                <div class="modal__container" role="dialog" aria-modal="true" aria-labelledby="modal-1-title">
                    <header class="modal__header">
                        <h2 class="modal__title" id="modal-1-title">
                            We're Working On Improvements !
                        </h2>
                        <button class="modal__close" aria-label="Close modal" data-micromodal-close></button>
                    </header>
                    <main class="modal__content" id="modal-1-content">
                        <ul>
                            <li>The team would like to hear your feedback about <strong>Booking Services</strong>.</li>
                            <li>And We still working on some new improvements to our <strong>User Experience</strong>. So tell us if you meet any trouble on experience :)</li>
                            <li>                 
                                You Have <strong>${sessionScope.feedbacks.size()}</strong> Booking(s) <%@include file="../../icons/register.svg" %> that already <strong>completed</strong>. 
                            </li>

                        </ul>
                        <p>
                            <%@include file="../../icons/arrow.svg" %>
                            <span style="margin-left: 5px;">
                                Would you mind giving us some feedback about <strong>Booking(s) or User Experience</strong> ? So that we can do better for you?
                            </span>
                        </p>
                        <div style="text-align: center;">
                            <%@include file="../../icons/feedback.svg" %>
                        </div>

                    </main>
                    <footer class="modal__footer">
                        <a class="modal__btn modal__btn-primary" href="/feedbacks">Continue</a>
                        <button class="modal__btn" data-micromodal-close aria-label="Close this dialog window">Close</button>
                    </footer>
                </div>
            </div>
        </div>
        <div id="overlayer"></div>
        <div class="loader">
            <div class="spinner-border text-primary" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>


        <div class="site-wrap">

            <div class="site-mobile-menu site-navbar-target">
                <div class="site-mobile-menu-header">
                    <div class="site-mobile-menu-close mt-3">
                        <span class="icon-close2 js-menu-toggle"></span>
                    </div>
                </div>
                <div class="site-mobile-menu-body"></div>
            </div>


            <header class="site-navbar js-sticky-header site-navbar-target" role="banner" >

                <div class="container">
                    <div class="row align-items-center">

                        <div class="col-6 col-xl-3">
                            <h1 class="mb-0 site-logo">
                                <a href="/index" class="h2 mb-0" style="display:flex;align-items: center;">
                                    <span style="margin-right: 10px;">Hotel Pet</span>
                                    <span class="text-primary">
                                        <%@include file="../../icons/dog.svg" %>
                                    </span> 
                                </a>
                            </h1>
                        </div>

                        <div class="col-12 col-md-9 d-none d-xl-block">
                            <nav class="site-navigation position-relative text-right" role="navigation">

                                <ul class="site-menu main-menu js-clone-nav mr-auto d-none d-lg-block">
                                    <li><a href="#home-section" class="nav-link">Home</a></li>
                                    <li class="has-children">
                                        <a href="#about-section" class="nav-link">About</a>
                                        <ul class="dropdown">
                                            <li><a href="#trainers-section" class="nav-link">Trainers</a></li>
                                            <li><a href="#pricing-section" class="nav-link">Pricing</a></li>
                                            <li><a href="#faq-section" class="nav-link">FAQ</a></li>
                                            <li><a href="#testimonials-section" class="nav-link">Testimonials</a></li>
                                            <li><a href="#gallery-section" class="nav-link">Gallery</a></li>
                                            <li><a href="#blog-section" class="nav-link">Blog</a></li>                        
                                        </ul>
                                    </li>

                                    <li><a href="#trainers-section" class="nav-link">Trainers</a></li>
                                    <li><a href="#services-section" class="nav-link">Services</a></li>
                                    <li><a href="#contact-section" class="nav-link">Contact</a></li>

                                    <c:if test="${sessionScope.user == null}">
                                        <li>
                                            <a href="/login" class="nav-link">
                                                <span>Login</span>
                                                <%@include file="../../icons/login.svg" %>
                                            </a>
                                        </li>
                                    </c:if>


                                    <c:if test="${sessionScope.user != null}">
                                        <li class="has-children">
                                            <a href="/bookings" class="nav-link" style="display:flex;align-items: center;">
                                                <div class="user-avatar" style="margin-right: 10px;">
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
                                                <span>${sessionScope.user.getUserName()}</span>
                                            </a>
                                            <ul class="dropdown">
                                                <li>
                                                    <a href="/bookings" class="nav-link" style="display: flex;justify-content: flex-start;align-items: center;">
                                                        <%@include file="../../icons/location.svg" %>
                                                        <span style="margin-left: 10px;">Booking</span>
                                                    </a>
                                                </li>
                                                <li>
                                                    <a class="nav-link" href="/logout" style="display: flex;justify-content: flex-start;align-items: center;">
                                                        <%@include file="../../icons/logout.svg" %> 
                                                        <span style="margin-left: 10px;">Sign Out</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </li>
                                    </c:if>
                                </ul>
                            </nav>
                        </div>          

                        <div class="col-6 d-inline-block d-xl-none ml-md-0 py-3" style="position: relative; top: 3px;"><a href="#" class="site-menu-toggle js-menu-toggle float-right"><span class="icon-menu h3"></span></a></div>

                    </div>
                </div>

            </header>


            <section class="site-blocks-cover overflow-hidden bg-light">
                <div class="container">
                    <div class="row">
                        <div class="col-md-7 align-self-center text-center text-md-left">
                            <div class="intro-text">
                                <h1>We Care For <span class="d-md-block">Your Pet</span></h1>
                                <p class="mb-4">We always try to provide your pet the best<span class="d-block"> services.</p>
                            </div>
                        </div>
                        <div class="col-md-5 align-self-end text-center text-md-right">
                            <img src="../../images/index/dogger_img_1.png" alt="Image" class="img-fluid cover-img">
                        </div>
                    </div>
                </div>
            </section> 

            <section class="site-section">
                <div class="container">
                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-6 text-center heading-section mb-5">
                            <div class="paws">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="text-black mb-2">Welcome to Hotel Pet Care</h2>
                            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                        </div>
                    </div>

                    <div class="row hover-1-wrap mb-5 mb-lg-0">
                        <div class="col-12">
                            <div class="row">
                                <div class="mb-4 mb-lg-0 col-lg-6 order-lg-2" data-aos="fade-right">
                                    <a href="#" class="hover-1">
                                        <img src="../../images/index/dogger_img_sm_3.jpg" alt="Image" class="img-fluid">
                                    </a>
                                </div>
                                <div class="col-lg-5 mr-auto text-lg-right align-self-center order-lg-1" data-aos="fade-left">
                                    <h2 class="text-black">Love &amp; Care</h2>
                                    <p class="mb-4">Far far away, behind the word mountains, Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row hover-1-wrap mb-5 mb-lg-0">
                        <div class="col-12">
                            <div class="row">
                                <div class="mb-4 mb-lg-0 col-lg-6"  data-aos="fade-left">
                                    <a href="#" class="hover-1">
                                        <img src="../../images/index/dogger_img_sm_1.jpg" alt="Image" class="img-fluid">
                                    </a>
                                </div>
                                <div class="col-lg-5 ml-auto align-self-center"  data-aos="fade-right">
                                    <h2 class="text-black">Fearsome</h2>
                                    <p class="mb-4">Far far away, behind the word mountains, Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row hover-1-wrap">
                        <div class="col-12">
                            <div class="row">
                                <div class="mb-4 mb-lg-0 col-lg-6 order-lg-2" data-aos="fade-right">
                                    <a href="#" class="hover-1">
                                        <img src="../../images/index/dogger_img_sm_2.jpg" alt="Image" class="img-fluid">
                                    </a>
                                </div>
                                <div class="col-lg-5 mr-auto text-lg-right align-self-center order-lg-1" data-aos="fade-left">
                                    <h2 class="text-black">Beautiful</h2>
                                    <p class="mb-4">Far far away, behind the word mountains, Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="site-section" id="about-section">
                <div class="container">
                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-5 align-self-center mr-auto text-left heading-section mb-5">
                            <div class="paws ml-4">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="text-black mb-3">About Us</h2>
                            <p class="lead">Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                            <p class="text-muted mb-4">A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth.</p>
                            <ul class="list-unstyled ul-paw primary mb-0">
                                <li>A small river named Duden flows</li>
                                <li>It is a paradisematic country</li>
                                <li>Roasted parts of sentences fly</li>
                            </ul>

                        </div>
                        <div class="col-lg-6 text-left heading-section mb-5"  data-aos="fade-up" data-aos-delay="100">
                            <a  data-fancybox data-ratio="1.5" href="https://vimeo.com/317571768" class="video-img">
                                <span class="play">
                                    <span class="icon-play"></span>
                                </span>
                                <img src="../../images/index/dogger_img_big_1.jpg" alt="Image" class="img-fluid">
                            </a>
                        </div>
                    </div>
                </div>
            </section>

            <section class="site-section bg-primary trainers" id="trainers-section">
                <div class="container">
                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-7 text-center heading-section mb-5">
                            <div class="paws white">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="mb-2 heading">Our Trainers</h2>
                            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                        </div>
                    </div>
                    <div class="row">

                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up" data-aos-delay="">
                            <div class="trainer">
                                <figure>
                                    <img src="../../images/index/dogger_trainer_1.jpg" alt="Image" class="img-fluid">
                                </figure>
                                <div class="px-md-3">
                                    <h3>Jessica White</h3>
                                    <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    <ul class="ul-social-circle">
                                        <li><a href="#"><span class="icon-twitter"></span></a></li>
                                        <li><a href="#"><span class="icon-instagram"></span></a></li>
                                        <li><a href="#"><span class="icon-facebook"></span></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up" data-aos-delay="100">
                            <div class="trainer">
                                <figure>
                                    <img src="../../images/index/dogger_trainer_2.jpg" alt="Image" class="img-fluid">
                                </figure>
                                <div class="px-md-3">
                                    <h3>Valerie Elash</h3>
                                    <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    <ul class="ul-social-circle">
                                        <li><a href="#"><span class="icon-twitter"></span></a></li>
                                        <li><a href="#"><span class="icon-instagram"></span></a></li>
                                        <li><a href="#"><span class="icon-facebook"></span></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up"  data-aos-delay="200">
                            <div class="trainer">
                                <figure>
                                    <img src="../../images/index/dogger_trainer_3.jpg" alt="Image" class="img-fluid">
                                </figure>
                                <div class="px-md-3">
                                    <h3>Alicia Jones</h3>
                                    <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                                    <ul class="ul-social-circle">
                                        <li><a href="#"><span class="icon-twitter"></span></a></li>
                                        <li><a href="#"><span class="icon-instagram"></span></a></li>
                                        <li><a href="#"><span class="icon-facebook"></span></a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>

            <section class="site-section" id="pricing-section">
                <div class="container">
                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-7 text-center heading-section mb-5">
                            <div class="paws">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="mb-2 text-black heading">Pricing Table</h2>
                            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                        </div>
                    </div>
                    <div class="row no-gutters">
                        <div class="col-12 col-sm-6 col-md-6 col-lg-4 bg-primary p-3 p-md-5" data-aos="fade-up" data-aos-delay="">


                            <div class="pricing">
                                <h3 class="text-center text-white text-uppercase">Small Pet</h3>
                                <div class="price text-center mb-4 ">
                                    <span><span>$${sessionScope.setting.getSmallPetFee()}</span> / day</span>
                                </div>
                                <ul class="list-unstyled ul-check success mb-5">

                                    <li>Wet/dry food included</li>
                                    <li>3 walks daily</li>
                                    <li>Social interaction</li>
                                    <li>Monitoring health</li>
                                    <li class="remove">Teeth cleaning</li>
                                    <li class="remove">Flea treatments</li>
                                    <li class="remove">Nail clipping</li>
                                </ul>
                                <p>More 5$ For Extra Services Above Unlock</p>
                                <p class="text-center">
                                    <a href="/bookings" class="btn btn-secondary">Booking Now</a>
                                </p>
                            </div>

                        </div>
                        <div class="col-12 col-sm-6 col-md-6 col-lg-4 bg-dark  p-3 p-md-5" data-aos="fade-up" data-aos-delay="100">
                            <div class="pricing">
                                <h3 class="text-center text-white text-uppercase">Medium Pet</h3>
                                <div class="price text-center mb-4 ">
                                    <span><span>$${sessionScope.setting.getMediumPetFee()}</span> / day</span>
                                </div>
                                <ul class="list-unstyled ul-check success mb-5">

                                    <li>Wet/dry food included</li>
                                    <li>3 walks daily</li>
                                    <li>Social interaction</li>
                                    <li>Monitoring health</li>
                                    <li class="remove">Teeth cleaning</li>
                                    <li class="remove">Flea treatments</li>
                                    <li class="remove">Nail clipping</li>
                                </ul>
                                <p>More 5$ For Extra Services Above Unlock</p>
                                <p class="text-center">
                                    <a href="/bookings" class="btn btn-primary">Booking Now</a>
                                </p>
                            </div>
                        </div>
                        <div class="col-12 col-sm-6 col-md-6 col-lg-4 bg-primary  p-3 p-md-5" data-aos="fade-up" data-aos-delay="200">
                            <div class="pricing">
                                <h3 class="text-center text-white text-uppercase">Larget Pet</h3>
                                <div class="price text-center mb-4 ">
                                    <span><span>$${sessionScope.setting.getFee()}</span> / day</span>
                                </div>
                                <ul class="list-unstyled ul-check success mb-5">

                                    <li>Wet/dry food included</li>
                                    <li>3 walks daily</li>
                                    <li>Social interaction</li>
                                    <li>Monitoring health</li>
                                    <li class="remove">Teeth cleaning</li>
                                    <li class="remove">Flea treatments</li>
                                    <li class="remove">Nail clipping</li>
                                </ul>
                                <p>More 5$ For Extra Services Above Unlock</p>
                                <p class="text-center">
                                    <a href="/bookings" class="btn btn-secondary">Booking Now</a>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>

            <section class="site-section" id="faq-section">
                <div class="container" id="accordion">
                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-6 text-center heading-section mb-5">
                            <div class="paws">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="text-black mb-2">Frequently Ask Questions</h2>
                            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                        </div>
                    </div>
                    <div class="row accordion justify-content-center block__76208">
                        <div class="col-lg-6 order-lg-2 mb-5 mb-lg-0" data-aos="fade-up"  data-aos-delay="">
                            <img src="../../images/index/dogger_img_sm_1.jpg" alt="Image" class="img-fluid rounded">
                        </div>
                        <div class="col-lg-5 mr-auto order-lg-1" data-aos="fade-up"  data-aos-delay="100">
                            <div class="accordion-item">
                                <h3 class="mb-0 heading">
                                    <a class="btn-block" data-toggle="collapse" href="#collapseFive" role="button" aria-expanded="true" aria-controls="collapseFive">Should I stop letting my dog sleep with me at night?<span class="icon"></span></a>
                                </h3>
                                <div id="collapseFive" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="body-text">
                                        <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.</p>
                                    </div>
                                </div>
                            </div> <!-- .accordion-item -->

                            <div class="accordion-item">
                                <h3 class="mb-0 heading">
                                    <a class="btn-block" data-toggle="collapse" href="#collapseSix" role="button" aria-expanded="false" aria-controls="collapseSix">Is it okay to dress up your dog?<span class="icon"></span></a>
                                </h3>
                                <div id="collapseSix" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="body-text">
                                        <p>A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth.</p>
                                    </div>
                                </div>
                            </div> <!-- .accordion-item -->

                            <div class="accordion-item">
                                <h3 class="mb-0 heading">
                                    <a class="btn-block" data-toggle="collapse" href="#collapseSeven" role="button" aria-expanded="false" aria-controls="collapseSeven">Why do dogs like belly rubs so much?<span class="icon"></span></a>
                                </h3>
                                <div id="collapseSeven" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="body-text">
                                        <p>Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar.</p>
                                    </div>
                                </div>
                            </div> <!-- .accordion-item -->

                            <div class="accordion-item">
                                <h3 class="mb-0 heading">
                                    <a class="btn-block" data-toggle="collapse" href="#collapseEight" role="button" aria-expanded="false" aria-controls="collapseEight">Is a warm dry nose a sign of illness in dogs?<span class="icon"></span></a>
                                </h3>
                                <div id="collapseEight" class="collapse" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="body-text">
                                        <p>The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way.</p>
                                    </div>
                                </div>
                            </div> <!-- .accordion-item -->

                        </div>


                    </div>
                </div>
            </section>

            <section class="site-section bg-light block-13" id="testimonials-section" data-aos="fade">
                <div class="container">

                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-6 text-center heading-section mb-5">
                            <div class="paws">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="text-black mb-2">Happy Customers</h2>
                        </div>
                    </div>
                    <div  data-aos="fade-up" data-aos-delay="200">
                        <div class="owl-carousel nonloop-block-13">
                            <!--                            <div>
                                                            <div class="block-testimony-1 text-center">
                            
                                                                <blockquote class="mb-4">
                                                                    <p>&ldquo;The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way.&rdquo;</p>
                                                                </blockquote>
                            
                                                                <figure>
                                                                    <img src="../../images/index/person_4.jpg" alt="Image" class="img-fluid rounded-circle mx-auto">
                                                                </figure>
                                                                <h3 class="font-size-20 text-black">Ricky Fisher</h3>
                                                            </div>
                                                        </div>-->
                            <c:forEach items="${requestScope.feedbacks}" var="fb">
                                <div>
                                    <div class="block-testimony-1 text-center">
                                        <blockquote class="mb-4">
                                            <p>&ldquo;${fb.getContent()}&rdquo;</p>
                                        </blockquote>

                                        <figure>
                                            <c:if test="${fb.getAvatar() != null}"> 
                                                <img class="img-fluid rounded-circle mx-auto" src="/retrieveImage?id=${fb.getAvatar()}" alt="user avatar" />
                                            </c:if>                      
                                            <c:if test="${fb.getAvatar() == null && fb.isGender()}"> 
                                                <img class="img-fluid rounded-circle mx-auto" src="../../images/male_default.jpg" alt="user avatar" />
                                            </c:if>
                                            <c:if test="${fb.getAvatar() == null && !fb.isGender()}"> 
                                                <img class="img-fluid rounded-circle mx-auto" src="../../images/female_default.jpg" alt="user avatar" />
                                            </c:if>
                                        </figure>
                                        <h3 class="font-size-20 text-black">${fb.getFullName()}</h3>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </section>

            <section class="site-section" id="gallery-section">
                <div class="container-fluid">
                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-6 text-center heading-section mb-5">
                            <div class="paws">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="text-black mb-2">Photo Gallery</h2>
                        </div>
                    </div>
                    <div class="row no-gutters">

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_1.jpg" data-fancybox="gal"><img src="../images/index/dogger_img_sm_1.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_2.jpg" data-fancybox="gal"><img src="../images/index/dogger_img_sm_2.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_3.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_3.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_4.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_4.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_5.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_5.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_6.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_6.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_1.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_1.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_2.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_2.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_3.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_3.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_4.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_4.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_5.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_5.jpg" alt="Image" class="img-fluid"></a>

                        <a class="col-6 col-md-6 col-lg-4 col-xl-3 gal-item d-block" data-aos="fade-up" data-aos-delay="100" href="../../images/index/dogger_img_sm_6.jpg" data-fancybox="gal"><img src="../../images/index/dogger_img_sm_6.jpg" alt="Image" class="img-fluid"></a>

                    </div>
                </div>
            </section>

            <section class="site-section " id="services-section">
                <div class="container">
                    <div class="row justify-content-center" data-aos="fade-up">
                        <div class="col-lg-6 text-center heading-section mb-5">
                            <div class="paws">
                                <span class="icon-paw"></span>
                            </div>
                            <h2 class="text-black mb-2">Our Services</h2>
                            <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts.</p>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up" data-aos-delay="">

                            <div class="block_service">
                                <img src="../../images/index/dogger_checkup.svg" alt="Image mb-5">
                                <h3>Dog Checkup</h3>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. </p>
                            </div>

                        </div>
                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up"  data-aos-delay="100">
                            <div class="block_service">
                                <img src="../../images/index/dogger_dermatology.svg" alt="Image mb-5">
                                <h3>Dog Dermatology</h3>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. </p>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up"  data-aos-delay="200">
                            <div class="block_service">
                                <img src="../../images/index/dogger_bones.svg" alt="Image mb-5">
                                <h3>For Strong Teeth</h3>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. </p>
                            </div>
                        </div>


                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up"  data-aos-delay="">

                            <div class="block_service">
                                <img src="../../images/index/dogger_veterinary.svg" alt="Image mb-5">
                                <h3>Dog First Aid</h3>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. </p>
                            </div>

                        </div>
                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up"  data-aos-delay="100">
                            <div class="block_service">
                                <img src="../../images/index/dogger_dryer.svg" alt="Image mb-5">
                                <h3>Dog Dryer</h3>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. </p>
                            </div>
                        </div>
                        <div class="col-md-6 mb-4 col-lg-4" data-aos="fade-up"  data-aos-delay="200">
                            <div class="block_service">
                                <img src="../../images/index/dogger_veterinarian.svg" alt="Image mb-5">
                                <h3>Expert Veterinarian</h3>
                                <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. </p>
                            </div>
                        </div>

                    </div>
                </div>
            </section>


    <footer class="site-footer">
        <div class="container">
            <div class="row">
                <div class="col-md-9">
                    <div class="row">
                        <div class="col-md-5">
                            <h2 class="footer-heading mb-4">About Us</h2>
                            <p>Lorem ipsum dolor sit amet consectetur adipisicing elit. Neque facere laudantium magnam voluptatum autem. Amet aliquid nesciunt veritatis aliquam.</p>
                        </div>
                       
                        <div class="col-md-3 ml-auto">
                            <h2 class="footer-heading mb-4">Quick Links</h2>
                            <ul class="list-unstyled">
                                <li><a href="#about-section" class="smoothscroll">About Us</a></li>
                                <li><a href="#trainers-section" class="smoothscroll">Trainers</a></li>
                                <li><a href="#services-section" class="smoothscroll">Services</a></li>
                                <li><a href="#testimonials-section" class="smoothscroll">Testimonials</a></li>
                                <li><a href="#contact-section" class="smoothscroll">Contact Us</a></li>
                            </ul>
                        </div>
                        <div class="col-md-3">
                            <h2 class="footer-heading mb-4">Follow Us</h2>
                            <a href="#" class="pl-0 pr-3"><span class="icon-facebook"></span></a>
                            <a href="#" class="pl-3 pr-3"><span class="icon-twitter"></span></a>
                            <a href="#" class="pl-3 pr-3"><span class="icon-instagram"></span></a>
                            <a href="#" class="pl-3 pr-3"><span class="icon-linkedin"></span></a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="paws">
                        <span class="icon-paw"></span>
                    </div>
                    <h2 class="text-white mb-5">Contact Us</h2>
                    <ul class="list-unstyled text-left address">
                        <li>
                            <span class="d-block">Address:</span>
                            <p>Melbourne St,South Birbane 4101 Austraila</p>
                        </li>
                        <li>
                            <span class="d-block">Phone:</span>
                            <p>+(000) 123 4567 89</p>
                        </li>
                        <li>
                            <span class="d-block">Email:</span>
                            <p>info@yourdomain.com</p>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="row pt-5 mt-5 text-center">
                <div class="col-md-12">
                    <div class="border-top pt-5">
                        <p class="copyright"><small>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                                Copyright &copy;<script>document.write(new Date().getFullYear());</script> All rights reserved | This template is made with <i class="icon-heart text-danger" aria-hidden="true"></i> by <a href="https://colorlib.com" target="_blank" >Colorlib</a>
                                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></small></p>

                    </div>
                </div>

            </div>
        </div>
    </footer>

</div> <!-- .site-wrap -->

<script src="../../js/index/js/jquery-3.3.1.min.js"></script>
<script src="../../js/index/js/jquery-ui.js"></script>
<script src="../../js/index/js/popper.min.js"></script>
<script src="../../js/index/js/bootstrap.min.js"></script>
<script src="../../js/index/js/owl.carousel.min.js"></script>
<script src="../../js/index/js/jquery.countdown.min.js"></script>
<script src="../../js/index/js/jquery.easing.1.3.js"></script>
<script src="../../js/index/js/aos.js"></script>
<script src="../../js/index/js/jquery.fancybox.min.js"></script>
<script src="../../js/index/js/jquery.sticky.js"></script>
<script src="../../js/index/js/isotope.pkgd.min.js"></script>
<script src="../../js/index/js/main.js"></script>
<script src="https://unpkg.com/micromodal/dist/micromodal.min.js"></script>
<script src="../../js/iziToast.min.js"></script>
<script>
                                    window.addEventListener('DOMContentLoaded', function () {
                                        if (document.querySelector('.login') != null) {
                                            setTimeout(() => {
                                                iziToast.success({
                                                    title: 'OK',
                                                    message: 'Login Successfully !',
                                                });
                                            }, 50);


                                        }
                                    });
</script>

<script>
    window.addEventListener('DOMContentLoaded', function (e) {
        MicroModal.init({
            awaitCloseAnimation: true, // set to false, to remove close animation   
        });
        if (document.querySelector('.hasFeedback')) {
            MicroModal.show('modal-1');
        }
    })
</script>
</body>
</html>