<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<jsp:useBean id="dbUtil" class="utils.DBUtil"></jsp:useBean>
<%@ page import="entities.Admin" %>
<% Admin adm = util.isLogin(request, response); %>
<!doctype html>
<html lang="en">

<head>
    <title>Yönetim</title>
    <jsp:include page="inc/css.jsp"></jsp:include>
</head>

<body>
<div class="wrapper d-flex align-items-stretch">
    <jsp:include page="inc/sideBar.jsp"></jsp:include>
    <!-- Page Content  -->
    <div id="content" class="p-4 p-md-5">
        <jsp:include page="inc/topMenu.jsp"></jsp:include>
        <h3 class="mb-3">
            Kasa
            <small class="h6">Kasa Hareketleri</small>
        </h3>

        <div class="row">

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground1" id="card1">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;">Toplam Kasaya Giren</h5>
                            <h4><strong> <c:out value="${dbUtil.viewPayment().get(0).totalPayInPrice}"></c:out>₺ </strong></h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 mb-3">
                <div class="card cardBackground2" id="card2">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;">Toplam Kasadan Çıkan</h5>
                            <h4><strong> <c:out value="${dbUtil.viewPayment().get(0).totalPayOutPrice}"></c:out>₺ </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground3" id="card3">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Kasada Kalan</h5>
                            <h4><strong> <c:out value="${dbUtil.viewPayment().get(0).totalPayInPrice - dbUtil.viewPayment().get(0).totalPayOutPrice}"></c:out>₺ </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground4" id="card4">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Bugün Kasaya Giriş</h5>
                            <h4><strong> <c:out value="${dbUtil.viewPayment().get(0).todayPayInPrice}"></c:out>₺ </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground4" id="card5">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Bugün Kasadan Çıkan</h5>
                            <h4><strong> <c:out value="${dbUtil.viewPayment().get(0).todayPayOutPrice}"></c:out>₺ </strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <a href="payIn.jsp">
                    <div class="card cardBackground4" id="card6">
                        <div class="card-body">
                            <div style="display: flex; justify-content: space-between;">
                                <h5 style="align-self: center;"> Kasa Yönetimi</h5>
                                <i class="fas fa-link fa-2x" style="color: white; align-self: center;"></i>
                            </div>
                        </div>
                    </div>
                </a>
            </div>



        </div>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Arama / Rapor</div>

            <form class="row p-3" id="getCustomerPaidReceip">

                <div class="col-md-3 mb-3">
                    <label for="customer_name" class="form-label">Müşteri Seçiniz</label>
                    <select name="cname" id="customer_name" class="selectpicker" data-width="100%" data-show-subtext="true" data-live-search="true" required>
                        <option value="-1" data-subtext="" >Seçim Yapınız</option>
                        <option value="1" data-subtext="">KASA</option>
                        <c:if test="${dbUtil.getCustomerPaidReceipt().size()>0}">
                            <c:forEach items="${dbUtil.getCustomerPaidReceipt()}" var="item">
                                <option data-subtext="${item[0]}">${item[1]}</option>
                            </c:forEach>
                        </c:if>
                    </select>
                </div>

                <div id="payment-type" class="col-md-3 mb-3" style="display: none;">
                    <label for="ctype" class="form-label">Tür</label>
                    <select class="form-select" name="ctype" id="ctype">
                        <option value="0">Giriş</option>
                        <option value="1">Çıkış</option>
                    </select>
                </div>


                <div class="col-md-3 mb-3">
                    <label for="startDate" class="form-label">Başlama Tarihi</label>
                    <input type="date" name="startDate" id="startDate" class="form-control" required/>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="endDate" class="form-label">Bitiş Tarihi</label>
                    <input type="date" name="endDate" id="endDate" class="form-control" required/>
                </div>

                <div class="col-md-3 mt-4">
                    <button style="margin-top:8px;" type="submit" class="col btn btn-outline-primary">Gönder</button>
                </div>
            </form>
        </div>

        <div id="table-safe" class="main-card mb-3 card mainCart" style="display: none;">
            <div class="card-header cardHeader">Arama Sonuçları</div>
            <div class="table-responsive">
                <table id="paymentTableRow" class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Adı</th>
                            <th>Soyadı</th>
                            <th>Odenen Tutar</th>
                            <th>Odenen Tarih</th>
                        </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
        </div>




    </div>
</div>

<jsp:include page="inc/js.jsp"></jsp:include>
<script src="js/payment.js"></script>
</body>

</html>