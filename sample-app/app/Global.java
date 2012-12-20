import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.RequestScoped;
import net.bunselmeyer.play.guice.GuiceGlobalSettings;
import net.bunselmeyer.play.guice.PlayModule;
import presenters.FooPresenter;

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
