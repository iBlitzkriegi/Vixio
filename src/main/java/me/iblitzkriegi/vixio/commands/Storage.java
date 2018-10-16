package me.iblitzkriegi.vixio.commands;

public class Storage {
    private String name;
    private DiscordCommand command;

    public Storage(String name, DiscordCommand command) {
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
