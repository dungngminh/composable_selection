package com.github.dungngminh.composableselection

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PriorityAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtFile

/**
 * Intention Action to select the nearest Composable function.
 * Reuses logic from [ComposableSelectionUtils].
 */
class ComposableSelectionIntention : IntentionAction, PriorityAction {
    override fun startInWriteAction(): Boolean = false

    override fun getText(): String = "Select Composable"

    override fun getFamilyName(): String = "Composable Selection"

    override fun isAvailable(project: Project, editor: Editor, file: PsiFile): Boolean {
        if (file !is KtFile) return false
        val elementAtCaret = file.findElementAt(editor.caretModel.offset) ?: return false
        return ComposableSelectionUtils.canSelect(editor, elementAtCaret)
    }

    override fun invoke(project: Project, editor: Editor, file: PsiFile) {
        val elementAtCaret = file.findElementAt(editor.caretModel.offset) ?: return
        ComposableSelectionUtils.selectComposable(editor, elementAtCaret)
    }

    override fun getPriority(): PriorityAction.Priority = PriorityAction.Priority.NORMAL
}
