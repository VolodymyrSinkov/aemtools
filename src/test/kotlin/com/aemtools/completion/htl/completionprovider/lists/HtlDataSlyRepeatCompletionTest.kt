package com.aemtools.completion.htl.completionprovider.lists

import com.aemtools.completion.blocks.BaseVariantsCheckContributorTest

/**
 * @author Dmytro_Troynikov
 */
class HtlDataSlyRepeatCompletionTest
    : BaseVariantsCheckContributorTest("com/aemtools/completion/htl/fixtures/noafter/data-sly-repeat") {

    fun testDataSlyRepeatDefaultVariables() =
            assertVariantsPresent(DEFAULT_LIST_VARIABLES)

    fun testDataSlyRepeatDefaultVariablesWithinCurrentTag() =
            assertVariantsPresent(DEFAULT_LIST_VARIABLES)

    fun testDataSlyRepeatOverridenVariables() =
            assertVariantsPresent(OVERRIDEN_LIST_VARIABLES)

    fun testDataSlyRepeatOverridenVariablesWithinCurrentTag() =
            assertVariantsPresent(OVERRIDEN_LIST_VARIABLES)

    fun testDataSlyRepeatVariablesNotAvailableInOuterScope() =
            assertVariantsAbsent(DEFAULT_LIST_VARIABLES)

    fun testDataSlyRepeatOverridenVariablesNotAvailableInOuterScope() =
            assertVariantsAbsent(OVERRIDEN_LIST_VARIABLES)

    fun testDataSlyRepeatListVariableFields() =
            assertVariants(LIST_VARIABLE_FIELDS)

    fun testDataSlyRepeatListVariableFieldsWithinCurrentTag() =
            assertVariants(LIST_VARIABLE_FIELDS)

    fun testDataSlyRepeatOverridenListVariableFields() =
            assertVariants(LIST_VARIABLE_FIELDS)

    fun testDataSlyRepeatOverridenListVariableFieldsWithinCurrentTag() =
            assertVariants(LIST_VARIABLE_FIELDS)

    fun testDataSlyRepeatResolveItem() =
            assertVariants(CUSTOM_MODEL_FIELDS)

    fun testDataSlyRepeatResolveOverridenItem() =
            assertVariants(PAGE_FIELDS)

    fun testDataSlyRepeatResolveItemRecursive() =
            assertVariants(PAGE_FIELDS)

    fun testDataSlyRepeatListVariablesShouldNotBeAvailableInDeclaration() =
            assertVariantsAbsent(DEFAULT_LIST_VARIABLES)

}