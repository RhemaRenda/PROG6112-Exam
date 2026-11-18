package com.mycompany.exam.report;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    private final Product product = new Product(
            new String[] {
                "Cozy Nights Monarch",
                "Restonic Barbados",
                "Sealy Phuket"
            },
            new int[][] {
                {25, 18, 20},
                {15, 10, 12},
                {18, 15, 20}
            }
    );

    @Test
    public void GetTotalProductSales_WithValidData_ReturnsCorrectValue() {
        assertEquals(153, product.GetTotalProductsSold());
    }

    @Test
    public void GetAverageSales_WithValidData_ReturnsCorrectValue() {
        assertEquals(17.0, product.GetAverageProductSold(), 0.001);
    }
}