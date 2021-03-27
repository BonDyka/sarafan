package loc.abondare.sarafan.controller;

import loc.abondare.sarafan.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;

    private final List<Map<String, String>> messages = new ArrayList<>() {{
        add(new HashMap<>() {{ put("id", "1"); put("message", "First message"); }});
        add(new HashMap<>() {{ put("id", "2"); put("message", "Second message"); }});
        add(new HashMap<>() {{ put("id", "3"); put("message", "Third message"); }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> read(@PathVariable("id") String id) {
        return getMessage(id);
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream()
                .filter(messages -> messages.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> save(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(this.counter++));
        this.messages.add(message);
        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> edit(@PathVariable("id") String id,
                                    @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);

        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") String id) {
        Map<String, String> messageFromDb = getMessage(id);

        messages.remove(messageFromDb);
    }
}