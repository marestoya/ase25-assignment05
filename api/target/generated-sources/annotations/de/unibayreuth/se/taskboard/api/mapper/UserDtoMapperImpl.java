package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T13:56:57+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class UserDtoMapperImpl extends UserDtoMapper {

    @Override
    public UserDto fromBusiness(User source) {
        if ( source == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        LocalDateTime createdAt = null;

        id = source.getId();
        name = source.getName();
        createdAt = mapTimestamp( source.getCreatedAt() );

        LocalDateTime updatedAt = null;

        UserDto userDto = new UserDto( id, name, createdAt, updatedAt );

        return userDto;
    }

    @Override
    public User toBusiness(UserDto source) {
        if ( source == null ) {
            return null;
        }

        String name = null;

        name = source.getName();

        User user = new User( name );

        user.setCreatedAt( mapTimestamp(source.getCreatedAt()) );

        return user;
    }
}
