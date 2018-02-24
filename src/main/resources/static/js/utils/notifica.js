class Notifica {
    static success(messages) {
        $.notify({
            // options
            message: messages.join('<br>')
        },{
            // settings
            type: 'success',
            placement: {
                from: "top",
                align: "center"
            }
        });
    }

    static error(messages) {
        $.notify({
            // options
            message: messages.join('<br>')
        },{
            // settings
            type: 'danger',
            placement: {
                from: "top",
                align: "center"
            }
        });
    }

    static notificar500() {
        Notifica.error(['Hubo un error en el servidor. Comuníquese con el administrador']);
    }

    static resolveNotifyFail(data) {
        try {
            Notifica.error(Notifica.resolveMessage(data));
        } catch (e) {
            Notifica.notificar500();
        }
    }

    static resolveDivErrorFail(formObject, data) {
        var classesDivErrors = '.' + formObject.name + '-form.errores';
        var divObject = document.querySelector(classesDivErrors);
        if (divObject === null) {
            divObject = document.createElement('div');
            divObject.classList.add('alert');
            divObject.classList.add('alert-danger');
            divObject.classList.add('errores');
            divObject.classList.add(formObject.name + '-form');
            formObject.append(divObject);
        }
        Notifica.mostrarErroresFromFails(divObject, data);
    }

    /**
     *
     * @param data
     * @return {string[]}
     */
    static resolveMessage(data) {
        var rpta = data.responseJSON || null;
        var messages = new Array();
        if (rpta === null) {
            if (data.messages !== undefined) {
                rpta = data;
            }
        }
        if (rpta !== null) {
            if (rpta.messages === undefined || rpta.messages === null) {
                var message = '';
                if (rpta.error == 'Internal Server Error') {
                    message = rpta.message;
                } else {
                    message = rpta.error + ': ' + rpta.message;
                }
                messages.push(message);
                if (rpta.errors !== undefined) {
                    rpta.errors.forEach(function(item) {
                        if (item.defaultMessage !== undefined) {
                            messages.push(item.field + ' ' + item.defaultMessage);
                        }
                    });
                }
            } else {
                messages = rpta.messages;
            }
        } else {
            rpta = data.responseText || null;
            if (rpta !== null) {
                return [rpta];
            }
            messages = ['Hubo un error en el servidor. Comuníquese con el administrador'];
        }
        return messages;
    }

    static mostrarErroresFromFails(divObject, data) {
        var messages = Notifica.resolveMessage(data);
        var html = '';
        for(var i = 0; i < messages.length; i++) {
            html += '<div class="text-danger">' + messages[i] + '</div>';
        }
        divObject.innerHTML = html;
    }
}