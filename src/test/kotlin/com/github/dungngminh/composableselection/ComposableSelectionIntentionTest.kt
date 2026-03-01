package com.github.dungngminh.composableselection

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ComposableSelectionIntentionTest : BasePlatformTestCase() {

    fun `test intention is available on normal function`() {
        val code = """
            fun main() {
                s<caret>um(1, 2)
            }

            fun sum(a: Int, b: Int): Int {
                return a + b
            }
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)
        val action = myFixture.findSingleIntention("Select Composable")
        assertNotNull(action)
    }

    fun `test intention selects function`() {
        val code = """
            fun main() {
                s<caret>um(1, 2)
            }

            fun sum(a: Int, b: Int): Int {
                return a + b
            }
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)
        val action = myFixture.findSingleIntention("Select Composable")
        myFixture.launchAction(action)

        val selection = myFixture.editor.selectionModel.selectedText
        assertEquals("sum(1, 2)", selection)
    }

    fun `test intention is available on composable`() {
        val code = """
            @Composable
            fun MyScreen() {
                My<caret>Text("Hello")
            }
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)

        val action = myFixture.findSingleIntention("Select Composable")
        assertNotNull(action)
    }

    fun `test intention selects composable`() {
        val code = """
            @Composable
            fun MyScreen() {
                My<caret>Text("Hello")
            }
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)
        val action = myFixture.findSingleIntention("Select Composable")
        myFixture.launchAction(action)

        val selection = myFixture.editor.selectionModel.selectedText
        assertEquals("""MyText("Hello")""", selection)
    }
}