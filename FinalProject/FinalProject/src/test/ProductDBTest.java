package test;

import db.ProductDB;
import model.Product;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDBTest {

    private ProductDB productDB;

    @BeforeEach
    public void setup() throws SQLException {
        productDB = new ProductDB();
    }

    @Test
    public void testCreateProduct() throws SQLException {
        int id = productDB.createProduct("Testprodukt", "Hal 4");
        assertTrue(id > 0);
    }

    @Test
    public void testFindById() throws SQLException {
        int id = productDB.createProduct("Testprodukt2", "Hal 4");
        Product p = productDB.findById(id);
        assertNotNull(p);
        assertEquals("Testprodukt2", p.getProductName());
    }

    
}
