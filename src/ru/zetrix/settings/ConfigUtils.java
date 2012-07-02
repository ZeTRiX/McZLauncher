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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class ConfigUtils
{
  private File out;
  private Boolean cached = Boolean.valueOf(false);
  private String filename = null;
  private HashMap<String, String> cache;
  private InputStream input = null;

  public ConfigUtils(String filename, String out)
  {
    this.filename = filename;
    this.out = new File(out);
  }

  public ConfigUtils(String filename, File out)
  {
    this.filename = filename;
    this.out = out;
  }

  public ConfigUtils(InputStream input, File out)
  {
    this.input = input;
    this.out = out;
  }

  public ConfigUtils(File out) throws FileNotFoundException
  {
    if (!out.exists())
      throw new FileNotFoundException("Эта переменная не найдена");
    this.out = out;
  }

  public Boolean isCached()
  {
    return this.cached;
  }

  public void setCached(Boolean cached)
  {
    this.cached = cached;
    if ((cached = Boolean.valueOf(false)).booleanValue())
      this.cache = null;
  }

  private void create(String filename)
  {
    InputStream input = getClass().getResourceAsStream(filename);
    if (input != null)
    {
      FileOutputStream output = null;
      try
      {
        this.out.getParentFile().mkdirs();
        output = new FileOutputStream(this.out);
        byte[] buf = new byte[8192];
        int length;
        while ((length = input.read(buf)) > 0)
        {
          output.write(buf, 0, length);
        }
      }
      catch (Exception localException)
      {
        try {
          input.close();
        } catch (Exception localException1) {
        }
        try {
          if (output != null)
            output.close();
        }
        catch (Exception localException2)
        {
        }
      }
      finally
      {
        try
        {
          input.close();
        } catch (Exception localException3) {
        }
        try {
          if (output != null)
            output.close();
        }
        catch (Exception localException4) {
        }
      }
    }
  }

  private void create(InputStream input) {
    if (input != null)
    {
      FileOutputStream output = null;
      try
      {
        output = new FileOutputStream(this.out);
        byte[] buf = new byte[8192];
        int length;
        while ((length = input.read(buf)) > 0)
        {
          output.write(buf, 0, length);
        }
      }
      catch (Exception localException)
      {
        try {
          input.close();
        } catch (Exception localException1) {
        }
        try {
          if (output != null)
            output.close();
        }
        catch (Exception localException2)
        {
        }
      }
      finally
      {
        try
        {
          input.close();
        } catch (Exception localException3) {
        }
        try {
          if (output != null)
            output.close();
        } catch (Exception localException4) {
        }
      }
    }
  }

  private HashMap<String, String> loadHashMap() {
    HashMap result = new HashMap();
    try
    {
      BufferedReader br = new BufferedReader(new FileReader(this.out));
      String line;
      while ((line = br.readLine()) != null)
      {
        if ((!line.isEmpty()) && (!line.startsWith("#")) && (line.contains(": "))) {
          String[] args = line.split(": ");
          if (args.length < 2)
          {
            result.put(args[0], null);
          }
          else
            result.put(args[0], args[1]); 
        }
      }
    } catch (IOException localIOException) {
    }
    return result;
  }

  public void load()
  {
    if ((this.filename != null) && (!this.out.exists())) create(this.filename);
    if ((this.input != null) && (!this.out.exists())) create(this.input);
    if (this.cached.booleanValue()) this.cache = loadHashMap();
  }

  public String getPropertyString(String property)
  {
    try
    {
      if (this.cached.booleanValue()) return (String)this.cache.get(property);

      HashMap contents = loadHashMap();
      return (String)contents.get(property);
    } catch (Exception localException) {
    }
    return null;
  }

  public Integer getPropertyInteger(String property)
  {
    try
    {
      if (this.cached.booleanValue()) return Integer.valueOf(Integer.parseInt((String)this.cache.get(property)));

      HashMap contents = loadHashMap();
      return Integer.valueOf(Integer.parseInt((String)contents.get(property)));
    } catch (Exception localException) {
    }
    return null;
  }

  public Boolean getPropertyBoolean(String property)
  {
    try
    {
      String result;
      if (this.cached.booleanValue()) { result = (String)this.cache.get(property);
      } else
      {
        HashMap contents = loadHashMap();
        result = (String)contents.get(property);
      }
      if ((result != null) && (result.equalsIgnoreCase("true"))) return Boolean.valueOf(true);
      return Boolean.valueOf(false); } catch (Exception localException) {
    }
    return null;
  }

  public Double getPropertyDouble(String property)
  {
    try
    {
      String result;
      if (this.cached.booleanValue()) { result = (String)this.cache.get(property);
      } else
      {
        HashMap contents = loadHashMap();
        result = (String)contents.get(property);
      }
      if (!result.contains(".")) result = result + ".0";
      return Double.valueOf(Double.parseDouble(result)); } catch (Exception localException) {
    }
    return null;
  }

  public Boolean checkProperty(String property)
  {
    try
    {
      String check;
      if (this.cached.booleanValue()) {
        check = (String)this.cache.get(property);
      }
      else {
        HashMap contents = loadHashMap();
        check = (String)contents.get(property);
      }
      if (check != null) return Boolean.valueOf(true); 
    }
    catch (Exception e)
    {
      return Boolean.valueOf(false);
    }
    String check;
    return Boolean.valueOf(false);
  }

  private void flush(HashMap<Integer, String> newContents)
  {
    try
    {
      delFile(this.out);
      this.out.createNewFile();
      BufferedWriter writer = new BufferedWriter(new FileWriter(this.out));
      for (int i = 1; i <= newContents.size(); i++)
      {
        String line = (String)newContents.get(Integer.valueOf(i));
        if (line == null)
        {
          writer.append("\n");
        }
        else {
          writer.append(line);
          writer.append("\n");
        }
      }
      writer.flush();
      writer.close();
      if (this.cached.booleanValue()) load(); 
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  private void delFile(File file)
  {
    if (file.exists()) file.delete();
  }

  private HashMap<Integer, String> getAllFileContents()
  {
    HashMap result = new HashMap();
    Integer i = Integer.valueOf(1);
    try
    {
      BufferedReader br = new BufferedReader(new FileReader(this.out));
      String line;
      while ((line = br.readLine()) != null)
      {
        if (line.isEmpty())
        {
          result.put(i, null);
          i = Integer.valueOf(i.intValue() + 1);
        }
        else
        {
          result.put(i, line);
          i = Integer.valueOf(i.intValue() + 1);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return result;
  }

  public void insertComment(String comment)
  {
    HashMap contents = getAllFileContents();
    contents.put(Integer.valueOf(contents.size() + 1), "#" + comment);
    flush(contents);
  }

  public void insertComment(String comment, Integer line)
  {
    HashMap contents = getAllFileContents();
    if (line.intValue() >= contents.size() + 1) return;
    HashMap newContents = new HashMap();
    for (int i = 1; i < line.intValue(); i++) newContents.put(Integer.valueOf(i), (String)contents.get(Integer.valueOf(i)));
    newContents.put(line, "#" + comment);
    for (int i = line.intValue(); i <= contents.size(); i++) newContents.put(Integer.valueOf(i + 1), (String)contents.get(Integer.valueOf(i)));
    flush(newContents);
  }

  public void put(String property, Object obj)
  {
    HashMap contents = getAllFileContents();
    contents.put(Integer.valueOf(contents.size() + 1), property + ": " + obj.toString());
    flush(contents);
  }

  public void put(String property, Object obj, Integer line)
  {
    HashMap contents = getAllFileContents();
    if (line.intValue() >= contents.size() + 1) return;
    HashMap newContents = new HashMap();
    for (int i = 1; i < line.intValue(); i++) newContents.put(Integer.valueOf(i), (String)contents.get(Integer.valueOf(i)));
    newContents.put(line, property + ": " + obj.toString());
    for (int i = line.intValue(); i <= contents.size(); i++) newContents.put(Integer.valueOf(i + 1), (String)contents.get(Integer.valueOf(i)));
    flush(newContents);
  }

  public void changeProperty(String property, Object obj)
  {
    HashMap contents = getAllFileContents();
    if (contents == null) return;
    for (int i = 1; i <= contents.size(); i++)
    {
      if (contents.get(Integer.valueOf(i)) != null) {
        String check = (String)contents.get(Integer.valueOf(i));
        if (!check.startsWith(property))
          continue;
        check = check.replace(property, "");
        if (check.startsWith(": ")) {
          contents.remove(Integer.valueOf(i));
          contents.put(Integer.valueOf(i), property + ": " + obj.toString());
        }
      }
    }
    flush(contents);
  }

  public Integer getLineCount()
  {
    HashMap contents = getAllFileContents();
    return Integer.valueOf(contents.size());
  }
}