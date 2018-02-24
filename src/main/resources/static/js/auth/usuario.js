class Usuario {
    /**
     *
     */
    static loadCambiarClave() {
        var url = web('/cambiar-clave');
        var modal = new Modal('Cambiar clave', url);
        modal.load(function(){
            modal.postForm(url, function() {
            });
        });
    }

    /**
     * Acción realizada despues de crear el usuario
     * @callback callbackOnCreate
     */
    /**
     *
     * @param {callbackOnCreate} callbackOnCreate
     */
    static loadCreateForm(callbackOnCreate) {
        var url = web('/auth/usuarios-nuevo');
        var modal = new Modal('Registrar usuario', url);
        modal.load(function(){
            modal.postForm(url, callbackOnCreate);
        });
    }

    /**
     * Acción realizada despues de actualizar el usuario
     * @callback callbackOnUpdate
     */
    /**
     *
     * @param {callbackOnUpdate} callbackOnUpdate
     */
    static loadEditForm(callbackOnUpdate, id) {
        var url = web('/auth/usuarios-editar/' + id);
        var modal = new Modal('Actualizar usuario', url);
        modal.load(function(){
            modal.postForm(url, callbackOnUpdate);
        });
    }

    static loadTable(reset) {
        if (reset === undefined || reset === null) {
            reset = false;
        }

        var url = innerApi('/auth/usuarios');
        var idTabla = 'tabla-usuarios';
        var form = document.getElementById('form-filtro-usuario');
        var params = '';
        if (reset) {
            form.reset();
        } else {
            params = $(form).serialize();
        }
        Table.load(url, params, idTabla, function(usuarios) {
            var html = '';
            for (var p = 0; p < usuarios.length; p++) {
                html += '<tr><td>' + usuarios[p].login + '</td>' +
                    '<td>' + usuarios[p].correo + '</td>' +
                    '<td>' + usuarios[p].habilitado + '</td>' +
                    '<td>' + usuarios[p].rol.nombre + '</td>' +
                    '<td>' +
                    '<div class="btn-group" role="group">' +
                    '<button onclick="editarUsuario(\'' + usuarios[p].login + '\')" class="btn btn-primary btn-sm btn-usuario-editar" data-toggle="tooltip" data-placement="top" title="Editar usuario"><i class="fa fa-edit"></i></button>' +
                    '<button onclick="eliminarUsuario(\'' + usuarios[p].login + '\')" class="btn btn-danger btn-sm btn-usuario-eliminar" data-toggle="tooltip" data-placement="top" title="Eliminar usuario"><i class="fa fa-remove"></i></button>' +
                    '</div>' +
                    '</td>' +
                    '</tr>';
            }
            return html;
        });
    }

    static eliminar(login, callback) {
        var modal = new Modal("¿Desea eliminar el usuario '" + login + "'?", '');
        modal.eliminar(web('/auth/usuarios-eliminar/' + login), callback);

    }

    static loadRecuperarClave() {
        var url = web('/recuperar-clave');
        var modal = new Modal('Recuperar clave', url);
        modal.load(function(){
            modal.postForm(url, function() {

            });
        });
    }

    static expotar() {
        window.location.href = web('/auth/usuarios-exportar?' + $('#form-filtro-usuario').serialize());
    }
}