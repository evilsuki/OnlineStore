package org.yearup;

public class Product implements Comparable<Product>
{
    @Override
    public int compareTo(Product p)
    {
        return this.productName.compareTo(p.productName);
    }

    private String productId;
    private String productName;
    private float productPrice;


    public Product(String productId, String productName, float productPrice)
    {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
    }


    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public float getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(float productPrice)
    {
        this.productPrice = productPrice;
    }
}
