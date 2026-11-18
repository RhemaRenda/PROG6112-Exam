package com.mycompany.exam.report;

import java.text.DecimalFormat;
import java.util.Scanner;

public class PrintProduct {
    private final IProduct product;

    public PrintProduct(IProduct product) {
        this.product = product;
    }

    public void printTotalProductSales() {
        System.out.println("TOTAL PRODUCT SALES: "
                + product.GetTotalProductsSold());
    }

    public void printAverageProductSales() {
        DecimalFormat format = new DecimalFormat("0.##");
        System.out.println("AVERAGE PRODUCT SALES: "
                + format.format(product.GetAverageProductSold()));
    }

    public void printMostPopularProduct() {
        System.out.println("MOST POPULAR PRODUCT: "
                + product.GetMostPopularProduct());
    }

    public static void main(String[] args) {
        String[] productNames = {
            "Cozy Nights Monarch",
            "Restonic Barbados",
            "Sealy Phuket"
        };

        // Each row is a mattress and each column is a month.
        int[][] sales = new int[productNames.length][3];

        try (Scanner input = new Scanner(System.in)) {
            for (int row = 0; row < productNames.length; row++) {
                for (int month = 0; month < sales[row].length; month++) {
                    System.out.printf(
                            "Enter number of sales for month %d for %s mattress: ",
                            month + 1, productNames[row]);

                    while (true) {
                        if (!input.hasNext()) {
                            System.out.println("\nSales input ended early.");
                            return;
                        }

                        if (input.hasNextInt()) {
                            int units = input.nextInt();
                            if (units >= 0) {
                                sales[row][month] = units;
                                break;
                            }
                        } else {
                            input.next();
                        }

                        System.out.print("Enter a non-negative whole number: ");
                    }
                }

                System.out.println();
            }
        }

        Product product = new Product(productNames, sales);
        PrintProduct report = new PrintProduct(product);

        System.out.println("PRODUCT SALES REPORT - 2026");
        System.out.println("****************************************");
        report.printTotalProductSales();
        report.printAverageProductSales();
        report.printMostPopularProduct();
    }
}