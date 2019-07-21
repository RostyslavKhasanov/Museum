package dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transfer data object for Exhibit
 *
 * @author Rostyslav Khasanov
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDto {
  private Integer id;
  private Integer positionId;
  private String firstName;
  private String lastName;
  List<HallDto> halls;
  List<ExcursionDto> excursions;
  private Integer countExcursion;
  private Integer countHour;
}
