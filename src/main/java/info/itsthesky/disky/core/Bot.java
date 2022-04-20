package info.itsthesky.disky.core;

import ch.njol.skript.util.Timespan;
import info.itsthesky.disky.BotApplication;
import info.itsthesky.disky.DiSky;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

/**
 * Class that will handle every information about a bot.
 * @author ItsTheSky
 */
// TODO: 29/12/2021 Maybe use records here, but it's only for Java 14+
public class Bot {

    private final long startedTime;
    private final String name;
    private final JDA instance;
    private final @Nullable BotApplication application;
    private final boolean forceReload;

    public Bot(String name, JDA instance, @Nullable BotApplication application, boolean forceReload) {
        this.name = name;
        this.application = application;
        this.instance = instance;
        this.forceReload = forceReload;
        this.startedTime = System.currentTimeMillis();
    }

    public static @Nullable Bot create(BotOptions options) {
        final JDABuilder builder = options.toBuilder();

        final JDA built;
        try {
            built = builder.build();
        } catch (Throwable throwable) {
            DiSky.getErrorHandler().exception(null, throwable);
            return null;
        }

        return new Bot(options.getName(), built, options.getApplication(), options.forceReload());
    }

    public String getName() {
        return name;
    }

    public boolean isForceReload() {
        return forceReload;
    }

    public JDA getInstance() {
        return instance;
    }

    public @Nullable BotApplication getApplication() {
        return application;
    }

    @SuppressWarnings("unchecked")
    public <T> T findSimilarEntity(T original) {
        if (original instanceof Guild)
            return (T) getInstance().getGuildById(((Guild) original).getId());
        if (original instanceof User)
            return (T) getInstance().getUserById(((User) original).getId());
        if (original instanceof Role)
            return (T) getInstance().getRoleById(((Role) original).getId());
        return original;
    }

    public MessageChannel findMessageChannel(MessageChannel original) {
        if (original instanceof TextChannel)
            return getInstance().getTextChannelById(original.getId());
        if (original instanceof NewsChannel)
            return getInstance().getNewsChannelById(original.getId());
        if (original instanceof ThreadChannel)
            return getInstance().getThreadChannelById(original.getId());
        if (original instanceof PrivateChannel)
            return getInstance().getPrivateChannelById(original.getId());
        return original;
    }

	public boolean coreIsEquals(JDA core) {
        return getInstance().getSelfUser().getId().equals(core.getSelfUser().getId());
	}

	public Timespan getUptime() {
        return new Timespan(System.currentTimeMillis() - startedTime);
	}

    public void shutdown(boolean force) {
        if (force)
            getInstance().shutdownNow();
        else
            getInstance().shutdown();
        DiSky.getManager().removeBot(this);
    }
}
