package com.mwronski.vaadinmvp.validation.model;

import com.mwronski.vaadinmvp.model.Product;
import com.mwronski.vaadinmvp.validation.ValidationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test cases for validator dedicated for products
 *
 * @author Michal Wronski
 * @date 04-03-2014
 * @see ProductValidator
 */
public class ProductValidatorTest {

    private final ProductValidator validator = new ProductValidator();

    @Test
    public void testProduct() {
        Product product = new Product();
        product.setName("Product A");
        product.setCount(0);
        validator.validate(product);
    }

    @Test
    public void testWrongName() {
        Product product = new Product();
        product.setName("Pr");
        product.setCount(1);
        try {
            validator.validate(product);
            fail("Should fail as name is too short");
        } catch (ValidationException e) {
            assertEquals("Wrong field 'Product's name':String Pr is shorten than 3", e.getMessage());
        }
    }

    @Test
    public void testWrongCount() {
        Product product = new Product();
        product.setName("Product A");
        product.setCount(-1);
        try {
            validator.validate(product);
            fail("Should fail as count cannot be negative number");
        } catch (ValidationException e) {
            assertEquals("Wrong field 'Product's count':Value -1 is lesser than 0", e.getMessage());
        }
    }
}
