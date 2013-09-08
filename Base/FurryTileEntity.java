/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2013
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.FurryKingdoms.Base;

import Reika.DragonAPI.Base.TileEntityBase;
import Reika.FurryKingdoms.Registry.FurryBlocks;
import Reika.FurryKingdoms.Registry.FurryTiles;

public abstract class FurryTileEntity extends TileEntityBase {

	@Override
	public final boolean canUpdate() {
		return true;
	}

	@Override
	public final int getTileEntityBlockID() {
		return FurryBlocks.TILEENTITY.getBlockID();
	}

	@Override
	public String getTEName() {
		return FurryTiles.TEList[this.getRealBlockMetadata()].getName();
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		return pass == 0;
	}
}
