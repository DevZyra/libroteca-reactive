package pl.devzyra.librotecareactivestack.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import pl.devzyra.librotecareactivestack.dtos.UserDto;
import pl.devzyra.librotecareactivestack.entities.UserDocument;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDocument mapToUser(@MappingTarget UserDocument userDocument, UserDto userDto);
}
