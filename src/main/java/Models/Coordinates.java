package Models;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "coordinates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates {
    @XmlElement(name = "xCoord", required = true)
    private Double x;
    @XmlElement(name = "yCoord", required = true)
    private Integer y;

    public Coordinates(Double x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Coordinates(){
        x = 0.0;
        y = 0;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates: " +
                "x = " + x +
                ", y = " + y;
    }

    public int compareTo(Coordinates o) {
        if (o == null) {
            return 1;
        }
        int result = Double.compare(this.x, o.x);
        if (result == 0)
            return Integer.compare(this.y, o.y);
        return result;
    }
}

