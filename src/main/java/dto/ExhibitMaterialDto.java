package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Transfer data object for Exhibit statistic
 *
 * @author Nazar Stasyk
 * @version 1.0
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExhibitMaterialDto {
    private String materialName;
    private Integer materialCount;
}
