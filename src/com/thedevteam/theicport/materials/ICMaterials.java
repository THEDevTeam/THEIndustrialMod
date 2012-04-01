/*
 * This file is part of IC (http://www.spout.org/).
 *
 * IC is licensed under the SpoutDev License Version 1.
 *
 * IC is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the SpoutDev License Version 1.
 *
 * IC is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the SpoutDev License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.
 */

package couk.rob4001.ic.materials;

//Equivilent to Vanilla's VanillaMaterials - All Blocks should be registered here

import org.spout.vanilla.material.block.Ore;

public class ICMaterials {

    public static Ore URANIUM_ORE = (Ore) register(new Ore("Uranium Ore",1));
}
