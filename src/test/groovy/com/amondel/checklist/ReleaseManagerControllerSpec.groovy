package com.amondel.checklist

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class ReleaseManagerControllerSpec extends Specification implements ControllerUnitTest<ReleaseManagerController> {

    def setup() {
    }

    def cleanup() {
    }

    void "Test the index action returns the correct model"() {
        given:
        controller.releaseManagerService = Mock(ReleaseManagerService) {
            1 * getReleasePackages(false) >> []
        }

        when:"The index action is executed"
        controller.index()

        then:"The model is correct"
        !model.releasePackageList
    }
}
