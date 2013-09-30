/**
 * This file is part of RibbonProber application (check README).
 * Copyright (C) 2012-2013 Stanislav Nepochatov
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
**/

package RibbonProber;

/**
 * Class of protocol commands description and common tools
 * @author Stanislav Nepochatov
 */
public abstract class ProtocolDecription {
    
    /**
     * Commands array
     */
    private static java.util.ArrayList<ProtocolCommand> commands = new java.util.ArrayList<>();
    
    /**
     * Command class
     */
    private static class ProtocolCommand {
        
        /**
         * Name of command;
         */
        public String NAME;
        
        /**
         * Doc string of command;
         */
        public String DOC;
        
        /**
         * Auto paste string
         */
        public String AUTO_PASTE;
        
        /**
         * Protocol level of command
         */
        public Integer COMMAND_LEVEL;
        
        /**
         * Constructor for command
         * @param givenName name of command
         * @param givenDoc doc for command
         * @param givenPaste paste arguments for command
         */
        ProtocolCommand(String givenName, String givenDoc, String givenPaste, Integer givenLevel) {
            NAME = givenName;
            DOC = givenDoc;
            AUTO_PASTE = givenPaste;
            COMMAND_LEVEL = givenLevel;
        }
    }
    
    /**
     * Init protocol commands
     */
    public static void initProtocol() {
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_INIT",
                "[LEVEL_0] RIBBON_NCTL_INIT\n"
                    + "Команда привітання з сервером.\n"
                    + "Аргументи: ТИП_З’ЄДНАННЯ,ВЕРСІЯ_ПРОТОКОЛУ,КОДОВА_СТОРІНКА\n"
                    + "Приклад: RIBBON_NCTL_INIT:CLIENT,a2,UTF-8",
                ":CLIENT,a2," + System.getProperty("file.encoding") , 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_LOGIN",
                "[LEVEL_0] RIBBON_NCTL_LOGIN\n" 
                    + "Команда реєстрації користувача у системі.\n"
                    + "Аргументи: ЛОГІН,ПАРОЛЬ у формі csv\n"
                    + "Приклад: RIBBON_NCTL_LOGIN:{root},74cc1c60799e0a786ac7094b532f01b1",
                ":{root},63a9f0ea7bb98050796b649e85481845", 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_GET_ID",
                "[LEVEL_0] RIBBON_NCTL_GET_ID\n"
                    + "Команда отримання ідентефикатору цієї сесії.\n"
                    + "Аргументи: немає",
                ":", 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_RESUME",
                "[LEVEL_0] RIBBON_NCTL_GET_ID\n"
                    + "Команда отримання ідентефикатору цієї сесії.\n"
                    + "Аргументи: ІДЕНТЕФІКАТОР\n"
                    + "Приклад: RIBBON_NCTL_RESUME:74cc1c60799e0a786ac7094b532f01b1",
                ":", 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_REM_LOGIN",
                "[LEVEL_0] RIBBON_NCTL_REM_LOGIN\n" 
                    + "Команда реєстрації користувача через видалений інтерфейс.\n"
                    + "Аргументи: {ЛОГІН},ПАРОЛЬ (у формі csv)\n"
                    + "Приклад: RIBBON_NCTL_REM_LOGIN:{root},63a9f0ea7bb98050796b649e85481845",
                ":{root},63a9f0ea7bb98050796b649e85481845", 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_GET_USERNAME",
                "[LEVEL_0] RIBBON_NCTL_GET_USERNAME\n"
                    + "Команда отримання логіну поточного користувача.\n"
                    + "Аргументи: немає",
                ":", 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_SET_REMOTE_MODE",
                "[LEVEL_0] RIBBON_NCTL_SET_REMOTE_MODE\n"
                    + "Команда керування видаленим режимом поточного з’єднання.\n"
                    + "Аргументи: 0 чи 1 (0 - вимикає, 1- вмикає)\n"
                    + "Приклад: RIBBON_NCTL_SET_REMOTE_MODE:1",
                ":1", 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_ACCESS_CONTEXT",
                "[LEVEL_0] RIBBON_NCTL_ACCESS_CONTEXT\n"
                    + "Команда зміни контексту виконання наступної команди.\n"
                    + "Аргументи: {КОРИСТУВАЧ}\n"
                    + "Приклад: RIBBON_NCTL_ACCESS_CONTEXT:{test}"
                    + "\n\nУВАГА! Ця команда для виконання вимагає увікнення видаленого режиму\n"
                    + "(команда RIBBON_NCTL_SET_REMOTE_MODE)!",
                ":{test}", 0));
        
        commands.add(new ProtocolCommand("RIBBON_NCTL_CLOSE",
                "[LEVEL_0] RIBBON_NCTL_CLOSE\n"
                    + "Команда закриття з’єднання з сервером.\n"
                    + "Аргументи: немає",
                ":", 0));
        
        commands.add(new ProtocolCommand("RIBBON_GET_DIRS",
                "[LEVEL_1] RIBBON_GET_DIRS\n"
                    + "Команда повертання списку напрямків системи у csv формі.\n"
                    + "Аргументы: немає",
                ":", 1));
        
        commands.add(new ProtocolCommand("RIBBON_GET_PSEUDO",
                "[LEVEL_1] RIBBON_GET_PSEUDO\n"
                    + "Команда повернення списку псевдонапрямків які може використовувати\n"
                    + "користувач у сsv формі.\n"
                    + "Аргументи: немає"
                    + "\n\nУВАГА! Ця команда для виконання вимагає увікнення видаленого режиму\n"
                    + "(команда RIBBON_NCTL_SET_REMOTE_MODE)!",
                ":", 1));
        
        commands.add(new ProtocolCommand("RIBBON_GET_TAGS",
                "[LEVEL_1] RIBBON_GET_TAGS\n"
                    + "Команда повертення списку тегів системи у csv формі.\n"
                    + "Аргументи: немає",
                ":", 1));
        
        commands.add(new ProtocolCommand("RIBBON_LOAD_BASE_FROM_INDEX",
                "[LEVEL_1] RIBBON_LOAD_BASE_FROM_INDEX\n"
                    + "Команда повертання списку індекса бази повідомлень.\n"
                    + "починаючи з певного індексу.\n"
                    + "Аргументи:ИНДЕКС або 0 (повернення усього індексу бази)\n"
                    + "Приклад: RIBBON_LOAD_BASE_FROM_INDEX:0000000107",
                ":0", 1));
        
        commands.add(new ProtocolCommand("RIBBON_POST_MESSAGE",
                "[LEVEL_1] RIBBON_POST_MESSAGE\n"
                    + "Команда випуску повідомлення до системи.\n"
                    + "Аргументи:ІНДЕКС_ОРИГІНАЛУ,[НАПРЯМОК1,НАПРЯМОК2],МОВА,{Заголовок},[ТЕГ1,ТЕГ2],{СИСТЕМНІ_ОЗНАКИ}\n"
                    + "ТЕКСТ_ПОВІДОМЛЕННЯ\n"
                    + "END:\n"
                    + "Приклад: RIBBON_POST_MESSAGE:-1,[СИСТЕМА.ТЕСТ],UA,{Тестове повідомлення},[система,тест],{}\n"
                    + "Це тестове повідомлення.\n"
                    + "END:\n\n"
                    + "Примітка: команда END: на кінці повідомлення обов’язкова,\n"
                    + "без цієї команди сервер буде сприймати усі наступні команди\n"
                    + "як текст повідомлення!",
                ":-1,[СИСТЕМА.Тест],UA,{Тестове повідомлення},[тест,система],{}\nЦе тестове повідомлення на напрямок СИСТЕМА.Тест.\nEND:", 1));
        
        commands.add(new ProtocolCommand("RIBBON_POST_MESSAGE_BY_PSEUDO",
                "[LEVEL_1] RIBBON_POST_MESSAGE_BY_PSEUDO\n"
                    + "Команда випуску повідомлення до системи за псевдонапрямком.\n"
                    + "Аргументи:{ПСЕВДОНАПРЯМОК},МОВА,{Заголовок},[ТЕГ1,ТЕГ2],{СИСТЕМНІ_ОЗНАКИ}\n"
                    + "ТЕКСТ_ПОВІДОМЛЕННЯ\n"
                    + "END:\n"
                    + "Приклад: RIBBON_POST_MESSAGE_BY_PSEUDO:{Тест},UA,{Тестове повідомлення},[система,тест],{}\n"
                    + "Це тестове повідомлення.\n"
                    + "END:\n\n"
                    + "Примітка: команда END: на кінці повідомлення обов’язкова,\n"
                    + "без цієї команди сервер буде сприймати усі наступні команди\n"
                    + "як текст повідомлення!"
                    + "\n\nУВАГА! Ця команда для виконання вимагає увікнення видаленого режиму\n"
                    + "(команда RIBBON_NCTL_SET_REMOTE_MODE)!",
                ":{Тест},UA,{Тестове повідомлення},[тест,система],{}\nЦе тестове повідомлення на псевдонапрямок Тест.\nEND:", 1));
        
        commands.add(new ProtocolCommand("RIBBON_GET_MESSAGE",
                "[LEVEL_1] RIBBON_GET_MESSAGE\n"
                    + "Команда одержання тексту повідомлення певного напрямку та індексу.\n"
                    + "Аргументи:НАПРЯМОК,ІНДЕКС\n"
                    + "Приклад: RIBBON_GET_MESSAGE:СИСТЕМА.ТЕСТ,0000000107",
                ":", 1));
        
        commands.add(new ProtocolCommand("RIBBON_MODIFY_MESSAGE",
                "[LEVEL_1] RIBBON_MODIFY_MESSAGE\n"
                    + "Команда редагування тексту повідомлення певного напрямку за індексом.\n"
                    + "Аргументи: ІНДЕКС,[НАПРЯМОК1,НАПРЯМОК2],МОВА,{Заголовок},[ТЕГ1,ТЕГ2],{СИСТЕМНІ_ОЗНАКИ}\n"
                    + "НОВИЙ_ТЕКСТ_ПОВІДОМЛЕННЯ\n"
                    + "END:\n"
                    + "Приклад:RIBBON_MODIFY_MESSAGE:0000000107,[СИСТЕМА.ТЕСТ],{Тестове повідомлення},[система,тест],{}\n"
                    + "Новий текст тестового повідомлення.\n"
                    + "END:\n\n"
                    + "Примітка: команда END: на кінці повідомлення обов’язкова,\n"
                    + "без цієї команди сервер буде сприймати усі наступні команди\n"
                    + "як текст повідомлення!",
                ":-1,[],UKN,{},[],{}\n\nEND:", 1));
        
        commands.add(new ProtocolCommand("RIBBON_MODIFY_MESSAGE_BY_PSEUDO",
                "[LEVEL_1] RIBBON_MODIFY_MESSAGE_BY_PSEUDO\n"
                    + "Команда редагування тексту повідомлення певного напрямку за індексом і псевдонапрямком.\n"
                    + "Аргументи: ІНДЕКС,{ПСЕВДОНАПРЯМОК},МОВА,{Заголовок},[ТЕГ1,ТЕГ2],{СИСТЕМНІ_ОЗНАКИ}\n"
                    + "НОВИЙ_ТЕКСТ_ПОВІДОМЛЕННЯ\n"
                    + "END:\n"
                    + "Приклад:RIBBON_MODIFY_MESSAGE_BY_PSEUDO:0000000107,{Тест},{Тестове повідомлення},[система,тест],{}\n"
                    + "Новий текст тестового повідомлення.\n"
                    + "END:\n\n"
                    + "Примітка: команда END: на кінці повідомлення обов’язкова,\n"
                    + "без цієї команди сервер буде сприймати усі наступні команди\n"
                    + "як текст повідомлення!"
                    + "\n\nУВАГА! Ця команда для виконання вимагає увікнення видаленого режиму\n"
                    + "(команда RIBBON_NCTL_SET_REMOTE_MODE)!",
                ":-1,{},UKN,{},[],{}\n\nEND:", 1));
        
        commands.add(new ProtocolCommand("RIBBON_DELETE_MESSAGE",
                "[LEVEL_1] RIBBON_DELETE_MESSAGE\n"
                    + "Команда видаленя повідомлення з усіх напрямків за індексом.\n"
                    + "Аргументи:ІНДЕКС_ПОВІДОМЛЕННЯ\n"
                    + "Приклад:RIBBON_DELETE_MESSAGE:0000000107",
                ":", 1
                ));
        commands.add(new ProtocolCommand("RIBBON_ADD_MESSAGE_PROPERTY",
                "[LEVEL_1] RIBBON_ADD_MESSAGE_PROPERTY\n"
                    + "Команда додавання системної ознаки до повідомлення.\n"
                    + "Аргументи:ІНДЕКС,ТИП_ОЗНАКИ,{ТЕКСТОВА_МІТКА}\n"
                    + "Приклад:RIBBON_ADD_MESSAGE_PROPERTY:0000000107,URGENT,{!!}",
                ":-1,URGENT,{}", 1
                ));
        
        commands.add(new ProtocolCommand("RIBBON_DEL_MESSAGE_PROPERTY",
                "[LEVEL_1] RIBBON_DEL_MESSAGE_PROPERTY\n"
                    + "Команда видалення системної ознаки з повідомлення.\n"
                    + "Аргументи:ІНДЕКС,ТИП_ОЗНАКИ,ДАТА_ТА_ЧАС_ОЗНАКИ\n"
                    + "Приклад:RIBBON_DEL_MESSAGE_PROPERTY:0000000107,URGENT,23:43:27 20.01.2013",
                ":-1,URGENT,", 1
                ));
    }
    
    /**
     * Get list of commands names
     * @return string array with names of RibbonProtocol commands
     */
    public static String[] getCommandList(Integer givenLevel) {
        java.util.ListIterator<ProtocolCommand> commIter = commands.listIterator();
        java.util.ArrayList<String> commList = new java.util.ArrayList<>();
        while (commIter.hasNext()) {
            ProtocolCommand currCommand = commIter.next();
            if (currCommand.COMMAND_LEVEL == givenLevel || givenLevel == -1) {
                commList.add(currCommand.NAME);
            }
        }
        return commList.toArray(new String[commList.size()]);
    }
    
    /**
     * Get doc string of command
     * @param givenName name to search
     * @return doc of command
     */
    public static String getDoc(String givenName) {
        java.util.ListIterator<ProtocolCommand> commIter = commands.listIterator();
        while (commIter.hasNext()) {
            ProtocolCommand currCommand = commIter.next();
            if (currCommand.NAME.equals(givenName)) {
                return currCommand.DOC;
            }
        }
        return "";
    }
    
    /**
     * Get paste string of command
     * @param givenName name to search
     * @return paste string for command arguments
     */
    public static String getPaste(String givenName) {
        java.util.ListIterator<ProtocolCommand> commIter = commands.listIterator();
        while (commIter.hasNext()) {
            ProtocolCommand currCommand = commIter.next();
            if (currCommand.NAME.equals(givenName)) {
                return currCommand.AUTO_PASTE;
            }
        }
        return "";
    }
}
