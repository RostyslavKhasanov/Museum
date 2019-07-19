package dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcursionDto {
  private Integer id;
  private Date begin;
  private Date end;
//  private WorkerDto workerDto;
}
