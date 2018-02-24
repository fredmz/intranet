class Table {
    /**
     * Render the table
     * @callback renderTable
     * @param {array} items for rows
     * @return {string} html text
     */

    /**
     * @param {string} url
     * @param {string} idTable
     * @param {string} data serializada con &
     * @param {renderTable} callbackRenderTable with items as param
     */
    static load(url, data, idTable, callbackRenderTable, withPagination) {
        if (withPagination === undefined || withPagination === null) {
            withPagination = false;
        }
        var tbody = document.querySelector('#' + idTable + ' tbody');
        tbody.innerHTML = '<tr><td colspan="100"><div class="text-center"><i class="fa fa-spinner fa-spin fa-3x fa-fw"></i> Cargando el contenido</td></tr>';
        $.get(url, data, function () {
        }, 'json')
            .done(function (items, textStatus, jqXHR) {
                var html = callbackRenderTable(items);
                tbody.innerHTML = html;
                if (withPagination) {
                    var totalItems = jqXHR.getResponseHeader('sia-total-items');
                    var page = jqXHR.getResponseHeader('sia-page');
                    Table.renderPagination(idTable, page, totalItems);
                }
            })
            .fail(function (data) {
                Notifica.mostrarErroresFromFails(tbody, data);
            });
    }

    static renderPagination(idTable, page, totalItems, url) {
        var itemsPerPage = 10;
        var pages = Math.floor(totalItems / itemsPerPage);
        var rest = totalItems % itemsPerPage;
        if (rest > 0) {
            pages++;
        }
        var html = '<nav aria-label="Page navigation example">' +
            '<ul class="pagination justify-content-end">';
        var classDisabled;
        for (var p = 1; p <= pages; p++) {
            classDisabled = (p == page) ? ' disabled' : '';
            html += '<li class="page-item' + classDisabled + '"><button class="page-link">' + p + '</button></li>';
        }
        html += '</ul></nav>';
        document.querySelector('#' + idTable + ' tfoot tr td').innerHTML = html;
    }
}