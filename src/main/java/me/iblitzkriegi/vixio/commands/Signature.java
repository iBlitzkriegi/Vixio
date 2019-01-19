package me.iblitzkriegi.vixio.commands;

public class Signature {
    private String name;
    private DiscordCommand command;

    public Signature(String name, DiscordCommand command) {
        this.name = name;
        this.command = command;
    }

    public String getName() {
        return name;
    }

    public DiscordCommand getCommand() {
        return command;
    }
}
