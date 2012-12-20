package controllers;

import com.google.inject.Inject;
import net.bunselmeyer.play.guice.RequestScopedAction;
import play.*;
import play.mvc.*;

import presenters.FooPresenter;
import views.html.index;

public class Application extends Controller {

    private final FooPresenter _fooPresenter;

    @Inject
    Application(FooPresenter fooPresenter) {
        _fooPresenter = fooPresenter;
    }

    @With(RequestScopedAction.class)
    public Result index() {
        String bar = _fooPresenter.getBar();
        return ok(index.render("Request: " + bar));
    }
}
