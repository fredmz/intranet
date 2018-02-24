class Pais {
    static url() {
        return '/ubicacion/paises'
    }

    static loadCreateForm(callbackOnCreate) {
        var url = Pais.url() + '-nuevo';
        var modal = new Modal('Nuevo pais', url);
        modal.load(function(){
            modal.postForm(url, callbackOnCreate);
        });
    }

    static loadJson() {
        var url = Pais.url() + '.json';
        var idTabla = 'tabla-paises';
        Table.load(url, idTabla, function(paises) {
            var html = '';
            for (var p = 0; p < paises.length; p++) {
                html += '<tr><td>' + paises[p].nombre + '</td><td></td></tr>';
            }
            return html;
        });
    }
}