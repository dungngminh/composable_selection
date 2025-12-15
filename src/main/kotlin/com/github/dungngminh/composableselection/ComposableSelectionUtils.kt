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
        val nearestCall = getParentComposableCall(elementAtCaret) ?: return false
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
            val parentCall = getParentComposableCall(nearestCall)
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
        return getParentComposableCall(elementAtCaret) != null
    }

    private fun getParentComposableCall(element: PsiElement): KtCallExpression? {
        var current: PsiElement? = element
        while (true) {
            val call =
                PsiTreeUtil.getParentOfType(current, KtCallExpression::class.java) ?: return null
            if (isComposableCall(call)) {
                return call
            }
            current = call
        }
    }

    private fun isComposableCall(call: KtCallExpression): Boolean {
        val resolvedCall =
            call.calleeExpression?.references?.firstOrNull()?.resolve() ?: return false
        return resolvedCall.isComposableAnnotationPresent()
    }

    private fun PsiElement.isComposableAnnotationPresent(): Boolean {
        if (this is org.jetbrains.kotlin.psi.KtNamedDeclaration) {
            return annotationEntries.any {
                val loadedShortName = it.shortName?.asString()
                // Check if it has an annotation named Composable
                loadedShortName == "Composable" || it.typeReference?.text == "Composable"
            }
        }
        return false
    }

    private fun selectRange(editor: Editor, range: TextRange) {
        editor.selectionModel.setSelection(range.startOffset, range.endOffset)
    }
}
