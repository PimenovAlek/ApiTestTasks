package randomuser.me.responseobject;


import java.util.List;
import java.util.Map;

public class ResponseData {
    List<Result> results;
    Info info;

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "results=" + results +
                ", info=" + info +
                '}';
    }
}
