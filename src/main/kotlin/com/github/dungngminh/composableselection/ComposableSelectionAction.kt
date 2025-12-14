package com.github.dungngminh.composableselection

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import org.jetbrains.kotlin.psi.KtFile

/**
 * Action triggered by shortcut (Option+Shift+W / Alt+Shift+W by default) to select Composable functions.
 */
class ComposableSelectionAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val psiFile = e.getData(CommonDataKeys.PSI_FILE) as? KtFile ?: return

        val currentCaretOffset = editor.caretModel.offset
        
        val elementAtCaret = psiFile.findElementAt(currentCaretOffset) ?: return

        ComposableSelectionUtils.selectComposable(editor, elementAtCaret)
    }

    override fun update(e: AnActionEvent) {
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        e.presentation.isEnabledAndVisible = psiFile is KtFile
    }
}
