package com.github.dungngminh.composableselection

import com.intellij.testFramework.fixtures.BasePlatformTestCase

class ComposableSelectionIntentionTest : BasePlatformTestCase() {

    fun `test intention is available on composable`() {
        val code = """
            import androidx.compose.runtime.Composable
            
            @Composable
            fun MyScreen() {
                TE<caret>XT("Hello")
            }
            
            fun TEXT(text: String) {}
        """.trimIndent()
        
        myFixture.configureByText("Test.kt", code)
        val action = myFixture.findSingleIntention("Select Composable")
        assertNotNull(action)
    }

    fun `test intention selects composable`() {
        val code = """
            import androidx.compose.runtime.Composable
            
            @Composable
            fun MyScreen() {
                <caret>TEXT("Hello")
            }
            
            fun TEXT(text: String) {}
        """.trimIndent()
        
        myFixture.configureByText("Test.kt", code)
        val action = myFixture.findSingleIntention("Select Composable")
        myFixture.launchAction(action)
        
        val selection = myFixture.editor.selectionModel.selectedText
        assertEquals("""TEXT("Hello")""", selection)
    }
}
