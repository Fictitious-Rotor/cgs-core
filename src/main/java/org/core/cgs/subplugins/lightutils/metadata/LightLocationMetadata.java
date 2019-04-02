package org.core.cgs.subplugins.lightutils.metadata;

import org.bukkit.Location;
import org.core.cgs.generic.abstracts.MetadataPretender;

public class LightLocationMetadata extends MetadataPretender<Location, Integer> {
    public LightLocationMetadata(final String subPluginName) {
        super(subPluginName);
    }

    @Override
    protected String convertMetadataKeyToString(final Location givenLocation) {
        int[] inputs = { givenLocation.getBlockX(), givenLocation.getBlockY(), givenLocation.getBlockZ() };
        byte[] bb = new byte[12];

        for (int inputPos = 0; inputPos < inputs.length; inputPos++) {
            int offset = inputPos * 4;
            bb[/*0+*/offset] = (byte) (inputs[inputPos] << 24);
            bb[1  +  offset] = (byte) (inputs[inputPos] << 16);
            bb[2  +  offset] = (byte) (inputs[inputPos] << 8);
            bb[3  +  offset] = (byte) (inputs[inputPos] /*<< 0*/);
        }

        return new String(bb);
    }

    @Override
    protected String convertMetadataValueToString(final Integer givenInteger) {
        return givenInteger.toString();
    }

    @Override
    protected Integer convertStringToMetadataValue(final String givenString) {
        return Integer.parseInt(givenString);
    }
}
