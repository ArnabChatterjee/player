/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerProcessControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void rootEndpointShouldReturnHello() throws Exception {

        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Hello from Players Process API!"));
    }

    @Test
    public void testBasicScenarios() throws Exception {
    	String body = "{" + 
    			"    \"players\": [" + 
    			"        {" + 
    			"            \"name\": \"Sub zero\"," + 
    			"            \"type\": \"expert\"" + 
    			"        }," + 
    			"        {" + 
    			"            \"name\": \"Scorpion\"," + 
    			"            \"type\": \"novice\"" + 
    			"        }," + 
    			"        {" + 
    			"            \"name\": \"Reptile\"," + 
    			"            \"type\": \"meh\"" + 
    			"        }" + 
    			"    ]" + 
    			"}";
        this.mockMvc.perform(post("/processPlayers").content(body))
        		
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content()
                        .string("{\"result\":[\"player Sub zero stored in DB\",\"player Scorpion sent to Kafka topic\",\"player Reptile did not fit\"]}"));
    }

}
