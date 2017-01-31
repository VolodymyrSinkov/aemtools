package com.aemtools.lang.htl.manipulator

import com.aemtools.lang.htl.psi.HtlPropertyAccess
import com.aemtools.lang.htl.psi.mixin.PropertyAccessMixin
import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiManager
import com.intellij.util.IncorrectOperationException

/**
 * @author Dmytro_Troynikov
 */
class HtlPropertyAccessManipulator : AbstractElementManipulator<HtlPropertyAccess>() {
    override fun handleContentChange(element: HtlPropertyAccess, range: TextRange, newContent: String): HtlPropertyAccess {
        val propertyAccessMixin = element as? PropertyAccessMixin
                ?: throw IncorrectOperationException("Cannot rename: $element")

        val originalName = propertyAccessMixin.text.substring(range.startOffset, range.endOffset)

        val newName = constructNewName(originalName, newContent)

        val oldText = propertyAccessMixin.text
        val newText = "${oldText.substring(0, range.startOffset)}$newName${oldText.substring(range.endOffset)}"
        val newElement = JavaPsiFacade.getInstance(propertyAccessMixin.project)
                .elementFactory.createExpressionFromText(newText, null)

        val psiManager = PsiManager.getInstance(propertyAccessMixin.project)

        return element
    }

    private fun constructNewName(oldName: String, newName: String): String {
        return when {
            (oldName.startsWith("get") && newName.startsWith("get"))
                    || (oldName.startsWith("is") && newName.startsWith("is")) ->
                newName
            !oldName.startsWith("get") && newName.startsWith("get") ->
                newName.substring(3).decapitalize()
            !oldName.startsWith("is") && newName.startsWith("is") ->
                newName.substring(2).decapitalize()
            else -> newName
        }
    }

}