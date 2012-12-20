package net.bunselmeyer.play.guice;

import com.google.inject.Injector;
import play.Application;
import play.GlobalSettings;

public abstract class GuiceGlobalSettings extends GlobalSettings {

    private Injector _injector;

    @Override
    public void onStart(Application application) {
        _injector = createInjector();
    }

    @Override
    public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
        return _injector.getInstance(controllerClass);
    }

    protected abstract Injector createInjector();
}