package net.skwh.NationsAndRanks.BaseTemplates;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.block.Block;

public class Borders {
	private Set<Block> borderBlocks;
	private Nation baseNation;
	
	public Set<Set<Integer>> getBorderAsIntSet() {
		Set<Set<Integer>> intSet = new HashSet<Set<Integer>>();
		for (int i=0;i<borderBlocks.size();i++) {
			Set<Integer> blockPos = new HashSet<Integer>();
			int blockX = ((Block) borderBlocks.toArray()[i]).getX();
			int blockZ = ((Block) borderBlocks.toArray()[i]).getZ();
			blockPos.add(blockX);
			blockPos.add(blockZ);
			intSet.add(blockPos);
		}
		return intSet;
	}
	
	public Borders(Nation nS, Set<Block> b) {
		borderBlocks = b;
		baseNation = nS;
	}
}
