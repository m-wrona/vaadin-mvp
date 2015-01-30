package com.mwronski.vaadinmvp.validation.model;

import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.validation.FieldValidator;
import com.mwronski.vaadinmvp.validation.NumberValidator;
import com.mwronski.vaadinmvp.validation.StringValidator;
import com.mwronski.vaadinmvp.validation.Validator;

import static com.mwronski.vaadinmvp.validation.Validations.validateNotNull;

/**
 * Validator for products
 *
 * @author Michal Wronski
 * @date 04-03-2014
 */
public final class ProductValidator implements Validator<Product> {

    @Override
    public void validate(Product entity) {
        validateNotNull(entity, "Product");
        createNameValidator().validate(entity.getName());
        createCountValidator(null).validate(entity.getCount());
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    /**
     * Create validator for 'name' field
     *
     * @return non-null instance of field validator
     */
    public FieldValidator<String> createNameValidator() {
        return new FieldValidator<String>("Product's name", new StringValidator(false, 3, 255));
    }

    /**
     * Create validator for 'count' field
     *
     * @param maxValue max count value (optional)
     * @return non-null instance of field validator
     */
    public FieldValidator<Integer> createCountValidator(Integer maxValue) {
        return new FieldValidator<Integer>("Product's count", new NumberValidator<Integer>(false, 0, maxValue));
    }

}
