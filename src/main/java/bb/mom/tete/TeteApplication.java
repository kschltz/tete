package bb.mom.tete;

import bb.mom.tete.entities.Event;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TeteApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeteApplication.class, args);
		Event.randomStream()
				.limit(100)
				.forEach(event ->
				{
					try {
						Thread.sleep(100);
						System.out.println(event);
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				});

	}

}
