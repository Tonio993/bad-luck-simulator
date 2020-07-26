package it.traininground.badluck.packer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class MyPacker {

    public static void main(String[] args) throws Exception {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.filterMin = Texture.TextureFilter.Linear;
        settings.filterMag = Texture.TextureFilter.Linear;
        TexturePacker.process(settings,"packer/source", "packer/destination", "terrain");
    }

}
