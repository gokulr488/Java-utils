
package foo;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "batter"
})
public class Batters {

    @JsonProperty("batter")
    private List<Batter> batter = new ArrayList<Batter>();

    @JsonProperty("batter")
    public List<Batter> getBatter() {
        return batter;
    }

    @JsonProperty("batter")
    public void setBatter(List<Batter> batter) {
        this.batter = batter;
    }

    public Batters withBatter(List<Batter> batter) {
        this.batter = batter;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Batters.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("batter");
        sb.append('=');
        sb.append(((this.batter == null)?"<null>":this.batter));
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
        result = ((result* 31)+((this.batter == null)? 0 :this.batter.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Batters) == false) {
            return false;
        }
        Batters rhs = ((Batters) other);
        return ((this.batter == rhs.batter)||((this.batter!= null)&&this.batter.equals(rhs.batter)));
    }

}
