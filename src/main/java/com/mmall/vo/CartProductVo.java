package com.mmall.vo;

import java.math.BigDecimal;

public class CartProductVo {
//购物车和产品的结合

    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;   //购物车中产品的数量
    private String  productName;
    private Integer productChecked;   //此商品是否被勾选
    private String ProductSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private BigDecimal productPriceTotal;
    private Integer productStock;
    private Integer ProductStatus;

    private String limitQuantity;//限制数量的一个返回结果

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductChecked() {
        return productChecked;
    }

    public void setProductChecked(Integer productChecked) {
        this.productChecked = productChecked;
    }

    public String getProductSubtitle() {
        return ProductSubtitle;
    }

    public void setProductSubtitle(String productSubtitle) {
        ProductSubtitle = productSubtitle;
    }

    public String getProductMainImage() {
        return productMainImage;
    }

    public void setProcuctMainImage(String productMainImage) {
        this.productMainImage = productMainImage;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public BigDecimal getProductPriceTotal() {
        return productPriceTotal;
    }

    public void setProductPriceTotal(BigDecimal productPriceTotal) {
        this.productPriceTotal = productPriceTotal;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductStatus() {
        return ProductStatus;
    }

    public void setProductStatus(Integer productStatus) {
        ProductStatus = productStatus;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }
}
