class Submodulo {
    static renderSubmodulosParaPermisos(moduloId, rol) {
        Table.load(innerApi(`/modulo/${moduloId}/submodulos`), 'rol=' + rol, 'permisos-submodulos', function(submodulos){
            var html = '';
            for (var s = 0; s < submodulos.length; s++) {
                var checked = (submodulos[s].rol != null) ? ' checked="checked" ': '';
                html += `<tr></tr><td><i class="fa fa-${submodulos[s].icono}"></i>${submodulos[s].nombre}</td>
                    <td>${submodulos[s].enlace}</td>
                    <td>
                        <input type="checkbox" data-submodulo-id="${submodulos[s].id}"
                        data-rol="${rol}"
                        ${checked}
                        onclick="Submodulo.checkPermiso(this)"/>
                    </td>
                    <td>
                        <i class="fa fa-eye pointer" onclick="Submodulo.renderEnlaces('${submodulos[s].id}', '${submodulos[s].rol}')"></i>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <table class="table table-sm table-responsive-sm" id="enlaces-submodulo-${submodulos[s].id}">
                                <thead>
                                    <tr><th>Enlace</th><th>Descripción</th><th>Método</th><th>Acceso</th></tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </td>
                    </tr>`;
            }
            return html;
        });
    }

    static checkPermiso(input) {
        if (input.checked) {
            post(innerApi(`/auth/submodulo-agregar/${input.dataset.submoduloId}/${input.dataset.rol}`),
                null,
                function(rpta) {});
        } else {
            post(innerApi(`/auth/submodulo-quitar/${input.dataset.submoduloId}/${input.dataset.rol}`),
                null,
                function(rpta) {});
        }
    }

    static renderEnlaces(submoduloId, rol) {
        Table.load(innerApi(`/auth/submodulos/${submoduloId}/enlaces`),
            'rol=' + rol, `enlaces-submodulo-${submoduloId}`, function(enlaces){
                var html = '';
                for (var i = 0; i < enlaces.length; i++) {
                    var checked = (enlaces[i].rol != null) ? ' checked="checked" ': '';
                    html += `<tr>
                                <td>${enlaces[i].nombre}</td>
                                <td>${enlaces[i].descripcion}</td>
                                <td>${enlaces[i].metodo}</td>
                                <td><input type="checkbox" data-enlace-id="${enlaces[i].id}"
                                    data-rol="${rol}" ${checked}
                                    ${checked} onclick="Submodulo.checkPermisoEnlace(this)"/></td>
                                </tr>`;
                }
                return html;
        });
    }

    static checkPermisoEnlace(input) {
        if (input.checked) {
            post(innerApi(`/auth/enlace/permiso-agregar/${input.dataset.enlaceId}/${input.dataset.rol}`), null, function() {});
        } else {
            post(innerApi(`/auth/enlace/permiso-quitar/${input.dataset.enlaceId}/${input.dataset.rol}`), null, function() {});
        }
    }
}