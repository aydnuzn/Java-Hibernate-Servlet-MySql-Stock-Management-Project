let customerCode = '';
$('#customer_name').change(function () {
    reset();
    customerCode = $("#customer_name option:selected").attr("data-subtext");
    var selectedItemValue = $("#customer_name option:selected").val();
    if(selectedItemValue == 1){
        $('#payment-type').show();
        $('#ctype').val("0")
    }else{
        $('#payment-type').hide();
    }
})

// get Customer PaidReceipt - Start
$('#getCustomerPaidReceip').submit( ( event ) => {
    event.preventDefault();
    var selectedItemValue = $("#customer_name option:selected").val();
    var paymentType = $('#ctype').val();
    const obj = {
        paymentType: paymentType,
        cuCode: customerCode,
        date_in: $("#startDate").val(),
        date_out: $("#endDate").val()
    }
    let dateStart = Date.parse(obj.date_in);
    let dateFinish = Date.parse(obj.date_out);

    if(selectedItemValue == -1 ){
        alert("Seçim yapiniz")
    }else if (dateStart > dateFinish){
        alert("1. tarih alanı, 2.tarih alanından küçük olmalı")
    }else if(customerCode == ''){
        // Kasa seçimi yapilmiş
   //     alert("Kasa İşlemi");
        getPaymentSafe(obj);
    }
    else{
        getPaymentCustomer(obj);
    }

})
// get Customer PaidReceipt - Finish


function reset(){
    $('#startDate').val("");
    $('#endDate').val("");
}

function getPaymentCustomer(obj){
    $.ajax({
        url: './payment-getCustomer',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data.length > 0 ) {
                customerInTableRow(data);
            }else {
                $('#table-safe').hide();
                alert("Veri Bulunamadi");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })
}


function getPaymentSafe(obj){
    $.ajax({
        url: './payment-getSafe',
        type: 'GET',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data.length > 0 ) {
                if(obj.paymentType == 0){
                    safeInTableRow(data);
                }else{
                    safeOutTableRow(data);
                }
            }else {
                $('#table-safe').hide();
                alert("Veri Bulunamadi");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })
}

// PaymentIn - getAllTable Value - Start
function customerInTableRow( data ) {
    $('#table-safe').show();
    let html = `<thead><tr><th>Id</th><th>Ad</th><th>Soyad</th><th>Ödenen Para</th><th>Tarih</th></tr></thead><tbody>`;
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        html += `<tr role="row" class="odd">
            <td>`+itm[0]+`</td>
            <td>`+itm[1]+`</td>
            <td>`+itm[2]+`</td>
            <td>`+itm[3]+`</td>
            <td>`+itm[4]+`</td>
          </tr>`;
    }
    $('#paymentTableRow').html(html);
}
// PaymentIn - getAllTable Value - Finish

// safeIn - getAllTable Value - Start
function safeInTableRow( data ) {
    $('#table-safe').show();
    let html = `<thead><tr><th>Id</th><th>Ad</th><th>Soyad</th><th>Fiş Numarası</th><th>Ödenen Para</th><th>Tarih</th></tr></thead><tbody>`;
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        html += `<tr role="row" class="odd">
            <td>`+itm[0]+`</td>
            <td>`+itm[1]+`</td>
            <td>`+itm[2]+`</td>
            <td>`+itm[3]+`</td>
            <td>`+itm[4]+`</td>
            <td>`+itm[5]+`</td>
          </tr>`;
    }
    html+=`</tbody>`;
    $('#paymentTableRow').html(html);
}
// safeIn - getAllTable Value - Finish

// safeOut - getAllTable Value - Start
function safeOutTableRow( data ) {
    debugger;
    $('#table-safe').show();
    let html = `<thead><tr><th>Id</th><th>İsim</th><th>İşlem Tipi</th><th>Çekilen Tutar</th><th>Ödenen Para</th><th>Tarih</th></tr></thead><tbody>`;
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        const pout_type = itm[4] == 1 ? 'Nakit'
            : (itm[4] == 2) ?'Kredi Kartı'
                : (itm[4] == 3) ?'Havale'
                    : (itm[4] == 4) ?'EFT' : 'Banka Çeki';
        html += `<tr role="row" class="odd">
            <td>`+itm[0]+`</td>
            <td>`+itm[3]+`</td>
            <td>`+pout_type+`</td>
            <td>`+itm[2]+`</td>
            <td>`+itm[5]+`</td>
            <td>`+itm[1]+`</td>
          </tr>`;
    }
    html+=`</tbody>`;
    $('#paymentTableRow').html(html);
}
// safeOut - getAllTable Value - Finish