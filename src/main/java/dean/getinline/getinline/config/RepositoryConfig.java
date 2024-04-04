package dean.getinline.getinline.config;

import dean.getinline.getinline.repository.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 인터페이스의 경우 스프링이 Bean 으로 생성할 수 없기 떄문에 config를 만들어 주어야한다.
@Configuration
public class RepositoryConfig {

    @Bean
    public EventRepository eventRepository() {
        return new EventRepository() {};
    }
}
