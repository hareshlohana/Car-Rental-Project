package org.car_rantel.dao;

import org.car_rantel.domain.Customer;
import org.car_rantel.domain.Vehicle;
import org.car_rantel.mapper.VehicleMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.car_rantel.dao.SqlQueryConstant.*;

public class VehicleDAO extends BaseDAO implements ICrud<Vehicle>{

    private static final VehicleMapper vehicleMapper = new VehicleMapper();

    public static Vehicle getByIndex(int index) {
        try {
            PreparedStatement ps = conn.prepareStatement("WITH IndexedRows AS (SELECT *, ROW_NUMBER() OVER (ORDER BY id) AS row_num FROM vehicle) SELECT * FROM IndexedRows WHERE row_num = "+index+"+1");
            ResultSet rs = ps.executeQuery();
            return vehicleMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Vehicle obj) {
        try {
            PreparedStatement ps = conn.prepareStatement(INSERT_INTO_VEHICLE);
            ps.setString(1, obj.getV_name());
            ps.setString(2, obj.getModel());
            ps.setString(3, obj.getBrand());
            ps.setString(4, obj.getColor());
            ps.setLong(5, obj.getOwner_id());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Vehicle> getAll() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(Get_All_VEHICLE);
            return vehicleMapper.resultSetToList(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Vehicle getById(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(Get_VEHICLE_BY_ID);
            ps.setInt(1, id.intValue());
            ResultSet rs = ps.executeQuery();
            return vehicleMapper.resultSetToObject(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getByName(String name) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from vehicle where v_name like '%"+name+"%'");
            ResultSet rs = ps.executeQuery();
            return vehicleMapper.resultSetToList(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Vehicle obj,Integer index) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE vehicle SET v_name = ?, model = ?, brand = ?, color = ?, owner_id = ? where id ="+index+"+1");
            ps.setString(1,obj.getV_name());
            ps.setString(2,obj.getModel());
            ps.setString(3,obj.getBrand());
            ps.setString(4,obj.getColor());
            ps.setString(5,obj.getColor());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> deleteByID(Long id) {
        try {
            PreparedStatement ps = conn.prepareStatement(DELETE_VEHICLE_BY_ID);
            ps.setInt(1,id.intValue());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static void deleteByIndex(Integer index) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM vehicle where id ="+index+"");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
