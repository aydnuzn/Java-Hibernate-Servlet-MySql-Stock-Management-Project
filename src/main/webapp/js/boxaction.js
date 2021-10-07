//Müşteri seçildiğinde devreye giriyor.
// Seçimde Seçim yapınız seçilirse değer boş oluyor
// Buna göre kontrol sağlanacak
// yani seçim yapınız seçilirse, ne sepette ürün kalacak ne de ürünler kısmında ürünler olacak

// İlk önce ürünler seçilirse, customer seçim yapınızda kalıyor. Ardından müşteri seçildiğinde seçilen ürün
// silinmesin diye ilk değer kontrolu yapildi.

let receiptNumber = "";

let fValue = '';
function firstVal(){
    fValue = $('#cname option:selected').text();
}
firstVal();


let customerCode = '';
$('#cname').change(function () {
    var selectedItem = $("#cname option:selected").attr("data-subtext");
    customerCode = selectedItem;
    console.log("selectedCustomer : " + selectedItem);

    receiptNumber = ""; // Her musteri degistiginde fis sifirlansin
    $('#or_quantity').val(""); // Adet miktarı sıfırlansın

    // *****************  Müşterilere tıkladınıgında Ürünler kısmı Seçim yapınız a gelsin Kontrolleri Başlangıç ***************
    var selectedValue = $("#cname option:selected").text();

    // Customer değiştiği gibi ürün kısmı Seçim Yapınız a gelecek
    var pSelected = $('#pname option:selected').text();
    if(pSelected != "Seçim Yapınız" && fValue != "Seçim Yapınız" && selectedValue != "Seçim Yapınız"){
        $('#pname').val('Seçim Yapınız');
        $('#pname').selectpicker("refresh");
    }
    fValue = '';
    // *****************  Müşterilere tıkladınıgında Ürünler kısmı Seçim yapınız a gelsin Kontrolleri Bitti ***************

    // Müşteri olarak, 'Seçim Yapınız' seçilirse tüm değerler sıfırlansın
    var isSelectedCustomer = $("#cname option:selected").val();
    if(isSelectedCustomer == 'Seçim Yapınız'){
        $('#pname').val('Seçim Yapınız');
        $('#pname').selectpicker("refresh");
        $('#or_quantity').val("");
        $('#or_receiptNumber').val("");
        customerCode = ''
    }

    // Seçilen Müşterinin Sepetindeki Ürünleri Getirme - Start
    getShoppingCart(customerCode);
});

// Ürünler kısmında seçim yapılınca devreye girecek. Yani değişim olunca.
// Bu kısım başta boş olacak. Müşteri seçimi yapılınca o zaman ürünler eklenecek
// ardından ürün seçimi yapılınca bi değişkende bu ürünün kodunu tutucaz.
// üst taraftada müşteri kodunu tutucaz böylece EKLE butonuna basıldıgında
// bu 2 degeri servlet kısmına yollayıp database işlemleri yapılacak.
let productCode = '';
$('#pname').change(function () {
    var selectedItem = $("#pname option:selected").attr("data-subtext");
    productCode = selectedItem;
    console.log("selectedProduct : " + selectedItem);
});


// Order - Shopping Cart Add - Start
let select_id = 0;
$('#cart_add_form').submit( ( event ) => {
    event.preventDefault();
    debugger;
    const obj = {
        cuCode: customerCode,
        prCode: productCode,
        or_quantity: $("#or_quantity").val(),
        or_receiptNumber: $("#or_receiptNumber").val()
    }
    $.ajax({
        url: './shopping-cart-post',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            debugger;
            if ( data[0] == 0 ) {
                alert("İşlem Başarılı")
                $('#pname').val('Seçim Yapınız');
                $('#pname').selectpicker("refresh");
                $('#or_quantity').val(""); // Adet miktarı sıfırlansın
                getShoppingCart(customerCode);
            }else {
                alert("Eklemek istediginiz üründen stokta " + data[1] + " adet kalmıştır.");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })
})
// Order - Shopping Cart Add - Finish

// selectedCustomer shopping-cart - start
function getShoppingCart(code) {
    debugger;
    $.ajax({
        url: './shopping-cart-get',
        type: 'GET',
        data: {code: code},
        dataType: 'Json',
        success: function (data) {
            debugger;
            if(data!=null && data.length > 0){
                $('#order_add_form').show();
                $('#order-cart-head').show();
                createRow(data);
            }else{
                $('#order_add_form').hide();
                $('#order-cart-head').hide();
                createRow(data);
            }

        },
        error: function (err) {
            debugger;
            console.log(err)
        }
    })
}
// selectedCustomer shopping-cart - finish


let globalArr = []
function createRow( data ) {
    debugger;
    globalArr = data;
    console.log(data)
    let html = ``;
    if(data != null){
        if(data.length > 0){
            // Sepette ürün varsa -> Kullanıcıya aynı fis numarasi gelsin.
            receiptNumber = data[0].receipt.receipt_number;
            $('#or_receiptNumber').val(receiptNumber);
        }else{  // Sepette ürün yoksa rasgele fiş numarası atansın
            $('#or_receiptNumber').val(codeGenerator());
        }
        let tempSection = '';
        let kdv = 0;
        for (let i = 0; i < data.length; i++) {
            const itm = data[i];
            switch(itm.product.pr_kdv) {
                case 0: tempSection = 'Dahil';  kdv=0;   break;
                case 1: tempSection = '%1';     kdv= 1;  break;
                case 2: tempSection = '%8';     kdv= 8;  break;
                case 3: tempSection = '%18';    kdv= 18; break;
                default: break;
            }
            let price = itm.product.pr_sellPrice*itm.or_quantity;
            const totalPrice = price + Math.floor(price*kdv/100);
            console.log(itm);


            html += `<tr role="row" class="odd">
            <td>`+itm.or_id+`</td>
            <td>`+itm.product.pr_name+`</td>
            <td>`+itm.product.pr_sellPrice+`</td>
            <td>`+itm.or_quantity+`</td>
            <td>`+totalPrice+`</td>
            <td>`+tempSection+`</td>
            <td>`+itm.receipt.receipt_number+`</td>
            <td>`+itm.customer.cu_name+`</td>
            <td class="text-right" >
               <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                 <button onclick="fncOrderDelete(`+itm.or_id+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
               </div>
            </td>          
          </tr>`;
        }
    }
    $('#tableRow').html(html);
}

function fncOrderDelete( orderId ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {
        let tmp = orderId;
        $.ajax({
            url: './shopping-cart-delete?orderId='+tmp,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
            //        select_id = 0;
                    getShoppingCart(data);
                }else {
                    alert("Silme sırasında bir hata oluştu!");
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
}

function codeGenerator() {
    const date = new Date();
    const time = date.getTime();
    const key = time.toString().substring(4);
    return key;
}

$('#order_add_form').submit( ( event ) => {
    event.preventDefault();
    const obj = {
    //    cuCode: customerCode,
    //    prCode: productCode,
    //    or_quantity: $("#or_quantity").val(),
        //or_receiptNumber: $("#or_receiptNumber").val()
        or_receiptNumber: receiptNumber
    }

    $.ajax({
        url: './order-add-post',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data > 0 ) {
                alert("İşlem Başarılı")
                getShoppingCart(data);
            }else if(data[0] == "0"){
                let message = '';
                for (let i = 1; i<data.length; i++){
                    message+= data[i] + '\n';
                }
                alert(message);
            } else {
                alert("İşlem sırasında hata oluştu!");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem sırısında bir hata oluştu!");
        }
    })
})


