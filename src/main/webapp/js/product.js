// Product

// Product Add - Begin
let select_id = 0
$('#product_add_form').submit( ( event ) => {
    event.preventDefault();

    const pname = $("#pname").val()
    const aprice = $("#aprice").val()
    const oprice = $("#oprice").val()
    const pcode = $("#pcode").val()
    const ptax = $("#ptax").val()
    const psection = $("#psection").val()
    const psize = $("#psize").val()
    const pdetail = $("#pdetail").val()

    const obj = {
        pr_name: pname,
        pr_code: pcode,
        pr_buyPrice: aprice,
        pr_sellPrice: oprice,
        pr_kdv: ptax,
        pr_unitType: psection,
        pr_detail: pdetail,
        pr_quantity: psize
    }
    console.log(obj);

    if ( select_id != 0 ) {
        // update
        obj["pr_id"] = select_id;
    }
    $.ajax({
        url: './product-post',
        type: 'POST',
        data: { obj: JSON.stringify(obj) },
        dataType: 'JSON',
        success: function (data) {
            if ( data > 0 ) {
                alert("İşlem Başarılı")
                fncReset();
            }else {
                alert("İşlem sırasında hata oluştu!");
            }
        },
        error: function (err) {
            console.log(err)
            alert("İşlem işlemi sırısında bir hata oluştu!");
        }
    })
})
// Product Add - Finish

// all product list - start
function allProduct() {
    $.ajax({
        url: './product-get',
        type: 'GET',
        dataType: 'Json',
        success: function (data) {
            createRow(data);
        },
        error: function (err) {
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
        let tempTax = '';
        switch(itm.pr_kdv) {
            case 0: tempTax = 'Dahil'; break;
            case 1: tempTax = '%1'; break;
            case 2: tempTax = '%8'; break;
            case 3: tempTax = '%18'; break;
            default: break;
        }
        console.log("tempTax : " + tempTax);

        let tempSection = '';
        switch(itm.pr_unitType) {
            case 0: tempSection = 'Adet'; break;
            case 1: tempSection = 'KG'; break;
            case 2: tempSection = 'Metre'; break;
            case 3: tempSection = 'Paket'; break;
            case 4: tempSection = 'Litre'; break;
            default: break;
        }
        console.log("tempSection : " + tempSection);

        html += `<tr role="row" class="odd">
            <td>`+itm.pr_id+`</td>
            <td>`+itm.pr_name+`</td>
            <td>`+itm.pr_buyPrice+`</td>
            <td>`+itm.pr_sellPrice+`</td>
            <td>`+itm.pr_code+`</td>
            <td>`+tempTax+`</td>
            <td>`+tempSection+`</td>
            <td>`+itm.pr_quantity+`</td>
            <td class="text-right" >
              <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                <button onclick="fncProductDelete(`+itm.pr_id+`)" type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                <button onclick="fncProductDetail(`+i+`)" data-bs-toggle="modal" data-bs-target="#productDetailModel" type="button" class="btn btn-outline-primary "><i class="far fa-file-alt"></i></button>
                <button onclick="fncProductUpdate(`+i+`)" type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button>
              </div>
            </td>
          </tr>`;
    }
    $('#tableRow').html(html);
}

function codeGenerator() {
    const date = new Date();
    const time = date.getTime();
    const key = time.toString().substring(4);
    //    ccode = $('#ccode').val( key )  // ccode ye atadım
    pcode = $('#pcode').val( key )  // pcode ye ekledim
    return key; //yeni eklendi
}

// yeni eklendi
function beginForm(temp) {
    select_id = 0; // buna gerek olmayabilir
    let item = globalArr;
    for (let i = 0; i < item.length; i++) {
        if(temp == item[i].pr_code){
            fncProductUpdate(i);
            break;
        }
    }
}
beginForm(codeGenerator());

// Bu kısımda yeni eklendi
$( "#pcode" ).keyup(function() {
    console.log($("#pcode").val())
    let temp = $("#pcode").val();
    beginForm(temp);
});

//************Direk veri getirme********************

allProduct();
// all product list - end

// reset fnc
function fncReset() {
    select_id = 0;
    $('#product_add_form').trigger("reset");
    codeGenerator();
    allProduct();
}

// product delete - start
function fncProductDelete( pr_id ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: './product-delete?pr_id='+pr_id,
            type: 'DELETE',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    fncReset();
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
// product delete - end

// product detail - start
function fncProductDetail(i){
    const itm = globalArr[i];
    $("#pr_name").text(itm.pr_name.toUpperCase() + " - " + itm.pr_id);
    $("#pr_code").text(itm.pr_code == "" ? '------' : itm.pr_code);
    $("#pr_buyPrice").text(itm.pr_buyPrice == "" ? '------' : itm.pr_buyPrice);
    $("#pr_sellPrice").text(itm.pr_sellPrice == "" ? '------' : itm.pr_sellPrice);
    $("#pr_kdv").text(itm.pr_kdv == 0 ? 'Dahil'
        : (itm.pr_kdv == 1) ?'%1'
        : (itm.pr_kdv == 2) ?'%8': '%18');
    $("#pr_unitType").text(itm.pr_unitType == 0 ? 'Adet'
        : (itm.pr_unitType == 1) ?'KG'
        : (itm.pr_unitType == 2) ?'Metre'
        : (itm.pr_unitType == 3) ?'Paket': 'Litre');
    $("#pr_quantity").text(itm.pr_quantity == "" ? '------' : itm.pr_quantity);
    $("#pr_detail").text(itm.pr_detail == "" ? '------' : itm.pr_detail);
}
// product detail - end

// product update select - start
function fncProductUpdate( i ) {
    debugger;
    const itm = globalArr[i];
    select_id = itm.pr_id
    $("#pname").val(itm.pr_name)
    $("#aprice").val(itm.pr_buyPrice)
    $("#oprice").val(itm.pr_sellPrice)
    $("#pcode").val(itm.pr_code)
    $("#ptax").val(itm.pr_kdv)
    $("#psection").val(itm.pr_unitType)
    $("#psize").val(itm.pr_quantity)
    $("#pdetail").val(itm.pr_detail)
}
// product update select - finish