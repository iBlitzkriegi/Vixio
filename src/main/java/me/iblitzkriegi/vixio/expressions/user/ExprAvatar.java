package me.iblitzkriegi.vixio.expressions.user;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.iblitzkriegi.vixio.Vixio;
import me.iblitzkriegi.vixio.util.Util;
import me.iblitzkriegi.vixio.util.wrapper.Avatar;
import me.iblitzkriegi.vixio.util.wrapper.Bot;
import net.dv8tion.jda.api.entities.Icon;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.managers.AccountManager;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class ExprAvatar extends SimplePropertyExpression<Object, Avatar> {

    static {
        Vixio.getInstance().registerPropertyExpression(ExprAvatar.class, Avatar.class,
                "[discord] [<default>] avatar", "users/bots/strings/member")
                .setName("Avatar of User")
                .setDesc("Get either the user's custom avatar or their default one that discord gave them. You can extract the id from the url using the ID expression.")
                .setExample(
                        "discord command $info <user>:",
                        "\ttrigger:",
                        "\t\treply with \"%avatar of arg-1%\""
                );
    }

    private boolean defaultAvatar;

    @Override
    public Avatar convert(Object object) {
        if (object instanceof User) {
            return getAvatar((User) object);
        } else if (object instanceof String || object instanceof Bot) {
            Bot bot = Util.botFrom(object);
            if (bot == null) {
                return null;
            }
            return getAvatar(bot.getSelfUser());
        } else if (object instanceof Member) {
            return getAvatar(((Member) object).getUser());
        }
        return null;
    }

    @Override
    public Class<? extends Avatar> getReturnType() {
        return Avatar.class;
    }

    @Override
    protected String getPropertyName() {
        return defaultAvatar ? "default avatar" : "avatar";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        setExpr(exprs[0]);
        defaultAvatar = parseResult.regexes.size() == 1;
        return true;
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return new Class[]{String.class};
        }
        return null;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (defaultAvatar) {
            return;
        }
        for (Object object : getExpr().getAll(e)) {
            if (!(object instanceof User)) {
                Bot bot = Util.botFrom(object);
                if (bot != null) {
                    String url = (String) delta[0];
                    AccountManager manager = bot.getSelfUser().getManager();
                    if (Util.isLink(url)) {
                        Util.async(() -> {
                            try {
                                InputStream inputStream = Util.getInputStreamFromUrl(url);
                                manager.setAvatar(Icon.from(inputStream)).queue();
                            } catch (MalformedURLException e1) {
                                Vixio.getErrorHandler().warn("Vixio attempted to set the avatar of a bot but the URL was invalid/was unable to be loaded.");
                            } catch (IOException e1) {
                                Vixio.getErrorHandler().warn("Vixio attempted to set the avatar of a bot with a URL but was unable to load the URL.");
                            }
                        });
                    } else {
                        File file = new File((String) delta[0]);
                        if (file.exists()) {
                            try {
                                manager.setAvatar(Icon.from(file)).queue();
                            } catch (IOException e1) {
                                Vixio.getErrorHandler().warn("Vixio attempted to set the avatar of a bot to a file but was unable to use the file.");
                            }
                        }
                    }

                }
            }
        }

    }

    public Avatar getAvatar(User user) {
        if (defaultAvatar) {
            return new Avatar(user, user.getDefaultAvatarUrl(), false);
        }
        String avatar = user.getAvatarUrl() == null ? user.getDefaultAvatarUrl() : user.getAvatarUrl();
        return new Avatar(user, avatar, defaultAvatar);
    }

}
