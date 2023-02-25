package sod.eastonone.music.es.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {
	
//	***********************************************************************************************
//	***********************************************************************************************
//	**********************     Unused Class, kept as a reference       ****************************
//	***  https://blogs.perficient.com/2022/08/22/elasticsearch-java-api-client-springboot/  *******
//	******     https://github.com/sundharamurali/elasticsearch-springboot                 *********
//	***********************************************************************************************
	
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String jobTitle;
    private String phone;
    private Integer size;
}
