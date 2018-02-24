class Modal {
    constructor(title, url) {
        this.title = title;
        this.url = url;
        this.dialog = null;
    }

    load(callback) {
        var title = this.title;
        var url = this.url;
        this.dialog = bootbox.dialog({
            title: title,
            message: '<div class="text-center"><i class="fa fa-spinner fa-spin fa-3x fa-fw"></i>' +
            '<span class="sr-only">Cargando...</span></div>',
            onEscape: true
        });
        var dialog = this.dialog;
        $.get(url).done(function(data) {
            dialog.init(function() {
                dialog.find('.bootbox-body').html(data);
                $('.bootbox-body form button[type=reset]').click(function() {
                    dialog.modal('hide');
                });
                callback();
            });
        }).fail(function(data) {
            dialog.find('.bootbox-body').html('<div id="errores-cargando-modal"></div>');
            Notifica.mostrarErroresFromFails(document.getElementById('errores-cargando-modal'), data);
        });
    }

    /**
     * Action after post
     * @callback afterPost
     * @param {string} message
     */
    /**
     * Se realiza un post del formulario incrustado en el modal
     * @param {string} url
     * @param {afterPost} callback para controlar la respuesta del servidor
     */
    postForm(url, callback) {
        var dialog = this.dialog;
        var formObj = document.querySelector('.bootbox-body form');
        var submit = document.querySelector('.bootbox-body form button[type=submit]');
        $(formObj).validate({
            submitHandler: function (form) {
                submit.disabled = true;
                var idLoading = 'loading-form-' + form.name;
                $(form).after('<div class="text-center" id="' + idLoading + '"><i class="fa fa-spinner fa-spin fa-2x fa-fw"></i>Cargando, espere por favor</div>');
                $.post(url, $(form).serialize(), function () {
                })
                    .done(function (rpta) {
                        callback(rpta);
                        dialog.modal('hide');
                        Notifica.success([rpta]);
                    })
                    .fail(function (data) {
                        Notifica.resolveDivErrorFail(form, data);
                    })
                    .always(function () {
                        submit.disabled = false;
                        $('#' + idLoading).remove();
                    });
                return false;
            }
        });
    }

    eliminar(url, callback) {
        this.url = web('/eliminar-form');
        var modal = this;
        modal.load(function() {
            modal.postForm(url, callback);
        });
    }
}