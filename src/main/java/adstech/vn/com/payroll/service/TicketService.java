package adstech.vn.com.payroll.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import adstech.vn.com.payroll.contract.ResponseContract;
import adstech.vn.com.payroll.model.Ticket;
import adstech.vn.com.payroll.repository.TicketRepository;
import adstech.vn.com.payroll.util.CommonConstant;
import adstech.vn.com.payroll.util.EmailSender;

@Service
public class TicketService implements ITicketService{
	@Autowired
	TicketRepository ticketRepository;

	@Override
	public ResponseContract<?> create(Ticket ticket) {
		try {
			return new ResponseContract<Long>( CommonConstant.RESPONSE_STATUS_SUCCESS,null,ticketRepository.create(ticket));
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(null, CommonConstant.RESPONSE_STATUS_FAILURE,e.getMessage());
		}
		
	}

	@Override
	public ResponseContract<?> update(Ticket ticket) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseContract<?> submitTicket(Map<String, Object> submit) {
		try {
		Integer ticketId = (Integer)submit.get("ticket_id");
		String linkApprove = (String)submit.get("link_submit");
		Ticket ticket = ticketRepository.getById(ticketId);
		EmailSender emailSender = new EmailSender();
		emailSender.setLinkAccess(linkApprove);
		emailSender.setStrMailApprover(ticket.getApproverEmail());
		emailSender.sendmail();
		return new ResponseContract<String>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, "Đã gửi mail thành công tới " + ticket.getApproverEmail());
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(null, CommonConstant.RESPONSE_STATUS_FAILURE,e.getMessage());
		}
	}

	@Override
	public ResponseContract<?> approve(Map<String, Object> approve) {
		try {
			Integer ticketId = (Integer)approve.get("ticket_id");
			Boolean isAccess = (Boolean)approve.get("is_access");
			Ticket ticket = ticketRepository.getById(ticketId);
			
			if(isAccess == true) {
				ticket.setStatus("approved");
				ticketRepository.update(ticket);
				EmailSender emailSender = new EmailSender();
				emailSender.setLinkAccess("Ban da duoc nghi");
				emailSender.setStrMailApprover(ticket.getApproverEmail());
				emailSender.sendmail();
				return new ResponseContract<Boolean>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, isAccess);
			}
			else {
				ticket.setStatus("rejected");
				String rejectReson = (String)approve.get("reject_reson");
				ticket.setRejectReason(rejectReson);
				ticketRepository.update(ticket);
				EmailSender emailSender = new EmailSender();
				emailSender.setLinkAccess(rejectReson);
				emailSender.setStrMailApprover(ticket.getApproverEmail());
				emailSender.sendmail();
				return new ResponseContract<Boolean>(CommonConstant.RESPONSE_STATUS_SUCCESS, null, isAccess);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>(null, CommonConstant.RESPONSE_STATUS_FAILURE,e.getMessage());
		}
	}

	@Override
	public ResponseContract<?> reject(Map<String, Object> reject) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
