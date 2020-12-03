package pl.devzyra.librotecareactivestack.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDocument {

    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String title;
    private List<Author> authors;

}
