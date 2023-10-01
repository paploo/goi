package net.paploo.goi.common.util

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

class UuidToolsTest : DescribeSpec({

    describe("v5") {

        //This expects it to match the ruby goi code, which also matches online tools.
        //e.g.:
        // UUIDTools::UUID.create_from_hash(Digest::SHA1, UUIDTools::UUID.parse('f552ea3a-8b7f-5433-aa20-4c93a6868fb3'), "foobar")
        // --> 627e02b5-9bcd-54b1-9345-db04d6362836
        it("should match known good output") {
            val namespace = UUID.fromString("f552ea3a-8b7f-5433-aa20-4c93a6868fb3")
            val name = "foobar"

            val uuidv5 = UuidTools.createV5(namespace, name)

            uuidv5 shouldBe UUID.fromString("627e02b5-9bcd-54b1-9345-db04d6362836")
        }

    }

    describe("v3") {

        //Output made with https://www.uuidtools.com/v3, which worked for v5
        it("should match known good output") {
            val namespace = UUID.fromString("f552ea3a-8b7f-5433-aa20-4c93a6868fb3")
            val name = "foobar"

            val uuidv3 = UuidTools.createV3(namespace, name)

            uuidv3 shouldBe UUID.fromString("981e741b-0a6e-3303-a62a-d10acc8d3ea3")
        }
    }

})