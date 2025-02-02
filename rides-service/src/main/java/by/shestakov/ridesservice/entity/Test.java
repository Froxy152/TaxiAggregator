package by.shestakov.ridesservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.naming.NamingEnumeration;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("test_user")
public class Test {
    @Id
    private String id;
    @Field(value = "name")
    private String name;
    @Field("lastName")
    private String lastName;
    @Field(name = "status", targetType = FieldType.INT32)
    private Status status;
}
