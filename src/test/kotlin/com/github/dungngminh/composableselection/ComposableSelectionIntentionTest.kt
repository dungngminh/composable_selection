package com.github.dungngminh.composableselection

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ComposableSelectionIntentionTest : BasePlatformTestCase() {

    private val sampleStubs = """
        @Composable
        fun MyText(text: String) {}
        
        fun sum(a: Int, b: Int): Int {
            return a + b
        }
    """.trimIndent()

    fun `test intention is NOT available on normal function`() {
        val code = """
            fun main() {
                s<caret>um(1, 2)
            }
        """.trimMargin()

        myFixture.configureByText("Stubs.kt", sampleStubs)
        myFixture.configureByText("Test.kt", code)
        val action = myFixture.getAvailableIntention("Select Composable")

        assertNull("Intention should not be available for normal functions", action)
    }

    fun `test intention is available on composable`() {
        val code = """
            @Composable
            fun MyScreen() {
                My<caret>Text("Hello")
            }
        """.trimIndent()
        
        myFixture.configureByText("Stubs.kt", sampleStubs)
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
        
        myFixture.configureByText("Stubs.kt", sampleStubs)
        myFixture.configureByText("Test.kt", code)
        val action = myFixture.findSingleIntention("Select Composable")
        myFixture.launchAction(action)
        
        val selection = myFixture.editor.selectionModel.selectedText
        assertEquals("""MyText("Hello")""", selection)
    }
}
