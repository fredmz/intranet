package web.intranet.auth.web.utils

class Pager {
    private val buttonsToShow = 5
    var startPage: Int = 0
    var endPage: Int = 0

    constructor(totalPages: Int, currentPage: Int) {
        val halfPagesToShow = buttonsToShow / 2

        if (totalPages <= buttonsToShow) {
            startPage = 1
            endPage = totalPages

        } else if (currentPage - halfPagesToShow <= 0) {
            startPage = 1
            endPage = buttonsToShow

        } else if (currentPage + halfPagesToShow == totalPages) {
            startPage = currentPage - halfPagesToShow
            endPage = totalPages

        } else if (currentPage + halfPagesToShow > totalPages) {
            startPage = totalPages - buttonsToShow + 1
            endPage = totalPages

        } else {
            startPage = currentPage - halfPagesToShow
            endPage = currentPage + halfPagesToShow
        }

    }
}