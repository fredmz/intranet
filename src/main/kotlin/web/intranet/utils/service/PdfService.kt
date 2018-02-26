package web.intranet.utils.service

import com.itextpdf.text.pdf.BaseFont.EMBEDDED
import com.itextpdf.text.pdf.BaseFont.IDENTITY_H
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import org.w3c.tidy.Tidy
import org.xhtmlrenderer.pdf.ITextRenderer
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream

@Service
class PdfService {
    fun generate(namePdf: String, template: String, model: Model) {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "/"
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML
        templateResolver.characterEncoding = "UTF-8"

        val templateEngine = TemplateEngine()
        templateEngine.setTemplateResolver(templateResolver)

        var ctx = Context()
        ctx.setVariables(model.asMap())

        val renderedHtmlContent = templateEngine.process(template, ctx)
        val xHtml = convertToXhtml(renderedHtmlContent)

        val renderer = ITextRenderer()
        renderer.fontResolver.addFont("Code39.ttf", IDENTITY_H, EMBEDDED)
        renderer.setDocumentFromString(xHtml)
        renderer.layout()

        val outputStream = FileOutputStream(namePdf + ".pdf")
        renderer.createPDF(outputStream)
        outputStream.close()
    }

    private fun convertToXhtml(html: String): String {
        val tidy = Tidy()
        tidy.inputEncoding = "UTF-8"
        tidy.outputEncoding = "UTF-8"
        tidy.xhtml = true
        val inputStream = ByteArrayInputStream(html.toByteArray(charset("UTF-8")))
        val outputStream = ByteArrayOutputStream()
        tidy.parseDOM(inputStream, outputStream)
        return outputStream.toString("UTF-8")
    }
}