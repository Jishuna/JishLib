package me.jishuna.jishlib.command;

import java.util.List;

public record CommandInfo(String name, String permission, List<String> aliases) {

}
