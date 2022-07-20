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

public class GetLocalListVersionConf 
{
	private Logger logger = LoggerFactory.getLogger("GetLocalListVersionConf");
	
	public String process(AprotechWebSocketSession aprotechSession, EVCMSService evcmsService, String machineId, String dumy, String rawData)
	{
		String response = "";
		
        try
        {
	        Gson gson = new Gson();
	        Object[] message = gson.fromJson(rawData, Object[].class);
	        
	        String payloadString = gson.toJson(message[2]);
	        GetLocalListVersionConf.Payload payload = gson.fromJson(payloadString, GetLocalListVersionConf.Payload.class);
	        
	        logger.info("================= GetLocalListVersionConf =================");
	        logger.info("machineId -> " + machineId);
	        logger.info("listVersion -> " + String.valueOf(payload.listVersion));	        
	        logger.info("====================================================");
			
			AccessDAO dao = new AccessDAO(evcmsService);
			dao.addMachineGetLocalListVersionLog(machineId, "recv", payload.listVersion);
	        
        }catch(Exception e) {
        	logger.error(String.format("GetLocalListVersionConf.process() exception : %s", e.getMessage()));
        }		
		
		return response;
	}
	
    public class Payload 
    {
    	public int listVersion;
    }
}
