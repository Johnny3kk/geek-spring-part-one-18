package ru.geekbrains.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepo {

    private final Connection conn;

    @Autowired
    public ProductRepo(DataSource dataSource) throws SQLException {
        this(dataSource.getConnection());
    }

    public ProductRepo(Connection conn) throws SQLException {
        this.conn = conn;
        createTableIfNotExists(conn);
    }

    private void createTableIfNotExists(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("create table if not exists products (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    title varchar(25),\n" +
                    "    cost decimal,\n" +
                    "    unique index uq_title(title)\n" +
                    ");");
        }
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "insert into products(title, cost) values (?, ?);")) {
            stmt.setString(1, product.getTitle());
            stmt.setBigDecimal(2, product.getCost());
            stmt.execute();
        }
    }

    public void update(Product product) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("update products set title = ? where id = ?");
        stmt.setString(1, product.getTitle());
        stmt.setInt(2, product.getId());
        stmt.execute();
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> res = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("select id, title, cost from products");

            while (rs.next()) {
                res.add(new Product(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3)));
            }
        }
        return res;
    }

}
