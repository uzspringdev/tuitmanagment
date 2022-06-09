$(document).ready(function () {
    $('#sidebarCollapse').on('click', function () {
        $('#sidebar').toggleClass('active');
    });
});

//student image preview
function previewFile() {
    const file = $("input[type=file]").get(0).files[0];
    if (file) {
        const reader = new FileReader();

        reader.onload = function () {
            $("#preview").attr("src", reader.result);
        }
        reader.readAsDataURL(file);
    }
}

function loadPhoto(obj) {
    const files = obj.files;
    if (files.length > 0) {
        if (files[0].size <= (2000 * 1024)) {
            const reader = new FileReader();
            reader.onloadend = function () {
                $('#preview').attr('src', reader.result);
                $('#photo').val(reader.result.replace(/^data:.+;base64,/, ''));
            }
            reader.readAsDataURL(files[0]);
        } else {
            alert("More than 2000 kb");
        }
    }
    //document.getElementById("preview").src = window.URL.createObjectURL(obj.files[0]);
}

function clearPhoto() {
    document.getElementById('inputPhoto').value = null;
    document.getElementById("preview").src = "img/no-image.jpg";
    document.getElementById('photo').value = "";
}
//Generate resume
$('#savePDF').click(function() {

    const element = document.getElementById('contentID');
    const opt = {
        margin:       1,
        filename:     'student.pdf',
        image:        { type: 'jpeg', quality: 0.98 },
        html2canvas:  { scale: 2 },
        jsPDF:        { unit: 'in', format: 'letter', orientation: 'landscape' }
    };
    // Avoid page-breaks on all elements, and add one before #page2el.
    /*html2pdf().set({
        pagebreak: { mode: 'avoid-all', before: '#contentID' }
    });*/

// New Promise-based usage:
    html2pdf().set(opt).from(element).save();

/*// Old monolithic-style usage:
    html2pdf(element, opt);*/
});
