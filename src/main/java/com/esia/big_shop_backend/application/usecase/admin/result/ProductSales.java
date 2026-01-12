package com.esia.big_shop_backend.application.usecase.admin.result;

public class ProductSales {
    private Long productId;
    private String productName;
    private int totalSold;
    private double totalRevenue;

    public ProductSales(Long productId, String productName, int totalSold, double totalRevenue) {
        this.productId = productId;
        this.productName = productName;
        this.totalSold = totalSold;
        this.totalRevenue = totalRevenue;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getTotalSold() {
        return totalSold;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }


}
