package nl.hkstwk.spring.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor(staticName = "nobody")
@AllArgsConstructor
public class HelloMessage {
    private String name;
}
