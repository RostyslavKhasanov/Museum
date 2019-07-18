package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitDto {
  private Integer id;
  private Integer authorId;
  private Integer hallId;
  private String name;
  private String material;
  private String technology;
}
