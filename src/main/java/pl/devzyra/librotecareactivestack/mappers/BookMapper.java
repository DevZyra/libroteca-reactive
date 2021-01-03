package pl.devzyra.librotecareactivestack.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import pl.devzyra.librotecareactivestack.entities.BookDocument;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookDocument mapToBook(@MappingTarget BookDocument userDocument, BookDocument bookDto);

}
