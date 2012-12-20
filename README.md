# play2-guice-module

Inspired by Guice's excellent [ServletModule](http://code.google.com/p/google-guice/wiki/ServletModule) integration, `play2-guice-module` takes advantage of some of the new Play 2.1 RC1 features to provide several utility classes to integrate Google Guice into Play.

## Requirements
* Play 2.1-RC1
* Java Play project

## Getting Started

Play 2.1-RC1 provides hooks to create a "dynamic" controller class with each request.  In `conf/routes` simply prefix each controller action with an `@`.

```
# Tell Play to create a new controller with each request by prefixing the action with an `@`:
GET     /                           @controllers.Application.index()
```

Create `Global` which extends `GuiceGlobalSettings` as illustrated below...

```java
public class Global extends GuiceGlobalSettings {
    @Override
    protected Injector createInjector() {
        return Guice.createInjector(Stage.PRODUCTION, new PlayModule() {

            @Override
            protected void configurePlay() {
                bind(FooPresenter.class).in(RequestScoped.class);
            }
        });
    }
}
```

Finally, just annotate your controller method with `@With(RequestScopedAction.class)` to enable `@RequestScoped` resources.

```java
public class Application extends Controller {

    private final RequestScopedService _service;

    // Yeah constructor injection for easy unit tests!!!
    @Inject
    Application(RequestScopedService service) {
        _service = service;
    }

    // Use action composition
    @With(RequestScopedAction.class)
    public Result index() {
        return ok(index.render(_service.doSomething()));
    }

}
```

## Available Injections
Installing the `PlayModule` automatically gives you access to several classes from the Play framework.  These classes are injectable in any Guice injected object by default, when you install the `PlayModule`...

```java
@RequestScoped
public class FooPresenter {

    @Inject
    FooPresenter(Http.Request request, Http.Response response, Http.Context context) {
        ...
    }

    ...
}
```

See the sample-app for more details.

## Roadmap

* Support Session Scopes
* Unit tests :)

## Known Issues

Play 2.1-RC1 has a few bugs with `publish-local`, which make it difficult to use this module:

* https://play.lighthouseapp.com/projects/82401/tickets/898-javadoc-error-invalid-flag-g-when-publishing-new-module-local
* https://play.lighthouseapp.com/projects/82401/tickets/710-publish-local-is-broken

As an ugly work around, you can run `play package` and throw the project jar into your lib folder.

## Licence

play2-guice-module is distributed under the [Apache 2 licence](http://www.apache.org/licenses/LICENSE-2.0.html).
