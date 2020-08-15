
package foo;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "name",
    "ppu",
    "batters",
    "topping"
})
public class Item {

    @JsonProperty("id")
    private String id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("name")
    private String name;
    @JsonProperty("ppu")
    private Double ppu;
    @JsonProperty("batters")
    private Batters batters;
    @JsonProperty("topping")
    private List<Topping> topping = new ArrayList<Topping>();

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Item withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public Item withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Item withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("ppu")
    public Double getPpu() {
        return ppu;
    }

    @JsonProperty("ppu")
    public void setPpu(Double ppu) {
        this.ppu = ppu;
    }

    public Item withPpu(Double ppu) {
        this.ppu = ppu;
        return this;
    }

    @JsonProperty("batters")
    public Batters getBatters() {
        return batters;
    }

    @JsonProperty("batters")
    public void setBatters(Batters batters) {
        this.batters = batters;
    }

    public Item withBatters(Batters batters) {
        this.batters = batters;
        return this;
    }

    @JsonProperty("topping")
    public List<Topping> getTopping() {
        return topping;
    }

    @JsonProperty("topping")
    public void setTopping(List<Topping> topping) {
        this.topping = topping;
    }

    public Item withTopping(List<Topping> topping) {
        this.topping = topping;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Item.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("ppu");
        sb.append('=');
        sb.append(((this.ppu == null)?"<null>":this.ppu));
        sb.append(',');
        sb.append("batters");
        sb.append('=');
        sb.append(((this.batters == null)?"<null>":this.batters));
        sb.append(',');
        sb.append("topping");
        sb.append('=');
        sb.append(((this.topping == null)?"<null>":this.topping));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.ppu == null)? 0 :this.ppu.hashCode()));
        result = ((result* 31)+((this.batters == null)? 0 :this.batters.hashCode()));
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.type == null)? 0 :this.type.hashCode()));
        result = ((result* 31)+((this.topping == null)? 0 :this.topping.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Item) == false) {
            return false;
        }
        Item rhs = ((Item) other);
        return (((((((this.ppu == rhs.ppu)||((this.ppu!= null)&&this.ppu.equals(rhs.ppu)))&&((this.batters == rhs.batters)||((this.batters!= null)&&this.batters.equals(rhs.batters))))&&((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.type == rhs.type)||((this.type!= null)&&this.type.equals(rhs.type))))&&((this.topping == rhs.topping)||((this.topping!= null)&&this.topping.equals(rhs.topping))));
    }

}
