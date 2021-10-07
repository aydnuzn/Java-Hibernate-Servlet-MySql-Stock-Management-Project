// Kasa Cikis
// add - start
let select_id = 0
$('#payout_add_form').submit( ( event ) => {
    event.preventDefault();

    const payOutTitle = $("#payOutTitle").val()
    const payOutType = $("#payOutType").val()
    const payOutTotal = $("#payOutTotal").val()
    const payOutDetail = $("#payOutDetail").val()

    const obj = {
        pout_name: payOutTitle,
        pout_paymentType: payOutType,
        pout_price: payOutTotal,
        pout_detail: payOutDetail
    }

    if ( select_id != 0 ) {
        // update
        obj["pout_id"] = select_id;
    }
    $.ajax({
        url: './payout-post',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            debugger;
            debugger;
            if ( data[0] == 0 ) {
                alert("İşlem Başarılı. \nKasada Kalan Para : " + data[1])
                fncReset();
            }else {
                alert("Kasadaki mevcut paradan" + data[1] + " daha fazla para çekilmeye çalışıldı.\nTekrardan para çekimi yapınız.");
            }
        },
        error: function (err) {
            debugger;
            console.log(err)
            alert("İşlem işlemi sırısında bir hata oluştu!");
        }
    })

})
// add - end

// all payout list - start
function allPaymentOut() {
    $.ajax({
        url: './payout-get',
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            debugger;
            createRow(data);
        },
        error: function (err) {
            debugger;
            console.log(err)
        }
    })

}


let globalArr = []
function createRow( data ) {
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data[i];
        const pout_type = itm.pout_paymentType == 1 ? 'Nakit'
            : (itm.pout_paymentType == 2) ?'Kredi Kartı'
            : (itm.pout_paymentType == 3) ?'Havale'
            : (itm.pout_paymentType == 4) ?'EFT' : 'Banka Çeki';
        html += `<tr role="row" class="odd">
            <td>`+itm.pout_id+`</td>
            <td>`+itm.pout_name+`</td>
            <td>`+pout_type+`</td>
            <td>`+itm.pout_price+`</td>
            <td>`+itm.pout_detail+`</td>
            <td class="text-right" style="display: flex; justify-content: center;">
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncPayOutDelete(`+itm.pout_id+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
              </div>
            </td>
          </tr>`;
    }
    $('#tableRow').html(html);
}

// All PaymentOut List
allPaymentOut()

// reset fnc
function fncReset() {
    select_id = 0;
    $('#payout_add_form').trigger("reset");
    allPaymentOut();
}


// paymentOut delete - start
function fncPayOutDelete( pout_id ) {
    debugger;
    let answer = confirm("Silmek istediğinizden emin misiniz?");

    if ( answer ) {
        $.ajax({
            url: './payout-delete?pout_id='+pout_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                debugger;
                if ( data != "0" ) {
                    fncReset();
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
// paymentOut delete - end


// paymentOut detail - start
function fncPayOutDetail(i){
    const itm = globalArr[i];
    $("#pout_name").text(itm.pout_name + " - " + itm.pout_id);
    $("#pout_paymentType").text(itm.pout_paymentType == 1 ? 'Nakit'
        : (itm.pout_paymentType == 2) ?'Kredi Kartı'
            : (itm.pout_paymentType == 3) ?'Havale'
                : (itm.pout_paymentType == 4) ?'EFT': 'Banka Çeki');
    $("#pout_price").text(itm.pout_price == "" ? '------' : itm.pout_price);
    $("#pout_detail").text(itm.pout_detail == "" ? '------' : itm.pout_detail);
}
// paymentOut detail - end

// paymentOut update select - start
function fncPayOutUpdate( i ) {
    const itm = globalArr[i];
    select_id = itm.pout_id;
    $("#payOutTitle").val(itm.pout_name)
    $("#payOutType").val(itm.pout_paymentType)
    $("#payOutTotal").val(itm.pout_price)
    $("#payOutDetail").val(itm.pout_detail)
}
// paymentOut update select - finish



// search - start
$('#search_payout_form').submit( ( event ) => {
    debugger;
    event.preventDefault();
    var searchPaymentOut = $('#search_payout').val()
    $.ajax({
        url: './search-payment-out',
        type: 'GET',
        data: { searchPayOut: JSON.stringify(searchPaymentOut) },
        dataType: 'JSON',
        success: function (data) {
            console.log("arama yapildi")
            createRow(data);
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })
})
// search - end

// search kismi degistigi gibi yakalayacak ve tamamen bos oldugunda tabloya verileri getirecek
$( "#search_payout" ).keyup(function() {
    console.log($("#search_payout").val())
    let temp = $("#search_payout").val();
    if(temp == ""){
        allPaymentOut();
    }
});

