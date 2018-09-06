$( document ).ready(function() {
    console.log( "ready!" );
    tinymce.init({
        selector: 'textarea',
        language: 'ru',
        height: 500,
        theme: 'modern',
        relative_urls: false,
        mode : "exact",
        elements : "page_text",
        branding: false,
        plugins: 'print preview searchreplace autolink directionality visualblocks visualchars fullscreen image link media template codesample table charmap hr pagebreak nonbreaking anchor toc insertdatetime advlist lists textcolor wordcount imagetools contextmenu colorpicker textpattern help',
        toolbar1: 'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat',
        image_advtab: true,
        content_css: [
            '/resources/common/css/fonts.googleapis.css',
            '/resources/common/css/codepen.min.css'
        ]
    });
});