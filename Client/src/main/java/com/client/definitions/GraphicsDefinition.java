package com.client.definitions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;

import com.client.Configuration;
import com.client.MRUNodes;
import com.client.Model;
import com.client.Stream;
import com.client.StreamLoader;

public final class GraphicsDefinition {

	public static void unpackConfig(StreamLoader streamLoader) {
		Stream stream = new Stream(streamLoader.getArchiveData("spotanim.dat"));
		int length = stream.readUShort();
		if (cache == null)
			cache = new GraphicsDefinition[length + 15000];
		for (int j = 0; j < length; j++) {
			if (cache[j] == null) {
				cache[j] = new GraphicsDefinition();
			}
			if (j == 65535) {
				j = -1;
			}
			cache[j].index = j;
			cache[j].setDefault();
			cache[j].readValues(stream);
		}
		cache[1282] = new GraphicsDefinition();
		cache[1282].index = 1282;
		cache[1282].modelId = 44811;
		cache[1282].anInt406 = 7155;
		cache[1282].aAnimation_407 = AnimationDefinition.anims[cache[1282].anInt406];

		if (Configuration.dumpDataLists) {
			gfxDump();
		}
	}

	public static void gfxDump() {
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter("./temp/gfx_list.txt"));
			for (int i = 0; i < cache.length; i++) {
				GraphicsDefinition item = cache[i];
				if (item == null)
					continue;
				fw.write("case " + i + ":");
				fw.write(System.getProperty("line.separator"));

				fw.write("gfx.anIntArray409 = \"" + Arrays.toString(item.anIntArray409) + "\";");
				fw.write(System.getProperty("line.separator"));

				fw.write("gfx.modelId = \"" + item.modelId + "\";");
				fw.write(System.getProperty("line.separator"));

				fw.write("break;");
				fw.write(System.getProperty("line.separator"));
				fw.write(System.getProperty("line.separator"));
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readValues(Stream stream) {
		while(true) {
			int i = stream.readUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				modelId = stream.readUShort();
			} else if (i == 2) {
				anInt406 = stream.readUShort();
				if (AnimationDefinition.anims != null) {
					aAnimation_407 = AnimationDefinition.anims[anInt406];
				}
			} else if (i == 4) {
				anInt410 = stream.readUShort();
			} else if (i == 5) {
				anInt411 = stream.readUShort();
			} else if (i == 6) {
				anInt412 = stream.readUShort();
			} else if (i == 7) {
				anInt413 = stream.readUnsignedByte();
			} else if (i == 8) {
				anInt414 = stream.readUnsignedByte();
			} else if (i == 40) {
				int j = stream.readUnsignedByte();
				for (int k = 0; k < j; k++) {
					anIntArray408[k] = stream.readUShort();
					anIntArray409[k] = stream.readUShort();
				}
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		}
	}


	public static GraphicsDefinition fetch(int modelId) {
		for (GraphicsDefinition anim : cache) {
			if (anim == null) {
				continue;
			}
			if (anim.modelId == modelId) {
				return anim;
			}
		}
		return null;
	}

	public Model getModel() {
		Model model = (Model) aMRUNodes_415.insertFromCache(index);
		if (model != null)
			return model;
		model = Model.method462(modelId);
		if (model == null)
			return null;
		for (int i = 0; i < anIntArray408.length; i++)
			if (anIntArray408[0] != 0) //default frame id
				model.replaceColor(anIntArray408[i], anIntArray409[i]);

		aMRUNodes_415.removeFromCache(model, index);
		return model;
	}

	private void setDefault() {
		modelId = -1;
		anInt406 = -1;
		anIntArray408 = new int[6];
		anIntArray409 = new int[6];
		anInt410 = 128;
		anInt411 = 128;
		anInt412 = 0;
		anInt413 = 0;
		anInt414 = 0;
	}

	public GraphicsDefinition() {
		anInt400 = 9;
		anInt406 = -1;
		anIntArray408 = new int[6];
		anIntArray409 = new int[6];
		anInt410 = 128;
		anInt411 = 128;
	}

	public int getModelId() {
		return modelId;
	}

	public int getIndex() {
		return index;
	}

	public final int anInt400;
	public static GraphicsDefinition cache[];
	private int index;
	private int modelId;
	public int anInt406;
	public AnimationDefinition aAnimation_407;
	public int[] anIntArray408;
	public int[] anIntArray409;
	public int anInt410;
	public int anInt411;
	public int anInt412;
	public int anInt413;
	public int anInt414;
	public static MRUNodes aMRUNodes_415 = new MRUNodes(30);

}