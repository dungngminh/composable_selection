package com.github.dungngminh.composableselection

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.kotlin.psi.KtCallExpression
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testFramework.TestActionEvent

class ComposableSelectionActionTest : BasePlatformTestCase() {

    fun `test select nearest composable when caret is inside`() {
        val code = """
            @Composable
            fun MyScreen() {
                Column {
                    <caret>Text("Hello")
                }
            }
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)

        myFixture.performEditorAction("ComposableSelectionAction")

        val selection = myFixture.editor.selectionModel.selectedText
        assertEquals("""Text("Hello")""", selection)
    }

    fun `test select nearest composable when caret is in properties`() {
         val code = """
            @Composable
            fun MyScreen() {
                TextField(
                    value = <caret>"text",
                    onValueChange = {}
                )
            }
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)
        myFixture.performEditorAction("ComposableSelectionAction")

        val selection = myFixture.editor.selectionModel.selectedText
        assertTrue(selection?.startsWith("TextField") == true)
    }

    fun `test select parent composable when current is selected`() {
        val code = """
            @Composable
            fun MyScreen() {
                Column {
                    Text("Hello")
                }
            }
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)

        val textStart = code.indexOf("""Text("Hello")""")
        val textEnd = textStart + """Text("Hello")""".length
        myFixture.editor.selectionModel.setSelection(textStart, textEnd)
        myFixture.editor.caretModel.moveToOffset(textStart)

        myFixture.performEditorAction("ComposableSelectionAction")

        val selection = myFixture.editor.selectionModel.selectedText

        assertTrue(selection?.startsWith("Column") == true)
        assertTrue(selection?.contains("""Text("Hello")""") == true)
    }

    fun `test select non-composable function`() {
        val code = """
            fun main() {
                f<caret>oo()
            }

            fun foo() {}
        """.trimIndent()

        myFixture.configureByText("Test.kt", code)
        myFixture.performEditorAction("ComposableSelectionAction")

        val selection = myFixture.editor.selectionModel.selectedText
        assertEquals("foo()", selection)
    }
}