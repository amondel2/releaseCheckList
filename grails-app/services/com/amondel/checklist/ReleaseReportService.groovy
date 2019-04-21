package com.amondel.checklist

import grails.gorm.transactions.Transactional
import grails.gsp.PageRenderer
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl
import org.docx4j.model.styles.StyleTree
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart
import org.docx4j.wml.BooleanDefaultTrue

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
                XHTMLImporter.convert(contents, null))

        wordMLPackage
    }
}

