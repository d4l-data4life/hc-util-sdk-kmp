/*
 * Copyright (c) 2021 D4L data4life gGmbH / All rights reserved.
 *
 * D4L owns all legal rights, title and interest in and to the Software Development Kit ("SDK"),
 * including any intellectual property rights that subsist in the SDK.
 *
 * The SDK and its documentation may be accessed and used for viewing/review purposes only.
 * Any usage of the SDK for other purposes, including usage for the development of
 * applications/third-party applications shall require the conclusion of a license agreement
 * between you and D4L.
 *
 * If you are interested in licensing the SDK for your own applications/third-party
 * applications and/or if you’d like to contribute to the development of the SDK, please
 * contact D4L by email to help@data4life.care.
 */

import XCTest
import Data4LifeSDKUtilSwift

class Test: XCTestCase {
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func test_createNSErrorWithCode_is_called_with_a_code_domain_and_localizedDescription_it_creats_an_NSError() throws {
        // Given
        let code = 23;
        let domain = "potato";
        let local = "soup";
        let kotlinError = "tomato"
        
        // When
        let error = Data4LifeSDKUtil.createNSError(
            code: code,
            domain: domain,
            localizedDescription: local,
            kotlinError : kotlinError
        );
        
        // Then
        assert(error.code == code)
        assert(error.domain == domain)
        assert(error.localizedDescription == local)
        assert(error.userInfo["kotlinError"] as! String? == kotlinError)
    }
}
