/*
 * McZLauncher (ZeTRiX's Minecraft Launcher)
 * Copyright (C) 2012 Evgen Yanov <http://www.zetlog.ru>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program (In the "_License" folder). If not, see <http://www.gnu.org/licenses/>.
*/

package ru.zetrix.settings;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Locale;

public class ShieldUtil {
    public static String ShaHash(String pass) {
        String sha1 = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(pass.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }  catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sha1;
    }
    
    public static String MACAddr() {
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (ni != null) {
                StringBuilder sb = new StringBuilder();
                Formatter formatter = new Formatter(sb, Locale.US);
                byte[] mac = ni.getHardwareAddress();
                if (mac != null)
                    for (int i = 0; i < mac.length; i++) {
                        formatter.format("%02X%s", new Object[] { Byte.valueOf(mac[i]), i < mac.length - 1 ? "-" : "" });
                        if (formatter.toString().equals("")) return "Fail";
                    }
                return formatter.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Fail";
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
