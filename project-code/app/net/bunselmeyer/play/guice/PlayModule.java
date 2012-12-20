package net.bunselmeyer.play.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.servlet.RequestScoped;
import play.mvc.Http;

import static com.google.inject.servlet.ServletScopes.REQUEST;

public class PlayModule extends AbstractModule {
    @Override
    protected final void configure() {
        bindScope(RequestScoped.class, REQUEST);

        // Bind Play request
        bind(Http.Request.class).toProvider(new Provider<Http.Request>() {
            @Override
            public Http.Request get() {
                return Http.Context.current().request();
            }
        });

        // Bind Play response
        bind(Http.Response.class).toProvider(new Provider<Http.Response>() {
            @Override
            public Http.Response get() {
                return Http.Context.current().response();
            }
        });

        // Bind Play Http.Context
        bind(Http.Context.class).toProvider(new Provider<Http.Context>() {
            @Override
            public Http.Context get() {
                return Http.Context.current();
            }
        });

        configurePlay();
    }

    // Override me
    protected void configurePlay() {
    }
}
