<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="tr">
<head>
    <title>Admin Giriş</title>
    <meta http-equiv="Content-Type" content="text/html" charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="dist/css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/fontawesome-free-5.15.4-web/css/all.min.css">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
    <link rel="stylesheet" href="fonts/icomoon/style.css">
    <link rel="stylesheet" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/site.css">
</head>
<body>

<div class="content">
    <div class="container">
        <div class="row">
            <div class="col-md-6 order-md-2">
                <img src="images/undraw_file_sync_ot38.svg" alt="Image" class="img-fluid">
            </div>
            <div class="col-md-6 contents">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="mb-4">
                            <h3>Yönetici <b>Girişi</b></h3>
                            <p class="mb-4">Depo ve Stok Yönetim uygulamasına giriş için bilgileri giriniz!</p>
                        </div>
                        <form action="login-servlet" method="POST">
                            <div class="form-floating mb-3">
                                <input name="email" type="email" class="form-control" id="email" placeholder="E-Mail"/>
                                <label for="email">E-Mail</label>
                            </div>
                            <div class="form-floating mb-3">
                                <input name="password" type="password" class="form-control" id="password" placeholder="Şifre" />
                                <label for="password">Şifre</label>
                            </div>

                            <div style="justify-content: space-between" class="d-flex mb-1 align-items-center">
                                <label class="control control--checkbox mb-0"><span class="caption">Beni Hatırla</span>
                                    <input name="remember" type="checkbox" checked="checked"/>
                                    <div class="control__indicator"></div>
                                </label>
                                <span class="ml-auto" style="margin-left: 5px;"><a href="#"  data-bs-toggle="modal" data-bs-target="#loginModal" class="forgot-pass"> Şifre Hatırlat</a></span>
                            </div>

                            <%
                                System.out.println("view kısmı");
                                Object object = request.getAttribute("error");
                                if(object != null){
                                    String error = ""+object;
                                    System.out.println(error);
                            %>

                            <div class="d-flex mb-4 align-items-center">
                                <div class="invalid-feedback" style="display: block;">
                                    <%=error%>
                                </div>
                            </div>
                            <%
                                }
                            %>

                            <input type="submit" value="Giriş Yap" class="btn text-white btn-block btn-primary">

                        </form>



                    </div>
                </div>

            </div>

        </div>
    </div>
</div>

<!-- Login Modal -->
<form id="email_send_message">
    <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 style="color: firebrick;" class="modal-title" id="exampleModalLabel">Şifre Hatırlat</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-floating mb-1">
                        <input name="pass_email" id="pass_email" type="text" aria-describedby="emailHelp" class="form-control"  placeholder="E-Mail"/>
                        <label for="pass_email">E-Mail</label>
                        <div id="emailHelp" class="form-text">Sistemde kayıtlı E-Mail adresinizi giriniz.</div>
                    </div>

                    <div id="pass_fail" class="invalid-feedback" style="display: block;">
                    <!--    Hata ifadeleri bu bölümde yazılacak. -->
                    </div>

                    <div  id="pass_success" class="valid-feedback" style="display: block;">
                    <!--    Başarılı ifadeleri bu bölümde yazılacak. -->
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Kapat</button>
                    <button id="pass_send_btn" type="Submit" class="btn btn-primary">Şifre Hatırlat</button>
                </div>
            </div>
        </div>
    </div>
</form>


<script src="js/jquery-3.6.0.min.js"></script>
<script src="js/dist/popover.js"></script>
<script src="dist/js/bootstrap.min.js"></script>
<script src="css/fontawesome-free-5.15.4-web/js/all.min.js"></script>
<script src="js/site.js"></script>
<script src="js/pass-email.js"></script>
</body>
</html>