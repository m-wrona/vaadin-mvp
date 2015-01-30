package com.mwronski.vaadinmvp.model;

import com.mwronski.vaadinmvp.exceptions.CoreRuntimeException;

import java.util.Objects;

import static com.google.common.base.Objects.toStringHelper;

/**
 * Product available in the service.
 * Entity may also serve as new order made by the user.
 *
 * @author Michal Wronski
 * @date 03-03-2014
 */
public final class Product implements Cloneable {

    private String name;
    private int count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("name", name).add("count", count).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        else if (this == obj) {
            return true;
        }
        else if (this.getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new CoreRuntimeException("Cannot clone object: " + toString(), e);
        }
    }
}
