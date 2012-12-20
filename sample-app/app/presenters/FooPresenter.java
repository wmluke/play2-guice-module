package presenters;

import com.google.inject.Inject;
import play.mvc.Http;

public class FooPresenter {

    private final Http.Request _request;

    @Inject
    FooPresenter(Http.Request request) {
        _request = request;
    }

    public String getBar() {
        return _request.toString();
    }
}
