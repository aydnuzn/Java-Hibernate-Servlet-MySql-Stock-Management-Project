
/*
// https://stackoverflow.com/questions/25720986/bootstrap-select-how-to-fire-event-on-change
$(function() {
    $('#cname2').on('change', function(e){
        console.log(this.value,
            this.options[this.selectedIndex].value,
            $(this).find("option:selected").val(),
            $(this).find("option:selected").attr("data-subtext"),);
    });
});
*/

let customerCode = '';
$('#customer_name').change(function () {
    var selectedItem = $("#customer_name option:selected").attr("data-subtext");
    customerCode = selectedItem;

    // Müşteri değiştiği zaman ödeme tutari ve ödeme detay resetlensin
    $('#payInTotal').val("");
    $('#payInDetail').val("");

    // Customer Seçim yapınıza geldiğinde fis kismi Secim Yapiniz a gelecek
    var pSelected = $('#customer_name option:selected').text();
    if(pSelected == "Seçim Yapınız"){
        $('#cUnpaid_receipt').val('Seçim Yapınız');
        $('#cUnpaid_receipt').selectpicker("refresh");
    }
    // Seçilen Müşterinin Odenmeyen fislerini Getirme
    getShoppingCart(customerCode);
})


// Fiş seçilinde Ödeme tutarında direk kalan borç yazılacak
// Seçim yapınıza gelindiğinde Ödeme tutarı ve detayı sıfırlanacak
$('#cUnpaid_receipt').change(function () {
    debugger;
    // Fiş Seçim yapınıza geldiğinde, Ödeme tutarı ve detayı sıfırlanacak
    var pSelected = $('#cUnpaid_receipt option:selected').text();
    if(pSelected == "Seçim Yapınız"){
        $('#payInTotal').val("");
        $('#payInDetail').val("");
    }else{
        console.log("globalArr.lenght = "+globalArr.length)
        for (let i = 0; i < globalArr.length; i++) {
            let temp = globalArr[i];
            if(temp[0] == pSelected){
                $('#payInTotal').val(temp[1]-temp[2]);
                break;
            }
        }
    }
})


let receiptNumber = '';
$('#cUnpaid_receipt').change(function () {
    var selectedItem = $("#cUnpaid_receipt option:selected").val();
    receiptNumber = selectedItem;
    console.log(receiptNumber);
})


// customer unPaidReceipt - start
function getShoppingCart(code) {
    $.ajax({
        url: './unpaid-receipt',
        type: 'GET',
        data: {code: code},
        dataType: 'Json',
        success: function (data) {
            createUnPaidReceipt(data);
        },
        error: function (err) {
            console.log(err)
        }
    })
}
// customer unPaidReceipt - finish

let globalArr = []

function createUnPaidReceipt( data ) {
    console.log("getCall createUnPaidReceipt")
    let html = `<option data-subtext="">Seçim Yapınız</option>`;
    if(data != null){
        for (let i = 0; i < data.length; i++) {
            globalArr[i] = data[i];
            const itm = data[i];
            const unPaidPrice = itm[1]-itm[2];
            html += `<option data-subtext="total = `+itm[1]+` paid = `+itm[2]+` unpaid = `+unPaidPrice+`">`+itm[0]+`</option>`;
        }
    }
    console.log(html);
    $('#cUnpaid_receipt').html(html);
    $("#cUnpaid_receipt").selectpicker('refresh');
}

function fncReset() {
    //select_id = 0;
    $('#paymentIn_add_form').trigger("reset");
    $('#customer_name').val('Seçim Yapınız');
    $("#customer_name").selectpicker('refresh');
    receiptNumber = '';
    customerCode = '';
    getShoppingCart(customerCode);
}


// insert PaymentIn - Start
$('#paymentIn_add_form').submit( ( event ) => {
    event.preventDefault();
    const obj = {
        cuCode: customerCode,
        receipt_number: receiptNumber,
        pin_price: $("#payInTotal").val(),
        pin_detail: $("#payInDetail").val()
    }

    $.ajax({
        url: './payment-in',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data[0] == 0 ) {
                alert("İşlem Başarılı. \nKalan Borç : " + data[1])
                fncReset();
                getAllPaymentIn();
            }else {
                alert(data[1] + " daha fazla para girişi yapıldı. Odemeyi tekrar yapınız");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })
})
// insert PaymentIn - Finish


// getAllPaymentIn - start
function getAllPaymentIn() {
    $.ajax({
        url: './all-payment-in',
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            paymentInTableRow(data);
        },
        error: function (err) {
            console.log(err)
        }
    })
}
getAllPaymentIn();
// getAllPaymentIn - finish


// PaymentIn - getAllTable Value - Start
function paymentInTableRow( data ) {
    let html = ``
    validTableInfo = []; // array boşaltılacak
    console.log("validTableInfo Length --> " + validTableInfo.length)
    if( data != null){
        for (let i = 0; i < data.length; i++) {
            const itm = data[i];
            validTableInfo[i] = data[i]; // -------------------- Mevcut tablo bilgileri kaydedilir
            html += `<tr role="row" class="odd">
            <td>`+itm[0]+`</td>
            <td>`+itm[1]+`</td>
            <td>`+itm[2]+`</td>
            <td>`+itm[3]+`</td>
            <td>`+itm[4]+`</td>
            <td class="text-right" style="display: flex; justify-content: center;">
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncPayInDelete(`+itm[0]+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
              </div>
            </td>
          </tr>`;
        }
        console.log("validTableInfo Length --> " + validTableInfo.length)
    }
    $('#paymentInTableRow').html(html);
}
// PaymentIn - getAllTable Value - Finish

let validTableInfo = []
// paymentIn delete - start
function fncPayInDelete( pin_id ) {
    debugger;
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {
        $.ajax({
            url: './payin-delete?pin_id='+pin_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                debugger;
                if ( data != "0" ) {
                    // Silme gerçekleşti, aynı tablo gelecek
                    console.log("Data length Before Delete --> " + validTableInfo.length)
                    for (let i = 0; i < validTableInfo.length; i++) {
                        var temp = validTableInfo[i];
                        if(temp[0] == data){
                            validTableInfo.splice(i,1); // i. indexteki array elemanini kaldirir. Null yapmaz.
                            break;
                        }
                    }
                    console.log("Data length Before Delete --> " + validTableInfo.length)
                    paymentInTableRow(validTableInfo)  // ------------------------ Sildikten sonra mevcut bilgiler gorunsun

                    // Customer -> receipt refresh
                    getShoppingCart(customerCode);
                    $('#payInTotal').val("");
                    $('#payInDetail').val("");

                    alert("Silme İşlemi Başarılı");
                }else {
                    alert("**Silme sırasında bir hata oluştu!**");
                }
            },
            error: function (err) {
                debugger;
                console.log(err)
            }
        })

    }
}
// paymentIn delete - end


// search - start
$('#search_payIn_form').submit( ( event ) => {
    event.preventDefault();
    var searchPaymentIn = $('#search_payIn').val()
    $.ajax({
        url: './search-payment-in',
        type: 'GET',
        data: { searchPayIn: JSON.stringify(searchPaymentIn) },
        dataType: 'JSON',
        success: function (data) {
            console.log("arama yapildi")
            paymentInTableRow(data);
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })


})
// search - end

// search kismi degistigi gibi yakalayacak ve tamamen bos oldugunda tabloya verileri getirecek
$( "#search_payIn" ).keyup(function() {
    console.log($("#search_payIn").val())
    let temp = $("#search_payIn").val();
    if(temp == ""){
        getAllPaymentIn();
    }
});