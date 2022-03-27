package info.itsthesky.disky.elements.events.channel;

import info.itsthesky.disky.api.events.DiSkyEvent;
import info.itsthesky.disky.api.events.SimpleDiSkyEvent;
import info.itsthesky.disky.core.SkriptUtils;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;

public class VoiceMuteEvent extends DiSkyEvent<GuildVoiceMuteEvent> {

    static {
        register("Voice Mute Event", VoiceMuteEvent.class, BukkitVoiceMuteEvent.class,
                "[discord] member voice mute")
                .description("Fired when a member gets (un)-muted from a voice channel, either by self-mute or by a moderator \n can be used to get the old/new mute state, the author (if a moderador has muted) the guild and the audio channel.")
                .examples("on member voice mute:");


        SkriptUtils.registerBotValue(VoiceMuteEvent.BukkitVoiceMuteEvent.class);

        SkriptUtils.registerAuthorValue(VoiceMuteEvent.BukkitVoiceMuteEvent.class, e -> e.getJDAEvent().getGuild());

        SkriptUtils.registerValue(VoiceMuteEvent.BukkitVoiceMuteEvent.class, Boolean.class,
                event -> event.getJDAEvent().getVoiceState().isMuted(), 0);

        SkriptUtils.registerValue(VoiceMuteEvent.BukkitVoiceMuteEvent.class, Channel.class,
                event -> event.getJDAEvent().getVoiceState().getChannel(), 0);

        SkriptUtils.registerValue(VoiceMuteEvent.BukkitVoiceMuteEvent.class, Guild.class,
                event -> event.getJDAEvent().getGuild(), 0);

    }

    public static class BukkitVoiceMuteEvent extends SimpleDiSkyEvent<GuildVoiceMuteEvent> {
        public BukkitVoiceMuteEvent(VoiceMuteEvent event) {
        }
    }
}