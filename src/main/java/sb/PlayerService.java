package sb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService implements PlayerInterface {
	@Autowired
    private PlayerRepository repository;

    @Override
    public void saveOrUpdate(String name)  {
    	PlayerDTO player = new PlayerDTO();
    	player.setName(name);
    	repository.save(player);
    }
}