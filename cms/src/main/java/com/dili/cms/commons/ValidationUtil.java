/**
 * Copyright (C) DiliGroup. All Rights Reserved.
 * <p>
 * ValidationUtil created on 2021/1/11 16:32 by Tab.Xie
 */
package com.dili.cms.commons;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * Description
 * TODO file description here 字段校验工具类
 * @author Tab.Xie
 * @since 1.0
 *
 * Change History
 * Date  Modifier  DESC.
 * 2021/1/11  Tab.Xie  Initial version.
 * </pre>
 */
public class ValidationUtil {
    /**
     * 开启快速结束模式 failFast (true)
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class)
            .configure().failFast(true)
            .buildValidatorFactory()
            .getValidator();


    /**
     * TODO 校验对象
     *
     * @param t:bean
     * @param groups:校验组
     * @return：com.dili.scm.common.utils.ValidationUtil.ValidResult
     * @author：Tab.Xie
     * @date：2021/1/12 9:27
     */
    public static <T> ValidResult validateBean(T t, Class<?>... groups) {
        ValidResult result = new ValidationUtil().new ValidResult();
        Set<ConstraintViolation<T>> violationSet = validator.validate(t, groups);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        result.setHasErrors(hasError);
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                result.addError(violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        return result;
    }


    /**
     * TODO 校验bean的某一个属性
     *
     * @param obj:bean
     * @param propertyName:属性名称
     * @return：com.dili.scm.common.utils.ValidationUtil.ValidResult
     * @author：Tab.Xie
     * @date：2021/1/12 9:27
     */
    public static <T> ValidResult validateProperty(T obj, String propertyName) {
        ValidResult result = new ValidationUtil().new ValidResult();
        Set<ConstraintViolation<T>> violationSet = validator.validateProperty(obj, propertyName);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        result.setHasErrors(hasError);
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                result.addError(propertyName, violation.getMessage());
            }
        }
        return result;
    }

    /**
     * 校验结果类
     */
    public class ValidResult {
        /**
         * 是否有错误
         */
        private boolean hasErrors;
        /**
         * 错误信息
         */
        private List<ErrorMessage> errors;

        public ValidResult() {
            this.errors = new ArrayList<>();
        }

        public boolean hasErrors() {
            return hasErrors;
        }

        public void setHasErrors(boolean hasErrors) {
            this.hasErrors = hasErrors;
        }

        /**
         * 获取所有验证信息
         *
         * @return 集合形式
         */
        public List<ErrorMessage> getAllErrors() {
            return errors;
        }

        /**
         * 获取所有验证信息
         *
         * @return 字符串形式
         */
        public String getErrors() {
            StringBuilder sb = new StringBuilder();
            for (ErrorMessage error : errors) {
                sb.append(error.getPropertyPath()).append(":").append(error.getMessage()).append(" ");
            }
            return sb.toString();
        }

        public void addError(String propertyName, String message) {
            this.errors.add(new ErrorMessage(propertyName, message));
        }

        public boolean isHasErrors() {
            return hasErrors;
        }

        public void setErrors(List<ErrorMessage> errors) {
            this.errors = errors;
        }
    }

    public class ErrorMessage {
        private String propertyPath;
        private String message;

        public ErrorMessage() {
        }

        public ErrorMessage(String propertyPath, String message) {
            this.propertyPath = propertyPath;
            this.message = message;
        }

        public String getPropertyPath() {
            return propertyPath;
        }

        public void setPropertyPath(String propertyPath) {
            this.propertyPath = propertyPath;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}