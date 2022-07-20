package kr.co.watchpoint.evcms.protocol.OCPP16.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import kr.co.watchpoint.evcms.config.EVCMSConfig;
import kr.co.watchpoint.evcms.dao.AccessDAO;
import kr.co.watchpoint.evcms.protocol.OCPP16.BootNotification;
import kr.co.watchpoint.evcms.service.EVCMSService;
import kr.co.watchpoint.evcms.struct.AprotechWebSocketSession;

public class CancelReservationConf 
{
	private Logger logger = LoggerFactory.getLogger("CancelReservationConf");
	
	public String process(AprotechWebSocketSession aprotechSession, EVCMSService evcmsService, String machineId, String reservationId, String rawData)
	{
		String response = "";
		
        try
        {
	        Gson gson = new Gson();
	        Object[] message = gson.fromJson(rawData, Object[].class);
	        
	        String payloadString = gson.toJson(message[2]);
	        CancelReservationConf.Payload payload = gson.fromJson(payloadString, CancelReservationConf.Payload.class);
	        
	        logger.info("================= CancelReservationConf =================");
	        logger.info("machineId -> " + machineId);
	        logger.info("status -> " + payload.status);	        
	        logger.info("====================================================");
			
			AccessDAO dao = new AccessDAO(evcmsService);
			dao.addMachineCancelReservationLog(machineId, payload.status, Integer.parseInt(reservationId));
	        
        }catch(Exception e) {
        	logger.error(String.format("CancelReservationConf.process() exception : %s", e.getMessage()));
        }		
		
		return response;
	}
	
    public class Payload 
    {
    	public String status;
    }
}
