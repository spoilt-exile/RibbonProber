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
 * Main class
 * @author Stanislav Nepochatov
 */
public class RibbonProber {
    
    /**
     * Client socket object
     */
    public static java.net.Socket ClientSocket;
    
    /**
     * Stream of the answers from server
     */
    public static java.io.BufferedReader inStream;
    
    /**
     * PrintWriter to the server
     */
    public static java.io.PrintWriter outStream;
    
    public static mainFrame mainWindow;
    
    private static proberThread networking;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProtocolDecription.initProtocol();
        mainWindow = new mainFrame();
        mainWindow.setVisible(true);
        mainWindow.addToLog("Дебагер завантажено та підготовлено до роботі.\n"
                + "перед початком роботи з’єднайтись з сервером, вказавши адрес та порт сервера,\n"
                + "та натиснувши кнопки 'З’єднати'\n"
                + "для отримання справки оберіть команду протоколу, та натиснить кнопку '?'\n"
                + "для формування команди, оберіть необхідну команду та натисніть кнопку 'Вставити'\n"
                + "для запуску сформованної команди, натисніть кнопку 'Послати!'\n");
    }
    
    /**
     * Connect to server
     */
    public static void connect() {
        if (networking != null) {
            mainWindow.addToLog("ПОВІДОМЛЕННЯ: перепідключення\n");
            outStream.println("RIBBON_EXIT:");
        }
        networking = new proberThread();
        networking.start();
    }
    
    /**
     * Network communication thread
     */
    private static class proberThread extends Thread {
        
        proberThread() {
            try {
                RibbonProber.ClientSocket = new java.net.Socket(java.net.InetAddress.getByName(RibbonProber.mainWindow.ipField.getText()),
                    Integer.parseInt(RibbonProber.mainWindow.portField.getText()));
            } catch (java.io.IOException ex) {
                mainWindow.addToLog("ПОМИЛКА: неможливо з'єднатися з сервером!\n");
            }
        }
        
        @Override
        public void run() {
            if (RibbonProber.ClientSocket != null) {
                try {
                    Boolean isAlive = true;
                    inStream = new java.io.BufferedReader(new java.io.InputStreamReader(ClientSocket.getInputStream(), "UTF-8"));
                    outStream = new java.io.PrintWriter(ClientSocket.getOutputStream(), true);
                    mainWindow.addToLog("З'єднання успішно встановлено.\n");
                    String inputLine;
                    while (isAlive) {
                       mainWindow.addToLog("< " + (inputLine = inStream.readLine()) + "\n");
                       if (inputLine.equals("COMMIT_CLOSE:")) {
                           isAlive = false;
                       }
                    }
                } catch (java.lang.NullPointerException ex ) {
                    mainWindow.addToLog("ПОМИЛКА: з'єднання розірвано!\n");
                } catch (java.io.IOException ex) {
                    mainWindow.addToLog("ПОМИЛКА: неможливо зчитати дані з сокету!\n");
                }
            }
        }
        
    }
}
