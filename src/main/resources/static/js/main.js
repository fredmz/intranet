function web(url) {
    var url = url || '';
    return window.location.origin + url;
}

function innerApi(url) {
    var url = url || '';
    return window.location.origin + '/api' + url;
}

function currentPath() {
    return location.protocol + '//' + location.host + location.pathname;
}

function limpiarFiltro() {
    window.location.href = currentPath();
}

function replaceUrlParam(param, value) {
    var urlParams = new URLSearchParams(window.location.search);
    urlParams.delete(param);
    urlParams.append(param, value);
    return urlParams.toString();
}

function crearVinculosPaginacion() {
    var links = document.querySelectorAll('.pagination-load a');
    links.forEach(function(a) {
        a.href = currentPath() + '?' + replaceUrlParam('page', a.dataset.page);
    })
}

function reloadAfterPost() {
    setInterval(function(){
        window.location.reload();
    }, 2000);
}

function getJson(url, callback) {
    $.get(url, null, function(){}, 'json')
        .done(function(rpta) {
            callback(rpta);
        })
        .fail(function(rpta) {
            Notifica.resolveNotifyFail(rpta);
        });
}

function post(url, data, callback) {
    $.post(url, data, function() {})
        .done(function(rpta) {
            callback(rpta);
            Notifica.success([rpta]);
        })
        .fail(function(rpta) {
            Notifica.resolveNotifyFail(rpta);
        });
}

function formNormalPost(idForm) {
    $('#' + idForm).validate();
}

function initLogin() {
    sessionStorage.clear();
}


