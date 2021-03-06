package net.ilexiconn.llibrary.common.command.builder;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * @author iLexiconn
 * @see CommandBuilder
 * @since 0.5.3
 */
public class CommandArguments {
    private List<Argument> arguments;
    private ICommandSender commandSender;

    public CommandArguments(List<Argument> arguments, ICommandSender commandSender) {
        this.arguments = arguments;
        this.commandSender = commandSender;
    }

    public boolean has(String name) {
        for (Argument argument : arguments) {
            if (argument.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name) {
        for (Argument argument : arguments) {
            if (argument.getName().equals(name)) {
                try {
                    return (T) argument.getType().parse(commandSender, argument.getValue());
                } catch (CommandException e) {
                    commandSender.addChatMessage(new ChatComponentText(e.getLocalizedMessage()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED)));
                }
            }
        }
        return null;
    }

    public ArgumentType<?> type(String name) {
        for (Argument argument : arguments) {
            if (argument.getName().equals(name)) {
                return argument.getType();
            }
        }
        return null;
    }

    public String asString(String name) {
        return get(name);
    }

    public int asInt(String name) {
        return (Integer) get(name);
    }

    public EntityPlayer asPlayer(String name) {
        return (EntityPlayer) get(name);
    }

    public ItemStack asStack(String name) {
        return (ItemStack) get(name);
    }

    public boolean asBoolean(String name) {
        return (Boolean) get(name);
    }
}
