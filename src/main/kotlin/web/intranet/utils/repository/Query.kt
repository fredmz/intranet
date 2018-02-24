package web.intranet.utils.repository

import org.springframework.data.domain.Pageable

open class Query {
    protected fun pagedQuery(query: String, pageable: Pageable): String {
        val offset = pageable.pageSize * (pageable.pageNumber - 1)
        return query + " LIMIT " + offset + ", " + pageable.pageSize
    }

    protected fun totalQuery(query: String): String {
        return "SELECT COUNT(*) total FROM ($query) AS tbl"
    }

    protected fun like(value: String, params: Array<String>): String {
        var query = ""
        if (params.isNotEmpty()) {
            query += "(" + params[0] + " like '%" + value + "%'"
            for (i in 1..(params.size - 1)) {
                query += " OR " + params[i] + " like '%" + value + "%'"
            }
            query += ")"
        }
        return query
    }
}