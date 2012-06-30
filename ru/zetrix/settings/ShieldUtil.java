/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.zetrix.settings;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Locale;

/**
 *
 * @author ZeTRiX
 */
public class ShieldUtil {
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
                        if (formatter.toString().equals("")) return "NO_MAC_Address";
                    }
                return formatter.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "NO_MAC_Address";
    }
    
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
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
