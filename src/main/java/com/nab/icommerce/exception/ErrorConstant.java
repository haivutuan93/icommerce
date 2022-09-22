package com.nab.icommerce.exception;

public final class ErrorConstant {
    //Common
    public final static String ERR_PARAMETER_MISSING = "10000";
    public final static String ERR_PARAMETER_MISSING_MSG = "Missing data";
    public final static String ERR_PARAMETER_NOT_CORRECT = "10001";
    public final static String ERR_PARAMETER_NOT_CORRECT_MSG = "Data is not correct";

    //Product
    public final static String ERR_PRODUCT_QUANTITY_IS_NOT_ENOUGH = "20000";
    public final static String ERR_PRODUCT_QUANTITY_IS_NOT_ENOUGH_MSG = "Product quantity is not enough";

    public final static String ERR_CART_USER_NOT_MATCH = "30000";
    public final static String ERR_CART_USER_NOT_MATCH_MSG = "Cart and User is not match";
}
