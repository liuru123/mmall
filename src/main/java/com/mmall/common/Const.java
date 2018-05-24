package com.mmall.common;

import com.google.common.collect.Sets;
import com.mmall.service.impl.OrderServiceImpl;

import java.util.Set;

public class Const {

    public static final String CURRENT_USER = "current_user";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";

    public interface ProductListOrderByList{
        Set<String> PRICE_DESC_ASC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED = 1;//选中状态
        int UN_CHECKED = 0;//未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";

    }

    public interface Role{
        int ROLE_ADMIN = 0;   //普通用户
        int ROLE_CUSTOMER = 1;       //管理员
    }

    public enum ProductStatus{
        ON_SALE(1,"在线");
        private String value;
        private int code;

        ProductStatus(int code, String value){
            this.code = code;
            this.value = value;
        }
        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

    }

    public enum OrderStatus{
        CANCELLED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已支付"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"已完成"),
        ORDER_CLOSE(60,"已关闭");

        OrderStatus(int code,String value){
            this.code = code;
            this.value = value;
        }

        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static OrderStatus codeOf(int code){
            for(OrderStatus status : values()){
                if(status.getCode() == code){
                    return status;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }

    public interface alipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }


    public enum platForm{


        ALIPAY(1,"支付宝");

        platForm(int code,String value){
            this.code = code;
            this.value = value;
        }

        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    public enum paymentType{
        ON_SALE_PAY(1,"在线支付");

        paymentType(int code,String value){
            this.code = code;
            this.value = value;
        }

        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {

            return value;
        }

        public static paymentType codeOf(int code){
            for(paymentType type : values()){
                if(type.getCode() == code){
                    return type;
                }
            }
            throw new RuntimeException("没有找到对应的枚举");
        }
    }
}