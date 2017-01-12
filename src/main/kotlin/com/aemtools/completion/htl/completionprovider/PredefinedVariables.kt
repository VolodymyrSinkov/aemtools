package com.aemtools.completion.htl.completionprovider

import com.aemtools.lang.htl.psi.mixin.VariableNameMixin
import com.aemtools.lang.htl.psi.util.elFields
import com.aemtools.lang.htl.psi.util.elMethods
import com.aemtools.lang.htl.psi.util.elName
import com.aemtools.service.ServiceFacade
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiClass
import com.intellij.psi.search.GlobalSearchScope
import java.util.*

/**
 * Provides completion on Htl context object (e.g. 'properties')
 * @author Dmytro_Troynikov
 */
object PredefinedVariables {

    private val repository = ServiceFacade.getHtlAttributesRepository()

    fun contextObjectsCompletion(): List<LookupElement> {
        return repository.getContextObjects().map {
            LookupElementBuilder.create(it.name)
                    .withTypeText(it.className)
        }
    }

    /**
     * Lookups for PsiClass by variable name e.g.
     * ```
     *  resolveByIdentifier("request")
     *  ->
     *  "SlingHttpServletRequest"
     * ```
     * @param variableName the name of variable
     * @return [PsiClass] instance or _null_ if no class was found
     */
    fun resolveByIdentifier(variableName: String, variableNameMixin: VariableNameMixin): PsiClass? {
        val project = variableNameMixin.project
        val classInfo = repository.findContextObject(variableName) ?: return null
        val fullClassName = classInfo.className
        return JavaPsiFacade.getInstance(project)
                .findClass(fullClassName, GlobalSearchScope.allScope(project))
    }

    /**
     * Extract Htl applicable completion variants from class element.
     * @return list of LookupElements extracted from given class.
     */
    fun extractSuggestions(psiClass: PsiClass): List<LookupElement> {
        val methods = psiClass.elMethods()
        val fields = psiClass.elFields()

        val methodNames = ArrayList<String>()
        val result = ArrayList<LookupElement>()

        methods.forEach {
            var name = it.elName()
            if (methodNames.contains(name)) {
                name = it.name
            } else {
                methodNames.add(name)
            }
            var lookupElement = LookupElementBuilder.create(name)
                    .withIcon(it.getIcon(0))
                    .withTailText(" ${it.name}()", true)

            val returnType = it.returnType
            if (returnType != null) {
                lookupElement = lookupElement.withTypeText(returnType.presentableText, true)
            }

            result.add(lookupElement)
        }

        fields.forEach {
            val lookupElement = LookupElementBuilder.create(it.name.toString())
                    .withIcon(it.getIcon(0))
                    .withTypeText(it.type.presentableText, true)

            result.add(lookupElement)
        }

        return result
    }

}