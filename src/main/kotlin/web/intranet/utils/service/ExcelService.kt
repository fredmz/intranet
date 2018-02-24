package web.intranet.utils.service

import org.jxls.template.SimpleExporter
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class ExcelService {
    fun generar(response: HttpServletResponse,
                headers: MutableList<String>,
                properties: String,
                list: List<Any?>, fileName: String) {
        response.addHeader("Content-disposition", "attachment; filename=$fileName.xls")
        response.contentType = "application/vnd.ms-excel"
        SimpleExporter().gridExport(headers, list, properties, response.outputStream)
        response.flushBuffer()
    }
}