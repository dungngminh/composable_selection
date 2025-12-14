package com.github.dungngminh.composableselection

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.jetbrains.kotlin.psi.KtCallExpression
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.testFramework.TestActionEvent

class ComposableSelectionActionTest : BasePlatformTestCase() {

    fun `test select nearest composable when caret is inside`() {
        val code = """
            import androidx.compose.runtime.Composable
            
            @Composable
            fun MyScreen() {
                Column {
                    <caret>Text("Hello")
                }
            }
        """.trimIndent()
        
        myFixture.configureByText("Test.kt", code)
        
        // Trigger action
        myFixture.performEditorAction("ComposableSelectionAction")
        
        val selection = myFixture.editor.selectionModel.selectedText
        assertEquals("""Text("Hello")""", selection)
    }

    fun `test select nearest composable when caret is in properties`() {
         val code = """
            import androidx.compose.runtime.Composable
            
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
        // It maintains formatting, so let's check if it starts with TextField
        assertTrue(selection?.startsWith("TextField") == true)
    }

    fun `test select parent composable when current is selected`() {
        val code = """
            import androidx.compose.runtime.Composable
            
            @Composable
            fun MyScreen() {
                Column {
                    Text("Hello")
                }
            }
        """.trimIndent()
        
        myFixture.configureByText("Test.kt", code)
        
        // 1. Select the inner Text("Hello") manually to simulate state
        val textStart = code.indexOf("""Text("Hello")""")
        val textEnd = textStart + """Text("Hello")""".length
        myFixture.editor.selectionModel.setSelection(textStart, textEnd)
        myFixture.editor.caretModel.moveToOffset(textStart) // Ensure caret is there

        // 2. Trigger action to expand
        myFixture.performEditorAction("ComposableSelectionAction")
        
        val selection = myFixture.editor.selectionModel.selectedText
        
        // Should select Column { ... }
        assertTrue(selection?.startsWith("Column") == true)
        assertTrue(selection?.contains("""Text("Hello")""") == true)
    }
}
