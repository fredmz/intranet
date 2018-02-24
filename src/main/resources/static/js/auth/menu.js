class Menu {
    static load() {
        var menuSession = sessionStorage.getItem('menu');
        if (menuSession == null) {
            getJson(innerApi('/auth/menu-items'), function(menu) {
                Menu.render(menu);
                sessionStorage.setItem('menu', JSON.stringify(menu));
            });
        } else {
            Menu.render(JSON.parse(menuSession));
        }
    }

    static render(menuItems) {
        var html = '<div class="collapse show" id="menu"><ul class="nav flex-column flex-nowrap">';
        for (var i = 0; i < menuItems.length; i++) {
            html += Menu.renderItem(menuItems[i]);
        }
        html += '</ul></div>'
        document.getElementById('menu-vertical').innerHTML = html;
    }

    static renderItem(item) {
        var html = '';
        if (item.subMenuItems.length > 0) {
            html = Menu.renderItemConHijos(item);
        } else {
            html = Menu.renderItemSinHijos(item);
        }
        return html;
    }

    static renderItemConHijos(item) {
        var id = 'menu' + Math.floor((Math.random() * 1000) + 1);
        var html = '<li class="nav-item">' +
            '<a class="nav-link collapsed" href="#' + id + '" data-toggle="collapse" data-target="#' + id + '" aria-controls="' + id + '">' +
            '<i class="fa fa-' + item.icono + '"></i> ' +
            '<span>' + item.nombre + '</span>' +
            '</a>';
        html += '<div class="collapse" id="' + id + '" aria-expanded="false">' +
            '<ul class="flex-column nav pl-2">';
        for (var i = 0; i < item.subMenuItems.length; i++) {
            html += Menu.renderItemSinHijos(item.subMenuItems[i]);
        }
        html += '</ul></div>';
        return html;
    }

    static renderItemSinHijos(item) {
        var html = '<li class="nav-item">' +
            '<a class="nav-link" href="' + item.link + '">' +
            '<i class="fa fa-' + item.icono + '"></i> ' +
            '<span>' + item.nombre + '</span>' +
            '</a>';
        return html;
    }
}