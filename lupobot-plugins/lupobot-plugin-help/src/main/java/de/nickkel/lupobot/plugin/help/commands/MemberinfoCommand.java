package de.nickkel.lupobot.plugin.help.commands;

import de.nickkel.lupobot.core.LupoBot;
import de.nickkel.lupobot.core.command.CommandContext;
import de.nickkel.lupobot.core.command.CommandInfo;
import de.nickkel.lupobot.core.command.LupoCommand;
import de.nickkel.lupobot.core.util.LupoColor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CommandInfo(name = "memberinfo", category = "information")
public class MemberinfoCommand extends LupoCommand {

    @Override
    public void onCommand(CommandContext context) {
        Member member = context.getMember();

        if(context.getArgs().length == 1) {
            member = context.getServer().getMember(context.getArgs()[0]);
            if(member == null) {
                sendSyntaxError(context, "help_memberinfo-not-found");
                return;
            }
        }

        String roles = "/";
        if(member.getRoles().size() != 0) {
            roles = "";
        }
        for(Role role : member.getRoles()) {
            roles = roles + role.getName() + ", ";
        }

        String activity = "/";
        if(member.getActivities().size() >= 1) {
            activity = member.getActivities().get(0).getName();
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTimestamp(LocalDateTime.now());
        builder.setColor(LupoColor.BLUE.getColor());
        builder.setAuthor(member.getUser().getAsTag() + " (" + member.getIdLong() + ")", null, member.getUser().getAvatarUrl());

        builder.addField(context.getServer().translate(context.getPlugin(), "help_memberinfo-creation"), member.getUser().getTimeCreated().format(DateTimeFormatter.ISO_DATE_TIME), false);
        builder.addField(context.getServer().translate(context.getPlugin(), "help_memberinfo-joined"), member.getTimeJoined().format(DateTimeFormatter.ISO_DATE_TIME), false);
        builder.addField(context.getServer().translate(context.getPlugin(), "help_memberinfo-status"), member.getOnlineStatus().name(), false);
        builder.addField(context.getServer().translate(context.getPlugin(), "help_memberinfo-activity"), activity, false);
        builder.addField(context.getServer().translate(context.getPlugin(), "help_memberinfo-roles"), roles, false);
        context.getChannel().sendMessage(builder.build()).queue();
    }
}