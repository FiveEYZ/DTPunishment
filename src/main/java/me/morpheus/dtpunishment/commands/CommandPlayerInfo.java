package me.morpheus.dtpunishment.commands;

import me.morpheus.dtpunishment.DTPunishment;
import me.morpheus.dtpunishment.utils.Util;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.UUID;

public class CommandPlayerInfo implements CommandExecutor {

    private DTPunishment main;

    public CommandPlayerInfo(DTPunishment main){
        this.main = main;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (args.getOne("player").isPresent()) {
            Optional<User> user = Util.getUser(args.<String>getOne("player").get());
            if (!user.isPresent()) {
                src.sendMessage(Text.of(args.<String>getOne("player").get() + " never joined your server "));
                return CommandResult.empty();
            }

            UUID uuid = user.get().getUniqueId();


            src.sendMessage(Text.of("Player : " + user.get().getName()));
            src.sendMessage(Text.of("Mute : " + main.getDatastore().getMutepoints(uuid)));
            src.sendMessage(Text.of("Ban : " + main.getDatastore().getBanpoints(uuid)));

            main.getDatastore().finish();
            return CommandResult.success();


        } else {

            if (src instanceof Player) {
                UUID uuid = ((Player) src).getUniqueId();

                src.sendMessage(Text.of("Player : " + src.getName()));
                src.sendMessage(Text.of("Mute : " + main.getDatastore().getMutepoints(uuid)));
                src.sendMessage(Text.of("Ban : " + main.getDatastore().getBanpoints(uuid)));

                main.getDatastore().finish();
                return CommandResult.success();
            }

            src.sendMessage(Text.of("You need to be a player to execute this"));
            return CommandResult.empty();

        }
    }
}