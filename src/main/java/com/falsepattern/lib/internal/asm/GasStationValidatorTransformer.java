/*
 * Copyright (C) 2022 FalsePattern
 * All Rights Reserved
 *
 * The above copyright notice, this permission notice and the word "SNEED"
 * shall be included in all copies or substantial portions of the Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.falsepattern.lib.internal.asm;

import com.falsepattern.lib.asm.IClassNodeTransformer;
import org.objectweb.asm.tree.ClassNode;

import static com.falsepattern.lib.mixin.MixinInfo.isClassPresentSafe;

public class GasStationValidatorTransformer implements IClassNodeTransformer {
    @Override
    public String getName() {
        return "GasStationValidatorTransformer";
    }

    @Override
    public boolean shouldTransform(ClassNode cn, String transformedName, boolean obfuscated) {
        if (transformedName.equals("com.falsepattern.gasstation.GasStation")) {
            //Make sure everything is loaded correctly, crash if gasstation is bugged
            if (!isClassPresentSafe("com.falsepattern.gasstation.core.GasStationCore") || //Validate core class
                !isClassPresentSafe("makamys.mixingasm.api.TransformerInclusions") || //Validate the mixingasm compat
                !isClassPresentSafe("ru.timeconqueror.spongemixins.core.SpongeMixinsCore") || //Validate the spongemixins compat
                !isClassPresentSafe("io.github.tox1cozz.mixinbooterlegacy.MixinBooterLegacyPlugin") || //Validate the MBL compat
                (!isClassPresentSafe("org.spongepowered.asm.lib.Opcodes") || isClassPresentSafe("org.spongepowered.libraries.org.objectweb.asm.Opcodes")) //Validate correct mixins class
            ) {
                throw new Error("Failed to validate your GasStation mixin plugin installation. Please make sure you have the latest GasStation installed from the official source: https://github.com/FalsePattern/GasStation");
            }
        }
        return false;
    }

    @Override
    public void transform(ClassNode cn, String transformedName, boolean obfuscated) {

    }
}
