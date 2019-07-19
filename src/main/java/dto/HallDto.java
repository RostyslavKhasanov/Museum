package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HallDto {
  private Integer id;
  private Integer worker_id;
  private String name;
  private List<ExhibitDto> exhibits;
}
