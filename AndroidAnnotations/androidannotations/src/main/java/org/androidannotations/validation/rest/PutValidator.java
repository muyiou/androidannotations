/**
 * Copyright (C) 2010-2012 eBusiness Information, Excilys Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.androidannotations.validation.rest;

import java.lang.annotation.Annotation;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import org.androidannotations.annotations.rest.Put;
import org.androidannotations.helper.RestAnnotationHelper;
import org.androidannotations.helper.TargetAnnotationHelper;
import org.androidannotations.helper.ValidatorHelper;
import org.androidannotations.model.AnnotationElements;
import org.androidannotations.validation.ElementValidator;
import org.androidannotations.validation.IsValid;

public class PutValidator implements ElementValidator {

	private ValidatorHelper validatorHelper;
	private RestAnnotationHelper restAnnotationHelper;

	public PutValidator(ProcessingEnvironment processingEnv) {
		TargetAnnotationHelper annotationHelper = new TargetAnnotationHelper(processingEnv, getTarget());
		validatorHelper = new ValidatorHelper(annotationHelper);
		restAnnotationHelper = new RestAnnotationHelper(processingEnv, getTarget());
	}

	@Override
	public Class<? extends Annotation> getTarget() {
		return Put.class;
	}

	@Override
	public boolean validate(Element element, AnnotationElements validatedElements) {

		IsValid valid = new IsValid();

		validatorHelper.notAlreadyValidated(element, validatedElements, valid);

		validatorHelper.enclosingElementHasRestAnnotation(element, validatedElements, valid);

		ExecutableElement executableElement = (ExecutableElement) element;

		validatorHelper.throwsOnlyRestClientException(executableElement, valid);

		validatorHelper.returnTypeIsVoid(executableElement, valid);

		restAnnotationHelper.urlVariableNamesExistInParametersAndHasOnlyOneMoreParameter(executableElement, valid);

		return valid.isValid();
	}

}
