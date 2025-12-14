package com.github.dungngminh.composableselection

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.psi.KtCallExpression

/**
 * Utility class for handling intelligent selection of Composable functions.
 */
object ComposableSelectionUtils {
    /**
     * Selects the nearest Composable function call at the caret position.
     *
     * Logic:
     * 1. If currently inside a Composable call but it's not selected, selects that call.
     * 2. If the nearest Composable call is already exactly selected, expands selection to its parent Composable call.
     *
     * @param editor The active editor.
     * @param elementAtCaret The PSI element at the current caret position.
     * @return true if a selection was made, false otherwise.
     */
    fun selectComposable(editor: Editor, elementAtCaret: PsiElement): Boolean {
        val nearestCall = PsiTreeUtil.getParentOfType(elementAtCaret, KtCallExpression::class.java) ?: return false
        val callRange = nearestCall.textRange

        val selectionModel = editor.selectionModel
        val currentSelectionStart = selectionModel.selectionStart
        val currentSelectionEnd = selectionModel.selectionEnd

        val isCurrentCallSelected = currentSelectionStart == callRange.startOffset &&
                                    currentSelectionEnd == callRange.endOffset

        if (!isCurrentCallSelected) {
            selectRange(editor, callRange)
            return true
        } else {
            val parentCall = PsiTreeUtil.getParentOfType(nearestCall, KtCallExpression::class.java)
            if (parentCall != null) {
                selectRange(editor, parentCall.textRange)
                return true
            }
        }
        return false
    }

    /**
     * Checks if a Composable selection is possible at the given position.
     * Used for determining action availability.
     *
     * @param editor The active editor.
     * @param elementAtCaret The PSI element at the current caret position.
     * @return true if a Composable call is found up the tree, false otherwise.
     */
    fun canSelect(editor: Editor, elementAtCaret: PsiElement): Boolean {
         val nearestCall = PsiTreeUtil.getParentOfType(elementAtCaret, KtCallExpression::class.java) ?: return false
         return true
    }

    private fun selectRange(editor: Editor, range: TextRange) {
        editor.selectionModel.setSelection(range.startOffset, range.endOffset)
    }
}
