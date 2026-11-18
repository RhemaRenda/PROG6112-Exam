package com.mycompany.exam.report;

public class Product implements IProduct {
    private final String[] productNames;
    private final int[][] sales;

    public Product(String[] productNames, int[][] sales) {
        this.productNames = productNames;
        this.sales = sales;
    }

    @Override
    public int GetTotalProductsSold() {
        int total = 0;

        for (int[] monthlySales : sales) {
            total += totalForProduct(monthlySales);
        }

        return total;
    }

    @Override
    public double GetAverageProductSold() {
        int numberOfEntries = 0;

        for (int[] monthlySales : sales) {
            numberOfEntries += monthlySales.length;
        }

        // The sample averages all nine monthly sales figures.
        return (double) GetTotalProductsSold() / numberOfEntries;
    }

    @Override
    public String GetMostPopularProduct() {
        int bestProductIndex = 0;
        int highestTotal = totalForProduct(sales[0]);

        // Compare each mattress's total sales across the three months.
        for (int i = 1; i < sales.length; i++) {
            int productTotal = totalForProduct(sales[i]);

            if (productTotal > highestTotal) {
                highestTotal = productTotal;
                bestProductIndex = i;
            }
        }

        return productNames[bestProductIndex] + " (" + highestTotal + ")";
    }

    private int totalForProduct(int[] monthlySales) {
        int total = 0;

        for (int unitsSold : monthlySales) {
            total += unitsSold;
        }

        return total;
    }
}