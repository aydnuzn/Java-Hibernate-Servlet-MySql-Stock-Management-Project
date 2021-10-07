$('#email_send_message').submit( ( event ) => {
    event.preventDefault();
    const email = $("#pass_email").val()
    modalReset();

    $.ajax({
        url: './message-servlet',
        type: 'POST',
        data: { email: email},
        dataType: 'text',
        success: function (data) {
            debugger;
            if ( data == '1' ) {
                createRow(data)
            }else if( data == '2') {
                createRow2(data)
            }else{
                createRow3(data)
            }
        },
        error: function (err) {
            debugger;
            console.log(err)
            createRow4(data)
            alert("İşlem işlemi sırısında bir hata oluştu!");
        }
    })
})

function createRow() {
    let html = ``
    html += `Mesaj Gönderildi`;
    $('#pass_success').html(html);
}
function createRow2() {
    let html = `Mesaj Gönderilemedi`;
    $('#pass_fail').html(html);
}
function createRow3() {
    let html = `Email Sistemde Mevcut Değil`;
    $('#pass_fail').html(html);
}
function createRow4() {
    let html = `İşlem sırısında bir hata oluştu!`;
    $('#pass_fail').html(html);
}

function modalReset(){
    $('#email_send_message').trigger("reset");
    $('#pass_success').html('');
    $('#pass_fail').html('');
}

// modal closed
$(window).on('hidden.bs.modal', function() {
    modalReset();
});