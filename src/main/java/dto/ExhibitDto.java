package dto;

public class ExhibitDto {
  private Integer id;
  private Integer author_id;
  private Integer hall_id;
  private String name;
  private String material;
  private String technology;

    public ExhibitDto() {
    }

    public ExhibitDto(Integer id, Integer author_id, Integer hall_id, String name, String material, String technology) {
        this.id = id;
        this.author_id = author_id;
        this.hall_id = hall_id;
        this.name = name;
        this.material = material;
        this.technology = technology;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public Integer getHall_id() {
        return hall_id;
    }

    public void setHall_id(Integer hall_id) {
        this.hall_id = hall_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }
}
