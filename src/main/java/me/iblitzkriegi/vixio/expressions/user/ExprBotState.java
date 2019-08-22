package me.iblitzkriegi.vixio.expressions.user;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import me.iblitzkriegi.vixio.Vixio;
import net.dv8tion.jda.api.entities.User;

public class ExprBotState extends SimplePropertyExpression<User, Boolean> {
    static {
        Vixio.getInstance().registerPropertyExpression(ExprBotState.class, Boolean.class, "bot state", "users")
                .setName("Bot State Of User")
                .setDesc("Check if a user is a bot or not.")
                .setExample("set {var} to bot state of event-user");
    }

    @Override
    protected String getPropertyName() {
        return "bot state";
    }

    @Override
    public Boolean convert(User user) {
        return user.isBot();
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }
}
