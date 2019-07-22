package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Transfer data object for Excursion
 *
 * @author Kateryna Horokh
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcursionDto {
  private Integer id;
  private LocalDateTime begin;
  private LocalDateTime end;
  private Double price;
  private Integer worker_id;

}
