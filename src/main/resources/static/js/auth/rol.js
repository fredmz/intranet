class Rol {
    /**
     * Acción realizada despues de crear el rol
     * @callback callbackOnCreate
     */
    /**
     *
     * @param {callbackOnCreate} callbackOnCreate
     */
    static loadCreateForm(callbackOnCreate) {
        var url = web('/auth/roles-nuevo');
        var modal = new Modal('Registrar rol', url);
        modal.load(function(){
            modal.postForm(url, callbackOnCreate);
        });
    }

    /**
     * Acción realizada despues de actualizar el rol
     * @callback callbackOnUpdate
     */
    /**
     *
     * @param {callbackOnUpdate} callbackOnUpdate
     */
    static loadEditForm(callbackOnUpdate, rolName) {
        var url = web('/auth/roles-editar/' + rolName);
        var modal = new Modal('Actualizar rol', url);
        modal.load(function(){
            modal.postForm(url, callbackOnUpdate);
        });
    }

    static eliminar(login, callback) {
        var modal = new Modal("¿Desea eliminar el rol '" + login + "'?", '');
        modal.eliminar(web('/auth/roles-eliminar/' + login), callback);

    }

    static loadPermisos(rolName) {
        var url = web('/auth/permisos/' + rolName);
        var modal = new Modal('Permisos para ' + rolName, url);
        modal.load(function(){
            modal.postForm(url);
        });
    }
}