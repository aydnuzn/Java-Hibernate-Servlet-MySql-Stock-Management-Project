<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<jsp:useBean id="dbUtil" class="utils.DBUtil"></jsp:useBean>
<%@ page import="entities.Admin" %>
<% Admin adm = util.isLogin(request, response); %>

<!doctype html>
<html lang="en">

<head>
  <title>Kasa Yönetimi / Ödeme Girişi</title>
  <jsp:include page="inc/css.jsp"></jsp:include>
</head>

<body>

<div class="wrapper d-flex align-items-stretch">
  <jsp:include page="inc/sideBar.jsp"></jsp:include>
  <div id="content" class="p-4 p-md-5">
    <jsp:include page="inc/topMenu.jsp"></jsp:include>
    <h3 class="mb-3">
      <a href="payOut.jsp" class="btn btn-danger float-right">Ödeme Çıkışı</a>
      Kasa Yönetimi
      <small class="h6">Ödeme Girişi</small>
    </h3>


    <div class="main-card mb-3 card mainCart">
      <div class="card-header cardHeader">Ödeme Ekle</div>

      <form id="paymentIn_add_form" class="row p-3">

        <div class="col-md-3 mb-3">
          <label for="customer_name" class="form-label">Müşteriler</label>
          <select id="customer_name" class="selectpicker" data-width="100%" data-show-subtext="true" data-live-search="true" required>
            <option data-subtext="">Seçim Yapınız</option>
            <c:if test="${dbUtil.payInCustomers().size()>0}">
              <c:forEach items="${dbUtil.payInCustomers()}" var="item">
                <option data-subtext="${item[0]}">${item[1]}</option>
              </c:forEach>
            </c:if>
          </select>
        </div>

        <div class="col-md-3 mb-3">
          <label for="cUnpaid_receipt" class="form-label">Müşteri Fişleri</label>
          <select id="cUnpaid_receipt" class="selectpicker" data-width="100%" data-show-subtext="true" data-live-search="true" required>
            <option data-subtext="">Seçim Yapınız</option>
            <!-- Add customer unPaid Receipt -->
          </select>
        </div>

        <div class="col-md-3 mb-3">
          <label for="payInTotal" class="form-label">Ödeme Tutarı</label>
          <input type="number" name="payInTotal" id="payInTotal" class="form-control" />
        </div>

        <div class="col-md-3 mb-3">
          <label for="payInDetail" class="form-label">Ödeme Detay</label>
          <input type="text" name="payInDetail" id="payInDetail" class="form-control" />
        </div>

        <div class="btn-group col-md-3 " role="group">
          <button type="submit" class="btn btn-outline-primary mr-1">Gönder</button>
          <button onclick="fncReset()" type="button" class="btn btn-outline-primary">Temizle</button>
        </div>
      </form>
    </div>


    <div class="main-card mb-3 card mainCart">
      <div class="card-header cardHeader">Ödeme Giriş Listesi</div>

      <form id="search_payIn_form">
        <div class="row mt-3" style="padding-right: 15px; padding-left: 15px;">
          <div class="col-sm-3"></div>
          <div class="col-sm-3"></div>
          <div class="col-sm-3"></div>
          <div class="col-sm-3">
            <div class="input-group flex-nowrap">
              <span class="input-group-text" id="addon-wrapping"><i class="fas fa-search"></i></span>
              <input id="search_payIn" type="text" class="form-control" placeholder="Arama.." aria-label="Username" aria-describedby="addon-wrapping">
              <button type="submit" class="btn btn-outline-primary">Ara</button>
            </div>
          </div>
        </div>
      </form>


      <div class="table-responsive">
        <table class="align-middle mb-0 table table-borderless table-striped table-hover">
          <thead>
          <tr>
            <th>Id</th>
            <th>Adı</th>
            <th>Soyadı</th>
            <th>Fiş No</th>
            <th>Ödeme Tutarı</th>
            <th class="text-center" style="width: 155px;" >Yönetim</th>
          </tr>
          </thead>
          <tbody id="paymentInTableRow">
          <!-- for loop  -->
          </tbody>
        </table>
      </div>
    </div>


  </div>
</div>
</div>
<jsp:include page="inc/js.jsp"></jsp:include>
<script src="js/payin.js"></script>
</body>

</html>

