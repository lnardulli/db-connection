package com.database.connectdb.model.dao.impl;

import com.database.connectdb.model.Animal;
import com.database.connectdb.model.dao.AnimalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AnimalDaoImpl implements AnimalDao {

    private DataSource dataSource;

    @Autowired
    public AnimalDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Integer insert(Animal animal) {

        String sql = "INSERT INTO animal " +
                "(name) VALUES (?)";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, animal.getName());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public Animal findById(Integer animalId) {

        String sql = "SELECT * FROM animal WHERE id = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, animalId);
            Animal animal = null;
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                animal = Animal.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
            }

            rs.close();
            ps.close();

            return animal;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
