package com.khushin.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.khushin.beans.TrainBean;
import com.khushin.beans.TrainException;
import com.khushin.constant.ResponseCode;
import com.khushin.service.TrainService;
import com.khushin.utility.DBUtil;

public class TrainServiceImpl implements TrainService {

	@Override
	public String addTrain(TrainBean train) {
		String responseCode = ResponseCode.FAILURE.toString();
		String query = "INSERT INTO TRAIN VALUES(?,?,?,?,?,?)";
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setLong(1, train.getTr_no());
			ps.setString(2, train.getTr_name());
			ps.setString(3, train.getFrom_stn());
			ps.setString(4, train.getTo_stn());
			ps.setLong(5, train.getSeats());
			ps.setDouble(6, train.getFare());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				responseCode = ResponseCode.SUCCESS.toString();
			}
			ps.close();
		} catch (SQLException | TrainException e) {
			responseCode += " : " + e.getMessage();
		}
		return responseCode;
	}

	@Override
	public String deleteTrainById(String trainNo) {
		String responseCode = ResponseCode.FAILURE.toString();
		String query = "DELETE FROM TRAIN WHERE TR_NO=?";
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, trainNo);
			int response = ps.executeUpdate();
			if (response > 0) {
				responseCode = ResponseCode.SUCCESS.toString();
			}
			ps.close();
		} catch (SQLException | TrainException e) {
			responseCode += " : " + e.getMessage();
		}
		return responseCode;
	}

	@Override
	public String updateTrain(TrainBean train) {
		System.out.println("Update Train called");
		
		String responseCode = ResponseCode.FAILURE.toString();
		try {
		    Connection con = DBUtil.getConnection();
		    Statement statement = con.createStatement();
		    String query = "UPDATE train SET from_stn = '" + train.getFrom_stn() + "', to_stn = '" + train.getTo_stn() + "', seats = " + train.getSeats() + ", fare = " + train.getFare() + " WHERE tr_name = '" + train.getTr_name() + "' AND tr_no = " + train.getTr_no();
		    int rowsUpdated = statement.executeUpdate(query);
		    if (rowsUpdated > 0) {
		        responseCode = ResponseCode.SUCCESS.toString();
		    }
		    statement.close();
		} catch (SQLException | TrainException e) {
		    responseCode += " : " + e.getMessage();
		}

		return responseCode;
	}

	@Override
	public TrainBean getTrainById(String trainNo) throws TrainException {
		TrainBean train = null;
		String query = "SELECT * FROM TRAIN WHERE TR_NO=?";
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, trainNo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				train = new TrainBean();
				train.setFare(rs.getDouble("fare"));
				train.setFrom_stn(rs.getString("from_stn"));
				train.setTo_stn(rs.getString("to_stn"));
				train.setTr_name(rs.getString("tr_name"));
				train.setTr_no(rs.getLong("tr_no"));
				train.setSeats(rs.getInt("seats"));
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new TrainException(e.getMessage());
		}
		return train;
	}

	@Override
	public List<TrainBean> getAllTrains() throws TrainException {
		List<TrainBean> trains = null;
		String query = "SELECT * FROM TRAIN";
		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			trains = new ArrayList<TrainBean>();
			while (rs.next()) {
				TrainBean train = new TrainBean();
				train.setFare(rs.getDouble("fare"));
				train.setFrom_stn(rs.getString("from_stn"));
				train.setTo_stn(rs.getString("to_stn"));
				train.setTr_name(rs.getString("tr_name"));
				train.setTr_no(rs.getLong("tr_no"));
				train.setSeats(rs.getInt("seats"));
				trains.add(train);
			}

			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new TrainException(e.getMessage());
		}
		return trains;
	}

	@Override
	public List<TrainBean> getTrainsBetweenStations(String fromStation, String toStation) throws TrainException {
		List<TrainBean> trains = null;
		String query = "SELECT * FROM TRAIN WHERE UPPER(FROM_STN) LIKE UPPER(?) AND UPPER(TO_STN) LIKE UPPER(?)";

		try {
			Connection con = DBUtil.getConnection();
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, "%" + fromStation + "%");
			ps.setString(2, "%" + toStation + "%");
			ResultSet rs = ps.executeQuery();
			trains = new ArrayList<TrainBean>();
			while (rs.next()) {
				TrainBean train = new TrainBean();
				train.setFare(rs.getDouble("fare"));
				train.setFrom_stn(rs.getString("from_stn"));
				train.setTo_stn(rs.getString("to_stn"));
				train.setTr_name(rs.getString("tr_name"));
				train.setTr_no(rs.getLong("tr_no"));
				train.setSeats(rs.getInt("seats"));
				trains.add(train);
			}

			ps.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new TrainException(e.getMessage());
		}
		return trains;
	}

}
