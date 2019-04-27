package core.commands;

/**
 * Интерфейс для всех команд имеющих описание, если
 * команда не имплементировала данный интерфейс она может не покааться при
 * выводе команды help через {@link core.util.Help}
 *
 * @author Arthur Kupriyanov
 */
public interface Helpable {
    String getDescription();
}
