package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcursionDto {
  private Integer id;
  private LocalDateTime begin;
  private LocalDateTime end;
  private Integer worker_id;

}
