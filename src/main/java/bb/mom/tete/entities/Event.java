package bb.mom.tete.entities;

import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Event(String name, String description, LocalDateTime date, EventType type) {

    public static Event now(String name, String description) {
        return now(name, description, EventType.CREATE);
    }

    public static Event now(String name, String description, EventType type) {
        return new Event(name, description, LocalDateTime.now(), type);
    }

    public static Stream<Event> randomStream() {
        return Stream.generate(() ->
                now((IntStream
                        .generate(() -> (int) (Math.random() * 26 + 'a'))
                        .limit((long) (Math.random() * 10 + 5))
                        .map(i -> (char) i)
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                        .toString()),
                        "description",
                        EventType.values()[(int) (Math.random() * 3)]));
    }
}


