package com.senla.repositories.jdbc;

import com.senla.models.ride.Ride;
import com.senla.pool.BeanConnectionPool;
import com.senla.util.repository.Repository;
import com.senla.util.repository.jdbc.TransactionalRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class RideJDBCRepo extends TransactionalRepository<Ride, Long> implements Repository<Ride, Long> {

    @Autowired
    public void init(BeanConnectionPool pool) {
        this.connectionPool = pool;
    }

    @Override
    protected String countSqlQuery() {
        return "select count(*) as number from rides";
    }

    @Override
    protected String selectAllSqlQuery() {
        return "select * from rides";
    }

    @Override
    protected String selectByIdSqlQuery() {
        return "select * from rides where ride_id = ?";
    }

    @Override
    protected String insertSqlQuery() {
        return """
            insert into rides(shift_id, customer_id, promocode_id, promocode_enter_code, ride_tip, ride_distance_meters, start_point_long, start_point_lat, end_point_long, end_point_lat, created_at, ride_driver_waiting, ride_starttime, ride_endtime, status)
                values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
    }

    @Override
    protected String updateSqlQuery() {
        return """
            update rides set (shift_id, customer_id, promocode_id, promocode_enter_code, ride_tip, ride_distance_meters, start_point_long, start_point_lat, end_point_long, end_point_lat, created_at,  ride_driver_waiting, ride_starttime, ride_endtime, status)
                = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                where ride_id = ?
            """;
    }

    @Override
    protected String deleteSqlQuery() {
        return "delete from rides where ride_id=?";
    }

    @Override
    protected void setStatementFirstPosId(PreparedStatement st, Long id) throws SQLException {
        st.setLong(1, id);
    }

    @Override
    protected Ride buildObj(ResultSet rs) throws SQLException {
        Ride ride = new Ride();
        ride.setId(rs.getLong("ride_id"));
        ride.setShiftId(rs.getLong("shift_id"));
        ride.setCustomerId(rs.getLong("customer_id"));
        ride.setPromocodeId(rs.getLong("promocode_id"));
        ride.setPromocodeEnterCode(rs.getString("promocode_enter_code"));
        ride.setRideTip(rs.getBigDecimal("ride_tip"));
        ride.setRideDistanceMeters(rs.getBigDecimal("ride_distance_meters"));

        ride.setStartPointLat(rs.getBigDecimal("start_point_lat"));
        ride.setStartPointLong(rs.getBigDecimal("start_point_long"));
        ride.setEndPointLat(rs.getBigDecimal("end_point_lat"));
        ride.setEndPointLong(rs.getBigDecimal("end_point_long"));

        if(rs.getTimestamp("created_at") == null){
            ride.setCreatedAt(null);
        } else {ride.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());}

        if(rs.getTimestamp("ride_driver_waiting") == null){
            ride.setRideDriverWaiting(null);
        } else {ride.setRideDriverWaiting(rs.getTimestamp("ride_driver_waiting").toLocalDateTime());}

        if(rs.getTimestamp("ride_starttime") == null){
            ride.setRideStartTime(null);
        } else {ride.setRideStartTime(rs.getTimestamp("ride_starttime").toLocalDateTime());}

        if(rs.getTimestamp("ride_endtime") == null){
            ride.setRideEndTime(null);
        } else {ride.setRideEndTime(rs.getTimestamp("ride_endtime").toLocalDateTime());}


        ride.setStatus(rs.getString("status"));
        return ride;
    }

    @Override
    protected void setDataToCreateQuery(Ride ride, PreparedStatement statement) throws SQLException {
        setEntityBody(ride, statement);
    }
    @Override
    protected void setDataToUpdateQuery(Ride ride, PreparedStatement statement) throws SQLException {
        setEntityBody(ride, statement);
        statement.setLong(16, ride.getId());
    }

    private void setEntityBody(Ride ride, PreparedStatement statement) throws SQLException {
        statement.setLong(1, ride.getShiftId());
        statement.setLong(2, ride.getCustomerId());

        if(ride.getPromocodeId() != null) {
            statement.setLong(3, ride.getPromocodeId());
        } else {statement.setNull(3, Types.BIGINT);}

        if(ride.getPromocodeEnterCode() != null) {
            statement.setString(4, ride.getPromocodeEnterCode());
        } else {statement.setNull(4, Types.VARCHAR);}

        if(ride.getRideTip() != null) {
            statement.setBigDecimal(5, ride.getRideTip());
        } else {statement.setNull(5, Types.NUMERIC);}

        statement.setBigDecimal(6, ride.getRideDistanceMeters());
//        тут надо 4 ифа - лень
        statement.setBigDecimal(7, ride.getStartPointLong());
        statement.setBigDecimal(8, ride.getStartPointLat());
        statement.setBigDecimal(9, ride.getEndPointLong());
        statement.setBigDecimal(10, ride.getEndPointLat());

        statement.setTimestamp(11, Timestamp.valueOf(ride.getCreatedAt()));
        if(ride.getRideDriverWaiting() != null) {
            statement.setTimestamp(12, Timestamp.valueOf(ride.getRideDriverWaiting()));
        } else {statement.setNull(12, Types.TIMESTAMP);}

        if(ride.getRideDriverWaiting() != null){
            statement.setTimestamp(13, Timestamp.valueOf(ride.getRideStartTime()));
        } else { statement.setNull(13, Types.TIMESTAMP);}

        if(ride.getRideStartTime() != null) {
            statement.setTimestamp(14, Timestamp.valueOf(ride.getRideEndTime()));
        } else {statement.setNull(14, Types.TIMESTAMP);}

        statement.setString(15, ride.getStatus());
    }
}
