package dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitDto {
  private Integer id;
  private Integer author_id;
  private Integer hall_id;
  private String name;
  private String material;
  private String technology;
}
