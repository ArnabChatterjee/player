package sb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerProcessController {

    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private Producer producer;

    public PlayerProcessController() {
        
    }

    @RequestMapping("/")
    public String index() {
        return "Hello from Players Process API!";
    }
    
    @RequestMapping(value= "/processPlayers", method = RequestMethod.POST)
    public Map<String,List<String>> processPlayers(@RequestBody String player) {
    	Map<String,List<String>> result = new HashMap<>();
    	List<String> indivResult = new ArrayList<String>();
    	JSONObject obj = new JSONObject(player);
    	JSONArray arr = obj.getJSONArray("players");
    	for (int i = 0; i < arr.length(); i++) {
            String name = arr.getJSONObject(i).getString("name");
            String type = arr.getJSONObject(i).getString("type");
            StringBuilder str = new StringBuilder("player ");
            str.append(name);
            switch (type) {
			case "expert":
					//store in H2 database
					indivResult.add(str.append(" stored in DB").toString());
					playerService.saveOrUpdate(name);
				break;
			case "novice":
					// sent to kafka
					indivResult.add(str.append(" sent to Kafka topic").toString());
					producer.sendMessage(name);
					break;
			default:
				indivResult.add(str.append(" did not fit").toString());
				break;
			}
        }
    	result.put("result", indivResult);
    	return result;
    }
}