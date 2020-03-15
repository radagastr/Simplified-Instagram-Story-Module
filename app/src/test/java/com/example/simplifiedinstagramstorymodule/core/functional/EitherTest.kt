package com.example.simplifiedinstagramstorymodule.core.functional

import com.example.simplifiedinstagramstorymodule.UnitTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert.*
import org.junit.Test

class EitherTest : UnitTest() {

    @Test
    fun `Either Right should return correct type`() {
        val result = Either.Right("Right")

        result shouldBeInstanceOf Either::class.java
        result.isRight shouldBe true
        result.isLeft shouldBe false
        result.either({},
            { right ->
                right shouldBeInstanceOf String::class.java
                right shouldBeEqualTo "Right"
            })
    }

    @Test
    fun `Either Left should return correct type`() {
        val result = Either.Left("Left")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldBe true
        result.isRight shouldBe false
        result.either(
            { left ->
                left shouldBeInstanceOf String::class.java
                left shouldBeEqualTo "Left"
            }, {})
    }
}