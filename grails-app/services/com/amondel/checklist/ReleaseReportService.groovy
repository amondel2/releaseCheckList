package com.amondel.checklist

import grails.gorm.transactions.Transactional
import grails.gsp.PageRenderer
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl
import org.docx4j.model.styles.StyleTree
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.BooleanDefaultTrue
import org.springframework.web.util.HtmlUtils

@Transactional
class ReleaseReportService {

    PageRenderer groovyPageRenderer

    def getRelease(String id) {
        ReleaseName.load(id)
    }

    def getPackages (ReleaseName rn) {
        ReleasePackage.withCriteria {
            eq('releaseName',rn)
            order("orderNumber",'asc')
        }
    }

    def getItems (ReleasePackage rp ) {
        ReleasePackageItems.withCriteria {
            eq ('releasePackage',rp )
        }
    }

    WordprocessingMLPackage generateReleaseReport(rn,releasePackages,contents) {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage()
        MainDocumentPart mdp = wordMLPackage.getMainDocumentPart()
        mdp.addStyledParagraphOfText("Title", "$rn Report");

        XHTMLImporterImpl XHTMLImporter = new XHTMLImporterImpl(wordMLPackage)
        XHTMLImporter.setHyperlinkStyle("Hyperlink");
        wordMLPackage.getMainDocumentPart().getContent().addAll(
                XHTMLImporter.convert(charEncode(contents), null))

        wordMLPackage
    }

    String charEncode(String str) {
        //The only way I could think how to do this and not mess with the HTML Tags but also recode > and < to actual entities.
        HtmlUtils.htmlUnescape(str.replaceAll('&gt;',"thegreatesttageintheworldgt12340099876543").replaceAll('&lt;',"thegreatesttageintheworldlt12340099876543")).replaceAll('&','&amp;').replaceAll("thegreatesttageintheworldgt12340099876543",'&gt;').replaceAll("thegreatesttageintheworldlt12340099876543","&lt;")
    }
}

