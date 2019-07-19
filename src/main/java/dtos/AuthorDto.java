package dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transfer data object for Author.
 *
 * @author Nazar Stasyk
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
  private Integer id;
  private String firstName;
  private String secondName;
}
