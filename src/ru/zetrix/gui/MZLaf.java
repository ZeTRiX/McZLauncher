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

package ru.zetrix.gui;

import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicLookAndFeel;
import ru.zetrix.gui.laf.MZButtonUI;

public class MZLaf extends BasicLookAndFeel {

    @Override
    public String getName() {
        return "MZLaF";
    }

    @Override
    public String getID() {
        return getName();
    }

    @Override
    public String getDescription() {
        return "LaF for Minecraft ZeTRiX's Launcher.";
    }

    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }
    
    @Override
    public boolean getSupportsWindowDecorations() {
        return true;
    }
    
    protected void initClassDefaults (UIDefaults table) {
        super.initClassDefaults(table);
        table.put ("ButtonUI", MZButtonUI.class.getCanonicalName());
    }
}
