package presenters;

import com.google.inject.Inject;
import play.mvc.Http;

import java.net.HttpCookie;

public class FooPresenter {

    private final Http.Request _request;
    private final Http.Response _response;
    private final Http.Context _context;

    @Inject
    FooPresenter(Http.Request request, Http.Response response, Http.Context context) {
        _request = request;
        _response = response;
        _context = context;
    }

    public String getBar() {
        return _request.toString();
    }
}
