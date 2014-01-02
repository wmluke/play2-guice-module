# play2-guice-module

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/wmluke/play2-guice-module/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

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

Create a `Global` object which extends `GuiceGlobalSettings` as illustrated below...

```java
public class Global extends GuiceGlobalSettings {
    @Override
    protected Injector createInjector() {
        return Guice.createInjector(Stage.PRODUCTION, new PlayModule() {

            @Override
            protected void configurePlay() {
                // bind some stuff!
                bind(FooPresenter.class).in(RequestScoped.class);
            }
        });
    }
}
```

Finally, just annotate your controller method with `@With(RequestScopedAction.class)` to enable `@RequestScoped` resources.

```java
public class Application extends Controller {

    // ahhh immutable...
    private final FooPresenter _fooPresenter;

    // yeah, constructor injector!
    @Inject
    Application(FooPresenter fooPresenter) {
        _fooPresenter = fooPresenter;
    }

    // Enable RequestScoped resources for this action
    @With(RequestScopedAction.class)
    public Result index() {
        String bar = _fooPresenter.getBar();
        return ok(index.render("Request: " + bar));
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

See the [sample-app](https://github.com/wmluke/play2-guice-module/tree/master/sample-app) for more details.

## Roadmap

* Support Session Scopes
* Unit tests :)

## Known Issues

Play 2.1-RC1 has a few bugs with `publish-local`, which make it difficult to use this module:

* https://play.lighthouseapp.com/projects/82401/tickets/898-javadoc-error-invalid-flag-g-when-publishing-new-module-local
* https://play.lighthouseapp.com/projects/82401/tickets/710-publish-local-is-broken

As a work around, I disabled publishing the main API jar...

```scala
publishArtifact in(Compile, packageDoc) := false
```

## Licence

play2-guice-module is distributed under the [Apache 2 licence](http://www.apache.org/licenses/LICENSE-2.0.html).
