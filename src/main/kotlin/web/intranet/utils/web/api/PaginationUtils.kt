package web.intranet.utils.web.api

import org.springframework.web.util.UriComponentsBuilder
import org.springframework.http.HttpHeaders
import org.springframework.data.domain.Page



class PaginationUtils {
    fun generatePaginationHttpHeaders(page: Page<*>, baseUrl: String): HttpHeaders {

        val headers = HttpHeaders()
        headers.add("sia-total-items", page.totalElements.toString())
        var link = ""
        if (page.number + 1 < page.totalPages) {
            link = "<" + generateUri(baseUrl, page.number + 1, page.size) + ">; rel=\"next\","
        }
        // prev link
        if (page.number > 0) {
            link += "<" + generateUri(baseUrl, page.number - 1, page.size) + ">; rel=\"prev\","
        }
        // last and first link
        var lastPage = 0
        if (page.totalPages > 0) {
            lastPage = page.totalPages - 1
        }
        link += "<" + generateUri(baseUrl, lastPage, page.size) + ">; rel=\"last\","
        link += "<" + generateUri(baseUrl, 0, page.size) + ">; rel=\"first\""
        headers.add(HttpHeaders.LINK, link)
        return headers
    }

    private fun generateUri(baseUrl: String, page: Int, size: Int): String {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString()
    }
}