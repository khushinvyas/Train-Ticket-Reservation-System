package com.khushin.service;

import java.util.List;

import com.khushin.beans.HistoryBean;
import com.khushin.beans.TrainException;

public interface BookingService {

	public List<HistoryBean> getAllBookingsByCustomerId(String customerEmailId) throws TrainException;

	public HistoryBean createHistory(HistoryBean bookingDetails) throws TrainException;

}
