package dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Transfer data object for Hall
 *
 * @author Kateryna Horokh
 * @version 1.0
 */
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
