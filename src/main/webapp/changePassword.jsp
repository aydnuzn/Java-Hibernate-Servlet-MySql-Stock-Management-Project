<%@ page import="entities.Admin" %>
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
            <div class="row justify-content-center">
                <div class="col-md-8 col-lg-6">
                    <div class="mb-4">
                        <h3><b>DEPO YÖNETİM</b> - Şifre Değiştir</h3>
                        <%
                            Admin adminInfo = null;
                            if(request.getAttribute("adminInfo") != null) {
                                adminInfo = (Admin) request.getAttribute("adminInfo");
                        %>
                            <p class="mb-4">HOSGELDİN <%=adminInfo.getFullName()%>.</p>
                        <%
                            }
                        %>
                        <p>Şifre Değiştirme İşleminizi Burada Gerçekleştirebilirsiniz</p>
                    </div>
                    <form action="change-password" method="POST">
                        <div class="form-floating mb-3">
                            <input name="password1" type="password" class="form-control" id="pass1" placeholder="Şifrenizi Giriniz" required/>
                            <label for="pass1">Şifrenizi Giriniz</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input name="password2" type="password" class="form-control" id="pass2" placeholder="Şifrenizi Tekrar Giriniz" required/>
                            <label for="pass2">Şifrenizi Tekrar Giriniz</label>
                        </div>
                        <%
                            System.out.println("view kısmı");
                            String changePasswordError = (String) request.getAttribute("changePasswordError");
                            if(changePasswordError != null){
                                System.out.println(changePasswordError);
                        %>
                        <div class="d-flex mb-4 align-items-center">
                            <div class="invalid-feedback" style="display: block;">
                                <%=changePasswordError%>
                            </div>
                        </div>
                        <%
                            }
                        %>
                        <input type="submit" value="Şifre Değiştir" class="btn text-white btn-block btn-primary">
                    </form>
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
                    <h5 class="modal-title" id="exampleModalLabel">Şifre Hatırlat</h5>
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