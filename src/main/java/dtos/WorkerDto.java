package dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {
    private Integer id;
    private Integer position_id;
    private Integer excursion_id;
    private String fName;
    private String sName;
}
