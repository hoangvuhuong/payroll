package adstech.vn.com.payroll.service;

import java.util.Map;

import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.model.Ticket;

public interface ITicketService {
	public ResponseContract<?> create(Ticket ticket);

	public ResponseContract<?> update(Ticket ticket);

	public ResponseContract<?> submitTicket(Map<String, Object> submit);
	
	public ResponseContract<?> approve(Map<String, Object> approve);
	
	public ResponseContract<?> reject(Map<String, Object> reject);

}
