package Models;

import java.sql.Timestamp;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;


public class TimestampAdapter extends XmlAdapter<String, Timestamp> {

    @Override
    public Timestamp unmarshal(String v) throws Exception {
        return Timestamp.valueOf(v);
    }

    @Override
    public String marshal(Timestamp v) throws Exception {
        return v.toString();
    }
}

